<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	xmlns:s="/struts-tags" xmlns:sj="/struts-jquery-tags">
	<jsp:directive.page language="java"
		contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
	<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Trayectorias</title>
<![CDATA[
	<script type="text/javascript" charset="utf8" src="${pageContext.request.contextPath}/pages/editor/pasos/js/index.js"></script>
	  
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
					<s:iterator value="listPasos" var="paso">
					<tr>
						<td><s:property
								value="%{#paso.numero}" /></td>
						<td>
							<s:if test="%{#paso.realizaActor == 1}">
								<img id="" class="button" title="Realiza Actor"
									src="${pageContext.request.contextPath}/resources/images/icons/actor.png" />
							</s:if>
							<s:else>
								<img id="" class="button" title="Realiza Sistema"
										src="${pageContext.request.contextPath}/resources/images/icons/Ver.svg" />
							</s:else>
							<s:if test="%{#paso.idVerbo == 13}">
								<s:property
								value="%{#paso.otroVerbo + ' '}" />
							</s:if>
							<s:else>
								<s:property
								value="%{#paso.verbo + ' '}" />
							</s:else>
							<s:property
								value="%{#paso.redaccion}" />
						</td>
						<td align="center">
							<s:url var="urlConsultar" value="%{#pageContext.request.contextPath}/pasos/%{#paso.id}!subirPaso"/>
							<s:a href="%{urlConsultar}">
								<img id="" class="button" title="Bajar Paso"
										src="${pageContext.request.contextPath}/resources/images/icons/FlechaAbajo.svg" />
							</s:a>
							${blanks}
							<s:url var="urlConsultar" value="%{#pageContext.request.contextPath}/pasos/%{#paso.id}!bajarPaso"/>
							<s:a href="%{urlConsultar}">
								<img id="" class="button" title="Subir Paso"
										src="${pageContext.request.contextPath}/resources/images/icons/FlechaArriba.svg" />
							</s:a>
							${blanks}
							<s:url var="urlEditar" value="%{#pageContext.request.contextPath}/pasos/%{#paso.id}/edit"/>			
							<s:a href="%{urlEditar}">
								<img id="" class="button" title="Modificar Paso"
										src="${pageContext.request.contextPath}/resources/images/icons/Editar.svg" />
							</s:a>
							<s:a href="#" onclick="return mostrarMensajeEliminacion('%{#paso.id}');">
							<img id="" class="button" title="Eliminar Paso"
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
				onclick="location.href='${pageContext.request.contextPath}/trayectorias'">
				<s:text name="Cancelar"></s:text>
			</button>
			<button class="boton" 
				onclick="location.href='${pageContext.request.contextPath}/pasos/new'">
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

