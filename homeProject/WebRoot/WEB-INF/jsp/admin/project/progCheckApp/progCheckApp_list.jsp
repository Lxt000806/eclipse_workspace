<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>ProgCheckApp列表</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>

<script type="text/javascript">
function add(){
	Global.Dialog.showDialog("progCheckAppAdd",{
		  title:"添加巡检申请",
		  url:"${ctx}/admin/progCheckApp/goSave",
		  height: 600,
		  width:1000,
		  returnFun: goto_query
		});
}

function update(){
	var ret = selectDataTableRow();
    if (ret) {
      Global.Dialog.showDialog("progCheckAppUpdate",{
		  title:"修改巡检申请",
		  url:"${ctx}/admin/progCheckApp/goUpdate?id="+ret.pk,
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
      Global.Dialog.showDialog("progCheckAppView",{
		  title:"查看巡检申请",
		  url:"${ctx}/admin/progCheckApp/goView?id="+ret.pk,
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
	var url = "${ctx}/admin/progCheckApp/doDelete";
	beforeDel(url);
}
//导出EXCEL
function doExcel(){
	var url = "${ctx}/admin/progCheckApp/doExcel";
	doExcelAll(url);
}
/**初始化表格*/
$(function(){
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/progCheckApp/goJqGrid",
			multiselect: true,
			height:$(document).height()-$("#content-list").offset().top-80,
			styleUI: 'Bootstrap', 
			colModel : [
			  {name : 'pk',index : 'pk',width : 60,label:'pk',sortable : true,align : "left",hidden: true},
			  {name : 'address',index : 'address',width : 200,label:'楼盘',sortable : true,align : "left"},
		      {name : 'projectmandescr',index : 'projectmandescr',width : 70,label:'项目经理',sortable : true,align : "left"},
		      {name : 'appczydescr',index : 'appczydescr',width : 70,label:'申请人',sortable : true,align : "left"},
		      {name : 'appdate',index : 'appdate',width : 90,label:'申请日期',sortable : true,align : "left",formatter:formatDate},
		      {name : 'remarks',index : 'remarks',width : 200,label:'说明',sortable : true,align : "left"},
		      {name : 'checkno',index : 'checkno',width : 100,label:'巡检单号',sortable : true,align : "left"},
		      {name : 'xjczydescr',index : 'xjczydescr',width : 70,label:'巡检人',sortable : true,align : "left"},
		      {name : 'statusdescr',index : 'statusdescr',width : 70,label:'巡检状态',sortable : true,align : "left"},
		      {name : 'expired',index : 'expired',width : 70,label:'过期标志',sortable : true,align : "left"}
            ]
		});
});
</script>
</head>
    
<body>
	<div class="body-box-list">
		<div class="query-form">
			  <form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
				<input type="hidden" id="expired" name="expired" value="${progCheckApp.expired}" />
				<input type="hidden" name="jsonString" value="" />
					<ul class="ul-form">
						<li>
							<label>楼盘</label>
							<input type="text" id="address" name="address" style="width:160px;"  value="${progCheckApp.address}" />
						</li>
						<li>
							<label>申请人</label>
							<input type="text" id="appCzyDescr" name="appCzyDescr" style="width:160px;"  value="${progCheckApp.appCzyDescr}" />
						</li>
						<li>
							<label>申请日期从</label>
							<input type="text" style="width:160px;" id="dateFrom" name="dateFrom" class="i-date" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${progCheckApp.dateFrom }' pattern='yyyy-MM-dd'/>"/>
						</li>
						<li>
							<label>至</label>
							<input type="text" style="width:160px;" id="dateTo" name="dateTo" class="i-date" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${progCheckApp.dateTo }' pattern='yyyy-MM-dd'/>"/>
						</li>
						<li class="search-group">
							<input type="checkbox" class="btn btn-sm btn-system " id="expired_show" name="expired_show" value="${progCheckApp.expired}" onclick="hideExpired(this)" ${progCheckApp.expired!='T'?'checked':'' }/><p>隐藏过期</p>
							<button type="button" class="btn  btn-sm btn-system " onclick="goto_query();"  >查询</button>
							<button type="button" class="btn btn-sm btn-system " onclick="clearForm();"  >清空</button>
						</li>
					</ul>		
			</form>
		</div>
		<div class="clear_float"> </div>
		<!--query-form-->
		<div class="pageContent">
			<!--panelBar-->
			<div class="btn-panel" >
    			  <div class="btn-group-sm"  >
            	<house:authorize authCode="PROGCHECKAPP_ADD">
                    	<button type="button" class="btn btn-system " onclick="add()">
					       <span>新增</span> </button>
                </house:authorize>
				<house:authorize authCode="PROGCHECKAPP_UPDATE">
                    	<button type="button" class="btn btn-system " onclick="update()">
					       <span>编辑</span> </button>
                </house:authorize>
                <house:authorize authCode="PROGCHECKAPP_DELETE">
						<button type="button" class="btn btn-system " onclick="del()">
							<span>删除</span> </button>
                 </house:authorize>
				 <house:authorize authCode="PROGCHECKAPP_VIEW">
                    	<button type="button" class="btn btn-system " onclick="view()">
					       <span>查看</span>
					       </button>
                </house:authorize>
						<button type="button" class="btn btn-system " onclick="doExcel()" title="导出检索条件数据">
							<span>导出excel</span> </button>
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


