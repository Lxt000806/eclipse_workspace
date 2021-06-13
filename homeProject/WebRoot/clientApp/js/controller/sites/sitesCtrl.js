/**
 * Created by xzp on 2016/9/7.
 */
define([],function(){
  'use strict';
  function ctrl($scope,sitesService, $timeout, $state, $stateParams,
                $ionicModal, $rootScope, $ionicPopup, $window,dao) {
    $scope.customer = [];
    $scope.payInfoItems = [];
    $scope.costInfoItems = [];
    $scope.gxrItems = [];
    $scope.payInfoDetailItems = [];
    $scope.payInfoDetailCount = 0;
    $scope.costAllAmountJs = 0;
    $scope.goSearch = function (isBack,address,stakeholder,haveGd) {
      $rootScope.sitesFresh=undefined;
      $rootScope.sitesFlag=false;
      $scope.goQuery(isBack,address,stakeholder,haveGd);
    }
    dao.modal.init($scope, "templates/modals/sites-search.html", "slide-in-left",function(){
      if(!$rootScope.sitesInfo.haveGd){
        $rootScope.sitesInfo.haveGd='0';
      }
      $scope.modal.show();
    });
    //工地信息、付款信息、干系人信息
    $scope.goQuery = function (isBack,address,stakeholder,haveGd) {
      if ($rootScope.sitesFresh==undefined || $rootScope.sitesFlag) {
        if ($rootScope.sitesFresh==undefined){
          $rootScope.sitesPageNow = 1;
          $rootScope.sitesItems = [];
        }else{
          if (isBack){return;}//返回后再进来不刷新
        }
        sitesService.getCustomerListByCustRight($rootScope.sitesPageNow,address,stakeholder,haveGd).success(function (data) {
          if (data && data.datas) {
            var datas = data.datas;
            for (var i = 0; i < datas.length; i++) {
              $rootScope.sitesItems.push({
                "address": datas[i].address,
                "code": datas[i].code,
                "descr": datas[i].descr,
                "mobile1": datas[i].mobile1
              });
            }
            $rootScope.sitesFlag = data.hasNext;
            $rootScope.sitesPageNow++;
          }
          $timeout(
            function () {
              $scope.$broadcast('scroll.refreshComplete');
              $scope.$broadcast('scroll.infiniteScrollComplete');
            }, 100
          );
          $rootScope.sitesFresh = 1;
        });

      }
    }

    $scope.goCustomerDetailUi=function(code){
      $state.go('sites-detail',{code:code});
    }
    $scope.goProgressUi=function(code){
      $state.go('sites-detail-progress-detailList',{clientId:code});
    }
    $scope.goPayInfoUi=function(code){
      $state.go('sites-detail-payInfo',{code:code});
    }

    $scope.goCostInfoUi=function(code){
      $state.go('sites-detail-costInfo',{code:code});
    }

    $scope.goGxrDetailUi=function(code){
      $state.go('sites-detail-gxr',{code:code});
    }

    $scope.goCustomerMessageUi=function(code){
      $state.go('sites-detail-message',{code:code});
    }

    $scope.goBasicNeedsUi=function(code){
      $state.go('sites-detail-basicNeeds',{code:code});
    }

    $scope.goBasicChangeUi=function(code){
      $state.go('sites-detail-basicChange',{code:code});
    }

    $scope.goMaterialNeedsUi=function(code){
      $state.go('sites-detail-materialNeeds',{code:code});
    }

    $scope.goMaterialChangeUi=function(code){
      $state.go('sites-detail-materialChange',{code:code});
    }

    $scope.goItemAppUi=function(code){
      $state.go('sites-detail-itemApp',{code:code});
    }

    $scope.getCustomerDetail = function () {
      if(!$scope.custCodeList){
        $scope.custCodeList=JSON.parse(localStorage.getItem('custCodeList'));
      }
      $scope.custCode=$stateParams.code;
      if ($rootScope.customerCache==undefined){
        $rootScope.customerCache={};
      }
      if ($rootScope.customerCache[$stateParams.code]==undefined){
        sitesService.getCustomerDetail($stateParams.code).success(function (data) {
          if (data){
            $scope.customer=data;
            $scope.footerData = data;
            $rootScope.customerCache[$stateParams.code]=data;
          }
        });
      }else{
        $scope.customer=$rootScope.customerCache[$stateParams.code];
        $scope.footerData = $scope.customer;
      }
    }

    $scope.getCustomerDetailPayInfo = function () {
      sitesService.getCustomerDetailPayInfo($stateParams.code).success(function (data) {
        if (data){
          $scope.payInfoItems=data;
          $scope.footerData = data;
        }
      })
      sitesService.getPayInfoDetailList($stateParams.code).success(function (data) {
        if (data){
          $scope.payInfoDetailItems=data.datas;
          $scope.payInfoDetailCount=data.recordSum;
        }
      })
    }

    $scope.getCustomerDetailCostInfo = function () {
      sitesService.getCustomerDetailCostInfo($stateParams.code).success(function (data) {
        if (data){
          $scope.costInfoItems=data.datas;
          $scope.footerData = data.datas;
          $scope.costAllAmountJs=data.allAmountJs;
        }
      })
    }

    $scope.getGxrList = function () {
      sitesService.getGxrList($stateParams.code).success(function (data) {
        if (data){
          $scope.gxrItems=data.datas;
          $scope.footerData = data.datas;
        }
      })
    }
    $scope.sitesSearch=function(address,stakeholder,haveGd){
      $rootScope.sitesInfo.search='';
       if(address)      $rootScope.sitesInfo.search+='楼盘：'+address+'；';
      if(stakeholder)   $rootScope.sitesInfo.search+='干系人：'+stakeholder+'；';
      if(haveGd=='0')  $rootScope.sitesInfo.search+='不包含完工客户';
      if(haveGd=='1')  $rootScope.sitesInfo.search+='包含完工客户';
      $rootScope.sitesFresh=undefined;
      $rootScope.sitesFlag=false;
      $scope.goQuery('',address,stakeholder,haveGd);
      $scope.modal.hide();
    }
    $scope.comboChange=function(custCode){
      localStorage.initCustCode=custCode;
      sitesService.getCustomerDetail(custCode).success(function (data) {
          $scope.customer=data;
      });
    }
  }
  ctrl.$inject=['$scope','sitesService', '$timeout', '$state', '$stateParams',
    '$ionicModal', '$rootScope', '$ionicPopup', '$window','dao'];
  return ctrl;
})
