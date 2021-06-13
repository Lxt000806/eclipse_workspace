<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<title>材料应下单楼盘明细</title>
<%@ include file="/commons/jsp/common.jsp" %>
<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
<script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript"> 
function goto_query(){
	$("#dataTable").jqGrid("setGridParam",{url:"${ctx}/admin/itemShouldOrder/goJqGrid",datatype:'json',postData:$("#page_form").jsonForm(),page:1,fromServer: true}).trigger("reloadGrid");
}				
/**初始化表格*/
$(function(){
	$("#builderCode").openComponent_builder();
	$("#mainBusinessMan").openComponent_employee();
	Global.LinkSelect.initSelect("${ctx}/admin/item/itemTypeByAuthority","itemType1");
	var postData=$("#page_form").jsonForm();
    //初始化表格
	Global.JqGrid.initJqGrid("dataTable",{
		postData: postData,
		height:$(document).height()-$("#content-list").offset().top-90,
		colModel : [
			{name: "custcode", index: "custcode", width: 70, label: "客户编号", sortable: true, align: "left"},
			{name: "address", index: "address", width: 150, label: "楼盘", sortable: true, align: "left"},
			{name: "custtypedescr", index: "custtypedescr", width: 100, label: "客户类型", sortable: true, align: "left"},
			{name: "projectmandescr", index: "projectmandescr", width: 100, label: "项目经理", sortable: true, align: "left"},
			{name: "prjdeptdescr", index: "prjdeptdescr", width: 100, label: "工程部", sortable: true, align: "left"},
			{name: "zcgjnamechi", index: "zcgjnamechi", width: 80, label: "主材管家", sortable: true, align: "left"},
			{name: "confitemtype", index: "confitemtype", width: 80, label: "施工材料分类", sortable: true, align: "left",hidden:true},
		 	{name: "descr", index: "descr", width: 100, label: "施工材料分类", sortable: true, align: "left"},
			{name: "shouldorderdate", index: "shouldorderdate", width: 90, label: "应下单时间", sortable: true, align: "left",formatter: formatDate},
			{name: "overdays", index: "overdays", width: 90, label: "超期天数", sortable: true, align: "left",},
			{name: "isconfirmed", index: "isconfirmed", width: 90, label: "材料是否确认", sortable: true, align: "left"},
			{name: "confirmremarks", index: "confirmremarks", width: 150, label: "材料确认说明", sortable: true, align: "left"},
			{name: "confirmdate", index: "confirmdate", width: 120, label: "材料确认时间", sortable: true, align: "left",formatter: formatTime},
			{name: "shouldordernode", index: "shouldordernode", width: 80, label: "应下单节点", sortable: true, align: "left"},
			{name: "nodedatetype", index: "nodedatetype", width: 70, label: "节点类型", sortable: true, align: "left"},
			{name: "nodetriggerdate", index: "nodetriggerdate", width: 90, label: "节点触发时间", sortable: true, align: "left",formatter: formatDate},
	   		{name: "paytype", index: "paytype", width: 80, label: "付款类型", sortable: true, align: "left"},
	   		{name: "nowNum", index: "nowNum", width: 90, label: "本期付款期数", sortable: true, align: "right"},
			{name: "nowShouldPay", index: "nowShouldPay", width: 90, label: "本期应付余额", sortable: true, align: "right"},
	    ],    
	});
});

function view(){
	var ret = selectDataTableRow();
    if (ret) {
      Global.Dialog.showDialog("detail",{
		  title:"查看明细",
		  url:"${ctx}/admin/itemShouldOrder/goDetail",
		  postData:{
		  	code:ret.custcode,
		  	confItemType:ret.confitemtype
		  },
		  height:700,
		  width:1000
		});
    } else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}
function doExcel(){
	var url = "${ctx}/admin/itemShouldOrder/doExcel";
	doExcelAll(url);
}
</script>
</head>
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form action="" method="post" id="page_form" class="form-search">
				<input type="hidden" name="jsonString" value="" />
				<ul class="ul-form">
					<li>
						<label>片区</label>
						<house:DictMulitSelect id="region" dictCode="" sql="SELECT Code,Descr FROM tRegion WHERE 1=1 and expired='F' " sqlLableKey="Descr" sqlValueKey="Code" selectedValue="${prjConfirmApp.prjItem }"></house:DictMulitSelect>
					</li>
					<li>
						<label>项目名称</label>
						<input type="text" id="builderCode" name="builderCode" style="width:160px;" value="${customer.builderCode }" />
					</li>
					<li>
						<label>楼盘</label>
						<input type="text" id="address" name="address" style="width:160px;" value="${customer.address }" />
					</li>
					<li>
						<label>触发时间从</label> 
						<input type="text" id="endDateFrom" name="endDateFrom" class="i-date"
							onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
							value="<fmt:formatDate value="${customer.endDateFrom}" pattern='yyyy-MM-dd'/>" />
					</li>
					<li>
						<label>至</label> 
						<input type="text" id="endDateTo" name="endDateTo" class="i-date"
							onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
							value="<fmt:formatDate value="${customer.endDateTo}" pattern='yyyy-MM-dd'/>" />
					</li>
					<li>
						<label>材料类型1</label>
						<select id="itemType1" name="itemType1" style="width:160px"  style="width:166px;"></select>
					</li>
					<li>
						<label>统计方式</label> 
						<select id="statistcsMethod" name="statistcsMethod">
							<option value="1">起始节点</option>
							<option value="2">终止节点</option>
						</select>
					</li>
					<li>
						<label>客户类型</label>
						<house:custTypeMulit id="custType" ></house:custTypeMulit>
					</li>
					<li>
						<label>工程部</label>
						<house:DictMulitSelect id="department2" dictCode="" 
							sql="select rtrim(Code) fd1, rtrim(Desc1) fd2 from tDepartment2 where expired='F' and DepType='3' order by DispSeq " 
							sqlLableKey="fd2" sqlValueKey="fd1">
						</house:DictMulitSelect>
					</li>
					<li>
						<label>应下单节点</label>
						<house:dict id="prjItem" dictCode="" sqlValueKey="Code" sqlLableKey="Descr"
								    sql="SELECT Code,Descr FROM tPrjItem1 a WHERE 1=1 and expired='F' 
								    	and exists (select 1 from tItemSendNodeDetail in_a
								    	left join tItemSendNode in_b on in_a.Code=in_b.Code
								    	where in_a.beginNode = a.Code and in_b.Type='1') order by  a.Seq"></house:dict>
					</li>
					<li>
						<label>主材管家</label>
						<input type="text" id="mainBusinessMan" name="mainBusinessMan" style="width:160px;" value="${customer.mainBusinessMan }" />
					</li>
					<li>
						<label>材料是否确认</label> 
						<select id="checkStatus" name="checkStatus">
							<option value="">请选择...</option>
							<option value="1">未确认</option>
							<option value="2">已确认</option>
						</select>
					</li>
					<li id="loadMore">
						<button type="button" class="btn btn-sm btn-system " onclick="goto_query();">查询</button>
					</li>
				</ul>
			</form>
		</div>
		<div class="btn-panel">
			<div class="btn-group-sm">
				<house:authorize authCode="ITEMSHOULDORDER_VIEW">
					<button type="button" class="btn btn-system" onclick="view()">查看</button>
				</house:authorize>
				<house:authorize authCode="ITEMSHOULDORDER_EXCEL">
					<button type="button" class="btn btn-system" onclick="doExcel()">导出excel</button>
				</house:authorize>
			</div>
			<div id="content-list">
				<table id="dataTable"></table>
				<div id="dataTablePager"></div>
			</div>
		</div>
	</div>
</body>
</html>


