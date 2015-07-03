'use strict';

angular.module('ebookApp.controllers', []).
  controller('MyCtrl1', ['$scope', 'EbookService', function($scope, EbookService) {
    console.log('control 1');
    // Instantiate an object to store your scope data in (Best Practices)
    $scope.data = {};
    
    EbookService.query(function(response) {
      // Assign the response INSIDE the callback
      $scope.data.ebooks = response;
      console.log($scope.data.ebooks.length);
    });
  }])
  .controller('MyCtrl2', [function() {
    console.log('control 2');
  }]);