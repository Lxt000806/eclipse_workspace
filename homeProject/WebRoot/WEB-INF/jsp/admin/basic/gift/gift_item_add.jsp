<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>添加材料明细</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_baseItem.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_item.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
//校验函数	
$(function() {
	$("#baseItemCode").openComponent_baseItem({
		showValue:"${gift.baseItemCode}",
		showLabel:"${gift.baseItemDescr}",
		callBack:function(data){
			$("#baseItemDescr").val(data.descr);
			validateRefresh_baseItemCode();
		},
	});
	$("#openComponent_baseItem_baseItemCode").attr("readonly",true);
	$("#itemCode").openComponent_item({
		showValue:"${gift.itemCode}",
		showLabel:"${gift.itemDescr}",
		condition:{itemType1:"${gift.quoteModule}".trim()},
		callBack:function(data){
			$("#itemDescr").val(data.descr);
			validateRefresh_itemCode();
		},
	});
	$("#openComponent_item_itemCode").attr("readonly",true);
	$("#dataForm").bootstrapValidator({
			message : 'This value is not valid',
			feedbackIcons : {/*input状态样式图片*/
			},
			fields: {  
				openComponent_baseItem_baseItemCode:{  
					validators: {  
						notEmpty: {  
							message: '基础项目编号不能为空'  
						}
					}  
				},
				openComponent_item_itemCode:{  
					validators: {  
						notEmpty: {  
							message: '材料编号不能为空'  
						}
					}  
				},
			},
			submitButtons : 'input[type="submit"]'/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
	});	
	if("${gift.quoteModule}".trim()=="JZ"){
		$("#itemCode").parent().attr("hidden","hidden");
		$("#itemCode").setComponent_item({readonly:true});
		$("#dataForm").bootstrapValidator("removeField","openComponent_item_itemCode");
	}else{	
		$("#baseItemCode").parent().attr("hidden","hidden");
		$("#baseItemCode").openComponent_baseItem({readonly:true});
		$("#dataForm").bootstrapValidator("removeField","openComponent_baseItem_baseItemCode");
	}
	if("${gift.m_umState}"=="V"){
		$("#saveBtn").attr("disabled",true);
	}
	$("#saveBtn").on("click",function(){	
		$("#dataForm").bootstrapValidator('validate'); //验证表单
		if(!$("#dataForm").data('bootstrapValidator').isValid()) return; //验证表单
		if("${gift.quoteModule}".trim()=="JZ"){
			var baseItemCode=$("#baseItemCode").val();
			var baseItemCodes="${gift.baseItemCodes}";
			if("${gift.m_umState}"=="M"){
				if(baseItemCodes.indexOf(baseItemCode)>-1 && baseItemCode!="${gift.baseItemCode}"){
					art.dialog({    	
						content: "基础项目编号重复！"
					});
					return;
				}
			}else{
				if(baseItemCodes.indexOf(baseItemCode)>-1){
					art.dialog({    	
						content: "基础项目编号重复！"
					});
					return;
				}
			} 
		}else{
			var itemCode=$("#itemCode").val();
			var itemCodes="${gift.itemCodes}";
			if("${gift.m_umState}"=="M"){
				if(itemCodes.indexOf(itemCode)>-1 && itemCode!="${gift.itemCode}"){
					art.dialog({    	
						content: "材料编号重复！"
					});
					return;
				}
			}else{
				if(itemCodes.indexOf(itemCode)>-1){
					art.dialog({    	
						content: "材料编号重复！"
					});
					return;
				}
			} 
		}
		var selectRows = [];		 
		var datas=$("#dataForm").jsonForm();					
			selectRows.push(datas);		
		Global.Dialog.returnData = selectRows;			
		closeWin();
	});	
});
function validateRefresh_baseItemCode(){
	$('#dataForm').data('bootstrapValidator')
	               .updateStatus('openComponent_baseItem_baseItemCode', 'NOT_VALIDATED',null)  
	               .validateField('openComponent_baseItem_baseItemCode');
}
function validateRefresh_itemCode(){
	$('#dataForm').data('bootstrapValidator')
	               .updateStatus('openComponent_item_itemCode', 'NOT_VALIDATED',null)  
	               .validateField('openComponent_item_itemCode');
}		
</script>
</head>
    
<body>
<div class="body-box-form" >
	<div class="panel panel-system" >
	    <div class="panel-body" >
	      <div class="btn-group-xs" >
		      <button type="button" id="saveBtn" class="btn btn-system "  >保存</button>
		      <button type="button" class="btn btn-system "   onclick="closeWin(false)">关闭</button>
	      </div>
	    </div>
	</div>
		<div class="panel panel-info">
			<div class="panel-body">
				<form role="form" class="form-search" id="dataForm" action=""
					method="post" target="targetFrame">
					 <input type="hidden" id="baseItemDescr" name="baseItemDescr" value="${gift.baseItemDescr}" />
					 <input type="hidden" id="itemDescr" name="itemDescr" value="${gift.itemDescr}" />
					<house:token></house:token>
					<ul class="ul-form">
						<div class="validate-group">
							<li class="form-validate"><label></span>基础项目编号</label> <input type="text" id="baseItemCode"
								name="baseItemCode"
								value="${gift.baseItemCode}" />
							</li>
						</div>
						<div class="validate-group">
							<li class="form-validate"><label></span>材料编号</label> <input type="text" id="itemCode"
								name="itemCode"
								value="${gift.itemCode}" />
							</li>
						</div>
					</ul>
				</form>
			</div>
		</div>
	</div>
</body>
</html>
