<%@ page language="java" import="com.house.framework.commons.utils.PathUtil" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://" + request.getServerName() + ":"+request.getServerPort()+path+"/";
String jasperPath = PathUtil.WEB_INF+"jsp/jasper/";
%>
<!DOCTYPE html>
<html>
<head>
	<title>毛利分析</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
</head>
<style type="text/css">
.panel-body {
    padding: 0px 1px 1px;
    padding-top: 1px;
    padding-right: 1px;
    padding-bottom: 1px;
    padding-left: 1px;
}
.btn-panel{
    padding: 0px 0px 1px;
}
.panel {
    margin-bottom: 5px;
}
</style> 
<script type="text/javascript">
$(function(){
	$("#status_NAME").attr("disabled","disabled");
	if(!${isProfitView}){
		 $("#tabmlfx").css("display","none");
	}
	if(!${isProfitZCView}){
		 $("#tabzcmlfx").css("display","none");
	}		
}) 
</script>
<body>
	<div class="body-box-form">
		<div class="panel panel-system">
			<div class="panel-body">
				<div class="btn-group-xs">
					<button id="saveBtn" type="button" class="btn btn-system" onclick="doPrint(false)">打印</button>
					<button type="button" class="btn btn-system " onclick="doPrint(true)">预览</button>
					<button id="excelBtn" type="button" class="btn btn-system" style="display: none" onclick="doExcel()">导出excel</button>
					<button type="button" class="btn btn-system" onclick="closeWin(false)">关闭</button>
				</div>
			</div>
		</div>
		<div class="panel panel-info">
			<div class="panel-body">
				<form role="form" class="form-search" id="dataForm" action="" method="post" target="targetFrame">
					<input type="hidden" id="hasCustProfit" name="hasCustProfit" value="${custProfit.custCode }" /> <input
						type="hidden" id="payType" name="payType" value="${custProfit.payType }" /> <input type="hidden"
						id="isChange" name="isChange" />
					<house:token></house:token>
					<ul class="ul-form">
						<li><label>客户编号</label> <input type="text" id="code" name="code" value="${customer.code}"
							disabled="disabled" /></li>
						<li><label>客户状态</label> <house:xtdmMulit id="status" dictCode="CUSTOMERSTATUS"
								selectedValue="${customer.status}"></house:xtdmMulit></li>
						<li><label>楼盘</label> <input type="text" id="address" name="address" value="${customer.address }"
							disabled="disabled" /></li>
						<li><label>工程总造价</label> <input type="text" id="contractFee" name="contractFee"
							value="${customer.contractFee}" disabled="disabled" /></li>
					</ul>
				</form>
			</div>
		</div>
		<!--标签页内容 -->
		<div class="container-fluid">
			<ul class="nav nav-tabs" id="myTab">
				<li id="tabhtxxyl" class="active"><a href="#tab_htxxyl" data-toggle="tab">合同信息预录</a>
				</li>
				<li id="tabfkjhyl" class=""><a name="tab_fkjhyl" href="#tab_fkjhyl" data-toggle="tab">付款计划预录</a>
				</li>
				<li id="tabmlfx" class=""><a name="tab_mlfx" href="#tab_mlfx" data-toggle="tab">毛利分析</a>
				</li>
				<li id="tabzcmlfx" class=""><a name="tab_zcmlfx" href="#tab_zcmlfx" data-toggle="tab">主材毛利分析</a>
				</li>
			</ul>
			<div class="tab-content">
				<div id="tab_htxxyl" class="tab-pane fade in active" style="width: 970px;height: 460px">
					<iframe id="custPayInfoIframe" style="width: 100%;height: 100%;border: none"
						src="${ctx}/admin/itemPlan/goProfitAnalyse_htxxyl?custCode=${customer.code}"></iframe>
				</div>
				<div id="tab_fkjhyl" class="tab-pane fade " style="width: 970px;height: 460px">
					<iframe id="custPayPreIframe" style="width: 100%;height: 100%;border: none"
						src="${ctx}/admin/itemPlan/goProfitAnalyse_fkjhyl?custCode=${customer.code}"></iframe>
				</div>
				<div id="tab_mlfx" class="tab-pane fade " style="width: 970px;height: 460px">
					<iframe id="mlfxIframe" style="width: 100%;height: 100%;border: none"
						src="${ctx}/admin/itemPlan/goProfitAnalyse_mlfx?custCode=${customer.code}"></iframe>
				</div>
				<div id="tab_zcmlfx" class="tab-pane fade " style="width: 970px;height: 460px">
					<iframe id="zcmlfxIframe" style="width: 100%;height: 100%;border: none"
						src="${ctx}/admin/itemPlan/goProfitAnalyse_zcmlfx?code=${customer.code}"></iframe>
				</div>

			</div>
		</div>
	</div>
<script type="text/javascript">
$('a[data-toggle="tab"]').on('show.bs.tab',
	function(e) {
		if(!${isProfitEdit} || "${customer.status}"=="4" || "${customer.status}"=="5" || "${latelyCon}"=="1"){
			 document.getElementById("isChange").value="";
		}else{
			if (!$("#hasCustProfit").val()) {
				e.preventDefault();
				art.dialog({
					content : "请先录入合同信息！"
				});
				return;
			}
			if ($("#isChange").val()) {
				e.preventDefault();
				art.dialog({
					content : '合同信息预录数据已修改,是否保存？',
					lock : true,
					width : 260,
					height : 100,
					ok : function() {
						document
								.getElementById("custPayInfoIframe").contentWindow
								.save(e.currentTarget.name);
					},
					cancel : function() {
						  document.getElementById("isChange").value="";
						  goTab(e.currentTarget.name);
					}
				});
			}
		}
		if (e.currentTarget.name == "tab_zcmlfx")
			$("#excelBtn").show();
		else
			$("#excelBtn").hide();

	});
	function goTab(tab) {
		$('#myTab a[href="#' + tab + '"]').tab('show');
	}
		//打印
	function doPrint(isPreview) {
		if (!$("#hasCustProfit").val()) {
			art.dialog({
				content : "未录入合同信息不允许打印！"
			});
			return false;
		}
		var isEqual = document.getElementById("custPayPreIframe").contentWindow
				.isEqual();
		var sContractFeeRepType="";
		$.ajax({
				url:"${ctx}/admin/itemPlan/getContractFeeRepType",
				type:"post",
				data:{custType:"${customer.custType}", code:"${customer.code}"},
				dataType:"json",
				cache:false,
				error:function(obj){
					art.dialog({
							content : "获取合同造价表类型失败"
					});
					return false;
				},
				success:function(obj){
					if(obj){
					   sContractFeeRepType=obj[0].contractFeeRepType;
					   if (!isEqual) {
							if (sContractFeeRepType == '1') {
								art.dialog({
									content : "付款计划总和不等于工程总价+税金,不允许打印！"
								});
							
							}else if (sContractFeeRepType == '2'||sContractFeeRepType == '4'){
								art.dialog({
									content : "付款计划、设计费返还总和不等于工程总价+税金,不允许打印！"
								});
							}else{
								art.dialog({
									content : "付款计划总和不等于工程总价+设计费+税金,不允许打印！"
								});	
							}	
							return false;
						}
						
						var reportName ="";
						if (sContractFeeRepType == '1') {
							reportName = "itemPlan_mlfx.jasper";
						}else if (sContractFeeRepType == '2'){
							reportName = "itemPlan_mlfx_1.jasper";
						}else if (sContractFeeRepType == '3'){
							reportName = "itemPlan_mlfx_2.jasper";
						}else{
							reportName = "itemPlan_mlfx_3.jasper";
						}
						
						/* var reportName = "itemPlan_mlfx_${custProfit.payType}.jasper"; */
						Global.Print.showPrint(reportName, {
							code : '${customer.code}',
							LogoPath : "<%=basePath%>jasperlogo/",
							SUBREPORT_DIR: "<%=jasperPath%>", 
							isPreview:isPreview
						});
					}else{
						art.dialog({
							content : "获取合同造价表类型失败"
						});
						return false;
					}
				}
		}); 
		
		
	}
	function doExcel(){
		document.getElementById("zcmlfxIframe").contentWindow.doExcel();
	}
 </script>
</body>
</html>
