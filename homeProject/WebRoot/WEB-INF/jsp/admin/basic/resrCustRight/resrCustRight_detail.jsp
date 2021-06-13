<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML>
<html>
  <head>
  
  	<title>查看客户资源分配信息</title>
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
     				<button  id="closeBut" type="button" class="btn btn-system "   onclick="closeWin(false)">关闭</button>
     			</div>
     		</div>
     	</div>
		<div class="panel panel-info" >  
			<div class="panel-body">		
				<form action="" method="post" id="dataForm" enctype="multipart/form-data" class="form-search">
					<house:token></house:token>
						<ul class="ul-form">
							<li>
								<label>一级部门</label>
								<input type="text" id="department1descr" name="department1descr" value="${resrCustRight.department1Descr }" />
							</li>
							<li>
								<label>二级部门编码</label>
								<input type="text" id="department2" name="department2" value="${resrCustRight.department2 }" />
							</li>
							<li>
								<label>项目编码</label>
								<input type="text" id="buildercode" name="buildercode" value="${resrCustRight.builderCode }" />
							</li>
							<li>
								<label>二级部门</label>
								<input type="text" id="department2descr" name="department2descr" value="${resrCustRight.department2Descr }" />
							</li>
							<li>
								<label>项目名称</label>
								<input type="text" id="builderdescr" name="builderdescr" value="${resrCustRight.builderDescr }" />
							</li>
							<li>
								<label>权限类型</label>
								<input type="text" id="righttypedescr" name="righttypedescr" value="${resrCustRight.rightTypeDescr }" />
							</li>
						</ul>
				</form>
			</div>
  		</div>
  	</div>
  </body>
</html>
