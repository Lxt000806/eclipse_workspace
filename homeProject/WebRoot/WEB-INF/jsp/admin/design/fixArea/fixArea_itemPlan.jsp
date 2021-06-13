
 <%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>预算区域</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp"%>
<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
<script src="${resourceRoot}/pub/component_prePlanArea.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
 //校验函数
$(function() {	
	$("#custCode").openComponent_customer({showLabel:"${fixArea.custDescr}",showValue:"${fixArea.custCode}",
		callBack:function(data){
			 getData(data);	
      	}	
	});
	if("${fixArea.custCode}"!=""){
		$("#prePlanAreaPK").openComponent_prePlanArea({showLabel:"${fixArea.prePlanAreaDescr}",showValue:"${fixArea.prePlanAreaPK}",
			disabled:$("#m_umState").val() == "M"?true:false,	
			condition: {custCode:"${fixArea.custCode}"},
				callBack:function(data){
					$("#descr").val(data.descr);
					$("#prePlanAreaDescr").val(data.descr);
	      			validateRefresh('openComponent_prePlanArea_prePlanAreaPK');
	      	    }
	    });	
	}
	
	$("#dataForm").bootstrapValidator({
		message : 'This value is not valid',
		feedbackIcons : {/*input状态样式图片*/
			validating : 'glyphicon glyphicon-refresh'
		},
		fields: {  
			custCode:{  
				validators: {  
					 notEmpty: {  
						message: '客户编号不能为空'  
					},
					stringLength: {
                           max: 20,
                           message: '长度不超过20个字符'
                    }
				}  
			},	
			descr: {
		        validators: { 
		        	notEmpty: {  
						message: '装修区域名称不能为空'  
					},
		        	stringLength:{
	               	 	min: 0,
	          			max: 50,
	               		message:'装修区域名称长度必须在0-400之间' 
	               	}
		        }
      		},
      		
      		openComponent_prePlanArea_prePlanAreaPK: {  
		        validators: {  
		             remote: {
			            message: '',
			            url: '${ctx}/admin/prePlanArea/getPrePlanArea',
			            data: getValidateVal,  
			            delay:4 //这里特别要说明，必须要加此属性，否则用户输入一个字就会访问后台一次，会消耗大量的系统资源，
			        }     
		        }  
	        }, 
	        
		},
		submitButtons : 'input[type="submit"]'/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
	});
});
function getData(data){
	if (!data) return;
	$("#address").val(data.address),
	validateRefresh('openComponent_customer_custCode'); 
	$("#prePlanAreaPK").openComponent_prePlanArea({condition: {custCode:data.code},
		callBack:function(data){
     			validateRefresh('openComponent_prePlanArea_prePlanAreaPK');
     		}
     	});	
} 

function save(){
	$("#dataForm").bootstrapValidator('validate');
	if(!$("#dataForm").data('bootstrapValidator').isValid()) return;
 	var datas = $("#dataForm").serialize();
 	var datas = $("#dataForm").serialize();
	var descr = $("#descr").val();
	var prePlanAreaDescr = $("#prePlanAreaDescr").val();
	if(descr=='水电项目'||descr=='土建项目'||descr=='安装项目'||descr=='综合项目'){
		art.dialog({
			content: "不能在预算中新增或编辑"+descr+"区域"
		});
		return;
	}
	if(descr !="" && prePlanAreaDescr!="" && descr.indexOf(prePlanAreaDescr) != 0){
		art.dialog({
			content: '区域名称的前缀必须是空间名称',
			width: 200
		});
		return;
	} 
	
	if($.trim($("#openComponent_prePlanArea_prePlanAreaPK").val())=="" && "${fixArea.mustImportTemp}"=='1'){
		art.dialog({
			content: '请选择对应空间!'
		});
		return;
	}
 	var url='';
 	if($("#m_umState").val() == "A"){
 		url='${ctx}/admin/fixArea/doSave';
 	}else if($("#m_umState").val() == "M"){
 		url='${ctx}/admin/fixArea/doUpdate';
 	}else if($("#m_umState").val() == "I"){
 		url='${ctx}/admin/fixArea/doInsertFixArea';
 	}
	
	$.ajax({		
		type: 'post',
		url: url,
		data: datas,
		dataType: 'json',
		cache: false,
		error: function(obj){
			showAjaxHtml({"hidden": false, "msg": '保存数据出错~'});
	    },
	    success: function(obj){
	    	if(obj.rs){
	    		$("#_form_token_uniq_id").val(obj.token.token);
	    		art.dialog({
					content: obj.msg,
					time: 500,
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
}
	
</script>
</head>
<body>
	<div class="body-box-form">
		<div class="panel panel-system">
			<div class="panel-body">
				<div class="btn-group-xs">
					<button id="saveBut" type="button" class="btn btn-system " onclick="save()">保存</button>
					<button id="closeBut" type="button" class="btn btn-system " onclick="closeWin()">关闭</button>
				</div>
			</div>
		</div>
		<div class="panel panel-info">
			<div class="panel-body">
				<form action="" method="post" id="dataForm" class="form-search">
					<house:token></house:token>
					<input type="hidden" id="m_umState" name="m_umState" value="${fixArea.m_umState}" />
					<input type="hidden" id='pk' name="pk" value="${fixArea.pk}" />
					<input type="hidden" id="prePlanAreaDescr" name="prePlanAreaDescr" />
					<input type="hidden" id="itemType1" name="itemType1" value="${fixArea.itemType1}" />
					<ul class="ul-form">
							<li class="form-validate" hidden="true"><label><span class="required">*</span>客户编号</label> <input type="text"
								id="custCode" name="custCode" value="${fixArea.custCode}" />
							</li>
							<li  class="form-validate" hidden="true"><label style="color:#777777">楼盘</label> <input type="text" id="address"
								name="address" value="${fixArea.address}" readonly="readonly" />
							</li>
							<li class="form-validate"><label>对应空间</label> <input type="text" id="prePlanAreaPK" name="prePlanAreaPK" />
							</li>
							<li class="form-validate"><label><span class="required">*</span>装修区域名称</label> <input type="text" id="descr"
								name="descr" value="${fixArea.descr}" />
							</li>
							
							<li class="form-validate" hidden="true"><label><span class="required">*</span>显示顺序</label> <input type="text"
									id="dispSeq" name="dispSeq" value="${fixArea.dispSeq}"  onkeyup="value=value.replace(/[^\-?\d]/g,'')"/>
							</li>
							<li  class="form-validate" hidden="true"><label><span class="required">*</span>服务性产品</label> <house:xtdm
								id="isService" dictCode="YESNO" value="${fixArea.isService }"></house:xtdm>
							</li>
							
						</div>
					</ul>
				</form>
			</div>
		</div>
	</div>
</body>
</html>

