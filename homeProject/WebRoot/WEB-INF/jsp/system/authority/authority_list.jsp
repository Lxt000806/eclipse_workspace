<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<title>权限列表</title>
	<%@ include file="/commons/jsp/common.jsp" %>

<script type="text/javascript">
function add(){
	var tree = window.parent.zTree;
	
	var defaultMenuId = "";
	var selectedNodes = parent.zTree.getSelectedNodes();
	if(selectedNodes.length == 1){
		if(selectedNodes[0].menuType == '${MENU_TYPE_URL }'){
			defaultMenuId = selectedNodes[0].menuId;
		}
	}
	Global.Dialog.showDialog("authorityAdd",{
		  title:"添加权限",
		  url:"${ctx}/admin/authority/goSave?defaultMenuId="+defaultMenuId,
		  height: 600,
		  width:1000,
		  returnFun: goto_query
		});
}

function update(id){
	var ret = selectDataTableRow();
    if (ret) {
      Global.Dialog.showDialog("authorityUpdate",{
		  title:"修改权限",
		  url:"${ctx}/admin/authority/goUpdate?authorityId="+ret.AUTHORITY_ID,
		  height:600,
		  width:1000,
		  returnFun: goto_query
		});
    } else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}

function view(id){
	var ret = selectDataTableRow();
    if (ret) {
      Global.Dialog.showDialog("authorityView",{
		  title:"查看权限",
		  url:"${ctx}/admin/authority/goDetail?authorityId="+ret.AUTHORITY_ID,
		  height:600,
		  width:1000
		});
    } else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}

function del(){
	var url = "${ctx}/admin/authority/doDelete";
	beforeDel(url);
}

//导出EXCEL
function doExcel(){
	var url = "${ctx}/admin/authority/doExcel";
	doExcelAll(url);
}
/**初始化表格*/
$(function(){
        //初始化表格
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/authority/goJqGrid?menuId=${authority.menuId }",
			multiselect: true,
			height:$(document).height()-$("#content-list").offset().top-70,
			styleUI: 'Bootstrap',
			colModel : [
			  {name : 'AUTHORITY_ID',index : 'AUTHORITY_ID',width : 200,label:'权限ID',sortable : true,align : "left",hidden:true},
			  {name : 'AUTH_CODE',index : 'AUTH_CODE',width : 250,label:'权限编码',sortable : true,align : "left"},
		      {name : 'AUTH_NAME',index : 'AUTH_NAME',width : 200,label:'权限名称',sortable : true,align : "left"},
		      {name : 'menu_name',index : 'menu_name',width : 200,label:'所属菜单',sortable : true,align : "left"},
		      {name : 'REMARK',index : 'REMARK',width : 200,label:'备注',sortable : true,align : "left"}
            ]
		});
});
</script>
</head>
    
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form action="" method="post" id="page_form" class="form-search">
				<input type="hidden" name="jsonString" value="" />
				<input type="hidden" id="menuId" name="menuId" value="${authority.menuId }"/>
					<ul class="ul-form">

							<li>
								<label>权限编码</label>
								<input type="text" id="authCode" name="authCode" value="${authority.authCode }" />
							</li>
							<li>
								<label>权限名称</label>
								<input type="text" id="authName" name="authName"  value="${authority.authName }" />
							</li>
					
							<li id="loadMore" >
								<button type="button" class="btn  btn-system btn-sm" onclick="goto_query();"  >查询</button>
								<button type="button" class="btn btn-system btn-sm" onclick="clearForm();"  >清空</button>
							</li>
				</ul>
			</form>
		</div><!--query-form-->
		     <div class="btn-panel" >
   
      <div class="btn-group-sm"  >
  	<house:authorize authCode="AUTH_SAVE">
      <button type="button" class="btn btn-system " onclick="add()">添加</button>
      </house:authorize>
       <house:authorize authCode="AUTH_UPDATE">
      <button type="button" class="btn btn-system "  onclick="update()">修改</button>
     </house:authorize>
    <house:authorize authCode="AUTH_VIEW">
      <button type="button" class="btn btn-system " onclick="view()">查看</button>
       </house:authorize>
     <house:authorize authCode="AUTH_DELETE">
      <button type="button" class="btn btn-system "  onclick="del()">删除</button>
      </house:authorize>
       
      <button type="button" class="btn btn-system "  onclick="doExcel()">excel</button>
      </div>
       
  
   
	</div>
     <div class="pageContent">
			<div id="content-list">
				<table id= "dataTable"></table>
				<div id="dataTablePager"></div>
			</div>
		</div> 
	</div>
</body>
</html>
