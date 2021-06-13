<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<title>材料完工分析</title>
<%@ include file="/commons/jsp/common.jsp" %>
<script type="text/javascript"> 
function goto_query(){
	if($.trim($("#itemType1").val())==""){
		art.dialog({
			content: "请选择材料类型1"
		});
		return false;
	} 
	if($.trim($("#isServiceItem").val())==""){
		art.dialog({
			content: "请选择是否服务性产品"
		});
		return false;
	} 
	if ($.trim($("#address").val())==''){	
		if($.trim($("#dateFrom").val())==""){
			art.dialog({
				content: "统计开始日期不能为空"
			});
			return false;
		} 
		if($.trim($("#dateTo").val())==""){
			art.dialog({
				content: "统计结束日期不能为空"
			});
			return false;
		}
	    var dateStart = Date.parse($.trim($("#dateFrom").val()));
	    var dateEnd = Date.parse($.trim($("#dateTo").val()));
	    if(dateStart>dateEnd){
	   		art.dialog({content: "统计开始日期不能大于结束日期",width: 200});
	 		return false;
	    }
	}
	$("#dataTable").jqGrid("setGridParam",{url:"${ctx}/admin/itemCheckAnalysis/goJqGrid",datatype:'json',
		  postData:$("#page_form").jsonForm(),page:1,fromServer: true}
	).trigger("reloadGrid");
    /*
	if ($.trim($("#itemType1").val())=="ZC" && $.trim($("#statistcsMethod").val())=="1") {
		$("#dataTable").jqGrid("setGroupHeaders", {
			useColSpanStyle: true, //true:和并列，false:不和并列
			groupHeaders:[
				{startColumnName: "planfee", numberOfColumns: 11, titleText: "主材"},
				{startColumnName: "planfee_serv", numberOfColumns: 11, titleText: "服务性产品"},
			]
		});
	}*/
	
}				
/**初始化表格*/
$(function(){
	Global.LinkSelect.initSelect("${ctx}/admin/item/itemTypeByAuthority","itemType1"); 
    var optionSelect=$("#itemType1 option");
    var sValue="";
    optionSelect.each(function (i,e) {
    	sValue= $(e).text().replace(/[^a-z]+/ig,"");
        if(sValue!="ZC" &&sValue!="RZ"){
            $(this).hide();
        }
        if(sValue=="RZ"){
            $(this).attr("selected","selected");
        }   
    });
	var postData=$("#page_form").jsonForm();
    //初始化表格
	Global.JqGrid.initJqGrid("dataTable",{
		postData: postData,
		height:$(document).height()-$("#content-list").offset().top-70,
		colModel : [
			{name: "custcode", index: "custcode", width: 70, label: "客户编号", sortable: true, align: "left"},
		 	{name: "documentno", index: "documentno", width: 70, label: "档案编号", sortable: true, align: "left"},
			{name: "address", index: "address", width: 150, label: "楼盘", sortable: true, align: "left"},
			{name: "custcheckdate", index: "custcheckdate", width: 90, label: "客户结算时间", sortable: true, align: "left", formatter: formatDate},
			{name: "custtypedescr", index: "custtypedescr", width: 80, label: "客户类型", sortable: true, align: "left"},
			{name: "area", index: "area", width: 60, label: "面积", sortable: true, align: "right", sum: true},
			{name: "m",index : "m",width : 70,label:"结算月份",sortable : true,align : "left",hidden:true},
			{name: "itemtype12descr",index : "itemtype12descr",width : 70,label:"材料分类",sortable : true,align : "left",hidden:true},
			{name: "planfee", index: "planfee", width: 70, label: "预算", sortable: true, align: "right", sum: true},
			{name: "chgfee", index: "chgfee", width: 70, label: "增减项", sortable: true, align: "right", sum: true},
			{name: "lineamount", index: "lineamount", width: 70, label: "结算", sortable: true, align: "right", sum: true},
			{name: "itemcost", index: "itemcost", width: 70, label: "材料成本", sortable: true, align: "right", sum: true},
			{name: "laborfee", index: "laborfee", width: 70, label: "人工费用", sortable: true, align: "right", sum: true},
			{name: "commiamount", index: "commiamount", width: 70, label: "提成", sortable: true, align: "right", sum: true},
			{name: "costtmp", index: "costtmp", width: 70, label: "成本小计", sortable: true, align: "right", sum: true},
			{name: "profit", index: "profit", width: 120, label: "毛利（扣完提成）", sortable: true, align: "right", sum: true},
			{name: "proper", index: "proper", width: 120, label: "毛利率（未扣提成）", sortable: true, align: "right"},
			{name: "profitper", index: "profitper", width: 120, label: "毛利率（扣完提成）", sortable: true, align: "right", sum: true},
			{name: "profitarea", index: "profitarea", width: 150, label: "单位毛利（扣完提成）", sortable: true, align: "right"},
			
			/*	
			{name: "planfee_serv", index: "planfee_serv", width: 70, label: "预算",excelname: "服务性预算", sortable: true, align: "right", sum: true},
			{name: "chgfee_serv", index: "chgfee_serv", width: 70, label: "增减项",excelname: "服务性增减项", sortable: true, align: "right", sum: true},
			{name: "lineamount_serv", index: "lineamount_serv", width: 70, label: "结算",excelname: "服务性结算", sortable: true, align: "right", sum: true},
			{name: "itemcost_serv", index: "itemcost_serv", width: 70, label: "材料成本",excelname: "服务性材料成本", sortable: true, align: "right", sum: true},
			{name: "laborfee_serv", index: "laborfee_serv", width: 70, label: "人工费用",excelname: "服务性人工费用", sortable: true, align: "right", sum: true},
			{name: "commiamount_serv", index: "commiamount_serv", width: 70, label: "提成",excelname: "服务性提成", sortable: true, align: "right", sum: true},
			{name: "costtmp_serv", index: "costtmp_serv", width: 70, label: "成本小计",excelname: "服务性成本小计", sortable: true, align: "right", sum: true},
			{name: "profit_serv", index: "profit_serv", width: 120, label: "毛利（扣完提成）",excelname: "服务性毛利（扣完提成）", sortable: true, align: "right", sum: true},
			{name: "proper_serv", index: "proper_serv", width: 120, label: "毛利率（未扣提成）",excelname: "服务性毛利率（未扣提成）", sortable: true, align: "right"},
			{name: "profitper_serv", index: "profitper_serv", width: 120, label: "毛利率（扣完提成）",excelname: "服务性毛利率（扣完提成）", sortable: true, align: "right", sum: true},
			{name: "profitarea_serv", index: "profitarea_serv", width: 150, label: "单位毛利（扣完提成）",excelname: "服务性单位毛利（扣完提成）", sortable: true, align: "right"},
			*/
			{name: "planfee_serv", index: "planfee_serv", width: 85, label: "服务性预算",excelname: "服务性预算", sortable: true, align: "right", sum: true},
			{name: "chgfee_serv", index: "chgfee_serv", width: 85, label: "服务性增减项",excelname: "服务性增减项", sortable: true, align: "right", sum: true},
			{name: "lineamount_serv", index: "lineamount_serv", width: 85, label: "服务性结算",excelname: "服务性结算", sortable: true, align: "right", sum: true},
			{name: "itemcost_serv", index: "itemcost_serv", width: 106, label: "服务性材料成本",excelname: "服务性材料成本", sortable: true, align: "right", sum: true},
			{name: "laborfee_serv", index: "laborfee_serv", width: 106, label: "服务性人工费用",excelname: "服务性人工费用", sortable: true, align: "right", sum: true},
			{name: "commiamount_serv", index: "commiamount_serv", width: 85, label: "服务性提成",excelname: "服务性提成", sortable: true, align: "right", sum: true},
			{name: "costtmp_serv", index: "costtmp_serv", width: 116, label: "服务性成本小计",excelname: "服务性成本小计", sortable: true, align: "right", sum: true},
			{name: "profit_serv", index: "profit_serv", width: 130, label: "服务性毛利（扣完提成）",excelname: "服务性毛利（扣完提成）", sortable: true, align: "right", sum: true},
			{name: "proper_serv", index: "proper_serv", width: 130, label: "服务性毛利率（未扣提成）",excelname: "服务性毛利率（未扣提成）", sortable: true, align: "right"},
			{name: "profitper_serv", index: "profitper_serv", width: 130, label: "服务性毛利率（扣完提成）",excelname: "服务性毛利率（扣完提成）", sortable: true, align: "right", sum: true},
			{name: "profitarea_serv", index: "profitarea_serv", width: 150, label: "服务性单位毛利（扣完提成）",excelname: "服务性单位毛利（扣完提成）", sortable: true, align: "right"},
			/*
			{name: "planfee", index: "planfee", width: 75, label: "服务性预算", sortable: true, align: "right", sum: true},
			{name: "chgfee", index: "chgfee", width: 90, label: "服务性增减项", sortable: true, align: "right", sum: true},
			{name: "lineamount", index: "lineamount", width: 85, label: "服务性结算", sortable: true, align: "right", sum: true},
			{name: "itemcost", index: "itemcost", width: 105, label: "服务性材料成本", sortable: true, align: "right", sum: true},
			{name: "laborfee", index: "laborfee", width: 105, label: "服务性人工费用", sortable: true, align: "right", sum: true},
			{name: "commiamount", index: "commiamount", width: 85, label: "服务性提成", sortable: true, align: "right", sum: true},
			{name: "costtmp", index: "costtmp", width: 85, label: "服务性成本小计", sortable: true, align: "right", sum: true},
			{name: "profit", index: "profit", width: 120, label: "服务性毛利（扣完提成）", sortable: true, align: "right", sum: true},
			{name: "proper", index: "proper", width: 120, label: "服务性毛利率（未扣提成）", sortable: true, align: "right"},
			{name: "profitper", index: "profitper", width: 120, label: "服务性毛利率（扣完提成）", sortable: true, align: "right", sum: true},
			{name: "profitarea", index: "profitarea", width: 150, label: "服务性单位毛利（扣完提成）", sortable: true, align: "right"},*/
	    ],    
	    loadonce: true,
	    rowNum:100000,  
	    pager :'1',
	  	gridComplete:function(){
	  		setTableColShow();
	        setTableFooterData();
	
        },
	});
	changeType();
	
});

function setTableColShow(){
	  if ($("#statistcsMethod").val() == '1') {
     		$("#dataTable").jqGrid('showCol', "custcode");
     		$("#dataTable").jqGrid('showCol', "documentno");
     		$("#dataTable").jqGrid('showCol', "address");
    	 	$("#dataTable").jqGrid('showCol', "area");
    	 	$("#dataTable").jqGrid('hideCol', "m");
    	 	$("#dataTable").jqGrid('hideCol', "itemtype12descr");			  
		}else if ($("#statistcsMethod").val() == '2') {
			$("#dataTable").jqGrid('hideCol', "custcode");
     		$("#dataTable").jqGrid('hideCol', "documentno");
     		$("#dataTable").jqGrid('hideCol', "address");
     		$("#dataTable").jqGrid('hideCol', "area");
    	 	$("#dataTable").jqGrid('showCol', "m");
    	 	$("#dataTable").jqGrid('hideCol', "itemtype12descr");			
		}else{
			$("#dataTable").jqGrid('hideCol', "custcode");
     		$("#dataTable").jqGrid('hideCol', "documentno");
     		$("#dataTable").jqGrid('hideCol', "address");
     		$("#dataTable").jqGrid('hideCol', "area");
    	 	$("#dataTable").jqGrid('showCol', "m");
    	 	$("#dataTable").jqGrid('showCol', "itemtype12descr");	
		} 
      
      if($("#statistcsMethod").val() == '1' && $("#itemType1").val()=='ZC' ) {
     		$("#dataTable").jqGrid('showCol', "planfee_serv");
     		$("#dataTable").jqGrid('showCol', "chgfee_serv");
     		$("#dataTable").jqGrid('showCol', "lineamount_serv");
    	 	$("#dataTable").jqGrid('showCol', "itemcost_serv");
    	 	$("#dataTable").jqGrid('showCol', "laborfee_serv");
    	 	$("#dataTable").jqGrid('showCol', "commiamount_serv");
     		$("#dataTable").jqGrid('showCol', "costtmp_serv");
     		$("#dataTable").jqGrid('showCol', "profit_serv");
    	 	$("#dataTable").jqGrid('showCol', "proper_serv");
    	 	$("#dataTable").jqGrid('showCol', "profitper_serv");
    	 	$("#dataTable").jqGrid('showCol', "profitarea_serv");	
		}else{
			$("#dataTable").jqGrid('hideCol', "planfee_serv");
     		$("#dataTable").jqGrid('hideCol', "chgfee_serv");
     		$("#dataTable").jqGrid('hideCol', "lineamount_serv");
    	 	$("#dataTable").jqGrid('hideCol', "itemcost_serv");
    	 	$("#dataTable").jqGrid('hideCol', "laborfee_serv");
    	 	$("#dataTable").jqGrid('hideCol', "commiamount_serv");
     		$("#dataTable").jqGrid('hideCol', "costtmp_serv");
     		$("#dataTable").jqGrid('hideCol', "profit_serv");
    	 	$("#dataTable").jqGrid('hideCol', "proper_serv");
    	 	$("#dataTable").jqGrid('hideCol', "profitper_serv");
    	 	$("#dataTable").jqGrid('hideCol', "profitarea_serv");
    	 	$("#dataTable").jqGrid('hideCol', "profitper");	
		}
}


function setTableFooterData(){
	var lineamount = parseFloat($("#dataTable").getCol('lineamount', false, 'sum'));
    var itemcost = parseFloat($("#dataTable").getCol('itemcost', false, 'sum'));
    var laborfee = parseFloat($("#dataTable").getCol('laborfee', false, 'sum'));
    var proper=0
    if(lineamount!=0){
    	proper = myRound((lineamount-itemcost-laborfee)/lineamount*100, 2);
    }
       
    $("#dataTable").footerData('set', {"proper": proper+"%"});

    var area = parseFloat($("#dataTable").getCol('area', false, 'sum'));
    var profit = parseFloat($("#dataTable").getCol('profit', false, 'sum'));
    var profitarea=0
    if(area!=0){
    	profitarea = myRound(profit/area, 4);
    }
    $("#dataTable").footerData('set', {"profitarea": profitarea});

    if($("#statistcsMethod").val() == '1' && $("#itemType1").val()=='ZC' ) {
   	    var commiamount = parseFloat($("#dataTable").getCol('commiamount', false, 'sum'));
   	    var profitper=0
   	    if(lineamount!=0){
   		   profitper = myRound((lineamount-itemcost-laborfee-commiamount)/lineamount*100, 2);
   	    }
   	    
	   	var lineamount_serv = parseFloat($("#dataTable").getCol('lineamount_serv', false, 'sum'));
	    var itemcost_serv = parseFloat($("#dataTable").getCol('itemcost_serv', false, 'sum'));
	    var laborfee_serv = parseFloat($("#dataTable").getCol('laborfee_serv', false, 'sum'));
	    var proper_serv=0
	    if(lineamount_serv!=0){
	    	proper_serv = myRound((lineamount_serv-itemcost_serv-laborfee_serv)/lineamount_serv*100, 2);
	    }
	       
	    $("#dataTable").footerData('set', {"proper_serv": proper_serv+"%"});
	    
	    var profit_serv = parseFloat($("#dataTable").getCol('profit_serv', false, 'sum'));
	    var profitarea_serv=0
	    if(area!=0){
	    	profitarea_serv = myRound(profit_serv/area, 4);
	    }
	    $("#dataTable").footerData('set', {"profitarea_serv": profitarea_serv});
	    
	    var commiamount_serv = parseFloat($("#dataTable").getCol('commiamount_serv', false, 'sum'));
   	    var profitper_serv=0
   	    if(lineamount_serv!=0){
   	    	profitper_serv = myRound((lineamount_serv-itemcost_serv-laborfee_serv-commiamount_serv)/lineamount_serv*100, 2);
   	    }
   	    $("#dataTable").footerData('set', {"profitper_serv": profitper_serv});
   	    var row = $("#dataTable").jqGrid("footerData", "get");
   	    console.log(JSON.stringify(row));
   	  
    }

}

function changeType(){
	var itemType1=$("#itemType1").val();
	if ($("#statistcsMethod").val()!="1"){
		if(itemType1=="ZC"){
			$("#itemType12RZ").parent().addClass("hidden");
			$("#itemType12ZC").parent().removeClass("hidden");
			$("#isServiceItem").val("0").removeProp("disabled");
		}else{
			$("#itemType12ZC").parent().addClass("hidden");
			$("#itemType12RZ").parent().removeClass("hidden");
			$("#isServiceItem").val("0").prop("disabled",true);
		}
	}
	
}
function changeStatistcs(){
	if ($("#statistcsMethod").val()=="1"){
		$("#address").parent().removeClass("hidden");
		$("#isServiceItem").parent().addClass("hidden");
		$("#itemType12RZ").parent().addClass("hidden");
		$("#itemType12ZC").parent().addClass("hidden")
	}else{
		$("#address").val("");
		$("#address").parent().addClass("hidden");
		$("#isServiceItem").parent().removeClass("hidden");
		changeType();
	}
	
}


</script>
</head>
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form action="" method="post" id="page_form" class="form-search">
				<input type="hidden" name="jsonString" value="" />
				<ul class="ul-form">
					<li>
						<label>统计方式</label> 
						<select id="statistcsMethod" name="statistcsMethod" onchange="changeStatistcs()">
							<option value="1">按楼盘统计</option>
							<option value="2">按月份汇总</option>
							<option value="3">按材料分类汇总</option>
							
						</select>
					</li>
					<li>
						<label>材料类型1</label> 
						<select id="itemType1" name="itemType1" onchange="changeType()"></select>
					</li>
					<li class="hidden">
						<label>服务性产品</label> 
						<house:xtdm id="isServiceItem" dictCode="YESNO" style="width:160px;" value="0"/>
					</li>
                    <li class="hidden"><label>材料分类</label> <house:xtdmMulit id="itemType12RZ" dictCode=""
                              sql="  select code SQL_VALUE_KEY,descr SQL_LABEL_KEY from tItemType12 where  ItemType1='RZ' and Expired='F'"></house:xtdmMulit>
                    </li>
					<li class="hidden"><label>材料分类</label> <house:xtdmMulit id="itemType12ZC" dictCode=""
                              sql="  select code SQL_VALUE_KEY,descr SQL_LABEL_KEY from tItemType12 where ItemType1='ZC' and Expired='F'"></house:xtdmMulit>
                    </li>
					<li>
						<label>客户结算日期从</label> 
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
						<label>楼盘</label> 
						<input id="address" name="address"></select>
					</li>
					<li >
						<label>客户类型</label>
						<house:custTypeMulit id="custType" ></house:custTypeMulit>
					</li>
					<li id="loadMore">
						<button type="button" class="btn btn-sm btn-system " onclick="goto_query();">查询</button>
					</li>
				</ul>
			</form>
		</div>
		<div class="btn-panel">
			<div class="btn-group-sm">
				<house:authorize authCode="ITEMCHECKANALYSIS_EXCEL">    
					<button type="button" class="btn btn-system" onclick="doExcelNow('材料完工分析','dataTable', 'page_form')">导出excel</button>
				</house:authorize>
			</div>
			<div id="content-list">
				<table id="dataTable"></table>
			</div>
		</div>
	</div>
</body>
</html>


