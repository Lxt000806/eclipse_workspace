<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>itemProcess列表</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>

<script type="text/javascript">
function add(){
	Global.Dialog.showDialog("itemProcessAdd",{
		  title:"材料加工管理--添加",
		  url:"${ctx}/admin/itemProcess/goSave",
		  height: 600,
		  width:1000,
		  returnFun: goto_query
	});
}

function confirm(){
	var ret = selectDataTableRow();
    if (ret) {
      Global.Dialog.showDialog("itemProcessUpdate",{
		  title:"材料加工管理--审核",
		  url:"${ctx}/admin/itemProcess/goConfirm",
		  postData:{no: ret.no},	  
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

function update(){
	var ret = selectDataTableRow();
    if (ret) {
      Global.Dialog.showDialog("itemProcessUpdate",{
		  title:"材料加工管理--修改",
		  url:"${ctx}/admin/itemProcess/goUpdate",
		  postData:{no: ret.no},	  
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
      Global.Dialog.showDialog("itemProcessView",{
		  title:"材料加工管理--查看",
		  url:"${ctx}/admin/itemProcess/goDetail",
		  postData:{no: ret.no},	  
		  height:600,
		  width:1000
		});
    } else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}

//导出EXCEL
function doExcel(){
	var url = "${ctx}/admin/itemProcess/doExcel";
	doExcelAll(url);
}
/**初始化表格*/
$(function(){
	    // 初始化材料类型1
        Global.LinkSelect.initSelect("${ctx}/admin/item/itemTypeByAuthority", "itemType1");
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/itemProcess/goJqGrid",
			multiselect: false,
			height:$(document).height()-$("#content-list").offset().top-70,
			colModel : [
			  {name : 'no',index : 'no',width : 100,label:'加工编号',sortable : true,align : 'left',key : true},
		      {name : 'itemtype1',index : 'itemtype1',width : 100,label:'材料类型1',sortable : true,align : 'left',hidden: true},
		      {name : 'itemtype1descr',index : 'itemtype1descr',width : 100,label:'材料类型1',sortable : true,align : 'left'},
		      {name : 'processitemwhcode',index : 'processitemwhcode',width : 100,label:'加工材料仓库',sortable : true,align : 'left',hidden: true},
		      {name : 'sourceitemwhcode',index : 'sourceitemwhcode',width : 100,label:'源材料仓库',sortable : true,align : 'left',hidden: true},
		      {name : 'processitemwhdescr',index : 'processitemwhdescr',width : 100,label:'加工材料仓库',sortable : true,align : 'left'},
		      {name : 'sourceitemwhdescr',index : 'sourceitemwhdescr',width : 100,label:'源材料仓库',sortable : true,align : 'left'},
		 
		      {name : 'status',index : 'status',width : 100,label:'状态',sortable : true,align : 'left',hidden: true,hidden: true},
		      {name : 'statusdescr',index : 'statusdescr',width : 100,label:'状态',sortable : true,align : 'left'},
		      {name : 'appczy',index : 'appczy',width : 100,label:'申请',sortable : true,align : 'left',hidden: true},
		      {name : 'appczydescr',index : 'appczydescr',width : 100,label:'申请人',sortable : true,align : 'left'},
		      {name : 'appdate',index : 'appdate',width : 100,label:'申请日期',sortable : true,align : 'left', formatter: formatTime},
		      {name : 'confirmczy',index : 'confirmczy',width : 100,label:'confirmCzy',sortable : true,align : 'left',hidden: true},
		      {name : 'confirmczydescr',index : 'confirmczydescr',width : 100,label:'审核人',sortable : true,align : 'left'},
		      {name : 'confirmdate',index : 'confirmdate',width : 100,label:'审核日期',sortable : true,align : 'left'},
		      {name : 'remarks',index : 'remarks',width : 100,label:'备注',sortable : true,align : 'left'},
		      {name: 'lastupdate', index: 'lastupdate', width: 120, label: '最后更新时间', sortable: true, align: 'left', formatter: formatTime},
			  {name: 'lastupdatedby', index: 'lastupdatedby', width: 96, label:'最后更新人员', sortable: true, align: 'left'},
			  {name: 'expired', index: 'expired', width: 79, label: '是否过期', sortable: true,align:'left'},
			  {name: 'actionlog', index: 'actionlog', width: 83, label: '操作', sortable: true,align:"left"}
            ]
		});
});
</script>
</head>
    
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form action="" method="post" id="page_form" class="form-search">
				<input type="hidden" id="expired" name="expired" value="${itemProcess.expired}" />
				<input type="hidden" name="jsonString" value="" />
				<ul class="ul-form">
					<li>
						<label>材料类型1</label> 
						 <select id="itemType1" name="itemType1"></select>
					</li>
					<li class="search-group">
						<button type="button" class="btn btn-sm btn-system " onclick="goto_query();">查询</button>
						<button type="button" class="btn btn-sm btn-system " onclick="clearForm();">清空</button>
				   </li>
				</ul>
			</form>
		</div>
		<div class="clear_float"> </div>
		<!--query-form-->
		<div class="btn-panel">
			<!--panelBar-->
			<div class="btn-group-sm">
            	<ul>
					<house:authorize authCode="ITEMPROCESS_SAVE">
						<button type="button" class="btn btn-system " onclick="add()">新增</button>
					</house:authorize>
		
					<house:authorize authCode="ITEMPROCESS_UPDATE">
						<button type="button" class="btn btn-system " onclick="update()">修改</button>
					</house:authorize>
					<house:authorize authCode="ITEMPROCESS_CONFIRM">
						<button type="button" class="btn btn-system " onclick="confirm()">审核</button>
					</house:authorize>
					<house:authorize authCode="ITEMPROCESS_VIEW">
						<button type="button" class="btn btn-system " onclick="view()">查看</button>
					</house:authorize>
					<house:authorize authCode="ITEMPROCESS_EXCEL">
						<button type="button" class="btn btn-system " onclick="doExcel()">导出excel</button>
					</house:authorize>
				
				</ul>
			 </div>
		</div><!--panelBar end-->
		<div id="content-list">
			<table id= "dataTable"></table>
			<div id="dataTablePager"></div>
		</div>

</body>
</html>


