<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://" + request.getServerName() + ":"+request.getServerPort()+path+"/";
String jasperPath = PathUtil.WEB_INF+"jsp/jasper/";
%>
<!DOCTYPE html>
<html lang="zh">
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8"/>
	<META HTTP-EQUIV="pragma" CONTENT="no-cache"/>
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"/>
	<META HTTP-EQUIV="expires" CONTENT="0"/>
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge"/>
	<title>工人评价管理</title>
	<%@ include file="/commons/jsp/common.jsp"%>

	<script src="${resourceRoot}/pub/component_worker.js?v=${v}" type="text/javascript"></script>
</head>
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
				<input type="hidden" id="expired" name="expired" value="${workerEval.expired}"/>
				<input type="hidden" name="jsonString" value=""/>
				<ul class="ul-form">
					<li>
						<label>工人</label>
						<input type="text" id="workerCode" name="workerCode" style="width:160px;"/>
					</li>
					<li>
						<label>评价日期从</label>
						<input type="text" id="dateFrom" name="dateFrom" class="i-date" style="width:160px;" 
							onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
							value=""/>
					</li>
					<li>
						<label>至</label>
						<input type="text" id="dateTo" name="dateTo" class="i-date" style="width:160px;" 
							onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
							value=""/>
					</li>
					<li>
							<label>工种</label>
							<house:DictMulitSelect id="workType12" dictCode="" sql="select a.* from tWorkType12 a where  (
							(select PrjRole from tCZYBM where CZYBH='${czybm }') is null 
							or( select PrjRole from tCZYBM where CZYBH='${czybm }') ='' ) or  Code in(
								select WorkType12 From tprjroleworktype12 pr where pr.prjrole = 
								(select PrjRole from tCZYBM where CZYBH='${czybm }') or pr.prjrole = ''
								 )   " 
							sqlValueKey="Code" sqlLableKey="Descr"></house:DictMulitSelect>
						</li>
					<li>
						<input type="checkbox" id="expired_show" name="expired_show" value="${workerEval.expired}" 
							onclick="hideExpired(this)" ${workerEval.expired!='T' ?'checked':'' }/>
						<label for="expired_show" style="margin-left: -3px;width: 50px;">隐藏过期</label>
						<button type="button" class="btn btn-sm btn-system "
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
				<house:authorize authCode="WORKEREVAL_ADD">
					<button type="button" class="btn btn-system" id="save">
						<span>新增</span>
					</button>
				</house:authorize>
				<house:authorize authCode="WORKEREVAL_UPDATE">
					<button type="button" class="btn btn-system" id="update">
						<span>编辑</span>
					</button>
				</house:authorize>
				<house:authorize authCode="WORKEREVAL_DELETE">
					<button type="button" class="btn btn-system" id="delete">
						<span>删除</span>
					</button>
				</house:authorize>
				<house:authorize authCode="WORKEREVAL_VIEW">
					<button type="button" class="btn btn-system" id="view" onclick="view()">
						<span>查看</span>
					</button>
				</house:authorize>
				<house:authorize authCode="WORKEREVAL_MANAGE">
					<!-- 管理权限 -->
					<input type="hidden" name="manage" id="manage" value="1"/>
				</house:authorize>
				<button type="button" class="btn btn-system" onclick="doExcel()">
					<span>输出到Excel</span>
				</button>
			</div>
		</div>
		<div id="content-list">
			<table id="dataTable"></table>
			<div id="dataTablePager"></div>
		</div>
	</div>
<script type="text/javascript" defer>
var postData = $("#page_form").jsonForm();
var manage = $("#manage").val()=="1"?"1":"0"; //管理权限
var czybh = "${sessionScope.USER_CONTEXT_KEY.czybh}";
$(function(){
	$("#workerCode").openComponent_worker();

	Global.JqGrid.initJqGrid("dataTable", {
		url: "${ctx}/admin/workerEval/goJqGrid",
		postData: postData,
		sortable: true,
		height: $(document).height() - $("#content-list").offset().top - 70,
		styleUI: "Bootstrap", 
		colModel: [
			{name:"pk", index:"pk", width:80, label:"pk", sortable:true, align:"left",hidden:true},
			{name:"custcode", index:"custcode", width:80, label:"客户编号", sortable:true, align:"left"},
			{name:"address", index:"address", width:160, label:"楼盘地址", sortable:true, align:"left"},
			{name:"workType12", index:"workType12", width:80, label:"工种", sortable:true, align:"left",hidden:true},
			{name:"worktype12descr", index:"worktype12descr", width:80, label:"工种", sortable:true, align:"left"},
			{name:"custname", index:"custname", width:80, label:"客户姓名", sortable:true, align:"left", hidden:true},
			{name:"workercode", index:"workercode", width:80, label:"工人编号", sortable:true, align:"left"},
			{name:"workerdescr", index:"workerdescr", width:80, label:"工人姓名", sortable:true, align:"left"},
			{name:"custwkpk", index:"custwkpk", width:80, label:"工地工人pk", sortable:true, align:"left"},
			{name:"date", index:"date", width:125, label:"评价时间", sortable:true, align:"left", formatter:formatTime}, 
			{name:"type", index:"type", width:80, label:"来源", sortable:true, align:"left", hidden:true}, 
			{name:"typedescr", index:"typedescr", width:80, label:"来源", sortable:true, align:"left"}, 
			{name:"evaman", index:"evaman", width:80, label:"评价人", sortable:true, align:"left", hidden:true},
			{name:"evamandescr", index:"evamandescr", width:80, label:"评价人", sortable:true, align:"left"},
			{name:"healthscore", index:"healthscore", width:80, label:"卫生评分", sortable:true, align:"right"},
			{name:"toolscore", index:"toolscore", width:80, label:"工具评分", sortable:true, align:"right"},
			{name:"score", index:"score", width:80, label:"综合评分", sortable:true, align:"right"},
			{name:"remark", index:"remark", width:200, label:"备注", sortable:true, align:"left"}, 
			{name:"evalworker", index:"evalworker", width:80, label:"评价工人", sortable:true, align:"left", hidden:true},
			{name:"evalworkerdescr", index:"evalworkerdescr", width:80, label:"评价工人", sortable:true, align:"left"},
			{name:"lastupdate", index:"lastupdate", width:125, label:"最后修改时间", sortable:true, align:"left", formatter:formatTime}, 
			{name:"expired", index:"expired", width:70, label:"是否过期", sortable:true, align:"left"}, 
			{name:"actionlog", index:"actionlog", width:70, label:"操作日志", sortable:true, align:"left"}, 
		],
		ondblClickRow: function(){
			view();
		}
	});
	
	$("#save").on("click", function() {
		Global.Dialog.showDialog("save", {
			title : "工人评价管理——新增",
			url : "${ctx}/admin/workerEval/goSave",
			height : 550,
			width : 537,
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
		if (0 == manage) {
			if (!(czybh == ret.evaman || czybh == ret.evalworker)) {
				art.dialog({content:"最后修改人为非本人记录"});
				return;
			}
		}
		Global.Dialog.showDialog("update",{
			title:"工人评价管理——编辑",
			url:"${ctx}/admin/workerEval/goUpdate",
			postData:{
				m_umState:"M",
				PK:ret.pk,
				expired:"T",
			},
			height:590,
			width:537,
			returnFun:goto_query
		});
	});
	
	$("#delete").on("click",function(){
		var ret = selectDataTableRow();
		if (!ret) {
			art.dialog({
				content: "请选择一条记录"
			});
			return;
		}
		if (0 == manage) {
			if (!(czybh == ret.evaman || czybh == ret.evalworker)) {
				art.dialog({content:"最后修改人为非本人记录"});
				return;
			}
		}
		art.dialog({
			content:"您确定要删除该信息吗？",
			lock: true,
			width: 200,
			height: 100,
			ok: function () {
				$.ajax({
					url:"${ctx}/admin/workerEval/doDelete",
					data:{PK:ret.pk},
					contentType:"application/x-www-form-urlencoded; charset=UTF-8",
					dataType:"json",
					type:"post",
					cache:false,
					error:function(){
						art.dialog({
							content:"删除该信息出现异常"
						});
					},
					success:function(obj){
						if(obj.rs){
							art.dialog({
								content:obj.msg,
								time:1000,
								beforeunload: function () {
									goto_query();
								}
							});
						}else{
							art.dialog({
								content:obj.msg
							});
						}
					}
				});
				return true;
			},
			cancel: function () {
				return true;
			}
		});
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
		title:"工人评价管理——查看",
		url:"${ctx}/admin/workerEval/goView",
		postData:{
			m_umState:"V",
			PK:ret.pk,
			expired:"T",
		},
		height:590,
		width:537,
		returnFun:goto_query
	});
}

function doExcel(){
	var url = "${ctx}/admin/workerEval/doExcel";
	doExcelAll(url);
}

function clearForm(){
		$("#page_form input[type='text']").val('');
		$("#workType12").val('');
		$.fn.zTree.getZTreeObj("tree_workType12").checkAllNodes(false);
	} 
</script>
</body>
</html>
