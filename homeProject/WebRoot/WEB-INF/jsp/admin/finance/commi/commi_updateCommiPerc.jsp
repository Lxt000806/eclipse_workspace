<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<!DOCTYPE html>
<html>
<head>
	<title>提成计算批量修改</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
<script src="${resourceRoot}/pub/component_item.js?v=${v}" type="text/javascript"></script>	
<script type="text/javascript">
function updatePrice(){
	var ids = $("#dataTable").jqGrid('getGridParam', 'selarrrow');
    if(ids==''||ids==null){
    	art.dialog({
    		content:'请选择一条或多条数据',
    	});
    	return false;
    }
	var arrCode=new Array();
	$.each(ids,function(k,id){
   		var row = $("#dataTable").jqGrid('getRowData', id);
   		arrCode.push(row.pk);
    });
    sCode=arrCode.toString();	
 	Global.Dialog.showDialog("UpdateValue",{			
		  title:"提成计算--批量修改值",		
		  url:"${ctx}/admin/commi/goUpdateValue",
		  postData:{reqPks:sCode},		 
		  height: 300,
		  width:500,
		  returnFun: goto_query
    });  
} 
function goto_query(){
	var postData = $("#page_form").jsonForm();
	var url="${ctx}/admin/commi/goItemReqJqGrid";
	$("#dataTable").jqGrid("setGridParam", {url: url,postData: postData}).trigger("reloadGrid");
}
$(function(){
	$("#custCode").openComponent_customer({
		condition:{status:"1,2,3,4,5"}
	});
	$("#itemCode").openComponent_item();
	 //初始化材料类型1，2，3三级联动
	Global.LinkSelect.initSelect("${ctx}/admin/item/itemTypeByAuthority","itemType1","itemType2","itemType3");
	Global.LinkSelect.setSelect({firstSelect:'itemType1',
								firstValue:'ZC',
								secondSelect:'itemType2',
								secondValue:'${item.itemType2 }',
								thridSelect:'itemType3',
								thirdValue:'${item.itemType3 }',});
	var postData = $("#page_form").jsonForm();
	//初始化表格
	Global.JqGrid.initJqGrid("dataTable",{
		multiselect: true,
		height:360,
		styleUI: 'Bootstrap', 
		colModel : [
			{name: "pk", index: "pk", width: 110, label: "pk", sortable: true, align: "left",hidden:true},
			{name: "custcode", index: "custcode", width: 70, label: "客户编号", sortable: true, align: "left"},
			{name: "address", index: "address", width: 120, label: "楼盘地址", sortable: true, align: "left"},
			{name: "fixareadescr", index: "fixareadescr", width: 80, label: "区域名称", sortable: true, align: "left"},
			{name: "itemcode", index: "itemcode", width: 70, label: "材料编号", sortable: true, align: "left",hidden:true},
			{name: "itemdescr", index: "itemdescr", width: 200, label: "材料名称", sortable: true, align: "left"},
			{name: "itemtype2descr", index: "itemtype2descr", width: 70, label: "材料类型2", sortable: true, align: "left"},
			{name: "commitypedescr", index: "commitypedescr", width: 80, label: "提成类型", sortable: true, align: "left"},
			{name: "commiperc", index: "commiperc", width: 70, label: "提成比例", sortable: true, align: "right"},
			{name: "qty", index: "qty", width: 60, label: "数量", sortable: true, align: "right"},
			{name: "unitprice", index: "unitprice", width: 60, label: "单价", sortable: true, align: "left"},
			{name: "cost", index: "cost", width: 70, label: "成本单价", sortable: true, align: "right"},
			{name: "processcost", index: "processcost", width: 70, label: "其他费用", sortable: true, align: "right"},
			{name: "costamount", index: "costamount", width: 70, label: "成本总价", sortable: true, align: "right",sum:true},
			{name: "beflineamount", index: "beflineamount", width: 80, label: "折扣前金额", sortable: true, align: "right"},
			{name: "markup", index: "markup", width: 70, label: "折扣", sortable: true, align: "right"},
			{name: "tmplineamount", index: "tmplineamount", width: 70, label: "小计", sortable: true, align: "right",sum:true},
			{name: "lineamount", index: "lineamount", width: 70, label: "总价", sortable: true, align: "right",sum:true},
			{name: "profitper", index: "profitper", width: 70, label: "毛利率", sortable: true, align: "right",formatter:function(cellvalue, options, rowObject){return myRound(cellvalue*100,2)+"%";}},
			{name: "isoutsetdescr", index: "isoutsetdescr", width: 80, label: "套餐外材料", sortable: true, align: "left"},
			{name: "commiamounts", index: "commiamounts", width: 80, label: "提成金额", sortable: true, align: "right"},
		],
	});
	//全选
	$("#selAll").on("click",function(){
		Global.JqGrid.jqGridSelectAll("dataTable",true);
	});
	//全不选
	$("#selNone").on("click",function(){
		Global.JqGrid.jqGridSelectAll("dataTable",false);
	});
	$("#excel").on("click", function () {
		var url = "${ctx}/admin/commi/doItemReqExcel";
		doExcelAll(url);
	});
});
</script>
</head>
	<body>
		<div class="body-box-list">
				<div class="panel panel-system" >
    <div class="panel-body" >
      <div class="btn-group-xs" >
						 <button type="button" class="btn btn-system" onclick="updatePrice()">批量修改值</button>
						<button type="button" class="btn btn-system" id="closeBut" onclick="closeWin(false)">
							<span>关闭</span>
						</button>
						<button type="button" class="btn btn-system" id="excel">导出excel</button>
					</div>
					</div>
					</div>
			<div class="panel-body">
			<div class="query-form">
			  <form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
					<input type="hidden" name="jsonString" value="" />
						<ul class="ul-form">
							<li>
								<label>材料编号</label>
								<input type="text" id="itemCode" name="itemCode"   />
							</li>
							<li>		
								<label>材料名称</label>
								<input type="text" id="itemDescr" name="itemDescr"  />
							</li>
							<li hidden>
								<label>材料类型1</label>
								<select id="itemType1" name="itemType1"></select>
							</li>
							<li>
								<label>材料类型2</label>
								<select id="itemType2" name="itemType2"></select>
							</li>
							<li>		
								<label>客户编号</label>
								<input type="text" id="custCode" name="custCode"  />
							</li>
							<li><label>合同签订时间从</label> <input type="text" id="signDateFrom"
								name="signDateFrom" class="i-date"
								onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
							</li>
							<li><label>至</label> <input type="text" id="signDateTo"
								name="signDateTo" class="i-date"
								onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
							</li>
							
							<li class="search-group" >
								<button type="button" class="btn btn-sm btn-system" onclick="goto_query();"  >查询</button>
								<button type="button" class="btn btn-sm btn-system" onclick="clearForm();"  >清空</button>
							</li>
						</ul>	
				</form>
				</div>
			</div>
			</div>
			<div class="clear_float"></div>
			<!--query-form-->
			<div class="pageContent">
				<div id="content-list">
					<table id= "dataTable"></table>
					<div id="dataTablePager"></div>
				</div> 
			</div>
	</body>	
</html>
