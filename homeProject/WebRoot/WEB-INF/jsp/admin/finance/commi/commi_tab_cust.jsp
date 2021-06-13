<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript">
$(function(){
	/**初始化表格*/
	var gridOption ={
			url:"${ctx}/admin/commi/goCustJqGrid",
			height:350,
			postData:{no:"${no}"},
			rowNum:10000000,
			colModel : [
				{name: "pk", index: "pk", width: 70, label: "pk", sortable: true, align: "left",hidden:true},
				{name: "documentno", index: "documentno", width: 70, label: "档案号", sortable: true, align: "left"},
				{name: "custcode", index: "custcode", width: 70, label: "客户编号", sortable: true, align: "left"},
				{name: "address", index: "address", width: 150, label: "楼盘", sortable: true, align: "left"},
				{name: "designman", index: "designman", width: 70, label: "设计师", sortable: true, align: "left",hidden:true},
				{name: "designmandescr", index: "designmandescr", width: 70, label: "设计师", sortable: true, align: "left"},
				{name: "designmandept", index: "designmandept", width: 80, label: "设计师部门", sortable: true, align: "left",hidden:true},
				{name: "designmandeptdescr", index: "designmandeptdescr", width: 80, label: "设计师部门", sortable: true, align: "left"},
				{name: "scenedesignman", index: "scenedesignman", width: 80, label: "现场设计师", sortable: true, align: "left",hidden:true},
				{name: "scenedesignmandescr", index: "scenedesignmandescr", width: 80, label: "现场设计师", sortable: true, align: "left"},
				{name: "scenedesignmandept", index: "scenedesignmandept", width: 105, label: "现场设计师部门", sortable: true, align: "left",hidden:true},
				{name: "scenedesignmandeptdescr", index: "scenedesignmandeptdescr", width: 105, label: "现场设计师部门", sortable: true, align: "left"},
				{name: "type", index: "type", width: 70, label: "提成类型", sortable: true, align: "left",hidden:true},
				{name: "typedescr", index: "typedescr", width: 70, label: "提成类型", sortable: true, align: "left"},
				{name: "lineamount", index: "lineamount", width: 70, label: "总价", sortable: true, align: "right",sum:true},
				{name: "commiamount", index: "commiamount", width: 70, label: "提成额", sortable: true, align: "right",sum:true},
				{name: "lastupdate", index: "lastupdate", width: 120, label: "最后更新时间", sortable: true, align: "left", formatter: formatTime},
				{name: "lastupdatedby", index: "lastupdatedby", width: 90, label: "最后更新人员", sortable: true, align: "left"},
				{name: "expired", index: "expired", width: 70, label: "是否过期", sortable: true, align: "left"},
				{name: "actionlog", index: "actionlog", width: 70, label: "操作日志", sortable: true, align: "left"}
			],
			 ondblClickRow:function(){
				view();
	         },	
 		};
	   Global.JqGrid.initJqGrid("custDataTable",gridOption);
});


</script>
<div class="body-box-list" style="margin-top: 0px;">
	<div class="clear_float"></div>
	<!--query-form-->
	<div id="content-list">
		<table id="custDataTable"></table>
	</div>
</div>



