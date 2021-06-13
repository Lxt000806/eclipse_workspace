var planReqFormCtrl = function($scope, dao){
	// 初始化数据
	$scope.self = $scope;
	$scope.submitData = {};
	$scope.beforeDetailNumData = {};
	$scope.gctdIndexs = [];
    $scope.photoUrlList=[];
    $scope.submitData.photoUrlList="";
	// $scope.submitData.fp__tWfCust_PrjReturnSet__0__Status = "撞单/跨部门";
	$scope.submitData.fp__tWfCust_PlanReqForm__0__Reason = "";
	$scope.rules = {
		fp__tWfCust_PlanReqForm__0__Reason: {
			tipMessage: "需求描述不能为空"
		},
		fp__tWfCust_PlanReqForm__0__DeliveryDate: {
			tipMessage: "交付时间不能为空"
		},
		
		gctdGroup: {
			isGroup: true,
			indexs: [],
			datas: [

			]
		}
	}
	
	//日期选择组件
	dao.datepicker.init($scope, "", function (val) {
		if (val === undefined) {
		} else {
			var date = transDate(val);
			var date1 = new Date(date);
			var date2 = new Date();
			var days = Math.ceil((date1- date2)/(1000*24*60*60));
			$scope.submitData.fp__tWfCust_PlanReqForm__0__DeliveryDate= date;
			$scope.submitData.fp__tWfCust_PlanReqForm__0__Date = days;
			if(days>=7){
				$scope.submitData.fp__tWfCust_PlanReqForm__0__Type="正常";
			}else{
				$scope.submitData.fp__tWfCust_PlanReqForm__0__Type="加急";
			}
			$scope.$emit("getOperator", {
		    	Date: $scope.submitData.fp__tWfCust_PlanReqForm__0__Date,
		    });
		}
	});
	
	$scope.takePhoto = function () {
        dao.photo.takePhoto($scope.photoUrlList, "client/wfProcInst/uploadWfPhoto", {type: '2'},
        {quality: 100, targetWidth: 1280, targetHeight:1280 });
  	};
  	
    $scope.pickerPhoto = function () {
    	dao.photo.pickerPhoto($scope.photoUrlList, "client/wfProcInst/uploadWfPhoto", {type: '2'},
        {maxImages: 9, quality: 95, width: 1280, height: 1280});
    }
    
    $scope.inputEmployee = function(data){
    	$scope.submitData.nextOperator = data.data.czybh;
    }

    // 开始提交事件监听 （每个流程必须）
    $scope.$on("wfProcInstDoSubmit", function(){
    	for(var i=0;i<$scope.photoUrlList.length;i++){
    		if($scope.submitData.photoUrlList==""){
    			$scope.submitData.photoUrlList=$scope.photoUrlList[i].photoName;
    		}else{
    			$scope.submitData.photoUrlList= $scope.submitData.photoUrlList+","+$scope.photoUrlList[i].photoName;
    		}
    	}

    	//$scope.rules.gctdGroup.indexs = $scope.gctdIndexs;
		$scope.$emit("wfProcInstSendDatas", {
			datas: $scope.submitData,
			rules: $scope.rules
		});
    });
    
    // 初始化数据（每个流程必须）
    $scope.$on("initData", function(event, data){
    	
    	$scope.submitData = data.submitData;
    	if(data.submitData && data.submitData.photoUrlList){
    		$scope.photoUrlList=data.submitData.photoUrlList;
    	}
    	$scope.$emit("getOperator", {
    		Date: $scope.submitData.fp__tWfCust_PlanReqForm__0__Date
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
planReqFormCtrl.$injector = ["$scope", "dao"];
window.$controllerProvider.register("planReqFormCtrl", planReqFormCtrl);
