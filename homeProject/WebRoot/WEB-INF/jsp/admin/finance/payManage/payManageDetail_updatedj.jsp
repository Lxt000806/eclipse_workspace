<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>编辑预付金管理页面</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
</head>
<body >
<div class="body-box-form" >
	<div class="panel panel-system" >
    <div class="panel-body" >
      <div class="btn-group-xs" >
       	<button type="button" id="saveBtn" class="btn btn-system" >保存</button>
		<button type="button" class="btn btn-system" onclick="closeWin(false)">关闭</button>
      </div>
   	</div>
	</div>
	<div class="panel panel-info" >  
         <div class="panel-body">
             <form role="form" class="form-search" id="dataForm" action="" method="post" target="targetFrame">
             		<input type="hidden" name="jsonString" value="" />
					<house:token></house:token>
				<ul class="ul-form">
					<li class="form-validate">
						<label><span class="required">*</span>采购单号</label>
						<input type="text" id="puNo" name="puNo" value="${supplierPrepayDetail.puNo}" readonly="true"/>
					</li>
					<li  class="form-validate">
						<label>供应商</label>
						<input type="text" id="splcode" name="splcode" style="width:79px;" value="${supplierPrepayDetail.splcode}" readonly="true"/>
						<input type="text" id="spldescr" name="spldescr" style="width:79px;" value="${supplierPrepayDetail.spldescr}" readonly="true"/>
					</li>
					<li class="form-validate" >
						<label>应付余额</label>
						<input name="remainamount" id="remainamount"  
							onafterpaste="this.value=this.value.replace(/\D/g,'')" type="text" value="${supplierPrepayDetail.remainamount}" readonly="true"/>								
					</li>
					<li class="form-validate" >
						<label>已付定金</label>
						<input name="firstamount" id="firstamount"  
							onafterpaste="this.value=this.value.replace(/\D/g,'')" type="text" value="${supplierPrepayDetail.firstamount}" readonly="true"/>								
					</li>
					<li class="form-validate" >
						<label><span class="required">*</span>付款金额</label>
						<input type="text" id="amount" name="amount" style="width:160px;" onkeyup="value=value.replace(/[^\-?\d.]/g,'')"  value="${supplierPrepayDetail.amount}"/>	
														
					</li>
					<li class="form-validate">
						<label class="control-textarea" >备注：</label>
						<textarea id="remarks" name="remarks">${supplierPrepayDetail.remarks}</textarea>
					</li>
					<li hidden="true">
						<label>材料类型</label>
						<input type="text" id="itemtype1" name="itemtype1" value="${supplierPrepayDetail.itemtype1}"/>
						<label>类型</label>
						<input type="text" id="type" name="type" value="${supplierPrepayDetail.type}"/>
						<label>状态</label>
						<input type="text" id="status" name="status"/>
						<label>总金额</label>
						<input type="text" id="sumamount" name="sumamount" value="${supplierPrepayDetail.sumamount}"/>
					</li>
				</ul>
			</form>
		</div>
	</div>
</div>
<script src="${resourceRoot}/pub/component_supplier.js?v=${v}" type="text/javascript"></script>
<script src="${resourceRoot}/pub/component_purchase.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
$(function() {
	$("#puNo").openComponent_purchase({showValue:"${supplierPrepayDetail.puNo}",readonly:true} );
	$("#dataForm").bootstrapValidator({
		message: "This value is not valid",
        feedbackIcons: { /*input状态样式图片*/
			/* valid: "glyphicon glyphicon-ok",
			invalid: "glyphicon glyphicon-remove", */
			validating: "glyphicon glyphicon-refresh"
        },
        fields: {  
	    	amount: {
	        validators: { 
	            numeric: {
	            	message: '付款金额只能是数字' 
	            },
	            notEmpty: { 
	            		message: '付款金额不能为空'  
	            }
	        }
      	 },
	      	
    	},
		submitButtons : 'input[type="submit"]' /*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
	});	
});
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
$("#saveBtn").on("click",function(){
	$("#dataForm").bootstrapValidator('validate');
	if(!$("#dataForm").data('bootstrapValidator').isValid()) return;
	if ($.trim($("#amount").val())=="") {
		art.dialog({
				content: "请填写付款金额！",
				width: 200
			});
		return false;	
	}
	if ($.trim($("#amount").val())=="") {
		art.dialog({
				content: "请填写付款金额！",
				width: 200
			});
		return false;	
	}
	var amount=parseFloat($.trim($("#amount").val()));//付款金额
	var remainamount=parseFloat($.trim($("#remainamount").val()));//应付金额
	var firstamount=parseFloat($.trim($("#firstamount").val()));//已付定金
	if (amount==0){
	    art.dialog({
				content: "付款金额不能为0！",
				width: 200
			});
		return false;	
	}
	var errMsg=CheckAmount(remainamount, firstamount, amount);
   	if(errMsg!=""){
		art.dialog({
			content: errMsg,
			width: 200
		});
		return  false;	
	}
	if ($.trim($("#puNo").val())=="") {
		art.dialog({
				content: "请选择采购单号！",
				width: 200
			});
		return false;	
	}
	var selectRows = [];		 
	var datas=$("#dataForm").jsonForm();					
		selectRows.push(datas);	 	
	Global.Dialog.returnData = selectRows;	
	closeWin();
});
</script>
</body>
</html>
