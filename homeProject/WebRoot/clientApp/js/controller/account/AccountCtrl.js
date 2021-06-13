/**
 * Created by Administrator on 2016/4/15.
 */
define([],function(){
  'use strict';
  function ctrl($scope, loginService, $ionicLoading, $window, $rootScope, $state, $ionicHistory, dao, $timeout,$interval) {
//        $scope.getCityList=function(){
//            loginService.getCityList().success(function(data){
//                 $scope.cityItem=data;
//                }
//            ).error(function(data){
//                    alert(data);
//                })
//        }
    $scope.CityList = CityList;
    $scope.updatePassword = function () {
      if (!$scope.data.oldPassword) {
        dao.popup.alert("旧密码不能为空");
      } else if ($scope.data.newPassword !=$scope.data.repeatPassword) {
        dao.popup.alert('两次填写的密码不一致');
      }
      else if ($scope.data.newPassword.length < 6) {
        dao.popup.alert('密码必须是6位(含6位)以上的数字、字符组合');
      } else {
        //输入规范发生请求
        loginService.updatePassword($scope.data.oldPassword, $scope.data.newPassword, $scope.data.repeatPassword).success(function (data) {
          dao.popup.alert(data, function () {
            $state.go('sites-detail');
          })
        }).error(function (data) {
          dao.popup.alert(data)
        })
      }

    }
    $scope.$on('$ionicView.beforeEnter', function () {
      var data = {};
      data.username = localStorage.username;
      data.password = localStorage.password;
      data.city = localStorage.city;
      $scope.data = data;
    })

    //说明已登陆
//        if (localStorage.haslogin == 1) {
//            $ionicLoading.show({
//                template: '数据加载中...'
//            });
//
//
//        }

//if(localStorage.haslogin==1){
//    window.open("#/tab/progress","_self");
//}
    $scope.hideDefault = function () {
      $("#city").remove();
      localStorage.city = CityList[0].code;
      basePath = CityList[0].path;
    }
    $scope.change = function (x) {
      localStorage.city = x;
      for (var i = 0; i < CityList.length; i++) {
        if (x == CityList[i].code) {
          basePath = CityList[i].path;
          break;
        }
      }
    }
    $scope.login = function () {
      if ($rootScope.netError) {
        dao.popup.alert("", "", '网络故障，请检查您的网络！');
        return;
      }
      if (!localStorage.city) {
        dao.popup.alert('请选择城市', "", '登录失败');
        return;
      }
      if (!$scope.data.username || !$scope.data.password) {
        dao.popup.alert('用户名或密码不能为空', "", '登录失败');
        return;
      }

      loginService.loginUser($scope.data.username, $scope.data.password).success(function (data) {
        //登录成功
        localStorage.haslogin = $scope.data.username;
        localStorage.user = data.descr;
        localStorage.username = $scope.data.username;
        localStorage.password = $scope.data.password;
        localStorage.initCustCode=data.custCodeList[0].code;
        localStorage.setItem('custCodeList',JSON.stringify(data.custCodeList))
        if ($scope.data.password != '00000') {
          $state.go('sites-detail',{code:data.custCodeList[0].code});
        } else {
          $state.go('updatePassword');
          dao.popup.alert('请修改初始密码');
        }

        //$window.location.reload(true);
//                $ionicLoading.show({
//                    template: '数据加载中...'
//                });

      }).error(function (data) {
        if (data == '无法连接服务器') {
          dao.popup.alert("", "", data + '，请稍后再试！');
        } else {
          localStorage.haslogin = 0;
          dao.popup.alert('请检查您填写的登陆信息！', "", '登录失败');
        }
      });
    }
    $scope.logout = function () {
      dao.popup.confirm('确定注销吗？', function () {
        loginService.logout();
        localStorage.removeItem('haslogin');
        deleteScope($rootScope);
        $ionicHistory.clearCache();
        $state.go('login');
      }, "", '用户退出');
    }
    //验证码倒计时
    var motiontime;
    $scope.getMotionTime = function(id){
      var start=59;
      motiontime = $interval(
        function() {
           $("#"+id).children().text('重新获取（'+start--+'）');
        },
        1000
      );
    };
    $scope.getSmsPassword=function(phone,type,id){
      var  reg = /^[0-9]{11}$/gi;
      if (!localStorage.city) {
        dao.popup.alert('请选择城市');
        return;
      }
      if(!phone){
      dao.popup.alert("请输入手机号");
      return;
     }
      if(phone.match(reg)==null){
        dao.popup.alert('请输入正确的手机号');
        return;
      }
      var len=(phone+"").length;
      if(len!=11){
        dao.popup.alert("请输入正确的手机号码");
        return;
      }
      loginService.getSmsPassword(phone,type).success(function(data){
       if(data.success==false){
         dao.popup.alert(data.returnInfo);
         return
       }
        //短信发送成功60s内不能在发请求
        $("#"+id).attr("disabled", "disabled");
        //开启倒计时
        $scope.getMotionTime(id);
        $timeout(function(){
          $("#"+id).removeAttr("disabled");
          $interval.cancel(motiontime);//停止掉倒计时
          $("#"+id).children().text("获取验证码");
        },60000);
      })
    }

    $scope.smsWork=function(phone,pwd,yzm,type,rpwd){
      var  reg = /^[0-9]{11}$/gi;
      if (!localStorage.city) {
        dao.popup.alert('请选择城市');
        return;
      }
      if(!yzm){
       dao.popup.alert('请输入验证码');
        return;
      }
      if(!pwd||pwd.length<6){
        dao.popup.alert('请输入长度6位以上密码');
        return;
      }
      if(pwd!=rpwd){
        dao.popup.alert('两次输入的密码不一致');
        return;
      }
      if(!phone){
        dao.popup.alert('请输入手机号');
        return;
      }
      if(phone.match(reg)==null){
        dao.popup.alert('请输入正确的手机号');
        return;
      }
         //注册
         if(type==1){
           loginService.register(phone,pwd,yzm,type,rpwd).success(function(data){
           if(data.success){
             dao.popup.alert("账号成功激活",function(){
               $state.go('login');
             })
           }else{
             dao.popup.alert(data.returnInfo);
           }
           }).error(function(){
             dao.popup.alert("请求服务失败，请稍后再试");
           })
         }
         if(type==2){
           loginService.resetPassword(phone,pwd,yzm,type,rpwd).success(function(data){
             if(data.success){
               dao.popup.alert("密码修改成功",function(){
                 $state.go('login');
               })
             }else{
               dao.popup.alert(data.returnInfo);
             }
           }).error(function(){
             dao.popup.alert("请求服务失败，请稍后再试");
           })
         }
    }
  }
  ctrl.$inject=['$scope', 'loginService', '$ionicLoading', '$window', '$rootScope', '$state', '$ionicHistory', 'dao', '$timeout','$interval'];
  return ctrl;
})
