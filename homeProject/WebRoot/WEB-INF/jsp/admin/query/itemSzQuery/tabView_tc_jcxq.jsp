
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>

<%@ taglib prefix="house" uri="http://www.housenet.com/Framework/tags" %>

<script type="text/javascript">
$(function(){
	Global.JqGrid.initJqGrid("dataTable_jcxq",{
		url:"${ctx}/admin/itemSzQuery/goJcxqJqGrid",
		postData:{custCode:$("#code").val(),workType1:$("#workType1").val(),baseItem:$("#baseItem").val(),isOutSet:$("#isOutSet").val()},
        autowidth: false,
        height:460,
		width:1259, 
		rowNum: 10000,
		styleUI: 'Bootstrap',
		colModel : [
			{name: "fixareadescr", index: "fixareadescr", width: 120, label: "区域", sortable: true, align: "left"},
			{name: "baseitemdescr", index: "baseitemdescr", width: 200, label: "基础项目", sortable: true, align: "left"},
			{name: "qty", index: "qty", width: 60, label: "数量", sortable: true, align: "right", sum: true},
			{name: "uom", index: "uom", width: 60, label: "单位", sortable: true, align: "left"},
			{name: "unitprice", index: "unitprice", width: 100, label: "人工单价", sortable: true, align: "right"/* , sum: true */},
			{name: "material", index: "material", width: 100, label: "材料单价", sortable: true, align: "right"},
			{name: "cost", index: "cost", width: 100, label: "材料单价", sortable: true, align: "right",hidden:true},
			{name: "lineamount", index: "lineamount", width: 80, label: "总价", sortable: true, align: "right", sum: true},
			{name: "prjctrltypedescr", index: "prjctrltypedescr", width: 80, label: "发包方式", sortable: true, align: "left"},
			{name: "offerctrl", index: "offerctrl", width: 80, label: "人工发包", sortable: true, align: "right"},
			{name: "materialctrl", index: "materialctrl", width: 80, label: "材料发包", sortable: true, align: "right"},
			{name: "totalctrl", index: "totalctrl", width: 80, label: "发包额", sortable: true, align: "right", sum: true},
			{name: "isoutsetdescr", index: "isoutsetdescr", width: 80, label: "套餐外项目", sortable: true, align: "left"},
			{name: "isbaseitemsetdescr", index: "isbaseitemsetdescr", width: 80, label: "套餐包", sortable: true, align: "left"},
			{name: "remark", index: "remark", width: 200, label: "备注", sortable: true, align: "left"},
			{name: "lastupdate", index: "lastupdate", width: 140, label: "最后修改日期", sortable: true, align: "left",formatter:formatTime},
			{name: "lastupdatedby", index: "lastupdatedby", width: 60, label: "修改人", sortable: true, align: "left"},
			{name: "expired", index: "expired", width: 50, label: "是否过期", sortable: true, align: "left"},
			{name: "actionlog", index: "actionlog", width: 40, label: "操作", sortable: true, align: "left"}
        ],           
        grouping:true,
        groupingView : {
               groupField : ["fixareadescr"],
               groupText: ["<b title=\"{0}({1})\">{0}({1})</b>"],
        }    
	});
	$("#baseItem").keydown(function(e){
		if(e.keyCode==13){
			goto_query_jcxq();
		}
	});
});
function goto_query_jcxq(){
	$("#dataTable_jcxq").jqGrid("setGridParam",{url:"${ctx}/admin/itemSzQuery/goJcxqJqGrid",postData:{custCode:$("#code").val(),workType1:$("#workType1").val(),baseItem:$("#baseItem").val(),isOutSet:$("#isOutSet").val()},page:1}).trigger("reloadGrid");			
}
</script>
<form action="" method="post" id="page_form_tc_jcxq" class="form-search" >
	<input type="hidden" name="jsonString" value="" />
	<ul class="ul-form">
		<li>
			<label>工种分类1</label>
			<house:dict id="workType1" dictCode="" sql=" select rtrim(Code)+' '+Descr fd,Code from tWorkType1 where Expired='F' order by Code " sqlLableKey="fd" sqlValueKey="Code"></house:dict>
		</li>
		<li>
			<label>基础项目名称</label>
			<input type="text" id="baseItem" name="baseItem"  />
		</li>
		</li>
			<li><label>套餐外项目</label> <house:xtdm id="isOutSet" dictCode="YESNO"></house:xtdm>
			</li>
		<li>
			<button type="button" class="btn btn-sm btn-system " onclick="goto_query_jcxq()">查询</button>
		</li>
	</ul>	
</form>
<table id="dataTable_jcxq"></table>
