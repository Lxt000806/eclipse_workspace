/**
 * Created by Administrator on 2016/4/18.
 */
define([],function(){
  'use strict';
  var routes=angular.module('set.routes',[]);
  routes.config(function($stateProvider){
    $stateProvider
      .state('more-set', {
        url: "/more/set",
        templateUrl: "templates/set/set.html",
        controller: "setCtrl"
      })

      .state('map', {
        url: "/more/map",
        templateUrl: "templates/set/map.html",
        controller: "set-MapCtrl"
      })
      .state('map-adjust', {
        url: "/more/map/adjust",
        cache: false,
        templateUrl: "templates/set/map-adjust.html",
        controller: "set-MapCtrl"
      })
      .state('sign-statistics', {
        url: '/more/map/statistics',
        templateUrl: 'templates/set/sign-statistics.html',
        controller: "set-MapCtrl"
      })
  });
  return routes;
})
