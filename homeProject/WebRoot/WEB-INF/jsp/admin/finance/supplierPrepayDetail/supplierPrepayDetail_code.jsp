<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<title>明细查询</title>
<%@ include file="/commons/jsp/common.jsp"%>
<script src="${resourceRoot}/pub/component_supplier.js?v=${v}" type="text/javascript"></script>
<script src="${resourceRoot}/pub/component_purchase.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
	function goto_query(){
		$("#dataTable").jqGrid("setGridParam",{postData:$("#page_form").jsonForm(),page:1}).trigger("reloadGrid");
	}
	
	function clearForm(){
		$("#page_form input[type='text']").val('');
		$("#documentNo").val('');
		$("#splcode").val('');
		$("#no").val('');
		$("#itemtype1").val('');
	} 
	$(function (){
		if('${supplierPrepayDetail.readonly}'=="1"){
			//$("#type").attr("disabled",true);
			//$("#status").attr("disabled",true);
		    document.getElementById("status").disabled=true;
		}
		Global.LinkSelect.initSelect("${ctx}/admin/item/itemTypeByAuthority","itemtype1","itemType2","itemType3");
		$("#puNo").openComponent_purchase({condition:{itemtype1:$("#itemtype1").val(),readonly:"1",},valueOnly:"1"});
		$("#splcode").openComponent_supplier();
        //初始化表格
        var postData = $("#page_form").jsonForm();	
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/supplierPrepayDetail/goJqGrid",
			postData:postData,
			styleUI: "Bootstrap",
			height:$(document).height()-$("#content-list").offset().top-100,
			colModel : [		
		  	    {name: "pk", index: "pk", width: 94, label: "pk", sortable: true, align: "left",hidden:true},
				{name: "no", index: "no", width: 80, label: "预付单号", sortable: true, align: "left"},
				{name: "typedescr", index: "typedescr", width: 86, label: "预付类型", sortable: true, align: "left"},
				{name: "itemtype1descr", index: "itemtype1descr", width: 80, label: "材料类型1", sortable: true, align: "left"},
				{name: "appdate", index: "appdate", width: 80, label: "申请日期", sortable: true, align: "left", formatter:formatDate},
				{name: "spldescr", index: "spldescr", width: 132, label: "供应商", sortable: true, align: "left"},
				{name: "puno", index: "puno", width: 80, label: "采购单号", sortable: true, align: "left",hidden:true},
				{name: "statusdescr", index: "statusdescr", width: 70, label: "状态", sortable: true, align: "left"},
				{name: "amount", index: "amount", width: 80, label: "本次付款", sortable: true, align: "right", sum: true},
				{name: "paydate", index: "paydate", width: 103, label: "付款日期", sortable: true, align: "left", formatter: formatDate,hidden:true},
				{name: "documentno", index: "documentno", width: 100, label: "凭证号", sortable: true, align: "left",hidden:true},
				{name: "remarks", index: "remarks", width: 300, label: "备注", sortable: true, align: "left"},
            ],
            ondblClickRow:function(rowid,iRow,iCol,e){
				var selectRow = $("#dataTable").jqGrid('getRowData', rowid);
            	Global.Dialog.returnData = selectRow;
            	Global.Dialog.closeDialog();
            }
		});
	}); 
	
</script>
</head>
<body>
	<div class="body-box-form">
		<div class="panel panel-info">
			<div class="panel-body">
				<form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
					<input type="hidden" name="jsonString" value="" />
					<input type="hidden" id="unSelected" name="unSelected" value="${supplierPrepayDetailDetail.unSelected}"/>
					<ul class="ul-form">
						<li><label>预付类型</label> <house:xtdm id="type" dictCode="SPLPREPAYTYPE"
								value="${supplierPrepayDetail.type}"></house:xtdm>
						</li>
						<li><label>预付单号</label> <input type="text" id="no" name="no" />
						</li>
						<li><label>供应商编号</label> <input type="text" id="splcode" name="splcode" />
						</li>
						<li><label>供应商名称</label> <input type="text" id="spldescr" name="spldescr" />
						</li>
						<li><label>材料类型1</label> <select id="itemtype1" name="itemtype1"></select>
						</li>
						<li hidden="true"><label>采购单号</label> <input type="text" id="puNo" name="puNo" />
						</li>
						<li><label>状态</label> <house:xtdmMulit id="status" dictCode="SPLPREPAYSTATUS"
								selectedValue="${supplierPrepayDetail.status }" unShowValue="2,3"></house:xtdmMulit>
						</li>
						<li id="loadMore">
							<button type="button" class="btn btn-system btn-sm" onclick="goto_query();">查询</button>
							<button type="button" class="btn btn-system btn-sm" onclick="clearForm();">清空</button>
						</li>
					</ul>
				</form>
			</div>
		</div>
		<div class="clear_float"></div>
		<!--query-form-->
		<div class="btn-panel">
			<!--panelBar-->
			<div class="pageContent">
				<div id="content-list">
					<table id="dataTable"></table>
					<div id="dataTablePager"></div>
				</div>
			</div>
		</div>
	</div>

</body>
</html>


