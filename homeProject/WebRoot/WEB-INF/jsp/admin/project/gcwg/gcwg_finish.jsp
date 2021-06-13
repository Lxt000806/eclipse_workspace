<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>工程完工结转</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_czybm.js?v=${v}" type="text/javascript"></script>

<script type="text/javascript">
function save(){
	$("#dataForm").bootstrapValidator("validate");
	if(!$("#dataForm").data("bootstrapValidator").isValid()) return;
	
	art.dialog({
		content:"确认要对["+"${customer.code}"+"]["+"${customer.descr}"+"]客户进行完工操作吗?",
		lock: true,
		width: 200,
		height: 100,
		ok: function () {
			doSave();
	    	return true;
		},
		cancel: function () {
			return true;
		}
	});
}
function doSave(){
	var datas = $("#dataForm").serialize();
	$.ajax({
		url:"${ctx}/admin/gcwg/doFinish",
		type: "post",
		data: datas,
		dataType: "json",
		cache: false,
		error: function(obj){
			showAjaxHtml({"hidden": false, "msg": "保存数据出错~"});
	    },
	    success: function(obj){
	    	if(obj.rs){
	    		if (obj.msg=="进入工程完工确认"){
	    			Global.Dialog.showDialog("gcwgFinishConfirm",{
					  	title:"工程完工确认",
					  	url:"${ctx}/admin/gcwg/goFinishConfirm",
					  	postData: $("#dataForm").serializeObject(),
					  	height:600,
					  	width:800
					});
	    		}else{
	    			art.dialog({
						content: obj.msg,
						time: 1000,
						beforeunload: function () {
		    				closeWin();
					    }
					});
	    		}
	    	}else{
				art.dialog({
					content: obj.msg,
					width: 200
				});
	    	}
	    }
	});
}
function changeEndCode(){
	if ($("#endCode").val()=="3"){
		$("#div_realDesignFee").hide();
		$("#realDesignFee").val("0");
	}else{
		$("#div_realDesignFee").show();
		$("#realDesignFee").val("0");
	}
}
//校验函数
$(function() {
	$("#dataForm").bootstrapValidator({
        message : "This value is not valid",
        feedbackIcons : {/*input状态样式图片*/
            validating : "glyphicon glyphicon-refresh"
        },
        fields: {
        	realDesignFee: {
      		validators: { 
      			numeric: {
	            	message: "实收设计费只能是数字" 
	            },
	            notEmpty: { 
	            	message: "实收设计费不能为空"  
	            }
	        }
      	}
      	},
        submitButtons : 'input[type="submit"]'
    });
});

</script>

</head>
    
<body>
<div class="body-box-form">
	<div class="panel panel-system">
	    <div class="panel-body" >
	      <div class="btn-group-xs" >
	      <button type="button" id="saveBtn" class="btn btn-system" onclick="save()">保存</button>
	      <button type="button" class="btn btn-system" onclick="closeWin(false)">关闭</button>
	      </div>
	    </div>
	</div>
	<div class="panel panel-info">  
        <div class="panel-body">
        	<form role="form" class="form-search" id="dataForm"  action="" method="post" target="targetFrame">
            	<input type="hidden" id="code" name="code" value="${customer.code }" />
            	<input type="hidden" id="status" name="status" value="${customer.status }" />
            	<ul class="ul-form">
					<div class="validate-group row">
						<div class="col-sm-12">
							<li class="form-validate">
								<label>结束代码</label>
								<house:xtdm id="endCode" dictCode="CUSTOMERENDCODE" showValue="3,4" value="${customer.endCode }" 
								headerLabel="" onchange="changeEndCode()"></house:xtdm>
							</li>
						</div>
					</div>
					
					<div class="validate-group row">
						<div class="col-sm-6">
							<li class="form-validate">
								<label>完工时间</label>
								<input type="text" style="width:160px;" id="endDate" name="endDate" class="i-date" 
								onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
								value="<fmt:formatDate value='${customer.endDate }' pattern='yyyy-MM-dd'/>"/>
							</li>
						</div>
						
						<div class="col-sm-6" style="display: none;" id="div_realDesignFee">
							<li class="form-validate">
								<label>实收设计费</label>
								<input type="text" id="realDesignFee" name="realDesignFee" style="width:160px;" value="0" />
							</li>
						</div>
					</div>
				
					<div class="validate-group row">
						<div class="col-sm-12">
							<li class="form-validate" style="width: 100%;position: relative;">
								<label style="float: left;">完工备注</label>
								<textarea id="endRemark" name="endRemark" rows="4" style="float: left;width: 500px;margin-left: 5px;"></textarea>
							</li>
						</div>
					</div>
						
					<div class="validate-group row">
						<div class="col-sm-12" style="margin-left: 60px;margin-top: 10px;">
							<p><font color="red" size="3">${hintString }</font></p>
						</div>
					</div>
            	</ul>
            </form>
         </div>
     </div>
</div>
</body>
</html>
