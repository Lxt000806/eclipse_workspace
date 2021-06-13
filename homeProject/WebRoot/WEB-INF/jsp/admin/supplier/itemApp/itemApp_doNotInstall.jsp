<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<!DOCTYPE html>
<html>
	<head>
		<title>集成进度明细--不能安装</title>
		<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
		<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
		<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
		<META HTTP-EQUIV="expires" CONTENT="0" />
		<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
		<%@ include file="/commons/jsp/common.jsp"%>
		<script type="text/javascript">
			$(function() {
				$("#dataForm").bootstrapValidator({
					message : "This value is not valid",
					feedbackIcons : {/*input状态样式图片*/
						validating : "glyphicon glyphicon-refresh"
					},
					fields : {
						custCode: {
							validators : {
								notEmpty : {
									message : "客户编号不能为空"
								}
							}
						},
						type: {
							validators : {
								notEmpty : {
									message: "类型不能为空"
								}
							}
						},
						date: {
							validators : {
								notEmpty : {
									message: "时间不能为空"
								}
							}
						},
						resPart : {
							validators : {
								notEmpty : {
									message : "请选择责任方"
								}
							}
						},
						isCupboard : {
							validators : {
								notEmpty : {
									message : "是否橱柜不能为空"
								}
							}
						},
						remarks: {
							validators : {
								notEmpty : {
									message : "备注不能为空"
								}
							}
						}
					}
				});
				
			    //初始化descr
				$("#saveBtn").on("click", function() {
					$("#dataForm").bootstrapValidator("validate");
					if(!$("#dataForm").data("bootstrapValidator").isValid()){
						return;
					}
					var datas = $("#dataForm").serialize();
					$.ajax({
						url: "${ctx}/admin/supplierItemApp/doSaveNotInstall",
						type: "post",
						data: datas,
						dataType: "json",
						cache: false,
						error: function(obj){
							showAjaxHtml({"hidden": false, "msg": '保存数据出错~'});
					    },
					    success: function(obj){
				    		art.dialog({
				    			content: obj.msg,
				    			time: 3000,
				    			ok: function(){
				    				if(obj.rs){
				    					closeWin();
				    				}
				    			},
				    			timeCloseButtonEvent: "ok"
				    		});
					    }
					 });
				});
			});
		
			function validateRefresh(){
				 $("#dataForm").data("bootstrapValidator")
			                   .updateStatus("date", "NOT_VALIDATED",null)  
			                   .validateField("date")
			                   .updateStatus("custCode", "NOT_VALIDATED",null)   
			                   .validateField("custCode")
			                   .updateStatus("type", "NOT_VALIDATED",null)  
			                   .validateField("type")
			                   .updateStatus("resPart", "NOT_VALIDATED",null)  
			                   .validateField("resPart")
			                   .updateStatus("isCupboard", "NOT_VALIDATED",null)  
			                   .validateField("isCupboard")
			                   .updateStatus("remarks", "NOT_VALIDATED",null)  
			                   .validateField("remarks");                    
			}
			
			function dateChange(){
				 $("#dataForm").data("bootstrapValidator")
			                   .updateStatus("date", "NOT_VALIDATED",null)  
			                   .validateField("date");
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
						<button type="button" class="btn btn-system" id="closeBut"
							onclick="closeWin(false)">
							<span>关闭</span>
						</button>
					</div>
				</div>
			</div>
			<div class="panel panel-info">
				<div class="panel-body">
					<form action="" method="post" id="dataForm" class="form-search">
						<ul class="ul-form">
							<div class="validate-group row">
								<li class="form-validate">
									<label>客户编号</label> 
									<input type="text" id="custCode" name="custCode" value="${data.custCode }" readonly />
								</li>
							</div>
							<div class="validate-group row">
								<li class="form-validate">
									<label>楼盘</label> 
									<input type="text" id="address" name="address" value="${data.address}" readonly />
								</li>
							</div>
							<div class="validate-group row">
								<li class="form-validate">
									<label>类型</label> 
									<house:xtdm id="type" dictCode="INTDTTYPE" value="${data.type }" disabled="true"></house:xtdm>
								</li>
							</div>
							<div class="validate-group row">
								<li class="form-validate">
									<label>日期</label> 
									<!--  onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" onchange="dateChange()" -->
									<input type="text" id="date" name="date" class="i-date" value="<fmt:formatDate value='${data.date}' pattern='yyyy-MM-dd'/>" readonly/>
								</li>
							</div>
							<div class="validate-group row">
								<li class="form-validate">
									<label>责任方</label> 
									<house:xtdm id="resPart" dictCode="RESPART" value="${data.resPart }" disabled="true"></house:xtdm>
								</li>
							</div>
							<div class="validate-group row">
								<li class="form-validate">
									<label>是否橱柜</label> 
									<house:xtdm id="isCupboard" dictCode="YESNO" value="${data.isCupboard }" disabled="true"></house:xtdm>
								</li>
							</div>
							<div class="validate-group row">
								<li class="form-validate">
									<label class="control-textarea"><span style="color:red">*</span>备注</label>
									<textarea id="remarks" name="remarks">${data.remarks }</textarea>
								</li>
							</div>
						</ul>
					</form>
				</div>
			</div>
		</div>
	</body>
</html>
