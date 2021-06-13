<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
	<title>项目大类列表</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
<script type="text/javascript">
function add(){
	Global.Dialog.showDialog("builderGroupAdd",{
		  title:"项目大类管理--新增",
		  url:"${ctx}/admin/builderGroup/goSave",
		  height: 400,
		  width:650,
		  returnFun: goto_query
		});
}

function update(){
	var ret = selectDataTableRow();
    if (ret) {
      Global.Dialog.showDialog("builderGroupUpdate",{
		  title:"项目大类--修改",
		  url:"${ctx}/admin/builderGroup/goUpdate?id="+ret.Code,
		  height:400,
		  width:650,
		  returnFun: goto_query
		});
    } else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}

function copy(){
	var ret = selectDataTableRow();
    if (ret) {
      Global.Dialog.showDialog("builderGroupCopy",{
		  title:"项目大类--复制",
		  url:"${ctx}/admin/builderGroup/goCopy?id="+ret.Code,
		  height:400,
		  width:650,
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
      Global.Dialog.showDialog("builderGroupView",{
		  title:"项目大类--查看",
		  url:"${ctx}/admin/builderGroup/goDetail?id="+ret.Code,
		  height:400,
		  width:650
		});
    } else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}
//导出EXCEL
function doExcel(){
	var url = "${ctx}/admin/builderGroup/doExcel";
	doExcelAll(url);
}
$(function(){
        //初始化表格
	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/builderGroup/goJqGrid",
		height:$(document).height()-$("#content-list").offset().top-70,
		colModel : [
		  {name : 'Code',index : 'Code',width : 100,label:'项目大类编号',sortable : true,align : "left"},
	      {name : 'Descr',index : 'Descr',width : 200,label:'项目大类名称',sortable : true,align : "left"},
	      {name : 'Remarks',index : 'Remarks',width : 200,label:'备注',sortable : true,align : "left"},
	      {name : 'LastUpdate',index : 'LastUpdate',width : 150,label:'最后修改时间',sortable : true,align : "left",formatter:formatTime},
	      {name : 'LastUpdatedBy',index : 'LastUpdatedBy',width : 100,label:'修改人',sortable : true,align : "left"},
	      {name : 'Expired',index : 'Expired',width : 100,label:'过期标志',sortable : true,align : "left"}
       ],
       ondblClickRow:function(){
	   	 view();	
	   }			
	});
});
</script>
</head>
    
<body>
	<div class="body-box-list">
  		<div class="query-form">
			<form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
				<input type="hidden" name="jsonString" value="" />
				<input type="hidden" id="expired" name="expired" value="${builderGroup.expired }" />
				<ul class="ul-form">
					<li><label>项目大类编号</label> <input type="text" id="code" name="code" /></li>
					<li><label>项目大类名称</label> <input type="text" id="descr" name="descr" /></li>
				    <li class="search-group-shrink">
						<input type="checkbox"  id="expired_show" name="expired_show" onclick="hideExpired(this)" ${builderGroup.expired !='T'?'checked':''} />   
						<P>隐藏过期</p>
						<button type="summit" class="btn  btn-sm btn-system " onclick="goto_query();">查询</button>
						<button type="button" class="btn btn-sm btn-system " onclick="clearForm();">清空</button>
					</li>
				</ul>
			</form>
		</div>
		<div class="pageContent">
			<div class="btn-panel">
				<div class="btn-group-sm">
					<button type="button" class="btn btn-system " onclick="add()">
					<span>新增</span>
					</button>
					<button type="button" class="btn btn-system " onclick="copy()">
					<span>复制</span>
					</button>
					<button type="button" class="btn btn-system " onclick="update()">
					<span>编辑</span>
					</button>
					<house:authorize authCode="BUILDERGROUP_VIEW">
					<button type="button" class="btn btn-system " onclick="view()">
					<span>查看</span>
					</button>
					</house:authorize>
					<button type="button" class="btn btn-system " onclick="doExcel()">导出到Excel</button>
				</div>
			</div>
		</div>		
  		<div id="content-list">
    		<table id="dataTable"></table>
			<div id="dataTablePager"></div>
		</div>
	</div>
  </body>
</html>
