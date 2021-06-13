<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>度量单位--查看</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp" %>
<script type="text/javascript">
</script>
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
							<li class="form-validate">
								<label>度量单位编号</label>
								<input id="code" name="code" value="${uom.code}" type="text" readonly="readonly"/>
							</li>
							<li class="form-validate" >	
								<label>中文名称</label>
								<input type="text" id="descr" value="${uom.descr}" name="descr" readonly="readonly"/>
							</li>
						</ul>		
					</form>
				</div>
	  		</div>
	  	</div>
	 </body>
</html>


