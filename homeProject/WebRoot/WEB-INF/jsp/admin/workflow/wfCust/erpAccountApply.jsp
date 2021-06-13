<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>erp申请</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
</head>
<body>
    <div class="query-form" style="border-bottom:0px;margin-top:0px">
    	<form role="form" class="form-search" id="dataForm" action="" method="post" target="targetFrame">
    		<input id="processDefinitionId" name="processDefinitionId" value="${processDefinitionId }" hidden="true"/>
    		<input id="wfProcNo" name="wfProcNo" value="${wfProcNo }" hidden="true"/>
    		<input id="taskId" name="taskId" value="${taskId }"  hidden="true"/>
    		<input id="department" name="department" hidden="true" />
	   		<input id="comment" name="comment" value="" hidden=""/>
	   		<input id="empComment" name="empComment" value="" hidden=""/><!-- 评论   -->
	   		<input id="wfProcInstNo" name="wfProcInstNo" value="${wfProcInstNo }" hidden=""/>
	   		<input id="processInstId" name="processInstId" value="${piId }" hidden=""/>
        	<house:token></house:token>
        	<ul class="ul-form">
	         	<div class="validate-group row">
	         		<li class="form-validate">
					        <label><span class="required">*</span>服务类型</label>
					        <select id="fp__tWfCust_erpAccountApply__0__type" name="fp__tWfCust_erpAccountApply__0__type" style="width: 160px;">
					            <option value="账号开通" ${ datas.fp__tWfCust_erpAccountApply__0__type == '账号开通' || m_umState == 'A' ? 'selected' : ''}>账号开通</option>
					            <option value="密码重置" ${ datas.fp__tWfCust_erpAccountApply__0__type == '密码重置' ? 'selected' : ''}>密码重置</option>
					        </select>
					    </li>
	         	</div>
        	</ul>
        </form>
     </div>
</body>
</html>

