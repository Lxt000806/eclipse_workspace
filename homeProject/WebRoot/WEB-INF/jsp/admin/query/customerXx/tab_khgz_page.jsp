<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<title>客户信息查询</title>
	<%@ include file="/commons/jsp/common.jsp" %>
<script type="text/javascript">
/**初始化表格*/
$(function(){
        //初始化表格
		Global.JqGrid.initJqGrid("dataTable_khgz",{
			url:"${ctx}/admin/customerXx/goJqGrid",
			width:200,
			colModel : [
			  {name : 'documentno',index : 'documentno',width : 70,label:'档案号',sortable : true,align : "left"},
			  {name : 'code',index : 'code',width : 70,label:'客户编号',sortable : true,align : "left"},
		      {name : 'descr',index : 'descr',width : 60,label:'客户名称',sortable : true,align : "left"},
		      {name : 'genderdescr',index : 'genderdescr',width : 30,label:'性别',sortable : true,align : "left"},
		      {name : 'sourcedescr',index : 'sourcedescr',width : 70,label:'客户来源',sortable : true,align : "left"},
		      {name : 'custtypedescr',index : 'custtypedescr',width : 70,label:'客户类型',sortable : true,align : "left"},
		      {name : 'saletypedescr',index : 'saletypedescr',width : 100,label:'销售类型',sortable : true,align : "left"},
		      {name : 'address',index : 'address',width : 200,label:'楼盘',sortable : true,align : "left"},
		      {name : 'layoutdescr',index : 'layoutdescr',width : 100,label:'户型',sortable : true,align : "left"},
		      {name : 'area',index : 'area',width : 60,label:'面积',sortable : true,align : "right"},
		      {name : 'designstyledescr',index : 'designstyledescr',width : 100,label:'设计风格',sortable : true,align : "left"},
		      {name : 'statusdescr',index : 'statusdescr',width : 100,label:'客户状态',sortable : true,align : "left"},
		      {name : 'endcodedescr',index : 'endcodedescr',width : 100,label:'结束代码',sortable : true,align : "left"},
		      {name : 'enddate',index : 'enddate',width : 150,label:'结束时间',sortable : true,align : "left",formatter: formatTime},
		      {name : 'setdate',index : 'setdate',width : 150,label:'下定时间',sortable : true,align : "left",formatter: formatTime},
		      {name : 'signdate',index : 'signdate',width : 150,label:'签订时间',sortable : true,align : "left",formatter: formatTime},
		      {name : 'endremark',index : 'endremark',width : 100,label:'结束原因',sortable : true,align : "left"},
		      {name : 'contractfee',index : 'contractfee',width : 100,label:'工程造价',sortable : true,align : "left"},
		      {name : 'designfee',index : 'designfee',width : 100,label:'设计费',sortable : true,align : "right",formatter: formatTime},
		      {name : 'measurefee',index : 'measurefee',width : 100,label:'量房费',sortable : true,align : "right"},
		      {name : 'drawfee',index : 'drawfee',width : 100,label:'制图费',sortable : true,align : "right"},
		      {name : 'colordrawfee',index : 'colordrawfee',width : 100,label:'效果图费',sortable : true,align : "right"},
		      {name : 'managefee',index : 'managefee',width : 100,label:'管理费',sortable : true,align : "right"},
		      {name : 'basefee',index : 'basefee',width : 100,label:'基础费',sortable : true,align : "right"},
		      {name : 'basedisc',index : 'basedisc',width : 100,label:'基础优惠',sortable : true,align : "right"},
		      {name : 'basefee_dirct',index : 'basefee_dirct',width : 100,label:'基础直接费',sortable : true,align : "right"},
		      {name : 'basefee_comp',index : 'basefee_comp',width : 100,label:'基础综合费',sortable : true,align : "right"},
		      {name : 'mainfee',index : 'mainfee',width : 100,label:'主材费',sortable : true,align : "right"},
		      {name : 'maindisc',index : 'maindisc',width : 100,label:'主材优惠',sortable : true,align : "right"},
		      {name : 'mainservfee',index : 'mainservfee',width : 100,label:'服务性产品费',sortable : true,align : "right"},
		      {name : 'softfee',index : 'softfee',width : 100,label:'软装费',sortable : true,align : "right"},
		      {name : 'softdisc',index : 'softdisc',width : 100,label:'软装优惠',sortable : true,align : "right"},
		      {name : 'softother',index : 'softother',width : 100,label:'软装其它费用',sortable : true,align : "right"},
		      {name : 'integratefee',index : 'integratefee',width : 100,label:'集成费',sortable : true,align : "right"},
		      {name : 'integratedisc',index : 'integratedisc',width : 100,label:'集成优惠',sortable : true,align : "right"},
		      {name : 'cupboardfee',index : 'cupboardfee',width : 100,label:'橱柜费',sortable : true,align : "right"},
		      {name : 'cupboarddisc',index : 'cupboarddisc',width : 100,label:'橱柜优惠',sortable : true,align : "right"},
		      {name : 'softfee_wallpaper',index : 'softfee_wallpaper',width : 100,label:'壁纸款',sortable : true,align : "right"},
		      {name : 'softfee_curtain',index : 'softfee_curtain',width : 100,label:'窗帘款',sortable : true,align : "right"},
		      {name : 'softfee_light',index : 'softfee_light',width : 100,label:'灯具款',sortable : true,align : "right"},
		      {name : 'softfee_furniture',index : 'softfee_furniture',width : 100,label:'家具款',sortable : true,align : "right"},
		      {name : 'softfee_adornment',index : 'softfee_adornment',width : 100,label:'装饰品及其它款',sortable : true,align : "right"},
		      {name : 'crtdate',index : 'crtdate',width : 150,label:'创建时间',sortable : true,align : "left",formatter: formatTime},
		      {name : 'desidpt1',index : 'desidpt1',width : 100,label:'设计师部门一',sortable : true,align : "left"},
		      {name : 'desidpt2',index : 'desidpt2',width : 100,label:'设计师部门二',sortable : true,align : "left"},
		      {name : 'desidpt3',index : 'desidpt3',width : 100,label:'设计师部门三',sortable : true,align : "left"},
		      {name : 'designmandescr',index : 'designmandescr',width : 100,label:'设计师',sortable : true,align : "left"},
		      {name : 'businessmandescr',index : 'businessmandescr',width : 100,label:'业务员',sortable : true,align : "left"},
		      {name : 'busidpt1',index : 'busidpt1',width : 100,label:'业务员部门一',sortable : true,align : "left"},
		      {name : 'busidpt2',index : 'busidpt2',width : 100,label:'业务员部门二',sortable : true,align : "left"},
		      {name : 'busidpt3',index : 'busidpt3',width : 100,label:'业务员部门三',sortable : true,align : "left"},
		      {name : 'againmandescr',index : 'againmandescr',width : 100,label:'翻单员',sortable : true,align : "left"},
		      {name : 'softdesigndescr',index : 'softdesigndescr',width : 100,label:'软装设计师',sortable : true,align : "left"},
		      {name : 'builderdescr',index : 'builderdescr',width : 100,label:'项目名称',sortable : true,align : "left"},
		      {name : 'consarea',index : 'consarea',width : 100,label:'片区',sortable : true,align : "left"},
		      {name : 'remarks',index : 'remarks',width : 200,label:'备注',sortable : true,align : "left"},
		      {name : 'lastupdate',index : 'lastupdate',width : 150,label:'最后修改时间',sortable : true,align : "left",formatter: formatTime},
		      {name : 'lastupdatedby',index : 'lastupdatedby',width : 100,label:'修改人',sortable : true,align : "left"},
		      {name : 'expired',index : 'expired',width : 100,label:'是否过期',sortable : true,align : "left"},
		      {name : 'actionlog',index : 'actionlog',width : 100,label:'操作日志',sortable : true,align : "left"}
            ]
		});
		
});
</script>
</head>
<body>
<div class="body-box-list" style="margin-top: 20px;">
	<div class="pageContent">
		<table id="dataTable_khgz"></table>
		<div id="dataTable_khgzPager"></div>
	</div>
</div>
</body>
</html>
