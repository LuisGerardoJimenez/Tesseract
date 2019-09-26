var contextPath = "tesseract";

$(document).ready(function() {
	var contextPath = $("#rutaContexto").val();
	$('#tablaAccion').DataTable();
	$('pantalla').attr('src', "${pantallaAccion}");
});

function mostrarPrevisualizacion(inputFile, nombre) {
	var idImg = nombre.replace(/\s/g, "_");
	if (inputFile.files && inputFile.files[0]) {
        var reader = new FileReader();
        reader.readAsDataURL(inputFile.files[0])
        reader.onload = function (e) {
            $('#' + idImg).attr('src', reader.result);
        }
        document.getElementById("marco-" + idImg).style.display = '';
    }
}
