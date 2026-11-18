<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="base-head.jsp"%>
<title>CRUD Manager</title>
</head>
<body>
    <!-- Nav -->
	<%@include file="nav-menu.jsp"%>

    <!-- Conteúdo principal -->
		<main
			class="flex-grow-1 d-flex flex-column justify-content-center align-items-center text-center">
			<h1 class="my-4">Bem-vindo ao CRUD Manager</h1>
			<div class="d-flex gap-3 flex-wrap justify-content-center" style="margin-bottom: 40px;">
				<a href="${pageContext.request.contextPath}/users" class="btn btn-danger">Usuários</a>
				<a href="${pageContext.request.contextPath}/posts" class="btn btn-danger">Posts</a>
				<a href="${pageContext.request.contextPath}/companies" class="btn btn-danger">Empresas</a>
                <a href="${pageContext.request.contextPath}/movies" class="btn btn-danger">Filmes</a>
                <a href="${pageContext.request.contextPath}/tickets" class="btn btn-danger">Ingressos</a>
			</div>
		</main>

    <!-- Rodapé -->
	<%@include file="footer.jsp"%>
	 
	<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
</body>
</html>