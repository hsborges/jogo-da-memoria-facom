<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="loginForm">
	<div class="container">
		<form method="post" class="formulario">
			<div>
				<label for="email">Email</label> <input id="email" name="email" />
			</div>
			<div>
				<label for="password">Senha</label> <input id="password"
					name="password" type="password" />
			</div>
			<div>
				<a
					href="${requestScope['javax.servlet.forward.request_uri']}?action=cadastro"
					class="theme-alt">cadastrar</a>
				<button type="submit">Login</button>
			</div>
		</form>
	</div>
</c:set>

<c:set var="infoForm">
	<div class="container">
		<form method="post" class="formulario" enctype="multipart/form-data">
			<div>
				<label for="name">Nome</label> <input id="name" name="name"
					type="text" required="required"
					value="${sessionScope.jogador.name}" />
			</div>
			<div>
				<label for="email">Email</label> <input id="email" name="email"
					type="email" required="required"
					value="${sessionScope.jogador.email}" disabled="disabled" />
			</div>
			<div>
				<label for="password">Senha</label> <input id="password"
					name="password" type="password" required="required" />
			</div>
			<div>
				<label for="password-confirm">Confirmar senha</label> <input
					id="password-confirm" type="password" required="required" />
			</div>
			<div>
				<label for="avatar">Avatar</label> <input id="avatar" name="avatar"
					type="file" accept=".png,.jpg,.jpeg" />
			</div>
			<div>
				<a href="${requestScope['javax.servlet.forward.request_uri']}?&action=sair"
					class="theme-alt">Sair</a>
				<button type="submit" onclick="return validaSenhas(this);">Atualizar</button>
			</div>
		</form>
	</div>
</c:set>

<c:set var="cadastroForm">
	<div class="container">
		<form method="post" class="formulario" enctype="multipart/form-data">
			<div>
				<label for="name">Nome</label> <input id="name" name="name"
					type="text" required="required" />
			</div>
			<div>
				<label for="email">Email</label> <input id="email" name="email"
					type="email" required="required" />
			</div>
			<div>
				<label for="password">Senha</label> <input id="password"
					name="password" type="password" required="required" />
			</div>
			<div>
				<label for="password-confirm">Confirmar senha</label> <input
					id="password-confirm" type="password" required="required" />
			</div>
			<div>
				<label for="avatar">Avatar</label> <input id="avatar" name="avatar"
					type="file" accept=".png,.jpg,.jpeg" />
			</div>
			<div>
				<a href="${requestScope['javax.servlet.forward.request_uri']}"
					class="theme-alt">Faça login</a>
				<button type="submit" onclick="return validaSenhas(this);">Cadastrar</button>
			</div>
		</form>
	</div>
</c:set>

<t:layout>
	<jsp:attribute name="head">
		<style>
.container {
	width: 100%;
	height: 80%;
	display: flex;
	justify-content: center;
}

.container .formulario {
	width: 250px;
	justify-self: center;
	align-self: center;
}

.container .formulario input {
	width: 100%;
}

.container .formulario button {
	float: right;
}

.container .formulario a, .container .formulario label {
	font-size: smaller;
}

.container .formulario>*:last-child {
	margin-top: 35px;
}
</style>
		<script type="text/javascript">
			var message = `${message}`;
			if (message)
				alert(message);

			function validaSenhas() {
				var pass = document.getElementById("password").value;
				var passConfirm = document.getElementById("password-confirm").value;
				if (pass !== passConfirm) {
					alert('Senhas não conferem!');
					return false;
				}
			};
		</script>
	</jsp:attribute>
	<jsp:body>
	<c:choose>
		<c:when test="${param.action == 'cadastro'}">
		${cadastroForm}	
		</c:when>
		<c:when test="${param.action == 'info'}">
		${infoForm}	
		</c:when>
		<c:otherwise>
		${loginForm}
		</c:otherwise>
	</c:choose>
	</jsp:body>
</t:layout>
