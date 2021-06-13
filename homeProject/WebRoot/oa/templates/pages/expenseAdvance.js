var expenseAdvanceCtrl = function($scope, wfProcInstService, dao){
	// 初始化数据
	$scope.remarks = localStorage.remarks ? localStorage.remarks.replace(/\r\n/g, "<br/>") : '';
	$scope.self = $scope;
	$scope.submitData = {};
	$scope.beforeDetailNumData = {};
	$scope.fyyzIndexs = [];
	$scope.submitData.actName="";
	$scope.submitData.bank="";
	$scope.submitData.cardId="";
	$scope.photoUrlList=[];
    $scope.submitData.photoUrlList="";
	$scope.cmpCode = "";
	$scope.selfDept = "1";
    
	$scope.rules = {
		fp__tWfCust_ExpenseAdvance__0__EmpCode: {
			tipMessage: "预支人不能为空"
		},
		fp__tWfCust_ExpenseAdvance__0__ActName: {
			tipMessage: "户名不能为空"
		},
		fp__tWfCust_ExpenseAdvance__0__CardId: {
			tipMessage: "卡号不能为空"
		},
		fp__tWfCust_ExpenseAdvance__0__Bank: {
			tipMessage: "开户行不能为空"
		},
		gctdGroup: {
			isGroup: true,
			indexs: [],
			datas: [
				{
					template: "fp__tWfCust_ExpenseAdvanceDtl__?__DtlAmount",
					tipMessage: "明细预支额不能为空"
				},
				{
					template: "fp__tWfCust_ExpenseAdvanceDtl__?__Remarks",
					tipMessage: "预支说明不能为空"
				},
			]
		}
	}
	
	$scope.formatCardId = function (){
		$scope.submitData.fp__tWfCust_ExpenseAdvance__0__CardId = 
			$scope.submitData.fp__tWfCust_ExpenseAdvance__0__CardId.replace(/\s/g,'').replace(/[^\d]/g,'').replace(/(^(\d{4})|(\d{4}))(?=\d)/g,'$1 ');
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
    	$scope.rules.gctdGroup.indexs = $scope.fyyzIndexs;
    	if($scope.type==""){
    		$scope.submitData.actName=$scope.submitData.fp__tWfCust_ExpenseAdvance__0__ActName;
    		$scope.submitData.bank=$scope.submitData.fp__tWfCust_ExpenseAdvance__0__Bank;
    		$scope.submitData.subBranch=$scope.submitData.fp__tWfCust_ExpenseAdvance__0__SubBranch;
    		$scope.submitData.cardId=$scope.submitData.fp__tWfCust_ExpenseAdvance__0__CardId;
    	}
    	
    	if($scope.submitData.fp__tWfCust_ExpenseAdvance__0__SubBranch == null ){
    		$scope.submitData.fp__tWfCust_ExpenseAdvance__0__SubBranch = "";
    	}
    	
		$scope.$emit("wfProcInstSendDatas", {
			datas: $scope.submitData,
			rules: $scope.rules
		});
    });
    
    /*//主管以上员工
    $scope.conditions = {
    	isManager:"1",
    };*/
    
    $scope.employeeCallback = function (data){
    	var expenseDate = new Date(data.data.expenseDate);
		var date = new Date();
		if(data.data.expenseDate &&(expenseDate.getMonth()<date.getMonth() || expenseDate.getFullYear()<date.getFullYear())){
			dao.popup.alert("该员工存在跨月预支单未核销");
		}

		$scope.submitData.fp__tWfCust_ExpenseAdvance__0__EmpCode = data.data.number;
		$scope.submitData.fp__tWfCust_ExpenseAdvance__0__EmpName = data.data.nameChi;
		$scope.submitData.fp__tWfCust_ExpenseAdvance__0__BefAmount = data.data.befAmount;
	}

    $scope.chgMainAmount = function(index){
    	if(!index) index = -1;
    	var amount = 0;
		for(var i=0;i<20;i++){
			if($scope.submitData["fp__tWfCust_ExpenseAdvanceDtl__"+i+"__DtlAmount"] && i != index){
				amount = parseFloat(amount) + parseFloat($scope.submitData["fp__tWfCust_ExpenseAdvanceDtl__"+i+"__DtlAmount"]);
			}
		}
		$scope.submitData.fp__tWfCust_ExpenseAdvance__0__Amount = amount;
		$scope.$emit("getOperator", { 
    		Amount:$scope.submitData.fp__tWfCust_ExpenseAdvance__0__Amount,
    		Company:$.trim($scope.submitData.fp__tWfCust_ExpenseAdvance__0__Company)
    	});
	}
    
    $scope.chgCompany = function (){
    	$scope.$emit("getOperator", { 
			Amount:$scope.submitData.fp__tWfCust_ExpenseAdvance__0__Amount,
    		Company:$.trim($scope.submitData.fp__tWfCust_ExpenseAdvance__0__Company)
    	});
    }
    
    $scope.getEmployeeAdvance = function(empCode){
        wfProcInstService.getEmployeeAdvance(empCode).success(function(data){

        	$scope.submitData.fp__tWfCust_ExpenseAdvance__0__BefAmount = data.amount;
        });
    }
    
    // 明细组件删除回调函数
    $scope.fyyzDelCallback = function(index){
		$scope.chgMainAmount(index);
    }
    
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
    	
    	if(data.empInfoMap && data.empInfoMap.companyCode){
    		$scope.cmpCode = data.empInfoMap.company;
    	}
    	
    	if(!$scope.submitData){
    		$scope.submitData = {};
    	}
    	if(data.submitData && data.submitData.photoUrlList){
    		$scope.photoUrlList=data.submitData.photoUrlList;
    	}
    	
    	if($scope.submitData.fp__tWfCust_ExpenseAdvance__0__Company && $scope.submitData.fp__tWfCust_ExpenseAdvance__0__Company != null ){
    		$scope.cmpCode = $scope.submitData.fp__tWfCust_ExpenseAdvance__0__Company;
    	}
    	
    	if($scope.submitData && $scope.submitData.fp__tWfCust_ExpenseAdvance__0__EmpCode){
	   		 $scope.getEmployeeAdvance($scope.submitData.fp__tWfCust_ExpenseAdvance__0__EmpCode);
	   	}
    	
    	if($scope.type == ""){
    		$scope.submitData.fp__tWfCust_ExpenseAdvance__0__EmpCode = data.empInfoMap.number;
    		$scope.submitData.fp__tWfCust_ExpenseAdvance__0__EmpName = data.empInfoMap.nameChi;
    		$scope.submitData.fp__tWfCust_ExpenseAdvance__0__BefAmount = data.empInfoMap.advanceAmount;
    		$scope.submitData.fp__tWfCust_ExpenseAdvance__0__Company = data.empInfoMap.company;
    	}
    	//调用部门2选择组件
    	$scope.submitData.fp__tWfCust_ExpenseAdvance__0__PayAccount = "";
    	$scope.initDept2Modal('fp__tWfCust_ExpenseAdvanceDtl__','DeptCode','DeptDescr',$scope.submitData);
    	$scope.initEmpCardModal('fp__tWfCust_ExpenseAdvance__','ActName','CardId','Bank','SubBranch',$scope.submitData, $scope.submitData.fp__tWfCust_ExpenseAdvance__0__EmpCode);
    	
    	$scope.$emit("getOperator", {
    		Amount:data.submitData.fp__tWfCust_ExpenseAdvance__0__Amount,
    		Company:$.trim($scope.submitData.fp__tWfCust_ExpenseAdvance__0__Company)
	    });
    	 
    	//初始化流程数据
    	$scope.detailNum = data.detailDatas["tWfCust_ExpenseAdvanceDtl"];
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
    $scope.$emit("getOperator", {Amount:0.0});
};
expenseAdvanceCtrl.$injector = ["$scope", "wfProcInstService"];
window.$controllerProvider.register("expenseAdvanceCtrl", expenseAdvanceCtrl);