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
		<%@ include file="/commons/jsp/common.jsp" %>
		<style type="text/css">
			.btn {
			    padding: 6px 50px;
			}
		</style>

		<script type="text/javascript" defer>
		$(function(){
			parent.$("#iframe_print").attr("height","97%");

		});

		function doPrint() {
			var printType = $(".btn-group .active input[type=radio]").val();
			if (0 == printType) {
				Global.Print.showPrint("salesInvoiceShipment.jasper", 
					{
						SINo:"${salesInvoice.no}",
						LogoFile : "<%=basePath%>jasperlogo/logo.jpg",
						SUBREPORT_DIR: "<%=jasperPath%>" 
					}
				);
			} else {
				Global.Print.showPrint("salesInvoiceSales.jasper", 
					{
						SINo:"${salesInvoice.no}",
						LogoFile : "<%=basePath%>jasperlogo/logo.jpg",
						SUBREPORT_DIR: "<%=jasperPath%>" 
					}
				);
			}
		}
		</script>
	</head>
	<body>
		<div class="body-box-form">
			<div class="panel panel-system">
			    <div class="panel-body">
			      <div class="btn-group-xs">
						<button type="button" class="btn btn-system " id="printBtn" onclick="doPrint()">
							<span>打印</span>
						</button>
						<button type="button" class="btn btn-system " id="closeBut" onclick="closeWin(false)">
							<span>关闭</span>
						</button>
					</div>
				</div>
			</div>
			<div class="panel panel-info" style="margin: 0px;">  
				<div class="panel-body">
					<form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
						<input type="hidden" id="lastupdatedby" name="lastupdatedby" value="${sessionScope.USER_CONTEXT_KEY.czybh}"/>
						<ul class="ul-form">
							<div class="row" style="text-align: center;">
								<div class="btn-group" data-toggle="buttons"  style="margin:auto;">
									<label class="btn btn-default active">
										<input type="radio" id="salesPrint" name="printType" value="1" />销售单
									</label>
									<label class="btn btn-default">
										<input type="radio" id="shipmentPrint" name="printType" value="0" />出货单
									</label>
								</div>
							</div>
						</ul>	
					</form>
				</div>
			</div>
		</div>
	</body>
</html>
