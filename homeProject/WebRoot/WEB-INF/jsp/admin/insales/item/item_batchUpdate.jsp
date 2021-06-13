<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<!DOCTYPE html>
<html>
<head>
	<title>材料信息批量 修改</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
<script src="${resourceRoot}/pub/component_supplier.js?v=${v}" type="text/javascript"></script>
	
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
   		arrCode.push(row.code);
    });
    sCode=arrCode.toString();	
 	Global.Dialog.showDialog("UpdateValue",{			
		  title:"材料信息--批量修改值",		
		  url:"${ctx}/admin/item/goUpdateValue",
		  postData:{code:sCode},		 
		  height: 300,
		  width:500,
		  returnFun: goto_query
    });  
} 
function goto_query(){
	var postData = $("#page_form").jsonForm();
	var url="${ctx}/admin/item/goJqGridBatchUpdate";
	$("#dataTable").jqGrid("setGridParam", {url: url,postData: postData}).trigger("reloadGrid");
}
$(function(){
	$("#supplCode").openComponent_supplier({condition:{ReadAll:"1"}});
	 //初始化材料类型1，2，3三级联动
	Global.LinkSelect.initSelect("${ctx}/admin/item/itemTypeByAuthority","itemType1","itemType2","itemType3");
	Global.LinkSelect.initOption("sqlCode","${ctx}/admin/brand/getBrandList",{itemType2:"${itemType2}"});
	Global.LinkSelect.setSelect({firstSelect:'itemType1',
								firstValue:'${item.itemType1 }',
								secondSelect:'itemType2',
								secondValue:'${item.itemType2 }',
								thridSelect:'itemType3',
								thirdValue:'${item.itemType3 }',});
	var postData = $("#page_form").jsonForm();
	//初始化表格
	Global.JqGrid.initJqGrid("dataTable",{
		multiselect: true,
		height:$(document).height()-$("#content-list").offset().top-82,
		styleUI: 'Bootstrap', 
		colModel : [
			{name: "code", index: "code", width: 100, label: "材料编码", sortable: true, align: "left"},
			{name: "descr", index: "descr", width: 110, label: "名称", sortable: true, align: "left"},
			{name: "expired", index: "expired", width: 79, label: "是否过期", sortable: true, align: "left" ,hiden:true},
			{name: "expireddescr", index: "expireddescr", width: 79, label: "是否过期", sortable: true, align: "left"},
			{name: "commitypedescr", index: "commitypedescr", width: 86, label: "提佣类型", sortable: true, align: "left"},
			{name: "commiperc", index: "commiperc", width: 83, label: "提佣比例", sortable: true, align: "left"},
			{name: "itemtype1descr", index: "itemtype1descr", width: 93, label: "材料类型1", sortable: true, align: "left"},
			{name: "itemtype2descr", index: "itemtype2descr", width: 94, label: "材料类型2", sortable: true, align: "left"},
			{name: "itemtype3descr", index: "itemtype3descr", width: 92, label: "材料类型3", sortable: true, align: "left"},
			{name: "supplcode", index: "supplcode", width: 91, label: "商家编码", sortable: true, align: "left"},
			{name: "splcodedescr", index: "splcodedescr", width: 111, label: "商家名称", sortable: true, align: "left"},
			{name: "sqldescr", index: "sqldescr", width: 110, label: "品牌", sortable: true, align: "left"},
			{name: "barcode", index: "barcode", width: 100, label: "条码", sortable: true, align: "left"},
			{name: "remcode", index: "remcode", width: 80, label: "助记码", sortable: true, align: "left"},
			{name: "uomdescr", index: "uomdescr", width: 60, label: "单位", sortable: true, align: "left"},
			{name: "sizedesc", index: "sizedesc", width: 80, label: "规格", sortable: true, align: "left"},
			{name: "model", index: "model", width: 60, label: "型号", sortable: true, align: "left"},
			{name: "color", index: "color", width: 50, label: "颜色", sortable: true, align: "left"},
			{name: "price", index: "price", width: 83, label: "现价", sortable: true, align: "left"},
			{name: "marketprice", index: "marketprice", width: 80, label: "市场价", sortable: true, align: "left"},
			{name: "isfixpricedescr", index: "isfixpricedescr", width: 80, label: "是否固定价格", sortable: true, align: "left"},
			{name: "cost", index: "cost", width: 78, label: "成本", sortable: true, align: "left", hidden: true},
			{name: "avgcost", index: "avgcost", width: 90, label: "移动下平均成本", sortable: true, align: "left", hidden: true},
			{name: "remark", index: "remark", width: 159, label: "材料描述", sortable: true, align: "left"},
			{name: "lastupdate", index: "lastupdate", width: 120, label: "最后更新时间", sortable: true, align: "left", formatter: formatTime},
			{name: "lastupdatedby", index: "lastupdatedby", width: 96, label: "最后更新人员", sortable: true, align: "left"},
			{name: "actionlog", index: "actionlog", width: 83, label: "操作", sortable: true, align: "left"}
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
					</div>
					</div>
					</div>
			<div class="panel-body">
			<div class="query-form">
			  <form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
					<input type="hidden" name="jsonString" value="" />
						<ul class="ul-form">
							<li>	
								<label>材料类型1</label>
								<select id="itemType1" name="itemType1"></select>
							</li>
							<li>
								<label>材料类型2</label>
								<select id="itemType2" name="itemType2"></select>
							</li>
							<li>	
								<label>材料类型3</label>
								<select id="itemType3" name="itemType3"></select>
							</li>
							<li>		
								<label>材料名称</label>
								<input type="text" id="descr" name="descr" value="${item.descr}" />
							</li>
							<li>		
								<label>品牌</label>
								<select id="sqlCode" name="sqlCode"></select>
							</li>
							<li>
								<label>供应商</label>
								<input type="text" id="supplCode" name="supplCode" />
						     </li>
							<li class="search-group" >
								<input type="checkbox"  id="expired" name="expired"  value="${item.expired=='T'?'T':'F'}" onclick="changeExpired(this)" ${item.expired=='T'?'':'checked' }/><p>隐藏过期</p>
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
