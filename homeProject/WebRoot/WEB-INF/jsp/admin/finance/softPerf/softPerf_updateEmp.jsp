<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://" + request.getServerName() + ":"+request.getServerPort()+path+"/";
String jasperPath = PathUtil.WEB_INF+"jsp/jasper/";
%>

<!DOCTYPE HTML>
<html>
<head>
    <title>修改员工信息</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_department2.js?v=${v}" type="text/javascript"></script>
	<script type="text/javascript">
	function doExcel(){
		var url = "${ctx}/admin/softPerf/doEmpInfoExcel";
		doExcelAll(url);
	}
	//新增
	function save(){
		Global.Dialog.showDialog("save",{
			title:"业绩归属员工信息-新增",
			url:"${ctx}/admin/softPerf/goSaveEmpInfo",
			height: 450,
		 	width:450,
			returnFun: goto_query 
		});
	}
	
	function update(){
		var ret=selectDataTableRow();
		if(!ret){
			art.dialog({
				content:"请选择一条归属员工信息",
			});
			return ;
		}
		Global.Dialog.showDialog("update",{
			title:"业绩归属员工信息-编辑",
			postData:{pk:ret.pk},
			url:"${ctx}/admin/softPerf/goUpdateEmpInfo",
			height: 450,
		 	width:450,
			returnFun: goto_query 
		});
	}
	
	function view(){
		var ret=selectDataTableRow();
		if(!ret){
			art.dialog({
				content:"请选择一条归属员工信息",
			});
			return ;
		}
		Global.Dialog.showDialog("view",{
			title:"业绩归属员工信息-查看",
			postData:{pk:ret.pk},
			url:"${ctx}/admin/softPerf/goViewEmpInfo",
			height: 450,
		 	width:450,
			returnFun: goto_query 
		});
	}
	
	//清空
	function clearForm(){
		$("#page_form input[type='text']").val("");
		$("#status").val("");
		$.fn.zTree.getZTreeObj("tree_status").checkAllNodes(false);
	} 
	/**初始化表格*/
	$(function(){
		$("#number").openComponent_employee();
		$("#department2").openComponent_department2();
		
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/softPerf/goEmpInfoJqGrid",
			height:$(document).height()-$("#content-list").offset().top-70,
			styleUI: "Bootstrap", 
			colModel : [
				{name: "pk", index: "pk", width: 85, label: "pk", sortable: true, align: "left",hidden:true},
				{name: "empcode", index: "empcode", width: 85, label: "员工编号", sortable: true, align: "left"},
				{name: "empdescr", index: "empdescr", width: 98, label: "员工姓名", sortable: true, align: "left"},
				{name: "department1descr", index: "department1descr", width: 110, label: "业绩归属一级部门", sortable: true, align: "left"},
				{name: "department2descr", index: "department2descr", width: 110, label: "业绩归属二级部门", sortable: true, align: "left"},
				{name: "begindate", index: "begindate", width: 90, label: "业绩归属开始时间", sortable: true, align: "left", formatter: formatDate},
				{name: "enddate", index: "enddate", width: 90, label: "业绩归属结束时间", sortable: true, align: "left", formatter: formatDate},
				{name: "nowdepartment1descr", index: "nowdepartment1descr", width: 136, label: "现一级部门", sortable: true, align: "left"},
				{name: "nowdepartment2descr", index: "nowdepartment2descr", width: 136, label: "现二级部门", sortable: true, align: "left"},
				{name: "lastupdate", index: "lastupdate", width: 111, label: "最后更新时间", sortable: true, align: "left", formatter: formatTime},
				{name: "lastupdatedby", index: "lastupdatedby", width:91, label: "最后更新人员", sortable: true, align: "left"},
				{name: "expired", index: "expired", width: 85, label: "是否过期", sortable: true, align: "left"},
				{name: "actionlog", index: "actionlog", width: 76, label: "操作日志", sortable: true, align: "left"}
			],
			ondblClickRow: function(){
				view();
			}
		});
	});
	</script>
</head>
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
				<input type="hidden" name="jsonString" value=""/>
					<input type="hidden" id="expired" name="expired"/>
					<ul class="ul-form">
						<li>
							<label>员工编号 </label>
							<input type="text" style="width:160px;" id="number" name="number"/>
						</li>
						<li>
							<label>所属部门</label>
							<input type="text" style="width:160px;" id="department2" name="department2"/>
						</li>
						<li class="search-group">					
							<input type="checkbox"  id="expired_show" name="expired_show"
								 value="${employee.expired }" onclick="hideExpired(this)" 
								 ${purchase.expired!='T'?'checked':'' }/><p>隐藏过期</p>
							<button type="button" class="btn btn-sm btn-system" onclick="goto_query();">查询</button>
							<button type="button" class="btn btn-sm btn-system" onclick="clearForm();">清空</button>
						</li>
					</ul>	
			</form>
		</div>
		<div class="pageContent">
			<div class="btn-panel">
    			<div class="btn-group-sm">
					<button type="button" class="btn btn-system" onclick="save()">
						<span>新增</span>
					</button>
					<button type="button" class="btn btn-system" onclick="update()">
						<span>编辑</span>
					</button>
					<button type="button" class="btn btn-system" onclick="view()">
						<span>查看</span>
					</button>
					<button type="button" class="btn btn-system" onclick="doExcel()" title="导出检索条件数据">
						<span>输出到excel</span>
					</button>
                </div>
			</div> 
			<div id="content-list">
				<table id="dataTable"></table>
				<div id="dataTablePager"></div>
			</div>
		</div> 
	</div>
</body>
</html>
