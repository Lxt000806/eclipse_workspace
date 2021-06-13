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
	<title>员工信息工作经验查看</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script type="text/javascript">
	$(function(){
		$("#company").val("${map.company[0]}");
		$("#position").val("${map.position[0]}");
		$("#salary").val("${map.salary[0]}");
	    $("#begindate").val("${map.begindate[0]}");
		$("#enddate").val("${map.enddate[0]}");
		$("#leaversn").val("${map.leaversn[0]}");		
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
						<input type="text" name="pk" id="pk" hidden="true"/>
						<input type="text" name="number" id="number" hidden="true">
						<ul class="ul-form">
							<li class="form-validate">
								<label>公司</label>
								<input type="text" name="company" id="company" value="${map.company[0]}" readonly="readonly"/>
								</li>
							<li class="form-validate">
								<label>职位</label>
								<input type="text" id="position" name="position" value="${map.position[0]}" readonly="readonly"/>
							</li>
							<li>
								<label>工薪</label>
								<input type="text" id="salary" name="salary" value="${map.salary[0]}" readonly="readonly"/>
							</li>
							 <li class="form-validate">
								<label>开始时间</label>
								<input type="text" id="begindate" name="begindate" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" readonly="readonly"/>
							</li>
							<li>
								<label>结束时间</label>
								<input type="text" id="enddate" name="enddate" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" readonly="readonly"/>
							</li>
							<li>
								<label class="control-textarea">离职原因</label>
								<textarea id="leaversn" name="leaversn" rows="3" readonly="readonly">${map.leaversn[0]}</textarea>
							</li> 	
						</ul>
					</form>
				</div>
			</div>
		</div>
	</body>	
</html>
