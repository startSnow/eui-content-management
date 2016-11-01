app .config(
				function($httpProvider) {
					$httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';
				}).controller("CategoryController", function($scope, $http , $timeout, ModalService,translationService) {
					
					$scope.loading = true;
					
					$http.get('/v1/user/locale').success(
						function(data) {
							console.log("Locale retreive successfully"+ data.locale);
							translationService.getTranslation($scope, data.locale);
						}).error(function() {
							console.log("list.category.controller[Locale retreive failed.]")
						}
					);
				
					$http.get('/v1/store/categories').success(function(data) {
						$scope.rowCollection = data.data;
					}).error(function() {
						console.log("Category loading failed")
						$scope.error = "Category loading failed";
						$timeout(function() {
					        $scope.error = '';
						}, 2000);
					}).finally(function() {
					    // called no matter success or failure
					    $scope.loading = false;
					});
					 
					$scope.removeRow = function removeRow(row) {
							$http.delete('/v1/store/categories/'+row.id).success(function(data) {
							    var index = $scope.rowCollection.indexOf(row);
						        if (index !== -1) {
						            $scope.rowCollection.splice(index, 1);
						        }
						        $scope.successMessage = "Category deleted successfully";
				    		  	$timeout(function() {
							        $scope.successMessage = '';
								}, 2000);
							}).error(function(data) {
								console.log("Category delete has been failed"+data)
								$scope.error = data.message;
								$timeout(function() {
								        $scope.error = '';
								}, 2000);
							});
					      
					  }
					
					  $scope.addNewCategory = function() {
						  	var regions = null;
							$http.get('/v1/store/regions').success(function(data) {
								regions = data.data;
							    ModalService.showModal({
							      templateUrl: "view/add.category.html",
							      controller: "AddCategoryController",
							      inputs: {
							    	  $http : $http,
							    	  translationService : translationService,
							    	  title: "Add Category",
							          regions : regions
							      }
							    }).then(function(modal) {
							      modal.element.modal();
							      modal.close.then(function(result) {
							    	  $scope.complexResult  = "Name: " + result.categoryName + ", age: " + result.iconUrl+ ", deviceType :" + result.deviceType + "";
							    	  var config = {
							                  headers : {
							                      'Content-Type': 'application/json;charset=UTF-8;'
							                  }
							          }
							    	  if(result.id == null || result.categoryName == null){
							    		  return;
							    	  }
							    	  var request = JSON.stringify({
						            	  id : result.id,
							    		  name: result.categoryName,
						            	  iconUrl : result.iconUrl,
						            	  regionId : result.region,
						            	  deviceType : result.deviceType 
						              });
							    	  console.log(request);
							    	  
							    	  $http.post('/v1/store/categories',request).success(function(data,status,config) {
							    		  	console.log(data);
							    		  	$scope.rowCollection.push(data);
							    		  	$scope.successMessage = "Category added successfully";
							    		  	$timeout(function() {
										        $scope.successMessage = '';
											}, 2000);
										}).error(function(data,status) {
											console.log("Create category failed."+data.message)
											$scope.error = data.message;
											$timeout(function() {
										        $scope.error = '';
											}, 2000);
										});
							    	  
							      });
							    });
							}).error(function() {
								console.log("Regions loading failed")
								$scope.error = "Regions loading failed";
								$timeout(function() {
							        $scope.error = '';
								}, 2000);
							});

						  };
		});