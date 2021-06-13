<%@ page language="java" import="com.house.framework.commons.utils.PathUtil" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>
<!DOCTYPE html>
<html>
<head>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp"%>
<title>物流仓储效率分析</title>
<%@ include file="/commons/jsp/common.jsp"%>
<script src="${resourceRoot}/pub/component_wareHouse.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
 $(function(){
 	$("#whCode").openComponent_wareHouse();
 	Global.LinkSelect.initSelect("${ctx}/admin/item/itemTypeByAuthority","itemType1","itemType2");
    //初始化表格
	Global.JqGrid.initJqGrid("dataTable",{
		height:$(document).height()-$("#content-list").offset().top-100,
		styleUI: 'Bootstrap',
		rowNum:100000,  
		pager :'1',
		loadonce: true,
		colModel : [
			{name: "itemtype2", index: "itemtype2", width: 75, label: "材料类型2", sortable: true, align: "left",hidden:true},
			{name: "itemtype2descr", index: "itemtype2descr", width: 75, label: "材料类型2", sortable: true, align: "left",count:true},
			{name: "qckcamount", index: "qckcamount", width: 75, label: "期初库存", sortable: true, align: "right", sum: true},
			{name: "ckamount", index: "ckamount", width: 75, label: "出库金额", sortable: true, align: "right", sum: true},
			{name: "lkamount", index: "lkamount", width: 75, label: "入库金额", sortable: true, align: "right", sum: true},
			{name: "qmckamount", index: "qmckamount", width: 75, label: "期末库存", sortable: true, align: "right", sum: true},
			{name: "psamount", index: "psamount", width: 75, label: "配送费用", sortable: true, align: "right", sum: true},
			{name: "zxamount", index: "zxamount", width: 75, label: "装卸费", sortable: true, align: "right", sum: true},
			{name: "gdckamount", index: "gdckamount", width: 120, label: "工地实际出库金额", sortable: true, align: "right", sum: true},
			{name: "psper", index: "psper", width: 110, label: "配送费用占比(%)", sortable: true, align: "right",formatter:"number",formatoptions:{decimalPlaces: 1, suffix: "%"}},
			{name: "apportionfee", index: "apportionfee", width: 75, label: "采购费用", sortable: true, align: "right", sum: true},
			{name: "purlkamount", index: "purlkamount", width: 90, label: "采购入库金额", sortable: true, align: "right", sum: true},
			{name: "apportionfeeper", index: "apportionfeeper", width: 100, label: "采购费用占比(%)", sortable: true, align: "right",formatter:"number",formatoptions:{decimalPlaces: 1, suffix: "%"}},
			{name: "lossamount", index: "lossamount", width: 75, label: "报损金额", sortable: true, align: "right", sum: true},
			{name: "lossper", index: "lossper", width: 75, label: "报损率(%)", sortable: true, align: "right",formatter:"number",formatoptions:{decimalPlaces: 1, suffix: "%"}},
			{name: "kczzper", index: "kczzper", width: 90, label: "周转率", sortable: true, align: "right",formatter:"number",formatoptions:{decimalPlaces: 1, suffix: "%"}},	
			{name: "sendontimeper", index: "sendontimeper", width: 75, label: "发货及时率(%)", sortable: true, align: "right",formatter:"number",formatoptions:{decimalPlaces: 1, suffix: "%"}},
        	{name: "sendontimenum", index: "sendontimenum", width: 75, label: "sendontimenum", sortable: true, align: "right", sum: true,hidden:true},
			{name: "sendnum", index: "sendnum", width: 75, label: "sendnum", sortable: true, align: "right", sum: true,hidden:true},
			
        ],  
         gridComplete:function(){
        	 var sumpsper=0.00,sumapportionfeeper=0.00; sumlossper=0.00,sumkczzper=0,sumsendontimenum=0,sumsendnum=0,sumsendontimeper=0;
		     var sumgdckamount=myRound($("#dataTable").getCol('gdckamount',false,'sum')); 
		     var sumpsamount=myRound($("#dataTable").getCol('psamount',false,'sum'));
		     var sumpurlkamount=myRound($("#dataTable").getCol('purlkamount',false,'sum'));
		     var sumapportionfee=myRound($("#dataTable").getCol('apportionfee',false,'sum'));
		     var sumqckcamount=myRound($("#dataTable").getCol('qckcamount',false,'sum'));
		     var sumqmckamount=myRound($("#dataTable").getCol('qmckamount',false,'sum'));
		     var sumckamount=myRound($("#dataTable").getCol('ckamount',false,'sum'));
		     var sumlossamount=myRound($("#dataTable").getCol('lossamount',false,'sum'));
		     var sumzxamount=myRound($("#dataTable").getCol('zxamount',false,'sum'));
		     var sumsendontimenum=myRound($("#dataTable").getCol('sendontimenum',false,'sum'));
		     var sumsendnum=myRound($("#dataTable").getCol('sendnum',false,'sum'));
             if(sumgdckamount!=0){
             	sumpsper=myRound((sumpsamount+sumzxamount)*100.00/sumgdckamount,1);	
             }
             if(sumpurlkamount!=0){
             	sumapportionfeeper=myRound(sumapportionfee*100.00/sumpurlkamount,1);	
             } 
             if((sumqckcamount+sumpurlkamount)!=0){
             	sumlossper=myRound(sumlossamount*100.00/(sumqckcamount+sumpurlkamount/2),1);	
             } 
             if((sumpurlkamount+sumqmckamount)!=0){
             	sumkczzper=myRound(sumckamount*100/((sumqckcamount+sumqmckamount)/2),1);	
             } 
             if(sumsendnum!=0){
             	sumsendontimeper=myRound(sumsendontimenum*100.00/sumsendnum,1);	
             } 
 			 $("#dataTable").footerData('set', {'psper': sumpsper});
 			 $("#dataTable").footerData('set', {'apportionfeeper': sumapportionfeeper});
 			 $("#dataTable").footerData('set', {'lossper': sumlossper});		
 			 $("#dataTable").footerData('set', {'kczzper': sumkczzper});
 			 $("#dataTable").footerData('set', {'sendontimeper': sumsendontimeper});			
	     },                  
	 });
});
function goto_query(){
	if($.trim($("#whCode").val())==''){
			art.dialog({content: "仓库编号不能为空",width: 200});
			return false;
	} 
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
	$("#dataTable").jqGrid("setGridParam",{url:"${ctx}/admin/logWareEffAnaly/goJqGrid",datatype:'json',postData:$("#page_form").jsonForm(),page:1,fromServer: true}).trigger("reloadGrid");
}  

//导出EXCEL
function doExcel(){
  	doExcelNow("物流仓储效率分析","dataTable","page_form");
}
function clearForm(){
	$("#page_form input[type='text']").val('');
}
function view() {
	var ret = selectDataTableRow();
  		if(!ret){
  			art.dialog({content: "请选择一条记录进行查看！",width: 200});
  			return false;
  		}
  	    var params=$("#page_form").jsonForm();
  	    params.itemType2=ret.itemtype2;
  	    Global.Dialog.showDialog("logWareEffAnalyView",{
			 title:"物流仓储效率分析--周转率明细",
			 url:"${ctx}/admin/logWareEffAnaly/goDetailView",	  	
			 height:700, 
	         width:1000,
			 postData:params,
			 returnFun: goto_query
		});
}
</script>
</head>

<body>
	<div class="body-box-list">
		<div class="query-form">
			<form action="" method="post" id="page_form" class="form-search">
				<input type="hidden" name="jsonString" value="" /> <input type="hidden" id="expired" name="expired"
					 />
				<ul class="ul-form">
					<li>
						<label>仓库编号</label>
						<input type="text" id="whCode" name="whCode"  />
					</li>
					<li>
						<label>材料类型1</label> <select id="itemType1" name="itemType1"></select>
					</li>
					<li>
						<label>材料类型2</label> <select id="itemType2" name="itemType2"></select>
					</li>
					<li>
						<label>统计日期从</label>
						<input type="text" id="dateFrom" name="dateFrom" class="i-date"  onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
						 value="<fmt:formatDate value='${itemWHBal.dateFrom}' pattern='yyyy-MM-dd'/>" />
					</li>
					<li>
						<label>至</label>
						<input type="text" id="dateTo" name="dateTo" class="i-date"  onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
						value="<fmt:formatDate value='${itemWHBal.dateTo}' pattern='yyyy-MM-dd'/>" />
					</li>
					<li><label></label> 
						<button type="button" class="btn btn-sm btn-system " onclick="goto_query();">查询</button>
						<button type="button" class="btn btn-sm btn-system " onclick="clearForm();">清空</button>
					</li>
				</ul>
			</form>
		</div>

		<div class="clear_float"></div>

		<div class="btn-panel">
			<div class="btn-group-sm">
				<house:authorize authCode="LOGWAREEFFANALY_EXCEL">
					<button type="button" class="btn btn-system " onclick="doExcel()">输出至excel</button>
				</house:authorize>
				<house:authorize authCode="LOGWAREEFFANALY_DETAILVIEW">
			        <button id="btnView" type="button" class="btn btn-system " onclick="view()">查看</button>	
				</house:authorize>				
			</div>
		</div>
		<div id="content-list">
			<table id="dataTable"></table>
			<div id="dataTablePager"></div>
		</div>
	</div>
</body>
</html>


