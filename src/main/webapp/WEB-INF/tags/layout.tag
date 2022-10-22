<%@ tag description="Page template" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ attribute name="end" fragment="true"%>
<%@ attribute name="head" fragment="true"%>

<html>
<head>
<meta name="viewport" content="width=device-width">
<title>Jogo da Memoria da FACOM - ProgWeb 2022.2</title>

<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
<link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@300;400;500;600;700;800&display=swap" rel="stylesheet">

<script src="https://cdn.jsdelivr.net/npm/lodash@4.17.21/lodash.min.js"></script>

<link rel="stylesheet" href="<%= request.getContextPath() %>/public/libs/materialize/css/materialize.css"> 

<jsp:invoke fragment="head" />

<style type="text/css">
:root {
	--color: blueviolet;
	--background-color: #f3f3fa;
	--color-alt: #f3f3fa;
	--background-color-alt: blueviolet;
}

html, body {
	height: 100vh;
	width: 100vw;
	margin: 0;
	font-family: 'Montserrat', sans-serif;
}

.theme {
	color: var(--color);
	background-color: var(--background-color);
}

.theme-alt {
	color: var(--color-alt);
	background-color: var(--background-color-alt);
}

body {
	display: flex;
	flex-direction: column;
}

body>header {
	display: flex;
	justify-content: center;
	height: 75px;
	min-height: 75px;
}

body>header a {
	text-decoration: none;
	color: inherit;
}

body>header>section.title {
	display: flex;
	align-items: center;
	font-size: 25px;
	font-weight: 700;
	font-size: 25px;
}

body>header>section.menu > a {
	font-size: smaller;
	position: absolute;
	right: 40px;
	height: 75px;
	display: flex;
	flex-direction: column;
	justify-content: center;
	align-items: center;
}

body>section.conteudo {
	flex-grow: 1;
	display: flex;
	margin: 15px;
	margin-top: 0;
}

.underline { text-decoration: underline; }
</style>
</head>
<body class="theme">
	<header>
		<section class="title">
			<a href="<%=request.getContextPath()%>">Jogo da Memória</a>
		</section>
		<section class="menu">
			<c:choose>
				<c:when test="${sessionScope.jogador == null}">
					<a href="<%=request.getContextPath()%>/usuario">Login/Cadastro</a>
				</c:when> 
				<c:otherwise>
					<a href="<%=request.getContextPath()%>/usuario?action=info"> 
						<c:if test="${sessionScope.jogador.avatar != null}">
							<img src="data:image/png;base64, ${sessionScope.jogador.avatar}" width="32" height="32">
						</c:if> 
						<c:if test="${sessionScope.jogador.avatar == null}">
							<img src="https://eu.ui-avatars.com/api/?name=${sessionScope.jogador.name}&size=32" width="32" height="32">
						</c:if> 
						<span>${sessionScope.jogador.name}</span>
					</a>
				</c:otherwise>
			</c:choose>
		</section>
	</header>

	<section class="conteudo theme-alt">
		<jsp:doBody />
	</section>

	<!-- Compiled and minified JavaScript -->
	<script src="<%= request.getContextPath() %>/public/libs/materialize/js/bin/materialize.min.js"></script>

	<jsp:invoke fragment="end" />
</body>
</html>