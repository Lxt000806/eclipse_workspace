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
	<script src="${resourceRoot}/pub/component_supplier.js?v=${v}" type="text/javascript"></script>
</head>
<body>
	<div class="body-box-form">
		<div class="panel panel-system">
		    <div class="panel-body">
		    	<div class="btn-group-xs">
					<button type="button" class="btn btn-system" id="saveBtn">
						<span>保存</span>
					</button>
					<button type="button" class="btn btn-system" id="closeBut" onclick="closeWin()">
						<span>关闭</span>
					</button>
				</div>
			</div>
		</div>
		<div class="panel panel-info" style="margin: 0px;height: 207px;">  
			<div class="panel-body">
				<form action="" method="post" id="page_form" class="form-search">
					<input type="hidden" name="jsonString" value=""/>
					<input type="hidden" name="no" value="${intReplenish.no}"/>
					<input type="hidden" name="m_umState" value="${intReplenish.m_umState}"/>
					<input type="hidden" id="lastUpdatedBy" name="lastUpdatedBy" value="${sessionScope.USER_CONTEXT_KEY.czybh}"/>
					<ul class="ul-form">
						<div class="row">
							<li>
								<label>楼盘</label>
								<input type="text" name="address" id="address" readonly="true" 
									value="${intReplenish.address}">
							</li>
							<li id="status_li">
								<label>状态</label>
								<house:xtdm id="status" dictCode="IntRepStatus" style="width:160px;" 
									value="${intReplenish.status}"/>
							</li>
							<li id="finishDate_li" hidden="true">
								<label><span class="required">*</span>完成时间</label>
								<input type="text" id="finishDate" name="finishDate" class="i-date"
									onclick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd',readOnly:true})" 
									value="<fmt:formatDate value='${intReplenish.finishDate}' pattern='yyyy-MM-dd'/>"
									readonly/>
							</li>
						</div>
					</ul>
				</form>
			</div>
		</div>
	</div>
	<script type="text/javascript"> 
		$(function() {
			$("#status option[value='']").remove();
			if ("finish" == "${intReplenish.m_umState}") {
				$("#status_li").hide();
				$("#status").val("5");
				$("#status").prop("disabled",true);
				$("#finishDate_li").removeProp("hidden");
			}
			$("#saveBtn").on("click",function(){
				var datas = $("#page_form").jsonForm();
				if ("finish" == "${intReplenish.m_umState}") {
					if ("" == datas.finishDate) {
						art.dialog({
							content: "完成时间不能为空"
						});
						return;
					}
				}
				$.ajax({
					url:"${ctx}/admin/intReplenish/doModifyStatus",
					type: "post",
					data: datas,
					dataType: "json",
					cache: false,
					error: function(obj){
						showAjaxHtml({"hidden": false, "msg": "保存数据出错"});
					},
					success: function(obj){
						if(obj.rs){
							art.dialog({
								content: obj.msg,
								time: 700,
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
</body>	
</html>
