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
    <title>业绩计算</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_prjPerf.js?v=${v}" type="text/javascript"></script>

	<script type="text/javascript">
	function addPeriod(){
		Global.Dialog.showDialog("addPeriod",{
			title:"新增周期统计",
			url:"${ctx}/admin/prjPerf/goAddPeriod",
			height: 450,
		 	width:650,
			returnFun: goto_query 
		});
	}
	
	function updatePeriod(){
		var ret =selectDataTableRow();
		if(!ret){
			art.dialog({
				content:"请选择一条数据",
			});
			return;
		}
		Global.Dialog.showDialog("updatePeriod",{
			title:"编辑周期统计",
			postData:{no:ret.no},
			url:"${ctx}/admin/prjPerf/goUpdatePeriod",
			height: 450,
		 	width:650,
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
			url:"${ctx}/admin/prjPerf/goReport",
			postData:{no:ret.no},
			height: 720,
		 	width:1250,
			returnFun: goto_query 
		});
	}
	
	function goCount(){
		var ret =selectDataTableRow();
		if(!ret){
			art.dialog({
				content:"请选择一条数据",
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
			url:"${ctx}/admin/prjPerf/getNotify",
			type: "post",
			data: {no:ret.no,beginDate:ret.begindate,status:ret.status},
			dataType: "json",
			cache: false,
			error: function(obj){
				showAjaxHtml({"hidden": false, "msg": "保存数据出错~"});
		    },
		    success: function(obj){
				if(!obj.rs){
					art.dialog({
						content:obj.msg,
					});
					return;
				}else{
					Global.Dialog.showDialog("count",{
						title:"业绩计算",
						postData:{no:ret.no},
						url:"${ctx}/admin/prjPerf/goCount",
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
			title:"业绩计算——查看",
			postData:{no:ret.no},
			url:"${ctx}/admin/prjPerf/goView",
			height: 750,
		 	width:1350,
			returnFun: goto_query 
		});
	}
	
	function countComplete(){
		var ret =selectDataTableRow();
		if(!ret){
			art.dialog({
				content:"请选择一条数据",
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
			url:"${ctx}/admin/prjPerf/getNotify",
			type: "post",
			data: {no:ret.no,status:ret.status},
			dataType:"json",
			cache:false,
			error:function(obj){
				showAjaxHtml({"hidden": false, "msg": "获取数据出错~"});
			},
			success:function(obj){
				if(!obj.rs){
					art.dialog({
						content:obj.msg,
					});
					return;
				}else{
					art.dialog({
						content:"确定该业绩统计周期已全部计算完成吗?",
						lock: true,
						width: 200,
						height: 100,
						ok: function () {
							$.ajax({
								url:"${ctx}/admin/prjPerf/doSaveCount",
								type:"post",
								data:{no:ret.no},
								dataType:"json",
								cache:false,
								error:function(obj){
									showAjaxHtml({"hidden": false, "msg": "获取数据出错~"});
								},
								success:function(obj){
									art.dialog({
										content:'操作成功',
									});
									$("#dataTable").jqGrid("setGridParam",{postData:$("#page_form").jsonForm(),page:1}).trigger("reloadGrid");
								}
							});
						},
						cancel: function () {
							return true;
						}
					});	
				}
			}
		});
	}
	
	function back(){
		var ret =selectDataTableRow();
		if(!ret){
			art.dialog({
				content:"请选择一条数据",
			});
			return;
		}
		if(ret.status=="1"){
			art.dialog({
				content:"该统计周期未计算完成，无法回退操作！",
			});
			return;
		}
		$.ajax({
			url:"${ctx}/admin/prjPerf/checkStatus",
			type:"post",
			data:{no:ret.no},
			dataType:"json",
			cache:false,
			error:function(obj){
				showAjaxHtml({"hidden": false, "msg": "获取数据出错~"});
			},
			success:function(obj){
				if(ret.status!=obj){
					art.dislog({
						content:"状态发生改变，请刷新数据!",
					});
					return;
				}else{
					art.dialog({
						content:"确定要回退该业绩统计周期吗？",
						lock: true,
						width: 200,
						height: 100,
						ok: function () {
							$.ajax({
								url:"${ctx}/admin/prjPerf/doSaveCountBack",
								type:"post",
								data:{no:ret.no},
								dataType:"json",
								cache:false,
								error:function(obj){
									showAjaxHtml({"hidden": false, "msg": "获取数据出错~"});
								},
								success:function(obj){
									art.dialog({
										content:"退回完成",
									});
									$("#dataTable").jqGrid("setGridParam",{postData:$("#page_form").jsonForm(),page:1}).trigger("reloadGrid");
								}
							});
						},
						cancel: function () {
							return true;
						}
					});	
				}
			}
		});
	}
	
	function doPerfChg (){
		var ret =selectDataTableRow();
		if(!ret){
			art.dialog({
				content:"请选择一条数据",
			});
			return;
		}
		if($.trim(ret.status)=="2"){
			art.dialog({
				content:"该业绩统计周期已计算完成,无法进行业绩计算操作",
			});
			return;
		}
		$.ajax({
			url:"${ctx}/admin/prjPerf/getNotify",
			type: "post",
			data: {no:ret.no,beginDate:ret.begindate,status:ret.status},
			dataType:"json",
			cache:false,
			error:function(obj){
				showAjaxHtml({"hidden": false, "msg": "获取数据出错~"});
			},
			success:function(obj){
				if(!obj.rs){
					art.dialog({
						content:obj.msg,
					});
					return;
				}else{
					art.dialog({
						content:"确定要将本周期结算的客户增减单设置为计算业绩吗?",
						lock: true,
						width: 200,
						height: 100,
						ok: function () {
							$.ajax({
								url:"${ctx}/admin/prjPerf/doPerfChg",
								type:"post",
								data:{no:ret.no},
								dataType:"json",
								cache:false,
								error:function(obj){
									showAjaxHtml({"hidden": false, "msg": "获取数据出错~"});
								},
								success:function(obj){
									art.dialog({
										content:'设置完成',
									});
									$("#dataTable").jqGrid("setGridParam",{postData:$("#page_form").jsonForm(),page:1}).trigger("reloadGrid");
								}
							});
						},
						cancel: function () {
							return true;
						}
					});	
				}
			}
		});
	}
	
	//清空
	function clearForm(){
		$("#page_form input[type='text']").val("");
		$("#openComponent_prjPerf_no").val("");
		$("#page_form select").val("");
		$("#no").val("");
	} 
	/**初始化表格*/
	$(function(){
		$("#no").openComponent_prjPerf();
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/prjPerf/goJqGrid",
			height:$(document).height()-$("#content-list").offset().top-70,
			styleUI: "Bootstrap", 
			colModel : [
				{name: "no", index: "no", width: 109, label: "统计周期编号", sortable: true, align: "left"},
				{name: "y", index: "y", width: 92, label: "归属年份", sortable: true, align: "right"},
				{name: "m", index: "m", width: 92, label: "归属月份", sortable: true, align: "right"},
				{name: "season", index: "season", width: 86, label: "归属季度", sortable: true, align: "right"},
				{name: "begindate", index: "begindate", width: 90, label: "开始时间", sortable: true, align: "left", formatter: formatDate},
				{name: "enddate", index: "enddate", width: 90, label: "结束时间", sortable: true, align: "left", formatter: formatDate},
				{name: "status", index: "status", width: 90, label: "状态", sortable: true, align: "left",hidden:true},
				{name: "statusdescr", index: "statusdescr", width: 110, label: "状态", sortable: true, align: "left"},
				{name: "remarks", index: "remarks", width: 200, label: "备注", sortable: true, align: "left"},
				{name: "lastupdate", index: "lastupdate", width: 151, label: "最后更新时间", sortable: true, align: "left", formatter: formatTime},
				{name: "lastupdatedby", index: "lastupdatedby", width: 101, label: "最后更新人员", sortable: true, align: "left"},
				{name: "expired", index: "expired", width: 74, label: "是否过期", sortable: true, align: "left"},
				{name: "actionlog", index: "actionlog", width: 76, label: "操作日志", sortable: true, align: "left"}
			],
			ondblClickRow: function(){
				view();
			}
		});
	});
	function doExcel(){
		var url = "${ctx}/admin/prjPerf/doExcel";
		doExcelAll(url);
	}
	</script>
</head>
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
				<input type="hidden" name="jsonString" value=""/>
				<ul class="ul-form">
					<li class="form-validate">
						<label>归属年份</label>
						<house:dict id="y" dictCode="" sql="select p.number+53 code ,p.number+53 descr from master..spt_values p 
						where p.type = 'p' and p.number between 1957 and 2048 " sqlValueKey="code" sqlLableKey="descr" value="${y }"></house:dict>
					</li> 
					<li class="form-validate">
						<label>归属月份</label>
						<house:dict id="m" dictCode="" sql="select p.number code ,p.number descr from master..spt_values p 
							where p.type = 'p' and p.number between 1 and 13 " sqlValueKey="code" sqlLableKey="descr" value="${m }"></house:dict>
					</li>	
					<li class="form-validate">
						<label>归属季度</label>
							<house:dict id="season" dictCode="" sql="select p.number code ,p.number descr from master..spt_values p 
							where p.type = 'p' and p.number between 1 and 6 " sqlValueKey="code" sqlLableKey="descr"></house:dict>
					</li>
					<li>
						<label>统计周期编号</label>
						<input type="text" id="no" name="no" style="width:160px;"/>
					</li>
					<li>
						<button type="button" class="btn btn-sm btn-system" onclick="goto_query();">查询</button>
						<button type="button" class="btn btn-sm btn-system" onclick="clearForm();">清空</button>
					</li>
				</ul>	
			</form>
		</div>
		<div class="pageContent">
			<div class="btn-panel">
    			<div class="btn-group-sm">
					<button type="button" class="btn btn-system" onclick="goCount()">
						<span>业绩计算</span>
					</button>
					<button type="button" class="btn btn-system" onclick="countComplete()">
						<span>计算完成</span>
					</button>
					<button type="button" class="btn btn-system" onclick="back()">
						<span>退回</span>
					</button>
					<house:authorize authCode="PRJPERF_VIEW">
						<button type="button" class="btn btn-system" onclick="doPerfChg()">
							<span>增减业绩</span>
						</button>
					</house:authorize>	
					<button type="button" class="btn btn-system" onclick="view()">
						<span>查看</span>
					</button>
					<button type="button" class="btn btn-system" onclick="report()">
						<span>报表</span>
					</button>
					<button type="button" class="btn btn-system" onclick="addPeriod()">
						<span>新增统计周期</span>
					</button>
					<button type="button" class="btn btn-system" onclick="updatePeriod()">
						<span>编辑</span>
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
