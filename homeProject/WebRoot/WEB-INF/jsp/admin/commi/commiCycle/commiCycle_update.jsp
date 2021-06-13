<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<!DOCTYPE HTML>
<html>
<head>
	<title>编辑统计周期</title>
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
		$("#saveBtn").on("click",function(){
			$("#page_form").bootstrapValidator("validate");
			if(!$("#page_form").data("bootstrapValidator").isValid()) return;
			var datas = $("#page_form").serialize();
			$.ajax({
				url:"${ctx}/admin/commiCycle/doUpdate",
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
						value="${commiCycle.m_umState}" />
					<ul class="ul-form">
						<div class="validate-group">
							<li><label><span class="required"></span>编号</label> <input
								type="text" id="no" name="no" value="${commiCycle.no}"
								placeholder="保存自动生成" readonly="readonly" />
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
								<input type="hidden" name="no" value="${commiCycle.no}">
								<ul class="ul-form">
									<li class="form-validate">
										<label>月份</label>
										<input type="text" id="mon" name="mon" class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyyMM'})" 
										required data-bv-notempty-message="开始时间不能为空" value="${commiCycle.mon}"/>
									</li>
									<li><label>状态</label> <house:xtdm id="status"
											dictCode="SOFTPERFSTATUS" value="${commiCycle.status}" disabled="true"></house:xtdm>
									</li>
									<li>
										<label>浮动业绩开始月份</label>
										<input type="text" id="floatBeginMon" name="floatBeginMon" class="i-date" 
											onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyyMM'})" value="${commiCycle.floatBeginMon}"/>
									</li>
									<li>
										<label>浮动业绩截止月份</label>
										<input type="text" id="floatEndMon" name="floatEndMon" class="i-date" 
											onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyyMM'})" value="${commiCycle.floatEndMon}"/>
									</li>
									<li>
										<label class="control-textarea">备注说明</label>
										<textarea id="remarks" name="remarks" rows="3">${commiCycle.remarks }</textarea>
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
