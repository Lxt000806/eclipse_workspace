/*global define, require, console, cordova, navigator */

define(['app'], function (app) {
  'use strict';
  angular.element(document).ready(function () {
    var startApp = function () {
      angular.bootstrap(document, [app.name]);
    }

    var onDeviceReady = function () {
      angular.element().ready(function () {
        startApp();
      });
    }

    if (typeof cordova === 'undefined') {
      startApp();
    } else {
      document.addEventListener("deviceready", onDeviceReady, false);
    }
  });
});
