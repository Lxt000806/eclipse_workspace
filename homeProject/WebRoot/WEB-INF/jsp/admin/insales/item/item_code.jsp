<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>Item列表</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>

<script type="text/javascript">
/**初始化表格*/
$(function(){
	if('${item.disabledEle}'){
		var arr='${item.disabledEle}'.split(",");
		$.each(arr,function(k,v){
			$("#"+v).attr("disabled","disabled");
		});
	}
	if("${item.isCustTypeItem }"=="1"){
		var opts = document.getElementById("isCustTypeItem");
		  opts.options[1].selected = 'selected';		
	}
	//初始化材料类型1，2，3三级联动
	Global.LinkSelect.initSelect("${ctx}/admin/item/itemTypeByAuthority","itemType1","itemType2","itemType3");
	Global.LinkSelect.setSelect({firstSelect:'itemType1',
								firstValue:'${item.itemType1 }',
								secondSelect:'itemType2',
								secondValue:'${item.itemType2 }',
								thirdSelect:'itemType3',
								thirdValue:'${item.itemType3 }',});
	
	//采购管理部分 传入材料类型1  并不可修改
	if('${item.readonly}'=="1"){
		document.getElementById("itemType1").disabled=true;
	}
	
	// 控制搜索条件是否隐藏，注意字段ID的匹配
	if ("${item.hiddenFields}") {
	    var fields = "${item.hiddenFields}".split(",")
	    for (var i = 0; i < fields.length; i++) {
	        switch (fields[i]) {
	            case "expired":
	                $("#expired_show").hide()
	                $("#expired_text").hide()
	                break
	            default:
	                $("#" + fields[i]).hide()
	        }
	    }
	}
	
	Global.LinkSelect.initOption("sqlCode","${ctx}/admin/brand/getBrandList",{
		itemType1:"${item.itemType1 }"
	});
	
    //初始化表格
	Global.JqGrid.initJqGrid("dataTable",{
		height:$(document).height()-$("#content-list").offset().top-70,
		styleUI: 'Bootstrap',
		colModel : [
		  {name : 'code',index : 'code',width : 100,label:'材料编号',sortable : true,align : "left"},
	      {name : 'descr',index : 'descr',width : 200,label:'材料名称',sortable : true,align : "left"},
          {name : 'uomdescr',index : 'uomdescr',width : 100,label:'单位',sortable : true,align : "left"},
	      {name : 'itemtype1descr',index : 'itemtype1descr',width : 100,label:'材料类型1',sortable : true,align : "left"},
	      {name : 'itemtype2',index : 'itemtype2',width : 100,label:'材料类型2',sortable : true,align : "left",hidden:true},
	      {name : 'itemtype2descr',index : 'itemtype2descr',width : 100,label:'材料类型2',sortable : true,align : "left"},
	      {name : 'itemtype3descr',index : 'itemtype3descr',width : 100,label:'材料类型3',sortable : true,align : "left"},
	      {name : 'barcode',index : 'barcode',width : 100,label:'条码',sortable : true,align : "left"},
	      {name : 'sqldescr',index : 'sqldescr',width : 100,label:'品牌',sortable : true,align : "left"},
	      {name : 'model',index : 'model',width : 100,label:'型号',sortable : true,align : "left"},
	      {name : 'sizedescr',index : 'sizedescr',width : 100,label:'规格',sortable : true,align : "left"},
	      {name : 'isfixpricedescr',index : 'isfixpricedescr',width : 100,label:'是否固定价',sortable : true,align : "left"},
	      {name : 'price',index : 'price',width : 100,label:'单价',sortable : true,align : "left"},
	      {name : 'marketprice',index : 'marketprice',width : 100,label:'市场价',sortable : true,align : "left"},
	      {name : 'supplcode',index : 'supplcode',width : 100,label:'供应商代码',sortable : true,align : "left",hidden:true},
	      {name : 'splcodedescr',index : 'splcodedescr',width : 100,label:'供应商',sortable : true,align : "left"},
          {name : 'allqty',index : 'allqty',width : 100,label:'库存数量',sortable : true,align : "left",hidden:true},
          {name : 'remark',index : 'remark',width : 100,label:'备注',sortable : true,align : "left",hidden:true},
          {name : 'color',index : 'color',width : 100,label:'颜色',sortable : true,align : "left",hidden:true},
          {name : 'cost',index : 'cost',width : 100,label:'进价',sortable : true,align : "left",hidden:true},
          {name : 'avgcostdescr',index : 'avgcostdescr',width : 100,label:'移动成本',sortable : true,align : "left",hidden:true},
          {name : 'avgcost',index : 'avgcost',width : 100,label:'真·移动成本',sortable : true,align : "left",hidden:true},
  		  {name: 'commitype', index: 'commitype', width: 84, label: 'commitype', sortable: true, align: "left", hidden: true},
          {name : 'projectcost',index : 'projectcost',width : 100,label:'项目经理结算价',sortable : true,align : "left",hidden:true},
          {name : 'expired',index : 'expired',width : 100,label:'是否过期',sortable : true,align : "left",hidden:true},
          {name : 'oldcost',index : 'oldcost',width : 100,label:'原成本',sortable : true,align : "left",hidden:true},
          {name : 'oldprice',index : 'oldprice',width : 100,label:'原价',sortable : true,align : "left",hidden:true},
          {name : 'pumarkup',index : 'pumarkup',width : 100,label:'采购单折扣',sortable : true,align : "left",hidden:true},
          {name : 'pubeflineamount',index : 'pubeflineamount',width : 100,label:'采购单折扣前金额',sortable : true,align : "left",hidden:true},
          {name : 'pubeflineprice',index : 'pubeflineprice',width : 100,label:'采购单折扣前单价',sortable : true,align : "left",hidden:true },
        ],
       	multiselect: "${item.isMultiselect}"=="1"?true:false,
        ondblClickRow:"${item.isMultiselect}"=="1"?function(){}:function(rowid,iRow,iCol,e){
			var selectRow = $("#dataTable").jqGrid('getRowData', rowid);
        	Global.Dialog.returnData = selectRow;
        	Global.Dialog.closeDialog();
        }
	});
	if('${costRight}'=='0'){
		jQuery("#dataTable").setGridParam().hideCol("price").trigger("reloadGrid");
	}
	if('${item.costRight}'=='0'){
		jQuery("#dataTable").setGridParam().hideCol("price").trigger("reloadGrid");
		jQuery("#dataTable").setGridParam().hideCol("isfixpricedescr").trigger("reloadGrid");
	}
	if('${item.isItemTran}'=='1'){
		jQuery("#dataTable").setGridParam().hideCol("marketprice").trigger("reloadGrid");
		jQuery("#dataTable").setGridParam().hideCol("splcodedescr").trigger("reloadGrid");//supplcode→splcodedescr --zb
		jQuery("#dataTable").setGridParam().hideCol("uomdescr").trigger("reloadGrid");
		$("#isCustTypeItem_li").hide();
	}
	$("#descr").focus();
});
function goto_query(){
	var postData = $("#page_form").jsonForm();
	var url="${ctx}/admin/item/goJqGrid";
	if($.trim($("#whCode").val())!=""){
		url="${ctx}/admin/item/goPurchJqGrid"
	}
	$("#dataTable").jqGrid("setGridParam", {
    	url: url,
    	postData: postData
  	}).trigger("reloadGrid");
}
function changeItemType(){
		Global.LinkSelect.initOption("sqlCode","${ctx}/admin/brand/getBrandList",{
		itemType1:$("#itemType1").val(),itemType2:$("#itemType2").val()
	});
}
function doSave(){
	var selectRows = [];
	var ids = $("#dataTable").jqGrid('getGridParam', 'selarrrow');
	for(var i = 0 ; i < ids.length ; i++){
		var rowData = $("#dataTable").jqGrid('getRowData', ids[i]); 
		selectRows.push(rowData);
	}
	Global.Dialog.returnData = selectRows;
	closeWin();
}
</script>
</head>
<body>
	<div class="body-box-list">
		<c:if test="${item.isMultiselect==1}">
			<div class="panel panel-system">
				<div class="panel-body">
					<div class="btn-group-xs">
						<button type="button" class="btn btn-system" id="saveBtn"
							onclick="doSave()">
							<span>保存</span>
						</button>
						<button type="button" class="btn btn-system" id="closeBut"
							onclick="closeWin(false)">
							<span>关闭</span>
						</button>
					</div>
				</div>
			</div>
		</c:if>
		<div class="query-form">
			<form action="" method="post" id="page_form" class="form-search" >
				<input type="hidden" id="expired" name="expired" value="${item.expired}" />
				<input type="hidden" id="supplCode" name="supplCode" value="${item.supplCode}" />
				<input type="hidden" id="actNo" name="actNo" value="${item.actNo}" />
				<input type="hidden" id="giftAppType" name="giftAppType" value="${item.giftAppType}" />
				<input type="hidden" id="puno" name="puno" value="${item.puno}" />
				<input type="hidden" id="isCmpActGift" name="isCmpActGift" value="${item.isCmpActGift}" />
				<input type="hidden" id="whCode" name="whCode" value="${item.whCode}" />
				<input type="hidden" id="custCode" name="custCode" value="${item.custCode}" />
				<input type="hidden" id="custType" name="custType" value="${item.custType}" />
				<input type="hidden" id="isItemProcess" name="isItemProcess" value="${item.isItemProcess}" />
					<ul class="ul-form">
						<li>
							<label>材料编号</label>
							<input type="text" id="code" name="code" value="${item.code}" />
						</li>
						<li>		
							<label>品牌</label>
							<select id="sqlCode" name="sqlCode"></select>
						</li>
						<li>	
							<label>条码</label>
							<input type="text" id="barCode" name="barCode" style="width:160px;" value="${item.barCode}" />
						</li>
						<li>	
							<label>材料名称</label>
							<input type="text" id="descr" name="descr" value="${item.descr}" />
						</li>
						<li>	
							<label>规格</label>
							<input type="text" id="sizeDesc" name="sizeDesc" value="${item.sizeDesc}" />
						</li>
						<li>	
							<label>型号</label>
							<input type="text" id="model" name="model" value="${item.model}" />
						</li>
						<li>	
							<label>材料类型1</label>
							<select id="itemType1" name="itemType1" onchange="changeItemType()"></select>
						</li>
						<li>
							<label>材料类型2</label>
							<select id="itemType2" name="itemType2" onchange="changeItemType()"></select>
						</li>
						<li>	
							<label>材料类型3</label>
							<select id="itemType3" name="itemType3" ></select>
						</li>
						<li id="isCustTypeItem_li">	
							<label>是否套餐</label>
							<select id="isCustTypeItem" name="isCustTypeItem" value="">
								<option value="">请选择...</option>
								<option value="1">是</option>
								<option value="2">否</option>
							</select>
						</li>
						<li>	
							<label>供应商</label>
							<input type="text" id="supplCode" name="supplCode" value="${item.supplCode}" /><!-- supplDescr→supplCode，否则无法查询 --add by zb -->
						</li>
						<li class="search-group" >
							<input type="checkbox" id="expired_show" name="expired_show" onclick="hideExpired(this)" ${item.expired=='T'?'':'checked' }/><p id="expired_text">隐藏过期</p>
							<button type="button" class="btn btn-sm btn-system" onclick="goto_query();">查询</button>
							<button type="button" class="btn btn-sm btn-system" onclick="clearForm();">清空</button>
						</li>
					</ul>
			</form>
		</div>
		<!--query-form-->
		<div class="pageContent">
			<div id="content-list">
				<table id="dataTable"></table>
				<div id="dataTablePager"></div>
			</div> 
		</div>
	</div>
</body>
</html>


