<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>添加升级扣项规则明细</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_item.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_baseItem.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">

//校验函数

	$(function() {
	Global.LinkSelect.initSelect("${ctx}/admin/item/itemTypeByAuthority","itemType1","itemType2","itemType3");
	
		if("V"=="${mainCommiRule.m_umState}"||"M"=="${mainCommiRule.m_umState}"){
			if("V"=="${mainCommiRule.m_umState}"){
				disabledForm("dataForm");
				$("#saveBtn").hide();
			}
			Global.LinkSelect.setSelect({
			firstSelect:"itemType1",
			firstValue:"${mainCommiRule.itemType1}",
			secondSelect:"itemType2",
			secondValue:"${mainCommiRule.itemType2}",
			thirdSelect:"itemType3",
			thirdValue:"${mainCommiRule.itemType3}"
			});
		}else{
			Global.LinkSelect.setSelect({
			firstSelect:"itemType1",
			firstValue:"${mainCommiRule.itemType1}",
			secondSelect:"itemType2",
			secondValue:"",
			});
		}
	
			
		$("#dataForm").bootstrapValidator({
			message : 'This value is not valid',
			feedbackIcons : {/*input状态样式图片*/
				validating : "glyphicon glyphicon-refresh"
			},
			fields: {
				itemType2:{  
					validators: {  
						notEmpty: {  
							message: "请选择材料类型2"
						}
					}  
				},
				itemType3:{  
					validators: {  
						notEmpty: {  
							message: "请选择材料类型3"  
						}
					}  
				}
			},
		});	
	});
	
	
	
	
	
	function validateRefresh_descr(data){
		var code = data.code;
		$("#itemDescr").val(data.descr);
		$("#code").val(code);
	}

	function save(){
		$("#dataForm").bootstrapValidator("validate");
		if(!$("#dataForm").data("bootstrapValidator").isValid()) return;
		var arr=JSON.parse('${mainCommiRuleItemPkArray}');
		for(var i=0;i<arr.length;i++){
			if($.trim(arr[i])==$.trim($("#itemType3").val())){
				art.dialog({
						content:"材料类型3已存在",
					});
					return false;
			}
		}
		var selectRows = [];		 
		var datas=$("#dataForm").jsonForm();
		datas.itemType2Descr=$("#itemType2").find("option:selected").text();
		datas.itemType3Descr=$("#itemType3").find("option:selected").text();					
		selectRows.push(datas);		
		Global.Dialog.returnData = selectRows;			
		closeWin();
	};
	
	
</script>
</head>
<body>
<div class="body-box-form" >
	<div class="panel panel-system" >
	    <div class="panel-body" >
	      <div class="btn-group-xs" >
		      <button type="button" id="saveBtn" class="btn btn-system "   onclick="save()">保存</button>
		      <button type="button" class="btn btn-system "   onclick="closeWin(false)">关闭</button>
	      </div>
	    </div>
	</div>
	<div class="panel panel-info" >  
         <div class="panel-body">
             <form role="form" class="form-search" id="dataForm"  action="" method="post" target="targetFrame">
             	<input type="hidden" name="lastUpdatedBy" id="lastUpdatedBy" value="${mainCommiRule.lastUpdatedBy }"/>
             	<input type="hidden" id="mainCommiRuleItemPkArray" name=mainCommiRuleItemPkArray style="width:160px;" value="${mainCommiRuleItemPkArray }"/>
				<house:token></house:token>
				<ul class="ul-form">
					<li hidden="true">					
						<label>材料类型1</label>
						<select id="itemType1" name="itemType1" ></select>
					</li>
					<li class="form-validate">
						<label><span class="required">*</span>材料类型2</label>
						<select id="itemType2" name="itemType2"></select>
					</li>
					<li class="form-validate">
						<label><span class="required">*</span>材料类型3</label>
						<select id="itemType3" name="itemType3"></select>
					</li>
				</ul>
			</form>
		</div>
	</div>
</div>
</body>
</html>
