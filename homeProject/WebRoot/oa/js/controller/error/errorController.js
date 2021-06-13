/**
 * Created by zzr on 2019/5/24.
 */
define(function(require){
  'use strict';
  var controllers=angular.module('error.controller',[]);
  controllers.controller('ErrorCtrl',require('controller/error/ErrorCtrl'));
  return controllers;
})
