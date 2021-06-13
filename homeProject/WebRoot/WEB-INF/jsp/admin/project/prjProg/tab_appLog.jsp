<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>

<script type="text/javascript">
$(function(){
	 //初始化表格
		Global.JqGrid.initJqGrid("dataTable_appLog",{
			url:"${ctx}/admin/sysLog/goAppJqGrid",
			rowNum:100,
			height:560,
			colModel : [
				  {name : 'operid',index : 'operid',width : 100,label:'员工编号',sortable : true,align : "left",hidden:true},
				  {name : 'operdescr',index : 'operid',width : 100,label:'项目经理',sortable : true,align : "left"},
				  {name : 'operdate',index : 'operdate',width : 100,label:'登录时间',sortable : true,align : "left",formatter: formatTime,},
				  {name : 'department2',index : 'department2',width : 100,label:'二级部门',sortable : true,align : "left"},
				  {name : 'department2descr',index : 'department2descr',width : 100,label:'二级部门',sortable : true,align : "left"},
	        ],
        });
	
});
 </script>
<div id="content-list">
	<table id= "dataTable_appLog"></table>
	<div id="dataTablePager"></div>
</div> 




