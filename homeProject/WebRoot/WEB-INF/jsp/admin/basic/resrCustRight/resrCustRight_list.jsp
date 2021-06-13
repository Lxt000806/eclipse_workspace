<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>资源客户分配列表</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>

<script type="text/javascript">
function clearForm(){
	$("#page_form input[type='text']").val('');
	$("#page_form select").val('');
	changeDepartment1(this,'department2','${ctx }');
}

function add(){
	Global.Dialog.showDialog("driverAdd",{
		  title:"添加资源客户分配信息",
		  url:"${ctx}/admin/ResrCustRight/goSave",
		  height: 600,
		  width:700,
		  returnFun: goto_query 
		});
}

function view(){
	var ret = selectDataTableRow();
    if (ret) {
      Global.Dialog.showDialog("resrCustRightView",{
		  title:"查看资源客户分配信息",
		  url:"${ctx}/admin/ResrCustRight/goDetail",
		  postData:{pk:ret.pk},
		  height:600,
		  width:700
		});
    } else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}

function del(){
	var url = "${ctx}/admin/ResrCustRight/doDelete";
	beforeDel(url,"pk");
}

/**初始化表格*/
$(function(){
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/ResrCustRight/goJqGrid",
			height:$(document).height()-$("#content-list").offset().top-80,
			multiselect: true,
			styleUI: 'Bootstrap',
			colModel : [
			  {name : 'pk',index : 'pk',width : 50,label:'pk',sortable : true,align : "left", hidden:true},
		      {name : 'department1descr',index : 'department1descr',width : 95,label:'一级部门',sortable : true,align : "left"},
		      {name : 'department2',index : 'department2',width : 115,label:'二级部门编码',sortable : true,align : "left"},
		      {name : 'department2descr',index : 'department2descr',width : 95,label:'二级部门',sortable : true,align : "left"},
		      {name : 'buildercode',index : 'buildercode',width : 95,label:'项目编码',sortable : true,align : "left"},
		      {name : 'builderdescr',index : 'builderdescr',width : 150,label:'项目名称',sortable : true,align : "left"},
		      {name : 'righttypedescr',index : 'righttypedescr',width : 95,label:'权限类型',sortable : true,align : "left"},
		      {name : 'lastupdate',index : 'lastupdate',width : 135,label:'修改时间',sortable : true,align : "left",formatter:formatTime},
		      {name : 'lastupdatedby',index : 'lastupdatedby',width : 100,label:'修改人',sortable : true,align : "left"},
		      {name : 'expired',index : 'expired',width : 75,label:'过期',sortable : true,align : "left"},
		      {name : 'actionlog',index : 'actionlog',width : 75,label:'日志',sortable : true,align : "left"}
            ]
		});
   		$("#builderCode").openComponent_builder();	
});
</script>
</head>
    
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form action="" method="post" id="page_form" class="form-search">
				<input type="hidden" name="jsonString" value="" />
				<ul class="ul-form">
					<li>
						<label>一级部门</label>
						<house:department1 id="department1" onchange="changeDepartment1(this,'department2','${ctx }')" value="${resrCustRight.department1 }"></house:department1>
					</li>
					<li>	
						<label>二级部门</label>
						<house:department2 id="department2" dictCode="${resrCustRight.department1 }" value="${resrCustRight.department2 }"></house:department2>
					</li>
					<li>	
						<label>项目编码</label>
						<input type="text" id="builderCode" name="builderCode" style="width:160px;"  value="${resrCustRight.builderCode }" />
					</li>	
					<li class="search-group">
						<button type="button" class="btn  btn-sm btn-system " onclick="goto_query();"  >查询</button>
						<button type="button" class="btn btn-sm btn-system " onclick="clearForm();"  >清空</button>
					</li>
				</ul>
			</form>
		</div>
		<div class="clear_float"> </div>

		<div class="btn-panel" >
			<div class="btn-group-sm"  >
				<house:authorize authCode="RESRCUSTRIGHT_SAVE">
					<button type="button" class="btn btn-system " onclick="add()">新增</button>
				</house:authorize>
				<house:authorize authCode="RESRCUSTRIGHT_VIEW">
					<button  id="btnView" type="button" class="btn btn-system " onclick="view()">查看</button>
				</house:authorize>
				<house:authorize authCode="RESRCUSTRIGHT_DELETE">
					<button type="button" class="btn btn-system " onclick="del()">删除</button>
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
