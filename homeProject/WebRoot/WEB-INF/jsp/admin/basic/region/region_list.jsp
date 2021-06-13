<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML>
<html>
  <head>
  	<title>项目区域管理--列表</title>
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
			        url : "${ctx}/admin/region/goJqGrid",
					height : $(document).height()- $("#content-list").offset().top - 70,
					styleUI : "Bootstrap",
					colModel : [
					{name: "code", index: "code", width: 84, label: "区域编号", sortable: true, align: "left"},
					{name: "descr", index: "descr", width: 148, label: "区域名称", sortable: true, align: "left"},
					{name: "isspcworkerdescr", index: "isspcworkerdescr", width: 122, label: "是否指定工人", sortable: true, align: "left"},
					{name: "intsendtypedescr", index: "intsendtypedescr", width: 122, label: "集成出货模式", sortable: true, align: "left"},
					{name: "cmpdescr", index: "cmpdescr", width: 160, label: "公司名称", sortable: true, align: "left"},
					{name: "waterlongpension", index: "waterlongpension", width: 100, label: "防水远程补贴", sortable: true, align: "left"},
					{name: "lastupdate", index: "lastupdate", width: 163, label: "最后更新时间", sortable: true, align: "left", formatter: formatTime},
					{name: "lastupdatedby", index: "lastupdatedby", width: 111, label: "最后更新人员", sortable: true, align: "left"},
					{name: "expired", index: "expired", width: 90, label: "是否过期", sortable: true, align: "left"},
					{name: "actionlog", index: "actionlog", width: 91, label: "操作", sortable: true, align: "left"}
					]
			    });
		});
		function add(){
			Global.Dialog.showDialog("region",{
				title:"项目区域管理--添加",
				url:"${ctx}/admin/region/goSave",
				width:700,
				height:300,
				returnFun:goto_query
			});
		}
		function update() {
			var ret = selectDataTableRow();
			if (ret) {
				Global.Dialog.showDialog("region", {
					title : "项目区域管理--编辑",
					url : "${ctx}/admin/region/goUpdate?id="+ ret.code,
					height : 300,
					width : 700,
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
				Global.Dialog.showDialog("region", {
			    	title : "项目区域管理--查看",
					url : "${ctx}/admin/region/goView?id=" + ret.code,
				    height : 300,
					width : 700,
					returnFun : goto_query
					});
			} else {
				art.dialog({
				content : "请选择一条记录"
					});
				}
		}	
		
		function doExcel() {
			var url = "${ctx}/admin/region/doExcel";
			doExcelAll(url);
		} 	
	</script>
  </head>
  <body>
  	<div class="body-box-list">
  		<div class="query-form">
			<form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
				<input type="hidden" name="jsonString" value="" />
				<input type="hidden" id="expired"  name="expired" value="${region.expired }"/>
				<ul class="ul-form">
					<li><label>区域编号</label> <input type="text" id="code" name="code" /></li>
				    <li><label>区域名称</label> <input type="text" id="descr" name="descr" /></li>
				    <li class="search-group-shrink">
						<input type="checkbox"  id="expired_show" name="expired_show" onclick="hideExpired(this)" ${region.expired!='T'?'checked':''} />   
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
					<house:authorize authCode="REGION_VIEW">
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
 
