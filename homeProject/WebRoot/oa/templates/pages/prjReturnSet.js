var prjReturnSetCtrl = function($scope, dao){
	// 初始化数据
	$scope.remarks = localStorage.remarks ? localStorage.remarks.replace(/\r\n/g, "<br/>") : '';
	$scope.self = $scope;
	$scope.submitData = {};
	$scope.beforeDetailNumData = {};
	$scope.gctdIndexs = [];
	// $scope.submitData.fp__tWfCust_PrjReturnSet__0__Status = "撞单/跨部门";
	$scope.submitData.fp__tWfCust_PrjReturnSet__0__Reason = "";
	$scope.photoUrlList=[];
	$scope.submitData.photoUrlList="";
	$scope.rules = {
		fp__tWfCust_PrjReturnSet__0__CustCode: {
			tipMessage: "客户编号不能为空"
		},
		fp__tWfCust_PrjReturnSet__0__Address: {
			tipMessage: "工程名称不能为空"
		},
		fp__tWfCust_PrjReturnSet__0__Phone: {
			tipMessage: "业主电话不能为空"
		},
		fp__tWfCust_PrjReturnSet__0__DesignMan: {
			tipMessage: "设计师不能为空"
		},
		fp__tWfCust_PrjReturnSet__0__BusinessMan: {
			tipMessage: "业务员不能为空"
		},
		fp__tWfCust_PrjReturnSet__0__SetDate: {
			tipMessage: "下定时间未选择"
		},
		fp__tWfCust_PrjReturnSet__0__Reason: {
			tipMessage: "退单原因不能为空"
		},
		fp__tWfCust_PrjReturnSet__0__ReturnAmount: {
			tipMessage: "退款额不能为空"
		},
		fp__tWfCust_PrjReturnSet__0__TakeBackDateList: {
			tipMessage: "退单收回资料未选择"
		},
		gctdGroup: {
			isGroup: true,
			indexs: [],
			datas: [
		        {
					template: "fp__tWfCust_PrjReturnSetDtl__?__ActName",
					tipMessage: "账户名不能为空"
				},
				{
					template: "fp__tWfCust_PrjReturnSetDtl__?__CardId",
					tipMessage: "账号不能为空"
				},
				{
					template: "fp__tWfCust_PrjReturnSetDtl__?__Bank",
					tipMessage: "开户行不能为空"
				},
				{
					template: "fp__tWfCust_PrjReturnSetDtl__?__ReturnAmount",
					tipMessage: "支行不能为空"
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
    
	//客户查询条件
	$scope.conditions= {
		authorityCtrl:"1",
		status: "5"	//限制只有客户状态：归档，结束代码：退定金，的楼盘可以申请 modify by zb on 20200319
		//endCode: "6" 	// 20200417 modify by xzy
	};
	
	//日期选择组件
	dao.datepicker.init($scope, "", function (val) {
		if (val === undefined) {
		} else {
			var date = transDate(val);
			$scope.submitData.fp__tWfCust_PrjReturnSet__0__SetDate = date;
		}
	});

	// 明细组件删除回调函数
    $scope.gctdDelCallback = function(index){
		delete $scope.submitData["fp__tWfCust_PrjReturnSetDtl__"+index+"__ActName"];
		delete $scope.submitData["fp__tWfCust_PrjReturnSetDtl__"+index+"__CardId"];
		delete $scope.submitData["fp__tWfCust_PrjReturnSetDtl__"+index+"__Bank"];
		delete $scope.submitData["fp__tWfCust_PrjReturnSetDtl__"+index+"__SubBranch"];
		delete $scope.submitData["fp__tWfCust_PrjReturnSetDtl__"+index+"__ReturnAmount"];
		$scope.chgMainAmount();
    }
	
	// 明细组件新增回调函数
    /*$scope.gctdAddCallback = function(index){
    	$scope.submitData["fp__tWfCust_PrjReturnSetDtl__"+index+"__ActName"] = "";
    	$scope.submitData["fp__tWfCust_PrjReturnSetDtl__"+index+"__CardId"] = "";
    	$scope.submitData["fp__tWfCust_PrjReturnSetDtl__"+index+"__Bank"] = "";
    	$scope.submitData["fp__tWfCust_PrjReturnSetDtl__"+index+"__SubBranch"] = "";
    	$scope.submitData["fp__tWfCust_PrjReturnSetDtl__"+index+"__ReturnAmount"] = "";
    }*/
    
    $scope.options = [
//		{text: "甲方合同", checked: false },
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
		$scope.submitData.fp__tWfCust_PrjReturnSet__0__TakeBackDateList = value;
    }
    $scope.gctdAfterDetailNum = function(){
   		$scope.submitData = $scope.beforeDetailNumData;
    	$scope.companyChange();
    };
	
    $scope.chgMainAmount = function(){
		var amount = 0;
		for(var i=0;i<20;i++){
			if($scope.submitData["fp__tWfCust_PrjReturnSetDtl__"+i+"__ReturnAmount"] ){
				amount = parseFloat(amount) + parseFloat($scope.submitData["fp__tWfCust_PrjReturnSetDtl__"+i+"__ReturnAmount"]);
			}
		}
		$scope.submitData.fp__tWfCust_PrjReturnSet__0__ReturnAmount = amount;
		
	}
    
	// 客户选择组件回掉函数
	$scope.customerCallback = function (data){
		$scope.submitData.fp__tWfCust_PrjReturnSet__0__CustDescr = data.descr;
		$scope.submitData.fp__tWfCust_PrjReturnSet__0__CustCode = data.code;
		$scope.submitData.fp__tWfCust_PrjReturnSet__0__Address = data.address;
		$scope.submitData.fp__tWfCust_PrjReturnSet__0__DesignMan = data.designManDescr;
		$scope.submitData.fp__tWfCust_PrjReturnSet__0__BusinessMan = data.businessManDescr;
		$scope.submitData.fp__tWfCust_PrjReturnSet__0__SetDate = data.setDate;
		$scope.submitData.fp__tWfCust_PrjReturnSet__0__Company = data.company;
		$scope.submitData.fp__tWfCust_PrjReturnSet__0__CompanyDescr= data.companyDescr;
		$scope.submitData.fp__tWfCust_PrjReturnSet__0__CustType= data.custTypeDescr;
		$scope.gctdDelAll = true;
		$scope.companyChange();
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
    	
    	$scope.rules.gctdGroup.indexs = $scope.gctdIndexs;
		$scope.$emit("wfProcInstSendDatas", {
			datas: $scope.submitData,
			rules: $scope.rules
		});
    });
    
    // 改变公司事件
    $scope.companyChange = function(){
    	$scope.$emit("getOperator", { 
    		Company: $scope.submitData.fp__tWfCust_PrjReturnSet__0__Company,
    		CustType: $scope.submitData.fp__tWfCust_PrjReturnSet__0__CustType
    	});
    }
    
    $scope.checkConfirmAmount = function(i){
    	if(parseFloat($scope.submitData["fp__tWfCust_PrjReturnSetDtl__"+i+"__ConfirmAmount"]) > 
    			parseFloat($scope.submitData["fp__tWfCust_PrjReturnSetDtl__"+i+"__ReturnAmount"])){
	      
    		dao.popup.alert("审核金额不能大于审核金额");
    		$scope.submitData["fp__tWfCust_PrjReturnSetDtl__"+i+"__ConfirmAmount"] = $scope.submitData["fp__tWfCust_PrjReturnSetDtl__"+i+"__ReturnAmount"]
    	}
    }
    
    // 初始化数据（每个流程必须）
    $scope.$on("initData", function(event, data){
    	$scope.submitData = data.submitData;
    	$scope.detailNum = data.detailDatas["tWfCust_PrjReturnSetDtl"];
    	$scope.beforeDetailNumData = data.submitData;
    	if(data.submitData && data.submitData.photoUrlList && data.submitData.photoUrlList != null){
    		$scope.photoUrlList=data.submitData.photoUrlList;
    	}
    });
    
    $scope.$on("wfProcInstDoPass", function(){
    	if($scope.submitData.tWfCust_PrjReturnSetDtl_ConfirmAmount_Edit == "1"){
    		$scope.rules.gctdGroup.datas.push({
				template: "fp__tWfCust_PrjReturnSetDtl__?__ConfirmAmount",
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
prjReturnSetCtrl.$injector = ["$scope", "dao"];
window.$controllerProvider.register("prjReturnSetCtrl", prjReturnSetCtrl);
