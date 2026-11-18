<jsp:directive.page contentType="text/html; charset=UTF-8" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:if test="${totalPages >= 1}">
	<div id="bottom" class="row text-center">
		<ul class="pagination">
			<%-- Botão Anterior --%>
			<c:choose>
				<c:when test="${currentPage > 1}">
					<li><a href="${pageUrl}?page=${currentPage - 1}">&laquo;
							Anterior</a></li>
				</c:when>
				<c:otherwise>
					<li class="disabled"><span>&laquo; Anterior</span></li>
				</c:otherwise>
			</c:choose>

			<%-- Números das páginas --%>
			<c:forEach begin="1" end="${totalPages}" var="i">
				<c:choose>
					<c:when test="${currentPage eq i}">
						<li class="active"><span>${i} <span class="sr-only">(atual)</span></span></li>
					</c:when>
					<c:otherwise>
						<li><a href="${pageUrl}?page=${i}">${i}</a></li>
					</c:otherwise>
				</c:choose>
			</c:forEach>

			<%-- Botão Próximo --%>
			<c:choose>
				<c:when test="${currentPage < totalPages}">
					<li><a href="${pageUrl}?page=${currentPage + 1}">Próximo
							&raquo;</a></li>
				</c:when>
				<c:otherwise>
					<li class="disabled"><span>Próximo &raquo;</span></li>
				</c:otherwise>
			</c:choose>
		</ul>
	</div>
</c:if>