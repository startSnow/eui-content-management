app.controller('UpdateApplicationController', [ '$scope', '$element','$http', 'row', 'translationService', 'modalTitle' ,'close',
		function($scope, $element, $http, row ,  translationService ,modalTitle, close) {
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

			
			$scope.id=row.id;
			$scope.packageName = row.packageName;
			$scope.companyName = row.companyName;
			$scope.title = row.title;
			$scope.bannerImageUrl = row.bannerImageUrl;
			$scope.iconUrl = row.iconUrl;
			$scope.bigImageUrl = row.bigImageUrl;
			$scope.modalTitle = modalTitle;
			$scope.deviceType = row.deviceType;
		 
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
