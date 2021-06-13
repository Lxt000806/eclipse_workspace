var departureStakeholderChgCtrl = function($scope, dao){
	// 初始化数据
	$scope.remarks = localStorage.remarks ? localStorage.remarks.replace(/\r\n/g, "<br/>") : '';
	$scope.self = $scope;
	$scope.submitData = {};
	$scope.beforeDetailNumData = {};
	$scope.gctdIndexs = [];
	$scope.codes = "";
	$scope.rules = {
		fp__tWfCust_DepartureStakeholderChg__0__Status: {
			tipMessage: "状态不能为空"
		},
		fp__tWfCust_DepartureStakeholderChg__0__OldStakeholder: {
			tipMessage: "原干系人不能为空"
		},
		fp__tWfCust_DepartureStakeholderChg__0__Stakeholder: {
			tipMessage: "新干系人不能为空"
		},
		gctdGroup: {
			isGroup: true,
			indexs: [],
			datas: [
		        {
					template: "fp__tWfCust_DepartureStakeholderChgDtl__?__CustCode",
					tipMessage: "客户编号不能为空"
				},
			]
		}
	}
	// 明细组件删除回调函数
    $scope.gctdDelCallback = function(index){
		delete $scope.submitData["fp__tWfCust_DepartureStakeholderChgDtl__"+index+"__CustCode"];
		delete $scope.submitData["fp__tWfCust_DepartureStakeholderChgDtl__"+index+"__Address"];
		delete $scope.submitData["fp__tWfCust_DepartureStakeholderChgDtl__"+index+"__CustDescr"];
    }
	
	// 明细组件新增回调函数
    $scope.gctdAddCallback = function(index){
		$scope.submitData["fp__tWfCust_DepartureStakeholderChgDtl__"+index+"__CustCode"];
		$scope.submitData["fp__tWfCust_DepartureStakeholderChgDtl__"+index+"__Address"];
		$scope.submitData["fp__tWfCust_DepartureStakeholderChgDtl__"+index+"__CustDescr"];
    }
    
    $scope.gctdAfterDetailNum = function(){
   		$scope.submitData = $scope.beforeDetailNumData;
   		$scope.$emit("getOperator", { 
    		Status:$scope.submitData.fp__tWfCust_DepartureStakeholderChg__0__Status
    	});
    };
	//默认值使得查询为空，填完原干系人才能查出来
	$scope.conditions= {
		designMan:"defautNull",
		status:"defautNull"
	}
	// 客户选择组件回掉函数
    $scope.customerCallback = function(data, index){
		$scope.submitData["fp__tWfCust_DepartureStakeholderChgDtl__"+index+"__CustCode"] = data.code;
		$scope.submitData["fp__tWfCust_DepartureStakeholderChgDtl__"+index+"__CustDescr"] = data.descr;
		$scope.submitData["fp__tWfCust_DepartureStakeholderChgDtl__"+index+"__Address"] = data.address;
		//拼接已填写的客户编号，不再显示在列表中
		for(var i in $scope.gctdIndexs){
			if($scope.codes.indexOf($scope.submitData["fp__tWfCust_DepartureStakeholderChgDtl__"+i+"__CustCode"])==-1){
				$scope.codes+=$scope.submitData["fp__tWfCust_DepartureStakeholderChgDtl__"+i+"__CustCode"]+",";
			}
		}
		// 客户查询条件
		var status="";
		if($scope.submitData.fp__tWfCust_DepartureStakeholderChg__0__Status=="订单跟踪"){
			status="3";
		}else if($scope.submitData.fp__tWfCust_DepartureStakeholderChg__0__Status=="合同施工"){
			status="4";
		}else{
			status="defaultNull";
		}
		$scope.conditions= {
			designMan:$scope.submitData.fp__tWfCust_DepartureStakeholderChg__0__OldStakeholder,
			codes:$scope.codes,
			status:status
		}
    }
	// 员工选择组件回掉函数
	$scope.inputEmployee = function(data){
		if(data && data.elementid && data.elementid != ""){
			var key = data.elementid.split("_")[0];
			if(key =="OldStakeholder"){
				$scope.submitData["fp__tWfCust_DepartureStakeholderChg__0__OldStakeholder"] = data.data.number;
				$scope.submitData["fp__tWfCust_DepartureStakeholderChg__0__OldStakeholderDescr"] = data.data.nameChi;
				//删除已经填写的客户
				for(var index in $scope.gctdIndexs){
					$scope.gctdDelCallback(index);
				}
				//清空已添加客户
				$scope.codes="";
				//更新客户设计师筛选条件
				var status="";
				if($scope.submitData.fp__tWfCust_DepartureStakeholderChg__0__Status=="订单跟踪"){
					status="3";
				}else if($scope.submitData.fp__tWfCust_DepartureStakeholderChg__0__Status=="合同施工"){
					status="4";
				}else{
					status="defaultNull";
				}
				$scope.conditions= {
					designMan:$scope.submitData.fp__tWfCust_DepartureStakeholderChg__0__OldStakeholder,
					status:status
				}
			}else if(key =="Stakeholder"){
				$scope.submitData["fp__tWfCust_DepartureStakeholderChg__0__Stakeholder"] = data.data.number;
				$scope.submitData["fp__tWfCust_DepartureStakeholderChg__0__StakeholderDescr"] = data.data.nameChi;
			}
		}
    }
    
    // 开始提交事件监听 （每个流程必须）
    $scope.$on("wfProcInstDoSubmit", function(){
    	$scope.rules.gctdGroup.indexs = $scope.gctdIndexs;
		$scope.$emit("wfProcInstSendDatas", {
			datas: $scope.submitData,
			rules: $scope.rules
		});
    });
    
    // 改变状态事件
    $scope.statusChange = function(){
    	//删除已经填写的客户
		for(var index in $scope.gctdIndexs){
			$scope.gctdDelCallback(index);
		}
		//清空已添加客户
		$scope.codes="";
    	//更新客户设计师筛选条件
		var status="";
		if($scope.submitData.fp__tWfCust_DepartureStakeholderChg__0__Status=="订单跟踪"){
			status="3";
		}else if($scope.submitData.fp__tWfCust_DepartureStakeholderChg__0__Status=="合同施工"){
			status="4";
		}else{
			status="defaultNull";
		}
		$scope.conditions= {
			designMan:$scope.submitData.fp__tWfCust_DepartureStakeholderChg__0__OldStakeholder,
			status:status
		}
    	$scope.$emit("getOperator", { 
    		Status:$scope.submitData.fp__tWfCust_DepartureStakeholderChg__0__Status
    	});
    }
    
    // 初始化数据（每个流程必须）
    $scope.$on("initData", function(event, data){
    	$scope.detailNum = data.detailDatas["tWfCust_DepartureStakeholderChgDtl"];
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
};
departureStakeholderChgCtrl.$injector = ["$scope", "dao"];
window.$controllerProvider.register("departureStakeholderChgCtrl", departureStakeholderChgCtrl);
