<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>查看楼层</title>
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
		      <button type="button" class="btn btn-system "   onclick="closeWin(false)">关闭</button>
	      </div>
	    </div>
	</div>
	<div class="panel panel-info" >  
         <div class="panel-body">
             <form role="form" class="form-search" id="dataForm"  action="" method="post" target="targetFrame">
					<house:token></house:token>
					<input type="hidden" name="m_umState" id="m_umState" value="A"/>
				<ul class="ul-form">
					<li>
							<label><span class="required">*</span>起始楼层</label>
							<input type="text" id="beginFloor" name="beginFloor" style="width:160px;" onkeyup="value=value.replace(/[^\-?\d.]/g,'')"
								onafterpaste="this.value=this.value.replace(/\D/g,'')"  value="${carryRuleFloor.beginFloor}" />
					</li>
					<li>
							<label><span class="required">*</span>截止楼层</label>
								<input type="text" id="endFloor" name="endFloor" style="width:160px;" onkeyup="value=value.replace(/[^\-?\d.]/g,'')"
								onafterpaste="this.value=this.value.replace(/\D/g,'')"  value="${carryRuleFloor.endFloor}" />
					</li>
					<li>	
							<label><span class="required">*</span>初始金额</label>
							<input type="text" id="cardAmount" name="cardAmount" style="width:160px;" onkeyup="value=value.replace(/[^\-?\d.]/g,'')"
								onafterpaste="this.value=this.value.replace(/\D/g,'')"  value="${carryRuleFloor.cardAmount}" />
					</li>
					<li>
							<label><span class="required">*</span>递增金额</label>
								<input type="text" id="incValue" name="incValue" style="width:160px;" onkeyup="value=value.replace(/[^\-?\d.]/g,'')"
								onafterpaste="this.value=this.value.replace(/\D/g,'')"  value="${carryRuleFloor.incValue}" />
					</li>
				</ul>	
			</form>
		</div>
	</div>
</div>
<script src="${resourceRoot}/pub/component_item.js?v=${v}" type="text/javascript"></script>
</body>
</html>
