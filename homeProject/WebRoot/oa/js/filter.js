define(function(require){
  var filters=angular.module('starter.filters', []);
  filters.filter("strToHtml", ['$sce', require('filters/strToHtml')]);
  return filters;
})
