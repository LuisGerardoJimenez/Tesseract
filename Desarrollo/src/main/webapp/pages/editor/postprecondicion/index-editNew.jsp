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
	<script type="text/javascript" charset="utf8" src="${pageContext.request.contextPath}/resources/scripts/validaciones.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/scripts/jquery.caret.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/scripts/jquery.atwho.js"></script>
	<script type="text/javascript" charset="utf8" src="${pageContext.request.contextPath}/pages/editor/postprecondicion/js/token.js"></script>
	<script type="text/javascript" charset="utf8" src="${pageContext.request.contextPath}/pages/editor/postprecondicion/js/index-editNew.js"></script>	
]]>

</head>
<body>
	<h1>Registrar PostPreCondición</h1>
	
	<s:actionmessage theme="jquery" />
	<s:actionerror theme="jquery" />
	<br/>

	<p class="instrucciones">Ingrese la información solicitada.</p>


	<s:form autocomplete="off" id="frmCU" theme="simple"
		action="%{#pageContext.request.contextPath}/postprecondicion" 
		method="post" onsubmit="return preparaEnvio();">
		<div align="center">
		
			<div class="formulario">
				<div class="tituloFormulario">Información de la PostPrecondición</div>
				<table class="seccion">
					<tr>
						<td class="label obligatorio"><s:text name="labelTipo" /></td>
						<td>
						<s:select name="model.precondicion"
								list="listAlternativa" headerValue="Seleccione" headerKey="-1"
								listKey="valor"
								listValue="nombre"
								value="model.precondicion" cssErrorClass="select-error"
								cssClass="inputFormulario ui-widget" /> <s:fielderror
								fieldName="model.precondicion" cssClass="error"
								theme="jquery" />
						</td>
					</tr>
					<tr>
						<td class="label obligatorio"><s:text name="labelRedaccion" /></td>
						<td><s:textarea rows="5" name="model.redaccion" id="precondicionInput" cssClass="inputFormularioExtraGrande ui-widget"
								maxlength="999" cssErrorClass="input-error"></s:textarea></td>
					</tr>
				</table>
			</div>
			<s:submit class="boton" value="Aceptar" />

			<input class="boton" type="button"
				onclick="location.href='${pageContext.request.contextPath}/postprecondicion'"
				value="Cancelar" />
		</div>    
		
	</s:form>
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
	
</body>
	</html>
</jsp:root>