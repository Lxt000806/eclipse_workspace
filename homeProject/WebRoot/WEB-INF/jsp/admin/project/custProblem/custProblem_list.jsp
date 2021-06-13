<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<!DOCTYPE html>
<html>
<head>
<title>客户投诉处理</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp"%>
<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
	function clearForm(){
			$("#page_form input[type='text']").val("");
			$("#status").val("");
			$.fn.zTree.getZTreeObj("tree_status").checkAllNodes(false);
		}	
	$(function(){
		$("#dealCZY").openComponent_employee();
   		var postData=$("#page_form").jsonForm();
		Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/custProblem/goJqGrid",
		postData:postData,
		height:$(document).height()-$("#content-list").offset().top-82,
		styleUI: 'Bootstrap', 
		colModel : [
				{name: "pk", index: "pk", width: 75, label: "pk", sortable: true, align: "left",hidden:true},
				{name: "addessstatus", index: "addessStatus", width: 75, label: "楼盘状态", sortable: true, align: "left",hidden:true},
				{name: "mobile1", index: "mobile1", width: 75, label: "业主电话", sortable: true, align: "left",hidden:true},
				{name: "projectdept3", index: "projectDept3", width: 75, label: "设计院", sortable: true, align: "left",hidden:true},
				{name: "designmandescr", index: "designManDescr", width: 75, label: "设计师", sortable: true, align: "left",hidden:true},
				{name: "designmanphone", index: "designManPhone", width: 75, label: "设计师电话", sortable: true, align: "left",hidden:true},
				{name: "projectmanphone", index: "projectManPhone", width: 75, label: "项目经理电话", sortable: true, align: "left",hidden:true},
				{name: "checkoutdate", index: "checkOutDate", width: 75, label: "实际结算时间", sortable: true, align: "left",formatter: formatDate,hidden:true},
				{name: "compstatus", index: "compStatus", width: 75, label: "投诉状态", sortable: true, align: "left",hidden:true},
				{name: "remarks", index: "remarks", width: 75, label: "投诉内容", sortable: true, align: "left",hidden:true},
				{name: "custtype", index: "custType", width: 75, label: "客户类型", sortable: true, align: "left",hidden:true},
				{name: "custcode", index: "custCode", width: 75, label: "客户编号", sortable: true, align: "left",hidden:true},
				{name: "crtdate", index: "crtDate", width: 75, label: "创建时间", sortable: true, align: "left",formatter: formatTime, hidden:true},
				{name: "crtczy", index: "crtCZY", width: 75, label: "登记人", sortable: true, align: "left", hidden:true},
				{name: "jscrtdate", index: "jsCrtDate", width: 75, label: "投诉时间", sortable: true, align: "left", formatter: formatTime,hidden:true},
				{name: "custdescr", index: "custdescr", width: 75, label: "业主姓名", sortable: true, align: "left"},
				{name: "address", index: "address", width: 160, label: "楼盘", sortable: true, align: "left"},
				{name: "promtype1descr", index: "promType1Descr", width: 75, label: "问题分类", sortable: true, align: "left"},
				{name: "suppldescr", index: "SupplDescr", width: 75, label: "供应商", sortable: true, align: "left"},
				{name: "promtype2descr", index: "PromType2Descr", width: 75, label: "材料分类", sortable: true, align: "left"},
				{name: "promrsndescr", index: "PromRsnDescr", width: 75, label: "原因", sortable: true, align: "left"},
				{name: "infodate", index: "infoDate", width: 122, label: "通知时间", sortable: true, align: "left", formatter: formatDate},
				{name: "dealczydescr", index: "DealCZYDescr", width: 100, label: "接收人", sortable: true, align: "left"},
				{name: "plandealdate", index: "planDealDate", width: 85, label: "计划处理时间", sortable: true, align: "left", formatter: formatDate},
				{name: "dealdate", index: "dealDate", width: 110, label: "处理时间", sortable: true, align: "left", formatter: formatDate},
				{name: "statusdescr", index: "StatusDescr", width: 110, label: "处理状态", sortable: true, align: "left"},
				{name: "dealremarks", index: "dealRemarks", width: 110, label: "处理结果", sortable: true, align: "left"},
				{name: "no", index: "no", width: 106, label: "投诉单号", sortable: true, align: "left"},
				{name: "source", index: "Source", width: 105, label: "问题来源", sortable: true, align: "left"},
				{name: "rcvdate", index: "RcvDate", width: 122, label: "接收时间", sortable: true, align: "left", formatter: formatTime},
				{name: "projectmandescr", index: "ProjectManDescr", width: 100, label: "项目经理", sortable: true, align: "left"},
				{name: "projectdept2", index: "ProjectDept2", width: 100, label: "工程部", sortable: true, align: "left"},
				{name: "lastupdate", index: "lastupdate", width: 122, label: "最后更新时间", sortable: true, align: "left", formatter: formatTime},
				{name: "lastupdatedby", index: "lastupdatedby", width: 110, label: "最后更新人员", sortable: true, align: "left"},
				{name: "expired", index: "expired", width: 110, label: "过期标志", sortable: true, align: "left"},
				{name: "actionlog", index: "actionLog", width: 110, label: "操作日志", sortable: true, align: "left"}
		],
	});
		
		//接收
		$("#rcv").on("click",function(){
			ret=selectDataTableRow();
			if(!ret){
				art.dialog({
	       			content: "请选择一条记录",
	       		});
	       		return;
			}
			if(ret.statusdescr!='待处理'){
					art.dialog({
	       			content: "请选择待处理的投诉单",
	       		});
			}else{
			  Global.Dialog.showDialog("Update",{
				  title:"客户投诉处理——接收",
				  url:"${ctx}/admin/custProblem/goRcv",
				  postData:{pk:ret.pk,addessStatus:ret.addessstatus,mobile1:ret.mobile1,projectDept3:ret.projectdept3,
				  designManDescr:ret.designmandescr,designManPhone:ret.designmanphone,projectManPhone:ret.projectmanphone,
				  projectManPhone:ret.projectmanphone,checkOutDate:ret.checkoutdate,compStatus:ret.compstatus,remarks:ret.remarks,
				  custType:ret.custtype,custCode:ret.custcode,address:ret.address,custdescr:ret.custdescr,projectManDescr:ret.projectmandescr,
				  projectDept2:ret.projectdept2,crtDate:ret.crtdate,source:ret.source,crtCZY:ret.crtczy.trim(),jsCrtDate:ret.jscrtdate,rcvDate:ret.rcvdate},
				  height:600,
				  width:1000,
				  returnFun:goto_query
			 });
			}
		});
		//处理
		$("#deal").on("click",function(){
			ret=selectDataTableRow();
			if(!ret){
				art.dialog({
	       			content: "请选择一条记录",
	       		});
	       		return;
			}
			if(ret.statusdescr!='正在处理'){
				art.dialog({
	       			content: "请选择正在处理的投诉单",
	       		});
			}else{
				Global.Dialog.showDialog("Update",{
					title:"客户投诉处理——处理",
					url:"${ctx}/admin/custProblem/goDeal",
					postData:{pk:ret.pk,addessStatus:ret.addessstatus,mobile1:ret.mobile1,projectDept3:ret.projectdept3,
					designManDescr:ret.designmandescr,designManPhone:ret.designmanphone,projectManPhone:ret.projectmanphone,
					projectManPhone:ret.projectmanphone,checkOutDate:ret.checkoutdate,compStatus:ret.compstatus,remarks:ret.remarks,
					custType:ret.custtype,custCode:ret.custcode,address:ret.address,custdescr:ret.custdescr,projectManDescr:ret.projectmandescr,
					projectDept2:ret.projectdept2,crtDate:ret.crtdate,source:ret.source,crtCZY:ret.crtczy.trim(),jsCrtDate:ret.jscrtdate,rcvDate:ret.rcvdate},
				    height:600,
				    width:1000,
					returnFun:goto_query
				});
			}
		});
		
		//修改处理结果
		$("#update").on("click",function(){
			ret=selectDataTableRow();
			if(!ret){
				art.dialog({
	       			content: "请选择一条记录",
	       		});
	       		return;
			}
			if(ret.statusdescr!='正在处理'){
				art.dialog({
	       			content: "请选择正在处理的投诉单",
	       		});
			}else{
				Global.Dialog.showDialog("Update",{
					title:"客户投诉处理——修改处理结果",
					url:"${ctx}/admin/custProblem/goUpdate",
					postData:{pk:ret.pk,addessStatus:ret.addessstatus,mobile1:ret.mobile1,projectDept3:ret.projectdept3,
					designManDescr:ret.designmandescr,designManPhone:ret.designmanphone,projectManPhone:ret.projectmanphone,dealRemarks:ret.dealremarks,
					projectManPhone:ret.projectmanphone,checkOutDate:ret.checkoutdate,compStatus:ret.compstatus,remarks:ret.remarks,
					custType:ret.custtype,custCode:ret.custcode,address:ret.address,custdescr:ret.custdescr,projectManDescr:ret.projectmandescr,
					projectDept2:ret.projectdept2,crtDate:ret.crtdate,source:ret.source,crtCZY:ret.crtczy.trim(),jsCrtDate:ret.jscrtdate,rcvDate:ret.rcvdate},
				   	height:450,
				    width:700,
					returnFun:goto_query
				});
			}
		});
		
		//查看
		$("#view").on("click",function(){
			ret=selectDataTableRow();
			if(!ret){
				art.dialog({
	       			content: "请选择一条记录",
	       		});
	       		return;
			}
			Global.Dialog.showDialog("view",{
				title:"客户投诉处理——查看",
				url:"${ctx}/admin/custProblem/goView",
				postData:{pk:ret.pk,addessStatus:ret.addessstatus,mobile1:ret.mobile1,projectDept3:ret.projectdept3,
				designManDescr:ret.designmandescr,designManPhone:ret.designmanphone,projectManPhone:ret.projectmanphone,
				projectManPhone:ret.projectmanphone,checkOutDate:ret.checkoutdate,compStatus:ret.compstatus,remarks:ret.remarks,
				custType:ret.custtype,custCode:ret.custcode,address:ret.address,custdescr:ret.custdescr,projectManDescr:ret.projectmandescr,
				projectDept2:ret.projectdept2,crtDate:ret.crtdate,source:ret.source,crtCZY:ret.crtczy.trim(),jsCrtDate:ret.jscrtdate,rcvDate:ret.rcvdate},
				height:600,
				width:1000,
				returnFun:goto_query
			});
		});
		//转售后
		$("#toCustService").on("click",function(){
			ret=selectDataTableRow();
			if(!ret){
				art.dialog({
	       			content: "请选择一条记录",
	       		});
	       		return;
			}
			if(ret.statusdescr!='待处理'){
				art.dialog({
	       			content: "请选择待处理的投诉单",
	       		});
			}else{
				Global.Dialog.showDialog("Add",{
					title:"客户投诉处理——转售后",
					url:"${ctx}/admin/custProblem/goToCustService",
					postData:{pk:ret.pk,custCode:ret.custcode,promRsnDescr:ret.promrsndescr,custDescr:ret.custdescr},
					height:600,
					width:1000,
					returnFun:goto_query
				});
			}	
		});
	});
		function doExcel(){
		var url = "${ctx}/admin/custProblem/doExcel";
		doExcelAll(url);
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
					<li><label>楼盘地址</label> <input type="text" id="address"
						name="address" />
					</li>
					<li><label>接收时间从</label> <input type="text" id="RcvDateFrom"
						name="RcvDateFrom" class="i-date" style="width:160px;"
						onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
					</li>
					<li><label>至</label> <input type="text" id="RcvDateTo"
						name="RcvDateTo" class="i-date" style="width:160px;"
						onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
					</li>
					<li><label>通知时间从</label> <input type="text" id="InfoDateFrom"
						name="InfoDateFrom" class="i-date" style="width:160px;"
						onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
					</li>
					<li><label>至</label> <input type="text" id="InfoDateTo"
						name="InfoDateTo" class="i-date" style="width:160px;"
						onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
					</li>
					<li><label>处理状态</label> <house:xtdmMulit id="status"
							dictCode="PROMSTATUS" selectedValue="0,2"></house:xtdmMulit>
					</li>
					<li><label>一级部门</label> 
						<house:department1 id="department1"
							onchange="changeDepartment1(this,'department2','${ctx }')"
							value="${employee.department1 }">
						</house:department1>
					</li>
					<li><label>二级部门</label> <house:department2 id="department2"
							dictCode="${employee.department1 }"
							value="${employee.department2 }"
							onchange="changeDepartment2(this,'department3','${ctx }')"></house:department2>
					</li>
					<li>
						<label>接收人</label>
						<input type="text" id="dealCZY" name="dealCZY" style="width:160px;"/>
					</li>
					<li class="search-group">
						<button type="button" class="btn btn-sm btn-system"
							onclick="goto_query();">查询</button>
						<button type="button" class="btn btn-sm btn-system"
							onclick="clearForm();">清空</button>
					</li>
				</ul>
			</form>
		</div>
	</div>
	<div class="clear_float"></div>
	<div class="btn-panel">
		<div class="btn-group-sm">
			<house:authorize authCode="CUSTPROBLEM_RECEIVE">
				<button type="button" class="btn btn-system" id="rcv">
					<span>接收</span>
				</button>
			</house:authorize>
			<house:authorize authCode="CUSTPROBLEM_DEAL">
				<button type="button" class="btn btn-system" id="deal">
					<span>处理</span>
				</button>
			</house:authorize>
			<house:authorize authCode="CUSTPROBLEM_TOCUSTSERVICE">
				<button type="button" class="btn btn-system" id="toCustService">
					<span>转售后</span>
				</button>
			</house:authorize>
			<house:authorize authCode="CUSTPROBLEM_VIEW">
				<button type="button" class="btn btn-system" id="view">
					<span>查看</span>
				</button>
			</house:authorize>
			<house:authorize authCode="CUSTPROBLEM_DEALREMARK">
				<button type="button" class="btn btn-system" id="update">
					<span>修改处理结果</span>
				</button>
			</house:authorize>
			<house:authorize authCode="CUSTPROBLEM_EXCEL">
				<button type="button" class="btn btn-system" onclick="doExcel()">
					<span>导出excel</span>
				</button>
			</house:authorize>
		</div>
	</div>
	<div id="content-list">
		<table id="dataTable"></table>
		<div id="dataTablePager"></div>
	</div>
</body>
</html>
