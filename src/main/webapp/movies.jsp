<jsp:directive.page contentType="text/html; charset=UTF-8" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
	<%@include file="base-head.jsp"%>
	<title>CRUD Manager - Filmes</title>
</head>
<body>
	<%@include file="modal.html"%>
	<%@include file="nav-menu.jsp"%>

	<div id="container" class="container-fluid">
		<div id="alert" style="${not empty message ? 'display: block;' : 'display: none;'}" class="alert alert-dismissable ${alertType eq 1 ? 'alert-success' : 'alert-danger'}">
		  <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
		  ${message}
		</div>

		<div id="top" class="row">
 			<div class="col-md-3">
		        <h3>Filmes</h3>
		    </div>

		    <div class="col-md-6">
		        <div class="input-group h2">
		            <input name="data[search]" class="form-control" id="search" type="text" placeholder="Pesquisar filmes">
		            <span class="input-group-btn">
		                <button class="btn btn-danger" type="submit">
		                    <span class="glyphicon glyphicon-search"></span>
		                </button>
		            </span>
		        </div>
		    </div>

		    <div class="col-md-3">
		        <a href="${pageContext.request.contextPath}/movie/form" class="btn btn-danger pull-right h2">
		        	<span class="glyphicon glyphicon-plus"></span>&nbsp;Adicionar Filme
		        </a>
		    </div>
     	</div>

     	<hr />

     	<div id="list" class="row">
     		<div class="table-responsive col-md-12">
		        <table class="table table-striped table-hover" cellspacing="0" cellpadding="0">
		            <thead>
		                <tr>
		                    <th>Título</th>
		                    <th>Empresa</th>
		                    <th>Duração (min)</th>
		                    <th>Cor Tema</th>
		                    <th>Arquivo</th>
		                    <th>Editar</th>
		                    <th>Excluir</th>
		                 </tr>
		            </thead>
		            <tbody>
		            	<c:forEach var="movie" items="${movies}">
							<tr>
			                    <td>${movie.getTitle()}</td>
			                    <td>${movie.getCompany().getName()}</td>
			                    <td>${movie.getDuration()} min</td>
			                    <td>
			                    	<span style="display:inline-block; width:20px; height:20px; background-color:${movie.getColor()}; border:1px solid #ccc; vertical-align:middle; border-radius:3px;"></span>
			                    	${movie.getColor()}
			                    </td>
			                    <td>${movie.getFile()}</td>

			                    <td class="actions">
			                        <a class="btn btn-info btn-xs" 
			                           href="${pageContext.request.contextPath}/movie/update?movieId=${movie.getId()}">
			                           <span class="glyphicon glyphicon-edit"></span>
			                        </a>
			                    </td>

			                    <td class="actions">
			                        <a class="btn btn-danger btn-xs modal-remove"
			                           movie-id="${movie.getId()}" 
			                           movie-title="${movie.getTitle()}" data-toggle="modal" 
			                           data-target="#delete-modal" href="#"><span 
			                           class="glyphicon glyphicon-trash"></span></a>
			                    </td>
			                </tr>
						</c:forEach>
		            </tbody>
		         </table>
		     </div>
     	</div>

     	<%@include file="pagination.jsp"%>
	</div>

	<script src="js/jquery.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
		    setTimeout(function() {
		        $("#alert").slideUp(500);
		    }, 3000);

		    $(".modal-remove").click(function () {
	            var movieTitle = $(this).attr('movie-title');
	            var movieId = $(this).attr('movie-id');
	            $(".modal-body #hiddenValue").text("o filme '"+movieTitle+"'");
	            $("#id").attr("value", movieId);
	            $("#form").attr("action", "movie/delete");
	        });
		});
	</script>
</body>
</html>
