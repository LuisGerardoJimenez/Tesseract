$(document).ready(function() {
	$('#gestion').DataTable();
	contextPath = $("#rutaContexto").val();

} );

function confirmarEliminacion(curp) {
	$('#confirmarEliminacionDialog').dialog('close');
	rutaVerificarReferencias = contextPath + '/personal!destroy';
	$.ajax({
		dataType : 'json',
		url : rutaVerificarReferencias,
		type: "POST",
		data : {
			idSel : curp
		},
		success : function(data) {
			alert("");//mostrarMensajeEliminacion(data, curp);
		},
		error : function(err) {
			alert("Ha ocurrido un error.");
			console.log("AJAX error in request: " + JSON.stringify(err, null, 2));
		}
	});
}

function cancelarConfirmarEliminacion() {
	$('#confirmarEliminacionDialog').dialog('close');
}

function verificarEliminacion(curp) {
	
	document.getElementById("btnConfirmarEliminacion").onclick = function(){ confirmarEliminacion(curp);};
	$('#confirmarEliminacionDialog').dialog('open');
	
	
}

function cerrarMensajeReferencias() {
	$('#mensajeReferenciasDialog').dialog('close');
}