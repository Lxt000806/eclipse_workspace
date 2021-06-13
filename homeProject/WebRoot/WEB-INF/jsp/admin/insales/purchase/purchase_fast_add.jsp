<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>材料订单管理快速新增</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
		<script src="${resourceRoot}/pub/component_supplier.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
/**初始化表格*/
$(function(){
	//初始化材料类型1，2，3三级联动
	$("#supplCode").openComponent_supplier({showValue:"${item.supplCode}",showLabel:"${supplDescr}"});
	
	Global.LinkSelect.initSelect("${ctx}/admin/item/itemTypeByAuthority","itemType1","itemType2","itemType3");
	Global.LinkSelect.setSelect({firstSelect:'itemType1',
								firstValue:'${item.itemType1 }',
								secondSelect:'itemType2',
								secondValue:'${item.itemType2 }',
								thirdSelect:'itemType1',
								thirdValue:'${item.itemType1 }',
								});
	Global.LinkSelect.initOption("sqlCode","${ctx}/admin/brand/getBrandList");
	var postData = $("#page_form").jsonForm();
	//var puno = $.trim($("#puno").val());
    //初始化表格
	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/item/goPurchJqGrid",
		postData:{itemType1:'${item.itemType1 }',supplCode:'${item.supplCode}',whCode:"${purchase.whcode}"},
		height:$(document).height()-$("#content-list").offset().top-108,
		multiselect:true,		
		colModel : [
		  {name : 'purqty',index : 'purqty',width : 100,label:'purqty',sortable : true,align : "left",hidden:true},
		  {name : 'useqty',index : 'useqty',width : 100,label:'useqty',sortable : true,align : "left",hidden:true},
		  {name : 'code',index : 'code',width : 70,label:'材料编号',sortable : true,align : "left"},
	      {name : 'descr',index : 'descr',width : 150,label:'名称',sortable : true,align : "left"},
          {name : 'whbalqty',index : 'whbalqty',width : 70,label:'库存数量',sortable : true,align : "right"},
          {name : 'cost',index : 'cost',width : 50,label:'成本',sortable : true,align : "right"},
          {name : 'uomdescr',index : 'uomdescr',width : 50,label:'单位',sortable : true,align : "left"},
	      {name : 'itemtype1',index : 'itemtype1',width : 70,label:'材料类型1',sortable : true,align : "left",hidden:true},
	      {name : 'itemtype1descr',index : 'itemtype1descr',width : 70,label:'材料类型1',sortable : true,align : "left"},
	      {name : 'itemtype2',index : 'itemtype2',width : 70,label:'材料类型2',sortable : true,align : "left"},
	      {name : 'itemtype2descr',index : 'itemtype2descr',width : 70,label:'材料类型2',sortable : true,align : "left"},
	      {name : 'itemtype3descr',index : 'itemtype3descr',width : 70,label:'材料类型3',sortable : true,align : "left"},
	      {name : 'sqldescr',index : 'sqldescr',width : 60,label:'品牌',sortable : true,align : "left"},
          {name : 'color',index : 'color',width : 60,label:'颜色',sortable : true,align : "left",},
	      {name : 'model',index : 'model',width : 60,label:'型号',sortable : true,align : "left"},
	      {name : 'sizedesc',index : 'sizedesc',width : 60,label:'规格',sortable : true,align : "left"},
	      {name : 'barcode',index : 'barcode',width : 70,label:'条码',sortable : true,align : "left"},
	      {name : 'isfixpricedescr',index : 'isfixpricedescr',width : 60,label:'是否固定价',sortable : true,align : "left"},
	      {name : 'sqlcode',index : 'sqlcode',width : 60,label:'供应商编号',sortable : true,align : "left",hidden:true},
	      {name : 'price',index : 'price',width : 60,label:'单价',sortable : true,align : "right",hidden:true},
	      {name : 'useqty',index : 'useqty',width : 100,label:'useqty',sortable : true,align : "left",hidden:true},
	      {name : 'purqty',index : 'purqty',width : 100,label:'purqty',sortable : true,align : "left",hidden:true},
	      {name : 'supplcode',index : 'supplcode',width : 100,label:'供应商代码',sortable : true,align : "left",hidden:true},
          {name : 'allqty',index : 'allqty',width : 100,label:'库存数量',sortable : true,align : "left",hidden:true},
          {name : 'remark',index : 'remark',width : 100,label:'备注',sortable : true,align : "left",hidden:true},
          {name : 'projectcost',index : 'projectcost',width : 60,label:'项目经理结算价',sortable : true,align : "left",},
          {name : 'remark',index : 'remark',width : 200,label:'材料描述',sortable : true,align : "left",},
          {name : 'pumarkup',index : 'markup',width : 200,label:'采购单折扣',sortable : true,align : "left",hidden:true},
          {name : 'pubeflineprice',index : 'pubeflineprice',width : 200,label:'采购单折扣前单价',sortable : true,align : "left",hidden:true},
        ],
	});
		if('${costRight}'=='0'){
			jQuery("#dataTable").setGridParam().hideCol("cost").trigger("reloadGrid");
			jQuery("#dataTable").setGridParam().hideCol("price").trigger("reloadGrid");
		}
		if('${purchase.m_umState}'!='R'){
			jQuery("#dataTable").setGridParam().hideCol("whbalqty").trigger("reloadGrid");
		}
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
				<input type="hidden" id="expired" name="expired" value="${item.expired}" />
				<input type="hidden" id="whCode" name="whCode" value="${purchase.whcode}" />
						<ul class="ul-form">
							<li>
								<label>材料编号</label>
								<input type="text" id="code" name="code" style="width:160px;"  value="${item.code}" />
							</li>
							<li>
								<label>品牌</label>
								<select id="sqlCode" name="sqlCode" style="width:160px;"></select>
							</li>
							<li>
								<label>供应商</label>
								<input type="text" id="supplCode" name="supplCode" style="width:160px;"  value="${item.supplCode}" />
							</li>
							<li>
								<label>材料名称</label>
								<input type="text" id="descr" name="descr" style="width:160px;"  value="${item.descr}" />
							</li>
							<li>
								<label>规格</label>
								<input type="text" id="itemSize" name="itemSize" style="width:160px;"  value="${item.itemSize}" />
							</li>
							<li>
								<label>型号</label>
								<input type="text" id="model" name="model" style="width:160px;"  value="${item.model}" />
							</li>
							<li>
								<label>材料类型1</label>
								<select id="itemType1" name="itemType1" style="width:160px;" value="${item.itemType1 }" disabled="disabled" ></select>
							</li>
							<li>
								<label>材料类型2</label>
								<select id="itemType2" name="itemType2" style="width:160px;"></select>
							</li>
							<li>
								<label>材料类型3</label>
								<select id="itemType3" name="itemType3" style="width:160px;"></select>
							</li>
							<%-- <li hidden="true">
								<label>puno</label>
								<input type="text" id="puno" name="puno" style="width:160px;"  value="${item.puno}" />
							</li> --%>
							<li hidden="true">
								<label>purqty</label>
								<input type="text" id="purqty" name="purqty" style="width:160px;"  value="${item.purqty}" />
							</li>
							<li hidden="true">
								<label>useqty</label>
								<input type="text" id="useqty" name="useqty" style="width:160px;"  value="${item.useqty}" />
							</li>
							<li class="search-group">
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


