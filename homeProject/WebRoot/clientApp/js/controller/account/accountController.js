/**
 * Created by Administrator on 2016/4/15.
 */
define(function(require){
  'use strict';
  var controllers=angular.module('account.controller',[]);
  controllers.controller('AccountCtrl',require('controller/account/AccountCtrl'));
  return controllers;
})
