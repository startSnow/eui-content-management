app.controller('AddCategoryController', [ '$scope', '$element', '$http', 'translationService' , 'title', 'regions' ,'close',
		function($scope, $element, $http, translationService ,title, regions, close) {
			$scope.loading = true;
			$http.get('/v1/user/locale').success(
				function(data) {
					console.log("Locale retreive successfully"+ data.locale);
					translationService.getTranslation($scope, data.locale);
				}).error(function() {
					console.log("add.category.controller [Locale retreive failed.]")
				}
			).finally(function() {
			    $scope.loading = false;
			});
	
			$scope.id=null;
			$scope.categoryName = null;
			$scope.iconUrl = null;
			$scope.deviceType = null;
			$scope.region = null;
			
			$scope.title = title;
			
			$http.get('/v1/utility/id/CATEGORY').success(function(data) {
				$scope.id = data;
			}).error(function() {
				console.log("Param generate utility failed")
			});
			
			$scope.data = {
				model : null,
				availableOptions : [ {
					id : 'MOBILE',
					name : 'MOBILE'
				}, {
					id : 'TV',
					name : 'TV'
				} ]
			};

			$scope.regionData = {
				model : null,
				availableOptions : regions
			};
			
		 
			
			$scope.close = function() {
				close({
					id : $scope.id,
					categoryName : $scope.categoryName,
					iconUrl : $scope.iconUrl,
					deviceType : $scope.deviceType,
					region : $scope.region 
				}, 500);
			};

			$scope.cancel = function() {

				$element.modal('hide');
 
			};

		} ]);
