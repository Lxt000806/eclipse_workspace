var initSignReturnCtrl = function($scope, wfProcInstService,dao){
	// 初始化数据
	$scope.self = $scope;
	$scope.submitData = {};
	$scope.beforeDetailNumData = {};
	$scope.gctkIndexs = [];
	$scope.notify = "";
	$scope.rules = {
		fp__tWfCust_InitSignReturn__0__CustCode: {
			tipMessage: "客户编号为空"
		},
		fp__tWfCust_InitSignReturn__0__Address: {
			tipMessage: "楼盘为空"
		}
	}
	
	$scope.conditions= {
		authorityCtrl:"1"
	}
	
	// 客户选择组件回掉函数
    $scope.customerCallback = function (data){
		if(data.isInitSign == "0"){
			$scope.submitData.fp__tWfCust_InitSignReturn__0__Type = "重签";
		}
		if(data.isInitSign == "1"){
			$scope.submitData.fp__tWfCust_InitSignReturn__0__Type = "草签";
		}
		$scope.getResignNotifyByCustCode(data);
    }
	
	$scope.typeChange = function(){
	    $scope.$emit("getOperator", {
	    	Type: $scope.submitData.fp__tWfCust_InitSignReturn__0__Type,
	    });
    }
	
	$scope.getResignNotifyByCustCode = function (data) {
		$scope.getResignNotify(data.code).success(function (datas) {
			if(datas.returnInfo != "notify" && data.returnInfo != ""){
				$scope.submitData.fp__tWfCust_InitSignReturn__0__CustCode = "";
		        dao.popup.alert(datas.returnInfo);
			} else {
				$scope.submitData.fp__tWfCust_InitSignReturn__0__CustCode = data.code;
				$scope.submitData.fp__tWfCust_InitSignReturn__0__CustDescr = data.descr;
				$scope.submitData.fp__tWfCust_InitSignReturn__0__Address = data.address;
			    $scope.gctkDelAll = true;
			}
		});
    }
	
	$scope.getResignNotifyConfirm = function (custCode) {
		
		$scope.getResignNotify(custCode).success(function (data) {
			if(data.returnInfo != "notify" && data.returnInfo != ""){
				$scope.notify = data.returnInfo;
			} else {
				$scope.notify = "";
			}
		});
    }
	
	$scope.getResignNotify = function (custCode){
        var data = {};
        data.code = custCode;
        data.callback = "JSON_CALLBACK";
        return dao.https.jsonp('client/customer/getResignNotify', data);
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
    	
    	if($scope.submitData.fp__tWfCust_InitSignReturn__0__CustCode != ""){
    		$scope.getResignNotifyConfirm($scope.submitData.fp__tWfCust_InitSignReturn__0__CustCode);
    	}
    	
    	/*$scope.$emit("getOperator", {
	    });*/
    });
    
    $scope.$on("wfProcInstDoPass", function(){
		$scope.$emit("wfProcInstDoPassSendDatas", {
			datas: $scope.submitData,
			rules: $scope.rules
		});
    });
    
    // 请求获取初始化数据（每个流程必须）;
    $scope.$emit("beginGetWfProcInstData");
    $scope.$emit("getOperator", {Type: ""});
};
initSignReturnCtrl.$injector = ["$scope", "wfProcInstService"];
window.$controllerProvider.register("initSignReturnCtrl", initSignReturnCtrl);