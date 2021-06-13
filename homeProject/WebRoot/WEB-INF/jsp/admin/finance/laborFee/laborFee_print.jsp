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
	<title>人工费用管理打印</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	
	<script type="text/javascript" src="${ctx}/commons/echarts/dist/echarts.min.js"></script>
	
<script type="text/javascript"> 
$(function(){
	if($.trim("${laborFee.wfProcInstNo}") == ""){
		$("#wfLabel").hide();
	}

}); 
function doPrint(){
	var reportName = $('input:radio:checked').val();
	var no = "${laborFee.no}";
	
	if(reportName == "wfConstructionClaim.jasper"){
		no = "${laborFee.wfProcInstNo}";
		
		Global.Print.showPrint(reportName, {
	   		No:no,
			LogoFile : "<%=basePath%>jasperlogo/logo.jpg",
			SUBREPORT_DIR: "<%=jasperPath%>" 
		},null,
		function(){
			art.dialog({
				content:"是否已打印报表?",
				ok:function(){
					updatePrint(no);
				},
				cancel:function(){},
				okValue:"是",
				cancelValue:"否"
			});
		});
		
	} else {
	   	Global.Print.showPrint(reportName, {
	   		No:no,
			LogoFile : "<%=basePath%>jasperlogo/logo.jpg",
			SUBREPORT_DIR: "<%=jasperPath%>" 
		});
	}
}

function updatePrint(no){
	$.ajax({
		url:"${ctx}/admin/wfProcInst/doUpdatePrint",
		type: "post",
    	data: {
    		no:no
    	},
		dataType: "json",
		cache: false,
		error: function(obj){			    		
			art.dialog({
				content: "访问出错,请重试",
				time: 3000,
				beforeunload: function () {}
			});
		},
		success: function(obj){
			if(!obj.rs){
				art.dialog({
					content:obj.msg
				});
			}
		}
	});
}

</script>
</head>
<body >
	<div class="body-box-form">
		<div class="content-form">
  			<div class="panel panel-system">
   				<div class="panel-body">
      				<div class="btn-group-xs">
						<button type="button" class="btn btn-system" id="closeBut" onclick="doPrint()">
							<span>打印</span>
						</button>
						<button type="button" class="btn btn-system" id="closeBut" onclick="closeWin(false)">
							<span>关闭</span>
						</button>
					</div>
				</div>
			</div>
			<div class="edit-form">
				<div class="panel panel-info">  
	         		<div class="panel-body">
	         			<form role="form" class="form-search" id="dataForm" action="" method="post" target="targetFrame">
							<input type="hidden" name="jsonString" value=""/>
							<ul class="ul-form">
								<label style="padding-left:50px"><input type="radio" name="rowNums" style="" value="laborFee.jasper" checked/>明细</label>
								<label style="padding-left:20px"><input type="radio" name="rowNums" style="" value="laborFeeAccount.jasper"/>收款账号</label>
								<label style="padding-left:20px" id="wfLabel"><input type="radio" name="rowNums" style="" value="wfConstructionClaim.jasper"/>报销单</label>
							</ul>	
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
