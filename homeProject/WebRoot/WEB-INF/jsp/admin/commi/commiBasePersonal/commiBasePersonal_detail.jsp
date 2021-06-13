<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>CommiBasePersonal明细</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
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
						<input type="hidden" name="jsonString" value="" /> 
						<input type="hidden" id="m_umState" name="m_umState" value="A" />
						<ul class="ul-form">	
							<li class="form-validate">
								<label><span class="required">*</span>基装类型1</label> 
								<house:dict id="baseItemType1" dictCode="" sql="select code,code+' '+Descr fd from tBaseItemType1 order by DispSeq"
					    		 sqlLableKey="fd" sqlValueKey="code" value="${commiBasePersonal.baseItemType1}"></house:dict>
							</li>
							<li class="form-validate">
								<label><span class="required">*</span>提成点</label> 
								<input type="text" id="commiPer" name="commiPer" value="${commiBasePersonal.commiPer}" />
							</li>
						</ul>
					</form>
				</div>
			</div>
		</div>
	</body>
</html>

