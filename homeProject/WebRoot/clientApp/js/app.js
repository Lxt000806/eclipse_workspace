
define(['controller','services', 'dao','routes','directive','plugins/ngIOS9UIWebViewPatch','plugins/ion-gallery','../lib/ionic-datepicker/dist/ionic-datepicker.bundle.min','../lib/ngCordova/dist/ng-cordova.min'],function(){
  'use strict';

  var app= angular.module('starter', ['ionic','starter.controllers', 'starter.services','starter.dao','starter.routes','starter.directives', 'ionic-datepicker',
    'ngCordova', 'ion-gallery', 'ngIOS9UIWebViewPatch']);
  app.run(function ($ionicPlatform, $rootScope, $state, $cordovaFileTransfer,
                   $cordovaFileOpener2, $ionicHistory, $window, $location,dao,$http) {
      $ionicPlatform.ready(function () {
//隐藏工具栏
        if (window.cordova && window.cordova.plugins.Keyboard) {

          cordova.plugins.Keyboard.hideKeyboardAccessoryBar(false);
          //防止input获取焦点时溢出
          cordova.plugins.Keyboard.disableScroll(true);
        }

        if (window.StatusBar) {
          //显示手机状态栏
          StatusBar.styleDefault();

        }


        //判断网络状态
        document.addEventListener("deviceready", function () {
          //var type = $cordovaNetwork.getNetwork()
          //
          //var isOnline = $cordovaNetwork.isOnline()
          //
          //var isOffline = $cordovaNetwork.isOffline()

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
        }, false)
        document.addEventListener("resume", function () {
            cordova.getAppVersion(function (version) {
              checkUpdate(dao,$cordovaFileTransfer, $cordovaFileOpener2, version);
            });
        }, false);

        //检查更新
        if(typeof cordova != 'undefined'){
        if (basePath) {
          cordova.getAppVersion(function (version) {
            checkUpdate(dao,$cordovaFileTransfer, $cordovaFileOpener2, version);
          });
        }
        }
      });
      $rootScope.$on('$stateChangeStart', function (event, next, toParams, self, fromParams) {
        if (next.name == 'login'||next.name=='registerUser'||next.name=='resetPassword') {
          return;
        }
        if (!localStorage.haslogin
        ) {
          event.preventDefault();
          $state.go('login');
        }
      })
      $rootScope.goback = function () {
        if ($ionicHistory.backView()) {
          $ionicHistory.goBack();
        }
      }
      $rootScope.goback_path = function () {
        $location.path("/tab/send");
      }
      $rootScope.callPhone = function (phoneNo) {
        $window.location.href = "tel:" + phoneNo;
      }
    })
// 注册拦截器
    .config(['$httpProvider', function ($httpProvider) {
      $httpProvider.interceptors.push('myInterceptor');
    }]
  )
    //配置iongallery
    .config(function (ionGalleryConfigProvider) {
      ionGalleryConfigProvider.setGalleryConfig({
        action_label: '关闭',
        toggle: false,
        row_size: 4,
        fixed_row_size: true
      });
    })

    .config(function ($ionicConfigProvider) {
      $ionicConfigProvider.tabs.position("bottom");
      $ionicConfigProvider.platform.android.navBar.alignTitle('center');
      $ionicConfigProvider.platform.android.tabs.style('standard');
      //禁止侧滑后退事件
      $ionicConfigProvider.views.swipeBackEnabled(false);

    })
  return app;
})
