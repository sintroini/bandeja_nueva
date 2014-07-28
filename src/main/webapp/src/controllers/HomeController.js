

function HomeController($scope, $http, $routeParams) {

	$scope.urlBase="http://" + document.location.hostname + ":"
	+ document.location.port + "/bandejaCuyum";
	$scope.urlLogin=$scope.urlBase + "/rest/session/login";
	$scope.urlLogout=$scope.urlBase + "/rest/session/logout";

	$scope.datos = null;
	$scope.query = "";
	$scope.user = null;
	$scope.password = null;
	
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
	
}