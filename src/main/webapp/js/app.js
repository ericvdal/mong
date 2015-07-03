// Declare app level module which depends on filters, and services
angular.module('ebookApp', ['ebookApp.filters', 'ebookApp.services', 'ebookApp.directives', 'ebookApp.controllers', 'ngRoute']).
  config(['$routeProvider', function($routeProvider) {
    $routeProvider.when('/view1', {templateUrl: 'partials/partial1.html', controller: 'MyCtrl1'});
    $routeProvider.when('/view2', {templateUrl: 'partials/partial2.html', controller: 'MyCtrl2'});
    $routeProvider.otherwise({redirectTo: '/view1'});
  }])