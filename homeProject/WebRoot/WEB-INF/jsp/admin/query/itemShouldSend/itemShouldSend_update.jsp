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
	<title>材料应发货楼盘明细</title>
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
	$("#saveBtn").on("click",function(){
		var delivType  = $.trim($("#delivType").val());
		if(delivType == "" ){
			art.dialog({
				content:"配送方式不能为空",
			});
			return;
		}
		var datas = $("#page_form").serialize();
		$.ajax({
			url:'${ctx}/admin/itemShouldSend/doUpdateItemApp',
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
		    				closeWin(true,true);
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
						<button type="button" class="btn btn-system " id="closeBut" onclick="closeWin(true)">
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
								<label>领料单号</label>
								<input type="text" id="no" name="no" style="width:160px;" value="${itemApp.no }" readonly="true" />
							</li>
							<li>
								<label>配送方式</label>
								<house:xtdm id="delivType" dictCode="DELIVTYPE" value="${itemApp.delivType }"/>
							</li>
							<li>
								<label class="control-textarea">供应商备注</label>
								<textarea id="splRemark" name="splRemark" rows="2">${itemApp.splRemark }</textarea>
							</li>
				 		</ul>	
					</form>
				</div>
			</div>
		</div>
	</body>	
</html>
