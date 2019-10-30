<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	xmlns:s="/struts-tags" xmlns:sj="/struts-jquery-tags">
	<jsp:directive.page language="java"
		contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
	<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Casos de uso</title>
<![CDATA[
	<script type="text/javascript" charset="utf8" src="${pageContext.request.contextPath}/pages/editor/caso-uso/js/index.js"></script>	
]]> 
</head>
<body>
	<div class="modal" id="modal"><!-- Place at bottom of page --></div>
	<h1>Gestionar Casos de uso</h1>

	<s:actionmessage theme="jquery" />
	<s:actionerror theme="jquery" />

	<s:form autocomplete="off" theme="simple" onsubmit="return false;">
	<div class="form">
	 
		<table id="gestion" class="tablaGestion" cellspacing="0" width="100%"> 
			<thead>
				<tr>
					<th><!-- Número del Caso de uso --></th>
					<th><s:text name="colCasoUso"/></th>
					<th style="width: 20%;"><s:text name="colEstado"/></th>
					<th style="width: 20%;"><s:text name="colAcciones"/></th>
				</tr>
			</thead>
			<tbody>
			<s:iterator value="listCU" var="cu">
				<tr class="${'filaCU'}${cu.estadoElemento.id}">
					<td><s:property value="%{#cu.numero}"/></td>
					<td><s:property value="%{#cu.clave + #cu.numero + ' ' +#cu.nombre}"/></td>
					<td><s:property value="%{#cu.estadoElemento.nombre}"/></td>
					<td align="center">
							<!-- Consultar caso de uso -->		
							<s:url var="urlConsultar" value="%{#pageContext.request.contextPath}/cu/%{#cu.id}"/>
							<s:a href="%{urlConsultar}">
								<img id="" class="button" title="Consultar Caso de uso"
										src="${pageContext.request.contextPath}/resources/images/icons/Ver.svg" alt="Consultar Caso de uso"/>
							</s:a>	
							${blanks}
							<s:if test="%{#cu.estadoElemento.id == 1 || #cu.estadoElemento.id == 3}">
								<!-- Modificar caso de uso -->		
								<s:url var="urlEditar" value="%{#pageContext.request.contextPath}/caso-uso/%{#cu.id}/edit"/>			
								<s:a href="%{urlEditar}">
									<img id="" class="button" title="Modificar Caso de uso"
											src="${pageContext.request.contextPath}/resources/images/icons/Editar.svg" alt="Modificar Caso de uso"/>
								</s:a>	
								${blanks}
								<s:url var="urlPostprecondiciones" value="%{#pageContext.request.contextPath}/caso-uso!entrarPostprecondiciones?idSel=%{#cu.id}"/>			
								<s:a href="%{urlPostprecondiciones}">
									<img id="" class="button" title="Gestionar Precondiciones y Postcondiciones"
											src="${pageContext.request.contextPath}/resources/images/icons/Postprecondiciones.svg" alt="Gestionar Precondiciones y Postcondiciones"/>
								</s:a>
								
								${blanks}	
								<!-- Gestionar trayectorias -->			
								<s:url var="urlGestionarTrayectorias" value="%{#pageContext.request.contextPath}/caso-uso!entrarTrayectorias?idSel=%{#cu.id}"/>
								<s:a href="%{urlGestionarTrayectorias}">
									<img id="" class="button" title="Gestionar Trayectorias"
											src="${pageContext.request.contextPath}/resources/images/icons/T.svg" alt="Gestionar Trayectorias"/>
								</s:a>	
								${blanks}		
								<!-- Gestionar puntos de extensión -->				
								<s:url var="urlGestionarPuntosExtension" value="%{#pageContext.request.contextPath}/caso-uso!entrarExtensiones?idSel=%{#cu.id}"/>
								<s:a href="%{urlGestionarPuntosExtension}">
									<img id="" class="button" title="Gestionar Puntos de Extension"
											src="${pageContext.request.contextPath}/resources/images/icons/P.svg" alt="Gestionar Extensiones"/>
								</s:a>	
								${blanks}
															
								<!-- Terminar caso de uso -->			
								<s:a onclick="return verificarTerminarCasoUso(%{#cu.id});">
									<img id="" class="button" title="Terminar Caso de uso"
										src="${pageContext.request.contextPath}/resources/images/icons/Terminar.svg" alt="Terminar Caso de uso"/>
								</s:a>	
										
								${blanks}	
									
								<!-- Eliminar caso de uso -->
								<s:a href="#" onclick="return mostrarMensajeEliminacion('%{#cu.id}');">
									<img id="" class="button" title="Eliminar Caso de uso"
										src="${pageContext.request.contextPath}/resources/images/icons/Eliminar.svg" alt="Eliminar Caso de uso"/>
								</s:a>
								${blanks}
							</s:if>				
							<s:if test="%{#cu.estadoElemento.id == 2}">	
								<!-- Revisar caso de uso -->			
								<s:url var="urlRevisar" value="%{#pageContext.request.contextPath}/cu!prepararRevision?idSel=%{#cu.id}" method="post"/>
								<s:a href="%{urlRevisar}">
									<img id="" class="button" title="Revisar Caso de uso"
										src="${pageContext.request.contextPath}/resources/images/icons/Revisar.svg" alt="Revisar Caso de uso"/>
								</s:a>	
								${blanks}	
							</s:if>
							
							<s:if test="%{#cu.estadoElemento.id == 4 and #session.rol == 1}">	
							<!-- Liberar caso de uso -->			
								<s:url var="urlLiberar" value="%{#pageContext.request.contextPath}/cu!prepararLiberacion?idSel=%{#cu.id}" method="post"/>
								<s:a href="%{urlLiberar}">
									<img id="" class="button" title="Liberar Caso de uso"
										src="${pageContext.request.contextPath}/resources/images/icons/Liberar.svg" alt="Liberar Caso de uso"/>
								</s:a>	
								${blanks}		
							</s:if>
							<s:if test="%{(#cu.estadoElemento.id == 5 or #cu.estadoElemento.id == 6 or #cu.estadoElemento.id == 7) and #session.rol == 1}">
							<!-- Desbloquear caso de uso -->			
								<s:url var="urlDesbloquear" value="%{#pageContext.request.contextPath}/cu!prepararLiberacion?idSel=%{#cu.id}" method="post"/>
								<s:a href="%{urlDesbloquear}">
									<img id="" class="button" title="Solicitar correcciones del Caso de uso"
										src="${pageContext.request.contextPath}/resources/images/icons/SolicitarCorrecciones.svg" alt="Solicitar correcciones del Caso de uso"/>
								</s:a>	
								${blanks}
							</s:if>

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
			onclick="mostrarMensajeCargando();location.href='${pageContext.request.contextPath}/configuracion-casos-uso!generarReporteGeneral'">
			<s:text name="Visualizar reporte general"></s:text>
		</button>
		<button class="boton" 
			onclick="location.href='${pageContext.request.contextPath}/caso-uso/new'">
			<s:text name="Registrar"></s:text>
		</button>
	</div>
	
	<s:hidden name="pruebaGenerada" id="pruebaGenerada" value="%{pruebaGenerada}"/>
	<s:hidden name="pruebaGenerada2" id="pruebaGenerada2" value="%{pruebaGenerada2}"/>
	
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
	
	<!-- EMERGENTE TERMINAR -->
	<sj:dialog id="mensajeTerminarDialog" title="Confirmación" autoOpen="false"
		minHeight="100" minWidth="550" modal="true" draggable="true">
		<s:form autocomplete="off" id="frmConfirmarEliminacion" name="frmConfirmarEliminacionName" theme="simple">
				
				<div class="seccion">
					<div id = "mensajeRestricciones"><!--  --></div>
					<div id="restriccionesTermino"><!--  --></div>
				<br />
			</div>
			<div align="center">
				<input id="btnConfirmarTermino" type="button" value="Aceptar"/> 
				 <input type="button" onclick="cancelarConfirmarTermino();" value="Cancelar" />
			</div>
		</s:form>
	</sj:dialog>
</div>
</body>
</html>
</jsp:root>

