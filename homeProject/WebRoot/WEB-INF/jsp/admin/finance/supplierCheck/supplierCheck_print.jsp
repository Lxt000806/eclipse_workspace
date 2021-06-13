<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>
<!DOCTYPE html>
<html>
	<head>
		<title>供应商结算打印</title>
		<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
		<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
		<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
		<META HTTP-EQUIV="expires" CONTENT="0" />
		<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
		<%@ include file="/commons/jsp/common.jsp" %>
		<script src="${resourceRoot}/pub/component_supplier.js?v=${v}" type="text/javascript"></script>
		
		<script type="text/javascript">
			function print(){
				var jasper = "";
				if("${data.page}" == "1"){
					jasper = "supplierCheck_report1.jasper";
				}else{
					jasper = "supplierCheck_report2.jasper";
				}
				
				Global.Print.showPrint(jasper, 
					{
						no:"${data.no}",
						companyName:"${data.cmpnyName}",
						titles:"${data.titles}"
					}
				);
			}
		</script>
	</head>
	    
	<body>
		<div class="body-box-list">
			<div class="panel panel-system">
			    <div class="panel-body">
			    	<div class="btn-group-xs">
	    				<button id="saveBut" type="button" class="btn btn-system" onclick="print()">打印</button>
	    				<button id="closeBut" type="button" class="btn btn-system" onclick="closeWin()">关闭</button>
					</div>
				</div>
			</div>
		</div>
	</body>
</html>


