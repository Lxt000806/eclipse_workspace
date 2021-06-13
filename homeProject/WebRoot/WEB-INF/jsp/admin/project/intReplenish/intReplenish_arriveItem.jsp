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
					<button type="button" class="btn btn-system" id="closeBut" onclick="closeWin(false)">
						<span>关闭</span>
					</button>
				</div>
			</div>
		</div>
		<div class="panel panel-info" style="margin: 0px;height: 207px;">  
			<div class="panel-body">
				<form action="" method="post" id="page_form" class="form-search">
					<input type="hidden" name="jsonString" value=""/>
					<input type="hidden" name="pk" value="${intReplenishDT.pk}"/>
					<input type="hidden" id="lastUpdatedBy" name="lastUpdatedBy" value="${sessionScope.USER_CONTEXT_KEY.czybh}"/>
					<ul class="ul-form">
						<div class="row">
							<li>
								<label>楼盘</label>
								<input type="text" id="address" name="address" style="width: 160px;"
									value="${intReplenishDT.address}" readonly="true" />
							</li>
							<li class="form-validate">
								<label for="intspl">供应商</label>
								<input type="text" id="intspl" name="intspl">
							</li>
							<li class="form-validate">
								<label><span class="required">*</span>实际到货日期</label> 
								<input type="text" id="arriveDate" name="arriveDate" class="i-date"
									onclick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd',readOnly:true})" 
									/>
							</li>
							<li class="form-validate" style="max-height: 120px;">
                                <label class="control-textarea">
                                    到货备注
                                </label>
                                <textarea id="arriveRemarks" name="arriveRemarks" 
                                	style="height: 70px;width: 160px;">${intReplenishDT.arriveRemarks}</textarea>
                            </li>
						</div>
					</ul>
				</form>
			</div>
		</div>
	</div>
	<script type="text/javascript"> 
		$(function() {
			$("#intspl").openComponent_supplier({
				showValue: "${intReplenishDT.intSpl}",
				showLabel: "${intReplenishDT.intSplDescr}",
				condition: {itemType1:"JC",isDisabled:"1"},
				readonly: true
			});
			$("#page_form").bootstrapValidator({
				message : "请输入完整的信息",
				feedbackIcons : {/*input状态样式图片*/
					validating : "glyphicon glyphicon-refresh"
				},
				fields: {  
					arriveDate:{  
						validators: {  
							notEmpty: {  
								message: "实际到货时间不能为空"  
							}
						}  
					}
				},
				submitButtons : "input[type='submit']"/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
			});
			$("#saveBtn").on("click",function(){
				$("#page_form").bootstrapValidator("validate");
				if(!$("#page_form").data("bootstrapValidator").isValid()) return;
				var datas = $("#page_form").jsonForm();
				$.ajax({
					url:"${ctx}/admin/intReplenish/doArriveSave",
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
