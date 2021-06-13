/**
 * Created by Administrator on 2016/4/18.
 */
define([],function(){
  'use strict';
  function  service($rootScope) {
    var responseInterceptor = {
      response: function (response) {
        var returnInfo = response.data.returnInfo;
        if (returnInfo == '用户未登录') {
          $rootScope.clear;
          $rootScope.fresh = 1;
          window.open("#/login", "_self");
        }
        return response;

      }
    };
    return responseInterceptor;
  }
  service.$inject=['$rootScope'];
  return service;
})
