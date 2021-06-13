var custGiftAppCtrl = function($scope, dao){
	// 初始化数据
	$scope.self = $scope;
	$scope.submitData = {};
	$scope.beforeDetailNumData = {};
	$scope.gctdIndexs = [];
	$scope.rules = {
			fp__tWfCust_CustGiftApp__0__CustCode: {
				tipMessage: "客户编号不能为空"
			},
			fp__tWfCust_CustGiftApp__0__EmpCode: {
				tipMessage: "员工编号不能为空"
			},
			fp__tWfCust_CustGiftApp__0__ApproveLine: {
				tipMessage: "审批人不能为空"
			},
	}
	
	$scope.chgApproveLine = function(){
		$scope.$emit("getOperator", { 
    		ApproveLine: $scope.submitData.fp__tWfCust_CustGiftApp__0__ApproveLine,
    	});
	}
	
    // 开始提交事件监听 （每个流程必须）
    $scope.$on("wfProcInstDoSubmit", function(){
		$scope.$emit("wfProcInstSendDatas", {
			datas: $scope.submitData,
			rules: $scope.rules
		});
    });
    
    // 初始化数据（每个流程必须）
    $scope.$on("initData", function(event, data){
    	$scope.detailNum = data.detailDatas["tWfCust_DrawPicChgDtl"];
    	$scope.submitData = data.submitData;
    	$scope.$emit("getOperator", { 
    		ApproveLine: $scope.submitData.fp__tWfCust_CustGiftApp__0__ApproveLine,
    	});
    });
    
    $scope.$on("wfProcInstDoPass", function(){
		$scope.$emit("wfProcInstDoPassSendDatas", {
			datas: $scope.submitData,
			rules: $scope.rules
		});
    });
    
    // 请求获取初始化数据（每个流程必须）;
    $scope.$emit("beginGetWfProcInstData");
};
custGiftAppCtrl.$injector = ["$scope", "dao"];
window.$controllerProvider.register("custGiftAppCtrl", custGiftAppCtrl);
