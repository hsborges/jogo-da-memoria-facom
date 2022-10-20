<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ page import="java.util.List, br.ufms.facom.jogo.entities.Jogador"%>

<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="body">
<div class="jogo">
	<div class="jogo-stats">
		<p class="jogadas">
			<span class="jogadas-bold"> Número de jogadas: </span> <span
				class="jogadas-count">0</span>
		</p>
		<p class="timer">00:00</p>
		<p>
			<input type="image"
				src="<%=request.getContextPath()%>/public/imagens/reload.png"
				class="botao-restart" onclick="restartTimer()">
		</p>
	</div>
	<div class="jogo-area"></div>
</div>
<div class="ranking">
	<div class="ranking-title">
		<h2>Ranking</h2>
	</div>
	<c:forEach var="user" varStatus="status" items="${ranking}">
		<div class="ranking-user">
			<p class="ranking-position">
				<c:out value="${status.index + 1}" />
			</p>
			<div class="ranking-avatar">
				<c:if test="${user.avatar != null}"><img src="data:image/png;base64, ${user.avatar}" width="32" height="32"></c:if>
				<c:if test="${user.avatar == null}"><img src="https://eu.ui-avatars.com/api/?name=${user.name}&size=32" width="32" height="32"></c:if>
			</div>
			<h3 class="ranking-name">
				<c:out value="${user.name}" />
			</h3>
			<c:choose>
				<c:when test="${status.index == 0}">
					<img class="ranking-trophy"
						src="<%=request.getContextPath()%>/public/imagens/medalha-ouro.png">
				</c:when>
				<c:when test="${status.index == 1}">
					<img class="ranking-trophy"
						src="<%=request.getContextPath()%>/public/imagens/medalha-prata.png">
				</c:when>
				<c:when test="${status.index == 2}">
					<img class="ranking-trophy"
						src="<%=request.getContextPath()%>/public/imagens/medalha-bronze.png">
				</c:when>
			</c:choose>
		</div>
	</c:forEach>
</div>
</c:set>

<c:set var="end">
<script src="<%=request.getContextPath()%>/public/scripts/home.js" ></script>
<script type="text/javascript">restartTimer();</script>
</c:set>

<c:set var="head">
<script src="https://cdnjs.cloudflare.com/ajax/libs/cropperjs/1.5.12/cropper.min.js" integrity="sha512-ooSWpxJsiXe6t4+PPjCgYmVfr1NS5QXJACcR/FPpsdm6kqG1FmQ2SVyg2RXeVuCRBLr0lWHnWJP6Zs1Efvxzww==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/cropperjs/1.5.12/cropper.min.css" integrity="sha512-0SPWAwpC/17yYyZ/4HSllgaK7/gg9OlVozq8K7rf3J8LvCjYEEIfzzpnA2/SSjpGIunCSD18r3UhvDcu/xncWA==" crossorigin="anonymous" referrerpolicy="no-referrer" />
<script type="text/javascript">const CONTEXT_PATH = "<%= request.getContextPath() %>";</script>
</c:set>

<t:layout>
	<jsp:attribute name="head">${head}</jsp:attribute>
   <jsp:attribute name="end">${end}</jsp:attribute>
   <jsp:body>${body}</jsp:body>
</t:layout>