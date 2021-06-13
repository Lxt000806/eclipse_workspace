/**
 * Created by xzp on 2016/8/3.
 */
define([],function(){
  'use strict';
  function ctrl($scope,confirmService,dao,$ionicActionSheet,$timeout){
    $scope.initModal=function(){
      dao.modal.init($scope, "templates/editPhoto.html", "fade-in", function (photo, event) {
        var index =parseInt(event.target.id)+$scope.rowNum*4;
        //var url = photo[index].src;
        $scope.photo=photo;
        //$scope.url = url;
        $scope.index = index;
        $scope.modal.show();
      });
    }
    $scope.initSlideIndex=function(photo,event){
      if($scope.modal){
        //删除模板
        $scope.modal.remove();
      }
      $scope.index=parseInt(event.target.id)+$scope.rowNum*4;
      $scope.initModal();
      //初始化后打开模板
      $timeout(function(){
        $scope.openModal(photo,event)
      },300);
    }
    $scope.showActionsheet = function (index) {
      $ionicActionSheet.show({
        titleText: '请选择操作',
//            buttons: [
//                {
//                    text: 'Share'
//                },
//                {
//                    text: 'Move'
//                }
//            ],
        destructiveText: '删除',
        cancelText: '取消',
        cancel: function () {
          return true;
        },
//            buttonClicked: function (index) {
//                console.log('BUTTON CLICKED', index);
//                return true;
//            },
        destructiveButtonClicked: function () {
          confirmService.prjProgPhotoDelete($scope.photo[index].photoName).success(function () {
            //删除对应的照片名字和url
            $scope.photo.splice(index, 1);
            //更新删除后的url
            if ($scope.photo.length == $scope.index) {
              $scope.index--;
            }
            //if ($scope.index >= 0) {
            //  $scope.url = $scope.photo[$scope.index].src;
            //} else {
            //  delete $scope['url'];
            //
            //}
          })
          return true;
        }
      });
    }
    $scope.slideHasChanged=function(index){
      $scope.index=index;
    }
    $scope.$on('rowNum', function (event, data) {
      $scope.rowNum=data;
    });
  }
  ctrl.$inject=['$scope','confirmService','dao','$ionicActionSheet','$timeout'];
  return ctrl;
})
