<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<title>人工费用-新增，修改，查看</title>
	<%@ include file="/commons/jsp/common.jsp" %>
</head>
<body>
<div class="body-box-form" >
	<div class="panel panel-system" >
		<div class="panel-body" >
			<div class="btn-group-xs" >
		      	<button type="button" id="saveBtn" class="btn btn-system " >保存</button>
				<button type="button" class="btn btn-system" onclick="closeWin(false)">关闭</button>
			</div>
		</div>
	</div>
	<div class="panel panel-info" >  
         <div class="panel-body">
             <form role="form" class="form-search" id="dataForm" action="" method="post" target="targetFrame">
				<input type="hidden" name="exportData" id="exportData">
				<input type="hidden" id="expired" name="expired" value="${laborFeeType.expired }"/>
				<input type="hidden" name="jsonString" value=""/>
				<ul class="ul-form">
					<li class="form-validate">
						<label><span class="required">*</span>编号</label>
						<input type="text" id="code" name="code" value="${laborFeeType.code}" />
					</li>
					<li class="form-validate">
						<label><span class="required">*</span>材料类型1</label>
						<select id="itemType1" name="itemType1"  ></select>	
					</li>	
					<li class="form-validate">
						<label ><span class="required">*</span>材料类型12</label>
						<select id="itemType12" name="itemType12"  ></select>
					</li>
					<li class="form-validate">
						<label ><span class="required">*</span>费用名称</label>
						<input type="text" id="descr" name="descr" value="${laborFeeType.descr}" />
					</li>
					<li class="form-validate">
						<label><span class="required">*</span>是否计成本</label>
						<house:xtdm id="isCalCost" dictCode="YESNO" value="${laborFeeType.isCalCost}"></house:xtdm>
					</li>
					<li class="form-validate">
						<label><span class="required">*</span>是否填发单编号</label>
						<house:xtdm id="isHaveSendNo" dictCode="YESNO" value="${laborFeeType.isHaveSendNo}"></house:xtdm>
					</li>
					<li class="form-validate">
						<label><span class="required">*</span>费用大类</label>
						<house:xtdm id="feeBigType" dictCode="FEEBIGTYPE" value="${laborFeeType.feeBigType}"></house:xtdm>
					</li>
					<li id="chekbox_show">
						<label>过期</label>
						<input type="checkbox" id="expired_show" name="expired_show" value="${laborFeeType.expired }",
							onclick="checkExpired(this)" ${laborFeeType.expired=="T"?"checked":"" }/>
					</li>
				</ul>
			</form>
		</div>
	</div>
</div>	
<script src="${resourceRoot}/pub/component_wareHouse.js?v=${v}" type="text/javascript"></script>
<script>
	$(function() {
		
		Global.LinkSelect.initSelect("${ctx}/admin/itemType1/byItemType1","itemType1","itemType12","laborFeeType");
		
		Global.LinkSelect.setSelect({
			firstSelect:"itemType1",
			firstValue:"${laborFeeType.itemType1}",
			secondSelect:"itemType12",
			secondValue:"${laborFeeType.itemType12}"
		});	
		
		$("#itemType1").change(function(){
  			$("#itemType12 option[value='']").remove();
  			$("#dataForm").data("bootstrapValidator")
		                  .updateStatus("itemType12", "NOT_VALIDATED",null)   
		                  .validateField("itemType12");   
		});
		
		if("V"=="${laborFeeType.m_umState}"){
			$("#saveBtn").hide();
			disabledForm("dataForm");
		}else if("A"=="${laborFeeType.m_umState}"){
			$("#chekbox_show").hide();
		}else {
			$("#code").prop("readonly","readonly");
		}
	
	
		
		$("#dataForm").bootstrapValidator({
			message : "This value is not valid",
			feedbackIcons : {/*input状态样式图片*/
				validating : "glyphicon glyphicon-refresh"
			},
			fields: { 
				code:{  
					validators: {  
						notEmpty: {  
							message: "请输入完整的信息"  
						}              
					}  
				},
				itemType1:{  
					validators: {  
						notEmpty: {  
							message: "请输入完整的信息"  
						}              
					}  
				},
				itemType12:{  
					validators: {  
						notEmpty: {  
							message: "请输入完整的信息"  
						}              
					}  
				},
				descr:{  
					validators: {  
						notEmpty: {  
							message: "请输入完整的信息"  
						}              
					}  
				},
				isCalCost:{  
					validators: {  
						notEmpty: {  
							message: "请输入完整的信息"  
						}              
					}  
				},
				isHaveSendNo:{  
					validators: {  
						notEmpty: {  
							message: "请输入完整的信息"  
						}              
					}  
				},
				feeBigType:{  
					validators: {  
						notEmpty: {  
							message: "请输入完整的信息"  
						}              
					}  
				}
				
			}
		});
		
		$("#saveBtn").on("click",function(){
			$("#dataForm").bootstrapValidator("validate");
			if(!$("#dataForm").data("bootstrapValidator").isValid()) return;
			
			var datas = $("#dataForm").serialize();
			$.ajax({
				url:"M" == "${laborFeeType.m_umState}"?"${ctx}/admin/laborFeeType/doUpdate":"${ctx}/admin/laborFeeType/doSave",
				type: "post",
				data: datas,
				dataType: "json",
				cache: false,
				error: function(obj){
					showAjaxHtml({"hidden": false, "msg": "保存数据出错"});
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
		});
	});
	
	
	
	
	
</script>	
</body>
</html>


