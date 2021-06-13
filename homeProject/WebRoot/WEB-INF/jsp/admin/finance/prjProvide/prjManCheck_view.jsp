<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>
<!DOCTYPE html>
<html>
<head>
<title>项目经理提成领取明细--查看</title>  
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp"%>
<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
<script>
$(function(){
	$("#appCZY").openComponent_employee({showValue:"${prjProvide.appCZY}",showLabel:"${prjProvide.appCZYDescr}",readonly:true });
	$("#confirmCZY").openComponent_employee({showValue:"${prjProvide.confirmCZY}",showLabel:"${prjProvide.confirmCZYDescr}",readonly:true });
});
$(function(){
	var lastCellRowId;
	var gridOption = {
		height:280,
		rowNum:10000000,
		url:"${ctx}/admin/prjProvide/goJqGrid_toPrjProCheck",
		postData:{provideNo:$.trim($("#no").val())}, 
		styleUI: "Bootstrap", 
		colModel : [
		{name: "ischecked", index: "ischecked", width: 80, label: "是否结帐", sortable: true, align: "left",hidden:true},
		{name: "projectman", index: "projectman", width: 80, label: "项目经理", sortable: true, align: "left"},
		{name: "address", index: "address", width: 150, label: "楼盘", sortable: true, align: "left"},
		{name: "area", index: "area", width: 60, label: "面积", sortable: true, align: "left"},
		{name: "zjfee", index: "zjfee", width: 70, label: "直接费", sortable: true, align: "left", sum: true},
		{name: "basectrlamt", index: "basectrlamt", width: 80, label: "发包", sortable: true, align: "left", sum: true},
		{name: "maincoopfee", index: "maincoopfee", width: 90, label: "主材配合费", sortable: true, align: "left", sum: true},
		{name: "withhold", index: "withhold", width: 80, label: "预扣", sortable: true, align: "left", sum: true},
		{name: "cost", index: "cost", width: 80, label: "成本", sortable: true, align: "left", sum: true},
		{name: "recvfee", index: "recvfee", width: 61, label: "已领", sortable: true, align: "left", sum: true},
		{name: "qualityfee", index: "qualityfee", width: 70, label: "质保金", sortable: true, align: "left", sum: true},
		{name: "accidentfee", index: "accidentfee", width: 80, label: "扣意外险", sortable: true, align: "left", sum: true},
		{name: "mustamount", index: "mustamount", width: 80, label: "应发金额", sortable: true, align: "left", sum: true},
		{name: "realamount", index: "realamount", width: 80, label: "实发金额", sortable: true, align: "left", sum: true},
		{name: "provideremark", index: "provideremark", width: 80, label: "发放备注", sortable: true, align: "left"},
		{name: "custcode", index: "custcode", width: 84, label: "custcode", sortable: true, align: "left", hidden: true},
		{name: "jsye", index: "jsye", width: 140, label: "项目经理结算余额", sortable: true, align: "left", sum: true},
		{name: "ysper", index: "ysper", width: 120, label: "占预算百分比", sortable: true, align: "left"},
		{name: "fbper", index: "fbper", width: 120, label: "占发包百分比", sortable: true, align: "left"}
		],
	};
	Global.JqGrid.initJqGrid("dataTable",gridOption);
});
$(function(){
	$("input").prop("disabled",true);
});
</script>
</head>
<body>
<div class="body-box-form" >
	<div class="panel panel-system" >
	    <div class="panel-body" >
	    	<div class="btn-group-xs" >
				<button type="button" class="btn btn-system "  id="doExcel">输出到excel</button>
				<button type="button" class="btn btn-system "  id="closeBut" onclick="closeWin(false)">关闭</button>
			</div>
		</div>
	</div>
	<div class="panel panel-info" >  
		<div class="panel-body">
			<form action="" method="post" id="dataForm" class="form-search" target="targetFrame">
				<input type="hidden" name="m_umState" id="m_umState" value="${prjCheck.m_umState}"/>
				<input type="hidden" name="custTypeType" id="custTypeType" value="${prjCheck.custTypeType}"/>
				<house:token></house:token>
				<ul class="ul-form">
						<li class="form-validate">
							<label>客户编号</label>
							<input type="text" id="custCode" name="custCode"   value="${prjCheck.custCode}" readonly="readonly"/>                                             
						</li>
						<li class="form-validate">
							<label>客户名称</label>
							<input type="text" id="custDescr" name="custDescr"   value="${prjCheck.custDescr}" readonly="readonly"/>                                             
						</li>
						<li class="form-validate">
							<label>楼盘</label>
							<input type="text" id="address" name="address"   value="${prjCheck.address}" readonly="readonly"/>                                             
						</li>
						<li class="form-validate">
							<label>基础预算</label>
							<input type="text" id="baseFee" name="baseFee"   value="${prjCheck.baseFee}" readonly="readonly"/>                                             
						</li>
						<li class="form-validate">
							<label>基础增减项</label>
							<input type="text" id="baseChg" name="baseChg"   value="${prjCheck.baseChg}" readonly="readonly"/>                                             
						</li>
						<li class="form-validate">
							<label>直接费</label>
							<input type="text" id="baseFeeDirct" name="baseFeeDirct"   value="${prjCheck.baseFeeDirct}" readonly="readonly"/>                                             
						</li>
						<li class="form-validate">
							<label>主材预算</label>
							<input type="text" id="mainAmount" name="mainAmount"   value="${prjCheck.mainAmount}" readonly="readonly"/>                                             
						</li>
						<li class="form-validate">
							<label>主材增减项</label>
							<input type="text" id="chgMainAmount" name="chgMainAmount"  value="${prjCheck.chgMainAmount}" readonly="readonly"/>                                             
						</li>
						<li class="form-validate">
							<label>主材配合费</label>
							<input type="text" id="mainCoopFee" name="mainCoopFee"  value="${prjCheck.mainCoopFee}" readonly="readonly"/>                                             
						</li>
						<li class="form-validate">
							<label>发包总价</label>
							<input type="text" id="baseCtrlAmt" name="baseCtrlAmt"  value="${prjCheck.baseCtrlAmt}" readonly="readonly"/>                                             
						</li>
						<li class="form-validate">
							<label>支出</label>
							<input type="text" id="cost" name="cost"  value="${prjCheck.cost}" readonly="readonly"/>                                             
						</li>
						<li class="form-validate">
							<label>预扣</label>
							<input type="text" id="withHold" name="withHold"  value="${prjCheck.withHold}" readonly="readonly"/>                                             
						</li>
						<li class="form-validate">
							<label>已领</label>
							<input type="text" id="recvFee" name="recvFee"  value="${prjCheck.recvFee}" readonly="readonly"/>                                             
						</li>
						<li class="form-validate">
							<label>质保金</label>
							<input type="text" id="qualityFee" name="qualityFee"  value="${prjCheck.qualityFee}" readonly="readonly"/>                                             
						</li>
						<li class="form-validate">
							<label>意外险</label>
							<input type="text" id="accidentFee" name="accidentFee"  value="${prjCheck.accidentFee}" readonly="readonly"/>                                             
						</li>
						<li class="form-validate">
							<label>应发金额</label>
							<input type="text" id="mustAmount" name="mustAmount"  value="${prjCheck.mustAmount}" readonly="readonly"/>                                             
						</li>
						<li class="form-validate">
							<label>实发金额</label>
							<input type="text" id="realAmount" name="realAmount"  value="${prjCheck.realAmount}" readonly="readonly"/>                                             
						</li>
						<li class="form-validate">
							<label class="control-textarea">备注</label>
							<textarea id="remarks" name="remarks" >${prjCheck.remarks}</textarea>
					    </li>
					</ul>
			</form>
		</div>
	</div>
	<div  class="container-fluid" >  
	     <ul class="nav nav-tabs">
	        <li class="active"><a href="#tab_prjProvide" data-toggle="tab">项目经理结算明细</a></li>  
	    </ul>  
	    <div class="tab-content">  
	    	<div id="tab_prjProvide" class="tab-pane fade in active">
	    		<div class="body-box-list"  style="margin-top: 0px;">
					<table id= "dataTable" style="overflow: auto;"></table>					
				</div>
			</div>
		</div>
	</div>
</div>		
</body>
</html>
