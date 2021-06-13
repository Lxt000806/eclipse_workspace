/**
 * Created by Administrator on 2016/4/15.
 */
define([],function(){
  'use strict';
  function ctrl($scope, $cordovaFileTransfer, $cordovaFileOpener2,dao) {
    $scope.user=localStorage.user;
    $scope.isIos=ionic.Platform.isIOS();
    $scope.checkUpdate = function () {
      cordova.getAppVersion(function (version) {
        checkUpdate(dao,$cordovaFileTransfer,$cordovaFileOpener2,version,function (response) {
          //存在新版本
          if (response.existNew) {
            dao.popup.confirm(response.downLoadRemark,function(){
              //升级
              window.open(response.downLoadUrl, '_system');
            },function(){
              //不升级
              //必须升级
              if (response.isForce) {
                //退出程序
                //ionic.Platform.exitApp();
                navigator.app.exitApp();
              } else {
                //非强制升级
                return;
              }
            }, '有家装饰已有新版本' + response.versionNo,'查看更新','暂不更新');
          } else {
            dao.popup.alert("你安装的已经是最新版本了");
          }
        })
      })
    }
  }
  ctrl.$inject=['$scope', '$cordovaFileTransfer', '$cordovaFileOpener2','dao'];
  return ctrl;
})
