function DisplayController($scope, $http, $routeParams) {
	
	$scope.urlBase="http://" + document.location.hostname + ":"
	+ document.location.port + "/bandejaCuyum";
	
	
	$scope.armarTabla = function() {
		var instanceId = getUrlVars()["instanceId"];
		var urlRest = $scope.urlBase +"/rest/process/variables/"+instanceId;
		
		oTable = $('#vars_table').dataTable({
				"bProcesing" : true,
		 	 	"bDestroy": true,
		 	 	"bLenthChange" : false,				
				"sAjaxSource" : urlRest,
				"sAjaxDataProp" : "",
				"fnServerData" : function(sSource, aoData, fnCallback) {
		 	 		$.getJSON( sSource, aoData, function (json) {
		                fnCallback(json);
		 		});
		 	 	},
		 	 	"oLanguage" : {
					"sLengthMenu" : "Cantidad de _MENU_ ",
					"sInfo": "Mostrando _START_ a _END_ de _TOTAL_ registros",
					"sInfoEmpty": "Mostrando 0 a 0 de 0 registros",
					"sEmptyTable" : "No hay datos disponibles",
					"sSearch": "Buscar:",
					"oPaginate": {
				        "sPrevious": "Anterior",
				        "sNext": "Siguiente",
				      }
				},
		 	 	"fnDrawCallback": function( oSettings ) {
//					settingRow();
			    },
		        
		        "aoColumns" : [{
					"mData" : "name",
					
				}, {
					"mData" : function (oObj){
						
						var a = "<input autocomplete='off' type='text' value='"+oObj.value+"' name='"+oObj.name+"' data-constraint='string-length .&gt;=0 and string-length .&lt;=255' data-constraint-msg='Ingrese valor mayor o igual a 0 y menor o igual a 255' data-type-xml='string'>";
						$scope.nombreVar = oObj.value;
						console.info(oObj.name +":"+ oObj.value);
						return a;
					}
				}
				]
			  });
		};
		
	$scope.armarTabla();
	
	$("#nav-logout").html("<a id='blogout'><span class='glyphicon glyphicon-log-out' style='font-size:18px; top:15px; '> Salir</span></a>");
	
	$scope.cancel =  function() {
		console.info("cancelando modificacion");
		window.location.href = $scope.urlBase +"/#/home";
	};
	
	$scope.complete =  function() {
		console.info("Completando tarea");
		
		var taskId = getUrlVars()["taskId"];
		
		$.ajax({
			  type: "POST",
			  url:  $scope.urlBase + "/rest/tasks/"+taskId+"/complete",
			  success: function(data){
				  console.log("RESULT de COMPLETE: ",data);
				  window.location.reload(true);
			  },
			  failure:function(a){
				  console.log("FAILURE ",a);
			  }
			});
	};
	
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
}