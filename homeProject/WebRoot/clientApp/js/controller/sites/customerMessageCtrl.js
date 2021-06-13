/**
 * Created by Administrator on 2016/4/15.
 */
//客户消息
define([],function(){
  'use strict';
  function ctrl($scope,sitesService, $timeout, $state, $stateParams,
                $ionicModal, $rootScope, $ionicPopup) {
    $scope.data = {};

    $scope.goSearch = function () {
      $rootScope.customerMessageFresh=undefined;
      $rootScope.customerMessageFlag=false;
      $scope.goQuery();
    }

    $scope.goQuery = function (isBack) {
      if ($rootScope.customerMessageFresh==undefined || $rootScope.customerMessageFresh!=$stateParams.code || $rootScope.customerMessageFlag) {
        if ($rootScope.customerMessageFresh==undefined || $rootScope.customerMessageFresh!=$stateParams.code ){
          $rootScope.customerMessagePageNow = 1;
          $rootScope.customerMessageItems = [];
        }else{
          if (isBack){return;}
        }
        sitesService.getCustomerMessageList($stateParams.code,
          $rootScope.customerMessagePageNow).success(function (data) {
            if (data && data.datas) {
              var datas = data.datas;
              for (var i = 0; i < datas.length; i++) {
                $rootScope.customerMessageItems.push({
                  "sendDate": datas[i].sendDate,
                  "msgTextNoAddress": datas[i].msgTextNoAddress,
                  "pk": datas[i].pk
                });
              }
              $rootScope.customerMessageFlag = data.hasNext;
              $rootScope.customerMessagePageNow++;
              $scope.customerMessageCount=data.recordSum;
            }
            $timeout(
              function () {
                $scope.$broadcast('scroll.refreshComplete');
                $scope.$broadcast('scroll.infiniteScrollComplete');
              }, 100
            );
            $rootScope.customerMessageFresh = $stateParams.code;
          });
      }
    }
  }
  ctrl.$inject=['$scope','sitesService', '$timeout', '$state', '$stateParams',
    '$ionicModal', '$rootScope', '$ionicPopup'];
  return ctrl;
})
