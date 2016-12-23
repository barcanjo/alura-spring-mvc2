<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Produto Ok</title>
</head>
<body>
	<h1>Lita de Produtos</h1>

	<div>${sucesso }</div>
	<div>${falha }</div>
	
	<table>
		<thead>
			<tr>
				<th>Título</th>
				<th>Descrição</th>
				<th>Páginas</th>
				<th>Detalhes</th>
			</tr>
		</thead>
		
		<tbody>
			<c:forEach items="${produtos }" var="produto">
				<tr>
					<td>${produto.titulo }</td>
					<td>${produto.descricao }</td>
					<td>${produto.paginas }</td>
					<td>
						<a href="${tags:mvcUrl('PC#detalhe').arg(0,produto.id).build()}">${produto.titulo}</a>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>