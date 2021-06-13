/**
 * Created by zzr on 2019/5/24.
 */
define(
  [
  'routes/wfProcInst-routes',
  'routes/error-routes'
  ],
  function(require){
  'use strict';
  var routes=angular.module( 'starter.routes',
    [
      'wfProcInst.routes',
      'error.routes'
    ]
  );
  routes .config(['$stateProvider', '$urlRouterProvider',function ($stateProvider, $urlRouterProvider) {
      $urlRouterProvider.otherwise("/wfProcInst/load");
  }]);
  return routes;
})



