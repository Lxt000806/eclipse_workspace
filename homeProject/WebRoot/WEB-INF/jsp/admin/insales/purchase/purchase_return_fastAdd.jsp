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
	Global.LinkSelect.setSelect({firstSelect:'itemType1',
								firstValue:'${item.itemType1 }',
								secondSelect:'itemType2',
								secondValue:'${item.itemType2 }',
								thirdSelect:'itemType1',
								thirdValue:'${item.itemType1 }',
								});
	Global.LinkSelect.initOption("sqlCode","${ctx}/admin/brand/getBrandList");
	var postData = $("#page_form").jsonForm();
	var puno = $.trim($("#puno").val());
	var sqlcode = $.trim($("#supplCode").val());
	
    //初始化表格
	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/item/goJqGrid",
		postData:{puno:puno},
		height:$(document).height()-$("#content-list").offset().top-80,
		multiselect:true,		
		colModel : [
		  {name : 'purqty',index : 'purqty',width : 100,label:'purqty',sortable : true,align : "left",hidden:true},
		  {name : 'useqty',index : 'useqty',width : 100,label:'useqty',sortable : true,align : "left",hidden:true},
		  {name : 'code',index : 'code',width : 100,label:'材料编号',sortable : true,align : "left"},
	      {name : 'descr',index : 'descr',width : 200,label:'名称',sortable : true,align : "left"},
          {name : 'cost',index : 'cost',width : 100,label:'成本',sortable : true,align : "left"},
          {name : 'uomdescr',index : 'uomdescr',width : 100,label:'单位',sortable : true,align : "left"},
	      {name : 'itemtype1',index : 'itemtype1',width : 100,label:'材料类型1',sortable : true,align : "left",hidden:true},
	      {name : 'itemtype1descr',index : 'itemtype1descr',width : 100,label:'材料类型1',sortable : true,align : "left"},
	      {name : 'itemtype2descr',index : 'itemtype2descr',width : 100,label:'材料类型2',sortable : true,align : "left"},
	      {name : 'itemtype2',index : 'itemtype2',width : 100,label:'材料类型2',sortable : true,align : "left"},
	      {name : 'itemtype3descr',index : 'itemtype3descr',width : 100,label:'材料类型3',sortable : true,align : "left"},
	      {name : 'sqldescr',index : 'sqldescr',width : 100,label:'品牌',sortable : true,align : "left"},
          {name : 'color',index : 'color',width : 100,label:'颜色',sortable : true,align : "left",},
	      {name : 'model',index : 'model',width : 100,label:'型号',sortable : true,align : "left"},
	      {name : 'sizedesc',index : 'sizedesc',width : 100,label:'规格',sortable : true,align : "left"},
	      {name : 'barcode',index : 'barcode',width : 100,label:'条码',sortable : true,align : "left"},
	      {name : 'isfixpricedescr',index : 'isfixpricedescr',width : 100,label:'是否固定价',sortable : true,align : "left"},
	      {name : 'sqlcode',index : 'sqlcode',width : 100,label:'供应商编号',sortable : true,align : "left"},
	      {name : 'price',index : 'price',width : 100,label:'单价',sortable : true,align : "left",hidden:true},
	      {name : 'useqty',index : 'useqty',width : 100,label:'useqty',sortable : true,align : "left",hidden:true},
	      {name : 'purqty',index : 'purqty',width : 100,label:'purqty',sortable : true,align : "left",hidden:true},
	      {name : 'supplcode',index : 'supplcode',width : 100,label:'供应商代码',sortable : true,align : "left",},
          {name : 'allqty',index : 'allqty',width : 100,label:'库存数量',sortable : true,align : "left",hidden:true},
          {name : 'remark',index : 'remark',width : 100,label:'备注',sortable : true,align : "left",hidden:true},
          {name : 'pumarkup',index : 'pumarkup',width : 100,label:'采购单折扣',sortable : true,align : "left",hidden:true},
          {name : 'pubeflineprice',index : 'pubeflineprice',width : 100,label:'采购单折扣前单价',sortable : true,align : "left",hidden:true},
        
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
	  			<div class="panel panel-info" >  
			<div class="panel-body">
			  <form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
				<input type="hidden" id="expired" name="expired" value="${item.expired}" />
				<input type="hidden" id="supplCode" name="supplCode" value="${item.supplCode}" />
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
								<label>条码</label>
								<input type="text" id="barCode" name="barCode" style="width:160px;"  value="${item.barCode}" />
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
								<label>供应商编号</label>
								<input type="text" id="supplCode" name="supplCode" style="width:160px;"  value="${item.supplCode}" />
							</li> --%>
							<li hidden="true" >
								<label>puno</label>
								<input type="text" id="puno" name="puno" style="width:160px;"  value="${item.puno}" />
							</li>
							<li hidden="true">
								<label>purqty</label>
								<input type="text" id="purqty" name="purqty" style="width:160px;"  value="${item.purqty}" />
							</li>
							<li hidden="true">
								<label>useqty</label>
								<input type="text" id="useqty" name="useqty" style="width:160px;"  value="${item.useqty}" />
							</li>
							<%-- <li hidden="true">
								<label>supplier</label>
								<input type="text" id="supplCode" name="supplCode" style="width:160px;"  value="${item.supplCode}" />
							</li> --%>
							<li class="search-group-shrink" >
								<button type="button" class="btn  btn-sm btn-system " onclick="goto_query();"  >查询</button>
							</li>
						</ul>	
			</form>
			</div>
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


