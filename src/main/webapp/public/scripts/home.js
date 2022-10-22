const params = new Proxy(new URLSearchParams(window.location.search), {
	get: (searchParams, prop) => searchParams.get(prop),
});

let inicio = null;
let intervalo = null;
let uuid = params.uuid;

let jogadas = 0;
let acertos = 0;
let pontuacao = 0;
let ordemAcertos = [];

let restauraCardTimeout = null;

const cardsVirados = [null, null];


let professores = new Chance(params.uuid).shuffle(['amauri.jpeg', 'awdren.jpg', 'edna.jpeg', 'hudson.jpeg', 'jane.jpg', 'jucele.jpg', 'liana.jpeg', 'mongelli.jpg', 'renan.jpg', 'said.jpeg', 'samuel.jpeg', 'takashi.jpg']);

String.prototype.hashCode = function() {
	var hash = 0,
		i, chr;
	if (this.length === 0) return hash;
	for (i = 0; i < this.length; i++) {
		chr = this.charCodeAt(i);
		hash = ((hash << 5) - hash) + chr;
		hash |= 0; // Convert to 32bit integer
	}
	return hash;
}

function submitResults() {
	console.log(new URLSearchParams({ uuid: uuid, tempo: Date.now() - inicio, acertos, ordemAcertos, jogadas, pontuacao: Math.ceil(pontuacao) }));
	return fetch(`${CONTEXT_PATH}/partidas`, {
		method: "POST",
		cache: 'no-cache',
		headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
		body: new URLSearchParams({ uuid: uuid, tempo: Date.now() - inicio, acertos, ordemAcertos, jogadas, pontuacao: Math.ceil(pontuacao) })
	});
}

function restauraCardsAbertos() {
	if (restauraCardTimeout) {
		clearTimeout(restauraCardTimeout);
		restauraCardTimeout = null;
	}

	cardsVirados.forEach((celula) => celula.classList.remove('virado'));
	cardsVirados[0] = cardsVirados[1] = null;
}

function openCard(event) {
	const target = event.target;
	const targetIndex = Array.prototype.slice.call(document.querySelector('.jogo-area').children).indexOf(target);

	if (intervalo === null) {
		startTimer().then(() => document.querySelector('.jogo-area').children[targetIndex].click());
	} else {
		if (!target.classList.contains('celula') || target.classList.contains('virado')) {
			return;
		} else if (cardsVirados[0] === null) {
			target.classList.add('virado');
			cardsVirados[0] = target;
		} else if (cardsVirados[1] === null) {
			if (cardsVirados[0] === target) return;

			jogadas += 1;

			target.classList.add('virado');
			cardsVirados[1] = target;


			if (cardsVirados[0].classList[1] === cardsVirados[1].classList[1]) {
				const match = cardsVirados[0].classList[1].match(/^celula-(\d+)$/i)
				ordemAcertos = [...ordemAcertos, match[1]]
				acertos += 1;
				pontuacao += 100;
				cardsVirados[0] = cardsVirados[1] = null;
				if (acertos == 12) {
					stopTimer();
					openModal();
				}
			} else {
				pontuacao -= 5;
				restauraCardTimeout = setTimeout(restauraCardsAbertos, 1500);
			}

			submitResults();
		} else {
			restauraCardsAbertos();
			openCard(event);
		}
	}
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

function atualizaStats() {
	document.querySelector('p.timer').innerHTML = inicio ? formataTempoDesde(inicio) : '00:00';
	document.querySelector('span.acertos-count').innerHTML = acertos;
	document.querySelector('span.pontuacao-count').innerHTML = Math.ceil(pontuacao);
	document.querySelector('span.jogadas-count').innerHTML = jogadas;
}

function startTimer() {
	if (intervalo !== null) return;

	let promiseUUID = null;
	if (params.uuid) {
		promiseUUID = fetch(`${CONTEXT_PATH}/partidas?uuid=${params.uuid}`, { cache: 'no-cache' })
			.then((response) => response.json());
	} else {
		promiseUUID = fetch(`${CONTEXT_PATH}/partidas`).then((response) => response.json());
	}

	return promiseUUID.then((data) => {
		sessionStorage.setItem("uuid", (uuid = data.uuid));

		uuid = data.uuid;
		acertos = data.acertos || 0;
		jogadas = data.jogadas || 0;
		pontuacao = data.pontuacao || 0;
		ordemAcertos = data.ordem_acertos ? data.ordem_acertos.split(',').map(v => parseInt(v)) : []; 
		inicio = data.tempo ? Date.now() - data.tempo : Date.now();

		professores = new Chance(uuid).shuffle(professores);

		populateCards();
		atualizaStats();

		for (let i of ordemAcertos)
			document.querySelector('.jogo-area').querySelectorAll(`.celula-${i}`).forEach(elem => elem.classList.add("virado"));

		if (acertos == 12) {
			submitResults();
			openModal();
		} else {
			intervalo = setInterval(() => {
				pontuacao -= 0.5;
				atualizaStats();
			}, 500)
		}
	});
}

function stopTimer() {
	clearInterval(intervalo);
	intervalo = null;
}

function restartTimer() {
	stopTimer();
	inicio = null;
	acertos = jogadas = pontuacao = 0;
	populateCards();
	atualizaStats();
}

function populateCards() {
	const area = document.querySelector('.jogo-area');
	const celulas = area.querySelectorAll('.celula');

	celulas.forEach((celula) => area.removeChild(celula));

	const novasCelulas = [];

	for (let i = 0; i < 12; i++) {
		for (let j = 0; j < 2; j++) {
			let celula = document.createElement('div');
			celula.classList.add('celula', `celula-${i}`);
			celula.addEventListener('click', openCard);
			let content = document.createElement('img');
			content.src = `${CONTEXT_PATH}/public/imagens/professores/${professores[i]}`;
			new Cropper(content, { viewMode: 3, minContainerWidth: 75, minContainerHeight: 85, autoCrop: false, background: false });
			celula.append(content);
			novasCelulas.push(celula);
		}
	}

	area.append(...(new Chance(uuid).shuffle(novasCelulas)));
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