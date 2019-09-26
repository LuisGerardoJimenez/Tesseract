$(document).ready(function() {
	var contextPath = $("#rutaContexto").val();
	disablefromTipoDato();
} );



function disablefromTipoDato() {

	document.getElementById("trTextoAyudaFormato").style.display = 'none';
	var tipoDato = document.getElementById("model.idTipoDato");
	var tipoDatoTexto = tipoDato.options[tipoDato.selectedIndex].text;

//	TIPO_DATO_CADENA = 1;
//	TIPO_DATO_FLOTANTE = 2;
//	TIPO_DATO_ENTERO = 3;
//	TIPO_DATO_BOOLEANO = 4;
//	TIPO_DATO_FECHA = 5;
//	TIPO_DATO_ARCHIVO = 6;
//	TIPO_DATO_OTRO = 7;
	
	if (tipoDatoTexto == 'Cadena' || tipoDatoTexto == 'Flotante' || tipoDatoTexto == 'Entero') {
		var input = document.getElementById("model.otroTipoDato");
		if (input) {
		    input.value = null;
		}
		input = document.getElementById("model.formatoArchivo");
		if (input) {
		    input.value = null;
		}
		input = document.getElementById("model.tamanioArchivo");
		if (input) {
		    input.value = null;
		}
		input = document.getElementById("model.idUnidadTamanio");
		if (input) {
			input.selectedIndex = 0;
		}
		document.getElementById("trLongitud").style.display = '';
		document.getElementById("trOtro").style.display = 'none';
		document.getElementById("trFormatoArchivo").style.display = 'none';
		document.getElementById("trTamanioArchivo").style.display = 'none';
	} else {
		var input = document.getElementById("model.longitud");
		if (input) {
		    input.value = null;
		}
		document.getElementById("trLongitud").style.display = 'none';
		if (tipoDatoTexto == 'Booleano' || tipoDatoTexto == 'Fecha') {
			input = document.getElementById("model.otroTipoDato");
			if (input) {
			    input.value = null;
			}
			input = document.getElementById("model.formatoArchivo");
			if (input) {
			    input.value = null;
			}
			input = document.getElementById("model.tamanioArchivo");
			if (input) {
			    input.value = null;
			}
			input = document.getElementById("model.idUnidadTamanio");
			if (input) {
				input.selectedIndex = 0;
			}
			document.getElementById("trOtro").style.display = 'none';
			document.getElementById("trFormatoArchivo").style.display = 'none';
			document.getElementById("trTamanioArchivo").style.display = 'none';
			document.getElementById("trTextoAyudaFormato").style.display = 'none';
			
		} else if (tipoDatoTexto == 'Archivo') {
			input = document.getElementById("model.otroTipoDato");
			if (input) {
			    input.value = null;
			}
			document.getElementById("trOtro").style.display = 'none';
			document.getElementById("trFormatoArchivo").style.display = '';
			document.getElementById("trTamanioArchivo").style.display = '';
			document.getElementById("trTextoAyudaFormato").style.display = '';
			
		} else if (tipoDatoTexto == 'Otro') {
			input = document.getElementById("model.formatoArchivo");
			if (input) {
			    input.value = null;
			}
			input = document.getElementById("model.tamanioArchivo");
			if (input) {
			    input.value = null;
			}
			input = document.getElementById("model.idUnidadTamanio");
			if (input) {
				input.selectedIndex = 0;
			}
			document.getElementById("trOtro").style.display = '';
			document.getElementById("trFormatoArchivo").style.display = 'none';
			document.getElementById("trTamanioArchivo").style.display = 'none';
			document.getElementById("trTextoAyudaFormato").style.display = 'none'
			
		}
	} 
}