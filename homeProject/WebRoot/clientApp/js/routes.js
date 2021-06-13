/**
 * Created by Administrator on 2016/2/19.
 */
define([
  'routes/account-routes',
  'routes/set-routes',
  'routes/sites-routes',
], function (require) {
  'use strict';
  var routes = angular.module('starter.routes', [
    'account.routes',
    'set.routes',
    'sites.routes',
  ]);
  routes.config(function ($stateProvider, $urlRouterProvider) {
      var code='0'
      if(localStorage.initCustCode){
        code= localStorage.initCustCode;
      }
      $urlRouterProvider.otherwise("/sites-detail/"+code);
    }
  );
  return routes;
})



