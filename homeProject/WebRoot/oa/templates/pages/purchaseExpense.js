var PurchaseExpenseCtrl = function($scope, wfProcInstService, dao){
	// 初始化数据
	$scope.self = $scope;
	$scope.submitData = {};
	$scope.beforeDetailNumData = {};
	$scope.rgfyIndexs = [];
	$scope.photoUrlList=[];
    $scope.submitData.photoUrlList="";
	$scope.cmpCode = "";
	$scope.selfDept = "1";
    $scope.delBtn = "false"
	$scope.rules = {

	}
	
    // 开始提交事件监听 （每个流程必须）
    $scope.$on("wfProcInstDoSubmit", function(){
    	if($scope.photoUrlList){
    		for(var i=0;i<$scope.photoUrlList.length;i++){
    			if($scope.submitData.photoUrlList=="" || !$scope.submitData.photoUrlList){
    				$scope.submitData.photoUrlList=$scope.photoUrlList[i].photoName;
    			}else{
    				$scope.submitData.photoUrlList= $scope.submitData.photoUrlList+","+$scope.photoUrlList[i].photoName;
    			}
    		}
    	}
    	//$scope.rules.gctdGroup.indexs = $scope.rgfyIndexs;
    	
		$scope.$emit("wfProcInstSendDatas", {
			datas: $scope.submitData,
			rules: $scope.rules
		});
    });
    
    $scope.takePhoto = function () {
        dao.photo.takePhoto($scope.photoUrlList, "client/wfProcInst/uploadWfPhoto", {type: '2'},
        {quality: 100, targetWidth: 1280, targetHeight:1280 });
  	};
  	
    $scope.pickerPhoto = function () {
        dao.photo.pickerPhoto($scope.photoUrlList, "client/wfProcInst/uploadWfPhoto", {type: '2'},
        {maxImages: 9, quality: 95, width: 1280, height: 1280});
    }
    
    // 初始化数据（每个流程必须）
    $scope.$on("initData", function(event, data){
    	$scope.submitData = data.submitData;
    	if(!$scope.submitData){
    		$scope.submitData = {};
    	}
    	
    	if(data.submitData){
    		$scope.photoUrlList=data.submitData.photoUrlList;
    	}

    	$scope.$emit("getOperator", {
    		Type: $.trim($scope.submitData.fp__tWfCust_PurchaseExpense__0__Type),
    		ClaimAmount: $.trim($scope.submitData.fp__tWfCust_PurchaseExpense__0__ClaimAmount),
    		ItemType1: $.trim($scope.submitData.fp__tWfCust_PurchaseExpense__0__ItemType1)    		
	    });
    	 
    	//初始化流程数据
    	$scope.detailNum = data.detailDatas["tWfCust_PurchaseExpenseDtl"];
    	$scope.beforeDetailNumData = data.submitData;
    	if($scope.submitData.taskDefKey != 'startevent' && $scope.submitData.taskDefKey != 'applyman'){
    		var ele = document.getElementById("editDiv");
    		$(ele.parentNode.parentNode.parentNode).find("button").hide();
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
    $scope.$emit("getOperator", {});
};
PurchaseExpenseCtrl.$injector = ["$scope", "wfProcInstService"];
window.$controllerProvider.register("PurchaseExpenseCtrl", PurchaseExpenseCtrl);