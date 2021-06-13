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
	<title>业绩归属员工信息--新增</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_department2.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	
	<script type="text/javascript"> 
	$(function() {
		$("#dataForm").bootstrapValidator({
			message : "This value is not valid",
			feedbackIcons : {
				validating : "glyphicon glyphicon-refresh",
			},
			fields: {  
			
			},
			submitButtons : "input[type='submit']"
		});
	});
	
	$(function(){
		$("#saveBtn").on("click",function(){
			$("#dataForm").bootstrapValidator("validate");
			if(!$("#dataForm").data("bootstrapValidator").isValid()) return;
			var datas = $("#dataForm").serialize();
			$.ajax({
				url:"${ctx}/admin/softPerf/doUpdatePeriod",
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
	function validateRefresh(){
			$("#dataForm").data("bootstrapValidator")
	                    .updateStatus("beginDate", "NOT_VALIDATED",null)  
	                    .validateField("beginDate")
	                     .updateStatus("endDate", "NOT_VALIDATED",null)  
	                    .validateField("endDate") 
	                    .updateStatus("mon", "NOT_VALIDATED",null)  
	                    .validateField("mon")  ;
	}
	
	function validateRefresh_choice(){
		 $("#dataForm").data("bootstrapValidator")
	                 .updateStatus("openComponent_employee_empCode", "NOT_VALIDATED",null)  
	                 .validateField("openComponent_employee_empCode");                    
	}
	function fillData(ret){
		validateRefresh_choice();
	}
	
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
				<form action="" method="post" id="dataForm" class="form-search">
					<input type="hidden" name="jsonString" value=""/>
						<ul class="ul-form">
							<div class="validate-group row" >
							<li class="form-validate">
								<label>编号</label>
								<input type="text" id="no" name="no" readonly="readonly" style="width:160px;" value="${softPerf.no }"/>
							</li>
							<li>
								<label>状态</label>
								<house:xtdm id="status" dictCode="SOFTPERFSTATUS" value="${softPerf.status }" disabled="true"></house:xtdm>                     
							</li>
							<li class="form-validate">
								<label>开始时间</label>
								<input type="text" id="beginDate" name="beginDate" onchange="validateRefresh()" required data-bv-notempty-message="结束时间不能为空" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${softPerf.beginDate}' pattern='yyyy-MM-dd'/>"/>
							</li>
							<li class="form-validate">
								<label>结束时间</label>
								<input type="text" id="endDate" name="endDate" onchange="validateRefresh()" required data-bv-notempty-message="结束时间不能为空" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${softPerf.endDate}' pattern='yyyy-MM-dd'/>"/>
							</li>
							<li class="form-validate">
								<label><span class="required">*</span>月份</label>
								<input type="text" id="mon" name="mon" class="i-date" onchange="validateRefresh()"
									style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyyMM'})"
									value="${softPerf.mon }"/>
							</li>
							<li>
								<label class="control-textarea">说明</label>
								<textarea id="remarks" name="remarks" rows="2">${softPerf.remarks }</textarea>
  							</li>
						</ul>
				</form>
				</div>
			</div>
		</div>
	</body>	
</html>
