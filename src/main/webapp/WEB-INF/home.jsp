<%@page import="br.ufms.facom.jogo.entities.Jogador"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List, br.ufms.facom.jogo.entities.Jogador" %>    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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

<body class="theme">
  <header class="header">
    <h1>Jogo da Memória</h1>
  </header>
  <section class="conteudo">
    <div class="jogo theme-alt">
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
    <div class="ranking theme-alt">
      <div class="ranking-title">
        <h2>Ranking</h2>
      </div>
      <c:forEach var="user" varStatus="status" items="${ranking}">
      	<div class="ranking-user">
	        <p class="ranking-position"><c:out value="${status.index + 1}" /></p>
	        <div class="ranking-avatar"><img></div>
	        <h3 class="ranking-username"><c:out value="${user.username}" /></h3>        
	        <c:choose>
	        	<c:when test="${status.index == 0}" >
	        		<img class="ranking-trophy" src="<%= request.getContextPath() %>/public/imagens/medalha-ouro.png">
	        	</c:when>
	        	<c:when test="${status.index == 1}" >
	        		<img class="ranking-trophy" src="<%= request.getContextPath() %>/public/imagens/medalha-prata.png">
	        	</c:when>
	        	<c:when test="${status.index == 2}" >
	        		<img class="ranking-trophy" src="<%= request.getContextPath() %>/public/imagens/medalha-bronze.png">
	        	</c:when>
	        </c:choose>	        
	      </div>
      </c:forEach>
    </div>
  </section>
  <footer class="footer"></footer>
  <script>
    restartTimer();
  </script>
</body>

</html>