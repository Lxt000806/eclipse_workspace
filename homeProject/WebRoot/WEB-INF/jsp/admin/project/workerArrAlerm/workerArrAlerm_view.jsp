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
	<title>工人安排提醒</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	
	<script type="text/javascript"> 
	$(function(){
		Global.LinkSelect.initSelect("${ctx}/admin/item/itemType","itemType1","itemType2","itemType3");
		Global.LinkSelect.setSelect({firstSelect:"itemType1",
								firstValue:"${workerArrAlerm.itemType1 }",
								secondSelect:"itemType2",
								secondValue:"${workerArrAlerm.itemType2 }",
								});
		(function setJobType(){
			$("#jobType").val("${workerArrAlerm.jobType }");
		})();
		$("#saveBtn").on("click",function(){
			var datas = $("#dataForm").serialize();
			var code=$.trim($("#code").val());
			var custType=$.trim($("#custType").val());
			
			var custSceneDesi=$.trim($("#custSceneDesi").val());
			$.ajax({
				url:"${ctx}/admin/workerArrAlarm/doSave",
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
	</script>
</head>
	<body>
		<div class="body-box-form">
			<div class="panel panel-system">
			    <div class="panel-body">
			    	<div class="btn-group-xs">
						<button type="button" class="btn btn-system " id="closeBut" onclick="closeWin(false)">
							<span>关闭</span>
						</button>
					</div>
				</div>
			</div>
			<div class="panel panel-info">  
				<div class="panel-body">
					<form action="" method="post" id="dataForm" class="form-search">
						<input type="hidden" name="jsonString" value=""/>
						<input type="hidden" id="pk" name="pk" value="${workerArrAlerm.pk }"/>
						<ul class="ul-form">
							<div class="validate-group row">
								<li class="form-validate">
									<label>工种</label>
									 <house:DataSelect id="workType12" className="WorkType12" keyColumn="code" valueColumn="descr" value="${workerArrAlerm.workType12 }"></house:DataSelect>
								</li>
								<li>
									<label>是否判断需求</label>
									<house:xtdm id="isNeedReq" dictCode="YESNO" value="${workerArrAlerm.isNeedReq }"></house:xtdm>                     
								</li>
							</div>
							<div class="validate-group row">
								<li>
									<label>材料类型1</label>
									<select id="itemType1" name="itemType1" style="width: 160px;"  ></select>
								</li>
								<li>
									<label>材料类型2</label>
										<select id="itemType2" name="itemType2" style="width: 160px;" ></select>
								</li>
							</div>
							<div class="validate-group row">
								<li>
									<label>任务类型</label>
									 <house:DataSelect id="jobType" className="JobType" keyColumn="code" valueColumn="descr" value="${workerArrAlerm.jobType }"></house:DataSelect>
								</li>
								<li>
									<label>是否判断干系人</label>
									<house:xtdm id="isNeedStakeholder" dictCode="YESNO" style="width:160px;"  value="${workerArrAlerm.isNeedStakeholder}" disabled="true"/>
								</li>
							</div>
						</ul>
					</form>
				</div>
			</div>
		</div>
	</body>	
</html>
