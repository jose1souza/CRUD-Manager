<jsp:directive.page contentType="text/html; charset=UTF-8" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
	<%@include file="base-head.jsp"%>
	<title>CRUD Manager - Formulário de Ingresso</title>
</head>
<body>
	<%@include file="nav-menu.jsp"%>

	<div id="container" class="container-fluid">
		<h3 class="page-header">Adicionar Ingresso</h3>

		<form action="${pageContext.request.contextPath}/ticket/${action}" method="POST">
			<input type="hidden" name="ticketId" value="${ticket.getId()}">

			<div class="row">
				<div class="form-group col-md-6">
					<label for="movie_id">Filme *</label>
					<select class="form-control" id="movie_id" name="movie_id" required
						    oninvalid="this.setCustomValidity('Por favor, selecione um filme.')"
						    oninput="setCustomValidity('')">
						<option value="" disabled ${not empty ticket ? "" : "selected"}>Selecione um filme</option>
						<c:forEach var="mov" items="${movies}">
							<option value="${mov.getId()}" ${ticket.getMovie().getId() eq mov.getId() ? 'selected' : ''}>
								${mov.getTitle()} (${mov.getDuration()} min)
							</option>
						</c:forEach>
					</select>
				</div>

				<div class="form-group col-md-6">
					<label for="price">Preço *</label>
					<input type="number" class="form-control" id="price" name="price" 
						   step="0.50" min="0" value="${ticket.getPrice() eq 0 ? '30.00' : ticket.getPrice()}" required>
				</div>
			</div>

			<div class="row">
				<div class="form-group col-md-4">
					<label for="start">Data *</label>
					<fmt:formatDate value="${ticket.getStart()}" pattern="yyyy-MM-dd" var="formattedDate" />
					<input type="date" class="form-control" id="start" name="start" 
						   value="${formattedDate}" required>
				</div>

				<div class="form-group col-md-4">
					<label for="startTime">Horário *</label>
					<fmt:formatDate value="${ticket.getStartTime()}" pattern="HH:mm" var="formattedTime" />
					<input type="time" class="form-control" id="startTime" name="startTime" 
						   value="${formattedTime}" required>
				</div>

				<div class="form-group col-md-4">
					<label>Tipo de Entrada</label>
					<div class="checkbox" style="margin-top: 5px;">
						<label>
							<input type="checkbox" name="halfPriceTicket" value="true" 
								   ${ticket.isHalfPriceTicket() ? 'checked' : ''}>
							<strong>Meia Entrada</strong>
						</label>
					</div>
				</div>
			</div>

			<hr />

			<div id="actions" class="row pull-right">
				<div class="col-md-12">
					<a href="${pageContext.request.contextPath}/tickets" class="btn btn-default">Cancelar</a>
					<button type="submit" class="btn btn-primary">${not empty ticket ? "Alterar Ingresso" : "Cadastrar Ingresso"}</button>
				</div>
			</div>
		</form>
	</div>

	<script src="js/jquery.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
</body>
</html>
