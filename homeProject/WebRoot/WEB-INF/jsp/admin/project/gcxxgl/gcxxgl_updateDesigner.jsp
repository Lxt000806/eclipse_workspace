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
	<title>工程信息管理修改设计师</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	
<script type="text/javascript"> 
$(function(){
	$("#softEmpCode").openComponent_employee({showValue:'${customer.softEmpCode}',showLabel:'${customer.designManDescr}',condition:{department1:"04"}});
	$("#softSteward").openComponent_employee({showValue:'${customer.softSteward}',showLabel:'${customer.softStewardDescr}',condition:{department1:"04"}});
	
	if("${customer.softSteward}" != "" && "${customer.softSteward}" != null){
		$("#softStewardStatus").val("M");
	}else{
		$("#softStewardStatus").val("A");
	}
	
	$("#saveBtn").on("click",function(){
		var softEmpCode = $("#softEmpCode").val();
		if(softEmpCode==''||softEmpCode==null){
			art.dialog({
				content:'软装设计师员工编号不存在，不能保存',
			});
			return false;
		}
	
		if(!$("#page_form").valid()) {return false;}//表单校验
		var datas = $("#page_form").serialize();
		$.ajax({
			url:'${ctx}/admin/gcxxgl/doUpdateDesigner',
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

</script>
</head>
	<body>
		<div class="body-box-list">
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
			  <form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
					<house:token></house:token>
					<input type="hidden" name="jsonString" value="" />
					<input type="hidden" id="softPK" name="softPK" style="width:160px;" value="${customer.softPK }" readonly="true" />
					<input type="hidden" id="softStewardStatus" name="softStewardStatus" style="width:160px;"  readonly="true" />
					<input type="hidden" id="softStewardPk" name="softStewardPk" style="width:160px;" value="${customer.softStewardPk }" readonly="true" />
					<ul class="ul-form">
						<li>
							<label>客户编号</label>
							<input type="text" id="code" name="code" style="width:160px;" value="${customer.code }" readonly="true"/>
						</li>
						<li>
							<label>地址</label>
							<input type="text" id="address" name="address" style="width:160px;" value="${customer.address }" readonly="true" />
						</li>
						<li>
							<label>角色</label>
							<input type="text" id="role" name="role" style="width:160px;" value="50|软装设计师" readonly="true" />
						</li>
						<li>
							<label>员工编号</label>
							<input type="text" id="softEmpCode" name="softEmpCode" style="width:160px;" value="${customer.softEmpCode }" />
			 			</li>
			 			<li>
							<label>角色</label>
							<input type="text" id="role" name="role" style="width:160px;" value="65|软装管家" readonly="true" />
						</li>
						<li>
							<label>员工编号</label>
							<input type="text" id="softSteward" name="softSteward" style="width:160px;" value="${customer.softSteward }" />
			 			</li>
			 		</ul>	
				</form>
			</div>
		</div>
	</div>
	</body>	
</html>
