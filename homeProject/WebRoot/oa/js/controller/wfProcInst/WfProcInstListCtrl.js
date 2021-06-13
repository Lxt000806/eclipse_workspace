/**
 * Created by zzr on 2019/5/24.
 */
define([],function(){
	'use strict';
	function ctrl($scope, $rootScope, $state, $stateParams, wfProcInstService) {
		$scope.goback = function(){
			$rootScope.postMessage({
				returnType: 2
			});
		}
		
		$scope.pageNo = 1;
		$scope.pageSize = 10;
		$scope.hasNext = false;
		$scope.datas = [];
		
		$scope.init = function(){
			$scope.pageNo = 1;
			$scope.pageSize = 10;
			$scope.hasNext = false;
			$scope.datas = [];
		}
		
		$scope.search = function(refreshFlag){
			$scope.init();
			$scope.getDatas(refreshFlag);
		}

		$scope.getDatas = function(refreshFlag, moreFlag){
			wfProcInstService.findWfProcInst($scope.pageNo, $scope.pageSize, $scope.type).success(function(data){
				if(data.success && data.pageNo == $scope.pageNo){
					for(var i = 0; i < data.datas.length; i++){
						data.datas[i].showSummary = "";
						if(data.datas[i].summary && data.datas[i].summary != ""){
							var summarySplit = data.datas[i].summary.split("<br/>");
							for(var j = 0; j < summarySplit.length && j < 3; j++){
								if(summarySplit[j] != ""){
									data.datas[i].showSummary += summarySplit[j] + "<br/>";
								}
							}
						}
					}
					$scope.datas = $scope.datas.concat(data.datas);
					$scope.pageNo++;
					$scope.hasNext = data.hasNext;
				}
				$scope.completeNotice(refreshFlag, moreFlag);
			}).error(function(){
				$scope.completeNotice(refreshFlag, moreFlag);
			});
		}
		
		$scope.completeNotice = function(refreshFlag, moreFlag){
			if(refreshFlag){
				$scope.$broadcast('scroll.refreshComplete');
			}

			if(moreFlag){
				$scope.$broadcast('scroll.infiniteScrollComplete');
			}
		}
		
		$scope.go = function(taskId, processInstanceId, title, procKey, wfProcNo, procId, wfProcInstNo, isEnd){
			$state.go("wfProcInstApply", {
				type: $scope.type,
				processInstanceId: processInstanceId,
				taskId: taskId,
				title: title,
				procKey: procKey,
				wfProcNo: wfProcNo,
				procID: procId,
				wfProcInstNo: wfProcInstNo,
				isEnd: isEnd
			});
		}
		
		$scope.$on("$ionicView.beforeEnter", function(){
			$scope.type = $stateParams.type;
			switch($scope.type){
				case "1": {
					$scope.title = "待我审批的";
				};break;
				case "2": {
					$scope.title = "我已审批的";
				};break;
				case "3": {
					$scope.title = "我发起的";
				};break;
				case "4": {
					$scope.title = "抄送我的";
				};break;
			}
			$scope.search();
		});
	}
	ctrl.$inject=['$scope', '$rootScope', '$state', '$stateParams', 'wfProcInstService'];
	return ctrl;
})
