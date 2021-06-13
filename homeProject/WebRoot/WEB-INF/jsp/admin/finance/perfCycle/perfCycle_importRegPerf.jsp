<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<title>导入原业绩</title>
		<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
		<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
		<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
		<META HTTP-EQUIV="expires" CONTENT="0" />
		<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
		<%@ include file="/commons/jsp/common.jsp" %>
	<script type="text/javascript">
		var selectRows = [];
		/**初始化表格*/
		$(function(){
			var gridOption ={
				url:"${ctx}/admin/perfCycle/goYyjJqGrid",
				postData:{
					custCode:"${perfCycle.custCode}",
				},
				height:400,
				mustUseSort:true,
				multiselect: true,
				colModel : [
					{name: "pk", index: "pk", width: 82, label: "pk", sortable: true, align: "left", frozen: true,hidden:true},
					{name: "custcode", index: "custcode", width: 82, label: "客户编号", sortable: true, align: "left", frozen: true},
					{name: "address", index: "address", width: 169, label: "楼盘", sortable: true, align: "left", frozen: true},
					{name: "perfdocumentno", index: "perfdocumentno", width: 70, label: "档案号", sortable: true, align: "left",},
					{name: "y", index: "y", width: 83, label: "归属年费", sortable: true, align: "left"},
					{name: "m", index: "m", width: 83, label: "归属月份", sortable: true, align: "left"},
					{name: "typedescr", index: "typedescr", width: 88, label: "类型", sortable: true, align: "left"},
					{name: "quantity", index: "quantity", width: 56, label: "单量", sortable: true, align: "left", sum: true},
					{name: "area", index: "area", width: 62, label: "面积", sortable: true, align: "left", sum: true},
					{name: "designfee", index: "designfee", width: 94, label: "实收设计费", sortable: true, align: "left", sum: true},
					{name: "perfsetadd", index: "perfsetadd", width: 94, label: "套餐外基础增项", sortable: true, align: "left", sum: true},
					{name: "baseplan", index: "baseplan", width: 80, label: "基础预算", sortable: true, align: "left", sum: true},
					{name: "mainplan", index: "mainplan", width: 81, label: "主材预算", sortable: true, align: "left", sum: true},
					{name: "baseperfper", index: "baseperfper", width: 102, label: "基础业绩比例", sortable: true, align: "left"},
					{name: "integrateplan", index: "integrateplan", width: 85, label: "集成预算", sortable: true, align: "left", sum: true},
					{name: "cupboardplan", index: "cupboardplan", width: 86, label: "橱柜预算", sortable: true, align: "left", sum: true},
					{name: "softplan", index: "softplan", width: 96, label: "软装预算", sortable: true, align: "left", sum: true},
					{name: "mainservplan", index: "mainservplan", width: 112, label: "服务性产品预算", sortable: true, align: "left", sum: true},
					{name: "basedisc", index: "basedisc", width: 111, label: "协议优惠额", sortable: true, align: "left", sum: true},
					{name: "markup", index: "markup", width: 85, label: "优惠折扣", sortable: true, align: "left"},
					{name: "contractfee", index: "contractfee", width: 109, label: "合同总额", sortable: true, align: "left", sum: true},
					{name: "contractanddesignfee", index: "contractanddesignfee", width: 131, label: "合同总价+价设计费", sortable: true, align: "left"},
					{name: "longfee", index: "longfee", width: 78, label: "远程费", sortable: true, align: "left", sum: true},
					{name: "tax", index: "tax", width: 78, label: "税金", sortable: true, align: "left", sum: true},
					{name: "softfee_furniture", index: "softfee_furniture", width: 97, label: "软装家具费", sortable: true, align: "left", sum: true},
					{name: "marketfund", index: "marketfund", width: 90, label: "营销基金", sortable: true, align: "left", sum: true},
					{name: "baseperfradix", index: "baseperfradix", width: 110, label: "基础业绩基数", sortable: true, align: "left", sum: true},
					{name: "realmaterperf", index: "realmaterperf", width: 100, label: "实际材料业绩", sortable: true, align: "left", sum: true},
					{name: "maxmaterperf", index: "maxmaterperf", width: 100, label: "封顶材料业绩", sortable: true, align: "left"},
					{name: "alreadymaterperf", index: "alreadymaterperf", width: 100, label: "已算材料业绩", sortable: true, align: "left"},
					{name: "materperf", index: "materperf", width: 100, label: "材料业绩", sortable: true, align: "left", sum: true},
					{name: "perfamount", index: "perfamount", width: 100, label: "签单业绩", sortable: true, align: "left", sum: true},
					{name: "basededuction", index: "basededuction", width: 110, label: "基础单项扣减", sortable: true, align: "left", sum: true},
					{name: "itemdeduction", index: "itemdeduction", width: 110, label: "材料单品扣减", sortable: true, align: "left", sum: true},
					{name: "realperfamount", index: "realperfamount", width: 114, label: "实际签单业绩", sortable: true, align: "left", sum: true},
					{name: "tilestatusdescr", index: "tilestatusdescr", width: 90, label: "瓷砖状态", sortable: true, align: "left"},
					{name: "bathstatusdescr", index: "bathstatusdescr", width: 90, label: "卫浴状态", sortable: true, align: "left"},
					{name: "tilededuction", index: "tilededuction", width: 108, label: "瓷砖扣减业绩", sortable: true, align: "left", sum: true},
					{name: "bathdeduction", index: "bathdeduction", width: 108, label: "卫浴扣减业绩", sortable: true, align: "left", sum: true},
					{name: "maindeduction", index: "maindeduction", width: 108, label: "主材扣减业绩", sortable: true, align: "left", sum: true},
					{name: "softtokenamount", index: "softtokenamount", width: 100, label: "软装券金额", sortable: true, align: "left", sum: true},
					{name: "perfperc", index: "perfperc", width: 85, label: "业绩比例", sortable: true, align: "left"},
					{name: "perfdisc", index: "perfdisc", width: 108, label: "业绩折扣金额", sortable: true, align: "left", sum: true},
					{name: "setadd", index: "setadd", width: 110, label: "套餐外基础增项", sortable: true, align: "left", sum: true},
					{name: "gift", index: "gift", width: 76, label: "实物赠送", sortable: true, align: "left", sum: true},
					{name: "recalperf", index: "recalperf", width: 108, label: "实际业绩", sortable: true, align: "left", sum: true},
					{name: "iscalpkperfdescr", index: "iscalpkperfdescr", width: 115, label: "是否计算PK算业绩", sortable: true, align: "left"},
					{name: "paytypedescr", index: "paytypedescr", width: 83, label: "付款方式", sortable: true, align: "left"},
					{name: "firstpay", index: "firstpay", width: 99, label: "首付款", sortable: true, align: "left", sum: true},
					{name: "mustreceive", index: "mustreceive", width: 104, label: "达标应收", sortable: true, align: "left", sum: true},
					{name: "realreceive", index: "realreceive", width: 109, label: "实收", sortable: true, align: "left", sum: true},
					{name: "achievedate", index: "achievedate", width: 88, label: "达标时间", sortable: true, align: "left", formatter: formatTime, sum: true},
					{name: "mainproper", index: "mainproper", width: 95, label: "主材毛利率", sortable: true, align: "left"},
					{name: "signdate", index: "signdate", width: 88, label: "签订时间", sortable: true, align: "left", formatter: formatTime},
					{name: "setdate", index: "setdate", width: 88, label: "下定时间", sortable: true, align: "left", formatter: formatTime},
					{name: "setminus", index: "setminus", width: 111, label: "套餐内减项", sortable: true, align: "left", sum: true},
					{name: "managefee_base", index: "managefee_base", width: 103, label: "基础管理费", sortable: true, align: "left", sum: true},
					{name: "managefee_inset", index: "managefee_inset", width: 114, label: "套餐内管理费", sortable: true, align: "left", sum: true},
					{name: "managefee_outset", index: "managefee_outset", width: 109, label: "套餐外管理费", sortable: true, align: "left", sum: true},
					{name: "managefee_main", index: "managefee_main", width: 105, label: "主材管理费", sortable: true, align: "left", sum: true},
					{name: "managefee_int", index: "managefee_int", width: 99, label: "集成管理费", sortable: true, align: "left", sum: true},
					{name: "managefee_serv", index: "managefee_serv", width: 134, label: "服务性产品管理费", sortable: true, align: "left", sum: true},
					{name: "managefee_soft", index: "managefee_soft", width: 110, label: "软装管理费", sortable: true, align: "left", sum: true},
					{name: "managefee_cup", index: "managefee_cup", width: 107, label: "橱柜管理费", sortable: true, align: "left", sum: true},
					{name: "managefee_sum", index: "managefee_sum", width: 107, label: "管理费合计", sortable: true, align: "left"},
					{name: "ischgholderdescr", index: "ischgholderdescr", width: 99, label: "干系人变动", sortable: true, align: "left"},
					{name: "businessmandescr", index: "businessmandescr", width: 90, label: "业务员", sortable: true, align: "left"},
					{name: "designmandescr", index: "designmandescr", width: 90, label: "设计师", sortable: true, align: "left"},
					{name: "againmandescr", index: "againmandescr", width: 90, label: "翻单员", sortable: true, align: "left"},
					{name: "busidrcdescr", index: "busidrcdescr", width: 90, label: "业务主任", sortable: true, align: "left"},
					{name: "businessmanleader", index: "businessmanleader", width: 113, label: "业务部领导", sortable: true, align: "left"},
					{name: "designmanleader", index: "designmanleader", width: 115, label: "设计部领导", sortable: true, align: "left"},
					{name: "businessmandeptdescr", index: "businessmandeptdescr", width: 108, label: "业务部", sortable: true, align: "left"},
					{name: "designmandeptdescr", index: "designmandeptdescr", width: 108, label: "设计部", sortable: true, align: "left"},
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
					{name: "oldbusinessmandescrdep", index: "oldbusinessmandescrdep", width: 118, label: "原业绩业务部", sortable: true, align: "left"},
					{name: "olddesignmandescrdep", index: "olddesignmandescrdep", width: 118, label: "原业绩设计部", sortable: true, align: "left"},
					{name: "oldbusidrcdescr", index: "oldbusidrcdescr", width: 94, label: "原业务主任", sortable: true, align: "left"},
					{name: "oldbusinessmanleader", index: "oldbusinessmanleader", width: 125, label: "原业绩业务部领导", sortable: true, align: "left"},
					{name: "olddesignmanleader", index: "olddesignmanleader", width: 125, label: "原业绩设计部领导", sortable: true, align: "left"},
					{name: "oldyear", index: "oldyear", width: 110, label: "原业绩归属年份", sortable: true, align: "left"},
					{name: "oldmonth", index: "oldmonth", width: 110, label: "原业绩归属月份", sortable: true, align: "left"},
					{name: "remarks", index: "remarks", width: 253, label: "备注", sortable: true, align: "left"},
					{name: "ismodifieddescr", index: "ismodifieddescr", width: 83, label: "人工修改", sortable: true, align: "left"},
					{name: "datatypedescr", index: "datatypedescr", width: 83, label: "数据类型", sortable: true, align: "left"},
					{name: "crtdate", index: "crtdate", width: 136, label: "生成时间", sortable: true, align: "left", formatter: formatTime},
					{name: "discremark", index: "discremark", width: 319, label: "优惠政策", sortable: true, align: "left"},
					{name: "lastupdate", index: "lastupdate", width: 136, label: "最后更新时间", sortable: true, align: "left", formatter: formatTime},
					{name: "lastupdatedby", index: "lastupdatedby", width: 108, label: "最后更新人员", sortable: true, align: "left"},
					{name: "expired", index: "expired", width: 80, label: "是否过期", sortable: true, align: "left"},
					{name: "actionlog", index: "actionlog", width: 85, label: "操作日志", sortable: true, align: "left"},
					{name: "perfdocumentno", index: "perfdocumentno", width: 85, label: "档案号", sortable: true, align: "left"}
	            ],
	            gridComplete:function(){
	                $(".ui-jqgrid-bdiv").scrollTop(0);
		            frozenHeightReset("addDetailDataTable");
	       		},
			};
			//全选
			$("#selAll").on("click",function(){
				Global.JqGrid.jqGridSelectAll("addDetailDataTable",true);
			});
			//全不选
			$("#selNone").on("click",function(){
				Global.JqGrid.jqGridSelectAll("addDetailDataTable",false);
			});
			Global.JqGrid.initJqGrid("addDetailDataTable",gridOption);
	    	$("#addDetailDataTable").jqGrid("setFrozenColumns");
		});
		function save(){
			var pks="";
			var ids=$("#addDetailDataTable").jqGrid("getGridParam", "selarrrow");
			if(ids.length == 0){
				art.dialog({
					content:"请选择已计算过的业绩！"
				});				
				return ;
			}
	 		$.each(ids,function(i,id){
				var rowData = $("#addDetailDataTable").jqGrid("getRowData", id);
				if(pks!=""){
					pks+=",";
				}
				pks+=rowData.pk;
			}); 
			Global.Dialog.returnData = pks; 
			closeWin(true);
		}
	</script>
	</head>
	    
	<body>
		<div class="body-box-list">
	 		<div class="panel panel-system">
			    <div class="panel-body">
			    	<div class="btn-group-xs" style="float:left">
	    				<button id="saveBut" type="button" class="btn btn-system" onclick="save()">保存</button>
	    				<button id="selAll" type="button" class="btn btn-system" >全选</button>
	    				<button id="selNone" type="button" class="btn btn-system" >全不选</button>
	    				<button id="closeBut" type="button" class="btn btn-system" onclick="closeWin(false)">关闭</button>
					</div>
				</div>
			</div> 
			<table id="addDetailDataTable"></table>
			<div id="addDetailDataTablePager"></div>
		</div>
	</body>
</html>
