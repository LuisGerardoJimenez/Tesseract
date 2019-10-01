var contextPath = "prisma";
$(document).ready(function() {
	contextPath = $("#rutaContexto").val();
	$('table.tablaGestion').DataTable();
	try {
		token.cargarListasToken();
	} catch (err) {
		alert("No existen elementos para referenciar con el token.");
	}
	
} );

/*
 * Agrega un mensaje en la pantalla
 */
function agregarMensaje(mensaje) {
	alert(mensaje);
};

