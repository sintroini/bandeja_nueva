function TasksController($scope, $http, $routeParams) {

	$scope.urlBase="http://" + document.location.hostname + ":"
	+ document.location.port + "/bandejaCuyum";
	$scope.urlRest=$scope.urlBase + "/rest/tasks";
	$scope.datos = null;
	$scope.query = getQueryParam(document.location.href);
	$scope.jsonToShow = null;
	var oTable = null;
		
	settingRow = function(){
		var action_form_buttons = $('#tasks_table').find('button[role*="action"]');

//		console.log("buttons",action_form_buttons);
		
		

	$.each(action_form_buttons,function(i,buttonEl){
		var button = $(buttonEl);
//			console.log("Boton encontrado:",button);
		if(button.attr("role")=="action_form"){
			button.on("click",function(evt){
				var rowIndex = oTable.fnGetPosition($(evt.target)
						.closest('tr').get(0));
				var record = oTable.fnGetData(rowIndex);
			
				window.open(record.taskFormUrl); 
			});
		}else if(button.attr("role")=="action_process"){
			button.on("click",function(evt){
				var rowIndex = oTable.fnGetPosition($(evt.target)
						.closest('tr').get(0));
				var record = oTable.fnGetData(rowIndex);
				
				window.location.href = $scope.urlBase +"/#/process?instance="+record.processInstance;
			});
		}else if(button.attr("role")=="action_claim"){
			button.on("click",function(evt){
				var rowIndex = oTable.fnGetPosition($(evt.target)
						.closest('tr').get(0));
				var record = oTable.fnGetData(rowIndex);
				console.info("Id tarea"+record.id);
				
				$.ajax({
				  type: "POST",
				  url:  $scope.urlBase + "/rest/tasks/claim/"+record.id,
				  success: function(data){
					  console.log("RESULT de CLAIM: ",data);
					  window.location.reload(true);
				  },
				  failure:function(a){
					  console.log("FAILURE ",a);
				  }
				});
			});
		}else if(button.attr("role")=="action_release"){
			button.on("click",function(evt){
				var rowIndex = oTable.fnGetPosition($(evt.target)
						.closest('tr').get(0));
				var record = oTable.fnGetData(rowIndex);
				console.info(record.id);
				$.ajax({
				  type: "POST",
				  url:  $scope.urlBase + "/rest/tasks/release/"+record.id,
				  success: function(data){
					  console.log("RESULT de RELEASE: ",data);
					  window.location.reload(true);
				  },
				  failure:function(a){
					  console.log("FAILURE ",a);
				  }
				});
			});
		}else if(button.attr("role")=="action_data"){
			
			button.on("click",function(evt){
				var rowIndex = oTable.fnGetPosition($(evt.target)
						.closest('tr').get(0));
				var record = oTable.fnGetData(rowIndex);
				console.info(record.id);
								
				$.ajax({
				  type: "GET",
				  url:  $scope.urlBase +"/rest/process/instance/"+record.processInstance+"/variables",
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
					  if(data.length>0)
						  $(".modal-body").html(data);
					  else
						  $(".modal-body").html("<h5>No hay variables para mostrar</h5>");
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
		
	
//			console.log("taskId",taskId);
	});
	};

	$scope.armarTabla = function() {

		oTable = $('#tasks_table').dataTable({
				"bProcesing" : true,
		 	 	"bDestroy": true,
		 	 	"bLenthChange" : false,				
				"sAjaxSource" : $scope.urlRest +"?"+ $scope.query,
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
					"mData" : "name"
				}, {
					"mData" : "status"
				}, {
					"mData" : "createdDate",
					"mRender" : function(data, type, row) {	
						moment.lang('en');
						return moment(data).format(
						'MMM D HH:mm:ss YYYY');
					}
				}, {
					"mData" : function (oObj)                              
		                  {
								var a = "";
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
								
								var own = getUrlVars()["own"];
								
								if(own!= undefined && own==true || oObj.ownerId!=undefined) {
									a = a + "<button type='button' class='btn btn-primary btn-xs' role='action_release' data-toggle='tooltip' data-placement='top' title='Liberar'><span class='glyphicon glyphicon-open'></span></button> "+
									"<button type='button' class='btn btn-primary btn-xs' role='action_form' data-toggle='tooltip' data-placement='top' title='Abrir Formulario'><span class='glyphicon glyphicon-search'></span></button> ";
								}else
									a = a + "<button type='button' class='btn btn-primary btn-xs' role='action_claim' data-toggle='tooltip' data-placement='top' title='Reclamar'><span class='glyphicon glyphicon-save'></span></button> ";
							
								a = a+ "<button type='button' class='btn btn-primary btn-xs' role='action_process' data-toggle='tooltip' data-placement='top' title='Proceso'><span class='glyphicon glyphicon-cog'></span></button> ";
							
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
		var queryUrl = Url.replace(/.*\?(.*?)/,"$1");
		
		return queryUrl;
    }
	
	function getUrlVars()
	{
	    var vars = [], hash;
	    var hashes = window.location.href.slice(window.location.href.indexOf('?') + 1).split('&');
	    for(var i = 0; i < hashes.length; i++)
	    {
	        hash = hashes[i].split('=');
	        vars.push(hash[0]);
	        vars[hash[0]] = hash[1];
	    }
	    return vars;
	}
}
