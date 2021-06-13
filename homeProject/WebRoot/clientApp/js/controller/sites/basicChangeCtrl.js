/**
 * Created by Administrator on 2016/4/15.
 */
//基础变更
define([],function(){
  'use strict';
  function ctrl($scope,sitesService, $timeout, $state, $stateParams,
            $ionicModal, $rootScope, $ionicPopup) {
    $scope.goBasicChangeDetailUi=function(no){
      $state.go('sites-detail-basicChange-detail',{no:no});
    }

    $scope.goSearch = function () {
      $rootScope.basicChangeFresh=undefined;
      $rootScope.basicChangeFlag=false;
      $scope.goQuery();
    }

    $scope.goQuery = function (isBack) {
      if ($rootScope.basicChangeFresh==undefined || $rootScope.basicChangeFresh!=$stateParams.code || $rootScope.basicChangeFlag) {
        if ($rootScope.basicChangeFresh==undefined || $rootScope.basicChangeFresh!=$stateParams.code){
          $rootScope.basicChangePageNow = 1;
          $rootScope.basicChangeItems = [];
        }else{
          if (isBack){return;}
        }
        sitesService.getBaseItemChgList($stateParams.code,$rootScope.basicChangePageNow).success(function (data) {
          if (data && data.datas) {
            var datas = data.datas;
            for (var i = 0; i < datas.length; i++) {
              $rootScope.basicChangeItems.push({
                "amount": datas[i].amount,
                "custCode": datas[i].custCode,
                "date": datas[i].date,
                "statusDescr": datas[i].statusDescr,
                "no": $.trim(datas[i].no)
              });
            }
            $rootScope.basicChangeFlag = data.hasNext;
            $rootScope.basicChangePageNow++;
          }
          $timeout(
            function () {
              $scope.$broadcast('scroll.refreshComplete');
              $scope.$broadcast('scroll.infiniteScrollComplete');
            }, 100
          );
          $rootScope.basicChangeFresh = $stateParams.code;
        });
      }
    }
  }
  ctrl.$inject=['$scope','sitesService', '$timeout', '$state', '$stateParams',
    '$ionicModal', '$rootScope', '$ionicPopup'];
  return ctrl;
})
