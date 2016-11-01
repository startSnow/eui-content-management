app .config(
				function($httpProvider) {
					$httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';
				}).controller("RecommendationsController", function($scope, $timeout, $http , ModalService,translationService) {
					$scope.loading = true;
					
					$scope.list = ["one", "two", "thre", "four", "five", "six"];
					 
					 
					$http.get('/v1/user/locale').success(
						function(data) {
							console.log("Locale retreive successfully"+ data.locale);
							translationService.getTranslation($scope, data.locale);
						}).error(function() {
							console.log("list.recommendation.controller[Locale retreive failed.]")
						}
					);
					
					$http.get('/v1/store/categories').success(function(data) {
						$scope.categoryData = data.data;
					}).error(function() {
						console.log("Category loading failed.")
						$scope.error = "Category loading failed.";
						$timeout(function() {
					        $scope.error = '';
						}, 2000);
					}).finally(function() {
					    // called no matter success or failure
					    $scope.loading = false;
					});
					
					$scope.loadRecommendations = function (selectedItem) {
						$scope.loading = true;
						$scope.selectedItem = selectedItem;
						$http.get('/v1/store/category/'+selectedItem.id+'/recommendation').success(function(recommendationsData) {
							$scope.rowCollection = recommendationsData.data;
						}).error(function() {
							console.log("Recomendation loading failed.")
							$scope.error = "Recomendation loading failed.";
							$timeout(function() {
						        $scope.error = '';
							}, 2000);
						}).finally(function() {
						    // called no matter success or failure
						    $scope.loading = false;
						});
				
					}
					
					$scope.removeRow = function(row){
						$scope.loading = true;
						$http.delete('/v1/store/category/'+row.category.id+'/recommendation/'+row.recommendationId).success(function(status,config) {
			    		  	 var index = $scope.rowCollection.indexOf(row);
					         if (index !== -1) {
					            $scope.rowCollection.splice(index, 1);
					         }
					         $scope.successMessage = "Recomendation removed successfully";
				    		  	$timeout(function() {
							        $scope.successMessage = '';
							  }, 2000);
						 }).error(function(data,status) {
							console.log("Recomendation delete failed."+data.message);
							$scope.error = data.message;
							$timeout(function() {
						        $scope.error = '';
							}, 2000);
						 }).finally(function() {
							    // called no matter success or failure
							    $scope.loading = false;
							}); 
					}
					
					$scope.updateRecommendation = function (row) {
						ModalService.showModal({
						      templateUrl: "view/update.recommendation.html",
						      controller: "UpdateRecommendationController",
						      inputs: {
						    	  $http : $http,
						    	  translationService :translationService,
						    	  modalTitle: "Update Recommendation",
						    	  row : row
						      }
						    }).then(function(modal) {
						      modal.element.modal();
						      modal.close.then(function(result) {
						    	   var config = {
						                  headers : {
						                      'Content-Type': 'application/json;charset=UTF-8;'
						                  }
						          }
						    	    var request = JSON.stringify({
						    		   	  priorityId : result.priorityId,
							    		  sequenceId : result.sequenceId,
							    		  categoryId : result.categoryId,
							    		  applicationId : result.applicationId,
							    		  recommendationId : result.recommendationId
						              });
						    	   
							    	  console.log(request);
							    	  $http.patch('/v1/store/category/'+result.categoryId+'/recommendation',request).success(function(data,status,config) {
						    		  	console.log(data);
						    		  	 var index = $scope.rowCollection.indexOf(row);
								         if (index !== -1) {
								            $scope.rowCollection.splice(index, 1);
								         }
								         $scope.rowCollection.splice(index, 0, data);
								         $scope.successMessage = "Recomendation updated successfully";
							    		  	$timeout(function() {
										        $scope.successMessage = '';
										  }, 2000);
						    		  	//TODO : Delete applications from UI
									 }).error(function(data,status) {
										console.log("Recomendation update failed."+data.message);
										$scope.error = data.message;
										$timeout(function() {
									        $scope.error = '';
										}, 2000);
									 });  
								    	  
						    	   
						      });
						    });
					}
					
					$scope.addNewRecommendation = function(){
						 
						if($scope.selectedItem == undefined){
							console.log("Please select category");
							$scope.error = "Please select category";
							$timeout(function() {
						        $scope.error = '';
							}, 2000);
							return ;
						}
						ModalService.showModal({
						      templateUrl: "view/add.recommendation.html",
						      controller: "AddRecommendationController",
						      inputs: {
						    	  $http : $http,
						    	  translationService: translationService,
						    	  $timeout : $timeout,
						    	  ModalService : ModalService,
						    	  modalTitle: "Add Recommendation",
						    	  categoryId : $scope.selectedItem.id
						      }
						    }).then(function(modal) {
						      modal.element.modal();
						      modal.close.then(function(result) {
								  $http.get('/v1/store/category/'+$scope.selectedItem.id+'/recommendation').success(function(recommendationsData) {
									$scope.rowCollection = recommendationsData.data;
								  }).error(function() {
										console.log("Recomendation add failed.")
										$scope.error = "Recomendation adding failed.";
										$timeout(function() {
									        $scope.error = '';
										}, 2000);
								  });
						      });
						    });
					}
					
					$scope.updateSequence = function(){
						 $scope.loading = true;
						 for(var cnt = 0 ; cnt < $scope.rowCollection.length; cnt++ ){
							$scope.rowCollection[cnt].sequenceNumber = cnt+1;
						 }
						 
						var request = JSON.stringify($scope.rowCollection);
						 $http.patch('/v1/store/category/'+$scope.selectedItem.id+'/recommendation/batch',request).success(function(data,status,config) {
							 	$scope.loadRecommendations($scope.selectedItem);
				    		  	$scope.successMessage = "Recomendation updated successfully";
				    		  	$timeout(function() {
							        $scope.successMessage = '';
				    		  	}, 2000);
						 }).error(function(data,status) {
								console.log("Recomendation update failed."+data.message);
								$scope.error = data.message;
								$scope.loadRecommendations($scope.selectedItem);
								$timeout(function() {
							        $scope.error = '';
								}, 2000);
								
						 }).finally(function() {
							    $scope.loading = false;
						});  
				         
					}
					
					$scope.resetSequence = function(){
						$scope.loadRecommendations($scope.selectedItem);
						$scope.successMessage = "Recomendation reset successfully";
		    		  	$timeout(function() {
					        $scope.successMessage = '';
					  }, 2000);
					}
		});