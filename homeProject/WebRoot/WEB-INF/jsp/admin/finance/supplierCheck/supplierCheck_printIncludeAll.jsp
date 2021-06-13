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
	<title>预付金管理打印</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	
	<script type="text/javascript" src="${ctx}/commons/echarts/dist/echarts.min.js"></script>
	
<script type="text/javascript"> 
$(function(){
	if($.trim("${splCheckOut.wfProcInstNo}") == ""){
		$("#purchaseExpense").hide();
	}
	
	if($.trim("${splCheckOut.itemType1}") != "LP"){
		$("#supplierCheck_LP").hide();
		$("#supplierCheck_LP_department").hide();
	}
	
}); 
function doPrint(){
	var reportName = $('input:radio:checked').val();
	console.log(reportName);
	var no = "${splCheckOut.no}";
	var itemType1 = $.trim("${splCheckOut.itemType1}");
	if(reportName == "purchaseExpense_main.jasper"){
		no = "${splCheckOut.wfProcInstNo}";
		
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
		if(itemType1 !='LP'){
			supplierCheck_detail_print(no, reportName);
		} else {
			if(reportName == "supplierCheck_LP.jasper" || reportName == "supplierCheck_LP_department.jasper"){
				Global.Print.showPrint(reportName, {
					CheckOutNo: no,
					LogoFile : "<%=basePath%>jasperlogo/logo.jpg",
					SUBREPORT_DIR: "<%=jasperPath%>" 
				});
			} else {
				supplierCheck_detail_print(no, reportName);
			}
		}
	}
}
function supplierCheck_detail_print(no, reportName){
	$.ajax({
		url:"${ctx}/admin/supplierCheck/doPrintBefore",
		type:"post",
	   	data:{
	   		no: no
	   	},
		dataType:"json",
		cache:false,
		error:function(obj){			    		
			art.dialog({
				content:"访问出错,请重试",
				time:3000,
				beforeunload: function () {}
			});
		},
		success:function(data){
			var jasper = "";
			if(data){
				if(reportName == "supplierCheck_checkOrder.jasper"){
					if(data.page == "1"){ // 有领料单
						jasper = "supplierCheck_checkOrder1.jasper";
					}else{
						jasper = "supplierCheck_checkOrder2.jasper";
					}
				}else {
					if(data.page == "1"){
						jasper = "supplierCheck_report1.jasper";
					}else{
						jasper = "supplierCheck_report2.jasper";
					}
				}	
				Global.Print.showPrint(jasper, 
					{
						no:data.no,
						companyName:data.cmpnyName,
						titles:data.titles,
						LogoFile : "<%=basePath%>jasperlogo/logo.jpg",
						SUBREPORT_DIR: "<%=jasperPath%>" 
					}
				);
			}else{
				art.dialog({
					content:"打印出错"
				});
			}
		}
	});
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
								<label style="padding-left:50px" id = "supplierCheck_detail"><input type="radio" name="rowNums" style="" value="supplierCheck_detail" checked/>结算明细</label>
								<label style="padding-left:50px" id="supplierCheck_LP"><input type="radio" name="rowNums" style="" value="supplierCheck_LP.jasper" />出库明细</label>
								<label style="padding-left:50px" id="supplierCheck_LP_department"><input type="radio" name="rowNums" style="" value="supplierCheck_LP_department.jasper" />出库汇总</label>
								<label style="padding-left:20px" id="purchaseExpense"><input type="radio" name="rowNums" style="" value="purchaseExpense_main.jasper"/>报销单</label>
								<label style="padding-left:20px" id="supplierCheck_checkOrder"><input type="radio" name="rowNums" style="" value="supplierCheck_checkOrder.jasper"/>结账单</label>
								
							</ul>	
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
