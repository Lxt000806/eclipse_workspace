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
	<title>项目经理质保金扣款规则管理配置</title>
	<%@ include file="/commons/jsp/common.jsp"%>
</head>
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
				<input type="hidden" id="expired" name="expired" value="${projectManQltFeeWithHoldRule.expired}"/>
				<input type="hidden" name="jsonString" value=""/>
				<ul class="ul-form">
					<li>
						<label>类型</label>
						<house:xtdm id="type" dictCode="WKQLTFEETYPE" />
					</li>
					<li>
						<label>项目经理类型</label>
						<house:xtdm id="isSupvr" dictCode="PRJMANTYPE" />
					</li>
					<li>
						<input type="checkbox" id="expired_show" name="expired_show" value="${projectManQltFeeWithHoldRule.expired}" 
							onclick="hideExpired(this)" ${projectManQltFeeWithHoldRule.expired!='T' ?'checked':'' }/>
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
				<house:authorize authCode="PROJECTMANQLTFEEWITHHOLDRULE_SAVE">
					<button type="button" class="btn btn-system" id="save">
						<span>新增</span>
					</button>
				</house:authorize>
				<house:authorize authCode="PROJECTMANQLTFEEWITHHOLDRULE_SAVE">
					<button type="button" class="btn btn-system" id="copy">
						<span>复制</span>
					</button>
				</house:authorize>
				<house:authorize authCode="PROJECTMANQLTFEEWITHHOLDRULE_UPDATE">
					<button type="button" class="btn btn-system" id="update">
						<span>编辑</span>
					</button>
				</house:authorize>
				<house:authorize authCode="PROJECTMANQLTFEEWITHHOLDRULE_DELETE">
					<button type="button" class="btn btn-system" id="delete">
						<span>删除</span>
					</button>
				</house:authorize>
				<house:authorize authCode="PROJECTMANQLTFEEWITHHOLDRULE_VIEW">
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
			Global.JqGrid.initJqGrid("dataTable", {
				url: "${ctx}/admin/projectManQltFeeWithHoldRule/goJqGrid",
				postData:postData ,
				sortable: true,
				height: $(document).height() - $("#content-list").offset().top - 70,
				styleUI: "Bootstrap", 
				colModel: [
					{name:"pk", index:"pk", width:60, label:"编号", sortable:true, align:"left",hidden:true},
					{name:"typedescr", index:"typedescr", width:80, label:"类型", sortable:true, align:"left"},
					{name:"qltfeefrom", index:"qltfeefrom", width:110, label:"起始质保金余额", sortable:true, align:"right"},
					{name:"qltfeeto", index:"qltfeeto", width:110, label:"截至质保金余额", sortable:true, align:"right"},
					{name:"commiamountfrom", index:"commiamountfrom", width:110, label:"起始提成金额", align:"right"},
					{name:"commiamountto", index:"commiamountto", width:110, label:"截至提成金额", sortable:true, align:"right"},
					{name:"issupvrdescr", index:"issupvrdescr", width:110, label:"项目经理类型", sortable:true, align:"left"},
					{name:"qltfeelimitamount", index:"qltfeelimitamount", width:110, label:"质保金上限金额", sortable:true, align:"right"},
					{name:"amount", index:"amount", width:80, label:"扣减额", sortable:true, align:"right"},
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
					title : "项目经理质保金扣款规则管理——新增",
					url : "${ctx}/admin/projectManQltFeeWithHoldRule/goSave",
					height:450,
					width : 445,
					returnFun : goto_query
				});
			});
			$("#copy").on("click", function() {
				var ret=selectDataTableRow();
				if (!ret) {
					art.dialog({
						content: "请选择一条记录"
					});
					return;
				}
				Global.Dialog.showDialog("copy", {
					title : "项目经理质保金扣款规则管理——复制",
					url : "${ctx}/admin/projectManQltFeeWithHoldRule/goSave",
					postData:{
						id:ret.pk
					},
					height:450,
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
					title:"项目经理质保金扣款规则管理——编辑",
					url:"${ctx}/admin/projectManQltFeeWithHoldRule/goUpdate",
					postData:{
						id:ret.pk
					},
					height:450,
					width:445,
					returnFun:goto_query
				});
			});
			$("#delete").on("click",function(){
				var url = "${ctx}/admin/projectManQltFeeWithHoldRule/doDelete";
				beforeDel(url,"pk","删除项目经理质保金扣款规则管理");
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
				title:"项目经理质保金扣款规则管理——查看",
				url:"${ctx}/admin/projectManQltFeeWithHoldRule/goDetail",
				postData:{
					id:ret.pk
				},
				height:400,
				width:445,
				returnFun:goto_query
			});
		}
		function doExcel(){
			var url = "${ctx}/admin/projectManQltFeeWithHoldRule/doExcel";
			doExcelAll(url);
		}
	</script>
</body>
</html>
