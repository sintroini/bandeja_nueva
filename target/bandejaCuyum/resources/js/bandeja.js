/*****--------- APPLICATION-WIDE CONFIGURATION -----******/
$(document).ready(function() {
	console.log("Ejecutando Javascript de Bandeja...");

	/*****--------- ACTIVATE TOOLTIPS -----******/
	$("[data-toggle='tooltip']").tooltip();
	
	/*****--------- FOOTER  -----******/
	$("footer").addClass("footer")/*.fixposition()*/;
	$( window ).resize(function() {
		$("footer").fixposition();
	});
});

/*****--------- DATATABLES I18N -----******/
$.extend( $.fn.dataTable.defaults, {
    language: {
        url: ctx + '/resources/js/datatables.spanish.json'
    }
});

/*****--------- UPLOAD FILE  -----******/
//$("files").upload();


console.log("Javascript de Bandeja cargado.");


