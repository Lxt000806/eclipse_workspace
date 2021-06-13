/**
 * Created by zzr on 2019/5/24.
 */
define([],function(){
	'use strict';
	function ctrl($scope, $rootScope, $state) {
		$scope.$on("$ionicView.afterEnter", function(e){
			window.houseMessage.callback = function(data){
				switch(data.type){
					case 0:{
						basePath = data.basePath;
						$rootScope.postMessage({
							returnType: 1
						});
					};break;	
					default: {
						if(data && data.m_umState == "L"){
							$state.go("wfProcInstList", {
								type: data.type
							})
						}else{
							$state.go("wfProcInstApply", {	
				                m_umState: data.m_umState,
				                procKey: data.procKey,
				                title: data.title,
				                procID: data.procID,
				                wfProcNo: data.wfProcNo
							})
						}
					}
				}
			}
			$rootScope.postMessage({
				returnType: 0
			});
		});
	}
	ctrl.$inject=['$scope', '$rootScope', '$state'];
	return ctrl;
})
