$(document).ready(function() {
	$('#gestion').DataTable();
	var contextPath = $("#rutaContexto").val();
} );

function confirmarEliminacion(urlEliminar) {
	$('#confirmarEliminacionDialog').dialog('close');
	window.location.href = urlEliminar;
}

function cancelarConfirmarEliminacion() {
	$('#confirmarEliminacionDialog').dialog('close');
}

function mostrarMensajeEliminacion(id) {
	var urlEliminar = contextPath + "/pantallas/" + id + "!destroy";	
	document.getElementById("btnConfirmarEliminacion").onclick = function(){ confirmarEliminacion(urlEliminar);};
	$('#confirmarEliminacionDialog').dialog('open');
	return false;
}