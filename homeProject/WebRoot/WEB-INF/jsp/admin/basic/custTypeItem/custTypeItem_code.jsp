<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>custTypeItem列表</title>
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
	})
}
	//初始化材料类型1，2，3三级联动
	Global.LinkSelect.initSelect("${ctx}/admin/item/itemTypeByAuthority","itemType1","itemType2","itemType3");
	Global.LinkSelect.setSelect({firstSelect:'itemType1',
								firstValue:'${item.itemType1 }',
								secondSelect:'itemType2',
								secondValue:'${item.itemType2 }',
								thridSelect:'itemType3',
								thirdValue:'${item.itemType3 }',});
	
	//采购管理部分 传入材料类型1  并不可修改
	if('${item.readonly}'=="1"){
		document.getElementById("itemType1").disabled=true;
	}
	Global.LinkSelect.initOption("sqlCode","${ctx}/admin/brand/getBrandList",{
		itemType1:"${item.itemType1 }"
	});
	var postData = $("#page_form").jsonForm();
    //初始化表格
	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/custTypeItem/goItemJqGrid",
		postData: postData,
		height:$(document).height()-$("#content-list").offset().top-70,
		styleUI: 'Bootstrap',
		colModel : [
		  {name : 'pk',index : 'pk',width : 110,label:'套餐材料信息编号',sortable : true,align : "left"},
		  {name : 'custtypeitempk',index : 'custtypeitempk',width : 110,label:'套餐材料信息编号',sortable : true,align : "left",hidden:true},
		  {name : 'code',index : 'code',width : 100,label:'材料编号',sortable : true,align : "left"},
	      {name : 'descr',index : 'descr',width : 200,label:'材料名称',sortable : true,align : "left"},
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
	      {name : 'supplcode',index : 'supplcode',width : 100,label:'供应商代码',sortable : true,align : "left"},
          {name : 'allqty',index : 'allqty',width : 100,label:'库存数量',sortable : true,align : "left",hidden:true},
          {name : 'remark',index : 'remark',width : 100,label:'备注',sortable : true,align : "left",hidden:true},
          {name : 'color',index : 'color',width : 100,label:'颜色',sortable : true,align : "left",hidden:true},
          {name : 'uomdescr',index : 'uomdescr',width : 100,label:'单位',sortable : true,align : "left"},
          {name : 'cost',index : 'cost',width : 100,label:'进价',sortable : true,align : "left",hidden:true},
          {name : 'avgcostdescr',index : 'avgcostdescr',width : 100,label:'移动成本',sortable : true,align : "left",hidden:true},
  		  {name: 'commitype', index: 'commitype', width: 84, label: 'commitype', sortable: true, align: "left", hidden: true},
          {name : 'projectcost',index : 'projectcost',width : 100,label:'升级项目经理结算价',sortable : true,align : "left",hidden:true},
          {name : 'oldprojectcost',index : 'oldprojectcost',width : 100,label:'原项目经理结算价',sortable : true,align : "left",hidden:true}

        ],
        ondblClickRow:function(rowid,iRow,iCol,e){
			var selectRow = $("#dataTable").jqGrid('getRowData', rowid);
        	Global.Dialog.returnData = selectRow;
        	Global.Dialog.closeDialog();
        }
	});
	if('${costRight}'=='0'){
		jQuery("#dataTable").setGridParam().hideCol("price").trigger("reloadGrid");
	}

});
</script>
</head>
    
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form action="" method="post" id="page_form" class="form-search" >
				<input type="hidden" id="expired" name="expired" value="${item.expired}" />
				<input type="hidden" id="supplCode" name="supplCode" value="${item.supplCode}" />
				<input type="hidden" id="actNo" name="actNo" value="${item.actNo}" />
				<input type="hidden" id="giftAppType" name="giftAppType" value="${item.giftAppType}" />
				<input type="hidden" id="puno" name="puno"   value="${item.puno}" />
				<input type="hidden" id="isCmpActGift" name="isCmpActGift"   value="${item.isCmpActGift}" />
				<input type="hidden" id="custType" name="custType"   value="${item.custType}" />
				<input type="hidden" id="custCode" name="custCode" value="${item.custCode}" />
				<input type="hidden" id="prePlanAreaPk" name="prePlanAreaPk" value="${item.prePlanAreaPk}" />
					<ul class="ul-form">
					<li>
							<label>材料编号</label>
							<input type="text" id="code" name="code"  value="${item.code}" />
					</li>
					<li>		
							<label>品牌</label>
							<select id="sqlCode" name="sqlCode"></select>
						</li>
						<li>	
							<label>条码</label>
						
							<input type="text" id="barCode" name="barCode" style="width:160px;"  value="${item.barCode}" />
						</li>
						<li>	
						
							<label>材料名称</label>
						
							<input type="text" id="descr" name="descr"   value="${item.descr}" />
						</li>
						<li>	
							<label>规格</label>
						
							<input type="text" id="itemSize" name="itemSize"  value="${item.itemSize}" />
						</li>
						<li>	
							<label>型号</label>
							
							<input type="text" id="model" name="model"   value="${item.model}" />
						</li>
						<li>	
					
							<label>材料类型1</label>
						
							<select id="itemType1" name="itemType1" ></select>
						</li>
						<li>
							<label>材料类型2</label>
							<select id="itemType2" name="itemType2" ></select>
						</li>
						<li>	
							<label>材料类型3</label>
						
							<select id="itemType3" name="itemType3" ></select>
							</li>
						
							
							<li class="search-group" >
				
							<input type="checkbox"  id="expired" name="expired"  value="${item.expired=='T'?'T':'F'}" onclick="changeExpired(this)" ${item.expired=='T'?'':'checked' }/><p>隐藏过期</p>
							<button type="button" class="btn  btn-sm btn-system " onclick="goto_query();"  >查询</button>
							<button type="button" class="btn btn-sm btn-system " onclick="clearForm();"  >清空</button>
							
					</li>
				</ul>
			</form>
		</div>
		<!--query-form-->
		<div class="pageContent">
			<div id="content-list">
				<table id= "dataTable"></table>
				<div id="dataTablePager"></div>
			</div> 
		</div>
		</div>
</body>
</html>


