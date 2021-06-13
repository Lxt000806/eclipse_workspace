<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<!DOCTYPE html>
<html>
<head>
<title>材料--库存信息</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp"%>
<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
<style type="text/css">
.form-search .ul-form li {
	padding-left: 1px;
}
</style>
<script type="text/javascript"> 
$(function(){
	$("#saveBtn").on("click",function(){
		$("#dataForm").bootstrapValidator("validate");
		if(!$("#dataForm").data("bootstrapValidator").isValid()) return;
		var datas = $("#dataForm").serialize();
		$.ajax({
			url:'${ctx}/admin/item/doUpdateInvInfo',
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
	});
	
	//校验函数
 	$("#dataForm").bootstrapValidator({
		message : 'This value is not valid',
		feedbackIcons : {
			validating : 'glyphicon glyphicon-refresh'
		},
		fields: {  
			 perNum: {
		         validators: { 
		            notEmpty: { 
		            	message: '单位片数不能为空'  
		            },
		             numeric: {
		            	message: '单位片数只能是数字' 
		            },
	        	}
      	    },
	      	 perWeight: {
		        validators: { 
		            notEmpty: { 
		            	message: '单位重量不能为空'  
		            },
		             numeric: {
		            	message: '单位重量只能是数字' 
		            },
		        }
	      	},
	      	 packageNum: {
		        validators: { 
		            notEmpty: { 
		            	message: '每箱片数不能为空'  
		            },
		             numeric: {
		            	message: '每箱片数只能是数字' 
		            },
		        }
	      	},
	      	volume: {
		        validators: { 
		            notEmpty: { 
		            	message: '体积不能为空'  
		            },
		             numeric: {
		            	message: '体积只能是数字' 
		            },
		        }
	      	},
	      	size: {
		        validators: { 
		            notEmpty: { 
		            	message: '尺寸不能为空'  
		            },
		             numeric: {
		            	message: '尺寸只能是数字' 
		            },
		        }
	      	},
	      	minQty: {
		        validators: { 
		            notEmpty: { 
		            	message: '最小库存量不能为空'  
		            },
		             numeric: {
		            	message: '最小库存量只能是数字' 
		            },
		        }
	      	},
		},
		submitButtons : 'input[type="submit"]'
	});	
});
</script>
</head>
<body>
	<div class="body-box-list">
		<div class="panel panel-system">
			<div class="panel-body">
				<div class="btn-group-xs">
					<button type="button" class="btn btn-system " id="saveBtn">
						<span>保存</span>
					</button>
					<button type="button" class="btn btn-system " id="closeBut" onclick="closeWin(false)">
						<span>关闭</span>
					</button>
				</div>
			</div>
		</div>
		<div class="panel panel-info">
			<div class="panel-body">
				<form role="form" class="form-search" id="dataForm" action="" method="post" target="targetFrame">
					<input type="hidden" name="jsonString" value="" />
					<ul class="ul-form">
						<li class="form-validate"><label>材料编号</label> <input type="text" style="width:160px;" id="code"
							name="code" value="${item.code }" readonly="readonly" />
						</li>
						<li class="form-validate"><label>型号</label> <input type="text" style="width:160px;" id="model"
							name="model" value="${item.model}" readonly="readonly" />
						</li>
						<li class="form-validate"><label>材料名称</label> <input type="text" style="width:160px;" id="desc"
							name="desc" value="${item.descr }" readonly="readonly" />
						</li>
						<li class="form-validate"><label>规格</label> <input type="text" style="width:160px;" id="sizeDesc"
							name="SizeDesc" value="${item.sizeDesc}" readonly="readonly" />
						</li>
						<li class="form-validate"><label>供应商</label> <input type="text" id="supplDescr" name="supplDescr"
							value="${item.supplDescr}" readonly="readonly" />
						<li class="form-validate"><label>单位</label> <input type="text" id="uom" name="uom"
							style="width:160px;" value="${item.uomDescr}" readonly="readonly" />
						</li>
						<li class="form-validate"><label><span class="required">*</span>单位重量</label> <input type="text"
							id="perWeight" name="perWeight" onkeyup="value=value.replace(/[^\-?\d.]/g,'')"
							value="${item.perWeight}" />
						</li>
						<li class="form-validate"><label><span class="required">*</span>尺寸</label> <input type="text"
							id="size" name="size" onkeyup="value=value.replace(/[^\d.]/g,'')" value="${item.size}" />
						</li>
						<li class="form-validate"><label><span class="required">*</span>最小库存量</label> <input type="text"
							id="minQty" name="minQty" onkeyup="value=value.replace(/[^\-?\d.]/g,'')" value="${item.minQty}" />
						</li>
						<li class="form-validate"><label><span class="required">*</span>单位片数</label> <input type="text"
							id="perNum" name="perNum" onkeyup="value=value.replace(/[^\d.]/g,'')" value="${item.perNum}" />
						</li>
						<li class="form-validate"><label><span class="required">*</span>每箱片数</label> <input type="text"
							id="packageNum" onkeyup="value=value.replace(/[^\d.]/g,'')" name="packageNum"
							value="${item.packageNum}" />
						</li>
						<li class="form-validate"><label><span class="required">*</span>体积</label> <input type="text"
							id="volume" name="volume" onkeyup="value=value.replace(/[^\-?\d.]/g,'')" value="${item.volume}" /><span>立方米</span>
						</li>
					</ul>
				</form>
			</div>
		</div>
	</div>
</body>
</html>
