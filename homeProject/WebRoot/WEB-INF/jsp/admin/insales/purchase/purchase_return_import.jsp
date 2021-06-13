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
	//初始化材料类型1，2，3三级联动
	Global.LinkSelect.initSelect("${ctx}/admin/item/itemTypeByAuthority","itemType1","itemType2","itemType3");
	Global.LinkSelect.initOption("sqlCode","${ctx}/admin/brand/getBrandList");
	var postData = $("#page_form").jsonForm();
    //初始化表格
    var custCode = $.trim($("#custCode").val());
    var puno = $.trim($("#puno").val());
    var arr = $.trim($("#arr").val());
	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/itemReq/goImportJqGrid",
		postData:{custCode:custCode,itemType1:'${itemAppDetail.itemType1 }',puno:puno,custcode:'${itemAppDetail.custCode}',arr:arr},
		//postData: postData,
		height:$(document).height()-$("#content-list").offset().top-90,
		multiselect:true,		
		colModel : [
			  {name : 'purqty',index : 'purqty',width : 100,label:'purqty',sortable : true,align : "left",hidden:true},
			  {name : 'useqty',index : 'useqty',width : 100,label:'useqty',sortable : true,align : "left",hidden:true},
			{name: "puno", index: "puno", width: 70, label: "puno", sortable: true, align: "left",hidden:true},
			{name: "itemcode", index: "itemcode", width: 70, label: "itcode", sortable: true, align: "left",hidden:true},
			{name: "pk", index: "pk", width: 70, label: "编号", sortable: true, align: "left",hidden:true},
			{name: "fixareadescr", index: "fixareadescr", width: 136, label: "区域", sortable: true, align: "left"},
			{name: "intprodpk", index: "intprodpk", width: 100, label: "集成成品", sortable: true, align: "left",hidden:true},
			{name: "intproddescr", index: "intproddescr", width: 100, label: "集成成品", sortable: true, align: "left"},
			{name: "itemtype2", index: "itemtype2", width: 100, label: "材料类型2", sortable: true, align: "left",hidden:true},
			{name: "itemdescr2", index: "itemdescr2", width: 100, label: "材料类型2", sortable: true, align: "left"},
			{name: "itemcode", index: "itemcode", width: 100, label: "材料编号", sortable: true, align: "left"},
			{name: "itemdescr", index: "itemdescr", width: 260, label: "材料名称", sortable: true, align: "left"},
			{name: "sqlcodedescr", index: "sqlcodedescr", width: 111, label: "品牌", sortable: true, align: "left"},
			{name: "suppldescr", index: "suppldescr", width: 170, label: "供应商", sortable: true, align: "left"},
			{name: "itemtype1", index: "itemtype1", width: 100, label: "类型编号", sortable: true, align: "left", hidden: true},
			{name: "typedescr", index: "typedescr", width: 100, label: "材料类型", sortable: true, align: "left", hidden: true},
			{name: "qty", index: "qty", width: 100, label: "需求数量", sortable: true, align: "left"},
			{name: "sendqty", index: "sendqty", width: 100, label: "已发货数量", sortable: true, align: "left"},
			{name: "itemchgno", index: "itemchgno", width: 120, label: "增减单号", sortable: true, align: "left"},
			{name: "remarks", index: "remarks", width: 173, label: "备注", sortable: true, align: "left"} ,
			{name: "allqty", index: "allqty", width: 173, label: "库存", sortable: true, align: "left",hidden:true}, 
			{name: "price", index: "price", width: 173, label: "单价", sortable: true, align: "left",hidden:true} ,
			{name: "beflineamount", index: "beflineamount", width: 173, label: "总价", sortable: true, align: "left",hidden:true} ,
      		{name: "uomdescr", index: "uomdescr", width: 173, label: "单位", sortable: true, align: "left",hidden:true} ,
      		{name: "color", index: "color", width: 173, label: "颜色", sortable: true, align: "left",hidden:true}, 
      		{name: "custcode", index: "custcode", width: 173, label: "客户编号", sortable: true, align: "left",hidden:true} ,
      		{name: "itemtype1", index: "itemtype1", width: 173, label: "材料类型1", sortable: true, align: "left",hidden:true} ,
      		{name: "cost", index: "cost", width: 173, label: "进价", sortable: true, align: "left",hidden:true} ,
      		{name: "pumarkup", index: "pumarkup", width: 173, label: "采购单折扣", sortable: true, align: "left",hidden:true} ,
      		{name: "pubeflineprice", index: "pubeflineprice", width: 173, label: "采购单折扣前单价", sortable: true, align: "left",hidden:true} ,
      		
        ],
	});
	 //保存
        $("#saveBtn").on("click",function(){
        	var ids = $("#dataTable").jqGrid('getGridParam', 'selarrrow');
        	if(ids.length==0){
        		Global.Dialog.infoDialog("请选择一条或多条记录进行新增操作！");	
        		return;
        	}
        	var selectRows = [];
    		$.each(ids,function(k,id){
    			var row = $("#dataTable").jqGrid('getRowData', id);
    			selectRows.push(row);
    		});
    		Global.Dialog.returnData = selectRows;
    		closeWin();
        });

});
</script>
</head>
    
<body>
	<div class="body-box-list">
			<div class="panel panel-system" >
				<div class="panel-body" >
					<div class="btn-group-xs" >
								<button type="button" class="btn btn-system " id="saveBtn">
									<span>保存</span>
								</button>
								<button type="button" class="btn btn-system " id="closeBut" onclick="closeWin(false)">
									<span>关闭</span>
								</button>
					</div>
				</div>
			</div>
		<div class="query-form">
			  <form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
					<input type="hidden" readonly="true" id="arr" name="arr" style="width:160px;" value="${arr}"/>
						<ul class="ul-form">
							<li hidden="true">
								<label>客户编号</label>
									<input type="text" id="custCode" name="custCode" style="width:160px;"   value="${itemAppDetail.custCode }" readonly="readonly"/>                                             
							</li>
							<li hidden="true">
								<label>材料1</label>
									<input type="text" id="itemType1" name="itemType1" style="width:160px;"  value="${itemAppDetail.itemType1 }" readonly="readonly"/>                                             
							</li>
							<li>
								<label>材料类型2</label>
								<house:DictMulitSelect id="itemType2" dictCode="" sql=" select rtrim(code) code,descr from titemType2 where Code in (
 									select i.itemType2 from titemreq a 
 									left join titem  i on  i.code=a.itemcode 
 									 left join tpurchaseDetail p on p.ITCode = a.itemcode 
 									where custcode='${itemAppDetail.custCode }' and a.itemType1='${itemAppDetail.itemType1}'
									and p.puno='${itemAppDetail.puno }'	 )" 
								sqlLableKey="descr" sqlValueKey="code" ></house:DictMulitSelect>
							</li>
							<li >
								<label>增减单号</label>
									<input type="text" id="itemChgNo" name="itemChgNo" style="width:160px;"   value="${itemChg.itemChgNo }" />                                             
							</li>
							<li hidden="true">
								<label>puno</label>
									<input type="text" id="puno" name="puno" style="width:160px;"  value="${itemAppDetail.puno }" readonly="readonly"/>                                             
							</li>
							<li> 
								<button type="button" class="btn  btn-sm btn-system " onclick="goto_query();"  >查询</button>
							</li>
						</ul>	
			</form>
		</div>
		<div class="clear_float"> </div>
		<!--query-form-->
		<div class="pageContent">
			<div id="content-list">
				<table id= "dataTable"></table>
				<div id="dataTablePager"></div>
			</div> 
		</div>
</body>
</html>


