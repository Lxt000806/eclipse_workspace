var prjReturnOrderCtrl = function($scope, dao){
	// 初始化数据
	$scope.remarks = localStorage.remarks ? localStorage.remarks.replace(/\r\n/g, "<br/>") : '';
	$scope.self = $scope;
	$scope.submitData = {};
	$scope.beforeDetailNumData = {};
	$scope.gctdIndexs = [];
	$scope.submitData.fp__tWfCust_PrjReturnOrder__0__Status = "撞单/跨部门";
	$scope.submitData.fp__tWfCust_PrjReturnOrder__0__Reason = "";
	$scope.isChargeDesignFee = "";
	$scope.photoUrlList=[];
	$scope.submitData.photoUrlList="";
	$scope.rules = {
		fp__tWfCust_PrjReturnOrder__0__CustCode: {
			tipMessage: "客户编号为空"
		},
		fp__tWfCust_PrjReturnOrder__0__Address: {
			tipMessage: "楼盘不能为空"
		},
		fp__tWfCust_PrjReturnOrder__0__DesignMan: {
			tipMessage: "设计师不能为空"
		},
		fp__tWfCust_PrjReturnOrder__0__BusinessMan: {
			tipMessage: "业务员不能为空"
		},
		fp__tWfCust_PrjReturnOrder__0__Status: {
			tipMessage: "施工状态未选择"
		},
		fp__tWfCust_PrjReturnOrder__0__Reason: {
			tipMessage: "退单原因不能为空"
		},
		fp__tWfCust_PrjReturnOrder__0__ContractFee: {
			tipMessage: "合同造价不能为空"
		},
/*		fp__tWfCust_PrjReturnOrder__0__DesignFee: {
			tipMessage: "设计费不能为空"
		},*/
		fp__tWfCust_PrjReturnOrder__0__IsChargeDesignFee: {
			tipMessage: "是否收设计费未选择"
		},
/*		fp__tWfCust_PrjReturnOrder__0__RealDesignFee: {
			tipMessage: "实收设计费不能为空"
		},*/
/*		fp__tWfCust_PrjReturnOrder__0__ReturnAmount: {
			tipMessage: "退款额不能为空"
		},*/
		fp__tWfCust_PrjReturnOrder__0__TakeBackDateList: {
			tipMessage: "单收回资料未选择"
		},
		gctdGroup: {
			isGroup: true,
			indexs: [],
			datas: [
/*		        {
					template: "fp__tWfCust_PrjReturnOrderDtl__?__ActName",
					tipMessage: "账户名不能为空"
				},
				{
					template: "fp__tWfCust_PrjReturnOrderDtl__?__CardId",
					tipMessage: "账号不能为空"
				},
				{
					template: "fp__tWfCust_PrjReturnOrderDtl__?__Bank",
					tipMessage: "开户行不能为空"
				},*/
				{
					template: "fp__tWfCust_PrjReturnOrderDtl__?__ReturnAmount",
					tipMessage: "明细退款金额不能为空"
				},
			]
		}
	}
	
	$scope.takePhoto = function () {
        dao.photo.takePhoto($scope.photoUrlList, "client/wfProcInst/uploadWfPhoto", {type: '2'},
        {quality: 100, targetWidth: 1280, targetHeight:1280 });
  	};
  	
    $scope.pickerPhoto = function () {
    	dao.photo.pickerPhoto($scope.photoUrlList, "client/wfProcInst/uploadWfPhoto", {type: '2'},
        {maxImages: 9, quality: 95, width: 1280, height: 1280});
       
    }
    
	$scope.conditions= {
		authorityCtrl:"1",
		status: "4,5"
	}
	// 明细组件删除回调函数
    $scope.gctdDelCallback = function(index){
		delete $scope.submitData["fp__tWfCust_PrjReturnOrderDtl__"+index+"__ActName"];
		delete $scope.submitData["fp__tWfCust_PrjReturnOrderDtl__"+index+"__CardId"];
		delete $scope.submitData["fp__tWfCust_PrjReturnOrderDtl__"+index+"__Bank"];
		delete $scope.submitData["fp__tWfCust_PrjReturnOrderDtl__"+index+"__SubBranch"];
		delete $scope.submitData["fp__tWfCust_PrjReturnOrderDtl__"+index+"__ReturnAmount"];
		$scope.chgMainAmount();
    }
	
	// 明细组件新增回调函数
    /*$scope.gctdAddCallback = function(index){
    	$scope.submitData["fp__tWfCust_PrjReturnOrderDtl__"+index+"__ActName"] = "";
    	$scope.submitData["fp__tWfCust_PrjReturnOrderDtl__"+index+"__CardId"] = "";
    	$scope.submitData["fp__tWfCust_PrjReturnOrderDtl__"+index+"__Bank"] = "";
    	$scope.submitData["fp__tWfCust_PrjReturnOrderDtl__"+index+"__SubBranch"] = "";
    	$scope.submitData["fp__tWfCust_PrjReturnOrderDtl__"+index+"__ReturnAmount"] = "";
    }*/
    
    $scope.options = [
		{text: "甲方合同", checked: false },
		{text: "设计协议", checked: false },
		{text: "收款收据", checked: false },
	];
    $scope.selectCallback = function(data){
		var value = "";
		if(data.data.length>0){
			for(var i = 0;i<data.data.length;i++){
				if(data.data[i].checked==true ){
					if(value !=""){
						value+=",";
					}
					value+=data.data[i].text;
				}
			}
		}				  
		$scope.submitData.fp__tWfCust_PrjReturnOrder__0__TakeBackDateList = value;
    }
    $scope.gctdAfterDetailNum = function(){
   		$scope.submitData = $scope.beforeDetailNumData;
   		$scope.statusChange();
    };
	
	// 客户选择组件回掉函数
    $scope.customerCallback = function (data){
		$scope.submitData.fp__tWfCust_PrjReturnOrder__0__CustDescr = data.descr;
		$scope.submitData.fp__tWfCust_PrjReturnOrder__0__CustCode = data.code;
		$scope.submitData.fp__tWfCust_PrjReturnOrder__0__Address = data.address;
		$scope.submitData.fp__tWfCust_PrjReturnOrder__0__DesignMan = data.designManDescr;
		$scope.submitData.fp__tWfCust_PrjReturnOrder__0__BusinessMan = data.businessManDescr;
		$scope.submitData.fp__tWfCust_PrjReturnOrder__0__Company = data.company;
		$scope.submitData.fp__tWfCust_PrjReturnOrder__0__CompanyDescr= data.companyDescr;
		$scope.submitData.fp__tWfCust_PrjReturnOrder__0__CustType= data.custTypeDescr;
		
		$scope.submitData.fp__tWfCust_PrjReturnOrder__0__DesignFee= data.designFee;
		$scope.submitData.fp__tWfCust_PrjReturnOrder__0__ContractFee= data.contractFee + data.tax;
		if(data.realDesignFee !=0 ){
			$scope.submitData.fp__tWfCust_PrjReturnOrder__0__IsChargeDesignFee="是";
			$scope.submitData.fp__tWfCust_PrjReturnOrder__0__RealDesignFee= data.designFee;
		}else{
			$scope.submitData.fp__tWfCust_PrjReturnOrder__0__IsChargeDesignFee="否"
			$scope.submitData.fp__tWfCust_PrjReturnOrder__0__RealDesignFee= 0;
		}
		if(data.confirmBegin != "" && data.confirmBegin != null && data.confirmBegin != "undefined"){
			$scope.submitData.fp__tWfCust_PrjReturnOrder__0__Status = "已开工";
		}else{
			$scope.submitData.fp__tWfCust_PrjReturnOrder__0__Status = "未开工";
		}
		$scope.submitData.fp__tWfCust_PrjReturnOrder__0__CompanyDescr= data.companyDescr;
	    $scope.gctdDelAll = true;
	    $scope.statusChange();
    }
    
    // 开始提交事件监听 （每个流程必须）
    $scope.$on("wfProcInstDoSubmit", function(){
    	  	
    	$scope.rules.gctdGroup.indexs = $scope.gctdIndexs;
    	if($scope.submitData["fp__tWfCust_PrjReturnOrder__0__DesignFee"] == null || $scope.submitData["fp__tWfCust_PrjReturnOrder__0__DesignFee"] == undefined){
    		dao.popup.alert("设计费不能为空");
    		return;
    	}
    	if($scope.submitData["fp__tWfCust_PrjReturnOrder__0__RealDesignFee"] == null || $scope.submitData["fp__tWfCust_PrjReturnOrder__0__RealDesignFee"] == undefined){
    		dao.popup.alert("实收设计费不能为空");
    		return;
    	}
    	if($scope.submitData["fp__tWfCust_PrjReturnOrder__0__ReturnAmount"] == null || $scope.submitData["fp__tWfCust_PrjReturnOrder__0__ReturnAmount"] == undefined){
    		dao.popup.alert("退款额不能为空");
    		return;
    	}
    	
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
    
    $scope.chgMainAmount = function(e, temIndex){
    	if(e < 0){
            dao.popup.alert("退款额不能小于0");
            $scope.submitData["fp__tWfCust_PrjReturnOrderDtl__"+temIndex+"__ReturnAmount"] = 0;
            return;
    	}
    	
		var amount = 0;
		for(var i=0;i<20;i++){
			if($scope.submitData["fp__tWfCust_PrjReturnOrderDtl__"+i+"__ReturnAmount"] ){
				amount = parseFloat(amount) + parseFloat($scope.submitData["fp__tWfCust_PrjReturnOrderDtl__"+i+"__ReturnAmount"]);
			}
		}
		$scope.submitData.fp__tWfCust_PrjReturnOrder__0__ReturnAmount = amount;
	}
    
    $scope.checkConfirmAmount = function(i){
    	if(parseFloat($scope.submitData["fp__tWfCust_PrjReturnOrderDtl__"+i+"__ConfirmAmount"]) > 
    			parseFloat($scope.submitData["fp__tWfCust_PrjReturnOrderDtl__"+i+"__ReturnAmount"])){
	      
    		dao.popup.alert("审核金额不能大于审核金额");
    		$scope.submitData["fp__tWfCust_PrjReturnOrderDtl__"+i+"__ConfirmAmount"] = $scope.submitData["fp__tWfCust_PrjReturnOrderDtl__"+i+"__ReturnAmount"]
    	}
    }
    
    // 改变类型事件
    $scope.statusChange = function(){
	    $scope.$emit("getOperator", { 
    		Status:$scope.submitData.fp__tWfCust_PrjReturnOrder__0__Status,
    		Company:$scope.submitData.fp__tWfCust_PrjReturnOrder__0__Company,
    		CustType: $scope.submitData.fp__tWfCust_PrjReturnOrder__0__CustType
	    });
    }
    
    $scope.chgRealDesignFee = function(){
    	$scope.isChargeDesignFee = $scope.submitData.fp__tWfCust_PrjReturnOrder__0__IsChargeDesignFee;
    	if ($scope.isChargeDesignFee == "否"){
    		$scope.submitData.fp__tWfCust_PrjReturnOrder__0__RealDesignFee = 0.0;
    	}
    }
    
    // 初始化数据（每个流程必须）
    $scope.$on("initData", function(event, data){
    	$scope.submitData = data.submitData;
    	$scope.detailNum = data.detailDatas["tWfCust_PrjReturnOrderDtl"];
    	$scope.beforeDetailNumData = data.submitData;
    	if(data.submitData && data.submitData.photoUrlList){
    		$scope.photoUrlList=data.submitData.photoUrlList;
    	}
    	
    	var Status  = $scope.beforeDetailNumData.fp__tWfCust_PrjReturnOrder__0__TakeBackDateList.split(",");
    	for(var i=0;i<Status.length;i++){
    		for(j=0;j< $scope.options.length;j++){
    			if(Status[i] == $scope.options[j].text ){
    				$scope.options[j].checked=true;
    			}
    		}
    	}
    });
    
    $scope.$on("wfProcInstDoPass", function(){
    	if($scope.submitData.tWfCust_PrjReturnOrderDtl_ConfirmAmount_Edit == "1"){
    		$scope.rules.gctdGroup.datas.push({
				template: "fp__tWfCust_PrjReturnOrderDtl__?__ConfirmAmount",
				tipMessage: "审核金额不能为空"
			}); 
    	}
    	$scope.rules.gctdGroup.indexs = $scope.gctdIndexs;
		$scope.$emit("wfProcInstDoPassSendDatas", {
			datas: $scope.submitData,
			rules: $scope.rules
		});
    });
    
    // 请求获取初始化数据（每个流程必须）;
    $scope.$emit("beginGetWfProcInstData");
};
prjReturnOrderCtrl.$injector = ["$scope", "dao"];
window.$controllerProvider.register("prjReturnOrderCtrl", prjReturnOrderCtrl);