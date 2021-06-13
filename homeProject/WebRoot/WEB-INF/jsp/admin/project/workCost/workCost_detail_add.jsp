<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<title>基础人工成本--增加</title>
		<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
		<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
		<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
		<META HTTP-EQUIV="expires" CONTENT="0" />
		<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
		<%@ include file="/commons/jsp/common.jsp" %>
		<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
		<script src="${resourceRoot}/pub/component_worker.js?v=${v}" type="text/javascript"></script>
		<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
		<script src="${resourceRoot}/pub/component_prjWithHold.js?v=${v}" type="text/javascript"></script>
	<script type="text/javascript">
	var selectRows = [];
	//责任人类型修改次数
	var clickNum=0;
	//修改工资类型时改变工种分类
	function changeWorkType(){
		var salaryType=$("#salaryType").val();
		var type='${workCostDetail.type}';
		var checkStatusDescr=$("#checkStatusDescr").val();
		if(type=="1"){
			var salaryType=$("#salaryType").val();
			if(salaryType=="05" || salaryType==""){
					$("#isWithHold").removeAttr("disabled");
					$("#isWithHold").val("0");
					$("#isWithHold").attr("disabled",true);
				}else{
					if(checkStatusDescr=="未结算" || checkStatusDescr=="客户已结算"){
						$("#isWithHold").removeAttr("disabled");
						$("#isWithHold").val("0");
						$("#isWithHold").attr("disabled",true);
					}else{
						$("#isWithHold").removeAttr("disabled");
						$("#isWithHold").val("1");
						$("#isWithHold").attr("disabled",true);
					}
				}
		}
		$("#prjItem").removeAttr("disabled");
		$("#prjItem").val("");
		$("#prjItem").attr("disabled","disabled");
		$("#endDate").val("");
		$("#confirmDate").val("");
		$("#openComponent_employee_confirmCzy").val("");
		$("#endDate").val("");
		$("#confirmDate").val("");
		var workType1=" ";
		var workType2=" ";
		if(salaryType=="02" || salaryType=="03"){
			workType1="01";
			workType2="006";
		}
		Global.LinkSelect.setSelect({firstSelect:'workType1',
								firstValue:workType1,
								secondSelect:'workType2',
								secondValue:workType2
								});	
		findDescr('workType2','workType2Descr');
		changeType2();
	}
	//根据id查descr
	function findDescr(id,taget) {
		var value=$("#"+id).val();
		if(value!="" && value!=null)
		$.ajax({
			url : '${ctx}/admin/workCostDetail/findDescr',
			type : 'post',
			data : {
				'value' :value,
				'id' : id,
			},
			async:false,
			dataType : 'json',
			cache : false,
			error : function(obj) {
				showAjaxHtml({
					"hidden" : false,
					"msg" : '保存数据出错~'
				});
			},
			success : function(obj) {
				$("#" + taget).val(obj.Descr);
			}
		});
	}
	//根据id查descr
	function findDescrByXtdm(cbm,id,taget) {
		$.ajax({
			url : '${ctx}/admin/intProgDetail/findDescr',
			type : 'post',
			data : {
				'cbm' : $("#"+cbm).val(),
				'id' : id
			},
			async:false,
			dataType : 'json',
			cache : false,
			error : function(obj) {
				showAjaxHtml({
					"hidden" : false,
					"msg" : '保存数据出错~'
				});
			},
			success : function(obj) {
				$("#" + taget).val(obj.note);
			}
		});
	}
		
		function changeType2(){
			var workType1=$("#workType1").val();
			var workType2=$("#workType2").val();
			var isFz=$("#isFzc").val();
			var custCode=$("#custCode").val();
			//统计单项工种余额、单项工种发包、单项工种累计支出、总发包余额、总发包、总支出
						$.ajax({
							url:'${ctx}/admin/workCostDetail/findCostByCodeWork',
							type: 'post',
							data: {'custCode':custCode,'workType2':workType2},
							dataType: 'json',
							cache: false,
							error: function(obj){
								showAjaxHtml({"hidden": false, "msg": '保存数据出错~'});
						    },
						    success: function(obj){
						    	$("#allCustCtrl").val(obj.AllCustCtrl);
						    	$("#allCustCost").val(obj.AllCustCost);
						    	$("#allLeaveCustCost").val(obj.AllLeaveCustCost);
						    	if(workType2!=""){
						    		$("#custCtrl").val(obj.CustCtrl);
						    		$("#custCtrl_Kzx").val((parseFloat(obj.CustCtrl)*1.1).toFixed(2));//保留两位小数
						    		$("#custCost").val(obj.CustCost);
						    		$("#leaveCustCost").val(obj.LeaveCustCost);
						    	}
						    }
	 					});
		 				if('${workCostDetail.type}'=='1'){
							initWithHoldNo();
						}
	 					if(custCode!="" && workType2!=""){
		 					//查询prj相关,统计合同总价、已领工资
		 					$.ajax({
								url:'${ctx}/admin/workCostDetail/findPrjByCodeWork',
								type: 'post',
								data: {'custCode':custCode,'workType2':workType2},
								dataType: 'json',
								cache: false,
								error: function(obj){
									showAjaxHtml({"hidden": false, "msg": '保存数据出错~'});
									$("#prjItem").removeAttr("disabled");
									$("#prjItem").val("");
									$("#prjItem").attr("disabled","disabled");
							    	$("#endDate").val("");
							    	$("#confirmDate").val("");
							    	$("#openComponent_employee_confirmCzy").val("");
							    	$("#endDate").val("");
							    	$("#confirmDate").val("");
							    },
							    success: function(obj){
							    	$("#prjItem").removeAttr("disabled");
							    	$("#prjItem").val(obj.PrjItem!=null?obj.PrjItem:"");
							    	$("#prjItem").attr("disabled","disabled");
							    	$("#endDate").val(obj.EndDate!=null?new Date(parseInt(obj.EndDate)).format("yyyy-MM-dd hh:mm:ss"):"");
							    	$("#confirmDate").val(obj.ConfirmDate!=null?new Date(parseInt(obj.ConfirmDate)).format("yyyy-MM-dd hh:mm:ss"):"");
							    	if(obj.ConfirmCZYDescr!=undefined){
								    	$("#confirmCzy").openComponent_employee({
											showValue:obj.ConfirmCZY,
											showLabel:obj.ConfirmCZYDescr,
											readonly:true
										});
							    	}else{
							    		$("#confirmCzy").val("");
							    		$("#openComponent_employee_confirmCzy").val("");
							    	}
									$("#confirmCzyDescr").val(obj.ConfirmCZYDescr!=null?obj.ConfirmCZYDescr:"");
									$("#totalAmount").val(obj.Amount!=null?obj.Amount:"0");
									$("#gotAmount").val(obj.ConfirmAmount!=null?obj.ConfirmAmount:"0");
									$("#workerPlanOffer").val(obj.WorkerPlanOffer==undefined?"0":obj.WorkerPlanOffer);
							    }
		 					});
	 				}
	 				if(workType2==""){
	 					$("#prjItem").removeAttr("disabled");
						$("#prjItem").val("");
						$("#prjItem").attr("disabled","disabled");
						$("#endDate").val("");
						$("#confirmDate").val("");
						$("#openComponent_employee_confirmCzy").val("");
						$("#endDate").val("");
						$("#confirmDate").val("");
						$("#custCtrl").val("0");
						$("#custCtrl_Kzx").val("0");
						$("#custCost").val("0");
						$("#leaveCustCost").val("0");
	 				}
	 				if(workType2=="020"&&isFz=="1"){
	 					$(".type020").attr("hidden","hidden");
	 				}else{
	 					$(".type020").removeAttr("hidden");
	 				}
	 				
		}
		var count=0;
		//预扣单号初始化
		function initWithHoldNo(){
			var workType1=$("#workType1").val();
			var workType2=$("#workType2").val();
			var custCode=$("#custCode").val();
			var isWithHold=$("#isWithHold").val();
			$("#withHoldNo").openComponent_prjWithHold({
				showValue:"${workCostDetail.withHoldNo}",
				isWithHold:"isWithHold",
				custCode:"custCode",
				workType2:"workType2",
				condition:{'custCode':custCode,'workType1':workType1,'workType2':workType2,'custDescr':"${workCostDetail.custDescr}"},
				callBack:function(data){
					//计算预扣金额和领取金额
					if(data.pk!=""){
						//获取父窗口的表格
						var rows = $(top.$("#iframe_detail")[0].contentWindow.document.getElementById("detailDataTable")).jqGrid("getRowData");
						var param = {"workCostDetailJson": JSON.stringify(rows)};
						Global.Form.submit("page_form","${ctx}/admin/workCostDetail/findYukou",param,function(obj){
							if(obj!="" && obj!=null){
								$("#withHoldCost").val(obj.Amount);
								$("#rcvCost").val(obj.ret);
							}
						});  
					}
				}
			});
			var withHoldNo=$("#withHoldNo").val();
			if(withHoldNo!="" && withHoldNo!="0"){
				//获取父窗口的表格
				var rows = $(top.$("#iframe_detail")[0].contentWindow.document.getElementById("detailDataTable")).jqGrid("getRowData");
				var param = {"workCostDetailJson": JSON.stringify(rows)};
				Global.Form.submit("page_form","${ctx}/admin/workCostDetail/findYukou",param,function(obj){
								if(obj!="" && obj!=null){
									$("#withHoldCost").val(obj.Amount);
									$("#rcvCost").val(obj.ret);
								}
							});  
			}
			if(count>1){
				if(isWithHold=="0"){
					$("#withHoldNo").setComponent_prjWithHold({readonly: true});
					$("#openComponent_prjWithHold_withHoldNo").val("0");
				}else{
					$("#openComponent_prjWithHold_withHoldNo").val("");
					$("#withHoldNo").val("");
				}
			}
			count++;
		}
		/**初始化表格*/
		$(function(){
			if("${AftCustCode}".indexOf($("#custCode").val().trim())!=-1 && $("#custCode").val().trim()!=""){
				$("#refCustCode").parent().removeClass("hidden");
				$("#faultType").parent().parent().removeClass("hidden");
			}else{
				$("#refCustCode").parent().addClass("hidden");
				$("#faultType").parent().parent().addClass("hidden");
			}
			$("#prjItem").attr("disabled","disabled");
			var salaryType=$("#salaryType").val();
			if(salaryType!=""){
				$("isWithHold").val(0);
			}
			findDescrByXtdm('status','WorkCostStatus','statusDescr');
			findDescrByXtdm('isWithHold','YESNO','isWithHoldDescr');
			findDescrByXtdm('prjItem','PRJITEM','prjItemDescr');
			Global.LinkSelect.initSelect("${ctx}/admin/workCostDetail/workTypeByAuthority","workType1","workType2");
			Global.LinkSelect.setSelect({firstSelect:'workType1',
								firstValue:'${workCostDetail.workType1}',
								secondSelect:'workType2',
								secondValue:'${workCostDetail.workType2}'
								});	
			$("#workerCode").openComponent_worker({
				showValue:"${workCostDetail.workerCode}",
				showLabel:"${workCostDetail.actName}",
				callBack : function(data) {
					$("#cardId").val(data["CardID"]);
					$("#cardId2").val(data["CardId2"]);
					$("#actName").val(data["NameChi"]);
					$("#isSignDescr").val(data["issigndescr"]);
					$("#qualityFeeBegin").val(data["qualityfeebegin"]);
				},
			});
			$("#openComponent_worker_workerCode").attr("readonly",true);
			$("#applyMan").openComponent_employee({
				showValue:"${workCostDetail.applyMan}",
				showLabel:"${workCostDetail.applyManDescr}",
				callBack:function(data){
					$("#applyManDescr").val(data.namechi);
				}
			});
			if("${workCostDetail.refCustCode}"!=""){
				$("#refCustCode").parent().removeClass("hidden");
			}else{
				$("#refCustCode").parent().addClass("hidden");
			} 
			$("#custCode").openComponent_customer({
				showValue:"${workCostDetail.custCode}",
				showLabel:"${workCostDetail.custDescr}",
			 	condition:{status:"4,5"},
				callBack :  function(data) {
					if((data["checkstatusdescr"]=='项目经理已领' || data["checkstatusdescr"]=='项目经理要领') && '${workCostDetail.type}'=='2'){
				 		var custCode=data.code;
						var custDescr=data.descr;
						if(custCode=="" || custDescr==""){
							$("#openComponent_customer_custCode").val("");
						}else{
							$("#custCode").openComponent_customer({
								showValue:$("#custCode").val(),
								showLabel:$("#custDescr").val()
							});
						}
						art.dialog({
				       		content: "该楼盘项目经理结算已完成的不允许在进行工地扣款申请！",
				       	});
				       	return;
					}
					console.log(data);
					var type='${workCostDetail.type}';
					$("#custCode").val(data["code"]);
					$("#custDescr").val(data["descr"]);
					$("#address").val(data["address"]);
					$("#documentNo").val(data["documentno"]);
					$("#custTypeDescr").val(data["custtypedescr"]);
					if(isNaN(data["signdate"])){
						$("#signDate").val(data["signdate"]);
					}else{
						$("#signDate").val(formatDate(data["signdate"]));
					}
					$("#custStatus").val(data["status"]);
					$("#checkStatusDescr").val(data["checkstatusdescr"]);
					$("#isWaterItemCtrl").val(data["iswateritemctrl"]);
					$("#isWaterItemCtrlDescr").val(data["iswateritemctrldescr"]);
				 	if(data["projectmandescr"]!="" && data["projectman"]!=""){
						$("#applyManDescr").val(data["projectmandescr"]);
						$("#applyMan").openComponent_employee({
							showValue:data["projectman"],
							showLabel:data["projectmandescr"],
							callBack:function(data){
								$("#applyManDescr").val(data.namechi);
							}
						});
					}else{
						$("#applyMan").val("");
						$("#openComponent_employee_applyMan").val("");
					}
					if(type=="1"){
						var salaryType=$("#salaryType").val();
						if(salaryType=="05"){
							$("#isWithHold").removeAttr("disabled");
							$("#isWithHold").val("0");
							$("#isWithHold").attr("disabled",true);
						}else{
							if(data["checkstatusdescr"]=="未结算" || data["checkstatusdescr"]=="客户已结算"){
								$("#isWithHold").removeAttr("disabled");
								$("#isWithHold").val("0");
								$("#isWithHold").attr("disabled",true);
							}else{
								$("#isWithHold").removeAttr("disabled");
								$("#isWithHold").val("1");
								$("#isWithHold").attr("disabled",true);
							}
						}
					}
					changeType2();
					if("${AftCustCode}".indexOf(data.code)!=-1){
						$("#refCustCode").parent().removeClass("hidden");
						$("#faultType").parent().parent().removeClass("hidden");
					}else{
						$("#refCustCode").parent().addClass("hidden");
						$("#refCustCode").val("");
						$("#openComponent_customer_refCustCode").val("");
						$("#faultType").parent().parent().addClass("hidden");
						$("#faultType").val("");
						$("#faultMan").val("");
						$("#faultTypeDescr").val("");
						$("#faultManDescr").val("");
						$("#prjQualityFee").val(0);
					} 
					$("#constructStatus").val(data["constructstatus"]);
				}, 
			});
			$("#refCustCode").openComponent_customer({
   				showValue:"${workCostDetail.refCustCode}",
   				showLabel:"${workCostDetail.refCustDescr}",
   				condition:{
   					status:"4,5",
					disabledEle:"status_NAME"
   				},
   				callBack:function(data){
   					var custCode=$("#custCode").val();
   					$("#refProjectMan").val(data["projectman"]);
   					$("#refProjectManDescr").val(data["projectmandescr"]);
   					if(custCode=="CT020893"){//售后工地，填入关联客户项目经理
   						$("#applyManDescr").val(data["projectmandescr"]);
   						$("#applyMan").setComponent_employee({
							showValue:data["projectman"],
							showLabel:data["projectmandescr"],
						});
   					}
   					$("#faultMan").val(data["projectman"]);
   					$("#faultManDescr").val(data["projectmandescr"]);
   					$("#refCustDescr").val(data.descr);
   					$("#refAddress").val(data.address);
   					$("#prjQualityFee").val(data.prjqualityfee);
   					if($("#constructStatus").val()=="7"){
   						$("#applyManDescr").val(data["projectmandescr"]);
   						$("#applyMan").setComponent_employee({
							showValue:data["projectman"],
							showLabel:data["projectmandescr"],
						});
						$("#salaryType").val('02');
						$("#salaryType").change();
   					}
   					if($("#faultType").val()=="1"){
	   					$("#openComponent_worker_faultWorker").val("");
						$("#faultEmp").parent().removeClass("hidden");
						$("#faultWorker").parent().addClass("hidden");
						$("#faultEmp").openComponent_employee({
							showValue:data["projectman"],
							showLabel:data["projectmandescr"],
							condition:{
								isStakeholder:"1",custCode:$("#refCustCode").val()
							},
							callBack:function(obj){
								$("#faultMan").val(obj.number);
								$("#faultManDescr").val(obj.namechi);
								if(obj.prjqualityfee!=""){
									$("#prjQualityFee").parent().removeClass("hidden");
									$("#prjQualityFee").val(obj.prjqualityfee);
								}else{
									$("#prjQualityFee").parent().addClass("hidden");
								}
							}
						});	
						if(data.projectmandescr==""){
							$("#openComponent_employee_faultEmp").val("");
						}	
   					}else if($("#faultType").val()=="2"){
  						$("#openComponent_employee_faultEmp").val("");
						$("#faultWorker").parent().removeClass("hidden");
						$("#faultEmp").parent().addClass("hidden");
						$("#faultWorker").openComponent_worker({
							showValue:clickNum==1?"${workCostDetail.faultMan}":"",
							showLabel:clickNum==1?"${workCostDetail.faultManDescr}":"",
							condition:{
								refCustCode:data.code
							},
							callBack:function(obj){
								$("#faultMan").val(obj.Code);
								$("#faultManDescr").val(obj.NameChi);
							}
						});
   					}
   				}
   			});
			$("#confirmCzy").openComponent_employee({
				showValue:"${workCostDetail.confirmCzy}",
				showLabel:"${workCostDetail.confirmCzyDescr}",
				readonly:true
			});
			$("#openComponent_employee_confirmCzy").attr("readonly",true); 
			//判断操作类型，新增，修改，查看
			if('${workCostDetail.button}'=='update'){
				findDescr('salaryType','salaryTypeDescr');
				findDescr('workType1','workType1Descr');
				findDescr('workType2','workType2Descr');
			}else if('${workCostDetail.button}'=='view'){
				$("#custCode").setComponent_customer({readonly: true});
				$("#applyMan").setComponent_employee({readonly: true});
				$("#workerCode").setComponent_worker({readonly: true});
				$("input").attr("disabled","true");
				$("select").attr("disabled","true");
				$("textarea").attr("disabled","true");
			}
			//初始化prj，金额等
			changeType2();
			$("#workerPlanOfferBtn").on("click",function () {
				Global.Dialog.showDialog("quotaSalaryDetail",{
					title:"定额工资—明细查看",
					url:"${ctx}/admin/workCostDetail/goDeDetail",
					postData:{custCode:"${workCostDetail.custCode}",workType2:"${workCostDetail.workType2}"},		
					height: 540,
					width:870,
				});
			});
			changeFaultType();
		});
		
		function addClose(flag){
			if($("#isExitTip").val() == "1"){
				art.dialog({
					content:"关闭不保存数据,是否继续?",
					ok:function(){
						closeWin(flag);
					},
					cancel:function(){}
				});
			}else{
				closeWin(flag);
			}
		}
		function d_add_save(isContinue){
			beforeSave();
			var cardId=$("#cardId").val();
			var custCode=$("#custCode").val().trim();
			var applyMan=$("#applyMan").val();
			var salaryType=$("#salaryType").val();
			var workType2=$("#workType2").val();
			var workType1=$("#workType1").val();
			var appAmount=$("#appAmount").val();
			var isWithHold=$("#isWithHold").val();
			var withHoldNo=$("#withHoldNo").val();
			var rcvCost=$("#rcvCost").val();
			var confirmCzy=$("#confirmCzy").val();
			var withHoldCost=$("#withHoldCost").val();
			var prjItem=$("#prjItem").val();
			var custStatus=$("#custStatus").val();
			var prjItemDescr=$("#prjItemDescr").val();
			var checkStatusDescr=$("#checkStatusDescr").val();
			var hasAddManageRight=$("#hasAddManageRight").val();
			var isFz=$("#isFz").val();
			var checkWorkType1=$("#checkWorkType1").val();
			var type='${workCostDetail.type}';
			var refCustCode=$("#refCustCode").val();
			var faultType=$("#faultType").val();
			var faultMan=$("#faultMan").val();
			if(custCode==""){
				art.dialog({
	       			content: "请先选择客户编号",
	       		});
	       		return;
			}
			if("${AftCustCode}".indexOf(custCode)!=-1 && refCustCode==""){
				art.dialog({
	       			content: "请选择关联客户",
	       		});
	       		return;
			}
			if("${AftCustCode}".indexOf(custCode)!=-1 && faultType==""){
				art.dialog({
	       			content: "请选择责任人类型",
	       		});
	       		return;
			}
			if("${AftCustCode}".indexOf(custCode)!=-1 && faultType!="3" &&  faultMan==""){
				art.dialog({
	       			content: "请选择责任人",
	       		});
	       		return;
			}
			if(applyMan==""){
				art.dialog({
	       			content: "请先选择申请人",
	       		});
	       		return;
			}
			if(salaryType==""){
				art.dialog({
	       			content: "请先选择工资类型",
	       		});
	       		return;
			}
			if(workType2==""){
				art.dialog({
	       			content: "请先选择工种类型2",
	       		});
	       		return;
			}
			if(appAmount=="0" || appAmount==""){
				art.dialog({
	       			content: "请填写申请金额，申请金额必须大于0",
	       		});
	       		return;
			}
			if(isWithHold=="1" && withHoldNo=="" ){
				art.dialog({
	       			content: "请填写预扣单号",
	       		});
	       		return;
			}
			if(isWithHold=="1" && (parseFloat(rcvCost)+parseFloat(appAmount)>parseFloat(withHoldCost))){
				art.dialog({
	       			content: "领取金额超过预扣金额，不允许保存！",
	       		});
	       		return;
			}
			if(custStatus=="4" && prjItem=="01" && confirmCzy=="" &&hasAddManageRight=="1"){
				art.dialog({
	       			content: "["+prjItemDescr+"]阶段未验收通过不允许申请工资，不允许保存！",
	       		});
	       		return;
			}
			if(salaryType=="01" && isWithHold=="0"){
				if(checkStatusDescr=="项目经理要领" || checkStatusDescr=="项目经理已领"){
					art.dialog({
		       			content: "非预扣时，项目经理要领和已领状态楼盘，不允许新增人工单!",
		       		});
	       			return;
				}
			
			}
			/* if(cardId!='' && type=='1' && isFz=='1' && workType1!='01' && (salaryType=='01' || salaryType=='05') && (checkWorkType1!="" &&checkWorkType1!=workType1)){
				art.dialog({
	       			content: "该卡号已申请过其他工种类型1的工资，无法保存！",//去掉该控制 20190603 by cjg
	       		});
	       		return;
			} */
			var datas = $("#page_form").jsonForm();
			selectRows.push(datas);
			Global.Dialog.returnData = selectRows;
			if(isContinue){
				$("#page_form input[id!='jsonString'][id!='workType1Descr'][id!='workType2Descr'][id!='salaryTypeDescr'][id!='statusDescr'][id!='status'][id!='applyDescr']").val("");
				$("#page_form select[id!='workType1'][id!='workType2'][id!='salaryType'][id!='status_disabled']").val("");
			}else{
				closeWin();
			}
		}
		//初始化 保存时需要的前提条件
		function beforeSave(){
			//获取父窗口的表格
			var rows = $(top.$("#iframe_detail")[0].contentWindow.document.getElementById("detailDataTable")).jqGrid("getRowData");
			$.ajax({
				url : '${ctx}/admin/workCostDetail/beforeSave',
				type : 'post',
				data : {
					'no' : '${workCostDetail.no}','cardId':$("#cardId").val(),'workCostDetailJson':JSON.stringify(rows)
				},
				async:false,
				dataType : 'json',
				cache : false,
				error : function(obj) {
					showAjaxHtml({
						"hidden" : false,
						"msg" : '保存数据出错~'
					});
				},
				success : function(obj) {
					$("#hasAddManageRight").val(obj.hasAddManageRight);
					$("#isFz").val(obj.isFz);
					$("#checkWorkType1").val(obj.checkWorkType1);
				}
			});
		}
		
		function changeFaultType(){
			clickNum++;
			var faultType=$("#faultType").val();
			if(clickNum!=1){
				$("#faultMan").val(faultType=="1"?$("#refProjectMan").val():"");
				$("#faultManDescr").val(faultType=="1"?$("#refProjectManDescr").val():"");
			}
			if(faultType=="1"){
				$("#openComponent_worker_faultWorker").val("");
				$("#faultEmp").parent().removeClass("hidden");
				$("#faultWorker").parent().addClass("hidden");
				$("#faultEmp").openComponent_employee({
					showValue:clickNum==1?"${workCostDetail.faultMan}":$("#refProjectMan").val(),
					showLabel:clickNum==1?"${workCostDetail.faultManDescr}":$("#refProjectManDescr").val(),
					condition:{
						isStakeholder:"1",custCode:$("#refCustCode").val()
					},
					callBack:function(data){
						$("#faultMan").val(data.number);
						$("#faultManDescr").val(data.namechi);
						if(data.prjqualityfee!=""){
							$("#prjQualityFee").parent().removeClass("hidden");
							$("#prjQualityFee").val(data.prjqualityfee);
						}else{
							$("#prjQualityFee").parent().addClass("hidden");
						}
					}
				});
			}else if(faultType=="2"){
				$("#openComponent_employee_faultEmp").val("");
				$("#faultWorker").parent().removeClass("hidden");
				$("#faultEmp").parent().addClass("hidden");
				$("#faultWorker").openComponent_worker({
					showValue:clickNum==1?"${workCostDetail.faultMan}":"",
					showLabel:clickNum==1?"${workCostDetail.faultManDescr}":"",
					condition:{
						refCustCode:$("#refCustCode").val()
					},
					callBack:function(data){
						$("#faultMan").val(data.Code);
						$("#faultManDescr").val(data.NameChi);
					}
				});
			}else{
				$("#faultWorker").parent().addClass("hidden");
				$("#faultEmp").parent().addClass("hidden");
				$("#openComponent_worker_faultWorker").val("");
				$("#openComponent_employee_faultEmp").val("");
			}
		}
	</script>
	</head>
<body>
	<div class="body-box-list">
		<div class="panel panel-system">
			<div class="panel-body">
				<div class="btn-group-xs" style="float:left">
					<c:if test="${workCostDetail.button!='view'}">
						<button type="button" class="btn btn-system"
							onclick="d_add_save()">保存</button>
					</c:if>
					<c:if test="${workCostDetail.button=='add'}">
						<button type="button" class="btn btn-system"
						onclick="d_add_save(true)">保存继续添加</button>
					</c:if>
					<button type="button" class="btn btn-system"
						onclick="addClose(true)">关闭</button> 
				</div>
			</div>
		</div>

		<div class="container-fluid">
			<ul class="nav nav-tabs">
				<li id="tabMainProject" class="active"><a
					href="#tab_MainProject" data-toggle="tab">主项目</a>
				</li>
			</ul>
			<div class="tab-content">
				<div id="tab_MainProject" class="tab-pane fade in active">
					<div class="panel panel-info">
						<form action="" method="post" id="page_form" class="form-search">
							<input type="hidden" name="jsonString" id="jsonString" value="" /> <input
								type="hidden" name="workType1Descr" id="workType1Descr" /> <input
								type="hidden" name="workType2Descr" id="workType2Descr" /> <input
								type="hidden" name="salaryTypeDescr" id="salaryTypeDescr"
								value="${workCostDetail.salaryTypeDescr}" /> <input
								type="hidden" name="statusDescr" id="statusDescr"
								value="${workCostDetail.statusDescr}" />
								<input
								type="hidden" name="qualityFeeBegin" id="qualityFeeBegin"
								value="${workCostDetail.qualityFeeBegin}" /> <input type="hidden"
								name="isWithHoldDescr" id="isWithHoldDescr"
								value="${workCostDetail.isWithHoldDescr}" /> <input
								type="hidden" name="applyManDescr" id="applyManDescr"
								value="${workCostDetail.applyManDescr}" /> <input type="hidden"
								name="custStatus" id="custStatus"
								value="${workCostDetail.custStatus}" /> <input type="hidden"
								name="pk" id="pk" value="${workCostDetail.pk}" /> 
								<input type="hidden"
								name="custDescr" id="custDescr" value="${workCostDetail.custDescr}" /><input
								type="hidden" name="prjItemDescr" id="prjItemDescr" /> <input
								type="hidden" name="hasAddManageRight" id="hasAddManageRight" />
								<input type="hidden"
								name="refCustDescr" id="refCustDescr" value="${workCostDetail.refCustDescr}" />
								<input type="hidden"
								name="refAddress" id="refAddress" value="${workCostDetail.refAddress}" />
							<input type="hidden" name="isFz" id="isFz" /> <input
								type="hidden" name="checkWorkType1" id="checkWorkType1" />
							<input type="hidden"
								name="isWaterItemCtrl" id="isWaterItemCtrl" value="${workCostDetail.isWaterItemCtrl}" />
							<input type="hidden"
								name="isWaterItemCtrlDescr" id="isWaterItemCtrlDescr" value="${workCostDetail.isWaterItemCtrlDescr}" />
							<input type="hidden" name="idnum" id="idnum" value="${workCostDetail.idnum}" />
							<input type="hidden" name="isFzc" id="isFzc" value="${workCostDetail.isFz }"/>
							<input type="hidden" name="constructStatus" id="constructStatus" value="${customer.constructStatus}"   />
							<input type="hidden" name="faultTypeDescr" id="faultTypeDescr" value="${workCostDetail.button=='add'?'员工（项目经理）':workCostDetail.faultTypeDescr}"   />
							<input type="hidden" name="faultMan" id="faultMan" value="${workCostDetail.faultMan}"   />
							<input type="hidden" name="faultManDescr" id="faultManDescr" value="${workCostDetail.faultManDescr}"   />
							<input type="hidden" name="refProjectMan" id="refProjectMan" value="${workCostDetail.refProjectMan}"/>
							<input type="hidden" name="refProjectManDescr" id="refProjectManDescr" value="${workCostDetail.refProjectManDescr}"/>
							<input type="hidden" name="custTypeDescr" id="custTypeDescr" value="${workCostDetail.custTypeDescr}"/>
							<c:choose>
								<c:when
									test="${ workCostDetail.isConfirm=='' || workCostDetail.isConfirm==null}">
									<input type="hidden" id="isConfirm" name="isConfirm"
										style="width:160px;" value="0" />
								</c:when>
								<c:otherwise>
									<input type="hidden" id="isConfirm" name="isConfirm"
										style="width:160px;" value="${ workCostDetail.isConfirm}" />
								</c:otherwise>
							</c:choose><input type="hidden"
								name="checkStatusDescr" id="checkStatusDescr"
								value="${workCostDetail.checkStatusDescr}" /> <input
								type="hidden" name="confirmCzyDescr" id="confirmCzyDescr"
								value="${workCostDetail.confirmCzyDescr}" /> <input
								type="hidden" name="isSignDescr" id="isSignDescr" /> <input
								type="hidden" name="lastUpdatedBy" id="lastUpdatedBy"
								value="${workCostDetail.lastUpdatedBy}" />
							<ul class="ul-form">
								<div class="validate-group row">
									<li><label>客户编号</label> <input type="text" id="custCode"
										name="custCode" value="${workCostDetail.custCode}" />
									</li>
									<li class="hidden">
										<label>关联客户</label>
										<input type="text" id="refCustCode" name="refCustCode" value="${workCostDetail.refCustCode}" readonly="true"/>
									</li> 
								</div>
								<div class="validate-group row hidden">
									<li><label>责任人类型</label> <house:xtdm id="faultType" 
										dictCode="FAULTTYPE" unShowValue="4" onchange="changeFaultType();getDescrByCode('faultType','faultTypeDescr')"
										value="${workCostDetail.button=='add'?'1':workCostDetail.faultType}"></house:xtdm>
									</li>
									<li><label>员工</label> <input type="text" id="faultEmp"
										name="faultEmp" />
									</li>
									<li class="hidden"><label>工人</label> <input type="text" id="faultWorker"
										name="faultWorker" />
									</li>
									<li><label>项目经理质保余额</label> <input type="text"
										id="prjQualityFee" name="prjQualityFee" style="width:160px;"
										value="${ workCostDetail.prjQualityFee}" readonly/>
									</li>
								</div>
								<div class="validate-group row">
									<li><label>档案编号</label> <input type="text" id="documentNo"
										name="documentNo" value="${workCostDetail.documentNo}"
										readonly="readonly" />
									</li>
									<li><label>申请人</label> <input type="text" id="applyMan"
										name="applyMan" style="width:160px;"
										value="${workCostDetail.applyMan }" />
									</li>
								</div>
								<div class="validate-group row">
									<li><label>工资类型</label> <house:dict id="salaryType"
											dictCode=""
											sql="select rtrim(Code)+' '+Descr fd,Code from tSalaryType order by Code"
											sqlValueKey="Code" sqlLableKey="fd"
											value="${workCostDetail.salaryType}"
											onchange="findDescr('salaryType','salaryTypeDescr');changeWorkType()">
										</house:dict>
									</li>
									<li><label>工种分类1</label> <select id="workType1"
										name="workType1"
										onchange="findDescr('workType1','workType1Descr')"></select>
									</li>
								</div>
								<div class="validate-group row">
									<li><label>工种分类2</label> <select id="workType2"
										name="workType2"
										onchange="changeType2();findDescr('workType2','workType2Descr')"></select>
									</li>
									<c:choose>
										<c:when
											test="${ workCostDetail.appAmount=='' || workCostDetail.appAmount==null}">
											<li><label>申请金额</label> <input type="text"
												id="appAmount" name="appAmount" style="width:160px;"
												value="0" onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\-\.\d]/g,'');"/>
											</li>
										</c:when>
										<c:otherwise>
											<li><label>申请金额</label> <input type="text"
												id="appAmount" name="appAmount" style="width:160px;"
												value="${ workCostDetail.appAmount}" onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\-\.\d]/g,'');"/>
											</li>
										</c:otherwise>
									</c:choose>
								</div>
								<div class="validate-group row">
									<li><label>楼盘</label> <input type="text" id="address"
										name="address" style="width:160px;"
										value="${workCostDetail.address }" readonly="readonly"/>
									</li>
									<li class="form-validate"><label>开工日期</label> <input
										type="text" id="signDate" name="signDate" class="i-date"
										style="width:160px;" readonly="readonly"
										value="<fmt:formatDate value='${workCostDetail.signDate}' pattern='yyyy-MM-dd'/>" />
									</li>
								</div>
								<div class="validate-group row">
									<c:choose>
										<c:when
											test="${ workCostDetail.totalAmount=='' || workCostDetail.totalAmount==null}">
											<li><label>合同总价</label> <input type="text"
												id="totalAmount" name="totalAmount" style="width:160px;"
												value="0" readonly="readonly" value="0" />
											</li>
										</c:when>
										<c:otherwise>
											<li><label>合同总价</label> <input type="text"
												id="totalAmount" name="totalAmount" style="width:160px;"
												readonly="readonly" value="${ workCostDetail.totalAmount}" />
											</li>
										</c:otherwise>
									</c:choose>
									<c:choose>
										<c:when
											test="${ workCostDetail.gotAmount=='' || workCostDetail.gotAmount==null}">
											<li><label>已领工资</label> <input type="text"
												id="gotAmount" name="gotAmount" style="width:160px;"
												readonly="readonly" value="0" /></li>
										</c:when>
										<c:otherwise>
											<li><label>已领工资</label> <input type="text"
												id="gotAmount" name="gotAmount" style="width:160px;"
												readonly="readonly" value="${ workCostDetail.gotAmount}" />
											</li>
										</c:otherwise>
									</c:choose>
								</div>
								<div class="validate-group row">

									<li class="form-validate"><label class="control-textarea">请款说明</label>
										<textarea id="remarks" name="remarks"
											style="overflow-y:scroll; overflow-x:hidden; height:75px; " />${workCostDetail.remarks
										}</textarea>
									</li>
								</div>
								<c:if test="${workCostDetail.type=='1'}">
									<div class="validate-group row type020" >
										<li ><label>工人编号</label> <input type="text"
											id="workerCode" name="workerCode" style="width:160px;"
											value="${workCostDetail.workerCode }" />
										</li>

										<li><label>卡号</label> <input type="text" id="cardId"
											name="cardId" style="width:160px;"
											value="${workCostDetail.cardId }" readonly/>
										</li>
									</div>
									<div class="validate-group row type020">
										<li><label>户名</label> <input type="text" id="actName"
											name="actName" style="width:160px;"
											value="${workCostDetail.actName }" readonly/>
										</li>
										<li><label>卡号2</label> <input type="text" id="cardId2"
											name="cardId2" style="width:160px;"
											value="${workCostDetail.cardId2 }" readonly/>
										</li>
									</div>
									<div class="validate-group row">
										<c:choose>
											<c:when
												test="${ workCostDetail.isWithHold=='' || workCostDetail.isWithHold==null}">
												<li><label>是否预扣</label> <house:xtdm id="isWithHold"
														dictCode="YESNO" value="0"
														onchange="findDescrByXtdm('isWithHold','YESNO','isWithHoldDescr');initWithHoldNo()"></house:xtdm>
												</li>
											</c:when>
											<c:otherwise>
												<li><label>是否预扣</label> <house:xtdm id="isWithHold"
														dictCode="YESNO" value="${workCostDetail.isWithHold}"
														onchange="findDescrByXtdm('isWithHold','YESNO','isWithHoldDescr');initWithHoldNo()"></house:xtdm>
												</li>
											</c:otherwise>
										</c:choose>
										<li><label>预扣单号</label> <input type="text"
											id="withHoldNo" name="withHoldNo" style="width:160px;"
											value="${workCostDetail.withHoldNo }" />
										</li>
									</div>
									<div class="validate-group row">
										<c:choose>
											<c:when
												test="${ workCostDetail.withHoldCost=='' || workCostDetail.withHoldCost==null}">
												<li><label>预扣金额</label> <input type="text"
													id="withHoldCost" name="withHoldCost" style="width:160px;"
													readonly="readonly" value="0" /></li>
											</c:when>
											<c:otherwise>
												<li><label>预扣金额</label> <input type="text"
													id="withHoldCost" name="withHoldCost" style="width:160px;"
													readonly="readonly" value="${ workCostDetail.withHoldCost}" />
												</li>
											</c:otherwise>
										</c:choose>
										<c:choose>
											<c:when
												test="${ workCostDetail.rcvCost=='' || workCostDetail.rcvCost==null}">
												<li><label>领取金额</label> <input type="text" id="rcvCost"
													name="rcvCost" style="width:160px;" readonly="readonly"
													value="0" /><font color='red'>(含申请)</font></li>
											</c:when>
											<c:otherwise>
												<li><label>领取金额</label> <input type="text" id="rcvCost"
													name="rcvCost" style="width:160px;" readonly="readonly"
													 /><font color='red'>(含申请)</font>
												</li>
											</c:otherwise>
										</c:choose>
									</div>
								</c:if>
								<div class="validate-group row">
									<li><label>状态</label> <house:dict id="status" dictCode=""
											sql="select CBM Code,CBM+' '+NOTE fd from tXTDM where id='WorkCostStatus'"
											sqlValueKey="Code" sqlLableKey="fd" disabled="true" value="1">
										</house:dict>
									</li>
									<c:choose>
										<c:when
											test="${ workCostDetail.leaveCustCost=='' || workCostDetail.leaveCustCost==null}">
											<li><label>工种金额</label> <input type="text"
												id="leaveCustCost" name="leaveCustCost" style="width:160px;"
												readonly="readonly" value="0" /></li>
										</c:when>
										<c:otherwise>
											<li><label>工种金额</label> <input type="text"
												id="leaveCustCost" name="leaveCustCost" style="width:160px;"
												readonly="readonly" value="${ workCostDetail.leaveCustCost}" />
											</li>
										</c:otherwise>
									</c:choose>
								</div>
								<div class="validate-group row">
									<c:choose>
										<c:when
											test="${ workCostDetail.custCtrl=='' || workCostDetail.custCtrl==null}">
											<li><label>工种发包</label> <input type="text" id="custCtrl"
												name="custCtrl" style="width:160px;" readonly="readonly"
												value="0" /></li>
										</c:when>
										<c:otherwise>
											<li><label>工种发包</label> <input type="text" id="custCtrl"
												name="custCtrl" style="width:160px;" readonly="readonly"
												value="${ workCostDetail.custCtrl}" />
											</li>
										</c:otherwise>
									</c:choose>
									<c:choose>
										<c:when
											test="${ workCostDetail.custCost=='' || workCostDetail.custCost==null}">
											<li><label>工种支出</label> <input type="text" id="custCost"
												name="custCost" style="width:160px;" readonly="readonly"
												value="0" /></li>
										</c:when>
										<c:otherwise>
											<li><label>工种支出</label> <input type="text" id="custCost"
												name="custCost" style="width:160px;" readonly="readonly"
												value="${ workCostDetail.custCost}" />
											</li>
										</c:otherwise>
									</c:choose>
								</div>
								<div class="validate-group row">
									<c:choose>
										<c:when
											test="${ workCostDetail.allLeaveCustCost=='' || workCostDetail.allLeaveCustCost==null}">
											<li><label>总发包余额</label> <input type="text"
												id="allLeaveCustCost" name="allLeaveCustCost"
												style="width:160px;" readonly="readonly" value="0" /></li>
										</c:when>
										<c:otherwise>
											<li><label>总发包余额</label> <input type="text"
												id="allLeaveCustCost" name="allLeaveCustCost"
												style="width:160px;" readonly="readonly"
												value="${ workCostDetail.allLeaveCustCost}" />
											</li>
										</c:otherwise>
									</c:choose>
									<c:choose>
										<c:when
											test="${ workCostDetail.allCustCtrl=='' || workCostDetail.allCustCtrl==null}">
											<li><label>总发包</label> <input type="text"
												id="allCustCtrl" name="allCustCtrl" style="width:160px;"
												readonly="readonly" value="0" /></li>
										</c:when>
										<c:otherwise>
											<li><label>总发包</label> <input type="text"
												id="allCustCtrl" name="allCustCtrl" style="width:160px;"
												readonly="readonly" value="${ workCostDetail.allCustCtrl}" />
											</li>
										</c:otherwise>
									</c:choose>
								</div>
								<div class="validate-group row">
									<c:choose>
										<c:when
											test="${ workCostDetail.allCustCost=='' || workCostDetail.allCustCost==null}">
											<li><label>总支出</label> <input type="text"
												id="allCustCost" name="allCustCost" style="width:160px;"
												readonly="readonly" value="0" /></li>
										</c:when>
										<c:otherwise>
											<li><label>总支出</label> <input type="text"
												id="allCustCost" name="allCustCost" style="width:160px;"
												readonly="readonly" value="${ workCostDetail.allCustCost}" />
											</li>
										</c:otherwise>
									</c:choose>
									<c:choose>
										<c:when
											test="${ workCostDetail.custCtrl_Kzx=='' || workCostDetail.custCtrl_Kzx==null}">
											<li><label>人工控制项</label> <input type="text"
												id="custCtrl_Kzx" name="custCtrl_Kzx" style="width:160px;"
												readonly="readonly" value="0" /></li>
										</c:when>
										<c:otherwise>
											<li><label>人工控制项</label> <input type="text"
												id="custCtrl_Kzx" name="custCtrl_Kzx" style="width:160px;"
												readonly="readonly" value="${ workCostDetail.custCtrl_Kzx}" />
											</li>
										</c:otherwise>
									</c:choose>
								</div>
								<div class="validate-group row">
									<c:choose>
										<c:when
											test="${ workCostDetail.prjItem=='' || workCostDetail.prjItem==null}">
											<li><label>施工项目名</label> <house:xtdm id="prjItem"
													dictCode="PRJITEM" style="width:160px;" value="1"></house:xtdm>
											</li>
										</c:when>
										<c:otherwise>
											<li><label>施工项目名</label> <house:xtdm id="prjItem"
													dictCode="PRJITEM" style="width:160px;"
													value="${workCostDetail.prjItem}"></house:xtdm></li>
										</c:otherwise>
									</c:choose>
									<li><label>完成日期</label> <input type="text" id="endDate"
										name="endDate" class="i-date" style="width:160px;"
										readonly="readonly"
										value="<fmt:formatDate value='${workCostDetail.endDate}' pattern='yyyy-MM-dd hh:mm:ss'/>" />
									</li>
								</div>
								<div class="validate-group row">
									<li><label>验收日期</label> <input type="text"
										id="confirmDate" name="confirmDate" class="i-date"
										value="<fmt:formatDate value='${workCostDetail.confirmDate }' pattern='yyyy-MM-dd hh:mm:ss'/>"
										readonly="readonly" />
									</li>
									<li><label>验收人</label> <input type="text" id="confirmCzy"
										name="confirmCzy" style="width:160px;" readonly="readonly" />
									</li>
								</div>
								<c:if test="${workCostDetail.button=='view'}">
									<div class="validate-group row">
										<li><label>审批金额</label> <input type="text"
											id="confirmAmount" name="confirmAmount" style="width:160px;"
											readonly="readonly" value="${workCostDetail.confirmAmount }" />
										</li>
										<li>
											<label>定额工资</label>
											<div class="input-group">
												<input type="text" class="form-control" id="workerPlanOffer" style="width: 121px;" value="${workCostDetail.workerPlanOffer}" readonly="readonly">
												<button type="button" class="btn btn-system" id="workerPlanOfferBtn" style="font-size: 12px;width: 40px;" >查看</button>
											</div>
										</li>
									</div>
									<div class="validate-group row">
										<li class="form-validate"><label class="control-textarea">审批说明</label>
											<textarea id="confirmRemark" name="confirmRemark"
												style="overflow-y:scroll; overflow-x:hidden; height:75px; " />${workCostDetail.confirmRemark
											}</textarea>
										</li>
									</div>
								</c:if>
								<c:if test="${workCostDetail.button!='view'}">
									<input type="hidden" name="workerPlanOffer" id="workerPlanOffer" value="${workCostDetail.workerPlanOffer}" /> 
								</c:if>
					</div>
					</ul>
					</form>
				</div>
			</div>
		</div>
	</div>
	</div>
</body>
</html>
