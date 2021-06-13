<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<!DOCTYPE html>
<html>
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8"/>
	<META HTTP-EQUIV="pragma" CONTENT="no-cache"/>
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"/>
	<META HTTP-EQUIV="expires" CONTENT="0"/>
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge"/>
	<title>工期计算规则</title>
	<%@ include file="/commons/jsp/common.jsp"%>
</head>
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
				<input type="hidden" id="expired" name="expired" value="${conDayCalcRule.expired}"/>
				<input type="hidden" name="jsonString" value=""/>
				<ul class="ul-form">
					<li>
						<label>工人分类</label>
						<house:xtdm id="workerClassify" dictCode="WORKERCLASSIFY" style="width:160px;" />
					</li>
					<li>	
						<label>工种分类12</label>
						<select id="workType12" name="workType12" ></select>
					</li>
					<li>
						<label>计算类型</label>
						<house:xtdm id="type" dictCode="DAYCALCTYPE" style="width:160px;" />
					</li>
					<li>
						<input type="checkbox" id="expired_show" name="expired_show" value="${conDayCalcRule.expired}" 
							onclick="hideExpired(this)" ${conDayCalcRule.expired!='T' ?'checked':'' }/>
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
				<house:authorize authCode="CONDAYCALCRULE_SAVE">
					<button type="button" class="btn btn-system" id="save">
						<span>新增</span>
					</button>
				</house:authorize>
				<house:authorize authCode="CONDAYCALCRULE_UPDATE">
					<button type="button" class="btn btn-system" id="update">
						<span>编辑</span>
					</button>
				</house:authorize>
				<house:authorize authCode="CONDAYCALCRULE_DEL">
					<button type="button" class="btn btn-system" id="delete">
						<span>删除</span>
					</button>
				</house:authorize>
				<house:authorize authCode="CONDAYCALCRULE_VIEW">
					<button type="button" class="btn btn-system" id="view" onclick="view()">
						<span>查看</span>
					</button>
				</house:authorize>
				<button type="button" class="btn btn-system" onclick="doExcel()">
					<span>导出excel</span>
				</button>
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
			Global.LinkSelect.initSelect("${ctx}/admin/worker/workType12","workType12","workType12Dept");/* 区域下拉栏联动 */
	
			Global.JqGrid.initJqGrid("dataTable", {
				url: "${ctx}/admin/conDayCalcRule/goJqGrid",
				postData:postData ,
				sortable: true,
				height: $(document).height() - $("#content-list").offset().top - 70,
				styleUI: "Bootstrap", 
				colModel: [
					{name:"pk", index:"pk", width:60, label:"编号", sortable:true, align:"left",hidden:true},
					{name:"workerclassifydescr", index:"workerclassifydescr", width:80, label:"工人分类", sortable:true, align:"left"},
					{name:"worktype12descr", index:"worktype12descr", width:85, label:"工种分类12", sortable:true, align:"left"},
					{name:"typedescr", index:"typedescr", width:80, label:"计算类型", sortable:true, align:"left"},
					{name:"baseconday", index:"baseconday", width:80, label:"基础天数", sortable:true, align:"right"},
					{name:"basework", index:"basework", width:80, label:"基础工作量", sortable:true, align:"right"},
					{name:"daywork", index:"daywork", width:80, label:"每日工作量", sortable:true, align:"right"},
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
					title : "工期计算规则——新增",
					url : "${ctx}/admin/conDayCalcRule/goSave",
					height : 400,
					width : 445,
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
					title:"工期计算规则——编辑",
					url:"${ctx}/admin/conDayCalcRule/goUpdate",
					postData:{
						id:ret.pk
					},
					height:400,
					width:445,
					returnFun:goto_query
				});
			});
			$("#delete").on("click",function(){
				var url = "${ctx}/admin/conDayCalcRule/doDelete";
				beforeDel(url,"pk","删除该工期计算规则的信息");
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
				title:"工期计算规则——查看",
				url:"${ctx}/admin/conDayCalcRule/goDetail",
				postData:{
					id:ret.pk
				},
				height:400,
				width:445,
				returnFun:goto_query
			});
		}
		function doExcel(){
			var url = "${ctx}/admin/conDayCalcRule/doExcel";
			doExcelAll(url);
		}
	</script>
</body>
</html>
