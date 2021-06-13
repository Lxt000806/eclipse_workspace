<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
  <head>
  	<title>系统参数--查看</title>
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
     				<button id="closeBut" type="button" class="btn btn-system "   onclick="closeWin()">关闭</button>
				</div>
			</div>
		</div>
		<div class="panel panel-info" >  
			<div class="panel-body">
				<form action="" method="post" id="dataForm" class="form-search">
					<ul class="ul-form">
						<div class="validate-group row">
							<li class="form-validate">
								<label>参数代码 </label> 
								<input type="text" " id="id" name="id" value="${xtcs.id}" readonly="readonly" />
							</li>
							<li class="form-validate">
								<label>说明</label> 
								<input type="text" id="sm" name="sm" value="${xtcs.sm }" />
							</li>
							<li class="form-validate" >
								<label>英文说明</label>
								<input type="text" id="smE" name="smE" value="${xtcs.smE }" />
							</li>
						</div>
					    <div class="validate-group row" style="padding-bottom: 5px;">	
							<li class="form-validate">
								<label class="control-textarea">取值</label>
								<textarea id="qz" name="qz" rows="5">${xtcs.qz}</textarea>
							</li>	
						</div>	
					</ul>		
				</form>
			</div>
  		</div>
  	</div>
  </body>
</html>
