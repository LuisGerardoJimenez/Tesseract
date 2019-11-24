var intervalStatus;

$(document).ready(function() {
	$('#gestion').DataTable();
	contextPath = $("#rutaContexto").val();
} );

function confirmarEliminacion(urlEliminar) {
	$('#confirmarEliminacionDialog').dialog('close');
	window.location.href = urlEliminar;
}

function cancelarConfirmarEliminacion() {
	$('#confirmarEliminacionDialog').dialog('close');
}

function verificarEliminacionElemento(idElemento) {
	var rutaVerificarReferencias = contextPath + '/actores!verificarElementosReferencias';
	$.ajax({
		dataType : 'json',
		url : rutaVerificarReferencias,
		type: "POST",
		data : {
			idSel : idElemento
		},
		success : function(data) {
			mostrarMensajeEliminacion(data, idElemento);
		},
		error : function(err) {
			alert("Ha ocurrido un error.");
			console.log("AJAX error in request: " + JSON.stringify(err, null, 2));
		}
	});
	
	return false;
	
}

function mostrarMensajeEliminacion(json, id) {
	var elementos = document.createElement("ul");
	var elementosReferencias = document.getElementById("elementosReferencias");
	var urlEliminar = contextPath + "/actores/" +id+ "?_method=delete";
	while (elementosReferencias.firstChild) {
		elementosReferencias.removeChild(elementosReferencias.firstChild);
	}
	if (json != "") {
		$
				.each(
						json,
						function(i, item) {
							var elemento = document.createElement("li");
							elemento.appendChild(document.createTextNode(item));
							elementos.appendChild(elemento);
						});
		document.getElementById("elementosReferencias").appendChild(elementos);
		
		$('#mensajeReferenciasDialog').dialog('open');
	} else {	
		document.getElementById("btnConfirmarEliminacion").onclick = function(){ confirmarEliminacion(urlEliminar);};
		$('#confirmarEliminacionDialog').dialog('open');
	}
}
function cerrarMensajeReferencias() {
	$('#mensajeReferenciasDialog').dialog('close');
}


function descargarPDF(idElemento, tipoExtension) {
	mostrarMensajeCargando();
	var rutadescargarPDF = contextPath + '/proyectos!descargarDocumento';
	
	$.ajax({
		dataType : 'json',
		url : rutadescargarPDF,
		type : "POST",
		data : {
			idSel : idElemento,
			extension: tipoExtension
		},
		success : function(data) {
			ocultarMensajeCargando();
			clearInterval(intervalStatus);
			return data;
		},
		error : function(err) {
			ocultarMensajeCargando();
			alert("Ha ocurrido un error.");
			console.log("AJAX error in request: " + JSON.stringify(err, null, 2));
			return false;
		}
	});
}

function mostrarMensajeCargando() {
	$("#modal").css("display", "block");
	intervalStatus = setInterval(function() {verificarLoadStatus(); }, 1000);
}

function ocultarMensajeCargando() {
	$("#modal").css("display", "");
}

function verificarLoadStatus(){
	var rutadescargarPDF = contextPath + '/proyectos!verificarLoadStatus';
	$.ajax({
		url : rutadescargarPDF,
		type: "POST",
		data : {
		},
		success: function(data){
			if(data == "ocultar"){
				ocultarMensajeCargando();
				clearInterval(intervalStatus);
			}
	    },
		error : function(err) {
			console.log("AJAX error in request: " + JSON.stringify(err, null, 2));
		}
	});
}