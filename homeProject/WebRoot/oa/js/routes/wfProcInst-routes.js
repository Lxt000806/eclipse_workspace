/**
 * Created by zzr on 2019/5/24.
 */
define([], function () {
  'use strict';
  var routes = angular.module('wfProcInst.routes', []);
  routes.config(['$stateProvider',function ($stateProvider) {
$stateProvider
	.state('wfProcInstList', {
		url: "/wfProcInst/list/:type",
		templateUrl: "templates/wfProcInst/wfProcInst-list.html?time" + (new Date().getTime()),
		controller: 'WfProcInstListCtrl'
	})
	.state('wfProcInstLoad', {
		url: "/wfProcInst/load",
		templateUrl: "templates/wfProcInst/wfProcInst-load.html?time" + (new Date().getTime()),
		controller: 'WfProcInstLoadCtrl'
	})
	.state('wfProcInstApply', {
		url: "/wfProcInst/apply/:title/:m_umState/:procKey/:procID/:wfProcNo/:type/:taskId/:processInstanceId/:wfProcInstNo/:isEnd",
		templateUrl: "templates/wfProcInst/wfProcInst-apply.html?time" + (new Date().getTime()),
		controller: 'WfProcInstApplyCtrl'
	})
  }]);
  return routes;
})
