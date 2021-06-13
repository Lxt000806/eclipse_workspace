<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>
<!DOCTYPE html>
<html>
<head>
	<title>修改</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
<script type="text/javascript">
$(function() {
	disabledForm("dataForm");
	$("#saveBtn").attr("disabled",true);
});

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
								<li><label>模板编号</label> <input type="text" id="no"
									name="no" value="${baseBatchTemp.no}" readonly />
								</li>
							</div>
							<div class="validate-group">
								<li><label>模板名称</label> <input type="text" id="descr"
									name="descr" value="${baseBatchTemp.descr}" />
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
							<div class="validate-group">
								<li><label>过期</label> <input type="checkbox"
										id="expired" name="expired" value="${baseBatchTemp.expired }"
										${baseBatchTemp.expired!='F' ?'checked':'' } onclick="checkExpired(this)">
								</li>
							</div>
						</ul>
					</form>
				</div>
			</div>
		</div>
</body>
</html>
