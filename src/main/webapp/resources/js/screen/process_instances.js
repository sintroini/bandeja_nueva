$(document).ready(function() {
	console.log("Ejecutando Javascript de pantalla de PROCESS INSTANCE...");
	$("#instances_table_title").addClass("portlet-title");

	var instance_table = $("#process_instance_table");
	instance_table.dataTable();

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

	//

	var action_form_buttons = instance_table.find('button[role*="action"]');

//		console.log("buttons",action_form_buttons);

	$.each(action_form_buttons,function(i,buttonEl){
		var button = $(buttonEl);
//			console.log("Boton encontrado:",button);
		if(button.attr("role")=="action_tasks"){
			var instanceId = button.parents("td").attr("instance_id");
			button.on("click",function(){
				document.location.href = ctx+"/rest/tasks?processInstanceId="+instanceId; 
			});
		}else if(button.attr("role")=="action_my_tasks"){
			var instanceId = button.parents("td").attr("instance_id");
			button.on("click",function(){
				document.location.href = ctx+"/rest/tasks?own=true&processInstanceId="+instanceId;
				
			});
		}else if(button.attr("role")=="action_data"){
			var instanceId = button.parents("td").attr("instance_id");
			
			button.on("click",function(){
				
				$("#modal").addClass("modal-styled");
				$(".modal-header").html("<h4 class='modal-title'>Variables</h4>");
				$(".modal-body").html("<div id='imgId' class='text-center'> <img src='"+ctx+"/resources/img/loading.gif'/><br><b>Obteniendo variables...</b></div>");
				$("#modal").modal("show");
				
				$.ajax({
				  type: "GET",
				  url:  ctx+"/rest/process/blocks/instance/"+instanceId+"/variables",
				  success: function(data){
					  console.log("success ",data);
					  
					  $(".modal-body").html("");
					  $(".modal-body").html(data);
				  },
				  failure:function(a){
					  console.log("FAILURE ",a);
				  },
				  error:function(a){
					  
					  
					
					if(a.getResponseHeader('error')=="401"){
						$(".modal-header").html("<h4 class='modal-title'>Acceso no autorizado</h4>");
						$(".modal-footer").html("<button id = 'bottonAuth' type='button' class='btn btn-primary' role='action_redirect'data-dismiss='modal'>Aceptar</button>");
						
						data = "<div> Su sesión ha caducado - Ingrese nuevamente</div>";
						
						if($("#bottonAuth").attr("role")=="action_redirect"){
							$("#bottonAuth").on("click",function(){
								document.location.href = ctx+"/rest"; 
							});
						}
						$(".modal-body").html(data);
						
					}
				  }
				});
			});
			
		}else if(button.attr("role")=="action_process_completed"){
			var instanceId = button.parents("td").attr("instance_id");
			
			button.on("click",function(){
				
				$("#modal").addClass("modal-styled");
				$(".modal-header").html("<h4 class='modal-title'>Variables</h4>");
				$(".modal-body").html("<div id='imgId' class='text-center'> <img src='"+ctx+"/resources/img/loading.gif'/><br><b>Obteniendo variables...</b></div>");
				$("#modal").modal("show");
				
				$.ajax({
				  type: "GET",
				  url:  ctx+"/rest/process/blocks/instance/"+instanceId+"/completed/variables",
				  success: function(data){
					  console.log("success ",data);
					  
					  $(".modal-body").html("");
					  $(".modal-body").html(data);
				  },
				  failure:function(a){
					  console.log("FAILURE ",a);
				  },
				  error:function(a){
					  
					  
					
					if(a.getResponseHeader('error')=="401"){
						$(".modal-header").html("<h4 class='modal-title'>Acceso no autorizado</h4>");
						$(".modal-footer").html("<button id = 'bottonAuth' type='button' class='btn btn-primary' role='action_redirect'data-dismiss='modal'>Aceptar</button>");
						
						data = "<div> Su sesión ha caducado - Ingrese nuevamente</div>";
						
						if($("#bottonAuth").attr("role")=="action_redirect"){
							$("#bottonAuth").on("click",function(){
								document.location.href = ctx+"/rest"; 
							});
						}
						$(".modal-body").html(data);
						
					}
				  }
				});
			});
			
		}else if(button.attr("role")=="action_diagram"){
			var instanceId = button.parents("td").attr("instance_id");
			
			button.on("click",function(){
				
				$("#modal").addClass("modal-styled");
				$(".modal-header").html("<h4 class='modal-title'>Diagrama de instancia "+instanceId+"</h4>");
				$(".modal-body").html("<div id='imgId' class='text-center'> <img src='"+ctx+"/resources/img/loading.gif'/><br><b>Obteniendo diagrama...</b></div>");
				$("#modal").modal("show");
				
				$.ajax({
					  type: "GET",
					  url:  ctx+"/rest/process/blocks/instance/"+instanceId+"/diagram",
					  success: function(data){
						  console.log("success ",data);
						  
						  $(".modal-body").html("");
						  $(".modal-body").html(data);
					  },
					  failure:function(a){
						  console.log("FAILURE ",a);
					  },
					  error:function(a){
						
						if(a.getResponseHeader('error')=="401"){
							$(".modal-header").html("<h4 class='modal-title'>Acceso no autorizado</h4>");
							$(".modal-footer").html("<button id = 'bottonAuth' type='button' class='btn btn-primary' role='action_redirect'data-dismiss='modal'>Aceptar</button>");
							
							data = "<div> Su sesión ha caducado - Ingrese nuevamente</div>";
							
							if($("#bottonAuth").attr("role")=="action_redirect"){
								$("#bottonAuth").on("click",function(){
									document.location.href = ctx+"/rest"; 
								});
							}
							$(".modal-body").html(data);
							
						}
					  }
				});
			});	
		}
//			console.log("taskId",taskId);
	});
	$("#load").on('click', function() {
		$("#modal").addClass("modal-styled");
		$(".modal-header").html("<h4 class='modal-title'>Subir formulario</h4>");
		$(".modal-body").html("<input type='file' id='fileupload' name='files'><div id='files' class='files'></div>");
		$(".modal-footer").html("<div id='progress' class='progress progress-striped active'><div class='progress-bar progress-bar-success'></div></div><button type='button' class='btn btn-default' data-dismiss='modal'>Cerrar</button>"); 
		$("#modal").modal("show");
		//------Modularizar este metodo en un plugin------
//		$(function () {
		    'use strict';
		    // Change this to the location of your server-side upload handler:
		    var url = ctx+"/rest/file/upload";
		    console.log("ejecutando function de upload, luego presionar boton");
		    $('#fileupload').fileupload({
		    	type: "POST",
		    	url: url,
		        dataType: 'multipart/form-data',
//		        acceptFileTypes: /(\.|\/)(bpmn2)$/i,
//		        maxFileSize: 5000000,//5 mb
		        
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
//	});
});
console.log("Javascript de pantalla PROCESS INSTANCE cargado.");
