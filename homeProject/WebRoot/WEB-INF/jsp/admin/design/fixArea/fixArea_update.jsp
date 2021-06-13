
 <%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>添加CustLoan</title>
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
	$("#custCode").openComponent_customer({showLabel:"${fixArea.custDescr}",showValue:"${fixArea.custCode}",callBack: getData});
	Global.LinkSelect.initSelect("${ctx}/admin/item/itemTypeByAuthority","itemType1");
	Global.LinkSelect.setSelect({firstSelect:"itemType1",
								firstValue:"${fixArea.itemType1}",		
	});	  
	$("#prePlanAreaPK").openComponent_prePlanArea({showLabel:"${fixArea.prePlanAreaDescr}",showValue:"${fixArea.prePlanAreaPK}",condition: {custCode:"${fixArea.custCode}"},
			callBack:function(data){
      		validateRefresh('openComponent_prePlanArea_prePlanAreaPK');}
    });	
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
      		itemType1: {
		        validators: { 
		        	notEmpty: {  
						message: '材料类型1不能为空'  
					},
		        }
      		},
      		dispSeq: {
		        validators: { 
		        	notEmpty: {  
						message: '显示顺序不能为空'  
					},
					numeric: {
		            	message: '显示顺序只能是数字' 
		            },
		        }
      		},
      		IsService: {
		        validators: { 
		        	notEmpty: {  
						message: '服务性产品不能为空'  
					},
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
	        openComponent_customer_custCode: {  
		        validators: {  
		             remote: {
			            message: '',
			            url: '${ctx}/admin/customer/getCustomer',
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
     			validateRefresh('openComponent_prePlanArea_prePlanAreaPK');}
     	});	
}
function save(){
	//$("#dataForm").bootstrapValidator('validate');
	//if(!$("#dataForm").data('bootstrapValidator').isValid()) return;
 	var datas = $("#dataForm").serialize();	
	$.ajax({
		url:'${ctx}/admin/fixArea/doUpdate',
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
					time: 3000,
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
					<input type="hidden"id='pk' name="pk" value="${fixArea.pk}" />
					<ul class="ul-form">
							<li class="form-validate"><label><span class="required">*</span>客户编号</label> <input type="text"
								id="custCode" name="custCode" value="${fixArea.custCode}" />
							</li>
							<li class="form-validate"><label style="color:#777777">楼盘</label> <input type="text" id="address"
								name="address" value="${fixArea.address}" readonly="readonly" />
							</li>
							<li class="form-validate"><label><span class="required">*</span>装修区域名称</label> <input type="text" id="address"
								name="descr" value="${fixArea.descr}" />
							</li>
							
							<li class="form-validate"><label><span class="required">*</span>显示顺序</label> <input type="text"
								id="dispSeq" name="dispSeq" value="${fixArea.dispSeq}"  onkeyup="value=value.replace(/[^\-?\d]/g,'')"/>
							</li>
							<li class="form-validate"><label><span class="required">*</span>材料类型1</label> <select id="itemType1"
								name="itemType1"></select>
							</li>
							<li  class="form-validate"><label><span class="required">*</span>服务性产品</label> <house:xtdm
								id="isService" dictCode="YESNO" value="${fixArea.isService }"></house:xtdm>
							</li>
							<li class="form-validate"><label>对应空间</label> <input type="text" id="prePlanAreaPK" name="prePlanAreaPK" />
							</li>
						   <li id="expired_show" class="form-validate"><label>过期</label> <input type="checkbox" id="expired"
							name="expired" value="${fixArea.expired}" onclick="checkExpired(this)" ${fixArea.expired== "T"?"checked":""}/>
						  </li>
					</ul>
				</form>
			</div>
		</div>
	</div>
</body>
</html>

