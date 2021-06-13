define(function(require){
	var directives=angular.module('starter.directives', ['starter.dao']);
	directives.directive('infolist',require('directive/infoList'));
	directives.directive('mulitselect'['dao', require('directive/mulitselect')]);
	directives.directive('inputcustomer',['dao', require('directive/appComponent_customer')]);
	directives.directive('inputemployee',['dao', require('directive/appComponent_employee')]);
	directives.directive('group',['$compile', 'dao', require('directive/group')]);
	directives.directive('textareaauto',['dao', require('directive/textareaauto')]);
	directives.directive('inputfocus', require('directive/inputfocus'));
	return directives;
})
