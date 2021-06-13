<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://" + request.getServerName() + ":"+request.getServerPort()+path+"/";
String jasperPath = PathUtil.WEB_INF+"jsp/jasper/";
%>
<!DOCTYPE html>
<html>
<head>
	<title>文档管理新增</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_department.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_assetType.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_docFolder.js?v=${v}" type="text/javascript"></script>
	<style type="text/css">
		::-webkit-input-placeholder { /* Chrome */
		  color: #cccccc;
		}
	</style>
<script type="text/javascript"> 
$(function() {
	$("#dataForm").bootstrapValidator({
		message : "This value is not valid",
		feedbackIcons : {/*input状态样式图片*/
			validating : "glyphicon glyphicon-refresh"
		},
		fields: {
			salarySchemeType:{
				validators:{
					notEmpty:{
						message:"名称不能为空"
					}
				}
			},
			payMode:{
				validators:{
					notEmpty:{
						message:"指标级别不能为空"
					}
				}
			},
			bankCardType:{
				validators:{
					notEmpty:{
						message:"适用对象不能为空"
					}
				}
			},
			
		},
        submitButtons : "input[type='submit']"/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
    });
});
function doSave(){
	$("#dataForm").bootstrapValidator("validate");
    if (!$("#dataForm").data("bootstrapValidator").isValid()) return;
    
	var datas = $("#dataForm").serialize();
	$.ajax({
		url:"${ctx}/admin/salaryBankCardTypeCfg/doUpdate",
		type: "post",
		data: datas,
		dataType: "json",
		cache: false,
		error: function(obj){
			showAjaxHtml({"hidden": false, "msg": "保存数据出错~"});
	    },
	    success: function(obj){
	    	if(obj.rs){
	    		art.dialog({
					content: obj.msg,
					time: 1000,
					beforeunload: function () {
	    				closeWin(true);
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

function chgExpired(e){
	if ($(e).is(':checked')){
		$('#expired').val('F');
	}else{
		$('#expired').val('T');
	}
}

</script>
</head>
	<body>
		<div class="body-box-form">
			<div class="panel panel-system" >
			    <div class="panel-body" >
			      <div class="btn-group-xs" >
						<button type="button" class="btn btn-system " id="saveBtn" onclick="doSave()">
							<span>保存</span>
						</button>
						<button type="button" class="btn btn-system " id="closeBut" onclick="closeWin(true)">
							<span>关闭</span>
						</button>
					</div>
				</div>
			</div>
			<div class="panel panel-info" >  
				<div class="panel-body">
					<form action="" method="post" id="dataForm" class="form-search">
						<house:token></house:token>
						<input type="hidden" name="jsonString" value=""/>
						<input type="hidden" name="pk" id = "pk" value="${salaryBankCardTypeCfg.pk}"/>
						<input type="hidden" name="expired" id = "expired" value="${salaryBankCardTypeCfg.expired}"/>
						<ul class="ul-form">
							<div class="validate-group row">
								<li class="form-validate">
									<label>公司</label>
									<house:dict id="salaryPayCmp" dictCode="" sql="select * from tConSignCmp where expired = 'F'" 
											disabled="true" sqlValueKey="Code" sqlLableKey="Descr" value="${salaryBankCardTypeCfg.salaryPayCmp }"></house:dict>
								</li>
							</div>
							<div class="validate-group row">
								<li class="form-validate">
									<label><span class="required">*</span>薪酬方案</label>
									<house:dict id="salarySchemeType" dictCode="" sql="select * from tSalarySchemeType where expired = 'F'" 
											disabled="true" sqlValueKey="Code" sqlLableKey="Descr" value="${salaryBankCardTypeCfg.salarySchemeType }"></house:dict>
								</li>
							</div>
							<div class="validate-group row">
								<li class="form-validate">
									<label><span class="required">*</span>发放方式</label>
									<house:xtdm  id="payMode" dictCode="SALPAYMODE" style="width:160px;" 
										disabled="true" value="${salaryBankCardTypeCfg.payMode }"></house:xtdm>
								</li>
							</div>
							<div class="validate-group row">
								<li class="form-validate">
									<label><span class="required">*</span>银行卡类型</label>
									<house:xtdm  id="bankCardType" dictCode="SALBANKTYPE" style="width:160px;" value="${salaryBankCardTypeCfg.bankCardType }"></house:xtdm>
								</li>
							</div>
							<div class="validate-group row">
								<li>
	                                <label>过期</label>
	                                <input type="checkbox" name="expiredCheckbox"
	                                    ${salaryBankCardTypeCfg.expired == 'T' ? 'checked' : ''} onclick="chgExpired(this)"/>
                          	 	</li>
							</div>
						</ul>
					</form>
				</div>
			</div>
		</div>
	</body>	
</html>
