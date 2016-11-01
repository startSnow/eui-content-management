app
		.config(
				function($httpProvider) {
					$httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';
				}).controller("UtilityController", function($scope, $http,translationService) {
			$scope.generateParameters = function() {
				$http.get('/v1/user/locale').success(
						function(data) {
							console.log("Locale retreive successfully"+ data.locale);
							translationService.getTranslation($scope, data.locale);
						}).error(function() {
							console.log("list.category.controller[Locale retreive failed.]")
						}
				);
				
				$http.get('/v1/utility/token').success(function(data) {
					$scope.query = data.query;
				}).error(function() {
					console.log("Param generate utility failed")
				});
			}
		});