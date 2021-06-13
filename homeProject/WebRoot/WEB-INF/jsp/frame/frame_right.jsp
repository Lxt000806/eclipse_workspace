<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
		<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
		<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
		<META HTTP-EQUIV="expires" CONTENT="0" />
		<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
		<title></title>
		<%@ include file="/commons/jsp/common.jsp" %>
		<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
		<script type="text/javascript">
			function onFrameRightLoad(){
				if("${showMsg}" == "true"){
					setInterval(function(){
						$("#dataTableDb").jqGrid("setGridParam",{url: "${ctx}/admin/wfProcInst/findWfProcInst",postData:{type: "1"},page:1,sortname:''}).trigger("reloadGrid");
					},600000);
				}
			}
			function tableNullTip(tableId, refresh) {
				var ids = $("#" + tableId).jqGrid("getDataIDs");
				if(ids && ids.length > 0 || refresh) {
					$("#gbox_" + tableId).css("display", "block");
					$("#gbox_" + tableId).next().css("display", "none");
				}else{
					$("#gbox_" + tableId).css("display", "none");
					$("#gbox_" + tableId).next().css("display", "block");
				}
			}
		</script>
		<style type="text/css">
			.panel-system {
				display: inline-block;
				width: 48%;
				background-color: white;
				border: 1px solid #dddddd;
				border-bottom:0;
				height: 365px;
			}
			.tableNull {
				position: relative;
			    border: 1px solid #ddd;
			    -webkit-border-radius: 3px;
			    -moz-border-radius: 3px;
			    border-radius: 3px;
			    height: 332px;
			}
			.tableNull > img {
				display: block;
				margin: 0 auto;
				margin-top: 80px;
			}
			.btn-group-xs {
				position: relative;
				padding-left: 10px;
				font-size: 12px;
				height: 20px;
			}
			.btn-group-xs > div {
				position: absolute;
				display: inline-block;
				width: 80%;
				right: 10px;
				text-align: right;
			}
			.btn-system {
				display: inline-block;
			    margin-bottom: 0;
			    font-size: 14px;
			    font-weight: normal;
			    text-align: center;
			    white-space: nowrap;
			    vertical-align: middle;
			    -ms-touch-action: manipulation;
			    touch-action: manipulation;
			    cursor: pointer;
			    -webkit-user-select: none;
			    -moz-user-select: none;
			    -ms-user-select: none;
			    user-select: none;
			    background-image: none;
			    border: 1px solid transparent;
			    
			    padding: 1px 5px;
			    font-size: 12px;
			    line-height: 1.5;
			    border-radius: 3px;
			}
			.table-bordered > thead > tr > th {
				border: 0;
				text-align: left!important;
			}
			.table-bordered > tbody > tr > td {
				border-top: 0;
				border-left: 0;
				border-right: 0;
			}
			.table > tbody > tr:nth-child(odd) > td, .ui-jqgrid-labels {
				background: rgba(221, 221, 221, 0.35)
			}
 			.table> tbody > tr:nth-child(odd).success > td {
				background: #198EDE;
			}
 			.table > tbody > tr.active > td {
				background: rgba(25, 142, 222, 0.3)!important;
			}
			.ui-jqgrid tr.jqgrow td, .ui-jqgrid .ui-jqgrid-htable thead th[role=columnheader] {
				padding-left: 13px;
			}
			.pageContent {
				width: 100%;
			    float: left;
			    margin-top: 2px;
			}	
			.ui-jqgrid {
				margin-top: 2px;
			}
		</style>
	</head>
	<body onload="onFrameRightLoad()">
		<div class="body-box-list" style="display: ${showMsg?'block':'none'};border-top: 1px solid #dddddd;">
			<jsp:include page="plates/waitDeal.jsp">
				<jsp:param value="0" name="index"/>
			</jsp:include>
			<jsp:include page="plates/personMessage.jsp">
				<jsp:param value="1" name="index"/>
			</jsp:include>
			<jsp:include page="plates/cmpNotice.jsp">
				<jsp:param value="2" name="index"/>
			</jsp:include>
			<c:forEach items="${plateList}" var="item" varStatus="varStatus">
				<jsp:include page="${item}">
					<jsp:param value="${varStatus.index + 3}" name="index"/>
				</jsp:include>
			</c:forEach>
		</div>
	</body>
</html>
