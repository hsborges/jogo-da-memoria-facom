let inicio = null;
let intervalo = null;
let uuid = null;

let jogadas = 0;
let acertos = 0;
let pontuacao = 0;

let restauraCardTimeout = null;

const cardsVirados = [null, null];
const professores = ['amauri.jpeg', 'awdren.jpg', 'edna.jpeg', 'hudson.jpeg', 'jane.jpg', 'jucele.jpg', 'liana.jpeg', 'mongelli.jpg', 'renan.jpg', 'said.jpeg', 'samuel.jpeg', 'takashi.jpg'];

function openCard(event) {
	if (intervalo === null) startTimer();

	const target = event.target;
	
	if (target.classList.contains('virado')) {
		return;
	} else if (cardsVirados[0] === null) {
		target.classList.add('virado');
		cardsVirados[0] = target;
	} else if (cardsVirados[1] === null) {
		if (cardsVirados[0] === target) return;
		
		document.querySelector('span.jogadas-count').innerHTML = (jogadas += 1);

		target.classList.add('virado');
		cardsVirados[1] = target;
		

		if (cardsVirados[0].classList[1] === cardsVirados[1].classList[1]) {
			cardsVirados[0] = cardsVirados[1] = null;
			document.querySelector('span.acertos-count').innerHTML = (acertos += 1);
			document.querySelector('span.pontuacao-count').innerHTML = (pontuacao += 10);
			if (acertos == 12) {
				stopTimer();
				openModal();
			}
		} else {
			document.querySelector('span.pontuacao-count').innerHTML = (pontuacao -= 2);
			restauraCardTimeout = setTimeout(restauraCardsAbertos, 1500);
		}

		submitResults();
	} else {
		restauraCardsAbertos();
		openCard(event);
	}
}

function restauraCardsAbertos() {
	if (restauraCardTimeout) {
		clearTimeout(restauraCardTimeout);
		restauraCardTimeout = null;
	}

	cardsVirados.forEach((celula) => celula.classList.remove('virado'));
	cardsVirados[0] = cardsVirados[1] = null;
}

function openModal() {
	var elem = document.getElementById('modal');
	var modal = M.Modal.init(elem, {
		opacity: 0.9,
		preventScrolling: true,
		dismissible: false,
		startingTop: '10%',
		onOpenStart() {
			elem.querySelector("#pontuacao").innerHTML = pontuacao;
			if (elem.querySelector(".register-message")) {
				elem.querySelector(".register-message").querySelector("a").addEventListener('click', (e) => {
					if (uuid) e.target.href += "?uuid=" + uuid;
				});
			}
		},
		onCloseEnd() {
			modal.destroy();
		}
	});

	modal.open();
}

function startTimer() {
	if (intervalo !== null) return;

	fetch(`${CONTEXT_PATH}/partidas`)
		.then((response) => response.json())
		.then((data) => sessionStorage.setItem("uuid", (uuid = data.uuid)));

	inicio = Date.now();
	jogadas = acertos = pontuacao = 0;

	intervalo = setInterval(
		() => {
			const p = document.querySelector('p.timer');
			p.innerHTML = formataTempoDesde(inicio);
		},
		500
	)
}

function submitResults() {
	return fetch(`${CONTEXT_PATH}/partidas`, {
		method: "POST",
		cache: 'no-cache',
		headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
		body: new URLSearchParams({ uuid: uuid, tempo: Date.now() - inicio, acertos, jogadas, pontuacao })
	});
}

function stopTimer() {
	clearInterval(intervalo);
	intervalo = null;
}

function restartTimer() {
	stopTimer();
	document.querySelector('p.timer').innerHTML = '00:00';
	document.querySelector('span.jogadas-count').innerHTML = 0;

	const area = document.querySelector('.jogo-area');
	const celulas = area.querySelectorAll('.celula');

	celulas.forEach((celula) => area.removeChild(celula));

	const novasCelulas = [];
	const photoOrder = _.shuffle(professores);

	for (let i = 0; i < 12; i++) {
		for (let j = 0; j < 2; j++) {
			let celula = document.createElement('div');
			celula.classList.add('celula', `celula-${i}`);
			celula.addEventListener('click', openCard);
			let content = document.createElement('img');
			content.src = `${CONTEXT_PATH}/public/imagens/professores/${photoOrder[i]}`;
			const cropper = new Cropper(content, { viewMode: 3, minContainerWidth: 75, minContainerHeight: 85, autoCrop: false, background: false });
			celula.append(content);
			novasCelulas.push(celula);
		}
	}

	area.append(..._.shuffle(novasCelulas));
}

function formataTempoDesde(inicio) {
	const tempoGasto = Date.now() - inicio;
	const minutos = Math.floor(tempoGasto / (1000 * 60));
	const segundos = Math.floor(Math.floor(tempoGasto % (1000 * 60)) / 1000);
	return `${padStartTempo(minutos)}:${padStartTempo(segundos)}`;
}

function padStartTempo(numero) {
	return ("" + numero).padStart(2, "0");
}

restartTimer();