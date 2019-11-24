var contextPath = "prisma";
$(document)
.ready(
		function() {
			window.scrollTo(0, 0);
			contextPath = $("#rutaContexto").val();
			$('#gestion').DataTable().destroy();
			$('#gestion').DataTable({"lengthMenu": [[-1], ["Todos"]]});					
			var json = $("#jsonCasoUsoTabla").val();
			if (json !== "") {
				var parsedJson = JSON.parse(json);
				$.each(parsedJson, function(i, item) {
					var curp = item.curp;
					document.getElementById("checkbox-" + curp).checked = true;
				});
			}
		});

function agregarMensaje(mensaje) {
	alert(mensaje);
};

function prepararEnvio() {
	mostrarMensajeCargando();
	try {
		tablaToJson("tablaColaboradores");
		return true;
	} catch (err) {
		alert("Ha ocurrido un error." + err);
	}
}

function tablaToJson(idTable) {
	var table = $("#gestion").dataTable();
	var arregloCU = "";
	var seleccionado;
	
	for (var i = 0; i < table.fnSettings().fnRecordsTotal(); i++) {
		var cu = table.fnGetData(i, 0);
		var regex = /(\d+)/g;
		var id = cu.match(regex);
		seleccionado = document.getElementById("checkbox-"+id).checked;
		if (seleccionado == true) {
			arregloCU += id+",";
		}
	}
	arregloCU = arregloCU.substring(0, arregloCU.length - 1);
	document.getElementById("jsonCasoUsoTabla").value = arregloCU;
	
	return false;
}

function mostrarMensajeCargando() {
	$("#modal").css("display", "block");
	intervalStatus = setInterval(function() {verificarLoadStatus(); }, 1000);
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

function ocultarMensajeCargando() {
	$("#modal").css("display", "");
}

function selTipo(tipo){
	$("#extension").val(tipo);
}