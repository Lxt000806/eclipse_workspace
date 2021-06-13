<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://" + request.getServerName() + ":"+request.getServerPort()+path+"/";
String jasperPath = PathUtil.WEB_INF+"jsp/jasper/";
%>
<!DOCTYPE html>
<html>
<head>
	<title>查看</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript"> 
$(function(){
	disabledForm();
	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/sendNotice/goItemAppJqGrid",
		postData:{code:"${map.custcode}",jobType:"${map.jobtype}"},
		height:$(document).height()-$("#content-list").offset().top-82,
		styleUI: 'Bootstrap', 
		rowNum:100000000,
		colModel : [
			{name: "no", index: "no", width: 70, label: "领料单号", sortable: true, align: "left"},
			{name: "confirmdate", index: "confirmdate", width: 120, label: "审核时间", sortable: true, align: "left",formatter: formatTime},
			{name: "sendtypedescr", index: "sendtypedescr", width: 70, label: "发货类型", sortable: true, align: "left"},
			{name: "suppldescr", index: "suppldescr", width: 100, label: "供应商", sortable: true, align: "left"},
			{name: "whdescr", index: "whdescr", width: 100, label: "仓库", sortable: true, align: "left"},
		],
	});
});
</script>
</head>
	<body>
		<div class="body-box-form">
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
						  <form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
								<house:token></house:token>
								<input type="hidden" name="jsonString" value="" />
								<ul class="ul-form">
										<li>
											<label>楼盘</label>
											<input type="text" id="address" name="address" style="width:160px;" value="${map.address }"/>
										</li>
										<li>
											<label>材料类型1</label>
											<input type="text" id="itemtype1descr" name="itemtype1descr" style="width:160px;" value="${map.itemtype1descr }"/>
										</li>
										<li>
											<label>项目任务</label>
											<input type="text" id="jobtypedescr" name="jobtypedescr" style="width:160px;" value="${map.jobtypedescr }"/>
										</li>
										<li>
											<label>应送货节点</label>
											<input type="text" id="shouldsendnode" name="shouldsendnode" style="width:160px;" value="${map.shouldsendnode }"/>
										</li>
										<li>
											<label>节点类型</label>
											<input type="text" id="nodedatetype" name="nodedatetype" style="width:160px;" value="${map.nodedatetype }"/>
										</li>
										<li>
											<label>节点触发时间</label>
											<input type="text" id="nodetriggerdate" name="nodetriggerdate" style="width:160px;" value="${map.nodetriggerdate }"/>
										</li>
										<li>
											<label>款项到位</label>
											<input type="text" id="moneyinfull" name="moneyinfull" style="width:160px;" value="${map.moneyinfull }"/>
										</li>
										<li>
											<label>差额</label>
											<input type="text" id="shouldbanlance" name="shouldbanlance" style="width:160px;" value="${map.shouldbanlance }"/>
										</li>
										<li>
											<label>发货备注</label>
											<input type="text" id="sendremarks" name="sendremarks" style="width:160px;" value="${map.sendremarks }"/>
										</li>
								</ul>
						  </form>
				   </div>
			   </div>
			   <div id="content-list">
					<table id= "dataTable"></table>
				</div>
		</div>
	</body>	
</html>
