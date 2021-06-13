var itemReturnRebateCtrl = function($scope, wfProcInstService){
	// 初始化数据
	$scope.self = $scope;
	$scope.submitData = {};
	$scope.beforeDetailNumData = {};
	$scope.gctkIndexs = [];
	$scope.submitData.fp__tWfCust_ItemReturnRebate__0__Reason = "";
	$scope.rules = {
		fp__tWfCust_ItemReturnRebate__0__CustCode: {
			tipMessage: "客户编号为空"
		},
		fp__tWfCust_ItemReturnRebate__0__Address: {
			tipMessage: "楼盘不能为空"
		},
		fp__tWfCust_ItemReturnRebate__0__BusinessMan: {
			tipMessage: "业务员不能为空"
		},
		fp__tWfCust_ItemReturnRebate__0__DesignMan: {
			tipMessage: "设计师不能为空"
		},
		fp__tWfCust_ItemReturnRebate__0__ItemType1: {
			tipMessage: "材料类型1未选择"
		},
/*		fp__tWfCust_ItemReturnRebate__0__ItemType: {
			tipMessage: "材料品类未选择"
		},*/
		fp__tWfCust_ItemReturnRebate__0__Reason: {
			tipMessage: "退项说明不能为空"
		},
		fp__tWfCust_ItemReturnRebate__0__DisChgAmount: {
			tipMessage: "退项金额不能为空"
		}
	}
	
	$scope.conditions= {
		authorityCtrl:"1"
	}
	// 客户选择组件回掉函数
    $scope.customerCallback = function (data){
		$scope.submitData.fp__tWfCust_ItemReturnRebate__0__CustDescr = data.descr;
		$scope.submitData.fp__tWfCust_ItemReturnRebate__0__CustCode = data.code;
		$scope.submitData.fp__tWfCust_ItemReturnRebate__0__Address = data.address;
		$scope.submitData.fp__tWfCust_ItemReturnRebate__0__DesignMan = data.designManDescr;
		$scope.submitData.fp__tWfCust_ItemReturnRebate__0__BusinessMan = data.businessManDescr;
	    $scope.gctkDelAll = true;
    }
    
    $scope.typeChange = function(){
	    $scope.$emit("getOperator", {
	    	ItemType1: $scope.submitData.fp__tWfCust_ItemReturnRebate__0__ItemType1,
	    });
    }
	$scope.options = [
		{ text: "瓷砖", checked: false },
		{ text: "地板", checked: false },
		{ text: "卫浴", checked: false },
		{ text: "木门", checked: false },
		{ text: "吊顶", checked: false },
		{ text: "五金配件", checked: false },
		{ text: "开关面板", checked: false },
		{ text: "服务型产品", checked: false },
		{ text: "橱柜", checked: false },
		{ text: "衣柜", checked: false },
		{ text: "推拉门", checked: false }
    ];
    $scope.selectCallback = function(data){
		var value = "";
		if(data.data.length>0){
			for(var i = 0;i<data.data.length;i++){
				if(data.data[i].checked==true ){
					if(value !=""){
						value+=",";
					}
					value+=data.data[i].text;
				}
			}
		}
		$scope.submitData.fp__tWfCust_ItemReturnRebate__0__ItemType = value;
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
	    	ItemType1: $scope.submitData.fp__tWfCust_ItemReturnRebate__0__ItemType1,
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
itemReturnRebateCtrl.$injector = ["$scope", "wfProcInstService"];
window.$controllerProvider.register("itemReturnRebateCtrl", itemReturnRebateCtrl);