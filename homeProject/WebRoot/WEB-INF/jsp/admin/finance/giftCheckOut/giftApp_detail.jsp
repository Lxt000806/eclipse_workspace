<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>
<%
UserContext uc = (UserContext)request.getSession().getAttribute(CommonConstant.USER_CONTEXT_KEY);
String czylb=uc.getCzylb();
String czybh="";
if("2".equals(czylb)) czybh=uc.getCzybh();
%>
<!DOCTYPE html>
<html>
<head>
	<title>WHCHeckOutDetail列表</title>
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
	    if("<%=czylb %>"=="1"){
       	 	$("#whCode").openComponent_wareHouse();
        }else{
       		$("#whCode").openComponent_wareHouse({condition:{czybh:"<%=czybh %>"}});
       		$("#documentNo").hide();
	      	$(".labeldocumentNo").hide();
        }
		$("#custCode").openComponent_customer();
		$("#appCzy").openComponent_employee();
		

		//初始化材料类型1，2，3三级联动
		Global.LinkSelect.initSelect("${ctx}/admin/item/itemTypeByAuthority","itemType1","itemType2");

        //初始化表格
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/whCheckOut/goDetailJqGrid",
			height:$(document).height()-$("#content-list").offset().top-120,
        	styleUI: 'Bootstrap',
			colModel : [
		      {name: "pk", index: "pk", width: 74, label: "pk", sortable: true, align: "left", count: true, hidden: true},
			  {name: "wcono", index: "wcono", width: 120, label: "记账单号", sortable: true, align: "left"},
              {name: "statusdescr", index: "statusdescr", width: 80, label: "状态", sortable: true, align: "left"},
              {name: "date", index: "date", width: 140, label: "申请日期", sortable: true, align: "left", formatter: formatTime},
              {name: "confirmdate", index: "confirmdate", width: 140, label: "审核日期", sortable: true, align: "left", formatter: formatTime},
              {name: "checkdate", index: "checkdate", width: 140, label: "记账日期", sortable: true, align: "left", formatter: formatTime},
              {name: "whdescr", index: "whdescr", width: 106, label: "仓库", sortable: true, align: "left"},
              {name: "wcodocumentno", index: "wcodocumentno", width: 103, label: "凭证号", sortable: true, align: "left"},
              {name: "no", index: "no", width: 100, label: "发货单号", sortable: true, align: "left"},
              {name: "iano", index: "iano", width: 100, label: "领料单号", sortable: true, align: "left"},
              {name: "itemtype1descr", index: "itemtype1descr", width: 110, label: "材料类型1", sortable: true, align: "left"},
              {name: "itemtype2descr", index: "itemtype2descr", width: 110, label: "材料类型2", sortable: true, align: "left"},
              {name: "itemsumcost", index: "itemsumcost", width: 100, label: "材料金额", sortable: true, align: "right", sum: true},
              {name: "projectamount", index: "projectamount", width: 140, label: "项目经理结算价", sortable: true, align: "right", sum: true},
              {name: "sumcost", index: "sumcost", width: 90, label: "总金额", sortable: true, align: "right", sum: true},
              {name: "sumprojectamount", index: "sumprojectamount", width: 160, label: "项目经理结算总价", sortable: true, align: "right", sum: true},
              {name: "custdocumentno", index: "custdocumentno", width: 90, label: "档案号", sortable: true, align: "left"},
              {name: "address", index: "address", width: 119, label: "楼盘", sortable: true, align: "left"},
              {name: "expired", index: "expired", width: 100, label: "是否过期", sortable: true, align: "left", hidden: true},
           	  {name: "sendfee", index: "sendfee", width: 90, label: "配送费", sortable: true, align: "right"},
			  {name: "whfee", index: "whfee", width: 90, label: "仓储费", sortable: true, align: "right"},
			  {name: "projectothercost", index: "projectothercost", width: 120, label: "项目经理调整", sortable: true, align: "right"},
			  {name: "remarks", index: "remarks", width: 150, label: "发货备注", sortable: true, align: "left"},
			  {name: "itemappremarks", index: "itemappremarks", width: 200, label: "领料备注", sortable: true, align: "left"} ,
            ],
           
		});
		  if("<%=czylb %>"!="1"){
            $("#dataTable").jqGrid('hideCol', "itemsumcost");
            $("#dataTable").jqGrid('hideCol', "projectothercost");
            $("#dataTable").jqGrid('hideCol', "sumcost");
            $("#dataTable").jqGrid('hideCol', "sumprojectothercost");
            $("#dataTable").jqGrid('hideCol', "wcodocumentno");
          }
        
        //导出EXCEL
        $("#whCheckOutDetailExcel").on("click",function(){
        	var url = "${ctx}/admin/whCheckOut/doWHCheckOutDetailExcel";
        	doExcelAll(url);
		});   
});
</script>
</head>

    
<body>
	<div class="body-box-list">
		<div class="panel panel-system" >
		    <div class="panel-body" >
		    	<div class="btn-group-xs" >
					<button type="button" class="btn btn-system "  id="closeBut" onclick="closeWin(false)">关闭</button>
				</div>
			</div>
		</div>
<!-- 		<div class="panel-info" >  
			<div class="panel-body"> -->
		<div>  
			<div class="query-form">
				<form action="" method="post" id="page_form" class="form-search">
					<input type="hidden" id="expired" name="expired" value="${whCheckOut.expired}" />
					<input type="hidden" name="jsonString" value="" />
					<ul class="ul-form">
						<li>
							<label>记账单号</label>
							<input type="text" id="no" name="no" />
						</li>
						<li>
							<label>申请日期</label>
							<input type="text" id="dateFrom" name="dateFrom" class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
						</li>
						<li>					
							<label>至</label>
							<input type="text" id="dateTo" name="dateTo" class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
						</li>
						<li>
							<label>仓库编号</label>
							<input type="text" id="whCode" name="whCode" />
						</li>
						<li>
							<label>记账日期</label>
							<input type="text" id="checkDateFrom" name="checkDateFrom" class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"  />
						</li>
						<li>				
							<label>至</label>
							<input type="text" id="checkDateTo" name="checkDateTo" class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"  />
						</li>
						<li>
							<label>发货单号</label>
							<input type="text" id="itemAppSendNo" name="itemAppSendNo" />
						</li>
						<li>
							<label>账单状态</label>
							<house:xtdmMulit id="status" dictCode="" sql="select cbm SQL_VALUE_KEY,note SQL_LABEL_KEY from tXTDM where ID='WHChkOutStatus' and (CBM='1' or CBM='2'or CBM='3')" selectedValue="1,2,3"></house:xtdmMulit>
						</li>
						<li>
							<label>材料类型1</label>
							<select id="itemType1" name="itemType1"></select>
						</li>
						<li>
							<label>楼盘</label>
							<input type="text" id="address" name="address" />
						</li>
						<li>
							<label>领料单号</label>
							<input type="text" id="iaNo" name="iaNo" />
						</li>
						<li>
							<label><div class="labeldocumentNo">凭证号</div></label>
							<input type="text" id="documentNo" name="documentNo" value="${whCheckOut.documentNo}" />
						</li>
						<li class="search-group">					
							<input type="checkbox" id="expired_show" name="expired_show"
								value="${whCheckOut.expired}" onclick="hideExpired(this)"
								${whCheckOut.expired!='T' ?'checked':'' }/><p>隐藏过期</p> 
							<button type="button" class="btn  btn-sm btn-system " onclick="goto_query();"  >查询</button>
							<button type="button" class="btn btn-sm btn-system " onclick="clearForm();"  >清空</button>
						</li>
					</ul>
				</form>
			</div>
		</div>
		
		<div class="clear_float"> </div>
		
		<div class="btn-panel" >
			<div class="btn-group-sm"  >
                 <house:authorize authCode="WHCHECKOUT_EXCEL">
					<button id="whCheckOutDetailExcel" type="button" class="btn btn-system ">输出至excel</button>
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


