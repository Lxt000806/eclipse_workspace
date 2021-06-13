<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>新增预付金管理页面</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_supplier.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
	//校验函数
	$(function() {
		$("#splcode").openComponent_supplier({showValue:"${supplierPrepayDetail.splcode}",readonly:true });
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
	function save(){
		$("#dataForm").bootstrapValidator('validate');
		if(!$("#dataForm").data('bootstrapValidator').isValid()) return;
			//if(!$("#dataForm").valid()) {return false;}//表单校验
			var splcode=$.trim($("#splcode").val());//预付的供应商编号
			if (splcode=="") {
				art.dialog({
						content: "请选择供应商！",
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
			if (amount==0){
				art.dialog({
						content: "付款金额不能为0！",
						width: 200
					});
				return false;	
			}
			var selectRows = [];		 
			var datas=$("#dataForm").jsonForm();					
			selectRows.push(datas);	
			Global.Dialog.returnData = selectRows;	
			closeWin();
	}
</script>	
</head>
<body >
<div class="body-box-form" >
	<div class="panel panel-system" >
	    <div class="panel-body" >
	      <div class="btn-group-xs" >
		      <button type="button" id="saveBtn"  onclick="save()" class="btn btn-system" >保存</button>
		      <button type="button" class="btn btn-system" onclick="closeWin(false)">关闭</button>
	      </div>
	    </div>
	</div>
	<div class="panel panel-info" >  
         <div class="panel-body">
             <form role="form" class="form-search" id="dataForm" action="" method="post" target="targetFrame">
					<house:token></house:token>
					<input type="hidden" name="m_umState" id="m_umState" value="A"/>
				<ul class="ul-form">
					<li class="form-validate">
						<label ><span class="required">*</span>供应商编号</label>
						<input type="text" id="splcode" name="splcode" value="${supplierPrepayDetail.splcode}" readonly="true" />
					</li>
					<li class="form-validate">
						<label >供应商名称</label>
						<input type="text" id="spldescr" name="spldescr" value="${supplierPrepayDetail.spldescr}" readonly="true"/>
					</li>			
					<li class="form-validate">
						<label >预付金额</label>
						<input name="prepaybalance" id="prepaybalance" type="text" value="${supplierPrepayDetail.prepaybalance}" readonly="true"/>								
					</li>
					<li class="form-validate">
						<label ><span class="required">*</span>付款金额</label>
						<input type="text" id="amount" name="amount" style="width:160px;" onkeyup="value=value.replace(/[^\-?\d.]/g,'')"  value="${supplierPrepayDetail.amount}"/>								
					<li class="form-validate">
						<label class="control-textarea" >备注：</label>
						<textarea id="remarks" name="remarks">${supplierPrepayDetail.remarks}</textarea>
					</li>
					<li hidden="true">
						<label>材料类型</label>
						<input type="text" id="itemtype1" name="itemtype1" value="${supplierPrepayDetail.itemtype1}"/>
						<label>类型</label>
						<input type="text" id="type" name="aaa" value="${supplierPrepayDetail.type}"/>
						<label><span class="required">*</span>供应商编号</label>
						<input type="text" id="oldsplcode" name="oldsplcode" value="${supplierPrepayDetail.splcode}" />
					</li>
				</ul>
			</form>
		</div>
	</div>
</div>

</body>
</html>
