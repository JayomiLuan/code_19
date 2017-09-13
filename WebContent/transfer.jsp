<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form action="${pageContext.request.contextPath}/accountServlet" method="post">
		<table align="center">
			<tr>
				<td>汇款人：</td>
				<td>
					<select name="outId">
						<c:forEach var="a" items="${accountList }">
							<option value="${a.id }">${a.name }</option>
						</c:forEach>
					</select>
				</td>
			</tr>		
			<tr>
				<td>收款人：</td>
				<td>
					<select name="inId">
						<c:forEach var="a" items="${accountList }">
							<option value="${a.id }">${a.name }</option>
						</c:forEach>
					</select>
				</td>
			</tr>		
			<tr>
				<td>转账金额：</td>
				<td><input type="text" name="money"></td>
			</tr>		
			<tr>
				<td colspan="2" align="center">
					<input type="submit" value="确认" />
					<input type="reset"/>
				</td>
			</tr>		
		</table>
	</form>
	
</body>
</html>