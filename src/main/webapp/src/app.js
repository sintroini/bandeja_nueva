angular.module('app', []).
 
  //definimos las rutas de la 'app'
  config(['$routeProvider', function($routes) {
 
  
  $routes.
  
      when('/login', {
          templateUrl: 'src/views/login.html',
          controller: LoginController
          }).
          when('/process', {
		      templateUrl: 'src/views/process_instances.html',
		      controller: ProcessController
          }).
          when('/tasks', {
		      templateUrl: 'src/views/tasks.html',
		      controller: TasksController
          }).
          when('/home', {
		      templateUrl: 'src/views/home.html',
		      controller: HomeController
          }).
      //cualquier ruta no definida
      otherwise({
          redirectTo: '/login'});
 
}]);