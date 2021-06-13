<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>主材管家工作量明细</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script type="text/javascript">
		var formatoption = {decimalSeparator:".", thousandsSeparator: "", decimalPlaces: 2},
		formatoption2 = {decimalSeparator:".", thousandsSeparator: "", decimalPlaces: 2, suffix: "%"},
		formatoptionDay = {decimalSeparator:".", thousandsSeparator: "", decimalPlaces: 0};
function doExcel(){ 
	if ($("ul li a[href='#tab1']").parent().hasClass("active")){	
		doExcelNow("仓储效率分析_周转率明细","dataTable","page_form");
	}else if  ($("ul li a[href='#tab2']").parent().hasClass("active")){
		doExcelNow("仓储效率分析_发货明细","sendDataTable","page_form");
	}
}
$(function(){ 
	Global.LinkSelect.initSelect("${ctx}/admin/item/itemTypeByAuthority","itemType1","itemType2");
	Global.LinkSelect.setSelect({firstSelect:'itemType1',
								firstValue:"${itemWHBal.itemType1}",
								secondSelect:'itemType2',
								secondValue:"${itemWHBal.itemType2}"
									});
	$("#itemType1").attr("disabled",true);
	var postData=$("#page_form").jsonForm();
	postData['itemType1']=$("#itemType1").val();
	postData['itemType2']=$("#itemType2").val();
	//初始化表格
	Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/logWareEffAnaly/goDetailJqGrid",
		postData:postData, 
		height:500-$("#content-list").offset().top-10,  
        width:1170,  
        rowNum:100000,  
		pager :'1',
		colModel : [
		 	{name: "itcode", index: "itcode", width: 75, label: "材料编号", sortable: true, align: "left"},
			{name: "descr", index: "descr", width: 75, label: "材料名称", sortable: true, align: "left",count:true},
			{name: "qckcamount", index: "qckcamount", width: 75, label: "期初库存", sortable: true, align: "right", sum: true},
			{name: "ckamount", index: "ckamount", width: 75, label: "出库金额", sortable: true, align: "right", sum: true},
			{name: "lkamount", index: "lkamount", width: 75, label: "入库金额", sortable: true, align: "right", sum: true},
			{name: "qmckamount", index: "qmckamount", width: 75, label: "期末库存", sortable: true, align: "right", sum: true},
			{name: "kczzper", index: "kczzper", width: 90, label: "周转率", sortable: true, align: "right",formatter:"number",formatoptions:{decimalPlaces: 1, suffix: "%"}}	
	    ],
	     gridComplete:function(){
             var sumqckcamount=myRound($("#dataTable").getCol('qckcamount',false,'sum'));
             var sumqmckamount=myRound($("#dataTable").getCol('qmckamount',false,'sum'));
           	 var sumckamount=myRound($("#dataTable").getCol('ckamount',false,'sum')); 
             if((sumqckcamount+sumqmckamount)!=0){
             	sumkczzper=myRound(sumckamount*100/((sumqckcamount+sumqmckamount)/2),1);
             	$("#dataTable").footerData('set', {'kczzper': sumkczzper});	
             } ;	
 		} 
	});	
	Global.JqGrid.initJqGrid("sendDataTable",{
		url:"${ctx}/admin/logWareEffAnaly/goSendDetailJqGrid",
		postData:postData,
		height:500-$("#content-list").offset().top-10,
	    width:1170,  
        rowNum:100000,  
		pager :'1',
		colModel : [
		 	 {name : 'no',index :'no',width : 80,label:'领料单号',sortable : true,align : "left",count: true},
		 	 {name : 'confirmdate',index :'confirmdate',width : 100,label:'审核日期',sortable : true,align : "left",formatter: formatTime},
		 	 {name : 'senddate',index :'senddate',width : 100,label:'发货日期',sortable : true,align : "left",formatter: formatTime},
		 	 {name : 'delayresondescr',index : 'delayresondescr', width: 90,label:'延误原因',sortable : true,align : "left"},
		 	 {name : 'delayremark',index: 'delayremark',width : 150,label:'延误说明',sortable : true,align : "left"},
			 {name : 'issendontime',index:'issendontime',width : 80,label:'是否及时',sortable : true,align : "left"},
			 {name : "psamount", index : "psamount", width : 80, label : "配送费用", sortable : true, align : "right", sum : true},
			 {name : "zxamount", index : "zxamount", width : 80, label : "装卸费用", sortable : true, align : "right", sum : true},
			 {name : "gdckamount", index : "gdckamount", width : 100, label : "实际出库金额", sortable : true, align : "right", sum : true},
	    ],
	    loadComplete: function(){
			refreshSumAndAvg();
		}
	});      
});
//刷新sum并取decimalPlaces位小数
function refreshSum(colModel_name, decimalPlaces) {
	decimalPlaces = decimalPlaces===undefined?2:decimalPlaces;
	var colModel_name_sum = myRound($("#sendDataTable").getCol(colModel_name,false,"sum"), decimalPlaces);
	var sumObj = {}; //json先要用{}定义，再传参
	sumObj[colModel_name] = colModel_name_sum;//要用参数作为名字时，用[]
	$("#sendDataTable").footerData("set", sumObj);
}
function refreshSumAndAvg() {
	refreshSum("psamount");refreshSum("zxamount");refreshSum("gdckamount");
}
function goto_query(){
	var postData=$("#page_form").jsonForm();
	postData['itemType2']=$("#itemType2").val();
	$("#sendDataTable").jqGrid(
		"setGridParam",{
			datatype:"json",
			postData:postData,
			page:1,
			url:"${ctx}/admin/logWareEffAnaly/goSendDetailJqGrid"
		}
	).trigger("reloadGrid");
}
</script>
</head>
<body>
	<div class="body-box-form">
		<div class="query-form" hidden>
			<form action="" method="post" id="page_form" >
				<input type="hidden" name="jsonString" value="" />
				<input  id="whCode" name="whCode" value="${itemWHBal.whCode}" />
				<input  id="dateFrom" name="dateFrom"  value="<fmt:formatDate value='${itemWHBal.dateFrom}' pattern='yyyy-MM-dd'/>" />
				<input  id="dateTo" name="dateTo"  value="<fmt:formatDate value='${itemWHBal.dateTo}' pattern='yyyy-MM-dd'/>" />
			</form>
		</div>
		<div class="panel panel-system" >
			    <div class="panel-body" >
			    	<div class="btn-group-xs" >
	               		<house:authorize authCode="LOGWAREEFFANALY_EXCEL">
	     					<button type="button" class="btn btn-system "   onclick="doExcel()">导出excel</button>
						</house:authorize>
						<button type="button" class="btn btn-system" onclick="closeWin(false)">关闭</button>
					</div>
				</div>
		  </div>
		<div  class="container-fluid" >  
		     <ul class="nav nav-tabs" >
		        <li id="tab1_li" class="active"><a href="#tab1" data-toggle="tab" >周转率明细</a></li>  
		    	<li id="tab2_li"  class=""><a href="#tab2" data-toggle="tab" >发货明细</a></li>
		    </ul>  
		    <div class="tab-content">  
				<div id="tab1"  class="tab-pane fade in active"> 
					<div id="content-list">
						<table id= "dataTable" ></table>
						<div id="dataTablePager"></div>
					</div> 
				</div> 
				<div id="tab2"  class="tab-pane fade " > 
					<div class="query-form">
						<form action="" method="post" id="page_form" class="form-search">
							<input type="hidden" name="jsonString" value="" /> <input type="hidden" id="expired" name="expired"
								 />
							<ul class="ul-form">
								<li>
									<label>材料类型1</label> <select id="itemType1" name="itemType1"></select>
								</li>
								<li>
									<label>材料类型2</label> <select id="itemType2" name="itemType2"></select>
								</li>
								<li><label></label> 
									<button type="button" class="btn btn-sm btn-system " onclick="goto_query();">查询</button>
									<button type="button" class="btn btn-sm btn-system " onclick="clearForm();">清空</button>
								</li>
							</ul>
						</form>
					</div>
					<div id="content-list2">
					  <table id= "sendDataTable" ></table>
					   <div id="sendDataTablePager"></div>
				   </div> 
				</div> 
			</div>	
		</div>
	</div>
</body>
</html>

