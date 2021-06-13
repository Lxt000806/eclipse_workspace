<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<title>角色列表</title>
	<%@ include file="/commons/jsp/common.jsp" %>
	
<script type="text/javascript">
function add(){
	Global.Dialog.showDialog("roleAdd",{
		  title:"添加用户角色",
		  url:"${ctx}/admin/role/goSave",
		  height: 600,
		  width:1000,
		  returnFun: goto_query
		});
}

function update(){
	var ret = selectDataTableRow();
    if (ret) {
      Global.Dialog.showDialog("roleUpdate",{
		  title:"修改用户角色",
		  url:"${ctx}/admin/role/goUpdate?id="+ret.ROLE_ID,
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

function view(){
	var ret = selectDataTableRow();
    if (ret) {
      Global.Dialog.showDialog("roleView",{
		  title:"查看用户角色",
		  url:"${ctx}/admin/role/goDetail",
		  postData:{id:ret.ROLE_ID,ptDescr:ret.syscodedescr},
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
	var url = "${ctx}/admin/role/doDelete";
	beforeDel(url);
}

/**初始化表格*/
$(function(){
        //初始化表格
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/role/goJqGrid",
			multiselect: true,
			height:$(document).height()-$("#content-list").offset().top-75,
			styleUI: 'Bootstrap', 
			colModel : [
			  {name : 'ROLE_ID',index : 'ROLE_ID',width : 100,label:'角色编码',sortable : true,align : "left"},
		      {name : 'ROLE_NAME',index : 'ROLE_NAME',width : 100,label:'角色名称',sortable : true,align : "left"},
		      {name : 'ROLE_CODE',index : 'ROLE_CODE',width : 150,label:'角色助记码',sortable : true,align : "left"},
		      {name : 'creater',index : 'creater',width : 100,label:'创建人',sortable : true,align : "left"},
		      {name : 'Priority',index : 'Priority',width : 60,label:'优先级',sortable : true,align : "left"},
		      {name : 'REMARK',index : 'REMARK',width : 200,label:'备注',sortable : true,align : "left"},
		      {name : 'syscodedescr',index : 'syscodedescr',width : 150,label:'平台类型',sortable : true,align : "left"}
            ]
		});
});

function authority(){
	var ret = selectDataTableRow();
    if (ret) {
      if (ret.ROLE_CODE=='${superAdmin}'){
    	  art.dialog({
  			content: "超级管理员不需要分配权限"
  		  });
    	  return;
      }
      Global.Dialog.showDialog("roleAuth",{
		  title:"选择权限",
		  url:"${ctx}/admin/role/goAuthority?id="+ret.ROLE_ID,
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
function authorityApp(){
	var ret = selectDataTableRow();
    if (ret) {
      Global.Dialog.showDialog("roleAuth",{
		  title:"选择权限",
		  url:"${ctx}/admin/role/goAppAuthority?id="+ret.ROLE_ID,
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

function copy(){
	var ret = selectDataTableRow();
    if (ret) {
      Global.Dialog.showDialog("copyAuth",{
		  title:"复制权限",
		  url:"${ctx}/admin/role/goCopy?id="+ret.ROLE_ID,
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

function fastMenu(){
	var ret = selectDataTableRow();
    if (ret) {
      Global.Dialog.showDialog("fastMenu",{
		  title:"快捷菜单",
		  url:"${ctx}/admin/role/goFastMenu?id="+ret.ROLE_ID,
		  height:650,
		  width:1000,
		  returnFun: goto_query
		});
    } else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}
</script>
</head>
    
<body>
	<div class="body-box-list" >
		<div class="query-form">
			  <form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
					<ul class="ul-form">
							<li>
								<label>角色编码</label>
								<input type="text" id="roleCode" name="roleCode" style="width:160px;"  value="${role.roleCode }" /></td>
							</li>
							<li>
								<label>角色名称</label>
								<input type="text" id="roleName" name="roleName" style="width:160px;" value="${role.roleName }" /></td>
							</li>
							<li>
								<label>	平台类型</label>
								<house:dict id="sysCode" dictCode="ptdm" value="${role.sysCode }"></house:dict>
							</li>
							<li >
								<button type="button" class="btn  btn-sm btn-system " onclick="goto_query();"  >查询</button>
								<button type="button" class="btn btn-sm btn-system " onclick="clearForm();"  >清空</button>
							</li>
					</ul>
			</form>
		</div><!--query-form-->
		<div class="pageContent">
			<!--panelBar-->
			<div class="btn-panel" >
    			  <div class="btn-group-sm"  >
	            	<house:authorize authCode="ROLE_SAVE">
	                    	<button type="button" class="btn btn-system "  onclick="add()">
						       <span>添加</span>
	                </house:authorize>
	                <house:authorize authCode="ROLE_UPDATE">
	                    	<button type="button" class="btn btn-system " onclick="update()">
						       <span>编辑</span>
	                </house:authorize>
	                <house:authorize authCode="ROLE_VIEW">
	                    	<button type="button" class="btn btn-system "  onclick="view()">
						       <span>查看</span>
	                </house:authorize>
	                <house:authorize authCode="ROLE_COPY">
	                    	<button type="button" class="btn btn-system "  onclick="copy()">
						       <span>权限复制</span>
	                </house:authorize>
	                <house:authorize authCode="ROLE_AUTH">
	                    	<button type="button" class="btn btn-system "  onclick="authority()">
						       <span>分配权限</span>
	                </house:authorize>
	                 <house:authorize authCode="ROLE_APPAUTH">
	                    	<button type="button" class="btn btn-system "  onclick="authorityApp()">
						       <span>分配APP权限</span>
	                </house:authorize>
	                <house:authorize authCode="ROLE_DELETE">
							<button type="button" class="btn btn-system "  onclick="del()">
								<span>删除</span>
	                 </house:authorize>
	                 <house:authorize authCode="ROLE_FASTMENU">
	                    	<button type="button" class="btn btn-system "  onclick="fastMenu()">
						       <span>快捷菜单</span>
	                </house:authorize>
	                 
				</div>
			</div>
			<!--panelBar end-->

			<div id="content-list">
				<table id= "dataTable"></table>
				<div id="dataTablePager"></div>
			</div>
		</div> 
	</div>
</body>
</html>
