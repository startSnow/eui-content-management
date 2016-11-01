app.controller('AddApplicationController', [ '$scope', '$element','$http', 'translationService', 'modalTitle' ,'close',
		function($scope, $element, $http, translationService ,modalTitle, close) {
			$scope.loading = true;
			
			$http.get('/v1/user/locale').success(
				function(data) {
					console.log("Locale retreive successfully"+ data.locale);
					translationService.getTranslation($scope, data.locale);
				}).error(function() {
					console.log("add.application.controller [Locale retreive failed.]")
				}
			).finally(function() {
			    $scope.loading = false;
			});
	
			$scope.deviceTypeData = {
				model : null,
				availableOptions : [ {
					id : 'MOBILE',
					name : 'MOBILE'
				}, {
					id : 'TV',
					name : 'TV'
				} ]
			};

			
			$scope.id=null;
			$scope.packageName = null;
			$scope.companyName = null;
			$scope.title = null;
			$scope.bannerImageUrl = null;
			$scope.iconUrl = null;
			$scope.bigImageUrl = null;
			$scope.modalTitle = modalTitle;
			
			if($scope.modalTitle != 'Add Application'){
				$scope.deviceType = $scope.modalTitle;
			}
			 
			$http.get('/v1/utility/id/APPLICATION').success(function(data) {
				$scope.id = data;
			}).error(function() {
				console.log("Param generate utility failed")
			});
			
			$scope.close = function() {
				close({
					id : $scope.id,
					packageName : $scope.packageName,
					companyName : $scope.companyName,
					deviceType : $scope.deviceType,
					title : $scope.title,
					bannerImageUrl : $scope.bannerImageUrl,
					iconUrl : $scope.iconUrl,
					bigImageUrl : $scope.bigImageUrl
				}, 500);
			};

			$scope.cancel = function() {
				$element.modal('hide');
			};

		} ]);
