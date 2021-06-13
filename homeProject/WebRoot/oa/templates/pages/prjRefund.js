var prjRefundCtrl = function($scope, dao){
	// 初始化数据
	$scope.remarks = localStorage.remarks ? localStorage.remarks.replace(/\r\n/g, "<br/>") : '';
	$scope.self = $scope;
	$scope.submitData = {};
	$scope.beforeDetailNumData = {};
	$scope.gctkIndexs = [];
	$scope.submitData.fp__tWfCust_PrjRefund__0__Reason = "";
	$scope.photoUrlList=[];
	$scope.submitData.photoUrlList="";
	$scope.rules = {
		fp__tWfCust_PrjRefund__0__CustCode: {
			tipMessage: "客户编号为空"
		},
		fp__tWfCust_PrjRefund__0__Address: {
			tipMessage: "楼盘不能为空"
		},
		fp__tWfCust_PrjRefund__0__IsReturnReceipt: {
			tipMessage: "是否收回收据未选择"
		},
		fp__tWfCust_PrjRefund__0__HasNewReceipt: {
			tipMessage: "是否开出收据未选择"
		},
		fp__tWfCust_PrjRefund__0__Amount: {
			tipMessage: "退款额不能为空"
		},
		fp__tWfCust_PrjRefund__0__Reason: {
			tipMessage: "备注不能为空"
		},
		gctkGroup: {
			isGroup: true,
			indexs: [],
			datas: [
		        {
					template: "fp__tWfCust_PrjRefundDtl__?__ActName",
					tipMessage: "工程退款明细账户名不能为空"
				},
				{
					template: "fp__tWfCust_PrjRefundDtl__?__CardId",
					tipMessage: "工程退款明细账号不能为空"
				},
				{
					template: "fp__tWfCust_PrjRefundDtl__?__Bank",
					tipMessage: "工程退款明细开户行不能为空"
				},
				{
					template: "fp__tWfCust_PrjRefundDtl__?__SubBranch",
					tipMessage: "工程退款明细支行不能为空"
				},
				{
					template: "fp__tWfCust_PrjRefundDtl__?__Amount",
					tipMessage: "工程退款明细退款额不能为空"
				}
			]
		}
	}
	$scope.conditions= {
		authorityCtrl:"1",
		status:"4"
	}
	
	$scope.takePhoto = function () {
        dao.photo.takePhoto($scope.photoUrlList, "client/wfProcInst/uploadWfPhoto", {type: '2'},
        {quality: 100, targetWidth: 1280, targetHeight:1280 });
  	};
  	
    $scope.pickerPhoto = function () {
    	dao.photo.pickerPhoto($scope.photoUrlList, "client/wfProcInst/uploadWfPhoto", {type: '2'},
        {maxImages: 9, quality: 95, width: 1280, height: 1280});
       
    }
    
	$scope.chgMainAmount = function(){
		var amount = 0;
		for(var i=0;i<20;i++){
			if($scope.submitData["fp__tWfCust_PrjRefundDtl__"+i+"__Amount"] ){
				amount = parseFloat(amount) + parseFloat($scope.submitData["fp__tWfCust_PrjRefundDtl__"+i+"__Amount"]);
			}
		}
		$scope.submitData.fp__tWfCust_PrjRefund__0__Amount = amount;
		
	}
	
	// 客户选择组件回掉函数
    $scope.customerCallback = function (data){
		$scope.submitData.fp__tWfCust_PrjRefund__0__CustDescr = data.descr;
		$scope.submitData.fp__tWfCust_PrjRefund__0__CustCode = data.code;
		$scope.submitData.fp__tWfCust_PrjRefund__0__Address = data.address;
	    $scope.gctkDelAll = true;
    }
    
    // 明细组件新增回调函数
    /*$scope.gctkAddCallback = function(index){
    	$scope.submitData["fp__tWfCust_PrjRefundDtl__"+index+"__ActName"] = "";
    	$scope.submitData["fp__tWfCust_PrjRefundDtl__"+index+"__CardId"] = "";
    	$scope.submitData["fp__tWfCust_PrjRefundDtl__"+index+"__Bank"] = "";
    	$scope.submitData["fp__tWfCust_PrjRefundDtl__"+index+"__SubBranch"] = "";
    }*/
    
    // 明细组件删除回调函数
    $scope.gctkDelCallback = function(index){
		delete $scope.submitData["fp__tWfCust_PrjRefundDtl__"+index+"__ActName"];
		delete $scope.submitData["fp__tWfCust_PrjRefundDtl__"+index+"__CardId"];
		delete $scope.submitData["fp__tWfCust_PrjRefundDtl__"+index+"__Bank"];
		delete $scope.submitData["fp__tWfCust_PrjRefundDtl__"+index+"__SubBranch"];
		delete $scope.submitData["fp__tWfCust_PrjRefundDtl__"+index+"__Amount"];
		$scope.chgMainAmount();
    }
    
    $scope.checkConfirmAmount = function(i){
    	if(parseFloat($scope.submitData["fp__tWfCust_PrjRefundDtl__"+i+"__ConfirmAmount"]) > 
    			parseFloat($scope.submitData["fp__tWfCust_PrjRefundDtl__"+i+"__Amount"])){
	      
    		dao.popup.alert("审核金额不能大于审核金额");
    		$scope.submitData["fp__tWfCust_PrjRefundDtl__"+i+"__ConfirmAmount"] = $scope.submitData["fp__tWfCust_PrjRefundDtl__"+i+"__Amount"]
    	}
    }
    
    $scope.gctkAfterDetailNum = function(){
   		$scope.submitData = $scope.beforeDetailNumData;
    };
    
    // 开始提交事件监听 （每个流程必须）
    $scope.$on("wfProcInstDoSubmit", function(){
    	if($scope.photoUrlList){
    		$scope.submitData.photoUrlList = "";
    		for(var i=0;i<$scope.photoUrlList.length;i++){
    			if($scope.submitData.photoUrlList=="" || !$scope.submitData.photoUrlList){
    				$scope.submitData.photoUrlList=$scope.photoUrlList[i].photoName;
    			} else {
    				$scope.submitData.photoUrlList= $scope.submitData.photoUrlList+","+$scope.photoUrlList[i].photoName;
    			}
    		}
    	}
    	
    	$scope.rules.gctkGroup.indexs = $scope.gctkIndexs;
		$scope.$emit("wfProcInstSendDatas", {
			datas: $scope.submitData,
			rules: $scope.rules
		});
    });
    
    // 初始化数据（每个流程必须）
    $scope.$on("initData", function(event, data){
    	$scope.submitData = data.submitData;
    	$scope.beforeDetailNumData = data.submitData;
    	if(data.submitData && data.submitData.photoUrlList){
    		$scope.photoUrlList=data.submitData.photoUrlList;
    	}
    	
    	if(data.empInfoMap && $scope.type ==""){
    		$scope.submitData.fp__tWfCust_PrjRefund__0__Company= data.empInfoMap.companyCode;
    	}
    	$scope.$emit("getOperator", {
    		Company: $scope.submitData.fp__tWfCust_PrjRefund__0__Company
    	});
    	
    	if(data.detailDatas["tWfCust_PrjRefundDtl"]){
    		$scope.detailNum = data.detailDatas["tWfCust_PrjRefundDtl"];
    	}
    });
    
    $scope.$on("wfProcInstDoPass", function(){
    	if($scope.submitData.tWfCust_PrjRefundDtl_ConfirmAmount_Edit == "1"){
    		$scope.rules.gctkGroup.datas.push({
				template: "fp__tWfCust_PrjRefundDtl__?__ConfirmAmount",
				tipMessage: "审核金额不能为空"
			});  
    	}
    	$scope.rules.gctkGroup.indexs = $scope.gctkIndexs;
		$scope.$emit("wfProcInstDoPassSendDatas", {
			datas: $scope.submitData,
			rules: $scope.rules
		});
    });
    
    // 请求获取初始化数据（每个流程必须）;
    $scope.$emit("beginGetWfProcInstData");
};
prjRefundCtrl.$injector = ["$scope", "dao"];
window.$controllerProvider.register("prjRefundCtrl", prjRefundCtrl);