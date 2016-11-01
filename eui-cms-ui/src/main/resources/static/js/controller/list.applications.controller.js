app .config(
				function($httpProvider) {
					$httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';
				}).controller("ApplicationController", function($scope, $http,$timeout , ModalService,translationService) {

					$scope.loading = true;

					$http.get('/v1/user/locale').success(
							function(data) {
								console.log("Locale retreive successfully"+ data.locale);
								translationService.getTranslation($scope, data.locale);
							}).error(function() {
								console.log("list.application.controller[Locale retreive failed.]")
							});
					
					$scope.addNewApplication = function() {
						 ModalService.showModal({
						      templateUrl: "view/add.application.html",
						      controller: "AddApplicationController",
						      inputs: {
						    	  $http : $http,
						    	  translationService : translationService,
						    	  modalTitle: "Add Application"
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
						    		  	$scope.rowCollection.push(data);
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
					
					$scope.updateApplication = function(row) {
						 ModalService.showModal({
						      templateUrl: "view/update.application.html",
						      controller: "UpdateApplicationController",
						      inputs: {
						    	  $http : $http,
						    	  row :row,
						    	  translationService : translationService,
						    	  modalTitle: "Update Application"
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
						    	  
						    	  $http.patch('/v1/store/apps',request).success(function(data,status,config) {
						    		  	console.log(data);
						    		  	var index = $scope.rowCollection.indexOf(row);
								        if (index !== -1) {
								            $scope.rowCollection.splice(index, 1);
								        }
								        $scope.rowCollection.splice(index, 0, data);
						    		  	$scope.successMessage = "Application updated successfully";
						    		  	$timeout(function() {
									        $scope.successMessage = '';
										}, 2000);
									}).error(function(data,status) {
										$scope.error = data.message;
										$timeout(function() {
									        $scope.error = '';
										}, 2000);
										console.log("application update failed."+data.message)
									});
						      });
						    });
					}
					
				 
					
					$http.get('/v1/store/apps').success(function(data) {
						$scope.rowCollection = data.data;
					}).error(function() {
						console.log("Applications loading failed");
						$scope.error = "Applications loading failed";
						$timeout(function() {
					        $scope.error = '';
						}, 2000);
					}).finally(function() {
					    // called no matter success or failure
						 $scope.loading = false;
					});
					
					$scope.removeRow = function removeRow(row) {
						$http.delete('/v1/store/apps/'+row.id).success(function(data) {
						    var index = $scope.rowCollection.indexOf(row);
					        if (index !== -1) {
					            $scope.rowCollection.splice(index, 1);
					        }
					        $scope.successMessage = "Application deleted successfully";
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
		});