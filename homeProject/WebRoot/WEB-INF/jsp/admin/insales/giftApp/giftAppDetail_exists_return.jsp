<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>GiftAppDetail退回列表</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
</head>
    
<body>
	<div class="body-box-list">
		<!--query-form-->
		<div class="pageContent">
		<div class="panel panel-system" >
	    <div class="panel-body" >
	    	<div class="btn-group-xs" >
				<button type="button" class="btn btn-system "  id="saveBtn">保存</button>
				<button type="button" class="btn btn-system "  id="selectallBtn">全选</button>
				<button type="button" class="btn btn-system "  id="unselectallBtn">不选</button>
				<button type="button" class="btn btn-system "  id="closeBut" onclick="closeWin(false)">关闭</button>
			</div>
		</div>
	</div>
	</div>
			<div id="content-list">
				<table id= "dataTable"></table>
				<div id="dataTablePager"></div>
			</div> 
	</div>
<script type="text/javascript">
/**初始化表格*/
$(function(){
        //初始化表格
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/giftApp/goGiftAppDetailExistsReturnJqGrid",
			postData:{no:"${postParam.no}",unSelected:"${postParam.unSelected}"},
			height:$(document).height()-$("#content-list").offset().top-70,
			multiselect:true,
			styleUI: 'Bootstrap', 
			colModel : [
			  {name : 'pk',index : 'pk',width : 70,label:'pk',sortable : true,align : "left",frozen: true,hidden:true},
		      {name : 'itemcode',index : 'itemcode',width : 70,label:'礼品编号',sortable : true,align : "left"},
		      {name : 'itemdescr',index : 'itemdescr',width : 120,label:'礼品名称',sortable : true,align : "left"},
		      {name : 'tokenpk',index : 'tokenpk',width : 60,label:'券号',sortable : true,align : "left",hidden:true},	
		      {name : 'tokenno',index : 'tokenno',width : 60,label:'券号',sortable : true,align : "left"},	
		      {name : 'qty',index : 'qty',width : 60,label:'数量',sortable : true,align : "right"},
		      {name : 'sendqtyed',index : 'sendqtyed',width : 60,label:'已发货数量',sortable : true,align : "right"},	
		      {name : 'cost',index : 'cost',width : 60,label:'单价',sortable : true,align : "right"},
		      {name : 'uomdescr',index : 'uomdescr',width : 60,label:'单位',sortable : true,align : "right"},	
		      {name : 'price',index : 'price',width : 60,label:'售价',sortable : true,align : "right",hidden:true},	
		      {name : 'sendqty',index : 'sendqty',width : 60,label:'已领数量',sortable : true,align : "right"},
		      {name : 'usediscamount',index : 'usediscamount',width : 80,label:'使用优惠额度',sortable : true,align : "right" },
		      // {name : 'usediscamountdescr',index : 'usediscamountdescr',width : 80,label:'使用优惠额度',sortable : true,align : "left"},
		      {name : 'remarks',index : 'remarks',width : 200,label:'备注',sortable : true,align : "left"},	     	 	
            ]
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
        
        //全选
        $("#selectallBtn").on("click",function(){
        	Global.JqGrid.jqGridSelectAll("dataTable",true);
        });
        
        //不选
        $("#unselectallBtn").on("click",function(){
        	Global.JqGrid.jqGridSelectAll("dataTable",false);
        });

});
</script>
</body>
</html>


