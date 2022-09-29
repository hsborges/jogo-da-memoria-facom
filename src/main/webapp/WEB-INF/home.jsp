<%@page import="br.ufms.facom.jogo.entities.Jogador"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List, br.ufms.facom.jogo.entities.Jogador" %>    
<!DOCTYPE html>
<html>

<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width">
  <title>Jogo da Memoria da FACOM - ProgWeb 2022.2</title>
  <link href="<%= request.getContextPath() %>/public/estilos/style.css" rel="stylesheet" type="text/css" />
  <link rel="preconnect" href="https://fonts.googleapis.com">
  <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
  <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@300;400;500;600;700;800&display=swap"
    rel="stylesheet">
  <script src="<%= request.getContextPath() %>/public/scripts/index.js"></script>
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
        <input type="image" src="<%= request.getContextPath() %>/public/imagens/reload.png" class="botao-restart" onclick="restartTimer()">
      </div>
      <div class="jogo-area"></div>
    </div>
    <div class="ranking">
      <div class="ranking-title">
        <h2>Ranking</h2>
      </div>
      <% 
      List<Jogador> ranking = (List<Jogador>)request.getAttribute("ranking");
      for (int i = 0; i < ranking.size(); i++) { %>
      <div class="ranking-user">
        <p class="ranking-position"><%= i + 1 %></p>
        <div class="ranking-avatar"><img></div>
        <h3 class="ranking-username"><%= ranking.get(i).getUsername() %></h3>        
        <% if (i == 0) out.println("<img class=\"ranking-trophy\" src=\"" + request.getContextPath() + "/public/imagens/medalha-ouro.png\">"); %>
        <% if (i == 1) out.println("<img class=\"ranking-trophy\" src=\"" + request.getContextPath() + "/public/imagens/medalha-prata.png\">"); %>
        <% if (i == 2) out.println("<img class=\"ranking-trophy\" src=\"" + request.getContextPath() + "/public/imagens/medalha-bronze.png\">"); %>
        
      </div>
      <% } %>
    </div>
  </section>
  <footer class="footer"></footer>
  <script>
    restartTimer();
  </script>
</body>

</html>