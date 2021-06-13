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
	<title>现场设计师查看</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	
	<script type="text/javascript"> 
	$(function(){
		$("#code").openComponent_customer({showValue:"${customer.code}",showLabel:"${customer.descr}",readonly:true});	
		$("#designer").openComponent_employee({showValue:"${customer.designMan}",showLabel:"${customer.designManDescr}",readonly:true});	
		$("#custSceneDesi").openComponent_employee({showValue:"${customer.custSceneDesi}",showLabel:"${customer.custSceneDesiDescr}"});	
	
	});
	</script>
</head>
	<body>
		<div class="body-box-form">
			<div class="panel panel-system">
			    <div class="panel-body">
			      <div class="btn-group-xs">
						<button type="button" class="btn btn-system" id="closeBut" onclick="closeWin(false)">
							<span>关闭</span>
						</button>
					</div>
				</div>
			</div>
			<div class="panel panel-info">  
				<div class="panel-body">
				<form action="" method="post" id="dataForm" class="form-search">
					<input type="hidden" name="jsonString" value="" />
					<ul class="ul-form">
						<div class="validate-group row">
							<li><label>客户编号</label> <input type="text" id="code"
								name="code" style="width:160px;" value="${customer.code }"
								placeholder="保存自动生成" readonly="readonly" />
							</li>
							<li><label>楼盘</label> <input type="text" id="address"
								name="address" style="width:160px;" value="${customer.address }"
								readonly="true" />
							</li>
						</div>
						<div class="validate-group row">
							<li class="form-validate"><label>客户类型</label> <house:xtdm
									id="custType" dictCode="CUSTTYPE" value="${customer.custType }"
									disabled="true"></house:xtdm>
							</li>
							<li><label>客户状态</label> <house:xtdm id="status"
									dictCode="CUSTOMERSTATUS" value="${customer.status }"
									disabled="true"></house:xtdm>
							</li>
						</div>
						<div class="validate-group row">
							<li><label>现场设计师</label> <input type="text"
								id="custSceneDesi" name="custSceneDesi"
								value="${customer.custSceneDesi }" />
							</li>
							<li class="form-validate"><label>设计师</label> <input
								type="text" id="designer" name="designer" style="width:160px;"
								value="${customer.designMan }" />
							</li>
						</div>
						<div class="validate-group row">
							<li class="form-validate"><label>签单时间</label> <input
								type="text" id="signDate" name="signDate" class="i-date"
								style="width:160px;"
								onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
								value="<fmt:formatDate value='${customer.signDate }' pattern='yyyy-MM-dd '/>"
								disabled="true" />
							</li>
							<li class="form-validate"><label>工程总价</label> <input
								type="text" id="contractFee" name="contractFee"
								style="width:160px;" value="${customer.contractFee}"
								readonly="readonly" />
							</li>
						</div>
						<div class="validate-group row">
							<li class="form-validate"><label>面积</label> <input
								type="text" id="area" name="area" style="width:160px;"
								value="${customer.area}" readonly="readonly" /> <span
								style="position: absolute;left:290px;width: 30px;top:3px;">㎡</span>
							</li>
							<li class="form-validate"><label>户型</label> <house:xtdm
									id="layout" dictCode="LAYOUT" value="${customer.layout }"
									disabled="true"></house:xtdm>
							</li>
						</div>
						<div class="validate-group row">
							<li class="form-validate"><label>设计师电话</label> <input
								type="text" id="designManPhone" name="designManPhone"
								style="width:160px;" value="${designManPhone}"
								readonly="readonly" />
							</li>
						</div>
					</ul>
				</form>
			</div>
			</div>
		</div>
	</body>	
</html>
