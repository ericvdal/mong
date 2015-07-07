 
  angular.module('ebookApp.services', ['ngResource'])
 
  .factory('AllEbooks', function($resource){
    return $resource('http://localhost:8080/mong2/getall', {})
  })
  
  .factory('EbooksByCategory', function($resource){
    return $resource('http://localhost:8080/mong2/get_by_category/:category', {category : '@magCategory'})
  })
  
  .factory('AllCategory', function($resource){
    return $resource('http://localhost:8080/mong2/category_list', {})
  })  
  
  .value('version', '0.1');