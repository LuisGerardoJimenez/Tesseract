<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	xmlns:s="/struts-tags" xmlns:sj="/struts-jquery-tags">
	<jsp:directive.page language="java"
		contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
	<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Atributos</title>
<![CDATA[
	<script type="text/javascript" charset="utf8" src="${pageContext.request.contextPath}/pages/editor/atributos/js/index.js"></script>
]]>
</head>

<body>
	<h1>Gestionar Atributos</h1>
	<s:actionmessage theme="jquery"/>
	<s:actionerror theme="jquery"/>
	
	<br/>
	<s:form autocomplete="off" theme="simple" onsubmit="return false;">
	<div class="form">
		<table id="gestion" class="tablaGestion" cellspacing="0" width="100%">
			<thead>
				<th style="width: 20%;"><s:text name="colAtributo"/></th>
				<th style="width: 20%;"><s:text name="colDescripcion" /></th>
				<th style="width: 10%;"><s:text name="colObligatorio"/></th>
				
				<th style="width: 0%;"><s:text name="colTipoDato"/></th>
				<th style="width: 0%;"><s:text name="colOtroTipoDato" /></th>
				<th style="width: 0%;"><s:text name="colLongitud" /></th>
				<th style="width: 0%;"><s:text name="colFormatoArchivo" /></th>
				<th style="width: 0%;"><s:text name="colTamanioArchivo" /></th>
				<th style="width: 0%;"><s:text name="colUnidadTamanio" /></th>
				
				<th style="width: 5%;"><s:text name="colAcciones"/></th>
			</thead>
			<tbody>
			<s:iterator value="listAtributos" var="atributo">
				<tr>
					<td><s:property value="%{#atributo.nombre}"/></td>
					<td><s:property value="%{#atributo.descripcion}"/></td>
					<td align="center">
						<s:if test = "#atributo.obligatorio == true">
							<s:label value="Si"/>
						</s:if>
						<s:else>
							<s:label value="No"/>
						</s:else>
					</td>
					<td><s:property value="%{#atributo.tipoDato.nombre}"/></td>
					<td align="center">
						<s:if test = "#atributo.otroTipoDato == null">
							<img class="button" title="No aplica" src="${pageContext.request.contextPath}/resources/images/icons/NA.svg" alt="No aplica"/>
						</s:if>
						<s:else>
							<s:property value="%{#atributo.otroTipoDato}"/>
						</s:else>
					</td>
					<td align="center">
						<s:if test = "#atributo.longitud == null">
							<img class="button" title="No aplica" src="${pageContext.request.contextPath}/resources/images/icons/NA.svg" alt="No aplica"/>
						</s:if>
						<s:else>
							<s:property value="%{#atributo.longitud}"/>
						</s:else>
					</td>
					<td align="center">
						<s:if test = "#atributo.formatoArchivo == null">
							<img class="button" title="No aplica" src="${pageContext.request.contextPath}/resources/images/icons/NA.svg" alt="No aplica"/>
						</s:if>
						<s:else>
							<s:property value="%{#atributo.formatoArchivo}"/>
						</s:else>
					</td>
					<td align="center">
						<s:if test = "#atributo.tamanioArchivo == null">
							<img class="button" title="No aplica" src="${pageContext.request.contextPath}/resources/images/icons/NA.svg" alt="No aplica"/>
						</s:if>
						<s:else>
							<s:property value="%{#atributo.tamanioArchivo}"/>
						</s:else>
					</td>
					<td align="center">
						<s:if test = "#atributo.unidadTamanio == null">
							<img class="button" title="No aplica" src="${pageContext.request.contextPath}/resources/images/icons/NA.svg" alt="No aplica"/>
						</s:if>
						<s:else>
							<s:property value="%{#atributo.unidadTamanio.nombre}"/>
						</s:else>
					</td>
					<td align="center">
						<!-- Modificar Atributo -->		
						<s:url var="urlEditar" value="%{#pageContext.request.contextPath}/atributos/%{#atributo.id}/edit"/>			
							<s:a href="%{urlEditar}">
							<img id="" class="button" title="Modificar Atributo"
								src="${pageContext.request.contextPath}/resources/images/icons/Editar.svg" alt="Modificar Atributo"/>
						</s:a>
					${blanks}
						<!-- Eliminar Atributo -->			
						<!--<s:url var="urlEliminar" value="%{#pageContext.request.contextPath}/atributos/%{#atributo.id}?_method=delete" method="post"/>-->
						<s:a href="#" onclick="return mostrarMensajeEliminacion(%{#atributo.id});">
						<img id="" class="button" title="Eliminar Atributo"
								src="${pageContext.request.contextPath}/resources/images/icons/Eliminar.svg" alt="Eliminar Atributo"/>
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
			onclick="location.href='${pageContext.request.contextPath}/atributos/new'">
			<s:text name="Registrar"></s:text>
		</button>
		${blanks}
		<s:url var="urlGestionarEntidades"
				value="%{#pageContext.request.contextPath}/entidades">
		</s:url>
		<input class="boton" type="button"
				onclick="location.href='${urlGestionarEntidades}'"
				value="Regresar" />
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

