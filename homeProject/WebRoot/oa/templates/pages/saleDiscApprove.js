var saleDiscApproveCtrl = function($scope, wfProcInstService, dao){
	// 初始化数据
	$scope.self = $scope;
	$scope.submitData = {};
	$scope.beforeDetailNumData = {};
	$scope.ysmxIndexs = [];
	$scope.submitData.actName="";
	$scope.submitData.bank="";
	$scope.submitData.cardId="";
	$scope.photoUrlList=[];
    $scope.submitData.photoUrlList="";
	$scope.cmpCode = "";
	$scope.selfDept = "1";
	$scope.delbtn = "false";
	
	$scope.rules = {
		gctdGroup: {
			isGroup: true,
			indexs: [],
			datas: [
			]
		}
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
    	$scope.rules.gctdGroup.indexs = $scope.ysmxIndexs;
    	
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
    	
    	var ele = document.getElementById("editDiv");
		$(ele.parentNode.parentNode.parentNode).find("button").hide();

		if(data.empInfoMap && data.empInfoMap.companyCode){
    		$scope.cmpCode = data.empInfoMap.company;
    	}
    	
    	if(!$scope.submitData){
    		$scope.submitData = {};
    	}
    	if(data.submitData){
    		$scope.photoUrlList=data.submitData.photoUrlList;
    	}
    	
    	$scope.$emit("getOperator", {
    		MinMarkup: $.trim($scope.submitData.fp__tWfCust_SaleDiscApprove__0__MinMarkup),
    		IsClearInvStatus: $.trim($scope.submitData.fp__tWfCust_SaleDiscApprove__0__IsClearInvStatus)    		
	    });
    	 
    	//初始化流程数据
    	$scope.detailNum = data.detailDatas["tWfCust_SaleDiscApproveDtl"];
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
    $scope.$emit("getOperator", {});
};
saleDiscApproveCtrl.$injector = ["$scope", "wfProcInstService"];
window.$controllerProvider.register("saleDiscApproveCtrl", saleDiscApproveCtrl);