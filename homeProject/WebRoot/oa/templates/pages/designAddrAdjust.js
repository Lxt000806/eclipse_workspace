var designAddrAdjustCtrl = function($scope, dao){
	// 初始化数据
	$scope.self = $scope;
	$scope.submitData = {};
	$scope.rules = {
		fp__tWfCust_DesignAddrAdjust__0__DesignAddress:{
			tipMessage: "调整前不能为空"
		},
		fp__tWfCust_DesignAddrAdjust__0__Address:{
			tipMessage: "调整后不能为空"
		}
	}
	$scope.conditions = {authorityCtrl:"1"};

    
    $scope.designAddrAdjustCallback = function(data){
		$scope.submitData["fp__tWfCust_DesignAddrAdjust__0__CustCode"] = data.code;
		$scope.submitData["fp__tWfCust_DesignAddrAdjust__0__CustDescr"] = data.descr;
		$scope.submitData["fp__tWfCust_DesignAddrAdjust__0__Address"] = data.address;
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
    	$scope.submitData = data.submitData;
    	$scope.$emit("getOperator", {
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
    $scope.$emit("getOperator", {});
};
designAddrAdjustCtrl.$injector = ["$scope", "wfProcInstService"];
window.$controllerProvider.register("designAddrAdjustCtrl", designAddrAdjustCtrl);
