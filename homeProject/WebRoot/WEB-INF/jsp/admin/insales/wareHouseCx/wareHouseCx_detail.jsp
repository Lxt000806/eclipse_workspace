<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    	<title>WareHouse明细</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	
	<script type="text/javascript">
	var setGroupHeadersNums=0;
function cxtj(event){
	if(event.value=="itCode"){
		$("#itCode").show();
		$("#descr").hide();
		$("#descr").val("");
	}else if(event.value=="descr"){
		$("#itCode").val("");
		$("#itCode").hide();
		$("#descr").show();
	}else if(event.value=="itCode2"){
		$("#itCode2").show();
		$("#descr2").hide();
		$("#document").hide();
		$("#descr2").val("");
		$("#document").val("");
	}else if(event.value=="descr2"){
		$("#itCode2").hide();
		$("#document").hide();
		$("#descr2").show();
		$("#itCode2").val("");
		$("#document").val("");
	}else{
		$("#itCode2").hide();
		$("#descr2").hide();
		$("#itCode2").val("");
		$("#descr2").val("");
		$("#document").show();
	
	}
}
	function goto_query(table,form){
	$("#"+table).jqGrid("setGridParam",{postData:$("#"+form).jsonForm(),page:1}).trigger("reloadGrid");
}
function view(){
	var ret = selectDataTableRow("itemWHBaldataTable");
    if (ret) {
      Global.Dialog.showDialog("wareHouseAnalyseView",{
		  title:"可用库存分析",
		  url:"${ctx}/admin/wareHouseCx/goAnalyseDetail?id="+ret.itcode,
		  height:600,
		  width:1000
		});
    } else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}
//导出EXCEL
function doItemWHBalExcel(){
	var url = "${ctx}/admin/wareHouseCx/doItemWHBalExcel?whCode="+$("#whCode").val();
	doExcelAll(url,'itemWHBaldataTable','tab1_page_form');
}
function doItemTransactionExcel(){
	var url = "${ctx}/admin/wareHouseCx/doItemTransactionExcel?whCode="+$("#whCode").val();
	doExcelAll(url,'itemTransactiondataTable','tab2_page_form');
}
function clearForm(page){
	$("#"+page+ " input[type='text']").val('');
	$("#"+page+ " select[name!='cx']").val('');
}
//tab分页

$(document).ready(function() {  
	// 默认选中最近三个月（前两个月月初到这个月月末）
	var date = new Date();
	var endYear = date.getFullYear();
	var beginYear=date.getFullYear();
	var beginMonth=date.getMonth() -1;//开始月份默认前两个月
	if(beginMonth<=0){//如果开始月份（前两个月）小于等于0，要变成去年的年月份 update by cjg
		beginMonth+=12;
		beginYear-=1;
	}
	var beginDate = beginYear+"-"+beginMonth+"-01 "; //00:00:00
	var endMonth = date.getMonth() + 1;
	var endDate = new Date(endYear, endMonth, 0);
	endDate = endYear+"-"+endMonth+"-"+endDate.getDate(); //+" 23:59:59"
	$("#dateFrom").val(formatDate(beginDate));
	$("#dateTo").val(formatDate(endDate));

	var postData = $("#tab2_page_form").jsonForm();

$('a[data-toggle="tab"]').on('shown.bs.tab', function (e) {
    		Global.JqGrid.initJqGrid("itemTransactiondataTable",{
			url:"${ctx}/admin/wareHouseCx/goItemTransactionJqGrid?whCode="+$("#whCode").val(),
			postData:postData,
			height:$(document).height()-$("#content-list").offset().top-300,
			styleUI: 'Bootstrap',
			colModel : [
			  {name : 'pk',index : 'pk',width : 80,label:'流水号',sortable : true,align : "left"},
			  {name : 'itcode',index : 'itcode',width : 80,label:'产品编号',sortable : true,align : "left"},
		      {name : 'descr',index : 'descr',width : 200,label:'产品名称',sortable : true,align : "left"},
		      {name: 'itemtype2descr', index: 'itemtype2descr', width: 85, label: '材料类型2', sortable: true, align: "left"},
		      {name: 'itemtype3descr', index: 'itemtype3descr', width: 85, label: '材料类型3', sortable: true, align: "left"},
		      {name: 'supplcodedescr', index: 'supplcodedescr', width: 85, label: '供应商', sortable: true, align: "left"},
		      {name : 'ckqty',index : 'ckqty',width :60,label:'数量',sortable : true,align : "right", sum: true},
		      {name : 'ckcost',index : 'ckcost',width :60,label:'成本',sortable : true,align : "right", sum: true},
		      {name : 'ckamount',index : 'ckamount',width : 60,label:'金额',sortable : true,align : "right", sum: true},
		      {name : 'lkqty',index : 'lkqty',width : 60,label:'数量',sortable : true,align : "right", sum: true},
		      {name : 'lkcost',index : 'lkcost',width : 60,label:'成本',sortable : true,align : "right", sum: true},
		      {name : 'lkamount',index : 'lkamount',width :60,label:'金额',sortable : true,align : "right", sum: true},
		      {name : 'aftallqty',index : 'aftallqty',width : 60,label:'数量',sortable : true,align : "right"},
		      {name : 'aftcost',index : 'aftcost',width : 60,label:'成本',sortable : true,align : "right"},
		      {name : 'date',index : 'date',width : 72,label:'变动时间',sortable : true,align : "left",formatter: formatTime,frozen:true},
		      {name : 'prefixdesc',index : 'prefixdesc',width : 100,label:'单据类型',sortable : true,align : "left"},
		      {name : 'document',index : 'document',width : 100,label:'档案号',sortable : true,align : "left"},
		      {name : 'checkoutno',index : 'checkoutno',width : 120,label:'账单编号',sortable : true,align : "left"},
		      {name : 'checkoutstatus',index : 'checkoutstatus',width : 100,label:'账单状态',sortable : true,align : "left"},
		      {name : 'documentno',index : 'documentno',width : 100,label:'凭证号',sortable : true,align : "left"},
		      {name : 'refdocument',index : 'refdocument',width : 100,label:'参考档案号',sortable : true,align : "left"},
		      {name : 'remarks',index : 'remarks',width : 100,label:'备注',sortable : true,align : "left"},
		      {name : 'lastupdate',index : 'lastupdate',width : 150,label:'最后更新时间',sortable : true,align : "left",formatter: formatTime},
		      {name : 'lastupdatedby',index : 'lastupdatedby',width : 100,label:'最后更新人员',sortable : true,align : "left"},
		      {name : 'expired',index : 'expired',width : 100,label:'是否过期',sortable : true,align : "left"},
		      {name : 'actionlog',index : 'actionlog',width : 100,label:'操作',sortable : true,align : "left"}
            ],sortorder:"desc",sortname:'pk',
            'gridComplete':function(){
            if(setGroupHeadersNums==0){
            $("#itemTransactiondataTable").jqGrid('setGroupHeaders', {
				useColSpanStyle : true, // 没有表头的列是否与表头列位置的空单元格合并
				groupHeaders : [ {
				startColumnName : 'ckqty', // 对应colModel中的name
				numberOfColumns : 3, // 跨越的列数
				titleText : '出库'
				}, {
				startColumnName : 'lkqty', // 对应colModel中的name
				numberOfColumns : 3, // 跨越的列数
				titleText : '入库'
				}, {
				startColumnName : 'aftallqty', // 对应colModel中的name
				numberOfColumns : 2, // 跨越的列数
				titleText : '变动后'
				}]
				});
				setGroupHeadersNums++;
            }
            }
		});
		if('${wareHouse.costRight}'=='0'){
				 $("#itemTransactiondataTable").jqGrid('hideCol', "ckamount");
				 $("#itemTransactiondataTable").jqGrid('hideCol', "ckcost");
				 $("#itemTransactiondataTable").jqGrid('hideCol', "lkamount");
				 $("#itemTransactiondataTable").jqGrid('hideCol', "lkcost");
				 $("#itemTransactiondataTable").jqGrid('hideCol', "aftcost");
		}

});
   
});
$(function(){
	Global.LinkSelect.initSelect("${ctx}/admin/item/itemTypeByAuthority","itemType1","itemType2");
	Global.LinkSelect.initSelect("${ctx}/admin/item/itemTypeByAuthority","itemType12","itemType22", "itemType32");
	$document = $(document);
	$('div.panelBar', $document).panelBar();
	 //初始化表格
		Global.JqGrid.initJqGrid("itemWHBaldataTable",{
			url:"${ctx}/admin/wareHouseCx/goItemWHBalJqGrid?whCode="+$("#whCode").val(),
			height:$(document).height()-$("#content-list").offset().top-100,
			styleUI: 'Bootstrap',
			colModel : [
			  {name : 'itcode',index : 'itcode',width : 100,label:'材料编号',sortable : true,align : "left"},
		      {name : 'itemtype2descr',index : 'itemtype2descr',width : 85,label:'材料类型2',sortable : true,align : "left"},
		      {name : 'descr',index : 'descr',width : 200,label:'材料名称',sortable : true,align : "left"},
		      {name : 'qtycal',index : 'qtycal',width : 100,label:'数量',sortable : true,align : "right", sum: true},
		      {name : 'avgcost',index : 'avgcost',width : 100,label:'单价',sortable : true,align : "right"},
		      {name : 'spldescr',index : 'spldescr',width : 100,label:'供应商',sortable : true,align : "left"},
		      {name : 'costamount',index : 'costamount',width : 100,label:'库存成本金额',sortable : true,align : "right", sum: true},
		      {name : 'lastupdate',index : 'lastupdate',width : 150,label:'最后更新时间',sortable : true,align : "left",formatter: formatTime},
		      {name : 'lastupdatedby',index : 'lastupdatedby',width : 100,label:'最后更新人员',sortable : true,align : "left"},
		      {name : 'expired',index : 'expired',width : 100,label:'是否过期',sortable : true,align : "left"},
		      {name : 'actionlog',index : 'actionlog',width : 100,label:'操作',sortable : true,align : "left"}
            ],sortorder:"desc",sortname:'lastupdate'
		});
		if('${wareHouse.costRight}'=='0'){
				 $("#itemWHBaldataTable").jqGrid('hideCol', "avgcost");
				 $("#itemWHBaldataTable").jqGrid('hideCol', "costamount");
		}
//$("#gbox_itemTransactiondataTable").css({"overflow":"auto","width":"100%"});
});
</script>
</head>
<body>
	<div class="body-box-form">
		<div class="panel panel-system">
			<div class="panel-body">
				<div class="btn-group-xs">
					<button type="button" class="btn btn-system "
						onclick="closeWin(false)">关闭</button>
				</div>
			</div>
		</div>
		<div class="panel panel-info" style="display: none">
			<div class="panel-body">
				<form role="form" class="form-search">
					<ul class="ul-form">
						<li><label>仓库名称</label> <input type="hidden" id="whCode"
							name="whCode" value="${wareHouse.code}" /> <input type="text"
							id="desc1" name="desc1" value="${wareHouse.desc1}" />
						</li>

						<li><label> 过期 </label> <input type="checkbox">
						</li>
					</ul>
				</form>
			</div>
		</div>
		<!--标签页内容 -->
		<div class="container-fluid">
			<ul class="nav nav-tabs">
				<li class="active"><a href="#menu1" data-toggle="tab">仓库余额</a>
				</li>
				<li><a href="#menu2" data-toggle="tab">仓库变动明细</a></li>
			</ul>
			<div class="tab-content">
				<div id="menu1" class="tab-pane fade in active">

					<form role="form" class="form-search" id="tab1_page_form"
						method="post">
						<input type="hidden" name="jsonString" value="" />
						<ul class="ul-form">
							<li><label>查询条件</label> <select name="cx"
								onchange="cxtj(this)">
									<option value="itCode">产品编号</option>
									<option value="descr">产品名称</option>
							</select>
							</li>
							<li><label></label> <input type="text" id="itCode"
								name="itCode" /> <input type="text" id="descr" name="descr"
								style="display: none" />
							</li>
							<li><label>材料类型1</label> <select id="itemType1"
								name="itemType1"></select>
							</li>
							<li><label>材料类型2</label> <select id="itemType2"
								name="itemType2"></select>
							</li>
							<li>
								<button type="button" class="btn btn-system btn-sm"
									onclick="goto_query('itemWHBaldataTable','tab1_page_form');">查询</button>
								<button type="button" class="btn btn-system btn-sm"
									onclick="clearForm('tab1_page_form');">清空</button>
							</li>
						</ul>
					</form>
					<div class="btn-panel">
						<div class="btn-group-sm">
							<button type="button" class="btn btn-system " onclick="view()">可用库存分析</button>
							<button type="button" class="btn btn-system "
								onclick="doItemWHBalExcel()">导出excel</button>
						</div>
					</div>
					<div id="content-list">
						<table id="itemWHBaldataTable"></table>
						<div id="itemWHBaldataTablePager"></div>
					</div>
				</div>
				<div id="menu2" class="tab-pane fade ">

					<form role="form" class="form-search" method="post"
						id="tab2_page_form">
						<input type="hidden" name="jsonString" value="" />
						<ul class="ul-form">
							<li><label>查询条件</label> <select name="cx"
								onchange="cxtj(this)">
									<option value="itCode2">产品编号</option>
									<option value="descr2">产品名称</option>
									<option value="document">档案号</option>
							</select>
							</li>
							<li><label></label> <input type="text" id="itCode2"
								name="itCode" /> <input type="text" id="descr2" name="descr"
								style="display: none" /> <input type="text" id="document"
								name="document" style="display: none" />
							</li>
							<li><label>材料类型1</label> <select id="itemType12"
								name="itemType1"></select>
							</li>
							<li><label>材料类型2</label> <select id="itemType22"
								name="itemType2"></select>
							</li>
							<li><label>材料类型3</label> <select id="itemType32"
								name="itemType3"></select>
							</li>
							<li><label>时间段</label> <input type="text" id="dateFrom"
								name="dateFrom" class="i-date" style="width:160px;"
								onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
								value="<fmt:formatDate value='${itemApp.date}' pattern='yyyy-MM-dd'/>" />
							</li>
							<li><label>到</label> <input type="text" id="dateTo"
								name="dateTo" class="i-date" style="width:160px;"
								onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
								value="<fmt:formatDate value='${itemApp.date}' pattern='yyyy-MM-dd'/>" />
							</li>
							<li>
								<button type="button" class="btn btn-system btn-sm"
									onclick="goto_query('itemTransactiondataTable','tab2_page_form');">查询</button>
								<button type="button" class="btn btn-system btn-sm"
									onclick="clearForm('tab2_page_form');">清空</button>
							</li>
						</ul>
					</form>
					<div class="btn-panel">
						<div class="btn-group-sm">
							<button type="button" class="btn btn-system "
								onclick="doItemTransactionExcel()">导出excel</button>
						</div>
					</div>
					<div id="content-list2">
						<table id="itemTransactiondataTable"></table>
						<div id="itemTransactiondataTablePager"></div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>

