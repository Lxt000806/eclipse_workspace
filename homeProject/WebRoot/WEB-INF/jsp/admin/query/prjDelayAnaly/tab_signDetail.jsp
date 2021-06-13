<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>

<script type="text/javascript">
$(function(){
	var lastCellRowId;
	/*工地验收*/
	var gridOption ={
		url:"${ctx}/admin/custWorker/goViewSignJqGrid",
		postData:{custCode:"${customer.code}"},
	    rowNum:10000000,
		height: $(document).height()-$("#content-list-9").offset().top-240,
		styleUI: 'Bootstrap', 
			colModel : [
				{name: "address", index: "address", width: 150, label: "楼盘", sortable: true, align: "left",},
				{name: "worktype12descr", index: "worktype12descr", width: 80, label: "工种类型", sortable: true, align: "left"},
				{name: "workerdescr", index: "workerdescr", width: 75, label: "工人名称", sortable: true, align: "left"},
				{name: "crtdate", index: "crtdate", width: 120, label: "签到时间", sortable: true, align: "left",formatter: formatTime},
				{name: "prjitem2descr", index: "prjitem2descr", width: 80, label: "施工阶段", sortable: true, align: "left"},
				{name: "isend", index: "isend", width: 80, label: "是否完成", sortable: true, align: "left"},
				{name: "no", index: "no", width: 80, label: "no", sortable: true, align: "left",hidden:true},
				{name: "photonum", index: "photonum", width: 80, label: "图片数量", sortable: true, align: "right"},
				{name: "custscore", index: "custscore", width: 80, label: "客户评分", sortable: true, align: "right"},
				{name: "custremarks", index: "custremarks", width: 220, label: "客户评价", sortable: true, align: "left"},
				{name: "custcode", index: "custcode", width: 80, label: "custcode", sortable: true, align: "left",hidden:true},
			], 
 		};
       //初始化送货申请明细
	   Global.JqGrid.initJqGrid("dataTable_signDetail",gridOption);
});
</script>
<div class="body-box-list" style="margin-top: 0px;">
	<div class="clear_float"></div>
	<!--query-form-->
	<div id="content-list-9" >
		<table id="dataTable_signDetail"></table>
	</div>
</div>



