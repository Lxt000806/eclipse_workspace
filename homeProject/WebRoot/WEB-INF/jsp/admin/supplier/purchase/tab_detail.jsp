<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<!DOCTYPE html>
<html>
<head>
	<title>拖期明细</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_item.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>

<script type="text/javascript"> 
$(function(){
	var lastCellRowId;
	var gridOption = {	
			url:"${ctx}/admin/supplierPurchase/goPurchJqGrid",
			postData:{puno:'${purchase.no }'},
			height:$(document).height()-$("#content-list").offset().top-70,
			styleUI: 'Bootstrap', 
			colModel : [
			{name:'pk', 			index:'pk', 			width:80, 	label:'材料编号', 	sortable:true ,align:"left",hidden:true},
			{name:'puno', 			index:'puno', 			width:80, 	label:'材料编号', 	sortable:true ,align:"left",hidden:true},
			{name:'type', 			index:'type', 			width:80, 	label:'type', 	sortable:true ,align:"left",hidden:true},
			{name:'status', 		index:'status', 		width:80, 	label:'status', 	sortable:true ,align:"left",hidden:true},
			{name:'delivtype',		index:'delivtype',		width:197, 	label:'delivtype', 	sortable:true,align:"left",hidden:true},
			{name:'itcode', 		index:'itcode', 		width:80, 	label:'材料编号', 	sortable:true ,align:"left"},
			{name:'itdescr', 		index:'itdescr', 		width:250, 	label:'材料名称', 	sortable:true ,align:"left",count:true},
			{name:'sqlcodedescr',	index:'sqlcodedescr', 	width:60, 	label:'品牌', 	sortable:true ,align:"left"},
			{name:'color',			index:'color', 			width:60,	label:'颜色', 	sortable:true ,align:"left"},	
			{name:'allqty', 		index:'allqty',			width:70, 	label:'当前库存',	sortable:true ,align:"left",},
			{name:'unitprice',		index:'unitprice',		width:50, 	label:'单价', 	sortable:true,align:"left",hidden:true,sum:true},
			{name:'qtycal',			index:'qtycal',			width:66, 	label:'退回数量', 	sortable:true,align:"left",sum:true},
			{name:'arrivqty', 		index:'arrivqty', 		width:50, 	label:'已到货',	sortable:true ,align:"left",sum:true},
			{name:'unidescr', 		index:'unidescr', 		width:40, 	label:'单位', 	sortable:true ,align:"left"},
			{name:'remarks',		index:'remarks',		width:197, 	label:'备注', 	sortable:true,align:"left"},
				],
			};
			//初始化采购单明细		
			Global.JqGrid.initJqGrid("dataTable",gridOption);
			//保存操作	
			$("#saveBtn").on("click",function(){
			var ids = $("#dataTable").jqGrid('getGridParam','selarrrow');
			var param= Global.JqGrid.allToJson("dataTable");
			Global.Form.submit("dataForm","${ctx}/admin/supplierPurchase/doReturnCheckOut",param,function(ret){
				if(ret.rs==true){
					art.dialog({
						content:ret.msg,
						time:1000,
						beforeunload:function(){
							closeWin();
						}
					});				
				}else{
					$("#_form_token_uniq_id").val(ret.token.token);
					art.dialog({
						content:ret.msg,
						width:200
					});
				}
			});
		});
	});
</script>
</head>
	<body>
<div class="body-box-list" style="margin-top: 0px;">
	<div class="panel panel-system" >
    <div class="panel-body" >
      <div class="btn-group-xs" >
						<button style="align:left" type="button" class="btn btn-system "  id="view1">
							<span>查看</span>
						</button>
						<button type="button" class="btn btn-system " id="Excel" title="导出检索条件数据">
							<span>导出excel</span>
		           		</button>
		            </div>  
		          </div> 
		          </div> 		
			<div class="clear_float"></div>
			<!--query-form-->
				<div id="content-list" >
					<table id="dataTable"></table>
					<div id="dataTablePager" ></div>
				</div>
				</div>
	</body>	
</html>
