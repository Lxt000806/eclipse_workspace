/**
 * Created by zzr on 2019/5/24.
 */
define(function(require){
  'use strict';
  var controllers=angular.module('wfProcInst.controller',[]);
  controllers.controller('WfProcInstCtrl',require('controller/wfProcInst/WfProcInstCtrl'));
  controllers.controller('WfProcInstLoadCtrl',require('controller/wfProcInst/WfProcInstLoadCtrl'));
  controllers.controller('WfProcInstApplyCtrl',require('controller/wfProcInst/WfProcInstApplyCtrl'));
  controllers.controller('WfProcInstListCtrl',require('controller/wfProcInst/WfProcInstListCtrl'));
  return controllers;
})
