/**
 * Created by Administrator on 2016/4/15.
 */
define([],function(){
  'use strict';
  function ctrl ($scope, sitesService, $timeout, $state, $stateParams, $rootScope, $ionicPopup,dao) {
    $scope.goProgressDetailUi = function (pk, clientId) {
      $state.go('sites-detail-progress-detail', {pk: pk, clientId: clientId});
    }
    $scope.getProgressDetailList = function () {

      sitesService.getProgressDetailList($stateParams.clientId).success(
        function (data) {
          var data = data.datas;
          $scope.items = []
          for (var i = 0; i < data.length; i++) {
            if (data[i].prjStatus == '施工中') {
              var planDays = GetDateDiff(data[i].planBegin, data[i].planEnd, 'day') + 1;
              var today = transDate(new Date()) + ' 00:00:00';
              var hasDo = GetDateDiff(data[i].beginDate, today, 'day') + 1;
              var progress = '(' + hasDo + '/' + planDays + ')';
              $scope.items.push({
                "pk": data[i].pk,
                "address": data[i].address,
                "prjItemDescr": data[i].prjItemDescr,
                "beginDate": data[i].beginDate,
                "endDate": data[i].endDate,
                "status": data[i].prjStatus,
                "planBegin": data[i].planBegin,
                "planEnd": data[i].planEnd,
                "custCode": data[i].custCode,
                "progress": progress
              });
            } else {
              $scope.items.push({
                "pk": data[i].pk,
                "address": data[i].address,
                "prjItemDescr": data[i].prjItemDescr,
                "beginDate": data[i].beginDate,
                "endDate": data[i].endDate,
                "status": data[i].prjStatus,
                "planBegin": data[i].planBegin,
                "planEnd": data[i].planEnd,
                "custCode": data[i].custCode
              });
            }
          }
        }
      )
    }
    $scope.getPrjProgDate=function(){
      sitesService.getPrjProgDate($stateParams.clientId).success(function(data){
        $scope.delay=data.delay;
        $scope.remainDate=data.remainDate;
      });
    }
    $scope.hide = true;
    $scope.photoList = [];
    $scope.getProgressDetail = function () {
      sitesService.getProgressDetail($stateParams.pk).success(function (data) {
        $scope.items = [];
        $scope.items.push(data);
        for (var i = 0; i < data.photoList.length; i++) {
          var json = {};
          json.src = data.photoList[i];
          $scope.photoList.push(json);
        }
        $scope.photoSize = data.photoList.length;
      })
    }
  }
  ctrl.$inject=['$scope', 'sitesService', '$timeout', '$state', '$stateParams', '$rootScope', '$ionicPopup','dao'];
  return ctrl;
})
