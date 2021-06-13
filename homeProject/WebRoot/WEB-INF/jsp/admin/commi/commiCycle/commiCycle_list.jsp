<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<!DOCTYPE HTML>
<html>
<head>
    <title>提成计算</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script type="text/javascript">
	function add(){
		Global.Dialog.showDialog("add",{
			title:"提成统计周期--增加",
			url:"${ctx}/admin/commiCycle/goSave",
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
				content:"该提成统计周期已计算完成，无法编辑！",
			});
			return;
		}
		Global.Dialog.showDialog("updatePeriod",{
			title:"提成统计周期--编辑",
			postData:{no:ret.no},
			url:"${ctx}/admin/commiCycle/goUpdate",
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
			url:"${ctx}/admin/commiCycle/goReport",
			height: 750,
		 	width:1350,
			returnFun: goto_query 
		});
	}
	
	function supplierRebate() {
	    Global.Dialog.showDialog("supplierRebate", {
            title: "商家返利信息",
            url: "${ctx}/admin/commiCycle/goSupplierRebate",
            height: 750,
            width: 1350
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
				content:"该提成统计周期已计算完成,无法进行提成计算操作",
			});
			return;
		}
		$.ajax({
			url:"${ctx}/admin/commiCycle/isExistsPeriod",
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
						title:"提成计算("+ret.mon+")",
						postData:{no:ret.no},
						url:"${ctx}/admin/commiCycle/goCount",
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
			title:"提成计算("+ret.mon+")",
			postData:{no:ret.no,m_umState:"V"},
			url:"${ctx}/admin/commiCycle/goView",
			height: 750,
		 	width:1350,
			returnFun: goto_query 
		});
	}
	function countComplete(){
		var ret =selectDataTableRow();
		var newStatus=checkStatus();
		var tipContent="确定该提成统计周期已全部计算完成吗？";
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
				content:"该提成统计周期已计算完成,无法再进行计算完成操作！",
			});
			return;
		}
		art.dialog({
			content:tipContent,
			lock: true,
			ok: function () {
				$.ajax({
					url:"${ctx}/admin/commiCycle/doComplete",
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
			content:"确定要回退该提成统计周期吗？",
			lock: true,
			ok: function () {
				$.ajax({
					url:"${ctx}/admin/commiCycle/doReturn",
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
	
	//清空
	function clearForm(){
		$("#page_form input[type='text']").val("");
		$("select").val("");
	} 
	/**初始化表格*/
	$(function(){
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/commiCycle/goJqGrid",
			height:$(document).height()-$("#content-list").offset().top-100,
			styleUI: "Bootstrap", 
			colModel : [
				{name: "no", index: "no", width: 100, label: "统计周期编号", sortable: true, align: "left"},
				{name: "mon", index: "mon", width: 80, label: "月份", sortable: true, align: "left"},
				{name: "floatbeginmon", index: "floatbeginmon", width: 120, label: "浮动业绩开始月份", sortable: true, align: "left"},
				{name: "floatendmon", index: "floatendmon", width: 120, label: "浮动业绩截止月份", sortable: true, align: "left"},
				{name: "status", index: "status", width: 80, label: "状态", sortable: true, align: "left",hidden:true},
				{name: "statusdescr", index: "statusdescr", width: 80, label: "状态", sortable: true, align: "left"},
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
	});
	function doExcel(){
		var url = "${ctx}/admin/commiCycle/doExcel";
		doExcelAll(url);
	}
	function checkStatus(){
		var status="";
		var ret =selectDataTableRow();
		$.ajax({
			url:"${ctx}/admin/commiCycle/checkStatus",
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
	</script>
</head>
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form role="form" class="form-search" id="page_form" action=""
				method="post" target="targetFrame">
				<input type="hidden" name="jsonString" value="" />
				<ul class="ul-form">
					<li>
						<label>月份</label>
						<input type="text" id="mon" name="mon" class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyyMM'})"  />
					</li>
					<li>
						<label>浮动业绩开始月份</label>
						<input type="text" id="floatBeginMon" name="floatBeginMon" class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyyMM'})"  />
					</li>
					<li>
						<label>浮动业绩截止月份</label>
						<input type="text" id="floatEndMon" name="floatEndMon" class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyyMM'})"  />
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
					<house:authorize authCode="COMMICYCLE_COUNT">
						<button type="button" class="btn btn-system" onclick="count()">
							<span>提成计算</span>
						</button>
					</house:authorize>
					<house:authorize authCode="COMMICYCLE_COMPLETE">
						<button type="button" class="btn btn-system"
							onclick="countComplete()">
							<span>计算完成</span>
						</button>
					</house:authorize>
					<house:authorize authCode="COMMICYCLE_RETURN">
						<button type="button" class="btn btn-system" onclick="back()">
							<span>回退</span>
						</button>
					</house:authorize>
					<house:authorize authCode="COMMICYCLE_VIEW">
						<button type="button" class="btn btn-system" onclick="view()">
							<span>查看</span>
						</button>
					</house:authorize>
					<house:authorize authCode="COMMICYCLE_SUPPLIER_REBATE">
						<button type="button" class="btn btn-system" onclick="supplierRebate()">
							<span>商家返利信息</span>
						</button>
					</house:authorize>
					<house:authorize authCode="COMMICYCLE_ADD">
						<button type="button" class="btn btn-system" onclick="add()">
							<span>新增统计周期</span>
						</button>
					</house:authorize>
					<house:authorize authCode="COMMICYCLE_UPDATE">
						<button type="button" class="btn btn-system"
							onclick="updatePeriod()">
							<span>编辑</span>
						</button>
					</house:authorize>
					<house:authorize authCode="COMMICYCLE_UPDATE">
						<button type="button" class="btn btn-system" onclick="doExcel()"
							title="导出检索条件数据">
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
