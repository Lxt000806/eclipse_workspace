var changeAddressCtrl = function($scope, dao){
	// 初始化数据
	$scope.remarks = localStorage.remarks ? localStorage.remarks.replace(/\r\n/g, "<br/>") : '';
	$scope.self = $scope;
	$scope.submitData = {};
	$scope.beforeDetailNumData = {};
	$scope.gctdIndexs = [];
	// $scope.submitData.fp__tWfCust_ChangeAddress__0__Status = "撞单/跨部门";
	$scope.submitData.fp__tWfCust_ChangeAddress__0__Reason = "";
	$scope.rules = {
		fp__tWfCust_ChangeAddress__0__Reason: {
			tipMessage: "调整原因不能为空"
		},
		gctdGroup: {
			isGroup: true,
			indexs: [],
			datas: [
		        {
					template: "fp__tWfCust_ChangeAddressDtl__?__OldAddress",
					tipMessage: "调整前不能为空"
				},
				{
					template: "fp__tWfCust_ChangeAddressDtl__?__Address",
					tipMessage: "调整后不能为空"
				},
			]
		}
	}
	$scope.conditions = {status: "2,3,4,5"};

	// 明细组件删除回调函数
    $scope.gctdDelCallback = function(index){
		delete $scope.submitData["fp__tWfCust_ChangeAddressDtl__"+index+"__OldCustCode"];
		delete $scope.submitData["fp__tWfCust_ChangeAddressDtl__"+index+"__OldDescr"];
		delete $scope.submitData["fp__tWfCust_ChangeAddressDtl__"+index+"__OldAddress"];
		delete $scope.submitData["fp__tWfCust_ChangeAddressDtl__"+index+"__Address"];
    }
	
	// 明细组件新增回调函数
    $scope.gctdAddCallback = function(index){
    	$scope.submitData["fp__tWfCust_ChangeAddressDtl__"+index+"__OldCustCode"] = "";
    	$scope.submitData["fp__tWfCust_ChangeAddressDtl__"+index+"__OldDescr"] = "";
    	$scope.submitData["fp__tWfCust_ChangeAddressDtl__"+index+"__OldAddress"] = "";
    	$scope.submitData["fp__tWfCust_ChangeAddressDtl__"+index+"__Address"] = "";
    }
    
    $scope.customerCallback = function(data, index){
		$scope.submitData["fp__tWfCust_ChangeAddressDtl__"+index+"__OldCustCode"] = data.code;
		$scope.submitData["fp__tWfCust_ChangeAddressDtl__"+index+"__OldDescr"] = data.descr;
		$scope.submitData["fp__tWfCust_ChangeAddressDtl__"+index+"__OldAddress"] = data.address;
	}

    $scope.gctdAfterDetailNum = function(){
   		$scope.submitData = $scope.beforeDetailNumData;
    };
	
    // 开始提交事件监听 （每个流程必须）
    $scope.$on("wfProcInstDoSubmit", function(){
    	$scope.rules.gctdGroup.indexs = $scope.gctdIndexs;
		$scope.$emit("wfProcInstSendDatas", {
			datas: $scope.submitData,
			rules: $scope.rules
		});
    });
    
    // 初始化数据（每个流程必须）
    $scope.$on("initData", function(event, data){
    	$scope.detailNum = data.detailDatas["tWfCust_ChangeAddressDtl"];
    	$scope.beforeDetailNumData = data.submitData;
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
changeAddressCtrl.$injector = ["$scope", "dao"];
window.$controllerProvider.register("changeAddressCtrl", changeAddressCtrl);
