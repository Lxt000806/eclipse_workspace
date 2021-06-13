define([
    'controller/account/accountController',
    'controller/set/setController',
    'controller/sites/sitesController',
    'controller/common/commonController'
  ],
  function (require) {
    'use strict';
    var controllers = angular.module('starter.controllers', [
      'account.controller',
      'set.controller',
      'sites.controller',
      'common.controller'
    ]);
    return controllers;
  })






