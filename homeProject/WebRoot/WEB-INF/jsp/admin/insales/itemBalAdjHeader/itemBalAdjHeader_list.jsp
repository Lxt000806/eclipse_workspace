<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://" + request.getServerName() + ":"+request.getServerPort()+path+"/";
String jasperPath = PathUtil.WEB_INF+"jsp/jasper/";
%>

<!DOCTYPE HTML>
<html>
<head>
    <title>仓库调整</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>

<script type="text/javascript">
//新增
function add(){
	Global.Dialog.showDialog("itemBalAdjHeaderAdd",{
		  title:"仓库调整-新增",
		  url:"${ctx}/admin/itemBalAdjHeader/goSave",
		  height: 600,
		  width:1000,
		  returnFun: goto_query 
		});
}

//编辑
function update(){
	var ret = selectDataTableRow();
    if (ret) {
      if(ret.statusdescr=='申请'){
	      Global.Dialog.showDialog("itemBalAdjHeaderUpdate",{
			  title:"仓库调整-编辑",
			  url:"${ctx}/admin/itemBalAdjHeader/goUpdate?id="+ret.no,
			  height:700,
			  width:1000,
			  returnFun: goto_query
			});
		}else{
    	art.dialog({
			content: "该仓库调整已审核或取消,不能编辑！"
		});
    	}
	} else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}

//审核
function verify(){
	var ret = selectDataTableRow();
    if (ret) {
    	if(ret.statusdescr.trim()!='申请'){
    	art.dialog({
			content: "该仓库调整已审核或取消,不能审核！"
		});
		return;
    	}
    	
      Global.Dialog.showDialog("itemBalAdjHeaderVerify",{
		  title:"仓库调整-审核",
		  url:"${ctx}/admin/itemBalAdjHeader/goVerify?id="+ret.no,
		  height:700,
		  width:1000,
		  returnFun: goto_query
		});
    } else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}

//查看
function view(){
   
	var ret = selectDataTableRow();
    if (ret) {
      Global.Dialog.showDialog("itemBalAdjHeaderView",{
		  title:"仓库调整-查看",
		  url:"${ctx}/admin/itemBalAdjHeader/goDetail?id="+ret.no,
		  height:720,
		  width:1000
		});
    } else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}
function detailQuery(){
	var ret = selectDataTableRow();
    if (ret) {
      Global.Dialog.showDialog("itemBalAdjHeaderDetailQuery",{
		  title:"仓库调整-明细查询",
		  url:"${ctx}/admin/itemBalAdjHeader/goDetailQuery?id="+ret.no,
		  height:720,
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
	var url = "${ctx}/admin/itemBalAdjHeader/doExcel";
	doExcelAll(url);
}

//打印
function print(){
	var ret = selectDataTableRow();
   	var reportName = "itemBalAdjHeaderPrint.jasper";
   	Global.Print.showPrint(reportName, {
   		No:ret.no,
		LogoFile : "<%=basePath%>jasperlogo/logo.jpg",
		SUBREPORT_DIR: "<%=jasperPath%>" 
	});
}

//清空
function clearForm(){
	$("#page_form input[type='text']").val('');
	$("#status").val('');
	$.fn.zTree.getZTreeObj("tree_status").checkAllNodes(false);
} 
/**初始化表格*/
$(function(){
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/itemBalAdjHeader/goJqGrid",
			height:$(document).height()-$("#content-list").offset().top-70,
			styleUI: 'Bootstrap', 
			colModel : [
				{name: "no", index: "no", width: 95, label: "仓库调整编号", sortable: true, align: "left"},
				{name: "whcode", index: "whcode", width: 80, label: "仓库编号", sortable: true, align: "left"},
				{name: "date", index: "date", width: 80, label: "调整日期", sortable: true, align: "left", formatter: formatDate},
				{name: "adjtypedescr", index: "adjtypedescr", width: 80, label: "调整类型", sortable: true, align: "left"},
				{name: "adjreasondescr", index: "adjreasondescr", width: 80, label: "调整原因", sortable: true, align: "left"},
				{name: "statusdescr", index: "statusdescr", width: 45, label: "状态", sortable: true, align: "left"},
				{name: "documentno", index: "documentno", width: 80, label: "凭证号", sortable: true, align: "left"},
				{name: "appempdescr", index: "appempdescr", width: 70, label: "申请人", sortable: true, align: "left"},
				{name: "appdate", index: "appdate", width: 80, label: "申请日期", sortable: true, align: "left", formatter: formatDate},
				{name: "confirmempdescr", index: "confirmempdescr", width: 70, label: "审核人", sortable: true, align: "left"},
				{name: "confirmdate", index: "confirmdate", width: 80, label: "审核日期", sortable: true, align: "left", formatter: formatDate},
				{name: "remarks", index: "remarks", width: 180, label: "备注", sortable: true, align: "left"},
				{name: "lastupdate", index: "lastupdate", width: 140, label: "上次更改时间", sortable: true, align: "left", formatter: formatTime},
				{name: "lastupdatedby", index: "lastupdatedby", width: 80, label: "上次更改人员", sortable: true, align: "left"},
				{name: "expired", index: "expired", width: 70, label: "过期标志", sortable: true, align: "left"},
				{name: "actionlog", index: "actionlog", width: 70, label: "操作日志", sortable: true, align: "left"}
            ]
		});
});
</script>
	
</head>
  
<body>
	<div class="body-box-list">
		<div class="query-form">
			  <form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
				<input type="hidden" name="jsonString" value="" />
						<ul class="ul-form">
							<li>
								<label>仓库调整编号</label>
								<input type="text" id="no" name="no" style="width:160px;" value="${itemBalAdjHeader.no }"/>
							</li>
							<li>
								<label for="documentNo">凭证号</label>
								<input type="text" id="documentNo" name="documentNo">
							</li>
							<li>
								<label>状态</label>
								<house:xtdmMulit id="status" dictCode="BALADJSTATUS" selectedValue="${itemBalAdjHeader.status}"></house:xtdmMulit>    
							</li>
							<li>
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
            	<house:authorize authCode="itemBalAdjHeader_SAVE">
                    	<button type="button" class="btn btn-system " onclick="add()">
					       <span>新增</span>
                </house:authorize>
                <house:authorize authCode="itemBalAdjHeader_UPDATE">
					<button type="button" class="btn btn-system " onclick="update()">
						<span>编辑</span>
				</house:authorize>
			    <house:authorize authCode="itemBalAdjHeader_VERIFY">
					<button type="button" class="btn btn-system " onclick="verify()">
						<span>审核</span>
				</house:authorize>
				<house:authorize authCode="itemBalAdjHeader_VIEW">
                    	<button type="button" class="btn btn-system " onclick="view()">
					       <span>查看</span>
                </house:authorize>
                    	<button type="button" class="btn btn-system " onclick="detailQuery()">
					       <span>明细查询</span>
                <house:authorize authCode="itemBalAdjHeader_EXCEL">
						<button type="button" class="btn btn-system " onclick="doExcel()" title="导出检索条件数据">
							<span>输出到excel</span>
                </house:authorize>
				<house:authorize authCode="itemBalAdjHeader_PRINT">
                    	<button type="button" class="btn btn-system " onclick="print()">
					       <span>打印</span>
                </house:authorize>
                </div>
			</div><!--panelBar end-->
			<div id="content-list">
				<table id= "dataTable"></table>
				<div id="dataTablePager"></div>
			</div>
		</div> 
	</div>
</body>
</html>
