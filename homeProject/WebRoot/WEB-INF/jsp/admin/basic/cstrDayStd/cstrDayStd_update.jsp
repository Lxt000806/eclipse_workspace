<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>
<!DOCTYPE html>
<html>
<head>
	<title>添加</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
<script type="text/javascript">
$(function() {
	if("${cstrDayStd.m_umState}"=="V"){
		disabledForm("dataForm");
		$("#saveBtn").attr("disabled",true);
	}
	$("#saveBtn").on("click", function() {
		var datas = $("#dataForm").serialize();
		$.ajax({
			url : "${ctx}/admin/cstrDayStd/doUpdate",
			type : "post",
			data : datas,
			dataType : "json",
			cache : false,
			error : function(obj) {
				showAjaxHtml({
					"hidden" : false,
					"msg" : "保存数据出错~"
				});
			},
			success : function(obj) {
				if (obj.rs) {
					art.dialog({
						content : obj.msg,
						time : 1000,
						beforeunload : function() {
							closeWin();
						}
					});
				} else {
					$("#_form_token_uniq_id").val(obj.token.token);
					art.dialog({
						content : obj.msg,
						width : 200
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
					<button type="button" id="saveBtn" class="btn btn-system "
						>保存</button>
					<button type="button" class="btn btn-system "
						onclick="closeWin(false)">关闭</button>
				</div>
			</div>
		</div>
		<div class="panel panel-info">
			<div class="panel-body">
				<div class="body-box-form">
					<form role="form" class="form-search" id="dataForm" action=""
						method="post" target="targetFrame">
						<input type="hidden" id="pk" name="pk" value="${cstrDayStd.pk}" />
						<house:token></house:token>
						<ul class="ul-form">
							<div class="validate-group">
								<li class="form-validate"><label>客户类型</label> <house:dict id="custType"
										dictCode=""
										sql="select code ,code+' '+desc1 descr from tcustType where expired='F'"
										sqlValueKey="Code" sqlLableKey="Descr"
										value="${cstrDayStd.custType.trim()}">
									</house:dict>
								</li>
								<li class="form-validate"><label>从面积</label> <input type="text" id="fromArea"
									name="fromArea" value="${cstrDayStd.fromArea}"
									onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\-\.\d]/g,'');" />
									<span
									style="position: absolute;left:290px;width: 30px;top:3px;">平方</span>
								</li>
							</div>
							<div class="validate-group">
								<li class="form-validate"><label>户型</label> <house:xtdm id="layout"
										dictCode="LAYOUT" value="${cstrDayStd.layout}"></house:xtdm></li>
								<li class="form-validate"><label>到面积</label> <input type="text" id="toArea"
									name="toArea" value="${cstrDayStd.toArea}"
									onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\-\.\d]/g,'');" />
									<span
									style="position: absolute;left:290px;width: 30px;top:3px;">平方</span>
								</li>
							</div>
							<div class="validate-group">
								<li class="form-validate"><label>优先级</label> <input type="text" id="prior"
									name="prior"
									onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\-\.\d]/g,'');"
									value="${cstrDayStd.prior}" />
								</li>
								<li class="form-validate"><label>施工工期</label> <input type="text"
									id="constructDay" name="constructDay"
									onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\-\.\d]/g,'');"
									value="${cstrDayStd.constructDay}" /> <span
									style="position: absolute;left:290px;width: 30px;top:3px;">天</span>
								</li>
							</div>
							<div class="validate-group">
								<li><label>过期</label> <input type="checkbox"
										id="expired" name="expired" value="${cstrDayStd.expired }"
										${cstrDayStd.expired!='F' ?'checked':'' } onclick="checkExpired(this)">
								</li>
							</div>
						</ul>
					</form>
				</div>
			</div>
		</div>
</body>
</html>
