<jsp:directive.page contentType="text/html; charset=UTF-8" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
	<%@include file="base-head.jsp"%>
	<title>CRUD Manager - Formulário de Filme</title>
</head>
<body>
	<%@include file="nav-menu.jsp"%>

	<div id="container" class="container-fluid">
		<h3 class="page-header">Adicionar Filme</h3>

		<form action="${pageContext.request.contextPath}/movie/${action}" method="POST">
			<input type="hidden" name="movieId" value="${movie.getId()}">

			<div class="row">
				<div class="form-group col-md-6">
					<label for="title">Título do Filme *</label>
					<input type="text" class="form-control" id="title" name="title" 
						   value="${movie.getTitle()}" required
						   oninvalid="this.setCustomValidity('Por favor, informe o título do filme.')"
						   oninput="setCustomValidity('')">
				</div>

				<div class="form-group col-md-6">
					<label for="company_id">Empresa Produtora *</label>
					<select class="form-control" id="company_id" name="company_id" required
						    oninvalid="this.setCustomValidity('Por favor, selecione uma empresa.')"
						    oninput="setCustomValidity('')">
						<option value="" disabled ${not empty movie ? "" : "selected"}>Selecione uma empresa</option>
						<c:forEach var="comp" items="${companies}">
							<option value="${comp.getId()}" ${movie.getCompany().getId() eq comp.getId() ? 'selected' : ''}>
								${comp.getName()}
							</option>
						</c:forEach>
					</select>
				</div>
			</div>

			<div class="row">
				<div class="form-group col-md-4">
					<label for="duration">
						Duração: <span id="duration-val">${movie.getDuration()}</span> minutos *
					</label>
					<input type="range" class="form-control" id="duration" name="duration" 
						   min="30" max="300" step="5" 
						   value="${movie.getDuration()}"
						   oninput="document.getElementById('duration-val').innerText = this.value;">
				</div>

				<div class="form-group col-md-4">
					<label for="color">Cor *</label>
					<input type="color" class="form-control" id="color" name="color" 
						   value="${movie.getColor()}">
				</div>

				<div class="form-group col-md-4">
					<label for="file">Arquivo ${not empty movie ? "" : "*"}</label>
					<input type="hidden" name="existingFile" value="${movie.getFile()}">
					<input type="file" class="form-control file-control" id="file" name="file"
						   ${not empty movie ? "" : "required"}
						   oninvalid="this.setCustomValidity('Por favor, selecione um arquivo.')"
						   oninput="setCustomValidity('')">
					<c:if test="${not empty movie.getFile()}">
						<p class="help-block"><small>Arquivo atual: <strong>${movie.getFile()}</strong></small></p>
					</c:if>
				</div>
			</div>

			<hr />

			<div id="actions" class="row pull-right">
				<div class="col-md-12">
					<a href="${pageContext.request.contextPath}/movies" class="btn btn-default">Cancelar</a>
					<button type="submit" class="btn btn-primary">${not empty movie ? "Alterar Filme" : "Cadastrar Filme"}</button>
				</div>
			</div>
		</form>
	</div>

	<script src="js/jquery.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
</body>
</html>
