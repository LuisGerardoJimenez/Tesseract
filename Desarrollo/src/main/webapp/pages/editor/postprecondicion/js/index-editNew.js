var contextPath = "tesseract";
$(document).ready(function() {
	contextPath = $("#rutaContexto").val();
	$('table.tablaGestion').DataTable();
	try {
		token.cargarListasToken();
	} catch (err) {
		alert("No existen elementos para referenciar con el token.");
	}
	ocultarTokens();
	
} );

function ocultarTokens() {
	$("div[id^='at-view']").each(function(index, value) {
		var id = $(this).attr('style', 'none');
	});
}
/*
 * Agrega un mensaje en la pantalla
 */
function agregarMensaje(mensaje) {
	alert(mensaje);
};



function prepararEnvio() {
	try {
		return true;
	} catch(err) {
		alert("Ha ocurrido un error.");
		return false;
	}
}

