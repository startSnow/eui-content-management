app .config(
				function($httpProvider) {
					$httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';
				}).controller("BatchUploadApplicationController", function($scope, $http,$timeout , ModalService,translationService) { 
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
					
					$scope.batchFile = null;
					 
				    $scope.$on("fileSelected", function (event, args) {
				        $scope.$apply(function () {   
				        	$scope.batchFile =  args.file;
				        });
				    });
				    
				    $scope.showContent = function($fileContent){
				        $scope.content = $fileContent;
				        $scope.rowCollection = null;
				    };

				    $scope.clearForm = function(){
				        $scope.content = "";
				        $scope.rowCollection = null;
				    };
				 
				    
				    $scope.uploadBatchApplications= function() { 
				    	 $http({
				             method: 'POST',
				             url: "/v1/store/apps/"+$scope.deviceType+"/batch",
				             headers: { 'Content-Type': undefined },
				             transformRequest: function (data) {
				                 var formData = new FormData();
				                 formData.append("file", $scope.batchFile);
				                 return formData;
				             },
				             data: {file: $scope.files }
				         }).
				         success(function (data, status, headers, config) {
				        	 $scope.content ="";
				        	 $scope.rowCollection = data;
				         }).
				         error(function (data, status, headers, config) {
				        	 $scope.error = data.message;
								$timeout(function() {
							        $scope.error = '';
								}, 2000);
								console.log("application upload failed."+data.message)
				         });
				    };
				});