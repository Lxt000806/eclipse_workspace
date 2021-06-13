<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <title>已有项变动列表</title>
  <META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8"/>
  <META HTTP-EQUIV="pragma" CONTENT="no-cache"/>
  <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"/>
  <META HTTP-EQUIV="expires" CONTENT="0"/>
  <META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge"/>
  <%@ include file="/commons/jsp/common.jsp" %>
  <style type="text/css">
    .commiColor {
      background-color: yellow;
    }
	.SelectBG{
          background-color:#198ede!important;
            color:rgb(255,255,255)
    }
    .SelectW{
          background-color:white!important;
            color:rgb(255,255,255)
    }
  </style>
  <script type="text/javascript">
    var arrReqpk;
    var reqpk;
    var id ='';
    var itemReplaceRowId=0;
    /**初始化表格*/
    $(function () {
      $("#content-list").show();
      $("#content-list2").show();	
      $("#content-list3").hide();	
      $("#content-list4").hide();	
      $("#clearPrice").hide();
      $("#descr").hide();
      $("#search").hide();
      $("#descr_label").hide();
      $("#clearPrice_span").hide();
      $("#page_form").show();	
      $("#saveBtn").show();
      $("#selectallBtn").show();
      $("#unselectallBtn").show();
      $("#itemReplaceBtn").show(); 
      $("#itemChgBtn").hide();	 
      $("#continueAddBtn").hide();
      $("#confirmBtn").hide();
      $("#deleteBtn").hide();
      if (top.$("#iframe_itemChg_Add")[0]) {
        var topFrame = "#iframe_itemChg_Add";
      } else if (top.$("#iframe_itemChgConfirm")[0]) {
        var topFrame = "#iframe_itemChgConfirm";
      } else {
        var topFrame = "#iframe_itemChgUpdate";
      }
      arrReqpk = top.$(topFrame)[0].contentWindow.getReqPk();
      reqpk=arrReqpk.join(",");
      
      $("#descr").on("keydown",function(e){
				console.log(e)
			if(e.keyCode == 13){
				searchItems();
			}
      });    
      
      //初始化表格
      Global.JqGrid.initJqGrid("dataTable", {
        url: "${ctx}/admin/itemChg/goTransActionJqGrid?custCode=${itemChg.custCode}&itemType1=${itemChg.itemType1}&isService=${itemChg.isService}&isCupboard=${itemChg.isCupboard}&unSelected=" + reqpk,
        height: $(document).height() - $("#content-list").offset().top - 62,
        multiselect: true,
        styleUI: 'Bootstrap',
        rowNum: 10000,
        footerrow: false,
		colModel : [
		  {name: "custcode", index: "custcode", width: 85, label: "客户编号", sortable: true, align: "left", hidden: true},
		  {name: "itemcode", index: "itemcode", width: 85, label: "材料编号", sortable: true, align: "left", hidden: true},
		  {name: "customerdescr", index: "customerdescr", width: 85, label: "客户名称", sortable: true, align: "left", hidden: true},
		  {name: "address", index: "address", width: 130, label: "楼盘", sortable: true, align: "left", hidden: true},
		  {name: "fixareadescr", index: "fixareadescr", width: 85, label: "区域名称", sortable: true, align: "left"},
		  {name: "fixareapk", index: "fixareapk", width: 127, label: "区域编号", sortable: true, align: "left", hidden: true},
		  {name: "intprodpk", index: "intprodpk", width: 127, label: "集成成品编号", sortable: true, align: "left", hidden: true},
		  {name: "intproddescr", index: "intproddescr", width: 70, label: "集成成品", sortable: true, align: "left", hidden: true},
		  {name: "itemdescr", index: "itemdescr", width: 150, label: "材料名称", sortable: true, align: "left"},
		  {name: "typedescr", index: "typedescr", width: 65, label: "材料类型1", sortable: true, align: "left"},
		  {name: "itemtype2descr", index: "itemtype2descr", width: 67, label: "材料类型2", sortable: true, align: "left"},
		  {name: "itemtype2", index: "itemtype2", width: 85, label: "材料类型2", sortable: true, align: "left", hidden: true},
		  {name: "itemtype3descr", index: "itemtype3descr", width: 65, label: "材料类型3", sortable: true, align: "left", hidden: true},
		  {name: "qty", index: "qty", width: 50, label: "数量", sortable: true, align: "right"},
		  {name: "uom", index: "uom", width: 30, label: "单位", sortable: true, align: "left", hidden: true},
		  {name: "beflineamount", index: "beflineamount", width: 80, label: "折扣前金额", sortable: true, align: "left", sum: true, hidden: true},
		  {name: "uomdescr", index: "uomdescr", width: 61, label: "单位", sortable: true, align: "left"},
		  {name: "sendqty", index: "sendqty", width: 65, label: "发货数量", sortable: true, align: "right"},
		  {name: "cost", index: "cost", width: 50, label: "成本", sortable: true, align: "right",hidden: true},
		  {name: "unitprice", index: "unitprice", width: 50, label: "单价", sortable: true, align: "right"},
		  {name: "markup", index: "markup", width: 40, label: "折扣", sortable: true, align: "right"},
		  {name: "tmplineamount", index: "tmplineamount", width: 77, label: "小计", sortable: true, align: "right"},
		  {name: "processcost", index: "processcost", width: 90, label: "其他费用", sortable: true, align: "right"},
		  {name: "lineamount", index: "lineamount", width: 90, label: "总价", sortable: true, align: "right"},
		  {name: "isoutsetdescr", index: "isoutsetdescr", width: 98, label: "套餐外材料", sortable: true, align: "left"},
		  {name: "isoutset", index: "isoutset", width: 98, label: "套餐外材料", sortable: true, align: "left", hidden: true},
		  {name: "remark", index: "remark", width: 140, label: "备注", sortable: true, align: "left"},
		  {name: "lastupdate", index: "lastupdate", width: 110, label: "修改时间", sortable: true, align: "left", formatter: formatTime},
		  {name: "lastupdatedby", index: "lastupdatedby", width: 70, label: "修改人", sortable: true, align: "left"},
		  {name: "expired", index: "expired", width: 85, label: "是否过期", sortable: true, align: "left"},
		  {name: "actionlog", index: "actionlog", width: 70, label: "操作", sortable: true, align: "left"},
		  {name: "reqpk", index: "reqpk", width: 20, label: "需求PK", sortable: true, align: "left", hidden: true},
		  {name: "itemsetno", index: "itemsetno", width: 118, label: "套餐包 ", sortable: true, align: "left", hidden: true},
		  {name: "itemsetdescr", index: "itemsetdescr", width: 118, label: "套餐包 ", sortable: true, align: "left", hidden: true},
		  {name: "custtypeitempk", index: "custtypeitempk", width: 0.5, label: "原套餐材料信息编号", sortable: true, align: "left"},
		  {name: "algorithm", index: "algorithm", width: 85, label: "算法", sortable:false, align: "left", hidden: true},
		  {name: "algorithmdescr", index: "algorithmdescr", width: 85, label: "算法", sortable:false, align: "left",hidden: true},
		  {name: "preplanareadescr", index: "preplanareadescr", width: 70, label: "空间名称", sortable:false, align: "left",hidden: true},
		  {name: "preplanareapk", index: "preplanareapk", width: 60, label: "空间pk", sortable:false, align: "left", hidden: true},
		  {name: "cuttypedescr", index: "cuttypedescr", width: 70, label: "切割类型", sortable:false, align: "left",hidden: true},
		  {name: "cuttype", index: "cuttype", width: 65, label: "切割类型",  width: 70,sortable:false, align: "left",hidden: true},
		  {name: "supplpromitempk", index: "supplpromitempk", width: 65, label: "供应商促销pk",  width: 0.5,sortable:false, align: "left",hidden: true},
		  {name: "doorpk", index: "doorpk", width: 65, label: "doorpk",  width: 0.5,sortable:false, align: "left",hidden: true},
		  {name: "algorithmper", index: "algorithmper", width: 80, label: "算法系数", sortable: true, align: "right",hidden: true},
		  {name: "algorithmdeduct", index: "algorithmdeduct", width: 80, label: "算法扣除数", sortable: true, align: "right",hidden: true},
		],
      });
       Global.JqGrid.initJqGrid("itemReplace_dataTable", {
    	url:"",
        height: 270,
        styleUI: 'Bootstrap',
        rowNum: 10000,
        cellEdit: true,
        cellsubmit: 'clientArray',
        mustUseEdit:true,
        hoverrows:false,
		colModel : [
		  {name: "deleteBtn", index: "deleteBtn", width: 84, label: "删除", sortable: false, align: "left", formatter: deleteBtn,frozen:true},
		  {name: "fixareadescr", index: "fixareadescr", width: 85, label: "区域名称", sortable: true, align: "left",frozen:true},
		  {name: "chgitemdescr", index: "chgitemdescr", width: 150, label: "新材料", sortable: true, align: "left",frozen:true},
	      {name: "algorithmdescr", index: "algorithmdescr", width: 100, label: "新算法", sortable: true, align: "left",editable:true,hidden:($.trim('${itemChg.itemType1}')== 'RZ'|| $.trim('${itemChg.itemType1}')== 'JC')?true:false,
		  		edittype:'select',
		  		editoptions:{ 
		  			dataUrl: "${ctx}/admin/item/getAlgorithm",
					postData: function(){
						var ret = selectDataTableRow("itemReplace_dataTable");
						if(ret){
							return {
								code: ret.itemcode
							};
						}
						return {code: ""};
					},
					buildSelect: function(e){
						var ret = selectDataTableRow("itemReplace_dataTable");

						var lists = JSON.parse(e);
						var html = "<option value=\"\" ></option>";
						for(var i = 0; lists && i < lists.length; i++){
							html += "<option value=\""+ lists[i].Code +"\">" + lists[i].Descr + "</option>"
						}
						return "<select style=\"font-family:宋体;\"> " + html + "</select>";
					},
		  			dataEvents:[{
	  					type:'change',
	  					fn:function(e){ 
	  						algorithmClick(e);
	  					}
		  			}, 
		  		]}
	 	  },
		  {name: "chgcuttypedescr", index: "chgcuttypedescr", width: 70, label: "新切割类型",  width: 70,sortable:false, align: "left", editable:true,hidden:($.trim('${itemChg.itemType1}')== 'RZ'|| $.trim('${itemChg.itemType1}')== 'JC')?true:false,
			  		edittype:'select',
			  		editoptions:{ 
			  			dataUrl: "${ctx}/admin/prePlanTempDetail/getQtyByCutType",
						postData: function(){
							var ret = selectDataTableRow("itemReplace_dataTable");
							if(ret){
								return {
									itemCode: ret.itemcode
								};
							}
							return {itemCode: ""};
						},
						buildSelect: function(e){
							var ret = selectDataTableRow("itemReplace_dataTable");

							var lists = JSON.parse(e);
							var html = "<option value=\"\" ></option>";
							
							for(var i = 0; lists && i < lists.length; i++){
								html += "<option value=\""+ lists[i].Code +"\">" + lists[i].fd + "</option>"
							}
							return "<select style=\"font-family:宋体;\"> " + html + "</select>";
						},
			  			dataEvents:[{
		  					type:'change',
		  					fn:function(e){ 
		  						cuttypeClick(e);
		  					}
			  			}, 
			  		]}
		 		},
	 	  {name: "chgqty", index: "chgqty", width: 55, label: "新数量", sortable: true, align: "right",editable:true,edittype:'text',editrules:{number:true,required:true,minValue:0.01}},
		  {name: "chguomdescr", index: "chguomdescr", width: 55, label: "新单位", sortable: true, align: "left"},
		  {name: "chgunitprice", index: "chgunitprice", width: 60, label: "新单价", sortable: true, align: "right"},
		  {name: "markup", index: "markup", width: 40, label: "折扣", sortable: true, align: "right"},
		  {name: "chgprocesscost", index: "chgprocesscost", width: 80, label: "新其他费用", sortable: true, align: "right",editable:true,edittype:'text',editrules:{number:true,required:true}},
		  {name: "chglineamount", index: "chglineamount", width: 90, label: "新总价", sortable: true, align: "right"},
		  {name: "chgremark", index: "chgremark", width: 140, label: "新备注", sortable: true, align: "left",editable:true,edittype:'textarea'}, 
		  {name: "chgisoutsetdescr", index: "chgisoutsetdescr", width: 60, label: "新套外", sortable: true, align: "left"},
		  {name: "itemdescr", index: "itemdescr", width: 150, label: "原材料", sortable: true, align: "left"},
		  {name: "oldalgorithmdescr", index: "oldalgorithmdescr", width: 100, label: "原算法", sortable: true, align: "left"},
		  {name: "qty", index: "qty", width: 60, label: "原数量", sortable: true, align: "right"},
		  {name: "unitprice", index: "unitprice", width: 60, label: "原单价", sortable: true, align: "right"},
		  {name: "processcost", index: "processcost", width: 80, label: "原其他费用", sortable: true, align: "right"},
		  {name: "uomdescr", index: "guomdescr", width: 55, label: "原单位", sortable: true, align: "left"},
		  {name: "lineamount", index: "lineamount", width: 90, label: "原总价", sortable: true, align: "right"},
		  {name: "remark", index: "remark", width: 100, label: "原备注", sortable: true, align: "left"}, 
		  {name: "isoutsetdescr", index: "isoutsetdescr", width: 50, label: "原套外", sortable: true, align: "left"},
		  {name: "custtypeitempk", index: "custtypeitempk", width: 0.5, label: "套餐材料信息编号", sortable: true, align: "left"},
		  {name: "chgcusttypeitempk", index: "chgcusttypeitempk", width: 0.5, label: "新套餐材料信息编号", sortable: true, align: "left"},
		  {name: "cuttypedescr", index: "cuttypedescr", width: 75, label: "原切割类型", sortable:false, align: "left"},
		  // 以下 隐藏列
		  {name: "custcode", index: "custcode", width: 85, label: "客户编号", sortable: true, align: "left", hidden: true},
		  {name: "itemcode", index: "itemcode", width: 85, label: "原材料编号", sortable: true, align: "left", hidden: true},
	      {name: "chgitemcode", index: "chgitemcode", width: 85, label: "新材料编号", sortable: true, align: "left", hidden: true},
		  {name: "customerdescr", index: "customerdescr", width: 85, label: "客户名称", sortable: true, align: "left", hidden: true},
		  {name: "address", index: "address", width: 130, label: "楼盘", sortable: true, align: "left", hidden: true},
		  {name: "preplanareadescr", index: "preplanareadescr", width: 70, label: "空间名称", sortable:false, align: "left",hidden:true},
		  {name: "algorithm", index: "algorithm", width: 85, label: "算法", sortable:false, align: "left", hidden: true},
		  {name: "chgcuttype", index: "chgcuttype", width: 70, label: "新切割类型",  width:70,sortable:false, align: "left",hidden:true},
		  {name: "chgbeflineamount", index: "chgbeflineamount", width: 80, label: "新折扣前金额", sortable: true, align: "left", sum: true, hidden: true},
		  {name: "tmplineamount", index: "tmplineamount", width: 77, label: "小计", sortable: true, align: "right", hidden: true},
		  {name: "chgtmplineamount", index: "chgtmplineamount", width: 77, label: "新小计", sortable: true, align: "right", hidden: true},
	 	  {name: "fixareapk", index: "fixareapk", width: 127, label: "区域编号", sortable: true, align: "left", hidden: true},
		  {name: "preplanareapk", index: "preplanareapk", width: 60, label: "空间pk", sortable:false, align: "left", hidden: true},
		  {name: "intprodpk", index: "intprodpk", width: 127, label: "原集成成品编号", sortable: true, align: "left", hidden: true},
		  {name: "intproddescr", index: "intproddescr", width: 70, label: "原集成成品", sortable: true, align: "left", hidden: true},
		  {name: "chgintprodpk", index: "chgintprodpk", width: 127, label: "新集成成品编号", sortable: true, align: "left", hidden: true},
		  {name: "chgintproddescr", index: "chgintproddescr", width: 70, label: "新集成成品", sortable: true, align: "left", hidden: true},
		  {name: "typedescr", index: "typedescr", width: 65, label: "材料类型称", sortable: true, align: "left", hidden: true},
		  {name: "itemtype2descr", index: "itemtype2descr", width: 67, label: "材料类型2", sortable: true, align: "left", hidden: true},
		  {name: "itemtype2", index: "itemtype2", width: 85, label: "材料类型2", sortable: true, align: "left", hidden: true},
		  {name: "itemtype3descr", index: "itemtype3descr", width: 65, label: "材料类型3", sortable: true, align: "left", hidden: true},
		  {name: "chgitemtype2descr", index: "chgitemtype2descr", width: 67, label: "新材料类型2", sortable: true, align: "left", hidden: true},
		  {name: "chgitemtype2", index: "chgitemtype2", width: 85, label: "新材料类型2", sortable: true, align: "left", hidden: true},
		  {name: "chgitemtype3descr", index: "chgitemtype3descr", width: 65, label: "新材料类型3", sortable: true, align: "left", hidden: true},
		  {name: "uom", index: "uom", width: 30, label: "原单位", sortable: true, align: "left", hidden: true},
		  {name: "beflineamount", index: "beflineamount", width: 80, label: "折扣前金额", sortable: true, align: "left", sum: true, hidden: true},
		  {name: "sendqty", index: "sendqty", width: 65, label: "发货数量", sortable: true, align: "right", hidden: true},
		  {name: "cost", index: "cost", width: 50, label: "成本", sortable: true, align: "right",hidden: true},
		  {name: "chgcost", index: "chgcost", width: 50, label: "新成本", sortable: true, align: "right",hidden: true},
		  {name: "isoutset", index: "isoutset", width: 98, label: "套餐外材料", sortable: true, align: "left", hidden: true},
		  {name: "chgisoutset", index: "chgisoutset", width: 75, label: "套餐外材料", sortable: true, align: "left"},
		  {name: "reqpk", index: "reqpk", width: 20, label: "需求PK", sortable: true, align: "left", hidden: true},
		  {name: "itemsetno", index: "itemsetno", width: 118, label: "套餐包 ", sortable: true, align: "left", hidden: true},
		  {name: "itemsetdescr", index: "itemsetdescr", width: 118, label: "套餐包 ", sortable: true, align: "left", hidden: true},
		  {name: "algorithm", index: "algorithm", width: 85, label: "原算法", sortable:false, align: "left", hidden: true},
		  {name: "chgpreplanareadescr", index: "chgpreplanareadescr", width: 80, label: "新空间名称", sortable:false, align: "left",hidden:true },
		  {name: "chgpreplanareapk", index: "chgpreplanareapk", width: 60, label: "新空间pk", sortable:false, align: "left",hidden:true },
		  {name: "cuttype", index: "cuttype", width: 75, label: "原切割类型",  width: 0.5,sortable:false, align: "left",hidden: true},
		  {name: "chgfixareadescr", index: "chgfixareadescr", width: 85, label: "新区域名称", sortable: true, align: "left", hidden:true},
		  {name: "chgfixareapk", index: "chgfixareapk", width: 127, label: "新区域编号", sortable: true, align: "left", hidden:true},
		  {name: "doorpk", index: "doorpk", width: 127, label: "门窗pk", sortable: true, align: "left", hidden:true},
		  {name: "algorithmper", index: "algorithmper", width: 80, label: "原算法系数", sortable: true, align: "right",hidden: true},
		  {name: "algorithmdeduct", index: "algorithmdeduct", width: 80, label: "原算法扣除数", sortable: true, align: "right",hidden: true},
		],
		beforeSaveCell:function(rowId,name,val,iRow,iCol){
			lastCellRowId = rowId;
		},
		afterSaveCell:function(rowId,name,val,iRow,iCol){  
		  var rowData = $("#itemReplace_dataTable").jqGrid("getRowData",rowId);
  	  	  if (rowData.chgitemcode){
             $("#itemReplace_dataTable").setCell(rowId,'chgbeflineamount',myRound(parseFloat(rowData.chgqty)* parseFloat(rowData.chgunitprice),4));
             $("#itemReplace_dataTable").setCell(rowId,'chgtmplineamount',myRound(myRound(parseFloat(rowData.chgqty)* parseFloat(rowData.chgunitprice),4)*parseFloat(rowData.markup)/100 ));
             $("#itemReplace_dataTable").setCell(rowId,'chglineamount',myRound(myRound(parseFloat(rowData.chgqty)* parseFloat(rowData.chgunitprice),4)*parseFloat(rowData.markup)/100)+parseFloat(rowData.chgprocesscost)); 	
  	  	  } 
	  	  	if(rowData.cuttype != "" && name =="chgqty"){
	            changeAlgorithm(rowId,rowData.cuttype,"");
	        }
   		},
        beforeSelectRow:function(id){
        	 setTimeout(function(){
          		 relocate(id,"itemReplace_dataTable");
        	  },10);
        },
        loadComplete: function(){
        	return false;
        },
        gridComplete: function () {
        	frozenHeightReset("itemReplace_dataTable");
        },
        onCellSelect: function(id,iCol,cellParam,e){
			var ids = $("#itemReplace_dataTable").jqGrid("getDataIDs");  
			
			$("#itemReplace_dataTable_frozen tr").removeClass("altrow selected-row active success SelectBG");
			$("#itemReplace_dataTable_frozen tr td").removeClass("success");
			
			$("#itemReplace_dataTable_frozen tr[id='"+id+"']").addClass("SelectBG");
		},
      });
   		$("#itemReplace_dataTable").jqGrid('setFrozenColumns');

       Global.JqGrid.initJqGrid("itemReplaceDataTable", {
   		height: 240,
   		styleUI: 'Bootstrap',
   		rowNum: 10000,
   		cellsubmit: 'clientArray',
   		colModel : [
   			{name: "custcode", index: "custcode", width: 85, label: "客户编号", sortable: true, align: "left", hidden: true},
   			{name: "itemcode", index: "itemcode", width: 85, label: "原材料编号", sortable: true, align: "left", hidden: true},
   	      	{name: "chgitemcode", index: "chgitemcode", width: 85, label: "新材料编号", sortable: true, align: "left", hidden: true},
   		  	{name: "customerdescr", index: "customerdescr", width: 85, label: "客户名称", sortable: true, align: "left", hidden: true},
   			{name: "address", index: "address", width: 130, label: "楼盘", sortable: true, align: "left", hidden: true},
   			{name: "fixareadescr", index: "fixareadescr", width: 85, label: "区域名称", sortable: true, align: "left"},
   		  	{name: "fixareapk", index: "fixareapk", width: 127, label: "区域编号", sortable: true, align: "left", hidden: true},
   		  	{name: "preplanareadescr", index: "preplanareadescr", width: 70, label: "空间名称", sortable:false, align: "left", hidden: true},
   		  	{name: "preplanareapk", index: "preplanareapk", width: 60, label: "空间pk", sortable:false, align: "left", hidden: true},
   		  	{name: "intprodpk", index: "intprodpk", width: 127, label: "原集成成品编号", sortable: true, align: "left", hidden: true},
   		  	{name: "intproddescr", index: "intproddescr", width: 70, label: "原集成成品", sortable: true, align: "left", hidden: true},
   		  	{name: "chgintprodpk", index: "chgintprodpk", width: 127, label: "新集成成品编号", sortable: true, align: "left", hidden: true},
   		  	{name: "chgintproddescr", index: "chgintproddescr", width: 70, label: "新集成成品", sortable: true, align: "left", hidden: true},
   		  	{name: "itemdescr", index: "itemdescr", width: 150, label: "原材料", sortable: true, align: "left"},
   		  	{name: "chgitemdescr", index: "chgitemdescr", width: 150, label: "新材料", sortable: true, align: "left", hidden: true},
   		  	{name: "typedescr", index: "typedescr", width: 65, label: "材料类型1", sortable: true, align: "left", hidden: true},
   		  	{name: "itemtype2descr", index: "itemtype2descr", width: 67, label: "材料类型2", sortable: true, align: "left", hidden: true},
   		  	{name: "itemtype2", index: "itemtype2", width: 85, label: "材料类型2", sortable: true, align: "left", hidden: true},
   		  	{name: "itemtype3descr", index: "itemtype3descr", width: 65, label: "材料类型3", sortable: true, align: "left", hidden: true},
   		  	{name: "chgitemtype2descr", index: "chgitemtype2descr", width: 67, label: "新材料类型2", sortable: true, align: "left", hidden: true},
   		  	{name: "chgitemtype2", index: "chgitemtype2", width: 85, label: "新材料类型2", sortable: true, align: "left", hidden: true},
   		  	{name: "chgitemtype3descr", index: "chgitemtype3descr", width: 65, label: "新材料类型3", sortable: true, align: "left", hidden: true},
   		  	{name: "qty", index: "qty", width: 60, label: "原数量", sortable: true, align: "right", },
   		  	{name: "chgqty", index: "chgqty", width: 40, label: "新数量", sortable: true, align: "right",editable:true,edittype:'text',editrules:{number:true,required:true,minValue:0.01}, hidden: true},
   		  	{name: "uom", index: "uom", width: 30, label: "原单位", sortable: true, align: "left", hidden: true},
   		  	{name: "uomdescr", index: "guomdescr", width: 55, label: "原单位", sortable: true, align: "left",},
   		  	{name: "chguomdescr", index: "chguomdescr", width: 55, label: "新单位", sortable: true, align: "left", hidden: true},
   		  	{name: "beflineamount", index: "beflineamount", width: 80, label: "折扣前金额", sortable: true, align: "left",  hidden: true},
   		  	{name: "chgbeflineamount", index: "chgbeflineamount", width: 80, label: "新折扣前金额", sortable: true, align: "left",  hidden: true},
   		  	{name: "sendqty", index: "sendqty", width: 65, label: "发货数量", sortable: true, align: "right", hidden: true},
   		  	{name: "cost", index: "cost", width: 50, label: "成本", sortable: true, align: "right",hidden: true},
   		  	{name: "chgcost", index: "chgcost", width: 50, label: "新成本", sortable: true, align: "right",hidden: true},
   		  	{name: "unitprice", index: "unitprice", width: 60, label: "原单价", sortable: true, align: "right",hidden:true },
   		  	{name: "chgunitprice", index: "chgunitprice", width: 60, label: "新单价", sortable: true, align: "right", hidden: true},
   		  	{name: "markup", index: "markup", width: 40, label: "折扣", sortable: true, align: "right", hidden: true},
   		  	{name: "tmplineamount", index: "tmplineamount", width: 77, label: "小计", sortable: true, align: "right", hidden: true},
   		  	{name: "chgtmplineamount", index: "chgtmplineamount", width: 77, label: "新小计", sortable: true, align: "right", hidden: true},
   		  	{name: "processcost", index: "processcost", width: 80, label: "原其他费用", sortable: true, align: "right", hidden: true},
   		  	{name: "chgprocesscost", index: "chgprocesscost", width: 80, label: "新其他费用", sortable: true, align: "right",editable:true,edittype:'text',editrules:{number:true,required:true}, hidden: true},
   		  	{name: "lineamount", index: "lineamount", width: 90, label: "原总价", sortable: true, align: "right", hidden: true},
   		  	{name: "chglineamount", index: "chglineamount", width: 90, label: "新总价", sortable: true, align: "right", hidden: true},
   		  	{name: "remark", index: "remark", width: 200, label: "原备注", sortable: true, align: "left"}, 
   		  	{name: "chgremark", index: "chgremark", width: 140, label: "新备注", sortable: true, align: "left",editable:true,edittype:'textarea', hidden: true}, 
   		  	{name: "isoutsetdescr", index: "isoutsetdescr", width: 75, label: "套餐外材料", sortable: true, align: "left", hidden: true},
   		  	{name: "isoutset", index: "isoutset", width: 98, label: "套餐外材料", sortable: true, align: "left", hidden: true},
   		  	{name: "reqpk", index: "reqpk", width: 20, label: "需求PK", sortable: true, align: "left", hidden: true},
   		  	{name: "itemsetno", index: "itemsetno", width: 118, label: "套餐包 ", sortable: true, align: "left", hidden: true},
   		  	{name: "itemsetdescr", index: "itemsetdescr", width: 118, label: "套餐包 ", sortable: true, align: "left", hidden: true},
   		  	{name: "custtypeitempk", index: "custtypeitempk", width: 0.5, label: "套餐材料信息编号", sortable: true, align: "left", hidden: true},
   		  	{name: "chgcusttypeitempk", index: "chgcusttypeitempk", width: 0.5, label: "新套餐材料信息编号", sortable: true, align: "left", hidden: true},
   		  	{name: "algorithm", index: "algorithm", width: 85, label: "算法", sortable:false, align: "left",hidden: true},
   		  	{name: "algorithmdescr", index: "algorithmdescr", width: 85, label: "原算法", sortable:false, align: "left", hidden: true},
   		  	{name: "chgalgorithm", index: "chgalgorithm", width: 85, label: "新算法", sortable:false, align: "left", hidden: true},
   		  	{name: "chgalgorithmdescr", index: "chgalgorithmdescr", width: 85, label: "新算法", sortable:false, align: "left", hidden: true},
   		  	{name: "chgpreplanareadescr", index: "chgpreplanareadescr", width: 80, label: "新空间名称", sortable:false, align: "left", hidden: true},
   		  	{name: "chgpreplanareapk", index: "chgpreplanareapk", width: 60, label: "新空间pk", sortable:false, align: "left", hidden: true},
   		  	{name: "cuttypedescr", index: "cuttypedescr", width: 75, label: "原切割类型", sortable:false, align: "left", hidden: true},
   		  	{name: "cuttype", index: "cuttype", width: 75, label: "原切割类型",  width: 50,sortable:false, align: "left",hidden: true},
   		  	{name: "chgcuttypedescr", index: "chgcuttypedescr", width: 75, label: "新切割类型", sortable:false, align: "left", hidden: true},
   		  	{name: "chgcuttype", index: "chgcuttype", width: 70, label: "新切割类型",  width: 0.5,sortable:false, align: "left",hidden: true},
   		  	{name: "chgfixareadescr", index: "chgfixareadescr", width: 85, label: "新区域名称", sortable: true, align: "left", hidden: true},
   		  	{name: "chgfixareapk", index: "chgfixareapk", width: 127, label: "新区域编号", sortable: true, align: "left", hidden: true},
  		    {name: "doorpk", index: "doorpk", width: 65, label: "doorpk",  width: 0.5,sortable:false, align: "left",hidden: true},
	  		{name: "algorithmper", index: "algorithmper", width: 80, label: "原算法系数", sortable: true, align: "right",hidden: true},
			{name: "algorithmdeduct", index: "algorithmdeduct", width: 80, label: "原算法扣除数", sortable: true, align: "right",hidden: true},
   		],
   		gridComplete: function () {
   			Global.JqGrid.jqGridSelectAll("itemReplaceDataTable",false);
           	var data = $("#itemReplaceDataTable").jqGrid("getRowData",1);
			
           	// 套外材料默认勾选套餐及升级材料
           	if(data.isoutset == "0"){
           		$("#clearPrice").prop("checked",true);
			}
           	
   		},
   		
   	});
   	
   	Global.JqGrid.initJqGrid("ctItemDataTable",{
   		//url:"${ctx}/admin/custTypeItem/goItemJqGrid",
   		//postData: postData,
   		height:270,
   		styleUI: 'Bootstrap',
   		colModel : [
	   		  {name : 'pk',index : 'pk',width : 110,label:'套餐材料信息编号',sortable : true,align : "left",hidden:true},
	   		  {name : 'custtypeitempk',index : 'custtypeitempk',width : 110,label:'套餐材料信息编号',sortable : true,align : "left",hidden:true},
	   		  {name : 'code',index : 'code',width : 100,label:'材料编号',sortable : true,align : "left",hidden:true},
	   	      {name : 'descr',index : 'descr',width : 200,label:'材料名称',sortable : true,align : "left"},
	   	      {name : 'price',index : 'price',width : 60,label:'单价',sortable : true,align : "left"},
	          {name : 'uomdescr',index : 'uomdescr',width : 60,label:'单位',sortable : true,align : "left"},
	          {name: "itemremark", index: "itemremark", width: 150, label: "材料描述", sortable: true, align: "left"},
	          {name : 'remark',index : 'remark',width : 100,label:'材料描述',sortable : true,align : "left",},
			 // {name: "remark", index: "remark", width: 150, label: "升级描述", sortable: true, align: "left"},
	   	      {name : 'itemtype1descr',index : 'itemtype1descr',width : 100,label:'材料类型1',sortable : true,align : "left",hidden:true},
	   	      {name : 'itemtype2descr',index : 'itemtype2descr',width : 100,label:'材料类型2',sortable : true,align : "left",hidden:true},
	   	      {name : 'itemtype3descr',index : 'itemtype3descr',width : 100,label:'材料类型3',sortable : true,align : "left"},
	   	      {name : 'itemtype2',index : 'itemtype2',width : 100,label:'材料类型2',sortable : true,align : "left",},
	   	      {name : 'splcodedescr',index : 'splcodedescr',width : 100,label:'供应商',sortable : true,align : "left"},
	   	      {name : 'model',index : 'model',width : 100,label:'型号',sortable : true,align : "left"},
	          {name : 'color',index : 'color',width : 100,label:'颜色',sortable : true,align : "left",hidden:true},
	   	      {name : 'sizedescr',index : 'sizedescr',width : 100,label:'规格',sortable : true,align : "left"},
	   	      {name : 'isfixpricedescr',index : 'isfixpricedescr',width : 100,label:'是否固定价',sortable : true,align : "left"},
	   	      {name : 'barcode',index : 'barcode',width : 100,label:'条码',sortable : true,align : "left",hidden:true},
	   	      {name : 'sqldescr',index : 'sqldescr',width : 100,label:'品牌',sortable : true,align : "left",hidden:true},
	   	      {name : 'marketprice',index : 'marketprice',width : 100,label:'市场价',sortable : true,align : "left",hidden:true},
	   	      {name : 'supplcode',index : 'supplcode',width : 100,label:'供应商代码',sortable : true,align : "left",hidden:true},
	          {name : 'allqty',index : 'allqty',width : 100,label:'库存数量',sortable : true,align : "left",hidden:true},
	          {name : 'cost',index : 'cost',width : 100,label:'进价',sortable : true,align : "left",hidden:true},
	          {name : 'avgcostdescr',index : 'avgcostdescr',width : 100,label:'移动成本',sortable : true,align : "left",hidden:true},
	  		  {name: 'commitype', index: 'commitype', width: 84, label: 'commitype', sortable: true, align: "left", hidden: true},
	          {name : 'projectcost',index : 'projectcost',width : 100,label:'升级项目经理结算价',sortable : true,align : "left",hidden:true},
	          {name : 'oldprojectcost',index : 'oldprojectcost',width : 100,label:'原项目经理结算价',sortable : true,align : "left",hidden:true},
	          {name : 'fixareatypedescr',index : 'fixareatypedescr',width : 100,label:'适用区域',sortable : true,align : "left",}
           
           ],
           loadComplete: function(){
        	   if ($("#clearPrice").is(':checked')){
  					$("#ctItemDataTable").jqGrid('showCol', "itemremark");
   					$("#ctItemDataTable").setLabel('remark', null,"升级描述","材料描述");
   					$("#jqgh_ctItemDataTable_remark").text("升级描述");
            	} else {
   					$("#ctItemDataTable").jqGrid('hideCol', "itemremark");
   					$("#jqgh_ctItemDataTable_remark").text("材料描述");
            	}
           },
           ondblClickRow:function(rowid,iRow,iCol,e){
           	var oldData =selectDataTableRow("itemReplaceDataTable");
           	var newData = $("#ctItemDataTable").jqGrid("getRowData",rowid);
           	
           	if(!oldData){
           		art.dialog({
           			content:"请选择被替换材料"
           		});
           		return;
           	}
   			oldData.chgitemcode = newData.code;
   			oldData.chgitemdescr = newData.descr;
   			oldData.chguomdescr = newData.uomdescr;
   			oldData.chgunitprice = newData.price;
   			oldData.chgitemtype2descr = newData.itemtype2descr;
   			oldData.chgitemtype2 = newData.itemtype2;
   			oldData.chgitemtype3descr = newData.itemtype3descr;
   		    chgunitprice = newData.price;
   		    oldData.chgcost = newData.cost;
   		    oldData.chgallcost = myRound(newData.cost * oldData.chgqty, 4);
   		    oldData.chgmarketprice = newData.marketprice;
   		    oldData.chgsqlcodedescr = newData.sqldescr;
   		    oldData.chgremark = newData.remark;
   		    oldData.chgprocesscost = 0.0;
   		    oldData.chgqty = 0.0;
   		    oldData.chgpreplanareapk = oldData.preplanareapk;
   		    oldData.chgpreplanareadescr = oldData.preplanareadescr;
   		    oldData.chgfixareapk= oldData.fixareapk;
   		    oldData.chgfixareadescr = oldData.fixareadescr;
			oldData.oldalgorithmdescr = oldData.algorithmdescr;
			if ($("#clearPrice").is(':checked')){
				oldData.chgisoutset='0';
		   		oldData.chgisoutsetdescr ='否';
        	}else{
        		oldData.chgisoutset='1';
		   		oldData.chgisoutsetdescr ='是';
        	}
			if(oldData.oldalgorithmdescr == "" || newData.itemtype2 == "028"){
				oldData.chgqty = oldData.qty
			}
			
   		    var newAlgorithm = "";
   		 	if(oldData.algorithm){
	   		 	newAlgorithm = checkAlgorithm(oldData.algorithm,newData.code);
   		    }
   			var newCutType = "";
   			if(oldData.cuttype){
   				newCutType = checkCutType(oldData.cuttype,newData.code);
   			}
   			
   			if(newCutType != ""){
   				oldData.chgcuttypedescr = oldData.cuttypedescr;
   				oldData.chgcuttype= oldData.cuttype;
   			}
   			
			oldData.chglineamount = myRound(myRound(myRound(oldData.chgqty * oldData.chgunitprice,4) * oldData.markup / 100) + myRound(oldData.chgprocesscost));
			oldData.chgbeflineamount = myRound(parseFloat(oldData.chgqty)* parseFloat(oldData.chgunitprice),4);
		    oldData.chgtmplineamount =  myRound(myRound(oldData.chgqty * oldData.chgunitprice, 4) * oldData.markup / 100);
   		    if(newAlgorithm != ""){
   		    	var datas=$("#dataForm").jsonForm();
	   				datas.custCode="${itemChg.custCode}";
	   				datas.itemCode=newData.code;
	   				datas.algorithm = newAlgorithm;
	   				datas.cutType=newCutType; 
	   				datas.doorPK=oldData.doorpk;
	   				datas.prePlanAreaPK=oldData.preplanareapk;
	   				datas.custType = "";
	   				if ($("#clearPrice").is(':checked')){
	   					datas.isoutset = "0";	
	   	        	}else{
	   	        		datas.isoutset = "1";
	   	        	}	
	   			$.ajax({
	   				url:"${ctx}/admin/itemPlan/getPrePlanQty",
	   				type:"post",
	   		        dataType:"json",
	   		        async:false,
	   		        data:datas,
	   				cache: false,
	   				error:function(obj){
	   					showAjaxHtml({"hidden": false, "msg": '获取数据出错~'});
	   				},
	   				success:function(obj){
	   					if (obj){
	   						oldData.chgprocesscost = obj.processcost;
	   						oldData.chgqty = obj.qty;
							oldData.chglineamount =	myRound(myRound(myRound(oldData.chgqty * oldData.chgunitprice,4) * oldData.markup / 100) + myRound(obj.processcost))
	   						oldData.chgbeflineamount = myRound(parseFloat(oldData.chgqty)* parseFloat(oldData.chgunitprice),4);
		    				oldData.chgtmplineamount =  myRound(myRound(oldData.chgqty * oldData.chgunitprice, 4) * oldData.markup / 100);
	   					}
	   				}
	   			});
   		    }
   		    if (newData.pk) oldData.chgcusttypeitempk = newData.pk;
   	        var rowNum = $("#itemReplace_dataTable").jqGrid('getGridParam', 'records');
			
   	        if(newData.fixareatypedescr != ""){
   	        	$.ajax({
	            	url: '${ctx}/admin/custTypeItem/checkFixAreaType',
	            	data:{custType:"${itemChg.custType}",prePlanAreaPk:oldData.preplanareapk,itemCode:newData.code},
	            	type: "post",
	    			dataType: "json",
	            	cache: false,
	            	success: function (obj) {
	                	if (obj) {
	                		$("#itemReplace_dataTable").addRowData(myRound(rowNum)+1, oldData, "last");
	        	   	     	$("#itemReplace_dataTable").trigger("reloadGrid");
	                  	} 
	                	else{
	                		art.dialog({
	             				content: "该材料适用类型为："+newData.fixareatypedescr,
	             			});
	                		return;
	                	}
	                }
	            });   	        	
   	        } else {
	   	        $("#itemReplace_dataTable").addRowData(myRound(rowNum)+1, oldData, "last");
	   	     	$("#itemReplace_dataTable").trigger("reloadGrid");
   	        }
   	        
   	     
		}
   	});

      $("#content-list").show();	
      $("#content-list2").hide();
      if ('${itemChg.itemType1}'.trim() == 'JC') {
        $("#dataTable").setGridParam().showCol("intproddescr").trigger("reloadGrid");
      }
      if ('${itemChg.costRight}'.trim() == '1') {
        $("#dataTable").setGridParam().showCol("cost").trigger("reloadGrid");
      }
      if ('${itemChg.itemType1}'.trim() == 'RZ') {
        $("#dataTable").setGridParam().showCol("itemsetdescr").trigger("reloadGrid");
      }
      //保存
      $("#saveBtn").on("click", function () {
        if($("#itemReplace_dataTable").jqGrid('getGridParam', 'records')>0){
        	art.dialog({
          		content: "已选取替换材料,请先删除！"
        	});
        	  return;
        }
        var ids = $("#dataTable").jqGrid('getGridParam', 'selarrrow');
        if (ids.length == 0) {
          Global.Dialog.infoDialog("请选择要增减的材料！");
          return;
        }
        
        var returnData = {};
        var selectRows = [];
        $.each(ids, function (k, id) {
          var row = $("#dataTable").jqGrid('getRowData', id);
          row.preqty = row.qty;
          row.qty = parseFloat(row.sendqty) - parseFloat(row.qty);
          row.tmplineamount = myRound(myRound(row.qty * row.unitprice, 4) * row.markup / 100);
          if (row.qty < 0) row.processcost = -row.processcost;
          row.lineamount = myRound(parseFloat(row.processcost) + parseFloat(row.tmplineamount));
          row.remarks = row.remark;
          selectRows.push(row);
        });
        returnData.selectRows = selectRows;
        Global.Dialog.returnData = returnData;
        closeWin();
      });

      //全选
      $("#selectallBtn").on("click", function () {
        Global.JqGrid.jqGridSelectAll("dataTable", true);
      });

      //不选
      $("#unselectallBtn").on("click", function () {
        Global.JqGrid.jqGridSelectAll("dataTable", false);
      });
    });
	function goto_query(){
	    var sReqpk=arrReqpk;;
	    if($("#itemReplace_dataTable").jqGrid('getGridParam', 'records')>0){
	    	 var rowData=$('#itemReplace_dataTable').jqGrid("getRowData");
			 $.each(rowData,function(k,v){
			 	 sReqpk.push(v.reqpk);
			 });
		}
		var url="";
		if(id==""||id==null){
			url ="${ctx}/admin/itemChg/goTransActionJqGrid?itemType2="+$.trim($("#itemType2").val())+"&custCode=${itemChg.custCode}&itemType1=${itemChg.itemType1}&isService=${itemChg.isService}&isCupboard=${itemChg.isCupboard}&unSelected=" + sReqpk ;
		}else{
			url ="${ctx}/admin/itemChg/goTransActionJqGrid?itemType2="+$.trim($("#itemType2").val())+"&custCode=${itemChg.custCode}&itemType1=${itemChg.itemType1}&isService=${itemChg.isService}&isCupboard=${itemChg.isCupboard}&unSelected=" + sReqpk + "&onlyShowNotEqual=" + $('#' + id).val();
		}
		$("#dataTable").jqGrid("setGridParam", {
        url: url,
        page: 1,
      }).trigger("reloadGrid");
	}
	
    function changeShow(obj) {
      id = $(obj).attr('id');
      if ($(obj).is(':checked')) {
        $('#' + id).val('1');
      } else {
        $('#' + id).val('0');
      }
      var sReqpk=arrReqpk;
      if($("#itemReplace_dataTable").jqGrid('getGridParam', 'records')>0){
    	 var rowData=$('#itemReplace_dataTable').jqGrid("getRowData");
		 $.each(rowData,function(k,v){
		 	sReqpk.push(v.reqpk);
		 });
	  }
      $("#dataTable").jqGrid("setGridParam", {
        url: "${ctx}/admin/itemChg/goTransActionJqGrid?itemType2="+$.trim($("#itemType2").val())+"&custCode=${itemChg.custCode}&itemType1=${itemChg.itemType1}&isService=${itemChg.isService}&isCupboard=${itemChg.isCupboard}&unSelected=" + sReqpk + "&onlyShowNotEqual=" + $('#' + id).val(),
        page: 1,
      }).trigger("reloadGrid");
    }
    function itemReplace(){ 
    	/*
    	var ids = $("#dataTable").jqGrid('getGridParam', 'selarrrow');
        if (ids.length == 0&&$("#itemReplace_dataTable").jqGrid('getGridParam', 'records')==0) {
          Global.Dialog.infoDialog("请选择要替换的材料！");
          return;
        }
    	$("#content-list").hide();
    	$("#content-list2").show();	
    	$("#page_form").hide();	
    	$("#selectallBtn").hide();
    	$("#saveBtn").hide();
    	$("#unselectallBtn").hide();
    	$("#itemReplaceBtn").hide();
    	$("#itemChgBtn").show();	 
    	$("#continueAddBtn").show();
        $("#confirmBtn").show(); 
        $("#deleteBtn").show();
        if(ids.length > 0){
        	$.each(ids,function(k, id){  
	    		var row = $("#dataTable").jqGrid('getRowData', id);
	    		row.chgqty=row.qty;
	    		row.chgprocesscost=0;
	    		row.chgalgorithm=row.algorithm;
	    		row.chgalgorithmdescr=row.algorithmdescr;
	    		row.cuttype=row.cuttype;
	    		row.cuttypedescr=row.cuttypedescr;
	    		row.chgpreplanareadescr=row.preplanareadescr;
	    		row.chgpreplanareapk=row.preplanareapk;
	    		row.chgfixareapk=row.fixareapk;
	   			row.chgfixareadescr=row.fixareadescr;
	   			row.chgintprodpk=row.intprodpk;
	   			row.chgintproddescr=row.intproddescr;
			 	$("#itemReplace_dataTable").addRowData(itemReplaceRowId+1,row, 'last');
			 	itemReplaceRowId++;
			});
			var len = ids.length;  
			for(var i = 0;i < len ;i++) {  
				$("#dataTable").jqGrid("delRowData", ids[0]); 
			}  	
	        
	    }	*/
	    var ids = $("#dataTable").jqGrid('getGridParam', 'selarrrow');
        if (ids.length == 0 && $("#itemReplace_dataTable").jqGrid('getGridParam', 'records')==0) {
          Global.Dialog.infoDialog("请选择要替换的材料！");
          return;
        }
	    $("#confirmBtn").show(); 
        $("#deleteBtn").show();
        $("#saveBtn").hide();
        $("#selectallBtn").hide();
        $("#unselectallBtn").hide();        
        $("#itemReplaceBtn").hide();
	    $("#content-list").hide();
	    $("#content-list2").show();
	    $("#content-list3").show();
	    $("#content-list4").show();
	    $("#clearPrice").show();
	    $("#clearPrice_span").show();
	    $("#descr").show();
	    $("#search").show();
	    $("#descr_label").show();
	    $("#page_form").hide();
	    $("#continueAddBtn").show();

	    var param= Global.JqGrid.selectToJson("dataTable");
    	var detailJson = JSON.parse(param.detailJson);
        var rowNum = $("#itemReplaceDataTable").jqGrid('getGridParam', 'records');

    	if(detailJson.length > 0){
	   		 $.each(detailJson, function (k, v) {
	   			$("#itemReplaceDataTable").addRowData(myRound(k)+myRound(rowNum)+1, v );
	   		});
	   	}
    	$("#itemReplaceDataTable").trigger("reloadGrid");
    	
    	
    	var ids = $("#dataTable").jqGrid('getGridParam', 'selarrrow');
    	var len = ids.length;  
		for(var i = 0;i < len ;i++) {  
			$("#dataTable").jqGrid("delRowData", ids[0]); 
		}  
   }
   function continueAdd(){
    	$("#content-list").show();
    	$("#content-list2").hide();	
    	$("#content-list3").hide();
	    $("#content-list4").hide();
    	$("#page_form").show();	
    	$("#selectallBtn").show();
    	$("#unselectallBtn").show();
    	$("#itemReplaceBtn").show(); 
    	$("#itemChgBtn").hide();	 
    	$("#continueAddBtn").hide();
        $("#confirmBtn").hide();
        $("#deleteBtn").hide(); 
        $("#descr").hide();
	    $("#search").hide();
	    $("#descr_label").hide();
        if($("#itemReplace_dataTable").jqGrid('getGridParam', 'records')==0){
        	$("#saveBtn").show();
        }	
    }
	function  itemChg(){
        var rowId = $('#itemReplace_dataTable').jqGrid('getGridParam', 'selrow');
        if (rowId) {
            var reqPk = $('#itemReplace_dataTable').getCell(rowId, "reqpk");
            var itemCode = $('#itemReplace_dataTable').getCell(rowId, "itemcode");    
	        Global.Dialog.showDialog("itemChg_detailViewEdit", {
	            title: "已有项新增--替换",
	            url: "${ctx}/admin/itemChg/goItemChg_itemReplace?itemType1=${itemChg.itemType1}&custCode=${itemChg.custCode}&isOutSet=${itemChg.isOutSet}&isService=${itemChg.isService}&rowId="+ rowId + "&reqPk=" + reqPk + "&itemCode=" + itemCode,
	            height: 500,
	            width: 1000,
	            resizable: false,
	            returnFun: function (data) {
				    $('#itemReplace_dataTable').setRowData(rowId,data);
                }
           });
       } else {
          art.dialog({
              content: "请选择一条记录"
          });
       }
    }
    
   function del(){
      var rowId = $('#itemReplaceDataTable').jqGrid('getGridParam', 'selrow');
      if (rowId) {
      	  art.dialog({
	          content: '删除该记录？',
	          lock: true,
	          ok: function () {
	          	   var row = $("#itemReplaceDataTable").jqGrid('getRowData', rowId);
	         	   $('#dataTable').addRowData($('#dataTable').jqGrid('getGridParam','records')+1,row, 'last');
	         	   //Global.JqGrid.addRowData("dataTable",row); 
        	   	   var rowDatas=$('#itemReplace_dataTable').jqGrid("getRowData");
				   var reqpk = row.reqpk;
				   if(rowDatas.length>0){
					   var len = rowDatas.length;
					   for(var i = 0; i < len; i++){
						   if(rowDatas[i].reqpk == row.reqpk){
				         	   $('#itemReplace_dataTable').delRowData(myRound(i)+1); 
						   }
					   }
					   
				   }
	         	   
	         	   $('#itemReplaceDataTable').delRowData(rowId); 
	          },
	          cancel: function () {
	             return true;
	          }
        });
      } else {
        art.dialog({
          content: "请选择一条记录"
        });
      }
   } 
   //确认
   function confirm(){ 		
	 if($("#itemReplace_dataTable").jqGrid('getGridParam', 'records')>0){
	 	 var rowDatas=$('#itemReplace_dataTable').jqGrid("getRowData");
	 	 var replacedDatas=$('#itemReplaceDataTable').jqGrid("getRowData");
	 	 
	 	 var returnData = {};
	     var selectRows = [];
	     var rowData={};
	     var flag=false;
	     var content = "";
	     $.each(replacedDatas,function(k,v){
	    	 flag = true;
	    	 for(var i = 0; i<rowDatas.length; i++){
				 if(rowDatas[i].reqpk == v.reqpk){
					 flag = false;
					 break; 
				 }
			 }    
	    	 if(flag){
	    		 content ="材料名称："+ v.itemdescr;
	    		 return false;
	    	 }
	     });
	     
		 $.each(rowDatas,function(k,v){

			 if(v.chgitemcode==""){
	          	flag=true;
	          	return;
	         }else{
	         	rowData={};
	         	v.refchgreqpk=v.reqpk;
	         	v.preqty = v.qty;
                v.qty = parseFloat(v.sendqty) - parseFloat(v.qty);
                v.tmplineamount = myRound(myRound(v.qty * v.unitprice, 4) * v.markup / 100);
                if (v.qty < 0) v.processcost = -v.processcost;
          		v.lineamount = myRound(parseFloat(v.processcost) + parseFloat(v.tmplineamount));
          	  	v.remarks = v.remark;
          	  	
          	  	if(selectRows.length > 0){
          	  		var hasAdd = false;
		         	for(var i = 0;i<selectRows.length;i++){
						if(selectRows[i].reqpk == v.reqpk){
							hasAdd = true;
							break;
						}
		         	}
		         	if(!hasAdd){
		         		selectRows.unshift(v);
		         	}
          	  	}else{
	         		selectRows.unshift(v);
          	  	}
          	  	rowData.itemcode=v.chgitemcode;
	         	rowData.itemdescr=v.chgitemdescr;
	 			rowData.itemtype2descr=v.chgitemtype2descr;
	         	rowData.itemtype2=v.chgitemtype2;
	         	rowData.itemtype3descr=v.chgitemtype3descr;
				rowData.itemtype3descr=v.chgitemtype3descr;
		 		rowData.qty=v.chgqty;
		 		rowData.uomdescr=v.chguomdescr; 
				rowData.beflineamount=v.chgbeflineamount;
				rowData.sendqty=0;
				rowData.cost=v.chgcost;
				rowData.unitprice=v.chgunitprice;
		  		rowData.tmplineamount=v.chgtmplineamount;
				rowData.processcost=v.chgprocesscost;
				rowData.lineamount=myRound(v.chglineamount);
				rowData.remarks=v.chgremark;
				rowData.reqpk="";
				rowData.refchgreqpk=v.reqpk;
				rowData.preqty=0;
				rowData.custtypeitempk=v.chgcusttypeitempk;
				rowData.algorithm=v.algorithm;
				rowData.algorithmdescr=v.algorithmdescr;
		 		rowData.cuttypedescr=v.chgcuttypedescr;
				rowData.cuttype=v.chgcuttype;
				rowData.fixareadescr=v.chgfixareadescr;
				rowData.fixareapk=v.chgfixareapk;
				rowData.intprodpk=v.intprodpk;
				rowData.intproddescr=v.intproddescr;
				rowData.markup=v.markup;
				rowData.preplanareadescr=v.chgpreplanareadescr;
				rowData.preplanareapk=v.chgpreplanareapk;
				rowData.isoutsetdescr=v.chgisoutsetdescr;
				rowData.isoutset=v.chgisoutset;
				rowData.algorithmper=1.0;
				rowData.algorithmdeduct=0.0;
	         	selectRows.push(rowData);
	         } 
		  });
		  
		  if(flag){
		  	  art.dialog({
	          		content: "存在未替换材料,"+content,
	          });
	          return;
		  }
		  returnData.selectRows = selectRows;
			
     	  Global.Dialog.returnData = returnData;
          closeWin();	
	 }else{
	 	art.dialog({
          	 content: "请先选择要替换的材料"
        });
	 }

   }  
    
   function searchItems(){
	   var rowId = $("#itemReplaceDataTable").jqGrid('getGridParam', 'selrow');
	   var rowData = $("#itemReplaceDataTable").jqGrid("getRowData",rowId);
      	var url ="${ctx}/admin/custTypeItem/goItemJqGrid"; 
      	if ($("#clearPrice").is(':checked')){
      		url = "${ctx}/admin/custTypeItem/goItemSelectJqGrid"
      	} else {
      		url = "${ctx}/admin/item/goItemSelectJqGrid";
      	}

      	$("#ctItemDataTable").jqGrid("setGridParam",{
      		url: url,
      		postData:{'itemType1': '${itemChg.itemType1}',
      					'descr':$("#descr").val(),
						'custCode': '${itemChg.custCode}',
						custType :'${itemChg.custType}',
						prePlanAreaPk:rowData.preplanareapk
					},page:1,sortname:''}).trigger("reloadGrid");
   }
   
   function algorithmClick(e){
		var rowid = $("#itemReplace_dataTable").getGridParam("selrow");
	    var rowData = $("#itemReplace_dataTable").jqGrid('getRowData', $(e.target).attr("rowid"));
	    $("#itemReplace_dataTable").setRowData($(e.target).attr("rowid"), {
	    	algorithm:$(e.target).val(),
	    });
	    changeAlgorithm($(e.target).attr("rowid"), $(e.target).val(),"algorithm");
	}
   
	//根据算法计算
	function changeAlgorithm(id,val,type){
		var ret = selectDataTableRow("itemReplace_dataTable");
		var datas=$("#dataForm").jsonForm();
			datas.custCode="${itemChg.custCode}";
			datas.itemCode=ret.chgitemcode;
			
			if(type=="algorithm"){
				datas.algorithm = val;
				datas.cutType=ret.cuttype; 
			} else {
				datas.algorithm = ret.algorithm;
				datas.cutType = val; 
				datas.type = '2';
				datas.qty = ret.chgqty;
			}

			datas.doorPK=ret.doorpk;
			datas.prePlanAreaPK=ret.preplanareapk;
			datas.isOutSet=ret.chgisoutset;
		$.ajax({
			url:"${ctx}/admin/itemPlan/getPrePlanQty",
			type:"post",
	        dataType:"json",
	        async:false,
	        data:datas,
			cache: false,
			error:function(obj){
				showAjaxHtml({"hidden": false, "msg": '获取数据出错~'});
			},
			success:function(obj){
				if (obj){
					console.log(obj);
					if(datas.type == "2"){
						$("#itemReplace_dataTable").setRowData(id, {
							chgprocesscost:obj.processcost,
			              	chglineamount: myRound(myRound(myRound(ret.chgqty * ret.chgunitprice, 4) * ret.markup / 100) + myRound(obj.processcost))
						});
					} else {
						$("#itemReplace_dataTable").setRowData(id, {
							chgprocesscost:obj.processcost,
							chgqty:obj.qty
						}); 
					 	$("#itemReplace_dataTable").setCell(id, 'chgbeflineamount', myRound(obj.qty * ret.chgunitprice, 4));
		              	$("#itemReplace_dataTable").setCell(id, 'chgtmplineamount', myRound(myRound(obj.qty * ret.chgunitprice, 4) * ret.markup / 100));
		              	$("#itemReplace_dataTable").setCell(id, 'chglineamount', myRound(myRound(myRound(obj.qty * ret.chgunitprice, 4) * ret.markup / 100) + myRound(obj.processcost)));
		              	$("#itemReplace_dataTable").footerData('set', {"qty": $("#itemReplace_dataTable").getCol('qty', false, 'sum')});
				
					}
				}
			}
		});	
	}
   
    function checkAlgorithm(algorithm,itemcode){
	   if(algorithm && algorithm != ""){
		var setAlgorithm = "";
		$.ajax({
			url : '${ctx}/admin/item/getAlgorithm',
			type : 'post',
			data : {
				'code' :itemcode,
			},
			async:false,
			dataType : 'json',
			cache : false,
			error : function(obj) {
				showAjaxHtml({
					"hidden" : false,
					"msg" : '加载算法数据出错~'
				});
				
			},
			success : function(obj) {
				if(obj && obj.length>0){
					for(var i = 0; i<obj.length; i++){
						if(algorithm == obj[i].Code){
							setAlgorithm = algorithm;
							break;
						}
					}
				}
			}
		 });
		 return setAlgorithm;
	   }
	}
   
    function checkCutType(cutType,itemCode){
    	var setCutType =""; 
    	if(cutType && cutType!=""){
			$.ajax({
				url : '${ctx}/admin/prePlanTempDetail/getQtyByCutType',
				type : 'post',
				data : {
					'itemCode' :itemCode
				},
				async:false,
				dataType : 'json',
				cache : false,
				error : function(obj) {
					showAjaxHtml({
						"hidden" : false,
						"msg" : '匹配数据出错~'
					});
					
				},
				success : function(obj) {
					if(obj && obj.length>0){
						for(var i = 0; i<obj.length; i++){
							if(cutType == obj[i].Code){
								setCutType = cutType;
								break;
							}
						}
					}
				}
			});
			return setCutType;
    	}
	 }
    
    function deleteBtn(v, x, r) {
       	return "<button type='button' class='btn btn-system btn-xs' onclick='deleteRow("+x.rowId+");'>删除</button>";
	}
       
    function deleteRow(rowId) {
        var nextId = $("#itemReplace_dataTable tr[id=" + rowId + "]").next().attr("id");
        var preId = $("#itemReplace_dataTable tr[id=" + rowId + "]").prev().attr("id");
        $('#itemReplace_dataTable').delRowData(rowId);
        if ($("#itemReplace_dataTable").jqGrid('getGridParam', 'records') > 0) {
          if (nextId) {
            relocate(nextId, 'itemReplace_dataTable');
            $("#itemReplace_dataTable tr[id=" + nextId + "] button").focus();
          }
          else {
            relocate(preId, 'itemReplace_dataTable');
            $("#itemReplace_dataTable tr[id=" + preId + "] button").focus();
          }
        }
	}
    
    function cuttypeClick(e){
    	var rowid = $("#itemReplace_dataTable").getGridParam("selrow");
        var rowData = $("#itemReplace_dataTable").jqGrid('getRowData', $(e.target).attr("rowid"));
        $("#itemReplace_dataTable").setRowData($(e.target).attr("rowid"), {
        	cuttype:$(e.target).val(),
        });
        // 选择切割方式,套餐内默认不自动计算其它费用
    	if(rowData.chgisoutset=='0'){
    		return;
    	}
        changeAlgorithm($(e.target).attr("rowid"),$(e.target).val(),"cutType");
    }
   
  </script>
</head>

<body>
<div class="body-box-list">
  <div class="panel panel-system">
    <div class="panel-body">
      <div class="btn-group-xs">
        <button type="button" class="btn btn-system " id="saveBtn">保存</button>
           <button type="button" class="btn btn-system " id="itemReplaceBtn" onclick="itemReplace()" >材料替换</button>
        <button type="button" class="btn btn-system " id="selectallBtn">全选</button>
        <button type="button" class="btn btn-system " id="unselectallBtn">不选</button>
        <button type="button" class="btn btn-system " id="confirmBtn" onclick="confirm()" hidden="true">确认</button>
        <button type="button" class="btn btn-system " id="itemChgBtn" onclick="itemChg()" >选择替换材料</button>
        <button type="button" class="btn btn-system " onclick="closeWin(false)">关闭</button>
		
		<input type="checkbox" id="clearPrice" onclick="searchItems()" name="clearPrice" style="width:15px;height:15px;margin-left:10px;margin-top:-2px"/><span id="clearPrice_span">套餐及升级材料</span>
      	
      	<label style="margin-left:100px;font-weight:normal" id="descr_label">材料名称</label>
      	<input type="text" id="descr" style="width:160px;height:22px;border-radius:2px"/>
      	<button type="button" class="btn btn-system btn-sm"	onclick="searchItems();" id="search" style="margin-top:-4px">查询</button>
      	
      </div>
      
    </div>
  </div>

  <div id="page_form" class="query-form">
    <form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
      <ul class="ul-form">
        <li>
          <label>材料类型2</label>
          <house:DictMulitSelect id="itemType2" dictCode="" sql=" select rtrim(code)code,descr from tItemType2 where Code in(
									Select i.ItemType2 from tItemReq t1  
									left outer join tItem i on t1.ItemCode=i.Code   
									 where t1.qty <> '0' AND  t1.expired='F'    
									and t1.CustCode='${itemChg.custCode}'  and t1.ItemType1='${itemChg.itemType1}'
									  and t1.IsService='${itemChg.isService}'  )" onCheck="goto_query()"
                                 sqlLableKey="descr" sqlValueKey="code"></house:DictMulitSelect>
        </li>
        <li style="with: 150%;">
          <label></label>
          <input id="onlyShowNotEqual" name="onlyShowNotEqual" type="checkbox" onclick="changeShow(this)"/>只显示发货不等需求
        </li>

      </ul>
    </form>
  </div>

  <div id="content-list">
    <table id="dataTable"></table>
  </div>
  
  <div id="content-list3" style="width:74%;float:right" >
    <table id="ctItemDataTable"></table>
  </div>
  <div id="content-list4" style="width:26%" >
	<table id="itemReplaceDataTable"></table>
	<div class="panel panel-system" style="margin-left:-8px" >
		<div class="panel-body">
			<div class="btn-group-xs" style="background-color: rgb(244,244,244)">
		    	<button type="button" id="continueAddBtn" class="btn btn-system" style="padding:1px;width:60px;margin-left: 15px"  onclick="continueAdd()">继续添加</button>
		    	<button type="button" id="deleteBtn" class="btn btn-system" style="padding:1px;width:30px;margin-left: 0px"  onclick="del()">删除</button>
			</div>
		</div>
	</div>
  </div>
  <div id="content-list2" style="padding-top:10px" >
    <table id="itemReplace_dataTable"></table>
  </div>
  
</div>

</body>
</html>


