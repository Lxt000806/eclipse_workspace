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
	<title>干系人管理</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_roll.js?v=${v}" type="text/javascript"></script>
	
	<script type="text/javascript"> 
	$(function(){
		$("#custCode").openComponent_customer({showValue:"${custStakeholder.custCode}",showLabel:"${customer.descr}",readonly:true});	
		$("#empCode").openComponent_employee({showValue:"${custStakeholder.empCode}",showLabel:"${employee.nameChi}"});	
		$("#role").openComponent_roll({showValue:"${custStakeholder.role}",showLabel:"${roll.descr}"});	
	
		$("#saveBtn").on("click",function(){
			var datas = $("#dataForm").serialize();
			var code=$.trim($("#code").val());
			var custType=$.trim($("#custType").val());
			
			var custSceneDesi=$.trim($("#custSceneDesi").val());
			$.ajax({
				url:"${ctx}/admin/custStakeholder/doUpdate",
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
	</script>
</head>
	<body>
		<div class="body-box-form">
			<div class="panel panel-system">
			    <div class="panel-body">
			    	<div class="btn-group-xs">
						<button type="button" class="btn btn-system" id="closeBut" onclick="closeWin(false)">
							<span>关闭</span>
						</button>
					</div>
				</div>
			</div>
			<div class="panel panel-info">  
				<div class="panel-body">
					<form action="" method="post" id="dataForm" class="form-search">
						<input type="hidden" name="jsonString" value=""/>
						<input type="hidden" id="pk" name="pk" value="${custStakeholder.pk}"/>
						<ul class="ul-form">
							<div class="validate-group row">
								<li>
									<label>客户编号</label>
									<input type="text" id="custCode" name="custCode" style="width:160px;" value="${custStakeholder.custCode}"/>
								</li>
							</div>
							<div class="validate-group row">
								<li>
									<label>角色</label>
									<input type="text" id="role" name="role" style="width:160px;" value="${custStakeholder.role }"/>
								</li>
							</div>
							<div class="validate-group row">
								<li>
									<label>员工编号</label>
									<input type="text" id="empCode" name="empCode" style="width:160px;" value="${custStakeholder.empCode }" />
								</li>
							</div>
						</ul>
					</form>
				</div>
			</div>
		</div>
	</body>	
</html>
