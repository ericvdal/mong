var ebookService = angular.module('ebookServices', ['ngResource']);

phonecatServices.factory('Ebook', ['$resource',
  function($resource){
    return $resource('mongo-ebook/getAll', {}, {
      query: {method:'GET', isArray:true}
    });
  }]);