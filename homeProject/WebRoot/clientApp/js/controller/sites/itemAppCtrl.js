/**
 * Created by Administrator on 2016/4/18.
 */
define([],function(){
  'use strict';
  //领料单
  function ctrl($scope,sitesService, $timeout, $state, $stateParams,
            $ionicModal, $rootScope, $ionicPopup) {
    $scope.data = {};

    $scope.goSearch = function () {
      $rootScope.itemAppFresh=undefined;
      $rootScope.itemAppFlag=false;
      $scope.goQuery();
    }

    $scope.goItemAppDetailUi=function(no){
      $state.go('sites-detail-itemApp-detail',{no:no});
    }

    $scope.goQuery = function (isBack) {
      getItemType1List($rootScope,sitesService);
      var itemType1 = $scope.data.itemType1;
      if ($rootScope.searchData==undefined){
        $rootScope.searchData = {};
      }
      if (itemType1==undefined && isBack){
        itemType1 = $rootScope.searchData["itemApp_itemType1_"+$stateParams.code];
      }else{
        $rootScope.searchData["itemApp_itemType1_"+$stateParams.code] = itemType1;
      }
      $scope.data.itemType1 = itemType1;

      if ($rootScope.itemAppFresh==undefined || $rootScope.itemAppFresh!=$stateParams.code || $rootScope.itemAppFlag) {
        if($rootScope.itemAppFresh==undefined || $rootScope.itemAppFresh!=$stateParams.code){
          $rootScope.itemAppPageNow = 1;
          $rootScope.itemAppItems = [];
        }else{
          if (isBack){return;}
        }

        sitesService.getCustomerDetailItemApp($stateParams.code,itemType1,$rootScope.itemAppPageNow).success(function (data) {
          if (data && data.datas) {
            var datas = data.datas;
            for (var i = 0; i < datas.length; i++) {
              $rootScope.itemAppItems.push({
                "confirmDate": datas[i].confirmDate,
                "date": datas[i].date,
                "sendDate": datas[i].sendDate,
                "custCode": datas[i].custCode,
                "itemType1Descr": datas[i].itemType1Descr,
                "itemType2Descr": datas[i].itemType2Descr,
                "statusDescr": datas[i].statusDescr,
                "typeDescr": datas[i].typeDescr,
                "no": $.trim(datas[i].no),
                "showDate": datas[i].showDate,
                "remarks": datas[i].remarks,
                "arriveDate":datas[i].arriveDate
              });
            }
            $rootScope.itemAppFlag = data.hasNext;
            $rootScope.itemAppPageNow++;
          }
          $timeout(
            function () {
              $scope.$broadcast('scroll.refreshComplete');
              $scope.$broadcast('scroll.infiniteScrollComplete');
            }, 100
          );
          $rootScope.itemAppFresh = $stateParams.code;
        });
      }
    }
  }
  ctrl.$inject=['$scope','sitesService', '$timeout', '$state', '$stateParams',
    '$ionicModal', '$rootScope', '$ionicPopup'];
  return ctrl;
})

