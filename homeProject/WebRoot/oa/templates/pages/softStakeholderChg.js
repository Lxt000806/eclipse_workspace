/**
 * 软装部干系人调整
 **/
var softStakeholderChg = function($scope, dao){
	// 初始化数据
	$scope.self = $scope;
	$scope.submitData = {};
	$scope.beforeDetailNumData = {};
	$scope.gctdIndexs = [];
	$scope.submitData.fp__tWfCust_SoftStakeholderChg__0__Status = "订单跟踪";
	$scope.submitData.fp__tWfCust_SoftStakeholderChg__0__Reason = "";
	$scope.conditions_emp_old = {}; //oldemp's conditions
	$scope.rules = {
		fp__tWfCust_SoftStakeholderChg__0__CustCode: {
			tipMessage: "客户编号不能为空"
		},
		fp__tWfCust_SoftStakeholderChg__0__Address: {
			tipMessage: "工地名称不能为空"
		},
		fp__tWfCust_SoftStakeholderChg__0__Status: {
			tipMessage: "状态不能为空"
		},
/*		fp__tWfCust_SoftStakeholderChg__0__Type: {
			tipMessage: "调整类型不能为空"
		},*/
		fp__tWfCust_SoftStakeholderChg__0__Reason: {
			tipMessage: "原因不能为空"
		},
		gctdGroup: {
			isGroup: true,
			indexs: [],
			datas: [
		        {
					template: "fp__tWfCust_SoftStakeholderChgDtl__?__Role",
					tipMessage: "角色不能为空"
				},
		        {
					template: "fp__tWfCust_SoftStakeholderChgDtl__?__EmpCode",
					tipMessage: "新干系人不能为空"
				},
			]
		}
	}
	 // 客户查询条件
	$scope.conditions= {
		status:"3,4"
	};

	// 明细组件删除回调函数
    $scope.gctdDelCallback = function(index){
		delete $scope.submitData["fp__tWfCust_SoftStakeholderChgDtl__"+index+"__Role"];
		delete $scope.submitData["fp__tWfCust_SoftStakeholderChgDtl__"+index+"__OldEmpCode"];
		delete $scope.submitData["fp__tWfCust_SoftStakeholderChgDtl__"+index+"__OldEmpName"];
		delete $scope.submitData["fp__tWfCust_SoftStakeholderChgDtl__"+index+"__EmpCode"];
		delete $scope.submitData["fp__tWfCust_SoftStakeholderChgDtl__"+index+"__EmpName"];
		delete $scope.conditions_emp_old["condition_Emp_Old_"+index];
    }
	
	// 明细组件新增回调函数
    $scope.gctdAddCallback = function(index){
    	$scope.submitData["fp__tWfCust_SoftStakeholderChgDtl__"+index+"__Role"] = "";
    	$scope.submitData["fp__tWfCust_SoftStakeholderChgDtl__"+index+"__OldEmpCode"] = "";
    	$scope.submitData["fp__tWfCust_SoftStakeholderChgDtl__"+index+"__OldEmpName"] = "";
    	$scope.submitData["fp__tWfCust_SoftStakeholderChgDtl__"+index+"__EmpCode"] = "";
    	$scope.submitData["fp__tWfCust_SoftStakeholderChgDtl__"+index+"__EmpName"] = "";
    	$scope.conditions_emp_old["condition_Emp_Old_"+index] = {
    		custCode: $scope.submitData.fp__tWfCust_SoftStakeholderChg__0__CustCode,
    		role: "***"
    	};
    }

    $scope.gctdAfterDetailNum = function(){
   		$scope.submitData = $scope.beforeDetailNumData;
    };
	
	// 客户选择组件回掉函数
	$scope.customerCallback = function (data){
		$scope.submitData.fp__tWfCust_SoftStakeholderChg__0__CustDescr = data.descr;
		$scope.submitData.fp__tWfCust_SoftStakeholderChg__0__CustCode = data.code;
		$scope.submitData.fp__tWfCust_SoftStakeholderChg__0__Address = data.address;
		$scope.submitData.fp__tWfCust_SoftStakeholderChg__0__Status = data.statusDescr;
		$scope.gctdDelAll = true;
	}
    // 原干系人回调
	$scope.oldEmployeeCallback = function (data, index){
		$scope.submitData["fp__tWfCust_SoftStakeholderChgDtl__"+index+"__OldEmpCode"] = data.data.number;
    	$scope.submitData["fp__tWfCust_SoftStakeholderChgDtl__"+index+"__OldEmpName"] = data.data.nameChi;
	}
	// 新干系人回调
	$scope.employeeCallback = function (data, index){
		$scope.submitData["fp__tWfCust_SoftStakeholderChgDtl__"+index+"__EmpCode"] = data.data.number;
    	$scope.submitData["fp__tWfCust_SoftStakeholderChgDtl__"+index+"__EmpName"] = data.data.nameChi;
	}

    // 开始提交事件监听 （每个流程必须）
    $scope.$on("wfProcInstDoSubmit", function(){
    	$scope.rules.gctdGroup.indexs = $scope.gctdIndexs;
		$scope.$emit("wfProcInstSendDatas", {
			datas: $scope.submitData,
			rules: $scope.rules
		});
    });
    
    //改变角色
    $scope.changeRole = function(index){
    	$scope.conditions_emp_old["condition_Emp_Old_"+index] = {
    		custCode: $scope.submitData.fp__tWfCust_SoftStakeholderChg__0__CustCode,
    		role: $scope.submitData["fp__tWfCust_SoftStakeholderChgDtl__"+index+"__Role"]?$scope.submitData["fp__tWfCust_SoftStakeholderChgDtl__"+index+"__Role"]:"***",
    		expired: 'T'
    	};
		var data = new Object();
		data.roll = $scope.submitData["fp__tWfCust_SoftStakeholderChgDtl__"+index+"__Role"];
		data.custCode = $scope.submitData.fp__tWfCust_SoftStakeholderChg__0__CustCode;
		data.callback = "JSON_CALLBACK";
		dao.https.jsonp("client/wfProcInst/getCustStakeholder", data).then(function(data){
    		if(data && data.success && data.obj){
    			$scope.submitData["fp__tWfCust_SoftStakeholderChgDtl__"+index+"__OldEmpCode"] = data.obj.EmpCode;
    			$scope.submitData["fp__tWfCust_SoftStakeholderChgDtl__"+index+"__OldEmpName"] = data.obj.NameChi;
    		}else{
    			$scope.submitData["fp__tWfCust_SoftStakeholderChgDtl__"+index+"__OldEmpCode"] = "";
    			$scope.submitData["fp__tWfCust_SoftStakeholderChgDtl__"+index+"__OldEmpName"] = "";
    		}
    	}, function(){
			$scope.submitData["fp__tWfCust_SoftStakeholderChgDtl__"+index+"__OldEmpCode"] = "";
			$scope.submitData["fp__tWfCust_SoftStakeholderChgDtl__"+index+"__OldEmpName"] = "";
    	});
    }

    // 初始化数据（每个流程必须）
    $scope.$on("initData", function(event, data){
    	$scope.detailNum = data.detailDatas["tWfCust_SoftStakeholderChgDtl"];
    	$scope.beforeDetailNumData = data.submitData;
    	var Status  = $scope.submitData.fp__tWfCust_SoftStakeholderChg__0__Status.split(",");
    	for(var i=0;i<Status.length;i++){
    		for(j=0;j< $scope.options.length;j++){
    			if(Status[i] == $scope.options[j].text ){
    				$scope.options[j].checked=true;
    			}
    		}
    	}
    });
    
    $scope.$on("wfProcInstDoPass", function(){
		$scope.$emit("wfProcInstDoPassSendDatas", {
			datas: $scope.submitData,
			rules: $scope.rules
		});
    });
    
    // 请求获取初始化数据（每个流程必须）;
    $scope.$emit("beginGetWfProcInstData");

	$scope.$emit("getOperator", { 
		// Status:$scope.submitData.fp__tWfCust_SoftStakeholderChg__0__Status,
		//Type:$scope.submitData.fp__tWfCust_SoftStakeholderChg__0__Type,
	});
};
softStakeholderChg.$injector = ["$scope", "dao"];
window.$controllerProvider.register("softStakeholderChg", softStakeholderChg);
