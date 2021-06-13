<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>

<script type="text/javascript">
$(function(){
	var lastCellRowId;
	/**初始化表格*/
	var gridOption ={
			url:"${ctx}/admin/perfCycle/goFkxxJqGrid",
			postData:{custCode:$("#custCode").val()},
		    rowNum:10000000,
			height:180,
			colModel : [
				{name: "adddate", index: "adddate", width: 140, label: "登记日期", sortable: true, align: "left", formatter: formatTime},
				{name: "date", index: "date", width: 140, label: "付款日期", sortable: true, align: "left", formatter: formatTime},
				{name: "amount", index: "amount", width: 114, label: "付款金额", sortable: true, align: "right",sorttype:"number", sum: true},
				{name: "documentno", index: "documentno", width: 145, label: "凭证号", sortable: true, align: "left"},
				{name: "remarks", index: "remarks", width: 438, label: "备注", sortable: true, align: "left"}
			], 
			loadonce:true,
			gridComplete:function(){
				$("#fkxxDataTable").footerData('set', { "date": "已付款金额" });    
			}  
 		};
	   Global.JqGrid.initJqGrid("fkxxDataTable",gridOption);
});
//重写格式化日期时间方法
function formatTime(strTime) {
	if (isNaN(strTime)){//中文不做格式化
		return strTime;
	}
	if (strTime){
		var date = new Date(strTime);
		var y = date.getFullYear();
		var m = date.getMonth()+1;
		var d = date.getDate();
		var h = date.getHours();
		var mi = date.getMinutes();
		var s = date.getSeconds();
		if (strTime.length<=10){
			return y+"-"
	    	+(m>9?m:'0'+m)+"-"
	    	+(d>9?d:'0'+d)+" "
	    	+"00:00:00";
		}else{
			return y+"-"
	    	+(m>9?m:'0'+m)+"-"
	    	+(d>9?d:'0'+d)+" "
	    	+(h>9?h:'0'+h)+":"
	    	+(mi>9?mi:'0'+mi)+":"
	    	+(s>9?s:'0'+s);
		}
	}
	return "";
}


</script>
<div class="body-box-list" style="margin-top: 0px;">
	<div class="panel panel-system">
		<div class="panel-body">
		</div>
	</div>
	<div class="clear_float"></div>
	<!--query-form-->
	<div id="content-list">
		<table id="fkxxDataTable"></table>
	</div>
</div>



