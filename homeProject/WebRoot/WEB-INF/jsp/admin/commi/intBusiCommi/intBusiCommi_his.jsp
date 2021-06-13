<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<!DOCTYPE HTML>
<html>
<head>
    <title>提成材料信息</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_roll.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_commiCycle.js?v=${v}" type="text/javascript"></script>
	<script type="text/javascript">
	//清空
	function clearForm(){
		$("#page_form input[type='text']").val("");
		$("select").val("");
	} 
	/**初始化表格*/
	$(function(){
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/intBusiCommi/goHisJqGrid",
			postData:{crtMon:"${intBusiCommi.crtMon}",custCode:"${intBusiCommi.custCode}",commiNo:"${intBusiCommi.commiNo}"},
			rowNum: 100000000,
			height:400,
			colModel : [
			    {name: "mon", index: "mon", width: 70, label: "提成月份", sortable: true, align: "left",},
			  	{name: "address", index: "address", width: 120, label: "楼盘", sortable: true, align: "left",},
			    {name: "typedescr", index: "typedescr", width: 80, label: "提成类型", sortable: true, align: "left"},
				{name: "busitypedescr", index: "busitypedescr", width: 80, label: "业务类型", sortable: true, align: "left"},
				{name: "empname", index: "empname", width: 65, label: "员工", sortable: true, align: "left"},
				{name: "departmentdescr", index: "departmentdescr", width: 90, label: "部门", sortable: true, align: "left"},
				{name: "itemchgno", index: "itemchgno", width: 70, label: "单号", sortable: true, align: "left",hidden:true },
				{name: "chgno", index: "chgno", width: 70, label: "单号", sortable: true, align: "left", },
				{name: "iscupboarddescr", index: "iscupboarddescr", width: 70, label: "是否橱柜", sortable: true, align: "left",},
				{name: "totalamount", index: "totalamount", width: 70, label: "总提成额", sortable: true, align: "right"},
				{name: "shouldprovideper", index: "shouldprovideper", width: 70, label: "应发比例", sortable: true, align: "right"},
				{name: "shouldprovideamount", index: "shouldprovideamount", width: 70, label: "应发提成额", sortable: true, align: "right"},
				{name: "totalprovideamount", index: "totalprovideamount", width: 90, label: "累计已发提成额", sortable: true, align: "right",},
			    {name: "thisamount", index: "thisamount", width: 80, label: "本次提成额", sortable: true, align: "right"},
			    {name: "iscompletedescr", index: "iscompletedescr", width: 70, label: "计算结束", sortable: true, align: "left",},
				{name: "isdesignsaledescr", index: "isdesignsaledescr", width: 80, label: "设计师销售", sortable: true, align: "left",},
				{name: "custcode", index: "custcode", width: 70, label: "客户编号", sortable: true, align: "left"},
				{name: "documentno", index: "documentno", width: 70, label: "档案号", sortable: true, align: "left"},
				{name: "custtypedescr", index: "custtypedescr", width: 70, label: "客户类型", sortable: true, align: "left"},
				{name: "signdate", index: "signdate", width: 90, label: "签订日期", sortable: true, align: "left", formatter: formatDate},
				{name: "lastupdate", index: "lastupdate", width: 133, label: "最后修改时间", sortable: true, align: "left", formatter: formatTime},
				{name: "lastupdatedby", index: "lastupdatedby", width: 61, label: "修改人", sortable: true, align: "left"},
				{name: "expired", index: "expired", width: 71, label: "是否过期", sortable: true, align: "left"},
				{name: "actionlog", index: "actionlog", width: 50, label: "操作", sortable: true, align: "left"},
				{name: "pk", index: "pk", width: 80, label: "pk", sortable: true, align: "left",hidden:true,},
            ],
		});
	});
	</script>
</head>
<body>
	<div class="body-box-list">
		<div class="pageContent">
			<div id="content-list">
				<table id="dataTable"></table>
				<div id="dataTablePager"></div>
			</div>
		</div> 
	</div>
</body>
</html>
