/**
 * Created by Administrator on 2016/4/18.
 */
define([],function(){
  'use strict';
  function  service($q, $http,dao) {

    return {
      loginUser: function (name, pw) {
        var deferred = $q.defer();
        var promise = deferred.promise;
        var loginResult = new Object();
        var data = new Object();
        data.portalAccount = name;
        data.portalPwd = pw;
        data.portalType = 1;
        data.callback = "JSON_CALLBACK";
        var url = basePath + "client/custAccount/getCustAccountDetail";
        var requestData = {params: data}
        $http.jsonp(url, requestData)
          .success(function (response) {

            loginResult = response;
            if (loginResult.success) {
//                                localStorage.userid = loginResult.UserId;
              //设置客户端的别名，用于定向接收消息的推送
              if (window.plugins && window.plugins.jPushPlugin){
                window.plugins.jPushPlugin.setAlias(name);
              }
              deferred.resolve(loginResult);
            } else {
              deferred.reject('Wrong credentials.');
            }
          }).error(function () {
            deferred.reject('无法连接服务器');
          })
        definePromise(promise);
        return promise;
      },
      logout:function(){
        var data = new Object();
        data.portalType = 1;
        data.callback = "JSON_CALLBACK";
        return dao.https.jsonp("client/custAccount/loginOutCustAccount",data);
      },
      updatePassword: function (oldPwd, newPwd, confirmPwd) {
        var defer = $q.defer();
        var promise = defer.promise;
        var data = new Object();
        data.portalType = 1;
        data.phone = localStorage.haslogin;
        data.oldPwd = oldPwd;
        data.newPwd = newPwd;
        data.confirmPwd = confirmPwd;
        data.callback = "JSON_CALLBACK";
        var url = basePath + "client/custAccount/updateCustAccountPwd";
        var requestData = {params: data};
        $http.jsonp(url, requestData).success(function (response) {
          if (response.success) {
            defer.resolve('设置新密码成功');
          } else {
            defer.reject('密码输入错误');
          }
        }).error(function () {
          defer.reject('设置新密码失败,请确认网络正常');
        })
        definePromise(promise);
        return promise;
      },
      getSmsPassword:function(phone,type){
        var data = {};
        data.portalAccount = phone;
        data.smsType = type;
        data.callback = 'JSON_CALLBACK';
        return dao.https.jsonp('client/sms/getCustAccountSmsPassword', data,"","",true);
      },
      register:function(phone,pwd,yzm,type,rpwd){
        var data={};
        data.portalAccount = phone;
        data.portalPwd = pwd;
        data.smsPwd=yzm;
        data.smsType =type;
        data.confirmPwd=rpwd;
        data.callback="JSON_CALLBACK";
        return dao.https.jsonp('client/custAccount/registerCustAccount', data);
      },
      resetPassword:function(phone,pwd,yzm,type,rpwd){
        var data={};
        data.portalAccount = phone;
        data.portalPwd = pwd;
        data.smsPwd=yzm;
        data.smsType =type;
        data.confirmPwd=rpwd;
        data.callback="JSON_CALLBACK";
        return dao.https.jsonp('client/custAccount/resetCustAccountPwd', data);
      }
    }
  }
  service.$inject=['$q', '$http','dao'];
  return service;
})
