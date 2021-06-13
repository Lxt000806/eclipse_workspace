<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<!DOCTYPE html>
<html lang="zh">
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8"/>
	<META HTTP-EQUIV="pragma" CONTENT="no-cache"/>
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"/>
	<META HTTP-EQUIV="expires" CONTENT="0"/>
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge"/>
	<title>施工项目1管理</title>
	<%@ include file="/commons/jsp/common.jsp"%>
</head>
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
				<input type="hidden" id="expired" name="expired" value="${prjItem1.expired}"/>
				<input type="hidden" name="jsonString" value=""/>
				<ul class="ul-form">
					<li>
						<label>编码</label> 
						<input type="text" id="code" name="code" style="width:160px;" value=""/>
					</li>
					<li>
						<label>名称</label> 
						<input type="text" id="descr" name="descr" style="width:160px;" value=""/>
					</li>
					<li>
						<input type="checkbox" id="expired_show" name="expired_show" value="${prjItem1.expired}" 
							onclick="hideExpired(this)" ${prjItem1.expired!='T' ?'checked':'' }/>
						<label for="expired_show" style="margin-left: -3px;width: 50px;">隐藏过期</label>
						<button type="button" class="btn  btn-sm btn-system "
							onclick="goto_query();">查询</button>
						<button type="button" class="btn btn-sm btn-system "
							onclick="clearForm();">清空</button>
					</li>
				</ul>
			</form>
		</div>
	</div>
	<div class="pageContent">
		<div class="btn-panel">
			<div class="btn-group-sm">
				<button type="button" class="btn btn-system" id="save">
					<span>新增</span>
				</button>
				<button type="button" class="btn btn-system" id="update">
					<span>编辑</span>
				</button>
				<button type="button" class="btn btn-system" id="delete">
					<span>删除</span>
				</button>
				<house:authorize authCode="prjItem1_VIEW">
					<button type="button" class="btn btn-system" id="view" onclick="view()">
						<span>查看</span>
					</button>
				</house:authorize>
				<house:authorize authCode="prjItem1_EXCEL">
					<button type="button" class="btn btn-system" onclick="doExcel()">
						<span>导出excel</span>
					</button>
				</house:authorize>
			</div>
		</div>
		<div id="content-list">
			<table id="dataTable"></table>
			<div id="dataTablePager"></div>
		</div>
	</div>
	<script type="text/javascript">
		var postData = $("#page_form").jsonForm();
		$(function(){
			Global.JqGrid.initJqGrid("dataTable", {
				url: "${ctx}/admin/prjItem1/goJqGrid",
				postData:postData ,
				sortable: true,
				height: $(document).height() - $("#content-list").offset().top - 70,
				styleUI: "Bootstrap", 
				colModel: [
					{name:"code", index:"code", width:60, label:"编号", sortable:true, align:"left"},
					{name:"descr", index:"descr", width:150, label:"名称", sortable:true, align:"left"},
					{name:"seq", index:"seq", width:80, label:"节点顺序", sortable:true, align:"right"},
					{name:"iscomfirmdescr", index:"iscomfirmdescr", width:80, label:"是否验收", sortable:true, align:"left"},
					{name:"worktype12descr", index:"worktype12descr", width:80, label:"工种分类", sortable:true, align:"left"},
					{name:"prjphotonum", index:"prjphotonum", width:80, label:"进度相片数", sortable:true, align:"right"},
					{name:"isfirstpassdescr", index:"isfirstpassdescr", width:130, label:"是否必须通过初检", sortable:true, align:"left"},
					{name:"firstaddconfirmdescr", index:"firstaddconfirmdescr", width:110, label:"初检触发验收", sortable:true, align:"left"},
					{name:"signtimes", index:"signtimes", width:130, label:"项目经理应签到次数", sortable:true, align:"right"},
					{name:"designsigntimes", index:"designsigntimes", width:130, label:"设计师应签到次数", sortable:true, align:"right"},
					{name:"isfirstcompletedescr", index:"isfirstcompletedescr", width:130, label:"是否初检标记完工", sortable:true, align:"left"},
		    		{name:"lastupdate", index:"lastupdate", width:120, label:"最后修改时间", sortable:true, align:"left", formatter:formatTime},
		      		{name:"lastupdatedby", index:"lastupdatedby", width:70, label:"修改人", sortable:true, align:"left"},
		     		{name:"expired", index:"expired", width:80, label:"是否过期", sortable:true, align:"left"},
		    		{name:"actionlog", index:"actionlog", width:60, label:"操作", sortable:true, align:"left"},
				],
				ondblClickRow: function(){
					view();
				}
			});
			$("#save").on("click", function() {
				Global.Dialog.showDialog("save", {
					title : "施工项目1——新增",
					url : "${ctx}/admin/prjItem1/goSave",
					height : 550,
					width : 800,
					returnFun : goto_query
				});
			});
			$("#update").on("click",function(){
				var ret=selectDataTableRow();
				if (!ret) {
					art.dialog({
						content: "请选择一条记录"
					});
					return;
				}
				Global.Dialog.showDialog("update",{
					title:"施工项目1——编辑",
					url:"${ctx}/admin/prjItem1/goUpdate",
					postData:{
						id:ret.code
					},
					height:550,
					width:800,
					returnFun:goto_query
				});
			});
			$("#delete").on("click",function(){
				var url = "${ctx}/admin/prjItem1/doDelete";
				beforeDel(url,"code","删除该施工项目1的信息");
				returnFun: goto_query;
				return true;
			});
		});
		function view(){
			var ret=selectDataTableRow();
			if (!ret) {
				art.dialog({
					content: "请选择一条记录"
				});
				return;
			}
			Global.Dialog.showDialog("view",{
				title:"施工项目1——查看",
				url:"${ctx}/admin/prjItem1/goDetail",
				postData:{
					id:ret.code
				},
				height:550,
				width:800,
				returnFun:goto_query
			});
		}
		function doExcel(){
			var url = "${ctx}/admin/prjItem1/doExcel";
			doExcelAll(url);
		}
	</script>
</body>
</html>
