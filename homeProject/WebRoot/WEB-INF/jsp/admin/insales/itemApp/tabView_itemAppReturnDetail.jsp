<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>

<script type="text/javascript">
	$(function(){
		var url="";
		if($("#m_umState").val() != "A"){
			url = "${ctx}/admin/itemApp/getItemReturnDTJqGrid?custCode"+$("#custCode").val()+"&no="+$("#no").val();
		}else{
			url = "#";
		}
		Global.JqGrid.initEditJqGrid("dataTable_itemAppReturnDetail",{
			url:url,
			height:220,
			rowNum: 10000,
			styleUI:"Bootstrap",
			colModel:[
				{name: "pk", index: "pk", width: 50, label: "编号", sortable: true, align: "left", hidden: true},
				{name: "itemcode", index: "itemcode", width: 80, label: "材料编号", sortable: true, align: "left"},
				{name: "itemdescr", index: "itemdescr", width: 188, label: "材料名称", sortable: true, align: "left"},
				{name: "no", index: "no", width: 80, label: "批次号", sortable: true, align: "left", hidden: true},
				{name: "reqpk", index: "reqpk", width: 80, label: "领料标识", sortable: true, align: "left", hidden: true},
				{name: "fixareapk", index: "fixareapk", width: 84, label: "fixareapk", sortable: true, align: "left", hidden: true},
				{name: "fixareadescr", index: "fixareadescr", width: 140, label: "装修区域", sortable: true, align: "left"},
				{name: "intprodpk", index: "intprodpk", width: 84, label: "intprodpk", sortable: true, align: "left", hidden: true},
				{name: "intproddescr", index: "intproddescr", width: 101, label: "集成产品", sortable: true, align: "left"},
				{name: "uomdescr", index: "uomdescr", width: 76, label: "单位", sortable: true, align: "left",editable:true},
				{name: "qty", index: "qty", width: 86, label: "退回数量", sortable: true, align: "right",editable:true,editrules:{number:true}},
				{name: "reqqty", index: "reqqty", width: 85, label: "需求数量", sortable: true, align: "right"},
				{name: "sendedqty", index: "sendedqty", width: 109, label: "总共已发货数量", sortable: true, align: "right"},
				{name: "confirmedqty", index: "confirmedqty", width: 92, label: "已审核数量", sortable: true, align: "right"},
				{name: "returnappqty", index: "returnappqty", width: 100, label: "退回申请数量", sortable: true, align: "right",editable:true,editrules:{number:true}},
				{name: "cost", index: "cost", width: 80, label: "成本", sortable: true, align: "right", hidden: true},
				{name: "allcost", index: "allcost", width: 90, label: "成本总价", sortable: true, align: "right", sum: true, hidden: true},
				{name: "projectcost", index: "projectcost", width: 112, label: "项目经理结算价", sortable: true, align: "right", hidden: true},
				{name: "allprojectcost", index: "allprojectcost", width: 120, label: "项目经理结算总价", sortable: true, align: "right", sum: true, hidden: true},
				{name: "processcost", index: "processcost", width: 92, label: "其它费用", sortable: true, align: "right"},
				{name: "weight", index: "weight", width: 85, label: "总重量", sortable: true, align: "right", sum: true,editable:true,editrules:{number:true}},
				{name: "numdescr", index: "numdescr", width: 85, label: "总片数", sortable: true, align: "right",editable:true,editrules:{number:true}},
				{name: "remarks", index: "remarks", width: 279, label: "备注", sortable: true, align: "left"},
				{name: "lastupdate", index: "lastupdate", width: 84, label: "最后更新时间", sortable: true, align: "left", formatter: formatTime, hidden: true},
				{name: "lastupdatedby", index: "lastupdatedby", width: 84, label: "更新人员", sortable: true, align: "left", hidden: true},
				{name: "expired", index: "expired", width: 84, label: "是否过期", sortable: true, align: "left", hidden: true},
				{name: "actionlog", index: "actionlog", width: 84, label: "操作", sortable: true, align: "left", hidden: true},
					
				{name: "aftqty", index: "aftqty", width: 85, label: "aftqty", sortable: true, align: "right", hidden: true},
				{name: "aftcost", index: "aftcost", width: 85, label: "aftcost", sortable: true, align: "right", hidden: true},
				{name: "sendqty", index: "sendqty", width: 85, label: "sendqty", sortable: true, align: "right", hidden: true},
				{name: "preappdtpk", index: "preappdtpk", width: 85, label: "preappdtpk", sortable: true, align: "left", hidden: true},
				{name: "shortqty", index: "shortqty", width: 85, label: "shortqty", sortable: true, align: "right", hidden: true},
				{name: "declareqty", index: "declareqty", width: 85, label: "declareqty", sortable: true, align: "right", hidden: true},
				{name: "specreqpk", index: "specreqpk", width: 75, label: "specreqpk", sortable: true, align: "left", hidden: true},
				
			],
			gridComplete:function(){
				selectFirstRow();
			},
			beforeSelectRow:function(id){
				setTimeout(function(){
					relocate(id,"dataTable_itemAppReturnDetail");
				},10);
			},
			beforeSaveCell:function(rowId, name, val, iRow, iCol){
				var ret = $("#dataTable_itemAppReturnDetail").jqGrid("getRowData", rowId);
				
				ret[name]=val;
				
				var reg = /^[0-9]+.?[0-9]*$/;
				if(!reg.test(ret.qty)) return;  
				
				$("#dataTable_itemAppReturnDetail").jqGrid("setCell", rowId, "allcost", parseFloat(ret.qty)*parseFloat(ret.cost));
				$("#dataTable_itemAppReturnDetail").jqGrid("setCell", rowId, "allprojectcost", parseFloat(ret.qty)*parseFloat(ret.projectcost));
				$("#dataTable_itemAppReturnDetail").jqGrid("setCell", rowId, "declareqty", parseFloat(ret.qty));
				
				$("#dataTable_itemAppReturnDetail").footerData("set", {
					allcost:$("#dataTable_itemAppReturnDetail").getCol("allcost", false, "sum") ,
					allprojectcost:$("#dataTable_itemAppReturnDetail").getCol("allprojectcost", false, "sum") ,
					weight:$("#dataTable_itemAppReturnDetail").getCol("weight", false, "sum")
				});
			},
			onCellSelect:function(rowid,iCol,cellcontent,e){
				if($("#m_umState").val().trim() == "V" || $("#m_umState").val().trim() == "C"){
                	$("#dataTable_itemAppReturnDetail").jqGrid("setCell", rowid, iCol, "", "not-editable-cell");  
				}
			}
		}); 
	});
	function add(){
		if($("#m_umState").val() == "V"){
			return;
		}
		if($("#oldNo").val() == ""){
			art.dialog({
				content:"请先选择原领料单号",
				ok:function(){
					$("#openComponent_itemApp_oldNo").focus();
				}
			});
			return;
		}
		
		var reqPks= $("#dataTable_itemAppReturnDetail").getCol("reqpk",false);
		
		Global.Dialog.showDialog("add",{
			title:"领料退回明细--增加",
			url:"${ctx}/admin/itemApp/goLlxxthAdd",
			postData:{
				custCode:$("#custCode").val(),
				oldNo:$("#oldNo").val(),
				reqPks:reqPks
			},
			height: 600,
			width:900,
			returnFun:function(data){
				if(data){
					$.each(data,function(i,rowData){
						$.extend(rowData,{
							lastupdate:new Date(),
							lastupdatedby:$("#czybh").val(),
							actionlog:"ADD",
							expired:"F"
						});
						if(rowData.reqqty == 0){
							rowData.reqqty = null;
						}
						//rowData.declareqty=rowData.qty;//申报数量改完与采购数量一样
						Global.JqGrid.addRowData("dataTable_itemAppReturnDetail",rowData);
					});
				}
				oldNoDisabled();
			}
		});
	}
	function del(){
		if($("#m_umState").val() == "V"){
			return;
		}
		var id = $("#dataTable_itemAppReturnDetail").jqGrid("getGridParam", "selrow");
		if(id){
		    art.dialog({
				content: "是删除该记录?",
				ok:function(){
    				$("#dataTable_itemAppReturnDetail").jqGrid("delRowData", id);
					var ids = $("#dataTable_itemAppReturnDetail").jqGrid("getDataIDs");
					if(ids.length > 0){
						$("#dataTable_itemAppReturnDetail").jqGrid("setSelection",ids[0]);
					}
					oldNoDisabled();
				},
				cancel:function(){}
			});
		}else{
			art.dialog({content:"请选择一条记录"});
		}
	}
	function doExcel(){
		doExcelNow("领料退回明细","dataTable_itemAppReturnDetail","returnDetailDataForm");
	}
	function view(){		
		var ret = selectDataTableRow("dataTable_itemAppReturnDetail");
		if(ret){
			Global.Dialog.showDialog("detail",{
				title:"领料退回明细--明细",
				url:"${ctx}/admin/itemApp/goLlxxthDetail",
				postData:{
					custCode:$("#custCode").val(),
					itemCode:ret.itemcode
				},
				height: 600,
				width:900,
			});
		}else{
			art.dialog({content:"请选择一条记录"});
		}
	}
	function oldNoDisabled(){
		var ids = $("#dataTable_itemAppReturnDetail").jqGrid("getDataIDs");
		if(ids.length > 0){
			$("#openComponent_itemApp_oldNo").attr("readonly",true);
			$("#openComponent_itemApp_oldNo").next().attr("data-disabled",true).css({
				"color":"#888"
			});
		}else{
			$("#openComponent_itemApp_oldNo").removeAttr("readonly");
			$("#openComponent_itemApp_oldNo").next().attr("data-disabled",false).css({
				"color":""
			});
		}
	}
</script>
<form action="" method="post" id="returnDetailDataForm"  class="form-search">
	<input type="hidden" name="jsonString" value="" />
</form>
<div class="btn-panel" style="margin-top:2px">
	<div class="btn-group-sm"  >
		<button id="addBtn" type="button" class="funBtn funBtn-system" onclick="add()">新增</button>
		<button id="delBtn" type="button" class="funBtn funBtn-system" onclick="del()">删除</button>
		<button id="doExcelBtn" type="button" class="funBtn funBtn-system" onclick="doExcel()">输出到Excel</button>
		<button id="returnDetailBtn" type="button" class="funBtn funBtn-system" onclick="view()">查看退回申请明细</button>
	</div>
</div>	
<table id="dataTable_itemAppReturnDetail"></table>
