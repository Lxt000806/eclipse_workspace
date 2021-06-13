/**
 * Created by zzr on 2019/5/24.
 */
define([],function(){
  'use strict';
  function ctrl($scope, $rootScope, $stateParams) {
	  $scope.$on("$ionicView.beforeEnter", function(){ 
		  $scope.type = $stateParams.type;
		  if(!$scope.type || $scope.type == ""){
			  $scope.type = "none";
		  }
	  });
  }
  ctrl.$inject=['$scope', '$rootScope', '$stateParams'];
  return ctrl;
})
