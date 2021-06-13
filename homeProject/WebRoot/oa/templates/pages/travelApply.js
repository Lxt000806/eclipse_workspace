var travelApplyCtrl = function($scope, wfProcInstService,dao){
	// 初始化数据
	$scope.self = $scope;
	$scope.submitData = {};
	$scope.beforeDetailNumData = {};
	$scope.fybxIndexs = [];
	$scope.submitData.actName="";
    $scope.dateIndex = 0;
    $scope.dateType = "begin";
    
	$scope.rules = {
		fp__tWfCust_TravelApply__0__EmpCode: {
			tipMessage: "员工编号"
		},
		fp__tWfCust_TravelApply__0__ApplyDate: {
			tipMessage: "申请时间"
		},
		fp__tWfCust_TravelApply__0__BeginDate: {
			tipMessage: "出发时间"
		},
		fp__tWfCust_TravelApply__0__EndDate: {
			tipMessage: "回程时间"
		},
		fp__tWfCust_TravelApply__0__Address: {
			tipMessage: "出发地点"
		},
		fp__tWfCust_TravelApply__0__Reason: {
			tipMessage: "出发事由"
		},
	}
	
	$scope.changeDate = function (flag){
		if(flag =='beginDate'){
			$scope.dateType = 'begin';
		} else if(flag =='endDate'){
			$scope.dateType = 'end';
		} else {
			$scope.dateType = 'apply';
		}
	}
	
    dao.datepicker.init($scope, "", function (val) {
		if (val === undefined) {
		} else {
			var date = transDate(val);
			if($scope.dateType == "begin"){
				$scope.submitData["fp__tWfCust_TravelApply__0__BeginDate"] = date;
			}else if($scope.dateType == "end"){
				$scope.submitData["fp__tWfCust_TravelApply__0__EndDate"] = date;
			} else {
				$scope.submitData["fp__tWfCust_TravelApply__0__ApplyDate"] = date;
			}
		}
	});
    
    $scope.chgCompany = function (){
    	if($scope.submitData.fp__tWfCust_TravelApply__0__CompanyCode != ""){
    		if($scope.submitData.fp__tWfCust_TravelApply__0__CompanyCode == "01"){
    			$scope.submitData.fp__tWfCust_TravelApply__0__CompanyDescr = "福州有家";
    		} else {
    			$scope.submitData.fp__tWfCust_TravelApply__0__CompanyDescr ="美第家居"
    		}
    	}
    	
    	$scope.$emit("getOperator", { 
    		CompanyCode:$scope.submitData.fp__tWfCust_TravelApply__0__CompanyCode
    	});
    }
    
    // 初始化数据（每个流程必须）
    $scope.$on("initData", function(event, data){
    	$scope.submitData = data.submitData;
    	
    	if($scope.type == ""){
    		if(!$scope.submitData){
    			$scope.submitData = {};
    		}
    		$scope.submitData.fp__tWfCust_TravelApply__0__EmpCode = data.empInfoMap.number;
    		$scope.submitData.fp__tWfCust_TravelApply__0__EmpName = data.empInfoMap.nameChi;
    		$scope.submitData.fp__tWfCust_TravelApply__0__CompanyCode = data.empInfoMap.companyCode;
    		
    		if($scope.submitData.fp__tWfCust_TravelApply__0__CompanyCode == "01"){
    			$scope.submitData.fp__tWfCust_TravelApply__0__CompanyDescr = "福州有家";
    		} else {
    			$scope.submitData.fp__tWfCust_TravelApply__0__CompanyDescr ="美第家居"
    		}
    		
    		$scope.submitData.fp__tWfCust_TravelApply__0__DeptDescr = data.empInfoMap.department2Descr;
    		$scope.submitData.fp__tWfCust_TravelApply__0__Position = data.empInfoMap.positionDescr;
    		$scope.submitData.fp__tWfCust_TravelApply__0__DeptCode = data.empInfoMap.department2Code;
    	}
    	
    	$scope.$emit("getOperator", {
    		CompanyCode:$scope.submitData.fp__tWfCust_TravelApply__0__CompanyCode
	    });
    	
    });
    
    // 开始提交事件监听 （每个流程必须）
    $scope.$on("wfProcInstDoSubmit", function(){
		$scope.$emit("wfProcInstSendDatas", {
			datas: $scope.submitData,
			rules: $scope.rules
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
travelApplyCtrl.$injector = ["$scope", "wfProcInstService"];
window.$controllerProvider.register("travelApplyCtrl", travelApplyCtrl);