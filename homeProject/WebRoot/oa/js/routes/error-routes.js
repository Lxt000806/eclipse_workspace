/**
 * Created by zzr on 2019/5/24.
 */
define([], function () {
  'use strict';
  var routes = angular.module('error.routes', []);
  routes.config(['$stateProvider',function ($stateProvider) {
    $stateProvider
      .state('error', {
        url: "/error:type",
        templateUrl: "templates/error/error.html?time" + (new Date().getTime()),
        controller: 'ErrorCtrl'
      })
  }]);
  return routes;
})
