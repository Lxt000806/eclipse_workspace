<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<!DOCTYPE html>
<html>
<head>
<title>基础备份编号编号</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp"%>
<script type="text/javascript">
	function clearForm(){
			$("#page_form input[type='text']").val("");		
	}	
	$(function(){
		var postData = $("#page_form").jsonForm();
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/itemPlanBak/goJqGrid",
			postData:postData,
			height:$(document).height()-$("#content-list").offset().top-82,
			styleUI: 'Bootstrap', 
			colModel : [
				{name: "no", index: "no", width: 75, label: "编号", sortable: true, align: "left"},
				{name: "remark", index: "remark", width: 170, label: "备注", sortable: true, align: "left"},
				{name :"maintempno",index : "maintempno",width : 100,label:'模板',sortable : true,align : "left"},
				{name :"maintempdescr",index : "maintempdescr",width : 100,label:'模板名称',sortable : true,align : "left"},
				{name: "lastupdate", index: "lastupdate", width: 120, label: "最后更新时间", sortable: true, align: "left", formatter: formatTime},
				{name: "lastupdatedby", index: "lastupdatedby", width: 95, label: "最后更新人员", sortable: true, align: "left"},
				{name: "expired", index: "expired", width: 80, label: "过期标志", sortable: true, align: "left"},
				{name: "actionlog", index: "actionlog", width: 80, label: "操作日志", sortable: true, align: "left"}
			],
			ondblClickRow:function(rowid,iRow,iCol,e){
				var selectRow = $("#dataTable").jqGrid('getRowData', rowid);
	        	Global.Dialog.returnData = selectRow;
	        		Global.Dialog.closeDialog();
        	}
		});
	});
	
</script>
</head>
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
				<input type="hidden" name="jsonString" value="" />
				<input type="hidden" id="custCode" name="custCode"  value="${itemPlanBak.custCode}" />
				<input type="hidden" id="custType" name="custType"  value="${itemPlanBak.custType}" />
				<input type="hidden" id="itemType1" name="itemType1"  value="${itemPlanBak.itemType1}" />
				<ul class="ul-form">
					<li class="form-validate"><label>编码</label> <input type="text" id="no" name="no"
						style="width:160px;" value="${baseItemPlanBak.no}" />
					</li>
					<li class="form-validate"><label>备注</label> <input type="text" id="remark" name="remark"
						style="width:160px;" value="${baseItemPlanBak.remark}" />
					</li>
					<li class="search-group"><input type="checkbox" id="expired" name="expired"
						value="${baseItemPlanBak.expired}" onclick="hideExpired(this)" ${prePlanTemp.expired!='T' ?'checked':'' }/>
						<p>隐藏过期</p>
						<button type="button" class="btn  btn-sm btn-system " onclick="goto_query();">查询</button>
						<button type="button" class="btn btn-sm btn-system " onclick="clearForm();">清空</button>
					</li>
				</ul>
			</form>
		</div>
	</div>
	<div class="clear_float"></div>
	<div id="content-list">
		<table id="dataTable"></table>
		<div id="dataTablePager"></div>
	</div>
</body>
</html>
