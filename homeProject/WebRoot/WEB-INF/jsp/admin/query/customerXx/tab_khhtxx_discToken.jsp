<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
/**初始化表格*/
$(function(){
        //初始化表格
		Global.JqGrid.initJqGrid("dataTable_khhtxx_discToken",{
			url:"${ctx}/admin/discTokenQuery/goJqGrid",
			postData:{custCode:'${customer.code}'},
			rowNum: 10000,
			height:450,
			colModel : [
				{name:"no", index:"no", width:60, label:"券号", sortable:true, align:"left"},
				{name:"amount", index:"amount", width:60, label:"金额", sortable:true, align:"right"},
				{name:"statusdescr", index:"statusdescr", width:60, label:"状态", sortable:true, align:"left"},
				{name:"useaddress", index:"useaddress", width:140, label:"使用楼盘", sortable:true, align:"left"},
				{name:"usestepdescr", index:"usestepdescr", width:80, label:"使用阶段", sortable:true, align:"left"},
				{name:"chgno", index:"chgno", width:80, label:"增减单号", sortable:true, align:"left"},
				{name:"checkdesdate", index:"checkdesdate", width:80, label:"核销日期", sortable:true, align:"left", formatter:formatDate},
				{name:"checkdesamount", index:"checkdesamount", width:75, label:"核销金额", sortable:true, align:"right"},
            ]
		});
});
</script>
<div class="body-box-list">
	<div class="pageContent" style="padding-top: 10px;">
		<table id="dataTable_khhtxx_discToken"></table>
	</div>
</div>
