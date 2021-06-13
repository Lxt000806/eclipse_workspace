<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<title>预付金管理-编辑</title>
<%@ include file="/commons/jsp/common.jsp"%>
<script type="text/javascript">
	var amount_beforeyf;
	var amount_beforedj;
	function add(){
	    var now= new Date();
	  	var itemType1 = $.trim($("#itemType1").val());
	  	var type = $.trim($("#type").val());
	  	var status = $.trim($("#status").val());
	  	var statusdescr;
	  	if(status=1){
	  		statusdescr="申请";  	
	  	}else if (status=2){
	  		statusdescr="审核";  
	  	}else if (status=3){
	  		statusdescr="取消";  
	  	}
		if(itemType1 ==''){
			art.dialog({content: "请选择材料类型",width: 200});			
			return false;
		}
		if(type ==''){
			art.dialog({content: "请选择预付类型",width: 200});			
			return false;
		}
		if (type=="1"){
			var splJson = Global.JqGrid.allToJson("dataTable","supplier");
			Global.Dialog.showDialog("payManageDetailAdd",{			
			  title:"添加预付金管理明细",		
			  url:"${ctx}/admin/payManage/goadd",
			  postData:{itemtype1:itemType1,type:type,unSelected: splJson["fieldJson"]},		 
			  height: 400,
			  width:800,		
			  returnFun: function(data){       	
	       		 if(data){    	 	
	           		 $.each(data,function(k,v){
	               	 var json = {     
	               		statusdescr:statusdescr,
	               		 status:status,         	 
	                    spldescr:v.spldescr,
	                    supplier:v.splcode,
						amount:v.amount,
	                    prepaybalance:v.prepaybalance,
	                    remarks:v.remarks,
	                    lastupdate: now,
	                    lastupdatedby:"${czy}",
	                    expired: "F", 
	                    actionlog: "ADD",
	                    lastupdateby:v.lastupdateby,
	                    puno:v.puNo,
	                    amount:v.amount,
	                    sumamount:v.sumamount,
	                    remainamount:v.remainamount,
	                    firstamount:v.firstamount                                            
	                };
	                Global.JqGrid.addRowData("dataTable",json);
	            }); 
	            $("#itemType1").attr("disabled","disabled"); 
	            $("#type").attr("disabled","disabled");                
	        }
	    }
	  });
	  }else if (type=="2"){
		  var puNoJson = Global.JqGrid.allToJson("dataTable_dj","puno");
		  Global.Dialog.showDialog("payManageDetailAdd",{			
			  title:"添加预付金管理明细",		
			  url:"${ctx}/admin/payManage/goadd",
			  postData:{itemtype1:itemType1,type:type,unSelected: puNoJson["fieldJson"]},		 
			  height: 400,
			  width:800,		
			  returnFun: function(data){       	
	       		 if(data){    	 	
	           		 $.each(data,function(k,v){
	               	 var json = {     
	               		statusdescr:statusdescr,
	               		 status:status,         	 
	                    spldescr:v.spldescr,
	                    supplier:v.splcode,
						amount:v.amount,
	                    prepaybalance:v.prepaybalance,
	                    remarks:v.remarks,
	                    lastupdate: now,
	                    lastupdatedby:"${czy }",
	                    expired: "F", 
	                    actionlog: "ADD",
	                    lastupdateby:v.lastupdateby,
	                    puno:v.puNo,
	                    amount:v.amount,
	                    sumamount:v.sumamount,
	                    remainamount:v.remainamount,
	                    firstamount:v.firstamount                                            
	                };
	               		Global.JqGrid.addRowData("dataTable_dj",json);
	           		}); 
	            	$("#itemType1").attr("disabled","disabled");    
	           		$("#type").attr("disabled","disabled");               
	        	}
	    	}
	  	});
	  }
	}
	function update(){
		var now= new Date(); 	
	  	var itemType1 = $.trim($("#itemType1").val());
		var type = $.trim($("#type").val());
		var status = $.trim($("#status").val());
	  	var statusdescr;
	  	if(status=1){
	  		statusdescr="申请";  	
	  	}else if (status=2){
	  		statusdescr="审核";  
	  	}else if (status=3){
	  		statusdescr="取消";  
	  	}
	  	if (type=="1"){
			var ret = selectDataTableRow("dataTable");	
		    if (ret) {
		      	Global.Dialog.showDialog("payManagegoaddUpdate",{
				  title:"修改预付金管理",	
				  url:"${ctx}/admin/payManage/goaddUpdate",	
				  postData:{itemtype1:itemType1,type:type,spldescr:ret.spldescr,splcode:ret.supplier,amount:ret.amount,prepaybalance:ret.prepaybalance,
				  remarks:ret.remarks },		 			 		  
				  height:400,
				  width:800,
				  returnFun:function(data){
					var id = $("#dataTable").jqGrid("getGridParam","selrow");
							Global.JqGrid.delRowData("dataTable",id);
					  if(data){
						  $.each(data,function(k,v){
							  var json = {
						  	  	statusdescr:statusdescr, 
						  	  	status:status,      	 
				                spldescr:v.spldescr,
				                supplier:v.splcode,
								amount:v.amount,
				                prepaybalance:v.prepaybalance,
				                remarks:v.remarks,
				                lastupdate: now,
				                lastupdatedby:"${czy }",
				                expired: "F", 
				                actionlog: "ADD",
				                lastupdateby:v.lastupdateby,
							  };
							  Global.JqGrid.addRowData("dataTable",json);
						  });
					  	}
					}   
				});
		    } 
	    }else if (type=="2"){
			var ret = selectDataTableRow("dataTable_dj");	
		    if (ret) {
		    	Global.Dialog.showDialog("payManagegoaddUpdate",{
				  title:"修改预付金管理",	
				  url:"${ctx}/admin/payManage/goaddUpdate",	
				  postData:{itemtype1:itemType1,type:type,puNo:ret.puno,spldescr:ret.spldescr,splcode:ret.supplier,amount:ret.amount,firstamount:ret.firstamount
				  ,sumamount:ret.sumamount,remarks:ret.remarks,remainamount:ret.remainamount },		 			 		  
				  height:400,
				  width:800,
				   returnFun:function(data){
						var id = $("#dataTable_dj").jqGrid("getGridParam","selrow");
								Global.JqGrid.delRowData("dataTable_dj",id);
						  if(data){
							  $.each(data,function(k,v){
								  var json = {
									statusdescr:statusdescr,
									status:status,         	 
				                    spldescr:v.spldescr,
				                    supplier:v.splcode,
									amount:v.amount,
				                    prepaybalance:v.prepaybalance,
				                    remarks:v.remarks,
				                    lastupdate: now,
				                    lastupdatedby:"${czy }",
				                    expired: "F", 
				                    actionlog: "ADD",
				                    lastupdateby:v.lastupdateby,
				                    puno:v.puNo,
				                    amount:v.amount,
				                    sumamount:v.sumamount,
				                    remainamount:v.remainamount,
				                    firstamount:v.firstamount  			                      		                      			                     		                     
								  };
								  Global.JqGrid.addRowData("dataTable_dj",json);
							  });
						  }
					  }   
				   });
		    	} 
		    }else {
		    	art.dialog({    	
					content: "请选择一条记录"
				});
		    }
	}
	function view(){
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
				url:"${ctx}/admin/payManage/goJqGridDetail",
				postData:{
				    no: "${supplierPrepay.no}",
				    type:"${supplierPrepay.type}"
				},
				height:$(document).height()-$("#content-list").offset().top-90,
				styleUI:"Bootstrap",
				cellEdit: true,
				cellsubmit: "clientArray",
				colModel : [
				   		{name: "pk", index: "pk", width: 94, label: "pk", sortable: false, align: "left", hidden: true},
				        {name: "supplier", index: "supplier", width: 110, label: "供应商", sortable: false, align: "left",hidden:true},
						{name: "spldescr", index: "spldescr", width: 110, label: "供应商", sortable: false, align: "left"},
						{name: "statusdescr", index: "statusdescr", width: 70, label: "状态", sortable: false, align: "left"},
						{name: "status", index: "status", width: 60, label: "状态", sortable: false, align: "left", hidden: true},
						{name: "amount", index: "amount", width: 80, label: "本次付款", sortable: false, align: "right", sum: true,editable:true,edittype:'text',editrules:{number:true,required:true}},
						{name: "prepaybalance", index: "prepaybalance", width: 80, label: "预付金余额", sortable: false, align: "right"},
						{name: "remarks", index: "remarks", width: 200, label: "备注", sortable: false, align: "left"},
						{name: "actname", index: "actname", width: 70, label: "户名", sortable: false, align: "left"},
						{name: "cardid", index: "cardid", width: 155, label: "账号", sortable: false, align: "left"},
						{name: "bank", index: "bank", width: 130, label: "开户行", sortable: false, align: "left"},
						{name: "lastupdate", index: "lastupdate", width: 110, label: "最后更新时间", sortable: false, align: "left", formatter: formatTime},
						{name: "lastupdatedby", index: "lastupdatedby", width: 100, label: "最后更新人员", sortable: false, align: "left"},
						{name: "actionlog", index: "actionlog", width: 88, label: "操作标志", sortable: false, align: "left"},
						{name: "splcode", index: "splcode", width: 142, label: "供应商编号", sortable: false, align: "left",hidden:true},
						{name: "puno", index: "puno", width: 80, label: "采购单号", sortable: false, align: "left",hidden:true},
						{name: "sumamount", index: "sumamount", width: 110, label: "总金额", sortable: false, align: "right",hidden:true},
						{name: "remainamount", index: "remainamount", width: 110, label: "应付余额", sortable: false, align: "right",hidden:true},
						{name: "firstamount", index: "firstamount", width: 110, label: "已付定金", sortable: false, align: "right",hidden:true}
	            ],
	            beforeSelectRow:function(id){
					setTimeout(function(){
				   		relocate(id,"dataTable");
					},100);
				},
				beforeEditCell: function (id, name, val, iRow, iCol){
					amount_beforeyf=val;
				},
				beforeSaveCell: function (id, name, val, iRow, iCol) {
			   		var rowData = $("#dataTable").jqGrid("getRowData", id);
			   		if (parseFloat(val)==0.0){
			   			art.dialog({
							content: "本次付款不能为0",
							width: 200
						});
						return amount_beforeyf;
			   		}		
    	 		},
			    rowNum:100000 ,
				pager :"1", 
			});
			Global.JqGrid.initJqGrid("dataTable_dj",{
				url:"${ctx}/admin/payManage/goJqGridDetail",
				postData:{
				    no: "${supplierPrepay.no}",
				    type:"${supplierPrepay.type}"
				},
				height:$(document).height()-$("#content-list").offset().top-69,
				width:$(document).width()-$("#content-list").offset().right-60,
				styleUI: "Bootstrap",
				cellEdit: true,
				cellsubmit: "clientArray",
				colModel : [
			 	        {name: "pk", index: "pk", width: 94, label: "pk", sortable: false, align: "left", hidden: true},
						{name: "spldescr", index: "spldescr", width: 110, label: "供应商", sortable: false, align: "left"},
						{name: "supplier", index: "supplier", width: 110, label: "供应商", sortable: false, align: "left",hidden:true},
						{name: "puno", index: "puno", width: 80, label: "采购单号", sortable: false, align: "left"},
						{name: "statusdescr", index: "statusdescr", width: 70, label: "状态", sortable: false, align: "left"},
						{name: "status", index: "status", width: 60, label: "状态", sortable: false, align: "left", hidden: true},
						{name: "sumamount", index: "sumamount", width: 80, label: "总金额", sortable: false, align: "right"},
						{name: "amount", index: "amount", width: 80, label: "本次付款", sortable: false, align: "right", sum: true,editable:true,edittype:'text',editrules:{number:true,required:true}},
						{name: "remainamount", index: "remainamount", width: 80, label: "应付余额", sortable: false, align: "right"},
						{name: "firstamount", index: "firstamount", width: 80, label: "已付定金", sortable: false, align: "right"},
						{name: "remarks", index: "remarks", width: 351, label: "备注", sortable: false, align: "left"},
						{name: "actname", index: "actname", width: 70, label: "户名", sortable: false, align: "left"},
						{name: "cardid", index: "cardid", width: 155, label: "账号", sortable: false, align: "left"},
						{name: "bank", index: "bank", width: 130, label: "开户行", sortable: false, align: "left"},
						{name: "lastupdate", index: "lastupdate", width: 110, label: "最后更新时间", sortable: false, align: "left", formatter: formatTime},
						{name: "lastupdatedby", index: "lastupdatedby", width: 105, label: "最后更新人员", sortable: false, align: "left"},
						{name: "actionlog", index: "actionlog", width: 88, label: "操作标志", sortable: false, align: "left"},
						{name: "splcode", index: "splcode", width: 142, label: "供应商编号", sortable: false, align: "left",hidden:true},
						{name: "prepaybalance", index: "prepaybalance", width: 110, label: "预付金余额", sortable: false, align: "right",hidden:true}
	            ],
	            beforeSelectRow:function(id){
	           		setTimeout(function(){
				   		relocate(id,"dataTable_dj");
					},100);
				},
				beforeEditCell: function (id, name, val, iRow, iCol){
					amount_beforedj=val;
				},
				beforeSaveCell: function (id, name, val, iRow, iCol) {
			   		var rowData = $("#dataTable_dj").jqGrid("getRowData", id);
			   		if (parseFloat(val)==0.0){
			   			art.dialog({
							content: "本次付款不能为0",
							width: 200
						});
						return amount_beforedj;
			   		}
			   		var errMsg=CheckAmount(parseFloat(rowData.remainamount), parseFloat(rowData.firstamount), parseFloat(val));
				   	if(errMsg!=""){
						art.dialog({
							content: errMsg,
							width: 200
						});
						return amount_beforedj;	
					}		
    	 		},
			   rowNum:100000 ,
			   pager :"1", 
			});
			$("#content-list-dj").hide();
			$("#content-list").hide();
			yftype();
	});
	function yftype(){
		var tableId;
		$("#itemType1").attr("disabled","disabled"); 
	    $("#type").attr("disabled","disabled"); 
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
	function typechange(){
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
	function CheckAmount(remainamount, firstamount, amount){
		if ((amount>0 &&remainamount>0)||( amount<0&&remainamount<0)){
			if (Math.abs(amount)>Math.abs(remainamount)){
				return '付款金额不能超过应付余额！';	
			}
		}else{
			if (firstamount > 0){
				if ((amount>0)||(firstamount+amount<0)){
					return '付款金额不能超过已付定金！';	
				}
			}else if (firstamount < 0){
				if ((amount<0)||(firstamount+amount>0)){
					'付款金额不能超过已付定金！'
				}
				return false;
			}else if (firstamount==0){
				'付款金额不能超过已付定金！'
			}
		}
		return '';
	}
	</script>
</head>
<body >
	<div class="body-box-form">
		<div class="panel panel-system">
			<div class="panel-body">
				<div class="btn-group-xs">
					<button type="button" id="saveBtn" class="btn btn-system ">保存</button>
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
							value="${supplierPrepay.itemType1 }"></select>
						</li>
						<li><label><span class="required">*</span>预付类型</label> <house:xtdm id="type"
								dictCode="SPLPREPAYTYPE" value="${supplierPrepay.type}" onchange="typechange()"></house:xtdm>
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
						<input type="text" id="confirmEmp" name="confirmEmp" style="width:79px;"  value="${supplierPrepay.confirmEmp}"  disabled="true"/> 
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
						<li hidden="true"><input type="text" id="m_umState" name="m_umState" value="M" disabled="true" />
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
					<button type="button" class="btn btn-system " onclick="add()">添加</button>
					<button type="button" class="btn btn-system " onclick="update()">修改</button>
					<button type="button" class="btn btn-system " id="delDetail">删除</button>
					<button type="button" class="btn btn-system " onclick="view()">查看</button>
					<button type="button" class="btn btn-system " onclick="doExcelNow('预付单明细')">导出excel</button>
				</div>
			</div>
			<div id="content-list">
				<table id="dataTable"></table>
				<div id="dataTablePager"></div>
			</div>
			<div id="content-list-dj">
				<table id="dataTable_dj"></table>
				<div id="dataTable_djPager"></div>
			</div>
		</div>
	</div>
	<script src="${resourceRoot}/pub/component_wareHouse.js?v=${v}" type="text/javascript"></script>
	<script>
$("#saveBtn").on("click",function(){	
	if(!$("#page_form").valid()) {return false;}//表单校验
	var itemtype1 = $.trim($("#itemType1").val());	
	var type = $.trim($("#type").val());	
	if (itemtype1==""){
		art.dialog({content: "材料类型1不能为空",width: 200});
		return false;
	}
	if (type=="1"){
		var param= Global.JqGrid.allToJson("dataTable");
		}
	else {
		param= Global.JqGrid.allToJson("dataTable_dj");
	}
	var a = param.itemcode;
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


