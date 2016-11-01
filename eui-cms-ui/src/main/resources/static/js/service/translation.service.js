app.service('translationService', function($resource) {
	this.getTranslation = function($scope, language) {
		var languageFilePath = '../../resources/messages_' + language + '.json';
		console.log(languageFilePath);
		$resource(languageFilePath).get(function(data) {
			console.log(data);
			$scope.translation = data;
		});
	};
});