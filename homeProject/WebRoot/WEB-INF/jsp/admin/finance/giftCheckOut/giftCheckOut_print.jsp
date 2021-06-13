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
	$('#giftCheckOut_all').each(function(){
        $('#giftCheckOut_all').click(function(){
                $('#giftCheckOut_department').removeAttr('checked');
                
        });
        $('#giftCheckOut_department').click(function(){
                $('#giftCheckOut_all').removeAttr('checked');
        });
    });
});
	 function printXjd(){
		var setName = "";
		if ($("#giftCheckOut_all").prop("checked")){
			setName = setName +"giftCheckOut" ;
		}
		if ($("#giftCheckOut_department").prop("checked")){
			setName = setName + "giftCheckOut_department" ;
		}
		if (setName==''){
			art.dialog({
				content: "请选择要打印的需求"
			});
			return;
		}
	   	var reportName = setName+".jasper";
	   	Global.Print.showPrint(reportName, {
			CheckOutNo:'${no }',
			LogoFile : "<%=basePath%>jasperlogo/logo.jpg",
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
							<td id="giftCheckout_print" class="td-value" colspan="4" style="height: 30px;border-bottom: 0px;">
								&nbsp;&nbsp;<input style="vertical-align:middle;margin-top:-1px;" id="giftCheckOut_all" name="giftCheckOut_all" type="radio" checked="checked" >出库明细
							</td>
						</tr>
						<tr>
							<td id="giftCheckout_print" class="td-value" colspan="4" style="height: 30px;border-top: 0px;border-bottom: 0px;">
								&nbsp;&nbsp;<input style="vertical-align:middle;margin-top:-1px;" id="giftCheckOut_department" name="giftCheckOut_department" type="radio"  >出库汇总
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

