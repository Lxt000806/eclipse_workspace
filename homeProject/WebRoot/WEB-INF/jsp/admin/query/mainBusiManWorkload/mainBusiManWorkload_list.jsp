<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<title>签单排行</title>
	<%@ include file="/commons/jsp/common.jsp" %>
<style type="text/css">

</style>
<script type="text/javascript">
function view() {
	var ret = selectDataTableRow();
  		if(!ret){
  			art.dialog({content: "请选择一条记录进行查看！",width: 200});
  			return false;
  		}
  	    var params=$("#page_form").jsonForm();
  	    params.empCode=ret.number;
  	    Global.Dialog.showDialog("mainBusiManWorkloadView",{
		 title:"主材工作量--明细查看",
		 url:"${ctx}/admin/mainBusiManWorkload/goView", 	  	
		 height:620,
         width:900,
		  postData:params,
		  returnFun: goto_query
		});
}
         
function goto_query(){
	if($.trim($("#dateFrom").val())==''){
			art.dialog({content: "统计开始日期不能为空",width: 200});
			return false;
	} 
	if($.trim($("#dateTo").val())==''){
			art.dialog({content: "统计结束日期不能为空",width: 200});
			return false;
	}
     var dateStart = Date.parse($.trim($("#dateFrom").val()));
     var dateEnd = Date.parse($.trim($("#dateTo").val()));
     if(dateStart>dateEnd){
    	 art.dialog({content: "统计开始日期不能大于结束日期",width: 200});
			return false;
     } 
	$("#dataTable").jqGrid("setGridParam",{url:"${ctx}/admin/mainBusiManWorkload/goJqGrid",datatype:'json',postData:$("#page_form").jsonForm(),page:1,fromServer: true}).trigger("reloadGrid");
}

function doExcel(){
	doExcelNow("主材管家工作量","dataTable","page_form");
}			
/**初始化表格*/
$(function(){
		var postData=$("#page_form").jsonForm();
		postData.custType="${customer.custType}",
        //初始化表格
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/mainBusiManWorkload/goJqGrid",
			postData: postData,
			height:$(document).height()-$("#content-list").offset().top-90,
			colModel : [
				{name: "number", index: "number", width: 55, label: "员工编号", sortable: true, align: "left",hideen:true},
				{name: "namechi", index: "namechi", width: 75, label: "管家姓名", sortable: true, align: "left", count: true},
				{name: "begincount", index: "begincount", width: 75, label: "分配套数", sortable: true,sorttype:'float', align: "right", sum: true },
				{name: "buildingcount", index: "buildingcount", width: 75, label: "在建套数", sortable: true,sorttype:'float', align: "right", sum: true},
				{name: "completedcount", index: "completedcount", width: 75, label: "完工套数", sortable: true,sorttype:'float', align: "right", sum: true},
				{name: "firstconfirmcount", index: "firstconfirmcount", width: 100, label: "第一次确认套数", sortable: true,sorttype:'float', align: "right", sum: true},
				{name: "firstontimeper", index: "firstontimeper", width: 115, label: "第一次确认及时率", sortable: true,sorttype:'float', align: "right",formatter:"number", formatoptions:{suffix: "%"}},
				{name: "firstconfirmallcount", index: "firstconfirmallcount", width: 115, label: "一次全部确认套数", sortable: true,sorttype:'float', align: "right",formatter:"number"},
				{name: "firstconfirmallrate", index: "firstconfirmallrate", width: 115, label: "一次性确认占比", sortable: true,sorttype:'float', align: "right",formatter:"number",formatoptions:{suffix: "%"}},
				{name: "secondconfirmcount", index: "secondconfirmcount", width: 100, label: "第二次确认套数", sortable: true,sorttype:'float', align: "right", sum: true},
				{name: "secondontimeper", index: "secondontimeper", width: 115, label: "第二次确认及时率", sortable: true,sorttype:'float', align: "right",formatter:"number", formatoptions:{suffix: "%"}},
				{name: "chgcount", index: "chgcount", width: 75, label: "变更套数", sortable: true,sorttype:'float', align: "right", sum: true},
				{name: "chgnum", index: "chgnum", width: 75, label: "变更次数", sortable: true,sorttype:'float', align: "right", sum: true},
				{name: "firstnocompletedcount", index: "firstnocompletedcount", width: 140, label: "第一次确认未完成套数", sortable: true,sorttype:'float', align: "right", sum: true},
				{name: "firstnocompletedovertimecount", index: "firstnocompletedovertimecount", width: 120, label: "一次超时未确认", sortable: true,sorttype:'float', align: "right", sum: true},
				{name: "secondnocompletedcount", index: "secondnocompletedcount", width: 140, label: "第二次确认未完成套数", sortable: true,sorttype:'float', align: "right", sum: true},
				{name: "secondnocompletedovertimecount", index: "secondnocompletedovertimecount", width: 120, label: "二次超时未确认", sortable: true,sorttype:'float', align: "right", sum: true}
		    ],    
		    loadonce: true,
		    rowNum:100000,  
		    pager :'1'
	});
});
</script>
</head>
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form action="" method="post" id="page_form" class="form-search" >
				<input type="hidden" id="expired" name="expired" value="F" />
				<input type="hidden" name="jsonString" value="" />
				<ul class="ul-form">
					
						<li>
							<label>统计日期</label>
							<input type="text" id="dateFrom" name="dateFrom" class="i-date"  onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${customer.dateFrom}' pattern='yyyy-MM-dd'/>" />
						</li>
						<li>
							<label>至</label>
							<input type="text" id="dateTo" name="dateTo" class="i-date"  onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${customer.dateTo}' pattern='yyyy-MM-dd'/>" />
						</li>
						<li>
							<label>客户类型</label>
							<house:custTypeMulit id="custType" selectedValue="${customer.custType}" ></house:custTypeMulit>
						</li>
						<li style="width:95px"><input type="checkbox" id="expired_show" name="expired_show"
							value="${customer.expired}" onclick="hideExpired(this)" ${customer.expired!='T' ?'checked':'' }/>隐藏过期&nbsp;
						</li>
					<li id="loadMore" >
						<button type="button" class="btn btn-sm btn-system " onclick="goto_query();" >查询</button>
					
					</li>		
			
				</ul>
			</form>
		</div>
		<div class="btn-panel" >
		       <div class="btn-group-sm"  >
               	   <house:authorize authCode="MAINBUSIMANWORKLOAD_VIEW">
               	        <button id="btnView" type="button" class="btn btn-system " onclick="view()">查看</button>	
					</house:authorize>	
	                <house:authorize authCode="MAINBUSIMANWORKLOAD_EXCEL">
	                     <button type="button" class="btn btn-system " onclick="doExcel()">导出excel</button>
					</house:authorize>
				
				</div>
			<div id="content-list">
				<table id= "dataTable"></table>
				<div id="dataTablePager"></div>
			</div>
		</div> 
	</div>
</body>
</html>


