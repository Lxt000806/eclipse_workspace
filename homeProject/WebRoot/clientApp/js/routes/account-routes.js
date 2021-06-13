/**
 * Created by Administrator on 2016/4/18.
 */
define([],function(){
  'use strict';
  var routes=angular.module('account.routes',[]);
  routes.config(function($stateProvider){
    $stateProvider
      .state('login', {
        url: "/login",
        templateUrl: "templates/account/login.html",
        controller: 'AccountCtrl'
      })
      .state('updatePassword', {
        url: "/more/set/updatePassword",
        templateUrl: "templates/account/updatePassword.html",
        controller: "AccountCtrl"
      })
      .state('registerUser',{
        url:'/register',
        templateUrl: "templates/account/register.html",
        controller:'AccountCtrl'
      })
      .state('resetPassword',{
        url:'/resetPassword',
        templateUrl: "templates/account/resetPassword.html",
        controller:'AccountCtrl'
      })
  });
  return routes;
})
