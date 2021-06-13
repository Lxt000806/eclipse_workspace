/**
 * Created by Administrator on 2016/4/11.
 */
define(function(require){
  var dao=angular.module('starter.dao', []);
  dao.factory('dao',require('dao/dao'));
  return dao;
})
