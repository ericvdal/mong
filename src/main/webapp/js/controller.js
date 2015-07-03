'use strict';

angular.module('ebookApp.controllers', [])
  .controller('MyCtrl1', ['$scope', 'AllEbooks', function($scope, AllEbooks) {
    console.log('control 1');
    // Instantiate an object to store your scope data in (Best Practices)
    $scope.data = {};
    
    AllEbooks.query(function(response) {
      // Assign the response INSIDE the callback
      $scope.data.ebooks = response;
      console.log($scope.data.ebooks.length);
    });
  }])
  
  .controller('MyCtrl2', ['$scope','EbooksByCategory', 'AllCategory', function($scope, EbooksByCategory, AllCategory) {
    console.log('control 2');
    
    
  }])

  
  .controller('MyCtrl3', [function() {
    console.log('control 2');
  }]);



	function FilterByCategory($scope){
		
		$scope.filterByCategory = function(){
			$scope.filtercategoy.
		};
		
	}
 
/*
 .factory('EbooksByCategory', function($resource){
    return $resource('http://localhost:8080/mong2/getall', {})
  })
  
  .factory('AllCategory', function($resource){
    return $resource('http://localhost:8080/mong2/category_list', {})
  })  
  
*/