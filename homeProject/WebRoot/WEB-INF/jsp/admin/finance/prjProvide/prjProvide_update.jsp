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
<title>项目经理提成领取--编辑</title>  
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp" %>
<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
<script>
$(function(){
	$("#appCZY").openComponent_employee({showValue:"${prjProvide.appCZY}",showLabel:"${prjProvide.appCZYDescr}",readonly:true });
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
		{name: "area", index: "area", width: 60, label: "面积", sortable: true, align: "right"},
		{name: "zjfee", index: "zjfee", width: 70, label: "直接费", sortable: true, align: "right", sum: true},
		{name: "basectrlamt", index: "basectrlamt", width: 80, label: "发包", sortable: true, align: "right", sum: true},
		{name: "maincoopfee", index: "maincoopfee", width: 90, label: "主材配合费", sortable: true, align: "right", sum: true},
		{name: "withhold", index: "withhold", width: 60, label: "预扣", sortable: true, align: "right", sum: true},
		{name: "cost", index: "cost", width: 80, label: "成本", sortable: true, align: "right", sum: true},
		{name: "recvfee", index: "recvfee", width: 61, label: "已领", sortable: true, align: "right", sum: true},
		{name: "recvfeefixduty", index: "recvfeefixduty", width: 61, label: "已领(定责)", sortable: true, align: "right", sum: true},
		{name: "qualityfee", index: "qualityfee", width: 70, label: "质保金", sortable: true, align: "right", sum: true},
		{name: "accidentfee", index: "accidentfee", width:80, label: "扣意外险", sortable: true, align: "right", sum: true},
		{name: "mustamount", index: "mustamount", width: 80, label: "应发金额", sortable: true, align: "right", sum: true},
		{name: "realamount", index: "realamount", width: 80, label: "实发金额", sortable: true, align: "right", sum: true, editable:true},
		{name: "provideremark", index: "provideremark", width: 80, label: "发放备注", sortable: true, align: "left",editable:true},
		{name: "custcode", index: "custcode", width: 84, label: "custcode", sortable: true, align: "left", hidden: true},
		{name: "jsye", index: "jsye", width: 120, label: "项目经理结算余额", sortable: true, align: "right", sum: true},
		{name: "ysper", index: "ysper", width: 100, label: "占预算百分比", sortable: true, align: "left"},
		{name: "fbper", index: "fbper", width: 100, label: "占发包百分比", sortable: true, align: "left"}
		],
		loadonce: true,
		beforeSelectRow:function(id){
			setTimeout(function(){
				relocate(id,"dataTable");
			},10);
		}
	};
	Global.JqGrid.initEditJqGrid("dataTable",gridOption);
	//项目经理结算新增
	$("#savePrj").on("click",function(){
		var custcode = Global.JqGrid.allToJson("dataTable","custcode");
		var arr = new Array();
		arr = custcode.fieldJson.split(",");
		var ids=$("#dataTable").jqGrid("getDataIDs");
		Global.Dialog.showDialog("savePrj",{
			title:"项目经理结算——新增",
			url:"${ctx}/admin/prjProvide/goSavePrj",
			postData:{
				arr:arr
			},
			width:1080,
			height:680,
		    returnFun:function(data){
				if(data){
					$.each(data,function(k,v){
						var json = {
							ischecked:v.ischecked,
							projectman:v.projectman,
							address:v.address,
							area:v.area,
							zjfee:v.zjfee,
							basectrlamt:v.basectrlamt,
							maincoopfee:v.maincoopfee,
							withhold:v.withhold,
							cost:v.cost,
							recvfee:v.recvfee,
							recvfeefixduty:v.recvfeefixduty,
							qualityfee:v.qualityfee,
							accidentfee:v.accidentfee,
							mustamount:v.mustamount,
							realamount:v.realamount,
							provideremark:v.provideremark,
							custcode:v.custcode,
							jsye:myRound(v.jsye,0),
							ysper:v.ysper,
							fbper:v.fbper
						};
						$("#dataTable").jqGrid("addRowData", (k+1+ids.length), json);
					});
				}
			} 
		});
	});
	//删除
	$("#deletePrj").on("click",function(){
		var rt = $("#dataTable").jqGrid("getGridParam","selrow");
		if(!rt){
			art.dialog({content:"请选择一条记录",width:200});
		    return false;	
		}
		art.dialog({
			content:"是否删除该记录",
			lock:true,
			width:200,
			height:100,
			ok:function(){
				$("#dataTable").delRowData(rt);
        	},
		cancel:function(){
			return true;
        }
		});
	});
	//查看
	$("#viewPrj").on("click",function(){
		var id = $("#dataTable").jqGrid("getGridParam","selrow");
		if(!id){
			art.dialog({content: "请选择一条记录！",width: 200});
			return false;
		}
		var ret= selectDataTableRow(); 
		Global.Dialog.showDialog("viewPrj",{
			title:"项目经理结算——查看",
		  	url:"${ctx}/admin/prjManCheck/goCheck",
		  	postData:{custCode:ret.custcode,prjNo:"1"},
		  	height: 700,
			width:1250,
		});
	}); 
	//保存 
	$("#saveBtn").on("click",function(){
		var ids = $("#dataTable").getDataIDs();
		if((!ids || ids.length == 0)){
			art.dialog({content: "明细表中无数据！",width: 200});
			return false;
		}
		var prjproCheckDetail=$('#dataTable').jqGrid("getRowData");
		var param = {"prjproCheckDetailJson": JSON.stringify(prjproCheckDetail)};
		console.log(param);
		Global.Form.submit("dataForm","${ctx}/admin/prjProvide/doUpdate",param,function(ret){
			if(ret.rs==true){
				art.dialog({
					content: ret.msg,
					/* time: 1000, */
					beforeunload: function () {
	    				closeWin();
				    }
				});
			}else{
				$("#_form_token_uniq_id").val(ret.token.token);
	    		art.dialog({
					content: ret.msg,
					width: 200,
				});
			}
			
		});
	});
});
</script>
</head>
<body>
<div class="body-box-form" >
	<div class="panel panel-system" >
	    <div class="panel-body" >
	    	<div class="btn-group-xs" >
				<button type="button" class="btn btn-system "  id="saveBtn">保存</button>
				<button type="button" class="btn btn-system "  id="closeBut" onclick="closeWin(false)">关闭</button>
			</div>
		</div>
	</div>
	<div class="panel panel-info" >  
		<div class="panel-body">
			<form action="" method="post" id="dataForm" class="form-search" target="targetFrame">
				<input type="hidden" name="jsonString" value=""/>
				<input type="text" id="status" name="status" value="${prjProvide.status}" hidden="true"/>
				<ul class="ul-form">	
					<div class="validate-group">		  
						<li class="form-validate">
							<label>结算流水</label>
							<input type="text" id="no" name="no" value="${prjProvide.no}" readonly="readonly"/>
						</li>
						<li>	
							<label>申请日期</label>
							<input type="text" id="joinDate" name="joinDate" class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${prjProvide.date}' pattern='yyyy-MM-dd'/>" disabled="disabled"/>
						</li>
						<li>
							<label>开单人</label>
							<input type="text" id="appCZY" name="appCZY" value="${prjProvide.appCZY }" readonly="readonly"/>
						</li>
						<li>
							<label>审核日期</label>
							<input type="text" id="joinDate" name="joinDate" class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${prjProvide.confirmDate}' pattern='yyyy-MM-dd'/>" disabled="disabled"/>
						</li>
						<li>
							<label>审核人员</label>
							<input type="text" id="confirmCZY" name="confirmCZY" value="${prjProvide.confirmCZY }" readonly="readonly"/>
						</li>
						<li>	
							<label>状态</label>
							<input type="text" id="statusName" name="statusName" value="${prjProvide.statusName}" readonly="readonly"/>
						</li>
					</div>
					<div class="validate-group">
						<li>
							<label class="control-textarea">备注</label>
							<textarea id="remarks" name="remarks" >${prjProvide.remarks}</textarea>
						</li>
					</div>	
				</ul>				
			</form>
		</div>
	</div>
	<div  class="container-fluid" >  
	     <ul class="nav nav-tabs">
	        <li class="active"><a href="#tab_prjProvide" data-toggle="tab">主项目</a></li>  
	    </ul>  
	    <div class="tab-content">  
	    	<div id="tab_prjProvide" class="tab-pane fade in active">
	    		<div class="body-box-list"  style="margin-top: 0px;">
					<div class="panel panel-system" >
					    <div class="panel-body">
					      	<div class="btn-group-xs" >
								<button type="submit" class="btn btn-system " id="savePrj">
									<span>新增</span>
								</button>
								<button type="submit" class="btn btn-system " id="deletePrj">
									<span>删除</span>
								</button>
								<button type="button" class="btn btn-system" id="viewPrj">
									<span>查看</span>
								</button>
								<button type="button" class="btn btn-system" onclick="doExcelNow('项目经理提成领取', 'dataTable','dataForm');">
									<span>输出到Excel</span>
								</button>
							</div>
						</div>
					</div>
					<table id= "dataTable" style="overflow: auto;"></table>					
				</div>
			</div>
		</div>
	</div>
</div>		
</body>
</html>
