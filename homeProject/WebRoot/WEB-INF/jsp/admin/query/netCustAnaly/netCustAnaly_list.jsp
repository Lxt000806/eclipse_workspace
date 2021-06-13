<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<!DOCTYPE html>
<html>
<head>
	<title>网络客服派单分析</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<style type="text/css">
		.frozen-div .jqg-third-row-header {
			height: 53px;
		}
	</style>
<script type="text/javascript"> 
$(function(){
	var postData=$("#page_form").jsonForm();
	postData.custType="${customer.custType}";
	var gridOption_kf ={
		height:$(document).height()-$("#content-list").offset().top-100,
		styleUI: 'Bootstrap', 
		postData:postData,
		url:"${ctx}/admin/netCustAnaly/goJqGrid",
		colModel : [
			{name: "CrtCzyDescr", index: "CrtCzyDescr", width: 80, label: "客服", sortable: true, align: "left",cellattr: function(rowId, value, rowObject, colModel, arrData) {return 'id=\'CrtCzyDescr' + rowId + "\'";},frozen:true,},
			{name: "BusinessManDescr", index: "BusinessManDescr", width: 77, label: "家装顾问", sortable: true, align: "left",frozen:true,},
			{name: "DispatchNum", index: "DispatchNum", width: 77, label: "派单量", sortable: true, align: "right", sum: true},
			{name: "ValidOrderNum_month", index: "ValidOrderNum_month", width: 87, label: "有效派单量", sortable: true, align: "right", sum: true,excelLabel:"当月有效派单量"},
			{name: "VisitNum_month", index: "VisitNum_month", width: 87, label: "来客量", sortable: true, align: "right", sum: true,excelLabel:"当月来客量"},
			{name: "VisitRate_month", index: "VisitRate_month", width: 90, label: "来客率", sortable: true, align: "right",formatter:'number',formatoptions:{decimalSeparator:".", thousandsSeparator: " ", decimalPlaces: 1, suffix: "%"},excelLabel:"当月来客率"},
			{name: "SetNum_month", index: "SetNum_month", width: 87, label: "订单量", sortable: true, align: "right", sum: true,excelLabel:"当月订单量"},
			{name: "SetRate_month", index: "SetRate_month", width: 87, label: "下定率", sortable: true, align: "right",formatter:'number',formatoptions:{decimalSeparator:".", thousandsSeparator: " ", decimalPlaces: 1, suffix: "%"},excelLabel:"当月下定率"},
			{name: "ConstructNum_month", index: "ConstructNum_month", width: 87, label: "签单量", sortable: true, align: "right", sum: true,excelLabel:"当月签单量"},
			{name: "ConstructFee_month", index: "ConstructFee_month", width: 87, label: "合同额", sortable: true, align: "right", sum: true,excelLabel:"当月合同额"},
			{name: "TransRate_month", index: "TransRate_month", width: 80, label: "转单率", sortable: true, align: "right",formatter:'number',formatoptions:{decimalSeparator:".", thousandsSeparator: " ", decimalPlaces: 1, suffix: "%"},excelLabel:"当月转单率"},
			{name: "VisitNum_history", index: "VisitNum_history", width:90, label: "来客量", sortable: true, align: "right", sum: true,excelLabel:"历史来客量"},
			{name: "SetNum_history", index: "SetNum_history", width: 87, label: "订单量", sortable: true, align: "right", sum: true,excelLabel:"历史订单量"},
			{name: "SetRate_history", index: "SetRate_history", width: 93, label: "下定率", sortable: true, align: "right",formatter:'number',formatoptions:{decimalSeparator:".", thousandsSeparator: " ", decimalPlaces: 1, suffix: "%"},excelLabel:"历史下定率"},
			{name: "ConstructNum_history", index: "ConstructNum_history", width: 85, label: "签单量", sortable: true, align: "right", sum: true,excelLabel:"历史签单量"},
			{name: "ContractFee_history", index: "ContractFee_history", width: 90, label: "合同额", sortable: true, align: "right", sum: true,excelLabel:"历史合同额"},
			{name: "VisitNum_sum", index: "VisitNum_sum", width: 85, label: "总来客", sortable: true, align: "right", sum: true},
			{name: "VisitRate_sum", index: "VisitRate_sum", width: 90, label: "总来客率", sortable: true, align: "right",formatter:'number',formatoptions:{decimalSeparator:".", thousandsSeparator: " ", decimalPlaces: 1, suffix: "%"}},
			{name: "SetNum_sum", index: "SetNum_sum", width: 95, label: "总订单量", sortable: true, align: "right", sum: true},
			{name: "SetRate_sum", index: "SetRate_sum", width: 85, label: "总下定率", sortable: true, align: "right",formatter:'number',formatoptions:{decimalSeparator:".", thousandsSeparator: " ", decimalPlaces: 1, suffix: "%"}},
			{name: "ConstructNum_sum", index: "ConstructNum_sum", width: 95, label: "总签单量", sortable: true, align: "right", sum: true},
			{name: "ContractFee_sum", index: "ContractFee_sum", width: 87, label: "总合同额", sortable: true, align: "right", sum: true},
			{name: "TransRate_sum", index: "TransRate_sum", width: 90, label: "总转单率", sortable: true, align: "right",formatter:'number',formatoptions:{decimalSeparator:".", thousandsSeparator: " ", decimalPlaces: 1, suffix: "%"}},
			{name: "BusinessMan", index: "BusinessMan", width: 80, label: "家装顾问", sortable: true, align: "left",hidden:true},
			{name: "CrtCzy", index: "CrtCzy", width: 80, label: "客服", sortable: true, align: "left",hidden:true}
		],
		gridComplete:function(){
       		Merger("dataTable","CrtCzyDescr");
			countRate();
			$(".ui-jqgrid-bdiv").scrollTop(0);
        	frozenHeightReset("dataTable");
        }, 
        beforeSelectRow:function(rowid, e){
 	      	$("#dataTable_frozen tbody tr td[id^=CrtCzyDescr]").css({"background":"white","color":"#333"});
        	$("#dataTable_frozen tbody tr #CrtCzyDescr"+rowid).css({"background":"#198EDE","color":"white"});
        },
		loadonce: true,
		rowNum:1000000,
	};
	var gridOption_wlqd ={
		height:$(document).height()-$("#content-list").offset().top-100,
		styleUI: 'Bootstrap', 
		url:"${ctx}/admin/netCustAnaly/goJqGrid",
		colModel : [
			{name: "NetChanelDescr", index: "NetChanelDescr", width: 80, label: "渠道", sortable: true, align: "left",frozen:true,},
			{name: "ValidOrderNum_month", index: "ValidOrderNum_month", width: 87, label: "有效派单量", sortable: true, align: "right", sum: true,excelLabel:"当月有效派单量"},
			{name: "VisitNum_month", index: "VisitNum_month", width: 87, label: "来客量", sortable: true, align: "right", sum: true,excelLabel:"当月-来客量"},
			{name: "VisitRate_month", index: "VisitRate_month", width: 90, label: "来客率", sortable: true, align: "right",formatter:'number',formatoptions:{decimalSeparator:".", thousandsSeparator: " ", decimalPlaces: 1, suffix: "%"},excelLabel:"当月来客率"},
			{name: "SetNum_month", index: "SetNum_month", width: 87, label: "订单量", sortable: true, align: "right", sum: true,excelLabel:"当月-订单量"},
			{name: "SetRate_month", index: "SetRate_month", width: 87, label: "下定率", sortable: true, align: "right",formatter:'number',formatoptions:{decimalSeparator:".", thousandsSeparator: " ", decimalPlaces: 1, suffix: "%"},excelLabel:"当月下定率"},
			{name: "ConstructNum_month", index: "ConstructNum_month", width: 87, label: "签单量", sortable: true, align: "right", sum: true,excelLabel:"当月签单量"},
			{name: "ConstructFee_month", index: "ConstructFee_month", width: 87, label: "合同额", sortable: true, align: "right", sum: true,excelLabel:"当月合同额"},
			{name: "TransRate_month", index: "TransRate_month", width: 80, label: "转单率", sortable: true, align: "right",formatter:'number',formatoptions:{decimalSeparator:".", thousandsSeparator: " ", decimalPlaces: 1, suffix: "%"},excelLabel:"当月转单率"},
			{name: "VisitNum_history", index: "VisitNum_history", width:90, label: "来客量", sortable: true, align: "right", sum: true,excelLabel:"历史来客量"},
			{name: "SetNum_history", index: "SetNum_history", width: 87, label: "订单量", sortable: true, align: "right", sum: true,excelLabel:"历史订单量"},
			{name: "SetRate_history", index: "SetRate_history", width: 93, label: "下定率", sortable: true, align: "right",formatter:'number',formatoptions:{decimalSeparator:".", thousandsSeparator: " ", decimalPlaces: 1, suffix: "%"},excelLabel:"历史下定率"},
			{name: "ConstructNum_history", index: "ConstructNum_history", width: 85, label: "签单量", sortable: true, align: "right", sum: true,excelLabel:"历史签单量"},
			{name: "ContractFee_history", index: "ContractFee_history", width: 90, label: "合同额", sortable: true, align: "right", sum: true,excelLabel:"历史合同额"},
			{name: "VisitNum_sum", index: "VisitNum_sum", width: 85, label: "总来客", sortable: true, align: "right", sum: true},
			{name: "VisitRate_sum", index: "VisitRate_sum", width: 90, label: "总来客率", sortable: true, align: "right",formatter:'number',formatoptions:{decimalSeparator:".", thousandsSeparator: " ", decimalPlaces: 1, suffix: "%"}},
			{name: "SetNum_sum", index: "SetNum_sum", width: 95, label: "总订单量", sortable: true, align: "right", sum: true},
			{name: "SetRate_sum", index: "SetRate_sum", width: 85, label: "总下定率", sortable: true, align: "right",formatter:'number',formatoptions:{decimalSeparator:".", thousandsSeparator: " ", decimalPlaces: 1, suffix: "%"}},
			{name: "ConstructNum_sum", index: "ConstructNum_sum", width: 95, label: "总签单量", sortable: true, align: "right", sum: true},
			{name: "ContractFee_sum", index: "ContractFee_sum", width: 87, label: "总合同额", sortable: true, align: "right", sum: true},
			{name: "TransRate_sum", index: "TransRate_sum", width: 90, label: "总转单率", sortable: true, align: "right",formatter:'number',formatoptions:{decimalSeparator:".", thousandsSeparator: " ", decimalPlaces: 1, suffix: "%"}},
			{name: "NetChanel", index: "NetChanel", width: 80, label: "渠道", sortable: true, align: "left",hidden:true,},
		],
		gridComplete:function(){
			$(".ui-jqgrid-bdiv").scrollTop(0);
        	frozenHeightReset("dataTable");
			countRate();
        }, 
		loadonce: true,
		rowNum:1000000,
	};
	var gridOption_wlqd_jzgw ={
		height:$(document).height()-$("#content-list").offset().top-100,
		styleUI: 'Bootstrap', 
		url:"${ctx}/admin/netCustAnaly/goJqGrid",
		colModel : [
			{name: "BusinessManDescr", index: "BusinessManDescr", width: 80, label: "家装顾问", sortable: true, align: "left",cellattr: function(rowId, value, rowObject, colModel, arrData) {return 'id=\'BusinessManDescr' + rowId + "\'";}},
			{name: "NetChanelDescr", index: "NetChanelDescr", width: 80, label: "渠道", sortable: true, align: "left",frozen:true},
			{name: "DispatchNum", index: "DispatchNum", width: 87, label: "派单量", sortable: true, align: "right", sum: true},
			{name: "VisitNum", index: "VisitNum", width: 87, label: "来客量", sortable: true, align: "right", sum: true},
			{name: "SetNum", index: "SetNum", width: 87, label: "订单量", sortable: true, align: "right", sum: true},
			{name: "ConstructNum", index: "ConstructNum", width: 87, label: "签单量", sortable: true, align: "right", sum: true},
			{name: "ContractFee", index: "ContractFee", width: 87, label: "合同额", sortable: true, align: "right", sum: true},
			{name: "VisitRate", index: "VisitRate", width: 90, label: "来客率", sortable: true, align: "right",formatter:'number',formatoptions:{decimalSeparator:".", thousandsSeparator: " ", decimalPlaces: 1, suffix: "%"}},
			{name: "SetRate", index: "SetRate", width: 87, label: "下定率", sortable: true, align: "right",formatter:'number',formatoptions:{decimalSeparator:".", thousandsSeparator: " ", decimalPlaces: 1, suffix: "%"}},
			{name: "TransRate", index: "TransRate", width: 80, label: "转单率", sortable: true, align: "right",formatter:'number',formatoptions:{decimalSeparator:".", thousandsSeparator: " ", decimalPlaces: 1, suffix: "%"}},
			{name: "NetChanel", index: "NetChanel", width: 80, label: "渠道", sortable: true, align: "left",hidden:true},
			{name: "BusinessMan", index: "BusinessMan", width: 80, label: "家装顾问", sortable: true, align: "left",hidden:true}
		],
		gridComplete:function(){
       		Merger("dataTable","BusinessManDescr");
			countRate_jzgw();
        }, 
        beforeSelectRow:function(rowid, e){
 	      	$("#dataTable tbody tr td[id^=BusinessManDescr]").css({"background":"white","color":"#333"});
        	$("#dataTable tbody tr #BusinessManDescr"+rowid).css({"background":"#198EDE","color":"white"});
        },
		loadonce: true,
		rowNum:1000000,
	};
    Global.JqGrid.initJqGrid("dataTable",gridOption_kf);
	$("#dataTable").jqGrid('setGroupHeaders', {
	  	useColSpanStyle: true, 
		groupHeaders:[
				{startColumnName: 'ValidOrderNum_month', numberOfColumns: 8, titleText: '当月转化情况'},
				{startColumnName: 'VisitNum_history', numberOfColumns: 5, titleText: '历史预约转换情况'},
				{startColumnName: 'VisitNum_sum', numberOfColumns: 7, titleText: '合计'},
		],
	});
	$("#dataTable").jqGrid("setFrozenColumns");
	function DiyFmatter (cellvalue, options, rowObject){ 
		if((cellvalue+"").indexOf("%")!=-1){
			return cellvalue;
		} 
	    return myRound(parseFloat(cellvalue*100),1)+"%";
	}

	window.goto_query = function(){
		var dateFrom=new Date($.trim($("#dateFrom").val()));
		var dateTo=new Date($.trim($("#dateTo").val()));
		if(dateFrom>dateTo){
			art.dialog({
				content: "开始日期不能大于结束日期",
			});
			return false;
		}
		
		if($.trim($("#dateFrom").val())==''||$.trim($("#dateTo").val())==''){
			art.dialog({
				content: "时间查询条件不能为空",
			});
			return false;
		}		
		var statistcsMethod=$.trim($("#statistcsMethod").val());
		$.jgrid.gridUnload("dataTable");
		if(statistcsMethod=='1'){//客服
			Global.JqGrid.initJqGrid("dataTable",
				$.extend(gridOption_kf, {
					gridComplete:function(){
			       		Merger("dataTable","CrtCzyDescr");
						countRate();
						$(".ui-jqgrid-bdiv").scrollTop(0);
        				frozenHeightReset("dataTable");
			        }, 
			        beforeSelectRow:function(rowid, e){
			 	      	$("#dataTable_frozen tbody tr td[id^=CrtCzyDescr]").css({"background":"white","color":"#333"});
			        	$("#dataTable_frozen tbody tr #CrtCzyDescr"+rowid).css({"background":"#198EDE","color":"white"});
			        },
			        postData:$("#page_form").jsonForm()
			    })
		    );
			$("#dataTable").jqGrid('setGroupHeaders', {
			  	useColSpanStyle: true, 
				groupHeaders:[
						{startColumnName: 'ValidOrderNum_month', numberOfColumns: 8, titleText: '当月转化情况'},
						{startColumnName: 'VisitNum_history', numberOfColumns: 5, titleText: '历史预约转换情况'},
						{startColumnName: 'VisitNum_sum', numberOfColumns: 7, titleText: '合计'},
				],
			});
		}else if(statistcsMethod=='2'){//网络渠道
			Global.JqGrid.initJqGrid("dataTable",
					$.extend(gridOption_wlqd,{
						postData:$("#page_form").jsonForm(),
						gridComplete:function(){
						$(".ui-jqgrid-bdiv").scrollTop(0);
			        	frozenHeightReset("dataTable");
			        	countRate();
			        }, 
        		})
        	);
			$("#dataTable").jqGrid('setGroupHeaders', {
			  	useColSpanStyle: true, 
				groupHeaders:[
						{startColumnName: 'ValidOrderNum_month', numberOfColumns: 8, titleText: '当月转化情况'},
						{startColumnName: 'VisitNum_history', numberOfColumns: 5, titleText: '历史预约转换情况'},
						{startColumnName: 'VisitNum_sum', numberOfColumns: 7, titleText: '合计'},
				],
			});
		}else if(statistcsMethod=='3'){//客服--家装顾问
			Global.JqGrid.initJqGrid("dataTable",
				$.extend(gridOption_wlqd_jzgw, {
					gridComplete:function(){
			       		Merger("dataTable","BusinessManDescr");
						countRate_jzgw();
			        }, 
			        beforeSelectRow:function(rowid, e){
			 	      	$("#dataTable tbody tr td[id^=BusinessManDescr]").css({"background":"white","color":"#333"});
			        	$("#dataTable tbody tr #BusinessManDescr"+rowid).css({"background":"#198EDE","color":"white"});
			        },
			        postData:$("#page_form").jsonForm()
			    })
		   );
		}
		$("#dataTable").jqGrid("setFrozenColumns");
	}
});

function doExcel(){
	var url = "${ctx}/admin/netCustAnaly/doExcel";
	doExcelAll(url);
}

//计算合计栏比率
function countRate(){
	var ValidOrderNum_month = parseFloat($("#dataTable").getCol('ValidOrderNum_month', false, 'sum'));
    var VisitNum_month = parseFloat($("#dataTable").getCol('VisitNum_month', false, 'sum'));
    var SetNum_month = parseFloat($("#dataTable").getCol('SetNum_month', false, 'sum'));
    var ConstructNum_month = parseFloat($("#dataTable").getCol('ConstructNum_month', false, 'sum'));
    var VisitNum_history = parseFloat($("#dataTable").getCol('VisitNum_history', false, 'sum'));
    var SetNum_history = parseFloat($("#dataTable").getCol('SetNum_history', false, 'sum'));
    var VisitNum_sum = parseFloat($("#dataTable").getCol('VisitNum_sum', false, 'sum'));
    var SetNum_sum = parseFloat($("#dataTable").getCol('SetNum_sum', false, 'sum'));
    var ConstructNum_sum = parseFloat($("#dataTable").getCol('ConstructNum_sum', false, 'sum'));
 	    
 	var VisitRate_month=ValidOrderNum_month==0?0:myRound(VisitNum_month/ValidOrderNum_month*100,1);
 	var SetRate_month=VisitNum_month==0?0:myRound(SetNum_month/VisitNum_month*100,1);
   	var TransRate_month=SetNum_month==0?0:myRound(ConstructNum_month/SetNum_month*100,1);
 	var SetRate_history=VisitNum_history==0?0:myRound(SetNum_history/VisitNum_history*100,1);
 	var VisitRate_sum=ValidOrderNum_month==0?0:myRound(VisitNum_sum/ValidOrderNum_month*100,1);
 	var SetRate_sum=VisitNum_sum==0?0:myRound(SetNum_sum/VisitNum_sum*100,1);
   	var TransRate_sum=SetNum_sum==0?0:myRound(ConstructNum_sum/SetNum_sum*100,1);
 	
    $("#dataTable").footerData('set', {"VisitRate_month": VisitRate_month});
    $("#dataTable").footerData('set', {"SetRate_month": SetRate_month});
    $("#dataTable").footerData('set', {"TransRate_month": TransRate_month});
    $("#dataTable").footerData('set', {"SetRate_history": SetRate_history});
    $("#dataTable").footerData('set', {"VisitRate_sum": VisitRate_sum});
    $("#dataTable").footerData('set', {"SetRate_sum": SetRate_sum});
    $("#dataTable").footerData('set', {"TransRate_sum": TransRate_sum});
}
function countRate_jzgw(){
	var DispatchNum = parseFloat($("#dataTable").getCol('DispatchNum', false, 'sum'));
  	var VisitNum = parseFloat($("#dataTable").getCol('VisitNum', false, 'sum'));
  	var SetNum = parseFloat($("#dataTable").getCol('SetNum', false, 'sum'));
  	var ConstructNum = parseFloat($("#dataTable").getCol('ConstructNum', false, 'sum'));
  	var ContractFee = parseFloat($("#dataTable").getCol('ContractFee', false, 'sum'));
  	
  	var VisitRate=DispatchNum==0?0:myRound(VisitNum/DispatchNum*100,1);
	var SetRate=VisitNum==0?0:myRound(SetNum/VisitNum*100,1);
 	var TransRate=SetNum==0?0:myRound(ConstructNum/SetNum*100,1);
 	
 	$("#dataTable").footerData('set', {"DispatchNum": DispatchNum/2});
 	$("#dataTable").footerData('set', {"VisitNum": VisitNum/2});
  	$("#dataTable").footerData('set', {"SetNum": SetNum/2});
  	$("#dataTable").footerData('set', {"ConstructNum": ConstructNum/2});
  	$("#dataTable").footerData('set', {"ContractFee": ContractFee/2});
 	
 	$("#dataTable").footerData('set', {"VisitRate": VisitRate});
 	$("#dataTable").footerData('set', {"SetRate": SetRate});
  	$("#dataTable").footerData('set', {"TransRate": TransRate});
}	

function view(){
		var ret = selectDataTableRow();
		var postData = $("#page_form").jsonForm();
		if(ret){
			$.extend(postData, {
				businessMan:ret.BusinessMan,
				netChanel:ret.NetChanel,
				crtCzy:ret.CrtCzy
			});
        	Global.Dialog.showDialog("netCustAnaly",{
        	  title:"网络客户派单分析--查看",
        	  url:"${ctx}/admin/netCustAnaly/goView",
        	  postData:postData,
        	  height: 670,
        	  width: 1200,
        	});
		}else{
			art.dialog({
				content:"请选择一条记录"
			});
		}
	}
</script>
</head>
	<body>
		<div class="body-box-list">
			<div class="query-form">
			  <form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
					<input type="hidden" name="jsonString" value="" />
						<ul class="ul-form">
							<li>
								<label>统计日期从</label>
								<input type="text" id="dateFrom" name="dateFrom" class="i-date" 
										onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" style="width:160px;"
										value="<fmt:formatDate value='${customer.dateFrom }'  pattern='yyyy-MM-dd'/>"/>
							</li>
							<li>
								<label>至</label>
								<input type="text" id="dateTo" name="dateTo" class="i-date" style="width:160px;"
										onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
										value="<fmt:formatDate value='${customer.dateTo }'  pattern='yyyy-MM-dd'/>"/>
							</li>

							<li>
								<label>一级部门</label>
								<house:DictMulitSelect id="department1" dictCode="" sql="select code,desc1 from tDepartment1 where Expired='F'" 
								sqlLableKey="desc1" sqlValueKey="code" selectedValue="${customer.department1 }" onCheck="checkDepartment1()"></house:DictMulitSelect>
							</li>
							<li>
								<label>二级部门</label>
								<house:DictMulitSelect id="department2" dictCode="" sql="select code,desc1 from tDepartment2 where 1=2" 
								sqlLableKey="desc1" sqlValueKey="code" selectedValue="${customer.department2 }" onCheck="checkDepartment2()"></house:DictMulitSelect>
							</li>
							<li>
                            	<label>客户类别</label> 
                            	<house:xtdmMulit id="custKind" dictCode="CUSTCLASS" ></house:xtdmMulit>
                            </li>
                            <li>
								<label>客户类型</label>
								<house:custTypeMulit id="custType"  selectedValue="${customer.custType}"></house:custTypeMulit>
							</li>
							<li>
								<label>统计方式</label>
 								<select id="statistcsMethod" name="statistcsMethod" style="width: 160px;" >
 									<option value="1">网络客服派单</option>
									<option value="2">网络渠道</option>
									<option value="3">网络渠道--家装顾问</option>
 								</select>
							</li>
							<li class="search-group">					
								<button type="button" class="btn btn-sm btn-system " onclick="goto_query();"  >查询</button>
								<button type="button" class="btn btn-sm btn-system " onclick="clearForm();"  >清空</button>
							</li>
						</ul>
				</form>
			</div>
			</div>
			<div class="clear_float"></div>
			<!--query-form-->
			<!-- panelBar -->
			<div class="btn-panel" >
    			 <div class="btn-group-sm"  >
   			  			<house:authorize authCode="NETCUSTANALY_VIEW">
							<button type="button" id="btnview" class="btn btn-system"  onclick="view()">查看</button>
						</house:authorize>
						<button type="button" class="btn btn-system "  onclick="doExcel()" >
							<span>导出excel</span>
						</button>
				 </div>
			</div>
					<!-- panelBar End -->
				<div id="content-list">
					<table id= "dataTable"></table>
				</div> 
	</body>	
</html>
