app.controller('AddRecommendationController', [ '$scope', '$element', '$http','translationService' , '$timeout' , 'ModalService',  'modalTitle', 'categoryId' , 'close',
		function($scope, $element, $http ,translationService , $timeout , ModalService ,  modalTitle, categoryId ,close) {
			$scope.loading = true;
			$http.get('/v1/user/locale').success(
					function(data) {
						console.log("Locale retreive successfully"+ data.locale);
						translationService.getTranslation($scope, data.locale);
					}).error(function() {
						console.log("add.recommendation.controller[Locale retreive failed.]")
					}
			);
			
			$scope.id=null;
			$scope.categoryName = null;
			$scope.categoryId = categoryId;
	 
			$scope.modalTitle = modalTitle;
			
			$http.get('/v1/store/category/'+categoryId+"/recommendation/apps").success(function(data) {
				$scope.rowCollection = data.data;
			}).error(function() {
				console.log("Param generate utility failed")
				$scope.error="Application loading failed.";
				$timeout(function() {
			        $scope.error = '';
				}, 2000);
			}).finally(function() {
			    $scope.loading = false;
			});
			
			$scope.addPriority = function(row){
				var applicationId = row.id
				ModalService.showModal({
				      templateUrl: "view/add.priority.html",
				      controller: "AddPrioritynController",
				      inputs: {
				    	  $http : $http,
				    	  translationService : translationService,
				    	  modalTitle: "Update Proirty and Sequence",
				    	  categoryId : $scope.categoryId,
				    	  applicationId : applicationId
				      }
				    }).then(function(modal) {
				      modal.element.modal();
				      modal.close.then(function(result) {
				    	   var config = {
				                  headers : {
				                      'Content-Type': 'application/json;charset=UTF-8;'
				                  }
				          }
				    	   if(result.priorityId == null){
				    		   return;
				    	   }
				    	    var request = JSON.stringify({
				    		   	  priorityId : result.priorityId,
					    		  sequenceId : result.sequenceId,
					    		  categoryId : $scope.categoryId,
					    		  applicationId : applicationId,
					    		  recommendationId : result.recommendationId
				              });
					    	  console.log(request);
					    	  $http.post('/v1/store/category/'+$scope.categoryId+'/recommendation',request).success(function(data,status,config) {
				    		  	console.log(data);
				    		  	 var index = $scope.rowCollection.indexOf(row);
						         if (index !== -1) {
						            $scope.rowCollection.splice(index, 1);
						         }
						         $scope.successMessage = "Recomendation added successfully";
					    		 $timeout(function() {
								        $scope.successMessage = '';
								  }, 2000);
				    		  	//TODO : Delete applications from UI
							 }).error(function(data,status) {
								console.log("Adding Recommendation failed."+ data.message)
								$scope.error =  data.message;
								$timeout(function() {
							        $scope.error = '';
								}, 2000);
							 });  
					    	  
				    	  
				    	   
				      });
				    });
			
			} 
			$scope.close = function() {
				close({ action : 'CLOSE' }, 500);
			};

			$scope.cancel = function() {
				$element.modal('hide');
				close({action : 'CANCEL' }, 500);
			};

		} ]);

 