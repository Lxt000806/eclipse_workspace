var officeSupplyPurchClaimCtrl = function($scope, wfProcInstService){
	// 初始化数据
	$scope.self = $scope;
	$scope.submitData = {};
	$scope.beforeDetailNumData = {};
	$scope.kghbIndexs = [];
	$scope.submitData.actName="";
	$scope.submitData.bank="";
	$scope.submitData.cardId="";
	$scope.depType = "";
	$scope.photoUrlList=[];
    $scope.submitData.photoUrlList="";
	
    // 开始提交事件监听 （每个流程必须）
    $scope.$on("wfProcInstDoSubmit", function(){
    	for(var i=0;i<$scope.photoUrlList.length;i++){
    		if($scope.submitData.photoUrlList==""){
    			$scope.submitData.photoUrlList= $scope.photoUrlList[i].photoName;
    		}else{
    			$scope.submitData.photoUrlList= $scope.submitData.photoUrlList+","+$scope.photoUrlList[i].photoName;
    		}
    	}
    	if($scope.type==""){
    		$scope.submitData.actName=$scope.submitData.fp__tWfCust_OfficeSupplyPurchClaim__0__ActName;
    		$scope.submitData.bank=$scope.submitData.fp__tWfCust_OfficeSupplyPurchClaim__0__Bank;
    		$scope.submitData.cardId=$scope.submitData.fp__tWfCust_OfficeSupplyPurchClaim__0__CardId;
    	}
		$scope.$emit("wfProcInstSendDatas", {
			datas: $scope.submitData,
			rules: $scope.rules
		});
    });
    
    $scope.employeeCallback = function (data){
		$scope.submitData.fp__tWfCust_OfficeSupplyPurchClaim__0__EmpCode = data.data.number;
		$scope.submitData.fp__tWfCust_OfficeSupplyPurchClaim__0__EmpName = data.data.nameChi;
	}

    // 初始化数据（每个流程必须）
    $scope.$on("initData", function(event, data){
    	$scope.submitData = data.submitData;
    	$scope.photoUrlList=data.submitData.photoUrlList;
    	//调用部门2选择组件
    	$scope.depType = data.empInfoMap.depType;
    	if(!$scope.submitData){
    		$scope.submitData = {};
    	}
    	$scope.initEmpCardModal('fp__tWfCust_OfficeSupplyPurchClaim__','ActName','CardId','Bank',$scope.submitData);
    	
    	$scope.$emit("getOperator", {});
    	
    	//初始化流程数据
    	$scope.beforeDetailNumData = data.submitData;

    	//有需要调用其他保存接口的流程节点 在这边设置
    	if("usertask5" == $scope.commitDatas.taskDefKey){
    		$scope.commitDatas.url = "client/wfProcInst/doApprFinanceTask";
    	}
    });
    
    // 请求获取初始化数据（每个流程必须）;
    $scope.$emit("beginGetWfProcInstData");
	$scope.$emit("getOperator", {});
};
officeSupplyPurchClaimCtrl.$injector = ["$scope", "wfProcInstService"];
window.$controllerProvider.register("officeSupplyPurchClaimCtrl", officeSupplyPurchClaimCtrl);