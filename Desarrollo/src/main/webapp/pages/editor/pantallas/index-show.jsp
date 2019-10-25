<?xml version="1.0" encoding="UTF-8" ?>

<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	xmlns:s="/struts-tags" xmlns:sj="/struts-jquery-tags">
	<jsp:directive.page language="java"
		contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
	<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Pantalla</title>
<![CDATA[
	<script type="text/javascript" charset="utf8" src="${pageContext.request.contextPath}/pages/editor/pantallas/js/index-show.js"></script>	
]]>

</head>
<body>
	<h1>Consultar Pantalla</h1>
	<h3><s:property value="model.nombre"/></h3>

	<s:actionmessage theme="jquery" />
	<s:actionerror theme="jquery" />
	<br />
		<div class="formulario">
			<div class="tituloFormulario">${blanks}</div>
			<div class="seccion">
				<h4><s:property value="model.nombre"/></h4>
				<p class="instrucciones"><s:property value="model.descripcion"/></p>
			</div>
			<div class="marcoImagen" id="marco-pantalla">
				<center>
					<s:url var="pantallaAction" value="%{#action.pantallaB64}"/>
					<img id="pantalla" src="${pantallaAction}" class="imagen" alt="pantalla"/>
				</center>
			</div>
		</div>
		
		
		<div class="formulario" id="seccion-acciones" >
			<div class="tituloFormulario">${blanks}</div>
			<div class="seccion" id="acciones">
				<h5><s:text name="labelAcciones" /></h5>
				<s:iterator value="listAcciones" var="accion">
				<table class="tablaConsulta">
						<tr>
							<td>
								<div  id="marco-accion${accion.id}">
										<s:url var="pantallaAccion" value="%{#accion.imagenB64}"/>
										<img src="${pantallaAccion}" id="accion${accion.id}" alt="accion"/>
								</div>
								<div class="descripcionAccion">
									<a name="accion-${accion.id}"><!-- accion --></a>
									<span class="labelIzq consulta"><s:property value="%{#accion.nombre}"/></span>
												<span class="ui-widget "> 
												${blanks} <s:property value="%{#accion.descripcion}"/>
												${blanks}
												</span>
									
								</div>
								<div>
										<span class="labelIzq consulta"><s:text name="labelTipoAccion" /></span>
										<span class="ui-widget "> 
													${blanks} <s:property value="#accion.nombreTipoAccion"/>
													${blanks}
										</span>
								</div>
								<div>
									<span class="labelIzq consulta"><s:text name="labelPantallaDestino" /></span>
									<span class="ui-widget "> 
											${blanks}
											<s:if test="#accion.idPantallaDestino == model.id">
												<s:text name="labelPantallaActual"/>
											</s:if>
											<s:else>
												 <a class="referencia"
													href='${pageContext.request.contextPath}/pantallas/${accion.idPantallaDestino}'>
														${accion.claveModuloPantallaDestino}
														${accion.numeroPantallaDestino} ${blanks}
														${accion.nombrePantallaDestino}</a>
											</s:else> 
									</span>
								</div>
							</td>
						</tr>
					</table>
				</s:iterator>
			</div>
		</div>

		<br />
		<div align="center">
			<s:url var="urlGestionarPantallas"
				value="%{#pageContext.request.contextPath}/pantallas">
			</s:url>
			<input class="boton" type="button"
				onclick="location.href='${urlGestionarPantallas}'" value="Regresar" />
				${blanks}
		</div>


</body>
	</html>
</jsp:root>
