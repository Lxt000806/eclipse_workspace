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
					<input type="hidden" name="no" value="${itemApp.no}"/>
					<input type="hidden" id="lastUpdatedBy" name="lastUpdatedBy" value="${sessionScope.USER_CONTEXT_KEY.czybh}"/>
					<ul class="ul-form">
						<div class="row">
							<li>
								<label>客户地址</label>
								<input type="text" name="custAddress" value="${itemApp.custAddress}" readonly="true">
							</li>
							<li class="form-validate" id="statusLi">
								<label><span class="required">*</span>委托加工状态</label>
								<house:xtdm id="entrustProcStatus" dictCode="EntrProcStatus" style="width:160px;" 
									value="${itemApp.entrustProcStatus}"/>
							</li>
							<li>
								<label>委外发出日期</label>
								<input type="text" id="entrustProcSendDate" name="entrustProcSendDate" class="i-date"
									onclick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd',readOnly:true})" 
									value="<fmt:formatDate value='${itemApp.entrustProcSendDate}' pattern='yyyy-MM-dd HH:mm:ss'/>"
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
			$("#entrustProcStatus option[value='']").remove();
			$("#entrustProcSendDate").prop("disabled", true);
			// 根据状态，改变日期
			$("#entrustProcStatus").on("change", function () {
				if ("0" == $(this).val()) {
					$("#entrustProcSendDate").val("");
				} else {
					$("#entrustProcSendDate").val(formatTime(new Date()));
				}
			});
			$("#page_form").bootstrapValidator({
                message : "请输入完整的信息",
                feedbackIcons : {
                    validating : "glyphicon glyphicon-refresh"
                },
                fields: { 
                    entrustProcStatus:{ 
                        validators: {  
                            notEmpty: {  
                                message: "委托加工状态不允许为空"  
                            },
                        }  
                    },
                }
            });
			$("#saveBtn").on("click",function(){
				$("#page_form").bootstrapValidator("validate");
            	if(!$("#page_form").data("bootstrapValidator").isValid()) return;
				var datas = $("#page_form").jsonForm();
				$.ajax({
					url: "${ctx}/admin/itemApp/doEntrustProcessSign",
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
