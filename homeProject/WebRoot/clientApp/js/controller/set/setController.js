/**
 * Created by Administrator on 2016/4/15.
 */
define(function(require){
  'use strict';
  var controllers=angular.module('set.controller', []);
  controllers.controller('set-MapCtrl',require('controller/set/set-MapCtrl'));
  controllers.controller('setCtrl',require('controller/set/setCtrl'));
  return controllers;
})
