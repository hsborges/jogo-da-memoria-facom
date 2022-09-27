<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width">
  <title>Jogo da Memoria da FACOM - ProgWeb 2022.2</title>
  <link href="/public/estilos/style.css" rel="stylesheet" type="text/css" />
  <link rel="preconnect" href="https://fonts.googleapis.com">
  <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
  <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@300;400;500;600;700;800&display=swap"
    rel="stylesheet">
  <script src="/public/scripts/index.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/lodash@4.17.21/lodash.min.js"></script>

</head>

<body>
  <header class="header">
    <h1>Jogo da Memória</h1>
  </header>
  <section class="conteudo">
    <div class="jogo">
      <div class="jogo-stats">
        <p class="jogadas">
          <span class="jogadas-bold">
            Número de jogadas
          </span>:
          <span class="jogadas-count">0</span>
        </p>
        <p class="timer">00:00</p>
        <input type="image" src="/public/imagens/reload.png" class="botao-restart" onclick="restartTimer()">
      </div>
      <div class="jogo-area"></div>
    </div>
    <div class="ranking">
      <div class="ranking-title">
        <h2>Ranking</h2>
      </div>
      <div class="ranking-user">
        <p class="ranking-position">1</p>
        <div class="ranking-avatar"><img></div>
        <h3 class="ranking-username">Isabela Resende</h3>
        <img class="ranking-trophy" src="/public/imagens/medalha-ouro.png">
      </div>
      <div class="ranking-user">
        <p class="ranking-position">2</p>
        <div class="ranking-avatar"><img></div>
        <h3 class="ranking-username">Victor Marques</h3>
        <img class="ranking-trophy" src="/public/imagens/medalha-prata.png">
      </div>
      <div class="ranking-user">
        <p class="ranking-position">3</p>
        <div class="ranking-avatar"><img></div>
        <h3 class="ranking-username">Raú Lion</h3>
        <img class="ranking-trophy" src="/public/imagens/medalha-bronze.png">
      </div>
      <div class="ranking-user">
        <p class="ranking-position">4</p>
        <div class="ranking-avatar"><img></div>
        <h3 class="ranking-username">Giovanna Coelho</h3>
      </div>
    </div>
  </section>
  <footer class="footer"></footer>
  <script>
    restartTimer();
  </script>
</body>

</html>