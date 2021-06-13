<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>

<script type="text/javascript">
$(function(){
	var lastCellRowId;
	/**初始化表格*/
	var gridOption ={
			url:"${ctx}/admin/purchasePay/goJqGrid",
			postData:{puno:'${purchase.no }'},
			height:150,
			colModel : [
				{name: "PK", index: "pk", width: 50, label: "pk", sortable: true, align: "left",hidden:true},
				{name: "PUNo", index: "PUNo", width: 50, label: "PUNo", sortable: true, align: "left",hidden:true },
				{name: "typedescr", index: "typedescr", width: 104, label: "付款类型", sortable: true, align: "left"},
				{name: "Type", index: "Type", width: 104, label: "付款类型", sortable: true, align: "left",hidden:true },
				{name: "Status", index: "Status", width: 90, label: "状态", sortable: true, align: "left",hidden:true },
				{name: "statusdescr", index: "statusdescr", width: 90, label: "状态", sortable: true, align: "left"},
				{name: "Amount", index: "Amount", width: 120, label: "付款金额", sortable: true, align: "right", sum: true},
				{name: "SplPayNo", index: "SplPayNo", width: 124, label: "供应商付款单号", sortable: true, align: "left"},
				{name: "LastUpdate", index: "LastUpdate", width: 164, label: "最后更新时间", sortable: true, align: "left", formatter: formatTime},
				{name: "LastUpdatedBy", index: "LastUpdatedBy", width: 84, label: "更新人员", sortable: true, align: "left", hidden: true},
				{name: "Expired", index: "expired", width: 84, label: "是否过期", sortable: true, align: "left", hidden: true},
				{name: "Actionlog", index: "actionlog", width: 84, label: "操作", sortable: true, align: "left", hidden: true}
			], 
			beforeSelectRow:function(rowId, e){
				setTimeout(function(){
					relocate(rowId,"dataTable2");
				},10);
			}
 		};
       //初始化送货申请明细
	   Global.JqGrid.initEditJqGrid("dataTable2",gridOption);


});
 </script>
<div class="body-box-list" style="margin-top: 0px;border:1px solid #ddd;">
	<div class="pageContent">
		<table id="dataTable2" style="overflow: auto;"></table>
	</div>
</div>




