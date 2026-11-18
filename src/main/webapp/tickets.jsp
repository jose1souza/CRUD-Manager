<jsp:directive.page contentType="text/html; charset=UTF-8" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
	<%@include file="base-head.jsp"%>
	<title>CRUD Manager - Ingressos</title>
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
		        <h3>Ingressos</h3>
		    </div>

		    <div class="col-md-6">
		        <div class="input-group h2">
		            <input name="data[search]" class="form-control" id="search" type="text" placeholder="Pesquisar ingressos">
		            <span class="input-group-btn">
		                <button class="btn btn-danger" type="submit">
		                    <span class="glyphicon glyphicon-search"></span>
		                </button>
		            </span>
		        </div>
		    </div>

		    <div class="col-md-3">
		        <a href="${pageContext.request.contextPath}/ticket/form" class="btn btn-danger pull-right h2">
		        	<span class="glyphicon glyphicon-plus"></span>&nbsp;Adicionar Ingresso
		        </a>
		    </div>
     	</div>

     	<hr />

     	<div id="list" class="row">
     		<div class="table-responsive col-md-12">
		        <table class="table table-striped table-hover" cellspacing="0" cellpadding="0">
		            <thead>
		                <tr>
		                    <th>Filme</th>
		                    <th>Data Sessão</th>
		                    <th>Horário</th>
		                    <th>Preço (R$)</th>
		                    <th>Meia Entrada?</th>
		                    <th>Editar</th>
		                    <th>Excluir</th>
		                 </tr>
		            </thead>
		            <tbody>
		            	<c:forEach var="ticket" items="${tickets}">
							<tr>
			                    <td>
			                    	<span style="display:inline-block; width:12px; height:12px; background-color:${ticket.getMovie().getColor()}; border-radius:50%; margin-right:5px;"></span>
			                    	<strong>${ticket.getMovie().getTitle()}</strong>
			                    </td>
			                    <td><fmt:formatDate value="${ticket.getStart()}" pattern="dd/MM/yyyy" /></td>
			                    <td><fmt:formatDate value="${ticket.getStartTime()}" pattern="HH:mm" /></td>
			                    <td><fmt:formatNumber value="${ticket.getPrice()}" type="currency" currencySymbol="R$ " /></td>
			                    <td>
			                    	<c:choose>
			                    		<c:when test="${ticket.isHalfPriceTicket()}">
			                    			<span class="label label-success">Sim (Meia)</span>
			                    		</c:when>
			                    		<c:otherwise>
			                    			<span class="label label-default">Não (Inteira)</span>
			                    		</c:otherwise>
			                    	</c:choose>
			                    </td>

			                    <td class="actions">
			                        <a class="btn btn-info btn-xs" 
			                           href="${pageContext.request.contextPath}/ticket/update?ticketId=${ticket.getId()}">
			                           <span class="glyphicon glyphicon-edit"></span>
			                        </a>
			                    </td>

			                    <td class="actions">
			                        <a class="btn btn-danger btn-xs modal-remove"
			                           ticket-id="${ticket.getId()}" 
			                           ticket-info="Ingresso para ${ticket.getMovie().getTitle()}" 
			                           data-toggle="modal" 
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
	            var ticketInfo = $(this).attr('ticket-info');
	            var ticketId = $(this).attr('ticket-id');
	            $(".modal-body #hiddenValue").text(ticketInfo);
	            $("#id").attr("value", ticketId);
	            $("#form").attr("action", "ticket/delete");
	        });
		});
	</script>
</body>
</html>
