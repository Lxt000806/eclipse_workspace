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
	$("#code").openComponent_customer({showValue:"${customer.code}",showLabel:"${customer.descr}"});
	$("#projectMan").openComponent_employee({condition:{prjManCustCount:'1',"onlyPrjMan":"1"}});
	
	$("#saveBtn").on("click",function(){

		if(!$("#page_form").valid()) {return false;}//表单校验
		var datas = $("#page_form").serialize();
		$.ajax({
			url:'${ctx}/admin/gcxxgl/doInsertPrjMan',
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
								<label>项目经理</label>
								<input type="text" id="projectMan" name="projectMan" style="width:160px;" value="${customer.projectMan }" />
				 			</li>
				 		</ul>	
					</form>
				</div>
			</div>
		</div>
	</body>	
</html>
