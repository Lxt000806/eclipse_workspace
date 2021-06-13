<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>查看预付金管理页面</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
<script type="text/javascript">
//校验函数
$(function() {
	$("#dataForm").validate({
		rules: {
			"itemcode": {
				validIllegalChar: true,
				maxlength: 60,
				required: true
			},
			"unitprice": {
				validIllegalChar: true,
				required: true
			}
		}
	});
});
</script>
</head>
<body>
<div class="body-box-form" >
	<div class="panel panel-system" >
    <div class="panel-body" >
      <div class="btn-group-xs" >
		<button type="button" class="btn btn-system" onclick="closeWin(false)">关闭</button>
      </div>
   	</div>
	</div>
	<div class="panel panel-info" >  
         <div class="panel-body">
             <form role="form" class="form-search" id="dataForm" action="" method="post" target="targetFrame">
					<house:token></house:token>
				<ul class="ul-form">
					<li >
						<label><span class="required">*</span>采购单号</label>
						<input type="text" id="puNo" name="puNo" value="${supplierPrepayDetail.puNo}" readonly="true"/>
					</li>
					<li >
						<label>供应商</label>
						<input type="text" id="splcode" name="splcode" style="width:79px;" value="${supplierPrepayDetail.splcode}" readonly="true"/>
						<input type="text" id="spldescr" name="spldescr" style="width:79px;" value="${supplierPrepayDetail.spldescr}" readonly="true"/>
					</li>
					<li >
						<label>应付余额</label>
						<input name="remainamount" id="remainamount"  
							onafterpaste="this.value=this.value.replace(/\D/g,'')" type="text" value="${supplierPrepayDetail.remainamount}" readonly="true"/>								
					</li>
					<li >
						<label>已付定金</label>
						<input name="firstamount" id="firstamount"  
							onafterpaste="this.value=this.value.replace(/\D/g,'')" type="text" value="${supplierPrepayDetail.firstamount}" readonly="true"/>								
					</li>
					<li >
						<label><span class="required">*</span>付款金额</label>
						<input name="amount" id="amount" 
							onkeyup="value=value.replace(/[^\-?\d.]/g,'')"
							onafterpaste="this.value=this.value.replace(/\D/g,'')" type="text" value="${supplierPrepayDetail.amount}" />								
					</li>
					<li>
						<label class="control-textarea" >备注：</label>
						<textarea id="remarks" name="remarks">${supplierPrepayDetail.remarks}</textarea>
					</li>
					<li hidden="true">
						<label>材料类型</label>
						<input type="text" id="itemtype1" name="itemtype1" value="${supplierPrepayDetail.itemtype1}"/>
						<label>类型</label>
						<input type="text" id="type" name="aaa" value="${supplierPrepayDetail.type}"/>
						<label>状态</label>
						<input type="text" id="status" name="status"/>
						<label>总金额</label>
						<input type="text" id="sumamount" name="sumamount"/>
					</li>
				</ul>
			</form>
		</div>
	</div>
</div>
<script src="${resourceRoot}/pub/component_supplier.js?v=${v}" type="text/javascript"></script>
<script src="${resourceRoot}/pub/component_purchase.js?v=${v}" type="text/javascript"></script>
<script src="${resourceRoot}/pub/component_item.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
$("#saveBtn").on("click",function(){
	if(!$("#dataForm").valid()) {return false;}//表单校验
	var  puNo=$.trim($("#puNo").val());//定金的采购单号
	var amount=$.trim($("#amount").val());//定金的付款金额
	if (puNo=="") {
		art.dialog({
				content: "请选择采购单号！",
				width: 200
			});
		return false;	
	}
	if (amount=="") {
		art.dialog({
				content: "请填写付款金额！",
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
