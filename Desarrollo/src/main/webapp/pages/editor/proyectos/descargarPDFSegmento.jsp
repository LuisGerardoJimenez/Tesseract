<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	xmlns:s="/struts-tags" xmlns:sj="/struts-jquery-tags">
	<jsp:directive.page language="java"
		contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
	<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Casos de uso</title>
<![CDATA[
	<script type="text/javascript" charset="utf8" src="${pageContext.request.contextPath}/resources/scripts/constructores.js"></script>
	<script type="text/javascript" charset="utf8" src="${pageContext.request.contextPath}/pages/editor/proyectos/js/descargarPDFSegmento.js"></script>
]]> 
</head>
<body>
	<div class="modal" id="modal"><!-- Place at bottom of page --></div>
	<h1>Generar Documento por Casos de Uso</h1>

	<s:actionmessage theme="jquery" />
	<s:actionerror theme="jquery" />

	<s:form autocomplete="off" theme="simple" action="%{#pageContext.request.contextPath}/proyectos!descargarPDFCU" onsubmit="prepararEnvio()" method="post">
	<div class="form">
	 
		<table id="gestion" class="tablaGestion" cellspacing="0" width="100%"> 
			<thead>
				<tr>
					<th style="width: 5%;"><s:text name="colElegir" /></th>
					<th style="width: 15%;"><s:text name="colModulo"/></th>
					<th><s:text name="colCasoUso"/></th>
				</tr>
			</thead>
			<tbody>
			<s:iterator value="listCU" var="cu">
				<tr>
					<td><input id="checkbox-${cu.id}" type="checkbox" /></td>
					<td><s:property value="%{#cu.modulo.nombre}"/></td>
					<td><s:property value="%{#cu.clave + ' ' + #cu.numero + ' ' +#cu.nombre}"/></td>
				</tr>
			</s:iterator>
			</tbody>
		</table>
		
	</div>
	<br />
	<br />
	<div align="center">
		<s:submit class="boton" value="Generar Documento" />
		<s:url var="urlGestionarProyectos"
			value="%{#pageContext.request.contextPath}/proyectos">
		</s:url>
		${blanks}
		<s:url var="urlProyectos"
				value="%{#pageContext.request.contextPath}/proyectos">
		</s:url>
		<input class="boton" type="button"
				onclick="location.href='${urlProyectos}'"
				value="Regresar" />
	</div>
		<s:hidden id="jsonCasoUsoTabla" name="jsonCasoUsoTabla"
			value="%{jsonCasoUsoTabla}" />
	</s:form>
	<div class = "invisible">
	<!-- EMERGENTE CONFIRMAR ELIMINACIÓN -->
	<sj:dialog id="confirmarEliminacionDialog" title="Confirmación" autoOpen="false"
		minHeight="100" minWidth="400" modal="true" draggable="true">
		<s:form autocomplete="off" id="frmConfirmarEliminacion" name="frmConfirmarEliminacionName" theme="simple">
				<div class="seccion">
				<s:text name="MSG10"></s:text>
				</div>
			<br />
			<div align="center">
				<input id = "btnConfirmarEliminacion" type="button" onclick="" value="Aceptar"/> <input
					type="button" onclick="cancelarConfirmarEliminacion();" value="Cancelar" />
			</div>
		</s:form>
	</sj:dialog>
	<!-- EMERGENTE ERROR REFERENCIAS -->
	<sj:dialog id="mensajeReferenciasDialog" title="Confirmación" autoOpen="false"
		minHeight="150" minWidth="700" modal="true" draggable="true">
		<s:form autocomplete="off" id="frmConfirmarEliminacion" name="frmConfirmarEliminacionName" theme="simple">
				<div class="seccion">
				<s:text name="MSG40"/>
				<div id="elementosReferencias"></div>
				</div>
			<br />
			<div align="center">
				<input type="button" onclick="cerrarMensajeReferencias()" value="Aceptar"/> 
			</div>
		</s:form>
	</sj:dialog>
	
	<!-- EMERGENTE TERMINAR -->
	<sj:dialog id="mensajeTerminarDialog" title="Confirmación" autoOpen="false"
		minHeight="100" minWidth="550" modal="true" draggable="true">
		<s:form autocomplete="off" id="frmConfirmarTermino" name="frmConfirmarTerminoName" theme="simple">
				
				<div class="seccion">
				<s:text name="MSG8"></s:text>
				</div>
				<br />
			<div align="center">
				<input id="btnConfirmarTermino" type="button" value="Aceptar"/> 
				<input type="button" onclick="cancelarConfirmarTermino();" value="Cancelar" />
			</div>
		</s:form>
	</sj:dialog>
</div>
</body>
</html>
</jsp:root>

