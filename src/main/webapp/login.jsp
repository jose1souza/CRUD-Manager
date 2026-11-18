<jsp:directive.page contentType="text/html; charset=UTF-8" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
	<%@include file="base-head.jsp"%>
	<title>Login - CRUD Manager</title>
</head>
<body>
	<!-- Navbar -->
	<%@include file="nav-menu-login.jsp"%>

	<div class="container">
		<div class="row">
			<div class="col-md-4 col-md-offset-4 col-sm-6 col-sm-offset-3">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h3 class="panel-title text-center">Login</h3>
					</div>
					<div class="panel-body">
						<form action="${pageContext.request.contextPath}/login" method="POST">
							<div class="form-group">
								<label for="user_login_id">Login (e-mail)</label>
								<input type="text" class="form-control" id="user_login_id" name="user_login" required />
							</div>

							<div class="form-group">
								<label for="user_pw_id">Senha</label>
								<input type="password" class="form-control" id="user_pw_id" name="user_pw" required />
							</div>

							<div class="form-group">
								<button type="submit" class="btn btn-danger btn-block">Logar</button>
							</div>

							<c:if test="${param.erro == 'true'}">
								<div class="text-danger small text-center">Usuário ou senha inválidos.</div>
							</c:if>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>

	<%@include file="footer.jsp"%>
</body>
</html>
</html>