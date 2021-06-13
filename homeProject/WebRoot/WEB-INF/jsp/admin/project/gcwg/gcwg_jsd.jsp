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
    <title>结算单打印</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<style type="text/css">
	.form-search .ul-form li {float: none;}
	</style>
	<script type="text/javascript">
	var str = "";
	function printXjd(){
		str = "";
		var reportName = "";
		if ($("#ck_jsd_jsd").prop("checked")){
			str = str + "'" + "JS" + "',";
			reportName = reportName + "gcwg_jsd.jasper,";
		}
		if ($("#ck_jzxq_jsd").prop("checked")){
			str = str + "'" + "JZ" + "',";
			reportName = reportName + "gcwg_jzxq.jasper,";
		}
		if ($("#ck_zcxq_jsd").prop("checked")){
			str = str + "'" + "ZC" + "',";
			reportName = reportName + "gcwg_zcxq.jasper,";
		}
		if ($("#ck_rzxq_jsd").prop("checked")){
			str = str + "'" + "RZ" + "',";	
			reportName = reportName + "gcwg_rzxq.jasper,";
		}
		if ($("#ck_jcxq_jsd").prop("checked")){
			str = str + "'" + "JC" + "',";
			reportName = reportName + "gcwg_jcxq.jasper,";
		}
		if (str==''){
			art.dialog({
				content: "请选择要打印的需求"
			});
			return;
		}
		str = str.substring(0, str.length-1);
		reportName = reportName.substring(0, reportName.length-1);
	   	Global.Print.showPrint(reportName, {
			CustCode: "${custCode}",
			LogoFile : "<%=basePath%>jasperlogo/logo.jpg",
			SUBREPORT_DIR: "<%=jasperPath%>"
		});
	}
	
	function printJsd(){
		if (str.indexOf("JS")>=0){
			var reportName = "gcwg_jsd.jasper";
		   	Global.Print.showPrint(reportName, {
				CustCode: "${custCode}",
				ItemType1: "JS",
				LogoFile : "<%=basePath%>jasperlogo/logo.jpg",
				SUBREPORT_DIR: "<%=jasperPath%>"
			},'',printJzxq);
		}
	}
	
	function printJzxq(){
		if (str.indexOf("JZ")>=0){
			var reportName = "gcwg_jzxq.jasper";
		   	Global.Print.showPrint(reportName, {
				CustCode: "${custCode}",
				ItemType1: "JZ",
				LogoFile : "<%=basePath%>jasperlogo/logo.jpg",
				SUBREPORT_DIR: "<%=jasperPath%>"
			},'',printZcxq);
		}
	}
	
	function printZcxq(){
		if (str.indexOf("ZC")>=0){
			var reportName = "gcwg_zcxq.jasper";
		   	Global.Print.showPrint(reportName, {
				CustCode: "${custCode}",
				ItemType1: "ZC",
				LogoFile : "<%=basePath%>jasperlogo/logo.jpg",
				SUBREPORT_DIR: "<%=jasperPath%>"
			},'',printRzxq);
		}
	}
	
	function printRzxq(){
		if (str.indexOf("RZ")>=0){
			var reportName = "gcwg_rzxq.jasper";
		   	Global.Print.showPrint(reportName, {
				CustCode: "${custCode}",
				ItemType1: "RZ",
				LogoFile : "<%=basePath%>jasperlogo/logo.jpg",
				SUBREPORT_DIR: "<%=jasperPath%>"
			},'',printJcxq);
		}
	}
	
	function printJcxq(){
		if (str.indexOf("JC")>=0){
			var reportName = "gcwg_jcxq.jasper";
		   	Global.Print.showPrint(reportName, {
				CustCode: "${custCode}",
				ItemType1: "JC",
				LogoFile : "<%=basePath%>jasperlogo/logo.jpg",
				SUBREPORT_DIR: "<%=jasperPath%>"
			});
		}
	}
	
	function checkAllXjd(){
		var ck_flag = $("#ck_all_jsd").prop("checked");
		if (ck_flag){
			$("#ck_all_jsd").prop("checked",true);
			$("#id_jsd_check").find("input[type='checkbox']").each(function(){
				$(this).prop("checked",true);
			});
		}else{
			$("#ck_all_jsd").prop("checked",false);
			$("#id_jsd_check").find("input[type='checkbox']").each(function(){
				$(this).prop("checked",false);
			});
		}
	}
	</script>
</head>
<body>
<div class="body-box-form">
	<div class="panel panel-system">
	    <div class="panel-body" >
	      <div class="btn-group-xs" >
	      <button type="button" id="saveBtn" class="btn btn-system" onclick="printXjd()">打印</button>
	      <button type="button" class="btn btn-system" onclick="closeWin(false)">关闭</button>
	      </div>
	    </div>
	</div>
	<div class="panel panel-info">  
        <div class="panel-body">
            <form role="form" class="form-search" id="dataForm_jsd"  action="" method="post" target="targetFrame">
            	<ul class="ul-form">
            		<li style="text-align: left;">
            			<input id="ck_all_jsd" name="ck_all_jsd" type="checkbox" onclick="checkAllXjd()" value="kkk">全选
            		</li>
            		<div id="id_jsd_check" style="position: relative;">
	            		<div style="float: left;">
	            		<li style="text-align: left;">
	           			需求单
	           			</li>
	            		<li>
	           			<input id="ck_jzxq_jsd" name="ck_jzxq_jsd" type="checkbox">基础需求报表
	           			</li>
	           			<li>
	            		<input id="ck_zcxq_jsd" name="ck_zcxq_jsd" type="checkbox">主材需求报表
	            		</li>
	           			<li>
						<input id="ck_rzxq_jsd" name="ck_rzxq_jsd" type="checkbox">软装需求报表
						</li>
	           			<li>
						<input id="ck_jcxq_jsd" name="ck_jcxq_jsd" type="checkbox">集成需求报表
						</li>
	            		</div>
	            		
	            		<div style="float: left;margin-left: 100px;">
	            		<li style="text-align: left;">
						结算单
	            		</li>
	           			<li>
						<input id="ck_jsd_jsd" name="ck_jsd_jsd" type="checkbox">结算单打印
	            		</li>
	            		</div>
            		</div>
            	</ul>
            </form>
         </div>
    </div>
</div>
</body>
</html>

