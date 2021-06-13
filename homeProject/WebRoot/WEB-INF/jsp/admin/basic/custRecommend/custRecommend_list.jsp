<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<title>推荐客户管理列表页</title>
		<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
		<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
		<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
		<META HTTP-EQUIV="expires" CONTENT="0" />
		<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
		<%@ include file="/commons/jsp/common.jsp" %>
		<script src="${resourceRoot}/pub/component_worker.js?v=${v}" type="text/javascript"></script>
		<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
		<script type="text/javascript">
		
		$(function(){
			$("#workerCode").openComponent_worker();
			$("#employeeNum").openComponent_employee();
			var postData = $("#page_form").jsonForm();
			Global.JqGrid.initJqGrid("dataTable", {
				height:$(document).height()-$("#content-list").offset().top-95,
				url:"${ctx}/admin/custRecommend/goJqGrid",
				postData:{
					status:"0",
				},
				colModel : [			
					{name: "pk", index: "pk", width: 160, label: "pk", sortable: true, align: "left", hidden:true},
					{name: "address", index: "address", width: 200, label: "楼盘地址", sortable: true, align: "left"},
					{name: "custname", index: "custname", width: 80, label: "客户姓名", sortable: true, align: "left"},
					{name: "custphone", index: "custphone", width: 80, label: "电话号码", sortable: true, align: "left"},
					{name: "recommendsource", index: "recommendsource", width: 100, label: "推荐来源", sortable: true, align: "left",hidden:true},
					{name: "recommendsourcedescr", index: "recommendsourcedescr", width: 100, label: "推荐来源", sortable: true, align: "left"},
					{name: "recommendertype", index: "recommendertype", width: 100, label: "推荐人类型", sortable: true, align: "left",hidden:true},
					{name: "recommendertypedescr", index: "recommendertypedescr", width: 100, label: "推荐人类型", sortable: true, align: "left"},
					{name: "recommender", index: "recommender", width: 100, label: "推荐人", sortable: true, align: "left",hidden:true},
					{name: "recommenderdescr", index: "recommenderdescr", width: 80, label: "推荐人", sortable: true, align: "left"},
					{name: "manager", index: "manager", width: 100, label: "管理人", sortable: true, align: "left",hidden:true},
					{name: "managerdescr", index: "managerdescr", width: 80, label: "管理人", sortable: true, align: "left"},
					{name: "recommenddate", index: "recommenddate", width: 140, label: "推荐日期", sortable: true, align: "left", formatter: formatTime},
					{name: "remarks", index: "remarks", width: 200, label: "推荐备注", sortable: true, align: "left"},
					{name: "status", index: "status", width: 100, label: "推荐状态", sortable: true, align: "left",hidden:true},
					{name: "statusdescr", index: "statusdescr", width: 100, label: "推荐状态", sortable: true, align: "left"},
					{name: "confirmdate", index: "confirmdate", width: 140, label: "确认日期", sortable: true, align: "left", formatter: formatTime},
					{name: "confirmremarks", index: "confirmremarks", width: 200, label: "确认备注", sortable: true, align: "left"},
					{name: "custcode", index: "custcode", width: 80, label: "关联客户号", sortable: true, align: "left"},
					{name: "custstatus", index: "custstatus", width: 80, label: "关联客户状态", sortable: true, align: "left",hidden:true},
					{name: "custstatusdescr", index: "custstatusdescr", width: 120, label: "关联客户状态", sortable: true, align: "left"}
	            ]
			});
		});
		
		function clearForm(){
			$("#page_form input[type='text']").val('');
			$("#openComponent_worker_workerCode").val('');
			$("#openComponent_employee_employee").val('');
			$("#workerCode").val('');
			$("#employeeNum").val('');
			$("#workType12").val('');
			$("#status").val('');
			$("#recommendSource").val('');
			$.fn.zTree.getZTreeObj("tree_workType12").checkAllNodes(false);
			$.fn.zTree.getZTreeObj("tree_status").checkAllNodes(false);
			$.fn.zTree.getZTreeObj("tree_recommendSource").checkAllNodes(false);
		}
		function goConfirm(){
			ret=selectDataTableRow();
			if(!ret){
				art.dialog({
	       			content: "请选择一条记录",
	       		});
	       		return;
			}
			if(ret.status!="0"){
				art.dialog({
	       			content: "只能对未确认的记录进行确认信息操作",
	       		});
	       		return;
			}
			Global.Dialog.showDialog("confirm",{
				title:"推荐客户——确认信息",
				url:"${ctx}/admin/custRecommend/goConfirm",
				postData:{pk:ret.pk},
				height:300,
				width:750,
				returnFun:goto_query
			});
		}
		
		function goUpdate(){
			ret=selectDataTableRow();
			if(!ret){
				art.dialog({
	       			content: "请选择一条记录",
	       		});
	       		return;
			}
			console.log(ret.status);
			if(!(ret.status=="0" || ret.status=="1" || ret.status=="2")){
				art.dialog({
	       			content: "只能对未确认、信息有效、信息无效的记录进行确认信息操作",
	       		});
	       		return;
			}
			Global.Dialog.showDialog("update",{
				title:"推荐客户——编辑",
				url:"${ctx}/admin/custRecommend/goUpdate",
				postData:{pk:ret.pk},
				height:350,
				width:750,
				returnFun:goto_query
			});
		}
		
		function goArrive(){
			ret=selectDataTableRow();
			if(!ret){
				art.dialog({
	       			content: "请选择一条记录",
	       		});
	       		return;
			}
			if(ret.status!="1"){
				art.dialog({
	       			content: "只能对信息有效的记录进行到店操作",
	       		});
	       		return;
			}
			Global.Dialog.showDialog("arrive",{
				title:"推荐客户——到店",
				url:"${ctx}/admin/custRecommend/goArrive",
				postData:{pk:ret.pk},
				height:350,
				width:750,
				returnFun:goto_query
			});
		}
		
		function goView(){
			ret=selectDataTableRow();
			if(!ret){
				art.dialog({
	       			content: "请选择一条记录",
	       		});
	       		return;
			}
			Global.Dialog.showDialog("arrive",{
				title:"推荐客户——查看",
				url:"${ctx}/admin/custRecommend/goView",
				postData:{pk:ret.pk},
				height:350,
				width:750,
				returnFun:goto_query
			});
		}
		
		function goCount(){
			Global.Dialog.showDialog("arrive",{
				title:"推荐客户——信息统计",
				url:"${ctx}/admin/custRecommend/goCount",
				height:700,
				width:1000,
				returnFun:goto_query
			});
		}
		
		function doExcel(){
			var url = "${ctx}/admin/custRecommend/doExcel";
			doExcelAll(url);
		}
		</script>
	</head>
	    
	<body>
		<div class="body-box-list" style="overflow-x:hidden">
			<div class="query-form">
				<form action="" method="post" id="page_form" class="form-search">
					<input type="hidden" name="jsonString" value=""/>
					<input type="hidden" id="expired" name="expired" value="${custRecommend.expired}"/>
					<ul class="ul-form">
						<li>
							<label>楼盘</label>
							<input type="text" id="address" name="address"  value="${custRecommend.address}" />
						</li>
						<li>
							<label>推荐状态</label>
							<house:xtdmMulit id="status" dictCode="CUSTRESSTAT" selectedValue="0"></house:xtdmMulit>
						</li>
						<li>
							<label>工人编号</label>
							<input type="text" id="workerCode" name="workerCode"/>
						</li>
						<li>
							<label>工种</label>
							<house:DictMulitSelect id="workType12" dictCode="" sql="select a.* from tWorkType12 a where  (
							(select PrjRole from tCZYBM where CZYBH='${czybm }') is null 
							or( select PrjRole from tCZYBM where CZYBH='${czybm }') ='' ) or  Code in(
								select WorkType12 From tprjroleworktype12 pr where pr.prjrole = 
								(select PrjRole from tCZYBM where CZYBH='${czybm }') or pr.prjrole = ''
								 )   "  onCheck="loadWorkType12Dept()"
							sqlValueKey="Code" sqlLableKey="Descr"></house:DictMulitSelect>
						</li>
						<li>
							<label>员工编号</label>
							<input type="text" id="employeeNum" name="employeeNum"/>
						</li>
						<li>
							<label>推荐来源</label>
							<house:DictMulitSelect id="recommendSource" dictCode="RECOMMENDSOURCE" sqlValueKey="Code" sqlLableKey="Descr"></house:DictMulitSelect>
						</li>
						<li class="search-group-shrink" >
							<button type="button" class="btn  btn-sm btn-system " onclick="goto_query();"  >查询</button>
							<button type="button" class="btn btn-sm btn-system " onclick="clearForm();"  >清空</button>
						</li>
					</ul>
				</form>
			</div>
			<div class="clear_float"> </div>
			<div class="btn-panel">
				<div class="btn-group-sm">
					<house:authorize authCode="CUSTRECOMMEND_CONFIRM">
						<button type="button" class="btn btn-system " onclick="goConfirm()">确认信息</button>
					</house:authorize>
					<house:authorize authCode="CUSTRECOMMEND_UPDATE">
						<button type="button" class="btn btn-system" onclick="goUpdate()">编辑</button>
					</house:authorize>
					<house:authorize authCode="CUSTRECOMMEND_ARRIVE">
						<button type="button" class="btn btn-system " onclick="goArrive()">到店</button>
					</house:authorize>
					<house:authorize authCode="CUSTRECOMMEND_VIEW">
						<button type="button" class="btn btn-system " onclick="goView()">查看</button>
					</house:authorize>
					<house:authorize authCode="CUSTRECOMMEND_COUNT">
						<button type="button" class="btn btn-system " onclick="goCount()">信息统计</button>
					</house:authorize>
					<house:authorize authCode="CUSTRECOMMEND_EXCEL">
				      	<button type="button" class="btn btn-system "  onclick="doExcel()">导出excel</button>
			      	</house:authorize>
				</div>
			</div>
			<div id="content-list">
				<table id="dataTable"></table>
				<div id="dataTablePager"></div>
			</div> 
		</div>
	</body>
</html>


