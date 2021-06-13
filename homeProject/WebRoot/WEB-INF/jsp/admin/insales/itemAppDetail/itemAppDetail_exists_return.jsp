<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>ItemAppDetail退回列表</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
</head>
    
<body>
	<div class="body-box-list">
<div class="panel panel-system" >
    <div class="panel-body" >
      <div class="btn-group-xs" >
       <button type="button" class="btn btn-system " id="saveBtn" >保存</button>
      <button type="button" class="btn btn-system " id="selectallBtn" >全选</button>
      <button type="button" class="btn btn-system " id="unselectallBtn" >不选</button>
      <button type="button" class="btn btn-system " id="closeBut" onclick="closeWin(false)">关闭</button>
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
			url:"${ctx}/admin/itemAppDetail/goItemAppDetailExistsReturnJqGrid",
			postData:{no:"${postParam.no}",unSelected:"${postParam.unSelected}"},
			height:$(document).height()-$("#content-list").offset().top-75,
			styleUI: 'Bootstrap',
			multiselect:true,
			colModel : [
			  {name : 'specreqpk',index : 'specreqpk',width : 100,label:'specreqpk',sortable : true,align : "left",hidden:true},
			  {name : 'pk',index : 'pk',width : 100,label:'编号',sortable : true,align : "left",hidden:true},
			  {name : 'reqpk',index : 'reqpk',width : 100,label:'reqpk',sortable : true,align : "left",hidden:true},
			  {name : 'uomdescr',index : 'uomdescr',width : 100,label:'uomdescr',sortable : true,align : "left",hidden:true},
			  {name : 'custcode',index : 'custcode',width : 100,label:'客户编号',sortable : true,align : "left",hidden:true},
		      {name : 'fixareapk',index : 'fixareapk',width : 100,label:'区域编码',sortable : true,align : "left",hidden:true},
		      {name : 'fixareadescr',index : 'fixareadescr',width : 100,label:'区域',sortable : true,align : "left"},
		      {name : 'intprodpk',index : 'intprodpk',width : 100,label:'集成成品PK',sortable : true,align : "left",hidden:true},
		      {name : 'intproddescr',index : 'intproddescr',width : 100,label:'集成成品',sortable : true,align : "left"},
		      {name : 'itemcode',index : 'itemcode',width : 100,label:'材料编号',sortable : true,align : "left"},
		      {name : 'itemdescr',index : 'itemdescr',width : 160,label:'材料名称',sortable : true,align : "left"},
		      {name : 'suppldescr',index : 'suppldescr',width : 160,label:'供应商',sortable : true,align : "left"},
		      {name : 'qty',index : 'qty',width : 100,label:'领料数量',sortable : true,align : "left"},
		      {name : 'reqqty',index : 'reqqty',width : 100,label:'需求数量',sortable : true,align : "left"},
		      {name : 'sendqty',index : 'sendqty',width : 100,label:'已发货数量',sortable : true,align : "left"},
		      {name : 'remarks',index : 'remarks',width : 160,label:'备注',sortable : true,align : "left"},
		      {name : 'cost',index : 'cost',width : 100,label:'cost',sortable : true,align : "left",hidden:true},
	  	      {name : 'projectcost',index : 'projectcost',width : 100,label:'projectcost',sortable : true,align : "left",hidden:true},
	  	      {name : 'aftqty',index : 'aftqty',width : 100,label:'aftqty',sortable : true,align : "left",hidden:true},
	  	      {name : 'aftcost',index : 'aftcost',width : 100,label:'aftcost',sortable : true,align : "left",hidden:true},
	  	      {name : 'allcost',index : 'allcost',width : 100,label:'allcost',sortable : true,align : "left",hidden:true},
	  	      {name : 'allprojectcost',index : 'allprojectcost',width : 100,label:'allprojectcost',sortable : true,align : "left",hidden:true}
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


