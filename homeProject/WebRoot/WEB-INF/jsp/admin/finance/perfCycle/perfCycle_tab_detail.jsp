<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>

<script type="text/javascript">
$(function(){
	var lastCellRowId;
	/**初始化表格*/
	var gridOption ={
		    mustUseSort:true,
		    searchBtn:true,
			height:280,
			colModel : [
				{name: "pk", index: "pk", width: 70, label: "编号", sortable: true, align: "left", frozen: true,hidden:true},
				{name: "ischeckeddescr", index: "ischeckeddescr", width: 50, label: "复核", sortable: true, align: "left", frozen: true},
				{name: "custcode", index: "custcode", width: 70, label: "客户编号", sortable: true, align: "left", frozen: true},
				{name: "documentno", index: "documentno", width: 70, label: "档案号", sortable: true, align: "left", frozen: true},
				{name: "custtypedescr", index: "custtypedescr", width: 70, label: "客户类型", sortable: true, align: "left", frozen: true},
				{name: "isinitsigndescr", index: "isinitsigndescr", width: 50, label: "草签", sortable: true, align: "left", frozen: true},
				{name: "address", index: "address", width: 140, label: "楼盘", sortable: true, align: "left", frozen: true},
				{name: "custdescr", index: "custdescr", width: 70, label: "客户名称", sortable: true, align: "left", frozen: true},
				{name: "typedescr", index: "typedescr", width: 70, label: "类型", sortable: true, align: "left", frozen: true},
				{name: "isaddallinfodescr", index: "isaddallinfodescr", width: 80, label: "非独立销售", sortable: true, align: "left", frozen: true},
				{name: "quantity", index: "quantity", width: 50, label: "单量", sortable: true, align: "right", sum: true},
				{name: "area", index: "area", width: 62, label: "面积", sortable: true, align: "right", sum: true},
				{name: "designfee", index: "designfee", width: 80, label: "实收设计费", sortable: true, align: "right", sum: true},
				{name: "baseplan", index: "baseplan", width: 70, label: "基础预算", sortable: true, align: "right", sum: true},
				{name: "baseplanwithoutlongfee", index: "baseplanwithoutlongfee", width: 120, label: "基础预算（不含远程费）", sortable: true, align: "right", sum: true},
				{name: "baseperfper", index: "baseperfper", width: 90, label: "基础业绩比例", sortable: true, align: "right"},
				{name: "managefee_basewithlongfee", index: "managefee_basewithlongfee", width: 94, label: "基础管理费（含远程费）", sortable: true, align: "right", sum: true},
				{name: "managefee_sumwithlongfee", index: "managefee_sumwithlongfee", width: 94, label: "管理费合计（含远程费）", sortable: true, align: "right", sum: true},
				{name: "basefee_dirct", index: "basefee_dirct", width: 70, label: "直接费", sortable: true, align: "right", sum: true},
				{name: "nomanageamount", index: "nomanageamount", width: 80, label: "不计管理费", sortable: true, align: "right", sum: true},
				{name: "mainplan", index: "mainplan", width: 70, label: "主材预算", sortable: true, align: "right", sum: true},
				{name: "integrateplan", index: "integrateplan", width: 70, label: "集成预算", sortable: true, align: "right",sum: true},
				{name: "cupboardplan", index: "cupboardplan", width: 70, label: "橱柜预算", sortable: true, align: "right", sum: true},
				{name: "softplan", index: "softplan", width: 70, label: "软装预算", sortable: true, align: "right", sum: true},
				{name: "mainservplan", index: "mainservplan", width: 112, label: "服务性产品预算", sortable: true, align: "right", sum: true},
				{name: "basedisc", index: "basedisc", width: 90, label: "协议优惠额", sortable: true, align: "right", sum: true},
				{name: "markup", index: "markup", width: 70, label: "优惠折扣", sortable: true, align: "right"},
				{name: "contractfee", index: "contractfee", width: 70, label: "合同总额", sortable: true, align: "right", sum: true},
				{name: "contractandtax", index: "contractandtax", width: 131, label: "合同总价+税金", sortable: true,align: "right"},
				{name: "contractanddesignfee", index: "contractanddesignfee", width: 145, label: "合同总价+设计费+税金", sortable: true,align: "right"},
				{name: "longfee", index: "longfee", width: 70, label: "远程费", sortable: true, align: "right", sum: true},
				{name: "tax", index: "tax", width: 70, label: "税金", sortable: true, align: "right", sum: true},
				{name: "basepersonal", index: "basepersonal", width: 100, label: "基础个性化预算", sortable: true, align: "right", sum: true},
				{name: "managefee_basepersonal", index: "managefee_basepersonal", width: 80, label: "基础个性化管理费", sortable: true, align: "right", sum: true},
				{name: "woodplan", index: "woodplan", width: 100, label: "木作预算", sortable: true, align: "right", sum: true},
				{name: "managefee_wood", index: "managefee_wood", width: 100, label: "木作管理费", sortable: true, align: "right", sum: true},
				{name: "softfee_furniture", index: "softfee_furniture", width: 80, label: "软装家具费", sortable: true, align: "right", sum: true},
				{name: "marketfund", index: "marketfund", width: 90, label: "营销基金", sortable: true, align: "right", sum: true},
				{name: "baseperfradix", index: "baseperfradix", width: 90, label: "基础业绩基数", sortable: true, align: "right", sum: true},
				{name: "realmaterperf", index: "realmaterperf", width: 90, label: "实际材料业绩", sortable: true, align: "right", sum: true},
				{name: "maxmaterperf", index: "maxmaterperf", width: 90, label: "封顶材料业绩", sortable: true, align: "right"},
				{name: "alreadymaterperf", index: "alreadymaterperf", width: 90, label: "已算材料业绩", sortable: true, align: "right"},
				{name: "materperf", index: "materperf", width: 80, label: "材料业绩", sortable: true, align: "right", sum: true},
				{name: "perfamountwithoutsofttoken", index: "perfamountwithoutsofttoken", width: 120, label: "签单业绩（减软装券）", sortable: true, align: "right", sum: true},
				{name: "basedeductionwithoutlongfee", index: "basedeductionwithoutlongfee", width: 120, label: "基础单项扣减（不含远程费）", sortable: true, align: "right", sum: true},
				{name: "itemdeduction", index: "itemdeduction", width: 90, label: "材料单品扣减", sortable: true, align: "right", sum: true},
				{name: "realperfamount", index: "realperfamount", width: 90, label: "实际签单业绩", sortable: true, align: "right", sum: true},
				{name: "tilestatusdescr", index: "tilestatusdescr", width: 70, label: "瓷砖状态", sortable: true, align: "left", hidden: true},
				{name: "bathstatusdescr", index: "bathstatusdescr", width: 70, label: "卫浴状态", sortable: true, align: "left", hidden: true},
				{name: "tilededuction", index: "tilededuction", width: 90, label: "瓷砖扣减业绩", sortable: true, align: "right", sum: true, hidden: true},
				{name: "bathdeduction", index: "bathdeduction", width: 90, label: "卫浴扣减业绩", sortable: true, align: "right", sum: true, hidden: true},
				{name: "maindeduction", index: "maindeduction", width: 90, label: "主材扣减业绩", sortable: true, align: "right", sum: true, hidden: true},
				{name: "softtokenamount", index: "softtokenamount", width: 80, label: "软装券金额", sortable: true, align: "right", sum: true},
				{name: "perfperc", index: "perfperc", width: 70, label: "业绩比例", sortable: true, align: "right"},
				{name: "perfdisc", index: "perfdisc", width: 90, label: "业绩折扣金额", sortable: true, align: "right", sum: true},
				{name: "setadd", index: "setadd", width: 105, label: "套餐外基础增项", sortable: true, align: "right", sum: true},
				{name: "gift", index: "gift", width: 70, label: "实物赠送", sortable: true, align: "right", sum: true},
				{name: "recalperf", index: "recalperf", width: 80, label: "实际业绩", sortable: true, align: "right", sum: true},
				{name: "perfmarkup", index: "perfmarkup", width: 80, label: "业绩折扣", sortable: true, align: "right"},
				{name: "befmarkupperf", index: "befmarkupperf", width: 80, label: "折扣前业绩", sortable: true, align: "right", sum: true},
				{name: "iscalpkperfdescr", index: "iscalpkperfdescr", width: 115, label: "是否计算PK业绩", sortable: true, align: "left"},
				{name: "paytypedescr", index: "paytypedescr", width: 83, label: "付款方式", sortable: true, align: "left"},
				{name: "firstpay", index: "firstpay", width: 70, label: "首付款", sortable: true, align: "right", sum: true},
				{name: "mustreceive", index: "mustreceive", width: 70, label: "达标应收", sortable: true, align: "right", sum: true},
				{name: "realreceive", index: "realreceive", width: 70, label: "实收", sortable: true, align: "right", sum: true},
				{name: "secondpay", index: "secondpay", width: 70, label: "二期款", sortable: true, align: "right", sum: true},
				{name: "achievedate", index: "achievedate", width: 88, label: "达标时间", sortable: true, align: "left", formatter: formatDate, sum: true},
				{name: "mainproper", index: "mainproper", width: 80, label: "主材毛利率", sortable: true, align: "right",formatter:function(cellvalue, options, rowObject){return myRound(cellvalue*100,2)+"%";}},
				{name: "signdate", index: "signdate", width: 88, label: "签订时间", sortable: true, align: "left", formatter: formatDate},
				{name: "setdate", index: "setdate", width: 88, label: "下定时间", sortable: true, align: "left", formatter: formatDate},
				{name: "setminus", index: "setminus", width: 80, label: "套餐内减项", sortable: true, align: "right", sum: true},
				{name: "managefee_base", index: "managefee_base", width: 80, label: "基础管理费", sortable: true, align: "right", sum: true},
				{name: "managefee_inset", index: "managefee_inset", width: 90, label: "套餐内管理费", sortable: true, align: "right", sum: true},
				{name: "managefee_outset", index: "managefee_outset", width: 90, label: "套餐外管理费", sortable: true, align: "right", sum: true},
				{name: "managefee_main", index: "managefee_main", width: 80, label: "主材管理费", sortable: true, align: "right", sum: true},
				{name: "managefee_int", index: "managefee_int", width: 80, label: "集成管理费", sortable: true, align: "right", sum: true},
				{name: "managefee_serv", index: "managefee_serv", width: 120, label: "服务性产品管理费", sortable: true, align: "right", sum: true},
				{name: "managefee_soft", index: "managefee_soft", width: 80, label: "软装管理费", sortable: true, align: "right", sum: true},
				{name: "managefee_cup", index: "managefee_cup", width: 80, label: "橱柜管理费", sortable: true, align: "right", sum: true},
				{name: "managefee_sum", index: "managefee_sum", width: 80, label: "管理费合计", sortable: true, align: "right"},
				{name: "ischgholderdescr", index: "ischgholderdescr", width: 80, label: "干系人变动", sortable: true, align: "left"},
				{name: "businessmandescr", index: "businessmandescr", width: 70, label: "业务员", sortable: true, align: "left"},
				{name: "designmandescr", index: "designmandescr", width: 70, label: "设计师", sortable: true, align: "left"},
				{name: "againmandescr", index: "againmandescr", width: 70, label: "翻单员", sortable: true, align: "left"},
				{name: "busidrcdescr", index: "busidrcdescr", width: 70, label: "业务主任", sortable: true, align: "left"},
				{name: "businessmanleader", index: "businessmanleader", width: 80, label: "业务部领导", sortable: true, align: "left"},
				{name: "designmanleader", index: "designmanleader", width: 80, label: "设计部领导", sortable: true, align: "left"},
				{name: "businessmandeptdescr", index: "businessmandeptdescr", width: 108, label: "业务部", sortable: true, align: "left", hidden: true},
				{name: "designmandeptdescr", index: "designmandeptdescr", width: 108, label: "设计部", sortable: true, align: "left", hidden: true},
				{name: "desidept1descr", index: "desidept1descr", width: 90, label: "设计部门1", sortable: true, align: "left"},
				{name: "desidept2descr", index: "desidept2descr", width: 90, label: "设计部门2", sortable: true, align: "left"},
				{name: "busidept1descr", index: "busidept1descr", width: 90, label: "业务部门1", sortable: true, align: "left"},
				{name: "busidept2descr", index: "busidept2descr", width: 90, label: "业务部门2", sortable: true, align: "left"},
				{name: "prjdept2descr", index: "prjdept2descr", width: 90, label: "翻单员部门", sortable: true, align: "left"},
				{name: "scenedesigndescr", index: "scenedesigndescr", width: 100, label: "现场设计师", sortable: true, align: "left"},
				{name: "deependesigndescr", index: "deependesigndescr", width: 100, label: "深化设计师", sortable: true, align: "left"},
				{name: "iscalbusimanperfdescr", index: "iscalbusimanperfdescr", width: 120, label: "计算业务员业绩", sortable: true, align: "left"},
				{name: "iscaldesimanperfdescr", index: "iscaldesimanperfdescr", width: 120, label: "计算设计师业绩", sortable: true, align: "left"},
				{name: "iscalagainmanperfdescr", index: "iscalagainmanperfdescr", width: 120, label: "计算翻单员业绩", sortable: true, align: "left"},
				{name: "iscalbusideptperfdescr", index: "iscalbusideptperfdescr", width: 120, label: "计算业务部业绩", sortable: true, align: "left"},
				{name: "iscaldesideptperfdescr", index: "iscaldesideptperfdescr", width: 120, label: "计算设计部业绩", sortable: true, align: "left"},
				{name: "iscalagaindeptperfdescr", index: "iscalagaindeptperfdescr", width: 120, label: "计算翻单部门业绩", sortable: true, align: "left"},
				{name: "oldbusinessmandescr", index: "oldbusinessmandescr", width: 109, label: "原业绩业务员", sortable: true, align: "left"},
				{name: "olddesignmandescr", index: "olddesignmandescr", width: 103, label: "原业绩设计师", sortable: true, align: "left"},
				{name: "oldbusidept1descr", index: "oldbusidept1descr", width: 107, label: "原业绩业务部1", sortable: true, align: "left"},
				{name: "oldbusinessmandescrdep", index: "oldbusinessmandescrdep", width: 118, label: "原业绩业务部2", sortable: true, align: "left"},
				{name: "olddesidept1descr", index: "olddesidept1descr", width: 107, label: "原业绩设计部1", sortable: true, align: "left"},
				{name: "olddesignmandescrdep", index: "olddesignmandescrdep", width: 118, label: "原业绩设计部2", sortable: true, align: "left"},
				{name: "oldbusidrcdescr", index: "oldbusidrcdescr", width: 94, label: "原业务主任", sortable: true, align: "left"},
				{name: "oldbusinessmanleader", index: "oldbusinessmanleader", width: 125, label: "原业绩业务部领导", sortable: true, align: "left"},
				{name: "olddesignmanleader", index: "olddesignmanleader", width: 125, label: "原业绩设计部领导", sortable: true, align: "left"},
				{name: "oldyear", index: "oldyear", width: 110, label: "原业绩归属年份", sortable: true, align: "right"},
				{name: "oldmonth", index: "oldmonth", width: 110, label: "原业绩归属月份", sortable: true, align: "right"},
				{name: "remarks", index: "remarks", width: 253, label: "备注", sortable: true, align: "left"},
				{name: "constructtypedescr", index: "constructtypedescr", width: 70, label: "施工方式", sortable: true, align: "left"},
				{name: "ismodifieddescr", index: "ismodifieddescr", width: 70, label: "人工修改", sortable: true, align: "left"},
				{name: "datatypedescr", index: "datatypedescr", width: 70, label: "数据类型", sortable: true, align: "left"},
				{name: "crtdate", index: "crtdate", width: 136, label: "生成时间", sortable: true, align: "left", formatter: formatTime},
				{name: "discremark", index: "discremark", width: 319, label: "优惠政策", sortable: true, align: "left"},
				{name: "lastupdate", index: "lastupdate", width: 136, label: "最后更新时间", sortable: true, align: "left", formatter: formatTime},
				{name: "lastupdatedby", index: "lastupdatedby", width: 108, label: "最后更新人员", sortable: true, align: "left"},
				{name: "expired", index: "expired", width: 70, label: "是否过期", sortable: true, align: "left"},
				{name: "actionlog", index: "actionlog", width: 70, label: "操作日志", sortable: true, align: "left"},
			], 
			gridComplete:function(){
                $(".ui-jqgrid-bdiv").scrollTop(0);
	            frozenHeightReset("detailDataTable");
	        },
 		};
	   Global.JqGrid.initJqGrid("detailDataTable",gridOption);
	   $("#detailDataTable").jqGrid("setFrozenColumns");
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
		<table id="detailDataTable"></table>
		<div id="detailDataTablePager"></div>
	</div>
</div>



