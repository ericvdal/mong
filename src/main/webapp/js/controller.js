'use strict';

angular.module('ebookApp.controllers', [])
  .controller('MyCtrl1', ['$scope', 'AllEbooks', 'AllCategory', 'EbooksByCategory', function($scope, AllEbooks, AllCategory, EbooksByCategory) {
    console.log('control 1');
    // Instantiate an object to store your scope data in (Best Practices)
    $scope.data = {};
    /*
    AllEbooks.query(function(response) {
      // Assign the response INSIDE the callback
      $scope.data.ebooks = response;
      console.log($scope.data.ebooks.length);
    });
*/
   AllCategory.query(function(response) {
        // Assign the response INSIDE the callback
        $scope.data.categories = response;
        
    });

   $scope.updateEbook = function(category){
    console.log('category = ' + category);
      EbooksByCategory.query({category:category},function(response){
        $scope.data.ebooks = response;
      });
   }

}])

 .controller('ebookController', ['$scope', function($scope) {
    console.log('control ebook');
 
    $scope.showImageBook = function(ebook) {
      $scope.img = ebook.Image;
    }
    
    $scope.hideImageBook = function(ebook) {
      $scope.img = "";
    }

 }])
    
  .controller('MyCtrl2', [function() {
    console.log('control 2');
  }]);

