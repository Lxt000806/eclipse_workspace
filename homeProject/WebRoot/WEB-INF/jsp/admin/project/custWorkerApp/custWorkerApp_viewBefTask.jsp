<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<title>施工任务监控</title>
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_builderGroup.js?v=${v}" type="text/javascript"></script>
	<style type="text/css">
      .SelectBG{
          background-color:#FF7575!important;
            }    
 	</style>
<script type="text/javascript">
/**初始化表格*/
$(function(){
    //初始化表格
	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/prjMsg/goJqGrid",
		postData:{msgRelCustCode:"${custCode }",workType12:"${workType12 }"},
		height:$(document).height()-$("#content-list").offset().top-70,
		styleUI: 'Bootstrap',
		colModel : [
		  {name : 'pk',index : 'pk',width : 120,label:'pk',sortable : true,align : "left",formatter:cutStr,hidden:true},	
		  {name : 'address',index : 'address',width : 160,label:'楼盘',sortable : true,align : "left",formatter:cutStr},	
		  {name : 'prjitemdescr',index : 'prjitemdescr',width : 120,label:'触发节点',sortable : true,align : "left",formatter:cutStr},
		  {name : 'senddate',index : 'senddate',width : 90,label:'触发时间',sortable : true,align : "left",formatter: formatDate},
		  {name : 'rcvdate',index : 'rcvdate',width : 90,label:'处理日期',sortable : true,align : "left",formatter: formatDate},
		  {name : 'plandealdate',index : 'plandealdate',width : 100,label:'计划处理时间',sortable : true,align : "left",formatter: formatDate},
		  {name : 'planarrdate',index : 'planarrdate',width : 100,label:'计划完成时间',sortable : true,align : "left",formatter: formatDate},
		  {name : 'title',index : 'title',width : 120,label:'任务标题',sortable : true,align : "left",formatter:cutStr},
		  {name : 'msgtext',index : 'msgtext',width : 280,label:'任务描述',sortable : true,align : "left"},
		  {name : 'brcvczy',index : 'brcvczy',width : 80,label:'执行人',sortable : true,align : "left",formatter:cutStr},
		  {name : 'department2descr',index : 'department2descr',width : 80,label:'二级部门',sortable : true,align : "left",formatter:cutStr},
		  {name : 'brcvstatus',index : 'brcvstatus',width : 70,label:'接收状态',sortable : true,align : "left",formatter:cutStr},
		  {name : 'rcvstatus',index : 'rcvstatus',width : 70,label:'接收状态',sortable : true,align : "left",formatter:cutStr,hidden:true},
		  {name : 'deal',index : 'deal',width : 70,label:'是否超时',sortable : true,align : "left",formatter:cutStr},
	      {name : 'itemtype1descr',index : 'itemtype1descr',width : 73,label:'材料类型1',sortable : true,align : "left",frozen: true},
	      {name : 'itemtype2',index : 'itemtype2',width : 70,label:'材料类型2编号',sortable : true,align : "left",frozen: true,hidden:true},
		],
		gridComplete:function(){
        	var ids = $("#dataTable").getDataIDs();
        	for(var i=0;i<ids.length;i++){
            	var rowData = $("#dataTable").getRowData(ids[i]);
             	if(rowData.deal=="是"){
					$('#'+ids[i]).find("td").addClass("SelectBG");
				}                   
			}
		}
	});
});  
 
</script>
</head>
<body >
	<div class="body-box-list">
		<div class="panel panel-system">
			<div class="panel-body">
				<div class="btn-group-xs">
					<button type="button" class="btn btn-system " onclick="closeWin(true,true)">关闭</button>
				</div>
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


