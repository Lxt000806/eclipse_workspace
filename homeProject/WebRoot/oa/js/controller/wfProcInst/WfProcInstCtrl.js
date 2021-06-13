/**
 * Created by zzr on 2019/5/24.
 */
define([],function(){
  'use strict';
  function ctrl($scope, loginService, $rootScope, $state) {
    
    $scope.postMessage = function(){
    	console.log($rootScope.origion);
    	top.postMessage({
    		
    		info: "from Account controller"
    	}, $rootScope.origion);
    }
    
    $scope.login = function(){
    	var data = {};
    	data.portalType = 1;
        data.id="00220";
        data.callback = "JSON_CALLBACK";
        dao.https.jsonp('client/prjProgCheck/getPrjProgCheckList', data).success(function(data){
        	dao.popup.alert(JSON.stringify("zzr huancun ceshi"+data));
        	$state.go("error", {type: 123});
        });
    }
  }
  ctrl.$inject=['$scope', '$rootScope', '$state'];
  return ctrl;
})
