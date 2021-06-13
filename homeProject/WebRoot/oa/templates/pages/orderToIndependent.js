var orderToIndependentCtrl = function($scope, wfProcInstService, dao){
	// 初始化数据
	$scope.remarks = localStorage.remarks ? localStorage.remarks.replace(/\r\n/g, "<br/>") : '';
	$scope.self = $scope;
	$scope.submitData = {};
	$scope.beforeDetailNumData = {};
	$scope.gctkIndexs = [];
	$scope.submitData.fp__tWfCust_OrderToIndependent__0__Reason = "";
	$scope.photoUrlList=[];
	$scope.submitData.photoUrlList="";
	$scope.rules = {
		fp__tWfCust_OrderToIndependent__0__CustCode: {
			tipMessage: "客户编号为空"
		},
		fp__tWfCust_OrderToIndependent__0__Address: {
			tipMessage: "楼盘不能为空"
		},
		fp__tWfCust_OrderToIndependent__0__ErpReturn: {
			tipMessage: "Erp退定未选择"
		},
		fp__tWfCust_OrderToIndependent__0__ItemType1: {
			tipMessage: "独立销售不能为空"
		},
		fp__tWfCust_OrderToIndependent__0__Reason: {
			tipMessage: "退定原因不能为空"
		},
		fp__tWfCust_OrderToIndependent__0__NewCustCode: {
			tipMessage: "转入客户不能为空"
		},
		fp__tWfCust_OrderToIndependent__0__NewAddress: {
			tipMessage: "转入楼盘不能为空"
		},
		fp__tWfCust_OrderToIndependent__0__NewAmount: {
			tipMessage: "转入金额不能为空"
		},
		fp__tWfCust_OrderToIndependent__0__Amount: {
			tipMessage: "转出金额不能为空"
		},
	}
	
	$scope.conditions= { 
		authorityCtrl:"1",
		status:"5"
	}
	
	$scope.newConditions= { 
		authorityCtrl:"1",
		isAddAllInfo:"0"
	}
	$scope.options = [
	      { text: "主材", checked: false },
	      { text: "软装", checked: false },
	      { text: "集成", checked: false }
    ];
	$scope.selectCallback = function (data){
		$scope.value = "";
		if(data.data.length>0){
			for(var i = 0;i<data.data.length;i++){
				if(data.data[i].checked==true ){
					if($scope.value !=""){
						$scope.value+=",";
					}
					$scope.value+=data.data[i].text;
				}
			}
		}
		$scope.submitData.fp__tWfCust_OrderToIndependent__0__ItemType1 = $scope.value;
	}
	
	$scope.takePhoto = function () {
        dao.photo.takePhoto($scope.photoUrlList, "client/wfProcInst/uploadWfPhoto", {type: '2'},
        {quality: 100, targetWidth: 1280, targetHeight:1280 });
  	};
  	
    $scope.pickerPhoto = function () {
    	dao.photo.pickerPhoto($scope.photoUrlList, "client/wfProcInst/uploadWfPhoto", {type: '2'},
        {maxImages: 9, quality: 95, width: 1280, height: 1280});
       
    }
    
	// 客户选择组件回掉函数
	$scope.customerCallback = function (data){
		$scope.submitData.fp__tWfCust_OrderToIndependent__0__CustDescr = data.descr;
		$scope.submitData.fp__tWfCust_OrderToIndependent__0__CustCode = data.code;
		$scope.submitData.fp__tWfCust_OrderToIndependent__0__Address = data.address;
		$scope.submitData.fp__tWfCust_OrderToIndependent__0__Company = data.company;
		$scope.submitData.fp__tWfCust_OrderToIndependent__0__CompanyDescr= data.companyDescr;
	    $scope.gctkDelAll = true;
	    $scope.companyChange();
    }
	
	$scope.customerNewCallback = function (data){
		$scope.submitData.fp__tWfCust_OrderToIndependent__0__NewCustDescr = data.descr;
		$scope.submitData.fp__tWfCust_OrderToIndependent__0__NewCustCode = data.code;
		$scope.submitData.fp__tWfCust_OrderToIndependent__0__NewAddress = data.address;
    }
	
	$scope.setNewAmount = function (){
		$scope.submitData.fp__tWfCust_OrderToIndependent__0__NewAmount= $scope.submitData.fp__tWfCust_OrderToIndependent__0__Amount;
	}
	
	$scope.checkNewAmount = function (){
		if(parseFloat($scope.submitData.fp__tWfCust_OrderToIndependent__0__NewAmount) > parseFloat($scope.submitData.fp__tWfCust_OrderToIndependent__0__Amount)){
	        dao.popup.alert("转入金额不能大于转出金额");
			$scope.submitData.fp__tWfCust_OrderToIndependent__0__NewAmount = $scope.submitData.fp__tWfCust_OrderToIndependent__0__Amount;
		}
	}
    
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
    	
		$scope.$emit("wfProcInstSendDatas", {
			datas: $scope.submitData,
			rules: $scope.rules
		});
    });
    
    // 改变公司事件
    $scope.companyChange = function(){
    	$scope.$emit("getOperator", { 
    		Company:$scope.submitData.fp__tWfCust_OrderToIndependent__0__Company
    	});
    }
    
    // 初始化数据（每个流程必须）
    $scope.$on("initData", function(event, data){
    	$scope.submitData = data.submitData;
    	
    	if(data.submitData && data.submitData.photoUrlList){
    		$scope.photoUrlList=data.submitData.photoUrlList;
    	}
    	
    	var itemType1  = $scope.submitData.fp__tWfCust_OrderToIndependent__0__ItemType1.split(",");
    	for(var i=0;i<itemType1.length;i++){
    		for(j=0;j< $scope.options.length;j++){
    			if(itemType1[i] == $scope.options[j].text ){
    				$scope.options[j].checked=true;
    			}
    		}
    	}
    	if($scope.submitData.fp__tWfCust_OrderToIndependent__0__Company){
    		$scope.$emit("getOperator", { 
    			Company:$scope.submitData.fp__tWfCust_OrderToIndependent__0__Company
    		});
    	}
    	if($scope.submitData.tWfCust_OrderToIndependent_ConfirmAmount_Edit == "1"){
    		if(!$scope.submitData.fp__tWfCust_OrderToIndependent__0__ConfirmAmount || $scope.submitData.fp__tWfCust_OrderToIndependent__0__ConfirmAmount == "" ){
    			$scope.submitData.fp__tWfCust_OrderToIndependent__0__ConfirmAmount = $scope.submitData.fp__tWfCust_OrderToIndependent__0__Amount;
    			$scope.submitData.fp__tWfCust_OrderToIndependent__0__NewConfirmAmount = $scope.submitData.fp__tWfCust_OrderToIndependent__0__NewAmount;
    		}
    	}
    });
    
    $scope.$on("wfProcInstDoPass", function(){
    	if($scope.submitData.tWfCust_OrderToIndependent_ConfirmAmount_Edit == "1"){
    		$scope.rules.gctdGroup.datas.push({
				template: "fp__tWfCust_OrderToIndependent__?__ConfirmAmount",
				tipMessage: "审核金额不能为空"
			});  
    	}
		$scope.$emit("wfProcInstDoPassSendDatas", {
			datas: $scope.submitData,
			rules: $scope.rules
		});
    });
    
    // 请求获取初始化数据（每个流程必须）;
    $scope.$emit("beginGetWfProcInstData");
};
orderToIndependentCtrl.$injector = ["$scope", "wfProcInstService"];
window.$controllerProvider.register("orderToIndependentCtrl", orderToIndependentCtrl);