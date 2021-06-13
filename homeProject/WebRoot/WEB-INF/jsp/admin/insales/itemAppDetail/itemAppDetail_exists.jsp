<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>ItemAppDetail列表</title>
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
			<!--panelBar-->
			<div class="panelBar">
            	<ul>
                	<li>
                    	<a href="javascript:void(0)" class="a1" id="saveBtn">
					       <span>保存</span>
						</a>	
                    </li>
                	<li>
						<a href="javascript:void(0)" class="a2" id="selectallBtn">
							<span>全选</span>
						</a>
					</li>
					<li>
						<a href="javascript:void(0)" class="a3" id="unselectallBtn">
							<span>不选</span>
						</a>
					</li>
					<li id="closeBut" onclick="closeWin(false)">
						<a href="javascript:void(0)" class="a2">
							<span>关闭</span>
						</a>
					</li>					
                    <li class="line"></li>
                </ul>
				<div class="clear_float"> </div>
			</div><!--panelBar end-->
			<div id="content-list">
				<table id= "dataTable"></table>
				<div id="dataTablePager"></div>
			</div> 
		</div>
	</div>
<script type="text/javascript">
function goto_query(){
	$("#dataTable").jqGrid("setGridParam",{postData:$("#page_form").jsonForm(),page:1}).trigger("reloadGrid");
}
/**初始化表格*/
$(function(){
        //初始化表格
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/itemReq/getItemReqList",
			postData:{no:"${postParam.no}",unSelected:"${postParam.unSelected}"},
			height:$(document).height()-$("#content-list").offset().top-70,
			multiselect:true,
			colModel : [
			  {name : 'pk',index : 'pk',width : 100,label:'编号',sortable : true,align : "left",hidden:true},
			  {name : 'custcode',index : 'custcode',width : 100,label:'客户编号',sortable : true,align : "left",hidden:true},
		      {name : 'fixareapk',index : 'fixAreaPk',width : 100,label:'区域编码',sortable : true,align : "left",hidden:true},
		      {name : 'fixareadescr',index : 'fa.Descr',width : 100,label:'区域',sortable : true,align : "left"},
		      {name : 'intprodpk',index : 'intProdPk',width : 100,label:'集成成品PK',sortable : true,align : "left",hidden:true},
		      {name : 'intproddescr',index : 'ip.Descr',width : 100,label:'集成成品',sortable : true,align : "left"},
		      {name : 'itemtype2descr',index : 's2.Descr',width : 100,label:'材料类型2',sortable : true,align : "left"},
		      {name : 'itemcode',index : 'itemcode',width : 100,label:'材料编号',sortable : true,align : "left"},
		      {name : 'itemdescr',index : 'i.Descr',width : 160,label:'材料名称',sortable : true,align : "left"},
		      {name : 'suppldescr',index : 'sp.Descr ',width : 160,label:'供应商',sortable : true,align : "left"},
		      {name : 'qty',index : 'qty',width : 100,label:'需求数量',sortable : true,align : "left"},
		      {name : 'sendqty',index : 'sendqty',width : 100,label:'已发货数量',sortable : true,align : "left"},
		      {name : 'remarks',index : 'remarks',width : 160,label:'备注',sortable : true,align : "left"}
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


