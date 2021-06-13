<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>ProgCheckApp明细</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
	<script type="text/javascript">
	$(function() {
		$("#custCode").openComponent_customer({showLabel:"${progCheckApp.descr}",showValue:"${progCheckApp.custCode}",disabled:true});
	});
	</script>
</head>
<body>
<div class="body-box-form">
	<div class="content-form">
		<!--panelBar-->
					<div class="panel panel-system" >
    <div class="panel-body" >
      <div class="btn-group-xs" >
						<button type="button" class="btn btn-system " id="closeBut" onclick="closeWin(false)">
							<span>关闭</span>
						</button>
					</div>
			</div>
			</div>
		<div class="panel panel-info" >  
         <div class="panel-body">
			  <form role="form" class="form-search" id="dataForm"  action="" method="post" target="targetFrame" >
						<ul class="ul-form">
						<li>
							<label>编号</label>
							<input type="text" id="pk" name="pk" style="width:160px;"  value="${progCheckApp.pk}" readonly="readonly"/>
						</li>
						<li>
							<label>客户编号</label>
							<input type="text" id="custCode" name="custCode" style="width:160px;"  value="${progCheckApp.custCode}" readonly="readonly"/>
						</li>
						<li>
							<label>楼盘</label>
							<input type="text" id="address" name="address" style="width:160px;"  value="${progCheckApp.address}" readonly="readonly"/>
						</li>
						<li>
							<label>申请日期</label>
							<input type="text" id="appDate" name="appDate" style="width:160px;"  value="<fmt:formatDate value='${progCheckApp.appDate}' pattern='yyyy-MM-dd HH:mm:ss'/>" readonly="readonly"/>
						</li>
						<li>
							<label>申请人员</label>
							<input type="text" id="appCzyDescr" name="appCzyDescr" style="width:160px;"  value="${progCheckApp.appCzyDescr}" readonly="readonly"/>
						</li>
						<li>
							<label>最后更新时间</label>
							<input type="text" id="lastUpdate" name="lastUpdate" style="width:160px;"  value="<fmt:formatDate value='${progCheckApp.lastUpdate}' pattern='yyyy-MM-dd HH:mm:ss'/>" readonly="readonly"/>
						</li>
						<li>
							<label>最后更新人员</label>
							<input type="text" id="lastUpdatedBy" name="lastUpdatedBy" style="width:160px;"  value="${progCheckApp.lastUpdatedBy}" readonly="readonly"/>
						</li>
						<li>
							<label>操作日志</label>
							<input type="text" id="actionLog" name="actionLog" style="width:160px;"  value="${progCheckApp.actionLog}" readonly="readonly"/>
						</li>
						<li>
							<label class="control-textarea"> 说明</label>
								<textarea rows="2" id="remarks" name="remarks" maxlength="200" readonly="readonly">${progCheckApp.remarks }</textarea>
						</li>
						<li>
							过期
								<input type="checkbox" id="expired_show" name="expired_show" value="${progCheckApp.expired}" onclick="checkExpired(this)" ${progCheckApp.expired=="T"?"checked":"" }/>
						</li>
					</ul>
			</form>
			</div>
			</div>
	</div>
</div>
</body>
</html>

