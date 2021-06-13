/**
 * Created by Administrator on 2016/4/18.
 */
//领料单详情
define([],function(){
  'use strict';
  function  ctrl($scope,sitesService, $timeout, $state, $stateParams,
            $ionicModal, $rootScope, $ionicPopup) {
    $scope.goSearch = function () {
      $rootScope.itemAppDetailFresh=undefined;
      $rootScope.itemAppDetailFlag=false;
      $scope.goQuery();
    }

    $scope.goQuery = function (isBack) {
      if ($rootScope.itemAppDetailFresh==undefined || $rootScope.itemAppDetailFresh!=$stateParams.no || $rootScope.itemAppDetailFlag) {
        if($rootScope.itemAppDetailFresh==undefined || $rootScope.itemAppDetailFresh!=$stateParams.no){
          $rootScope.itemAppDetailPageNow = 1;
          $rootScope.itemAppDetailItems = [];
        }else{
          if (isBack){return;}
        }
        sitesService.getItemAppDetailList($stateParams.no,$rootScope.itemAppDetailPageNow).success(function (data) {
          if (data && data.datas) {
            $scope.itemApp = {};
            $scope.itemApp.date = data.date;
            $scope.itemApp.confirmDate = data.confirmDate;
            $scope.itemApp.sendDate = data.sendDate;
            $scope.itemApp.arriveDate = data.arriveDate;
            $scope.itemApp.remarks = data.remarks;
            var datas = data.datas;
            for (var i = 0; i < datas.length; i++) {
              $rootScope.itemAppDetailItems.push({
                "itemCodeDescr": datas[i].itemCodeDescr,
                "qty": datas[i].qty,
                "sendQty": datas[i].sendQty,
                "pk": datas[i].pk,
                "uom": datas[i].uom
              });
            }
            $rootScope.itemAppDetailFlag = data.hasNext;
            $rootScope.itemAppDetailPageNow++;
          }
          $timeout(
            function () {
              $scope.$broadcast('scroll.refreshComplete');
              $scope.$broadcast('scroll.infiniteScrollComplete');
            }, 100
          );
          $rootScope.itemAppDetailFresh = $stateParams.no;
        });
      }
    }
  }
  ctrl.$inject=['$scope','sitesService', '$timeout', '$state', '$stateParams',
    '$ionicModal', '$rootScope', '$ionicPopup'];
  return ctrl;
})
