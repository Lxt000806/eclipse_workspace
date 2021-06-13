<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<title>客户列表</title>
	<%@ include file="/commons/jsp/common.jsp" %>
<script src="${resourceRoot}/pub/component_laborFeeType.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
/**材料类型1*/
$(function(){
	Global.LinkSelect.initSelect("${ctx}/admin/item/itemTypeByAuthority","itemType1","itemType2","itemType3");	
	var dataSet = {
		firstSelect:"itemType1",
		firstValue:'${itemtype12.itemType1}',
		disabled:"true"
	};
	Global.LinkSelect.setSelect(dataSet);
	$("#installFeeType").openComponent_laborFeeType({
		showValue:"${itemtype12.installFeeType}",
		showLabel:"${itemtype12.installFeeTypeDescr}",
		condition:{itemType1:"${itemtype12.itemType1}"}
	});
	$("#openComponent_laborFeeType_installFeeType").attr("readonly",true);
	$("#transFeeType").openComponent_laborFeeType({
		showValue:"${itemtype12.transFeeType}",
		showLabel:"${itemtype12.transFeeTypeDescr}",
		condition:{itemType1:"${itemtype12.itemType1}"}
	});
	$("#openComponent_laborFeeType_transFeeType").attr("readonly",true);
	setTimeout(changeItemType1, 500);
});
$(function() {
	$("#dataForm").bootstrapValidator({
		message : 'This value is not valid',
		feedbackIcons : {},
		fields: {  
			itemType1:{  
				validators: {  
					notEmpty: {  
						message: '材料类型1不能为空'  
					}
				}  
			},
			descr:{  
				validators: {  
					notEmpty: {  
						message: '材料名称不能为空'  
					}  
				}  
			},
			remarks:{
				validators:{
					stringLength: {
                    	max: 200,
                        message: '长度不超过200个字符'
                    } 
				}
			}
		},
		submitButtons : 'input[type="submit"]'/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
	});	
});
function save(){	
	$("#dataForm").bootstrapValidator('validate'); //验证表单
	if(!$("#dataForm").data('bootstrapValidator').isValid()) return; //验证表单
	var itemtype1 = $.trim($("#itemType1").val());	
	var dispseq=$.trim($("#dispseq").val());
	if (dispseq==""){
		document.getElementById("dispseq").value=0;		
	}
	var proper=$.trim($("#proper").val());
	if (proper==""){
		document.getElementById("proper").value=0;		
	}
	$("#dataForm").bootstrapValidator('validate');
	if(!$("#dataForm").data('bootstrapValidator').isValid()) return;
	var datas = $("#dataForm").serialize();
	$.ajax({
		url:'${ctx}/admin/itemType12/doitemtype12Update',
		type: 'post',
		data: datas,
		dataType: 'json',
		cache: false,
		error: function(obj){
			showAjaxHtml({"hidden": false, "msg": '保存数据出错~'});
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
};
function validateRefresh(){
	 $('#dataForm').data('bootstrapValidator')
	               .updateStatus('itemType1', 'NOT_VALIDATED',null)  
	               .validateField('itemType1')
	               .updateStatus('descr', 'NOT_VALIDATED',null)  
	               .validateField('descr')
	               .updateStatus('remarks', 'NOT_VALIDATED',null)  
		           .validateField('remarks');                     
};
function changeItemType1(){
	var itemType1=$("#itemType1").val();
	$("#installFeeType").openComponent_laborFeeType({
		showValue:"${itemtype12.installFeeType}",
		showLabel:"${itemtype12.installFeeTypeDescr}",
		condition:{itemType1:itemType1}
	});
	$("#openComponent_laborFeeType_installFeeType").attr("readonly",true);
	$("#transFeeType").openComponent_laborFeeType({
		showValue:"${itemtype12.transFeeType}",
		showLabel:"${itemtype12.transFeeTypeDescr}",
		condition:{itemType1:itemType1}
	});
	$("#openComponent_laborFeeType_transFeeType").attr("readonly",true);
	$("#openComponent_laborFeeType_installFeeType").next().unbind("click",alertFun);
	$("#openComponent_laborFeeType_transFeeType").next().unbind("click",alertFun);
	if(itemType1==""){
		$("#openComponent_laborFeeType_installFeeType").next().attr("data-disabled",true);
		$("#openComponent_laborFeeType_installFeeType").next().click(alertFun);
		$("#openComponent_laborFeeType_transFeeType").next().attr("data-disabled",true);
		$("#openComponent_laborFeeType_transFeeType").next().click(alertFun);
	}else{
		$("#openComponent_laborFeeType_installFeeType").next().attr("data-disabled",false);
		$("#openComponent_laborFeeType_transFeeType").next().attr("data-disabled",false);
	}
}
function alertFun(){
	art.dialog({    	
		content: "请先选择材料类型1"
	});
}
</script>
</head>
<body>
	<div class="body-box-form" >
	<div class="panel panel-system" >
	    <div class="panel-body" >
	      <div class="btn-group-xs" >
		      <button type="button" id="saveBtn" name="saveBtn" class="btn btn-system " onclick="save()">保存</button>
		      <button type="button" class="btn btn-system "   onclick="closeWin()">关闭</button>
	      </div>
	    </div>
	</div>
	<div class="panel panel-info" >  
         <div class="panel-body">
             <form role="form" class="form-search" id="dataForm"  action="" method="post" target="targetFrame">
					<house:token></house:token>
					<input type="hidden" name="m_umState" id="m_umState" value="A"/>
				<ul class="ul-form">
					<li>
						<label>编号</label>
						<input type="text" id="code" name="code"  readonly="true"  value="${itemtype12.code}"/>
					</li>
					<li class="form-validate">
						<label><span class="required">*</span>材料类型1</label>
							<select id="itemType1" name="itemType1"   onchange="changeItemType1()"></select>
					</li>
					<li class="form-validate"> 
						<label>名称</label>
						<input type="text" id="descr" name="descr"  value="${itemtype12.descr }" />
					</li>
					<li hidden="true">
						<label>名称</label>
						<input type="text" id="descr1" name="descr1"  value="${itemtype12.descr }" />
					</li>
					<li>
						<label>显示顺序</label>
						<input type="text" id="dispseq" name="dispseq" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"  value="${itemtype12.dispseq }" />
					</li>
					<li>
						<label>毛利率控制线</label>
						<input type="text" id="proper" name="proper"  onkeyup="value=value.replace(/[^\-?\d.]/g,'')"  value="${itemtype12.proper }" />
					</li>
					<li>
						<label>安装费用类型</label>
						<input type="text" id="installFeeType" name="installFeeType"  value="${itemtype12.installFeeType }" />
					</li>
					<li>
						<label>搬运费用类型</label>
						<input type="text" id="transFeeType" name="transFeeType"  value="${itemtype12.transFeeType }" />
					</li>
				</ul>
			</form>
		</div>
	</div>
</div>
</body>
</html>


