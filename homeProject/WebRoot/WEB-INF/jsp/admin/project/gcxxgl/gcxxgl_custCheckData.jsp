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
	<title>工程信息修改集成设计师</title>
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
	$("#custCode").openComponent_customer({showValue:"${customer.code}",showLabel:"${customer.descr}"});

	$("#saveBtn").on("click",function(){
		if(!$("#page_form").valid()) {return false;}//表单校验
		var datas = $("#page_form").serialize();
		$.ajax({
			url:'${ctx}/admin/gcxxgl/doUpdateCustCheckData',
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
					<input type="hidden" name="jsonString" value="" />
						<ul class="ul-form">
							<li>
								<label>客户编号</label>
								<input type="text" id="custCode" name="custCode" style="width:160px;" value="${custCheckData.custCode }" readonly="true"/>
							</li>
							<li>
								<label>卫生间个数</label>
								<input type="text" id="toiletNum" name="toiletNum" style="width:160px;" value="${custCheckData.toiletNum }"	 />
							</li>
							<li>
								<label>卧室个数</label>
								<input type="text" id="bedroomNum" name="bedroomNum" style="width:160px;" value="${custCheckData.bedroomNum }" />
							</li>
							<li>
								<label>厨房门类型</label>
								<house:xtdm  id="kitchDoorType" dictCode="KITCHDRTYPE"   style="width:160px;" value="${custCheckData.kitchDoorType}"></house:xtdm>
				 			</li>
				 			<li>
								<label>阳台个数</label>
								<input type="text" id="balconyNum" name="balconyNum" style="width:160px;" value="${custCheckData.balconyNum }" />
				 			</li>
				 			<li>
								<label>阳台贴墙面砖</label>
								<house:xtdm  id="balconyTitle" dictCode="YESNO"   style="width:160px;" value="${custCheckData.balconyTitle}"></house:xtdm>
				 			</li>
				 			<li>
								<label>有木作</label>
								<house:xtdm  id="isWood" dictCode="YESNO"   style="width:160px;" value="${custCheckData.isWood}"></house:xtdm>
				 			</li>
				 			<li>
								<label>新砌墙</label>
								<house:xtdm  id="isBuildWall" dictCode="YESNO"   style="width:160px;" value="${custCheckData.isBuildWall}"></house:xtdm>
				 			</li>
				 		</ul>	
				</form>
				</div>
				</div>
		</div>
	</body>	
</html>
