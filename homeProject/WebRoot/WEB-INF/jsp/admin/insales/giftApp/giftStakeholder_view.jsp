<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://" + request.getServerName() + ":"+request.getServerPort()+path+"/";
String jasperPath = PathUtil.WEB_INF+"jsp/jasper/";
%>

<!DOCTYPE HTML>
<html>
<head>
<title>礼品领用明细-新增</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_roll.js?v=${v}" type="text/javascript"></script>
    <script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">

$(function() {
	$("#role").openComponent_roll({showLabel:"${giftStakeholder.roleDescr}",showValue:"${giftStakeholder.role}",readonly:true});
    $("#empCode").openComponent_employee({showLabel:"${giftStakeholder.empDescr}",showValue:"${giftStakeholder.empCode}",readonly:true});
});
</script>
</head>
<body>
<div class="body-box-form" >
	<div class="panel panel-system" >
	    <div class="panel-body" >
	    	<div class="btn-group-xs" >
				<button type="button" class="btn btn-system "  id="closeBut" onclick="closeWin(false)">关闭</button>
			</div>
		</div>
	</div>
	<div class="panel panel-info" >  
		<div class="panel-body">
			<form action="" method="post" id="page_form" class="form-search">
				<house:token></house:token>
				<input type="hidden" name="m_umState" id="m_umState" value="${giftApp.m_umState}"/>
				<ul class="ul-form">
						<li>
							<label ><span class="required">*</span>角色</label>
							<input type="text" id="role" name="role" style="width:160px;" value="${giftStakeholder.role}" disabled="true"/>     
						</li>
						<li>
							<label ><span class="required">*</span>员工</label>
							<input type="text" id="empCode" name="empCode" style="width:160px;"  value="${giftStakeholder.empCode}" disabled="true"/>    
						</li>
						<li>
							<label ><span class="required">*</span>一级部门</label>
							<house:department1 id="department1" onchange="changeDepartment1(this,'department2','${ctx }')" value="${giftStakeholder.department1 }"  disabled="true"></house:department1>   
						</li>
						<li>
							<label>二级部门</label>
							<house:department2 id="department2" dictCode="${giftStakeholder.department1 }" value="${giftStakeholder.department2}" disabled="true" ></house:department2>
						</li>
						<li >
							<label >分摊比例</label>
						    <input type="text" id="sharePer" name="sharePer" style="width:160px;" onkeyup="value=value.replace(/[^\-?\d.]/g,'')" value="${giftStakeholder.sharePer}"  />
						</li>
						<li hidden="true">
							<label>一级部门名称</label>
							<input type="text" id="department1Descr" name="department1Descr" style="width:160px;" value="${giftStakeholder.department1Descr}"/>
						</li>
						<li hidden="true">
							<label>二级部门名称</label>
							<input type="text" id="department2Descr" name="department2Descr" style="width:160px;" value="${giftStakeholder.department2Descr}"/>
						</li>
						<li hidden="true">
							<label>角色名称</label>
							<input type="text" id="roleDescr" name="roleDescr" style="width:160px;" value="${giftStakeholder.roleDescr}"/>
						</li>
					    <li hidden="true">
							<label>员工姓名</label>
							<input type="text" id="empDescr" name="empDescr" style="width:160px;" value="${giftStakeholder.empDescr}"/>
						</li>
						<li hidden="true">
							<label>最后修改时间</label>
							<input type="text" id="lastUpdate" name="lastUpdate"  value="<fmt:formatDate value='${giftAppDetail.lastUpdate}' pattern='yyyy-MM-dd HH:mm:ss'/>"/>
					   </li>
							
						<li hidden="true">
							<label>最后修改人</label>
							<input type="text" id="lastUpdatedBy" name="lastUpdatedBy"  value="${giftAppDetail.lastUpdatedBy}"/>
					   </li>
					</ul>
			</form>
		</div>
	</div>			
</div>
</body>
</html>
