<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<title>基础人工成本管理-生成水电材料奖惩</title>
		<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
		<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
		<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
		<META HTTP-EQUIV="expires" CONTENT="0" />
		<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
		<%@ include file="/commons/jsp/common.jsp" %>
	
	<script type="text/javascript">
		var selectRows = [];
		var rows = $(top.$("#iframe_detail")[0].contentWindow.document.getElementById("detailDataTable")).jqGrid("getRowData");
		/**初始化表格*/
		$(function(){
			Global.JqGrid.initJqGrid("addDetailDataTable",{
				url:"${ctx}/admin/workCostDetail/goWaterItemJqGrid",
				postData:{
					'workCostDetailJson':JSON.stringify(rows)
				}, 
				height:450,
				rowNum:10000000,
				colModel : [
					{name: "custcode", index: "custcode", width: 70, label: "客户编号", sortable: true, align: "left"},
					{name: "iswithhold", index: "iswithhold", width: 100, label: "是否预扣", sortable: true, align: "left", hidden: true},
					{name: "address", index: "address", width: 122, label: "楼盘", sortable: true, align: "left"},
 					{name: "documentno", index: "documentno", width: 120, label: "档案号", sortable: true, align: "left", hidden: true},
					{name: "salarytypedescr", index: "salarytypedescr", width: 100, label: "工资类型", sortable: true, align: "left", hidden: true},
					{name: "worktype1descr", index: "worktype1descr", width: 100, label: "工种类型1", sortable: true, align: "left", hidden: true},
					{name: "worktype2descr", index: "worktype2descr", width: 100, label: "工种类型2", sortable: true, align: "left", hidden: true},
					{name: "iswithholddescr", index: "iswithholddescr", width: 94, label: "是否预扣", sortable: true, align: "left", hidden: true},
					{name: "withholdno", index: "withholdno", width: 113, label: "预扣单号", sortable: true, align: "left", hidden: true},
					{name: "workercode", index: "workercode", width: 100, label: "工人编号", sortable: true, align: "left", hidden: true},
					{name: "cardid", index: "cardid", width: 170, label: "卡号", sortable: true, align: "left", hidden: true},
					{name: "cardid2", index: "cardid2", width: 170, label: "卡号2", sortable: true, align: "left", hidden: true},
					{name: "appamount", index: "appamount", width: 100, label: "申请金额", sortable: true, align: "right",sorttype:"number", hidden: true},
					{name: "cfmamount", index: "cfmamount", width: 90, label: "审核金额", sortable: true, align: "right",sorttype:"number", hidden: true},
					{name: "applyman", index: "applyman", width: 100, label: "申请人", sortable: true, align: "left", hidden: true},
					{name: "applydate", index: "applydate", width: 140, label: "申请日期", sortable: true, align: "left", formatter: formatTime, hidden: true},
					{name: "remarks", index: "remarks", width: 170, label: "请款说明", sortable: true, align: "left", hidden: true},
					{name: "checkstatusdescr", index: "checkstatusdescr", width: 100, label: "客户结算状态", sortable: true, align: "left", hidden: true},
					{name: "applymandescr", index: "applymandescr", width: 72, label: "申请人", sortable: true, align: "left", hidden: true},
					{name: "statusdescr", index: "statusdescr", width: 61, label: "状态", sortable: true, align: "left", hidden: true},
					{name: "confirmamount", index: "confirmamount", width: 96, label: "审批金额", sortable: true, align: "right",sorttype:"number", sum: true, hidden: true},
					{name: "issigndescr", index: "issigndescr", width: 80, label: "是否签约", sortable: true, align: "left", hidden: true},
					{name: "custctrl", index: "custctrl", width: 100, label: "人工发包额", sortable: true, align: "right",sorttype:"number", sum: true, hidden: true},
					{name: "custctrl_kzx", index: "custctrl_kzx", width: 110, label: "人工发包控制", sortable: true, align: "right",sorttype:"number", sum: true, hidden: true},
					{name: "custcost", index: "custcost", width: 90, label: "人工总成本", sortable: true, align: "right",sorttype:"number", sum: true, hidden: true},
					{name: "leavecustcost", index: "leavecustcost", width: 100, label: "单项工种余额", sortable: true, align: "right",sorttype:"number", hidden: true},
					{name: "allleavecustcost", index: "allleavecustcost", width: 100, label: "总发包余额", sortable: true, align: "right",sorttype:"number", hidden: true},
					{name: "allcustctrl", index: "allcustctrl", width: 90, label: "总发包额", sortable: true, align: "right",sorttype:"number", sum: true, hidden: true},
					{name: "allcustcost", index: "allcustcost", width: 90, label: "总成本", sortable: true, align: "right",sorttype:"number", sum: true, hidden: true},
					{name: "refprepk", index: "refprepk", width: 80, label: "预申请PK", sortable: true, align: "left", hidden: true},
					{name: "lastupdatedby", index: "lastupdatedby", width: 90, label: "最后修改人", sortable: true, align: "left", hidden: true},
					{name: "lastupdate", index: "lastupdate", width: 140, label: "最后修改时间", sortable: true, align: "left", formatter: formatTime, hidden: true},
					{name: "qualityfeebegin", index: "qualityfeebegin", width: 120, label: "质保金起始金额", sortable: true, align: "right",sorttype:"number", hidden: true},
					{name: "isconfirm", index: "isconfirm", width: 80, label: "审核标志", sortable: true, align: "left", hidden: true},
					{name: "custcode", index: "custcode", width: 100, label: "客户编号", sortable: true, align: "left", hidden: true},
					{name: "salarytype", index: "salarytype", width: 100, label: "工资类型", sortable: true, align: "left", hidden: true},
					{name: "worktype2", index: "worktype2", width: 100, label: "工资类型2", sortable: true, align: "left", hidden: true},
					{name: "worktype1", index: "worktype1", width: 100, label: "工资类型1", sortable: true, align: "left", hidden: true},
					{name: "status", index: "status", width: 100, label: "状态", sortable: true, align: "left", hidden: true},
					{name: "type", index: "type", width: 100, label: "申请类型", sortable: true, align: "left", hidden: true},
					{name: "prjitem", index: "prjitem", width: 100, label: "项目名", sortable: true, align: "left", hidden: true},
					{name: "enddate", index: "enddate", width: 140, label: "完成时间", sortable: true, align: "left", formatter: formatTime, hidden: true},
					{name: "confirmdate", index: "confirmdate", width: 140, label: "验收时间", sortable: true, align: "left", formatter: formatTime, hidden: true},
					{name: "rcvcost", index: "rcvcost", width: 100, label: "领取金额", sortable: true, align: "right",sorttype:"number", hidden: true},
					{name: "withholdcost", index: "withholdcost", width: 100, label: "预扣金额", sortable: true, align: "right",sorttype:"number", hidden: true},
					{name: "gotamount", index: "gotamount", width: 100, label: "已领工资", sortable: true, align: "right",sorttype:"number", hidden: true},
					{name: "totalamount", index: "totalamount", width: 100, label: "合同总价", sortable: true, align: "right",sorttype:"number", hidden: true},
					{name: "signdate", index: "signdate", width: 100, label: "开工日期", sortable: true, align: "left", formatter: formatTime, hidden: true},
					{name: "confirmczy", index: "confirmczy", width: 100, label: "验收人", sortable: true, align: "left", hidden: true},
					{name: "confirmczydescr", index: "confirmczydescr", width: 100, label: "验收人", sortable: true, align: "left", hidden: true},
					{name: "innerarea", index: "innerarea", width: 65, label: "套内面积", sortable: true, align: "right",sorttype:"number"},
					{name: "confirmbegin", index: "confirmbegin", width: 95, label: "实际开工日期", sortable: true, align: "left", formatter: formatDate},
					{name: "actname", index: "actname", width: 70, label: "工人姓名", sortable: true, align: "left"},
	         		{name: "workerplanmaterial", index: "workerplanmaterial", width: 95, label: "工人预算材料", sortable: true, align: "right",sorttype:"number"},
	         		{name: "workerplanoffer", index: "workerplanoffer", width: 95, label: "定额工资", sortable: true, align: "right",sorttype:"number",hidden:true},
	            	{name: "clamount", index: "clamount", width: 95, label: "水电实际材料", sortable: true, align: "right",sorttype:"number"},
	          		{name: "clamountjc", index: "clamountjc", width: 95, label: "水电材料奖惩", sortable: true, align: "right",sorttype:"number"},
	          		{name: "clamount_wfh", index: "clamount_wfh", width: 130, label: "水电材料未发货金额", sortable: true, align: "right",sorttype:"number"},
	          		{name: "iswateritemctrl", index: "iswateritemctrl", width: 120, label: "是否水电发包", sortable: true,align: "left", hidden: true},
	          		{name: "iswateritemctrldescr", index: "iswateritemctrldescr", width: 90, label: "水电发包工人", sortable: true,align: "left", hidden: true},
	          		{name: "idnum", index: "idnum", width: 90, label: "idnum", sortable: true,align: "left", hidden: true},
	          		{name: "custtypedescr", index: "custtypedescr", width: 90, label: "客户类型", sortable: true,align: "left", hidden: true},
	            ],
	            loadonce:true,
	            gridComplete:function(){
					var ids = $("#addDetailDataTable").getDataIDs();
			        for(var i=0;i<ids.length;i++){
			            var rowData = $("#addDetailDataTable").getRowData(ids[i]);
			            //未发货金额大于0，字体显示为红色
			            if(parseFloat(rowData.clamount_wfh)>0){
			            	$('#'+ids[i]).find("td").css("color","red");
			            } 
			      	}
	            }
			});
		});
		function save(){
			art.dialog({
				 content:"是否确认要导入？",
				 lock: true,
				 width: 200,
				 height: 100,
				 ok: function () {
					var rowData = $("#addDetailDataTable").jqGrid("getRowData");
					Global.Dialog.returnData = rowData;
					closeWin();
				},
				cancel: function () {
						return true;
				}
			}); 
		}
	</script>
	</head>
	    
	<body>
		<div class="body-box-list">
			<form role="form" class="form-search" id="page_form" action=""
				method="post" target="targetFrame">
				<input type="hidden" name="jsonString" value="" />
			</form>
			<div class="panel panel-system">
			    <div class="panel-body">
			    	<div class="btn-group-xs" style="float:left">
	    				<button id="saveBut" type="button" class="btn btn-system" onclick="save()">导入</button>
	    				<button id="excel" type="button" class="btn btn-system" onclick="doExcelNow('水电材料奖惩','addDetailDataTable')">导出excel</button>
	    				<button id="closeBut" type="button" class="btn btn-system" onclick="closeWin()">关闭</button>
					</div>
					&nbsp;&nbsp;&nbsp;<span style="color:red">如果存在未发货的水电材料，将不生成水电材料奖惩！</span>
				</div>
			</div>
			<table id="addDetailDataTable"></table>
		</div>
	</body>
</html>
