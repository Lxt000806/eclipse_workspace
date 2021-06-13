<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>操作日志明细</title>
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
					<ul class="ul-form">
						<li>
							<label>操作日期</label>
							<input type="text" value="<fmt:formatDate value="${czrz.czdate}" pattern="yyyy-MM-dd HH:mm:ss"/>" />
						</li>
						<li>	
							<label>操作员编号</label>
							<input type="text" value="${czrz.czybh}" />
						</li>
						<li>	
							<label>模块代码</label>
							<input type="text" value="${czrz.mkdm}" />
						</li>
						<li>	
							<label>相关主键</label>
							<input type="text" value="${czrz.refPk}" />
						</li>
						<li>	
							<label>操作类型</label>
							<input type="text" value="${czrz.czlx}" />
						</li>
						<li style="max-height:100px;height:100px">	
							<label class="control-textarea">摘要</label>
							<textarea rows="6">${czrz.zy}</textarea>				
						</li>	
					</ul>
				</form>
			</div>
		</div>
	</div>
</body>
</html>

