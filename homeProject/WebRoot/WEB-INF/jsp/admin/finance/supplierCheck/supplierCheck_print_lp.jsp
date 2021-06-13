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
$(function(){
	$('#supplierCheck_all').each(function(){
        $('#supplierCheck_all').click(function(){
			$('#supplierCheck_department').removeAttr('checked');
			$("#supplierCheck_detail").removeAttr("checked");
        });
        $('#supplierCheck_department').click(function(){
			$('#supplierCheck_all').removeAttr('checked');
			$("#supplierCheck_detail").removeAttr("checked");
        });
        $("#supplierCheck_detail").click(function(){
			$('#supplierCheck_all').removeAttr('checked');
			$('#supplierCheck_department').removeAttr('checked');
        });
    });
});
	 function printXjd(){
		var setName = "";
		if ($("#supplierCheck_all").prop("checked")){
			setName = setName +"supplierCheck_LP" ;
		}
		if ($("#supplierCheck_department").prop("checked")){
			setName = setName + "supplierCheck_LP_department" ;
		}
		if($("#supplierCheck_detail").prop("checked")) {
			setName = setName + "supplierCheck_detail" ;
		}
		if (setName==''){
			art.dialog({
				content: "请选择要打印的需求"
			});
			return;
		}
		if(setName == "supplierCheck_detail"){
			supplierCheck_detail_print();
		} else {
		   	var reportName = setName+".jasper";
		   	Global.Print.showPrint(reportName, {
				CheckOutNo:'${no }',
				LogoFile : "<%=basePath%>jasperlogo/logo.jpg",
				SUBREPORT_DIR: "<%=jasperPath%>" 
			});
		}
	}
	
	function supplierCheck_detail_print(){
		$.ajax({
			url:"${ctx}/admin/supplierCheck/doPrintBefore",
			type:"post",
			data:{
				no:"${no}"
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
					if(data.page == "1"){
						jasper = "supplierCheck_report1.jasper";
					}else{
						jasper = "supplierCheck_report2.jasper";
					}
					
					Global.Print.showPrint(jasper, {
						no:data.no,
						companyName:data.cmpnyName,
						titles:data.titles,
						LogoFile : "<%=basePath%>jasperlogo/logo.jpg",
						SUBREPORT_DIR: "<%=jasperPath%>" 
					});
				}else{
					art.dialog({
						content:"打印出错"
					});
				}
			}
		});	
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
									<span>打印</span>
								</button>
								<button type="button" class="btn btn-system " id="closeBut" onclick="closeWin(false)">
									<span>关闭</span>
								</button>
					</div>
				</div>
			</div>
		<div class="edit-form">
			<form action="" method="post" id="dataForm">
				<input type="hidden" id="no" name="no" value="${no }" >
				<table cellspacing="0" cellpadding="0" width="100%">
					<col  width="72"/>
					<col  width="150"/>
					<col  width="72"/>
					<col  width="150"/>
					<tbody>
						<tr>
							<td id="supplierCheck_print" class="td-value" colspan="4" style="height: 30px;border-bottom: 0px;">
								&nbsp;&nbsp;<input style="vertical-align:middle;margin-top:-1px;" id="supplierCheck_all" name="supplierCheck_all" type="radio" checked="checked" >出库明细
							</td>
						</tr>
						<tr>
							<td id="supplierCheck_print" class="td-value" colspan="4" style="height: 30px;border-top: 0px;border-bottom: 0px;">
								&nbsp;&nbsp;<input style="vertical-align:middle;margin-top:-1px;" id="supplierCheck_department" name="supplierCheck_department" type="radio"  >出库汇总
							</td>
						</tr>
						<tr>
							<td id="supplierCheck_print" class="td-value" colspan="4" style="height: 30px;border-top: 0px;border-bottom: 0px;">
								&nbsp;&nbsp;<input style="vertical-align:middle;margin-top:-1px;" id="supplierCheck_detail" name="supplierCheck_detail" type="radio"  >结算明细
							</td>
						</tr>
						<tr >
							<td class="td-value"  style="height: 30px;border-top: 0px;" colspan="4">												
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

