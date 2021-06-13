<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>

<script type="text/javascript">
$(function(){
	var lastCellRowId;
	/**初始化表格*/
	var gridOption ={
			url:"${ctx}/admin/prjProgPhoto/goPrjProgPhotoJqGrid",
			height:550,
			colModel : [
				{name: "custcode", index: "custcode", width: 170, label: "custcode", sortable: true, align: "left"},
				{name: "address", index: "address", width: 170, label: "楼盘地址", sortable: true, align: "left"},
				{name: "projectmandescr", index: "projectmandescr", width: 100, label: "项目经理", sortable: true, align: "left"},
				{name: "prjitemdescr", index: "prjitemdescr", width: 175, label: "施工项目", sortable: true, align: "left"},
				{name: "lastupdate", index: "lastupdate", width: 187, label: "相片上传时间", sortable: true, align: "left", formatter: formatTime}
			], 
 		};
       //初始图片更新信息
	   Global.JqGrid.initEditJqGrid("dataTable_photo",gridOption);


});
 </script>
<div class="body-box-list" style="margin-top: 0px;">

	<div class="pageContent">
		<table id="dataTable_photo" style="overflow: auto;"></table>
	</div>
</div>




