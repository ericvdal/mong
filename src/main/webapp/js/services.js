/*var ebookService = angular.module('ebookServices', ['ngResource']);

ebookService.factory('Ebook', ['$resource',
  function($resource){
    return $resource('mongo-ebook/getall', {}, {
      query: {method:'GET', isArray:true}
    });
  }]);
  */
  
  angular.module('ebookApp.services', ['ngResource'])
  .factory('EbookService', function($resource){
    return $resource('http://localhost:8080/mong2/getall', {})
  })
  .value('version', '0.1');
  