<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>修改itemProcess</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>

<script type="text/javascript">
function save(){
	//表单校验
	$("#dataForm").bootstrapValidator('validate');
	if (!$("#dataForm").data('bootstrapValidator').isValid()) return;
	
	var datas = $("#dataForm").serialize();
	$.ajax({
		url:'${ctx}/admin/itemProcess/doUpdate',
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

//校验函数
$(function() {
	$("#dataForm").bootstrapValidator({
		message : 'This value is not valid',
		feedbackIcons : {/*input状态样式图片*/
			validating : 'glyphicon glyphicon-refresh'
		},
		fields : {
			"itemType1": {
				validators : {
				}		
			},
			"processItemWhcode": {
				validators : {
					notEmpty : {
						message : '不能为空'
					},
				}		
			},
			"sourceItemWhcode": {
				validators : {
					notEmpty : {
						message : '不能为空'
					},
				}		
			},
			"remarks": {
				validators : {
				}		
			},
			"status": {
				validators : {
				}		
			},
			"appCzy": {
				validators : {
				}		
			},
			"appDate": {
				validators : {
				}		
			},
			"confirmCzy": {
				validators : {
				}		
			},
			"confirmDate": {
				validators : {
				}		
			},
			"lastUpdate": {
				validators : {
				}		
			},
			"lastUpdatedBy": {
				validators : {
				}		
			},
			"expired": {
				validators : {
				}		
			},
			"actionLog": {
				validators : {
				}		
			}
		},
		submitButtons : 'input[type="submit"]'/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
	});
});

</script>

</head>
	<body>
		<div class="body-box-form">
			<div class="panel panel-system">
				<div class="panel-body">
					<div class="btn-group-xs">
						<button type="button" class="btn btn-system" id="saveBtn" onclick="save()">
							<span>保存</span>
						</button>
						<button type="button" class="btn btn-system" id="closeBut" onclick="closeWin(false)">
							<span>关闭</span>
						</button>
					</div>
				</div>
			</div>
			<div class="panel panel-info">
				<div class="panel-body">
					<form action="" method="post" id="dataForm" class="form-search">
						<input type="hidden" name="jsonString" value="" /> 
						<input type="hidden" id="m_umState" name="m_umState" value="A" />
						<ul class="ul-form">	
							<li class="form-validate">
								<label>itemType1</label> 
								<input type="text" id="itemType1" name="itemType1" style="width:160px;"  value="${itemProcess.itemType1}" />
								</li>
							<li class="form-validate">
								<label><span class="required">*</span>processItemWhcode</label> 
								<input type="text" id="processItemWhcode" name="processItemWhcode" style="width:160px;"  value="${itemProcess.processItemWhcode}" />
								</li>
							<li class="form-validate">
								<label><span class="required">*</span>sourceItemWhcode</label> 
								<input type="text" id="sourceItemWhcode" name="sourceItemWhcode" style="width:160px;"  value="${itemProcess.sourceItemWhcode}" />
								</li>
							<li class="form-validate">
								<label>remarks</label> 
								<input type="text" id="remarks" name="remarks" style="width:160px;"  value="${itemProcess.remarks}" />
								</li>
							<li class="form-validate">
								<label>status</label> 
								<input type="text" id="status" name="status" style="width:160px;"  value="${itemProcess.status}" />
								</li>
							<li class="form-validate">
								<label>appCzy</label> 
								<input type="text" id="appCzy" name="appCzy" style="width:160px;"  value="${itemProcess.appCzy}" />
								</li>
							<li class="form-validate">
								<label>appDate</label> 
								<input type="text" id="appDate" name="appDate" style="width:160px;"  value="${itemProcess.appDate}" />
								</li>
							<li class="form-validate">
								<label>confirmCzy</label> 
								<input type="text" id="confirmCzy" name="confirmCzy" style="width:160px;"  value="${itemProcess.confirmCzy}" />
								</li>
							<li class="form-validate">
								<label>confirmDate</label> 
								<input type="text" id="confirmDate" name="confirmDate" style="width:160px;"  value="${itemProcess.confirmDate}" />
								</li>
							<li class="form-validate">
								<label>lastUpdate</label> 
								<input type="text" id="lastUpdate" name="lastUpdate" style="width:160px;"  value="${itemProcess.lastUpdate}" />
								</li>
							<li class="form-validate">
								<label>lastUpdatedBy</label> 
								<input type="text" id="lastUpdatedBy" name="lastUpdatedBy" style="width:160px;"  value="${itemProcess.lastUpdatedBy}" />
								</li>
							<li class="form-validate">
								<label>expired</label> 
								<input type="text" id="expired" name="expired" style="width:160px;"  value="${itemProcess.expired}" />
								</li>
							<li class="form-validate">
								<label>actionLog</label> 
								<input type="text" id="actionLog" name="actionLog" style="width:160px;"  value="${itemProcess.actionLog}" />
								</li>
							<li>
								<label>过期</label>
								<input type="checkbox" id="expired_show" name="expired_show" value="${itemProcess.expired}" onclick="checkExpired(this)" ${itemProcess.expired=="T"?"checked":"" }>
							</li>
						</ul>
					</form>
				</div>
			</div>
		</div>
	</body>
</html>