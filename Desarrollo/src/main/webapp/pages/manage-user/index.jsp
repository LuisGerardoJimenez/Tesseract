<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page"
	xmlns:s="/struts-tags" version="2.0">
	<jsp:directive.page language="java"
		contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
	<jsp:text>
		<![CDATA[ <?xml version="1.0" encoding="UTF-8" ?> ]]>
	</jsp:text>
	<jsp:text>
		<![CDATA[ <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> ]]>
	</jsp:text>
	<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Manage User</title>
</head>
<body>
	<div class="row">
		<h1 class="title">Manage users</h1>
	</div>
	<div class="row">
		<table>
			<thead>
				<tr>
					<th>First name</th>
					<th>Last name</th>
					<th>Second last name</th>
					<th>CURP</th>
					<th>Birth day</th>
					<th>Nickname</th>
					<th>Actions</th>
				</tr>
			</thead>
			<tbody>
				<s:iterator value="listUsers" var="user">
					<tr>
						<td>${user.name}</td>
						<td>${user.lastName}</td>
						<td>${user.secondLastName}</td>
						<td>${user.curp}</td>
						<td>${user.birthDay}</td>
						<td>${user.login}</td>
						<td><a
							href="${pageContext.request.contextPath}/manage-user/${user.id}">Delete</a>
							<a
							href="${pageContext.request.contextPath}/manage-user/${user.id}">View</a>
							<a
							href="${pageContext.request.contextPath}/manage-user/${user.id}/edit">Edit</a></td>
					</tr>
				</s:iterator>
			</tbody>
		</table>
	</div>
	<div class="row">
		<div class="col-md-2">
			<a class="btn btn-success"
				href="${pageContext.request.contextPath}/manage-user/new"><s:text
					name="LBL_NEW_USER" /> </a>
		</div>
	</div>
</body>
	</html>
</jsp:root>