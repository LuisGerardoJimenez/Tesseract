<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	xmlns:s="/struts-tags" xmlns:sj="/struts-jquery-tags">
	<jsp:directive.page language="java"
		contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
	<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Trayectorias</title>
<![CDATA[
	<script type="text/javascript" charset="utf8" src="${pageContext.request.contextPath}/pages/editor/trayectorias/js/index.js"></script>
	  
]]>
</head>

<body> 
	<h1>Gestionar Pasos</h1>
	<s:actionmessage theme="jquery" />
	<s:actionerror theme="jquery" />	
	<s:set var="obs" value="observaciones" />

	<s:if test="%{#obs != null}">
		<div class="formulario">
			<div class="tituloObservaciones">Observaciones</div>
			<div class="observaciones">
				<s:property value="#obs" />
			</div>
		</div>
	</s:if>
	<s:form autocomplete="off" theme="simple" onsubmit="return false;">
		<div class="form">
			<s:fielderror fieldName="model.pasos" cssClass="error" theme="jquery" />
			<s:hidden name="numPasos" value="%{listPasos.length}" id="numPasos"/> 
			<table id="gestion" class="tablaGestion" cellspacing="0" width="100%">
				<thead>
					<th style="width: 5%;"><s:text name="colNumero"/></th>
					<th style="width: 55%;"><s:text name="colRedaccion"/></th>
					<th style="width: 10%;"><s:text name="colAcciones"/></th>
				</thead>
				<tbody>
					<s:iterator value="listTrayectorias" var="tray">
					<td>1</td>
					<td class="trayectoriaPrincipal"><s:property
							value="%{#tray.clave}" /></td>
					<td class="trayectoriaPrincipal">Trayectoria principal</td>
					<td align="center" class="trayectoriaPrincipal">
						<!-- Modificar trayectoria --> 
						<s:url var="urlModificar"
								value="%{#pageContext.request.contextPath}/trayectorias/%{#tray.id}/edit" />
							<s:a href="%{urlModificar}">
								<img id="" class="button" title="Modificar Trayectoria"
									src="${pageContext.request.contextPath}/resources/images/icons/editar.png" />
							</s:a> ${blanks} 
						<!-- Gestionar Pasos -->			
						<s:url var="urlGestionarPasos" value="%{#pageContext.request.contextPath}/trayectorias!entrarPasos?idSel=%{#tray.id}"/>
						<s:a href="%{urlGestionarPasos}">
							<img id="" class="button" title="Gestionar Pasos"
									src="${pageContext.request.contextPath}/resources/images/icons/T.svg" />
						</s:a>	
						${blanks}	
						<!-- Eliminar caso de uso --> 
						<s:a href="#" onclick="return mostrarMensajeEliminacion('%{#tray.id}');">
							<img id="" class="button" title="Eliminar Trayectoria"
								src="${pageContext.request.contextPath}/resources/images/icons/Eliminar.svg" /></s:a>
					</td>
					</s:iterator>
				</tbody>
			</table>

		</div>	
	</s:form>
	<div class = "invisible">
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

