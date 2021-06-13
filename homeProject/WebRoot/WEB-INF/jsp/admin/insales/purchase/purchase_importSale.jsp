<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>销售单查询</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_salesInvoice.js?v=${v}" type="text/javascript"></script>

<script type="text/javascript">

/**初始化表格*/
$(function(){
	var XZKZ = $.trim($("#XZKZ").val());
	if(XZKZ==1){
		$("#sino").openComponent_salesInvoice();
		$("#sino").setComponent_salesInvoice();
	}else{
		$("#sino").openComponent_salesInvoice({readonly:true});
	}
	
	if('${salesInvoiceDetail.puno}'==''){
		 $("#sino").val('1');
	}
	
	 $("#goto_query").on("click",function(tableId){
		var sino = $.trim($("#sino").val());
		
		if (!tableId || typeof(tableId)!="string"){
			tableId = "dataTable";
		}
		if( $.trim($("#sino").val())==1){
 			art.dialog({
				content:"请输入销售单号！",
			});
		}else{	
			$("#"+tableId).jqGrid("setGridParam",{postData:$("#page_form").jsonForm(),page:1}).trigger("reloadGrid");
		}
	});
	
	var postData = $("#page_form").jsonForm();
    //初始化表格
	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/salesInvoiceDetail/goImportSaleJqGrid",
		postData: postData,
		height:$(document).height()-$("#content-list").offset().top-80,
		multiselect:true,		
		colModel : [
				{name : 'cost',index : 'cost',width : 100,label:'cost',sortable : true,align : "left",hidden:true},
				{name : 'purqty',index : 'purqty',width : 100,label:'purqty',sortable : true,align : "left",hidden:true},
				{name : 'useqty',index : 'useqty',width : 100,label:'useqty',sortable : true,align : "left",hidden:true},
				{name: "address", index: "address", width: 70, label: "address", sortable: true, align: "left",hidden:true},
				{name: "custdescr", index: "custdescr", width: 70, label: "custdescr", sortable: true, align: "left",hidden:true},
				{name: "custcode", index: "custcode", width: 70, label: "custcode", sortable: true, align: "left",hidden:true},
				{name: "mobile1", index: "mobile1", width: 70, label: "mobile1", sortable: true, align: "left",hidden:true},
				{name: "sino", index: "sino", width: 70, label: "sino", sortable: true, align: "left",hidden:true},
				{name: "arrivqty", index: "arrivqty", width: 70, label: "arrivqty", sortable: true, align: "left",hidden:true},
				{name: "pk", index: "pk", width: 70, label: "编号", sortable: true, align: "left",hidden:true},
				{name: "itcode", index: "itcode", width: 100, label: "材料编号", sortable: true, align: "left"},
				{name: "itemdescr", index: "itemdescr", width: 260, label: "材料名称", sortable: true, align: "left"},
				{name: "itemtype2", index: "itemtype2", width: 260, label: "材料2", sortable: true, align: "left"},
				{name: "sqlcodedescr", index: "sqlcodedescr", width: 97, label: "品牌", sortable: true, align: "left"},
				{name: "qty", index: "qty", width: 103, label: "销售数量", sortable: true, align: "left"},
				{name: "allqty", index: "allqty", width: 103, label: "库存", sortable: true, align: "left",hidden:true},
				{name: "unitprice", index: "unitprice", width: 100, label: "单价", sortable: true, align: "left",},
				{name: "beflineamount", index: "beflineamount", width: 109, label: "折扣前金额", sortable: true, align: "left",},
				{name: "uomdescr", index: "uomdescr", width: 100, label: "单位", sortable: true, align: "left",hidden:true},
				{name: "markup", index: "markup", width: 89, label: "折扣", sortable: true, align: "left"},
				{name: "beflineamount", index: "beflineamount", width: 92, label: "总价", sortable: true, align: "left"},
				{name: "remarks", index: "remarks", width: 140, label: "备注", sortable: true, align: "left"},
				{name: "color", index: "color", width: 140, label: "颜色", sortable: true, align: "left",hidden:true},
				{name: "cost", index: "cost", width: 140, label: "进价", sortable: true, align: "left",hidden:true},
				{name: "projectcost", index: "projectcost", width: 140, label: "项目经理结算价", sortable: true, align: "left",hidden:true},
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
		<div class="query-form">
			  <form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
				<input type="hidden" id="expired" name="expired" value="${SalesInvoiceDetail.expired}" />
				<input type="hidden" id="supplCode" name="supplCode" value="${SalesInvoiceDetail.supplCode}" />
							<ul class="ul-form">
							<li>
								<label>销售单号</label>
								 <input type="text" id="sino" name="sino" style="width:160px;"  value="${salesInvoiceDetail.sino}" />
							</li>
							<li hidden="true">
								<label>材料类型1</label>
							<input type="text" id="itemType1" name="itemType1" style="width:160px;"  value="${salesInvoiceDetail.itemType1}" />
							</li>
							<li hidden="true">
								<label>puno</label>
							<input type="text" id="puno" name="puno" style="width:160px;"  value="${salesInvoiceDetail.puno}" />
							</li>
							<li hidden="true">
								<label>XZKZ</label>
							<input type="text" id="XZKZ" name="XZKZ" style="width:160px;"  value="${salesInvoiceDetail.XZKZ}" />
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


