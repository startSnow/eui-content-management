app
		.config(
				function($httpProvider) {
					$httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';
				}).controller("UserProfileController", function( $scope ,$http, $location,translationService) {
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
					
					var self = this;
					$http.get("/user").success(function(data) {
						if (data.userAuthentication == undefined) {
							$scope.user = data.name;
							$scope.email = 'N/A';
							$scope.id = data.name;
						} else {
							$scope.user = data.userAuthentication.details.name;
							$scope.email = data.userAuthentication.details.email;
							$scope.id = data.userAuthentication.details.id;
						}
						self.authenticated = true;
					}).error(function() {
						self.user = "N/A";
						self.authenticated = false;
					});
					self.logout = function() {
						$http.post('logout', {}).success(function() {
							self.authenticated = false;
							location.href = "/admin/login";
							//$location.path("/admin/login");
						}).error(function(data) {
							console.log("Logout failed")
							self.authenticated = false;
						});
					};
		});