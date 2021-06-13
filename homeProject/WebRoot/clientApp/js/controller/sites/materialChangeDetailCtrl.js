/**
 * Created by Administrator on 2016/4/18.
 */
define([],function(){
  'use strict';
  //材料变更详情
  function  ctrl($scope,sitesService, $timeout, $state, $stateParams,
            $ionicModal, $rootScope, $ionicPopup) {
    $scope.goSearch = function () {
      $rootScope.materialChangeDetailFresh=undefined;
      $rootScope.materialChangeDetailFlag=false;
      $scope.goQuery();
    }

    $scope.goQuery = function (isBack) {
      if ($rootScope.materialChangeDetailFresh==undefined || $rootScope.materialChangeDetailFresh!=$stateParams.no || $rootScope.materialChangeDetailFlag) {
        if ($rootScope.materialChangeDetailFresh==undefined || $rootScope.materialChangeDetailFresh!=$stateParams.no){
          $rootScope.materialChangeDetailPageNow = 1;
          $rootScope.materialChangeDetailItems = [];
        }else{
          if (isBack){return;}
        }
        sitesService.getMaterialChangeDetail($stateParams.no,$rootScope.materialChangeDetailPageNow).success(function (data) {
          if (data && data.datas) {
            var datas = data.datas;
            for (var i = 0; i < datas.length; i++) {
              $rootScope.materialChangeDetailItems.push({
                "itemCodeDescr": datas[i].itemCodeDescr,
                "itemType2Descr": datas[i].itemType2Descr,
                "qty": datas[i].qty,
                "lineAmount": datas[i].lineAmount,
                "pk": datas[i].pk,
                "uom": datas[i].uom,
                "fixAreaDescr":datas[i].fixAreaDescr
              });
            }
            $rootScope.materialChangeDetailFlag = data.hasNext;
            $rootScope.materialChangeDetailPageNow++;
          }
          $timeout(
            function () {
              $scope.$broadcast('scroll.refreshComplete');
              $scope.$broadcast('scroll.infiniteScrollComplete');
            }, 100
          );
          $rootScope.materialChangeDetailFresh = $stateParams.no;
        });
      }
    }
  }
  ctrl.$inject=['$scope','sitesService', '$timeout', '$state', '$stateParams',
    '$ionicModal', '$rootScope', '$ionicPopup'];
  return ctrl;
})

