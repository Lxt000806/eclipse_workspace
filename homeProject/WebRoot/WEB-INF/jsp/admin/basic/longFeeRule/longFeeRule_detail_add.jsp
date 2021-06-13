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
//校验函数	
$(function() {
	$("#dataForm").bootstrapValidator({
			message : 'This value is not valid',
			feedbackIcons : {/*input状态样式图片*/
			},
			fields: {  
				longFee:{  
					validators: {  
						notEmpty: {  
							message: '远程费不能为空'  
						}
					}  
				},
				sendRegion:{  
					validators: {  
						notEmpty: {  
							message: '配送区域不能为空'  
						}
					}  
				},
				smallSendType:{  
					validators: {  
						notEmpty: {  
							message: '少量配送类型不能为空'  
						}
					}  
				},
				smallSendMaxValue:{  
					validators: {  
						notEmpty: {  
							message: '少量配送最大值不能为空'  
						}
					}  
				},
			},
			submitButtons : 'input[type="submit"]'/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
	});	
	if("${longFeeRule.m_umState}"=="V"){
		$("#sendRegion,#longFee,#saveBtn").attr("disabled",true);
	}
	$("#saveBtn").on("click",function(){	
		$("#dataForm").bootstrapValidator('validate'); //验证表单
		if(!$("#dataForm").data('bootstrapValidator').isValid()) return; //验证表单
		var sendRegion=$("#sendRegion").val();
		var sendRegions="${longFeeRule.sendRegions}";
		if("${longFeeRule.m_umState}"=="M"){
			if(sendRegions.indexOf(sendRegion)>-1 && sendRegion!=$.trim("${longFeeRule.sendRegion}")){
				art.dialog({    	
					content: "配送区域重复！"
				});
				return;
			}
		}else{
			if(sendRegions.indexOf(sendRegion)>-1){
				art.dialog({    	
					content: "配送区域重复！"
				});
				return;
			}
		}
		var selectRows = [];		 
		var datas=$("#dataForm").jsonForm();					
			selectRows.push(datas);		
	    console.log(datas); 
		Global.Dialog.returnData = selectRows;			
		closeWin();
	});	
});
function validateRefresh(){
	$('#dataForm').data('bootstrapValidator')
	               .updateStatus('sendRegion', 'NOT_VALIDATED',null)  
	               .validateField('sendRegion')
	               .updateStatus('longFee', 'NOT_VALIDATED',null)  
	               .validateField('longFee');;
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
					<house:token></house:token>
					<input type="hidden" id="sendRegionDescr"name="sendRegionDescr" value="${longFeeRule.sendRegionDescr }"/>
					<input type="hidden" id="smallSendTypeDescr"name="smallSendTypeDescr" value="${longFeeRule.smallSendTypeDescr }"/>
					<ul class="ul-form">
						<li class="form-validate"><label style="width:120px"><span
								class="required">*</span>配送区域</label> <house:dict
								id="sendRegion" dictCode=""
								sql="select a.No,a.No+' '+a.descr descr  from tSendRegion a where a.Expired='F' and SendType = '${longFeeRule.sendType}' or isnull(SendType, '') = '' "
								sqlValueKey="No" sqlLableKey="Descr" onchange="getDescrByCode('sendRegion','sendRegionDescr')"
								value="${longFeeRule.sendRegion.trim()}">
							</house:dict>
						</li>
						<li class="form-validate"><label style="width:120px"><span
								class="required">*</span> 远程费</label> <input type="text" id="longFee"
							name="longFee" value="${longFeeRule.longFee }"
							onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\.\d]/g,'');" />
						</li>
						<li class="form-validate">
							<label style="width:120px"><span class="required">*</span>少量配送类型</label>
							<house:xtdm id="smallSendType" dictCode="SmallSendType" value="${longFeeRule.smallSendType }" onchange="getDescrByCode('smallSendType','smallSendTypeDescr')"></house:xtdm>
						</li>
						<li class="form-validate"><label style="width:120px"><span
								class="required">*</span> 少量配送最大值</label> <input type="text" id="smallSendMaxValue"
							name="smallSendMaxValue" value="${longFeeRule.smallSendMaxValue }" 
							onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\.\d]/g,'');"/>
						</li>
					</ul>
				</form>
			</div>
		</div>
	</div>
</body>
</html>
