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
	$("#saveBtn").on("click", function() {
		var descr=$("#descr").val(); 
		if(descr==""){
			art.dialog({
				content : "请输入模板名称！",
				width : 200
			});
			return;
		}
		var result=checkExist(descr);
		if(result){
			art.dialog({
				content : "已存在名称为【"+descr+"】的模板，不允许保存！",
			});
			return;
		}
		var datas = $("#dataForm").serialize();
		$.ajax({
			url : "${ctx}/admin/baseBatchTemp/doSave",
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
function checkExist(descr){
	var result=false;
	$.ajax({
		url : '${ctx}/admin/baseBatchTemp/checkExistTempDescr',
		type : 'post',
		data : {
			'no' :"${baseBatchTemp.no}",'m_umState':"${baseBatchTemp.m_umState}",'descr':descr
		},
		async:false,
		dataType : 'json',
		cache : false,
		error : function(obj) {
			showAjaxHtml({
				"hidden" : false,
				"msg" : '保存数据出错~'
			});
		},
		success : function(obj) {
			if(obj.length>0){
				result=true;
			}
		}
	});
	return result;
}
</script>
</head>
<body>
	<div class="body-box-form">
		<div class="panel panel-system">
			<div class="panel-body">
				<div class="btn-group-xs">
					<button type="button" id="saveBtn" class="btn btn-system "
						onclick="save()">保存</button>
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
						<house:token></house:token>
						<ul class="ul-form">
							<div class="validate-group">
								<li ><label>模板编号</label> <input type="text" id="no"
										name="no" value="${baseBatchTemp.no}" placeholder="保存时自动生成" readonly
										/>
								</li>
							</div>
							<div class="validate-group">
								<li><label>模板名称</label> <input type="text" id="descr"
									name="descr" value="${baseBatchTemp.descr}"
									/>
								</li>
							</div>
							<div class="validate-group">
								<li><label>客户类型</label> <house:dict id="custType"
										dictCode=""
										sql="select code ,code+' '+desc1 descr from tcustType where expired='F'"
										sqlValueKey="Code" sqlLableKey="Descr"
										value="${baseBatchTemp.custType}">
									</house:dict>
								</li>
							</div>
						</ul>
					</form>
				</div>
			</div>
		</div>
</body>
</html>
