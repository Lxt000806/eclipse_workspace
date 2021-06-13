<%@ page language="java" import="com.house.framework.commons.utils.PathUtil" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://" + request.getServerName() + ":"+request.getServerPort()+path+"/";
String jasperPath = PathUtil.WEB_INF+"jsp/jasper/";
%>
<!DOCTYPE html>
<html>
	<head>
		<title>预算打印</title>
		<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
		<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
		<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
		<META HTTP-EQUIV="expires" CONTENT="0" />
		<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
		<%@ include file="/commons/jsp/common.jsp"%>
		<script type="text/javascript">
		$(function(){
		    $("input:radio:first").attr("checked", "checked");
		});
		function checkPrint(value){
			if(value == "2" && parseInt("${itemPlan.containInt}") == 0 && parseInt("${itemPlan.containCup}") == 0){
				return "预算不包含集成和橱柜，无法打印！";
			}else if((value == "5" && value == "6") && parseInt("${itemPlan.containMain}") == 0){
				return "预算不包含主材，无法打印，无法打印！";
			}else if(value == "3" && parseInt("${itemPlan.containSoft}") == 0){
				return "预算不包含软装，无法打印！";
			}else if(value == "7" && parseInt("${itemPlan.containMainServ}") == 0){
				return "预算不包含服务性产品，无法打印！" ;
			}else if(value == "4" && parseInt("${itemPlan.containSoft}") == 0){
				return "预算不包含软装，无法打印！" ;
			}else if(value == "14" && "${itemPlan.giftPK}"!= 1 && $("#oldRepClause").val() == "" && $("#repClause").val() == ""){
				return "没有优惠项目，无法打印补充协议！" ;
			}else{
				return " "
			}
		}
		function doPrint(isPreview){
			var str = "";
			var reportName = "";
			var  strMsg=" ";
			if($("#jcys").prop("checked")){
				checkPrint("1");
				strMsg=checkPrint("1");
				if(strMsg!=" "){
				    art.dialog({
						content: strMsg
				    }); 
				    return false;
				}
				str = str + "'" + "jcys" + "',";
				if("${itemPlan.custType}" != "2"){
					reportName = reportName + "itemPlan_JZ_baseItemTypeGroup_main_cover.jasper,";
				}
				reportName = reportName + "itemPlan_JZ.jasper,";
			}
			
			if($("#jcygcg").prop("checked")){
				strMsg=checkPrint("2");
				if(strMsg!=" "){
				    art.dialog({
						content: strMsg
				    }); 
				    return false;
				}
				str = str + "'" + "jcygcg" + "',";
				if("${itemPlan.custType}" != "2"){
					reportName = reportName + "itemPlan_JC_cover.jasper,";
				}
				reportName = reportName + "itemPlan_JC.jasper,";
			}
			
			if($("#rzys").prop("checked")){
				strMsg=checkPrint("3");
				if(strMsg!=" "){
				    art.dialog({
						content: strMsg
				    });
				    return false; 
				}
				str = str + "'" + "rzys" + "',";
				
				if("${itemPlan.custType}" != "2"){
					reportName = reportName + "itemPlan_RZ_cover.jasper,";
				}
				
				reportName = reportName + "itemPlan_RZ.jasper,";
			}
			
			if($("#rzystcbhz").prop("checked")){
				strMsg=checkPrint("4");
				if(strMsg!=" "){
				    art.dialog({
						content: strMsg
				    }); 
				    return false;
				}
				str = str + "'" + "rzystcbhz" + "',";
				if("${itemPlan.custType}" != "2"){
					reportName = reportName + "itemPlan_RZ_itemSet_cover.jasper,";
				}
				
				reportName = reportName + "itemPlan_RZ_itemSet.jasper,";
			}
			if($("#zcys").prop("checked")){
				strMsg=checkPrint("5");
				if(strMsg!=" "){
				    art.dialog({
						content: strMsg
				    });
				    return false; 
				}
				str = str + "'" + "zcys" + "',";
				if("${itemPlan.custType}" != "2"){
					reportName = reportName + "itemPlan_ZC_cover.jasper,";
				}
				reportName = reportName + "itemPlan_ZC.jasper,";
			}
		
			if($("#zcqdwjg").prop("checked")){
				strMsg=checkPrint("6");
				if(strMsg!=" "){
				    art.dialog({
						content: strMsg
				    }); 
				}
				str = str + "'" + "zcqdwjg" + "',";
				if("${itemPlan.custType}" != "2"){
					reportName = reportName + "itemPlan_ZC_noPrice_cover.jasper,";
				}
				reportName = reportName + "itemPlan_ZC_noPrice.jasper,";
			}
		
			if($("#fwcpys").prop("checked")){
				strMsg=checkPrint("7");
				if(strMsg!=" "){
				    art.dialog({
						content: strMsg
				    }); 
				    return false;
				}
				str = str + "'" + "fwcpys" + "',";
				if("${itemPlan.custType}" != "2"){
					reportName = reportName + "itemPlan_ZC_service_cover.jasper,";
				}
				reportName = reportName + "itemPlan_ZC_service.jasper,";
			}
		
			if($("#rgys").prop("checked")){
				strMsg=checkPrint("8");
				if(strMsg!=" "){
				    art.dialog({
						content: strMsg
				    });
				    return false; 
				}
				str = str + "'" + "rgys" + "',";
				if("${itemPlan.custType}" != "2"){
					reportName = reportName + "itemPlan_JZ_baseItemTypeGroup_main_cover.jasper,";
				}
				reportName = reportName + "itemPlan_JZ_man.jasper,";
			}
		
			if($("#clys").prop("checked")){
				strMsg=checkPrint("9");
				if(strMsg!=" "){
				    art.dialog({
						content: strMsg
				    }); 
				    return false;
				}
				str = str + "'" + "clys" + "',";
				if("${itemPlan.custType}" != "2"){
					reportName = reportName + "itemPlan_JZ_baseItemTypeGroup_main_cover.jasper,";
				}
				reportName = reportName + "itemPlan_JZ_material.jasper,";
			}
		
			if($("#jcysrgclhb").prop("checked")){
				strMsg=checkPrint("10");
				if(strMsg!=" "){
				    art.dialog({
						content: strMsg
				    }); 
				    return false;
				}
				str = str + "'" + "jcysrgclhb" + "',";
				if("${itemPlan.custType}" != "2"){
					reportName = reportName + "itemPlan_JZ_baseItemTypeGroup_main_cover.jasper,";
				}
				reportName = reportName + "itemPlan_JZ_union.jasper,";
			}
		
			if($("#jcyslxhz").prop("checked")){
				strMsg=checkPrint("13");
				if(strMsg!=" "){
				    art.dialog({
						content: strMsg
				    }); 
				    return false;
				}
				str = str + "'" + "jcyslxhz" + "',";
				if("${itemPlan.custType}" != "2"){
					reportName = reportName + "itemPlan_JZ_baseItemTypeGroup_main_cover.jasper,";
				}
				reportName = reportName + "itemPlan_JZ_baseItemTypeGroup_main.jasper,";
			}
			if($("#bcxy").prop("checked")){
				str = str + "'" + "bcxy" + "',";
				strMsg=checkPrint("14");
				if(strMsg!=" "){
				    art.dialog({
						content: strMsg
				    }); 
				    return false;
				}
				reportName = reportName + "itemPlan_bcxy.jasper,";
			}
			if (str == ""){
				art.dialog({
					content: "请选择要打印的需求"
				});
				return;
			}	
			reportName = reportName.substring(0, reportName.length-1);
			var logoFile="<%=basePath%>jasperlogo/";
				logoFile=logoFile+$.trim("${logoFile}"); 
			Global.Print.showPrint(reportName, {
				code: "${itemPlan.custCode}",
				LogoFile : logoFile,
				SUBREPORT_DIR: "<%=jasperPath%>",
				isPreview:isPreview,
			},null,null, {
				hideDialog:!isPreview,
				modal:false
			});
		}
		function selectAll(e){
			if($(e).is(":checked"))
				$(":checkbox").prop("checked", true);
			else $(":checkbox").prop("checked", false);
		}
		</script>
	</head>
	
	<body>
		<div class="body-box-form">
			<div class="panel panel-system">
				<div class="panel-body">
					<div class="btn-group-xs">
						<%--<button type="button" class="btn btn-system " onclick="doPrint(false)">打印</button>--%>
						<button type="button" class="btn btn-system " onclick="doPrint(true)">打印</button>
						<button type="button" class="btn btn-system " onclick="closeWin()">关闭</button>
					</div>
				</div>
			</div>
	
			<div class="query-form">
				<form action="" method="post" id="page_form">
					<input type="hidden" id="expired" name="expired" value="${itemPlan.expired}" /> <input type="hidden"
						name="jsonString" value="" />
					<input type="hidden" id="repClause" name="repClause" value="${customer.repClause}" />
					<input type="hidden" id="oldRepClause" name="oldRepClause" value="${customer.oldRepClause}" />
					<table>
						<tbody>
							<tr style="border: none">
								<td style="width: 20px;border:none;padding-bottom: 5px"><input type="checkbox"
									onclick="selectAll(this)" name="qx" value="0" /></td>
								<td class="td-value" style="border:none">全选</td>
							</tr>
						</tbody>
					</table>
					<table id="selectTable">
						<tbody>
							<tr style="border: none">
								<td style="width: 20px;border:none;padding-bottom: 5px">
									<input type="checkbox" id="jcys" name="printIdVer" value="1" />
								</td>
								<td class="td-value" style="border:none">基础预算报表</td>
								
								<td style="width: 20px;border:none;padding-bottom: 5px">
									<input type="checkbox" id="jcygcg" name="printIdVer" value="2" />
								</td>
								<td class="td-value" style="border:none">集成衣柜橱柜报表</td>
								
								<td style="width: 20px;border:none;padding-bottom: 5px">
									<input type="checkbox" id="rzys" name="printIdHo" value="3" />
								</td>
								<td class="td-value" style="border:none">软装预算报表</td>
								
								<td style="width: 20px;border:none;padding-bottom: 5px">
									<input type="checkbox" id="rzystcbhz" name="printIdHo" value="4" />
								</td>
								<td class="td-value" style="border:none">软装预算报表（按套餐包汇总）</td>
							</tr>
							<tr style="border: none">
								<td style="width: 20px;border:none;padding-bottom: 5px">
									<input type="checkbox" id="zcys" name="printIdHo" value="5" />
								</td>
								<td class="td-value" style="border:none">主材预算报表</td>
								
								<td style="width: 20px;border:none;padding-bottom: 5px">
									<input type="checkbox" id="zcqdwjg" name="printIdHo" value="6" />
								</td>
								<td class="td-value" style="border:none">主材清单（无价格）</td>
								
								<td style="width: 20px;border:none;padding-bottom: 5px">
									<input type="checkbox" id="fwcpys" name="printIdHo" value="7" />
								</td>
								<td class="td-value" style="border:none">服务产品预算报表</td>
	
							</tr>
							<tr style="border: none">
								<td style="width: 20px;border:none;padding-bottom: 5px">
									<input type="checkbox" id="rgys" name="printIdVer" value="8" />
								</td>
								<td class="td-value" style="border:none">人工预算报表</td>
								
								<td style="width: 20px;border:none;padding-bottom: 5px">
									<input type="checkbox" id="clys" name="printIdVer" value="9" />
								</td>
								<td class="td-value" style="border:none">材料预算报表</td>
								
								<td style="width: 20px;border:none;padding-bottom: 5px">
									<input type="checkbox" id="jcysrgclhb" name="printIdVer" value="10" />
								</td>
								<td class="td-value" style="border:none">基础预算报表（人工材料合并）</td>
								
								<td style="width: 20px;border:none;padding-bottom: 5px">
									<input type="checkbox" id="jcyslxhz" name="printIdVer" value="13" />
								</td>
								<td class="td-value" style="border:none">基础预算报表（按类型汇总）</td>
							</tr>
							<tr style="border: none">
								<td style="width: 20px;border:none;padding-bottom: 5px">
									<input type="checkbox" id="bcxy" name="printIdVer" value="14" />
								</td>
								<td class="td-value" style="border:none">补充协议</td>
							</tr>
						</tbody>
					</table>
				</form>
			</div>
		</div>
	</body>
</html>


