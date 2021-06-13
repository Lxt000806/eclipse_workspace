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
	<title>任务管理——添加供应商</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_supplier.js?v=${v}" type="text/javascript"></script>
	
	<script type="text/javascript"> 
	$(function(){
		$("#supplCode").openComponent_supplier({showValue:"${supplJob.supplCode}",showLabel:"${supplJob.supplDescr}",
				condition:{supplJob:"1",custCode:"${prjJob.custCode}",itemType1:"${prjJob.itemType1}"},readonly:true});	
		$("#appCzy").openComponent_employee({showValue:"${supplJob.appCzy}",showLabel:"${supplJob.appDescr}",readonly:true});	
	
		$("#saveBtn").on("click",function(){
			var postData = $("#page_form").jsonForm();
			$.ajax({
				url:"${ctx}/admin/prjJobManage/doUpdateSuppl",
				type: "post",
				data:postData,
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
					<form action="" method="post" id="page_form" class="form-search">
						<input type="hidden" name="jsonString" value=""/>
						<input type="hidden" name="pk" id="pk" value="${supplJob.pk }"/>
						<ul class="ul-form">
							<div class="validate-group row">
								<li>
									<label>供应商</label>
									<input type="text" id="supplCode" name="supplCode" style="width:160px;" value="${supplJob.supplCode }"/>
								</li>
								<li>
									<label>状态</label>
									<house:xtdm id="status" dictCode="SUPPLJOBSTS" value="${supplJob.status }" disabled="true"></house:xtdm>                     
								</li>
							</div>
							<div class="validate-group row">
								<li>
									<label>指派人</label>
									<input type="text" id="appCzy" name="appCzy" style="width:160px;" value="${supplJob.appCzy }" readonly="true"/>
								</li>
								<li class="form-validate">
									<label>指派时间</label>
									<input type="text" id="date" name="date" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${supplJob.date}' pattern='yyyy-MM-dd'/>" disabled="true"/>
								</li>
							</div>
							<div class="validate-group row">
								<li class="form-validate">
									<label>接收时间</label>
									<input type="text" id="recvDate" name="recvDate" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${supplJob.recvDate}' pattern='yyyy-MM-dd'/>" disabled="true"/>
								</li>
								<li class="form-validate">
									<label>完成时间</label>
									<input type="text" id="completeDate" name="completeDate" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${supplJob.recvDate}' pattern='yyyy-MM-dd'/>" disabled="true"/>
								</li>
							</div>
							<div class="validate-group row">
								<li>
									<label class="control-textarea">备注</label>
									<textarea id="remarks" name="remarks" rows="2">${supplJob.remarks }</textarea>
	  							</li>
	  							<li>
									<label class="control-textarea">供应商备注</label>
									<textarea id="supplRemarks" name="supplRemarks" rows="2" readonly="true">${supplJob.supplRemarks }</textarea>
	  							</li>
							</div>
						</ul>
					</form>
				</div>
			</div>
		</div>
	</body>	
</html>
