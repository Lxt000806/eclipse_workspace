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
    <title>打印</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
<script type="text/javascript">
$(function(){
	if("${costRight}"=="0"){
		$("tbody tr:eq(2) td:lt(2)").css("display","none");
		$("tbody tr:eq(3) td:lt(3)").css("display","none");
	}

$("#purchase").each(function(){
	$("#purchase").click(function(){
	        $("#purchase_name").removeAttr("checked");
	        $("#purchase_money").removeAttr("checked");
	        $("#purchase_all").removeAttr("checked");
	    });
	    $("#purchase_name").click(function(){
			$("#purchase").removeAttr("checked");
			$("#purchase_money").removeAttr("checked");
			$("#purchase_all").removeAttr("checked");
	        
	    });
	    $("#purchase_money").click(function(){
			$("#purchase_name").removeAttr("checked");
			$("#purchase").removeAttr("checked");
			$("#purchase_all").removeAttr("checked");
	    });
	    $("#purchase_all").click(function(){
	     	$("#purchase_name").removeAttr("checked");
	        $("#purchase_money").removeAttr("checked");
	        $("#purchase").removeAttr("checked");
	    });
	});
});
function printXjd(){
	var setName = "";
	if ($("#purchase").prop("checked")){
		setName = setName +"purchase" ;
	}
	if ($("#purchase_name").prop("checked")){
		setName = setName + "purchase_name" ;
	}
	if ($("#purchase_money").prop("checked")){
		setName = setName + "purchase_money";
	}
	if ($("#purchase_all").prop("checked")){
		setName = setName + "purchase_all" ;
	}
	if (setName==''){
		art.dialog({
			content: "请选择要打印的需求"
		});
		return;
	}
  	var reportName = setName+".jasper";
  	Global.Print.showPrint(reportName, {
		PUNo:"${purchase.no }",
		CZYDescr:"${purchase.CZYDescr}",
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
		$("#ck_all_xjd").removeProp("checked");
		$("#id_xjd_check").find("input[type='checkbox']").each(function(){
			$(this).removeProp("checked");
		});
	}
}
</script>
</head>
<body>
<div class="body-box-form">
	<div class="content-form">
	<!--panelBar-->
		<div class="panel panel-system" >
				<div class="panel-body" >
					<div class="btn-group-xs" >
								<button type="button" class="btn btn-system " onclick="printXjd()">
									<span>保存</span>
								</button>
								<button type="button" class="btn btn-system " id="closeBut" onclick="closeWin(false)">
									<span>关闭</span>
								</button>
					</div>
				</div>
			</div>
		<div class="edit-form">
			<form action="" method="post" id="dataForm">
				<table cellspacing="0" cellpadding="0" width="100%">
					<col width="72"/>
					<col width="150"/>
					<col width="72"/>
					<col width="150"/>
					<tbody>
						<tr>
							<td id="id_xjd_check" class="td-value" colspan="4" style="height: 30px;border-bottom: 0px;">
								&nbsp;&nbsp;<input id="purchase" name="purchase" type="checkbox" checked="checked" >采购单
							</td>
						</tr>
						<tr>
							<td id="id_xjd_check" class="td-value" colspan="4" style="height: 30px;border-top: 0px;border-bottom: 0px;">
								&nbsp;&nbsp;<input id="purchase_name" name="purchase_name" type="checkbox" >采购单(带名称)
							</td>
						</tr>
						<tr id="money">
							<td id="id_xjd_check" class="td-value" colspan="4" style="height: 30px;border-bottom: 0px;border-top: 0px;">
								&nbsp;&nbsp;<input id="purchase_money" name="purchase_money" type="checkbox" >采购单(带金额)
							</td>
						</tr >
						<tr id="all">
							<td id="id_xjd_check" class="td-value" colspan="4" style="height: 30px;border-top: 0px;">												
								&nbsp;&nbsp;<input id="purchase_all" name="purchase_all" type="checkbox" >采购单(带名称和金额)
							</td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
	</div>
</div>
</body>
</html>

