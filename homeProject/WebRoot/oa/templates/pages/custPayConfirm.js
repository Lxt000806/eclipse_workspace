var custPayConfirmCtrl = function($scope, wfProcInstService, dao, ionicTimePicker){
	// 初始化数据
	$scope.self = $scope;
	$scope.submitData = {};
	$scope.beforeDetailNumData = {};
	$scope.zzqrIndexs = [];
	$scope.submitData.actName="";
	$scope.submitData.bank="";
	$scope.submitData.cardId="";
	$scope.photoUrlList=[];
    $scope.submitData.photoUrlList="";
	$scope.cmpCode = "";
	$scope.defStamp = 43200;
	$scope.flag="false";
	$scope.taskDefKey = "";
    
	$scope.conditions= {
		authorityCtrl:"1"
	}
	
	$scope.rules = {
		fp__tWfCust_CustPayConfirm__0__EmpCode: {
			tipMessage: "申请人不能为空"
		},
		fp__tWfCust_CustPayConfirm__0__Phone: {
			tipMessage: "申请人手机号不能为空"
		},
		fp__tWfCust_CustPayConfirm__0__CustCode: {
			tipMessage: "客户编号不能为空"
		},
		fp__tWfCust_CustPayConfirm__0__Address: {
			tipMessage: "楼盘不能为空"
		},
		fp__tWfCust_CustPayConfirm__0__PayTime: {
			tipMessage: "到账时间不能为空"
		},
		fp__tWfCust_CustPayConfirm__0__PayDate: {
			tipMessage: "到账日期不能为空"
		},
		fp__tWfCust_CustPayConfirm__0__ReceiveActName: {
			tipMessage: "收款户名不能为空"
		},
		fp__tWfCust_CustPayConfirm__0__PayActName: {
			tipMessage: "付款户名不能为空"
		},
		fp__tWfCust_CustPayConfirm__0__PayAmount: {
			tipMessage: "转账金额不能为空"
		},
		fp__tWfCust_CustPayConfirm__0__PayDetail: {
			tipMessage: "转账内容不能为空"
		},
		fp__tWfCust_CustPayConfirm__0__LoansCard: {
			tipMessage: "装修贷款卡不能为空"
		},
		gctdGroup: {
			isGroup: true,
			indexs: [],
			datas: [
			        
			]
		}
	}
	
	$scope.openTime = function(){
		if($scope.submitData.tWfCust_CustPayConfirm_ReceiveActName_Edit !='1'){
			return;
		}
		
		if($scope.submitData && $scope.submitData.fp__tWfCust_CustPayConfirm__0__PayTime){
			$scope.timeStamp = $scope.submitData.fp__tWfCust_CustPayConfirm__0__PayTime ;
			$scope.defStamp = $scope.countDown($scope.timeStamp);
		}
		
	    $scope.ipObj1 = {
		    callback: function (val) {
		      if (typeof (val) === 'undefined') {
		      } else {
		        var selectedTime = new Date(val);
		        $scope.submitData.fp__tWfCust_CustPayConfirm__0__PayTime = $scope.formatTime(val);
		      }
		    },
		    inputTime: $scope.defStamp,   
		    format: 24,         
		    step: 1,         
		    setLabel: '保存',    
		    closeLabel:"关闭"
		  };
		ionicTimePicker.openTimePicker($scope.ipObj1);
	}
	
	$scope.chgTime = function(){
		
       $scope.timeStamp = $scope.submitData.fp__tWfCust_CustPayConfirm__0__PayTime ;
       
       $scope.defStamp = $scope.countDown($scope.timeStamp);
	}
	
	$scope.countDown = function(time) {
		$scope.s = 0;
		$scope.hour = time.split(':')[0];
		$scope.min = time.split(':')[1];
		$scope.sec = time.split(':')[2];
		if (!$scope.sec){
			$scope.sec=0;
		}
		$scope.s = Number($scope.hour * 3600) + Number($scope.min * 60) + Number($scope.sec);
		return $scope.s;

	},
	
	$scope.formatTime = function(time){
		  $scope.hours = Math.floor(time / 3600);
		  $scope.min = Math.floor((time % 3600) / 60);
		  $scope.s = parseInt(time % 3600) % 60;
		  $scope.hh = $scope.hours < 10 ? '0' + $scope.hours : $scope.hours;
		  $scope.mm = $scope.min < 10 ? '0' + $scope.min : $scope.min;
		  $scope.ss = $scope.s < 10 ? '0' + $scope.s : $scope.s;
		  return $scope.hh + ":" + $scope.mm + ":" + $scope.ss;
	}
	
    // 开始提交事件监听 （每个流程必须）
    $scope.$on("wfProcInstDoSubmit", function(){
		$scope.$emit("wfProcInstSendDatas", {
			datas: $scope.submitData,
			rules: $scope.rules
		});
    });
    
	$scope.customerCallback = function(data){
		$scope.submitData["fp__tWfCust_CustPayConfirm__0__Address"] = data.address;
		$scope.submitData["fp__tWfCust_CustPayConfirm__0__CustDescr"] = data.descr;
		$scope.doCheckPayInfo();
	}
	
	dao.datepicker.init($scope, "", function (val) {
		if (val === undefined) {
		} else {
			if($scope.type != ''){
				return;
			}
			var date = transDate(val);
			$scope.submitData["fp__tWfCust_CustPayConfirm__0__PayDate"] = date;
			$scope.doCheckPayInfo();
		}
	});
	
	$scope.doCheckPayInfo = function (){
		if($scope.submitData.fp__tWfCust_CustPayConfirm__0__CustCode &&
				$scope.submitData.fp__tWfCust_CustPayConfirm__0__PayDate &&
				$scope.submitData.fp__tWfCust_CustPayConfirm__0__ReceiveActName &&
				$scope.submitData.fp__tWfCust_CustPayConfirm__0__PayAmount &&
				$scope.submitData.fp__tWfCust_CustPayConfirm__0__PayActName){
			$scope.getCheckPayInfo(
					$scope.submitData.fp__tWfCust_CustPayConfirm__0__CustCode,
					$scope.submitData.fp__tWfCust_CustPayConfirm__0__PayDate,
					$scope.submitData.fp__tWfCust_CustPayConfirm__0__ReceiveActName,
					$scope.submitData.fp__tWfCust_CustPayConfirm__0__PayAmount,
					$scope.submitData.fp__tWfCust_CustPayConfirm__0__PayActName,
					$scope.taskDefKey
			);
		}
	}
	
	$scope.getCheckPayInfo = function (custCode, payDate, receiveActName, payAmount,payActName,flag) {
		wfProcInstService.getCheckPayInfo(custCode, payDate, receiveActName, payAmount,payActName,flag).success(function (data) {
	    	  if(data.success){
	    		  if(data.recordSum>0){
	    			  $scope.flag = "true";
	    		  }else {
	    			  $scope.flag = "false";
	    		  }
	    	  }
	      });
    }
	
	$scope.detailChanged = function (){
		$scope.loansCardRule = {
				tipMessage: "装修贷款卡不能为空"
		},
		delete $scope.rules["fp__tWfCust_CustPayConfirm__0__LoansCard"];

		if($scope.submitData.fp__tWfCust_CustPayConfirm__0__PayDetail == "工程款"){
			$scope.rules["fp__tWfCust_CustPayConfirm__0__LoansCard"]=$scope.loansCardRule;
			$scope.submitData.fp__tWfCust_CustPayConfirm__0__LoansCard = "否"
		} else {
			delete $scope.rules["fp__tWfCust_CustPayConfirm__0__LoansCard"];
		}
		
	}
	
    // 初始化数据（每个流程必须）
    $scope.$on("initData", function(event, data){
    	$scope.submitData = data.submitData;
    	
    	if(data.submitData.taskDefKey){
    		if(data.submitData.taskDefKey == "startevent"){
    			$scope.taskDefKey = "1";
    		}
    	}
    	
    	if(!$scope.submitData){
    		$scope.submitData = {};
    	}
    	if($scope.type == "1" && $scope.submitData){
    		$scope.getCheckPayInfo(
    				$scope.submitData.fp__tWfCust_CustPayConfirm__0__CustCode,
    				$scope.submitData.fp__tWfCust_CustPayConfirm__0__PayDate,
    				$scope.submitData.fp__tWfCust_CustPayConfirm__0__ReceiveActName,
    				$scope.submitData.fp__tWfCust_CustPayConfirm__0__PayAmount,
    				$scope.submitData.fp__tWfCust_CustPayConfirm__0__PayActName
    		);
    	}
    	//$scope.getCheckPayInfo('','','','','');
    	$scope.initRcvActModal('fp__tWfCust_CustPayConfirm__',"RcvActCode",'ReceiveActName',$scope.submitData);
    	
    	if($scope.type == ""){
    		$scope.submitData.fp__tWfCust_CustPayConfirm__0__EmpCode = data.empInfoMap.number;
    		$scope.submitData.fp__tWfCust_CustPayConfirm__0__EmpName = data.empInfoMap.nameChi;
    		$scope.submitData.fp__tWfCust_CustPayConfirm__0__Phone = data.empInfoMap.phone;
    	}
    	
    	$scope.$emit("getOperator", {});
    });
    
    $scope.initRcvActModal = function (preName,code,descr,submitData) {
        var i = 0;
        dao.modal.init($scope, "modals/rcvAct.html", "fade-in-up",
          function (index) {
            i = index;
            $scope["rcvActModal"].show();
            $scope.searchRcvAct();
          },
          function (rcvActData) {
        	  submitData[preName+i+"__"+code] = rcvActData.code;
              submitData[preName+i+"__"+descr] = rcvActData.descr;
            $scope.doCheckPayInfo();
            $scope["rcvActModal"].hide();
          }, "rcvActModal", "openRcvActModal", "closeRcvActModal"
        );
      };
    
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
custPayConfirmCtrl.$injector = ["$scope", "wfProcInstService"];
window.$controllerProvider.register("custPayConfirmCtrl", custPayConfirmCtrl);