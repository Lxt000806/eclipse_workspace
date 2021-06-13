<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>

<script type="text/javascript">
$(function(){
	var lastCellRowId;
	/**初始化表格*/
	var gridOption ={
			url:"${ctx}/admin/custPay/goChgInfoJqGrid",
			postData:{code:"${customerPayMap.code }"},
		    rowNum:10000000,
			height:220,
			styleUI: 'Bootstrap', 
			onSortColEndFlag:true,
			colModel : [
				{name: "date", index: "date", width: 120, label: "增减日期", sortable: true, align: "left", formatter: formatTime},
				{name: "itemtype1descr", index: "itemtype1descr", width: 70, label: "项目", sortable: true, align: "left"},
				{name: "amount", index: "amount", width: 60, label: "金额", sortable: true, align: "right", sum: true}
			], 
			onSortCol:function(index, iCol, sortorder){
					var rows = $("#chgInfoDataTable").jqGrid("getRowData");
		   			rows.sort(function (a, b) {
		   				var reg = /^[0-9]+.?[0-9]*$/;
						if(reg.test(a[index])){
		   					return (a[index]-b[index])*(sortorder=="desc"?1:-1);
						}else{
		   					return a[index].toString().localeCompare(b[index].toString())*(sortorder=="desc"?1:-1);
						}  
		   			});    
		   			Global.JqGrid.clearJqGrid("chgInfoDataTable"); 
		   			$.each(rows,function(k,v){
						Global.JqGrid.addRowData("chgInfoDataTable", v);
					});
				}, 
 		};
       //初始化集成进度明细
	   Global.JqGrid.initJqGrid("chgInfoDataTable",gridOption);
});
</script>
<div class="body-box-list" style="margin-top: 0px;">
	<div class="panel panel-system" >
    <div class="panel-body" >
		          </div> 
		          </div> 		
			<div class="clear_float"></div>
			<!--query-form-->
				<div id="content-list" >
					<table id="chgInfoDataTable"></table>
				</div>
		</div>



