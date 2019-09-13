var contextPath = "tesseract";

$(document).ready(function() {
	contextPath = $("#rutaContexto").val();
	$('#tablaAccion').DataTable();
});

function mostrarPrevisualizacion(inputFile, nombre) {
	document.getElementById("fila-" + nombre).style.display = 'none';
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

function eliminarImagen(idImg, idFileUpload) {
	document.getElementById(idImg).src = "";
	document.getElementById("marco-" + idImg).style.display = 'none';
	document.getElementById(idFileUpload).value = null;
	document.getElementById("fila-" + idImg).style.display = '';
}

