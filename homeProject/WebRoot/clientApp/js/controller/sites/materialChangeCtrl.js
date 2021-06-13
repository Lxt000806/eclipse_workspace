/**
 * Created by Administrator on 2016/4/15.
 */
//材料变更
define([],function(){
  'use strict';
  function  ctrl($scope,sitesService, $timeout, $state, $stateParams,
            $ionicModal, $rootScope, $ionicPopup) {
    $scope.goMaterialChangeDetailUi=function(no){
      $state.go('sites-detail-materialChange-detail',{no:no});
    }

    $scope.goSearch = function () {
      $rootScope.materialChangeFresh=undefined;
      $rootScope.materialChangeFlag=false;
      $scope.goQuery();
    }

    $scope.goQuery = function (isBack) {
      if ($rootScope.materialChangeFresh==undefined || $rootScope.materialChangeFresh!=$stateParams.code || $rootScope.materialChangeFlag) {
        if ($rootScope.materialChangeFresh==undefined || $rootScope.materialChangeFresh!=$stateParams.code){
          $rootScope.materialChangePageNow = 1;
          $rootScope.materialChangeItems = [];
        }else{
          if (isBack){return;}
        }
        sitesService.getCustomerDetailMaterialChange($stateParams.code,$rootScope.materialChangePageNow).success(function (data) {
          if (data && data.datas) {
            var datas = data.datas;
            for (var i = 0; i < datas.length; i++) {
              $rootScope.materialChangeItems.push({
                "amount": datas[i].amount,
                "custCode": datas[i].custCode,
                "date": datas[i].date,
                "itemType1Descr": datas[i].itemType1Descr,
                "statusDescr": datas[i].statusDescr,
                "no": $.trim(datas[i].no)
              });
            }
            $rootScope.materialChangeFlag = data.hasNext;
            $rootScope.materialChangePageNow++;
          }
          $timeout(
            function () {
              $scope.$broadcast('scroll.refreshComplete');
              $scope.$broadcast('scroll.infiniteScrollComplete');
            }, 100
          );
          $rootScope.materialChangeFresh = $stateParams.code;
        });
      }
    }
  }
  ctrl.$inject=['$scope','sitesService', '$timeout', '$state', '$stateParams',
    '$ionicModal', '$rootScope', '$ionicPopup'];
  return ctrl;
})
