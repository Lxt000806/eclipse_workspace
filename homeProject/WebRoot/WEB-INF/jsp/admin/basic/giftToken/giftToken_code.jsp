<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>giftApp列表</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_item.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>

<script type="text/javascript">
/**初始化表格*/
	
$(function(){
	
	var postData = $("#page_form").jsonForm();
		postData.custCode="${giftToken.custCode}",
        //初始化表格
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/giftToken/goJqGrid",
			postData: postData,
			height:$(document).height()-$("#content-list").offset().top-70,
			styleUI: 'Bootstrap',
			colModel : [
				  {name : 'pk',index : 'pk',width : 50,label:'pk',sortable : true,align : "left", hidden:true},
				  {name : 'status',index : 'status',width : 50,label:'status',sortable : true,align : "left", hidden:true},
			      {name : 'custcode',index : 'custcode',width : 80,label:'客户编号',sortable : true,align : "left"},
			      {name : 'custdescr',index : 'custdescr',width : 70,label:'客户名称',sortable : true,align : "left"},
			      {name : 'address',index : 'address',width : 200,label:'楼盘',sortable : true,align : "left"},
			      {name : 'tokenno',index : 'tokenno',width : 100,label:'券号',sortable : true,align : "left"},
			      {name : 'itemcode',index : 'itemcode',width : 80,label:'礼品编号',sortable : true,align : "left"},
			      {name : 'itemdescr',index : 'itemdescr',width : 100,label:'礼品名称',sortable : true,align : "left"},
			      {name : 'qty',index : 'qty',width : 60,label:'数量',sortable : true,align : "right"},
			      {name : 'statusdescr',index : 'statusdescr',width : 60,label:'状态',sortable : true,align : "left"}
            ],
            
            ondblClickRow:function(rowid,iRow,iCol,e){
				var selectRow = $("#dataTable").jqGrid('getRowData', rowid);
            	Global.Dialog.returnData = selectRow;
            	Global.Dialog.closeDialog();
            }
		});
		
		 $("#custCode").openComponent_customer({showValue:"${giftToken.custCode}",showLabel:"${giftToken.custDescr}"});
   		 $("#itemCode").openComponent_item({showValue:"${giftToken.itemCode}",showLabel:"${giftToken.itemDescr}",condition:{itemType1:'LP'}});	
		 if('${giftToken.custCode}'!=''){	
		 $("#custCode").setComponent_customer({disabled: true})
	
		} 
		if('${giftToken.itemCode}'!=''){
			 $("#itemCode").setComponent_item({disabled:true});
		} 
		if('${giftToken.status}'!=''){
		  	 $("#status").attr("disabled",true); 
	
		} 
		
});
	
	
</script>
</head>
    
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form action="" method="post" id="page_form"  class="form-search">
				<input type="hidden" id="expired" name="expired" value="F" />
				<ul class="ul-form">
						<li>
							<label>客户编号</label>
							
							<input type="text" id="custCode" name="custCode" style="width:160px;" value="${giftToken.custCode}" />
						</li>
						<li>
							<label >礼品编号</label>
							<input type="text" id="itemCode" name="itemCode" style="width:160px;" value="${giftToken.itemCode}" />                    
						</li>
						<li>
							<label >券号</label>
							<input type="text" id="tokenNo" name="tokenNo" style="width:160px;" value="${giftToken.tokenNo}"/>                    
						</li>
						<li>
							<label>状态</label>
							<house:xtdm id="status" dictCode="GIFTTOKENSTATUS" value="${giftToken.status }"></house:xtdm>
						</li>
						</li>				
							<li id="loadMore" >
							<button type="button" class="btn btn-system btn-sm" onclick="goto_query();"  >查询</button>
							<button type="button" class="btn btn-system btn-sm" onclick="clearForm();"  >清空</button>
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


