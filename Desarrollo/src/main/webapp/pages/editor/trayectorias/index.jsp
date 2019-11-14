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
	<h1>Gestionar Trayectorias</h1>
	<s:actionmessage theme="jquery" />
	<s:actionerror theme="jquery" />

	<br />
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
			<table id="gestion" class="tablaGestion" cellspacing="0" width="100%">
				<thead>
					<th></th>
					<th style="width: 15%;"><s:text name="colTrayectoria" /></th>
					<th style="width: 70%;"><s:text name="colCondicion" /></th>
					<th style="width: 15%;"><s:text name="colAcciones" /></th>
				</thead>
				<tbody>
					<s:iterator value="listTrayectorias" var="tray">
						<s:if test="%{#tray.alternativa}">
							<tr>
								<td>Alternatia</td>
								<td><s:property value="%{#tray.clave}" /></td>
								<td><s:property value="%{#tray.condicion}" /></td>
								<td align="center">
									<!-- Modificar trayectoria --> 
									<s:url var="urlModificar"
										value="%{#pageContext.request.contextPath}/trayectorias/%{#tray.id}/edit" />
									<s:a href="%{urlModificar}">
										<img id="" class="button" title="Modificar Trayectoria"
											src="${pageContext.request.contextPath}/resources/images/icons/Editar.svg" alt="Modificar Trayectoria"/>
									</s:a> ${blanks} 
									<!-- Gestionar Pasos -->			
									<s:url var="urlGestionarPasos" value="%{#pageContext.request.contextPath}/trayectorias!entrarPasos?idSel=%{#tray.id}"/>
									<s:a href="%{urlGestionarPasos}">
										<img id="" class="button" title="Gestionar Pasos"
												src="${pageContext.request.contextPath}/resources/images/icons/Pasos.svg" alt="Gestionar Pasos"/>
									</s:a>
									
									<s:a href="#" onclick="return mostrarMensajeEliminacion('%{#tray.id}');">
										<img id="" class="button" title="Eliminar Trayectoria"
											src="${pageContext.request.contextPath}/resources/images/icons/Eliminar.svg" alt="Eliminar Trayectoria"/>
									</s:a>
								</td>
							</tr>
						</s:if>
						<s:else>
							<td>Principal</td>
							<td class="trayectoriaPrincipal"><s:property
									value="%{#tray.clave}" /></td>
							<td class="trayectoriaPrincipal">Trayectoria principal</td>
							<td align="center" class="trayectoriaPrincipal">
								<!-- Modificar trayectoria --> 
								<s:url var="urlModificar"
										value="%{#pageContext.request.contextPath}/trayectorias/%{#tray.id}/edit" />
									<s:a href="%{urlModificar}">
										<img id="" class="button" title="Modificar Trayectoria"
											src="${pageContext.request.contextPath}/resources/images/icons/Editar.svg" alt="Modificar Trayectoria"/>
									</s:a> ${blanks} 
								<!-- Gestionar Pasos -->			
								<s:url var="urlGestionarPasos" value="%{#pageContext.request.contextPath}/trayectorias!entrarPasos?idSel=%{#tray.id}"/>
								<s:a href="%{urlGestionarPasos}">
									<img id="" class="button" title="Gestionar Pasos"
											src="${pageContext.request.contextPath}/resources/images/icons/Pasos.svg" alt="Gestionar Pasos"/>
								</s:a>	
								${blanks}	
								<!-- Eliminar caso de uso --> 
								<s:a href="#" onclick="return mostrarMensajeEliminacion('%{#tray.id}');">
									<img id="" class="button" title="Eliminar Trayectoria"
										src="${pageContext.request.contextPath}/resources/images/icons/Eliminar.svg" alt="Eliminar Trayectoria"/>
								</s:a>
							</td>
						</s:else>
					</s:iterator>
				</tbody>
			</table>

		</div>
		<br />
		<br />
		<div align="center">
			<button class="boton" 
				onclick="location.href='${pageContext.request.contextPath}/caso-uso'">
				<s:text name="Regresar"></s:text>
			</button>
			<button class="boton" 
				onclick="location.href='${pageContext.request.contextPath}/trayectorias/new'">
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

