<?xml version="1.0" encoding="UTF-8" ?>

<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	xmlns:s="/struts-tags" xmlns:sj="/struts-jquery-tags">
	<jsp:directive.page language="java"
		contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
	<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Regla de negocio</title>
</head>
<body>
	<h1>Consultar Regla de Negocio</h1>
	<h3>
		<s:property
			value="model.clave + ' ' + model.numero + ' ' + model.nombre"/>
	</h3>
		
	<h4>
	Tipo de Regla de Negocio:
	<s:property
			value="model.tiporeglanegocioNombre"  />
	</h4>
	<s:actionmessage theme="jquery" />
	<s:actionerror theme="jquery" />
	<br />
	<div class="formulario">
		<div class="tituloFormulario">${blanks}</div>
		<div class="seccion">
			<h4>
				<s:property
					value="model.clave + ' ' + model.numero + ' ' + model.nombre" />
			</h4>
			<p class="instrucciones">
				<s:property value="model.descripcion" />
			</p>
		</div>
	</div>
	<div class="formulario">
	<div class="tituloFormulario">${blanks}</div>	
		<div class="seccion">
		<table>
				<tr>
					<td class="definicion"><span class="labelIzq consulta"><s:text
								name="labelRedaccion" /></span> <span
						class="ui-widget inputFormulario"> ${blanks} <s:property
								value="model.redaccion" /></span></td>
				</tr>

				<s:if
					test="model.tiporeglanegocioNombre == 'Comparación de atributos'">
					<tr>
						<td class="ui-widget inputFormulario"><span class="ecuacion">
								<s:property value="model.atributo1Nombre" /> <s:property
									value="model.operadorSimbolo" /> <s:property
									value="model.atributo2Nombre" />
						</span></td>
					</tr>
				</s:if>
				<s:elseif test="model.tiporeglanegocioNombre == 'Formato correcto'">
					<tr>
						<td class="definicion"><span class="labelIzq consulta"><s:text
								name="labelExpReg" /></span> <span
						class="ui-widget inputFormulario"> ${blanks} <s:property
								value="model.expresionRegular" /></span></td>
					</tr>
					<tr>
						<td class="definicion"><span class="labelIzq consulta"><s:text
								name="labelAtributo" /></span> <span
						class="ui-widget inputFormulario"> ${blanks} <s:property
								value="model.atributoExpRegNombre" /></span></td>
					</tr>
				</s:elseif>
				<s:elseif
					test="model.tiporeglanegocioNombre == 'Unicidad de par\u00E1metros'">
					<tr>
						<td class="ui-widget inputFormulario"><span
							class="labelIzq consulta"><s:text name="labelEntidad" /></span>
							<span class="ui-widget inputFormulario"> ${blanks}
							<s:property value="model.atributoUnicidad.entidad.nombre" />
						</span></td>
					</tr>
					<tr>
						<td class="ui-widget inputFormulario"><span
							class="labelIzq consulta"><s:text name="labelAtributo" /></span>
							<span class="ui-widget inputFormulario"> ${blanks} <s:property
									value="model.atributoUnicidadNombre" />
						</span></td>
					</tr>
				</s:elseif>
			</table>
		</div>
	</div>
	<br />
	<div align="center">

		<input class="boton" type="button"
			onclick="location.href='${urlPrev}'" value="Regresar" />
	</div>
</body>
	</html>
</jsp:root>