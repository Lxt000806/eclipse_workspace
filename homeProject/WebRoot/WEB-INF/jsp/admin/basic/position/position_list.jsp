<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<title>职位列表</title>
	<%@ include file="/commons/jsp/common.jsp" %>

<script type="text/javascript">
function add(){
	Global.Dialog.showDialog("positionAdd",{
		  title:"添加职位",
		  url:"${ctx}/admin/position/goSave",
		  height: 320,
		  width:400,
		  returnFun: goto_query
		});
}

function update(){
	var ret = selectDataTableRow();
    if (ret) {
      Global.Dialog.showDialog("positionUpdate",{
		  title:"修改职位",
		  url:"${ctx}/admin/position/goUpdate?id="+ret.code,
		  height:320,
		  width:400,
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
      Global.Dialog.showDialog("positionCopy",{
		  title:"复制职位",
		  url:"${ctx}/admin/position/goSave?id="+ret.code,
		  height:320,
		  width:400,
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
      Global.Dialog.showDialog("positionView",{
		  title:"查看职位",
		  url:"${ctx}/admin/position/goDetail?id="+ret.code,
		  height:300,
		  width:400
		});
    } else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}

function del(){
	var url = "${ctx}/admin/position/doDelete";
	beforeDel(url);
}
//导出EXCEL
function doExcel(){
	var url = "${ctx}/admin/position/doExcel";
    doExcelAll(url);
}
/**初始化表格*/
$(function(){
        //初始化表格
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/position/goJqGrid",
			height:$(document).height()-$("#content-list").offset().top-100,
			styleUI:"Bootstrap",
			colModel : [
			  {name : 'code',index : 'code',width : 100,label:'职位编号',sortable : true,align : "left"},
		      {name : 'desc2',index : 'desc2',width : 100,label:'职位名称',sortable : true,align : "left"},
		      {name : 'typedescr',index : 'type',width : 100,label:'类型',sortable : true,align : "left"},
		      {name : 'issigndescr',index : 'issigndescr',width : 80,label:'是否监理',sortable : true,align : "left"},
		      {name : 'lastupdate',index : 'lastupdate',width : 150,label:'最后修改时间',sortable : true,align : "left", formatter:formatTime},
		      {name : 'lastupdatedby',index : 'lastupdatedby',width : 100,label:'修改人',sortable : true,align : "left"},
		      {name : 'expired',index : 'expired',width : 100,label:'过期标志',sortable : true,align : "left"},
		      {name : 'actionlog',index : 'actionlog',width : 100,label:'操作日志',sortable : true,align : "left"}
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
                <input type="hidden" id="expired" name="expired" value="F" />
                <ul class="ul-form">
                    <li>
                        <label>职位编号</label>
                        <input type="text" id="code" name="code" style="width:160px;"  value="${position.code}" />
                    </li>
                     <li>
                        <label>职位名称</label>
                        <input type="text" id="desc2" name="desc2" style="width:160px;"  value="${position.desc2}" />
                    </li>
                    <li>
                        <label>类型</label>
                        <house:dict id="type" dictCode=""
                                    sql="select cbm, note from tXTDM where id = 'POSTIONTYPE'"
                                    sqlLableKey="note" sqlValueKey="cbm" unShowValue="0" headerLabel="请选择" />
                    </li>
                    <li class="search-group">
                        <input type="checkbox" onclick="hideExpired(this)" checked="checked" />
                        <p>隐藏过期</p>
                        <button type="button" class="btn  btn-sm btn-system "
                            onclick="goto_query();">查询</button>
                        <button type="button" class="btn btn-sm btn-system "
                            onclick="clearForm();">清空</button></li>
                </ul>
            </form>
		</div>
		<!--query-form-->
		<div class="pageContent">
			<!--panelBar-->
			<div class="btn-panel">
                <div class="btn-group-sm">
                	<button type="button" class="btn btn-system " onclick="add()">新增</button>
                	<button type="button" class="btn btn-system " onclick="update()">编辑</button>
                	<button type="button" class="btn btn-system " onclick="copy()">复制</button>
	                <house:authorize authCode="POSITION_VIEW">
	                	<button type="button" class="btn btn-system " onclick="view()">查看</button>	
	                </house:authorize>
	                <button type="button" class="btn btn-system " onclick="doExcel()">导出excel</button>
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


