<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>UserLeader列表</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_czybm.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
function add(){
	Global.Dialog.showDialog("userLeaderAdd",{
		  title:"添加下属",
		  url:"${ctx}/admin/userLeader/goSave",
		  height: 320,
		  width:600,
		  returnFun: goto_query
		});
}

function del(){
	var url = "${ctx}/admin/userLeader/doDelete";
	var tishi = tishi?tishi:"删除记录";
	var ret = selectDataTableRows();
	if (ret && ret.length>0){
		var arr=[];arr2=[];
		for (var i=0;i<ret.length;i++){
			arr.push(ret[i]["userid"]);
			arr2.push(ret[i]["leaderid"]);
		}
		art.dialog({
			 content:'您确定要'+tishi+'吗？',
			 lock: true,
			 width: 200,
			 height: 100,
			 ok: function () {
		        $.ajax({
					url : url,
					data : "deleteIds="+arr.join(',')+"&deleteIds2="+arr2.join(','),
					contentType: "application/x-www-form-urlencoded; charset=UTF-8",
					dataType: 'json',
					type: 'post',
					cache: false,
					error: function(){
				        art.dialog({
							content: tishi+'出现异常'
						});
				    },
				    success: function(obj){
				    	if(obj.rs){
				    		art.dialog({
								content: obj.msg,
								time: 1000,
								beforeunload: function () {
									goto_query();
							    }
							});
				    	}else{
				    		art.dialog({
								content: obj.msg
							});
				    	}
					}
				});
		    	return true;
			},
			cancel: function () {
				return true;
			}
		});
	}
}
//导出EXCEL
function doExcel(){
	var url = "${ctx}/admin/userLeader/doExcel";
	doExcelAll(url);
}
/**初始化表格*/
$(function(){
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/userLeader/goJqGrid",
			multiselect: true,
			height:$(document).height()-$("#content-list").offset().top-80,
			colModel : [
			  {name : 'userid',index : 'userid',width : 100,label:'下属编号',sortable : true,align : "left"},
			  {name : 'username',index : 'username',width : 100,label:'下属姓名',sortable : true,align : "left"},
			  {name : 'leaderid',index : 'leaderid',width : 100,label:'上级领导编号',sortable : true,align : "left"},
			  {name : 'leadername',index : 'leadername',width : 100,label:'上级领导姓名',sortable : true,align : "left"}
            ]
		});
		$("#leaderId").openComponent_czybm();
		$("#userId").openComponent_czybm();
});
//导入excel
function goImport(){
	Global.Dialog.showDialog("userLeaderImport",{
		  title:"导入excel",
		  url:"${ctx}/admin/userLeader/goImport",
		  height: 600,
		  width: 1000,
		  returnFun: goto_query
		});
}
</script>
</head>
    
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
				<input type="hidden" name="jsonString" value="" />
				<ul class="ul-form">
					<li>
						<label>上级领导</label>
						<input type="text" id="leaderId" name="leaderId" style="width:160px;" value="${userLeader.leaderId }" />
					</li>
					<li>
						<label>下属</label>
						<input type="text" id="userId" name="userId" style="width:160px;" value="${userLeader.userId}" />
					</li>
					<li>
						<button type="button" class="btn btn-sm btn-system" onclick="goto_query();">查询</button>
						<button type="button" class="btn btn-sm btn-system" onclick="clearForm();">清空</button>
					</li>
				</ul>
			</form>
		</div>
		<!--query-form-->
		<div class="btn-panel">
			 <div class="btn-group-sm">
			 	<house:authorize authCode="userLeader_add">
            	<button type="button" class="btn btn-system" onclick="add()">添加</button>
            	</house:authorize>
				<house:authorize authCode="userLeader_del">
                <button type="button" class="btn btn-system" onclick="del()">删除</button>
                </house:authorize>
                <house:authorize authCode="userLeader_import">
				<button type="button" class="btn btn-system" onclick="goImport()">导入excel</button>
				</house:authorize>
                <house:authorize authCode="userLeader_excel">
				<button type="button" class="btn btn-system" onclick="doExcel()">导出excel</button>
				</house:authorize>
			</div>
		</div>
		<div id="content-list">
			<table id= "dataTable"></table>
			<div id="dataTablePager"></div>
		</div>
	</div> 
</body>
</html>


