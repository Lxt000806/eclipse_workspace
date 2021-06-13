<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<!DOCTYPE HTML>
<html>
<head>
	<title>搜寻——周期编号</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
<script type="text/javascript"> 
$(function(){
	Global.LinkSelect.initSelect("${ctx}/admin/item/itemTypeByAuthority","itemType1");
	Global.LinkSelect.setSelect({firstSelect:'itemType1',
							firstValue:'${laborFeeType.itemType1 }'
							});
        //初始化表格
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/laborFeeType/goJqGrid",
            styleUI: 'Bootstrap',   
            postData:{itemType1:'${laborFeeType.itemType1 }'},
			height:$(document).height()-$("#content-list").offset().top-70,
			colModel : [
			  	{name: "code", index: "code", width: 70, label: "编号", sortable: true, align: "left"},
			  	{name: "itemtype1", index: "itemtype1", width: 100, label: "材料类型1", sortable: true, align: "left",hidden:true},
			  	{name: "itemtype1descr", index: "itemtype1descr", width: 100, label: "材料类型1", sortable: true, align: "left"},
				{name: "descr", index: "descr", width: 130, label: "费用名称", sortable: true, align: "left"},
				{name: "iscalcost", index: "iscalcost", width: 70, label: "是否计入成本", sortable: true, align: "left",hidden:true},
				{name: "iscalcostdescr", index: "iscalcostdescr", width: 100, label: "是否计入成本", sortable: true, align: "left"},
				{name: "ishavesendno", index: "ishavesendno", width: 70, label: "是否填发单编号", sortable: true, align: "left",hidden:true},
				{name: "ishavesendnodescr", index: "ishavesendnodescr", width: 120, label: "是否填发单编号", sortable: true, align: "left"},
				{name: "itemtype12", index: "itemtype12", width: 70, label: "材料类型12", sortable: true, align: "left",hidden:true},
				{name: "itemtype12descr", index: "itemtype12descr", width: 100, label: "材料类型12", sortable: true, align: "left"},
				{name: "lastupdate", index: "lastupdate", width: 140, label: "最后更新时间", sortable: true, align: "left", formatter: formatTime},
				{name: "lastupdatedby", index: "lastupdatedby", width: 90, label: "最后更新人员", sortable: true, align: "left"},
				{name: "expired", index: "expired", width: 80, label: "是否过期", sortable: true, align: "left"},
				{name: "actionlog", index: "actionlog", width: 80, label: "操作日志", sortable: true, align: "left"}
            ],
             ondblClickRow:function(rowid,iRow,iCol,e){
				var selectRow = $("#dataTable").jqGrid('getRowData', rowid);
            	Global.Dialog.returnData = selectRow;
            	Global.Dialog.closeDialog();
            }
		});	
		if('${laborFeeType.itemType1 }'!=""){
			$("#itemType1").attr("disabled",true);
		}
});

</script>
</head>
	<body >
		<div class="body-box-list">
			<div class="query-form">
				<form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
	        	<input type="hidden" id="expired" name="expired" value="${laborFeeType.expired }"/>
				<input type="hidden" name="exportData" id="exportData">
				<input type="hidden" name="jsonString" value="" />
				<ul class="ul-form">
					<li>
						<label>编号</label>
						<input type="text" id="code" name="code" style="width:160px;" value="${laborFeeType.code}"/>
					</li>
					<li>
						<label>费用名称</label>
						<input type="text" id=descr name="descr" style="width:160px;" value="${laborFeeType.descr}"/>
					</li>
					<li>
						<label>材料类型1</label>
						<select id="itemType1" name="itemType1"></select>
					</li>
					<li >
						<input type="checkbox" id="expired_show" name="expired_show" value="${laborFeeType.expired }" onclick="hideExpired(this)" ${laborFeeType.expired!='T' ?'checked':'' }/>
						<label for="expired_show" style="margin-left: -3px;width: 50px;">隐藏过期</label>																	
						<button type="button" class="btn  btn-system btn-sm" onclick="goto_query();"  >查询</button>
						<button type="button" class="btn btn-system btn-sm" onclick="clearForm();"  >清空</button>
					</li>
				</ul>
			</form>
			</div>
			<!--query-form-->
			<div class="pageContent">
				<div id="content-list">
					<table id= "dataTable"></table>
					<div id="dataTablePager"></div>
				</div> 
			</div>
		</div>
	</body>	
</html>
