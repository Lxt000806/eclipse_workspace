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
<script type="text/javascript">
function onchange1(){	
	var str=$("#itemType3").find("option:selected").text();	
	str = str.split(' ');//先按照空格分割成数组
	str=str[1];
	document.getElementById("itemType3Descr").value=str;   
}
$(function(){
	Global.LinkSelect.initSelect("${ctx}/admin/item/itemTypeByAuthority","itemType1","itemType2","itemType3");
	var dataSet = {
		firstSelect:"itemType1",
		secondSelect:"itemType2",
		thirdSelect:"itemType3",
		firstValue:'${carryRuleItem.itemType1}',
		secondValue:'${carryRuleItem.itemType2}',
		thirdValue:'${carryRuleItem.itemType3}',
		disabled:"true"
	};
	Global.LinkSelect.setSelect(dataSet);
});
//校验函数	
$(function() {
	$("#dataForm").validate({
		rules: {
			"itemcode": {
				validIllegalChar: true,
				maxlength: 60,
				required: true
			},			
		}
	});
function getData(data){
	if (!data) return;
	var dataSet = {
		firstSelect:"itemType1",
		firstValue:'${itemsetdetail.itemtype1}',
	};
	Global.LinkSelect.setSelect(dataSet);
	$('#itemcode').val(data.code);
	$('#itemcodedescr').val(data.descr);
}	
$(function() {
	$("#dataForm").bootstrapValidator({
			message : 'This value is not valid',
			feedbackIcons : {/*input状态样式图片*/
			},
			fields: {  
				itemType3:{  
					validators: {  
						notEmpty: {  
							message: '材料类型3不能为空'  
						}
					}  
				},
			},
			submitButtons : 'input[type="submit"]'/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
	});	
});
$("#saveBtn").on("click",function(){	
	$("#dataForm").bootstrapValidator('validate'); //验证表单
	if(!$("#dataForm").data('bootstrapValidator').isValid()) return; //验证表单
		var selectRows = [];		 
		var datas=$("#dataForm").jsonForm();					
			selectRows.push(datas);		
	    console.log(datas); 	
		Global.Dialog.returnData = selectRows;			
		closeWin();
});	
function validateRefresh(){
 $('#dataForm').data('bootstrapValidator')
               .updateStatus('itemType3', 'NOT_VALIDATED',null)  
               .validateField('itemType3');
};	
});
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
	<div class="panel panel-info" >  
         <div class="panel-body">
             <form role="form" class="form-search" id="dataForm"  action="" method="post" target="targetFrame">
					<house:token></house:token>
					<input type="hidden" name="m_umState" id="m_umState" value="A"/>
				<ul class="ul-form">
					<li hidden="true">							
						<label>材料类型1</label>
						<select id="itemType1" name="itemType1" style="width: 166px;"  value="${carryRuleItem.itemType1}"></select>
						<label>材料类型2</label>
						<select id="itemType2" name="itemType2" style="width: 166px;"  value="${carryRuleItem.itemType2}"></select>
						<label>材料名称</label>
						<input id="itemType3Descr" name="itemType3Descr" style="width: 166px;"  value="${carryRuleItem.itemType3Descr}"/>
					</li>
					<li class="form-validate">
						<label>材料类型3</label>
						<select id="itemType3" name="itemType3" style="width: 166px;"  value="${carryRuleItem.itemType3}" onchange="onchange1()"></select>
					</li>
				</ul>
			</form>
		</div>
	</div>
</div>
<script src="${resourceRoot}/pub/component_item.js?v=${v}" type="text/javascript"></script>
</body>
</html>
