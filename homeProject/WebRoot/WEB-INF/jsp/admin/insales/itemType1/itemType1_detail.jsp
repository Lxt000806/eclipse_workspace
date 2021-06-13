<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>添加ItemType1</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_wareHouse.js?v=${v}" type="text/javascript"></script>
	<script type="text/javascript">
//校验函数
$(function() {
	$("#whCode").openComponent_wareHouse({
		showValue:"${itemType1.whCode}",
		showLabel:"${itemType1.whDescr}",
		readonly:true
	});
});
</script>
</head>   
<body>
<div class="body-box-form" >
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
						<label>编码</label>
						<input type="text" id="code" name="code" value="${itemType1.code}" readonly="readonly"/>
					</li>
					<li class="form-validate">	
						<label>名称</label>
						<input type="text" id="descr" name="descr" value="${itemType1.descr}" readonly="readonly" />
					</li>
					<li class="form-validate">	
						<label>营销费用点数</label>
						<input type="text" id="profitPer" name="profitPer" value="${itemType1.profitPer}" readonly="readonly"/>
					</li>
					<li class="form-validate">	
						<label>是否解单领料</label>
						<house:xtdm id="isSpecReq" dictCode="YESNO" value="${itemType1.isSpecReq}" disabled="true"></house:xtdm>
					</li>
					<li class="form-validate">	
						<label>显示顺序</label>
						<input type="text" id="dispSeq" name="dispSeq" value="${itemType1.dispSeq}" readonly="readonly"/>
					</li>
					<li class="form-validate">
						<label >发货公司仓</label>
						<input type="text" id="whCode" name="whCode"   value="${itemType1.whCode}"/>    
					</li>
					<li style="margin-left: 90px">
						<lable>过期</lable>
						<input type="checkbox" id="expired_show" name="expired_show"
						 onclick="checkExpired(this)" ${itemType1.expired=="T"?"checked":""}/>
					</li>
				</ul>		
			</form>
		</div>
  	</div>
</div>
</body>
</html>
