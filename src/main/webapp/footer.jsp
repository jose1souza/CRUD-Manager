<jsp:directive.page contentType="text/html; charset=UTF-8" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<footer>
	<div class="container text-center">
		<jsp:useBean id="date" class="java.util.Date" />
		<fmt:formatDate value="${date}" pattern="yyyy" var="currentYear" />
		<p style="margin: 0;">
			&copy; <c:out value="${currentYear}" /> CRUD Manager. Todos os direitos reservados.
		</p>
	</div>
</footer>
