// app.js
var app = angular.module("app", ['ui','angularModalService','ui.router','ngMessages','ngResource','dndLists']);
app.config(function($stateProvider, $urlRouterProvider) {
	//$urlRouterProvider.otherwise('/com.leeco.eui.ui');
	$stateProvider.state('applications', {
		url : '/applications',
		templateUrl : 'view/list.applications.html',
		controller: "ApplicationController"
	}).state('categories', {
		url : '/categories',
		templateUrl : 'view/list.categories.html',
		controller: "CategoryController"
	}).state('recommendations', {
		url : '/recommendations',
		templateUrl : 'view/list.recommendations.html',
		controller: "RecommendationsController"
	}).state('upload', {
		url : '/upload',
		templateUrl : 'view/upload.file.html'
	}).state('batchUpload', {
		url : '/batchUpload',
		templateUrl : 'view/batch.upload.application.html',
		controller: "BatchUploadApplicationController"
	}).state('utility', {
		url : '/utility',
		templateUrl : 'view/utility.page.html',
		controller: "UtilityController"
	}).state('profile', {
		url : '/profile',
		templateUrl : 'view/profile.page.html',
		controller: "UserProfileController"
	}).state('about', {
		url : '/about',
		template : "<p></p>"
	}).state('logout', {
		url : 'logout',
		templateUrl : "view/user.logout.html",
		controller:"LogoutController"
	}).state('recommendationUI', {
		url : '/recommendationUI',
		templateUrl : "view/add.update.recommendation.html",
		controller:"AddUpdateRecommendationController"
	}).state('#', {
		url : "#"
	});
});
 

	