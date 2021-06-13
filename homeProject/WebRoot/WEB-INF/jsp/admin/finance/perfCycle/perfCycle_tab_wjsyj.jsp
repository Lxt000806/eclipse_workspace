<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>

<script type="text/javascript">
$(function(){
	//初始化未计算业绩表格
	gridOption ={
		url:"${ctx}/admin/perfCycle/goWjsyjJqGrid",
		postData:{custCode:$("#custCode").val(),address:$("#address").val(),
		businessMan:$("#businessMan").val(),designMan:$("#designMan").val(),
		againMan:$("#againMan").val(),documentNo:$("#documentNo").val(),checkStatus:$("#checkStatus").val()
		},
	    rowNum:10000000,
	    mustUseSort:true,
	    sortable: true,
	    searchBtn:true,
		height:320,
		colModel : [
			{name: "IsCheckedDescr", index: "IsCheckedDescr", width: 50, label: "复核", sortable: true, align: "left", frozen: true},
			{name: "CustCode", index: "CustCode", width: 70, label: "客户编号", sortable: true, align: "left", frozen: true},
			{name: "DocumentNo", index: "DocumentNo", width: 70, label: "档案号", sortable: true, align: "left", frozen: true},
			{name: "CustTypeDescr", index: "CustTypeDescr", width: 70, label: "客户类型", sortable: true, align: "left", frozen: true},
			{name: "Address", index: "Address", width: 140, label: "楼盘", sortable: true, align: "left", frozen: true},
			{name: "CustDescr", index: "CustDescr", width: 70, label: "客户名称", sortable: true, align: "left", frozen: true},
			{name: "TypeDescr", index: "TypeDescr", width: 70, label: "类型", sortable: true, align: "left", frozen: true},
			{name: "Quantity", index: "Quantity", width: 50, label: "单量", sortable: true, align: "right", sum: true},
			{name: "Area", index: "Area", width: 62, label: "面积", sortable: true, align: "right", sum: true},
			{name: "DesignFee", index: "DesignFee", width: 80, label: "实收设计费", sortable: true, align: "right", sum: true},
			{name: "BasePlan", index: "BasePlan", width: 70, label: "基础预算", sortable: true, align: "right", sum: true},
			{name: "BasePlanWithoutLongFee", index: "BasePlanWithoutLongFee", width: 120, label: "基础预算（不含远程费）", sortable: true, align: "right", sum: true},
			{name: "BasePerfPer", index: "BasePerfPer", width: 90, label: "基础业绩比例", sortable: true, align: "right"},
			{name: "ManageFee_BaseWithLongFee", index: "ManageFee_BaseWithLongFee", width: 94, label: "基础管理费（含远程费）", sortable: true, align: "right", sum: true},
			{name: "ManageFee_SumWithLongFee", index: "ManageFee_SumWithLongFee", width: 94, label: "管理费合计（含远程费）", sortable: true, align: "right", sum: true},
			{name: "BaseFee_Dirct", index: "BaseFee_Dirct", width: 70, label: "直接费", sortable: true, align: "right", sum: true},
			{name: "NoManageAmount", index: "NoManageAmount", width: 80, label: "不计管理费", sortable: true, align: "right", sum: true},
			{name: "MainPlan", index: "MainPlan", width: 70, label: "主材预算", sortable: true, align: "right", sum: true},
			{name: "IntegratePlan", index: "IntegratePlan", width: 70, label: "集成预算", sortable: true, align: "right", sum: true},
			{name: "CupboardPlan", index: "CupboardPlan", width: 70, label: "橱柜预算", sortable: true, align: "right", sum: true},
			{name: "SoftPlan", index: "SoftPlan", width: 70, label: "软装预算", sortable: true, align: "right", sum: true},
			{name: "MainServPlan", index: "MainServPlan", width: 112, label: "服务性产品预算", sortable: true, align: "right", sum: true},
			{name: "BaseDisc", index: "BaseDisc", width: 90, label: "协议优惠额", sortable: true, align: "right", sum: true},
			{name: "Markup", index: "Markup", width: 70, label: "优惠折扣", sortable: true, align: "right"},
			{name: "ContractFee", index: "ContractFee", width: 70, label: "合同总额", sortable: true, align: "right", sum: true},
			{name: "ContractAndTax", index: "ContractAndTax", width: 131, label: "合同额+税金", sortable: true, align: "right"},
			{name: "ContractAndDesignFee", index: "ContractAndDesignFee", width: 131, label: "合同总价+设计费+税金", sortable: true, align: "right"},
			{name: "LongFee", index: "LongFee", width: 70, label: "远程费", sortable: true, align: "right", sum: true},
			{name: "basepersonalplan", index: "basepersonalplan", width: 100, label: "基础个性化预算", sortable: true, align: "right", sum: true},
			{name: "managefee_basepersonal", index: "managefee_basepersonal", width: 100, label: "基础个性化管理费", sortable: true, align: "right", sum: true},
			{name: "woodplan", index: "woodplan", width: 100, label: "木作预算", sortable: true, align: "right", sum: true},
			{name: "managefee_wood", index: "managefee_wood", width: 100, label: "木作管理费", sortable: true, align: "right", sum: true},
			{name: "SoftFee_Furniture", index: "SoftFee_Furniture", width: 80, label: "软装家具费", sortable: true, align: "right", sum: true},
			{name: "MarketFund", index: "MarketFund", width: 90, label: "营销基金", sortable: true, align: "right", sum: true},
			{name: "BasePerfRadix", index: "BasePerfRadix", width: 90, label: "基础业绩基数", sortable: true, align: "right", sum: true},
			{name: "RealMaterPerf", index: "RealMaterPerf", width: 90, label: "实际材料业绩", sortable: true, align: "right", sum: true},
			{name: "MaxMaterPerf", index: "MaxMaterPerf", width: 90, label: "封顶材料业绩", sortable: true, align: "right"},
			{name: "AlreadyMaterPerf", index: "AlreadyMaterPerf", width: 90, label: "已算材料业绩", sortable: true, align: "right"},
			{name: "MaterPerf", index: "MaterPerf", width: 80, label: "材料业绩", sortable: true, align: "right", sum: true},
			{name: "PerfAmountWithoutSoftToken", index: "PerfAmountWithoutSoftToken", width: 120, label: "签单业绩（减软装券）", sortable: true, align: "right", sum: true},
			{name: "BaseDeductionWithoutLongFee", index: "BaseDeductionWithoutLongFee", width: 120, label: "基础单项扣减（不含远程费）", sortable: true, align: "right", sum: true},
			{name: "ItemDeduction", index: "ItemDeduction", width: 90, label: "材料单品扣减", sortable: true, align: "right", sum: true},
			{name: "RealPerfAmount", index: "RealPerfAmount", width: 90, label: "实际签单业绩", sortable: true, align: "right", sum: true},
			{name: "SoftTokenAmount", index: "SoftTokenAmount", width: 80, label: "软装券金额", sortable: true, align: "right", sum: true},
			{name: "PerfPerc", index: "PerfPerc", width: 70, label: "业绩比例", sortable: true, align: "right"},
			{name: "PerfDisc", index: "PerfDisc", width: 90, label: "业绩折扣金额", sortable: true, align: "right", sum: true},
			{name: "SetAdd", index: "SetAdd", width: 105, label: "套餐外基础增项", sortable: true, align: "right", sum: true},
			{name: "Gift", index: "Gift", width: 70, label: "实物赠送", sortable: true, align: "right", sum: true},
			{name: "RecalPerf", index: "RecalPerf", width: 80, label: "实际业绩", sortable: true, align: "right", sum: true},
			{name: "PerfMarkup", index: "PerfMarkup", width: 80, label: "业绩折扣", sortable: true, align: "right"},
			{name: "BefMarkupPerf", index: "BefMarkupPerf", width: 80, label: "折扣前业绩", sortable: true, align: "right", sum: true},
			{name: "IsCalPkPerfDescr", index: "IsCalPkPerfDescr", width: 115, label: "是否计算PK业绩", sortable: true, align: "left"},
			{name: "PayTypeDescr", index: "PayTypeDescr", width: 83, label: "付款方式", sortable: true, align: "left"},
			{name: "FirstPay", index: "FirstPay", width: 70, label: "首付款", sortable: true, align: "right", sum: true},
			{name: "MustReceive", index: "MustReceive", width: 70, label: "达标应收", sortable: true, align: "right", sum: true},
			{name: "RealReceive", index: "RealReceive", width: 70, label: "实收", sortable: true, align: "right", sum: true},
			{name: "SecondPay", index: "SecondPay", width: 70, label: "二期款", sortable: true, align: "right", sum: true},
			{name: "AchieveDate", index: "AchieveDate", width: 88, label: "达标时间", sortable: true, align: "left", formatter: formatDate, sum: true},
			{name: "MainProPer", index: "MainProPer", width: 80, label: "主材毛利率", sortable: true, align: "right",formatter:function(cellvalue, options, rowObject){return myRound(cellvalue*100,2)+"%";}},
			{name: "SignDate", index: "SignDate", width: 88, label: "签订时间", sortable: true, align: "left", formatter: formatDate},
			{name: "SetDate", index: "SetDate", width: 88, label: "下定时间", sortable: true, align: "left", formatter: formatDate},
			{name: "SetMinus", index: "SetMinus", width: 80, label: "套餐内减项", sortable: true, align: "right", sum: true},
			{name: "ManageFee_Base", index: "ManageFee_Base", width: 80, label: "基础管理费", sortable: true, align: "right", sum: true},
			{name: "ManageFee_InSet", index: "ManageFee_InSet", width: 90, label: "套餐内管理费", sortable: true, align: "right", sum: true},
			{name: "ManageFee_OutSet", index: "ManageFee_OutSet", width: 90, label: "套餐外管理费", sortable: true, align: "right", sum: true},
			{name: "ManageFee_Main", index: "ManageFee_Main", width: 80, label: "主材管理费", sortable: true, align: "right", sum: true},
			{name: "ManageFee_Int", index: "ManageFee_Int", width: 80, label: "集成管理费", sortable: true, align: "right", sum: true},
			{name: "ManageFee_Serv", index: "ManageFee_Serv", width: 120, label: "服务性产品管理费", sortable: true, align: "right", sum: true},
			{name: "ManageFee_Soft", index: "ManageFee_Soft", width: 80, label: "软装管理费", sortable: true, align: "right", sum: true},
			{name: "ManageFee_Cup", index: "ManageFee_Cup", width: 80, label: "橱柜管理费", sortable: true, align: "right", sum: true},
			{name: "ManageFee_Sum", index: "ManageFee_Sum", width: 80, label: "管理费合计", sortable: true, align: "right"},
			{name: "IsChgHolderDescr", index: "IsChgHolderDescr", width: 80, label: "干系人变动", sortable: true, align: "left"},
			{name: "BusinessManDescr", index: "BusinessManDescr", width: 70, label: "业务员", sortable: true, align: "left"},
			{name: "DesignManDescr", index: "DesignManDescr", width: 70, label: "设计师", sortable: true, align: "left"},
			{name: "AgainManDescr", index: "AgainManDescr", width: 70, label: "翻单员", sortable: true, align: "left"},
			{name: "SceneDesignDescr", index: "SceneDesignDescr", width: 100, label: "现场设计师", sortable: true, align: "left"},
			{name: "DeepenDesignDescr", index: "DeepenDesignDescr", width: 100, label: "深化设计师", sortable: true, align: "left"},
			{name: "DesiDept1Descr", index: "DesiDept1Descr", width: 90, label: "设计部门1", sortable: true, align: "left"},
			{name: "DesiDept2Descr", index: "DesiDept2Descr", width: 90, label: "设计部门2", sortable: true, align: "left"},
			{name: "BusiDept1Descr", index: "BusiDept1Descr", width: 90, label: "业务部门1", sortable: true, align: "left"},
			{name: "BusiDept2Descr", index: "BusiDept2Descr", width: 90, label: "业务部门2", sortable: true, align: "left"},
			{name: "ConstructTypeDescr", index: "ConstructTypeDescr", width: 70, label: "施工方式", sortable: true, align: "left"},
			{name: "IsModifiedDescr", index: "IsModifiedDescr", width: 70, label: "人工修改", sortable: true, align: "left"},
			{name: "DataTypeDescr", index: "DataTypeDescr", width: 70, label: "数据类型", sortable: true, align: "left"},
			{name: "CrtDate", index: "CrtDate", width: 136, label: "生成时间", sortable: true, align: "left", formatter: formatTime},
			{name: "DiscRemark", index: "DiscRemark", width: 319, label: "优惠政策", sortable: true, align: "left"},
			{name: "LastUpdate", index: "LastUpdate", width: 136, label: "最后更新时间", sortable: true, align: "left", formatter: formatTime},
			{name: "LastUpdatedBy", index: "LastUpdatedBy", width: 108, label: "最后更新人员", sortable: true, align: "left"},
			{name: "Expired", index: "Expired", width: 70, label: "是否过期", sortable: true, align: "left"},
			{name: "ActionLog", index: "ActionLog", width: 70, label: "操作日志", sortable: true, align: "left"},

		], 
		loadonce:true,
		gridComplete:function(){
            $(".ui-jqgrid-bdiv").scrollTop(0);
            frozenHeightReset("wjsyjDataTable");
        },
 	};
});


</script>
<div class="body-box-list" style="margin-top: 0px;">
	<div class="panel panel-system">
		<div class="panel-body">
		</div>
	</div>
	<div class="clear_float"></div>
	<!--query-form-->
	<div id="content-list">
		<table id="wjsyjDataTable"></table>
	</div>
</div>



