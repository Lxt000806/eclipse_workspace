<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://" + request.getServerName() + ":"+request.getServerPort()+path+"/";
String jasperPath = PathUtil.WEB_INF+"jsp/jasper/";
%>
<!DOCTYPE html>
<html lang="zh">
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
</head>
<body>
	<div class="body-box-form">
		<div class="panel panel-system">
		    <div class="panel-body">
		    	<div class="btn-group-xs">
					<button type="button" class="btn btn-system" id="saveBtn">
						<span>保存</span>
					</button>
					<button type="button" class="btn btn-system" onclick="closeWin(true)">
						<span>关闭</span>
					</button>
				</div>
			</div>
		</div>
		<div class="panel panel-info">  
			<div class="panel-body">
				<form action="" method="post" id="dataForm" class="form-search">
					<input type="hidden" name="jsonString" value=""/>
					<input type="hidden" id="id" name="id" value="${actGroup.id}"/>
					<ul class="ul-form">
						<li class="form-validate">
							<label><span class="required">*</span>流程名称</label>
							<house:dict id="wfProcNo" dictCode="" 
							sql="select no Code,no+' '+Descr Descr from tWfprocess a where expired = 'F'"  
							sqlValueKey="Code" sqlLableKey="Descr" ></house:dict>
						</li>
					</ul>
				</form>
			</div>
		</div>
	</div>
</body>	
	<script type="text/javascript"> 
		$(function() {
			$("#saveBtn").on("click",function(){
				if($("#wfProcNo").val() == ""){
					art.dialog({
						content:"请选择流程",
					});
					return;
				}
				var datas = $("#dataForm").serialize();
				$.ajax({
					url:"${ctx}/admin/actGroup/doSaveRoleAuth",
					type:"post",
					data:datas,
					dataType:"json",
					cache:false,
					error:function(obj){
						showAjaxHtml({"hidden": false, "msg": "获取数据出错~"});
					},
					success:function(obj){
						if(obj.rs){
							art.dialog({
								content: obj.msg,
								time: 1000,
							});
						}else{
							art.dialog({
								content: obj.msg,
								time: 1000,
							});
						}
					}
				});
			});
		});
	</script>
</html>
