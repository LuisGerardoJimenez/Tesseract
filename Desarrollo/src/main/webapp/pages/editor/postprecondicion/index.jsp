<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	xmlns:s="/struts-tags" xmlns:sj="/struts-jquery-tags">
	<jsp:directive.page language="java"
		contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
	<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Precondiciones y Postcondiciones</title>
<![CDATA[
	<script type="text/javascript" charset="utf8" src="${pageContext.request.contextPath}/pages/editor/postprecondicion/js/index.js"></script>
]]>
</head>

<body>
	<h1>Gestionar Precondiciones y Postcondiciones</h1>
	<s:actionmessage theme="jquery" />
	<s:actionerror theme="jquery" />

	<br />

	<s:form autocomplete="off" theme="simple" onsubmit="return false;">
		<div class="form">
			<table id="gestion" class="tablaGestion" cellspacing="0" width="100%">
				<thead>
					<th style="width: 20%;"><s:text name="colTipo" /></th>
					<th style="width: 60%;"><s:text name="colRedaccion" /></th>
					<th style="width: 20%;"><s:text name="colAcciones" /></th>
				</thead>
				<tbody>
					<s:iterator value="listPostprecondiciones" var="postprecondicion">
						<tr>
							<td>
								<s:if test="%{#postprecondicion.precondicion == true}">
									<s:property value="Precondición" />
								</s:if>
								<s:else>
									<s:property value="Postcondición" />
								</s:else>
							</td>
							<td><s:property value="%{#postprecondicion.redaccion}" /></td>
							<td align="center">
								<s:url var="urlEditar" value="%{#pageContext.request.contextPath}/postprecondicion/%{#postprecondicion.id}/edit" />
								<s:a href="%{urlEditar}">
										<img id="" class="button" title="Modificar"
											src="${pageContext.request.contextPath}/resources/images/icons/Editar.svg" />
								</s:a>
								${blanks}
								<s:a href="#" onclick="return mostrarMensajeEliminacion('%{#postprecondicion.id}');">
									<img id="" class="button" title="Eliminar"
										src="${pageContext.request.contextPath}/resources/images/icons/Eliminar.svg" />
								</s:a>
							</td>
						</tr>
					</s:iterator>
				</tbody>
			</table>
		</div>
		<br />
	</s:form>
</body>
	</html>
</jsp:root>

