<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>项目大类--修改</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
</head>   
<body>
<div class="body-box-form" >
	<div class="panel panel-system" >
	    <div class="panel-body" >
    		<div class="btn-group-xs" >
   				<button id="closeBut" type="button" class="btn btn-system " onclick="closeWin()">关闭</button>
			</div>
		</div>
	</div>
	<div class="panel panel-info" >  
		<div class="panel-body">
			<form action="" method="post" id="dataForm" class="form-search">
				<input type="text" id="expired" name="expired" hidden="true" value="${builderGroup.expired}"> 
				
				<ul class="ul-form">	
					<div class="validate-group row">				  
						<li>
							<label>项目大类编号</label>
							<input type="text" id="code" name="code" value="${builderGroup.code}" readonly="readonly" />
						</li>
						<li class="form-validate">
							<label>项目大类名称</label>
							<input type="text" id="descr" name="descr" value="${builderGroup.descr }" readonly="readonly"/>
						</li>
					</div>
				    <div class="validate-group row">
						<li class="form-validate">
							<label class="control-textarea">备注</label>
							<textarea id="remarks" name="remarks" rows="4" disabled="disabled">${builderGroup.remarks}</textarea>
						</li>
					</div>
				</ul>
				<div class="validate-group row" style="margin-left: 90px;">
					<li>
						<label>过期</label>
						<input type="checkbox" id="expired_show" name="expired_show"
						 onclick="checkExpired(this)" ${builderGroup.expired=="T"?"checked":""}/>
					</li>
			   </div>			
			</form>
		</div>
  	</div>
</div>
</body>
</html>
