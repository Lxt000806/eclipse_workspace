<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<title>预付金管理--反审核</title>
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script type="text/javascript">
	function view(){
		var now= new Date();
		var type = $.trim($("#type").val());
		if ($("#type").val() == "1") {
			tableId = "dataTable";
		} else if ($("#type").val() == "2"){
			tableId = "dataTable_dj";
		}
		var ret = selectDataTableRow(tableId);
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
							var json = {};
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
	$(function(){
		Global.LinkSelect.initSelect("${ctx}/admin/item/itemTypeByAuthority","itemType1","itemType2","itemType3");
		var dataSet = {
			firstSelect:"itemType1",
			firstValue:"${supplierPrepay.itemType1}",
			disabled:"true"
		};
		Global.LinkSelect.setSelect(dataSet);
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/payManage/goJqGridDetail?no="+"${supplierPrepay.no}",
			postData:{type:"${supplierPrepay.type}"},
			height:$(document).height()-$("#content-list").offset().top-90,
			styleUI: "Bootstrap",
			colModel : [
			   		{name: "pk", index: "pk", width: 94, label: "pk", sortable: false, align: "left", hidden: true},
			        {name: "supplier", index: "supplier", width: 142, label: "供应商", sortable: false, align: "left",hidden: true},
					{name: "spldescr", index: "spldescr", width: 110, label: "供应商", sortable: false, align: "left"},
					{name: "statusdescr", index: "statusdescr", width: 70, label: "状态", sortable: false, align: "left"},
					{name: "status", index: "status", width: 70, label: "状态", sortable: false, align: "left",hidden:true},
					{name: "amount", index: "amount", width: 80, label: "本次付款", sortable: false, align: "right", sum: true},
					{name: "prepaybalance", index: "prepaybalance", width: 80, label: "预付金余额", sortable: false, align: "right"},
					{name: "remarks", index: "remarks", width: 200, label: "备注", sortable: false, align: "left"},
					{name: "actname", index: "actname", width: 70, label: "户名", sortable: false, align: "left"},
					{name: "cardid", index: "cardid", width: 155, label: "账号", sortable: false, align: "left"},
					{name: "bank", index: "bank", width: 130, label: "开户行", sortable: false, align: "left"},
					{name: "lastupdate", index: "lastupdate", width: 110, label: "最后更新时间", sortable: false, align: "left", formatter: formatTime},
					{name: "lastupdatedby", index: "lastupdatedby", width: 100, label: "最后更新人员", sortable: false, align: "left"},
					{name: "actionlog", index: "actionlog", width: 80, label: "操作标志", sortable: false, align: "left"},
					{name: "splcode", index: "splcode", width: 142, label: "供应商编号", sortable: false, align: "left",hidden:true},
					{name: "puno", index: "puno", width: 125, label: "采购单号", sortable: false, align: "left",hidden:true},
					{name: "sumamount", index: "sumamount", width: 110, label: "总金额", sortable: false, align: "left",hidden:true},
					{name: "remainamount", index: "remainamount", width: 110, label: "应付余额", sortable: false, align: "left",hidden:true},
					{name: "firstamount", index: "firstamount", width: 110, label: "已付定金", sortable: false, align: "left",hidden:true}
            ],
            beforeSelectRow:function(id){
				setTimeout(function(){
			    	relocate(id,"dataTable");
				},100);
			},
		    rowNum:100000 ,
			pager :"1", 
		});
		Global.JqGrid.initJqGrid("dataTable_dj",{
			url:"${ctx}/admin/payManage/goJqGridDetail?no="+"${supplierPrepay.no}",
			postData:{type:"${supplierPrepay.type}"},
			height:$(document).height()-$("#content-list").offset().top-69,
			width:$(document).width()-$("#content-list").offset().right-60,
			styleUI: "Bootstrap",
			colModel : [
		      	{name: "pk", index: "pk", width: 94, label: "pk", sortable: false, align: "left", hidden: true},
				{name: "spldescr", index: "spldescr", width: 142, label: "供应商", sortable: false, align: "left"},
				{name: "supplier", index: "supplier", width: 142, label: "供应商", sortable: false, align: "left" ,hidden: true},
				{name: "puno", index: "puno", width: 80, label: "采购单号", sortable: false, align: "left"},
				{name: "statusdescr", index: "statusdescr", width: 70, label: "状态", sortable: false, align: "left"},
				{name: "status", index: "status", width: 70, label: "状态", sortable: false, align: "left",hidden:true},
				{name: "sumamount", index: "sumamount", width: 80, label: "总金额", sortable: false, align: "right"},
				{name: "remainamount", index: "remainamount", width: 80, label: "应付余额", sortable: false, align: "right"},
				{name: "amount", index: "amount", width: 80, label: "本次付款", sortable: false, align: "right", sum: true},
				{name: "firstamount", index: "firstamount", width: 80, label: "已付定金", sortable: false, align: "right"},
				{name: "remarks", index: "remarks", width: 200, label: "备注", sortable: false, align: "left"},
				{name: "actname", index: "actname", width: 70, label: "户名", sortable: false, align: "left"},
				{name: "cardid", index: "cardid", width: 155, label: "账号", sortable: false, align: "left"},
				{name: "bank", index: "bank", width: 117, label: "开户行", sortable: false, align: "left"},
				{name: "lastupdate", index: "lastupdate", width: 110, label: "最后更新时间", sortable: false, align: "left", formatter: formatTime},
				{name: "lastupdatedby", index: "lastupdatedby", width: 100, label: "最后更新人员", sortable: false, align: "left"},
				{name: "actionlog", index: "actionlog", width: 80, label: "操作标志", sortable: false, align: "left"},
				{name: "splcode", index: "splcode", width: 142, label: "供应商编号", sortable: false, align: "left",hidden:true},
				{name: "prepaybalance", index: "prepaybalance", width: 110, label: "预付金余额", sortable: false, align: "left",hidden:true}
            ],
            beforeSelectRow:function(id){
				setTimeout(function(){
			    	relocate(id,"dataTable_dj");
				},100);
			},
		    rowNum:100000 ,
			pager :"1", 
		});
		$("#content-list-dj").hide();
	});
	function yftype(){
		var tableId;
		if ($("#type").val() == "1") {
			tableId = "dataTable";
			$("#content-list-dj").hide();
			$("#content-list").show();
		} else if ($("#type").val() == "2"){
			tableId = "dataTable_dj";
			$("#content-list").hide();
			$("#content-list-dj").show();
		}	
		$("#"+tableId).jqGrid("setGridParam",{postData:$("#page_form").jsonForm(),page:1,}).trigger("reloadGrid"); 
	} 
	</script>
</head>
<body onload="yftype()">
<div class="body-box-form" >
	<div class="panel panel-system" >
    <div class="panel-body" >
      <div class="btn-group-xs" >
       	<button type="button" id="back" class="btn btn-system " >反审核</button>
		<button type="button" class="btn btn-system " onclick="closeWin(false)">关闭</button>
      </div>
   	</div>
	</div>
	<div class="panel panel-info" >  
         <div class="panel-body">
             <form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
				<input type="hidden" name="jsonString" value="" />
				<ul class="ul-form">
					<li>
						<label>预付金编号</label>
						<input type="text" id="no" name="no" value="${supplierPrepay.no}" readonly="true" />
					</li>
					<li>	
						<label>状态</label>
						<house:xtdm id="status" dictCode="SPLPREPAYSTATUS" value="${supplierPrepay.status}" disabled="true"></house:xtdm>
					</li>
					<li>		
						<label>材料类型1</label>
						<select id="itemType1" name="itemType1" value="${supplierPrepay.itemType1 }" disabled="disabled"></select>
					</li>
					<li>
						<label>预付类型</label>					
						<house:xtdm  id="type" dictCode="SPLPREPAYTYPE" value="${supplierPrepay.type}" disabled="true"></house:xtdm>	
					</li>
					<li>
						<label>申请日期</label>
						<input type="text"  id="appDate" name="appDate" class="i-date" value="<fmt:formatDate value='${supplierPrepay.appDate}' pattern='yyyy-MM-dd'/>" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-%d'})" disabled='true'/>
					</li>
					<li>	
						<label   readonly="true">申请人</label>
						<input type="text" id="appEmp" name="appEmp" style="width:79px;" value="${supplierPrepay.appEmp}" disabled="true"/> 
						<input type="text" id="appEmpDescr" name="appEmpDescr" style="width:79px;" value="${supplierPrepay.appEmpDescr}" disabled="true"/> 
					</li>
					<li>	
						<label>凭证号</label>
						<input type="text" id="documentNo" name="documentNo" value="${supplierPrepay.documentNo }"   />
					</li>
					<li>	
						<label>审核日期</label>
						<input type="text"  id="confirmDate" name="confirmDate" class="i-date" value="<fmt:formatDate value='${supplierPrepay.confirmDate}' pattern='yyyy-MM-dd'/>" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-%d'})" disabled='true'/>
					</li>
					<li>	
						<label>审核人</label>
						<input type="text" id="confirmEmp" name="confirmEmp" style="width:79px;" value="${supplierPrepay.confirmEmp}"  disabled="true"/> 
						<input type="text" id="confirmEmpDescr" name="confirmEmpDescr" style="width:79px;" value="${supplierPrepay.confirmEmpDescr}" disabled="true"/> 
				
					</li>
					<li>	
						<label>付款日期</label>
						<input type="text"  id="payDate" name="payDate" class="i-date" value="<fmt:formatDate value='${supplierPrepay.payDate}' pattern='yyyy-MM-dd'/>" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-%d'})" disabled='true'/>
					</li>
					<li>	
						<label>付款人</label>
						<input type="text" id="payEmp" name="payEmp" style="width:79px;"  value="${supplierPrepay.payEmp}"  disabled="true"/> 
						<input type="text" id="payEmpDescr" name="payEmpDescr" style="width:79px;" value="${supplierPrepay.payEmpDescr}" disabled="true"/> 
					</li>
					<li>
						<label class="control-textarea" >备注：</label>
						<textarea id="remarks" name="remarks">${supplierPrepay.remarks}</textarea>
					</li>
					<li hidden="true">
						<input type="text" id="m_umState" name="m_umState" value="B" disabled="true"/> 
					</li>					
				</ul>
			</form>
		</div>
		</div>
		<div class="clear_float"> </div>
		<!--query-form-->
		<div class="pageContent">
			<!--panelBar-->
			<div class="btn-panel" >
		    	<div class="btn-group-sm"  >
					<button type="button" class="btn btn-system " onclick="view()">查看</button>
		    		<button type="button" class="btn btn-system " onclick="doExcelNow('预付单明细')">导出excel</button>
		    	</div>
			</div>
			<div id="content-list">
				<table id= "dataTable"></table>
				<div id="dataTablePager"></div>
			</div>
			<div id="content-list-dj">
				<table id= "dataTable_dj"></table> 
				<div id="dataTable_djPager"></div>
			</div>
		</div>
	</div>
<script src="${resourceRoot}/pub/component_wareHouse.js?v=${v}" type="text/javascript"></script>
<script>
$("#back").on("click",function(){	
	if(!$("#page_form").valid()) {return false;}//表单校验
	var documentNo = $.trim($("#documentNo").val());	
	var type = $.trim($("#type").val());	
	art.dialog({
		content:"是否确认审核取消该预付单？",
		lock: true,
		width: 200,
		height: 100,
		ok: function () {
	    	if (type=="1"){
				var param= Global.JqGrid.allToJson("dataTable");
			}
			else {
				param= Global.JqGrid.allToJson("dataTable_dj");
			}	
			Global.Form.submit("page_form","${ctx}/admin/payManage/dopayManageUpdateSave",param,function(ret){				
				if(ret.rs==true){
					art.dialog({
						content:ret.msg,
						time:3000,
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
	    	return true;
		},
		cancel: function () {
			return true;
		}
	});
});
//删除
$("#delDetail").on("click",function(){
	var type=$("#type").val();
	if (type=="1"){
		var id = $("#dataTable").jqGrid("getGridParam","selrow");
		if(!id){
			art.dialog({content: "请选择一条记录进行删除操作！",width: 200});
			return false;
		}
		Global.JqGrid.delRowData("dataTable",id);	
			var ids = $("#dataTable").getDataIDs();		
			if((!ids || ids.length == 0)){
			    $("#itemType1").removeAttr("disabled","disabled");	
			    $("#type").removeAttr("disabled","disabled");
		    };
	}else if (type=="2"){
		var id = $("#dataTable_dj").jqGrid("getGridParam","selrow");
		if(!id){
			art.dialog({content: "请选择一条记录进行删除操作！",width: 200});
			return false;
		}
		Global.JqGrid.delRowData("dataTable_dj",id);	
			var ids = $("#dataTable_dj").getDataIDs();		
			if((!ids || ids.length == 0)){
			    $("#itemType1").removeAttr("disabled","disabled");	
			    $("#type").removeAttr("disabled","disabled");
		    };
	}else {
		art.dialog({content: "请选择一条记录进行删除操作！",width: 200});
		return false;
	}
});
</script>	
</body>
</html>


