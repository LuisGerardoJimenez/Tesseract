var contextPath = "tesseract";

$(document).ready(function() {
	contextPath = $("#rutaContexto").val();
	$('#tablaAccion').DataTable();
	$('pantalla').attr('src', "${pantallaAction}");
});

