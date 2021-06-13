/**
 * Created by zzr on 2019/5/24.
 */
define([],function(){
  'use strict';
  function  service($rootScope,$injector) {
    var responseInterceptor = {
      response: function (response) {
        var returnInfo = response.data.returnInfo;
        if (returnInfo == '用户未登录') {
          $rootScope.clear;
          $rootScope.fresh = 1;
          var state = $injector.get('$state');
          state.go("login");
        }
        return response;

      }
    };
    return responseInterceptor;
  }
  service.$inject=['$rootScope','$injector'];
  return service;
})
