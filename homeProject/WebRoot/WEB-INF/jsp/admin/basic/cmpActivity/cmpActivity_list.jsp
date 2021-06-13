<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<title>公司活动管理</title>
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_builderGroup.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_item.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
/**初始化表格*/
$(function(){
	Global.LinkSelect.initSelect("${ctx}/admin/item/itemTypeByAuthority","itemType1","itemType2","itemType3");
	$(function(){
		$("#giftCode").openComponent_item({
			condition:{
				itemType1:"LP",
				disabledEle:"itemType1",
				isItemTran:"1",
				costRight:"0"
			}
		});
	    //初始化表格
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/cmpActivity/goJqGrid",
			styleUI: 'Bootstrap',
			height:$(document).height()-$("#content-list").offset().top-100,
			colModel : [
			  {name : 'no',index : 'no',width : 50,label:'编号',sortable : true,align : "left"},
		      {name : 'descr',index : 'descr',width : 80,label:'活动名称',sortable : true,align : "left"},
		      {name : 'begindate',index : 'begindate',width : 100,label:'活动开始时间',sortable : true,align : "left",formatter:formatDate},			      
		      {name : 'enddate',index : 'enddate',width : 100,label:'活动结束时间',sortable : true,align : "left",formatter:formatDate},
		      {name : 'remarks',index : 'remarks',width : 120,label:'备注',sortable : true,align : "left",},
		      {name : 'lastupdate',index : 'lastupdate',width : 150,label:'最后修改时间',sortable : true,align : "left",formatter: formatTime},
		      {name : 'lastupdatedby',index : 'lastupdatedby',width : 70,label:'修改人',sortable : true,align : "left"},
		      {name : 'expired',index : 'expired',width : 70,label:'是否过期',sortable : true,align : "left"},
		      {name : 'actionlog',index : 'actionlog',width : 70,label:'操作日志',sortable : true,align : "left"}
	           ]
		});
	});  
});

function add(){	
	Global.Dialog.showDialog("cmpActivity",{			
		  title:"添加公司活动信息",
		  url:"${ctx}/admin/cmpActivity/goSave",
		  height: 700,
		  width:1100,
		  returnFun: goto_query
	});
}

function update(){
	var ret = selectDataTableRow();
    if (ret) {    	
    	Global.Dialog.showDialog("cmpActivityUpdate",{
			title:"编辑公司活动信息",
		 	url:"${ctx}/admin/cmpActivity/goUpdate?id="+ret.no,
		  	height:700,
		  	width:1100,
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
        Global.Dialog.showDialog("cmpActivityView",{
			title:"查看公司活动信息",
			url:"${ctx}/admin/cmpActivity/goView?id="+ret.no,
			height:700,
			width:1100,
			returnFun: goto_query
		});
    } else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}

function actSupplier(){
	var ret = selectDataTableRow();
    if (ret) {    	
      Global.Dialog.showDialog("actSupplier",{
		  title:"活动供应商编辑",
		  url:"${ctx}/admin/cmpActivity/goActSupplier?id="+ret.no,
		  height:700,
		  width:1100,
		  returnFun: goto_query
		});
    } else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}
//导出Excel
</script>
</head>
<body>
	<div class="body-box-list">
        <div class="query-form"  >  
	        <form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
				<input type="hidden" name="exportData" id="exportData">
				<input type="hidden" id="expired" name="expired" value="${cmpactivity.expired}" />
				<input type="hidden" name="jsonString" value="" />
				<ul class="ul-form">
					<li>																		
						<label>活动编号</label>
						<input type="text" id="no" name="no"   value="${cmpactivity.no }" />
					</li>
					<li>
						<label>活动名称</label>
						<input type="text" id="descr" name="descr"   value="${cmpactivity.descr }" />
					</li>
					<li>
						<label>开始时间从</label>
						<input type="text" id="beginDate" name="beginDate" class="i-date"   onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
					</li>
					<li>
						<label>至</label>
						<input type="text" id="endDate" name="endDate" 	 class="i-date"   onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"  />
					</li>
					<li>
						<label>礼品编号</label>
						<input type="text" name="giftCode" id="giftCode">
					</li>
					<li id="loadMore" >
						<input type="checkbox" id="expired_show" name="expired_show"
								value="${cmpactivity.expired}" onclick="hideExpired(this)"
								${cmpactivity.expired!='T' ?'checked':'' }/>隐藏过期&nbsp;
						<button type="button" class="btn  btn-system btn-sm" onclick="goto_query();"  >查询</button>
						<button type="button" class="btn btn-system btn-sm" onclick="clearForm();"  >清空</button>
						
					</li>
				</ul>				
			</form>
		</div>
		<div class="clear_float"> </div>
		<!--query-form-->
		<div class="pageContent">
			<div class="btn-panel" >
		    	<div class="btn-group-sm"  >
					<house:authorize authCode="CMPACTIVITY_ADD">
						<button type="button" class="btn btn-system " onclick="add()">添加</button>
					</house:authorize>
					<house:authorize authCode="CMPACTIVITY_EDIT">
						<button type="button" class="btn btn-system "  onclick="update()">编辑</button>
					</house:authorize>
					<house:authorize authCode="CMPACTIVITY_VIEW">
						<button type="button" class="btn btn-system "  onclick="view()">查看</button>
					</house:authorize>
					<house:authorize authCode="CMPACTIVITY_SUPPLIER">
						<button type="button" class="btn btn-system "  onclick="actSupplier()">活动供应商</button>
					</house:authorize>
					<house:authorize authCode="CMPACTIVITY_EXCEL">
						<button type="button" class="btn btn-system " onclick="doExcelNow('公司活动管理')">导出excel</button>					
						</a>
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


