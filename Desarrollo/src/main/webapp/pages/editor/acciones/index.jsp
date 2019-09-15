<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	xmlns:s="/struts-tags" xmlns:sj="/struts-jquery-tags">
	<jsp:directive.page language="java"
		contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
	<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Acciones</title>
<![CDATA[
	<script type="text/javascript" charset="utf8" src="${pageContext.request.contextPath}/pages/editor/acciones/js/index.js"></script>
]]>
</head>

<body>
	<h1>Gestionar Acciones</h1>

	<s:actionmessage theme="jquery" />
	<s:actionerror theme="jquery" />

	<s:form autocomplete="off" theme="simple" onsubmit="return false;">
		<div class="form">

			<table id="gestion" class="tablaGestion" cellspacing="0" width="100%">
				<thead>
					<tr>
						<th><s:text name="colAcciones" /></th>
						<th style="width: 20%;"><s:text name="colAcciones" /></th>
					</tr>
				</thead>
				<tbody>
					<s:iterator value="listAcciones" var="accion">
						<tr>
							<td>
								<s:property value="%{#accion.nombre}" /></td>
							<td align="center">
								${blanks}
								<s:url var="urlEditar"
									value="%{#pageContext.request.contextPath}/acciones/%{#accion.id}/edit" />
								<s:a href="%{urlEditar}">
									<img id="" class="button" title="Modificar Acción"
										src="${pageContext.request.contextPath}/resources/images/icons/Editar.svg" />
								</s:a>
								${blanks}
								<s:a
									onclick="return verificarEliminacionElemento(%{#accion.id});">
									<img id="" class="button" title="Eliminar Acción"
										src="${pageContext.request.contextPath}/resources/images/icons/Eliminar.svg" />
								</s:a>
							</td>
						</tr>
					</s:iterator>
				</tbody>
			</table>

		</div>
		<br />
		<br />
		<div align="center">
			<button class="boton" 
				onclick="location.href='${pageContext.request.contextPath}/acciones/new'">
				<s:text name="Registrar"></s:text>
			</button>
			${blanks}
			<s:url var="urlGestionarPantallas"
					value="%{#pageContext.request.contextPath}/pantallas">
			</s:url>
			<input class="boton" type="button"
					onclick="location.href='${urlGestionarPantallas}'"
					value="Regresar" />
		</div>
	</s:form>
	<div class="invisible">
		<!-- EMERGENTE CONFIRMAR ELIMINACIÓN -->
		<sj:dialog id="confirmarEliminacionDialog" title="Confirmación"
			autoOpen="false" minHeight="100" minWidth="400" modal="true"
			draggable="true">
			<s:form autocomplete="off" id="frmConfirmarEliminacion"
				name="frmConfirmarEliminacionName" theme="simple">
				<div class="seccion">
					<s:text name="MSG11"></s:text>
				</div>
				<br />
				<div align="center">
					<input id="btnConfirmarEliminacion" type="button" onclick=""
						value="Aceptar" /> <input type="button"
						onclick="cancelarConfirmarEliminacion();" value="Cancelar" />
				</div>
			</s:form>
		</sj:dialog>
		<!-- EMERGENTE ERROR REFERENCIAS -->
		<sj:dialog id="mensajeReferenciasDialog" title="Confirmación"
			autoOpen="false" minHeight="200" minWidth="700" modal="true"
			draggable="true">
			<s:form autocomplete="off" id="frmConfirmarEliminacion"
				name="frmConfirmarEliminacionName" theme="simple">
				<div class="seccion">
					<s:text name="MSG14" />
					<div id="elementosReferencias"></div>
				</div>
				<br />
				<div align="center">
					<input type="button" onclick="cerrarMensajeReferencias()"
						value="Aceptar" />
				</div>
			</s:form>
		</sj:dialog>
	</div>
</body>
	</html>
</jsp:root>

