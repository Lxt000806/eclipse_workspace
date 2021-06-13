/**
 * Created by Administrator on 2016/4/15.
 */
//基础需求
define([],function(){
  'use strict';
  function  ctrl($scope,sitesService, $timeout, $state, $stateParams,
            $ionicModal, $rootScope, $ionicPopup) {
    $scope.data = {};

    $scope.goSearch = function () {
      $rootScope.basicNeedsFresh=undefined;
      $rootScope.basicNeedsFlag=false;
      $scope.goQuery();
    }

    $scope.goQuery = function (isBack) {
      var fixAreaDescr = $scope.data.fixAreaDescr;
      if ($rootScope.searchData==undefined){
        $rootScope.searchData = {};
      }
      if (fixAreaDescr==undefined  && isBack){
        fixAreaDescr = $rootScope.searchData["basicNeeds_fixAreaDescr_"+$stateParams.code];
      }else{
        $rootScope.searchData["basicNeeds_fixAreaDescr_"+$stateParams.code] = fixAreaDescr;
      }
      $scope.data.fixAreaDescr = fixAreaDescr;

      if ($rootScope.basicNeedsFresh==undefined || $rootScope.basicNeedsFresh!=$stateParams.code || $rootScope.basicNeedsFlag) {
        if ($rootScope.basicNeedsFresh==undefined || $rootScope.basicNeedsFresh!=$stateParams.code ){
          $rootScope.basicNeedsPageNow = 1;
          $rootScope.basicNeedsItems = [];
        }else{
          if (isBack){return;}
        }

        sitesService.getBaseItemReqList($stateParams.code,fixAreaDescr,
          $rootScope.basicNeedsPageNow).success(function (data) {
            if (data && data.datas) {
              var datas = data.datas;
              for (var i = 0; i < datas.length; i++) {
                $rootScope.basicNeedsItems.push({
                  "baseItemDescr": datas[i].baseItemDescr,
                  "fixAreaDescr": datas[i].fixAreaDescr,
                  "qty": datas[i].qty,
                  "uom": datas[i].uom,
                  "custCode": datas[i].custCode,
                  "lineAmount": datas[i].lineAmount
                });
              }
              $rootScope.basicNeedsFlag = data.hasNext;
              $rootScope.basicNeedsPageNow++;
            }
            $timeout(
              function () {
                $scope.$broadcast('scroll.refreshComplete');
                $scope.$broadcast('scroll.infiniteScrollComplete');
              }, 100
            );
            $rootScope.basicNeedsFresh = $stateParams.code;
          });
      }
    }
  }
  ctrl.$inject=['$scope','sitesService', '$timeout', '$state', '$stateParams',
    '$ionicModal', '$rootScope', '$ionicPopup'];
  return ctrl;
})
