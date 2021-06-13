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
    <title>巡检单</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script type="text/javascript">
	function printXjd(){
		var str = "";
		if ($("#ck_zcxq_xjd").prop("checked")){
			str = str + "'" + "ZC" + "',";
		}
		if ($("#ck_rzxq_xjd").prop("checked")){
			str = str + "'" + "RZ" + "',";	
		}
		if ($("#ck_jcxq_xjd").prop("checked")){
			str = str + "'" + "JC" + "',";
		}
		if (str==''){
			art.dialog({
				content: "请选择要打印的需求"
			});
			return;
		}
		str = str.substring(0, str.length-1);
	   	var reportName = "gdxj_main.jasper";
	   	Global.Print.showPrint(reportName, {
			CustCode: "${customer.code}",
			ItemType1: str,
			LogoFile : "<%=basePath%>jasperlogo/logo.jpg",
			SUBREPORT_DIR: "<%=jasperPath%>" 
		});
	}
	function checkAllXjd(){
		var ck_flag = $("#ck_all_xjd").prop("checked");
		if (ck_flag){
			$("#ck_all_xjd").prop("checked",true);
			$("#id_xjd_check").find("input[type='checkbox']").each(function(){
				$(this).prop("checked",true);
			});
		}else{
			//$("#ck_all_xjd").removeProp("checked");
			$("#ck_all_xjd").prop("checked",false);
			$("#id_xjd_check").find("input[type='checkbox']").each(function(){
				//$(this).removeProp("checked");
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
            <form role="form" class="form-search" id="dataForm_xjd"  action="" method="post" target="targetFrame">
            	<ul class="ul-form">
            		<li>
            			<input id="ck_all_xjd" name="ck_all_xjd" type="checkbox" onclick="checkAllXjd()" value="kkk" checked="checked">全选
            		</li>
            		<li id="id_xjd_check">
	            		<input id="ck_zcxq_xjd" name="ck_zcxq_xjd" type="checkbox" checked="checked">主材需求
						<input id="ck_rzxq_xjd" name="ck_rzxq_xjd" type="checkbox" checked="checked">软装需求
						<input id="ck_jcxq_xjd" name="ck_jcxq_xjd" type="checkbox" checked="checked">集成需求
            		</li>
            	</ul>
            </form>
         </div>
    </div>
                  
</div>
</body>
</html>

