<%@ page language="java" import="com.house.framework.commons.utils.PathUtil" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://" + request.getServerName() + ":"+request.getServerPort()+path+"/";
String jasperPath = PathUtil.WEB_INF+"jsp/jasper/";
%>
<%
UserContext uc = (UserContext)request.getSession().getAttribute(CommonConstant.USER_CONTEXT_KEY);
String czylb=uc.getCzylb();
String czybh="";
if("2".equals(czylb)) czybh=uc.getCzybh();
%>
<!DOCTYPE html>
<html>
<head>
	<title>查看whCheckOut</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script type="text/javascript">
//tab分页
  $(function(){
     if("<%=czylb %>"!="1"){
    	    $("#tabsalesInvoice").css("display","none");
  			$("#tabtotalByItemType2").css("display","none");
  			$("#tabtotalByBrand").css("display","none");
  			$("#documentNo").css("type","hidden")
  		    $("#documentNo").hide();
	      	$(".labeldocumentNo").hide();
	      	$("#trdocumentNo").hide();
	      	
	}
}); 
</script>
</head>
    
<body>
<div class="body-box-form" >
	<div class="panel panel-system" >
	    <div class="panel-body" >
	    	<div class="btn-group-xs" >
				<button type="button" class="btn btn-system "  id="closeBut" onclick="closeWin(false)">关闭</button>
			</div>
		</div>
	</div>
	<div class="panel panel-info" >  
		<div class="panel-body">
			<form action="" method="post" id="dataForm" class="form-search">
				<input type="hidden" name="jsonString" value="" />
				<house:token></house:token>
				<input type="hidden" name="m_umState" id="m_umState" value="V"/>
				<ul class="ul-form">
					<li>
						<label><span class="required"></span>记账单号</label>
						<input type="text" id="no" name="no" value="${whCheckOut.no}" placeholder="保存自动生成" readonly="readonly"/>
					</li>
					<li>
						<label><span class="required">*</span>申请日期</label>
						<input type="text" id="date" name="date" class="i-date" 
					     value="<fmt:formatDate value='${whCheckOut.date}'  pattern='yyyy-MM-dd HH:mm:ss'/>" readonly="readonly"/>
					</li>
					<li>
						<label>账单状态</label>
						<house:xtdm id="status" dictCode="WHChkOutStatus" value="${whCheckOut.status}" disabled="true"></house:xtdm>
					</li>
					<li>
						<label><span class="required">*</span>申请人</label>
						<input type="text" id="appCZY" name="appCZY" value="${whCheckOut.appCZY}" />
					</li>
					<li>
					    <label><span class="required">*</span>仓库</label>
						<input type="text" id="whCode" name="whCode" value="${whCheckOut.whCode}" />
					</li>
					<li>
						<label>审核日期</label>
						<input type="text" id="confirmDate" name="confirmDate" class="i-date" 
						 value="<fmt:formatDate value='${whCheckOut.confirmDate}' pattern='yyyy-MM-dd HH:mm:ss'/>" readonly="readonly"/>
					</li>
					<li>
						<label>记账日期</label>
						<input type="text" id="checkDate" name="checkDate" class="i-date" 
						 value="<fmt:formatDate value='${whCheckOut.checkDate}' pattern='yyyy-MM-dd HH:mm:ss'/>"readonly="readonly"/>
					</li>
					<li>
						<label>审核人</label>
						<input type="text" id="confirmCZY" name="confirmCZY" value="${whCheckOut.confirmCZY}" readonly="readonly"/>
					</li>
					<li id="trdocumentNo">	
						<label><div class="labeldocumentNo">凭证号</div></label>
						<input type="text" id="documentNo" name="documentNo" value="${whCheckOut.documentNo}"  readonly="readonly"/>
					</li>
					<li>
						<label class="control-textarea">备注</label>
						<textarea id="remarks" name="remarks" >${whCheckOut.remarks}</textarea>
					</li>
				</ul>
			</form>
		</div>
	</div>
	<div  class="container-fluid" >  
	     <ul class="nav nav-tabs">
	        <li id="tabitemAppSend" class="active"><a href="#tab_itemAppSend" data-toggle="tab">领料发货单</a></li>  
	        <li id="tabsalesInvoice" class=""><a href="#tab_salesInvoice" data-toggle="tab">销售单</a></li>
	        <li id="tabtotalByItemType2" class=""><a href="#tab_totalByItemType2" data-toggle="tab">按材料类型2汇总</a></li>  
	        <li id="tabtotalByBrand" class=""><a href="#tab_totalByBrand" data-toggle="tab">按品牌汇总</a></li> 
	   		<li id="tabtotalByCompany" class=""><a href="#tab_totalByCompany" data-toggle="tab">按公司汇总</a></li> 
	    </ul>  
	    <div class="tab-content">  
			<div id="tab_itemAppSend"  class="tab-pane fade in active"> 
		    	<jsp:include page="tab_itemAppSend.jsp"></jsp:include>
			</div>  
			<div id="tab_salesInvoice"  class="tab-pane fade "> 
	         	<jsp:include page="tab_salesInvoice.jsp"></jsp:include>
			</div>
			<div id="tab_totalByItemType2"  class="tab-pane fade "> 
	         	<jsp:include page="tab_totalByItemType2.jsp"></jsp:include> 
			</div>
			<div id="tab_totalByBrand"  class="tab-pane fade ">  
	         	<jsp:include page="tab_totalByBrand.jsp"></jsp:include>
			</div>
			<div id="tab_totalByCompany"  class="tab-pane fade ">  
	         	<jsp:include page="tab_totalByCompany.jsp"></jsp:include>
			</div>
		</div>	
	</div>	  
    <script src="${resourceRoot}/pub/component_wareHouse.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>  
<script type="text/javascript">
$("#whCode").openComponent_wareHouse({showValue:"${whCheckOut.whCode}",showLabel:"${whCheckOut.whDescr}",readonly:true});
$("#appCZY").openComponent_employee({showValue:"${whCheckOut.appCZY}",showLabel:"${whCheckOut.appCZYDescr}",readonly:true});
$("#confirmCZY").openComponent_employee({showValue:"${whCheckOut.confirmCZY}",showLabel:"${whCheckOut.confirmCZYDescr}",readonly:true});
//查看领料单
$("#viewItemAppSend").on("click",function(){
	var ret = selectDataTableRow("dataTable_itemAppSend");
	if(ret){
       	Global.Dialog.showDialog(ret.type=="S"?"itemAppSend":"itemAppReturn",{
       		title:ret.type=="S"?"领料单--查看":"领料退回--查看",
       	  	url:"${ctx}/admin/itemApp/goLlgzView",
       	  	postData:{
       	  		type:ret.type,
       	  		no:ret.iano
       	  	},
       	  	height: 730,
       	  	width:ret.type=="S"?1400:1300,
       	});
	}else{
		art.dialog({
			content:"请选择一条记录"
		});
	}
 });

//发货单输出到Excel
$("#itemAppSendExcel").on("click",function(){
	doExcelNow("发货单导出","dataTable_itemAppSend","dataForm");
});
	
//销售单输出到Excel
$("#salesInvoiceExcel").on("click",function(){
	doExcelNow("销售单导出","dataTable_salesInvoice","dataForm");
});
//按材料类型2输出到Excel
$("#totalByItemTypeExcel").on("click",function(){
	doExcelNow("按材料类型2汇总导出","dataTable_totalByItemType2","dataForm");
});
//按品牌输出到Excel
$("#totalByBrandExcel").on("click",function(){
	doExcelNow("按品牌汇总导出","dataTable_totalByBrand","dataForm");
});
//按公司输出到Excel
$("#totalByCompanyExcel").on("click",function(){
	doExcelNow("按公司汇总导出","dataTable_totalByCompany","dataForm");
});
//打印按材料类型2统计
$("#totalByItemType2Print").on("click",function(){
	var totalrow =$("#dataTable_totalByItemType2").jqGrid('getGridParam', 'records'); 
	if(totalrow<1){
		art.dialog({content: "无明细数据，不能打印！",width: 200});
		return false;
	}
    var reportName = "whCheckOut_totalByItemType2.jasper";
    Global.Print.showPrint(reportName, {
	No: "${whCheckOut.no}",
	LogoPath : "<%=basePath%>jasperlogo/", 
	SUBREPORT_DIR: "<%=jasperPath%>" 
	});	
});
//打印按品牌统计
$("#totalByBrandPrint").on("click",function(){
	 var totalrow =$("#dataTable_totalByBrand").jqGrid('getGridParam', 'records'); 
	 if(totalrow<1){
		art.dialog({content: "无明细数据，不能打印！",width: 200});
		return false;
	 }
     var reportName = "whCheckOut_totalByBrand.jasper";
     Global.Print.showPrint(reportName, {
	 No: "${whCheckOut.no}",
	 LogoPath : "<%=basePath%>jasperlogo/", 
	 SUBREPORT_DIR: "<%=jasperPath%>" 
	});	
});
//打印公司统计
$("#totalByCompanyPrint").on("click",function(){
	 var totalrow =$("#dataTable_totalByCompany").jqGrid('getGridParam', 'records'); 
	 if(totalrow<1){
		art.dialog({content: "无明细数据，不能打印！",width: 200});
		return false;
	 }
     var reportName = "whCheckOut_totalByCompany.jasper";
     Global.Print.showPrint(reportName, {
	 No: "${whCheckOut.no}",
	 LogoPath : "<%=basePath%>jasperlogo/", 
	 SUBREPORT_DIR: "<%=jasperPath%>" 
	});	
});
</script>
</body>
</html>
