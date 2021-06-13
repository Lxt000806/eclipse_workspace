<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<title>工程角色管理</title>
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_builderGroup.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
/**初始化表格*/
$(function(){
	      //初始化表格
	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/prjRole/goJqGrid",
		ondblClickRow: function(){
	              	view();
	              },
	          styleUI: 'Bootstrap',
		height:$(document).height()-$("#content-list").offset().top-70,
		colModel : [
		  {name : 'code',index : 'code',width : 70,label:'编号',sortable : true,align : "left"},
	      {name : 'descr',index : 'descr',width : 150,label:'名称',sortable : true,align : "left",},
	      {name : 'lastupdate',index : 'lastupdate',width : 150,label:'最后修改时间',sortable : true,align : "left",formatter: formatTime},
	      {name : 'lastupdatedby',index : 'lastupdatedby',width : 70,label:'修改人',sortable : true,align : "left"},
	      {name : 'expired',index : 'expired',width : 70,label:'是否过期',sortable : true,align : "left"},
	      {name : 'actionlog',index : 'actionlog',width : 70,label:'操作日志',sortable : true,align : "left"}
	          ]                        
	});
});  

function add(){	
	Global.Dialog.showDialog("CarryRuleAdd",{			
		title:"工程角色——添加",
		url:"${ctx}/admin/prjRole/goSave",
		height: 600,
		width:900,
		returnFun: goto_query
	});
}


function update(){
	var ret = selectDataTableRow();
    if (ret) {    	
      Global.Dialog.showDialog("CarryRuleUpdate",{
		  title:"工程角色——编辑",
		  url:"${ctx}/admin/prjRole/goUpdate?id="+ret.code,
		  height:600,
		  width:900,
		  returnFun: goto_query
		});
    } else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}

function view(){
	var ret = selectDataTableRow();
    if (ret) {    	
      Global.Dialog.showDialog("CarryRuleview",{
		  title:"工程角色——查看",
		  url:"${ctx}/admin/prjRole/goview?id="+ret.code,
		  height:600,
		  width:900,
		  returnFun: goto_query
		});
    } else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}

function del(){
 var url = "${ctx}/admin/prjRole/doDelete";
	 beforeDel(url,"code");
 	 returnFun: goto_query;
	 return true;		
}

</script>
</script>
</head>
    
<body>
	<div class="body-box-list">
        <div class="query-form"  >  
	        <form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
				<input type="hidden" name="exportData" id="exportData">
				<input type="hidden" name="jsonString" value="" />
				<ul class="ul-form">
					<li>
						<label>编号</label>
						<input type="text" id="code" name="code"   />
					</li>
					<li>
						<label>名称</label>
						<input type="text" id="descr" name="descr"   />
					</li>
					<li hidden="true">							
						<label>备注信息</label>
						<input type="text" id="remarks" name="remarks" style="width:160px;"   />
					</li>
					<li id="loadMore" >
						<button type="button" class="btn  btn-system btn-sm" onclick="goto_query();"  >查询</button>
						<button type="button" class="btn btn-system btn-sm" onclick="clearForm();"  >清空</button>
					</li>
			</form>
		</div>
		<div class="clear_float"> </div>
		<!--query-form-->
		<div class="pageContent">
			<div class="btn-panel" >
		    	<div class="btn-group-sm"  >
					<house:authorize authCode="gcxxgl_save">
						<button type="button" class="btn btn-system " onclick="add()">添加</button>
					</house:authorize>
					<house:authorize authCode="gcxxgl_update">
						<button type="button" class="btn btn-system "  onclick="update()">编辑</button>
					</house:authorize>
					<house:authorize authCode="gcxxgl_delete">
						<button type="button" class="btn btn-system " onclick="del()">删除</button>
					</house:authorize>
					<house:authorize authCode="gcxxgl_Excel">
						<button type="button" class="btn btn-system " onclick="doExcelNow('工程角色管理')">导出excel</button>
					</house:authorize>
					<house:authorize authCode="gcxxgl_view">
						<button type="button" class="btn btn-system "  onclick="view()">查看</button>
					</house:authorize>
		    	</div>
			</div>
			<div id="content-list">
				<table id= "dataTable"></table>
				<div id="dataTablePager"></div>
			</div>
		</div>
	</div>
</body>
</html>


