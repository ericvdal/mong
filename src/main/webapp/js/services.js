/*var ebookService = angular.module('ebookServices', ['ngResource']);

ebookService.factory('Ebook', ['$resource',
  function($resource){
    return $resource('mongo-ebook/getall', {}, {
      query: {method:'GET', isArray:true}
    });
  }]);
  */
  
  angular.module('ebookApp.services', ['ngResource'])
 
  .factory('AllEbooks', function($resource){
    return $resource('http://localhost:8080/mong2/getall', {})
  })
  
  .factory('EbooksByCategory', function($resource){
    return $resource('http://localhost:8080/mong2/getall', {})
  })
  
  .factory('AllCategory', function($resource){
    return $resource('http://localhost:8080/mong2/category_list', {})
  })  
  
  .value('version', '0.1');