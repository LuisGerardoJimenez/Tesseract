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
	<script type="text/javascript" charset="utf8" src="${pageContext.request.contextPath}/pages/editor/trayectorias/js/token.js"></script>
	<script type="text/javascript" charset="utf8" src="${pageContext.request.contextPath}/pages/editor/trayectorias/js/index-editNew.js"></script>
		
]]>

</head>
<body>
	<h1>Registrar Trayectoria</h1>
	
	<s:actionmessage theme="jquery" />
	<s:actionerror theme="jquery" />
	<br/>

	<p class="instrucciones">Los campos marcados con * son obligatorios</p>
	<s:form autocomplete="off" id="frmTrayectoria" theme="simple" action="%{#pageContext.request.contextPath}/trayectorias"
		method="post" onsubmit="return prepararEnvio();">
		<div class="formulario">
			<div class="tituloFormulario">Información general de la Trayectoria</div>
			<table class="seccion">
				<tr>
					<td class="label obligatorio"><s:text name="labelClave" /></td>
					<td><s:textfield name="model.clave" maxlength="5"
							cssErrorClass="input-error" cssClass="inputFormulario ui-widget" /> <s:fielderror
							fieldName="model.clave" cssClass="error" theme="jquery" /></td>
				</tr>
				<tr>
					<td class="label obligatorio"><s:text name="labelTipo" /></td>
					<td class="">
						<s:if test="existeTPrincipal">
								<s:select 
							list="listAlternativa"
							headerKey="-1" 
							headerValue="Seleccione"
							value="model.alternativa"
							disabled="true"
							cssErrorClass="select-error" onchange="cambiarElementosAlternativaPrincipal();" 
							cssClass="inputFormulario ui-widget" />
							<s:select 
								name="model.alternativa"
								list="listAlternativa"
								headerKey="-1" 
								headerValue="Seleccione"
								id="idAlternativaPrincipal"
								value="model.alternativa"
								style="display:none"
								cssErrorClass="select-error" onchange="cambiarElementosAlternativaPrincipal();" 
								cssClass="inputFormulario ui-widget" />
							<s:fielderror fieldName="model.alternativa" cssClass="error"
							theme="jquery" />
							<p id = "textoAyudaPA" class="textoAyuda">Solamente puede registrar Trayectorias alternativas, debido a que ya existe una Trayectoria principal.</p> 
						</s:if>
						<s:else>
							<s:select 
									name="model.alternativa"
									list="listAlternativa"
									headerKey="-1" 
									headerValue="Seleccione"
									id="idAlternativaPrincipal"
									value="model.alternativa"
									cssErrorClass="select-error" onchange="cambiarElementosAlternativaPrincipal();" 
									cssClass="inputFormulario ui-widget" />
								<s:fielderror fieldName="model.alternativa" cssClass="error"
								theme="jquery" />
						</s:else>
					</td>
				</tr>
				<tr id="filaCondicion" style="display: none;">
					<td class="label obligatorio"><s:text name="labelCondicion" /></td>
					<td><s:textarea rows="5" name="model.condicion" cssClass="inputFormularioExtraGrande ui-widget" id="model.idCondicion"
							value="" maxlength="999" cssErrorClass="input-error"></s:textarea> 
							<s:fielderror
							fieldName="model.condicion" cssClass="error"
							theme="jquery" /></td>
				</tr>
				<tr>
					<td class="label"><s:text name="labelFinCasoUso" /></td>
					<td><s:checkbox name="model.finCasoUso" id="model.finCasoUso"
							cssErrorClass="input-error"></s:checkbox> 
							<s:fielderror
							fieldName="model.finCasoUso" cssClass="error"
							theme="jquery" /></td>
				</tr>
			</table>
		</div>
		<!-- <div class="formulario">
			<div class="tituloFormulario">Pasos de la Trayectoria</div>
			<div class="seccion">
				<s:fielderror fieldName="model.pasos" cssClass="error" theme="jquery" />
				<table id="tablaPaso" class="tablaGestion" cellspacing="0" width="100%">
				<s:hidden name="numPasos" value="%{listPasos.length}" id="numPasos"/> 
					<thead>
						<tr>
							<th style="width: 5%;"><s:text name="colNumero"/></th>
							<th style="width: 55%;"><s:text name="colRedaccion"/></th>
							<th style="width: 0;"><s:text name=""/></th>
							<th style="width: 0;"><s:text name=""/></th>
							<th style="width: 0;"><s:text name=""/></th>
							<th style="width: 0;"><s:text name=""/></th>	
							<th style="width: 10%;"><s:text name="colAcciones"/></th>
						</tr>
					</thead>
					
				</table>
				<div align="center">
					<sj:a onclick="solicitarRegistroPaso();" button="true">Registrar</sj:a>
				</div>
			</div>
		</div> -->
		
		<br />
		<div align="center">
			<s:submit class="boton" value="Aceptar" />
			
			<s:url var="urlGestionarTrayectorias" value="%{#pageContext.request.contextPath}/trayectorias">
				<s:param name="idCU" value="%{idCU}"/>
			</s:url>
			<input class="boton" type="button"
				onclick="location.href='${urlGestionarTrayectorias}'"
				value="Cancelar" />			
		</div>
		<s:hidden id="jsonPasosTabla" name="jsonPasosTabla" value="%{jsonPasosTabla}"/>    	
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
	<!-- Booleano que indica si existen trayectorias -->
	<s:hidden id="existeTPrincipal" value="%{existeTPrincipal}"/>
	
</body>
	</html>
</jsp:root>