<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>客户类型--编辑</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp"%>
<script type="text/javascript">
//校验函数
$(function() {
	  $("#dataForm").bootstrapValidator({
            message : 'This value is not valid',
            feedbackIcons : {/*input状态样式图片*/
                validating : 'glyphicon glyphicon-refresh'
            },
            fields: {  
		        custType: {  
			        validators: {  
			            notEmpty: {  
			            message: '客户类型不能为空'  
			            }  
			        }  
		       }  
    		},
            submitButtons : 'input[type="submit"]'/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
        }).on('success.form.bv', function (e) {
   		 e.preventDefault();
   		 save();	
	});
   		
});
function save(){
    if(!$("#dataForm").data('bootstrapValidator').isValid()) return;
    var datas = $("#dataForm").serialize();
  	$.ajax({
		url:'${ctx}/admin/itemPlan/doUpdateCustType',
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
</script>

</head>
<body>
	<div class="body-box-form">
		<div class="panel panel-system">
			<div class="panel-body">
				<div class="btn-group-xs">
					<button type="button" class="btn btn-system" onclick="validateDataForm()">保存</button>
					<button type="button" class="btn btn-system" onclick="closeWin(false)">关闭</button>
					<span   style="font-size: 12px;color: red;margin-left:5px">修改客户类型将清空原有预算，并对原有预算进行备份</span>
				</div>
			</div>
		</div>
		<div class="panel panel-info">
			<div class="panel-body">
				<form role="form" class="form-search" id="dataForm" action="" method="post" target="targetFrame">
					<input type="hidden" id="code" name="code" value="${customer.code}" />
					<house:token></house:token>
					<ul class="ul-form">
						<div class="validate-group">
							<li><label>客户编号</label> <input type="text" id="custCode" name="custCode" value="${customer.code}"
								disabled="disabled" />
							</li>
							<li><label>客户名称</label> <input type="text" id="descr" name="descr" value="${customer.descr}"
								disabled="disabled" />
							</li>
							<li><label>楼盘</label> <input type="text" id="address" name="address" value="${customer.address }"
								disabled="disabled" />
							</li>
							<li class="form-validate"><label>客户类型</label> 
								<house:dict id="custType" dictCode="" 
									sql="select a.Code,a.code+' '+a.desc1 desc1 from tcustType a where a.Expired='F' order By a.Code " 
									sqlValueKey="Code" sqlLableKey="Desc1" value="${customer.custType}"  >
								</house:dict>
							</li>
							
						</div>
					</ul>
				</form>
			</div>
		</div>
	</div>
</body>
</html>
