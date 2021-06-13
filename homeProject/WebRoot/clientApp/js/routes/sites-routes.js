
define([],function(){
  'use strict';
  var routes=angular.module('sites.routes',[]);
  routes.config(function($stateProvider){
    $stateProvider
      .state('sites-info',{
        url:'/sites/info',
        cache:false,
        templateUrl:"templates/sites/sites-info.html",
        controller:'sitesCtrl'
      })
      .state('sites-detail', {
        url: "/sites-detail/:code",
        templateUrl: "templates/sites/sites-detail.html",
        controller: "sitesCtrl"
      })
      .state('sites-detail-payInfo', {
        url: "/sites-detail-payInfo/:code",
        cache: false,
        templateUrl: "templates/sites/sites-detail-payInfo.html",
        controller: "sitesCtrl"
      })
      .state('sites-detail-costInfo', {
        url: "/sites-detail-costInfo/:code",
        cache: false,
        templateUrl: "templates/sites/sites-detail-costInfo.html",
        controller: "sitesCtrl"
      })
      .state('sites-detail-gxr', {
        url: "/sites-detail-gxr/:code",
        cache: false,
        templateUrl: "templates/sites/sites-detail-gxr.html",
        controller: "sitesCtrl"
      })
      .state('sites-detail-message', {
        url: "/sites-detail-message/:code",
        cache: false,
        templateUrl: "templates/sites/sites-detail-message.html",
        controller: "customerMessageCtrl"
      })
      .state('sites-detail-itemApp', {
        url: "/sites-detail-itemApp/:code",
        cache: false,
        templateUrl: "templates/sites/sites-detail-itemApp.html",
        controller: "itemAppCtrl"
      })
      .state('sites-detail-itemApp-detail', {
        url: "/sites-detail-itemApp-detail/:no",
        cache: false,
        templateUrl: "templates/sites/sites-detail-itemApp-detail.html",
        controller: "itemAppDetailCtrl"
      })
      .state('sites-detail-basicChange', {
        url: "/sites-detail-basicChange/:code",
        cache: false,
        templateUrl: "templates/sites/sites-detail-basicChange.html",
        controller: "basicChangeCtrl"
      })
      .state('sites-detail-basicChange-detail', {
        url: "/sites-detail-basicChange-detail/:no",
        cache: false,
        templateUrl: "templates/sites/sites-detail-basicChange-detail.html",
        controller: "basicChangeDetailCtrl"
      })
      .state('sites-detail-basicNeeds', {
        url: "/sites-detail-basicNeeds/:code",
        cache: false,
        templateUrl: "templates/sites/sites-detail-basicNeeds.html",
        controller: "basicNeedsCtrl"
      })
      .state('sites-detail-materialChange', {
        url: "/sites-detail-materialChange/:code",
        cache: false,
        templateUrl: "templates/sites/sites-detail-materialChange.html",
        controller: "materialChangeCtrl"
      })
      .state('sites-detail-materialChange-detail', {
        url: "/sites-detail-materialChange-detail/:no",
        cache: false,
        templateUrl: "templates/sites/sites-detail-materialChange-detail.html",
        controller: "materialChangeDetailCtrl"
      })
      .state('sites-detail-materialNeeds', {
        url: "/sites-detail-materialNeeds/:code",
        cache: false,
        templateUrl: "templates/sites/sites-detail-materialNeeds.html",
        controller: "materialNeedsCtrl"
      })
      .state('sites-detail-progress-detailList', {
        url: "/sites-detail-progress-detailList/:clientId",
        cache: false,
        templateUrl: "templates/sites/sites-detail-progress-detailList.html",
        controller: 'progressCtrl'
      })
      .state('sites-detail-progress-detail', {
        url: "/sites-detail-progress-detail/:pk:clientId",
        cache: false,
        templateUrl: "templates/sites/sites-detail-progress-detail.html",
        controller: 'progressCtrl'
      })
  });
  return routes;
})
