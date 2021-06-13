<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<title>新增领料 快速新增（从解单导入）</title>
		<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
		<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
		<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
		<META HTTP-EQUIV="expires" CONTENT="0" />
		<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
		<%@ include file="/commons/jsp/common.jsp" %>
		<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	
		<script type="text/javascript">
			$(function(){
				Global.JqGrid.initJqGrid("dataTable_jddr",{
					height:450,
					rowNum: 10000,
					styleUI:"Bootstrap",
					url:"${ctx}/admin/itemPreApp/goJddrJqGrid",
					postData:{
						custCode:$("#custCode").val(),
						seqpks:"${data.specreqpks}",
						isCupboard:"${data.isCupboard}",
						appNo:"${data.appNo}"
					},
					multiselect: true,
					colModel:[
						{name: "specreqpk", index: "specreqpk", width: 75, label: "编号", sortable: true, align: "left", hidden: true},
						{name: "custcode", index: "custcode", width: 100, label: "客户编号", sortable: true, align: "left", hidden: true},
						{name: "fixareadescr", index: "fixareadescr", width: 120, label: "区域", sortable: true, align: "left"},
						{name: "intproddescr", index: "intproddescr", width: 70, label: "集成成品", sortable: true, align: "left"},
						{name: "itemtype2descr", index: "itemtype2descr", width: 110, label: "材料类型2", sortable: true, align: "left"},
						{name: "itemcode", index: "itemcode", width: 70, label: "材料编号", sortable: true, align: "left"},
						{name: "itemdescr", index: "itemdescr", width: 231, label: "材料名称", sortable: true, align: "left"},
						{name: "supplcodedescr", index: "supplcodedescr", width: 160, label: "供应商", sortable: true, align: "left", hidden: true},
						{name: "sendtypedescr", index: "sendtypedescr", width: 105, label: "发货类型", sortable: true, align: "left"},
						{name: "itemtype1", index: "itemtype1", width: 70, label: "类型编号", sortable: true, align: "left", hidden: true},
						{name: "typedescr", index: "typedescr", width: 70, label: "材料类型", sortable: true, align: "left", hidden: true},
						{name: "reqqty", index: "reqqty", width: 70, label: "解单数量", sortable: true, align: "right"},
						{name: "sendqty", index: "sendqty", width: 80, label: "已发货数量", sortable: true, align: "right", hidden: true},
						{name: "itemchgno", index: "itemchgno", width: 100, label: "增减单号", sortable: true, align: "left", hidden: true},
						{name: "remarks", index: "remarks", width: 150, label: "备注", sortable: true, align: "left"},
						{name: "reqpk", index: "reqpk", width: 70, label: "领料标识", sortable: true, align: "left", hidden: true},
						{name: "uomdescr",index: "uomdescr",width:60,label: "单位",sortable: true,align: "left",hidden: true},
						{name: "reqremarks",index: "reqremarks",width:60,label: "预算备注",sortable: true,align: "left",hidden: true},
						{name: "no", index: "no", width: 80, label: "批次号", sortable: true, align: "left", hidden: true},
						{name: "fixareapk", index: "fixareapk", width: 84, label: "fixareapk", sortable: true, align: "left", hidden: true},
						{name: "intprodpk", index: "intprodpk", width: 84, label: "intprodpk", sortable: true, align: "left", hidden: true},
						{name: "color", index: "color", width: 76, label: "颜色", sortable: true, align: "left", hidden: true},
						{name: "sqlcodedescr", index: "sqlcodedescr", width: 78, label: "品牌", sortable: true, align: "left", hidden: true},
						{name: "declareqty", index: "declareqty", width: 76, label: "申报数量", sortable: true, align: "left",hidden: true},
						{name: "shortqty", index: "shortqty", width: 89, label: "缺货数量", sortable: true, align: "left", hidden: true},
						{name: "qty", index: "qty", width: 79, label: "qty", sortable: true, align: "left", sum: true,hidden: true},
						{name: "sendedqty", index: "sendedqty", width: 89, label: "总共已发货", sortable: true, align: "left", sum: true},
						{name: "applyqty", index: "applyqty", width: 89, label: "已申请数量", sortable: true, align: "left",hidden: true},
						{name: "confirmedqty", index: "confirmedqty", width: 89, label: "已审核数量", sortable: true, align: "left", sum: true,hidden: true},
						{name: "allqty", index: "allqty", width: 80, label: "库存数量", sortable: true, align: "right", sum: true,hidden: true},
						{name: "userqty", index: "userqty", width: 80, label: "可用数量", sortable: true, align: "right", sum: true,hidden: true},
						{name: "cost", index: "cost", width: 81, label: "成本单价", sortable: true, align: "left", hidden: true},
						{name: "allcost", index: "allcost", width: 90, label: "成本总价", sortable: true, align: "left", sum: true, hidden: true},
						{name: "projectcost", index: "projectcost", width: 110, label: "项目经理结算价", sortable: true, align: "left", hidden: true},
						{name: "allprojectcost", index: "allprojectcost", width: 120, label: "项目经理结算总价", sortable: true, align: "left", sum: true, hidden: true},
						{name: "price", index: "price", width: 80, label: "销售价", sortable: true, align: "left", hidden: true},
						{name: "processcost", index: "processcost", width: 80, label: "其它费用", sortable: true, align: "left", sum: true,hidden: true},
						{name: "differences", index: "differences", width: 89, label: "成本差异额 ", sortable: true, align: "left", sum: true, hidden: true},
						{name: "pricedifferences", index: "pricedifferences", width: 90, label: "销售差异额", sortable: true, align: "left", hidden: true},
						{name: "weight", index: "weight", width: 85, label: "总重量", sortable: true, align: "left", sum: true,hidden: true},
						{name: "numdescr", index: "numdescr", width: 85, label: "总片数", sortable: true, align: "left", hidden:true},
						{name: "reqprocesscost", index: "reqprocesscost", width: 156, label: "reqprocesscost", sortable: true, align: "left", hidden:true},
						{name: "perweight", index: "perweight", width: 156, label: "perweight", sortable: true, align: "left", hidden:true},
						{name: "aftqty", index: "aftqty", width: 90, label: "aftqty", sortable: true, align: "left",hidden:true},
						{name: "aftcost", index: "aftcost", width: 90, label: "aftcost", sortable: true, align: "left",hidden:true},
						{name: "whcode", index: "whcode", width: 90, label: "whcode", sortable: true, align: "left",hidden:true},
						{name: "whdescr", index: "whdescr", width: 90, label: "whdescr", sortable: true, align: "left",hidden:true},
						{name: "supplcode", index: "supplcode", width: 90, label: "supplcode", sortable: true, align: "left",hidden:true},
						{name: "sendtype", index: "sendtype", width: 90, label: "sendtype", sortable: true, align: "left",hidden:true},
						{name: "itemtype2", index: "itemtype2", width: 90, label: "itemtype2", sortable: true, align: "left",hidden:true},
						{name: "differencesperf", index: "differencesperf", width: 70, label: "差异占比", sortable: true, align: "right",hidden:true}
					],
					gridComplete:function (){
						dataTableCheckBox("dataTable_jddr", "itemtype2descr");
						dataTableCheckBox("dataTable_jddr", "supplcodedescr");
						dataTableCheckBox("dataTable_jddr", "sendtypedescr");
					}
				});
			});
			function save(){
				var ids=$("#dataTable_jddr").jqGrid("getGridParam", "selarrrow");
				if(ids.length == 0){
					art.dialog({
						content:"请选择数据后保存"
					});				
					return ;
				}
				var selectRows = [];
				$.each(ids,function(i,id){
					var rowData = $("#dataTable_jddr").jqGrid("getRowData", id);
					selectRows.push(rowData);
				});
	    		Global.Dialog.returnData = selectRows;
	    		closeWin();
			}
			function tableSelected(flag){
				selectAll("dataTable_jddr", flag);
			}
		</script>
	</head>
	    
	<body>
		<div class="body-box-list">
			<div class="panel panel-system">
			    <div class="panel-body">
			    	<div class="btn-group-xs">
		   				<button id="saveBut" type="button" class="btn btn-system" onclick="save()">保存</button>
		   				<button id="allSelectBut" type="button" class="btn btn-system" onclick="tableSelected(true)">全选</button>
		   				<button id="nonSelectBut" type="button" class="btn btn-system" onclick="tableSelected(false)">不选</button>
		   				<button id="closeBut" type="button" class="btn btn-system" onclick="closeWin(false)">关闭</button>
					</div>
				</div>
			</div>
			
			<div class="clear_float"> </div>
	
			<input type="hidden" id="custCode" name="custCode" value="${data.custCode}" />
			<input type="hidden" id="itemType1" name="itemType1" value="${data.itemType1}" />
			<input type="hidden" id="itemType2" name="itemType2" value="${data.itemType2}" />
			<input type="hidden" id="isSetItem" name="isSetItem" value="${data.isSetItem}" />
			<input type="hidden" id="isService" name="isService" value="${data.isService}" />
			<input type="hidden" id="pks" name="pks" value="${data.pks}"/>
			<input type="hidden" id="appNo" name="appNo" value="${data.appNo}"/>
			<input type="hidden" id="m_umState" name="m_umState" value="${data.m_umState}"/>
			<div id="content-list">
				<table id="dataTable_jddr"></table>
			</div>
		</div>
	</body>
</html>


