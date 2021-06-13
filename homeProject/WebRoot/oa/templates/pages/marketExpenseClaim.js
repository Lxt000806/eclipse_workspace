var marketExpenseClaimCtrl = function($scope, wfProcInstService,dao){
	// 初始化数据
	$scope.remarks = localStorage.remarks ? localStorage.remarks.replace(/\r\n/g, "<br/>") : '';
	$scope.self = $scope;
	$scope.submitData = {};
	$scope.beforeDetailNumData = {};
	$scope.fybxIndexs = [];
	$scope.yxfyIndexs = [];
	$scope.ftmxIndexs = [];
	$scope.bxyzdBeforeDetailNumData = {};
	$scope.bxyzdIndexs = [];
	$scope.submitData.actName="";
	$scope.submitData.bank="";
	$scope.submitData.cardId="";
	$scope.submitData.subBranch="";
	$scope.IsCommon ="";
	$scope.depType = "";
	$scope.confAmount = 0.0;
	$scope.photoUrlList=[];
    $scope.submitData.photoUrlList="";
	$scope.cmpCode = "";
	$scope.addRule = {};

	$scope.rules = {
		fp__tWfCust_MarketExpenseClaim__0__EmpCode: {
			tipMessage: "员工编号"
		},
		fp__tWfCust_MarketExpenseClaim__0__IsCommon: {
			tipMessage: "请选择是否常规类"
		},
		fp__tWfCust_MarketExpenseClaim__0__Amount: {
			tipMessage: "请填写明细表报销额"
		},
		fp__tWfCust_MarketExpenseClaim__0__Deduction:{
			tipMessage: "请选择是否抵预支"
		},
		yxfyGroup: {
			isGroup: true,
			indexs: [],
			datas: [
				{			   
					template: "fp__tWfCust_MarketExpenseClaimMainDtl__?__Bank",
					tipMessage: "开户行不能为空"
				},
				{			   
					template: "fp__tWfCust_MarketExpenseClaimMainDtl__?__SubBranch",
					tipMessage: "支行不能为空"
				},
				{
					template: "fp__tWfCust_MarketExpenseClaimMainDtl__?__CardId",
					tipMessage: "卡号不能为空"
				},
				{
					template: "fp__tWfCust_MarketExpenseClaimMainDtl__?__ActName",
					tipMessage: "户名不能为空"
				},
				{
					template: "fp__tWfCust_MarketExpenseClaimMainDtl__?__DtlAmount",
					tipMessage: "报销额不能为空"
				},
			]
		},
		ftmxGroup: {
			isGroup: true,
			indexs: [],
			datas: [
				{				
					template: "fp__tWfCust_MarketExpenseClaimDtl__?__DeptDescr",
					tipMessage: "部门不能为空"
				},
				{
					template: "fp__tWfCust_MarketExpenseClaimDtl__?__ShareAmount",
					tipMessage: "分摊额不能为空"
				},
				{
					template: "fp__tWfCust_MarketExpenseClaimDtl__?__Remarks",
					tipMessage: "说明不能为空"
				},
			]
		}
	}
	
	$scope.formatCardId = function (i){
		$scope.submitData["fp__tWfCust_MarketExpenseClaimMainDtl__"+i+"__CardId"] = 
			$scope.submitData["fp__tWfCust_MarketExpenseClaimMainDtl__"+i+"__CardId"].replace(/\s/g,'').replace(/[^\d]/g,'').replace(/(^(\d{4})|(\d{4}))(?=\d)/g,'$1 ');
	}
	
    $scope.employeeCallback = function (data){
		$scope.submitData.fp__tWfCust_MarketExpenseClaim__0__EmpCode = data.data.number;
		$scope.submitData.fp__tWfCust_MarketExpenseClaim__0__EmpName = data.data.nameChi;
		$scope.submitData.fp__tWfCust_MarketExpenseClaim__0__BefAmount = data.data.befAmount;
	}

    $scope.typeChange = function(data){
    	$scope.$emit("getOperator", { 
			Amount:$scope.submitData.fp__tWfCust_MarketExpenseClaim__0__Amount,
    		EmpType:$scope.submitData.fp__tWfCust_MarketExpenseClaim__0__EmpType,
    		IsCommon:$scope.submitData.fp__tWfCust_MarketExpenseClaim__0__IsCommon,
    		Company:$.trim($scope.submitData.fp__tWfCust_MarketExpenseClaim__0__Company),
    		SKIP_NEXTTASK:"否",
    		CostCategories: $scope.submitData.fp__tWfCust_MarketExpenseClaim__0__CostCategories
    	});
    }
    
    $scope.chgCompany = function (){
    	$scope.$emit("getOperator", { 
    		Amount:$scope.submitData.fp__tWfCust_MarketExpenseClaim__0__Amount,
    		EmpType:$scope.submitData.fp__tWfCust_MarketExpenseClaim__0__EmpType,
    		IsCommon:$scope.submitData.fp__tWfCust_MarketExpenseClaim__0__IsCommon,
    		Company:$.trim($scope.submitData.fp__tWfCust_MarketExpenseClaim__0__Company),
    		SKIP_NEXTTASK:"否",
    		CostCategories: $scope.submitData.fp__tWfCust_MarketExpenseClaim__0__CostCategories
    	});
    }
    
    $scope.calcDeductionAmount = function(index){
    	if(!index) index = -1;
    	var amount = 0;
		for(var i=0;i<20;i++){
			if($scope.submitData["fp__tWfCust_MarketClaimAdvanceDtl__"+i+"__DeductionAmount"] && i != index){
				amount = parseFloat(amount) + parseFloat($scope.submitData["fp__tWfCust_MarketClaimAdvanceDtl__"+i+"__DeductionAmount"]);
			}
		}
		$scope.submitData.fp__tWfCust_MarketExpenseClaim__0__DeductionAmount = amount;
    }
    
    $scope.chgMainAmount = function(index){
    	if(!index) index = -1;
    	var amount = 0;
		for(var i=0;i<20;i++){
			if($scope.submitData["fp__tWfCust_MarketExpenseClaimMainDtl__"+i+"__DtlAmount"] && i != index){
				amount = parseFloat(amount) + parseFloat($scope.submitData["fp__tWfCust_MarketExpenseClaimMainDtl__"+i+"__DtlAmount"]);
			}
		}
		$scope.submitData.fp__tWfCust_MarketExpenseClaim__0__Amount = amount;
		$scope.$emit("getOperator", { 
			Amount:$scope.submitData.fp__tWfCust_MarketExpenseClaim__0__Amount,
    		EmpType:$scope.submitData.fp__tWfCust_MarketExpenseClaim__0__EmpType,
    		IsCommon:$scope.submitData.fp__tWfCust_MarketExpenseClaim__0__IsCommon,
    		Company:$.trim($scope.submitData.fp__tWfCust_MarketExpenseClaim__0__Company),
    		SKIP_NEXTTASK:"否",
    		CostCategories: $scope.submitData.fp__tWfCust_MarketExpenseClaim__0__CostCategories
    	});
	}
    
    $scope.takePhoto = function () {
        dao.photo.takePhoto($scope.photoUrlList, "client/wfProcInst/uploadWfPhoto", {type: '2'},
        {quality: 100, targetWidth: 1280, targetHeight:1280 });
  	};
  	
    $scope.pickerPhoto = function () {
    	dao.photo.pickerPhoto($scope.photoUrlList, "client/wfProcInst/uploadWfPhoto", {type: '2'},
        {maxImages: 9, quality: 95, width: 1280, height: 1280});
       
    }
    
    // 明细组件删除回调函数
    $scope.fybxDelCallback = function(index){
		$scope.chgMainAmount(index);
    }
    
    // 初始化数据（每个流程必须）
    $scope.$on("initData", function(event, data){
    	
    	$scope.submitData = data.submitData;
    	
    	if($scope.submitData){
    		$scope.submitData.TaskContinue = "否";
    	}
    	if($scope.submitData && $scope.submitData.tWfCust_MarketClaimAdvanceDtl_PK_Show != "1"){
    		var ele = document.getElementById("editDiv");
    		ele.parentNode.parentNode.parentNode.parentNode.removeChild(ele.parentNode.parentNode.parentNode);
    	}
    	
    	if($scope.submitData && $scope.submitData.tWfCust_MarketClaimAdvanceDtl_PK_Edit != "1" 
    				&& $scope.submitData.tWfCust_MarketClaimAdvanceDtl_PK_Show=="1"){
    		var ele = document.getElementById("editDiv");
    		$(ele.parentNode.parentNode.parentNode).find("button").hide();
    	}
    	
    	if($scope.submitData && $scope.submitData.tWfCust_MarketExpenseClaimDtl_PK_Edit != "1"){
    		var ele = document.getElementById("detailDiv");
    		$(ele.parentNode.parentNode.parentNode).find("button").hide();
    	}
    	/*if($scope.submitData && $scope.submitData.tWfCust_MarketExpenseClaimMainDtl_PK_Edit != "1"){
    		var ele = document.getElementById("mainDetailDiv");
    		$(ele.parentNode.parentNode.parentNode).find("button").hide();
    	}*/
    	
    	if(data.empInfoMap && data.empInfoMap.company){
    		$scope.cmpCode = data.empInfoMap.company;
    	}
    	
    	if(!$scope.submitData){
    		$scope.submitData = {};
    	}
    	
    	if($scope.submitData && $scope.submitData.fp__tWfCust_MarketExpenseAdvance__0__EmpCode){
	   		 $scope.getEmployeeAdvance($scope.submitData.fp__tWfCust_MarketExpenseAdvance__0__EmpCode);
	   	}
    	
    	if($scope.type == ""){
    		$scope.submitData.fp__tWfCust_MarketExpenseClaim__0__EmpCode = data.empInfoMap.number;
    		$scope.submitData.fp__tWfCust_MarketExpenseClaim__0__EmpName = data.empInfoMap.nameChi;
    		$scope.submitData.fp__tWfCust_MarketExpenseClaim__0__BefAmount = data.empInfoMap.advanceAmount;
    		$scope.submitData.fp__tWfCust_MarketExpenseClaim__0__IsCommon ="是";
    		$scope.submitData.fp__tWfCust_MarketExpenseClaim__0__Company = data.empInfoMap.company;
    	}

    	if(data.submitData && data.submitData.photoUrlList){
    		$scope.photoUrlList=data.submitData.photoUrlList;
    	}
    	
    	if($scope.submitData.fp__tWfCust_MarketExpenseClaim__0__Company && $scope.submitData.fp__tWfCust_MarketExpenseClaim__0__Company != null ){
    		$scope.cmpCode = $scope.submitData.fp__tWfCust_MarketExpenseClaim__0__Company;
    	}
    	
    	$scope.commitDatas.confAmount = $scope.submitData.fp__tWfCust_MarketExpenseClaim__0__DeductionAmount;
    	
    	if(data.empInfoMap){
    		$scope.submitData.fp__tWfCust_MarketExpenseClaim__0__EmpType = data.empInfoMap.depType;
    	}
    	
    	if($scope.submitData.fp__tWfCust_MarketExpenseClaim__0__DocumentNo ==null){
    		$scope.submitData.fp__tWfCust_MarketExpenseClaim__0__DocumentNo = "";
    	}

    	//调用部门2选择组件
    	$scope.initDept2Modal('fp__tWfCust_MarketExpenseClaimDtl__','DeptCode','DeptDescr',$scope.submitData);
    	$scope.initEmpCardModal('fp__tWfCust_MarketExpenseClaimMainDtl__','ActName','CardId','Bank','SubBranch',$scope.submitData, $scope.submitData.fp__tWfCust_MarketExpenseClaim__0__EmpCode);
    	$scope.initExpenseAdvanceModal('fp__tWfCust_MarketClaimAdvanceDtl__','WfExpenseAdvanceNo','AdvanceAmount',$scope.submitData);
    	$scope.$emit("getOperator", {
    		Amount:data.submitData.fp__tWfCust_MarketExpenseClaim__0__Amount,
    		EmpType:$scope.submitData.fp__tWfCust_MarketExpenseClaim__0__EmpType,
    		IsCommon:$scope.submitData.fp__tWfCust_MarketExpenseClaim__0__IsCommon,
    		Company:$.trim($scope.submitData.fp__tWfCust_MarketExpenseClaim__0__Company),
    		SKIP_NEXTTASK:"否",
    		CostCategories: $scope.submitData.fp__tWfCust_MarketExpenseClaim__0__CostCategories
	    });
    	//初始化流程数据
    	$scope.ftmxDetailNum = data.detailDatas["tWfCust_MarketExpenseClaimDtl"];
    	$scope.detailNum = data.detailDatas["tWfCust_MarketExpenseClaimMainDtl"];
    	$scope.bxyzdDetailNum = data.detailDatas["tWfCust_MarketClaimAdvanceDtl"];
    	$scope.beforeDetailNumData = data.submitData;
    	$scope.bxyzdBeforeDetailNumData = data.submitData;
    	$scope.ftmxBeforeDetailNumData = data.submitData;
    	
    	//有需要调用其他保存接口的流程节点 在这边设置
    	/*if("usertask18" == $scope.commitDatas.taskDefKey){
    		$scope.commitDatas.url = "client/wfProcInst/doApprFinanceTask";
    	}*/
    });
    
    $scope.getEmployeeAdvance = function(empCode){
        wfProcInstService.getEmployeeAdvance(empCode).success(function(data){

        	$scope.submitData.fp__tWfCust_MarketExpenseClaim__0__BefAmount = data.amount;
        });
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
    	$scope.rules.yxfyGroup.indexs = $scope.yxfyIndexs;
    	$scope.rules.ftmxGroup.indexs = $scope.ftmxIndexs;
    	if($scope.type==""){
    		$scope.submitData.actName=$scope.submitData.fp__tWfCust_MarketExpenseClaim__0__ActName;
    		$scope.submitData.bank=$scope.submitData.fp__tWfCust_MarketExpenseClaim__0__Bank;
    		$scope.submitData.subBranch=$scope.submitData.fp__tWfCust_MarketExpenseClaim__0__SubBranch;
    		$scope.submitData.cardId=$scope.submitData.fp__tWfCust_MarketExpenseClaim__0__CardId;
    	}
		$scope.$emit("wfProcInstSendDatas", {
			datas: $scope.submitData,
			rules: $scope.rules
		});
    });
    
    $scope.$on("wfProcInstDoPass", function(){
    	if($scope.commitDatas.taskDefKey == "usertask17" && $scope.cmpCode == "福州有家"){
    		$scope.addRule = {
    				tipMessage: "请填写审批金额"
    		},
    		$scope.rules.fp__tWfCust_MarketExpenseClaim__0__ApproveAmount = $scope.addRule;
    	}
    	
		$scope.$emit("wfProcInstDoPassSendDatas", {
			datas: $scope.submitData,
			rules: $scope.rules
		});
    });
    
    // 请求获取初始化数据（每个流程必须）;
    $scope.$emit("beginGetWfProcInstData");
};
marketExpenseClaimCtrl.$injector = ["$scope", "wfProcInstService"];
window.$controllerProvider.register("marketExpenseClaimCtrl", marketExpenseClaimCtrl);