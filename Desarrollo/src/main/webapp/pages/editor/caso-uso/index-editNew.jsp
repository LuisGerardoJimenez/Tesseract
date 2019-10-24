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
	<script type="text/javascript" charset="utf8" src="${pageContext.request.contextPath}/pages/editor/caso-uso/js/token.js"></script>
	<script type="text/javascript" charset="utf8" src="${pageContext.request.contextPath}/pages/editor/caso-uso/js/index-editNew.js"></script>	
]]>

</head>
<body>
	<h1>Registrar Caso de uso</h1>
	
	<s:actionmessage theme="jquery" />
	<s:actionerror theme="jquery" />
	<br/>

	<p class="instrucciones">Los campos marcados con * son obligatorios</p>


	<s:form autocomplete="off" id="frmCU" theme="simple"
		action="%{#pageContext.request.contextPath}/caso-uso" 
		method="post" onsubmit="return preparaEnvio();">
		<div class="formulario">
			<div class="tituloFormulario">Información general del caso de
				uso</div>
			<table class="seccion">
				<tr>
					<td class="label"><s:text name="labelClave" /></td>
					<td class="labelDerecho"><s:property value="model.clave"/>
							<s:fielderror fieldName="model.clave" cssClass="error" theme="jquery" /></td>
					<s:hidden value="%{model.clave}" name="model.clave"/>
				</tr>
				<tr>
					<td class="label obligatorio"><s:text name="labelNumero" /></td>
					<td><s:textfield name="model.numero" maxlength="20"
							cssErrorClass="input-error" cssClass="inputFormulario ui-widget" /> <s:fielderror
							fieldName="model.numero" cssClass="error" theme="jquery" /></td>
				</tr>
				<tr>
					<td class="label obligatorio"><s:text name="labelNombre" /></td>
					<td><s:textfield name="model.nombre" maxlength="200"
							cssErrorClass="input-error" cssClass="inputFormulario ui-widget" /> <s:fielderror
							fieldName="model.nombre" cssClass="error" theme="jquery" /></td>
				</tr>
				<tr>
						<td class="label"><s:text name="labelResumen" /></td>
						<td><s:textarea rows="5" name="model.descripcion" cssClass="inputFormularioExtraGrande ui-widget"
								maxlength="999" cssErrorClass="input-error"></s:textarea> 
								<s:fielderror
								fieldName="model.descripcion" cssClass="error"
								theme="jquery" /></td>
					</tr>
			</table>
		</div>
		<div class="formulario">
			<div class="tituloFormulario">Descripción del caso de uso</div>
				<table class="seccion">
					<tr>
						<td class="label"><s:text name="labelActores" /></td>
						<td><s:textarea rows="5" name="model.redaccionActores" cssClass="inputFormularioExtraGrande ui-widget"
								maxlength="999" cssErrorClass="input-error" id="actorInput" ></s:textarea>
								<s:fielderror
								fieldName="model.redaccionActores" cssClass="error"
								theme="jquery" /> </td>
					</tr>
					<tr>
						<td class="label"><s:text name="labelEntradas" /></td>
						<td><s:textarea rows="5" name="model.redaccionEntradas" id="entradaInput" cssClass="inputFormularioExtraGrande ui-widget"
								maxlength="999" cssErrorClass="input-error"></s:textarea> <s:fielderror
								fieldName="model.redaccionEntradas" cssClass="error"
								theme="jquery" /></td>
					</tr>
					<tr>
						<td class="label"><s:text name="labelSalidas" /></td>
						<td><s:textarea rows="5" name="model.redaccionSalidas" id="salidaInput" cssClass="inputFormularioExtraGrande ui-widget"
								maxlength="999" cssErrorClass="input-error"></s:textarea> <s:fielderror
								fieldName="model.redaccionSalidas" cssClass="error"
								theme="jquery" /></td>
					</tr>
					<tr>
						<td class="label"><s:text
								name="labelReglasNegocio" /></td>
						<td><s:textarea rows="5" name="model.redaccionReglasNegocio" id="inputor" cssClass="inputFormularioExtraGrande ui-widget"
								maxlength="999" cssErrorClass="input-error"></s:textarea> <s:fielderror
								fieldName="model.redaccionReglasNegocio" cssClass="error"
								theme="jquery" /></td>
					</tr>
				</table>
		</div>
		
		<br />
		<div align="center">
			<s:submit class="boton" value="Aceptar" />

			<input class="boton" type="button"
				onclick="location.href='${pageContext.request.contextPath}/caso-uso'"
				value="Cancelar" />
		</div>
		
	</s:form>
	
	<!-- Json de elementos -->
	<s:hidden name="jsonReglasNegocio" id="jsonReglasNegocio" value="%{jsonReglasNegocio}"/>
	<s:hidden name="jsonEntidades" id="jsonEntidades" value="%{jsonEntidades}"/>
	<s:hidden name="jsonPantallas" id="jsonPantallas" value="%{jsonPantallas}"/>
	<s:hidden name="jsonMensajes" id="jsonMensajes" value="%{jsonMensajes}"/>
	<s:hidden name="jsonActores" id="jsonActores" value="%{jsonActores}"/>
	<s:hidden name="jsonTerminosGls" id="jsonTerminosGls" value="%{jsonTerminosGls}"/>
	<s:hidden name="jsonAtributos" id="jsonAtributos" value="%{jsonAtributos}"/>
</body>
	</html>
</jsp:root>