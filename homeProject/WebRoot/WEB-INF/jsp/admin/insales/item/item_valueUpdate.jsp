<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>材料信息--批量修改值</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp"%>
<script type="text/javascript">
function commiTypeChange(){
	 if ($.trim($("#commiType").val())=="1"){
		  $("#uom_show").show();
	 }else{
	 	  $("#uom_show").hide();
	 }	    
}
//保存
function save(){
	if ($.trim($("#expired").val())=="" && $.trim($("#commiType").val())=="" && $.trim($("#commiPerc").val())==""){
		art.dialog({content: "没有设置批量修改值",width: 200});
		return false;
  	} 
  	if (($.trim($("#commiType").val())=="1"||$.trim($("#commiType").val())=="2")&& $.trim($("#commiPerc").val())==""){
		art.dialog({content: "提成比例未填写",width: 200});
		return false;
 	 } 
	 var sCode="${item.code}";
	 var sExpired=$.trim($("#expired").val());
	 var sCommiType=$.trim($("#commiType").val());
	 var sCommiPerc=$.trim($("#commiPerc").val());
	 $.ajax({
		url:"${ctx}/admin/item/doUpdateBatch",
		type:'post',
		data:{code:sCode,expired:sExpired,commiType:sCommiType,commiPerc:sCommiPerc},
		cache:false,
		error:function(obj){
			showAjaxHtml({"hidden": false, "msg": '获取数据出错~'});
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
$(function(){
  $('#uom_show').hide();
  $("#dataForm").bootstrapValidator({
            message : 'This value is not valid',
            feedbackIcons : {/*input状态样式图片*/
                validating : 'glyphicon glyphicon-refresh'
            },
            fields: {  
	        commiPerc: {
		        validators: { 
		            notEmpty: { 
		            	message: '提成比例不能为空'  
		            },
		             numeric: {
		            	message: '提成比例只能是数字' 
		            },
		        }
      		},
    	},
            submitButtons : 'input[type="submit"]'/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
        });

})

</script>
</head>
<body>
	<div class="body-box-form">
		<div class="panel panel-system">
			<div class="panel-body">
				<div class="btn-group-xs">
					<button type="button" id="saveBtn" class="btn btn-system "
						onclick="save()">保存</button>
					<button type="button" class="btn btn-system " onclick="closeWin(false)">关闭</button>
				</div>
			</div>
		</div>
		<div class="panel panel-info">
			<div class="panel-body">
				<form role="form" class="form-search" id="dataForm" action=""
					method="post" target="targetFrame">
					<house:token></house:token>
								<input hidden="true" id="code" name="code" value="${item.code}" />
					<ul class="ul-form">
						<div class="validate-group row">
							<div class="col-sm-4">
								<li class="form-validate"><label>是否过期</label> <select
									id="expired" name="expired">
										<option value="">请选择</option>
										<option value="F">否</option>
										<option value="T">是</option>
								</select></li>
							</div>
							<div class="col-sm-4">
								<li class="form-validate"><label>提成类型</label> <house:xtdm
										id="commiType" dictCode="COMMITYPE" value="${item.commiType}" onchange="commiTypeChange()"></house:xtdm>
								</li>
							</div>
							<div class="col-sm-4" >
								<li class="form-validate"><label><span
										class="required">*</span>提成比例</label> <input type="text"
									id="commiPerc" name="commiPerc"
									onkeyup="value=value.replace(/[^\-?\d.]/g,'')"
									value="${item.commiPerc}" /> <span id="uom_show">元</span>
								</li>
							</div>
								<div class="col-sm-4" hidden="true">
								<li class="form-validate"><label>提成类型名称</label> 
								<input id="commiTypeDescr" name="commiTypeDescr" />
								</li>
								<li class="form-validate"><label>过期名称</label> 
								<input id="expiredDescr" name="expiredDescr" />
								</li>
							</div>
						</div>
					</ul>
				</form>
			</div>
		</div>
	</div>
</body>
</html>
