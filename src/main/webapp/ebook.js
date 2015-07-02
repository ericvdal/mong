angular.module('ebookApp', ['ngResource'])
	.controller('EbookController', function() {
		
	})
	
	
app.factory("Post", function($resource) {
		return $resource("/mongo-ebook/getAll");
	});
	
	app.controller("name", function($scope, Post) {
		  Post.query(function(data) {
		    $scope.posts = data;
		  });
	});	