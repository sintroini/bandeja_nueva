function ProcessController($scope, $http, $routeParams) {

	$scope.urlBase="http://" + document.location.hostname + ":"
	+ document.location.port + "/bandeja";
	$scope.urlRest=$scope.urlBase + "/rest/process";
	$scope.datos = null;
	$scope.processDefinition="processDefinition=proceso.proceso-prueba";
	$scope.query = getQueryParam(document.location.href);
	$scope.jsonToShow = null;
	var oTable = null;
	
	
	
	settingRow = function(){
		var action_form_buttons = $('#process_instance_table').find('button[role*="action"]');


	$.each(action_form_buttons,function(i,buttonEl){
		var button = $(buttonEl);
//			console.log("Boton encontrado:",button);
		if(button.attr("role")=="action_tasks"){
						
			button.on("click",function(evt){
				var rowIndex = oTable.fnGetPosition($(evt.target)
						.closest('tr').get(0));
				var record = oTable.fnGetData(rowIndex);
				console.info(record.id);
				document.location.href = $scope.urlBase +"/#/tasks?processInstanceId="+record.id; 
			});
		}else if(button.attr("role")=="action_my_tasks"){
			button.on("click",function(evt){
				var rowIndex = oTable.fnGetPosition($(evt.target)
						.closest('tr').get(0));
				var record = oTable.fnGetData(rowIndex);
				console.info(record.id);
				document.location.href = $scope.urlBase +"/#/tasks?own=true&processInstanceId="+record.id;
				
			});
		}else if(button.attr("role")=="action_data"){
			
			button.on("click",function(evt){
				var rowIndex = oTable.fnGetPosition($(evt.target)
						.closest('tr').get(0));
				var record = oTable.fnGetData(rowIndex);
				console.info(record.id);
								
				$.ajax({
				  type: "GET",
				  url:  $scope.urlBase +"/rest/process/instance/"+record.id+"/variables",
				  success: function(data){
					  console.log("success ",data);
					  $("#modal").addClass("modal-styled");
						$(".modal-header").html("<h4 class='modal-title'>Variables</h4>");
						$(".modal-body").html("<div id='imgId' class='text-center'> <img src='"+$scope.urlBase +"/resources/img/loading.gif'/><br><b>Obteniendo variables...</b></div>");
						
					  $(".modal-body").html("");
					  $(".modal-body").html(data);
					  $("#modal").modal("show");
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
								document.location.href = $scope.urlBase +"/rest"; 
							});
						}
						$(".modal-body").html(data);
						
					}
				  }
				});
			});
			
		}else if(button.attr("role")=="action_process_completed"){
			
			button.on("click",function(evt){
				var rowIndex = oTable.fnGetPosition($(evt.target)
						.closest('tr').get(0));
				var record = oTable.fnGetData(rowIndex);
				console.info(record.id);
				
				$("#modal").addClass("modal-styled");
				$(".modal-header").html("<h4 class='modal-title'>Variables</h4>");
				$(".modal-body").html("<div id='imgId' class='text-center'> <img src='"+ $scope.urlBase +"/resources/img/loading.gif'/><br><b>Obteniendo variables...</b></div>");
				$("#modal").modal("show");
				
				$.ajax({
				  type: "GET",
				  url:  $scope.urlBase +"/rest/process/instance/"+record.id+"/completed/variables",
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
								document.location.href = $scope.urlBase +"/rest"; 
							});
						}
						$(".modal-body").html(data);
						
					}
				  }
				});
			});
			
		}
	});
	};

	$scope.armarTabla = function() {
		oTable = $('#process_instance_table').dataTable({
				"bProcesing" : true,
		 	 	"bDestroy": true,
		 	 	"bLenthChange" : false,				
				"sAjaxSource" : $scope.urlRest + "?" + $scope.processDefinition + $scope.query,
				"sAjaxDataProp" : "",
				"fnServerData" : function(sSource, aoData, fnCallback) {
		 	 		$.getJSON( sSource, aoData, function (json) {
		                fnCallback(json);
		 		});
		 	 	},
		 	 	"fnDrawCallback": function( oSettings ) {
					settingRow();
			    },
		        
		        "aoColumns" : [{
					"mData" : "id"
				}, {
					"mData" : "description"
				}, {
					"mData" : "state",
					"mRender" : function(data, type, row) {	
							var state = null; 
							switch (data) {
							case 1:
								state = "Active";
								break;
							case 2:
								state = "Completed";
								break;
							case 3:
								state = "Aborted";
								break;
							case 4:
								state = "Suspended";
								break;
							default:
								state = "Undefined";
							}
							return state;
						}
				}, {
					"mData" : function (oObj)                              
		                  {	var a = "";
							switch (oObj.state) {
							
							case 1:
								a = "<button type='button' class='btn btn-secondary btn-xs' role='action_data' data-toggle='tooltip' data-placement='top'title='Variables'><span class='glyphicon glyphicon-list'></span></button> ";
								break;
							case 2:
								a = "<button type='button' class='btn btn-secondary btn-xs' role='action_data' data-toggle='tooltip' data-placement='top'title='Variables'><span class='glyphicon glyphicon-list'></span></button> ";
								break;
							case 3:
								a = "<button type='button' class='btn btn-secondary btn-xs' role='action_process_completed' data-toggle='tooltip' data-placement='top'title='Variables'><span class='glyphicon glyphicon-list'></span></button> ";
								break;
							case 4:
								a = "<button type='button' class='btn btn-secondary btn-xs' role='action_process_completed' data-toggle='tooltip' data-placement='top'title='Variables'><span class='glyphicon glyphicon-list'></span></button> ";
								break;
							default:
								a = "<button type='button' class='btn btn-secondary btn-xs' role='action_process_completed' data-toggle='tooltip' data-placement='top'title='Variables'><span class='glyphicon glyphicon-list'></span></button> ";
							}
						
							a = a + "<button type='button' class='btn btn-primary btn-xs' role='action_tasks' data-toggle='tooltip' data-placement='top'title='Tareas'><span class='glyphicon glyphicon-tasks'></span></button> "+
							"<button type='button' class='btn btn-primary btn-xs' role='action_my_tasks' data-toggle='tooltip' data-placement='top' title='Mis Tareas'> <span class='glyphicon glyphicon-ok'></span></button> ";
							
							return a;
		                  }
		           }
				]
			  });
		};

	$scope.armarTabla();	
	$("#nav-logout").html("<a id='blogout'><span class='glyphicon glyphicon-log-out' style='font-size:18px; top:15px; '> Salir</span></a>");
	
	$("#blogout").on('click',function(){		
		$http({
					method : 'GET',
					url : $scope.urlLogout,
					
				}).success(function(data, status, headers, config) {
				
				window.location.href = $scope.urlBase +"/#/login";		
		}).error(function(data, status, headers, config) {
			alert("Error intentando llamar al servicio de desloguear:"+status);
		});
	});
	function getQueryParam(url) 
    {
		var Url = url;
		var queryUrl="";
		var index= Url.indexOf("?");
		if(index>0){
			queryUrl = "&"+Url.substring(index+1);
		}
		return queryUrl;
    }
}