<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<title>查看</title>
<%@ include file="/commons/jsp/common.jsp"%>
<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
	function doExcel(){
		var url = "${ctx}/admin/payManage/doExcelDetail";
		doExcelAll(url);
	}
	
	function view(){
		var type = $.trim($("#type").val());
		var ret = selectDataTableRow();	
	    if (ret) {
	      Global.Dialog.showDialog("payManageview",{
			  title:"查看预付金管理",	
			  url:"${ctx}/admin/payManage/goDetailview",	
			  postData:{type:type,spldescr:ret.spldescr,splcode:ret.supplier,amount:ret.amount,prepaybalance:ret.prepaybalance,
			  remarks:ret.remarks,puNo:ret.puno,remainamount:ret.remainamount,firstamount:ret.firstamount },		 			 		  
			  height:400,
			  width:800,
			   returnFun:function(data){
					var id = $("#dataTable").jqGrid("getGridParam","selrow");
							Global.JqGrid.delRowData("dataTable",id);
					  if(data){
						  $.each(data,function(k,v){
							  var json = {
							  };
							  Global.JqGrid.addRowData("dataTable",json);
						  });
					  }
				  }   
			});
	    } else {
	    	art.dialog({    	
				content: "请选择一条记录"
			});
	    }
	}
	
	function viewPu(){
		var ret = selectDataTableRow();	
		console.log(ret);
	    if (ret) {
	      Global.Dialog.showDialog("viewPu",{
			  title:"查看采购记录",	
			  url:"${ctx}/admin/payManage/goViewPu",	
			  postData:{puNo: ret.puno},		 			 		  
			  height:400,
			  width:800,
			});
	    } else {
	    	art.dialog({    	
				content: "请选择一条记录"
			});
	    }
	}
	
	$(function (){
		Global.LinkSelect.initSelect("${ctx}/admin/item/itemTypeByAuthority","itemType1","itemType2","itemType3");
		var dataSet = {
			firstSelect:"itemType1",
			firstValue:"${supplierPrepay.itemType1}",
			disabled:"true"
		};
		Global.LinkSelect.setSelect(dataSet);
		yftype();
	});
	
	function yf(){
		/**初始化表格--预付*/
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/payManage/goJqGridDetail?no="+"${supplierPrepay.no}",
			postData:{type:"${supplierPrepay.type}"},
			styleUI: "Bootstrap",
			height:$(document).height()-$("#content-list").offset().top-70,
			colModel : [		
		        {name: "pk", index: "pk", width: 94, label: "pk", sortable: true, align: "left", hidden: true},
		        {name: "supplier", index: "supplier", width: 142, label: "供应商", sortable: true, align: "left", hidden: true},
				{name: "spldescr", index: "spldescr", width: 110, label: "供应商", sortable: true, align: "left"},
				{name: "statusdescr", index: "statusdescr", width: 70, label: "状态", sortable: true, align: "left"},
				{name: "amount", index: "amount", width: 80, label: "本次付款", sortable: true, align: "right", sum: true},
				{name: "prepaybalance", index: "prepaybalance", width: 80, label: "预付金余额", sortable: true, align: "right"},
				{name: "remarks", index: "remarks", width: 200, label: "备注", sortable: true, align: "right"},
				{name: "actname", index: "actname", width: 70, label: "户名", sortable: true, align: "left"},
				{name: "cardid", index: "cardid", width: 155, label: "账号", sortable: true, align: "left"},
				{name: "bank", index: "bank", width: 117, label: "开户行", sortable: true, align: "left"},
				{name: "lastupdate", index: "lastupdate", width: 138, label: "最后更新时间", sortable: true, align: "left", formatter: formatTime},
				{name: "lastupdatedby", index: "lastupdatedby", width: 105, label: "最后更新人员", sortable: true, align: "left"},
				{name: "actionlog", index: "actionlog", width: 88, label: "操作标志", sortable: true, align: "left"},
				{name: "splcode", index: "splcode", width: 142, label: "供应商编号", sortable: true, align: "left",hidden:true},
				{name: "puno", index: "puno", width: 125, label: "采购单号", sortable: true, align: "left",hidden:true},
				{name: "sumamount", index: "sumamount", width: 110, label: "总金额", sortable: true, align: "left",hidden:true},
				{name: "remainamount", index: "remainamount", width: 110, label: "应付余额", sortable: true, align: "left",hidden:true},
				{name: "firstamount", index: "firstamount", width: 110, label: "已付定金", sortable: true, align: "left",hidden:true}
		    ],
		    beforeSelectRow:function(id){
				setTimeout(function(){
			    	relocate(id,"dataTable");
				},100);
			},
		    rowNum:100000 ,
			pager :1, 
		});
	}
	function dj(){
		/**初始化表格--定金*/
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/payManage/goJqGridDetail?no="+"${supplierPrepay.no}",
			postData:{type:"${supplierPrepay.type}"},
			height:$(document).height()-$("#content-list").offset().top-70,
			styleUI: 'Bootstrap',
			colModel : [		
				{name: "pk", index: "pk", width: 94, label: "pk", sortable: true, align: "left", hidden: true},
				{name: "spldescr", index: "spldescr", width: 142, label: "供应商", sortable: true, align: "left"},
				{name: "supplier", index: "supplier", width: 110, label: "供应商", sortable: true, align: "left"},
				{name: "punoformat", index: "punoformat", width: 125, label: "采购单号", sortable: true, align: "left",formatter: formatPuno},
				{name: "puno", index: "puno", width: 125, label: "采购单号", sortable: true, align: "left",hidden: true},
				{name: "pustatusdescr", index: "pustatusdescr", width: 80, label: "采购单状态", sortable: true, align: "left"},
				{name: "statusdescr", index: "statusdescr", width: 70, label: "状态", sortable: true, align: "left"},
				{name: "sumamount", index: "sumamount", width: 80, label: "总金额", sortable: true, align: "right"},
				{name: "remainamount", index: "remainamount", width: 80, label: "应付余额", sortable: true, align: "right"},
				{name: "amount", index: "amount", width: 80, label: "本次付款", sortable: true, align: "right", sum: true},
				{name: "firstamount", index: "firstamount", width: 80, label: "已付定金", sortable: true, align: "right"},
				{name: "remarks", index: "remarks", width: 200, label: "备注", sortable: true, align: "left"},
				{name: "actname", index: "actname", width: 70, label: "户名", sortable: true, align: "left"},
				{name: "cardid", index: "cardid", width: 155, label: "账号", sortable: true, align: "left"},
				{name: "bank", index: "bank", width: 117, label: "开户行", sortable: true, align: "left"},
				{name: "lastupdate", index: "lastupdate", width: 138, label: "最后更新时间", sortable: true, align: "left", formatter: formatTime},
				{name: "lastupdatedby", index: "lastupdatedby", width: 105, label: "最后更新人员", sortable: true, align: "left"},
				{name: "actionlog", index: "actionlog", width: 88, label: "操作标志", sortable: true, align: "left"},
				{name: "splcode", index: "splcode", width: 142, label: "供应商编号", sortable: true, align: "left",hidden:true},
				{name: "prepaybalance", index: "prepaybalance", width: 80, label: "预付金余额", sortable: true, align: "left",hidden:true}
	           ],
	        beforeSelectRow:function(id){
				setTimeout(function(){
			    	relocate(id,"dataTableGroupBySignCZY");
				},100);
			},
		    rowNum:100000,
			pager:1
		});
	}
	
	function formatPuno(cellvalue, options, rowObject){
      	if(rowObject==null){
        	return '';
		}
		if(cellvalue == null){
			return 0;
		}
		
      	return "<a href=\"javascript:void(0)\" style=\"\" onclick=\"viewPurchase('"
      			+cellvalue+"')\">"+cellvalue+"</a>";
   	}
   	
   	function viewPurchase(puno){
   		Global.Dialog.showDialog("PurchaseView",{
			title:"采购单——查看",
			url: "${ctx}/admin/purchase/goViewNew?id=" + puno,
			height:730,
			width:1250,
		});
   	}
   	
	function yftype(){
		var type = $.trim($("#type").val());
		if	( type == "1" ){
			yf();
		}else if (type == "2"){
			dj();
		}
	}
	</script>
</head>

<div class="body-box-form">
	<div class="panel panel-system">
		<div class="panel-body">
			<div class="btn-group-xs">
				<button type="button" class="btn btn-system" onclick="closeWin(false)">关闭</button>
			</div>
		</div>
	</div>
	<div class="panel panel-info">
		<div class="panel-body">
			<form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
				<input type="hidden" name="jsonString" value="" />
				<ul class="ul-form">
					<li><label>预付金编号</label> <input type="text" id="no" name="no" value="${supplierPrepay.no}"
						readonly="true" />
					</li>
					<li><label>状态</label> <house:xtdm id="status" dictCode="SPLPREPAYSTATUS"
							value="${supplierPrepay.status}" disabled="true"></house:xtdm>
					</li>
					<li><label><span class="required">*</span>材料类型1</label> <select id="itemType1" name="itemType1"
						value="${supplierPrepay.itemType1 }" disabled="disabled"></select>
					</li>
					<li><label><span class="required">*</span>预付类型</label> <house:xtdm id="type"
							dictCode="SPLPREPAYTYPE" value="${supplierPrepay.type}" disabled="true"></house:xtdm>
					</li>
					<li><label>申请日期</label> <input type="text" id="appDate" name="appDate" class="i-date"
						value="<fmt:formatDate value='${supplierPrepay.appDate}' pattern='yyyy-MM-dd'/>"
						onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-%d'})" disabled='true' />
					</li>
					<li><label>申请人</label> <input type="text" id="appEmp" name="appEmp" style="width:79px;"
						value="${supplierPrepay.appEmp}" disabled="true" /> <input type="text" id="appEmpDescr"
						name="appEmpDescr" style="width:79px;" value="${supplierPrepay.appEmpDescr}" disabled="true" />
					</li>
					<li><label>凭证号</label> <input type="text" id="documentNo" name="documentNo"
						value="${supplierPrepay.documentNo }" disabled="true" />
					</li>
					<li><label>审核日期</label> <input type="text" id="confirmDate" name="confirmDate" class="i-date"
						value="<fmt:formatDate value='${supplierPrepay.confirmDate}' pattern='yyyy-MM-dd'/>"
						onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-%d'})" disabled='true' />
					</li>
					<li>	
						<label>审核人</label>
						<input type="text" id="confirmEmp" name="confirmEmp" style="width:79px;" value="${supplierPrepay.confirmEmp}"  disabled="true"/> 
						<input type="text" id="confirmEmpDescr" name="confirmEmpDescr" style="width:79px;" value="${supplierPrepay.confirmEmpDescr}" disabled="true"/> 
					</li>
					<li><label>付款日期</label> <input type="text" id="payDate" name="payDate" class="i-date"
						value="<fmt:formatDate value='${supplierPrepay.payDate}' pattern='yyyy-MM-dd'/>"
						onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-%d'})" disabled='true' />
					</li>
					<li>	
						<label>付款人</label>
						<input type="text" id="payEmp" name="payEmp" style="width:79px;"  value="${supplierPrepay.payEmp}"  disabled="true"/> 
						<input type="text" id="payEmpDescr" name="payEmpDescr" style="width:79px;" value="${supplierPrepay.payEmpDescr}" disabled="true"/> 
					</li>
					<li><label class="control-textarea">备注：</label> <textarea id="remarks" name="remarks">${supplierPrepay.remarks}</textarea>
					</li>
				</ul>
			</form>
		</div>
	</div>
	<div class="clear_float"></div>
	<!--query-form-->
	<div class="pageContent">
		<!--panelBar-->
		<div class="btn-panel">
			<div class="btn-group-sm">
				<button type="button" class="btn btn-system " onclick="view()">查看</button>
				<button type="button" class="btn btn-system " onclick="doExcel('预付单明细')">导出excel</button>
				<c:if test="${supplierPrepay.type == '2'}">
					<button type="button" class="btn btn-system " onclick="viewPu()">查看采购记录</button>
				</c:if>
			</div>
		</div>
		<div id="content-list">
			<table id="dataTable"></table>
			<div id="dataTablePager"></div>
		</div>
	</div>
</div>
</body>
</html>


