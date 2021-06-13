<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="house" uri="http://www.housenet.com/Framework/tags" %>
<script type="text/javascript">
/**初始化表格*/
$(function(){
		//初始化表格
		Global.JqGrid.initJqGrid("dataTable_softInstall",{
			url:"${ctx}/admin/customerXx/goJqGrid_softInstall?custCode=${customer.code }",
			height:490,
			rowNum: 10000,
			colModel : [
			    {name: "address", index: "address", width: 200, label: "楼盘", sortable: true, align: "left",count: true},
				{name: "itemtype12descr", index: "itemtype12descr", width: 150, label: "材料分类", sortable: true, align: "left"},
				{name: "installempdescr", index: "installempdescr", width: 150, label: "安装工人", sortable: true, align: "left"},
				{name: "installdate", index: "installdate", width: 200, label: "安装时间", sortable: true, align: "left",formatter: formatTime},
				{name: "lastupdatedby", index: "lastupdatedby", width: 200, label: "最后修改人", sortable: true, align: "left"},
				{name: "lastupdate", index: "lastupdate", width: 200, label: "最后修改时间", sortable: true, align: "left",formatter: formatTime}
            ]
		});
});
</script>
<div class="body-box-list">
	<div class="pageContent" style="padding-top: 10px;">
		<table id="dataTable_softInstall"></table>
	</div>
</div>
