<%@ tag description="Page template" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ attribute name="end" fragment="true"%>
<%@ attribute name="head" fragment="true"%>

<html>
<head>
<meta name="viewport" content="width=device-width">
<title>Jogo da Memoria da FACOM - ProgWeb 2022.2</title>
<link href="<%=request.getContextPath()%>/public/estilos/style.css"
	rel="stylesheet" type="text/css" />
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link
	href="https://fonts.googleapis.com/css2?family=Montserrat:wght@300;400;500;600;700;800&display=swap"
	rel="stylesheet">
<style type="text/css">
.header a {
	text-decoration: none;
	color: inherit;
}
</style>

<script src="https://cdn.jsdelivr.net/npm/lodash@4.17.21/lodash.min.js"></script>

<jsp:invoke fragment="head" />
<style type="text/css">
body>header>a {
	font-size: smaller;
	position: absolute;
	right: 40px;
	top: 15px;
	display: flex;
	flex-direction: column;
	justify-content: center;
	align-items: center;
}
</style>
</head>
<body class="theme">
	<header class="header">
		<h1>
			<a href="<%=request.getContextPath()%>">Jogo da Memória</a>
		</h1>
		<c:choose>
			<c:when test="${sessionScope.jogador == null}">
				<a href="<%=request.getContextPath()%>/usuario"><br/>Login/Cadastro</a>
			</c:when>
			<c:otherwise>
				<a href="<%=request.getContextPath()%>/usuario?action=info">
					<c:if test="${sessionScope.jogador.avatar != null}"><img src="data:image/png;base64, ${sessionScope.jogador.avatar}" width="32" height="32"></c:if>
					<c:if test="${sessionScope.jogador.avatar == null}"><img src="https://eu.ui-avatars.com/api/?name=${sessionScope.jogador.name}&size=32" width="32" height="32"></c:if>
					<span>${sessionScope.jogador.name}</span>
				</a>
			</c:otherwise>
		</c:choose>
	</header>
	<section class="conteudo theme-alt">
		<jsp:doBody />
	</section>
	<jsp:invoke fragment="end" />
</body>
</html>