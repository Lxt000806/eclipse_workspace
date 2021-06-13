<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>接收ItemApp</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>

<script type="text/javascript">
var parentWin=window.opener;
$(function(){
	$document = $(document);
	$('div.panelBar', $document).panelBar();

	if ('${itemApp.isAutoArriveDate }' == 'true') {
		$("#arriveDate").attr("onfocus", "").attr("readonly", "true");
	}
	
	if ("${itemApp.sendType}" == "2") { //仓库发货，加工费，物流配送费不让编辑
		$("#otherCost").attr("readonly","true");
		$("#otherCostAdj").attr("readonly","true");
	}
	
	//初始化表格
	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/supplierItemApp/goJqGridDetail?id=${itemApp.no}",
		height:$(document).height()-$("#content-list").offset().top-65,
		styleUI: 'Bootstrap',
		rowNum: 10000,
		colModel : [
		  {name : 'itemcode',index : 'a.itemcode',width : 100,label:'材料编号',align : "left",count:true},
		  {name : 'itemdescr',index : 'a.itemdescr',width : 220,label:'材料名称',align : "left"},
		  {name : "fixareadescr",index : "a.fixareadescr",width : 100,label:"区域",align : "left"},
		  {name : "intproddescr",index : "a.intproddescr",width : 100,label:"集成产品",align : "left",hidden:true},
		  {name : 'qty',index : 'a.qty',width : 100,label:'数量',align : "right",sum:true},
		  {name : 'uomdescr',index : 'a.uomdescr',width : 60,label:'单位',align : "left"},
		  {name : 'processcost',index : 'processcost',width : 100,label:'其他费用',align : "right",sum:true},
		  {name : 'remarks',index : 'a.remarks',width : 350,label:'备注',align : "left"}
          ],
        gridComplete: function() {
/*         	if ($.trim("${itemApp.itemType1}") == "JC" && "${itemApp.sendType}" == "1") {
				var datas = $("#dataTable").jqGrid("getRowData");
        		for (var i = 1; i<=datas.length; i++) {
        			$("#dataTable").jqGrid('setCell', i, 'qty', '0', 'not-editable-cell');
        		}
			} */
			if("${itemApp.supplyRecvModel}" == "1"){
				$("#dataTable").jqGrid("showCol", "qty");
			}else if("${itemApp.supplyRecvModel}" == "2"){
				var datas = $("#dataTable").jqGrid("getRowData");
        		for (var i = 1; i<=datas.length; i++) {
				  	if(!($.trim("${itemApp.itemType1}") == "JC" && ("${itemApp.splRcvCZY}" != "" || "${itemApp.splRcvDate}" != ""))){
        				$("#dataTable").jqGrid("setCell", i, "qty", "0", "not-editable-cell");
        			}
        		}
			}else{
				$("#dataTable").jqGrid("hideCol", "qty");
			}
			if ($.trim("${itemApp.itemType1}") == "JC") {
				$("#dataTable").jqGrid("showCol", "intproddescr");
			}
        } 
	});
	
	if ($.trim("${itemApp.itemType1}") == "JC") {
		$("#lblOtherCost").text("安装费");
		$("#lblOtherCostAdj").text("售后费");
	}  
});

function onProductTypeChange() {
	if ($("#productType").val() == "") {
		return;
	}

	$.ajax({
		url:'${ctx}/admin/supplierItemApp/doCalcArriveDateBySendDay',
		type: 'post',
		data: {no:$("#no").val(),productType:$("#productType").val()},
		dataType: 'json',
		cache: false,
		error: function(obj){
			showAjaxHtml({"hidden": false, "msg": '无法计算预计发货日期~'});
	    },
	    success: function(obj){
	    	if (obj.rows[0]) {
	    		var d = new Date();
	    		d.setTime(obj.rows[0].arrivedate);
	    		$("#arriveDate").val(d.format("yyyy-MM-dd"));
	    	} else {
	    		$("#arriveDate").val("");
	    	}
	    }
	 });
}

function receive(){
	//if(!$("#dataForm").valid()) {return false;}//表单校验
	//if($("#infoBoxDiv").css("display")!='none'){return false;}
	//验证
	
	$("#dataForm").bootstrapValidator('validate');
	if(!$("#dataForm").data('bootstrapValidator').isValid()) return;
	var datas = $("#dataForm").serialize();
	
	$.ajax({
		url:'${ctx}/admin/supplierItemApp/doReceive',
		type: 'post',
		data: datas,
		dataType: 'json',
		cache: false,
		error: function(obj){
			art.dialog({content: '接收出错~', width: 200});
			//showAjaxHtml({"hidden": false, "msg": '接收出错~'});
	    },
	    success: function(obj){
	    	if(obj.rs){
	    		art.dialog({
					content: obj.msg,
					time: 1000,
					beforeunload: function () {
						if(parentWin != null)
				        	parentWin.goto_query();
	    				closeWin();
				    }
				});
	    	}else{
	    		art.dialog({
					content: obj.msg,
					width: 200
				});
	    	}
	    }
	 });
	
}

//校验函数
$(function() {   
	/*
	$("#dataForm").validate({
		rules: {
				"arriveDate": {
				maxlength: 23,
				required: true
				},
				"splRemark": {
				validIllegalChar: true,
				maxlength: 1000
				},
				"otherCost": {
				number: true,
				maxlength: 19,
				required: true
				},
				"otherCostAdj": {
				number: true,
				maxlength: 19,
				required: true
				},
				"productType": {
				required: true
				}
		}
	});
	*/
	$("#dataForm").bootstrapValidator({
		message: "This value is not valid",
        feedbackIcons: { /*input状态样式图片*/
			/* valid: "glyphicon glyphicon-ok",
			invalid: "glyphicon glyphicon-remove", */
			validating: "glyphicon glyphicon-refresh"
        },
        fields: {  
	    	productType: {  
	        	validators: {  
	            	notEmpty: {  
	            		message: "必填字段"  
	            	}  
	        	}  
	        },
	        otherCost: {  
	        	validators: {  
	            	notEmpty: {  
	            		message: "必填字段"  
	            	},
	            	numeric: {
	            		message: "请输入数值"  
	            	}  
	        	}  
	        },
	        otherCostAdj: {  
	        	validators: {  
	            	notEmpty: {  
	            		message: "必填字段"  
	            	},
	            	numeric: {
	            		message: "请输入数值"  
	            	}   
	        	}  
	        }              
    	},
		submitButtons : 'input[type="submit"]' /*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
	});
});

//时间重新验证
function validateRefresh(){
	$('#dataForm').data('bootstrapValidator')  
                  .updateStatus('arriveDate', 'NOT_VALIDATED',null)  
                  .validateField('arriveDate');                    
}
</script>

</head>
    
<body onunload="closeWin()">
<div class="body-box-form" >
	<div class="panel panel-system">
	    <div class="panel-body" >
	    	<div class="btn-group-xs" >
	      	<button type="button" class="btn btn-system " id="receiveBut" onclick="receive()">接收</button>
	      	<button type="button" class="btn btn-system" onclick="closeWin(false)">关闭</button>
	      	</div>
	   </div>
	</div>
	
	<div class="panel panel-info"> 
		<div class="panel-body">
			<form role="form" class="form-search" id="dataForm"  action="" method="post" target="targetFrame">
				<house:token></house:token>	
				<input type="hidden" name="m_umState" id="m_umState" value="M"/>
				
				<ul class="ul-form">
					<div class="validate-group row" >
						<div class="col-sm-6" >
							<li>
								<label>领料单号 </label>
								<input type="text" id="no" name="no" value="${itemApp.no}" readonly="readonly"/>
							</li>
						</div>
						<div class="col-sm-6" >
							<li>
								<label>单据状态 </label>
								<house:xtdm id="status" dictCode="ITEMAPPSTATUS" value="${itemApp.status}" disabled="true"></house:xtdm>
							</li>
						</div>
					</div>
					<div class="validate-group row" >
						<div class="col-sm-6" >
							<li>
								<label>项目经理 </label>
								<input type="text" id="projectMan" name="projectMan" value="${itemApp.projectMan}" readonly="readonly"/>
							</li>
						</div>
						<div class="col-sm-6" >
							<li>
								<label>电话号码</label>
								<input type="text" id="phone" name="phone" value="${itemApp.phone}" readonly="readonly"/>
							</li>
						</div>
					</div>
					<div class="validate-group row" >
						<div class="col-sm-6" >
							<li>
								<label>楼盘地址 </label>
								<input type="text" id="custAddress" name="custAddress" value="${itemApp.custAddress}" readonly="readonly" />
							</li>
						</div>
						<div class="col-sm-6" >
							<li>
								<label>供应商状态</label>
								<house:xtdm id="splStatus" dictCode="APPSPLSTATUS" value="${itemApp.splStatus}" disabled="true"></house:xtdm>
							</li>
						</div>
					</div>
					<div class="validate-group row" >
						<div class="col-sm-6 form-validate" >
							<li>
								<label>产品类型 </label>
								<house:xtdm id="productType" dictCode="APPPRODUCTTYPE" value="${itemApp.productType}" onchange="onProductTypeChange();"></house:xtdm>
							</li>
						</div>
						<div class="col-sm-6 form-validate" >
							<li>
								<label>预计到货日期</label>
								<input type="text" id="arriveDate" name="arriveDate" class="i-date" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${itemApp.arriveDate }' pattern='yyyy-MM-dd'/>" onchange="validateRefresh()" required data-bv-notempty-message="预计到货日期不能为空"/>
							</li>
						</div>
					</div>
					<div class="validate-group row" >
						<div class="col-sm-6 form-validate" >
							<li>
								<label id="lblOtherCost">加工费 </label>
								<input type="text" id="otherCost" name="otherCost" value="${itemApp.otherCost}" />
							</li>
						</div>
						<div class="col-sm-6 form-validate" >
							<li>
								<label id="lblOtherCostAdj">物流配送费</label>
								<input type="text" id="otherCostAdj" name="otherCostAdj" value="${itemApp.otherCostAdj}" readonly/>
							</li>
						</div>
					</div>
					<div class="validate-group row">
						<div class="col-sm-12" >
							<li>
								<label class="control-textarea">备注 </label>
								<textarea id="splRemark" name="splRemark" style="width:645px;" rows="3">${itemApp.splRemark}</textarea>
							</li>	
						</div>
					</div>
				</ul>
			</form>
		</div>
	</div>
	
	<div id="content-list">
		<table id= "dataTable"></table> 
	</div> 
</div>
</body>
</html>
