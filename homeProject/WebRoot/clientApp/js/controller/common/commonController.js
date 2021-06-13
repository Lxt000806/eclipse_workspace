/**
 * Created by xzp on 2016/8/3.
 */
define(function(require){
  var controllers=angular.module('common.controller',[]);
  controllers.controller('photoCtrl',require('controller/common/photoCtrl'));
  controllers.controller('mapCtrl',require('controller/common/mapCtrl'));
  return controllers;
})
