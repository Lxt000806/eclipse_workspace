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
	<script src="${resourceRoot}/pub/component_worker.js?v=${v}" type="text/javascript"></script>
	
	<script type="text/javascript"> 
	$(function(){
		$("#code").openComponent_worker({showValue:"${worker.code}",showLabel:"${worker.nameChi}",readonly:true});	
	
		$("#saveBtn").on("click",function(){
			$("#page_form").bootstrapValidator("validate");
			if(!$("#page_form").data("bootstrapValidator").isValid()) return;
			var postData = $("#page_form").jsonForm();
			$.ajax({
				url:"${ctx}/admin/worker/doUpdateWorker",
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
						<button type="button" class="btn btn-system" id="saveBtn">
							<span>保存</span>
						</button>
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
						<ul class="ul-form">
							<div class="validate-group row">
								<li class="form-validate">
									<label>工人</label>
									<input type="text" id="code" name="code" style="width:160px;" value="${worker.code }"/>
								</li>
								<li>
									<label>住址</label>
									<input type="text" id="address" name="address" style="width:160px;" value="${worker.address }"/>
	  							</li>
								
							</div>
							<div class="validate-group row">
								<%-- <li>
									<label>工程大区</label>
									<house:DataSelect id="prjRegionCode" className="PrjRegion" keyColumn="code" valueColumn="descr" 
											value="${worker.prjRegionCode }" ></house:DataSelect>
								</li> --%>
								<li>
									<label>工程大区</label>
									<house:dict id="prjRegionCode" dictCode="" sql="select  Code,code+' '+descr Descr from tPrjRegion where expired ='F' " 
									sqlValueKey="Code" sqlLableKey="Descr" value="${worker.prjRegionCode }"></house:dict>
								</li>
								<li>
									<label>交通工具</label>
									<house:xtdm id="vehicle" dictCode="VEHICLE" value="${worker.vehicle }"></house:xtdm>                     
								</li>
							</div>
							<div class="validate-group row">
								<li>
									<label>所属专盘</label>
									<house:DataSelect id="spcBuilder" className="SpcBuilder" keyColumn="code" valueColumn="descr" 
											value="${worker.spcBuilder }"></house:DataSelect>
								</li>
								<li>
									<label>承接工地类型</label>
									<house:xtdmMulit id="rcvPrjType" dictCode="RCVPRJTYPE" selectedValue="${worker.rcvPrjType }"></house:xtdmMulit>                     
								</li>
							</div>
							<div class="validate-group row">
	  							<li>
									<label class="control-textarea">备注</label>
									<textarea id="remarks" name="remarks" rows="2" >${worker.remarks }</textarea>
	  							</li>
							</div>
						</ul>
					</form>
				</div>
			</div>
		</div>
	</body>	
</html>
