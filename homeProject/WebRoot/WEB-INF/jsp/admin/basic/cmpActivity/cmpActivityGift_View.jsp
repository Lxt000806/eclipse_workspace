<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>查看礼品明细</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>

<script type="text/javascript">
//校验函数
$(function() {
	$("#itemcode").openComponent_item({valueOnly:'1',showValue:'${cmpactivitygift.itemcode}',readonly:true});	
});

</script>

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
					<input type="hidden" name="m_umState" id="m_umState" value="M"/>
				<ul class="ul-form">
					<li>
						<label><span class="required">*</span>礼品编号</label>
						<input type="text" id="itemcode" name="itemcode"  value="${cmpactivitygift.itemcode}" readonly="true"/>
					</li>
					<li>
						<label>礼品名称</label>
						<input type="text" id="itemcodedescr" name="itemcodedescr"  value="${cmpactivitygift.itemCodeDescr}" readonly="true"/>
					</li>
					<li>
						<label><span class="required">*</span>活动类型</label>
						<house:xtdm  id="Type" dictCode="ACTGIFTTYPE"    value="${cmpactivitygift.type}" onchange="onchange1()" ></house:xtdm>
					</li>
					<li hidden="true">
						<label>活动类型名称</label>
						<input id="typedescr" name="typedescr"   value="${cmpactivitygift.typedescr}"/>
					</li>
			</form>
		</div>
	</div>
</div>
<script src="${resourceRoot}/pub/component_item.js?v=${v}" type="text/javascript"></script>
</body>
</html>
