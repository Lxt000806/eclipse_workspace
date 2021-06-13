<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>自动拆单--插入</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>

	<script type="text/javascript">
		$(function(){
			Global.JqGrid.initJqGrid("dataTable_preApply",{
				height:450,
				rowNum: 10000,
				styleUI:"Bootstrap",
				url:"${ctx}/admin/itemPreApp/goAutoOrderJqGrid",
				postData:{
					custCode:$("#custCode").val(),
					itemType1:$("#itemType1").val(),
					itemType2:"",
					preAppNo:$("#preAppNo").val(),
					isService:$("#isService").val(),
					preAppDTPks:$("#preAppDTPks").val(),
					reqPks:$("#reqPks").val(),
					appNo:$("#appNo").val(),
					m_umState:$("#m_umState").val(),
					itemCodes:$("#itemCodes").val(),
					isSetItem:$("#isSetItem").val()
				},
				multiselect: true,
				colModel:[
					{name: "ordernamecode", index: "ordernamecode", width: 75, label: "ordernamecode", sortable: true, align: "left", hidden: true},
					{name:"ordername", index:"ordername", width:70, label:"ordername", sortable:true, align:"left", hidden:true},
					{name:"pk", index:"pk", width:70, label:"编号", sortable:true, align:"left", hidden:true},
					{name:"fixareadescr", index:"fixareadescr", width:120, label:"区域", sortable:true, align:"left"},
					{name:"intproddescr", index:"intproddescr", width:70, label:"集成成品", sortable:true, align:"left"},
					{name:"custcode", index:"custcode", width:70, label:"客户编号", sortable:true, align:"left", hidden:true},
					{name:"itemtype2descr", index:"itemtype2descr", width:110, label:"材料类型2", sortable:true, align:"left"},
					{name:"itemtype3descr", index:"itemtype3descr", width:110, label:"材料类型3", sortable:true, align:"left"},
					{name:"itemcode", index:"itemcode", width:70, label:"材料编号", sortable:true, align:"left"},
					{name:"itemdescr", index:"itemdescr", width:208, label:"材料名称", sortable:true, align:"left"},
					{name:"suppldescr", index:"suppldescr", width:105, label:"供应商", sortable:true, align:"left"},
					{name:"sendtypedescr", index:"sendtypedescr", width:105, label:"发货类型", sortable:true, align:"left"},
					{name:"qty", index:"qty", width:50, label:"数量", sortable:true, align:"left"},
					{name:"itemtype1", index:"itemtype1", width:70, label:"类型编号", sortable:true, align:"left", hidden:true},
					{name:"isoutsetdescr", index:"isoutsetdescr", width:80, label:"套餐外材料", sortable:true, align:"left"},
					{name:"typedescr", index:"typedescr", width:70, label:"材料类型", sortable:true, align:"left", hidden:true},
					{name:"reqqty", index:"reqqty", width:70, label:"需求数量", sortable:true, align:"left"},
					{name:"sendqty", index:"sendqty", width:80, label:"已发货数量", sortable:true, align:"left"},
					{name:"preremarks", index:"preremarks", width:150, label:"备注", sortable:true, align:"left"},
					{name:"reqpk", index:"reqpk", width:70, label:"领料标识", sortable:true, align:"left", hidden:true},
					{name:"uomdescr", index:"uomdescr", width:60, label:"单位", sortable:true, align:"left", hidden:true},
					{name:"reqremarks", index:"reqremarks", width:60, label:"预算备注", sortable:true, align:"left", hidden:true},
					{name:"no", index:"no", width:80, label:"批次号", sortable:true, align:"left", hidden:true},
					{name:"fixareapk", index:"fixareapk", width:84, label:"fixareapk", sortable:true, align:"left", hidden:true},
					{name:"intprodpk", index:"intprodpk", width:84, label:"intprodpk", sortable:true, align:"left", hidden:true},
					{name:"color", index:"color", width:76, label:"颜色", sortable:true, align:"left", hidden:true},
					{name:"sqlcodedescr", index:"sqlcodedescr", width:78, label:"品牌", sortable:true, align:"left", hidden:true},
					{name:"supplcodedescr", index:"supplcodedescr", width:160, label:"供应商", sortable:true, align:"left", hidden:true},
					{name:"declareqty", index:"declareqty", width:76, label:"申报数量", sortable:true, align:"left",hidden:true},
					{name:"shortqty", index:"shortqty", width:89, label:"缺货数量", sortable:true, align:"left", hidden:true},
					{name:"sendedqty", index:"sendedqty", width:89, label:"总共已发货", sortable:true, align:"left", sum:true, hidden:true},
					{name:"applyqty", index:"applyqty", width:89, label:"已申请数量", sortable:true, align:"left", hidden:true},
					{name:"confirmedqty", index:"confirmedqty", width:89, label:"已审核数量", sortable:true, align:"left", sum:true, hidden:true},
					{name: "allqty", index: "allqty", width: 80, label: "库存数量", sortable: true, align: "right", sum: true,hidden: true},
					{name: "userqty", index: "userqty", width: 80, label: "可用数量", sortable: true, align: "right", sum: true,hidden: true},
					{name:"cost", index:"cost", width:81, label:"成本单价", sortable:true, align:"left", hidden:true},
					{name:"allcost", index:"allcost", width:90, label:"成本总价", sortable:true, align:"left", sum:true, hidden:true},
					{name:"projectcost", index:"projectcost", width:110, label:"项目经理结算价", sortable:true, align:"left", hidden:true},
					{name:"allprojectcost", index:"allprojectcost", width:120, label:"项目经理结算总价", sortable:true, align:"left", sum:true, hidden:true},
					{name:"price", index:"price", width:80, label:"销售价", sortable:true, align:"left", hidden:true},
					{name:"processcost", index:"processcost", width:80, label:"其它费用", sortable:true, align:"left", sum:true, hidden:true},
					{name:"differences", index:"differences", width:89, label:"成本差异额", sortable:true, align:"left", sum:true, hidden:true},
					{name:"pricedifferences", index:"pricedifferences", width:90, label:"销售差异额", sortable:true, align:"left", hidden:true},
					{name:"weight", index:"weight", width:85, label:"总重量", sortable:true, align:"left", sum:true, hidden:true},
					{name:"numdescr", index:"numdescr", width:85, label:"总片数", sortable:true, align:"left", hidden:true},
					{name:"reqprocesscost", index:"reqprocesscost", width:156, label:"reqprocesscost", sortable:true, align:"left", hidden:true},
					{name:"perweight", index:"perweight", width:156, label:"perweight", sortable:true, align:"left", hidden:true},
					{name:"isoutset", index:"isoutset", width:156, label:"isoutset", sortable:true, align:"left", hidden:true},
					{name:"aftqty", index:"aftqty", width:90, label:"aftqty", sortable:true, align:"left", hidden:true},
					{name:"aftcost", index:"aftcost", width:90, label:"aftcost", sortable:true, align:"left", hidden:true},
					{name:"whcode", index:"whcode", width:90, label:"whcode", sortable:true, align:"left", hidden:true},
					{name:"whdescr", index:"whdescr", width:90, label:"whdescr", sortable:true, align:"left", hidden:true},
					{name:"supplcode", index:"supplcode", width:90, label:"supplcode", sortable:true, align:"left", hidden:true},
					{name:"sendtype", index:"sendtype", width:90, label:"sendtype", sortable:true, align:"left", hidden:true},
					{name:"preappdtpk", index:"preappdtpk", width:75, label:"preappdtpk", sortable:true, align:"left", hidden:true},
					{name:"itemtype2", index:"itemtype2", width:90, label:"itemtype2", sortable:true, align:"left", hidden:true},
					{name:"itemtype3", index:"itemtype3", width:90, label:"itemtype3", sortable:true, align:"left", hidden:true},
					{name:"ordertype", index:"ordertype", width:90, label:"ordertype", sortable:true, align:"left", hidden:true},
					{name:"delivtype", index: "delivtype", width: 90, label: "delivtype", sortable: true, align: "left",hidden:true},
				],
				gridComplete:function (){
					dataTableCheckBox("dataTable_preApply", "itemtype2descr");
					dataTableCheckBox("dataTable_preApply", "itemtype3descr");
					dataTableCheckBox("dataTable_preApply", "suppldescr");
					dataTableCheckBox("dataTable_preApply", "sendtypedescr");
				},
			});
		});
		function save(){
			var ids=$("#dataTable_preApply").jqGrid("getGridParam", "selarrrow");
			if(ids.length == 0){
				art.dialog({
					content:"请选择要领用的材料!"
				});				
				return ;
			}
			var selectRows = [];
			var qtyZero = "";
			var isOutSet = false;
			var isInSet = false;
			$.each(ids,function(i,id){
				var rowData = $("#dataTable_preApply").jqGrid("getRowData", id);
				if(parseFloat(rowData.reqqty) == 0 && rowData.reqpk > 0){
					qtyZero += "["+rowData.itemdescr+"]<br/>";
				}
				if(rowData.isoutset == "1"){
					isOutSet = true;
				}else if(rowData.isoutset == "0"){
					isInSet = true;
				}
				if("${data.isInsert}"=="1"){
					rowData.ordername="${data.ordername}";
					rowData.ordernamecode="${data.ordernamecode}";
				}
				selectRows.push(rowData);
			});
			if(qtyZero != ""){
				art.dialog({
					content:"以下材料需求数量为0，无法进行领料操作!<br/>"+qtyZero
				});
				return;
			}
			if(isOutSet && isInSet){
				art.dialog({
					content:"同时存在套餐内外材料，是否确认保存?",
					ok:function(){
			    		Global.Dialog.returnData = selectRows;
			    		closeWin();
					},
					cancel:function(){}
				});
				return ;
			}
    		Global.Dialog.returnData = selectRows;
    		closeWin();
		}
		function tableSelected(flag){  
			//update by zzr 2017/12/18 -全选时过滤不允许选中的记录-
			if("${data.itemConfCtrl}" == "1" || "${data.itemConfCtrl}" == "2"){
				$("#cb_dataTable_preApply_new").get(0).checked=flag;
				if(flag == true){
					$("#dataTable_preApply").jqGrid("resetSelection");
					var ids=$("#dataTable_preApply").jqGrid("getDataIDs");
					for(var i=0;i<ids.length;i++){
						var canSelect = $("#dataTable_preApply #"+ids[i]+" td #jqg_dataTable_preApply_"+ids[i]).attr("disabled")?false:true;
						if($("#dataTable_preApply tbody tr[id="+ids[i]+"]").css("display")=="table-row" && canSelect){
							$("#dataTable_preApply").jqGrid("setSelection", ids[i], flag);
						}
					}
				}else{
					$("#dataTable_preApply").jqGrid("resetSelection");
				}
			}else{
				selectAll("dataTable_preApply", flag);
			}
			//Global.JqGrid.jqGridSelectAll("dataTable_preApply",flag);
		}
	</script>
</head>
    
<body>
	<div class="body-box-list">
		<div class="panel panel-system" >
		    <div class="panel-body" >
		    	<div class="btn-group-xs" >
	   				<button id="saveBut" type="button" class="btn btn-system" onclick="save()">保存</button>
	   				<button id="allSelectBut" type="button" class="btn btn-system" onclick="tableSelected(true)">全选</button>
	   				<button id="nonSelectBut" type="button" class="btn btn-system" onclick="tableSelected(false)">不选</button>
	   				<button id="closeBut" type="button" class="btn btn-system" onclick="closeWin(false)">关闭</button>
				</div>
			</div>
		</div>
		
		<div class="clear_float"> </div>

		<input type="hidden" id="custCode" name="custCode" value="${data.custCode}"/>
		<input type="hidden" id="itemType1" name="itemType1" value="${data.itemType1}"/>
		<input type="hidden" id="preAppNo" name="preAppNo" value="${data.preAppNo}"/>
		<input type="hidden" id="isService" name="isService" value="${data.isService}"/>
		<input type="hidden" id="appNo" name="appNo" value="${data.appNo}"/>
		<input type="hidden" id="m_umState" name="m_umState" value="${data.m_umState}"/>
		<input type="hidden" id="reqPks" name="reqPks" value="${data.reqPks}"/>
		<input type="hidden" id="preAppDTPks" name="preAppDTPks" value="${data.preAppDTPks}"/>
		<input type="hidden" id="itemCodes" name="itemCodes" value="${data.itemCodes}"/>
		<input type="hidden" id="isSetItem" name="isSetItem" value="${data.isSetItem}"/>
		<div id="content-list">
			<table id= "dataTable_preApply"></table>
		</div>
	</div>
</body>
</html>


