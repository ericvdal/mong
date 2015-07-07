'use strict';

angular.module('ebookApp.controllers', [])
  .controller('MyCtrl1', ['$scope', 'AllEbooks', 'AllCategory', function($scope, AllEbooks, AllCategory) {
    console.log('control 1');
    // Instantiate an object to store your scope data in (Best Practices)
    $scope.data = {};
    
    AllEbooks.query(function(response) {
      // Assign the response INSIDE the callback
      $scope.data.ebooks = response;
      console.log($scope.data.ebooks.length);
    });

   AllCategory.query(function(response) {
        // Assign the response INSIDE the callback
        $scope.data.categories = response;
        console
    });

}])

 .controller('ebookController', ['$scope', function($scope) {
    console.log('control ebook');
    // Instantiate an object to store your scope data in (Best Practices)
 //   $scope.img = $scope.data.ebook.image;
 
    
    $scope.showImageBook = function(ebook) {
    	$scope.img = ebook.Image;
    }
    
    $scope.hideImageBook = function(ebook) {
    	$scope.img = "";
    }

 }])
    

  /*
  .controller('MyCtrl2', ['$scope','EbooksByCategory', 'AllCategory', function($scope, EbooksByCategory, AllCategory) {
    console.log('control 2');
    
    
  }])

  */
/*
  .controller('FilterByCategory', function ($scope, $http) {
      $scope.selectedCategory = null;
      $scope.categories = [];

      $http({
              method: 'GET',
              url: '/Admin/GetTestAccounts',
              data: { applicationId: 3 }
          }).success(function (result) {
          $scope.categories = result;
      });
  })
  */
  .controller('MyCtrl2', [function() {
    console.log('control 2');
  }]);

/*
	function FilterByCategory($scope){
		
		$scope.filterByCategory = function(category){
	//		$scope.filtercategoy.
		};
		
	}
 */
/*
angular.module('test', []).controller('DemoCtrl', function ($scope, $http) {
    $scope.selectedTestAccount = null;
    $scope.testAccounts = [];

    $http({
            method: 'GET',
            url: '/Admin/GetTestAccounts',
            data: { applicationId: 3 }
        }).success(function (result) {
        $scope.testAccounts = result;
    });
});
*/
/*
 .factory('EbooksByCategory', function($resource){
    return $resource('http://localhost:8080/mong2/getall', {})
  })
  
  .factory('AllCategory', function($resource){
    return $resource('http://localhost:8080/mong2/category_list', {})
  })  
  
*/