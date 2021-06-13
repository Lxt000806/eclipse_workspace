<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<title>模型工作区</title>
	<%@ include file="/commons/jsp/common.jsp" %>
<script type="text/javascript">
function add(){
	Global.Dialog.showDialog("modelAdd",{
		  title:"添加模型",
		  url:"${ctx}/admin/workflow/model/goAdd",
		  height: 750,
		  width:1200,
		  returnFun: goto_query
		});
}
function update(){
	var ret = selectDataTableRow();
    if (ret) {
      Global.Dialog.showDialog("modelUpdate",{
		  title:"修改模型-"+ret.id_,
		  url:"${ctx}/service/editor?id="+ret.id_,
		  height:750,
		  width:1200,
		  returnFun: goto_query
		});
    } else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}
function versionListen(){
	var ret = selectDataTableRow();
    if (ret) {
      Global.Dialog.showDialog("modelUpdate",{
		  title:"版本监控-"+ret.name_,
		  url:"${ctx}/admin/workflow/model/goListen?key="+ret.key_,
		  height:600,
		  width:1200,
		  returnFun: goto_query
		});
    } else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}

function deploy(){
	var ret = selectDataTableRow();
    if (ret) {
		$.ajax({
			url:"${ctx}/admin/workflow/model/doDeploy/"+ret.id_,
			type: 'post',
			data: {},
			dataType: 'json',
			cache: false,
			error: function(obj){
				showAjaxHtml({"hidden": false, "msg": '保存数据出错~'});
		    },
		    success: function(obj){
		    	if(obj.rs){
		    		art.dialog({
						content: obj.msg,
						time: 1000,
						beforeunload: function () {
		    				goto_query();
					    },
					});
				
		    	}else{
		    		art.dialog({
						content: obj.msg,
						width: 200
					});
		    	}
		    }
		 });
    } else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}
function exp(){
	var ret = selectDataTableRow();
    if (ret) {
    	$.form_submit($("#page_form").get(0), {
    		"action": "${ctx}/admin/workflow/model/doExport/"+ret.id_
    	});
    } else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}
function del(){
    var ret = selectDataTableRow();
    if (ret) {
	    if (ret.status=='已发布'){
			 art.dialog({
				content: '已发布模型不能删除',
				lock: true
			 });
			return ;
		}
		var url = "${ctx}/admin/workflow/model/doDelete/"+ret.id_
		beforeDel(url);
	 } else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}
/**初始化表格*/
$(function(){
        //初始化表格
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/workflow/model/goJqGrid",
			postData:{name:$("#name").val()},
			height:$(document).height()-$("#content-list").offset().top-80,
			colModel : [
		      {name : 'id_',index : 'id_',width : 70,label:'模型编号',sortable : true,align : "left"},
		      {name : 'key_',index : 'key_',width : 70,label:'KEY',sortable : true,align : "left"},
		      {name : 'name_',index : 'name_',width : 100,label:'名称',sortable : true,align : "left"},
		      {name : 'version_',index : 'version_',width : 70,label:'版本号',sortable : true,align : "left"},
		      {name : 'create_time_',index : 'create_time_',width : 150,label:'创建时间',sortable : true,align : "left",formatter: formatTime},
		      {name : 'last_update_time_',index : 'last_update_time_',width : 150,label:'最后更新时间',sortable : true,align : "left",formatter: formatTime},
		      {name : 'status',index : 'status',width : 70,label:'状态',sortable : true,align : "left"},
		      {name : 'meta_info_',index : 'meta_info_',width : 450,label:'元数据',sortable : true,align : "left"}
            ]
		});
});
</script>
</head>
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
				<input type="hidden" name="jsonString" value="" />
				<ul class="ul-form">
						<li>
							<label>模型名称</label>
							<input type="text" id="name" name="name" style="width:160px;" value="" />
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
			 	<house:authorize authCode="model_add">
            	<button type="button" class="btn btn-system" onclick="add()">添加</button>
            	</house:authorize>
				<house:authorize authCode="model_update">
				<button type="button" class="btn btn-system" onclick="update()">编辑</button>
				</house:authorize>
				<house:authorize authCode="model_deploy">
				<button type="button" class="btn btn-system" onclick="deploy()">部署</button>
				</house:authorize>
				<house:authorize authCode="model_exp">
				<button type="button" class="btn btn-system" onclick="exp()">导出</button>
				</house:authorize>
				<house:authorize authCode="model_update">
				<button type="button" class="btn btn-system" onclick="versionListen()">版本监控</button>
				</house:authorize>
				<house:authorize authCode="model_versionListen">
                <button type="button" class="btn btn-system" onclick="del()">删除</button>
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


