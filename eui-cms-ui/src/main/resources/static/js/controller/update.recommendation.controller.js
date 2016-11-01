app.controller('UpdateRecommendationController', [ '$scope', '$element', '$http' ,'translationService',  'modalTitle', 'row', 'close',
		function($scope, $element, $http , translationService, modalTitle, row ,close) {
			
			$http.get('/v1/user/locale').success(
				function(data) {
					console.log("Locale retreive successfully"+ data.locale);
					translationService.getTranslation($scope, data.locale);
				}).error(function() {
					console.log("Locale retreive failed.")
				}
			);
		
			$scope.priorityId=null;
			$scope.sequenceId=null;
			$scope.categoryId = categoryId;
			$scope.recommendationId = null;
			$scope.modalTitle = modalTitle;
			$scope.row = row;
			$scope.close = function() {
				close({
					priorityId : $scope.row.priority,
					sequenceId : $scope.row.sequenceNumber,
					recommendationId : $scope.row.recommendationId,
					applicationId : $scope.row.applilcation.id,
					categoryId :$scope.row.category.id 
				}, 500);
			};

			$scope.cancel = function() {
				$element.modal('hide');
			};

		} ]);


 