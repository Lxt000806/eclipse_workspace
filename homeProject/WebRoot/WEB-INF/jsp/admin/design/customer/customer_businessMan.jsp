<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>修改业务员</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
function save(){
	//$("#dataForm").bootstrapValidator('validate');
	if(!$("#dataForm").data('bootstrapValidator').isValid()) return;
	//验证
	var datas = {
	    code: '${customer.code}',
	    businessMan: $("#businessMan").val(),
	    againMan: $("#againMan").val()
	}
	
	$.ajax({
		url:'${ctx}/admin/customer/doUpdateBusinessMan',
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
}
//校验函数
$(function() {
	$("#dataForm").bootstrapValidator({
        message : 'This value is not valid',
        feedbackIcons : {/*input状态样式图片*/
            validating : 'glyphicon glyphicon-refresh'
        },
        fields: {
        	
      	},
        submitButtons : 'input[type="submit"]'/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
    }).on('success.form.bv', function (e) {
   		 e.preventDefault();
   		 save();
	});
	
	$("#saveBtn").hide()
	if ('${customer.status}' === '1' || '${customer.status}' === '2') {
	    $("#saveBtn").show()
	}
	
	if ('${customer.status}'!='1' && '${customer.status}'!='2'){
		$("#businessMan").openComponent_employee({showLabel:"${customer.businessManDescr}",showValue:"${customer.businessMan}",disabled:true});
	}else{
		$("#businessMan").openComponent_employee({showLabel:"${customer.businessManDescr}",showValue:"${customer.businessMan}"});
	}
	
	// 首先客户类型是软装客户，然后必有已经有翻单员，而且是“未到公司”或“已到公司”才可以修改翻单员
	// 张海洋 20200915
	if ('${customer.custType}'.trim() === '2' && '${customer.againMan}'
	    && ('${customer.status}' === '1' || '${customer.status}' === '2')) {
	    
	    $("#againMan").openComponent_employee({
	        showLabel: "${customer.againManDescr}",
	        showValue: "${customer.againMan}"
	    })
	} else {
	    $("#againMan").openComponent_employee({
            showLabel: "${customer.againManDescr}",
            showValue: "${customer.againMan}",
            disabled: true
        })
	}
	
	$("#dataForm").bootstrapValidator("addField", "openComponent_employee_businessMan", {  
        validators: {  
            notEmpty: {  
                message: '业务员不能为空'  
            },
            remote: {
	            message: '',
	            url: '${ctx}/admin/employee/getEmployee',
	            data: getValidateVal,  
	            delay: 4 //这里特别要说明，必须要加此属性，否则用户输入一个字就会访问后台一次，会消耗大量的系统资源，
	        }
        }  
    });
});
</script>
</head>
<body>
<div class="body-box-form" >
	<div class="panel panel-system">
	    <div class="panel-body" >
	      <div class="btn-group-xs" >
	      <button type="button" id="saveBtn" class="btn btn-system" onclick="validateDataForm()">保存</button>
	      <button type="button" class="btn btn-system" onclick="closeWin(false)">关闭</button>
	      </div>
	    </div>
	</div>
	<div class="panel panel-info">  
        <div class="panel-body">
            <form role="form" class="form-search" id="dataForm"  action="" method="post" target="targetFrame">
            	<house:token></house:token>
				<input type="hidden" name="m_umState" id="m_umState" value="M"/>
					<ul class="ul-form">
						<li>
							<label><span class="required">*</span>客户编号</label>
							<input type="text" style="width:160px;" id="code" name="code" value="${customer.code }" readonly="readonly"/>
						</li>
						<li>
							<label><span class="required">*</span>客户名称</label>
							<input type="text" style="width:160px;" id="descr" name="descr" value="${customer.descr }" readonly="readonly"/>
						</li>
						<li>
							<label><span class="required">*</span>楼盘</label>
							<input type="text" style="width:160px;" id="address" name="address" value="${customer.address }" readonly="readonly"/>
						</li>
						<li class="form-validate">
							<label>业务员</label>
							<input type="text" id="businessMan" name="businessMan" style="width:160px;"/>
						</li>
						<li>
							<label>翻单员</label>
							<input type="text" id="againMan" name="againMan" style="width:160px;"/>
						</li>
					</ul>
            	</form>
            </div>
          </div>
</div>
</body>
</html>
