define(
  [
   'controller/wfProcInst/wfProcInstController',
   'controller/error/errorController'
  ],
  function (require) {
    'use strict';
    var controllers = angular.module('starter.controllers',
      [
       'wfProcInst.controller',
       'error.controller'
      ]);
    return controllers;
  })






