<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<!DOCTYPE HTML>
<html>
<head>
    <title>业绩计算</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script type="text/javascript">
	function add(){
		Global.Dialog.showDialog("add",{
			title:"业绩统计周期--增加",
			url:"${ctx}/admin/perfCycle/goSave",
			height: 450,
		 	width:700,
			returnFun: goto_query 
		});
	}
	
	function updatePeriod(){
		var ret =selectDataTableRow();
		var newStatus=checkStatus();
		if(!ret){
			art.dialog({
				content:"请选择一条数据",
			});
			return;
		}
		if(newStatus!=ret.status){
			art.dialog({
				content:"状态发生改变，请刷新数据！",
			});
			return;
		}
		if(ret.status=="2"){
			art.dialog({
				content:"该业绩统计周期已计算完成，无法编辑！",
			});
			return;
		}
		Global.Dialog.showDialog("updatePeriod",{
			title:"业绩统计周期--编辑",
			postData:{id:ret.no},
			url:"${ctx}/admin/perfCycle/goUpdate",
			height: 450,
		 	width:700,
			returnFun: goto_query 
		});
	}
	
	function report(){
		var ret =selectDataTableRow();
		if(!ret){
			art.dialog({
				content:"请选择一条数据",
			});
			return;
		}
		
		Global.Dialog.showDialog("report",{
			title:"报表",
			url:"${ctx}/admin/perfCycle/goReport",
			height: 750,
		 	width:1350,
			returnFun: goto_query 
		});
	}
	
	function count(){
		var ret =selectDataTableRow();
		var newStatus=checkStatus();
		if(!ret){
			art.dialog({
				content:"请选择一条数据",
			});
			return;
		}
		if(newStatus!=ret.status){
			art.dialog({
				content:"状态发生改变，请刷新数据！",
			});
			return;
		}
		if(ret.status=="2"){
			art.dialog({
				content:"该业绩统计周期已计算完成,无法进行业绩计算操作",
			});
			return;
		}
		$.ajax({
			url:"${ctx}/admin/perfCycle/isExistsPeriod",
			type: "post",
			data: {no:ret.no},
			dataType: "text",
			cache: false,
			error: function(obj){
				showAjaxHtml({"hidden": false, "msg": "保存数据出错~"});
		    },
		    success: function(obj){
				if(obj!=null && obj!=""){
					var msg="";
					if("1"==obj){
						msg="没有找到相应的业绩统计周期或该统计周期已计算完成!";
					}else{
						msg="之前的统计周期未计算完成，不允许计算本周期业绩!";
					}
					art.dialog({
						content:msg,
					});
					return;
				}else{
					Global.Dialog.showDialog("count",{
						title:"业绩计算",
						postData:{no:ret.no,endDate:ret.enddate},
						url:"${ctx}/admin/perfCycle/goCount",
						height: 750,
		 				width:1350,
						returnFun: goto_query 
					});
				}
		   	} 
		 });
	}
	
	function view(){
		var ret =selectDataTableRow();
		if(!ret){
			art.dialog({
				content:"请选择一条数据",
			});
			return;
		}
		Global.Dialog.showDialog("count",{
			title:"业绩计算",
			postData:{no:ret.no,m_umState:"V",status:ret.status},
			url:"${ctx}/admin/perfCycle/goView",
			height: 750,
		 	width:1350,
			returnFun: goto_query 
		});
	}
	function employInfo(){
		Global.Dialog.showDialog("employInfo",{
			title:"员工信息同步",
			url:"${ctx}/admin/perfCycle/goEmployeeInfo",
			height: 750,
		 	width:1350,
			returnFun: goto_query 
		});
	}
	function countComplete(){
		var ret =selectDataTableRow();
		var newStatus=checkStatus();
		var resultCount=checkEmployeeInfo();
		var tipContent="确定该业绩统计周期已全部计算完成吗？";
		if(resultCount>0){
			tipContent="业绩计算使用的员工信息与当前员工信息不一致，是否确认完成？";
		}
		if(!ret){
			art.dialog({
				content:"请选择一条数据",
			});
			return;
		}
		if(newStatus!=ret.status){
			art.dialog({
				content:"状态发生改变，请刷新数据！",
			});
			return;
		}
		if(ret.status=="2"){
			art.dialog({
				content:"该业绩统计周期已计算完成,无法再进行计算完成操作！",
			});
			return;
		}
		art.dialog({
			content:tipContent,
			lock: true,
			ok: function () {
				$.ajax({
					url:"${ctx}/admin/perfCycle/doComplete",
					type:"post",
					data:{no:ret.no},
					dataType:"json",
					cache:false,
					error:function(obj){
						showAjaxHtml({"hidden": false, "msg": "获取数据出错~"});
					},
					success:function(obj){
						$("#dataTable").jqGrid("setGridParam",{postData:$("#page_form").jsonForm(),page:1}).trigger("reloadGrid");
					}
				});
			},
			cancel: function () {
					return true;
			}
		});	
	}
	
	function back(){
		var ret =selectDataTableRow();
		var newStatus=checkStatus();
		if(!ret){
			art.dialog({
				content:"请选择一条数据",
			});
			return;
		}
		if(newStatus!=ret.status){
			art.dialog({
				content:"状态发生改变，请刷新数据！",
			});
			return;
		}
		if(ret.status=="1"){
			art.dialog({
				content:"该统计周期未计算完成，无法回退操作！",
			});
			return;
		}
		art.dialog({
			content:"确定要回退该业绩统计周期吗？",
			lock: true,
			ok: function () {
				$.ajax({
					url:"${ctx}/admin/perfCycle/doReturn",
					type:"post",
					data:{no:ret.no},
					dataType:"json",
					cache:false,
					error:function(obj){
						showAjaxHtml({"hidden": false, "msg": "获取数据出错~"});
					},
					success:function(obj){
						$("#dataTable").jqGrid("setGridParam",{postData:$("#page_form").jsonForm(),page:1}).trigger("reloadGrid");
					}
				});
			},
			cancel: function () {
					return true;
			}
		});	
	}
	
	function signData(){
		Global.Dialog.showDialog("signData",{
			title:"签单数据统计",
			url:"${ctx}/admin/perfCycle/goSignData",
			height: 720,
		 	width:1250,
		});
	} 

	//清空
	function clearForm(){
		$("#page_form input[type='text']").val("");
		$("select").val("");
	} 
	/**初始化表格*/
	$(function(){
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/perfCycle/goJqGrid",
			height:$(document).height()-$("#content-list").offset().top-100,
			styleUI: "Bootstrap", 
			colModel : [
				{name: "no", index: "no", width: 100, label: "统计周期编号", sortable: true, align: "left"},
				{name: "y", index: "y", width: 80, label: "归属年份", sortable: true, align: "right"},
				{name: "m", index: "m", width: 80, label: "归属月份", sortable: true, align: "right"},
				{name: "season", index: "season", width: 80, label: "归属季度", sortable: true, align: "right"},
				{name: "begindate", index: "begindate", width: 90, label: "开始时间", sortable: true, align: "left", formatter: formatDate},
				{name: "enddate", index: "enddate", width: 90, label: "结束时间", sortable: true, align: "left", formatter: formatDate},
				{name: "status", index: "status", width: 80, label: "状态", sortable: true, align: "left",hidden:true},
				{name: "statusdescr", index: "statusdescr", width: 80, label: "状态", sortable: true, align: "left"},
				{name: "contractfee", index: "contractfee", width: 100, label: "合同总额", sortable: true, align: "right", sum: true},
				{name: "contractanddesignfee", index: "contractanddesignfee", width: 120, label: "合同总价+设计费", sortable: true, align: "right", sum: true},
				{name: "recalperf", index: "recalperf", width: 100, label: "实际业绩", sortable: true, align: "right", sum: true},
				{name: "remarks", index: "remarks", width: 200, label: "备注", sortable: true, align: "left"},
				{name: "lastupdate", index: "lastupdate", width: 120, label: "最后更新时间", sortable: true, align: "left", formatter: formatTime},
				{name: "lastupdatedby", index: "lastupdatedby", width: 101, label: "最后更新人员", sortable: true, align: "left"},
				{name: "expired", index: "expired", width: 74, label: "是否过期", sortable: true, align: "left"},
				{name: "actionlog", index: "actionlog", width: 76, label: "操作日志", sortable: true, align: "left"}
			],
			ondblClickRow: function(){
				view();
			}
		});
		$("#y,#m,#season").append($("<option/>").text("请选择...").attr("value",""));
		for(var i=2014;i<=2100;i++){
			$("#y").append($("<option/>").text(i).attr("value",i));
		}
		for(var i=1;i<=14;i++){
			$("#m").append($("<option/>").text(i).attr("value",i));
		}
		for(var i=1;i<=6;i++){
			$("#season").append($("<option/>").text(i).attr("value",i));
		}
	});
	function doExcel(){
		var url = "${ctx}/admin/perfCycle/doExcel";
		doExcelAll(url);
	}
	function checkStatus(){
		var status="";
		var ret =selectDataTableRow();
		$.ajax({
			url:"${ctx}/admin/perfCycle/checkStatus",
			type:"post",
			data:{no:ret.no},
			dataType:"json",
			cache:false,
			async:false,
			error:function(obj){
				showAjaxHtml({"hidden": false, "msg": "获取数据出错~"});
			},
			success:function(obj){
				status=obj;
			}
		});
		return status;
	}
	function checkEmployeeInfo(){
		var resultCount=0;
		$.ajax({
			url:"${ctx}/admin/perfCycle/checkEmployeeInfo",
			type:"post",
			dataType:"json",
			cache:false,
			async:false,
			error:function(obj){
				showAjaxHtml({"hidden": false, "msg": "获取数据出错~"});
			},
			success:function(list){
				resultCount=list.length;
			}
		});
		return resultCount;
	}
	</script>
</head>
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form role="form" class="form-search" id="page_form" action=""
				method="post" target="targetFrame">
				<input type="hidden" name="jsonString" value="" />
				<ul class="ul-form">
					<li><label>归属年份</label> <select id="y" name="y"></select>
					</li>
					<li><label>归属月份</label> <select id="m" name="m"></select>
					</li>
					<li><label>归属季度</label> <select id="season" name="season"></select>
					</li>
					<li><label>统计周期编号</label> <input type="text" id="no" name="no"
						style="width:160px;" />
					</li>
					<li>
						<button type="button" class="btn btn-sm btn-system"
							onclick="goto_query();">查询</button>
						<button type="button" class="btn btn-sm btn-system"
							onclick="clearForm();">清空</button>
					</li>
				</ul>
			</form>
		</div>
		<div class="pageContent">
			<div class="btn-panel">
				<div class="btn-group-sm">
					<house:authorize authCode="PERFCYCLE_PERFCOUNT">
						<button type="button" class="btn btn-system" onclick="count()">
							<span>业绩计算</span>
						</button>
					</house:authorize>
					<house:authorize authCode="PERFCYCLE_COMPLETE">
						<button type="button" class="btn btn-system"
							onclick="countComplete()">
							<span>计算完成</span>
						</button>
					</house:authorize>
					<house:authorize authCode="PERFCYCLE_RETURN">
						<button type="button" class="btn btn-system" onclick="back()">
							<span>回退</span>
						</button>
					</house:authorize>
					<house:authorize authCode="PERFCYCLE_VIEW">
						<button type="button" class="btn btn-system" onclick="view()">
							<span>查看</span>
						</button>
					</house:authorize>
					<button type="button" class="btn btn-system" onclick="employInfo()">
							<span>员工信息同步</span>
					</button>
					<house:authorize authCode="PERFCYCLE_REPORT">
						<button type="button" class="btn btn-system" onclick="report()">
							<span>报表</span>
						</button>
					</house:authorize>
					<house:authorize authCode="PERFCYCLE_ADD">
						<button type="button" class="btn btn-system" onclick="add()">
							<span>新增统计周期</span>
						</button>
					</house:authorize>
					<house:authorize authCode="PERFCYCLE_UPDATE">
						<button type="button" class="btn btn-system"
							onclick="updatePeriod()">
							<span>编辑</span>
						</button>
					</house:authorize>
					<house:authorize authCode="PERFCYCLE_SIGNDATA">
						<button type="button" class="btn btn-system"
							onclick="signData()">
							<span>签单数据统计</span>
						</button>
					</house:authorize>
						<button type="button" class="btn btn-system" onclick="doExcel()"
							title="导出检索条件数据">
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
