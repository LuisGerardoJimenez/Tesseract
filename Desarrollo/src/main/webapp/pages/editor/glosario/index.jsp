<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	xmlns:s="/struts-tags" xmlns:sj="/struts-jquery-tags">
	<jsp:directive.page language="java"
		contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
	<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Glosario</title>
<![CDATA[
	<script type="text/javascript" charset="utf8" src="${pageContext.request.contextPath}/pages/editor/glosario/js/index.js"></script>	
]]>
</head>

<body>
	<h1>Gestionar Términos del Glosario</h1>
	<s:actionmessage theme="jquery"/>
	<s:actionerror theme="jquery"/>
	
	<br/>
	<s:form autocomplete="off" theme="simple" onsubmit="return false;">
	<div class="form">
		<table id="gestion" class="tablaGestion" cellspacing="0" width="100%">
			<thead>
				<th style="width: 80%;"><s:text name="colTermino"/></th>
				<th style="width: 20%;"><s:text name="colAcciones"/></th>
			</thead>
			<tbody>
			<s:iterator value="listGlosario" var="termino">
				<tr>
					<td><s:property value="%{#termino.nombre}"/></td>		
					<td align="center">
						<s:url var="urlConsultar" value="%{#pageContext.request.contextPath}/glosario/%{#termino.id}"/>
						<s:a href="%{urlConsultar}">
							<img id="" class="button" title="Consultar Término"
									src="${pageContext.request.contextPath}/resources/images/icons/Ver.svg" alt=""/>
						</s:a>
						${blanks}
						<s:if test="%{#termino.estadoElemento.id == 1}">	
						<s:url var="urlEditar" value="%{#pageContext.request.contextPath}/glosario/%{#termino.id}/edit"/>			
						<s:a href="%{urlEditar}">
							<img id="" class="button" title="Modificar Término"
									src="${pageContext.request.contextPath}/resources/images/icons/Editar.svg" alt="Modificar Término"/>
						</s:a>
						${blanks}		
						<!-- Eliminar término del glosario -->			
						<!--<s:url var="urlEliminar" value="%{#pageContext.request.contextPath}/glosario/%{#termino.id}?_method=delete" method="post"/>-->
						<s:a href="#" onclick="return mostrarMensajeEliminacion(%{#termino.id});">
							<img id="" class="button" title="Eliminar Persona"
								src="${pageContext.request.contextPath}/resources/images/icons/Eliminar.svg" alt="Eliminar Persona"/>
						</s:a>		
						${blanks}	
						</s:if>
					</td>
				</tr>
			</s:iterator>
			</tbody>
		</table>
		
	</div>
	<br />
	<br />
	<div align="center">
		<s:url var="urlProyectos"
				value="%{#pageContext.request.contextPath}/proyectos">
		</s:url>
		<input class="boton" type="button"
				onclick="location.href='${urlProyectos}'"
				value="Regresar" />
		${blanks}
		<button class="boton" 
			onclick="location.href='${pageContext.request.contextPath}/glosario/new'">
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
		minHeight="200" minWidth="700" modal="true" draggable="true">
		<s:form autocomplete="off" id="frmConfirmarEliminacion" name="frmConfirmarEliminacionName" theme="simple">
				<div class="seccion">
				<s:text name="MSG13"/>
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

