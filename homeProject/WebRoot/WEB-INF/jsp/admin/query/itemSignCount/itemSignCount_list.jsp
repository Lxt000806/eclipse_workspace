<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<title>材料签单统计</title>
<%@ include file="/commons/jsp/common.jsp" %>
<script type="text/javascript">
function view() {
	var ret = selectDataTableRow();
 	if(ret){
 	 	var params=$("#page_form").jsonForm();
  	    params.signMonth=ret.yuefen;
  	    params.department1=ret.shiyebu;
		Global.Dialog.showDialog("view",{
			title:"签单统计明细查看",
			url:"${ctx}/admin/itemSignCount/goView",
			postData:params,
			height:700,
			width:1000,
			returnFun:goto_query
		});
	}else{
		art.dialog({
			content:"请选择一条数据",
		});
	}
}     
//导出EXCEL
function doExcel(){
	var url = "${ctx}/admin/itemSignCount/doExcel";
	doExcelAll(url);
}
function goto_query(){
	if($.trim($("#itemType1").val())==""){
		art.dialog({content: "请选择材料类型1",width: 200});
		return false;
	} 
	if($.trim($("#dateFrom").val())==""){
		art.dialog({content: "统计开始日期不能为空",width: 200});
		return false;
	} 
	if($.trim($("#dateTo").val())==""){
		art.dialog({content: "统计结束日期不能为空",width: 200});
		return false;
	}
    var dateStart = Date.parse($.trim($("#dateFrom").val()));
    var dateEnd = Date.parse($.trim($("#dateTo").val()));
    if(dateStart>dateEnd){
   		art.dialog({content: "统计开始日期不能大于结束日期",width: 200});
 		return false;
    } 
	$("#dataTable").jqGrid("setGridParam",{url:"${ctx}/admin/itemSignCount/goJqGrid",datatype:'json',postData:$("#page_form").jsonForm(),page:1,fromServer: true}).trigger("reloadGrid");
}				
/**初始化表格*/
$(function(){
	Global.LinkSelect.initSelect("${ctx}/admin/item/itemTypeByAuthority","itemType1"); 
    var optionSelect=$("#itemType1 option");
    var sValue=""
    optionSelect.each(function (i,e) {
    	sValue= $(e).text().replace(/[^a-z]+/ig,"");
        if(sValue!="ZC" &&sValue!="JC"&&sValue!="RZ"){
            $(this).hide();
        }
        if(sValue=="JC"){
            $(this).attr("selected","selected");
        }   
    });
	var postData=$("#page_form").jsonForm();
	postData.custType="${customer.custType}",
    //初始化表格
	Global.JqGrid.initJqGrid("dataTable",{
		postData: postData,
		height:$(document).height()-$("#content-list").offset().top-70,
		colModel : [
			{name: "yuefen", index: "yuefen", width: 100, label: "月份", sortable: true, align: "left", hidden: true},
		 	{name: "yuefendescr", index: "yuefendescr", width: 100, label: "月份", sortable: true, align: "left", count: true},
			{name: "shiyebudescr", index: "shiyebudescr", width: 100, label: "事业部", sortable: true, align: "left"},
			{name: "shiyebu", index: "shiyebu", width: 100, label: "事业部", sortable: true, align: "left",hidden: true},
			{name: "signcount", index: "signcount", width: 100, label: "总签单数", sortable: true, align: "right", sum: true},
			{name: "zqdts", index: "zqdts", width: 100, label: "材料签单套数", sortable: true, align: "right", sum: true},
			{name: "zhte", index: "zhte", width: 100, label: "总合同额", sortable: true, align: "right", sum: true},
			{name: "zmj", index: "zmj", width: 100, label: "总面积", sortable: true, align: "right", sum: true},
			{name: "avgje", index: "avgje", width: 100, label: "每平方金额", sortable: true, align: "right", sum: true},
			{name: "zlr", index: "zlr", width: 100, label: "总利润", sortable: true, align: "right", sum: true},
			{name: "avglr", index: "avglr", width: 100, label: "每平方毛利", sortable: true, align: "right", sum: true},
			{name: "fwxje", index: "fwxje", width: 100, label: "服务性金额", sortable: true, align: "right", sum: true}
	    ],    
	    loadonce: true,
	    rowNum:100000,  
	    pager :'1',
	  	gridComplete:function(){
	        if ($("#statistcsMethod").val() == '1') {
	       		$("#dataTable").jqGrid('showCol', "yuefendescr");
	      	 	$("#dataTable").jqGrid('hideCol', "shiyebudescr");		  
			}else if ($("#statistcsMethod").val() == '2') {
				$("#dataTable").jqGrid('hideCol', "yuefendescr");
				$("#dataTable").jqGrid('showCol', "shiyebudescr");			
			} 
        },
        ondblClickRow: function(){
                	view();
        } 	 
	});
});
</script>
</head>
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form action="" method="post" id="page_form" class="form-search">
				<input type="hidden" id="expired" name="expired" value="F" /> <input
					type="hidden" name="jsonString" value="" />
				<ul class="ul-form">
					<li>
						<label>材料类型1</label> 
						<select id="itemType1" name="itemType1"></select></li>
					<li>
						<label>客户类型</label>
						<house:custTypeMulit id="custType"  selectedValue="${customer.custType}"></house:custTypeMulit>
					</li>
					<li>
						<label>签订时间从</label> 
						<input type="text" id="dateFrom" name="dateFrom" class="i-date"
							onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
							value="<fmt:formatDate value="${customer.dateFrom}" pattern='yyyy-MM-dd'/>" />
					</li>
					<li>
						<label>到</label>
						<input type="text" id="dateTo" name="dateTo" class="i-date"
							onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
							value="<fmt:formatDate value="${customer.dateTo}" pattern='yyyy-MM-dd'/>" />
					</li>
					<li>
						<label>统计方式</label> 
						<select id="statistcsMethod" name="statistcsMethod">
							<option value="1">按月份</option>
							<option value="2">按事业部</option>
						</select>
					</li>
					<li id="loadMore">
						<button type="button" class="btn btn-sm btn-system " onclick="goto_query();">查询</button>
					</li>
				</ul>
			</form>
		</div>
		<div class="btn-panel">
			<div class="btn-group-sm">
				<house:authorize authCode="ITEMSIGNCOUNT_VIEW">
					<button type="button" class="btn btn-system" onclick="view()">查看</button>
				</house:authorize>
				<house:authorize authCode="ITEMSIGNCOUNT_EXCEL">
					<button type="button" class="btn btn-system" onclick="doExcel()">导出excel</button>
				</house:authorize>
			</div>
			<div id="content-list">
				<table id="dataTable"></table>
				<div id="dataTablePager"></div>
			</div>
		</div>
	</div>
</body>
</html>


