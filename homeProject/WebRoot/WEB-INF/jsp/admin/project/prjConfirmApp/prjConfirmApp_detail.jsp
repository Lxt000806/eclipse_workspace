<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML>
<html>
  <head>
  
  	<title>查看验收申请信息</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
  	
  </head>
  
  <body>
  	<div class="body-box-form">
		<div class="panel panel-system" >
		    <div class="panel-body" >
		    	<div class="btn-group-xs" >
     				<button id="closeBut" type="button" class="btn btn-system "   onclick="closeWin(false)">关闭</button>
				</div>
			</div>
		</div>
		<div class="panel panel-info" >  
			<div class="panel-body">	
				<form action="" method="post" id="dataForm" enctype="multipart/form-data" class="form-search">
					<house:token></house:token>
					<ul class="ul-form">
						<li>
							<label>楼盘</label>
							<input type="text" id="address" name="address" value="${prjConfirmApp.address }" />
						</li>
						<li>
							<label>工程事业部</label>
							<input type="text" id="department1Descr" name="department1Descr" value="${prjConfirmApp.department1Descr }" />
						</li>
						<li>	
							<label>工程部</label>
							<input type="text" id="department2Descr" name="department2Descr" value="${prjConfirmApp.department2Descr }" />
						</li>
						<li>
							<label>监理</label>
							<input type="text" id="projectManDescr" name="projectManDescr" value="${prjConfirmApp.projectManDescr }" />
						</li>
						<li>
							<label>电话</label>
							<input type="text" id="phone" name="phone"  value="${prjConfirmApp.phone }" />
						</li>
						<li>
							<label>申报时间</label>
							<input type="text" id="appDate" name="appDate" value="<fmt:formatDate value='${prjConfirmApp.appDate }' pattern='yyyy-MM-dd HH:mm:ss'/>" />
						</li>
						<li>
							<label>验收人</label>
							<input type="text" id="confirmCZY" name="confirmCZY" value="${prjConfirmApp.confirmCZY }" />
						</li>
						<li>
							<label>验收时间</label>
							<input type="text" id="date" name="date" value="<fmt:formatDate value='${prjConfirmApp.date }' pattern='yyyy-MM-dd HH:mm:ss'/>" />
						</li>
						<li>
							<label>验收评级</label>
							<input type="text" id="prjLevelDescr" name="prjLevelDescr" value="${prjConfirmApp.prjLevelDescr }" />
						</li>
						<li>
							<label>验收申请工人</label>
							<input type="text" id="workerName" name="workerName" value="${prjConfirmApp.workerName }" />
						</li>
						<li>
							<label class="control-textarea">备注</label>
							<textarea id="prjLevelDescr" name="prjLevelDescr" />${prjConfirmApp.remarks }</textarea>
						<li>
					</ul>
				</form>
			</div>
  		</div>
  	</div>
  </body>
</html>
