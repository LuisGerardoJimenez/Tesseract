<?xml version="1.0" encoding="UTF-8" ?>

<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	xmlns:s="/struts-tags" xmlns:sj="/struts-jquery-tags">
	<jsp:directive.page language="java"
		contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
	<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Pantalla</title>
<![CDATA[
	<script type="text/javascript" charset="utf8" src="${pageContext.request.contextPath}/resources/scripts/constructores.js"></script>
	<script type="text/javascript" charset="utf8" src="${pageContext.request.contextPath}/resources/scripts/validaciones.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/scripts/jquery.caret.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/scripts/jquery.atwho.js"></script>
	<script type="text/javascript" charset="utf8" src="${pageContext.request.contextPath}/pages/editor/pantallas/js/index-edit.js"></script>	
]]>

</head>
<body>

	<h1>Modificar Pantalla</h1>

	<s:actionmessage theme="jquery" />
	<s:actionerror theme="jquery" />
	<br />

	<p class="instrucciones">Ingrese la información solicitada.</p>
	<s:form autocomplete="off" id="frmPantalla" theme="simple"
		enctype="multipart/form-data"
		action="%{#pageContext.request.contextPath}/pantallas/%{idSel}" method="post">
		<s:hidden name="_method" value="put" />
		<div class="formulario">
			<div class="tituloFormulario">Información general de la
				Pantalla</div>
			<table class="seccion">
				<tr>
					<td class="label"><s:text name="labelClave" /></td>
					<td class="labelDerecho"><s:property value="model.clave" /> <s:fielderror
							fieldName="model.clave" cssClass="error" theme="jquery" /></td>
					<s:hidden value="%{model.clave}" name="model.clave" />
				</tr>
				<tr>
					<td class="label obligatorio"><s:text name="labelNumero" /></td>
					<td><s:textfield name="model.numero" maxlength="20"
							cssErrorClass="input-error" cssClass="inputFormulario ui-widget" />
						<s:fielderror fieldName="model.numero" cssClass="error"
							theme="jquery" /></td>
				</tr>
				<tr>
					<td class="label obligatorio"><s:text name="labelNombre" /></td>
					<td><s:textfield name="model.nombre" maxlength="200"
							cssErrorClass="input-error" cssClass="inputFormulario ui-widget" />
						<s:fielderror fieldName="model.nombre" cssClass="error"
							theme="jquery" /></td>
				</tr>
				<tr>
					<td class="label obligatorio"><s:text name="labelDescripcion" /></td>
					<td><s:textarea rows="5" name="model.descripcion"
							cssClass="inputFormularioExtraGrande ui-widget" maxlength="999"
							cssErrorClass="input-error"></s:textarea> <s:fielderror
							fieldName="model.descripcion" cssClass="error" theme="jquery" /></td>
				</tr>
				<tr id="fila-pantalla">
					<td class="label"><s:text name="labelImagen" /></td>
					<td><s:file id="imagenPantalla" name="imagenPantalla"
							size="40" cssClass="inputFormulario ui-widget"
							cssErrorClass="input-error"
							onchange="mostrarPrevisualizacion(this, 'pantalla');"
							accept=".png" /> <s:fielderror fieldName="model.imagen"
							cssClass="error" theme="jquery" /></td>
				</tr>
			</table>
			<div class="fieldError-pantalla" >
			<s:fielderror fieldName="pantallaB64" cssClass="error" theme="jquery" />
			</div>
			<div class="marcoImagen" id="marco-pantalla" style="display: none;">
				
				<div class="btnEliminar">
					<a onclick="eliminarImagen('pantalla', 'imagenPantalla');"><img
						title="Eliminar"
						src="${pageContext.request.contextPath}/resources/images/icons/Eliminar.svg" /></a>
				</div>
				<center>
					<img id="pantalla" src="#" class="imagen" />
				</center>
				<div class="textoAyuda">Imagen seleccionada</div>
			</div>
			<br />
		</div>

		<br />
		<div align="center">
			<s:submit class="boton" value="Aceptar"/>

			<s:url var="urlGestionarPantallas"
				value="%{#pageContext.request.contextPath}/pantallas">
			</s:url>
			<input class="boton" type="button"
				onclick="location.href='${urlGestionarPantallas}'" value="Cancelar" />
		</div>
		
		<s:hidden id="src-pantalla" name="pantallaB64"
			value="%{pantallaB64}" />
	</s:form>
	

</body>
	</html>
</jsp:root>
