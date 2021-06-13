<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  	<title>材料批次管理--快速新增</title>
  	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8"/>
  	<META HTTP-EQUIV="pragma" CONTENT="no-cache"/>
  	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"/>
  	<META HTTP-EQUIV="expires" CONTENT="0"/>
  	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge"/>
  	<%@ include file="/commons/jsp/common.jsp" %>
  	<script type="text/javascript">
    var fixAreaPk = 0;
    var intProdPk = 0;
    var leftOffset;
    var isAddFixArea = false;
    function saveData() {
    var ids=$("#itemDetailDataTable").jqGrid('getDataIDs');
			if(ids.length == 0){
				art.dialog({
					content:"请选择数据后保存"
				});				
				return ;
			}
			var selectRows = [];
			$.each(ids,function(i,id){
				var rowData = $("#itemDetailDataTable").jqGrid("getRowData", id);
				//保存前设置最后修改人,修改时间,操作标志
				rowData['lastupdatedby']='1';
				rowData['lastupdate']=new Date().getTime();
				rowData['actionlog']='ADD';
				selectRows.push(rowData);
			});
			Global.Dialog.returnData = selectRows;
      	//Global.Dialog.returnData = $("#itemDetailDataTable").jqGrid("getRowData");
    }
    function doSave() {
      	if ($("#itemDetailDataTable").jqGrid("getGridParam", "records") > 0)  saveData();
      	closeWin();
    }
    function closeAddUi() {
      	if ($("#itemDetailDataTable").jqGrid("getGridParam", "records") > 0) {
        	art.dialog({
          		content: "是否保存被更改的数据?",
          		ok: function () {
            		saveData();
            		closeWin();
          		},
          		cancel: function () {
            		closeWin();
          		}
        	});
        	return;
      	}
      	closeWin();
    }
     function reloadIntProd() {
      	intProdPk = 0;
      	$("#intProdPk").val(intProdPk);
      	$("#intProdDescr").val("");
    } 
    function goto_query(table, param) {
      	fixAreaPk = 0;
      	$("#fixAreaPk").val(fixAreaPk);
      	$("#fixAreaDescr").val("");
      	$("#" + table).jqGrid("setGridParam", {postData: param, page: 1}).trigger("reloadGrid");
    } 
    
    function deleteBtn(v, x, r) {
      	return "<button type=\"button\" class=\"btn btn-system btn-xs\" onclick=\"deleteRow('" + x.rowId + "');\">删除</button>";
    }
    function deleteRow(rowId) {
 		art.dialog({
			 content:"是否确认要删除",
			 lock: true,
			 width: 200,
			 height: 100,
			 ok: function () {
				Global.JqGrid.delRowData("itemDetailDataTable",rowId);
			},
			cancel: function () {
					return true;
			}
		});
    }
    
     function enterClick(e, type, d) {
      	if (e.keyCode == 13) {
        	switch (type) {
          	case "item":
            	search();
            	break;
          	case "fixAreaSearch":
            	goto_query("dataTable", {"itcodedescr": d.value});
            	break;
          	case "fixAreaAdd":
            	add(d);
            	break;
          	case "fixAreaEdit":
            	update(d);
            	break;
          	case "fixAreaInsert":
            	insert(d);
            	break;
        	}
      	}
    }
    function showValue(e) {
      	var ret = selectDataTableRow();
      	if (ret) {
        	e.value = ret.Descr;
      	}
    }
    function scale(opt) {
      	switch (opt) {
	        case "big":
	          	top.$("#dialog_quickSave").parent().css({"width": "1380px", "left": leftOffset});
	          	$("#" + opt).hide();
	          	$("#small").show();
	          	break;
	        default:
	          	leftOffset = top.$("#dialog_quickSave").parent().css("left");
	          	top.$("#dialog_quickSave").parent().css({"width": "60%", "left": "40%"});
	          	$("#" + opt).hide();
	          	$("#big").show();
	          	break;
      	}
    } 
 	</script>
</head>
<body>
<div>
  	<div class="query-form">
	    <form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
	    <input type="hidden" id="expired" name="expired" value="${baseItemChg.expired}"/>
	    <input type="hidden" id="fixAreaPk" name="fixAreaPk"/>
	    <input type="hidden" id="intProdPk" name="intProdPk"/>
	    <input type="hidden" id="fixAreaDescr" name="fixAreaDescr"/>
	    <input type="hidden" id="intProdDescr" name="intProdDescr"/>
	    <input type="hidden" id="rowNo" name="rowNo" value="1"/>
	    <ul class="ul-form">
	        <li>
	          	<button onclick="doSave();" class="btn btn-system btn-sm" type="button">保存</button>
	        </li>
	        <li>
	          	<button onclick="closeAddUi();" class="btn btn-system btn-sm" type="button">退出</button>
	        </li>
	        			<li><label>显示已添加材料</label> <input type="checkbox" id="isAdded"
							name="isAdded">
						</li>
	  <li>
          <label>材料编码</label>
          <input type="text" id="code" name="code" value="${item.code}" onkeydown="enterClick(window.event,'item')"/>
        </li>
        <li>
          <label>材料名称</label>
          <input type="text" id="itemDescr" name="itemDescr" value="${item.descr}"
                 onkeydown="enterClick(window.event,'item')"/>
        </li>
	        <li>
	          	<button onclick="search();" class="btn btn-system btn-sm" type="button">查询</button>
	        </li>
		</ul>
		</form>
  	</div>
  
	<div style="width:1200px;">
    	<div style="width: 120px;height: 315px;float: left;padding-left: 2px;">
      		<div style="text-align: center;border: 1px solid #DDDDDD;border-bottom:none;">
        		<strong>材料类型</strong>
      		</div>
      		<iframe id="treeFrame" style="width: 100%;height: 100%;border-left: 1px solid #DDDDDD;border-top:none;"
                  src="${ctx}/admin/itemType2/goTree?itemType1=${itemBatchHeader.itemType1}&itemType2=${itemBatchHeader.itemType2}"></iframe>
    	</div>
    	<div style="width: 1030px;height: 315px;float: left;padding-left: 2px;">
      		<table id="dataTableSelect"></table>
			<div id="dataTableSelectPager"></div>
    	</div>
  	</div>
  	<div style="width: 1200px;height: 258px;float: left;margin-top: 20px">
    	<div style="width: 1150px">
      		<table id="itemDetailDataTable"></table>
    	</div>
  	</div>
</div>
<script type="text/javascript">
//材料名称
$(function(){
	var rowNo=1;
    var gridOption = {
		height:266,
		colModel : [
			{name: "itcodedescr", index: "itcodedescr", width: 180, label: "材料名称", sortable: true, align: "left"},
			{name: "price", index: "price", width: 75, label: "单价", sortable: true, align: "right"},
			{name: "uomdescr", index: "uomdescr", width: 75, label: "单位", sortable: true, align: "left"},
			{name: "remarks", index: "remarks", width: 150, label: "材料描述", sortable: true, align: "left"},
			{name: "itemtype3descr", index: "itemtype3descr", width: 110, label: "材料类型3", sortable: true, align: "left"},
			{name: "supplcodedescr", index: "supplcodedescr", width: 90, label: "供应商", sortable: true, align: "left"},
			{name: "whdescr", index: "whdescr", width: 85, label: "仓库", sortable: true, align: "left"},
			{name: "model", index: "model", width: 90, label: "型号", sortable: true, align: "left"},
			{name: "sizedesc", index: "sizedesc", width: 100, label: "规格说明", sortable: true, align: "left"},
			{name: "color", index: "color", width: 75, label: "颜色", sortable: true, align: "left"},
			{name: "isfixpricedescr", index: "isfixpricedescr", width: 110, label: "是否固定价", sortable: true, align: "left"},
			{name: "itcode", index: "itcode", width: 100, label: "材料编号", sortable: true, align: "left"},
			{name: "isfixprice", index: "isfixprice", width: 85, label: "是否固定价", sortable: true, align: "left", hidden: true},
			{name: "cost", index: "cost", width: 20, label: "成本", sortable: true, align: "left", hidden: true},
			{name: "itemtype2", index: "itemtype2", width: 85, label: "itemtype2", sortable: true, align: "left", hidden: true},
			{name: "itemtype2descr", index: "itemtype2descr", width: 85, label: "itemtype2descr", sortable: true, align: "left", hidden: true},
			{name: "itemtype3", index: "itemtype3", width: 50, label: "itemtype3", sortable: true, align: "left", hidden: true},
			{name: "sqlcodedescr", index: "sqlcodedescr", width: 85, label: "品牌", sortable: true, align: "left", hidden: true},
			{name: "expired", index: "expired", width: 77, label: "是否过期", sortable: true, align: "left", hidden: true},
			{name: "commitype", index: "commitype", width: 84, label: "commitype", sortable: true, align: "left", hidden: true},
            {name: "isoutsetdescr", index: "isoutsetdescr", width: 122, label: "是否套餐外", sortable: true, align: "left", hidden: true},
            {name: "isoutset", index: "isoutset", width: 122, label: "是否套餐外", sortable: true, align: "left", hidden: true},
            {name: "qty", index: "qty", width: 50, label: "数量", sortable: true, align: "right",hidden: true},
            {name: "beflineamount", index: "beflineamount", width: 50, label: "总价", sortable: true, align: "right",hidden: true},
        ],
        ondblClickRow: function (rowid, status) {
           	var rowData = $("#dataTableSelect").jqGrid("getRowData",rowid);
           	rowData['qty']=0;
           	rowData['beflineamount']=0;
           	$("#itemDetailDataTable").addRowData(rowNo, rowData);
           	rowNo++;
		},
		gridComplete:function (){
			dataTableCheckBox("dataTableSelect","itcodedescr");
			dataTableCheckBox("dataTableSelect","price");
			dataTableCheckBox("dataTableSelect","uomdescr");
			dataTableCheckBox("dataTableSelect","remarks");
			dataTableCheckBox("dataTableSelect","itemtype3descr");
			dataTableCheckBox("dataTableSelect","supplcodedescr");
			dataTableCheckBox("dataTableSelect","model");
			dataTableCheckBox("dataTableSelect","sizedesc");
			dataTableCheckBox("dataTableSelect","color");
			dataTableCheckBox("dataTableSelect","isfixpricedescr");
			dataTableCheckBox("dataTableSelect","itcode");
			dataTableCheckBox("dataTableSelect","whdescr");
		}
	};
    Global.JqGrid.initJqGrid("dataTableSelect", gridOption);
    window.clickTree = function (treeNode) {
    	var itemType2="",sqlCode="";
    	if (treeNode.isParent){
    		itemType2 = treeNode.menuIdStr;
    		sqlCode = "";
    	}else{
    		itemType2 = "";
    		sqlCode = treeNode.menuIdStr;
    	}
    	$("#dataTableSelect").jqGrid("setGridParam",{
    	url:"${ctx}/admin/item/goItemSelectJqGrid2",
    	postData:{'sqlCode':sqlCode,'itemType2':itemType2,'isAdded':$("#isAdded").prop('checked'),'itcodes':"${itemBatchHeader.itcodes}",itemType1:"${itemBatchHeader.itemType1}",custType:"${itemBatchHeader.custType}"}
    	}).trigger("reloadGrid");
	},
    window.search = function () {
    	var descr=$("#itemDescr").val();
    	var code=$("#code").val();
    	$("#dataTableSelect").jqGrid("setGridParam",{
	    	url:"${ctx}/admin/item/goItemSelectJqGrid2",
	    	postData:{'descr':descr,'code':code,'itcodes':"${itemBatchHeader.itcodes}",'isAdded':$("#isAdded").prop('checked'),itemType1:"${itemBatchHeader.itemType1}",custType:"${itemBatchHeader.custType}"}
    	}).trigger("reloadGrid");
    };
});

$(document).ready(function () {
  	Global.JqGrid.initEditJqGrid("itemDetailDataTable", {
    	height: 233,
    	cellEdit: true,
    	cellsubmit: "clientArray",
    	rowNum: 10000000,
		colModel : [
			{name: "itcodedescr", index: "itcodedescr", width: 150, label: "材料名称", sortable: true, align: "left"},
			{name: "qty", index: "qty", width: 50, label: "数量", sortable: true, align: "right",editrules:{number:true},editable:true},
			{name: "price", index: "price", width: 50, label: "单价", sortable: true, align: "right"},
			{name: "uomdescr", index: "uomdescr", width: 60, label: "单位", sortable: true, align: "left"},
			{name: "beflineamount", index: "beflineamount", width: 70, label: "总价", sortable: true, align: "right"},
			{name: "remarks", index: "remarks", width: 150, label: "材料描述", sortable: true, align: "left",editable:true},
			{name: "itemtype3descr", index: "itemtype3descr", width: 90, label: "材料类型3", sortable: true, align: "left", hidden: true},
			{name: "supplcodedescr", index: "supplcodedescr", width: 65, label: "供应商", sortable: true, align: "left", hidden: true},
			{name: "model", index: "model", width: 90, label: "型号", sortable: true, align: "left", hidden: true},
			{name: "sizedesc", index: "sizedesc", width: 70, label: "规格说明", sortable: true, align: "left", hidden: true},
			{name: "color", index: "color", width: 40, label: "颜色", sortable: true, align: "left", hidden: true},
			{name: "isfixpricedescr", index: "isfixpricedescr", width: 85, label: "是否固定价", sortable: true, align: "left", hidden: true},
			{name: "itcode", index: "itcode", width: 20, label: "材料编号", sortable: true, align: "left", hidden: true},
			{name: "deleteBtn", index: "deleteBtn", width: 84, label: "删除", sortable: false, align: "left", formatter:deleteBtn},
			{name: "isfixprice", index: "isfixprice", width: 85, label: "是否固定价", sortable: true, align: "left", hidden: true},
			{name: "cost", index: "cost", width: 20, label: "成本", sortable: true, align: "left", hidden: true},
			{name: "itemtype2", index: "itemtype2", width: 85, label: "itemtype2", sortable: true, align: "left", hidden: true},
			{name: "itemtype2descr", index: "itemtype2descr", width: 85, label: "itemtype2descr", sortable: true, align: "left", hidden: true},
			{name: "itemtype3", index: "itemtype3", width: 50, label: "itemtype3", sortable: true, align: "left", hidden: true},
			{name: "sqlcodedescr", index: "sqlcodedescr", width: 85, label: "品牌", sortable: true, align: "left", hidden: true},
			{name: "expired", index: "expired", width: 77, label: "是否过期", sortable: true, align: "left", hidden: true},
			{name: "commitype", index: "commitype", width: 84, label: "commitype", sortable: true, align: "left", hidden: true},
            {name: "isoutsetdescr", index: "isoutsetdescr", width: 122, label: "是否套餐外", sortable: true, align: "left", hidden: true},
            {name: "isoutset", index: "isoutset", width: 122, label: "是否套餐外", sortable: true, align: "left", hidden: true},
			{name: "lastupdate", index: "lastupdate", width: 126, label: "最后修改日期", sortable: true, align: "left", formatter: formatTime, hidden: true},
			{name: "lastupdatedby", index: "lastupdatedby", width: 78, label: "修改人", sortable: true, align: "left", hidden: true},
			{name: "expired", index: "expired", width: 77, label: "是否过期", sortable: true, align: "left", hidden: true},
			{name: "actionlog", index: "actionlog", width: 68, label: "操作", sortable: true, align: "left", hidden: true}
		
		],
		beforeSelectRow: function (id) {
          setTimeout(function () {
            relocate(id, 'itemDetailDataTable');
          }, 100);
        },
    	beforeSaveCell:function(rowId,name,val,iRow,iCol){
    		var ret = $("#itemDetailDataTable").jqGrid('getRowData', rowId);
    		var beflineamount;
    		//计算总价
    		if(!isNaN(val)){
	    		if(val!=""){
	    			beflineamount=(parseFloat(val)*parseFloat(ret.price)).toFixed(2);
	    		}else{
	    			beflineamount=" ";
	    		}
    		}else{
    			beflineamount=ret.beflineamount;
    		}
    		$("#itemDetailDataTable").jqGrid('setCell',rowId,"beflineamount",beflineamount);
    	}
  	});
}); 
</script>
</body>
</html>
