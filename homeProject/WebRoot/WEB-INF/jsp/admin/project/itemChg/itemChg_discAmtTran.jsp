<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<title>优惠额度调整</title>
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
		url:"${ctx}/admin/discAmtTran/goDetailJqGrid",
		postData:{custCode:"${itemChg.custCode }"},
		height:$(document).height()-$("#content-list").offset().top-70,
		styleUI: "Bootstrap",
		colModel : [
			  {name : "pk",index : "pk",width : 80,label:"pk",sortable : true,align : "left",hidden:true},	
	  		  {name : "custcode",index : "custcode",width : 80,label:"客户编号",sortable : true,align : "left",},	
			  {name : "address",index : "address",width : 210,label:"楼盘地址",sortable : true,align : "left",},	
			  {name : "amount",index : "amount",width : 70,label:"金额",sortable : true,align : "left",},	
			  {name : "typedescr",index : "typedescr",width : 80,label:"类型",sortable : true,align : "left",},	
			  {name : "isextradescr",index : "isextradescr",width : 105,label:"是否额外赠送",sortable : true,align : "left",},	
			  {name : "isriskfunddescr",index : "isriskfunddescr",width : 105,label:"是否风控基金",sortable : true,align : "left",},	
			  {name : "remarks",index : "remarks",width : 220,label:"说明",sortable : true,align : "left",},	
			  {name : "lastupdate",index : "lastupdate",width : 120,label:"最后修改时间",sortable : true,align : "left",formatter:formatDate,},	
			  {name : "lastupdatedby",index : "lastupdatedby",width : 120,label:"最后修改人",sortable : true,align : "left",formatter:cutStr,},	
		],
	});
});  

function goSaveDiscAmtTran(){
	var custCode = $.trim("${itemChg.custCode }");
	Global.Dialog.showDialog("goSaveDiscAmtTran",{
		title:"优惠调度调整——新增",
		url:"${ctx}/admin/itemChg/goSaveDiscAmtTran",
		postData:{custCode:custCode},
		height:410,
		width:730,
		returnFun:goto_query
	});
}

function goUpdateDiscAmtTran(){
	var custCode = $.trim("${itemChg.custCode }");
	var ret=selectDataTableRow();
	var today=formatDate(new Date());
	var lastUpdate=ret.lastupdate;
	if(today!=lastUpdate){
		art.dialog({
			content: "只能修改当天添加的记录",
		});
		return;
	}
	Global.Dialog.showDialog("goUpdateDiscAmtTran",{
		title:"优惠调度调整——编辑",
		url:"${ctx}/admin/itemChg/goUpdateDiscAmtTran",
		postData:{pk:ret.pk,custCode:custCode},
		height:410,
		width:730,
		returnFun:goto_query
	});
}
 
</script>
</head>
<body >
	<div class="body-box-list">
		<div class="panel panel-system">
			<div class="panel-body">
				<div class="btn-group-xs">
					<button type="button" class="btn btn-system " onclick="goSaveDiscAmtTran()">新增</button>
					<button type="button" class="btn btn-system " onclick="goUpdateDiscAmtTran()">编辑</button>
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


