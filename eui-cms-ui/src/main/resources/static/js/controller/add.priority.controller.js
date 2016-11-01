app.controller('AddPrioritynController', [ '$scope', '$element', '$http' ,'translationService',  'modalTitle', 'categoryId' , 'applicationId' , 'close',
		function($scope, $element, $http,translationService , modalTitle, categoryId, applicationId ,close) {
			$scope.loading = true;
			$http.get('/v1/user/locale').success(
				function(data) {
					console.log("Locale retreive successfully"+ data.locale);
					translationService.getTranslation($scope, data.locale);
				}).error(function() {
					console.log("add.priority.controller [Locale retreive failed.]")
				}
			).finally(function() {
			    $scope.loading = false;
			});
		
			$scope.priorityId=null;
			$scope.sequenceId=null;
			$scope.categoryId = categoryId;
			$scope.recommendationId = null;
			$scope.applicationId = applicationId;
			$scope.modalTitle = modalTitle;
		 

			$http.get('/v1/utility/id/RECOMMENDATION').success(function(data) {
				$scope.recommendationId = data;
			}).error(function() {
				console.log("Param generate utility failed")
			});
			
			$scope.close = function() {
				close({
					priorityId : $scope.priorityId,
					sequenceId : $scope.sequenceId,
					recommendationId : $scope.recommendationId
				}, 500);
			};

			$scope.cancel = function() {
				$element.modal('hide');
			};

		} ]);


 