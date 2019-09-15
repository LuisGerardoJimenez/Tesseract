<?xml version="1.0" encoding="UTF-8" ?>

<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	xmlns:s="/struts-tags" xmlns:sj="/struts-jquery-tags">
	<jsp:directive.page language="java"
		contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
	<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Acción</title>
<![CDATA[
	<script type="text/javascript" charset="utf8" src="${pageContext.request.contextPath}/resources/scripts/constructores.js"></script>
	<script type="text/javascript" charset="utf8" src="${pageContext.request.contextPath}/resources/scripts/validaciones.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/scripts/jquery.caret.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/scripts/jquery.atwho.js"></script>
	<script type="text/javascript" charset="utf8" src="${pageContext.request.contextPath}/pages/editor/acciones/js/index-editNew.js"></script>	
]]>

</head>
<body>

	<h1>Registrar Acción</h1>

	<s:actionmessage theme="jquery" />
	<s:actionerror theme="jquery" />
	<br />

	<p class="instrucciones">Ingrese la información solicitada.</p>
	<s:form autocomplete="off" id="frmAcciones" theme="simple"
		enctype="multipart/form-data"
		action="%{#pageContext.request.contextPath}/acciones" method="post">
		<div class="formulario">
			<div class="tituloFormulario">Información general de la Acción</div>
			<table class="seccion">
				<tr>
					<td class="label obligatorio"><s:text name="labelNombre" /></td>
					<td><s:textfield name="model.nombre" maxlength="50"
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
				<tr>
					<td class="label obligatorio"><s:text name="labelTipoAccion" /></td>
					<td><s:select name="model.idTipoAccion"
							list="listTipoAccion" headerValue="Seleccione"
							headerKey="-1" listKey="id" listValue="nombre"
							value="model.idTipoAccion" cssErrorClass="select-error"
							cssClass="inputFormulario ui-widget" /> <s:fielderror
							fieldName="model.idTipoAccion" cssClass="error"
							theme="jquery" /></td>
				</tr>
				<tr>
					<td class="label obligatorio"><s:text name="labelPantallaDestino" /></td>
					<td><s:select name="model.idPantallaDestino"
							list="listPantallas" headerValue="Seleccione"
							headerKey="-1" listKey="id" listValue="nombre"
							value="model.idPantallaDestino" cssErrorClass="select-error"
							cssClass="inputFormulario ui-widget" /> <s:fielderror
							fieldName="model.idPantallaDestino" cssClass="error"
							theme="jquery" /></td>
				</tr>
				<tr id="fila-pantalla">
					<td class="label"><s:text name="labelImagen" /></td>
					<td><s:file id="imagenPantalla" name="imagenPantalla"
							size="40" cssClass="inputFormulario ui-widget"
							cssErrorClass="input-error"
							onchange="mostrarPrevisualizacion(this, 'pantalla'); "
							accept=".png" /> <s:fielderror fieldName="imagenPantalla"
							cssClass="error" theme="jquery" /></td>
				</tr>
			</table>
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
			<s:submit class="boton" value="Aceptar" />

			<s:url var="urlGestionarAcciones"
				value="%{#pageContext.request.contextPath}/acciones">
			</s:url>
			<input class="boton" type="button"
				onclick="location.href='${urlGestionarAcciones}'" value="Cancelar" />
		</div>
	</s:form>


</body>
	</html>
</jsp:root>
