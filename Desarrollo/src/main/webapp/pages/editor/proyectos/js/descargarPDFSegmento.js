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
