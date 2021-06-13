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
	<title>业绩计算周期--新增</title>
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
				validating : "glyphicon glyphicon-refresh"
			},
			fields: { 
				y:{  
					validators: {  
						notEmpty: {  
							message: "归属年份不能为空"  
						}
					}  
				},
				m:{  
					validators: {  
						notEmpty: {  
							message: "归属月份不能为空"  
						}
					}  
				},
				season:{  
					validators: {  
						notEmpty: {  
							message: "归属季度不能为空"  
						}
					}  
				}, 
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
				url:"${ctx}/admin/prjPerf/doSavePeriod",
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
							<li>
								<label>编号</label>
								<input type="text" id="no" name="no" placeholder="保存自动生成" readonly="readonly" style="width:160px;"/>
							</li>
						</div>
						<div class="validate-group row" >
							<li class="form-validate">
								<label><span class="required">*</span>归属年份</label>
								<house:dict id="y" dictCode="" sql="select p.number+53 code ,p.number+53 descr from master..spt_values p 
								where p.type = 'p' and p.number between 1957 and 2048 " sqlValueKey="code" sqlLableKey="descr" value="${y }"></house:dict>
							</li> 
							<li class="form-validate">
								<label><span class="required">*</span>开始时间</label>
								<input type="text" id="beginDate" name="beginDate" onchange="validateRefresh()" required data-bv-notempty-message="开始时间不能为空" class="i-date"  style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"/>
							</li>
						</div>	
						<div class="validate-group row" >
							<li class="form-validate">
								<label><span class="required">*</span>归属月份</label>
								<house:dict id="m" dictCode="" sql="select p.number code ,p.number descr from master..spt_values p 
									where p.type = 'p' and p.number between 1 and 13 " sqlValueKey="code" sqlLableKey="descr" value="${m }"></house:dict>
							</li>											
							<li class="form-validate">
								<label><span class="required">*</span>结束时间</label>
								<input type="text" id="endDate" name="endDate" onchange="validateRefresh()" required data-bv-notempty-message="结束时间不能为空" class="i-date"  style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"/>
							</li>
						</div>
						<div class="validate-group row" >
							<li class="form-validate">
								<label><span class="required">*</span>归属季度</label>
									<house:dict id="season" dictCode="" sql="select p.number code ,p.number descr from master..spt_values p 
									where p.type = 'p' and p.number between 1 and 6 " sqlValueKey="code" sqlLableKey="descr"></house:dict>
							</li>
							<li class="form-validate">
								<label>状态</label>
								<house:xtdm id="status" dictCode="PRJPERFSTATUS" value="1" disabled="true"></house:xtdm>                     
							</li>
						</div>
						<div class="validate-group row" >
							<li>
								<label class="control-textarea">备注</label>
								<textarea id="remarks" name="remarks" rows="3"></textarea>
							</li>	
						</div>
					</ul>
				</form>
			</div>
		</div>
	</div>
</body>	
</html>
