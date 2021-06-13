<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>
<!DOCTYPE html>
<html>
<head>
	<title>公司销售产品管理--查看</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_company.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
$(function() {
	$("#cmpCode").openComponent_company({
		showValue:"${cmpCustType.cmpCode}",
		showLabel:"${cmpCustType.cmpDescr}",
	});
	$("#saveBtn").attr("disabled",true);
	disabledForm("dataForm");
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
						 <input type="hidden" id="pk" name="pk" value="${cmpCustType.pk}" />
						<input type="hidden" id="cmpDescr" name="cmpDescr" value="${cmpCustType.cmpDescr}" />
						<input type="hidden" id="custTypeDescr" name="custTypeDescr" value="${cmpCustType.custTypeDescr}" />
						<house:token></house:token>
						<ul class="ul-form">
							<div class="validate-group">
								<li><label></span>公司</label> <input type="text" id="cmpCode"
									name="cmpCode"
									value="${cmpCustType.cmpCode}" />
								</li>
								<li><label>客户类型</label> <house:dict id="custType" dictCode=""
										sql="select code ,code+' '+desc1 descr from tcustType where expired='F'"
										sqlValueKey="Code" sqlLableKey="Descr"
										value="${cmpCustType.custType}">
									</house:dict>
								</li>
							</div>
							<div class="validate-group">
								<li><label>公司名称</label> <input type="text"
									id="cmpnyName" name="cmpnyName" style="width:160px;"
									value="${cmpCustType.cmpnyName}"
									 />
								</li>
								<li><label>logo文件名</label> <input
									type="text" id="logoFile" name="logoFile" style="width:160px;"
									value="${cmpCustType.logoFile}"
									 />
								</li>
							</div>
							<div class="validate-group">
								<li><label>公司全称</label> <input type="text"
									id="cmpnyFullName" name="cmpnyFullName" style="width:451px;"
									value="${cmpCustType.cmpnyFullName}"
									 />
								</li>
							</div>
							<div class="validate-group">
								<li><label>公司地址</label> <input type="text"
									id="cmpnyAddress" name="cmpnyAddress" style="width:451px;"\
									value="${cmpCustType.cmpnyAddress}"
									/>
								</li>
							</div>
							<div class="validate-group">
								<li>
									<label>工程款收款单位</label>
									<house:dict id="payeeCode" dictCode="" 
										sql=" select rtrim(Code) code,rtrim(Code)+' '+rtrim(Descr) descr from tTaxPayee where Expired = 'F' "
										sqlLableKey="descr" sqlValueKey="code" value="${cmpCustType.payeeCode }"></house:dict>
								</li>
								<li>
									<label>设计费收款单位</label>
									<house:dict id="designPayeeCode" dictCode="" 
										sql=" select rtrim(Code) code,rtrim(Code)+' '+rtrim(Descr) descr from tTaxPayee where Expired = 'F' "
										sqlLableKey="descr" sqlValueKey="code" value="${cmpCustType.designPayeeCode}"></house:dict>
								</li>
							</div>
							<div class="validate-group">
								<li><label>过期</label> <input type="checkbox"
										id="expired" name="expired" value="${cmpCustType.expired }"
										${cmpCustType.expired!='F' ?'checked':'' } onclick="checkExpired(this)">
								</li>
							</div>
						</ul>
					</form>
				</div>
			</div>
		</div>
</body>
</html>
