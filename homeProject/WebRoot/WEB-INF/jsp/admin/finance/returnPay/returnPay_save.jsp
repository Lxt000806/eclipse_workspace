<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<title>工程退款管理--新增</title>
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_builderGroup.js?v=${v}" type="text/javascript"></script>
</head>
<body>
<div class="body-box-form" >
	<div class="panel panel-system" >
		<div class="panel-body" >
			<div class="btn-group-xs" >
		      	<button type="button" id="saveBtn" class="btn btn-system " >保存</button>
		      	<button type="button" id="confirmPass" class="btn btn-system " >审核通过</button>
		      	<button type="button" id="confirmCancel" class="btn btn-system " >审核取消</button>
		      	<button type="button" id="batchCheck" class="btn btn-system " >批量结算</button>
				<button type="button" class="btn btn-system" onclick="closeWin(false)">关闭</button>
			</div>
		</div>
	</div>
	<div class="panel panel-info" >  
         <div class="panel-body">
             <form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
            	<house:token></house:token>
				<input type="hidden" name="exportData" id="exportData">
				<input type="hidden" name="jsonString" value="" />
				<ul class="ul-form">
					<div style="float:left;">
						<div class="validate-group" >
							<li>
								<label>结算单号</label>
								<input type="text" id="no" name="no" value="${returnPay.no}" readonly="true" placeholder="保存时生成"/>
							</li>
							<li>
								<label>申请日期</label>
								<input type="text" id="date" name="date" value="${returnPay.date}" disabled="true"/>	
							</li>
						</div>
						<div class="validate-group" >				
							<li>
								<label>账单状态</label>
								<house:xtdm id="status" dictCode="RETURNPAYSTATUS" value="${returnPay.status }" disabled="true"></house:xtdm>
							</li>
							<li>	
								<label>申请人</label>
								<input type="text" id="appCZY" name="appCZY" style="width:79px;" value="${returnPay.appCZY}" disabled="true"/> 
								<input type="text" id="appCzyDescr" name="appCzyDescr" style="width:79px;" value="${returnPay.appCzyDescr}" disabled="true"/> 
							</li>	
						</div>
						<div class="validate-group" >
							<li class="form-validate">
								<label ><span class="required">*</span>凭证号</label>
								<input type="text" id="documentNo" name="documentNo" style="width:160px;" value="${returnPay.documentNo }" />
							</li>
							<li>
								<label>审核日期</label>
								<input type="text" id="confirmDate" name="confirmDate" value="${returnPay.confirmDate}" disabled="true"/>	
							</li>
						</div>
						<div class="validate-group" >				
							<li class="form-validate">
								<label><span class="required">*</span>退款时间</label>
								<input type="text" id="checkDate" name="checkDate" class="i-date" style="width:160px;" 
								onFocus="WdatePicker({onpicked:validateRefresh(),skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss'})" 
								value="<fmt:formatDate value='${returnPay.confirmDate}' pattern='yyyy-MM-dd HH:mm:ss'/>" />
							</li>
							<li>	
								<label>审核人</label>
								<input type="text" id="confirmCZY" name="confirmCZY" style="width:79px;" value="${returnPay.confirmCZY}" disabled="true"/> 
								<input type="text" id="confirmCzyDescr" name="confirmCzyDescr" style="width:79px;" value="${returnPay.confirmCzyDescr}" disabled="true"/> 
							</li>
						</div>
						<div class="validate-group" >				
							<li class="form-validate">	
								<label><span class="required">*</span>类型</label>
								<house:xtdm id="type" dictCode="RETURNPAYTYPE" value="${returnPay.type}"></house:xtdm>
							</li>
						</div>
					</div>
					<div>
						<div class="validate-group" style="height:140px">
							<li>
								<label class="control-textarea" style="width:40px">备注</label> 
								<textarea id="remarks" name="remarks" style="height:120px;width:300px ">${returnPay.remarks}</textarea>
							</li>
						</div>
					</div>
				</ul>
			</form>
		</div>
	</div>
	<div class="container-fluid" >  
		<ul class="nav nav-tabs" >
			<li class="active"><a href="#custVisit_tabView_customer" data-toggle="tab">退款客户</a></li>  
		</ul>
	    <div class="tab-content">  
			<div id="custVisit_tabView_customer"  class="tab-pane fade in active"> 
	         	<div class="body-box-list">
					<div class="btn-panel">
						<div class="btn-group-sm" style="padding-top: 5px;">
							<button type="button" class="btn btn-system " id="settleRefunds" >
								<span>结算退款</span>
							</button>
							<button type="button" class="btn btn-system " id="add" >
								<span>新增</span>
							</button>
							<button type="button" class="btn btn-system " id="update" >
								<span>编辑</span>
							</button>
							<button type="button" class="btn btn-system " id="view" >
								<span>查看</span>
							</button>
							<button type="button" class="btn btn-system " id="delete">
								<span>删除</span>
							</button>
							<button type="button" class="btn btn-system"  id="custPay">
								<span>客户付款</span>
							</button>
							<button type="button" class="btn btn-system"  id="doExcel">
								<span>导出Excel</span>
							</button>
						</div>
					</div>
					<div id="content-list">
						<table id="dataTable"></table>
						<div id="dataTablePager"></div>
					</div>
				</div>
			</div> 
		</div>	
	</div>
</div>	
<script src="${resourceRoot}/pub/component_wareHouse.js?v=${v}" type="text/javascript"></script>
<script>
	$(function() {
		var originalData = $("#page_form").serialize();
	
		var gridOption = {
			url:"${ctx}/admin/returnPay/goDetailJqGrid",
			postData:{no:"${returnPay.no}"},
			sortable: true,
			height : $(document).height()-$("#content-list").offset().top - 80,
			rowNum : 10000000,
			pager:'1',
			colModel : [
			    {name: "custcode", index: "custcode", width: 70, label: "客户编号", sortable: true, align: "left"},      
				{name: "region", index: "region", width: 70, label: "区域编号", sortable: true, align: "left", hidden: true},
				{name: "regiondescr", index: "regiondescr", width: 50, label: "区域", sortable: true, align: "left"},
				{name: "documentno", index: "documentno", width: 70, label: "档案号", sortable: true, align: "left"},
				{name: "descr", index: "descr", width: 80, label: "客户名称", sortable: true, align: "left"},
				{name: "address", index: "address", width: 200, label: "楼盘名称", sortable: true, align: "left"},
				{name: "amount", index: "amount", width: 70, label: "金额", sortable: true, align: "right",sum:true},
				{name: "statusdescr", index: "statusdescr", width: 70, label: "客户状态", sortable: true, align: "left"},
				{name: "enddescr", index: "enddescr", width: 70, label: "结束代码", sortable: true, align: "left"},
				{name: "remarks", index: "remarks", width: 200, label: "备注", sortable: true, align: "left", editable: true, edittype: "textarea"},
                {name: "projectmanname", index: "projectmanname", width: 80, label: "项目经理", sortable: true, align: "left"},
				{name: "prjdeptleadername", index: "prjdeptleadername", width: 80, label: "工程部经理", sortable: true, align: "left"},
				{name: "custcheckstatus", index: "custcheckstatus", width: 100, label: "客户结算状态", sortable: true, align: "left", hidden: true},
				{name: "custcheckstatusdescr", index: "custcheckstatusdescr", width: 100, label: "客户结算状态", sortable: true, align: "left"},
				{name: "ispubreturndescr", index: "ispubreturndescr", width: 70, label: "对公退款", sortable: true, align: "left"},
				{name: "returnamount", index: "returnamount", width: 70, label: "退款金额", sortable: true, align: "left"},
				{name: "refsupplprepaypk", index: "refsupplprepaypk", width: 75, label: "预付金单号", sortable: true, align: "left",hidden:true},
				{name: "refsupplprepayno", index: "refsupplprepayno", width: 85, label: "预付金单号", sortable: true, align: "left"},
				{name: "lastupdate", index: "lastupdate", width: 140, label: "最后更新时间", sortable: true, align: "left", formatter: formatTime, hidden: true},
				{name: "lastupdatedby", index: "lastupdatedby", width: 93, label: "最后更新人员", sortable: true, align: "left", hidden: true},
				{name: "expired", index: "expired", width: 65, label: "是否过期", sortable: true, align: "left", hidden: true},
				{name: "actionlog", index: "actionlog", width: 70, label: "操作日志", sortable: true, align: "left", hidden: true},
				{name: "spldescr", index: "spldescr", width: 70, label: "供应商", sortable: true, align: "left",hidden:true},
				{name: "thisamount", index: "thisamount", width: 70, label: "本次付款金额", sortable: true, align: "left",hidden:true}
				
			],
			loadonce: true,
			ondblClickRow: function(){
				view();
			},
			onCellSelect: function(rowid, iCol, cellcontent, e) {
			    var dataTable = $("#dataTable");
			    dataTable.setSelection(rowid, true);
			},
			gridComplete:function(){
                var dataTable = $("#dataTable");
				var amount_sum = dataTable.getCol("amount",false,"sum");
				dataTable.footerData("set",{"amount":myRound(amount_sum,2)});
				
				disableOrEnableTypeChange();
			},
		};
		
		
		if("A"=="${returnPay.m_umState}"||"M"=="${returnPay.m_umState}"){
			$("#confirmPass").remove();
			$("#confirmCancel").remove();
			$("#batchCheck").remove();
			// $("#doExcel").remove();
		}else if("C"=="${returnPay.m_umState}"){
			$("#saveBtn").remove();
			$("#add").remove();
			$("#update").remove();
			$("#delete").remove();
			// $("#doExcel").remove();
			$("#settleRefunds").remove();
			$("#batchCheck").remove();
		}else if("BC"=="${returnPay.m_umState}"){
            $("#saveBtn").remove();
            $("#add").remove();
            $("#update").remove();
            $("#delete").remove();
            // $("#doExcel").remove();
            $("#settleRefunds").remove();
            $("#confirmPass").remove();
            $("#confirmCancel").remove();
        }else{
			$("#saveBtn").remove();
			$("#confirmPass").remove();
			$("#confirmCancel").remove();
			$("#add").remove();
			$("#update").remove();
			$("#delete").remove();
			$("#settleRefunds").remove();
			$("#batchCheck").remove();
		}
	
	    if ("A" === "${returnPay.m_umState}"
	           || "M" === "${returnPay.m_umState}") {
	    
    		Global.JqGrid.initEditJqGrid("dataTable",gridOption);
	    } else {
	        Global.JqGrid.initJqGrid("dataTable",gridOption);
	    }
		
		$("#page_form").bootstrapValidator({
			message : "This value is not valid",
			feedbackIcons : {/*input状态样式图片*/
				validating : "glyphicon glyphicon-refresh"
			},
			fields: { 
				documentNo:{  
					validators: {  
						notEmpty: {  
							message: "请输入完整的信息"  
						}              
					}  
				},
				type: {
				    validators: {
				        notEmpty: {
				            message: "请选择退款类型"
				        }
				    }
				}
			}
		});
	});
	
	$("#settleRefunds").on("click", function() {
	    if ($("#type").val() !== "1") {
            art.dialog({content: "类型非结算退款时不能使用此功能！"});
            return;
        }
        
        var excludedCusts = Global.JqGrid.allToJson("dataTable", "custcode").fieldJson;
        
        Global.Dialog.showDialog("returnPaySettleRefunds", {
            title: "客户退款--结算退款",
            url: "${ctx}/admin/returnPay/settleRefunds",
            postData: {excludedCusts: excludedCusts},
            height: 700,
            width: 1100,
            returnFun: function(selectedRows) {
                for (var i = 0; i < selectedRows.length; i++) {
                    var row = selectedRows[i];
                    
                    row.amount = row.unpaidamount;
                    row.custcheckstatus = row.checkstatus;
                    row.custcheckstatusdescr = row.checkstatusdescr;
                    row.lastupdatedby = "${sessionScope.USER_CONTEXT_KEY.czybh}";
                    row.lastupdate = new Date();
                    row.actionlog = "ADD";
                    row.expired = "F";
                    
                    Global.JqGrid.addRowData("dataTable", row);
                }
            }
        })
	});
	
	$("#add").on("click",function(){
	    if (!$("#type").val()) {
	        art.dialog({content: "请选择退款类型！"});
            return;
	    }
		var splJson = Global.JqGrid.allToJson("dataTable", "custcode");	
		var refsupplprepaypkJson = Global.JqGrid.allToJson("dataTable","refsupplprepaypk");
		Global.Dialog.showDialog("customerReturnPayAdd",{			
			title:"客户退款--增加",
			url:"${ctx}/admin/returnPay/goCustPayDetail",
			height:350,
			width:1000,
			postData:{m_umState:"A",hasCustCode: splJson["fieldJson"],hasRefSupplPrepayPK: refsupplprepaypkJson["fieldJson"]},
			returnFun: function(data){
				if(data){
					$.each(data,function(k,v){
						var json = {
							custcode: v.custCode,
							documentno : v.documentNo,
							descr : v.descr,
							address : v.address,
							amount : v.amount,
							remarks : v.remarks,
							statusdescr : v.statusDescr,
							enddescr:v.endDescr,
							returnamount:v.returnAmount,
							ispubreturndescr:v.isPubReturnDescr,
							lastupdatedby : v.lastupdatedby,
							lastupdate : new Date(),
							actionlog : "ADD",
							expired : "F",
							custcheckstatus:v.custCheckStatus,
							custcheckstatusdescr:v.custCheckStatusDescr,
							prjdeptleadername:v.prjDeptLeaderName,
							refsupplprepaypk:v.refSupplPrepayPK,
							refsupplprepayno:v.refSupplPrepayNo,
							spldescr:v.splDescr,
							thisamount:v.thisAmount,
							regiondescr: v.regionDescr
					  	};
					  	Global.JqGrid.addRowData("dataTable",json);
					});
				}
			}
		});
	});
	
	$("#update").on("click",function(){
		var ret=selectDataTableRow();
		var rowId = $("#dataTable").jqGrid("getGridParam","selrow");
		if (!ret) {
			art.dialog({
				content: "请选择一条记录"
			});
			return;
		}
		Global.Dialog.showDialog("customerReturnPayAdd",{			
			title:"客户退款--编辑",
			url:"${ctx}/admin/returnPay/goCustPayDetail",
			postData:{
				m_umState:"M",
				descr:ret.descr,
				address:ret.address,
				amount:ret.amount,
				remarks:ret.remarks,
				custCode:ret.custcode,
				custCheckStatus:ret.custcheckstatus,
				endDescr:ret.enddescr,
				isPubReturnDescr:ret.ispubreturndescr,
				refSupplPrepayPK:ret.refsupplprepaypk,
				refSupplPrepayNo:ret.refsupplprepayno,
				thisAmount:ret.thisamount==""?0:ret.thisamount,
				splDescr:ret.spldescr
			},
			height:350,
			width:1000,
			returnFun: function(data){
				if(data){
					$.each(data,function(k,v){
						var json = {
							custcode: v.custCode,
							descr : v.descr,
							address : v.address,
							amount : v.amount,
							remarks : v.remarks,
							enddescr:v.endDescr,
							returnamount:v.returnAmount,
							ispubreturndescr:v.isPubReturnDescr,
							lastupdatedby : v.lastupdatedby,
							lastupdate : new Date(),
							actionlog : "EDIT",
							expired : "F",
							refsupplprepaypk:v.refSupplPrepayPK,
							refsupplprepayno:v.refSupplPrepayNo,
							spldescr:v.splDescr,
							thisamount:v.thisAmount
					  	};
					  	$("#dataTable").setRowData(rowId,json);
					});
				}
			}
		});
	});
	
	$("#view").on("click",function(){
		var ret=selectDataTableRow();
		var rowId = $("#dataTable").jqGrid("getGridParam","selrow");
		if (!ret) {
			art.dialog({
				content: "请选择一条记录"
			});
			return;
		}
		Global.Dialog.showDialog("customerReturnPayAdd",{			
			title:"客户退款--查看",
			url:"${ctx}/admin/returnPay/goCustPayDetail",
			postData:{
				m_umState:"V",
				descr:ret.descr,
				address:ret.address,
				amount:ret.amount,
				remarks:ret.remarks,
				custCode:ret.custcode,
				refSupplPrepayPK:ret.refsupplprepaypk,
				refSupplPrepayNo:ret.refsupplprepayno,
				thisAmount:ret.thisamount==""?0:ret.thisamount,
				splDescr:ret.spldescr
			},
			height:350,
			width:1000
		});
	});
	
	$("#delete").on("click",function(){
		var id = $("#dataTable").jqGrid("getGridParam","selrow");
		if (!id) {
			art.dialog({
				content: "请选择一条记录"
			});
			return;
		}
		
		art.dialog({
			content:"是否删除该记录？",
			lock: true,
			width: 200,
			height: 80,
			ok: function () {
				Global.JqGrid.delRowData("dataTable",id);
			},
			cancel: function () {
				return true;
			}
		});
	});
	
	$("#custPay").on("click",function(){
		var ret = selectDataTableRow();
		rowId = $("#dataTable").jqGrid('getGridParam', 'selrow');
		if (!ret) {
			art.dialog({
				content: "请选择一条记录"
			});
			return;
		}
		
		Global.Dialog.showDialog("custPay",{
		  title:"客户付款",
		  url:"${ctx}/admin/custPay/goReturnPayCustPay?code="+ret.custcode,
		  postData:{
		  	m_umState:"V",
		  },
		  height:750,
		  width:1300,
		  returnFun: goto_query
		});
	});
	
    function disableOrEnableTypeChange() {
        var type = $("#type");
        var dataTable = $("#dataTable");
        var records = dataTable.getGridParam("records");
        
        // typeof records is "number", so records greater than zero is true 
        if (records) {
            type.attr("disabled", true);
        } else {
            type.removeAttr("disabled");
        }
    }
	
	$("#saveBtn").on("click",function(){
		$("#page_form").bootstrapValidator("validate");
		if(!$("#page_form").data("bootstrapValidator").isValid()) return;
		
		var datas = $("#page_form").jsonForm();
		var param= Global.JqGrid.allToJson("dataTable");
		if(param.datas.length == 0){
			art.dialog({content: "请先增加要退款的客户信息",width: 220});
			return;
		}
		/* 将datas（json）合并到param（json）中 */
		$.extend(param,datas);
		//console.log(param);
		//---------------------------
		var splJson = Global.JqGrid.allToJson("dataTable","custcode");		
		$.ajax({
			url : "${ctx}/admin/returnPay/checkHasCustCode",
			type : "post",
			data :{'no': $.trim($("#no").val()),hasCustCode: splJson["fieldJson"]},
			dataType : "json",
			cache : false,
			async :false,
			error : function(obj) {
				showAjaxHtml({
					"hidden" : false,
					"msg" : "保存数据出错~"
				});
			},
			success : function(obj) {
				if(obj.length>0){
					var sMsg='';
					$.each(obj, function(k, v){    
	                	sMsg=sMsg+'【'+v.CustCode+'】';
	                });  
					
					art.dialog({
						 content:"已存在客户编号为"+sMsg+"的申请单,是否确定保存？",
						 lock: true,
						 width: 200,
						 height: 100,
						 ok: function () {
							doSave(param);
						 },
						cancel: function () {
							return true;
						}
					}); 
				}else{
					doSave(param);
				}
			}
		});
	});
	
	$("#confirmPass").on("click",function(){
		$("#page_form").bootstrapValidator("validate");
		if(!$("#page_form").data("bootstrapValidator").isValid()) return;
		
		var datas = $("#page_form").jsonForm();
		$.ajax({
			url:"${ctx}/admin/returnPay/doConfirmPass",
			type: "post",
			data: datas,
			dataType: "json",
			cache: false,
			error: function(obj){
				showAjaxHtml({"hidden": false, "msg": "保存数据出错"});
		    },
		    success: function(obj){
		    	if(obj.rs){
		    		art.dialog({
						content: obj.msg,
						time: 1000,
						beforeunload: function () {
		    				closeWin();
					    }
					});
		    	}else{
		    		$("#_form_token_uniq_id").val(obj.token.token);
		    		art.dialog({
						content: obj.msg,
						width: 200
					});
		    	}
	    	}
		});
		
	});
	
	$("#confirmCancel").on("click",function(){
		$("#page_form").bootstrapValidator("validate");
		if(!$("#page_form").data("bootstrapValidator").isValid()) return;
		
		var datas = $("#page_form").jsonForm();
		$.ajax({
			url:"${ctx}/admin/returnPay/doConfirmCancel",
			type: "post",
			data: datas,
			dataType: "json",
			cache: false,
			error: function(obj){
				showAjaxHtml({"hidden": false, "msg": "保存数据出错"});
		    },
		    success: function(obj){
		    	if(obj.rs){
		    		art.dialog({
						content: obj.msg,
						time: 1000,
						beforeunload: function () {
		    				closeWin();
					    }
					});
		    	}else{
		    		$("#_form_token_uniq_id").val(obj.token.token);
		    		art.dialog({
						content: obj.msg,
						width: 200
					});
		    	}
	    	}
		});
		
	});
	
	$("#doExcel").on("click",function(){
		// var url = "${ctx}/admin/returnPay/doExcelForDetail";
		// doExcelAll(url);
		doExcelNow('退款客户','dataTable','page_form')
		
	});
	
	function doSave(param){
	 	$.ajax({
			url:"${ctx}/admin/returnPay/doSave",
			type: "post",
			data: param,
			dataType: "json",
			cache: false,
			error: function(obj){
				showAjaxHtml({"hidden": false, "msg": "保存数据出错"});
		    },
		    success: function(obj){
		    	if(obj.rs){
		    		art.dialog({
						content: obj.msg,
						time: 1000,
						beforeunload: function () {
		    				closeWin();
					    }
					});
		    	}else{
		    		$("#_form_token_uniq_id").val(obj.token.token);
		    		art.dialog({
						content: obj.msg,
						width: 200
					});
		    	}
	    	}
		});
	}
	
    $("#batchCheck").on("click", function() {
		$.ajax({
		    url: "${ctx}/admin/returnPay/doBatchCheck",
		    type: "post",
		    data: $("#page_form").jsonForm(),
		    dataType: "json",
		    cache: false,
		    error: function(obj) {
		        showAjaxHtml({"hidden": false, "msg": "保存数据出错"});
		    },
		    success: function(obj) {
		        if (obj.rs) {
		            art.dialog({
		                content: obj.msg,
		                time: 1000,
		                beforeunload: function() {
		                    closeWin(false);
		                }
		            });
		        } else {
		            $("#_form_token_uniq_id").val(obj.token.token);
		            art.dialog({
		                content: obj.msg,
		                width: 300
		            });
		        }
		    }
		});
    });

</script>	
</body>
</html>


