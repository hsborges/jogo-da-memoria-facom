<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="head">
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
	<style>
	.valign-wrapper { height: 95%; } 
	div.container form > *:last-child { padding-top: 25px; display: flex; justify-content: flex-end; align-items: center; column-gap: 10px; }
	.file-field > div { display: flex; } 
	.file-field > div > .material-icons { padding-right: 5px; }
	</style> 
</c:set> 

<c:set var="loginForm">
	<form method="post" class="col s8 offset-s2">
		<div class="row input-field">
			<input id="email" name="email" type="email" class="white-text"/>
			<label for="email" class="white-text">Email</label>
		</div>
		<div class="row input-field">
			<input id="password" name="password" type="password" class="white-text"/>
			<label for="password" class="white-text">Senha</label>
		</div>
		<div class="row">
			<a href="${requestScope['javax.servlet.forward.request_uri']}?action=cadastro&${requestScope['javax.servlet.forward.query_string']}" class="white-text underline">Novo usuário? Cadastre-se!</a>
			<button type="submit" class="btn primary-text right">Login</button>
		</div>
	</form>
</c:set>

<c:set var="infoForm">
	<form method="post" class="col s8 offset-s2" enctype="multipart/form-data">
		<div class="row input-field">
			<label for="name" class="white-text">Nome</label> 
			<input id="name" name="name" type="text" required="required" value="${sessionScope.jogador.name}" class="white-text"/>
		</div>
		<div class="row input-field">
			<label for="email" class="white-text">Email</label>
			<input id="email" name="email" type="email" required="required" value="${sessionScope.jogador.email}" disabled="disabled" class="white-text"/>
		</div>
		<div class="row input-field">
			<label for="password" class="white-text">Senha</label>
			<input id="password" name="password" type="password" required="required" class="white-text"/>
		</div>
		<div class="row input-field">
			<label for="password-confirm" class="white-text">Confirmar senha</label>
			<input id="password-confirm" type="password" required="required" class="white-text"/>
		</div>
		<div class="row file-field input-field">
			<div class="col s2 btn btn-small white primary-text">
				<i class="material-icons">image</i>Avatar
				<input name="avatar" type="file" accept=".png,.jpg,.jpeg"/>		
			</div>
			<div class="col s10 file-path-wrapper">
		        <input class="file-path validate" type="text">
		    </div>
		</div>
		<div class="row">
			<a href="${requestScope['javax.servlet.forward.request_uri']}?&action=sair" class="white-text underline valign-wrapper">Sair</a>
			<button class="btn btn right primary-text" type="submit" onclick="return validaSenhas(this);">
				Atualizar<i class="material-icons primary-text right">save</i>
			</button>
		</div>
	</form>
</c:set>

<c:set var="cadastroForm"> 
	<form method="post" class="col s8 offset-s2" enctype="multipart/form-data">
		<div class="row input-field">
			<label for="name" class="white-text">Nome</label> 
			<input id="name" name="name" type="text" required="required"  class="white-text"/>
		</div>
		<div class="row input-field">
			<label for="email" class="white-text">Email</label>
			<input id="email" name="email" type="email" required="required" class="white-text" />
		</div>
		<div class="row input-field">
			<label for="password" class="white-text">Senha</label>
			<input id="password" name="password" type="password" required="required" class="white-text"/>
		</div>
		<div class="row input-field">
			<label for="password-confirm" class="white-text">Confirmar senha</label>
			<input id="password-confirm" type="password" required="required" class="white-text"/>
		</div>
		<div class="row file-field input-field">
			<div class="col s2 btn btn-small white primary-text">
				<i class="material-icons">image</i>Avatar
				<input name="avatar" type="file" accept=".png,.jpg,.jpeg"/>		
			</div>
			<div class="col s10 file-path-wrapper">
		        <input class="file-path validate" type="text">
		    </div>
		</div>
		<div class="row">
			<a href="${requestScope['javax.servlet.forward.request_uri']}" class="white-text underline left">
				Possui conta? Faça login!
			</a>
			<button class="btn primary-text" type="submit" onclick="return validaSenhas(this);">
				Cadastrar<i class="material-icons primary-text right">save</i>
			</button>
		</div>
	</form>
</c:set>

<t:layout>
	<jsp:attribute name="head">${head}</jsp:attribute>
	<jsp:body>
	<div class="valign-wrapper">
		<div class="container"> 
			<div class="row">
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
			</div>
		</div>
	</div>
	</jsp:body>
</t:layout>
