var ebookService = angular.module('ebookServices', ['ngResource']);

phonecatServices.factory('Ebook', ['$resource',
  function($resource){
    return $resource('mongo-ebook/getall', {}, {
      query: {method:'GET', isArray:true}
    });
  }]);