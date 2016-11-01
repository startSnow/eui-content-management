app .config(
				function($httpProvider) {
					$httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';
				}).controller("AddUpdateRecommendationController", function($scope, $timeout, $http , ModalService,translationService) { 

					$scope.models = {
						        selected: null,
						        lists: {"A": [], "B": []}
					};
					 
					$scope.addNewApplication = function() {
						 ModalService.showModal({
						      templateUrl: "view/add.application.html",
						      controller: "AddApplicationController",
						      inputs: {
						    	  $http : $http,
						    	  translationService : translationService,
						    	  modalTitle: $scope.selectedItem.deviceType
						      }
						    }).then(function(modal) {
						      modal.element.modal();
						      modal.close.then(function(result) {
						    	  if(result.id == null || result.packageName == null){
						    		  return;
						    	  }
						    	   var config = {
						                  headers : {
						                      'Content-Type': 'application/json;charset=UTF-8;'
						                  }
						          }
						    	  var request = JSON.stringify({
						    		  id : result.id,
						    		  packageName : result.packageName,
						    		  deviceType : result.deviceType,
						    		  companyName : result.companyName,
						    		  title : result.title,
						    		  bannerImageUrl : result.bannerImageUrl,
						    		  iconUrl : result.iconUrl,
						    		  bigImageUrl : result.bigImageUrl
					              });
						    	  console.log(request);
						    	  
						    	  $http.post('/v1/store/apps',request).success(function(data,status,config) {
						    		  	console.log(data);
						    		  	$scope.loadRecommendations($scope.selectedItem);
						    		  	$scope.successMessage = "Application added successfully";
						    		  	$timeout(function() {
									        $scope.successMessage = '';
										}, 2000);
									}).error(function(data,status) {
										$scope.error = data.message;
										$timeout(function() {
									        $scope.error = '';
										}, 2000);
										console.log("application creation failed."+data.message)
									});
						      });
						    });
					} 
					
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
						$scope.models.lists.A= [];
						$scope.models.lists.B = [];
						$scope.loading = true;
						$scope.selectedItem = selectedItem;
						
						$http.get('/v1/store/category/'+selectedItem.id+'/recommendation').success(function(recommendationsData) {
							if(recommendationsData.data != undefined){
								$scope.models.lists.A=recommendationsData.data;
							}
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
						
						$http.get('/v1/store/category/'+selectedItem.id+'/recommendation?available=true').success(function(recommendationsData) {
							if(recommendationsData.data != undefined){
								$scope.models.lists.B=recommendationsData.data;
							}
						}).error(function() {
							console.log("Recomendation available loading failed.")
							$scope.error = "Recomendation available loading failed.";
							$timeout(function() {
						        $scope.error = '';
							}, 2000);
						}).finally(function() {
						    // called no matter success or failure
						    $scope.loading = false;
						});
						 
					} 
				   
					$scope.updateRecommendations = function(){
						 $scope.loading = true;
						 if($scope.models.lists.A.length != 0){
							 var request = JSON.stringify($scope.models.lists.A);
							 $http.patch('/v1/store/category/'+$scope.selectedItem.id+'/recommendation/batch',request).success(function(data,status,config) {
								    $scope.models.lists.A = data;
					    		  	$scope.successMessage = "Recomendation updated successfully";
					    		  	$timeout(function() {
								        $scope.successMessage = '';
					    		  	}, 2000);
							 }).error(function(data,status) {
									console.log("Recomendation update failed."+data.message);
									$scope.error = data.message;
									$timeout(function() {
								        $scope.error = '';
									}, 2000);
									
							 }).finally(function() {
								    $scope.loading = false;
							});  
						 }
				         
						 var deletedItems = [];
						 for(var cnt = 0 ; cnt < $scope.models.lists.B.length; cnt++ ){
							 if($scope.models.lists.B[cnt].recommendationId != '-99999'){
								 deletedItems.push($scope.models.lists.B[cnt].recommendationId);
							 }
						 }
						 if(deletedItems.length != 0){
							 
							 $scope.loading = true;
								$http.delete('/v1/store/category/'+$scope.selectedItem.id+'/recommendation/batch?recommendationIds='+deletedItems).success(function(status,config) {
									 for(var cnt = 0 ; cnt < $scope.models.lists.B.length; cnt++ ){
										 if($scope.models.lists.B[cnt].recommendationId != '-99999'){
											 $scope.models.lists.B[cnt].recommendationId = '-99999';
										 }
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
									    $scope.loading = false;
								});   
						 }
					}
				 
				});