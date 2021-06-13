<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://" + request.getServerName() + ":"+request.getServerPort()+path+"/";
String jasperPath = PathUtil.WEB_INF+"jsp/jasper/";
%>
<!DOCTYPE html>
<html>
<head>
<title>基础人工成本管理</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp"%>
<script src="${resourceRoot}/pub/component_employee.js?v=${v}"
	type="text/javascript"></script>
<script type="text/javascript">
	function clearForm(){
		$("#page_form input[type='text']").val("");
		$("#status").val("");
		$("#type").val("");
		$.fn.zTree.getZTreeObj("tree_status").checkAllNodes(false);
	}	
	$(function(){
		Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/workCost/goJqGrid",
		height:$(document).height()-$("#content-list").offset().top-82,
		styleUI: 'Bootstrap', 
		colModel : [
				{name: "type", index: "type", width: 80, label: "申请类型", sortable: true, align: "left",hidden:true},
				{name: "status", index: "status", width: 60, label: "状态", sortable: true, align: "left",hidden:true},
				{name: "appczy", index: "appczy", width: 65, label: "开单人", sortable: true, align: "left",hidden:true},
				{name: "confirmczy", index: "confirmczy", width: 80, label: "审批人", sortable: true, align: "left",hidden:true},
				{name: "payczy", index: "payczy", width: 65, label: "出纳签字人", sortable: true, align: "left",hidden:true},
				{name: "issyscrt", index: "issyscrt", width: 80, label: "是否系统生成", sortable: true, align: "left",hidden:true},
				{name: "payczy", index: "payczy", width: 70, label: "出纳签字人", sortable: true, align: "left",hidden:true},
				{name: "no", index: "no", width: 100, label: "申请编号",  align: "left"},
				{name: "typedescr", index: "typedescr", width: 75, label: "类型", sortable: true, align: "left"},
				{name: "appczydescr", index: "appczydescr", width: 60, label: "开单人", sortable: true, align: "left"},
				{name: "date", index: "date", width: 130, label: "申请日期", sortable: true, align: "left", formatter: formatTime},
				{name: "statusdescr", index: "statusdescr", width: 50, label: "状态", sortable: true, align: "left"},
				{name: "confirmczydescr", index: "confirmczydescr", width: 60, label: "审批人", sortable: true, align: "left"},
				{name: "confirmdate", index: "confirmdate", width: 130, label: "审批日期", sortable: true, align: "left", formatter: formatTime},
				{name: "documentno", index: "documentno", width: 75, label: "凭证号", sortable: true, align: "left"},
				{name: "payczydescr", index: "payczydescr", width: 80, label: "出纳签字人", sortable: true, align: "left"},
				{name: "paydate", index: "paydate", width: 75, label: "签字日期", sortable: true, align: "left", formatter: formatDate},
				{name: "issyscrtdescr", index: "issyscrtdescr", width: 104, label: "是否系统生成", sortable: true, align: "left"},
				{name: "commino", index: "commino", width: 97, label: "提成领取单号", sortable: true, align: "left"},
				{name: "remarks", index: "remarks", width: 177, label: "备注", sortable: true, align: "left"},
				{name: "lastupdatedby", index: "lastupdatedby", width: 91, label: "最后修改人", sortable: true, align: "left"},
				{name: "lastupdate", index: "lastupdate", width: 132, label: "修改日期", sortable: true, align: "left", formatter: formatTime},
				{name: "expired", index: "expired", width: 80, label: "是否过期", sortable: true, align: "left"},
				{name: "actionlog", index: "actionlog", width: 80, label: "操作类型", sortable: true, align: "left"}
		],
	});
		$("#appCzy").openComponent_employee();
		$("#confirmCzy").openComponent_employee();
		$("#payCzy").openComponent_employee();
		
		//明细查询
		$("#detailQuery").on("click",function(){
				Global.Dialog.showDialog("detailQuery",{
					title:"基础人工成本--明细查询",
					url:"${ctx}/admin/workCostDetail/goDetailQuery",
				    height:675,
				    width:1000,
					returnFun:goto_query
				});
		});
		//人工成本汇总
		$("#costGather").on("click",function(){
				Global.Dialog.showDialog("goCostGather",{
					title:"基础人工成本--选择汇总数据",
					url:"${ctx}/admin/workCost/goCostGather",
				    height:600,
				    width:1000,
					returnFun:goto_query
				});
		});
		//工资出账处理
		$("#salaryCheckOut").on("click",function(){
			var ret = selectDataTableRow();
			if(!ret){
				art.dialog({
	       			content: "请选择一条记录",
	       		});
	       		return;
			}
			if(ret.status!="2" || ret.paydate!=""){
				art.dialog({
	       			content: "只有审核且未出纳签字状态可操作",
	       		});
	       		return;
			}
			Global.Dialog.showDialog("salaryCheckOut",{
				title:"基础人工成本--工资出账处理",
				url:"${ctx}/admin/workCost/goSalaryCheckOut",
				postData:{no:ret.no},
			    height:600,
			    width:1000,
				returnFun:goto_query
			});
		});
		//工资出账处理
		$("#salaryCheckOutQuery").on("click",function(){
			Global.Dialog.showDialog("salaryCheckOutQuery",{
				title:"基础人工成本--工资出账查询",
				url:"${ctx}/admin/workCost/goSalaryCheckOutQuery",
			    height:600,
			    width:1200,
				returnFun:goto_query
			});
		});
	});
	function doExcel(){
		var url = "${ctx}/admin/workCost/doExcel";
		doExcelAll(url);
	}
	//各按钮点击操作
	function go(btn,name,target){
		var postdata="";
		if(btn!='add'){
			var ret=selectDataTableRow();
			postdata={no:ret.no,appCzy:ret.appczy,appCZYDescr:ret.appczydescr,type:ret.type,payDate:ret.paydate,remarks:ret.remarks,
					date:ret.date,status:ret.status,confirmDate:ret.confirmdate,button:btn,documentNo:ret.documentno,
					confirmCzy:ret.confirmczy,confirmCZYDescr:ret.confirmczydescr,payCzy:ret.payczy,payCZYDescr:ret.payczydescr};
			if(!ret){
				art.dialog({
	       			content: "请选择一条记录",
	       		});
	       		return;
			}
			if(btn=='update'){
				if(ret.status!='1'){
					art.dialog({
		       			content: "只能对申请状态的基础人工成本进行编辑操作!",
	       			});
	       			return;
				}
			}else if(btn=='examine'){
				if(ret.status!='1'){
					art.dialog({
		       			content: "只能对申请状态的基础人工成本进行审核操作!",
	       			});
	       			return;
				}
			}else if(btn=='returnExamine'){
				if(ret.status!='2'){
					art.dialog({
		       			content: "只能对审核状态的基础人工成本进行反审核操作!",
	       			});
	       			return;
				}
				if(ret.payczy!=''){
					art.dialog({
		       			content: "该基础人工成本已经进行出纳签字操作，不能再进行反审核操作!",
	       			});
	       			return;
				}
				if(ret.issyscrt=='1'){
					art.dialog({
		       			content: "系统生成的基础人工成本不允许进行反审核操作!",
	       			});
	       			return;
				}
			}else if(btn=='sign'){
				if(ret.status!='2'){
					art.dialog({
		       			content: "只能对审核状态的基础人工成本进行出纳签字操作!",
	       			});
	       			return;
				}
				if(ret.type=='2'){
					art.dialog({
		       			content: "类型为工地扣款的记录不需要进行出纳签字操作!",
	       			});
	       			return;
				}
				if(ret.payczy!=''){
					art.dialog({
		       			content: "该条基础人工成本已经进行出纳签字操作!",
	       			});
	       			return;
				}
			}

		}else{
			postdata={button:btn};
		}
		Global.Dialog.showDialog("detail",{
					title:"基础人工成本管理——"+name,
					url:"${ctx}/admin/workCost/"+target,
					postData:postdata,
				    height:700,
				    width:1300,
					returnFun:goto_query
				});
	}
	//打印
	function print(){                
		var ret = selectDataTableRow();
	   	var reportName = "workCost_gcxmfytj.jasper";
	   	Global.Print.showPrint(reportName, {
	   		No:ret.no,
	   		AppDate:(ret.date).substring(0,10),
	   		DocumentNo:ret.documentno,
			LogoFile : "<%=basePath%>jasperlogo/logo.jpg",
			SUBREPORT_DIR: "<%=jasperPath%>" 
		});
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
					<li><label>申请编号</label> <input type="text" id="no" name="no"
						value="${workCost.no}" />
					</li>
					<li><label>类型</label> <house:xtdm id="type"
							dictCode="WorkCostType" value="${workCost.type}"></house:xtdm>
					</li>
					<li><label>开单人</label> <input type="text" id="appCzy"
						name="appCzy" style="width:160px;" value="${workCost.appCzy }" />
					</li>
					<li><label>状态</label> <house:xtdmMulit id="status"
							dictCode="WorkCostStatus" selectedValue="${workCost.status}"></house:xtdmMulit>
					</li>
					<li><label>申请日期从</label> <input type="text" id="dateFrom"
						name="dateFrom" class="i-date" style="width:160px;"
						onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
					</li>

					<li><label>至</label> <input type="text" id="dateTo"
						name="dateTo" class="i-date" style="width:160px;"
						onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
					</li>
					<li><label>审批日期从</label> <input type="text"
						id="confirmDateFrom" name="confirmDateFrom" class="i-date"
						style="width:160px;"
						onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
					</li>
					<li><label>至</label> <input type="text" id="confirmDateTo"
						name="confirmDateTo" class="i-date" style="width:160px;"
						onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
					</li>
					<li><label>审批人</label> <input type="text" id="confirmCzy"
						name="confirmCzy" style="width:160px;"
						value="${workCost.confirmCzy }" />
					</li>
					<li><label>凭证号</label> <input type="text" id="documentNo"
						name="documentNo" value="${workCost.documentNo}" />
					</li>
					<li><label>出纳签字人</label> <input type="text" id="payCzy"
						name="payCzy" style="width:160px;" value="${workCost.payCzy}" />
					</li> 
					</li>
					<li><label>提成领取单号</label> <input type="text" id="commiNo"
						name="commiNo" value="${workCost.commiNo}" />
					</li>
					<li><label>签字日期从</label> <input type="text" id="payDateFrom"
						name="payDateFrom" class="i-date" style="width:160px;"
						onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
					</li>
					<li><label>至</label> <input type="text" id="payDateTo"
						name="payDateTo" class="i-date" style="width:160px;"
						onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
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
			<house:authorize authCode="WORKCOST_ADD">
				<button type="button" class="btn btn-system" onclick="go('add','新增','goAdd')">
					<span>新增</span>
				</button>
			</house:authorize>
			<house:authorize authCode="WORKCOST_UPDATE">
				<button type="button" class="btn btn-system" onclick="go('update','编辑','goUpdate')">
					<span>编辑</span>
				</button>
			</house:authorize>
			<house:authorize authCode="WORKCOST_EXAMINE">
				<button type="button" class="btn btn-system" onclick="go('examine','审核','goExamine')">
					<span>审核</span>
				</button>
			</house:authorize>
			<house:authorize authCode="WORKCOST_RETURNEXAMINE">
				<button type="button" class="btn btn-system" onclick="go('returnExamine','反审核','goReturnExamine')">
					<span>反审核</span>
				</button>
			</house:authorize>
			<house:authorize authCode="WORKCOST_SIGN">
				<button type="button" class="btn btn-system" onclick="go('sign','出纳签字','goSign')" >
					<span>出纳签字</span>
				</button>
			</house:authorize>
			<house:authorize authCode="WORKCOST_VIEW">
				<button type="button" class="btn btn-system" onclick="go('view','查看','goView')">
					<span>查看</span>
				</button>
			</house:authorize>
			<house:authorize authCode="WORKCOST_DETAILQUERY">
				<button type="button" class="btn btn-system" id="detailQuery">
					<span>明细查询</span>
				</button>
			</house:authorize>
			<house:authorize authCode="WORKCOST_COSTGATHER">
				<button type="button" class="btn btn-system" id="costGather">
					<span>人工成本汇总</span>
				</button>
			</house:authorize>
			<house:authorize authCode="WORKCOST_SALARYCHECKOUT">
				<button type="button" class="btn btn-system" id="salaryCheckOut">
					<span>工资出账处理</span>
				</button>
			</house:authorize>
			<house:authorize authCode="WORKCOST_SALARYCHECKOUTQUERY">
				<button type="button" class="btn btn-system" id="salaryCheckOutQuery">
					<span>出账查询</span>
				</button>
			</house:authorize>
			<house:authorize authCode="WORKCOST_PRINT">
				<button type="button" class="btn btn-system" id="print" onclick="print()">
					<span>打印</span>
				</button>
			</house:authorize>
			<house:authorize authCode="WORKCOST_EXCEL">
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
