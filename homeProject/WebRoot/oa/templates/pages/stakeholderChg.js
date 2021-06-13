var stakeholderChgCtrl = function($scope, dao){
	// 初始化数据
	$scope.self = $scope;
	$scope.submitData = {};
	$scope.beforeDetailNumData = {};
	$scope.gxrIndexs = [];
	$scope.submitData.fp__tWfCust_StakeholderChg__0__Type = "撞单/跨部门";
	$scope.submitData.fp__tWfCust_StakeholderChg__0__Reason = "";
	$scope.rules = {
		fp__tWfCust_StakeholderChg__0__CustCode: {
			tipMessage: "客户编号为空"
		},
		fp__tWfCust_StakeholderChg__0__Status: {
			tipMessage: "状态不能未选择"
		},
		fp__tWfCust_StakeholderChg__0__Type: {
			tipMessage: "类型不能未选择"
		},
		gxrGroup: {
			isGroup: true,
			indexs: [],
			datas: [
		        {
					template: "fp__tWfCust_StakeholderChgDtl__?__Role",
					tipMessage: "干系人明细角色未选择"
				},
				{
					template: "fp__tWfCust_StakeholderChgDtl__?__OldEmpCode",
					tipMessage: "干系人明细原干系人不能为空"
				},
				{
					template: "fp__tWfCust_StakeholderChgDtl__?__EmpCode",
					tipMessage: "干系人明细新干系人不能为空"
				}
			]
		}
	}
	
	// 客户选择组件回掉函数
    $scope.customerCallback = function (data){
		$scope.submitData.fp__tWfCust_StakeholderChg__0__CustDescr = data.descr;
		$scope.submitData.fp__tWfCust_StakeholderChg__0__CustCode = data.code;
		$scope.submitData.fp__tWfCust_StakeholderChg__0__Address = data.address;
		$scope.submitData.fp__tWfCust_StakeholderChg__0__Status = data.statusDescr;
	    $scope.$emit("getOperator", {
	    	Status: $scope.submitData.fp__tWfCust_StakeholderChg__0__Status,
	    	Type: $scope.submitData.fp__tWfCust_StakeholderChg__0__Type
	    });
	    $scope.gxrDelAll = true;
    }
    
    // 明细组件新增回调函数
    $scope.gxrAddCallback = function(index){
    	$scope.submitData["fp__tWfCust_StakeholderChgDtl__"+index+"__OldEmpCode"] = "";
    	$scope.submitData["fp__tWfCust_StakeholderChgDtl__"+index+"__OldEmpName"] = "";
    	$scope.submitData["fp__tWfCust_StakeholderChgDtl__"+index+"__EmpCode"] = "";
    	$scope.submitData["fp__tWfCust_StakeholderChgDtl__"+index+"__EmpName"] = "";
    	$scope["conditions_"+index] = {
			role: "",
			custCode: ""
    	}
    }
    
    // 明细组件删除回调函数
    $scope.gxrDelCallback = function(index){
		delete $scope.submitData["fp__tWfCust_StakeholderChgDtl__"+index+"__OldEmpCode"];
		delete $scope.submitData["fp__tWfCust_StakeholderChgDtl__"+index+"__OldEmpName"];
		delete $scope.submitData["fp__tWfCust_StakeholderChgDtl__"+index+"__EmpCode"];
		delete $scope.submitData["fp__tWfCust_StakeholderChgDtl__"+index+"__EmpName"];
		delete $scope["conditions_"+index];
    }
    
    $scope.inputEmployee = function(data){
		if(data && data.elementid && data.elementid != ""){
			var key = data.elementid.split("_")[0];
			var index = data.elementid.split("_")[1];
	    	$scope.submitData["fp__tWfCust_StakeholderChgDtl__"+index+"__"+key+"Code"] = data.data.number;
	    	$scope.submitData["fp__tWfCust_StakeholderChgDtl__"+index+"__"+key+"Name"] = data.data.nameChi;
		}
    }
    
    // 改变角色事件
    $scope.selRole = function(index){
    	$scope["conditions_"+index].role = $scope.submitData["fp__tWfCust_StakeholderChgDtl__"+index+"__Role"];
    	$scope["conditions_"+index].custCode = $scope.submitData.fp__tWfCust_StakeholderChg__0__CustCode;
		var data = new Object();
		data.roll = $scope["conditions_"+index].role;
		data.custCode = $scope.submitData.fp__tWfCust_StakeholderChg__0__CustCode;
		data.callback = "JSON_CALLBACK";
		dao.https.jsonp("client/wfProcInst/getCustStakeholder", data).then(function(data){
    		if(data && data.success && data.obj){
    			for(var i = 0; i < $scope.gxrIndexs.length; i++){
    				if($scope.gxrIndexs[i] == index){
		    			$scope.submitData["fp__tWfCust_StakeholderChgDtl__"+$scope.gxrIndexs[i]+"__OldEmpCode"] = data.obj.EmpCode;
		    			$scope.submitData["fp__tWfCust_StakeholderChgDtl__"+$scope.gxrIndexs[i]+"__OldEmpName"] = data.obj.NameChi;
    				}
    			}
    		}
    	});
    }
    
    // 改变类型事件
    $scope.typeChange = function(){
	    $scope.$emit("getOperator", {
	    	Status: $scope.submitData.fp__tWfCust_StakeholderChg__0__Status,
	    	Type: $scope.submitData.fp__tWfCust_StakeholderChg__0__Type
	    });
    }
    
    // 开始提交事件监听 （每个流程必须）
    $scope.$on("wfProcInstDoSubmit", function(){
    	$scope.rules.gxrGroup.indexs = $scope.gxrIndexs;
		$scope.$emit("wfProcInstSendDatas", {
			datas: $scope.submitData,
			rules: $scope.rules
		});
    });
    
    // 初始化数据（每个流程必须）
    $scope.$on("initData", function(event, data){
    	$scope.detailNum = data.detailDatas["tWfCust_StakeholderChgDtl"];
    	$scope.beforeDetailNumData = data.submitData;
    });
    
    $scope.gxrAfterDetailNum = function(){
   		$scope.submitData = $scope.beforeDetailNumData;
	    $scope.$emit("getOperator", {
	    	Status: $scope.submitData.fp__tWfCust_StakeholderChg__0__Status,
	    	Type: $scope.submitData.fp__tWfCust_StakeholderChg__0__Type
	    });
	    for(var key in $scope.submitData){
	    	if(key && key.indexOf("Role") != -1){
	    		var index = key.substring(31, key.indexOf("Role")-2);
		    	$scope["conditions_"+index] = {
					role: $scope.submitData[key],
					custCode: $scope.submitData.fp__tWfCust_StakeholderChg__0__CustCode
		    	}
	    	}
	    }
    };
    
    $scope.$on("wfProcInstDoPass", function(){
		$scope.$emit("wfProcInstDoPassSendDatas", {
			datas: $scope.submitData,
			rules: $scope.rules
		});
    });
    
    // 请求获取初始化数据（每个流程必须）;
    $scope.$emit("beginGetWfProcInstData");
};
stakeholderChgCtrl.$injector = ["$scope", "dao"];
window.$controllerProvider.register("stakeholderChgCtrl", stakeholderChgCtrl);