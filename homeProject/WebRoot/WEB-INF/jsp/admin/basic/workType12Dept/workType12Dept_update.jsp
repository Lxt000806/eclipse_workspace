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
	<title>工种分组增加</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	
<script type="text/javascript"> 
$(function() {
	$("#dataForm").bootstrapValidator({
		message : 'This value is not valid',
		feedbackIcons : {/*input状态样式图片*/
			validating : 'glyphicon glyphicon-refresh'
		},
		fields: {  
			code:{  
				validators: {  
					notEmpty: {  
						message: '编号不能为空'  
					}
				}  
			},
			descr:{  
				validators: {  
					notEmpty: {  
						message: '分组名称不能为空'  
					}
				}  
			},
			workType12:{  
				validators: {  
					notEmpty: {  
						message: '工种12不能为空'  
					}
				}  
			},
		},
		submitButtons : 'input[type="submit"]'/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
	});
});
$(function(){
	$("#saveBtn").on("click",function(){
		$("#dataForm").bootstrapValidator('validate');
		if(!$("#dataForm").data('bootstrapValidator').isValid()) return;
		var datas = $("#dataForm").serialize();
		$.ajax({
			url:'${ctx}/admin/workType12Dept/doUpdate',
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
});
function chgExpired(e){
	if(e.checked){
		$("#expired").val("T");
	}else{
		$("#expired").val("F");
	}
}

</script>
</head>
<body>
	<div class="body-box-form">
		<div class="panel panel-system" >
		    <div class="panel-body" >
		      <div class="btn-group-xs" >
					<button type="button" class="btn btn-system " id="saveBtn">
						<span>保存</span>
					</button>
					<button type="button" class="btn btn-system " id="closeBut" onclick="closeWin(false)">
						<span>关闭</span>
					</button>
				</div>
			</div>
		</div>
		<div class="panel panel-info" >  
			<div class="panel-body">
				<form action="" method="post" id="dataForm" class="form-search">
					<input type="hidden" name="jsonString" value="" />
					<input type="hidden" name="expired" id="expired" value="${workType12Dept.expired }" />
					<ul class="ul-form">
						<div class="validate-group row" >
							<li class="form-validate">
								<label><span class="required">*</span>编号</label>
								<input type="text" id="code" name="code" style="width:160px;" value="${workType12Dept.code }" readonly="readonly"/>
							</li>
							<li class="form-validate">
								<label><span class="required">*</span>分组名称</label>
								<input type="text" id="descr" name="descr" style="width:160px;" value="${workType12Dept.descr }"/>
							</li>
						</div>
						<div class="validate-group row" >
							<li class="form-validate">
								<label><span class="required">*</span>工种12</label>
								<house:DataSelect id="workType12" className="WorkType12" keyColumn="code" valueColumn="descr" value="${workType12Dept.workType12 }" ></house:DataSelect>
							</li>
							<li class="form-validate">
                                <label>过期</label>
                                <input type="checkbox"  id="expired_show" name="expired_show"
								 value="${workType12Dept.expired }" onclick="chgExpired(this)" 
								 ${workType12Dept.expired!='F'?'checked':'' } />
                            </li>
						</div>
					</ul>
				</form>
			</div>
		</div>
	</div>
</body>	
</html>
