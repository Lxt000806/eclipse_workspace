var stakeholderCtrl = function($scope, dao){
	// 初始化数据
	$scope.self = $scope;
	$scope.submitData = {};
	$scope.beforeDetailNumData = {};
	$scope.gxrIndexs = [];
	$scope.submitData.fp__tWfCust_Stakeholder__0__Type = "";
	$scope.submitData.fp__tWfCust_Stakeholder__0__Reason = "";
	$scope.rules = {
		fp__tWfCust_Stakeholder__0__CustCode: {
			tipMessage: "客户编号为空"
		},
		fp__tWfCust_Stakeholder__0__Status: {
			tipMessage: "状态未选择"
		},
		fp__tWfCust_Stakeholder__0__Type: {
			tipMessage: "类型未选择"
		},
		fp__tWfCust_Stakeholder__0__Reason: {
			tipMessage: "备注必填"
		},
	}
	
	$scope.conditions= {
		status:"2,3,4"
	}
	
	// 客户选择组件回掉函数
    $scope.customerCallback = function (data){
		$scope.submitData.fp__tWfCust_Stakeholder__0__CustDescr = data.descr;
		$scope.submitData.fp__tWfCust_Stakeholder__0__CustCode = data.code;
		$scope.submitData.fp__tWfCust_Stakeholder__0__Address = data.address;
		$scope.submitData.fp__tWfCust_Stakeholder__0__Status = data.statusDescr;
	    $scope.$emit("getOperator", {
	    	Status: $scope.submitData.fp__tWfCust_Stakeholder__0__Status,
	    	Type: $scope.submitData.fp__tWfCust_Stakeholder__0__Type,
	    	Roll: $scope.submitData.fp__tWfCust_Stakeholder__0__Roll
	    });
    }
    
	$scope.inputEmployee = function(data){
		if(data && data.elementid && data.elementid != ""){
			var key = data.elementid.split("_")[0];
			if(key =="OldEmp2"){
				$scope.submitData["fp__tWfCust_Stakeholder__0__OldEmpNumber2"] = data.data.number;
				$scope.submitData["fp__tWfCust_Stakeholder__0__OldEmpNameChi2"] = data.data.nameChi;
			}else if(key =="NewEmp2"){
				$scope.submitData["fp__tWfCust_Stakeholder__0__NewEmpNumber2"] = data.data.number;
				$scope.submitData["fp__tWfCust_Stakeholder__0__NewEmpNameChi2"] = data.data.nameChi;
			}else{
				$scope.submitData["fp__tWfCust_Stakeholder__0__"+key+"Number"] = data.data.number;
				$scope.submitData["fp__tWfCust_Stakeholder__0__"+key+"NameChi"] = data.data.nameChi;
			}
			
		}
    }
    
    // 改变类型事件
    $scope.typeChange = function(){
    	if($scope.submitData.fp__tWfCust_Stakeholder__0__Type =="干系人不变,部门业绩归属调整"){
	    	$scope.submitData.fp__tWfCust_Stakeholder__0__Roll="";
	    	$scope.OldEmployeeShow="";
	    	$scope.submitData["fp__tWfCust_Stakeholder__0__OldEmpNumber"] = "";
			$scope.submitData["fp__tWfCust_Stakeholder__0__OldEmpNameChi"] = "";
    	}
	    $scope.$emit("getOperator", {
	    	Status: $scope.submitData.fp__tWfCust_Stakeholder__0__Status,
	    	Type: $scope.submitData.fp__tWfCust_Stakeholder__0__Type,
	    	Roll: $scope.submitData.fp__tWfCust_Stakeholder__0__Roll
	    });
    }
    
    $scope.selRole = function(index){
		var data = new Object();
		data.roll = $scope.submitData.fp__tWfCust_Stakeholder__0__Roll;
		data.custCode = $scope.submitData.fp__tWfCust_Stakeholder__0__CustCode;
		data.callback = "JSON_CALLBACK";
		dao.https.jsonp("client/wfProcInst/getCustStakeholder", data).then(function(data){
    		if(data && data.success && data.obj){
    			$scope.OldEmployeeShow=data.obj.EmpCode+"|"+data.obj.NameChi;
    			$scope.submitData["fp__tWfCust_Stakeholder__0__OldEmpNumber"] = data.obj.EmpCode;
    			$scope.submitData["fp__tWfCust_Stakeholder__0__OldEmpNameChi"] = data.obj.NameChi;
    		}else{
    			$scope.submitData["fp__tWfCust_Stakeholder__0__OldEmpNumber"] = "";
    			$scope.submitData["fp__tWfCust_Stakeholder__0__OldEmpNameChi"] = "";
    		}
    	});
		if($scope.submitData.fp__tWfCust_Stakeholder__0__Roll=="撞单"){
			$scope.submitData["fp__tWfCust_Stakeholder__0__OldEmpNumber2"] = "";
			$scope.submitData["fp__tWfCust_Stakeholder__0__OldEmpNameChi2"] = "";
		}
		$scope.typeChange();
		
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
	    	Status: $scope.submitData.fp__tWfCust_Stakeholder__0__Status,
	    	Type: $scope.submitData.fp__tWfCust_Stakeholder__0__Type,
	    	Roll: $scope.submitData.fp__tWfCust_Stakeholder__0__Roll
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
stakeholderCtrl.$injector = ["$scope", "dao"];
window.$controllerProvider.register("stakeholderCtrl", stakeholderCtrl);