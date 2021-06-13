<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>ItemApp列表</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_supplier.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>

<script type="text/javascript">
var parentWin=window.opener;
var docHeight=0;
$(function(){
	$document = $(document);
	docHeight = $document.height();
	$('div.panelBar', $document).panelBar();
	
	if ('${itemApp.isTimeout }' == 'false') {
		$("#delayReson").attr("disabled",true);
	}
	
    //初始化表格
	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/supplierItemApp/goJqGridDetail?id=${itemApp.no}",
		height:docHeight-$("#content-list").offset().top-65,
		styleUI: 'Bootstrap',
		rowNum: 10000,
		colModel : [
		  {name : 'itemcode',index : 'a.itemcode',width : 100,label:'材料编号',align : "left",count:true},
		  {name : 'itemdescr',index : 'a.itemdescr',width : 220,label:'材料名称',align : "left"},
		  {name : "fixareadescr",index : "fixareadescr",width : 100,label:"区域",align : "left"},
		  {name : "intproddescr",index : "intproddescr",width : 100,label:"集成产品",align : "left",hidden:true},
		  {name : 'qty',index : 'a.qty',width : 100,label:'数量',align : "right",sum:true},
		  {name : 'uomdescr',index : 'a.uomdescr',width : 100,label:'单位',align : "left"},
		  {name : 'remarks',index : 'a.remarks',width : 350,label:'备注',align : "left"}
           ],
          gridComplete:function(){
			if ($.trim("${itemApp.itemType1}") == "JC") {
				$("#dataTable").jqGrid("showCol", "intproddescr");
			}
          }
	}); 

	doDelayResonChange();
	// 下拉搜索功能更新后  下拉触发事件不用.change,用标签里面的onchange方法 
	// $("#delayReson").change(doDelayResonChange);
	
	if ($.trim("${itemApp.itemType1}") != "JC") {
		$("#trOtherCost").hide(); //只有集成领料单才显示安装费、售后费
	    setGridHeight();
    }      
});

function send(){
	//验证
	//if(!$("#dataForm").valid()) {return false;}//表单校验
	//if($("#infoBoxDiv").css("display")!='none'){return false;}
	$("#dataForm").bootstrapValidator('validate');
	if(!$("#dataForm").data('bootstrapValidator').isValid()) return;
	
	//延误原因为2.现场不具备条件时，延误原因必填
	if (($("#delayReson").val().trim() == "1" || $("#delayReson").val().trim() == "2") && $("#delayRemark").val().trim() == "") {
		art.dialog({content: "请输入延误说明！", width: 200});
		return;
	}	
	
	var datas = $("#dataForm").serialize();
	$.ajax({
		url:'${ctx}/admin/supplierItemApp/doSend',
		type: 'post',
		data: datas,
		dataType: 'json',
		cache: false,
		error: function(obj){
			//showAjaxHtml({"hidden": false, "msg": '保存数据出错~'});
			art.dialog({content: '保存数据出错~', width: 200});
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
	    		$("#_form_token_uniq_id").val(obj.token.token);
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
				"delayReson": {
				required: true
				},
				"otherCost": {
				number: true,
				required: true
				},
				"otherCostAdj": {
				number: true,
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
	    	delayReson: {  
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

//延误原因变化时调用
function doDelayResonChange(){
	console.log();
	if ($("#delayReson").val().trim() == "1" || $("#delayReson").val().trim() == "2") { //延误原因为1.厂家拖期 2.现场不具备条件时，延误原因必填
		$("#trDelayRemark").show();
	} else {
		$("#delayRemark").val("");
		$("#trDelayRemark").hide();
	}
	setGridHeight();
}
	
function setGridHeight() { //设置表格高度
	$("#dataTable").setGridHeight(docHeight-$("#content-list").offset().top-65+"px");	
}
</script>
</head>
    
<body onunload="closeWin()">
<div class="body-box-form" >
	<div class="panel panel-system">
	    <div class="panel-body" >
	    	<div class="btn-group-xs" >
	      	<button type="button" class="btn btn-system " id="saveBut" onclick="send()">发货</button>
	      	<button type="button" class="btn btn-system" onclick="closeWin(false)">关闭</button>
	      	</div>
	   </div>
	</div>
	
	<div class="panel panel-info"> 
		<div class="panel-body">
			<form role="form" class="form-search" id="dataForm"  action="" method="post" target="targetFrame">
				<house:token></house:token>
				<input type="hidden" name="m_umState" id="m_umState" value="S"/>
				<input type="hidden" id="no" name="no" value="${itemApp.no }" />
				
				<ul class="ul-form">
					<div class="validate-group row" >
						<div class="col-sm-6" >
							<li>
								<label>供应商编号</label>
								<input type="text" id="supplCode" name="supplCode"  value="${itemApp.supplCode}" readonly="readonly"/>
							</li>
						</div>
						<div class="col-sm-6" >
							<li>
								<label>供应商名称 </label>
								<input type="text" id="supplCodeDescr" name="supplCodeDescr"  value="${itemApp.supplCodeDescr}" readonly="readonly"/>
							</li>
						</div>
					</div>
					<div class="validate-group row" >
						<div class="col-sm-6" >
							<li>
								<label>发货日期 </label>
								<input type="text" id="sendDate" name="sendDate" class="i-date" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${itemApp.sendDate }' pattern='yyyy-MM-dd'/>" disabled="true"/>
							</li>
						</div>
						<div class="col-sm-6" >
							<li>
								<label>配送方式</label>
								<house:xtdm id="delivType" dictCode="DELIVTYPE" value="${itemApp.delivType}" disabled="true"/>
							</li>
						</div>
					</div>
					<div class="validate-group row" >
						<div class="col-sm-6" >
							<li>
								<label>产品类型</label>
								<house:xtdm id="productType" dictCode="APPPRODUCTTYPE" value="${itemApp.productType}" disabled="true"></house:xtdm>
							</li>
						</div>
						<div class="col-sm-6" >
							<li>
								<label>预计到货日期 </label>
								<input type="text" id="arriveDate" name="arriveDate" class="i-date" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
								value="<fmt:formatDate value='${itemApp.arriveDate }' pattern='yyyy-MM-dd'/>" readonly="readonly" disabled="true"/>
							</li>
						</div>
					</div>
					<div class="validate-group row" >
						<div class="col-sm-6 form-validate" >
							<li>
								<label>延误原因</label>
								<house:xtdm id="delayReson" dictCode="APPDELAYRESON" value="${itemApp.delayReson}" unShowValue="${itemApp.delayResonUnshowValue}" onchange="doDelayResonChange()"></house:xtdm>
							</li>
						</div>
					</div>
					<div class="validate-group row" id="trDelayRemark">
						<div class="col-sm-12" >
							<li>
								<label class="control-textarea">延误说明</label>
								<textarea id="delayRemark" name="delayRemark" style="width:635px;" value="${itemApp.delayRemark}" rows="3">${itemApp.delayRemark}</textarea>
							</li>	
						</div>
					</div>
					<div class="validate-group row" id="trOtherCost">
						<div class="col-sm-6 form-validate" >
							<li>
								<label id="lblOtherCost">安装费 </label>
								<input type="text" id="otherCost" name="otherCost"  value="${itemApp.otherCost}" />
							</li>
						</div>
						<div class="col-sm-6 form-validate" >
							<li>
								<label id="lblOtherCostAdj">售后费</label>
								<input type="text" id="otherCostAdj" name="otherCostAdj"  value="${itemApp.otherCostAdj}" />
							</li>
						</div>
					</div>
					<div class="row">
						<div class="col-sm-12">
							<li>
								<label class="control-textarea">备注</label>
								<textarea id="splRemark" name="splRemark" style="width:635px;" 
									rows="3">${itemApp.splRemark}</textarea>
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


