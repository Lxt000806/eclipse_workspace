var resignRecordCtrl = function($scope, dao){
	// 初始化数据
	$scope.self = $scope;
	$scope.submitData = {};
	$scope.beforeDetailNumData = {};
	$scope.empInfoMap = {};
	$scope.options = [{}];
	$scope.label = "";
	$scope.rules = {
		fp__tWfCust_ResignRecord__0__LeaveType: {
			tipMessage: "离职类型不能为空"
		},
		fp__tWfCust_ResignRecord__0__LeaveDate: {
			tipMessage: "离职时间不能为空"
		},
		fp__tWfCust_ResignRecord__0__CompanyItems: {
			tipMessage: "行政物品不能为空"
		},
	}
	
	//日期选择组件
	dao.datepicker.init($scope, "", function (val) {
		if (val === undefined) {
		} else {
			var date = transDate(val);
			$scope.submitData.fp__tWfCust_ResignRecord__0__LeaveDate = date;
		}
	});
	
	$scope.ManageOptions = [
	    {text: "不适应本职", checked: false },
	    {text: "工作缺少人指导", checked: false },
	    {text: "工作压力大，加班频繁", checked: false },
	    {text: "没哟业绩", checked: false },
	    {text: "上下级关系不合", checked: false },
	    {text: "其他", checked: false },
    ];
	
	$scope.PersonOptions = [
	    {text: "创业", checked: false },
	    {text: "换新工作", checked: false },
	    {text: "继续升学", checked: false },
	    {text: "健康原因", checked: false },
	    {text: "结婚/订婚/怀孕", checked: false },
	    {text: "转项目经理", checked: false },
	    {text: "自离", checked: false },
	    {text: "其他", checked: false },
    ];
	
	$scope.FamilyOptions = [
	    {text: "回家带孩子", checked: false },
	    {text: "家里有急事", checked: false },
	    {text: "家里有人生病", checked: false },
	    {text: "其他", checked: false },
    ];
	
	$scope.CompanyOptions = [
	     {text: "不适应公司文化", checked: false },
	     {text: "发展、晋升空间小", checked: false },
	     {text: "工作环境（噪音、办公地方）及设施", checked: false },
	     {text: "培训、学习机会少", checked: false },
	     {text: "劝退/辞退", checked: false },
	     {text: "薪酬待遇低，福利不完善", checked: false },
	     {text: "其他", checked: false },
     ];
	$scope.changeReasonType = function(){
		$scope.submitData.fp__tWfCust_ResignRecord__0__Reason="";
		if($scope.submitData.fp__tWfCust_ResignRecord__0__ReasonType=="管理原因"){
			$scope.options=$scope.ManageOptions;
		} else if($scope.submitData.fp__tWfCust_ResignRecord__0__ReasonType=="个人原因"){
			$scope.options=$scope.PersonOptions;
		} else if($scope.submitData.fp__tWfCust_ResignRecord__0__ReasonType=="家庭原因"){
			$scope.options=$scope.FamilyOptions;
		} else if($scope.submitData.fp__tWfCust_ResignRecord__0__ReasonType=="公司原因"){
			$scope.options=$scope.CompanyOptions;
		} 
	}

    
    $scope.ComeBackOptions = [
		{text: "取决于工资福利", checked: false },
		{text: "取决于公司文化", checked: false },
		{text: "取决于提供岗位", checked: false },
		{text: "再也不愿意回来", checked: false },
	];
    //管理原因返回函数
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
		$scope.submitData.fp__tWfCust_ResignRecord__0__Reason = value;
    }
    
    //回来原因返回函数
    $scope.selComeBackCallback = function(data){
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
		$scope.submitData.fp__tWfCust_ResignRecord__0__ComeBackReason = value;
    }
    
    // 开始提交事件监听 （每个流程必须）
    $scope.$on("wfProcInstDoSubmit", function(){
		$scope.$emit("wfProcInstSendDatas", {
			datas: $scope.submitData,
			rules: $scope.rules
		});
    });
    
    $scope.getFormatDate = function(time) { 
    	if(typeof time == "string" && time){
    		time = time.replace(/-/g, "/");
    	}
    	var date = new Date(time); 
    	var Str=date.getFullYear() + '-' + 
    	((date.getMonth() + 1).toString().length==1?'0'+(date.getMonth()+1):date.getMonth()+1) + '-' + 
    	(date.getDate().toString().length==1?'0'+date.getDate():date.getDate() ) + ' ' + 
    	(date.getHours().toString().length==1?'0'+date.getHours():date.getHours() )+ ':' + 
    	(date.getMinutes().toString().length==1?'0'+date.getMinutes():date.getMinutes())+ ':' + 
    	(date.getSeconds().toString().length==1?'0'+date.getSeconds():date.getSeconds()); 
    	return Str; 
    } 
    
    $scope.getCompanyYears = function(time) {
    	if(typeof time == "string" && time){
    		time = time.replace(/-/g, "/");
    	}
    	var date = new Date(); 
    	var	dateYear = date.getFullYear();
    	var	dateMonth = date.getMonth();
    	var	empDate = new Date(time);
    	var	empYear = empDate.getFullYear();
    	var empMonth = empDate.getMonth();	
    	var year = 0;   //2010-05    //2011-04
    	var month = 0;
    	if(dateMonth >= empMonth ){
    		month = dateMonth - empMonth;
    		year = dateYear - empYear;
    	}else{
    		month = dateMonth+12 - empMonth;
    		year = dateYear - 1 - empYear;
    	}
    	return year+"年"+month+"月";
    } 
    
    // 初始化数据（每个流程必须）
    $scope.$on("initData", function(event, data){
    	if($scope.type==""){
    		$scope.submitData.fp__tWfCust_ResignRecord__0__Company = data.empInfoMap.companyCode;
    		$scope.submitData.fp__tWfCust_ResignRecord__0__CompanyDescr = data.empInfoMap.company;
    		$scope.submitData.fp__tWfCust_ResignRecord__0__EmpCode = data.empInfoMap.number;
    		$scope.submitData.fp__tWfCust_ResignRecord__0__EmpName = data.empInfoMap.nameChi;
    		$scope.submitData.fp__tWfCust_ResignRecord__0__Department2 = data.empInfoMap.department2Descr;
    		$scope.submitData.fp__tWfCust_ResignRecord__0__Position = data.empInfoMap.positionDescr;
    		$scope.submitData.fp__tWfCust_ResignRecord__0__JoinDate = data.empInfoMap.joinDate;	
    		$scope.submitData.fp__tWfCust_ResignRecord__0__EmpType = data.empInfoMap.type;	
    		$scope.submitData.fp__tWfCust_ResignRecord__0__ApplyDate = $scope.getFormatDate(new Date());	
    		var joinDate = new Date($scope.submitData.fp__tWfCust_ResignRecord__0__JoinDate);
    		var leaveDate = new Date();
    		var joinYear = joinDate.getFullYear();
    		var leaveYear = leaveDate.getFullYear();
    		$scope.submitData.fp__tWfCust_ResignRecord__0__CmpYears= $scope.getCompanyYears($scope.submitData.fp__tWfCust_ResignRecord__0__JoinDate);
    		$scope.$emit("getOperator", {
    			EmpType:$scope.submitData.fp__tWfCust_ResignRecord__0__EmpType,
    			Company:$scope.submitData.fp__tWfCust_ResignRecord__0__Company
    	    });
    	}else{
    		$scope.submitData=data.submitData;
    		$scope.$emit("getOperator", {
    			EmpType:$scope.submitData.fp__tWfCust_ResignRecord__0__EmpType,
    			Company:$scope.submitData.fp__tWfCust_ResignRecord__0__Company
    	    });
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
    
};
resignRecordCtrl.$injector = ["$scope", "dao"];
window.$controllerProvider.register("resignRecordCtrl", resignRecordCtrl);
