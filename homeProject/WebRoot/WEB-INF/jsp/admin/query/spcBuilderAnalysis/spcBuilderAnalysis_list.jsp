<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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
	<script src="${resourceRoot}/pub/component_spcBuilder.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
//导出EXCEL
function doExcel(){
	var url = "${ctx}/admin/spcBuilderAnalysis/doExcel";
	doExcelAll(url);
}
function view(){
	var emNum="${USER_CONTEXT_KEY.emnum}";
	var custRight="${USER_CONTEXT_KEY.custRight}";
	if($.trim($("#dateTo").val())==''){
		art.dialog({content:"统计截止日期不能为空",width:200});
		return false;
	}
	var ret = selectDataTableRow();
	var params=$("#page_form").jsonForm();
	params.code=ret.code;
	if(emNum.trim()!=ret.leadercode.trim() && custRight.trim()!="3" && "1"!=$("#viewDT").val()){
		art.dialog({
			content:"只有这个专盘的队长才允许查看明细"
		});
		return false;
	}
    if (ret) {
      Global.Dialog.showDialog("spcBuilderAnalysisDetail",{
		  title:"专盘数据分析明细-查看",
		  url:"${ctx}/admin/spcBuilderAnalysis/goView",
		  postData:params,
		  height:650,
		  width:1000
		});
    } else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}
function goto_query(){
	if($.trim($("#dateTo").val())==''){
		art.dialog({content:"统计截止日期不能为空",width:200});
		return false;
	}
    var dateStart = Date.parse($.trim($("#delivDateFrom").val()));
    var dateEnd = Date.parse($.trim($("#delivDateTo").val()));
    if(dateStart>dateEnd){
   		art.dialog({content: "交房开始日期不能大于结束日期",width: 200});
 		return false;
    } 
    $("#dataTable").jqGrid('destroyFrozenColumns');
	$("#dataTable").jqGrid("setGridParam",{url:"${ctx}/admin/spcBuilderAnalysis/goJqGrid",datatype:'json',postData:$("#page_form").jsonForm(),page:1}).trigger("reloadGrid");
	$("#dataTable").jqGrid('destroyFrozenColumns');
}				
/**初始化表格*/
$(function(){
	$("#code").openComponent_spcBuilder();
	var postData=$("#page_form").jsonForm();
       //初始化表格
	Global.JqGrid.initJqGrid("dataTable",{
		postData: postData,
		height:$(document).height()-$("#content-list").offset().top-80,
		colModel : [
			{name: "code", index:"code", width: 70, label:"专盘编号", sortable: true, align: "left",count: true,frozen : true},
			{name: "leadercode", index:"leadercode", width: 60, label:"队长", sortable: true, align: "left",frozen : true,hidden:true},
			{name: "leaderdescr", index:"leaderdescr", width: 60, label:"队长", sortable: true, align: "left",frozen : true},
		 	{name: "builderdescr", index:"builderdescr", width: 120, label:"楼盘名称", sortable: true, align: "left",frozen : true},
			{name: "typedescr", index:"typedescr", width: 70, label:"楼盘类型", sortable: true, align: "left"},
			{name: "delivdate", index: "delivdate", width: 75, label:"交房时间", sortable: true, align: "left", formatter: formatDate},
			{name: "totalqty", index: "totalqty", width: 70, label:"总户数", sortable: true,sorttype:"float", align: "right", sum: true},
	    	{name: "delivqty", index: "delivqty", width: 70, label:"交房户数", sortable: true,sorttype:"float", align: "right", sum: true},
	    	{name: "delivper", index: "delivper", width: 70, label:"交房率", sortable: true,sorttype:'number', align: "right",formatter:"number", formatoptions:{decimalSeparator:".",thousandsSeparator:" ",decimalPlaces:2,suffix:"%"}},
	   		{name: "crtcount", index: "crtcount", width: 70, label:"来客量", sortable: true,sorttype:"float", align: "right", sum: true},
	   		{name: "crtper", index: "crtper", width: 70, label: "到店率", sortable: true,sorttype:"number", align: "right",formatter:"number", formatoptions:{decimalSeparator:".",thousandsSeparator:" ",decimalPlaces:2,suffix:"%"}},
	  		{name: "setcount", index: "setcount", width: 70, label:"下定数", sortable: true,sorttype:"float", align: "right", sum: true},
	  		{name: "unsubcount", index: "unsubcount", width: 70, label:"退定数", sortable: true,sorttype:"float", align: "right", sum: true},
	  	    {name: "setper", index: "setper", width: 70, label:"订单占比", sortable: true,sorttype:"number", align: "right",formatter:"number", formatoptions:{decimalSeparator:".",thousandsSeparator:" ",decimalPlaces:2,suffix:"%"}},
	    	{name: "signcount", index: "signcount", width: 70, label:"有效合同数", sortable: true,sorttype:"float", align: "right", sum: true},
	  		{name: "sumcontractfee", index: "sumcontractfee", width: 90, label:"合同金额/万", sortable: true,sorttype:"float", align: "right", sum: true},
	  		{name: "yjdecorcmpbeginqty", index: "yjdecorcmpbeginqty", width: 90, label:"有家开工户数", sortable: true,sorttype:"float", align: "right", sum: true},
	  		{name: "totalbeginqty", index: "totalbeginqty", width: 85, label:"总开工户数", sortable: true,sorttype:"float", align: "right", sum: true},
	  		{name: "yjdecorcmpbeginper", index: "yjdecorcmpbeginper", width: 85, label:"有家开工率", sortable: true,sorttype:"number", align: "right",formatter:"number", formatoptions:{decimalSeparator:".",thousandsSeparator:" ",decimalPlaces:2,suffix:"%"}},
	   		{name: "beginper", index: "beginper", width: 70, label:"装修率", sortable: true,sorttype:"number", align: "right",formatter:"number", formatoptions:{decimalSeparator:".",thousandsSeparator:" ",decimalPlaces:2,suffix:"%"}},
	   		{name: "selfdecorqty", index: "selfdecorqty", width: 110, label:"业主自装开工数", sortable: true,sorttype:"float", align: "right", sum: true},
	   		{name: "selfdecorper", index: "selfdecorper", width: 85, label:"自装率", sortable: true,sorttype:"number", align: "right",formatter:"number", formatoptions:{decimalSeparator:".",thousandsSeparator:" ",decimalPlaces:2,suffix:"%"}},
	    	{name: "decorcmpbeginqty", index: "decorcmpbeginqty", width: 110, label:"装修公司开工数", sortable: true,sorttype:"float", align: "right", sum: true},
	    	{name: "prjmanqty", index: "prjmanqty", width: 110, label:"项目经理翻单数", sortable: true,sorttype:"float", align: "right", sum: true},
	    	{name: "prjmanper", index: "prjmanper", width: 85, label:"翻单占有率", sortable: true,sorttype:"number", align: "right",formatter:"number", formatoptions:{decimalSeparator:".",thousandsSeparator:" ",decimalPlaces:2,suffix:"%"}},
	    	{name: "signper", index: "signper", width: 95, label:"有效合同占比", sortable: true,sorttype:"number", align: "right",formatter:"number", formatoptions:{decimalSeparator:".",thousandsSeparator:" ",decimalPlaces:2,suffix:"%"}},
			{name: "yjsigncount", index:"yjsigncount", width: 85, label:"有家合同数", sortable: true,sorttype:"float", align: "right", sum: true},
	   		{name: "decorcmp1", index: "decorcmp1", width: 100, label: "装修公司", sortable: true, align: "left"},
	   		{name: "decorcmp1qty", index:"decorcmp1qty", width: 70, label:"套数", sortable: true,sorttype:"float", align: "right", sum: true},
	   		{name: "decorcmp2", index: "decorcmp2", width: 100, label: "装修公司", sortable: true, align: "left"},
	   		{name: "decorcmp2qty", index:"decorcmp2qty", width: 70, label:"套数", sortable: true,sorttype:"float", align: "right", sum: true},
	   		{name: "decorcmp3", index: "decorcmp3", width: 100, label: "装修公司", sortable: true, align: "left"},
	   		{name: "decorcmp3qty", index:"decorcmp3qty", width: 70, label:"套数", sortable: true,sorttype:"float", align: "right", sum: true},
	   		{name: "decorcmp4", index: "decorcmp4", width: 100, label: "装修公司", sortable: true, align: "left"},
	   		{name: "decorcmp4qty", index:"decorcmp4qty", width: 70, label:"套数", sortable: true,sorttype:"float", align: "right", sum: true},
	   		{name: "decorcmp5", index: "decorcmp5", width: 100, label: "装修公司", sortable: true, align: "left"},
	   		{name: "decorcmp5qty", index:"decorcmp5qty", width: 85, label:"套数", sortable: true,sorttype:"float", align: "right", sum: true}
	    ],  
	    loadonce: true, 
       	rowNum:100000,  
   		pager :'1',
   		gridComplete:function(){
   			$('.ui-jqgrid-bdiv').scrollTop(0);
            frozenHeightReset("dataTable");
   		},
	});
	$("#dataTable").jqGrid('setFrozenColumns'); 
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
						<label>统计截止日期</label>
						<input type="text" id="dateTo" name="dateTo" class="i-date"
							onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
							value="<fmt:formatDate value="${spcBuilder.dateTo}" pattern='yyyy-MM-dd'/>" />
					</li>
					<li>
						<label>专盘编号</label>
						<input type="text" id="code" name="code" value="${spcBuilder.code}"/>
					</li>
					<li><label>交房时间从</label> 
						<input type="text" id="delivDateFrom"name="delivDateFrom" class="i-date"
							onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
							value="<fmt:formatDate value="${spcBuilder.delivDateFrom}" pattern='yyyy-MM-dd'/>"/>
					</li>
					<li>
						<label>到</label> 
						<input type="text" id="delivDateTo"name="delivDateTo" class="i-date"
							onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
							value="<fmt:formatDate value="${spcBuilder.delivDateTo}" pattern='yyyy-MM-dd'/>" />
					</li>
					<li>
						<label>楼盘类型</label> 
						<house:xtdmMulit id="type" dictCode="SPCBUILDERTYPE"></house:xtdmMulit>
					</li>
					<li>
						<label>项目类型</label> 
						<house:xtdmMulit id="builderType" dictCode="BUILDERTYPE"></house:xtdmMulit>
					</li>
					<li id="loadMore">
						<input type="checkbox" id="expired_show" name="expired_show"
										value="${spcBuilder.expired}" onclick="hideExpired(this)"
										${spcBuilder.expired!='T' ?'checked':'' }/>隐藏过期&nbsp;  
						<button type="button" class="btn btn-sm btn-system "onclick="goto_query();">查询</button>
					</li>
				</ul>
			</form>
		</div>
		<div class="clear_float"> </div>
		<div class="btn-panel">
			<div class="btn-group-sm">
				<house:authorize authCode="SPCBUILDERANALYSIS_VIEWDT">
					<!-- 增加权限，查看所有明细，分配该权限非队长可以查看明细。 -->
					<input type="hidden" id="viewDT" value="1">
				</house:authorize>
				<house:authorize authCode="SPCBUILDERANALYSIS_VIEW">
					<button id="btnView" type="button" class="btn btn-system " onclick="view()">查看</button>
				</house:authorize>
				<house:authorize authCode="SPCBUILDERANALYSIS_EXCEL">
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


