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
	<title>供货管理——生产进度管理</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8"/>
	<META HTTP-EQUIV="pragma" CONTENT="no-cache"/>
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"/>
	<META HTTP-EQUIV="expires" CONTENT="0"/>
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge"/>
	<%@ include file="/commons/jsp/common.jsp"%>
	<script src="${resourceRoot}/pub/component_supplier.js?v=${v}" type="text/javascript"></script>
</head>
<body>
	<div class="body-box-form">
		<div class="panel panel-system">
		    <div class="panel-body">
		      <div class="btn-group-xs">
		      		<button type="button" class="btn btn-system " id="updateBtn" onclick="update()">
						<span>编辑</span>
					</button>
					<button type="button" class="btn btn-system " id="closeBtn">
						<span>关闭</span>
					</button>
					<button type="button" class="btn btn-system" id="delete">
						<span>删除</span>
					</button>
			  </div>
			</div>
		</div>
		<div class="body-box-list">
			<div class="query-form">
			    <form class="form-search" id="page_form" action="" method="post" target="targetFrame">
			    	<house:token></house:token>
					<input type="hidden" name="jsonString" value=""/>
					<input type="hidden" id="expired" name="expired" value=""/>
					<ul class="ul-form">
						<li>
							<label>楼盘</label>
							<input type="text" id="address" name="address" style="width:160px;"/>
						</li>
						<li>
							<label>供应商</label>
							<input type="text" name="supplCode" id="supplCode">
						</li>
						<li>
							<label>下单日期从</label>
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
							<label>是否橱柜</label>
							<house:xtdm id="isCupboard" dictCode="YESNO" style="width:160px;"/>
						</li>
						<li>
							<label>包含已出货</label>
							<input type="checkbox" name="includeShipped" value="0" onchange="toggleIncludeShipped(this)"/>
						</li>
						<li class="search-group">
							<button type="button" class="btn btn-sm btn-system" onclick="goto_query();">查询</button>
							<button id="clear" type="button" class="btn btn-sm btn-system" onclick="clearForm();">清空</button>
						</li>
					</ul>
				</form>
			</div>
		</div>
		<div class="clear_float"></div>
		<div id="content-list">
			<table id="dataTable"></table>
			<div id="dataTablePager"></div>
		</div> 
	</div>
	
	<script>
		$(function(){
			$("#supplCode").openComponent_supplier();
			var postData = $("#page_form").jsonForm();
			var gridOption ={
				url:"${ctx}/admin/custIntProg/goIntProduceJqGrid",
				postData:postData,
				height:$(document).height()-$("#content-list").offset().top-70,
				styleUI:"Bootstrap", 
				colModel:[
					{name:"pk", index:"pk", width:140, label:"编号", sortable:true, align:"left", hidden:true},
					{name:"address", index:"address", width:140, label:"楼盘", sortable:true, align:"left"},
					{name:"supplcode", index:"supplcode", width:120, label:"供应商", sortable:true, align:"left", hidden:true},
					{name:"suppldescr", index:"suppldescr", width:120, label:"供应商", sortable:true, align:"left"},
					{name:"appdate", index:"appdate", width:120, label:"下单日期", sortable:true, align:"left", formatter:formatDate},
					{name:"iscupboard", index:"iscupboard", width:70, label:"是否橱柜", sortable:true, align:"left", hidden:true},
					{name:"iscupboarddescr", index:"iscupboarddescr", width:70, label:"是否橱柜", sortable:true, align:"left"},
					{name:"setboarddate", index:"setboarddate", width:120, label:"定板时间", sortable:true, align:"left", formatter:formatDate},
					{name:"arrboarddate", index:"arrboarddate", width:120, label:"到板时间", sortable:true, align:"left", formatter:formatDate},
					{name:"openmaterialdate", index:"openmaterialdate", width:120, label:"开料时间", sortable:true, align:"left", formatter:formatDate},
					{name:"sealingsidedate", index:"sealingsidedate", width:120, label:"封边时间", sortable:true, align:"left", formatter:formatDate},
					{name:"exholedate", index:"exholedate", width:120, label:"排孔时间", sortable:true, align:"left", formatter:formatDate},
					{name:"packdate", index:"packdate", width:120, label:"包装时间", sortable:true, align:"left", formatter:formatDate},
					{name:"inwhdate", index:"inwhdate", width:120, label:"入库时间", sortable:true, align:"left", formatter:formatDate},
					{name:"lastupdate", index:"lastupdate", width:120, label:"最后更新时间", sortable:true, align:"left", formatter:formatTime},
                    {name:"lastupdatedby", index:"lastupdatedby", width:60, label:"修改人", sortable:true, align:"left"},
                    {name:"expired", index:"expired", width:70, label:"过期标志", sortable:true, align:"left"},
                    {name:"actionlog", index:"actionlog", width:60, label:"操作", sortable:true, align:"left"}
				],
			};
			Global.JqGrid.initJqGrid("dataTable",gridOption);

			$("#closeBtn").on("click", function () {
				closeWin(false);
			});
			$("#delete").on("click",function(){
				var ret=selectDataTableRow();
				if(!ret){
					art.dialog({content:"请选择一条记录",});
					return;
				}
				var url = "${ctx}/admin/custIntProg/doDelete";
				beforeDel(url,"pk","删除该条数据");
				returnFun: goto_query;
				return true;	
			});
		});
		
		function toggleIncludeShipped(obj) {
		    obj.value = obj.checked ? "1" : "0";
		}
		
		function update(){
			var ret=selectDataTableRow();
			if(!ret){
				art.dialog({
					content:"请选择一条记录",
				});
				return;
			}
			Global.Dialog.showDialog("IntProduceViewUpdate",{
				title:"生产进度管理--编辑",
				url:"${ctx}/admin/custIntProg/goIntProduceViewUpdate?pk="+ret.pk,
				width:740,
				height:350,
				returnFun: goto_query
			});
		}
	</script>
</body>	
</html>
