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
    <title>签单奖励管理打印</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
<script type="text/javascript">
$(function(){
	$('#againAward_detail').each(function(){
        $('#againAward_detail').click(function(){
                $('#againAward_emp').removeAttr('checked');
                
        });
        $('#againAward_emp').click(function(){
                $('#againAward_detail').removeAttr('checked');
        });
    });
});
	 function printXjd(){
		var setName = "";
		if ($("#againAward_detail").prop("checked")){
			setName = setName +"againAward" ;
		}
		if ($("#againAward_emp").prop("checked")){
			setName = setName + "againAward_emp" ;
		}
		if (setName==''){
			art.dialog({
				content: "请选择要打印的需求"
			});
			return;
		}
	   	var reportName = setName+".jasper";
	   	Global.Print.showPrint(reportName, {
			No:'${no }',
			SUBREPORT_DIR: "<%=jasperPath%>" 
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
							<td id="againAward_print" class="td-value" colspan="4" style="height: 30px;border-bottom: 0px;">
								&nbsp;&nbsp;<input style="vertical-align:middle;margin-top:-1px;" id="againAward_detail" name="againAward_detail" type="radio" checked="checked" >签单奖励明细
							</td>
						</tr>
						<tr>
							<td id="againAward_print" class="td-value" colspan="4" style="height: 30px;border-top: 0px;border-bottom: 0px;">
								&nbsp;&nbsp;<input style="vertical-align:middle;margin-top:-1px;" id="againAward_emp" name="againAward_emp" type="radio"  >签单奖励汇总
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

