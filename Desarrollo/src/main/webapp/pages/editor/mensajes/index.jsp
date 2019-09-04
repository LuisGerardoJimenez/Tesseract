<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	xmlns:s="/struts-tags" xmlns:sj="/struts-jquery-tags">
	<jsp:directive.page language="java"
		contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
	<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Mensajes</title>
<![CDATA[
	<script type="text/javascript" charset="utf8" src="${pageContext.request.contextPath}/pages/editor/mensajes/js/index.js"></script>
]]>
</head>

<body>
	<div class="modal" id="modal"><!-- Place at bottom of page --></div>
	<h1>Gestionar Mensajes</h1>
	<s:actionmessage theme="jquery"/>
	<s:actionerror theme="jquery"/>
	
	<br/>
	<s:form autocomplete="off" theme="simple" onsubmit="return false;">
	<div class="form">
		<table id="gestion" class="tablaGestion" cellspacing="0" width="100%">
			<thead>
				<th style="width: 80%;"><s:text name="colMensaje"/></th>
				<th style="width: 20%;"><s:text name="colAcciones"/></th>
			</thead>
			<tbody>
			<s:iterator value="listMensajes" var="mensaje">
				<tr>
					<td><s:property value="%{#mensaje.clave + ' ' + #mensaje.nombre}"/></td>		
					<td align="center">
						${blanks}
						<s:url var="urlCU" value="%{#pageContext.request.contextPath}/mensajes!entrarCU?idSel=%{#mensaje.id}"/>
						<s:a href="%{urlCU}">
						<img id="" class="button" title=""
								src="${pageContext.request.contextPath}/resources/images/icons/UC.svg2" /></s:a>
						${blanks}
						<s:url var="urlIU" value="%{#pageContext.request.contextPath}/mensajes!entrarIU?idSel=%{#mensaje.id}"/>
						<s:a href="%{urlIU}">
						<img id="" class="button" title=""
								src="${pageContext.request.contextPath}/resources/images/icons/IU.svg2" /></s:a>
						${blanks}
						<s:url var="urlEditar" value="%{#pageContext.request.contextPath}/mensajes/%{#mensaje.id}/edit"/>			
						<s:a href="%{urlEditar}">
							<img id="" class="button" title="Modificar Mensaje"
									src="${pageContext.request.contextPath}/resources/images/icons/Editar.svg" />
						</s:a>
						<s:a href="#" onclick="return mostrarMensajeEliminacion('%{#mensaje.id}');">
						<img id="" class="button" title="Eliminar Mensaje"
								src="${pageContext.request.contextPath}/resources/images/icons/Eliminar.svg" /></s:a>
						
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
			onclick="location.href='${pageContext.request.contextPath}/mensajes/new'">
			<s:text name="Registrar"></s:text>
		</button>
	</div>
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
	</div>	
</body>
</html>
</jsp:root>

