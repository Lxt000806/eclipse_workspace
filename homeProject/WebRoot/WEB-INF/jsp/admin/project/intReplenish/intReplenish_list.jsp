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
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<title>集成补货信息登记</title>
	<%@ include file="/commons/jsp/common.jsp" %>
</head>
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form class="form-search" id="page_form" action="" method="post" target="targetFrame">
				<input type="hidden" id="expired" name="expired" value="${intReplenish.expired}" />
				<input type="hidden" name="jsonString" value=""/><%-- 导出EXCEL用 --%>
				<ul class="ul-form">
					<li>
						<label for="address">楼盘</label>
						<input type="text" id="address" name="address"/>
					</li>
					<li>
						<label>货好时间从</label> 
						<input type="text" id="boardOKDateFrom" name="boardOKDateFrom" class="i-date"
							onclick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd',readOnly:true})" />
					</li>
					<li>
						<label>至</label> 
						<input type="text" id="boardOKDateTo" name="boardOKDateTo" class="i-date"
							onclick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd',readOnly:true})" />
					</li>
					<li>
						<label>货齐时间从</label> 
						<input type="text" id="readyDateFrom" name="readyDateFrom" class="i-date"
							onclick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd',readOnly:true})" />
					</li>
					<li>
						<label>至</label> 
						<input type="text" id="readyDateTo" name="readyDateTo" class="i-date"
							onclick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd',readOnly:true})" />
					</li>
					<li>
						<label for="isCupboard">是否橱柜</label>
						<house:xtdm id="isCupboard" dictCode="YESNO" style="width:160px;"/>
					</li>
					<li>
						<label>完成时间从</label> 
						<input type="text" id="finishDateFrom" name="finishDateFrom" class="i-date"
							onclick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd',readOnly:true})" />
					</li>
					<li>
						<label>至</label> 
						<input type="text" id="finishDateTo" name="finishDateTo" class="i-date"
							onclick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd',readOnly:true})" />
					</li>
					<li>
						<label>状态</label>
						<house:xtdmMulit id="status" dictCode="IntRepStatus"/>
					</li>
					<li>
						<label>集成部</label>
						<house:DictMulitSelect id="intDepartment2" dictCode="" 
							sql="select rtrim(Code) fd1, rtrim(Desc1) fd2 from tDepartment2 where expired='F' and DepType='6' order by DispSeq " 
							sqlLableKey="fd2" sqlValueKey="fd1">
						</house:DictMulitSelect>
					</li>
					<li>
						<label>已定责</label>
						<house:xtdm id="isFixDuty" dictCode="YESNO"></house:xtdm>
					</li>
					
					<li>
					   <label>一级区域</label>
					   <house:xtdmMulit id="region" dictCode="" sql="select code SQL_VALUE_KEY,descr SQL_LABEL_KEY  from tRegion a where a.expired='F' " ></house:xtdmMulit>
					</li>
					
					<li>
						<input type="checkbox" id="expired_show" name="expired_show" value="${intReplenish.expired }" 
							onclick="hideExpired(this)" ${intReplenish.expired!='T' ?'checked':''}/>
						<label for="expired_show" style="margin-left: -3px;width: 50px;">隐藏过期</label>
						<button type="button" class="btn btn-system btn-sm" onclick="goto_query();">查询</button>
						<button type="button" class="btn btn-sm btn-system" id="clear" onclick="clearForm();">清空</button>
					</li>
				</ul>
			</form>
		</div>
		<div class="clear_float"></div>
		<div class="pageContent">
			<div class="btn-panel">
				<div class="btn-group-sm">
					<house:authorize authCode="INTREPLENISH_ADD">
						<button type="button" class="btn btn-system" id="add">
							<span>新增</span>
						</button>
					</house:authorize>
					<house:authorize authCode="INTREPLENISH_INPUT">
						<button type="button" class="btn btn-system" id="input">
							<span>补货明细录入</span>
						</button>
					</house:authorize>
					<house:authorize authCode="INTREPLENISH_SIGN">
						<button type="button" class="btn btn-system" id="sign">
							<span>货好登记</span>
						</button>
					</house:authorize>
					<house:authorize authCode="INTREPLENISH_ARRIVE">
						<button type="button" class="btn btn-system" id="arrive">
							<span>货到登记</span>
						</button>
					</house:authorize>
					<house:authorize authCode="INTREPLENISH_ARRANGE">
						<button type="button" class="btn btn-system" id="arrange">
							<span>售中安排</span>
						</button>
					</house:authorize>
					<house:authorize authCode="INTREPLENISH_FIXDUTY">
						<button type="button" class="btn btn-system" id="fixDuty">
							<span>定责</span>
						</button>
					</house:authorize>
					<house:authorize authCode="INTREPLENISH_FINISH">
						<button type="button" class="btn btn-system" id="finish">
							<span>完成</span>
						</button>
					</house:authorize>
					<house:authorize authCode="INTREPLENISH_VIEW">
						<button type="button" class="btn btn-system" id="view" onclick="view()">
							<span>查看</span>
						</button>
					</house:authorize>
					<house:authorize authCode="INTREPLENISH_DETAILLIST">
						<button type="button" class="btn btn-system" id="detailList">
							<span>明细查询</span>
						</button>
					</house:authorize>
					<house:authorize authCode="INTREPLENISH_MODIFYSTATUS">
						<button type="button" class="btn btn-system" id="modifyStatus">
							<span>修改状态</span>
						</button>
					</house:authorize>
					<house:authorize authCode="INTREPLENISH_EXCEL">
						<button type="button" class="btn btn-system" onclick="doExcel();">
							<span>输出到Excel</span>
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
	<script type="text/javascript">
		function view(){
			var ret = selectDataTableRow();
		    if (ret) {
		      Global.Dialog.showDialog("intReplenishView",{
					title:"集成补货信息登记——查看",
					url:"${ctx}/admin/intReplenish/goView",
					postData: {no: ret.no, m_umState: "V"},
					height: 708,
					width: 1074,
				});
		    } else {
		    	art.dialog({
					content: "请选择一条记录"
				});
		    }
		}
		//导出EXCEL
		function doExcel(){
			var url = "${ctx}/admin/intReplenish/doExcel";
			doExcelAll(url);
		}
		/**初始化表格*/
		$(function(){
	        //初始化表格
			Global.JqGrid.initJqGrid("dataTable",{
				url:"${ctx}/admin/intReplenish/goJqGrid",
				height:$(document).height()-$("#content-list").offset().top-67,
				postData: $("#page_form").jsonForm(),
				colModel : [
					{name: "no",index: "no",width: 80,label:"单号",sortable: true,align: "left"},
					{name: "iscupboard",index: "iscupboard",width: 80,label:"是否橱柜",sortable: true,align: "left", hidden: true},
					{name: "iscupboarddescr",index: "iscupboarddescr",width: 80,label:"是否橱柜",sortable: true,align: "left"},
					{name: "regiondescr",index: "regiondescr",width: 60,label:"区域",sortable: true,align: "left"},
					{name: "address",index: "address",width: 140,label:"楼盘",sortable: true,align: "left"},
					{name: "status",index: "status",width: 60,label:"状态",sortable: true,align: "left", hidden: true},
					{name: "statusdescr",index: "statusdescr",width: 60,label:"状态",sortable: true,align: "left"},
					{name: "source",index: "source",width: 80,label:"补货来源",sortable: true,align: "left", hidden: true},
					{name: "sourcedescr",index: "sourcedescr",width: 80,label:"补货来源",sortable: true,align: "left"},
					{name: "remarks",index: "remarks",width: 160,label:"说明",sortable: true,align: "left"},
					{name: "projectman",index: "projectman",width: 80,label:"项目经理",sortable: true,align: "left", hidden: true},
					{name: "projectmandescr",index: "projectmandescr",width: 80,label:"项目经理",sortable: true,align: "left"},
					{name: "phone",index: "phone",width: 100,label:"项目经理电话",sortable: true,align: "left"},
			    	{name: "boardokdate",index: "boardokdate",width: 100,label:"货好时间",sortable: true,align: "left",formatter: formatDate},
					{name: "boardokremarks",index: "boardokremarks",width: 160,label:"货好备注",sortable: true,align: "left"},
			    	{name: "readydate",index: "readydate",width: 100,label:"货齐时间",sortable: true,align: "left",formatter: formatDate},
			    	{name: "servicedate",index: "servicedate",width: 100,label:"售中时间",sortable: true,align: "left",formatter: formatDate},
					{name: "serviceman",index: "serviceman",width: 80,label:"售中人员",sortable: true,align: "left",hidden: true},
					{name: "servicemandescr",index: "servicemandescr",width: 80,label:"售中人员",sortable: true,align: "left"},
					{name: "respart",index: "respart",width: 80,label:"责任人",sortable: true,align: "left", hidden: true},
					{name: "respartdescr",index: "respartdescr",width: 80,label:"责任人",sortable: true,align: "left"},
			    	{name: "finishdate",index: "finishdate",width: 100,label:"完成时间",sortable: true,align: "left",formatter: formatDate},
			    	{name: "appczydescr",index: "appczydescr",width: 80,label:"补货人",sortable: true,align: "left"},
			    	{name: "date",index: "date",width: 120,label:"补货日期",sortable: true,align: "left",formatter: formatTime},
			    	{name: "fixdutymandescr",index: "fixdutymandescr",width: 80,label:"定责人",sortable: true,align: "left"},
			    	{name: "fixdutydate",index: "fixdutydate",width: 120,label:"定责日期",sortable: true,align: "left",formatter: formatTime},
			    	{name: "lastupdate",index: "lastupdate",width: 120,label:"最后修改时间",sortable: true,align: "left",formatter: formatTime},
      				{name: "lastupdatedby",index: "lastupdatedby",width: 70,label:"修改人",sortable: true,align: "left"},
      				{name: "expired",index: "expired",width: 80,label:"过期标志",sortable: true,align: "left"},
      				{name: "actionlog",index: "actionlog",width: 80,label:"操作日志",sortable: true,align: "left"}
	            ],
	            ondblClickRow: function () {
	            	view();
	            }
			});

			$("#clear").on("click",function(){
				zTreeCheckFalse("status");
				zTreeCheckFalse("intDepartment2");
			});

			$("#add").on("click",function () {
				Global.Dialog.showDialog("intReplenishAdd",{
					title: "集成补货信息登记——新增",
					url: "${ctx}/admin/intReplenish/goAdd",
					height: 395,
					width: 450,
					returnFun: goto_query
				});
			});
			$("#input").on("click",function () {
				var ret = selectDataTableRow();
				if (ret) {
					if ("1" != $.trim(ret.status)) {
						art.dialog({
							content: "只能操作状态为‘提交’的记录"
						});
						return;
					}
					Global.Dialog.showDialog("intReplenishInput",{
						title: "集成补货信息登记——补货明细录入",
						url: "${ctx}/admin/intReplenish/goInput",
						postData: {no: ret.no, m_umState: "I"},
						height: 670,
						width: 990,
						returnFun: goto_query
					});
				} else {
					art.dialog({
						content: "请选择一条记录"
					});
				}
			});
			$("#sign").on("click",function () {
				var ret = selectDataTableRow();
				if (ret) {
					if ("2" != $.trim(ret.status)) {
						art.dialog({
							content: "只能操作状态为‘已下单’的记录"
						});
						return;
					}
					Global.Dialog.showDialog("intReplenishSign",{
						title: "集成补货信息登记——货好登记",
						url: "${ctx}/admin/intReplenish/goSign",
						postData: {no: ret.no, m_umState: "S"},
						height: 670,
						width: 990,
						returnFun: goto_query
					});
				} else {
					art.dialog({
						content: "请选择一条记录"
					});
				}
			});
			$("#arrive").on("click",function () {
				Global.Dialog.showDialog("intReplenishArrive",{
					title: "集成补货信息登记——货到登记",
					url: "${ctx}/admin/intReplenish/goArrive",
					height: 580,
					width: 966,
					returnFun: goto_query
				});
			});
			$("#arrange").on("click",function () {
				var ret = selectDataTableRow();
				if (ret) {
					if ("3" != $.trim(ret.status)) {
						art.dialog({
							content: "只能操作状态为‘已到货’的记录"
						});
						return;
					}
					Global.Dialog.showDialog("intReplenishArrange",{
						title: "集成补货信息登记——售中安排",
						url: "${ctx}/admin/intReplenish/goArrange",
						postData: {no: ret.no, m_umState: "A"},
						height: 670,
						width: 990,
						returnFun: goto_query
					});
				} else {
					art.dialog({
						content: "请选择一条记录"
					});
				}
			});
			$("#fixDuty").on("click",function () {
				var ret = selectDataTableRow();
				if (ret) {
					if ("1" != $.trim(ret.status)) {
						art.dialog({
							content: "只能操作状态为‘提交’的记录"
						});
						return;
					}
					Global.Dialog.showDialog("intReplenishFixDuty",{
						title: "集成补货信息登记——定责",
						url: "${ctx}/admin/intReplenish/goFixDuty",
						postData: {no: ret.no, m_umState: "F"},
						height: 670,
						width: 990,
						returnFun: goto_query
					});
				} else {
					art.dialog({
						content: "请选择一条记录"
					});
				}
			});
			$("#finish").on("click",function () {
				var ret = selectDataTableRow();
				if (ret) {
					if ("4" != $.trim(ret.status)) {
						art.dialog({
							content: "只能操作状态为‘已安排’的记录"
						});
						return;
					}
					Global.Dialog.showDialog("intReplenishFinish",{
						title: "集成补货信息登记——完成",
						url: "${ctx}/admin/intReplenish/goFinish",
						postData: {
							m_umState:"finish",
							no:ret.no,
							status:ret.status,
							address:ret.address,
							finishDate:ret.finishdate
						},
						height: 330,
						width: 450,
						returnFun: goto_query
					});
				} else {
					art.dialog({
						content: "请选择一条记录"
					});
				}
			});
			$("#detailList").on("click",function () {
				Global.Dialog.showDialog("intReplenishDetailList",{
					title: "集成补货信息登记——明细查询",
					url: "${ctx}/admin/intReplenish/goDetailList",
					height: 740,
					width: 1250,
					// returnFun: goto_query
				});
			});
			$("#modifyStatus").on("click",function () {
				var ret = selectDataTableRow();
				if (ret) {
					Global.Dialog.showDialog("intReplenishModifyStatus",{
						title: "集成补货信息登记——修改状态",
						url: "${ctx}/admin/intReplenish/goModifyStatus",
						postData: {
							no:ret.no,
							status:ret.status,
							address:ret.address
						},
						height: 330,
						width: 450,
						returnFun: goto_query
					});
				} else {
					art.dialog({
						content: "请选择一条记录"
					});
				}
			});
		});
		// 清空下拉选择树选项
		function zTreeCheckFalse(id) {
			$("#"+id).val("");
			$.fn.zTree.getZTreeObj("tree_"+id).checkAllNodes(false);
		}
	</script>
</body>
</html>
