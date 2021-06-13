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
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<title>材料结算管理--打印</title>
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_builderGroup.js?v=${v}" type="text/javascript"></script>
	
	<script type="text/javascript">
	var printJSD="false";
	var printReq="false";
	var page="";
	/**初始化表格*/
	$(function(){
		$("#jsdBox").click(function(){
			if(this.checked){
				printJSD="true";
			}else{
				printJSD="false";
			}
		});
		$("#zcBox").click(function(){
			if(this.checked){
				printReq="zc";
			}else{
				printReq="false";
			}
		});
		$("#rzBox").click(function(){
			if(this.checked){
				printReq="rz";
			}else{
				printReq="false";
			}
		});
		$("#jcBox").click(function(){
			if(this.checked){
				printReq="jc";
			}else{
				printReq="false";
			}
		});
	});
	
	function doPrint(){
		var reportName = "cljsgl_main.jasper";
		if(printReq=="false" && printJSD=="false"){
			art.dialog({
					content: "请选择要打印的报表！"
				});
			return;	
		}
		if(printJSD=="true" && printReq=="false"){
			reportName="cljsgl_jsd";
			page="";
		}
		if(printJSD=="true" && printReq=="jc"){
			reportName="cljsgl_JcJsd";
			page="";
		}
		if(printJSD=="true" && (printReq=="rz" || printReq=="zc")){
			reportName="cljsgl_main";
			page="1";
		}
		if(printJSD=="false" && printReq=="zc"){
			reportName="cljsgl_ZC";
			page="page";
		}
		if(printJSD=="false" && printReq=="rz"){
			reportName="cljsgl_RZ";
			page="page";
		}
		if(printJSD=="false" && printReq=="jc"){
			reportName="cljsgl_JC";
			page="page";
		}
		var logoFile="<%=basePath%>jasperlogo/";
		logoFile=logoFile+$.trim("${logoFile}");
		
		Global.Print.showPrint(reportName+".jasper", {
			No:$.trim("${itemCheck.no}"),
			ItemType1:$.trim("${itemCheck.itemType1}"),
			CustCode:$.trim("${itemCheck.custCode}"),
			page:page,
			LogoFile :logoFile,
			SUBREPORT_DIR: "<%=jasperPath%>"
		});	
		
	}
	function hidden(){
		if ($.trim("${itemCheck.itemType1}")=="ZC"){
			$("#ZCprintCheck").removeAttr("hidden");
			$("#ZCprint").removeAttr("hidden");
		}else if ($.trim("${itemCheck.itemType1}")=="RZ"){
			$("#RZprintCheck").removeAttr("hidden");
			$("#RZprint").removeAttr("hidden");
		}else if ($.trim("${itemCheck.itemType1}")=="JC"){
			$("#JCprintCheck").removeAttr("hidden");
			$("#JCprint").removeAttr("hidden");
		}
	}
	</script>
</head>
    
<body  onload="hidden()">
<input type="hidden" name="jsonString" value=""/>
<div class="body-box-form" >
	<div class="panel panel-system" >
    <div class="panel-body" >
      <div class="btn-group-xs" >
      	<button type="button" class="btn btn-system" onclick="doPrint()">打印</button>
	     <button type="button" class="btn btn-system" onclick="closeWin()">关闭</button>
      </div>
   	</div>
	</div>
	<div class="panel panel-info" >  
         <div class="query-form" style="border: none" >
			<form action="" method="post" id="page_form">
			    <input type="hidden" name="jsonString" value="" />
					<table id="selectTable">
					<tbody>
						<tr style="border: none">
							<td style="width: 20px;border:none;padding-bottom: 5px;" >
								<input type="checkbox" name="jcys" id="jsdBox" value="1" style="vertical-align:middle;margin-top:-1px;margin-left:30px"/>结算单打印
							</td>
							
							<td id="ZCprintCheck" style="width: 20px;border:none;padding-bottom: 5px" hidden="true">
								<input type="checkbox" id="zcBox" name="printId" value="2" style="vertical-align:middle;margin-top:-1px;" />主材需求报表
							</td>
							
							<td id="RZprintCheck" style="width: 20px;border:none;padding-bottom: 5px" hidden="true">
								<input type="checkbox" id="rzBox" name="printId" value="2" style="vertical-align:middle;margin-top:-1px;"/>软装需求报表
							</td>
							
							<td id="JCprintCheck" style="width: 20px;border:none;padding-bottom: 5px" hidden="true">
								<input type="checkbox" id="jcBox" name="printId" value="2" style="vertical-align:middle;margin-top:-1px;"/>集成需求报表
							</td>
						</tr>					
					</tbody>
					<td hidden="true">		
						<label>材料类型1</label>
						<input id="itemType1" name="itemType1" value="${itemCheck.itemType1}" disabled="true" />
					</td>
					<td hidden="true">
						<label>结算单号</label>
						<input type="text" id="no" name="no" readonly="true" value="${itemCheck.no}"/>
					</td>
					<td hidden="true">
						<label>客户编号</label>
						<input type="text" id="custCode" name="custCode" readonly="true" value="${itemCheck.custCode}"/>
					</td>
				</table>
			</form>
		</div>
		</div>
<script src="${resourceRoot}/pub/component_wareHouse.js?v=${v}" type="text/javascript"></script>
<script>


</script>	
</body>
</html>


