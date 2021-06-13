<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>
<%
UserContext uc = (UserContext)request.getSession().getAttribute(CommonConstant.USER_CONTEXT_KEY);
String czybh=uc.getCzybh();
%>
<!DOCTYPE html>
<html>
<head>
<title>WareHouse明细</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp"%>
<script src="${resourceRoot}/pub/component_baseBatchTemp.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
$(function(){
	$("#no").openComponent_baseBatchTemp({condition:{'custType':'${baseBatchTemp.custType}'}});
	Global.JqGrid.initJqGrid("dataTable",{
		height:$(document).height()-$("#content-list").offset().top-100,
		cellEdit:true,
		cellsubmit:'clientArray',
		styleUI: 'Bootstrap',
		rowNum:10000,
		page:1,
		colModel : [
		    {name: "restaurant", index: "restaurant", width: 155, label:'<h7>客餐厅</h7><br/><select id="restaurant"  class="form-control" onchange="selectChange(\'restaurant\',\'客餐厅\',this)"><option>0</option><option selected>1</option><option>2</option> <option>3</option><option>4</option><option>5</option></select> ',sortable : false,align: "center",editable:true,edittype:'text'},
			{name: "cookroom", index: "cookroom", width: 155, label:'<h7>厨房</h7><br/><select id="cookroom"   class="form-control" onchange="selectChange(\'cookroom\',\'厨房\',this)"><option>0</option><option selected>1</option><option>2</option> <option>3</option><option>4</option><option>5</option></select> ',sortable : false,align: "center",editable:true,edittype:'text'},
			{name: "bedroom", index: "bedroom", width: 155, label:'<h7>卧室</h7><br/><select id="bedroom"   class="form-control" onchange="selectChange(\'bedroom\',\'卧室\',this)"><option>0</option><option>1</option><option>2</option> <option>3</option><option>4</option><option>5</option></select> ',sortable : false,align: "center",editable:true,edittype:'text'},
			{name: "leisurebalcony", index: "leisurebalcony", width: 155, label:'<h7>休闲阳台</h7><br/><select id="leisurebalcony"   class="form-control" onchange="selectChange(\'leisurebalcony\',\'休闲阳台\',this)"><option>0</option><option>1</option><option>2</option> <option>3</option><option>4</option><option>5</option></select> ',sortable : false,align: "center",editable:true,edittype:'text'},
			{name: "lifebalcony", index: "lifebalcony", width: 155,label:'<h7>生活阳台</h7><br/><select id="lifebalcony"   class="form-control" onchange="selectChange(\'lifebalcony\',\'生活阳台\',this)"><option>0</option><option>1</option></select> ',sortable : false,align: "center",editable:true,edittype:'text'},
			{name: "toilet", index: "toilet", width: 155, label:'<h7>卫生间</h7><br/><select id="toilet"   class="form-control" onchange="selectChange(\'toilet\',\'卫生间\',this)"><option>0</option><option>1</option><option>2</option> <option>3</option><option>4</option><option>5</option></select> ',sortable : false,align: "center",editable:true,edittype:'text'},
	    ]
	});
	for(var i=1;i<6;i++){
		if(i==1) $("#dataTable").addRowData(i, {"restaurant":"客餐厅1","cookroom":"厨房1"}, "last");
		else $("#dataTable").addRowData(i, {}, "last");
	}
})
function selectChange(name,title,e){
	for(var i=1;i<6;i++){
		$("#dataTable").setCell(i,name);
	}
	for(var i=1;i<=e.value;i++){
		$("#dataTable").setCell(i,name,title+i);
	}
}
function save(){
	var fixAreaData=[];
	var fixAreas=[{"name":"restaurant","type":"2"},{"name":"cookroom","type":"3"},{"name":"bedroom","type":"4"},{"name":"leisurebalcony","type":"5"},{"name":"lifebalcony","type":"7"},{"name":"toilet","type":"6"}];
	var descrJson={};
	for(var i=0;i<fixAreas.length;i++){
		var fixAreaName=fixAreas[i]["name"];
		var type=fixAreas[i]["type"];
		var nums=$("#"+fixAreaName).val();
		for(j=1;j<=nums;j++){
			var json={};
			var descr=$("#dataTable").getCell(j,fixAreaName);
		    if(!descr){
			 	art.dialog({
						content: "存在空的区域名称，无法保存！"
				});
				return;
			 }
			 if(!descrJson[descr]){
			  	 descrJson[descr]=1;
			  	 json.descr=descr;
			  	 json.areatype=type;
			  	 fixAreaData.push(json);
			  }else{
			  	 art.dialog({
						content: "存在相同的区域名称：【" + descr + "】，无法保存！"
				 });
				 return;
			  }
		}
	}
	if($("#no").val()){
		art.dialog({
			 content:'保存后将清除原来装修区域，同时清除原有的预算数据，是否确定保存？',
			 lock: true,
			 width: 260,
			 height: 100,
			 ok: function () {
			 	var postData={tempNo:$("#no").val(),custCode:'${BaseItemPlan.custCode}',detailJson:JSON.stringify(fixAreaData)};
			 	$.ajax({
					url:"${ctx}/admin/itemPlan/doBaseBatchFromTemp",
					type: 'POST',
					data:postData,
					cache: false,
				    success: function(obj){
				  		if(obj.rs){
				  		Global.Dialog.returnData = JSON.parse(obj.datas);
				  		closeWin();
				  		}else{
				  			art.dialog({
							content: obj.msg
							});
				  		}
				  		
				    }
			 });
		},
		cancel: function () {
			return true;
			}
		});
	}else{
		art.dialog({
			content: "请选择模板编号！"
		});
	}
}
</script>
</head>
<body>
	<div class="body-box-form">
		<div class="panel panel-system">
			<div class="panel-body">
				<div class="btn-group-xs">
					<button type="button" id="saveBtn" class="btn btn-system " onclick="save()">保存</button>
					<button type="button" class="btn btn-system " onclick="closeWin()">关闭</button>
				</div>
			</div>
		</div>
		<div>
			<div id="tab1" class="tab_content" style="display: block; ">
				<div class="edit-form">
					<form role="form" class="form-search" action="" method="post" id="tab1_page_form">
						<house:token></house:token>
						<ul class="ul-form">
							<li><label>模板编号</label> <input type="text" id="no" name="no" /></li>
						</ul>
					</form>
				</div>
			</div>
			<div class="clear_float"></div>

			<div class="pageContent">
				<div id="content-list">
					<table id="dataTable"></table>
					<div id="dataTablePager"></div>
				</div>
			</div>
		</div>
</body>
</html>
