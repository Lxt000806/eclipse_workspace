<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>

<script type="text/javascript">
	$(function(){
		if($("#m_umState").val() != "A"){
			url="${ctx}/admin/itemPreApp/goItemAppDetailJqGrid?m_umState="+$("#m_umState").val()+"&appNo="+$("#appNo").val()
			+"&custCode="+$("#custCode").val()+"&isSetItem="+$("#isSetItem").val()+"&itemType1="+$("#itemType1").val();
		}else{
			url="#";
		}
		initApplyDetailDataTable(url);
	});
	function initApplyDetailDataTable(url, height){

		Global.JqGrid.initEditJqGrid("dataTable_applyDetail", {
			height:height?height:($("#m_umState").val()=="A"?245:225),
			url:url,
			rowNum: 10000,
			styleUI:"Bootstrap",
			colModel:[
				{name: "specreqpk", index: "specreqpk", width: 75, label: "specreqpk", sortable: true, align: "left", hidden: true},
				{name: "preappdtpk", index: "preappdtpk", width: 75, label: "preappdtpk", sortable: true, align: "left", hidden: true},
				{name: "reqpk", index: "reqpk", width: 75, label: "领料标识", sortable: true, align: "left", hidden: true},
				{name: "pk", index: "pk", width: 50, label: "编号", sortable: true, align: "left", hidden: true},
				{name: "itemcode", index: "itemcode", width: 75, label: "材料编号", sortable: true, align: "left"},
				{name: "itemdescr", index: "itemdescr", width: 189, label: "材料名称", sortable: true, align: "left"},
				{name: "no", index: "no", width: 80, label: "批次号", sortable: true, align: "left", hidden: true},
				{name: "fixareapk", index: "fixareapk", width: 84, label: "fixareapk", sortable: true, align: "left", hidden: true},
				{name: "fixareadescr", index: "fixareadescr", width: 120, label: "装修区域", sortable: true, align: "left"},
				{name: "intprodpk", index: "intprodpk", width: 84, label: "intprodpk", sortable: true, align: "left", hidden: true},
				{name: "intproddescr", index: "intproddescr", width: 70, label: "集成产品", sortable: true, align: "left"},
				{name: "uomdescr", index: "uomdescr", width: 50, label: "单位", sortable: true, align: "left"},
				{name: "color", index: "color", width: 50, label: "颜色", sortable: true, align: "left", hidden: true,editable:true},
				{name: "sqlcodedescr", index: "sqlcodedescr", width: 70, label: "品牌", sortable: true, align: "left", hidden: true,editable:true},
				{name: "supplcodedescr", index: "supplcodedescr", width: 70, label: "供应商", sortable: true, align: "left", hidden: true,editable:true},
				{name: "declareqty", index: "declareqty", width: 70, label: "申报数量", sortable: true, align: "right",editable:true, editrules: {number:true,required:true},sum:true},
				{name: "qty", index: "qty", width: 70, label: "采购数量", sortable: true, align: "right", sum: true,editable:true, editrules: {number:true,required:true}},
				{name: "differencesperf", index: "differencesperf", width: 70, label: "差异占比", sortable: true, align: "right",sum:true,formatter:function(cellvalue, options, rowObject){return myRound(parseFloat(cellvalue)*100, 2)+"%";}},
				{name: "remarks", index: "remarks", width: 150, label: "备注", sortable: true, align: "left",editable:true},
				{name: "reqremarks", index: "reqremarks", width: 150, label: "预算备注", sortable: true, align: "left"},//,editable:true
				{name: "sendqty", index: "sendqty", width: 80, label: "已发货数量", sortable: true, align: "right", hidden: true,editable:true,	editrules: {number:true,required:true}},
				{name: "shortqty", index: "shortqty", width: 70, label: "缺货数量", sortable: true, align: "right", hidden: true,editable:true,	editrules: {number:true,required:true}},
				{name: "reqqty", index: "reqqty", width: 70, label: "预算数量", sortable: true, align: "right", sum: true},
				{name: "sendedqty", index: "sendedqty", width: 80, label: "总共已发货", sortable: true, align: "right", sum: true},
				{name: "applyqty", index: "applyqty", width: 80, label: "已申请数量", sortable: true, align: "right",editable:true,	editrules: {number:true,required:true}},
				{name: "confirmedqty", index: "confirmedqty", width: 80, label: "已审核数量", sortable: true, align: "right", sum: true},
				{name: "allqty", index: "allqty", width: 80, label: "库存数量", sortable: true, align: "right", sum: true},
				{name: "userqty", index: "userqty", width: 80, label: "可用数量", sortable: true, align: "right", sum: true},
				{name: "cost", index: "cost", width: 70, label: "成本单价", sortable: true, align: "right", hidden: true},
				{name: "allcost", index: "allcost", width: 70, label: "成本总价", sortable: true, align: "right", sum: true, hidden: true,editable:true, editrules: {number:true,required:true}},
				{name: "projectcost", index: "projectcost", width: 110, label: "项目经理结算价", sortable: true, align: "right", hidden: true},
				{name: "allprojectcost", index: "allprojectcost", width: 115, label: "项目经理结算总价", sortable: true, align: "right", sum: true, hidden: true,editable:true, editrules: {number:true,required:true}},
				{name: "price", index: "price", width: 60, label: "销售价", sortable: true, align: "right", hidden: true},
				{name: "processcost", index: "processcost", width: 70, label: "其它费用", sortable: true, align: "right", sum: true},
				{name: "differences", index: "differences", width: 80, label: "成本差异额 ", sortable: true, align: "right", sum: true, hidden: true},
				{name: "pricedifferences", index: "pricedifferences", width: 80, label: "销售差异额", sortable: true, align: "right", hidden: true},
				{name: "sendtypedescr", index: "sendtypedescr", width: 70, label: "发货类型", sortable: true, align: "left",editable:true},
				{name: "weight", index: "weight", width: 60, label: "总重量", sortable: true, align: "right", sum: true,editable:true,	editrules: {number:true,required:true},formatter:function(cellvalue, options, rowObject){return myRound(cellvalue,2);}},
				{name: "numdescr", index: "numdescr", width: 60, label: "总片数", sortable: true, align: "right",editable:true,	editrules: {number:true,required:true}},
				{name: "lastupdate", index: "lastupdate", width: 100, label: "最后更新时间", sortable: true, align: "left", formatter: formatTime},
				{name: "reqprocesscost", index: "reqprocesscost", width: 156, label: "reqprocesscost", sortable: true, align: "right", hidden:true},
				{name: "perweight", index: "perweight", width: 156, label: "perweight", sortable: true, align: "right", hidden:true},
				{name: "lastupdatedby", index: "lastupdatedby", width: 70, label: "更新人员", sortable: true, align: "left"},
				{name: "expired", index: "expired", width: 70, label: "是否过期", sortable: true, align: "left", hidden: true},
				{name: "actionlog", index: "actionlog", width: 60, label: "操作", sortable: true, align: "left"},
				{name: "whcode", index: "whcode", width: 90, label: "whcode", sortable: true, align: "left", hidden:true},
				{name: "whdescr", index: "whdescr", width: 90, label: "whdescr", sortable: true, align: "left", hidden:true},
				{name: "supplcode", index: "supplcode", width: 90, label: "supplcode", sortable: true, align: "left", hidden:true},
				{name: "sendtype", index: "sendtype", width: 90, label: "sendtype", sortable: true, align: "left", hidden:true},
				{name: "itemtype2", index: "itemtype2", width: 90, label: "itemtype2", sortable: true, align: "left", hidden:true}
			],
			loadonce:true,
			gridComplete:function (){
				selectFirstRow();
				remarksDiffColor();
				qtyDiffColor();
				if($("#needCheckQty").val()=="1"){
					checkQty();
				}
				changeDiffPerf();
			},
			beforeSelectRow:function(id){
				setTimeout(function(){
					relocate(id,"dataTable_applyDetail");
				},10);
			},
			beforeSaveCell:function(rowId, name, val, iRow, iCol){
				$("#isTipExit").val("T");
				var ret = $("#dataTable_applyDetail").jqGrid("getRowData", rowId);

				ret[name]=val;
				
				var reg = /^[0-9]+.?[0-9]*$/;
				if(!reg.test(ret.qty)) return;  
				
				if(name == "remarks" && remarksDiffColor()){
					if(val != ret.reqremarks){
						$("#"+rowId+" td").css("background", "#FFAA00");
					}else{
						$("#"+rowId+" td").css("background", "");
					}
				}
				if(name == "reqremarks" && remarksDiffColor()){
					if(val != ret.remarks){
						$("#"+rowId+" td").css("background", "#FFAA00");
					}else{
						$("#"+rowId+" td").css("background", "");
					}
				}
				ajaxPost("${ctx}/admin/itemPreApp/getBeforeSaveCellInfo",
					{
						"itemCode":ret.itemcode,
						"qty":ret.qty
					},
					function(data){
						var i=0.0;
						
						if(data.result == "1" && parseFloat(ret.reqqty) != 0){
							i = parseFloat(ret.reqprocesscost) * parseFloat(ret.qty) / parseFloat(ret.reqqty);
						}else{
							i = parseFloat(ret.reqprocesscost);
						}
						$("#dataTable_applyDetail").jqGrid("setCell", rowId, "processcost", i);
						i = parseFloat(ret.qty) * parseFloat(ret.cost);
						$("#dataTable_applyDetail").jqGrid("setCell", rowId, "allcost", i.toFixed(2));
						i = parseFloat(ret.qty) * parseFloat(ret.projectcost);

						$("#dataTable_applyDetail").jqGrid("setCell", rowId, "allprojectcost", i.toFixed(2));
						$("#dataTable_applyDetail").jqGrid("setCell", rowId, "lastupdatedby", $("#czybh").val());
						$("#dataTable_applyDetail").jqGrid("setCell", rowId, "lastupdate", new Date());
						$("#dataTable_applyDetail").jqGrid("setCell", rowId, "actionlog", "EDIT");
						
						i = parseFloat(ret.qty) * parseFloat(ret.perweight);
						$("#dataTable_applyDetail").jqGrid("setCell", rowId, "weight", i.toFixed(2));
						$("#dataTable_applyDetail").jqGrid("setCell", rowId, "numdescr", i.toFixed(2));
						
						if(data.numDescr != ""){
							$("#dataTable_applyDetail").jqGrid("setCell", rowId, "numdescr", data.numDescr);
						}
						$("#dataTable_applyDetail").jqGrid("setCell", rowId, "declareqty", parseFloat(ret.qty)); //by zjfvar 
						$("#dataTable_applyDetail").footerData("set", {
							allcost:$("#dataTable_applyDetail").getCol("allcost", false, "sum") ,
							allprojectcost:$("#dataTable_applyDetail").getCol("allprojectcost", false, "sum") ,
							weight:$("#dataTable_applyDetail").getCol("weight", false, "sum") ,
							numdescr:$("#dataTable_applyDetail").getCol("numdescr", false, "sum")
						});
					}
				);
				if(name=="qty"){
					$("#dataTable_applyDetail").jqGrid("setCell", rowId, "differencesperf",0); //by cjg
					$("#dataTable_applyDetail").footerData('set', { "differencesperf": 0 });
				}
				if(name=="declareqty") {
					var result;
					if(val==0 && ret.qty!=0){
						result=1;
					}else if(val==0 && ret.qty==0){
						result=0;
					}else {
						result=((parseFloat(val)-parseFloat(ret.qty))/parseFloat(val)).toFixed(2);
					}
					$("#dataTable_applyDetail").jqGrid("setCell", rowId, "differencesperf",result ); //by cjg
				} 
			},
			onCellSelect:function(rowid,iCol,cellcontent,e){
				if($("#m_umState").val() == "C" || $("#m_umState").val().trim() == "V" || $("#m_umState").val().trim() == "Q"){
                	$("#dataTable_applyDetail").jqGrid("setCell", rowid, iCol, "", "not-editable-cell");  
				}
			},
			afterSaveCell:function(rowId, name, val, iRow, iCol){
				changeDiffPerf();
				//供应商直送，修改采购数量和成本总价，判断配送方式发送公司仓规则 by cjg 20191020
				if($("#sendType").val() == "1" &&(name=="qty" || name=="allcost")){
		   			var allcost=$("#dataTable_applyDetail").getCol('allcost',false,'sum');
		   			var isSendCmpWh=checkSendCmpWh(allcost);
		   			if(isSendCmpWh=="1"){
		   				$("#delivType").val("4");
		   			}else{
		   				$("#delivType").val("2");
		   			}
				}
			}
		}); 
	}
	//差异性占比sum
	function changeDiffPerf(){
		var declareqtySum=$("#dataTable_applyDetail").getCol('declareqty',false,'sum');
		var qtySum=$("#dataTable_applyDetail").getCol('qty',false,'sum');
		var result;
		if(declareqtySum==0 && qtySum!=0){
			result=1;
		}else if(declareqtySum==0 && qtySum==0){
			result=0;
		}else {
			result=((parseFloat(declareqtySum)-parseFloat(qtySum))/parseFloat(declareqtySum)).toFixed(2);
		}
		$("#dataTable_applyDetail").footerData('set', { "differencesperf": result });
	}
</script>
<div class="btn-panel" style="margin-top:2px">
	<div class="btn-group-sm"  >
		<button id="yyxxzBtn" type="button" class="funBtn funBtn-system " onclick="yyxxz()">已有项新增</button>
		<button id="ksxzBtn" type="button" class="funBtn funBtn-system" onclick="ksxz()">快速新增</button>
		<button id="cysqdrBtn" type="cysqdrBtn" class="funBtn funBtn-system" onclick="cysqdr()" disabled="disabled">从预申请导入</button>
		<button id="delBtn" type="button" class="funBtn funBtn-system" onclick="del()">删除</button>
		<button id="doExcelBtn" type="button" class="funBtn funBtn-system" onclick="doExcel()">输出到Excel</button>
		<button id="ckylclBtn" type="button" class="funBtn funBtn-system" onclick="ckylcl()">查看已领材料</button>
		<button id="unItemAppMaterialBtn" type="button" class="funBtn funBtn-system" onclick="unItemAppMaterial()">查看未领材料</button>
		<button id="importExcelBtn" type="button" class="funBtn funBtn-system" onclick="importExcel()" disabled="disabled">从Excel导入</button>
	</div>
</div>	
<table id="dataTable_applyDetail"></table>
