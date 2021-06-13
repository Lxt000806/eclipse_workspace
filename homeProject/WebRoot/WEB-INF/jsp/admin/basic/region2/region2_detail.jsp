<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<title>项目区域管理2--查看</title>
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
						<input type="text" id="expired" name="expired" value="${region2.expired}" hidden="true" />	
						<ul class="ul-form">					  
							<li class="form-validate">
								<label>区域编号</label>
								<input type="text" id="code" name="code" value="${region2.code}" readonly="readonly"/>
							</li>
							<li class="form-validate">	
								<label>区域名称</label>
								<input type="text" id="descr" name="descr" value="${region2.descr}" readonly="readonly"/>
							</li>
							<li>
								<label>区域分类</label>
								<house:dict id="regionCode" dictCode="" sql="select code,code+' '+descr descr from tRegion a where a.Expired='F' order by Code ASC " 
				 				sqlValueKey="code" sqlLableKey="descr" value="${region2.regionCode}" disabled="true"></house:dict>
							</li>	
							<li>
								<label>工程大区</label>
								<house:dict id="prjRegionCode" dictCode="" 
									sql="select Code,Code+' '+Descr descr from tPrjRegion where Expired='F' order by Code " 
				 					sqlValueKey="code" sqlLableKey="descr" value="${region2.prjRegionCode}"></house:dict>
							</li>
						</ul>	
						<div class="validate-group row">
							<li>
								<label>过期</label>
								<input type="checkbox" id="expired_show" name="expired_show"
									onclick="checkExpired(this)" ${region2.expired=="T"?"checked":""}/>
							</li>
						</div>		
					</form>
				</div>
	  		</div>
	  	</div>
	 </body>
</html>


