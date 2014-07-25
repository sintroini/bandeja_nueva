

function LoginController($scope, $http, $routeParams) {

	$scope.urlBase="http://" + document.location.hostname + ":"
	+ document.location.port + "/bandeja";
	$scope.urlLogin=$scope.urlBase + "/rest/session/login";
	$scope.urlLogout=$scope.urlBase + "/rest/session/logout";

	$scope.datos = null;
	$scope.query = "";
	$scope.user = null;
	$scope.password = null;
	$(".navbar-default").text("");

	
	$scope.login = function() {
		$http({
			method: 'POST', 
			url: $scope.urlLogin, 
			data : $.param({
				"username" : $scope.username,
				"password" : $scope.password
			}),
			headers : {
				'Content-Type' : 'application/x-www-form-urlencoded; charset=UTF-8'
			},
		}).
			success(function(status, data) {
				window.location.href = $scope.urlBase +"/#/home";
				
	    	  console.info(status);
	        }).
	        error(function(status) {
	          $scope.status = status.mensaje;
	      });
		
	};
	
	$scope.summit =  function() {
		$scope.login();
	};
	
	$scope.logout = function(){		
		$http({
					method : 'GET',
					url : $scope.urlLogout,
					headers : {
						'Authorization' : ""
					}
				}).success(function(data, status, headers, config) {
				console.log("logout:"+$sharedProperties.getAppUrl());
				$.session.clear();
				window.location.href = $scope.urlBase +"/#/login";		
		}).error(function(data, status, headers, config) {
			alert("Error intentando llamar al servicio de desloguear:"+status);
		});
	};
	
}