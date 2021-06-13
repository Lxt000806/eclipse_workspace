<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript">
$(function(){
	/**初始化表格*/
	var gridOption ={
			url:"${ctx}/admin/perfCycle/goBcyyjjsJqGrid",
			postData:{no:"${perfCycle.no}"},
			height:350,
			rowNum:10000000,
			colModel : [
				{name: "pk", index: "pk", width: 70, label: "编号", sortable: true, align: "left",hidden:true},
				{name: "custcode", index: "custcode", width: 70, label: "客户编号", sortable: true, align: "left"},
				{name: "custdescr", index: "custdescr", width: 80, label: "客户名称", sortable: true, align: "left"},
				{name: "address", index: "address", width: 150, label: "楼盘地址", sortable: true, align: "left"},
				{name: "signdate", index: "signdate", width: 80, label: "签单日期", sortable: true, align: "left", formatter: formatDate},
				{name: "achievedate", index: "achievedate", width: 90, label: "业绩达标日期", sortable: true, align: "left", formatter: formatDate,hidden:true},
				{name: "remarks", index: "remarks", width: 150, label: "备注", sortable: true, align: "left"},
				{name: "lastupdate", index: "lastupdate", width: 120, label: "最后更新时间", sortable: true, align: "left", formatter: formatTime},
				{name: "lastupdatedby", index: "lastupdatedby", width: 90, label: "最后更新人员", sortable: true, align: "left"},
				{name: "expired", index: "expired", width: 70, label: "是否过期", sortable: true, align: "left"},
				{name: "actionlog", index: "actionlog", width: 70, label: "操作日志", sortable: true, align: "left"}
			],
 		};
       //初始化发货明细
	   Global.JqGrid.initJqGrid("bcyyjjsDataTable",gridOption);
});


</script>
<div class="body-box-list" style="margin-top: 0px;">
	<form role="form" class="form-search" id="page_form2" action=""
		method="post" target="targetFrame">
		<input type="hidden" name="jsonString" value="" />
		<ul class="ul-form">
			<li><label>楼盘</label> <input type="text" name="address" style="width:160px;" />
			</li>
			<li>
				<button type="button" class="btn btn-sm btn-system"
					onclick="doQuery('page_form2');">查询</button>
				<button type="button" class="btn btn-sm btn-system"
					onclick="clearForm('page_form2');">清空</button>
			</li>
		</ul>
	</form>
	<div class="panel panel-system">
		<div class="panel-body">
			<div class="btn-group-xs">
				<button style="align:left" type="button" 
					class="btn btn-system viewFlag" onclick="add()">
					<span>新增 </span>
				</button>
				<button style="align:left" type="button" 
					class="btn btn-system viewFlag" onclick="update()">
					<span>编辑 </span>
				</button>
				<button style="align:left" type="button" 
					class="btn btn-system viewFlag" onclick="del()">
					<span>删除 </span>
				</button>
				<button style="align:left" type="button" 
					class="btn btn-system " onclick="view()">
					<span>查看 </span>
				</button>
			</div>
		</div>
	</div>
	<div class="clear_float"></div>
	<!--query-form-->
	<div id="content-list">
		<table id="bcyyjjsDataTable"></table>
	</div>
</div>



