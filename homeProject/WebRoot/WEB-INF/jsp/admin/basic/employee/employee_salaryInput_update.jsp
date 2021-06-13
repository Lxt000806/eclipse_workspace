<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<!DOCTYPE html>
<html>
<head>
<title>员工信息--薪资录入</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp"%>
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
			url:'${ctx}/admin/employee/doUpdateSalary',
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
			 basicWage: {
		         validators: { 
		            notEmpty: { 
		            	message: '薪资不能为空'  
		            },
		             numeric: {
		            	message: '薪资只能是数字' 
		            },
	        	}
      	    },
	      	 perSocialInsur: {
		        validators: { 
		            notEmpty: { 
		            	message: '五险一金个人不能为空'  
		            },
		             numeric: {
		            	message: '五险一金个人只能是数字' 
		            },
		        }
	      	},
	      	 comSocialInsur: {
		        validators: { 
		            notEmpty: { 
		            	message: '五险一金企业不能为空'  
		            },
		             numeric: {
		            	message: '五险一金企业只能是数字' 
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
						<li class="form-validate"><label>员工号</label> <input type="text" style="width:160px;" id="number"
							name="number" value="${employee.number }" readonly="readonly" />
						</li>
						<li class="form-validate"><label>姓名</label> <input type="text" style="width:160px;" id="nameChi"
							name="nameChi" value="${employee.nameChi }" readonly="readonly" />
						</li>
						<li class="form-validate"><label><span class="required">*</span>薪资</label> <input type="text"
							id="basicWage" name="basicWage" onkeyup="value=value.replace(/[^\-?\d.]/g,'')" value="${employee.basicWage}" /><span>元</span>
						</li>
						<li class="form-validate"><label><span class="required">*</span>五险一金个人</label> <input type="text"
							id="perSocialInsur" name="perSocialInsur" onkeyup="value=value.replace(/[^\-?\d.]/g,'')" value="${employee.perSocialInsur}" /><span>元</span>
						</li>
						<li class="form-validate"><label><span class="required">*</span>五险一金企业</label> <input type="text"
							id="comSocialInsur" name="comSocialInsur" onkeyup="value=value.replace(/[^\-?\d.]/g,'')" value="${employee.comSocialInsur}" /><span>元</span>
						</li>
						<li class="form-validate"><label>社保所属公司</label> 
							<input type="text" style="width:160px;" id="insurSignCmp" name="insurSignCmp" 
									value="${employee.insurSignCmp }"/>
						</li>
					</ul>
				</form>
			</div>
		</div>
	</div>
</body>
</html>










