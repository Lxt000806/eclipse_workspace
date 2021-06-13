
define(['controller','services', 'dao','routes','directive','plugins/ngIOS9UIWebViewPatch','plugins/ion-gallery','../lib/ionic-datepicker/dist/ionic-datepicker.bundle.min','../lib/ngCordova/dist/ng-cordova.min', 'filter'],function(){
  'use strict';

  var app= angular.module('starter', ['ionic','starter.controllers', 'starter.services','starter.dao','starter.routes','starter.directives', 'ionic-datepicker',
    'ngCordova', 'ion-gallery', 'ngIOS9UIWebViewPatch', 'starter.filters']);
  app.run(["$ionicPlatform", "$rootScope", "$state", "$cordovaFileTransfer",
    "$cordovaFileOpener2", "$ionicHistory", "$window", "$location","dao","$http",function ($ionicPlatform, $rootScope, $state, $cordovaFileTransfer,
                   $cordovaFileOpener2, $ionicHistory, $window, $location,dao,$http) {
      //注册安卓返回按钮
      if(ionic.Platform.isAndroid()){
        $ionicPlatform.registerBackButtonAction(function (e) {
          e.preventDefault();
          return false;
        }, 101);
      }
      $ionicPlatform.ready(function () {
    	  //隐藏工具栏
        if (window.cordova && window.cordova.plugins.Keyboard) {

          cordova.plugins.Keyboard.hideKeyboardAccessoryBar(false);
          //防止input获取焦点时溢出
          cordova.plugins.Keyboard.disableScroll(true);
        }


        //判断网络状态
        document.addEventListener("deviceready", function () {
          // listen for online event
          $rootScope.$on('$cordovaNetwork:online', function (event, networkState) {
            var onlineState = networkState;
            $rootScope.netError = false;
          })

          // listen for Offline event
          $rootScope.$on('$cordovaNetwork:offline', function (event, networkState) {
            var offlineState = networkState;
            $rootScope.netError = true;
          })
        }, false);
      });

      $rootScope.$on('$stateChangeStart', function (event, next, toParams, self, fromParams) {
        $rootScope.prePage = self.name;
        $rootScope.nextPage = next.name;
      })
      $rootScope.goback = function () {
        if ($ionicHistory.backView()) {
          $ionicHistory.goBack();
        }
      }
      $rootScope.callPhone = function (phoneNo) {
        $window.location.href = "tel:" + phoneNo;
      }
      $rootScope.postMessage = function(data){
    	  top.postMessage(data, window.houseMessage.origin);
      }
    }])
// 注册拦截器
/*    .config(['$httpProvider', function ($httpProvider) {
      $httpProvider.interceptors.push('myInterceptor');
    }]
  )*/
    //配置iongallery
    .config(['ionGalleryConfigProvider',function (ionGalleryConfigProvider) {
      ionGalleryConfigProvider.setGalleryConfig({
        action_label: '关闭',
        toggle: false,
        row_size: 4,
        fixed_row_size: true
      });
    }])

    .config(['$ionicConfigProvider',function ($ionicConfigProvider) {
      $ionicConfigProvider.tabs.position("bottom");
      $ionicConfigProvider.platform.android.navBar.alignTitle('center');
      $ionicConfigProvider.platform.android.tabs.style('standard');
      //禁止侧滑后退事件
      $ionicConfigProvider.views.swipeBackEnabled(false);

    }])
    // 获取$controllerProvider方便动态注册controller
    .config(['$controllerProvider', function($controllerProvider){
    	window.$controllerProvider = $controllerProvider;
    }])
  return app;
})
