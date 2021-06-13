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
	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>

	<script type="text/javascript">
	function doExcel(){
		var url = "${ctx}/admin/prjPerf/doCountExcel";
		doExcelAll(url);
	}
	
	function doCount(calcType){
		$.ajax({
			url:"${ctx}/admin/prjPerf/doCount",
			type: "post",
			data: {no:"${no}",calcType:calcType},
			dataType: "json",
			cache: false,
			error: function(obj){
				showAjaxHtml({"hidden": false, "msg": "保存数据出错~"});
		    },
		    success: function(obj){
				$("#dataTable").jqGrid("setGridParam",{postData:$("#page_form").jsonForm(),page:1}).trigger("reloadGrid");
				art.dialog({
					content	:"生成数据成功",			
				});
		    }
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
		Global.Dialog.showDialog("viewUpdate",{
			title:"业绩明细——编辑",
			postData:{map:JSON.stringify(ret)},
			url:"${ctx}/admin/prjPerf/goUpdatePrjPerfDetail",
			height: 670,
		 	width:1250,
		 	returnFun:goto_query
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
		Global.Dialog.showDialog("viewDetail",{
			title:"业绩明细——查看",
			postData:{map:JSON.stringify(ret)},
			url:"${ctx}/admin/prjPerf/goPrjPerfDetail",
			height: 670,
		 	width:1250,
		});
	}
	
	/**初始化表格*/
	$(function(){
		if("${m_status}"=="V"){
			$("#doCount").attr("disabled","disabled");
			$("#update").attr("disabled","disabled");
			$("#doRegen").attr("disabled","disabled");

			if("2"==$.trim("${prjPerf.status }")){
				console.log(11);
				$("#statusSpan").removeAttr("hidden","true");
			}
		}
		$("#projectMan").openComponent_employee();	
	
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/prjPerf/getCountPrjPerJqGrid",
			postData:{no:"${no}"},
			height:$(document).height()-$("#content-list").offset().top-102,
			styleUI: "Bootstrap", 
			colModel : [
			  	{name: "pk", index: "pk", width: 82, label: "pk", sortable: true, align: "left", frozen: true,hidden:true},
			  	{name: "custcode", index: "custcode", width: 75, label: "客户编号", sortable: true, align: "left", frozen: true},
				{name: "documentno", index: "documentno", width: 70, label: "档案号", sortable: true, align: "left", frozen: true},
				{name: "custtypedescr", index: "custtypedescr", width: 70, label: "客户类型", sortable: true, align: "left", frozen: true},
				{name: "address", index: "address", width: 140, label: "楼盘", sortable: true, align: "left", frozen: true},
				{name: "custdescr", index: "custdescr", width: 70, label: "客户名称", sortable: true, align: "left", hidden: true},
				{name: "constructstatusdescr", index: "constructstatusdescr", width: 80, label: "施工状态", sortable: true, align: "left"},
				{name: "custtype", index: "custtype", width: 70, label: "客户类型", sortable: true, align: "left", hidden:true},
				{name: "custcheckdate", index: "custcheckdate", width: 100, label: "客户结算时间", sortable: true, align: "left", formatter: formatDate},
				{name: "basectrlamount", index: "basectrlamount", width: 70, label: "发包金额", sortable: true, align: "right", sum: true},
				{name: "area", index: "area", width: 60, label: "面积", sortable: true, align: "right", sum: true},
				{name: "designfee", index: "designfee", width: 70, label: "设计费", sortable: true, align: "right", sum: true},
				{name: "baseplan", index: "baseplan", width: 70, label: "基础预算", sortable: true, align: "right", sum: true},
				{name: "longfee", index: "longfee", width: 70, label: "远程费", sortable: true, align: "right", sum: true},
				{name: "managefee", index: "managefee", width: 65, label: "管理费", sortable: true, align: "right", sum: true},
				{name: "setadd", index: "setadd", width: 100, label: "套餐外基础增项", sortable: true, align: "right", sum: true},
				{name: "setminus", index: "setminus", width: 85, label: "套餐内减项", sortable: true, align: "right", sum: true},
				{name: "mainplan", index: "mainplan", width: 70, label: "主材预算", sortable: true, align: "right", sum: true},
				{name: "intplan", index: "intplan", width: 70, label: "集成预算", sortable: true, align: "right", sum: true},
				{name: "cupplan", index: "cupplan", width: 70, label: "橱柜预算", sortable: true, align: "right", sum: true},
				{name: "softplan", index: "softplan", width: 70, label: "软装预算", sortable: true, align: "right", sum: true},
				{name: "servplan", index: "servplan", width: 100, label: "服务性产品预算", sortable: true, align: "right", sum: true},
				{name: "allplan", index: "allplan", width: 70, label: "预算小计", sortable: true, align: "right", sum: true},
				{name: "furnplan", index: "furnplan", width: 80, label: "furnplan", sortable: true, align: "right", sum: true,hidden:true},
				{name: "basedisc", index: "basedisc", width: 85, label: "协议优惠额", sortable: true, align: "right", sum: true},
				{name: "tax", index: "tax", width: 70, label: "税金", sortable: true, align: "right", sum: true},
				{name: "contractfee", index: "contractfee", width: 70, label: "合同总额", sortable: true, align: "right", sum: true},
				{name: "basechg", index: "basechg", width: 70, label: "基础增减", sortable: true, align: "right", sum: true},
				{name: "managechg", index: "managechg", width: 85, label: "管理费增减", sortable: true, align: "right", sum: true},
				{name: "taxchg", index: "taxchg", width: 70, label: "税金增减", sortable: true, align: "right", sum: true},
				{name: "designchg", index: "designchg", width: 85, label: "设计费增减", sortable: true, align: "right", sum: true},
				{name: "chgdisc", index: "chgdisc", width: 70, label: "变更优惠", sortable: true, align: "right", sum: true},
				{name: "mainchg", index: "mainchg", width: 70, label: "主材增减", sortable: true, align: "right", sum: true},
				{name: "intchg", index: "intchg", width: 70, label: "集成增减", sortable: true, align: "right", sum: true},
				{name: "softchg", index: "softchg", width: 70, label: "软装增减", sortable: true, align: "right", sum: true},
				{name: "furnchg", index: "furnchg", width: 70, label: "家具增减", sortable: true, align: "right", sum: true,hidden:true},
				{name: "furncheck", index: "furncheck", width: 70, label: "家具结算", sortable: true, align: "right", sum: true},
				{name: "servchg", index: "servchg", width: 70, label: "服务性产品增减", sortable: true, align: "right", sum: true},
				{name: "checkamount", index: "checkamount", width: 70, label: "结算金额", sortable: true, align: "right", sum: true},
				{name: "perfperc", index: "perfperc", width: 70, label: "业绩比例", sortable: true, align: "right"},
				{name: "perfdisc", index: "perfdisc", width: 70, label: "扣减金额", sortable: true, align: "right", sum: true},
				{name: "softtokenamount", index: "softtokenamount", width: 85, label: "软装券金额", sortable: true, align: "right", sum: true},
				{name: "gift", index: "gift", width: 70, label: "实物赠送", sortable: true, align: "right", sum: true},
				{name: "basededuction", index: "basededuction", width: 90, label: "基础单项扣减", sortable: true, align: "right", sum: true},
				{name: "itemdeduction", index: "itemdeduction", width: 90, label: "材料单品扣减", sortable: true, align: "right", sum: true},
				{name: "checkperf", index: "checkperf", width: 70, label: "结算业绩", sortable: true, align: "right", sum: true},
				{name: "providecard", index: "providecard", width: 70, label: "提成基数", sortable: true, align: "right", sum: true},
				{name: "provideamount", index: "provideamount", width: 70, label: "提成金额", sortable: true, align: "right", sum: true},
				{name: "prjdeptleaderdescr", index: "prjdeptleaderdescr", width: 80, label: "工程部经理", sortable: true, align: "left"},
				{name: "projectmandescr", index: "projectmandescr", width: 80, label: "项目经理", sortable: true, align: "left"},
				{name: "dept2descr", index: "dept2descr", width: 70, label: "工程部", sortable: true, align: "left"},
				{name: "checkmandescr", index: "checkmandescr", width: 70, label: "质检员", sortable: true, align: "left"},
				{name: "delayday", index: "delayday", width: 70, label: "拖期天数", sortable: true, align: "right"},
				{name: "basechgcnt", index: "basechgcnt", width: 85, label: "基础变更次数", sortable: true, align: "right", sum: true},
				{name: "mainchgcnt", index: "mainchgcnt", width: 85, label: "主材变更次数", sortable: true, align: "right", sum: true},
				{name: "intchgcnt", index: "intchgcnt", width: 85, label: "集成变更次数", sortable: true, align: "right", sum: true},
				{name: "softchgcnt", index: "softchgcnt", width: 85, label: "软装变更次数", sortable: true, align: "right", sum: true},
				{name: "servchgcnt", index: "servchgcnt", width: 100, label: "服务性变更次数", sortable: true, align: "right", sum: true},
				{name: "allchgcnt", index: "allchgcnt", width: 85, label: "合计变更次数", sortable: true, align: "right", sum: true},
				{name: "ismodifieddescr", index: "ismodifieddescr", width: 70, label: "人工修改", sortable: true, align: "left"},
				{name: "payeedescr", index: "payeedescr", width: 80, label: "签约公司", sortable: true, align: "left",},
				{name: "lastupdate", index: "lastupdate", width: 100, label: "最后更新时间", sortable: true, align: "left", formatter: formatTime},
				{name: "lastupdatedby", index: "lastupdatedby", width: 100, label: "最后更新人员", sortable: true, align: "left"},
				{name: "expired", index: "expired", width: 80, label: "是否过期", sortable: true, align: "left"},
				{name: "actionlog", index: "actionlog", width: 85, label: "操作日志", sortable: true, align: "left"},
				{name: "perfexpr", index: "perfexpr", width: 85, label: "业绩公式", sortable: true, align: "left",hidden:true},
				{name: "perfexprremarks", index: "perfexprremarks", width: 85, label: "业绩公式", sortable: true, align: "left",hidden:true},
				{name: "ismodified", index: "ismodified", width: 85, label: "ismodified", sortable: true, align: "left",hidden:true},
				{name: "perfmarkup", index: "perfmarkup", width: 85, label: "业绩折扣", sortable: true, align: "right"},
				{name: "preperf", index: "preperf", width: 85, label: "折扣前业绩", sortable: true, align: "right"},
				{name: "cmpname", index: "cmpname", width: 85, label: "分公司", sortable: true, align: "left"},
				{name: "managefee_base", index: "managefee_base", width: 96, label: "基础管理费", sortable: true, align: "right", sum: true},
				{name: "managechg_base", index: "managechg_base", width: 106, label: "基础管理费增减", sortable: true, align: "right", sum: true},
				{name: "managefee_main", index: "managefee_main", width: 96, label: "主材管理费", sortable: true, align: "right", sum: true},
				{name: "managechg_main", index: "managechg_main", width: 106, label: "主材管理费增减", sortable: true, align: "right", sum: true},
				{name: "managefee_int", index: "managefee_int", width: 96, label: "集成管理费", sortable: true, align: "right", sum: true},
				{name: "managechg_int", index: "managechg_int", width: 106, label: "集成管理费增减", sortable: true, align: "right", sum: true},
				{name: "managefee_cup", index: "managefee_cup", width: 96, label: "橱柜管理费", sortable: true, align: "right", sum: true},
				{name: "managechg_cup", index: "managechg_cup", width: 106, label: "橱柜管理费增减", sortable: true, align: "right", sum: true},
				{name: "managefee_soft", index: "managefee_soft", width: 96, label: "软装管理费", sortable: true, align: "right", sum: true},
				{name: "managechg_soft", index: "managechg_soft", width: 106, label: "软装管理费增减", sortable: true, align: "right", sum: true},
				{name: "managefee_serv", index: "managefee_serv", width: 96, label: "服务性管理费", sortable: true, align: "right", sum: true},
				{name: "managechg_serv", index: "managechg_serv", width: 120, label: "服务性管理费增减", sortable: true, align: "right", sum: true},
				{name: "scenedesignercheck", index: "scenedesignercheck", width: 120, label: "现场设计师结算业绩", sortable: true, align: "right", sum: true},
				{name: "noscenedesignercheck", index: "noscenedesignercheck", width: 120, label: "非现场设计师结算额", sortable: true, align: "right", sum: true},
				{name: "basepersonalplan", index: "basepersonalplan", width: 105, label: "基础个性化预算", sortable: true, align: "right", sum: true},
				{name: "basepersonalchg", index: "basepersonalchg", width: 105, label: "基础个性化增减", sortable: true, align: "right", sum: true},
			],
			ondblClickRow: function(){
				view();
			},
			loadComplete: function(){
				$('.ui-jqgrid-bdiv').scrollTop(0);
	       	 	$("#dataTable").setSelection('1');
          	  	frozenHeightReset("dataTable");
            },
			
		});
		$("#dataTable").jqGrid('setFrozenColumns');
		
		function DiyFmatter (cellvalue, options, rowObject){ 
		    return myRound(cellvalue,2);
		}
	});
	function clearForm(){
		$("#page_form input[type='text']").val("");
		$("#page_form select").val("");
		$("#custType").val("");
		$("#employee").val("");
		$("#openComponent_employee_projectMan").val("");
		$.fn.zTree.getZTreeObj("tree_custType").checkAllNodes(false);
		$("#custType_NAME").val('');
		
	} 
	</script>
</head>
<body>
	<div class="body-box-list">
		<div class="panel panel-system">
		    <div class="panel-body">
		      	<div class="btn-group-xs">
					<button type="button" class="btn btn-system" id="doCount" onclick="doCount('1')">
						<span>数据生成</span>
					</button>
					<button type="button" class="btn btn-system" id="doRegen" onclick="doCount('2')">
						<span>重算结算业绩</span>
					</button>
					<button type="button" class="btn btn-system" id="update" onclick="update()">
						<span>编辑</span>
					</button>
					<button type="button" class="btn btn-system" onclick="view()">
						<span>查看</span>
					</button>
					<button type="button" class="btn btn-system" onclick="doExcel()" title="导出检索条件数据">
						<span>导出Excel</span>
					</button>
					<button type="button" class="btn btn-system" id="closeBut" onclick="closeWin(false)">
						<span>关闭</span>
					</button>
					<span style="color:red" id="statusSpan" hidden="true">该周期业绩已计算完成！</span>
				</div>
			</div>
		</div>
		<div class="query-form">
			<form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
				<house:token></house:token>
				<input type="hidden" name="jsonString" value=""/>
				<input type="hidden" style="width:160px;" id="no" name="no" value="${no }"/>
				<ul class="ul-form">
					<li>
						<label>楼盘</label>
						<input type="text" style="width:160px;" id="address" name="address"/>
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
						<label>分公司</label>
						<house:dict id="company" dictCode="" 
							sql="select a.Code,a.desc2  from dbo.tCompany a  where a.Expired='F'" 
							sqlValueKey="Code" sqlLableKey="Desc2"  >
						</house:dict>
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
