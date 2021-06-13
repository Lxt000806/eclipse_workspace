<%@ page language="java" import="com.house.framework.commons.utils.PathUtil" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>
<%
UserContext uc = (UserContext)request.getSession().getAttribute(CommonConstant.USER_CONTEXT_KEY);
String czylb=uc.getCzylb();
String czybh="";
if("2".equals(czylb)) czybh=uc.getCzybh();
%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://" + request.getServerName() + ":"+request.getServerPort()+path+"/";
String jasperPath = PathUtil.WEB_INF+"jsp/jasper/";
%>
<!DOCTYPE html>
<html>
<head>
<title>WHCheckOut列表</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp" %>
<script src="${resourceRoot}/pub/component_wareHouse.js?v=${v}" type="text/javascript"></script>
<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
<style type="text/css">
.panelBar{
	height:26px;
}
</style>
<script type="text/javascript">
function goto_query(){
	$("#dataTable").jqGrid("setGridParam",{postData:$("#page_form").jsonForm(),page:1}).trigger("reloadGrid");
}
function clearForm(){
	$("#page_form input[type='text']").val('');
	$("#page_form select").val('');
	$("#status").val('');
	$.fn.zTree.getZTreeObj("tree_status").checkAllNodes(false);
}
/**初始化表格*/
$(function(){
        //初始化表格
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/whCheckOut/goJqGrid",
			height:$(document).height()-$("#content-list").offset().top-80,
        	styleUI: 'Bootstrap',
			colModel : [
		       {name: "no", index: "no", width: 110, label: "记账单号", sortable: true, align: "left"},
               {name: "statusdescr", index: "statusdescr", width: 90, label: "账单状态", sortable: true, align: "left"},
			   {name: "documentno", index: "documentno", width: 80, label: "凭证号", sortable: true, align: "left"},
			   {name: "checkdate", index: "checkdate", width: 120, label: "记账日期", sortable: true, align: "left", formatter: formatTime},
               {name: "remarks", index: "remarks", width: 150, label: "备注", sortable: true, align: "left"},
               {name: "whdescr", index: "whdescr", width: 80, label: "仓库", sortable: true, align: "left"},
               {name :"whcode",index :"whcode",width : 40,label:"仓库编号",sortable : true,align : "left",hidden:true},
               {name: "appczydescr", index: "appczydescr", width: 80, label: "开单人", sortable: true, align: "left"},
               {name: "date", index: "date", width: 120, label: "申请日期", sortable: true, align: "left", formatter: formatTime},
               {name: "confirmczydescr", index: "confirmczydescr", width: 90, label: "审核人员", sortable: true, align: "left"},
               {name: "confirmdate", index: "confirmdate", width: 120, label: "审核日期", sortable: true, align: "left", formatter: formatTime},
               {name: "lastupdate", index: "lastupdate", width: 120, label: "最后修改时间", sortable: true, align: "left", formatter: formatTime},
               {name: "lastupdatedby", index: "lastupdatedby", width: 80, label: "修改人", sortable: true, align: "left"},
               {name: "expired", index: "expired", width: 90, label: "是否过期", sortable: true, align: "left", hidden: true},
               {name: "actionlog", index: "actionlog", width: 70, label: "操作", sortable: true, align: "left"}
            ]   
		});
	    if("<%=czylb %>"=="1"){
   	 		$("#whCode").openComponent_wareHouse({condition:{ctrlItemType1:"1"}});
	    }else{
      		$("#whCode").openComponent_wareHouse({condition:{czybh:"<%=czybh %>"}});
      		$("#dataTable").jqGrid('hideCol', "documentno");
      		$("#documentNo").hide();
      		$(".labeldocumentNo").hide();
	    }
		$("#custCode").openComponent_customer();
		$("#appCzy").openComponent_employee();
        //新增领料
        $("#addWHCheckOut").on("click",function(){
	        //新增窗口
	        Global.Dialog.showDialog("whCheckOutAdd",{
		       	title:"新增记账单",
		        url:"${ctx}/admin/whCheckOut/goSave",
		      	height: 700,
		        width:1100,
		        returnFun:goto_query
	        });
        });  
        //修改
        $("#updateWHCheckOut").on("click", function() {
			var row = selectDataTableRow();
    		if(!row){
    			art.dialog({content: "请选择一条记录进行修改操作！",width: 200});
    			return false;
    		}
    		$.ajax({
				url : "${ctx}/admin/whCheckOut/doCheckUpdate",
				data : {no: row.no},
				contentType: "application/x-www-form-urlencoded; charset=UTF-8",
				dataType: "json",
				type: "post",
				cache: false,
				error: function(){
		        	art.dialog({
						content: '出现异常，无法修改'
					});
			    },
			    success: function(obj){
		    		if(obj.rs){
			        	Global.Dialog.showDialog("updateWHCheckOut", {
			        	 	title: "出库记账--修改",
			        	    url: "${ctx}/admin/whCheckOut/goUpdate?id=" + row.no,
			        	    height: 700,
			        	    width: 1100,
			        	    postData: row.no,
			        	    returnFun: goto_query
			        	}); 			    		
			    	}else {
			    		art.dialog({
							content: obj.msg
						});
			    	}
				}
			});
        });    
        //审核   
        $("#checkWHCheckOut").on("click", function() {
			var row = selectDataTableRow();
    		if(!row){
    			art.dialog({content: "请选择一条记录进行审核操作！",width: 200});
    			return false;
    		}
    		$.ajax({
				url : "${ctx}/admin/whCheckOut/doCheckCheck",
				data : {no: row.no},
				contentType: "application/x-www-form-urlencoded; charset=UTF-8",
				dataType: "json",
				type: "post",
				cache: false,
				error: function(){
			        art.dialog({
						content: '出现异常，无法审核'
					});
			    },
			    success: function(obj){
			    	if(obj.rs){
			        	Global.Dialog.showDialog("checkWHCheckOut", {
			        	  title: "出库记账--审核",
			        	  url: "${ctx}/admin/whCheckOut/goCheck?id=" + row.no,
			        	  height: 700,
			        	  width: 1100,
			        	  postData: row.no,
			        	  returnFun: goto_query
			        	}); 			    		
			    	} else {
			    		art.dialog({
							content: obj.msg
						});
			    	}
				}
			});
        });          
        //反审核
        $("#checkReturnWHCheckOut").on("click", function() {
			var row = selectDataTableRow();
    		if(!row){
    			art.dialog({content: "请选择一条记录进行审核操作！",width: 200});
    			return false;
    		}
    		$.ajax({
				url : "${ctx}/admin/whCheckOut/doCheckCheckReturn",
				data : {no: row.no},
				contentType: "application/x-www-form-urlencoded; charset=UTF-8",
				dataType: "json",
				type: "post",
				cache: false,
				error: function(){
			        art.dialog({
						content: '出现异常，无法审核'
					});
			    },
			    success: function(obj){
			    	if(obj.rs){
			        	Global.Dialog.showDialog("checkReturnWHCheckOut", {
			        	  title: "出库记账--反审核",
			        	  url: "${ctx}/admin/whCheckOut/goCheckReturn?id=" + row.no,
			        	  height: 700,
			        	  width: 1100,
			        	  postData: row.no,
			        	  returnFun: goto_query
			        	}); 			    		
			    	} else {
			    		art.dialog({
							content: obj.msg
						});
			    	}
				}
			});
        });          
        //查看
        $("#viewWHCheckOut").on("click",function(){
        	var row = selectDataTableRow();
    		if(!row){
    			art.dialog({content: "请选择一条记录！",width: 200});
    			return false;
    		}
            //查看窗口
        	Global.Dialog.showDialog("viewWHCheckOut",{
        	  title:"出库记账--查看",
        	  url:"${ctx}/admin/whCheckOut/goView?id=" + row.no,
        	  height: 700,
        	  width:1100,
        	  postData: row.no,
        	  returnFun:goto_query
        	});
        });
        //导出EXCEL
        $("#whCheckOutExcel").on("click",function(){
        	var url = "${ctx}/admin/whCheckOut/doWHCheckOutExcel";
        	doExcelAll(url);
		});
        //明细查询
        $("#showWHCheckOutDetail").on("click",function(){
       		Global.Dialog.showDialog("showWHCheckOutDetail",{
        		title:"出库记账--明细查询",
        	  	url:"${ctx}/admin/whCheckOut/goDetail",
        	  	height: 700,
        	  	width:1200,
        	  	returnFun:goto_query
        	});
        });  
         //打印
        $("#whCHeckoutPrint").on("click",function(){
			var row = selectDataTableRow();
    		if(!row){
    			art.dialog({content: "请选择一条记录进行打印操作！",width: 200});
    			return false;
    		} 
    		var reportName ="";
    		if("<%=czylb %>"=="1"){
   	 			reportName="whCheckOut_itemAppSend_erp.jasper";
		    }else{
		    	reportName = "whCheckOut_ItemAppSend.jasper";
		    }
        	Global.Print.showPrint(reportName, {
				No: $.trim(row.no) ,
				Date: $.trim(row.date) ,
				WHDescr: $.trim(row.whdescr) ,
				AppCZYDescr: $.trim(row.appczydescr) ,
				LogoPath : "<%=basePath%>jasperlogo/",
				SUBREPORT_DIR: "<%=jasperPath%>" 
			});
        });
});
</script>
</head> 
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form action="" method="post" id="page_form" class="form-search">
				<input type="hidden" name="jsonString" value="" />
				<input type="hidden" id="expired" name="expired" value="${whCheckOut.expired}" />
				<ul class="ul-form">
					<li>
						<label>记账单号</label>
						<input type="text" id="no" name="no" value="${whCheckOut.no}" />
					</li>
					<li>
						<label>申请日期</label>
						<input type="text" id="dateFrom" name="dateFrom" class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${whCheckOut.date}' pattern='yyyy-MM-dd'/>" />
					</li>
					<li>					
						<label>至</label>
						<input type="text" id="dateTo" name="dateTo" class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${whCheckOut.date}' pattern='yyyy-MM-dd'/>" />
					</li>
					<li>
						<label>仓库编号</label>
						<input type="text" id="whCode" name="whCode" value="${whCheckOut.whCode}" />
					</li>
					<li>
						<label>记账日期</label>
						<input type="text" id="checkDateFrom" name="checkDateFrom" class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${whCheckOut.checkDate}' pattern='yyyy-MM-dd'/>" />
					</li>
					<li>					
						<label>至</label>
						<input type="text" id="checkDateTo" name="checkDateTo" class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${whCheckOut.checkDate}' pattern='yyyy-MM-dd'/>" />
					</li>
					<li>
						<label>账单状态</label>
						<house:xtdmMulit id="status" dictCode="" sql="select cbm SQL_VALUE_KEY,note SQL_LABEL_KEY from tXTDM where ID='WHChkOutStatus' and (CBM='1' or CBM='2'or CBM='3')" selectedValue="1,2,3"></house:xtdmMulit>
					</li>
					<li>
						<label><div class="labeldocumentNo">凭证号</div></label>
						<input type="text" id="documentNo" name="documentNo" value="${whCheckOut.documentNo}" />
					</li>
					<li class="search-group">					
						<input type="checkbox" id="expired_show" name="expired_show"
							value="${whCheckOut.expired}" onclick="hideExpired(this)"
							${itemApp.expired!='T' ?'checked':'' }/><p>隐藏过期</p> 
						<button type="button" class="btn  btn-sm btn-system " onclick="goto_query();"  >查询</button>
						<button type="button" class="btn btn-sm btn-system " onclick="clearForm();"  >清空</button>
					</li>
				</ul>
			</form>
		</div>
		<div class="clear_float"> </div>
		<div class="btn-panel" >
			<div class="btn-group-sm"  >
				<house:authorize authCode="WHCHECKOUT_ADD">
					<button id="addWHCheckOut" type="button" class="btn btn-system ">新增</button>
				</house:authorize>
				
				<house:authorize authCode="WHCHECKOUT_UPDATE">
					<button id="updateWHCheckOut" type="button" class="btn btn-system ">编辑</button>
				</house:authorize>
				
				<house:authorize authCode="WHCHECKOUT_AUDIT">
					<button id="checkWHCheckOut" type="button" class="btn btn-system ">审核</button>
				</house:authorize>
				
				<house:authorize authCode="WHCHECKOUT_REAUDIT">
					<button id="checkReturnWHCheckOut" type="button" class="btn btn-system ">反审核</button>
				</house:authorize>
				
				<house:authorize authCode="WHCHECKOUT_VIEW">
					<button id="viewWHCheckOut" type="button" class="btn btn-system ">查看</button>
				</house:authorize>
				
				<house:authorize authCode=" WHCHECKOUT_DETAIL">
					<button id="showWHCheckOutDetail" type="button" class="btn btn-system ">明细查询</button>
				</house:authorize>
				<house:authorize authCode="WHCHECKOUT_EXCEL">
					<button id="whCheckOutExcel" type="button" class="btn btn-system ">输出至excel</button>
				</house:authorize>
				<house:authorize authCode="WHCHECKOUT_PRINT">
					<button id="whCHeckoutPrint" type="button" class="btn btn-system ">打印</button>
				</house:authorize>
			</div>
		</div>
		<div id="content-list">
			<table id= "dataTable"></table>
			<div id="dataTablePager"></div>
		</div> 
	</div>
</body>
</html>


