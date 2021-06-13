<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<!DOCTYPE html>
<html lang="zh">
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
</head>
<body>
	<div class="body-box-form">
		<div class="panel panel-system">
			<div class="panel-body">
				<div class="btn-group-xs">
					<button type="button" class="btn btn-system" id="closeBut"
						onclick="closeWin(false)">
						<span>关闭</span>
					</button>
				</div>
			</div>
		</div>
		<div class="panel panel-info" style="margin-bottom: 10px;">
			<div class="panel-body">
				<form action="" method="post" id="page_form" class="form-search">
					<house:token></house:token>
					<input type="hidden" name="jsonString" value="" />
					<input type="hidden" name="dataChanged" value="0" />
					<ul class="ul-form">
						<li class="form-validate"><label>定责申请编号</label> <input
							type="text" id="no" name="no" style="width:160px;"
							readonly="readonly" value="${map.no}" />
						</li>
						<li><label>客户编号</label> <input type="text" id="custcode"
							name="custcode" style="width:160px;" readonly="readonly"
							value="${map.custcode}">
						</li>
						<li><label>楼盘地址</label> <input type="text" id="address"
							name="address" style="width:160px;" readonly="readonly"
							value="${map.address}">
						</li>
						<li><label>工地状态</label> <input type="text" id="cwstatusdescr"
							name="cwstatusdescr" style="width:160px;" readonly="readonly"
							value="${map.cwstatusdescr}">
						</li>
						<li><label>申请人类型</label> <input type="text"
							id="appmantypedescr" name="appmantypedescr" style="width:160px;"
							readonly="readonly" value="${map.appmantypedescr}">
						</li>
						<li><label>状态</label> <input type="text" id="statusdescr"
							name="statusdescr" style="width:160px;" readonly="readonly"
							value="${map.statusdescr}">
						</li>
						<li><label>申请日期</label> <input type="text" id="appdate"
							name="appdate" style="width:160px;" readonly="readonly"
							value="${map.appdate}">
						</li>
						<li><label>申请人</label> <input type="text" id="appmandescr"
							name="appmandescr" style="width:160px;" readonly="readonly"
							value="${map.appmandescr}">
						</li>

						<li><label>人工金额</label> <input type="text" id="offerprj"
							name="offerprj" style="width:160px;" readonly="readonly"
							value="${map.offerprj}" />
						</li>
						<li><label>材料金额</label> <input type="text" id="material"
							name="material" style="width:160px;" readonly="readonly"
							value="${map.material}">
						</li>
						<li><label>描述</label> <input type="text" id="remarks"
							name="remarks" style="width:451px;" readonly="readonly"
							value="${map.remarks}">
						</li>
					</ul>
				</form>
			</div>
		</div>
		<div class="container-fluid">
			<ul class="nav nav-tabs">
				<li><a href="#tab_detail" data-toggle="tab">定责申请明细</a>
				</li>
				<li class="active"><a href="#tab_man" data-toggle="tab">定责人员</a></li>
			</ul>
			<div class="tab-content">
				<div id="tab_detail" class="tab-pane fade ">
					<div class="pageContent">
						<div id="content-list">
							<table id="dataTable_detail"></table>
						</div>
					</div>
				</div>
				<div id="tab_man" class="tab-pane fade in active">
					<div class="pageContent">
						<div class="panel panel-system">
							<div class="panel-body">
								<div class="btn-group-xs">
									<button style="align:left" type="button"
										class="btn btn-system " onclick="m_view()">
										<span>查看 </span>
									</button>
								</div>
							</div>
						</div>
						<div id="content-list">
							<table id="dataTable_man"></table>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript"> 
$(function() {
	$("#isPrjAllDuty").prop("disabled", true);
	var gridOption1 = {
		url:"${ctx}/admin/fixDuty/goDetailJqGrid",
		postData:{no:"${map.no}"},
		sortable: true,
		height : 230,
		rowNum : 10000000,
		colModel : [
			{name: "baseitemdescr", index: "baseitemdescr", width: 120, label: "基础项目", sortable: true, align: "left"},
			{name: "basecheckitemdescr", index: "basecheckitemdescr", width: 120, label: "基础结算项目", sortable: true, align: "left"},
			{name: "qty", index: "qty", width: 80, label: "数量", sortable: true, align: "right"},
			{name: "cfmqty", index: "cfmqty", width: 90, label: "专员确认数量", sortable: true, align: "right"},
			{name: "offerpri", index: "offerpri", width: 70, label: "人工单价", sortable: true, align: "right"},
			{name: "material", index: "material", width: 70, label: "材料单价", sortable: true, align: "right"},
			{name: "allofferpri", index: "allofferpri", width: 70, label: "人工总价", sortable: true, align: "right", sum: true},
			{name: "allmaterial", index: "allmaterial", width: 70, label: "材料总价", sortable: true, align: "right", sum: true},
			{name: "remark", index: "remark", width: 120, label: "描述", sortable: true, align: "left"},
			{name: "lastupdate", index: "lastupdate", width: 120, label: "最后更新时间", sortable: true, align: "left", formatter: formatTime},
			{name: "lastupdatedby", index: "lastupdatedby", width: 101, label: "最后更新人员", sortable: true, align: "left"},
			{name: "expired", index: "expired", width: 74, label: "是否过期", sortable: true, align: "left"},
			{name: "actionlog", index: "actionlog", width: 76, label: "操作日志", sortable: true, align: "left"},
			{name: "no", index: "no", width: 70, label: "no", sortable: true, align: "left",hidden:true},
		],
	};
	Global.JqGrid.initJqGrid("dataTable_detail",gridOption1);
	var detailGrid = $("#dataTable_detail")
	
	"${map.type}" === "1" ? detailGrid.hideCol("baseitemdescr") : detailGrid.hideCol("basecheckitemdescr")
	
	
	var gridOption2 = {
		url:"${ctx}/admin/fixDuty/goManJqGrid",
		postData:{no:"${map.no}"},
		sortable: true,
		height : 210,
		rowNum : 10000000,
		colModel : [
			{name: "faulttypedescr", index: "faulttypedescr", width: 100, label: "责任人类型", sortable: true, align: "left"},
			{name: "empnamechi", index: "empnamechi", width: 70, label: "员工姓名", sortable: true, align: "left"},
			{name: "workernamechi", index: "workernamechi", width: 70, label: "工人姓名", sortable: true, align: "left"},
			{name: "suppldescr", index: "suppldescr", width: 70, label: "供应商", sortable: true, align: "left"},
			{name: "amount", index: "amount", width: 80, label: "金额", sortable: true, align: "right",sum:true},
			{name: "riskfund", index: "riskfund", width: 100, label: "使用风控金额", sortable: true, align: "right"},
			{name: "issalarydescr", index: "issalarydescr", width: 70, label: "正常工资", sortable: true, align: "left"},
			{name: "confirmamount", index: "confirmamount", width: 70, label: "已发工资", sortable: true, align: "right"},
            {name: "paidamount", index: "paidamount", width: 80, label: "已支付金额", sortable: true, align: "right"},
			{name: "remark", index: "remark", width: 180, label: "备注", sortable: true, align: "left"},
			{name: "lastupdate", index: "lastupdate", width: 120, label: "最后更新时间", sortable: true, align: "left", formatter: formatTime},
			{name: "lastupdatedby", index: "lastupdatedby", width: 101, label: "最后更新人员", sortable: true, align: "left"},
			{name: "expired", index: "expired", width: 74, label: "是否过期", sortable: true, align: "left"},
			{name: "actionlog", index: "actionlog", width: 76, label: "操作日志", sortable: true, align: "left"},
			{name: "no", index: "no", width: 70, label: "no", sortable: true, align: "left",hidden:true},
			{name: "empcode", index: "empcode", width: 70, label: "empcode", sortable: true, align: "left",hidden:true},
			{name: "workercode", index: "workercode", width: 70, label: "workercode", sortable: true, align: "left",hidden:true},
			{name: "supplcode", index: "supplcode", width: 70, label: "supplcode", sortable: true, align: "left",hidden:true},
			{name: "issalary", index: "issalary", width: 70, label: "issalary", sortable: true, align: "left",hidden:true},
			{name: "faulttype", index: "faulttype", width: 70, label: "faulttype", sortable: true, align: "left",hidden:true},
			{name: "pk", index: "pk", width: 70, label: "pk", sortable: true, align: "left",hidden:true}
			
		],
	};
	Global.JqGrid.initJqGrid("dataTable_man",gridOption2);

	if("${map.m_umState}" == "goConfirm"){
		//dutydate dutymandescr managecfmdate managecfmmandescr cancelremark providedate
		$("#providemandescr_li").attr("hidden","true");
		$("#dutymandescr_li").attr("hidden","true");
		$("#managecfmdate_li").attr("hidden","true");
		$("#managecfmmandescr_li").attr("hidden","true");
		$("#cancelremark_li").attr("hidden","true");
		$("#providedate_li").attr("hidden","true");
		$("#dutydate_li").attr("hidden","true");
	}else{
		jQuery("#dataTable_man").setGridParam().hideCol("confirmamount").trigger("reloadGrid");
	}
	

});
 
//保存定责人员明细
function save(isCfm){
	var rets = $("#dataTable_man").jqGrid("getRowData");
	var params = {"fixDutyManJson": JSON.stringify(rets)};
	Global.Form.submit("page_form","${ctx}/admin/fixDuty/doSave",params,function(ret){
		if(ret.rs==true){
			if(isCfm){
				closeWin();
			}else{
				art.dialog({
					content: ret.msg,
					time: 1000,
					beforeunload: function () {
	    				closeWin();
				    }
				});
			}
		}else{
			$("#_form_token_uniq_id").val(ret.token.token);
    		art.dialog({
				content: ret.msg,
				width: 200
			});
		}
		
	});
} 
function submit(){
	var m_umState="${map.m_umState}";
	if(m_umState=="goCancel"){
		cancel();
	}else{
		var newStatus="";
		if(m_umState=="goConfirm"){
			newStatus="5";
			var sumAmount=$("#dataTable_man").footerData().amount;
			var cfmofferprj=parseFloat($("#cfmofferprj").val());
			var cfmmaterial=parseFloat($("#cfmmaterial").val());
			if(cfmofferprj+cfmmaterial!=sumAmount){
				art.dialog({
					 content:'确认金额不等于总金额，是否确认提交审批？',
					 lock: true,
					 ok: function () {
				      updateOneStatus(newStatus);
				      return true;
					},
					cancel: function () {
						return true;
					}
				});
			}else{
				 updateOneStatus(newStatus);
			}
		}else if(m_umState=="goRetCfm"){
			newStatus="4";
			updateOneStatus(newStatus);
		}else if(m_umState=="goManageCfm"){
			newStatus="6";
			updateOneStatus(newStatus);
		}else if(m_umState=="goManageCb"){
			var isForbidden = true;
			$.ajax({
				url:"${ctx}/admin/fixDuty/isWorkCostDetail",
				type: "post",
				data: {no: "${map.no}"},
				dataType: "json",
				cache: false,
				async: false,
				success: function(obj){
					if (obj.isHad) {
						isForbidden = false;
						art.dialog({
							content: "已存在工人工资单的定责单不能反审批。",
						});
					}
				},
				error: function(obj){
					showAjaxHtml({"hidden": false, "msg": "获取数据出错~"});
				}
			});
			if (isForbidden) {
				newStatus="5";
				updateOneStatus(newStatus);
			}
		}
	}
}
//取消
function cancel(){
	$.ajax({
		url:"${ctx}/admin/fixDuty/doCancel",
		type:"post",
		data:{no:"${map.no}",cancelRemark:$("#cancelremark").val(),status:"${map.status}"},
		dataType:"json",
		cache:false,
		async:false,
		error:function(obj){
			showAjaxHtml({"hidden": false, "msg": "获取数据出错~"});
		},
		success:function(obj){
			if(obj>0){
				art.dialog({
					content : "取消成功",
					time : 1000,
					beforeunload: function () {
			    		closeWin();
					}
				});
			}else{
				art.dialog({
					content :"取消失败，请检查状态是否已被更新！",
				});
			}
		}
	});
}
function updateOneStatus(newStatus){
	var rets;
	if("${map.m_umState}"=="goConfirm"){
	 	rets = $("#dataTable_man").jqGrid("getRowData");
	} 
	$.ajax({
		url:"${ctx}/admin/fixDuty/doUpdateOneStatus",
		type:"post",
		data:{status:newStatus,oldStatus:"${map.status}",no:"${map.no}",fixDutyManJson:JSON.stringify(rets),m_umState:"${map.m_umState}",cfmReturnRemark:$("#cfmReturnRemark").val()},
		dataType:"json",
		cache:false,
		async:false,
		error:function(obj){
			showAjaxHtml({"hidden": false, "msg": "获取数据出错~"});
		},
		success:function(obj){
			if(obj>0){
				art.dialog({
					content : "${map.content}"+"成功",
					time : 1000,
					beforeunload : function() {
						closeWin();
					}
				});
			}else{
				art.dialog({
					content : "${map.content}"+"失败，请检查状态是否已被更新！",
				});
			}
		}
	});
}
function m_add(){
	var ids=$("#dataTable_man").jqGrid("getDataIDs");
	var ret = $("#dataTable_man").jqGrid('getRowData',ids[ids.length-1]);
	var ids=$("#dataTable_man").jqGrid("getDataIDs");
	var newId=1;
	if(ids.length>0){
		newId=parseInt(ids[ids.length-1],0)+1;
	} 
	var confirmAmount = 0;
	if(ret.length>0){
		confirmAmount = ret.confirmamount;
	}
	
    var balances = calculateBalances()
	var leftRiskFundJson=leftRiskFund();
	Global.Dialog.showDialog("save", {
		title : "定责人员--增加",
		url : "${ctx}/admin/fixDuty/goManAdd",
		postData:{
			m_umState:"A",
			fromPage: "confirmDuty",
			no:"${map.no}",
			projectMan:"${map.projectman}",
			projectManDescr:"${map.projectmandescr}",
			appManType:"${map.appmantype}",
			appWorkerCode:"${map.appworkercode}",
			appManDescr: "${map.appmandescr}",
			custCode:"${map.custcode}",
			confirmAmount:confirmAmount,
			designMan:"${map.designman}",
			leftDesignRiskFund:leftRiskFundJson.leftDesignRiskFund,
			leftPrjManRiskFund:leftRiskFundJson.leftPrjManRiskFund,
			totalDiscBalance: balances.totalDiscBalance,
            discBalance: balances.discBalance,
			isAddDesign:"0"
		},
	    height:450,
	    width:720,
		returnFun : function(v) {
			
			var json = {
				faulttypedescr:v.faultTypeDescr,amount:v.amount,empnamechi:v.empNamechi,
				workernamechi:v.workerNamechi,suppldescr:v.supplDescr,issalarydescr:v.isSalaryDescr,
				empcode:v.empCode,workercode:v.workerCode,supplcode:v.supplCode,
				amount:v.amount,empnamechi:v.empNamechi,pk:(ids.length+1)*-1,
				issalary:v.isSalary,faulttype:v.faultType, paidamount: v.paidAmount,
				expired:"F",actionlog:"ADD",lastupdate:new Date(),lastupdatedby:"${sessionScope.USER_CONTEXT_KEY.czybh}",
				remark:v.remark,confirmamount:(v.workerCode == "" ? "": v.confirmAmount),riskfund:v.riskFund
			};									
			$("#dataTable_man").jqGrid("addRowData", newId, json);
			$("#dataChanged").val("1");
		}
	});
}
function m_update(){
	var id = $("#dataTable_man").jqGrid("getGridParam","selrow");
	var ret = $("#dataTable_man").jqGrid('getRowData',id);
	if(!id){
		art.dialog({
			content: "请选择一条记录！",width: 200
		});
		return false;
	} 
	
    var balances = calculateBalances()
	var leftRiskFundJson=leftRiskFund();
	Global.Dialog.showDialog("update", {
		title : "定责人员--编辑",
		url : "${ctx}/admin/fixDuty/goManUpdate",
		postData:{
			map:JSON.stringify(ret),
			m_umState:"M",
			fromPage: "confirmDuty",
			no:"${map.no}",
			projectMan:"${map.projectman}",
			projectManDescr:"${map.projectmandescr}",
			appManType:"${map.appmantype}",
			appWorkerCode:"${map.appworkercode}",
			appManDescr: "${map.appmandescr}",
			custCode: "${map.custcode}",
			confirmAmount:ret.confirmamount,
			designMan:"${map.designman}",
			leftDesignRiskFund:leftRiskFundJson.leftDesignRiskFund,
			leftPrjManRiskFund:leftRiskFundJson.leftPrjManRiskFund,
			totalDiscBalance: balances.totalDiscBalance,
            discBalance: balances.discBalance,
			isAddDesign:"0"
		},
	    height:450,
	    width:720,
		returnFun : function(v) {
			var json = {
				faulttypedescr:v.faultTypeDescr,amount:v.amount,empnamechi:v.empNamechi,
				workernamechi:v.workerNamechi,suppldescr:v.supplDescr,issalarydescr:v.isSalaryDescr,
				empcode:v.empCode,workercode:v.workerCode,supplcode:v.supplCode,
				faulttypedescr:v.faultTypeDescr,amount:v.amount,empnamechi:v.empNamechi,
				issalary:v.isSalary,faulttype:v.faultType,pk:v.pk, paidamount: v.paidAmount,
				expired:"F",actionlog:"EDIT",lastupdate:new Date(),lastupdatedby:"${sessionScope.USER_CONTEXT_KEY.czybh}",
				remark:v.remark,confirmamount:(v.workerCode == "" ? "": v.confirmAmount),riskfund:v.riskFund
			};
			$("#dataTable_man").setRowData(id, json);
			$("#dataChanged").val("1");
			//编辑时更新合计金额
			var sumAmount=parseFloat($("#dataTable_man").footerData().amount);
			$("#dataTable_man").footerData('set', { "amount": sumAmount-parseFloat(ret.amount)+parseFloat(json.amount)});   
		}
	});
}
function m_view(){
	var id = $("#dataTable_man").jqGrid("getGridParam","selrow");
	var ret = $("#dataTable_man").jqGrid('getRowData',id);
	if(!id){
		art.dialog({
			content: "请选择一条记录！",width: 200
		});
		return false;
	}
	
    var balances = calculateBalances()
	var leftRiskFundJson=leftRiskFund();
	Global.Dialog.showDialog("m_view", {
		title : "定责人员--查看",
		url : "${ctx}/admin/fixDuty/goManUpdate",
		postData:{
			map:JSON.stringify(ret),
			m_umState:"V",
			no:"${map.no}",
			fromPage: "confirmDuty",
			projectMan:"${map.projectman}",
			projectManDescr:"${map.projectmandescr}",
			appManType:"${map.appmantype}",
			appWorkerCode:"${map.appworkercode}",
			appManDescr: "${map.appmandescr}",
			custCode: "${map.custcode}",
			confirmAmount:ret.confirmamount,
			designMan:"${map.designman}",
			leftDesignRiskFund:leftRiskFundJson.leftDesignRiskFund,
			leftPrjManRiskFund:leftRiskFundJson.leftPrjManRiskFund,
			totalDiscBalance: balances.totalDiscBalance,
            discBalance: balances.discBalance,
			isAddDesign:"0"
			
		},
	    height:450,
	    width:720
	});
}
//删除
function m_del(){
 	var id = $("#dataTable_man").jqGrid("getGridParam","selrow");
	if(!id){
		art.dialog({
			content: "请选择一条记录进行删除操作！"
		});
		return false;
	}
	art.dialog({
		 content:"是否确认要删除",
		 lock: true,
		 ok: function () {
			Global.JqGrid.delRowData("dataTable_man",id);
			$("#dataChanged").val("1");
		},
		cancel: function () {
				return true;
		}
	}); 
}
//风控基金余额
function leftRiskFund(){
	var designRiskFund=parseFloat("${map.designriskfund}");//楼盘可使用设计师风控基金
	var prjManRiskFund=parseFloat("${map.prjmanriskfund}");//楼盘可使用项目经理风控基金
	var thisDesignRiskFund=0;//本单累计设计师风控金额
	var thisPrjManRiskFund=0;//本单累计项目经理风控金额
	var otherRiskFundJson=OtherRiskFund();
	var otherDesignRiskFund=parseFloat(otherRiskFundJson.DesignRiskFund);//其他单累计设计师风控金额
	var otherPrjManRiskFund=parseFloat(otherRiskFundJson.PrjManRiskFund);//其他单累计项目经理风控金额
	var designMan="${map.designman}";//楼盘设计师
	var rowData= $("#dataTable_man").jqGrid('getRowData');
	$.each(rowData, function(k, v){//员工编号与楼盘设计师相同的判断设计师风控基金，否则判断项目经理风控基金
		if(v.faulttype=="1" && v.empcode==designMan){
			thisDesignRiskFund+=parseFloat(v.riskfund);
		}else{
			thisPrjManRiskFund+=parseFloat(v.riskfund);
		}
	});
    
    var leftDesignRiskFund=designRiskFund-(thisDesignRiskFund+otherDesignRiskFund);
    var leftPrjManRiskFund=prjManRiskFund-(thisPrjManRiskFund+otherPrjManRiskFund);
    var returnJson = {
        leftDesignRiskFund: leftDesignRiskFund,
        leftPrjManRiskFund: leftPrjManRiskFund,
    };
        
	return returnJson;
}
//其他单累计风控金额
function OtherRiskFund(){
	var riskFundJson={};
	$.ajax({
		url:"${ctx}/admin/fixDuty/getOtherRiskFund",
		type:"post",
    	data:{custCode:$("#custcode").val(),no:$("#no").val()},
		dataType:"json",
		cache:false,
		async:false,
		error:function(obj){			    		
			art.dialog({
				content:"访问出错,请重试",
				time:3000,
				beforeunload: function () {}
			});
		},
		success:function(data){
			riskFundJson=data;
		}
	});	
	return riskFundJson;
}

function calculateBalances() {
    var discounts = summarizeDiscounts()
    var designMan = "${map.designman}"
    var grid = $("#dataTable_man")
    var ids = grid.getDataIDs()
    
    var sumFrontendRiskOfThisDuty = 0
    for (var i = 0; i < ids.length; i++) {
        var rowData = grid.getRowData(ids[i])
        
        if (rowData.faulttype === "1"
            && rowData.empcode === designMan) {
	        sumFrontendRiskOfThisDuty += parseFloat(rowData.riskfund)
        }
    }
    
    return {
        totalDiscBalance: discounts.TotalDiscBalance - sumFrontendRiskOfThisDuty,
        discBalance: discounts.DiscBalance
    }
}

function summarizeDiscounts(){
    var discounts = {}
    
    $.ajax({
        url: "${ctx}/admin/fixDuty/summarizeDiscounts",
        type: "post",
        data: {
            custCode: $("#custcode").val(),
            fixDutyNo: $("#no").val()
        },
        dataType: "json",
        cache: false,
        async: false,
        success: function(data) {
            discounts = data
        }
    })
    
    return discounts
}
</script>
</html>
