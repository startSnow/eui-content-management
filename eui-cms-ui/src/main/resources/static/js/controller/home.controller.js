app
		.config(
				function($httpProvider) {
					$httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';
				}).controller(
				"HomePageController",
				function($scope, $http, $location, translationService) {
					$http.get('/v1/user/locale').success(
							function(data) {
								console.log("Locale retreive successfully"
										+ data.locale);
								$scope.username=data.userName;
								translationService.getTranslation($scope,
										data.locale);
							}).error(function() {
						console.log("home.controller[Locale retreive failed.]")
					});

				});