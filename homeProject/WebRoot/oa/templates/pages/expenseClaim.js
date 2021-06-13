var expenseClaimCtrl = function($scope, wfProcInstService,dao){
	// 初始化数据
	$scope.remarks = localStorage.remarks ? localStorage.remarks.replace(/\r\n/g, "<br/>") : '';
	$scope.self = $scope;
	$scope.submitData = {};
	$scope.beforeDetailNumData = {};
	$scope.bxyzdBeforeDetailNumData = {};
	$scope.bxyzdIndexs = [];
	$scope.fybxIndexs = [];
	$scope.hideflag = "0";
	$scope.deductionHideFlag = "0";
	$scope.submitData.actName="";
	$scope.submitData.bank="";
	$scope.submitData.cardId="";
	$scope.submitData.subBranch="";
	$scope.IsCommon ="";
	$scope.depType = "";
	$scope.cmpCode = "";
	$scope.confAmount = 0.0;
	$scope.photoUrlList=[];
	$scope.addRule = {};
    $scope.submitData.photoUrlList="";
	
	$scope.rules = {
		fp__tWfCust_ExpenseClaim__0__EmpCode: {
			tipMessage: "员工编号"
		},
		fp__tWfCust_ExpenseClaim__0__ActName: {
			tipMessage: "户名不能为空"
		},
		fp__tWfCust_ExpenseClaim__0__IsCommon: {
			tipMessage: "请选择是否常规类"
		},
		fp__tWfCust_ExpenseClaim__0__CardId: {
			tipMessage: "卡号不能为空"
		},
		fp__tWfCust_ExpenseClaim__0__Bank: {
			tipMessage: "开户行不能为空"
		},
		fp__tWfCust_ExpenseClaim__0__SubBranch: {
			tipMessage: "支行不能为空"
		},
		fp__tWfCust_ExpenseClaim__0__Amount: {
			tipMessage: "请填写明细表报销额"
		},
		fp__tWfCust_ExpenseClaim__0__Deduction:{
			tipMessage: "请选择是否抵预支"
		},
		fybxGroup: {
			isGroup: true,
			indexs: [],
			datas: [
		        {
					template: "fp__tWfCust_ExpenseClaimDtl__?__DeptCode",
					tipMessage: "部门不能为空"
				},
				{
					template: "fp__tWfCust_ExpenseClaimDtl__?__DtlAmount",
					tipMessage: "明细预支额不能为空"
				},
				{
					template: "fp__tWfCust_ExpenseClaimDtl__?__Remarks",
					tipMessage: "报销说明不能为空"
				},
			]
		}
	}

	$scope.formatCardId = function (){
		$scope.submitData.fp__tWfCust_ExpenseClaim__0__CardId = 
			$scope.submitData.fp__tWfCust_ExpenseClaim__0__CardId.replace(/\s/g,'').replace(/[^\d]/g,'').replace(/(^(\d{4})|(\d{4}))(?=\d)/g,'$1 ');
	}
	
	$scope.employeeCallback = function (data){
		$scope.submitData.fp__tWfCust_ExpenseClaim__0__EmpCode = data.data.number;
		$scope.submitData.fp__tWfCust_ExpenseClaim__0__EmpName = data.data.nameChi;
		$scope.submitData.fp__tWfCust_ExpenseClaim__0__BefAmount = data.data.befAmount;
	}

    $scope.typeChange = function(data){
    	$scope.$emit("getOperator", { 
			Amount:$scope.submitData.fp__tWfCust_ExpenseClaim__0__Amount,
    		EmpType:$.trim($scope.submitData.fp__tWfCust_ExpenseClaim__0__EmpType),
    		IsCommon:$scope.submitData.fp__tWfCust_ExpenseClaim__0__IsCommon,
    		Company:$.trim($scope.submitData.fp__tWfCust_ExpenseClaim__0__Company),
    		SKIP_NEXTTASK:"否"
    	});
    }
    
    $scope.chgMainAmount = function(index){
    	if(!index) index = -1;
    	var amount = 0;
		for(var i=0;i<20;i++){
			if($scope.submitData["fp__tWfCust_ExpenseClaimDtl__"+i+"__DtlAmount"] && i != index){
				amount = parseFloat(amount) + parseFloat($scope.submitData["fp__tWfCust_ExpenseClaimDtl__"+i+"__DtlAmount"]);
			}
		}
		$scope.submitData.fp__tWfCust_ExpenseClaim__0__Amount = amount;
		
		$scope.$emit("getOperator", { 
			Amount:$scope.submitData.fp__tWfCust_ExpenseClaim__0__Amount,
    		EmpType:$.trim($scope.submitData.fp__tWfCust_ExpenseClaim__0__EmpType),
    		IsCommon:$scope.submitData.fp__tWfCust_ExpenseClaim__0__IsCommon,
    		Company:$.trim($scope.submitData.fp__tWfCust_ExpenseClaim__0__Company),
    		SKIP_NEXTTASK:"否"
    	});
	}
    
    $scope.chgCompany = function (){
    	$scope.$emit("getOperator", { 
			Amount:$scope.submitData.fp__tWfCust_ExpenseClaim__0__Amount,
    		EmpType:$.trim($scope.submitData.fp__tWfCust_ExpenseClaim__0__EmpType),
    		IsCommon:$scope.submitData.fp__tWfCust_ExpenseClaim__0__IsCommon,
    		Company:$.trim($scope.submitData.fp__tWfCust_ExpenseClaim__0__Company),
    		SKIP_NEXTTASK:"否"
    	});
    }
    
    $scope.calcDeductionAmount = function(index){
    	if(!index) index = -1;
    	var amount = 0;
		for(var i=0;i<20;i++){
			if($scope.submitData["fp__tWfCust_ExpenseClaimAdvanceDtl__"+i+"__DeductionAmount"] && i != index){
				amount = parseFloat(amount) + parseFloat($scope.submitData["fp__tWfCust_ExpenseClaimAdvanceDtl__"+i+"__DeductionAmount"]);
			}
		}
		$scope.submitData.fp__tWfCust_ExpenseClaim__0__DeductionAmount = amount;
    }
    
    $scope.takePhoto = function () {
        dao.photo.takePhoto($scope.photoUrlList, "client/wfProcInst/uploadWfPhoto", {type: '2'},
        {quality: 100, targetWidth: 1280, targetHeight:1280 });
  	};
  	
    $scope.pickerPhoto = function () {
    	dao.photo.pickerPhoto($scope.photoUrlList, "client/wfProcInst/uploadWfPhoto", {type: '2'},
        {maxImages: 9, quality: 95, width: 1280, height: 1280});
       
    }
    
    $scope.bxyzdAddCallback = function(i,n){
		$scope.submitData["fp__tWfCust_ExpenseClaimAdvanceDtl__"+i+"__PK"] =-1;
    	if($scope.submitData.tWfCust_ExpenseClaimAdvanceDtl_PK_Edit == "1"){
    		$scope.submitData["fp__tWfCust_ExpenseClaimAdvanceDtl__"+i+"__PK"] =-1;
    	}
    }
    
    // 明细组件删除回调函数
    $scope.fybxDelCallback = function(index){
		$scope.chgMainAmount(index);
    }
    
    $scope.getEmployeeAdvance = function(empCode){
        wfProcInstService.getEmployeeAdvance(empCode).success(function(data){

        	$scope.submitData.fp__tWfCust_ExpenseClaim__0__BefAmount = data.amount;
        });
    }
    
    // 初始化数据（每个流程必须）
    $scope.$on("initData", function(event, data){
    	$scope.submitData = data.submitData;
    	if($scope.submitData && $scope.submitData.tWfCust_ExpenseClaimAdvanceDtl_PK_Show != "1"){
    		var ele = document.getElementById("editDiv");
    		ele.parentNode.parentNode.parentNode.parentNode.removeChild(ele.parentNode.parentNode.parentNode);
    	}
    	
    	if($scope.submitData){
    		$scope.submitData.TaskContinue = "否";
    	}
    	
    	if($scope.submitData && $scope.submitData.fp__tWfCust_ExpenseClaim__0__EmpCode){
    		 $scope.getEmployeeAdvance($scope.submitData.fp__tWfCust_ExpenseClaim__0__EmpCode);
    	}
    	
    	if($scope.submitData && $scope.submitData.tWfCust_ExpenseClaimAdvanceDtl_PK_Edit != "1" && $scope.submitData.tWfCust_ExpenseClaimAdvanceDtl_PK_Show=="1"){
    		var ele = document.getElementById("editDiv");
    		$(ele.parentNode.parentNode.parentNode).find("button").hide();
    	}
    	
    	if($scope.submitData && $scope.submitData.tWfCust_ExpenseClaimDtl_PK_Edit != "1"){
    		var ele = document.getElementById("detailDiv");
    		$(ele.parentNode.parentNode.parentNode).find("button").hide();
    	}
    	
    	if(data.empInfoMap && data.empInfoMap.company){
    		$scope.cmpCode = data.empInfoMap.company;
    	}
    	
    	if(!$scope.submitData){
    		$scope.submitData = {};
    	}
    	if($scope.submitData.fp__tWfCust_ExpenseClaim__0__Company && $scope.submitData.fp__tWfCust_ExpenseClaim__0__Company != null ){
    		$scope.cmpCode = $scope.submitData.fp__tWfCust_ExpenseClaim__0__Company;
    	}
    	if($scope.type == ""){
    		$scope.submitData.fp__tWfCust_ExpenseClaim__0__Deduction = "是";
    		$scope.submitData.fp__tWfCust_ExpenseClaim__0__EmpCode = data.empInfoMap.number;
    		$scope.submitData.fp__tWfCust_ExpenseClaim__0__EmpName = data.empInfoMap.nameChi;
    		$scope.submitData.fp__tWfCust_ExpenseClaim__0__BefAmount = data.empInfoMap.advanceAmount;
    	}
    	
    	if($scope.submitData.fp__tWfCust_ExpenseClaim__0__AdvanceAmount == null){
    		$scope.submitData.fp__tWfCust_ExpenseClaim__0__AdvanceAmount = 0;
    	}
    	
    	if($scope.submitData.fp__tWfCust_ExpenseClaim__0__DeductionAmount == null){
    		$scope.submitData.fp__tWfCust_ExpenseClaim__0__DeductionAmount = 0;
    	}
    	
    	if($scope.submitData.fp__tWfCust_ExpenseClaim__0__DocumentNo ==null){
    		$scope.submitData.fp__tWfCust_ExpenseClaim__0__DocumentNo = "";
    	}
    	
    	if($scope.submitData.fp__tWfCust_ExpenseClaim__0__WfExpenseAdvanceNo ==null){
    		$scope.submitData.fp__tWfCust_ExpenseClaim__0__WfExpenseAdvanceNo = "";
    	}
    	
    	if(data.submitData && data.submitData.photoUrlList){
    		$scope.photoUrlList=data.submitData.photoUrlList;
    	}
    	$scope.commitDatas.confAmount = $scope.submitData.fp__tWfCust_ExpenseClaim__0__DeductionAmount;
    	if(data.empInfoMap && $scope.type ==""){
    		$scope.submitData.fp__tWfCust_ExpenseClaim__0__EmpType = data.empInfoMap.depType;
    		$scope.submitData.fp__tWfCust_ExpenseClaim__0__Company = $.trim(data.empInfoMap.company);
    	}
    	
    	//调用部门2选择组件
    	$scope.initDept2Modal('fp__tWfCust_ExpenseClaimDtl__','DeptCode','DeptDescr',$scope.submitData);
    	$scope.initEmpCardModal('fp__tWfCust_ExpenseClaim__','ActName','CardId','Bank','SubBranch', $scope.submitData, $scope.submitData.fp__tWfCust_ExpenseClaim__0__EmpCode);
    	$scope.initExpenseAdvanceModal('fp__tWfCust_ExpenseClaimAdvanceDtl__','WfExpenseAdvanceNo','AdvanceAmount',$scope.submitData);
    	
    	$scope.$emit("getOperator", {
    		Amount:data.submitData.fp__tWfCust_ExpenseClaim__0__Amount,
    		EmpType:$.trim($scope.submitData.fp__tWfCust_ExpenseClaim__0__EmpType),
    		IsCommon:$scope.submitData.fp__tWfCust_ExpenseClaim__0__IsCommon,
    		Company:$.trim($scope.submitData.fp__tWfCust_ExpenseClaim__0__Company),
    		SKIP_NEXTTASK:"否"
	    });
    	//初始化流程数据
    	$scope.detailNum = data.detailDatas["tWfCust_ExpenseClaimDtl"];
    	$scope.beforeDetailNumData = data.submitData;
    	$scope.bxyzdDetailNum = data.detailDatas["tWfCust_ExpenseClaimAdvanceDtl"];
    	$scope.bxyzdBeforeDetailNumData = data.submitData;
    	//有需要调用其他保存接口的流程节点 在这边设置
    	/*if("usertask18" == $scope.commitDatas.taskDefKey){
    		$scope.commitDatas.url = "client/wfProcInst/doApprFinanceTask";
    	}*/
    });
    
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
    	$scope.rules.fybxGroup.indexs = $scope.fybxIndexs;
    	if($scope.type==""){
    		$scope.submitData.actName=$scope.submitData.fp__tWfCust_ExpenseClaim__0__ActName;
    		$scope.submitData.bank=$scope.submitData.fp__tWfCust_ExpenseClaim__0__Bank;
    		$scope.submitData.cardId=$scope.submitData.fp__tWfCust_ExpenseClaim__0__CardId;
    		$scope.submitData.subBranch=$scope.submitData.fp__tWfCust_ExpenseClaim__0__SubBranch;
    	}
		$scope.submitData.AdvanceAmount=0.0;

		$scope.$emit("wfProcInstSendDatas", {
			datas: $scope.submitData,
			rules: $scope.rules
		});
    });
    
    $scope.$on("wfProcInstDoPass", function(){
    	if($scope.commitDatas.taskDefKey == "usertask17"){
    		$scope.addRule = {
    			tipMessage: "请填写审批金额"
    		},
    		$scope.rules.fp__tWfCust_ExpenseClaim__0__ApproveAmount = $scope.addRule;
    	}
    	
		$scope.$emit("wfProcInstDoPassSendDatas", {
			datas: $scope.submitData,
			rules: $scope.rules
		});
    });
    
    // 请求获取初始化数据（每个流程必须）;
    $scope.$emit("beginGetWfProcInstData");
};
expenseClaimCtrl.$injector = ["$scope", "wfProcInstService"];
window.$controllerProvider.register("expenseClaimCtrl", expenseClaimCtrl);