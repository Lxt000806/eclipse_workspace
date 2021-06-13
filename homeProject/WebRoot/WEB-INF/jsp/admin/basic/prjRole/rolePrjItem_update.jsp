<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>工程角色施工项目表添加</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>

<script type="text/javascript">
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
			prjItem:{  
				validators: {  
					notEmpty: {  
						message: '施工项目不能为空'  
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
	var str1=$("#prjItem").find("option:selected").text();	
	str1 = str1.split(' ');//先按照空格分割成数组
	str1=str1[0];
	var str=$("#unSelected").val();	
	str = str.split(',');//先按照空格分割成数组
	var prjItemDescr=$("#prjItemDescr").val();
	var prjItemDescr1=$("#prjItemDescr1").val();
	if (prjItemDescr==prjItemDescr1){
	}else{
		for (var i=0; i<str.length;i++){
			if (str[i]==str1){
			art.dialog({content: prjItemDescr+"已经存在！请重新选择",width: 200});
				return false; 
				}
		}
	}
	var selectRows = [];		 
	var datas=$("#dataForm").jsonForm();					
		selectRows.push(datas);		
	Global.Dialog.returnData = selectRows;			
	closeWin();
});	
function validateRefresh(){
	$('#dataForm').data('bootstrapValidator')
                  .updateStatus('prjItem', 'NOT_VALIDATED',null)  
                  .validateField('prjItem');
};	
});

function prjRolechange(){	
	var str=$("#prjRole").find("option:selected").text();	
	str = str.split(' ');//先按照空格分割成数组
	document.getElementById("prjRoleDescr").value=str;   
}

function PrjItemchange(){	
	var str=$("#prjItem").find("option:selected").text();	
	str = str.split(' ');//先按照空格分割成数组
	str=str[1];
	document.getElementById("prjItemDescr").value=str;   
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
	<div class="panel panel-info" >  
         <div class="panel-body">
             <form role="form" class="form-search" id="dataForm"  action="" method="post" target="targetFrame">
					<house:token></house:token>
					<input type="hidden" name="m_umState" id="m_umState" value="A"/>
				<ul class="ul-form">
					<li class="form-validate" hidden="true">
						<label><span class="required">*</span>工程角色</label>
						<house:dict id="prjRole" dictCode="" sql=" select Code,Descr from tPrjRole order by Code " 
								sqlValueKey="Code" sqlLableKey="Descr"  value="${prjRolePrjItem.prjRole}"  onchange="prjRolechange()"></house:dict>
					</li>				
					<li class="form-validate">
						<label><span class="required">*</span>施工项目</label>
							<house:xtdm  id="prjItem" dictCode="PRJITEM"    value="${prjRolePrjItem.prjItem}" onchange="PrjItemchange()"></house:xtdm>
					</li>
					<li hidden="true"> 
						<label>工程角色名称</label>	
						<input  name="prjRoleDescr" id="prjRoleDescr" value="${prjRolePrjItem.prjRoleDescr}" />
						
					</li>
					<li hidden="true"> 
						<label>施工项目名称</label>	
						<input type="text" name="prjItemDescr" id="prjItemDescr" value="${prjRolePrjItem.prjItemDescr}"/>
						<input type="text" name="prjItemDescr1" id="prjItemDescr1" value="${prjRolePrjItem.prjItemDescr}"/>
						<input type="text" name="unSelected" id="unSelected" value="${prjRolePrjItem.unSelected}"/>
					</li>
				</ul>
			</form>
		</div>
	</div>
</div>
<script src="${resourceRoot}/pub/component_item.js?v=${v}" type="text/javascript"></script>
</body>
</html>
