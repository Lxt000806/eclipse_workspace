/**
 * Created by Administrator on 2015/11/30.
 */
define(function(require){
  'use strict';
  var services=angular.module('starter.services', []);
  services.service('myInterceptor',require('service/myInterceptor'));
  services.service('loginService',require('service/loginService'));
  services.service('mapService',require('service/mapService'));
  services.service('sitesService',require('service/sitesService'));
  services.service('confirmService',require('service/confirmService'));
  return services;
})





//            getCityList:function(){
//                var defer=$q.defer();
//                var promise=defer.promise;
//                var data={};
//                data.portalType=1;
//                data.callback="JSON_CALLBACK";
//                var url="https://app.u-om.com:20002/client/basic/getAppServerUrlList";
//                var requestData={params:data};
//                $http.jsonp(url,requestData).success(function(response){
//                        defer.resolve(response.datas);
//                }).error(function(){
//                    defer.reject('鑾峰彇鍩庡競鍒楄〃澶辫触');
//                })
//                definePromise(promise);
//                return promise;
//            }

  //閰嶇疆鎷︽埅鍣�
//    .factory('userInterceptor', ['$q',function($q) {
//    var requestInterceptor = {
//        request: function(config) {
//            var deferred = $q.defer();
//
//                // Asynchronous operation succeeded, modify config accordingly
//
//                deferred.resolve(config);
//
//            return deferred.promise;
//        }
//    };
//
//    return requestInterceptor;
//}]);






