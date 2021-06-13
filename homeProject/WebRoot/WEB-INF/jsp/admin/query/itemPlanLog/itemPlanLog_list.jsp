<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<title>预算修改日志查询</title>
	<%@ include file="/commons/jsp/common.jsp" %>
    <script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_item.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_fixArea.js?v=${v}" type="text/javascript"></script>
<style type="text/css">

</style>
<script type="text/javascript">
function goto_query(){
	if($.trim($("#custCode").val())==''){
			art.dialog({content: "客户编号不能为空",width: 200});
			return false;
	} 
	if($.trim($("#itemType1").val())==''){
			art.dialog({content: "材料类型1不能为空",width: 200});
			return false;
	}
	$("#dataTable").jqGrid("setGridParam",{url:"${ctx}/admin/itemPlanLog/goJqGrid",postData:$("#page_form").jsonForm(),page:1}).trigger("reloadGrid");
}
function clearForm(){
	$("#page_form input[type='text']").val('');
	$("#page_form select").val('');
}
//导出EXCEL
function doExcel(){
	var url = "${ctx}/admin/itemPlanLog/doExcel";
	doExcelAll(url);
}
   
function itemType1Change(){ 
   $("#itemCode").openComponent_item({condition:{itemType1:$.trim($("#itemType1").val())}});
}  

function fillData(data){
     $("#fixArea").openComponent_fixArea({condition:{custCode:data["code"]}});
	}
 
/**初始化表格*/
$(function(){
        //初始化材料类型1，2，3三级联动
	    Global.LinkSelect.initSelect("${ctx}/admin/item/itemTypeByAuthority","itemType1");	
        //初始化表格
		Global.JqGrid.initJqGrid("dataTable",{
			height:$(document).height()-$("#content-list").offset().top-100,
			styleUI: 'Bootstrap',
			colModel : [
			  {name : 'fixareadescr',index : 'fixareadescr',width : 100,label:'区域',sortable : true,align : 'left'} ,
			  {name : 'itemcode',index : 'itemcode',width : 80,label:'材料编号',sortable : true,align : "left"},
		      {name : 'qty',index : 'qty',width : 75,label:'数量',sortable : true,align : 'right'},
		      {name : 'unitprice',index : 'unitprice',width : 75,label:'单价',sortable : true,align : 'right'},
		      {name : 'beflineamount', index: 'beflineamount', width: 85, label: '折扣前金额', sortable: true, align: 'right'},
		      {name : 'markup',index : 'markup',width : 75,label:'折扣',sortable : true,align : 'right'},
		      {name : 'processcost', index: 'processcost', width:75 , label: '其他费用', sortable: true, align: 'right'},
		      {name : 'lineamount', index: 'lineamount', width: 75, label: '总价', sortable: true, align: 'right'},
		      {name : 'lastupdate',index : 'lastupdate',width : 150,label:'最后修改时间',sortable : true,align : 'left',formatter: formatTime},
			  {name : 'lastupdatedby',index : 'lastupdatedby',width : 60,label:'修改人',sortable : true,align : 'left'},
			  {name : 'actionlogdescr',index : 'actionlogdescr',width : 70,label:'修改类型',sortable : true,align : 'left'},
			  {name : 'planpk',index : 'planpk',width : 70,label:'预算单号',sortable : true,align :'left'} 
		    ]
           	
		});
	    $("#custCode").openComponent_customer({callBack: fillData}); 
  
            
});
</script>
</head>
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form action="" method="post" id="page_form" class="form-search" >
				<input type="hidden" id="expired" name="expired" value="F" />
				<input type="hidden" name="jsonString" value="" />
				<ul class="ul-form">
						<li>
							<label>客户编号</label>
							<input type="text" id="custCode" name="custCode"  value="${itemPlanLog.custCode}"  />
						</li>
						<li>
						   <label>材料类型1</label>
						   <select id="itemType1" name="itemType1"  value="${itemPlanLog.itemType1}" onchange="itemType1Change()"; ></select>
						</li>
						<li>
							<label>材料编号</label>
							<input type="text" id="itemCode" name="itemCode"   value="${itemPlanLog.itemCode}" readonly="true"/>
						</li>
						<li>
							<label>区域编号</label>
							<input type="text" id="fixArea" name="fixArea" style="width:160px;"  value="${itemPlanLog.fixArea}" />
						</li>
						<li id="loadMore" >
							<button type="button" class="btn btn-sm btn-system " onclick="goto_query();"  >查询</button>
							<button type="button" class="btn btn-sm btn-system " onclick="clearForm();"  >清空</button>
						</li>
				</ul>
			</form>
		</div>
		
		<div class="btn-panel" >
   
      <div class="btn-group-sm"  >   
                 <house:authorize authCode="itemPlanLog_EXCEL"> 
                 	<li>
					     <button type="button" class="btn btn-system " onclick="doExcel()">导出excel</button>
					</li>
				 </house:authorize> 
                    <li class="line"></li>
			</div>
			<div id="content-list">
				<table id= "dataTable"></table>
				<div id="dataTablePager"></div>
			</div>
		</div> 
	</div>
</body>
</html>


