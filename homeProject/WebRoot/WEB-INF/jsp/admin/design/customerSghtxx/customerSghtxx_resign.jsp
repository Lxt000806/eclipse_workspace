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
	<title>施工合同管理转施工</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
	
	<script type="text/javascript">
	$(function(){
		$("#code").openComponent_customer({showValue:"${customer.code}",showLabel:"${customer.descr}",readonly:true});
	
	});

	function saveBtn(){
		var datas = $("#dataForm").serialize();
		art.dialog({
			content:"确定要对["+$.trim("${customer.code}")+"]["+$.trim("${customer.descr}")+"]客户进行重签操作吗？",
			lock: true,
			width: 200,
			height: 100,
			ok: function () {
				 $.ajax({
					url:"${ctx}/admin/customerSghtxx/doResign",
					type: "post",
					data: datas,
					dataType: "json",
					cache: false,
					error: function(obj){
						showAjaxHtml({"hidden": false, "msg": "保存数据出错~"});
				    },
				    success: function(obj){
				    	if(obj.rs==true){
							art.dialog({
								content:obj.msg,
								time:500,
								beforeunload:function(){
									closeWin();
								}
							});				
						}else{
							$("#_form_token_uniq_id").val(obj.token.token);
							art.dialog({
								content:obj.msg,
								width:200
							});
						}
				    }
				});
			},
			cancel: function () {
				return true;
			}
		});	
	}
	</script>
</head>
	<body>
		<div class="body-box-form">
			<div class="panel panel-system">
			    <div class="panel-body">
			    	<div class="btn-group-xs">
						<button type="button" class="btn btn-system" id="saveBtn" onclick="saveBtn()">
							<span>保存</span>
						</button>
						<button type="button" class="btn btn-system" id="closeBut" onclick="closeWin(false)">
							<span>关闭</span>
						</button>
					</div>
				</div>
			</div>
			<div class="panel panel-info">  
				<div class="panel-body" >
					<form action="" method="post" id="dataForm" class="form-search">
						<input type="hidden" name="jsonString" value=""/>
						<ul class="ul-form">
							<div class="validate-group row">
								<li>
									<label>客户名称</label>
									<input type="text" id="code" name="code" style="width:160px;" value="${customer.code }" />
								</li>
								<li>
									<label>楼盘</label>
									<input type="text" id="address" name="address" style="width:160px;" value="${customer.address }" readonly="true" />
								</li>
								<li class="form-validate">
									<label><span class="required">*</span>结束代码</label>
									<house:xtdm id="endCode" dictCode="CUSTOMERENDCODE" value="${customer.endCode }" disabled="true"></house:xtdm>
								</li>
								<li class="form-validate">
									<label><span class="required">*</span>客户状态</label>
									<house:xtdm id="status" dictCode="CUSTOMERSTATUS" value="${customer.status }" disabled="true"></house:xtdm>
								</li>
							</div>	
							<div class="validate-group row" style="height: 90px">
								<li class="form-validate" >
									<label class="control-textarea" style="margin-top:-50px">备注</label>
									<textarea id="remarks" name="remarks" rows="5"></textarea>
	  							</li>
							</div>
						</ul>
					</form>
				</div>
			</div>
		</div>
	</body>	
</html>
