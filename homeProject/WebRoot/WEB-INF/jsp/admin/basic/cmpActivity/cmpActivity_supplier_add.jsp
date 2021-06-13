<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>Supplier列表</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_supplier.js?v=${v}" type="text/javascript"></script>

<script type="text/javascript">
/**初始化表格*/
$(function(){
	$("#code").openComponent_supplier({callBack:validateRefresh_choice,condition:{existSuppl:'1',actNo:$.trim('${cmpactivity.no}')}});
	$("#page_form").bootstrapValidator({
		message : 'This value is not valid',
		feedbackIcons : {/*input状态样式图片*/
			validating : 'glyphicon glyphicon-refresh'
		},
		fields: {  
			type:{  
				validators: {  
					notEmpty: {  
						message: '类型不能为空'  
					}
				}  
			},
		     openComponent_supplier_code:{  
		        validators: {  
		            notEmpty: {  
		           		 message: '供应商编号不能为空'  ,
		            },
		            remote: {
	    	            message: '',
	    	            url: '${ctx}/admin/supplier/getSupplier',
	    	            data: getValidateVal,  
	    	            delay: 4 //这里特别要说明，必须要加此属性，否则用户输入一个字就会访问后台一次，会消耗大量的系统资源，
	    	        }
		        }  
		     },
		},
		submitButtons : 'input[type="submit"]'/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
	});
});
function validateRefresh_choice(){
	$('#page_form').data('bootstrapValidator')
	               .updateStatus('openComponent_supplier_code', 'NOT_VALIDATED',null)  
	               .validateField('openComponent_supplier_code'); 
}

$(function(){
	$("#saveBtn").on("click",function(){
		$("#page_form").bootstrapValidator('validate');
		if(!$("#page_form").data('bootstrapValidator').isValid()) return;
		var datas = $("#page_form").serialize();
		$.ajax({
			url:'${ctx}/admin/cmpActivity/doSaveSuppl',
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
							$("#code").val("");
							$("#openComponent_supplier_code").val('');
							$("#type").val('');
							$("#page_form").bootstrapValidator("addField", "openComponent_supplier_code", {  
								validators: {  
					            	notEmpty: {  
						           		 message: '业务员不能为空'  ,
						            },
					                remote: {
					    	            message: '',
					    	            url: '${ctx}/admin/supplier/getSupplier',
					    	            data: getValidateVal,  
					    	            delay: 4 //这里特别要说明，必须要加此属性，否则用户输入一个字就会访问后台一次，会消耗大量的系统资源，
					    	        }
					            }  
					        });
							$("#page_form").bootstrapValidator("addField", "type", {  
								validators: {  
					            	notEmpty: {  
						           		 message: '类型不能为空'  ,
						            },
					            }  
					        });
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
</head>
    
<body>
	<div class="body-box-list">
		<div class="panel panel-system" >
		    <div class="panel-body" >
		      <div class="btn-group-xs" >
		     	 <button type="button" class="btn btn-system " id="saveBtn">
					<span>保存</span>
				 </button>
			     <button type="button" class="btn btn-system "   onclick="closeWin()">关闭</button>
		      </div>
		    </div>
		</div>
		<div class="query-form">
			<form action="" method="post" id="page_form" class="form-search">
					<input type="hidden" id="no" name="no" value="${cmpactivity.no}" />
					<ul class="ul-form">
						<li class="form-validate"> 
						<label>供应商编号</label>
							<input type="text" id="code" name="code" value="${supplier.code}" />
						</li>
						<li class="form-validate"> 
							<label>供应商类型</label>
							<house:xtdm id="type" dictCode="ACTSPLTYPE"  value="${supplier.type }" ></house:xtdm>
						</li>
				</ul>
			</form>
		</div>
	</div>	
</body>
</html>


