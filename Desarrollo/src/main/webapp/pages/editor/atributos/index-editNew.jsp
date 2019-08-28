<?xml version="1.0" encoding="UTF-8" ?>

<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	xmlns:s="/struts-tags" xmlns:sj="/struts-jquery-tags">
	<jsp:directive.page language="java"
		contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
	<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Atributos</title>
<![CDATA[
	<script type="text/javascript" charset="utf8" src="${pageContext.request.contextPath}/resources/scripts/constructores.js"></script>
	<script type="text/javascript" charset="utf8" src="${pageContext.request.contextPath}/resources/scripts/validaciones.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/scripts/jquery.caret.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/scripts/jquery.atwho.js"></script>
	<script type="text/javascript" charset="utf8" src="${pageContext.request.contextPath}/pages/editor/atributos/js/index-editNew.js"></script>	
]]>

</head>
<body>

	<h1>Registrar Atributo</h1>

	<s:actionmessage theme="jquery" />
	<s:actionerror theme="jquery" />
	<br />

	<p class="instrucciones">Ingrese la información solicitada.</p>
	<s:form autocomplete="off" id="frmAtributo" theme="simple"
		action="%{#pageContext.request.contextPath}/atributos" method="post">
		<div class="formulario">
			<div class="tituloFormulario">Información del Atributo</div>
			<table class="seccion">
				<tr>
						<td class="label obligatorio"><s:text name="labelNombre" /></td>
						<td><s:textfield name="model.nombre" id="model.nombre" cssClass="inputFormulario ui-widget"
								maxlength="50" cssErrorClass="input-error"/>
							<s:fielderror fieldName ="model.nombre" cssClass="error" theme="jquery" /></td>
					</tr>
					<tr>
						<td class="label obligatorio"><s:text name="labelDescripcion" /></td>
						<td><s:textarea rows="5" name="model.descripcion" id="model.descripcion" cssClass="inputFormularioExtraGrande ui-widget"
								maxlength="999" cssErrorClass="input-error"/>
								<s:fielderror fieldName ="model.descripcion" cssClass="error" theme="jquery" /></td>
					</tr>
					<tr>
						<td class="label obligatorio"><s:text name="labelTipoDato"/></td>
						<td><s:select list="listTipoDato" cssClass="inputFormulario" name="model.tipoDato.id" id="model.tipoDato.id" listKey="id"
       						cssErrorClass="input-error" headerValue="Seleccione" headerKey="-1" listValue="nombre" onchange="disablefromTipoDato();"/>
       						<s:fielderror fieldName ="model.tipoDato.id" cssClass="error" theme="jquery" /></td>
					</tr>
					<tr id = 'trOtro' style="display: none;">
						<td class="label obligatorio"><s:text name="labelOtro" /></td>
						<td><s:textfield name="model.otroTipoDato" id="model.otroTipoDato" cssClass="inputFormulario ui-widget"
								maxlength="45" cssErrorClass="input-error"/>
								<s:fielderror fieldName ="model.otroTipoDato" cssClass="error" theme="jquery" /></td>
					</tr>

					<tr id = 'trLongitud' style="display: none;">
						<td class="label obligatorio"><s:text name="labelLongitud" /></td>
						<td><s:textfield name="model.longitud" id="model.longitud" cssClass="inputFormulario ui-widget"
								maxlength="10" cssErrorClass="input-error" type="number" step="1"/>
								<s:fielderror fieldName ="model.longitud" cssClass="error" theme="jquery" /></td>
					</tr>

					<tr id = 'trTextoAyudaFormato' style="display: none;">
						<td></td>
						<td class="textoAyuda">Indique las extensiones permitidas separadas por una coma p.e.: PDF, docx, doc.</td>
					</tr>

					<tr id = 'trFormatoArchivo' style="display: none;">
						<td class="label obligatorio"><s:text name="labelFormatoArchivo" /></td>
						<td><s:textfield name="model.formatoArchivo" id="model.formatoArchivo" cssClass="inputFormulario ui-widget"
								maxlength="50" cssErrorClass="input-error"/>
								<s:fielderror fieldName ="model.formatoArchivo" cssClass="error" theme="jquery" /></td>
					</tr> 

					<tr id = 'trTamanioArchivo' style="display: none;">
						<td class="label obligatorio"><s:text name="labelTamanioArchivo" /></td>
						<td><s:textfield name="model.tamanioArchivo" id="model.tamanioArchivo"
								maxlength="10" cssErrorClass="input-error" />${blanks}
						<s:select list="listUnidadTamanio"  name="model.unidadTamanio.id" id="model.unidadTamanio.id" listKey="id"
       						cssErrorClass="input-error" headerValue="Seleccione" headerKey="0" listValue="abreviatura"/></td>
					</tr>

					<tr>
						<td class = "label"><s:text name="labelObligatorio" /></td>
						<td><s:checkbox 
								name="model.obligatorio" id="model.obligatorio" cssErrorClass="input-error"></s:checkbox></td>
					</tr>
			</table> 
		</div>

		<br />
		<div align="center">
			<s:submit class="boton" value="Aceptar" />

			<s:url var="urlGestionarAtributos"
				value="%{#pageContext.request.contextPath}/atributos">
			</s:url>
			<input class="boton" type="button"
				onclick="location.href='${urlGestionarAtributos}'"
				value="Cancelar" />
		</div>
		
	</s:form>

</body>
	</html>
</jsp:root>
