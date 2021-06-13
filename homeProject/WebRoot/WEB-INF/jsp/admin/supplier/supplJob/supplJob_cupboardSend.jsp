<%@ page language="java" import="com.house.framework.commons.utils.PathUtil" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://" + request.getServerName() + ":"+request.getServerPort()+path+"/";
String jasperPath = PathUtil.WEB_INF+"jsp/jasper/";
%>
<!DOCTYPE html>
<html>
<head>
	<title>supplJob列表</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
<script type="text/javascript">
$(function(){
        //初始化表格
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/supplJob/goCupboardJqGrid",
			height:$(document).height()-$("#content-list").offset().top-100,
			postData:{status:"2,3"},
			styleUI: 'Bootstrap',
			colModel : [
		      {name: 'address', index: 'address', width: 160, label: '楼盘', sortable: true, align: "left"},
		      {name: 'jobtypedescr', index: 'jobtypedescr', width: 100, label: '任务类型', sortable: true, align: "left"},
		      {name: 'note', index: 'note', width: 60, label: '状态', sortable: true, align: "left"},
		      {name: 'date', index: 'date', width: 125, label: '申请日期', sortable: true, align: "left", formatter: formatTime},
		      {name: 'recvdate', index: 'recvdate', width: 125, label: '接收时间', sortable: true, align: "left", formatter: formatTime},
		      {name: 'completedate', index: 'completedate', width: 125, label: '完成时间', sortable: true, align: "left", formatter: formatTime},
            ]
		});
	});
	function clearForm(){
		$("#page_form input[type='text']").val('');
		$("#page_form select").val('');
		$("#status").val('');
		$.fn.zTree.getZTreeObj("tree_status").checkAllNodes(false);
		$("#splStatus").val('');
		$.fn.zTree.getZTreeObj("tree_splStatus").checkAllNodes(false);
	}
	function doExcel(){                      
			var url = "${ctx}/admin/supplJob/doCupBoardExcel";
			doExcelAll(url);
		}
</script>
</head>
    
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
			<input type="hidden" name="jsonString" value="" />
				<ul class="ul-form">
					<li><label>状态</label> <house:xtdmMulit id="status"
							dictCode="SUPPLJOBSTS" selectedValue="2,3" unShowValue="0,1"></house:xtdmMulit>
					</li>
					<li id="loadMore" >
					<button type="button" class="btn btn-system btn-sm" onclick="goto_query();" >查询</button>
					<button type="button" class="btn btn-system btn-sm" onclick="clearForm();" >清空</button>
					</li>
				</ul>
			</form>
		</div>

		<div class="btn-panel">
			<div class="btn-group-sm">
                  <house:authorize authCode="SUPPLJOB_CUPBOARDSEND">
                 <button type="button" class="btn btn-system " id="excel" onclick="doExcel()">导出Excel</button>
                </house:authorize>
			</div>
		</div>
		<div id="content-list">
			<table id= "dataTable"></table> 
			<div id="dataTablePager"></div>
		</div> 
	</div>
</body>
</html>


