<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML>
<html>
  <head>
  	<title>展会活动管理--列表</title>
  	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp"%>
	<script type="text/javascript">
		$(function() {
			  //初始化表格
			  Global.JqGrid.initJqGrid("dataTable",{
			        url : "${ctx}/admin/activity/goJqGrid",
					height : $(document).height()- $("#content-list").offset().top - 70,
					styleUI : "Bootstrap",
					colModel : [
						{name: "no", index: "no", width: 90, label: "编码", sortable: true, align: "left"},
						{name: "actname", index: "actname", width: 110, label: "名称", sortable: true, align: "left"},
						{name: "times", index: "times", width: 101, label: "界数", sortable: true, align: "left"},
						{name: "sites", index: "sites", width: 122, label: "地点", sortable: true, align: "left"},
						{name: "begindate", index: "begindate", width: 127, label: "开始时间", sortable: true, align: "left", formatter: formatTime},
						{name: "enddate", index: "enddate", width: 131, label: "结束时间", sortable: true, align: "left", formatter: formatTime},
						{name: "prefix", index: "prefix", width: 170, label: "编号前缀", sortable: true, align: "left"},
						{name: "length", index: "length", width: 82, label: "编号长度", sortable: true, align: "right"},
						{name: "remarks", index: "remarks", width: 177, label: "备注", sortable: true, align: "left"},
						{name: "cmpactdescr", index: "cmpactdescr", width: 140, label: "公司活动名称", sortable: true, align: "left"},
						{name: "lastupdate", index: "lastupdate", width: 142, label: "最后修改时间", sortable: true, align: "left", formatter: formatTime},
						{name: "lastupdatedby", index: "lastupdatedby", width: 118, label: "修改人", sortable: true, align: "left"},
						{name: "expired", index: "expired", width: 102, label: "是否过期", sortable: true, align: "left"},
						{name: "actionlog", index: "actionlog", width: 120, label: "操作", sortable: true, align: "left"}	
					 ]
			    });
		});
		function add(){
			Global.Dialog.showDialog("activity",{
				title:"展会活动管理--添加",
				url:"${ctx}/admin/activity/goSave",
				width:800,
				height:400,
				returnFun:goto_query
			});
		}
		function update() {
			var ret = selectDataTableRow();
			if (ret) {
				Global.Dialog.showDialog("activity", {
					title : "展会活动管理--编辑",
					url : "${ctx}/admin/activity/goUpdate?id="+ ret.no,
					height : 400,
					width : 800,
					returnFun : goto_query
					});
			} else {
				art.dialog({
				content : "请选择一条记录"
					});
				}
			}
		function view() {
			var ret = selectDataTableRow();
			if (ret) {
				Global.Dialog.showDialog("activity", {
			    	title : "展会活动管理--查看",
					url : "${ctx}/admin/activity/goView?id=" + ret.no,
				    height : 400,
					width : 800,
					returnFun : goto_query
					});
			} else {
				art.dialog({
				content : "请选择一条记录"
					});
				}
			}	
		
		function doExcel() {
			var url = "${ctx}/admin/activity/doExcel";
			doExcelAll(url);
		} 	
	</script>
  </head>
  <body>
  	<div class="body-box-list">
  		<div class="query-form">
			<form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
				<input type="hidden" name="jsonString" value="" />
				<input type="hidden" id="expired"  name="expired" value="${activity.expired }"/>
				<ul class="ul-form">
					<li><label>编号</label> <input type="text" id="no" name="no" /></li>
				    <li><label>名称</label> <input type="text" id="actName" name="actName" /></li>
					<li><label>地点</label> <input type="text" id="sites" name="sites" /></li>
					<li>
						<label>开始时间从</label>
						<input type="text" id="beginDate" name="beginDate" class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${activity.beginDate}' pattern='yyyy-MM-dd'/>" />
					</li>
					<li>						
						<label>至</label>
						<input type="text" id="endDate" name="endDate" class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${activity.endDate}' pattern='yyyy-MM-dd'/>" />
					</li>
				    <li class="search-group-shrink">
						<input type="checkbox"  id="expired_show" name="expired_show" onclick="hideExpired(this)" ${activity.expired!='T'?'checked':''} />   
						<P>隐藏过期</p>
						<button type="summit" class="btn  btn-sm btn-system " onclick="goto_query();">查询</button>
						<button type="button" class="btn btn-sm btn-system " onclick="clearForm();">清空</button>
					</li>
				</ul>
			</form>
		</div>
		<div class="pageContent">
			<div class="btn-panel">
				<div class="btn-group-sm">
					<button type="button" class="btn btn-system " onclick="add()">
					<span>新增</span>
					</button>
					<button type="button" class="btn btn-system " onclick="update()">
					<span>编辑</span>
					</button>
					<house:authorize authCode="activity_VIEW">
					<button type="button" class="btn btn-system " onclick="view()">
					<span>查看</span>
					</button>
					</house:authorize>
					<button type="button" class="btn btn-system " onclick="doExcel()">导出到Excel</button>
				</div>
			</div>
		</div>		
  		<div id="content-list">
    		<table id="dataTable"></table>
			<div id="dataTablePager"></div>
		</div>
	</div>
  </body>
</html>
 
