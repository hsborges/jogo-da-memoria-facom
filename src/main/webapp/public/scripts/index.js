let inicio = null;
let intervalo = null;

let jogadas = 0;

const cardsVirados = [null, null];

function openCard(event) {
  const target = event.target;

  console.log(cardsVirados, target, cardsVirados[0] !== target);
  if (cardsVirados[0] === null) {
    target.classList.add('virado');
    cardsVirados[0] = target;
  } else if (cardsVirados[1] === null && cardsVirados[0] !== target) {
    target.classList.add('virado');
    cardsVirados[1] = target;

    if (cardsVirados[0].classList[1] === cardsVirados[1].classList[1]) {
      cardsVirados[0] = cardsVirados[1] = null;
    } else {
      setTimeout(() => {
        cardsVirados.forEach((celula) => celula.classList.remove('virado'));
        cardsVirados[0] = cardsVirados[1] = null;
      }, 2000);  
    }
    
    
    
    incrementaJogada();
  }
}

function incrementaJogada() {
  if (inicio === null) startTimer();

  jogadas += 1;
  document.querySelector('span.jogadas-count').innerHTML = jogadas;
}

function startTimer() {
  if (inicio !== null) return;

  inicio = Date.now();
  jogadas = 0;

  intervalo = setInterval(
    () => {
      const p = document.querySelector('p.timer');
      p.innerHTML = formataTempoDesde(inicio);
    },
    500
  )
}

function stopTimer() {
  clearInterval(intervalo);
  inicio = null;
}

function restartTimer() {
  stopTimer();
  document.querySelector('p.timer').innerHTML = '00:00';
  document.querySelector('span.jogadas-count').innerHTML = 0;

  const area = document.querySelector('.jogo-area');
  const celulas = area.querySelectorAll('.celula');

  celulas.forEach((celula) => area.removeChild(celula));

  const novasCelulas = [];
  for (let i = 0; i < 12; i++) {
	for (let j = 0; j < 2; j++) {
		let celula = document.createElement('div');
    	celula.classList.add('celula', `celula-${i}`);
    	celula.addEventListener('click', openCard);
    	let content = document.createElement('span');
    	content.innerHTML = i;
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