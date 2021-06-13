<%@ page language="java" import="com.house.framework.commons.utils.PathUtil" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://" + request.getServerName() + ":"+request.getServerPort()+path+"/";
String jasperPath = PathUtil.WEB_INF+"jsp/jasper/";
%>
<!DOCTYPE html>
<html>
<head>
<title>预算打印套餐类</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp"%>
<script type="text/javascript">

$(function(){
    
    <c:forEach var="report" items="${disabledPlanReports}">
        $("input[data-code=" + "'${report.code}'" + "]").attr("disabled", true);
    </c:forEach>
    
    
    $("input[data-code=" + "'${defaultReport.code}'" + "]").attr("checked", true);
    
})

function doPrint(isPreview){ 
	var sType=getPlanDiffByArea()
	if(sType){
		var sContent="基础和主材"
		if(sType=="1"){
			sContent="基础"
		}else{
			sContent="主材"
		}
		art.dialog({
			content:"空间信息发生变化，请重新生成"+sContent+"预算数量" 
	    }); 
		 return false;
	}
    var reportName = "itemPlan_TC_main_cover.jasper,";
    if ($("#individulPlan").is(':checked')){
		reportName = reportName + "itemPlan_basicPrj.jasper,"+"itemPlan_individul.jasper";
	}else{
		reportName = reportName + "itemPlan_TC.jasper";	
	}
    
	if ($("#bcxy").is(':checked')){
		if("${itemPlan.giftPK}"!= 1 && $("#oldRepClause").val() == "" && $("#repClause").val() == ""){
		    art.dialog({
				content:"没有优惠项目，无法打印补充协议!"
		    }); 
		    return false;
		}
		reportName = "itemPlan_bcxy.jasper";
	}else if('${itemPlan.isHasCustProfit}'=='0'){
	    art.dialog({
			content:"请先在造价分析中,保存合同信息预录!"
	    }); 
	    return false;
	}
	
	var logoFile="<%=basePath%>jasperlogo/";
	logoFile=logoFile+$.trim("${logoFile}"); 
   	Global.Print.showPrint(reportName, {
		code: '${itemPlan.custCode}',
		printTC:$("input[name='printId']:checked").val(),
		LogoFile :logoFile,
		SUBREPORT_DIR: "<%=jasperPath%>",
		isPreview:isPreview
	},null,null, {
		modal:false ,
		hideDialog:!isPreview
	});	
}

function getPlanDiffByArea(){
	var sType;
	$.ajax({
		url:"${ctx}/admin/itemPlan/getPlanDiffByArea",
        type: "post",
        data: {
        	custCode: '${itemPlan.custCode}', hasAuthorityJudge: true
        },
        dataType: "json",
        cache: false,
        async: false,
        success: function(data) {
        	if (data){
        		sType = data  
			}
        }
    })
    return sType;	
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
				<table cellpadding="0" cellspacing="0" width="100%">
					<tbody>
						<tr class="td-btn">
							<td class="td-label" style="width: 200px">预算报价表（主套餐和套餐内材料）</td>
							<td class="td-value" colspan="1"><input type="radio" data-code="01" id="mainAndSetPlan" name="printId" value="1" /></td>
							<td class="td-label">预算报价表（主套餐报价单列）</td>
							<td class="td-value" colspan="1"><input type="radio" data-code="02" id="mainPlan" name="printId" value="0" /></td>
						</tr>
						<tr class="td-btn">
							<td class="td-label">预算报价表（基础工程+个性化）</td>
							<td class="td-value" colspan="1"><input type="radio" data-code="03" id="individulPlan" name="printId" value="1" /></td>
							<td class="td-label">补充协议</td>
							<td class="td-value" colspan="1"><input type="radio" data-code="04" id="bcxy" name="printId" value="1" /></td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
	</div>
</body>
</html>


