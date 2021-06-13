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
    <title>报表</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_prjPerf.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_department2.js?v=${v}" type="text/javascript"></script>

	<script type="text/javascript">
	function doExcel(){
		var url = "${ctx}/admin/prjPerf/doPrjExcel";
		doExcelAll(url);
	}
	/**初始化表格*/
	$(function(){
		$("#projectMan").openComponent_employee();	
		$("#no").openComponent_prjPerf();	
		
		Global.JqGrid.initJqGrid("dataTable",{
			height:$(document).height()-$("#content-list").offset().top-110,
			styleUI: "Bootstrap", 
			colModel : [
				{name: "custcode", index: "custcode", width: 70, label: "客户编号", sortable: true, align: "left", frozen: true},
				{name: "documentno", index: "documentno", width: 80, label: "档案号", sortable: true, align: "left", frozen: true},
				{name: "custtypedescr", index: "custtypedescr", width: 87, label: "客户类型", sortable: true, align: "left", frozen: true},
				{name: "address", index: "address", width: 140, label: "楼盘", sortable: true, align: "left", frozen: true},
				{name: "custdescr", index: "custdescr", width: 87, label: "客户名称", sortable: true, align: "left"},
				{name: "constructstatusdescr", index: "constructstatusdescr", width: 80, label: "施工状态", sortable: true, align: "left"},
				{name: "custcheckdate", index: "custcheckdate", width: 95, label: "客户结算时间", sortable: true, align: "left", formatter: formatDate},
				{name: "basectrlamount", index: "basectrlamount", width: 90, label: "发包金额", sortable: true, align: "right", sum: true},
				{name: "area", index: "area", width: 62, label: "面积", sortable: true, align: "right", sum: true},
				{name: "designfee", index: "designfee", width: 94, label: "设计费", sortable: true, align: "right", sum: true},
				{name: "baseplan", index: "baseplan", width: 70, label: "基础预算", sortable: true, align: "right", sum: true},
				{name: "basepersonalplan", index: "basepersonalplan", width: 105, label: "基础个性化预算", sortable: true, align: "right", sum: true},
				{name: "basepersonalchg", index: "basepersonalchg", width: 105, label: "基础个性化增减", sortable: true, align: "right", sum: true},
				{name: "longfee", index: "longfee", width: 78, label: "远程费", sortable: true, align: "right", sum: true},
				{name: "managefee", index: "managefee", width: 70, label: "管理费", sortable: true, align: "right", sum: true},
				{name: "setadd", index: "setadd", width: 119, label: "套餐外基础增项", sortable: true, align: "right", sum: true},
				{name: "setminus", index: "setminus", width: 95, label: "套餐内减项", sortable: true, align: "right", sum: true},
				{name: "mainplan", index: "mainplan", width: 81, label: "主材预算", sortable: true, align: "right", sum: true},
				{name: "intplan", index: "intplan", width: 85, label: "集成预算", sortable: true, align: "right", sum: true},
				{name: "cupplan", index: "cupplan", width: 86, label: "橱柜预算", sortable: true, align: "right", sum: true},
				{name: "softplan", index: "softplan", width: 96, label: "软装预算", sortable: true, align: "right", sum: true},
				{name: "servplan", index: "servplan", width: 112, label: "服务性产品预算", sortable: true, align: "right", sum: true},
				{name: "allplan", index: "allplan", width: 70, label: "预算小计", sortable: true, align: "right", sum: true},
				{name: "basedisc", index: "basedisc", width: 95, label: "协议优惠额", sortable: true, align: "right", sum: true},
				{name: "tax", index: "tax", width: 70, label: "税金", sortable: true, align: "right", sum: true},
				{name: "contractfee", index: "contractfee", width: 109, label: "合同总额", sortable: true, align: "right", sum: true},
				{name: "basechg", index: "basechg", width: 70, label: "基础增减", sortable: true, align: "right", sum: true},
				{name: "managechg", index: "managechg", width: 101, label: "管理费增减", sortable: true, align: "right", sum: true},
				{name: "taxchg", index: "taxchg", width: 70, label: "税金增减", sortable: true, align: "right", sum: true},
				{name: "designchg", index: "designchg", width: 106, label: "设计费增减", sortable: true, align: "right", sum: true},
				{name: "chgdisc", index: "chgdisc", width: 70, label: "变更优惠", sortable: true, align: "right", sum: true},
				{name: "mainchg", index: "mainchg", width: 70, label: "主材增减", sortable: true, align: "right", sum: true},
				{name: "intchg", index: "intchg", width: 70, label: "集成增减", sortable: true, align: "right", sum: true},
				{name: "softchg", index: "softchg", width: 70, label: "软装增减", sortable: true, align: "right", sum: true},
				{name: "furnchg", index: "furnchg", width: 70, label: "家具增减", sortable: true, align: "right", sum: true},
				{name: "servchg", index: "servchg", width: 70, label: "服务性产品增减", sortable: true, align: "right", sum: true},
				{name: "checkamount", index: "checkamount", width: 70, label: "结算金额", sortable: true, align: "right", sum: true},
				{name: "perfperc", index: "perfperc", width: 84, label: "扣减比例", sortable: true, align: "right"},
				{name: "perfdisc", index: "perfdisc", width: 84, label: "扣减金额", sortable: true, align: "right", sum: true},
				{name: "softtokenamount", index: "softtokenamount", width: 98, label: "软装券金额", sortable: true, align: "right", sum: true},
				{name: "gift", index: "gift", width: 86, label: "实物赠送", sortable: true, align: "right", sum: true},
				{name: "basededuction", index: "basededuction", width: 90, label: "基础单项扣减", sortable: true, align: "right", sum: true},
				{name: "itemdeduction", index: "itemdeduction", width: 90, label: "材料单品扣减", sortable: true, align: "right", sum: true},
				{name: "checkperf", index: "checkperf", width: 70, label: "结算业绩", sortable: true, align: "right", sum: true},
				{name: "providecard", index: "providecard", width: 70, label: "提成基数", sortable: true, align: "right", sum: true},
				{name: "provideamount", index: "provideamount", width: 70, label: "提成金额", sortable: true, align: "right", sum: true},
				{name: "prjdeptleaderdescr", index: "prjdeptleaderdescr", width: 83, label: "工程部经理", sortable: true, align: "left"},
				{name: "projectmandescr", index: "projectmandescr", width: 70, label: "项目经理", sortable: true, align: "left"},
				{name: "dept2descr", index: "dept2descr", width: 70, label: "工程部", sortable: true, align: "left"},
				{name: "checkmandescr", index: "checkmandescr", width: 70, label: "质检员", sortable: true, align: "left"},
				{name: "delayday", index: "delayday", width: 70, label: "拖期天数", sortable: true, align: "left"},
				{name: "designmandescr", index: "designmandescr", width: 83, label: "设计师", sortable: true, align: "left"},
				{name: "designmandept2descr", index: "designmandept2descr", width: 85, label: "设计所", sortable: true, align: "left"},
				{name: "designmandept1descr", index: "designmandept1descr", width: 90, label: "事业部", sortable: true, align: "left"},
				{name: "basechgcnt", index: "basechgcnt", width: 90, label: "基础变更次数", sortable: true, align: "right", sum: true},
				{name: "mainchgcnt", index: "mainchgcnt", width: 90, label: "主材变更次数", sortable: true, align: "right", sum: true},
				{name: "intchgcnt", index: "intchgcnt", width: 90, label: "集成变更次数", sortable: true, align: "right", sum: true},
				{name: "softchgcnt", index: "softchgcnt", width: 90, label: "软装变更次数", sortable: true, align: "right", sum: true},
				{name: "servchgcnt", index: "servchgcnt", width: 113, label: "服务性变更次数", sortable: true, align: "right", sum: true},
				{name: "allchgcnt", index: "allchgcnt", width: 90, label: "合计变更次数", sortable: true, align: "right", sum: true},
				{name: "freebaseamount", index: "freebaseamount", width: 90, label: "甲醛水电", sortable: true, align: "right", sum: true, title:"甲醛治理和水电延保"},
				{name: "lastupdate", index: "lastupdate", width: 136, label: "最后更新时间", sortable: true, align: "left", formatter: formatTime},
				{name: "lastupdatedby", index: "lastupdatedby", width: 100, label: "最后更新人员", sortable: true, align: "left"},
				{name: "expired", index: "expired", width: 70, label: "是否过期", sortable: true, align: "left"},
				{name: "actionlog", index: "actionlog", width: 85, label: "操作日志", sortable: true, align: "left"},
				{name: "perfmarkup", index: "perfmarkup", width: 85, label: "业绩折扣", sortable: true, align: "right"},
				{name: "preperf", index: "preperf", width: 85, label: "折扣前业绩", sortable: true, align: "right"},
			],
			loadComplete: function(){
				$('.ui-jqgrid-bdiv').scrollTop(0);
	       	 	$("#dataTable").setSelection('1');
          	  	frozenHeightReset("dataTable");
            },
		});
		$("#dataTable").jqGrid('setFrozenColumns');
		
		window.goto_query = function(){
			countType=$.trim($("#countType").val());
			var no=$.trim($("#no").val());
			$("#dataTable").jqGrid("setGridParam",{datatype:'json',url:"${ctx}/admin/prjPerf/getReportJqGrid",postData:$("#page_form").jsonForm(),page:1,}).trigger("reloadGrid");
		}
	});
	function clearForm(){
		$("#page_form input[type='text']").val("");
		$("#page_form select").val("");
		$("#custType").val("");
		$.fn.zTree.getZTreeObj("tree_custType").checkAllNodes(false);
		$("#custType_NAME").val('');
		
	}
	function goto_query(){
		$("#dataTable").jqGrid("setGridParam",{url:"${ctx}/admin/prjPerf/getReportJqGrid",postData:$("#page_form").jsonForm(),page:1}).trigger("reloadGrid");
	}
	</script>
</head>
<body>
	<div class="body-box-list">
		<div class="panel panel-system">
		    <div class="panel-body">
		      <div class="btn-group-xs">
					<button type="button" class="btn btn-system" onclick="doExcel()">
						<span>导出Excel</span>
					</button>
					<button type="button" class="btn btn-system" id="closeBut" onclick="closeWin(false)">
						<span>关闭</span>
					</button>
				</div>
			</div>
		</div>
		<div class="query-form">
			<form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
				<input type="hidden" name="jsonString" value=""/>
				<ul class="ul-form">
					<li>
						<label>楼盘</label>
						<input type="text" style="width:160px;" id="address" name="address"/>
					</li>
					<li>
						<label>统计周期</label>
						<input type="text" style="width:160px;" id="no" name="no" />
					</li>
					<li>
						<label>结算日期</label>
						<input type="text" id="dateFrom" name="dateFrom" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"/>
					</li>
					<li>
						<label>至</label>
						<input type="text" id="dateTo" name="dateTo" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"/>
					</li>
					<li>
						<label>工程部</label>
						<house:dict id="projectManDept2" dictCode="" 
							sql="select a.Code,a.code+' '+a.desc1+' '+isnull(e.NameChi,'') desc1  from dbo.tDepartment2 a
									left join tEmployee e on e.Department2=a.Code and e.IsLead='1' and e.LeadLevel='1' and e.expired='F'
									 where  a.deptype='3' and a.Expired='F' order By dispSeq " 
							sqlValueKey="Code" sqlLableKey="Desc1"  >
						</house:dict>
					</li>
					<li>
						<label>工程部经理</label>
						<input type="text" style="width:160px;" id="projectMan" name="projectMan"/>
					</li>
					<li>
						<label>客户类型</label>
						<house:DictMulitSelect id="custType" dictCode="" sql=" select * from tCustType  where Expired='F'" 
						sqlValueKey="Code" sqlLableKey="Desc1"  ></house:DictMulitSelect>
					</li>
					<li>
						<button type="button" class="btn btn-sm btn-system" onclick="goto_query();">查询</button>
						<button type="button" class="btn btn-sm btn-system" onclick="clearForm();">清空</button>
					</li>
				</ul>	
			</form>
		</div>
		<div class="pageContent">
			<div id="content-list">
				<table id="dataTable"></table>
				<div id="dataTablePager"></div>
			</div>
		</div> 
	</div>
</body>
</html>
