<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>

<script type="text/javascript">
	$(function(){
		initExcessDataTable(230);
	});
	function initExcessDataTable(height){
		Global.JqGrid.initJqGrid("excessDataTable", {
			height:height,
			url:"#",
			rowNum: 10000,
			styleUI:"Bootstrap",
			colModel:[
				{name: "address", index: "address", width: 120, label: "楼盘", sortable: true, align: "left"},
				{name: "statusdescr", index: "statusdescr", width: 70, label: "楼盘状态", sortable: true, align: "left"},
				{name: "projectmandescr", index: "projectmandescr", width: 70, label: "项目经理", sortable: true, align: "left"},
				{name: "itemtype1descr", index: "itemtype1descr", width: 70, label: "材料类型1", sortable: true, align: "left"},
				{name: "isservicedescr", index: "isservicedescr", width: 100, label: "是否服务性产品", sortable: true, align: "left"},
				{name: "chaochue", index: "chaochue", width: 70, label: "超出额", sortable: true, align: "right", sum: true}
			],
			gridComplete:function(){
				excessDataTableComplete();
			}
		}); 
	}
	function excessDataTableComplete(){
	
		if($("#m_umState").val() == "C"){
			$("#mainItemDataTable").jqGrid("hideCol", "chaochue");
			$("#a_tabView_excess").css("display", "none");
			
			var ids = $("#excessDataTable").jqGrid("getDataIDs");
			
			if(ids.length > 0 && $("#status").val() == "1"){
				$("#a_tabView_excess").html("超出额（"+ids.length+"）");
				$("#a_tabView_excess").css("display", "block");
			}else{
				$("#a_tabView_excess").css("display", "none");
			}
		}else{
			$("#mainItemDataTable").jqGrid("showCol", "chaochue");
			$("#a_tabView_excess").css("display", "none");
		}
	}
</script>
<table id="excessDataTable"></table>
