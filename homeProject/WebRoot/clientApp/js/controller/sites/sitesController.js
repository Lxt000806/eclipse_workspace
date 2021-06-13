define(function(require){
  'use strict';
  var controllers=angular.module('sites.controller', []);
  controllers.controller('basicChangeCtrl',require('controller/sites/basicChangeCtrl'));
  controllers.controller('basicChangeDetailCtrl',require('controller/sites/basicChangeDetailCtrl'));
  controllers.controller('basicNeedsCtrl',require('controller/sites/basicNeedsCtrl'));
  controllers.controller('customerMessageCtrl',require('controller/sites/customerMessageCtrl'));
  controllers.controller('itemAppCtrl',require('controller/sites/itemAppCtrl'));
  controllers.controller('itemAppDetailCtrl',require('controller/sites/itemAppDetailCtrl'));
  controllers.controller('materialChangeCtrl',require('controller/sites/materialChangeCtrl'));
  controllers.controller('materialChangeDetailCtrl',require('controller/sites/materialChangeDetailCtrl'));
  controllers.controller('materialNeedsCtrl',require('controller/sites/materialNeedsCtrl'));
  controllers.controller('progressCtrl',require('controller/sites/progressCtrl'));
  controllers.controller('sitesCtrl',require('controller/sites/sitesCtrl'));
  return controllers;
})




