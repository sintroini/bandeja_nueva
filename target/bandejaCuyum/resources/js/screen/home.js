$(document).ready(function() {
	console.log("Ejecutando Javascript de pantalla de HOME...");
	$("#menu").addClass("mainnav-menu");

	//barra de notificaciones
	$("#notification_bar").addClass("nav navbar-nav noticebar navbar-right");
	$("#icon_bell").addClass("fa fa-bell");
	$("#icon_envelope").addClass("fa fa-envelope");
	$("#icon_triangle").addClass("fa fa-exclamation-triangle");
	$("#notification_bar_notifications").addClass("dropdown-menu noticebar-menu noticebar-hoverable");
	$("#notification_bar_messages").addClass("dropdown-menu noticebar-menu noticebar-hoverable");
	$("#notification_bar_alerts").addClass("dropdown-menu noticebar-menu noticebar-hoverable");
	//avatar user
	$("#image_user").addClass("navbar-profile-avatar");
	$("#label_user").addClass("navbar-profile-label");
	$("#icon_user").addClass("fa fa-caret-down");	
	$("#header_user_icon").addClass("glyphicon glyphicon-user");

	$("#home_content_title").addClass("portlet-title");
	
	$("#load").on('click', function() {
		$("#modal").addClass("modal-styled");
		$(".modal-header").html("<h4 class='modal-title'>Subir formulario</h4>");
		$(".modal-body").html("<input type='file' id='fileupload' name='files'><div id='files' class='files'></div>");
		$(".modal-footer").html("<div id='progress' class='progress progress-striped active'><div class='progress-bar progress-bar-success'></div></div><button type='button' class='btn btn-default' data-dismiss='modal'>Cerrar</button>"); 
		$("#modal").modal("show");
		//------Modularizar este metodo en un plugin------
		
		    var url = ctx+"/rest/file/upload";
		    console.log("ejecutando function de upload, luego presionar boton");
		    $('#fileupload').fileupload({
		    	type: "POST",
		    	url: url,
		        dataType: 'multipart/form-data',
		        acceptFileTypes: /(\.|\/)(bpmn2)$/i,
		        maxFileSize: 5000000,//5 mb
		        
		        done: function (e, data) {
		            $.each(data.result.files, function (index, file) {
		                $('<p/>').text(file.name).appendTo('#files');
		            });
		        },
		        progressall: function (e, data) {
		            var progress = parseInt(data.loaded / data.total * 100, 10);
		            $('#progress .progress-bar').css(
		                'width',
		                progress + '%'
		            );
		        }
		    }).prop('disabled', !$.support.fileInput)
		        .parent().addClass($.support.fileInput ? undefined : 'disabled');
		});
	

	});

console.log("Javascript de pantalla HOME cargado.");
