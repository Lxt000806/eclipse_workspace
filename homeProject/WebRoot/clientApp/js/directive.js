define(function(require){
  var directives=angular.module('starter.directives', []);
  directives.directive('zoom',require('directive/zoom'));
  return directives;
})
