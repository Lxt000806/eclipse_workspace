<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<!DOCTYPE HTML>
<html>
<head>
	<title>修改统计周期</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script type="text/javascript"> 
	$(function() {
		$("#dataForm").bootstrapValidator({
			message : "This value is not valid",
			feedbackIcons : {
				validating : "glyphicon glyphicon-refresh"
			},
			fields: {  
			
			},
			submitButtons : "input[type='submit']"
		});
	});
	
	$(function(){
		for(var i=2014;i<=2100;i++){
			$("#y").append($("<option/>").text(i).attr("value",i));
		}
		for(var i=1;i<=14;i++){
			$("#m").append($("<option/>").text(i).attr("value",i));
		}
		for(var i=1;i<=6;i++){
			$("#season").append($("<option/>").text(i).attr("value",i));
		}
		$("#y").val("${perfCycle.y}");
		$("#m").val("${perfCycle.m}");
		$("#season").val("${perfCycle.season}");
		$("#saveBtn").on("click",function(){
			$("#page_form").bootstrapValidator("validate");
			if(!$("#page_form").data("bootstrapValidator").isValid()) return;
			var datas = $("#page_form").serialize();
			$.ajax({
				url:"${ctx}/admin/perfCycle/doUpdate",
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
			$("#page_form").data("bootstrapValidator")
	                    .updateStatus("beginDate", "NOT_VALIDATED",null)  
	                    .validateField("beginDate")
	                     .updateStatus("endDate", "NOT_VALIDATED",null)  
	                    .validateField("endDate")  ;
	}
	function validateRefresh_choice(){
		 $("#page_form").data("bootstrapValidator")
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
					<button type="button" class="btn btn-system " id="saveBtn"
						onclick="save()">保存</button>
					<button type="button" class="btn btn-system " id="closeBut"
						onclick="closeWin(false)">关闭</button>
				</div>
			</div>
		</div>
		<div class="panel panel-info">
			<div class="panel-body">
				<form action="" method="post" id="dataForm" class="form-search">
					<house:token></house:token>
					<input type="hidden" name="m_umState" id="m_umState"
						value="${perfCycle.m_umState}" />
					<ul class="ul-form">
						<div class="validate-group">
							<li><label><span class="required"></span>编号</label> <input
								type="text" id="no" value="${perfCycle.no}"
								 readonly="readonly" />
							</li>
						</div>
					</ul>
				</form>
			</div>
		</div>
		<div class="btn-panel">
			<ul class="nav nav-tabs">
				<li class="active"><a href="#main" data-toggle="tab">主项目</a></li>
			</ul>
			<div class="tab-content">
				<ul class="ul-form">
					<div id="main" class="tab-pane fade in active">
						<div class="pageContent">
							<form action="" method="post" id="page_form"
								class="form-search"
								style="padding-top: 25px;padding-left: 30px;">
								<ul class="ul-form">
								<input type="hidden" name="no" value="${perfCycle.no}"/>
										<li><label>归属年份</label> <select id="y" name="y"></select>
										</li>
										<li class="form-validate"><label>开始时间</label> <input
											type="text" id="beginDate" name="beginDate"
											onchange="validateRefresh()" required
											data-bv-notempty-message="开始时间不能为空" class="i-date"
											style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
											value="<fmt:formatDate value='${perfCycle.beginDate}' pattern='yyyy-MM-dd'/>"
											/>
										</li>
										<li><label>归属月份</label> <select id="m" name="m"></select>
										</li>
										<li class="form-validate"><label>结束时间</label> <input
											type="text" id="endDate" name="endDate"
											onchange="validateRefresh()" required
											data-bv-notempty-message="结束时间不能为空" class="i-date"
											style="width:160px;"onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
											value="<fmt:formatDate value='${perfCycle.endDate}' pattern='yyyy-MM-dd'/>"
											/>
										</li>
										<li><label>归属季度</label> <select id="season" name="season"></select>
										</li>
										<li><label>状态</label> <house:xtdm id="status"
												dictCode="SOFTPERFSTATUS" value="${perfCycle.status}" disabled="true"></house:xtdm>
										</li>
										<li>
											<label class="control-textarea">备注说明</label>
											<textarea id="remarks" name="remarks" rows="3">${perfCycle.remarks}</textarea>
										</li>	
								</ul>
							</form>
						</div>
					</div>
				</ul>
			</div>
		</div>
</body>
</html>
