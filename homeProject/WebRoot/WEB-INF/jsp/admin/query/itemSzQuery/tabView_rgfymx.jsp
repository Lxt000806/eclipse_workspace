<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>

<%@ taglib prefix="house" uri="http://www.housenet.com/Framework/tags" %>

<script type="text/javascript">
$(function(){
	Global.JqGrid.initJqGrid("dataTable_rgfymx_zcrzszxx",{
		url:"${ctx}/admin/itemSzQuery/goRgfymxZcrzszxxJqGrid",
		postData:{custCode:$("#code").val(),laborFeeStatus:$("#laborFeeStatus").val(),itemType1:$("#rgfymxItemType1").val()},
        autowidth: false,
        height:480,
		width:1259, 
		styleUI: 'Bootstrap',
		rowNum: 10000,
		colModel : [
			{name: "no", index: "no", width: 120, label: "费用申请批号", sortable: true, align: "left"},
			{name: "itemtype1descr", index: "itemtype1descr", width: 100, label: "材料类型1", sortable: true, align: "left"},
			{name: "statusdescr", index: "statusdescr", width: 100, label: "状态", sortable: true, align: "left"},
			{name: "actname", index: "actname", width: 100, label: "户名", sortable: true, align: "left"},
			{name: "pk", index: "pk", width: 100, label: "费用明细PK", sortable: true, align: "right"},
			{name: "feetypedescr", index: "feetypedescr", width: 120, label: "费用类型", sortable: true, align: "left"},
			{name: "documentno", index: "documentno", width: 120, label: "凭证号", sortable: true, align: "left"},
			{name: "amount", index: "amount", width: 100, label: "金额", sortable: true, align: "right", sum: true},
			{name: "remarks", index: "remarks", width: 220, label: "明细备注", sortable: true, align: "left"}
        ]
	});
});
function rgfymx_goto_query(){
	$("#dataTable_rgfymx_zcrzszxx").jqGrid("setGridParam",{postData:{custCode:$("#code").val(),laborFeeStatus:$("#laborFeeStatus").val(),itemType1:$("#rgfymxItemType1").val()},page:1}).trigger("reloadGrid");
}
</script>
<div class="panel panel-info" style="border-top:0px"> 
	<form action="" method="post" id="page_form_rgfymx_zcrzszxx" class="form-search" >
		<input type="hidden" name="jsonString" value="" />
		<ul class="ul-form">
			<li>
				<label>材料类型1</label>
				<house:dict id="rgfymxItemType1" dictCode="" sql="select rtrim(Code)+' '+Descr fd,Code from tItemType1 where Expired='F' order by dispSeq" sqlValueKey="Code" sqlLableKey="fd"></house:dict>
			</li>
			<li>
				<label>状态</label>
				<house:xtdm id="laborFeeStatus" dictCode="LABORFEESTATUS"></house:xtdm>
			</li>
			<li>
				<button type="button" class="btn btn-sm btn-system " onclick="rgfymx_goto_query()">查询</button>
			</li>
		</ul>	
	</form>
	<table id="dataTable_rgfymx_zcrzszxx"></table>
</div>
