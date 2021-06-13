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
    <title>仓库转移</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>

	<script type="text/javascript">
	//新增
	function add(){
		Global.Dialog.showDialog("Add",{
		  title:"仓库转移-新增",
		  url:"${ctx}/admin/itemTransferHeader/goSave",
		  height: 600,
		  width:1000,
		  returnFun: goto_query 
		});
	}
	
	//编辑
	function update(){
		var ret = selectDataTableRow();
	    if (ret) {
	    	if($.trim(ret.status)!="1"){
	    		art.dialog({
	    			content:"该转移单不在申请状态，无法进行编辑操作！",
	    		});
	    		return;
	    	}
			Global.Dialog.showDialog("Update",{
				title:"仓库转移-编辑",
				url:"${ctx}/admin/itemTransferHeader/goUpdate?no="+ret.no,
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
	
	//审核
	function check(){
		var ret = selectDataTableRow();
		if (ret) {
			if($.trim(ret.status)!="1"){
	    		art.dialog({
	    			content:"该转移单不在申请状态，无法进行审核操作！",
	    		});
	    		return;
	    	}
	      Global.Dialog.showDialog("check",{
			  title:"仓库转移-审核",
			  url:"${ctx}/admin/itemTransferHeader/goCheck?no="+ret.no,
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
			  title:"仓库转移-查看",
			  url:"${ctx}/admin/itemTransferHeader/goView?no="+ret.no,
			  height:720,
			  width:1000
			});
	    } else {
	    	art.dialog({
				content: "请选择一条记录"
			});
	    }
	}
	
	function doExcel(){
		var url = "${ctx}/admin/itemTransferHeader/doExcel";
		doExcelAll(url);
	}
	//打印
	function print(){
		var ret = selectDataTableRow();
	   	var reportName = "ItemTransferHeader.jasper";
	   	Global.Print.showPrint(reportName, {
	   		no:ret.no,
			LogoFile : "<%=basePath%>jasperlogo/logo.jpg",
			SUBREPORT_DIR: "<%=jasperPath%>" 
		});
	}
	//清空
	function clearForm(){
		$("#page_form input[type='text']").val("");
		$("#status").val("");
		$.fn.zTree.getZTreeObj("tree_status").checkAllNodes(false);
	} 
	/**初始化表格*/
	$(function(){
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/itemTransferHeader/goJqGrid",
			height:$(document).height()-$("#content-list").offset().top-70,
			styleUI: "Bootstrap", 
			colModel : [
				{name: "no", index: "no", width: 100, label: "转移编号", sortable: true, align: "left"},
				{name: "status", index: "status", width: 100, label: "状态编号", sortable: true, align: "left",hidden:true},
				{name: "date", index: "date", width: 110, label: "转移日期", sortable: true, align: "left", formatter: formatDate},
				{name: "usegitdescr", index: "usegitdescr", width: 105, label: "是否使用暂存仓", sortable: true, align: "left"},
				{name: "fromwhdescr", index: "fromwhdescr", width: 120, label: "源仓库名称", sortable: true, align: "left"},
				{name: "towhdescr", index: "towhdescr", width: 120, label: "目的仓库名称", sortable: true, align: "left"},
				{name: "isgitdealdescr", index: "isgitdealdescr", width: 110, label: "暂存仓处理标志", sortable: true, align: "left", hidden: true},
				{name: "dealtypedescr", index: "dealtypedescr", width: 110, label: "暂存仓处理类型", sortable: true, align: "left", hidden: true},
				{name: "dealdate", index: "dealdate", width: 120, label: "处理时间", sortable: true, align: "left", formatter: formatTime, hidden: true},
				{name: "statusdescr", index: "statusdescr", width: 65, label: "状态", sortable: true, align: "left"},
				{name: "documentno", index: "documentno", width: 95, label: "凭证号", sortable: true, align: "left"},
				{name: "appempdescr", index: "appempdescr", width: 90, label: "申请人", sortable: true, align: "left"},
				{name: "appdate", index: "appdate", width:150, label: "申请日期", sortable: true, align: "left", formatter: formatTime},
				{name: "confirmempdescr", index: "confirmempdescr", width: 90, label: "审核人", sortable: true, align: "left"},
				{name: "confirmdate", index: "confirmdate", width: 150, label: "审核日期", sortable: true, align: "left", formatter: formatTime},
				{name: "remarks", index: "remarks", width: 130, label: "备注", sortable: true, align: "left"},
				{name: "lastupdate", index: "lastupdate", width: 150, label: "最后更新日期", sortable: true, align: "left", formatter: formatTime},
				{name: "lastupdatedby", index: "lastupdatedby", width: 100, label: "最后更新人员", sortable: true, align: "left"},
				{name: "expired", index: "expired", width: 80, label: "是否过期", sortable: true, align: "left"},
				{name: "actionlog", index: "actionlog", width: 80, label: "操作日志", sortable: true, align: "left"}
			],
			ondblClickRow: function(){
				view();
			}
		});
	});
	</script>
</head>
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
				<input type="hidden" name="jsonString" value=""/>
				<ul class="ul-form">
					<li>
						<label>仓库转移流水号</label>
						<input type="text" id="no" name="no" style="width:160px;" value="${itemBalAdjHeader.no }"/>
					</li>
					<li>
						<label>状态</label>
						<house:xtdmMulit id="status" dictCode="BALADJSTATUS" selectedValue="1,2,3"></house:xtdmMulit>    
					</li>
					<li>
						<button type="button" class="btn btn-sm btn-system" onclick="goto_query();">查询</button>
						<button type="button" class="btn btn-sm btn-system" onclick="clearForm();">清空</button>
					</li>
				</ul>	
			</form>
		</div>
		<div class="pageContent">
			<div class="btn-panel">
    			<div class="btn-group-sm">
					<house:authorize authCode="ITEMTRANSFERHEADER_SAVE">
						<button type="button" class="btn btn-system" onclick="add()">
							<span>新增</span>
						</button>
	                </house:authorize>
	                <house:authorize authCode="ITEMTRANSFERHEADER_UPDATE">
						<button type="button" class="btn btn-system" onclick="update()">
							<span>编辑</span>
						</button>
					</house:authorize>
				    <house:authorize authCode="ITEMTRANSFERHEADER_CHECK">
						<button type="button" class="btn btn-system" onclick="check()">
							<span>审核</span>
						</button>
					</house:authorize>
					<house:authorize authCode="ITEMTRANSFERHEADER_VIEW">
						<button type="button" class="btn btn-system" onclick="view()">
							<span>查看</span>
						</button>
	                </house:authorize>
					<button type="button" class="btn btn-system" onclick="doExcel()" title="导出检索条件数据">
						<span>输出到excel</span>
					</button>
					<house:authorize authCode="ITEMTRANSFERHEADER_PRINT">
	                    <button type="button" class="btn btn-system" onclick="print()">
							<span>打印</span>
						</button>
	                </house:authorize>
                </div>
			</div> 
			<div id="content-list">
				<table id="dataTable"></table>
				<div id="dataTablePager"></div>
			</div>
		</div> 
	</div>
</body>
</html>
