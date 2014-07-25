function LoginController($scope, $http, $routeParams) {

	$scope.url = "http://" + document.location.hostname + ":"
			+ document.location.port + "/ticket-monster/rest/usuario/login";
	$scope.datos = null;
	$scope.query = "";
	$scope.user = null;
	$scope.password = null;

	$scope.login = function() {
		$http({method: 'POST', url: $scope.url + "/" + $scope.user, data: $scope.password, headers : {'Content-Type': 'text/plain'}}).
	      success(function(status) {
	    	  alert("Bienvenido a Ticket Monster: " + $scope.user + "Token: " + status);
	    	  console.info(status);
	        }).
	        error(function(status) {
	          $scope.status = status.mensaje;
	      });
		
	};
	
}