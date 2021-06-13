/**
 * Created by zzr on 2019/5/24.
 */
define(function(require){
  var dao=angular.module('starter.dao', []);
  dao.factory('dao',require('dao/dao'));
  return dao;
})
