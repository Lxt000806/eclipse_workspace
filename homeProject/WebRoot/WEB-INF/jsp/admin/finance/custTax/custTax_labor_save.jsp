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
		<div class="panel panel-info" style="margin: 0px;">  
			<div class="panel-body">
				<form action="" method="post" id="page_form" class="form-search">
					<input type="hidden" name="jsonString" value=""/>
					<input type="hidden" id="lastUpdatedBy" name="lastUpdatedBy" value="${sessionScope.USER_CONTEXT_KEY.czybh}"/>
					<ul class="ul-form">
						<div class="row">
							<li class="form-validate">
								<label><span class="required">*</span>劳务开票日期</label>
								<input type="text" id="date" name="date" class="i-date" style="width:160px;" 
									onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
									value="<fmt:formatDate value='${custTax.date}' pattern='yyyy-MM-dd'/>" />
							</li>
						</div>
						<div class="row">
							<li class="form-validate">
								<label><span class="required">*</span>劳务开票金额</label>
								<input type="text" id="amount" name="amount" style="width:160px;"
									onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\.\d]/g,'');" value="${custTax.amount}" />
							</li>
						</div>
					</ul>
				</form>
			</div>
		</div>
	</div>
</body>	
<script type="text/javascript"> 
$(function() {
	if("${custTax.m_umState}"=="V"){
		$("input[type=text],#saveBtn").attr("disabled",true);
	}
	$("#page_form").bootstrapValidator({
		message : "请输入完整的信息",
		feedbackIcons : {/*input状态样式图片*/
			validating : "glyphicon glyphicon-refresh"
		},
		fields: {  
			date:{  
				validators: {  
					notEmpty: {  
						message: "劳务开票日期不能为空"  
					}
				}  
			},
			amount:{  
				validators: {  
					notEmpty: {  
						message: "劳务开票金额不能为空"  
					}
				}  
			}
		},
		submitButtons : "input[type='submit']"
	});

	$("#saveBtn").on("click",function(){
 		var selectRows = [];
		var datas=$("#page_form").jsonForm();
		$("#page_form").bootstrapValidator("validate");
		if(!$("#page_form").data("bootstrapValidator").isValid()) {
			return;
		}
		// 将page_form json化后赋给selectRows
	 	selectRows.push(datas);
		Global.Dialog.returnData = selectRows;
		closeWin();
	});
});

</script>
</html>
