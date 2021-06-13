/**
 * Created by Administrator on 2016/4/15.
 */
//基础变更详情
define([],function(){
  'use strict';
  function  ctrl($scope,sitesService, $timeout, $state, $stateParams,
            $ionicModal, $rootScope, $ionicPopup) {
    $scope.goSearch = function () {
      $rootScope.basicChangeDetailFresh=undefined;
      $rootScope.basicChangeDetailFlag=false;
      $scope.goQuery();
    }

    $scope.goQuery = function (isBack) {
      if ($rootScope.basicChangeDetailFresh==undefined || $rootScope.basicChangeDetailFresh!=$stateParams.no || $rootScope.basicChangeDetailFlag) {
        if ($rootScope.basicChangeDetailFresh==undefined || $rootScope.basicChangeDetailFresh!=$stateParams.no){
          $rootScope.basicChangeDetailPageNow = 1;
          $rootScope.basicChangeDetailItems = [];
        }else{
          if (isBack){return;}
        }
        sitesService.getBaseItemChgDetailList($stateParams.no,$rootScope.basicChangeDetailPageNow).success(function (data) {
          if (data && data.datas) {
            var datas = data.datas;
            for (var i = 0; i < datas.length; i++) {
              $rootScope.basicChangeDetailItems.push({
                "baseItemDescr": datas[i].baseItemDescr,
                "fixAreaDescr": datas[i].fixAreaDescr,
                "qty": datas[i].qty,
                "lineAmount": datas[i].lineAmount,
                "uom": datas[i].uom
              });
            }
            $rootScope.basicChangeDetailFlag = data.hasNext;
            $rootScope.basicChangeDetailPageNow++;
          }
          $timeout(
            function () {
              $scope.$broadcast('scroll.refreshComplete');
              $scope.$broadcast('scroll.infiniteScrollComplete');
            }, 100
          );
          $rootScope.basicChangeDetailFresh = $stateParams.no;
        });
      }
    }
  }
  ctrl.$inject=['$scope','sitesService', '$timeout', '$state', '$stateParams',
    '$ionicModal', '$rootScope', '$ionicPopup'];
  return ctrl;
})
