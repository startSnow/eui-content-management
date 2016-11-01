app
		.config(
				function($httpProvider) {
					$httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';
				}).controller("LogoutController", function( $scope ,$http, $location) { 
					
				});