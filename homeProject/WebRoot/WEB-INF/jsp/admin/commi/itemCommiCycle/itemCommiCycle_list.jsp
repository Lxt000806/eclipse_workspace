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
    <title>材料独立销售提成计算</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_itemCommiCycle.js?v=${v}" type="text/javascript"></script>

	<script type="text/javascript">
	function add(){
		Global.Dialog.showDialog("save",{
			title:"新增周期统计",
			url:"${ctx}/admin/itemCommiCycle/goSave",
			height: 450,
		 	width:650,
			returnFun: goto_query 
		});
	}
	
	function update(){
		var ret =selectDataTableRow();
		if(!ret){
			art.dialog({
				content:"请选择一条数据",
			});
			return;
		}
		Global.Dialog.showDialog("update",{
			title:"编辑周期统计",
			postData:{id:ret.no},
			url:"${ctx}/admin/itemCommiCycle/goUpdate",
			height: 450,
		 	width:650,
			returnFun: goto_query 
		});
	}
	
	function count(){
		var ret =selectDataTableRow();
		if(!ret){
			art.dialog({
				content:"请选择一条数据",
			});
			return;
		}
		if(ret.status=="2"){
			art.dialog({
				content:"该提成统计周期已计算完成,无法进行提成计算操作",
			});
			return;
		}
		$.ajax({
			url:"${ctx}/admin/itemCommiCycle/isExistsPeriod",
			type: "post",
			data: {no:ret.no,beginDate:ret.begindate},
			dataType: "text",
			cache: false,
			error: function(obj){
				showAjaxHtml({"hidden": false, "msg": "保存数据出错~"});
		    },
		    success: function(obj){
				if(obj!=null && obj!=""){
					var msg="";
					if("1"==obj){
						msg="没有找到相应的提成统计周期或该统计周期已计算完成!";
					}else{
						msg="之前的统计周期未计算完成，不允许计算本周期提成!";
					}
					art.dialog({
						content:msg,
					});
					return;
				}else{
					Global.Dialog.showDialog("count",{
						title:"提成计算",
						postData:{no:ret.no},
						url:"${ctx}/admin/itemCommiCycle/goCount",
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
		Global.Dialog.showDialog("view",{
			title:"提成计算——查看",
			postData:{id:ret.no},
			url:"${ctx}/admin/itemCommiCycle/goView",
			height: 750,
		 	width:1350,
			returnFun: goto_query 
		});
	}
	
	function complete(){
		var ret =selectDataTableRow();
		if(!ret){
			art.dialog({
				content:"请选择一条数据",
			});
			return;
		}
		if(ret.status=="2"){
			art.dialog({
				content:"该提成统计周期已计算完成,无法进行提成计算操作",
			});
			return;
		}
		$.ajax({
			url:"${ctx}/admin/itemCommiCycle/checkStatus",
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
						content:"确定该提成统计周期已全部计算完成吗?",
						lock: true,
						width: 200,
						height: 100,
						ok: function () {
							$.ajax({
								url:"${ctx}/admin/itemCommiCycle/doComplete",
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
			url:"${ctx}/admin/itemCommiCycle/checkStatus",
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
						content:"确定要回退该提成统计周期吗？",
						lock: true,
						width: 200,
						height: 100,
						ok: function () {
							$.ajax({
								url:"${ctx}/admin/itemCommiCycle/doReturn",
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
			}
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
	
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/itemCommiCycle/goJqGrid",
			height:$(document).height()-$("#content-list").offset().top-70,
			styleUI: "Bootstrap", 
			colModel : [
				{name: "no", index: "no", width: 100, label: "统计周期编号", sortable: true, align: "left"},
				{name: "begindate", index: "begindate", width: 110, label: "开始时间", sortable: true, align: "left", formatter: formatDate},
				{name: "enddate", index: "enddate", width: 110, label: "结束时间", sortable: true, align: "left", formatter: formatDate},
				{name: "mon", index: "mon", width: 80, label: "归属月份", sortable: true, align: "left",},
				{name: "status", index: "status", width: 110, label: "状态", sortable: true, align: "left",hidden:true},
				{name: "statusdescr", index: "statusdescr", width: 110, label: "状态", sortable: true, align: "left"},
				{name: "remarks", index: "remarks", width: 322, label: "说明", sortable: true, align: "left"},
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
		var url = "${ctx}/admin/itemCommiCycle/doExcel";
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
					<li>
						<label>统计周期编号</label>
						<input type="text" id="no" name="no" style="width:160px;"/>
					</li>
					<li>
						<label>统计日期</label>
						<input type="text" id="dateFrom" name="dateFrom" class="i-date"  style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"/>
					</li>
					<li>
						<label>至</label>
						<input type="text" id="dateTo" name="dateTo" class="i-date"  style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"/>
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
					<house:authorize authCode="ITEMCOMMICYCLE_COUNT">
						<button type="button" class="btn btn-system" onclick="count()">
							<span>提成计算</span>
						</button>
					</house:authorize>	
					<house:authorize authCode="ITEMCOMMICYCLE_COMPLETE">
						<button type="button" class="btn btn-system" onclick="complete()">
							<span>计算完成</span>
						</button>
					</house:authorize>	
					<house:authorize authCode="ITEMCOMMICYCLE_RETURN">
						<button type="button" class="btn btn-system" onclick="back()">
							<span>回退</span>
						</button>
					</house:authorize>	
					<house:authorize authCode="ITEMCOMMICYCLE_VIEW">
						<button type="button" class="btn btn-system" onclick="view()">
							<span>查看</span>
						</button>
					</house:authorize>	
					<house:authorize authCode="ITEMCOMMICYCLE_ADD">
						<button type="button" class="btn btn-system" onclick="add()">
							<span>新增统计周期</span>
						</button>
					</house:authorize>	
					<house:authorize authCode="ITEMCOMMICYCLE_UPDATE">
						<button type="button" class="btn btn-system" onclick="update()">
							<span>编辑</span>
						</button>
					</house:authorize>	
					<house:authorize authCode="ITEMCOMMICYCLE_EXCEL">
						<button type="button" class="btn btn-system" onclick="doExcel()" title="导出检索条件数据">
							<span>输出到excel</span>
						</button>
					</house:authorize>	
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
