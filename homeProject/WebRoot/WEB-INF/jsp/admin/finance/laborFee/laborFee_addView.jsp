<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://" + request.getServerName() + ":"+request.getServerPort()+path+"/";
String jasperPath = PathUtil.WEB_INF+"jsp/jasper/";
%>
<!DOCTYPE html>
<html>
<head>
	<title>人工费用编辑明细</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_workCard.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_itemSendNo.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_worker.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript"> 
//责任人类型修改次数
var clickNum=0;
$(function(){
	var custCode="1";
	function getCustomer(data){
		if(!data) {
			return false;
		}
		var feeType=$.trim($("#feeType").val());
		if(feeType!=""){
			$.ajax({
				url:"${ctx}/admin/laborFee/getFeeTypeDescr",
				type: "get",
				data:{feeType:feeType},
				dataType: "json",
				cache: false,
				error: function(obj){
					showAjaxHtml({"hidden": false, "msg": "保存数据出错~"});
			    },
			    success: function(obj){
					if("1"==obj[1]){
						$("#iaNo").val('');
						$("#designMan").val('');
						$("#openComponent_employee_designMan").val('');
						$('input[name=openComponent_itemSendNo_appSendNo]').css('background','#FFFFFF');
						$("#appSendNo").openComponent_itemSendNo({callBack:getIaNo,condition:{custCode:data.code,itemType1:$.trim("${laborFee.itemType1}")}});	 
					}else{
						$("#iaNo").val('');
						$("#designMan").val('');
						$("#openComponent_employee_designMan").val('');
						$('input[name=openComponent_itemSendNo_appSendNo]').css('background','#CCCCCC');
						$("#appSendNo").openComponent_itemSendNo({readonly:true});	 
					}
				}
			});
			$.ajax({
				url:"${ctx}/admin/laborFee/getNoHaveAmount",
				type: "get",
				data:{custCode:data.custCode,feeType:feeType,},
				dataType: "json",
				cache: false,
				error: function(obj){
					showAjaxHtml({"hidden": false, "msg": "保存数据出错~"});
			    },
			    success: function(obj){
					$("#sendNoHaveAmount").val(obj);
				}
			});
		}else{
			$("#appSendNo").openComponent_itemSendNo({callBack:getIaNo,condition:{custCode:custCode,itemType1:$.trim("${laborFee.itemType1}")}});	 
		}
		$("#appSendNo").openComponent_itemSendNo({callBack:getIaNo,condition:{custCode:data.code,itemType1:$.trim("${laborFee.itemType1}")}});	 
		$("#address").val(data.address);
		$("#documentNo").val(data.documentno);
		$("#checkStatus").val(data.checkstatusdescr);
		$("#checkDate").val(data.custcheckdate);
	}
	function getWorkCard(data){
		if(!data){
			return false;
		}
		$("#cardID").val(data.CardID);
	} 
	$("#custCode").openComponent_customer({callBack:getCustomer,showValue:"${laborFee.custCode}",readonly:true,});
	$("#refCustCode").openComponent_customer({
		showValue:"${laborFee.refCustCode}",
		showLabel:"${laborFee.refCustDescr}",
		condition:{
			status:"4,5",
			disabledEle:"status_NAME"
		},
		readonly:true,
		callBack:function(data){
			var custCode=$("#custCode").val();
			$("#faultMan").val(data["projectman"]);
			$("#faultManDescr").val(data["projectmandescr"]);
			$("#refCustDescr").val(data.descr);
			$("#prjQualityFee").val(data.prjqualityfee);
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
					showValue:clickNum==1?"${laborFee.faultMan}":"",
					showLabel:clickNum==1?"${laborFee.faultManDescr}":"",
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
  	changeFaultType();	
	$("#actName").openComponent_workCard({callBack:getWorkCard,showValue:"${laborFee.actName}"});	 
	
	if("${AftCustCode}".indexOf("${laborFee.custCode}".trim())!=-1){
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
	
	function getIaNo(data){
		if(!data){
			return false;
		}
		$("#iaNo").val(data.iano);
		feeType=$.trim($("#feeType").val());
		$.ajax({
			url:"${ctx}/admin/laborFee/getHaveAmount",
			type: "get",
			data:{sendNo:data.no,feeType:feeType},
			dataType: "json",
			cache: false,
			error: function(obj){
				showAjaxHtml({"hidden": false, "msg": "保存数据出错~"});
		    },
		    success: function(obj){
				$("#haveAmount").val(obj);
			}
		});
	}
	$("#appSendNo").openComponent_itemSendNo({readonly:true,showValue:"${laborFee.appSendNo }",callBack:getIaNo,condition:{custCode:"{laborfee.custCode}",itemType1:$.trim("${laborFee.itemType1}")}});	 
	
	
	$("#saveBtn").on("click",function(){
		var no=$.trim($("#appSendNo").val());	
		$.ajax({
			url:"${ctx}/admin/laborFee/getIsSetItem",
			type: "get",
			data:{no:no},
			dataType: "json",
			cache: false,
			error: function(obj){
				showAjaxHtml({"hidden": false, "msg": "保存数据出错~"});
		    },
		    success: function(obj){
				if(obj){
					art.dialog({
					content:"送货单为套餐内材料的项目经理已领、未领状态的不允许保存!",
					});
				}else{
					var selectRows = [];
					var datas=$("#dataForm").jsonForm();
				 	selectRows.push(datas);
					Global.Dialog.returnData = selectRows;
					closeWin();
				}
			}
		});
	}); 
	(function getFeeTypeDescr(){
		var feeType=$.trim($("#feeType").val());
		$.ajax({
			url:"${ctx}/admin/laborFee/getFeeTypeDescr",
			type: "get",
			data:{feeType:feeType},
			dataType: "json",
			cache: false,
			error: function(obj){
				showAjaxHtml({"hidden": false, "msg": "保存数据出错~"});
		    },
		    success: function(obj){
				$("#feeTypeDescr").val(obj[0]);
			}
		});
	})();
});

function getFeeTypeDescr(){
	var feeType=$.trim($("#feeType").val());
	var custCode=$.trim($("#custCode").val());
	var appSendNo=$.trim($("#appSendNo").val());
	function getIaNo(data){
		if(!data){
			return false;
		}
		$("#iaNo").val(data.iano);
		$.ajax({
			url:"${ctx}/admin/laborFee/getHaveAmount",
			type: "get",
			data:{sendNo:data.no,feeType:feeType},
			dataType: "json",
			cache: false,
			error: function(obj){
				showAjaxHtml({"hidden": false, "msg": "保存数据出错~"});
		    },
		    success: function(obj){
				$("#haveAmount").val(obj);
			}
		});
	}
	if(appSendNo!=""){
		$.ajax({
			url:"${ctx}/admin/laborFee/getHaveAmount",
			type: "get",
			data:{sendNo:appSendNo,feeType:feeType},
			dataType: "json",
			cache: false,
			error: function(obj){
				showAjaxHtml({"hidden": false, "msg": "保存数据出错~"});
		    },
		    success: function(obj){
				$("#haveAmount").val(obj);
			}
		});
	}
	$.ajax({
		url:"${ctx}/admin/laborFee/getNoHaveAmount",
		type: "get",
		data:{custCode:custCode,feeType:feeType},
		dataType: "json",
		cache: false,
		error: function(obj){
			showAjaxHtml({"hidden": false, "msg": "保存数据出错~"});
	    },
	    success: function(obj){
			$("#sendNoHaveAmount").val(obj);
		}
	});
	$.ajax({
		url:"${ctx}/admin/laborFee/getFeeTypeDescr",
		type: "get",
		data:{feeType:feeType},
		dataType: "json",
		cache: false,
		error: function(obj){
			showAjaxHtml({"hidden": false, "msg": "保存数据出错~"});
	    },
	    success: function(obj){
			$("#feeTypeDescr").val(obj[0]);
			if("1"==obj[1]){
				$("#iaNo").val('');
				$("#designMan").val('');
				$("#openComponent_employee_designMan").val('');
				$('input[name=openComponent_itemSendNo_appSendNo]').css('background','#FFFFFF');
				$("#appSendNo").openComponent_itemSendNo({callBack:getIaNo,condition:{custCode:custCode,itemType1:$.trim("${laborFee.itemType1}")}});	 
			}else{
				$("#iaNo").val('');
				$("#designMan").val('');
				$("#openComponent_employee_designMan").val('');
				$('input[name=openComponent_itemSendNo_appSendNo]').css('background','#CCCCCC');
				$("#appSendNo").openComponent_itemSendNo({readonly:true});	 
			}
		}
	});
}

function validateRefresh(){
		$('#dataForm').data('bootstrapValidator')
		                   .updateStatus('openComponent_builder_builderCode', 'NOT_VALIDATED',null)  
		                   .validateField('openComponent_builder_builderCode')
                    .updateStatus('crtDate', 'NOT_VALIDATED',null)  
                    .validateField('crtDate')  ;
}

function validateRefresh_choice(){
	 $('#dataForm').data('bootstrapValidator')
                 .updateStatus('openComponent_builder_builderCode', 'NOT_VALIDATED',null)  
                 .validateField('openComponent_builder_builderCode');                    
}
function fillData(ret){
	validateRefresh_choice();
}
function changeFaultType(){
	clickNum++;
	var faultType=$("#faultType").val();
	if(clickNum!=1){
		$("#faultMan").val(faultType=="1"?"${laborFee.projectMan}":"");
		$("#faultManDescr").val(faultType=="1"?"${laborFee.projectManDescr}":"");
	}
	if(faultType=="1"){
		$("#openComponent_worker_faultWorker").val("");
		$("#faultEmp").parent().removeClass("hidden");
		$("#faultWorker").parent().addClass("hidden");
		$("#faultEmp").openComponent_employee({
			showValue:clickNum==1?"${laborFee.faultMan}":"${laborFee.projectMan}",
			showLabel:clickNum==1?"${laborFee.faultManDescr}":"${laborFee.projectManDescr}",
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
			showValue:clickNum==1?"${laborFee.faultMan}":"",
			showLabel:clickNum==1?"${laborFee.faultManDescr}":"",
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
<div class="body-box-form">
			<div class="panel panel-system" >
			    <div class="panel-body" >
			      <div class="btn-group-xs" >
						<button type="button" class="btn btn-system " id="closeBut" onclick="closeWin(false)">
							<span>关闭</span>
						</button>
					</div>
				</div>
			</div>
		<div class="panel panel-info" >  
			<div class="panel-body">
				<form action="" method="post" id="dataForm" class="form-search">
						<input type="hidden" name="jsonString" value=""/>
						<input type="hidden" id="checkStatus" name="checkStatus" value="${laborFee.checkStatus }"/>
						<input type="hidden" id="checkDate" name="checkDate" value="${laborFee.checkDate }"/>
						<input type="hidden" id="feeTypeDescr" name="feeTypeDescr" />
						<input type="hidden" id="iaNo" name="iaNo" value="${laborfee.iaNo}" />
						
						<ul class="ul-form">
							<li>
								<label><span class="required">*</span>客户编号</label>
								<input type="text" id="custCode" name="custCode" style="width:160px;" value="${laborFee.custCode }"/>
							</li>
							<li class="hidden">
								<label>关联客户</label>
								<input type="text" id="refCustCode" name="refCustCode" value="${laborFee.refCustCode}" readonly="true"/>
							</li> 
							<div class="hidden">
								<li><label>责任人类型</label> <house:xtdm id="faultType" 
									dictCode="FAULTTYPE" unShowValue="4" onchange="changeFaultType();getDescrByCode('faultType','faultTypeDescr')"
									value="${laborFee.faultType}"></house:xtdm>
								</li>
								<li><label>员工</label> <input type="text" id="faultEmp"
									name="faultEmp" />
								</li>
								<li class="hidden"><label>工人</label> <input type="text" id="faultWorker"
									name="faultWorker" />
								</li>
								<li><label>项目经理质保余额</label> <input type="text"
									id="prjQualityFee" name="prjQualityFee" style="width:160px;"
									value="${ laborFee.prjQualityFee}" readonly/>
								</li>
							</div>
							
							<li>
								<label><span class="required">*</span>户名</label>
								<input type="text" id="actName" name="actName" style="width:160px;" value="${laborFee.actName }"/>
							</li>
							<li class="form-validate">
								<label>档案号</label>
								<input type="text" id="documentNo" name="documentNo" style="width:160px;" value="${laborFee.documentNo }" readonly="true"/>
							</li>
							<li>
								<label><span class="required">*</span>卡号</label>
								<input type="text" id="cardID" name="cardID" style="width:160px;" value="${laborFee.cardID }"/>
							</li>
							<li class="form-validate">
								<label>楼盘</label>
								<input type="text" id="address" name="address" style="width:160px;" value="${laborFee.address }" readonly="true"/>
							</li>
							<li  >
								<label>备注</label>
								<input type="text" id="detailRemarks" name="detailRemarks"  value="${laborFee.detailRemarks }"/>
							</li>
							<li>
								<label >费用类型</label>
								<house:DataSelect id="feeType" className="LaborFeeType"  disabled="true"
									keyColumn="code" valueColumn="descr" value="${laborFee.feeType }"></house:DataSelect>
							</li>
							<li class="form-validate">
								<label><span class="required">*</span>发货单号</label>
								<input type="text" id=appSendNo name="appSendNo"  value="${laborFee.appSendNo }"/>
							</li>
							<li class="form-validate">
								<label>本单已报销</label>
								<input type="text" id="haveAmount" name="haveAmount" style="width:160px;" value="${laborFee.haveAmount }" readonly="true"/>
							</li>
							<li class="form-validate">
								<label>已报销金额</label>
								<input type="text" id="sendNoHaveAmount" name="sendNoHaveAmount" style="width:160px;" value="${laborFee.sendNoHaveAmount }" readonly="true"/>
							</li>
							<li class="form-validate">
								<label><span class="required">*</span>付款金额</label>
								<input type="text" id="amount" name="amount" style="width:160px;" value="${laborFee.amount }"/>
							</li> 
						</ul>
				</form>
				</div>
			</div>
		</div>
	</body>	
</html>
