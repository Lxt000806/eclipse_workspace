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
	<title>特定分包比例</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_supplier.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_wareHouse.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_salesInvoice.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript"> 
var edit = "";
$(function(){
	$("#code").openComponent_customer({showValue:"${customer.code}",showLabel:"${customer.descr}",readonly:true});	
	//初始化表格
	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/jcfbkz/getReqCtrlList",
		postData:{code:"${customer.code}"},
		height:$(document).height()-$("#content-list").offset().top-70,
		styleUI: "Bootstrap", 
		colModel : [
			{name: "BaseItemCode", index: "BaseItemCode", width: 73, label: "基础项目", sortable: true, align: "left",hidden:true},
			{name: "baseitemdescr", index: "baseitemdescr", width: 273, label: "基础项目", sortable: true, align: "left"},
			{name: "BaseCtrlPer", index: "BaseCtrlPer", width: 100, label: "分包比例", sortable: true, align: "right"},
			{name: "Remarks", index: "Remarks", width: 139, label: "备注", sortable: true, align: "left"},
			{name: "LastUpdate", index: "LastUpdate", width: 141, label: "最后修改日期", sortable: true, align: "left", formatter: formatTime},
			{name: "LastUpdatedBy", index: "LastUpdatedBy", width: 88, label: "修改人", sortable: true, align: "left"},
			{name: "Expired", index: "Expired", width: 77, label: "是否过期", sortable: true, align: "left", hidden: true},
			{name: "ActionLog", index: "ActionLog", width: 98, label: "操作", sortable: true, align: "left"}
		],
	});
	
	//
	$("#add").on("click",function(){
		var codes= $("#dataTable").getCol("BaseItemCode",false);
		Global.Dialog.showDialog("add",{
			title:"特定分包比例设定-新增",
			url:"${ctx}/admin/jcfbkz/goAddReqCtrlPer",
			postData:{codes:codes},
			height: 350,
			width: 680,
		    returnFun:function(data){
				if(data){
					$.each(data,function(k,v){
						var json = {
							BaseItemCode:v.baseItemCode,
							baseitemdescr:v.baseItemDescr,
							BaseCtrlPer:v.baseCtrlPer,
							Remarks:v.remarks,
							LastUpdate:new Date(),
							LastUpdatedBy:"${customer.lastUpdatedBy}",
							Expired:"F",
							ActionLog:"ADD",
					  	};
					  	Global.JqGrid.addRowData("dataTable",json);
				  	});
				  	edit = "1";
			  	}
		  	} 
	 	});
	});
	
	$("#fastAdd").on("click",function(){
		var codes= $("#dataTable").getCol("BaseItemCode",false);
		Global.Dialog.showDialog("fastAdd",{
			title:"特定分包比例设定-快速新增",
			url:"${ctx}/admin/jcfbkz/goFastAddReqCtrlPer",
			postData:{codes:codes},
			height: 350,
			width: 680,
		    returnFun:function(data){
				if(data){
					$.each(data,function(k,v){
						var json = {
							BaseItemCode:v.baseItemCode,
							baseitemdescr:v.baseItemDescr,
							BaseCtrlPer:v.baseCtrlPer,
							Remarks:v.remarks,
							LastUpdate:new Date(),
							LastUpdatedBy:"${customer.lastUpdatedBy}",
							Expired:"F",
							ActionLog:"ADD",
					  	};
					  	Global.JqGrid.addRowData("dataTable",json);
				  	});
				  	edit = "1";
			  	}
		  	} 
	 	});
	});
	
	$("#update").on("click",function(){
		var ret = selectDataTableRow();
		var codes= $("#dataTable").getCol("BaseItemCode",false);
		var rowId = $("#dataTable").jqGrid("getGridParam","selrow");
		if(!ret){
			art.dialog({
				content:"请选择一条数据！",
			});
			return;
		}
		Global.Dialog.showDialog("save",{
			title:"特定分包比例-编辑",
			url:"${ctx}/admin/jcfbkz/goUpdateReqCtrlPer",
			postData:{baseItemCode:ret.BaseItemCode,baseCtrlPer:ret.BaseCtrlPer,remarks:ret.Remarks,codes:codes},
			height: 350,
			width: 680,
		    returnFun:function(data){
				if(data){
					$.each(data,function(k,v){
						var json = {
							BaseItemCode:v.baseItemCode,
							baseitemdescr:v.baseItemDescr,
							BaseCtrlPer:v.baseCtrlPer,
							Remarks:v.remarks,
							LastUpdate:new Date(),
							LastUpdatedBy:"${customer.lastUpdatedBy}",
							Expired:"F",
							ActionLog:"ADD",
					  	};
			   			$("#dataTable").setRowData(rowId,json);
				  	});
				  	edit = "1";
			  	}
		  	} 
	 	});
	});
	
	$("#delDetail").on("click",function(){
		var id = $("#dataTable").jqGrid("getGridParam","selrow");
		if(!id){
			art.dialog({content: "请选择一条记录进行删除操作！",width: 200});
			return false;
		}
		art.dialog({
			content:"是否删除",
			lock: true,
			width: 200,
			height: 100,
			ok: function () {
				edit = "1";
				Global.JqGrid.delRowData("dataTable",id);
			},
			cancel: function () {
					return true;
			}
		});
	});
	
	$("#saveBtn").on("click",function(){
		var param= Global.JqGrid.allToJson("dataTable");
		var Ids =$("#dataTable").getDataIDs();
		Global.Form.submit("page_form","${ctx}/admin/jcfbkz/doSaveReqCtrlPer",param,function(ret){
			if(ret.rs==true){
				art.dialog({
					content:ret.msg,
					time:1000,
					beforeunload:function(){
						closeWin();
					}
				});				
			}else{
				$("#_form_token_uniq_id").val(ret.token.token);
				art.dialog({
					content:ret.msg,
					width:200
				});
			}
		}); 
	});
});
function closeWindows(){
	if(edit !=""){
		art.dialog({
			content:"是否保存被更新的数据？",
			lock: true,
			width: 200,
			height: 100,
			ok: function () {
				var param= Global.JqGrid.allToJson("dataTable");
				var Ids =$("#dataTable").getDataIDs();
				if(Ids==null||Ids==""){
					art.dialog({
						content:"明细表为空，不能保存",
					});
					return false;
				}
				Global.Form.submit("page_form","${ctx}/admin/jcfbkz/doSaveReqCtrlPer",param,function(ret){
					if(ret.rs==true){
						art.dialog({
							content:ret.msg,
							time:1000,
							beforeunload:function(){
								closeWin();
							}
						});				
					}else{
						$("#_form_token_uniq_id").val(ret.token.token);
						art.dialog({
							content:ret.msg,
							width:200
						});
					}
				}); 
			},
			cancel: function () {
				closeWin();
			}
		});
	}else{
		closeWin();
	}
}
</script>
</head>
	<body >
		<div class="body-box-list">
			<div class="panel panel-system" >
				<div class="panel-body" >
					<div class="btn-group-xs" >
						<button type="button" class="btn btn-system " id="saveBtn" >
							<span>保存</span>
						</button>
						<button type="button" class="btn btn-system " id="closeBut" onclick="closeWindows()">
							<span>关闭</span>
						</button>
					</div>
				</div>
			</div>
			<div class="query-form">
				<form action="" method="post" id="page_form" class="form-search">
					<ul class="ul-form">
						<li class="form-validate">
							<label>客户编号</label>
							<input type="text" id="code" name="code" style="width:160px;" value="${customer.code}"/>
						</li>
						<li>
							<label>楼盘</label>
							<input type="text" id="address" name="address" style="width:160px;" value="${customer.address}" readonly="true"/>
						</li>
					</ul>	
				</form>
			</div>
			<!--query-form-->
			<div class="btn-panel" >
    			<div class="btn-group-sm"  >
					<button type="button" class="btn btn-system " id="add" >
						<span>新增</span>
					</button>
					<button type="button" class="btn btn-system " id="fastAdd" >
						<span>快速新增</span>
					</button>
					<button type="button" class="btn btn-system " id=update >
						<span>编辑</span>
					</button>
					<button type="button" class="btn btn-system " id="delDetail">
						<span>删除</span>
					</button>
				</div>
			</div>	
			<div class="pageContent">
				<div id="content-list">
					<table id= "dataTable"></table>
				</div> 
			</div>
		</div>
	</body>	
</html>
