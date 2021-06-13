/**
 * Created by Administrator on 2016/4/15.
 */
//材料需求
define([],function(){
  'use strict';
  function ctrl($scope,sitesService, $timeout, $state, $stateParams,
            $ionicModal, $rootScope, $ionicPopup) {
    $scope.data = {};

    $scope.goSearch = function () {
      $rootScope.materialNeedsFresh=undefined;
      $rootScope.materialNeedsFlag=false;
      $scope.goQuery();
    }

    $scope.goQuery = function (isBack) {
      getItemType1List($rootScope,sitesService);
      var itemType1 = $scope.data.itemType1;
      var itemDescr = $scope.data.itemDescr;
      if ($rootScope.searchData==undefined){
        $rootScope.searchData = {};
      }
      if (itemType1==undefined && isBack){
        itemType1 = $rootScope.searchData["materialNeeds_itemType1_"+$stateParams.code];
      }else{
        $rootScope.searchData["materialNeeds_itemType1_"+$stateParams.code] = itemType1;
      }
      if (itemDescr==undefined && isBack){
        itemDescr = $rootScope.searchData["materialNeeds_itemDescr_"+$stateParams.code];
      }else{
        $rootScope.searchData["materialNeeds_itemDescr_"+$stateParams.code] = itemDescr;
      }
      $scope.data.itemType1 = itemType1;
      $scope.data.itemDescr = itemDescr;

      //alert("materialNeedsFresh="+$rootScope.materialNeedsFresh+";materialNeedsFlag="+$rootScope.materialNeedsFlag);
      if ($rootScope.materialNeedsFresh==undefined || $rootScope.materialNeedsFresh!=$stateParams.code || $rootScope.materialNeedsFlag) {
        if ($rootScope.materialNeedsFresh==undefined || $rootScope.materialNeedsFresh!=$stateParams.code ){
          $rootScope.materialNeedsPageNow = 1;
          $rootScope.materialNeedsItems = [];
        }else{
          if (isBack){return;}
        }

        sitesService.getCustomerDetailMaterialNeeds($stateParams.code,itemType1,itemDescr,
          $rootScope.materialNeedsPageNow).success(function (data) {
            if (data && data.datas) {
              var datas = data.datas;
              for (var i = 0; i < datas.length; i++) {
                $rootScope.materialNeedsItems.push({
                  "itemDescr": datas[i].itemDescr,
                  "fixAreaDescr": datas[i].fixAreaDescr,
                  "qty": datas[i].qty,
                  "isCheckOutQty": datas[i].isCheckOutQty,
                  "custCode": datas[i].custCode,
                  "pk": datas[i].pk,
                  "uom": datas[i].uom,
                  "lineAmount": datas[i].lineAmount
                });
              }
              $rootScope.materialNeedsFlag = data.hasNext;
              $rootScope.materialNeedsPageNow++;
            }
            $timeout(
              function () {
                $scope.$broadcast('scroll.refreshComplete');
                $scope.$broadcast('scroll.infiniteScrollComplete');
              }, 100
            );
            $rootScope.materialNeedsFresh = $stateParams.code;
          });
      }
    }
  }
  ctrl.$inject=['$scope','sitesService', '$timeout', '$state', '$stateParams',
    '$ionicModal', '$rootScope', '$ionicPopup'];
  return ctrl;
})
