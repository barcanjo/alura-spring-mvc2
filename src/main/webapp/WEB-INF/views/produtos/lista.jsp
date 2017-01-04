<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Produto Ok</title>
<c:url value="/resources/css" var="cssPath" />
<link rel="stylesheet" href="${cssPath }/bootstrap.min.css">
<link rel="stylesheet" href="${cssPath }/bootstrap-theme.min.css">
<style type="text/css">
	body {
		padding-bottom: 60px;
	}
</style>
</head>
<body>
	<nav class="navbar navbar-inverse">
	  <div class="container">
	    <div class="navbar-header">
	      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
	        <span class="sr-only">Toggle navigation</span>
	        <span class="icon-bar"></span>
	        <span class="icon-bar"></span>
	        <span class="icon-bar"></span>
	      </button>
	      <a class="navbar-brand" href="${tags:mvcUrl('HC#index').build() }">Casa do Código</a>
	    </div>
	    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
	      <ul class="nav navbar-nav">
	        <li><a href="${tags:mvcUrl('PC#listar').build() }">Lista de Produtos</a></li>
	        <li><a href="${tags:mvcUrl('PC#form').build() }">Cadastro de Produtos</a></li>
	      </ul>
	      <ul class="nav nav-navbar navbar-right">
	      	<li><a href="#">
	      		<!-- coloca na variável usuario os dados do usuário autenticado no Spring Framework -->
	      		<security:authentication property="principal" var="usuario" />	      		
	      		<!-- Exibe o nome do usuário autenticado no Spring Framework -->
	      		Usuário: ${usuario.username }
	      	</a></li>
	      </ul>
	    </div><!-- /.navbar-collapse -->
	  </div>
	</nav>
	<div class="container">
		<h1>Lita de Produtos</h1>
	
		<div>${sucesso }</div>
		<div>${falha }</div>
		
		<table class="table table-bordered table-striped table-hover">
			<thead>
				<tr>
					<th>Título</th>
					<th>Descrição</th>
					<th>Preços</th>
					<th>Páginas</th>
					<th>Detalhes</th>
				</tr>
			</thead>
			
			<tbody>
				<c:forEach items="${produtos }" var="produto">
					<tr>
						<td>${produto.titulo }</td>
						<td>${produto.descricao }</td>
						<td>${produto.precos }</td>
						<td>${produto.paginas }</td>
						<td>
							<a href="${tags:mvcUrl('PC#detalhe').arg(0,produto.id).build()}">${produto.titulo}</a>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</body>
</html>