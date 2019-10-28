<?xml version="1.0" encoding="UTF-8" ?>

<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	xmlns:s="/struts-tags" xmlns:sj="/struts-jquery-tags">
	<jsp:directive.page language="java"
		contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
	<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Trayectoria</title>
<![CDATA[
	<script type="text/javascript" charset="utf8" src="${pageContext.request.contextPath}/resources/scripts/constructores.js"></script>
	<script type="text/javascript" charset="utf8" src="${pageContext.request.contextPath}/resources/scripts/validaciones.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/scripts/jquery.caret.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/scripts/jquery.atwho.js"></script>
	<script type="text/javascript" charset="utf8" src="${pageContext.request.contextPath}/pages/editor/pasos/js/token.js"></script>
	<script type="text/javascript" charset="utf8" src="${pageContext.request.contextPath}/pages/editor/pasos/js/index-editNew.js"></script>
]]>

</head>
<body>
	<h1>Registrar Paso</h1>
	
	<s:actionmessage theme="jquery" />
	<s:actionerror theme="jquery" />
	<br/>

	<p class="instrucciones">Ingrese la información solicitada.</p>
	<s:form autocomplete="off" id="frmTrayectoria" theme="simple" action="%{#pageContext.request.contextPath}/pasos"
		method="post" onsubmit="return prepararEnvio();">
		<s:hidden id="filaPaso" />
			<div class="formulario">
				<div class="tituloFormulario">Información del Paso</div>
				<table class="seccion">
						<tr>
							<td class="label obligatorio"><s:text name="labelRealiza"/></td>
							<td>
								<s:select 
									name="model.realizaActor"
									list="listRealiza"
									headerKey="-1" 
									headerValue="Seleccione"
									id="idRealizaActor"
									value="model.realizaActor"
									cssErrorClass="select-error" 
									cssClass="inputFormulario ui-widget" />
									<s:fielderror fieldName="model.realizaActor" cssClass="error"
									theme="jquery" />
						</td>
						</tr>
						<tr>
							<td class="label obligatorio"><s:text name="labelVerbo"/></td>
							<td><s:select list="listVerbos" cssClass="inputFormulario" name="model.verbo" id="model.verbo"
       						cssErrorClass="input-error" headerKey="-1" headerValue="Seleccione" onchange="verificarOtro(this);"></s:select>
       						<s:fielderror fieldName="model.verbo" cssClass="error"
									theme="jquery" />
       						</td>
						</tr>
						<tr style="display: none;" id = "otroVerbo">
							<td class="label obligatorio"><s:text name="labelOtro" /></td>
							<td><s:textfield name="model.otroVerbo" id="model.otroVerbo" maxlength="10"
							cssErrorClass="input-error" cssClass="inputFormulario ui-widget" />
							</td>
						</tr>
						<tr>
							<td class="label obligatorio"><s:text name="labelRedaccion" /></td>
							<td><s:textarea rows="5" name="model.redaccion" id="inputor" cssClass="inputFormularioExtraGrande ui-widget"
									maxlength="999" cssErrorClass="input-error"></s:textarea>
									<s:fielderror fieldName="model.redaccion" cssClass="error"
									theme="jquery" />
							</td>
						</tr>
				</table>
			</div>
			<br />
		<br />
		<div align="center">
			<s:submit class="boton" value="Aceptar" />
			
			<s:url var="urlGestionarTrayectorias" value="%{#pageContext.request.contextPath}/pasos">
			</s:url>
			<input class="boton" type="button"
				onclick="location.href='${urlGestionarTrayectorias}'"
				value="Cancelar" />			
		</div>
		<s:hidden id="jsonPasosTabla" name="jsonPasosTabla" value="%{jsonPasosTabla}"/>    	
	</s:form>
	
	
	<!-- EMERGENTE REGISTRAR PASO -->	
   	<sj:dialog id="pasoDialog" title="Registrar Paso" autoOpen="false" 
   	minHeight="300" minWidth="800" modal="true" draggable="true" >
	   	<s:form autocomplete="off" id="frmPaso" name="frmPasoName" theme="simple">
	   		<s:hidden id="filaPaso" />
			<div class="formulario">
				<div class="tituloFormulario">Información del Paso</div>
				<table class="seccion">
						<tr>
							<td class="label obligatorio"><s:text name="labelRealiza"/></td>
							<td><s:select list="listRealiza" cssClass="inputFormulario" name="paso.realizaActor" id="realiza" 
       						cssErrorClass="input-error" headerKey="-1" headerValue="Seleccione"></s:select></td>
						</tr>
						<tr>
							<td class="label obligatorio"><s:text name="labelVerbo"/></td>
							<td><s:select list="listVerbos" cssClass="inputFormulario" name="paso.verbo" id="paso.verbo"
       						cssErrorClass="input-error" headerKey="-1" headerValue="Seleccione" onchange="verificarOtro();"></s:select></td>
						</tr>
						<tr style="display: none;" id = "otroVerbo">
							<td class="label obligatorio"><s:text name="labelOtro" /></td>
							<td><s:textfield name="paso.otroVerbo" id="paso.otroVerbo" maxlength="10"
							cssErrorClass="input-error" cssClass="inputFormulario ui-widget" />
							</td>
						</tr>
						<tr>
							<td class="label obligatorio"><s:text name="labelRedaccion" /></td>
							<td><s:textarea rows="5" name="paso.redaccion" id="inputor" cssClass="inputFormularioExtraGrande ui-widget"
									maxlength="999" cssErrorClass="input-error"></s:textarea></td>
						</tr>
				</table>
			</div>
			<br />
			<div align="center">
				<input type="button"
					onclick="verificarRegistroModificacion()"
					value="Aceptar" />
				<input type="button"
					onclick="cancelarRegistrarPaso()"
					value="Cancelar" />
			</div>
			
			
		</s:form>
	</sj:dialog>
	
	<!-- Json de elementos -->
	<s:hidden name="jsonReglasNegocio" id="jsonReglasNegocio" value="%{jsonReglasNegocio}"/>
	<s:hidden name="jsonEntidades" id="jsonEntidades" value="%{jsonEntidades}"/>
	<s:hidden name="jsonCasosUsoProyecto" id="jsonCasosUsoProyecto" value="%{jsonCasosUsoProyecto}"/>
	<s:hidden name="jsonPantallas" id="jsonPantallas" value="%{jsonPantallas}"/>
	<s:hidden name="jsonMensajes" id="jsonMensajes" value="%{jsonMensajes}"/>
	<s:hidden name="jsonActores" id="jsonActores" value="%{jsonActores}"/>
	<s:hidden name="jsonTerminosGls" id="jsonTerminosGls" value="%{jsonTerminosGls}"/>
	<s:hidden name="jsonAtributos" id="jsonAtributos" value="%{jsonAtributos}"/>
	<s:hidden name="jsonPasos" id="jsonPasos" value="%{jsonPasos}"/>
	<s:hidden name="jsonTrayectorias" id="jsonTrayectorias" value="%{jsonTrayectorias}"/>
	<s:hidden name="jsonAcciones" id="jsonAcciones" value="%{jsonAcciones}"/>
	<!-- Booleano que indica si existen trayectorias -->
	<s:hidden id="existeTPrincipal" value="%{existeTPrincipal}"/>
	
	
	
</body>
	</html>
</jsp:root>