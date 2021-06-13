/**
 * Created by zzr on 2019/5/24.
 */
define(function(require){
  'use strict';
  var services=angular.module('starter.services', []);
  services.service('myInterceptor',require('service/myInterceptor'));
  services.service('wfProcInstService',require('service/wfProcInstService'));
  return services;
})







