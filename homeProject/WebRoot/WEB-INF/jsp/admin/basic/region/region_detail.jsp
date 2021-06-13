<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<title>项目区域管理--查看</title>
		<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
		<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
		<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
		<META HTTP-EQUIV="expires" CONTENT="0" />
		<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
		<%@ include file="/commons/jsp/common.jsp" %>
		<script src="${resourceRoot}/pub/component_company.js?v=${v}" type="text/javascript"></script>
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
						<input type="text" id="expired" name="expired" value="${region.expired}" hidden="true" />	
						<ul class="ul-form">					  
							<li class="form-validate">
								<label>区域编号</label>
								<input type="text" id="code" name="code" value="${region.code}" readonly="readonly"/>
							</li>
							<li class="form-validate">	
								<label>区域名称</label>
								<input type="text" id="descr" name="descr" value="${region.descr}" readonly="readonly"/>
							</li>
							<li>
								<label>是否指定工人</label>
								<house:dict id="isSpcWorker" dictCode="" sql="select cbm,cbm+' '+note note from tXTDM a where a.ID='YESNO' " 
						 				sqlValueKey="cbm" sqlLableKey="note" value="${region.isSpcWorker}" disabled="true"></house:dict>
							</li>	
							<li>
								<label for="cmpCode">公司编号</label>
								<input type="text" id="cmpCode" name="cmpCode">
							</li>
							<li>
								<label>集成出货模式</label>
								<house:dict id="intSendType" dictCode="" sql="select cbm,cbm+' '+note note from tXTDM a where a.ID='INTSENDTYPE' " 
						 				sqlValueKey="cbm" sqlLableKey="note" value="${region.intSendType}"></house:dict>
							</li>
							<li class="form-validate">	
								<label>防水远程补贴</label>
								<input type="text" id="waterLongPension" name="waterLongPension" value="${region.waterLongPension}" readonly="readonly"/>
							</li>
							<li>
								<label>过期</label>
								<input type="checkbox" id="expired_show" name="expired_show"
									onclick="checkExpired(this)" ${region.expired=="T"?"checked":""} />
							</li>
						</ul>	
					</form>
				</div>
	  		</div>
	  	</div>
	  	<script type="text/javascript">
	  		$(function () {
	  			$("#cmpCode").openComponent_company({
	     			showValue:"${region.cmpCode}",
	     			showLabel:"${region.cmpDescr}",
	     		});
	     		$("#openComponent_company_cmpCode").prop("readonly", true);
	  		});
	  	</script>
	 </body>
</html>


