var erpAccountCtrl = function($scope, wfProcInstService){
	// 初始化数据
	$scope.self = $scope;
	$scope.submitData = {};
	$scope.beforeDetailNumData = {};
	$scope.gctkIndexs = [];
	$scope.rules = {
		fp__tWfCust_ErpAccount__0__EmpCode: {
			tipMessage: "员工编号为空"
		},
		fp__tWfCust_ErpAccount__0__Type: {
			tipMessage: "类型为空"
		}
	}
	
	$scope.conditions = {
		startMan:localStorage.czyNumber
	}
	
	// 客户选择组件回掉函数
    $scope.employeeCallback = function (data){
		$scope.submitData.fp__tWfCust_ErpAccount__0__EmpCode = data.data.number;
		$scope.submitData.fp__tWfCust_ErpAccount__0__EmpName = data.data.nameChi;
	    $scope.gctkDelAll = true;
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
erpAccountCtrl.$injector = ["$scope", "wfProcInstService"];
window.$controllerProvider.register("erpAccountCtrl", erpAccountCtrl);