var drawPicChgCtrl = function($scope, dao){
	// 初始化数据
	$scope.self = $scope;
	$scope.submitData = {};
	$scope.beforeDetailNumData = {};
	$scope.gctdIndexs = [];
	$scope.submitData.fp__tWfCust_DrawPicChg__0__Status = "撞单/跨部门";
	$scope.submitData.fp__tWfCust_DrawPicChg__0__Reason = "";
	$scope.submitData.fp__tWfCust_DrawPicChg__0__MonthType = "当月";
	$scope.submitData.fp__tWfCust_DrawPicChg__0__Type = "制图费调整";
	$scope.rules = {
		fp__tWfCust_DrawPicChg__0__CustCode: {
			tipMessage: "客户编号不能为空"
		},
		fp__tWfCust_DrawPicChg__0__Address: {
			tipMessage: "工地名称不能为空"
		},
		fp__tWfCust_DrawPicChg__0__Status: {
			tipMessage: "状态不能为空"
		},
		fp__tWfCust_DrawPicChg__0__MonthType: {
			tipMessage: "月份不能为空"
		},
		fp__tWfCust_DrawPicChg__0__Type: {
			tipMessage: "调整类型不能为空"
		},
		fp__tWfCust_DrawPicChg__0__BeforeAdjust: {
			tipMessage: "调整前不能为空"
		},
		fp__tWfCust_DrawPicChg__0__AfterAdjust: {
			tipMessage: "调整后不能为空"
		},
		fp__tWfCust_DrawPicChg__0__Reason: {
			tipMessage: "调整原因不能为空"
		}
	}
	 // 客户查询条件
	$scope.conditions= {
		status:"3,4",
		authorityCtrl:"1"
	}
	
	// 客户选择组件回掉函数
	$scope.customerCallback = function (data){
		$scope.submitData.fp__tWfCust_DrawPicChg__0__CustDescr = data.descr;
		$scope.submitData.fp__tWfCust_DrawPicChg__0__CustCode = data.code;
		$scope.submitData.fp__tWfCust_DrawPicChg__0__Address = data.address;
		$scope.submitData.fp__tWfCust_DrawPicChg__0__Status = data.statusDescr;
		$scope.gctdDelAll = true;
		$scope.statusChange();
	}
    
    // 开始提交事件监听 （每个流程必须）
    $scope.$on("wfProcInstDoSubmit", function(){
		$scope.$emit("wfProcInstSendDatas", {
			datas: $scope.submitData,
			rules: $scope.rules
		});
    });
    
    // 改变类型事件
    $scope.statusChange = function(){
    	$scope.$emit("getOperator", { 
    		Status:$scope.submitData.fp__tWfCust_DrawPicChg__0__Status,
    		MonthType:$scope.submitData.fp__tWfCust_DrawPicChg__0__MonthType
    	});
    }
    
    // 初始化数据（每个流程必须）
    $scope.$on("initData", function(event, data){
    	$scope.detailNum = data.detailDatas["tWfCust_DrawPicChgDtl"];
    	$scope.submitData = data.submitData;
    	$scope.$emit("getOperator", { 
    		Status:$scope.submitData.fp__tWfCust_DrawPicChg__0__Status,
    		MonthType:$scope.submitData.fp__tWfCust_DrawPicChg__0__MonthType
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
drawPicChgCtrl.$injector = ["$scope", "dao"];
window.$controllerProvider.register("drawPicChgCtrl", drawPicChgCtrl);
