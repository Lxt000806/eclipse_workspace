<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>签单排行明细</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	
	<script type="text/javascript">
function goto_query(table,form){
	$("#"+table).jqGrid("setGridParam",{postData:$("#"+form).jsonForm(),page:1}).trigger("reloadGrid");
}

function doExcel(){
    if ($("ul li a[href='#tab1']").parent().hasClass("active")){
			var url = "${ctx}/admin/employeeDdph/doSignExcel";
	    doExcelAll(url,'signdataTable');
	}else if  ($("ul li a[href='#tab2']").parent().hasClass("active")){
			var url = "${ctx}/admin/employeeDdph/doSignSetExcel";
	    doExcelAll(url,'signSetdataTable');
	}else if  ($("ul li a[href='#tab3']").parent().hasClass("active")){
			var url = "${ctx}/admin/employeeDdph/doNowSignExcel";
	    doExcelAll(url,'nowSigndataTable');
	}else if  ($("ul li a[href='#tab4']").parent().hasClass("active")){
			var url = "${ctx}/admin/employeeDdph/doCrtExcel";
	    doExcelAll(url,'crtdataTable');
   	}else if  ($("ul li a[href='#tab5']").parent().hasClass("active")){
			var url = "${ctx}/admin/employeeDdph/doSetExcel";
	    doExcelAll(url,'setdataTable');   
	}else if  ($("ul li a[href='#tab6']").parent().hasClass("active")){
			var url = "${ctx}/admin/employeeDdph/doQdExcel";
	    doExcelAll(url,'qddataTable');   
	}else if  ($("ul li a[href='#tab7']").parent().hasClass("active")){
			var url = "${ctx}/admin/employeeDdph/doZjExcel";
	    doExcelAll(url,'zjdataTable');   
	}		
		
}

$(function(){ 
	//初始化表格
	Global.JqGrid.initJqGrid("signdataTable",{
			url:"${ctx}/admin/employeeDdph/goSignJqGrid",
		postData:$("#page_form").jsonForm(), 
		height:500-$("#content-list2").offset().top-50,  
        styleUI: 'Bootstrap',
        autowidth:false,
        width:1170, 
        rowNum:100000,  
	    pager :'1',
		colModel : [
			  {name : 'code',index : 'code',width : 150,label:'客户号',sortable : true,align : "left",hidden : true},     
			  {name : 'address',index : 'address',width : 150,label:'楼盘',sortable : true,align : "left"},
			  {name : 'custtypedescr',index : 'custtypedescr',width : 120,label:'客户类型',sortable : true,align : "left"},
		      {name : 'designmandescr',index : 'designmandescr',width :60,label:'设计师',sortable : true,align : "left"},
		      {name : 'businessmandescr',index : 'businessmandescr',width :60,label:'业务员',sortable : true,align : "left"},
		      {name : 'setdate',index : 'setdate',width : 75,label:'下定时间',sortable : true,align : "left",formatter: formatTime},
		      {name : 'signdate',index : 'signdate',width :  85,label:'合同签订时间',sortable : true,align : "left",formatter: formatTime},
		      {name : 'contractfee',index : 'contractfee',width :75,label:'签单金额',sortable : true,align : "right",sum: true},
		      {name : 'payper',index : 'payper',width :  85,label:'首款到账率',sortable : true,align : "right" , hidden: true, formatter:"number", formatoptions:{decimalSeparator:".", thousandsSeparator: " ", decimalPlaces: 2, suffix: "%"}},
		      {name : 'payamount',index : 'payamount',width : 75,label:'已付款',sortable : true,align : "right"},
		      {name : 'designfee',index : 'designfee',width : 75,label:'设计费',sortable : true,align : "right"},
		      {name : 'area',index : 'area',width : 60,label:'面积',sortable : true,align : "right"},
		      {name : 'managefee',index : 'managefee',width : 75,label:'管理费',sortable : true,align : "right"},
		      {name : 'mainservfee',index : 'mainservfee',width :75,label:'服务性产品费',sortable : true,align : "right"},
		      {name : 'achievded',index : 'achievded',width : 60,label:'业绩调整数',sortable : true,align : "right"},
		      {name : 'firstpay',index : 'firstpay',width : 75,label:'首付款',sortable : true,align : "right"},
		      {name: "contracttype", index: "contracttype", width: 65, label: "合同类型", sortable: true, align: "left"}
	          ]
	});
	 Global.JqGrid.initJqGrid("signSetdataTable",{
			url:"${ctx}/admin/employeeDdph/goSignSetJqGrid",
			postData:$("#page_form").jsonForm(),
			height:500-$("#content-list2").offset().top-50,
			styleUI: 'Bootstrap',
			autowidth:false,
		    width:1170, 
		    rowNum:100000,  
		    pager :'1',
			colModel : [
			  {name : 'address',index : 'address',width : 250,label:'楼盘',sortable : true,align : "left"},
			  {name : 'custtypedescr',index : 'custtypedescr',width : 120,label:'客户类型',sortable : true,align : "left"},
		      {name : 'designmandescr',index : 'designmandescr',width : 100,label:'设计师',sortable : true,align : "left"},
		      {name : 'businessmandescr',index : 'businessmandescr',width : 100,label:'业务员',sortable : true,align : "left"},
		      {name : 'setdate',index : 'setdate',width : 150,label:'下定时间',sortable : true,align : "left",formatter: formatTime},
		      {name : 'signdate',index : 'signdate',width : 150,label:'签单时间',sortable : true,align : "left",formatter: formatTime},
		      {name : 'enddate',index : 'enddate',width : 150,label:'结转时间',sortable : true,align : "left",formatter: formatTime},
		      {name : 'statusdescr',index : 'statusdescr',width : 150,label:'客户状态',sortable : true,align : "left"},
		      {name : 'endcodedescr',index : 'endcodedescr',width : 100,label:'结束原因',sortable : true,align : "left"}
            ]
		});
       
      
        Global.JqGrid.initJqGrid("nowSigndataTable",{
			url:"${ctx}/admin/employeeDdph/goNowSignJqGrid",
			postData:$("#page_form").jsonForm(),
			height:500-$("#content-list3").offset().top-50,
			styleUI: 'Bootstrap',
			autowidth:false,
			width:1170,
			rowNum:100000,  
			pager :'1',
			colModel : [
		        {name : 'address',index : 'address',width : 150,label:'楼盘',sortable : true,align : "left"},
		        {name : 'custtypedescr',index : 'custtypedescr',width : 120,label:'客户类型',sortable : true,align : "left"},
		        {name : 'designmandescr',index : 'designmandescr',width : 100,label:'设计师',sortable : true,align : "left"},
		        {name : 'businessmandescr',index : 'businessmandescr',width : 100,label:'业务员',sortable : true,align : "left"},
		        {name : 'setdate',index : 'setdate',width : 150,label:'下定时间',sortable : true,align : "left",formatter: formatTime},
		        {name : 'signdate',index : 'signdate',width : 150,label:'签单时间',sortable : true,align : "left",formatter: formatTime},
		        {name : 'enddate',index : 'enddate',width : 150,label:'结转时间',sortable : true,align : "left",formatter: formatTime},
		        {name : 'statusdescr',index : 'statusdescr',width : 150,label:'客户状态',sortable : true,align : "left"},
		        {name : 'endcodedescr',index : 'endcodedescr',width : 100,label:'结束原因',sortable : true,align : "left"}
            ]
		});
   
        Global.JqGrid.initJqGrid("crtdataTable",{
			url:"${ctx}/admin/employeeDdph/goCrtJqGrid",
			postData:$("#page_form").jsonForm(),
			height:500-$("#content-list4").offset().top-50,
			styleUI: 'Bootstrap',
			autowidth:false,
			width:1170,
			rowNum:100000,  
			pager :'1',
			colModel : [
				{name : 'address',index : 'address',width : 150,label:'楼盘',sortable : true,align : "left"},
				{name : 'custtypedescr',index : 'custtypedescr',width : 120,label:'客户类型',sortable : true,align : "left"},
			    {name : 'statusdescr',index : 'statusdescr',width : 60,label:'客户状态',sortable : true,align : "left"},
		        {name : 'designmandescr',index : 'designmandescr',width : 100,label:'设计师',sortable : true,align : "left"},
		        {name : 'businessmandescr',index : 'businessmandescr',width : 100,label:'业务员',sortable : true,align : "left"},
		        {name : 'crtdate',index : 'crtdate',width : 150,label:'创建时间',sortable : true,align : "left",formatter: formatTime},
		        {name : 'setdate',index : 'setdate',width : 150,label:'下定时间',sortable : true,align : "left",formatter: formatTime},
		        {name : 'signdate',index : 'signdate',width : 150,label:'签单时间',sortable : true,align : "left",formatter: formatTime},
		        {name : 'enddate',index : 'enddate',width : 150,label:'结束时间',sortable : true,align : "left",formatter: formatTime},
		        {name : 'endcodedescr',index : 'endcodedescr',width : 100,label:'结束原因',sortable : true,align : "left"}
            ]
		});

          Global.JqGrid.initJqGrid("setdataTable",{
			url:"${ctx}/admin/employeeDdph/goSetJqGrid",
			postData:$("#page_form").jsonForm(),
			height:500-$("#content-list5").offset().top-50,
			styleUI: 'Bootstrap',
			autowidth:false,
			width:1170,
			rowNum:100000,  
			pager :'1',
			colModel : [
				{name : 'address',index : 'address',width : 250,label:'楼盘',sortable : true,align : "left"},
				{name : 'custtypedescr',index : 'custtypedescr',width : 120,label:'客户类型',sortable : true,align : "left"},
		        {name : 'designmandescr',index : 'designmandescr',width : 100,label:'设计师',sortable : true,align : "left"},
		        {name : 'businessmandescr',index : 'businessmandescr',width : 100,label:'业务员',sortable : true,align : "left"},
		        {name : 'setdate',index : 'setdate',width : 150,label:'下定时间',sortable : true,align : "left",formatter: formatTime},
		        {name : 'crtdate',index : 'crtdate',width : 150,label:'创建时间',sortable : true,align : "left",formatter: formatTime}
            ]
		});
		
		 Global.JqGrid.initJqGrid("qddataTable",{
			url:"${ctx}/admin/employeeDdph/goQdJqGrid",
			postData:$("#page_form").jsonForm(),
			height:500-$("#content-list6").offset().top-50,
			styleUI: 'Bootstrap',
			autowidth:false,
			width:1170,
			rowNum:100000,  
			pager :'1',
			colModel : [
				{name: 'address',index : 'address',width : 150,label:'楼盘',sortable : true,align : "left"},
				{name: 'custtypedescr',index : 'custtypedescr',width : 120,label:'客户类型',sortable : true,align : "left"},
		        {name: 'designmandescr',index : 'designmandescr',width : 100,label:'设计师',sortable : true,align : "left"},
		        {name: 'designmandeptdescr', index: 'designmandeptdescr', width: 108, label: '设计部', sortable: true, align: 'left'},
		        {name: 'businessmandescr',index : 'businessmandescr',width : 100,label:'业务员',sortable : true,align : "left"},
		        {name: 'businessmandeptdescr', index: 'businessmandeptdescr', width: 108, label: '业务部', sortable: true, align: 'left'},
		        {name: 'area',index : 'area',width : 70,label:'面积',sortable : true,align : "right"},
		        {name: 'perftype',index : 'perftype',width : 80,label:'业绩类型',sortable : true,align : "left"},
		        {name: 'perfper',index : 'perfper',width : 80,label:'业绩比例',sortable : true,align : "right"},
            	{name: 'contractfee', index: 'contractfee', width: 70, label: '合同总额', sortable: true, align: 'right', sum: true,formatter:'number', formatoptions:{decimalSeparator:".", thousandsSeparator: "", decimalPlaces: 0}},
		        {name: 'achievedate',index : 'setdate',width : 90,label:'达标时间',sortable : true,align : "left",formatter: formatDate},
		        {name: 'designfee', index: 'designfee', width: 80, label: '设计费', sortable: true, align: 'right', sum: true,formatter:'number', formatoptions:{decimalSeparator:".", thousandsSeparator: "", decimalPlaces: 0}},
		        {name: 'baseplan', index: 'baseplan', width: 70, label: '基础预算', sortable: true, align: 'right', sum: true,formatter:'number', formatoptions:{decimalSeparator:".", thousandsSeparator: "", decimalPlaces: 0}},
		        {name: 'managefee_sum', index: 'managefee_sum', width: 80, label: '管理费', sortable: true, align: 'right',formatter:'number', formatoptions:{decimalSeparator:".", thousandsSeparator: "", decimalPlaces: 0}},
				{name: 'mainplan', index: 'mainplan', width: 70, label: '主材预算', sortable: true, align: 'right', sum: true,formatter:'number', formatoptions:{decimalSeparator:".", thousandsSeparator: "", decimalPlaces: 0}},
				{name: 'integrateplan', index: 'integrateplan', width: 70, label: '集成预算', sortable: true, align: 'right',sum: true,formatter:'number', formatoptions:{decimalSeparator:".", thousandsSeparator: "", decimalPlaces: 0}},
				{name: 'cupboardplan', index: 'cupboardplan', width: 70, label: '橱柜预算', sortable: true, align: 'right', sum: true,formatter:'number', formatoptions:{decimalSeparator:".", thousandsSeparator: "", decimalPlaces: 0}},
				{name: 'softplan', index: 'softplan', width: 70, label: '软装预算', sortable: true, align: 'right', sum: true,formatter:'number', formatoptions:{decimalSeparator:".", thousandsSeparator: "", decimalPlaces: 0}},
				{name: 'softfee_furniture', index: 'softfee_furniture', width: 70, label: '软装家具', sortable: true, align: 'right', sum: true,formatter:'number', formatoptions:{decimalSeparator:".", thousandsSeparator: "", decimalPlaces: 0}},
				{name: 'mainservplan', index: 'mainservplan', width: 112, label: '服务性产品预算', sortable: true, align: 'right', sum: true,formatter:'number', formatoptions:{decimalSeparator:".", thousandsSeparator: "", decimalPlaces: 0}},
				{name: 'basedisc', index: 'basedisc', width: 90, label: '协议优惠', sortable: true, align: 'right', sum: true,formatter:'number', formatoptions:{decimalSeparator:".", thousandsSeparator: "", decimalPlaces: 0}},
            	{name: 'gift', index: 'gift', width: 70, label: '实物赠送', sortable: true, align: 'right', sum: true,formatter:'number', formatoptions:{decimalSeparator:".", thousandsSeparator: "", decimalPlaces: 0}},
		        {name: 'initperfamount',index : 'initperfamount',width : 100,label:'业绩金额',sortable : true,align : "right",sum: true},
		        {name: 'realperfamount',index : 'realperfamount',width : 100,label:'实际业绩',sortable : true,align : "right",sum: true},
            ]
		});
		
		 Global.JqGrid.initJqGrid("zjdataTable",{
			url:"${ctx}/admin/employeeDdph/goZjJqGrid",
			postData:$("#page_form").jsonForm(),
			height:500-$("#content-list7").offset().top-50,
			styleUI: 'Bootstrap',
			autowidth:false,
			width:1170,
			rowNum:100000,  
			pager :'1',
			colModel : [
				{name : 'address',index : 'address',width : 150,label:'楼盘',sortable : true,align : "left"},
				{name : 'custtypedescr',index : 'custtypedescr',width : 120,label:'客户类型',sortable : true,align : "left"},
		        {name : 'designmandescr',index : 'designmandescr',width : 100,label:'设计师',sortable : true,align : "left"},
		        {name: 'designmandeptdescr', index: 'designmandeptdescr', width: 108, label: '设计部', sortable: true, align: 'left'},
		        {name : 'businessmandescr',index : 'businessmandescr',width : 100,label:'业务员',sortable : true,align : "left"},
		        {name: 'businessmandeptdescr', index: 'businessmandeptdescr', width: 108, label: '业务部', sortable: true, align: 'left'},
		        {name : 'area',index : 'area',width : 70,label:'面积',sortable : true,align : "right"},
		        {name : 'perftype',index : 'perftype',width : 80,label:'业绩类型',sortable : true,align : "left"},
		        {name : 'perfper',index : 'perfper',width : 80,label:'业绩比例',sortable : true,align : "right"},
            	{name: 'contractfee', index: 'contractfee', width: 70, label: '增减金额', sortable: true, align: 'right', sum: true,formatter:'number', formatoptions:{decimalSeparator:".", thousandsSeparator: "", decimalPlaces: 0}},
		        {name : 'achievedate',index : 'setdate',width : 90,label:'达标时间',sortable : true,align : "left",formatter: formatDate},
				{name : 'checkstatusdescr',index : 'checkstatusdescr',width : 100,label:'客户结算状态',sortable : true,align : "left"},
		        {name : 'custcheckdate',index : 'custcheckdate',width : 90,label:'客户结算日期',sortable : true,align : "left",formatter: formatDate},
		        {name: 'designfee', index: 'designfee', width: 80, label: '设计费', sortable: true, align: 'right', sum: true,formatter:'number', formatoptions:{decimalSeparator:".", thousandsSeparator: "", decimalPlaces: 0}},
		        {name: 'baseplan', index: 'baseplan', width: 70, label: '基础预算', sortable: true, align: 'right', sum: true,formatter:'number', formatoptions:{decimalSeparator:".", thousandsSeparator: "", decimalPlaces: 0}},
		        {name: 'managefee_sum', index: 'managefee_sum', width: 80, label: '管理费', sortable: true, align: 'right',formatter:'number', formatoptions:{decimalSeparator:".", thousandsSeparator: "", decimalPlaces: 0}},
				{name: 'mainplan', index: 'mainplan', width: 70, label: '主材预算', sortable: true, align: 'right', sum: true,formatter:'number', formatoptions:{decimalSeparator:".", thousandsSeparator: "", decimalPlaces: 0}},
				{name: 'integrateplan', index: 'integrateplan', width: 70, label: '集成预算', sortable: true, align: 'right',sum: true,formatter:'number', formatoptions:{decimalSeparator:".", thousandsSeparator: "", decimalPlaces: 0}},
				{name: 'cupboardplan', index: 'cupboardplan', width: 70, label: '橱柜预算', sortable: true, align: 'right', sum: true,formatter:'number', formatoptions:{decimalSeparator:".", thousandsSeparator: "", decimalPlaces: 0}},
				{name: 'softplan', index: 'softplan', width: 70, label: '软装预算', sortable: true, align: 'right', sum: true,formatter:'number', formatoptions:{decimalSeparator:".", thousandsSeparator: "", decimalPlaces: 0}},
				{name: 'softfee_furniture', index: 'softfee_furniture', width: 70, label: '软装家具', sortable: true, align: 'right', sum: true,formatter:'number', formatoptions:{decimalSeparator:".", thousandsSeparator: "", decimalPlaces: 0}},
				{name: 'mainservplan', index: 'mainservplan', width: 112, label: '服务性产品预算', sortable: true, align: 'right', sum: true,formatter:'number', formatoptions:{decimalSeparator:".", thousandsSeparator: "", decimalPlaces: 0}},
				{name: 'basedisc', index: 'basedisc', width: 90, label: '协议优惠', sortable: true, align: 'right', sum: true,formatter:'number', formatoptions:{decimalSeparator:".", thousandsSeparator: "", decimalPlaces: 0}},
            	{name: 'gift', index: 'gift', width: 70, label: '实物赠送', sortable: true, align: 'right', sum: true,formatter:'number', formatoptions:{decimalSeparator:".", thousandsSeparator: "", decimalPlaces: 0}},
		        {name : 'initperfamount',index : 'initperfamount',width : 100,label:'业绩金额',sortable : true,align : "right",sum: true},
		        {name : 'realperfamount',index : 'realperfamount',width : 100,label:'实际业绩',sortable : true,align : "right",sum: true},
            ]
		});
		if("${customer.statistcsMethod}"=="1"){
			$("#tab1_li").show();
			$("#tab2_li").show(); 
			$("#tab3_li").show(); 
			$("#tab4_li").show(); 
			$("#tab5_li").show();
			$("#tab1_li").addClass("active");  	 		
		}else{
			$("#tab1_li").removeClass("active");
			$("#tab6_li").addClass("active"); 
			$("#tab1").addClass("tab-pane fade");
			$("#tab1").hide();
			$("#tab6").addClass("tab-pane fade in active");	
		}
		
	if ($("ul li a[href='#tab1']").parent().hasClass("active")){
		$("#perfEstimateBtn").show();
	}else{
		$("#perfEstimateBtn").hide();
	}	
	
	$(".nav-tabs li").click(function() {
		  if($(this).find("a").attr('href')=='#tab1'){
			  $("#perfEstimateBtn").show();
		  }else{
			  $("#perfEstimateBtn").hide(); 
		  }   
	});  
		
});
function perfEstimate(){
	var ret = selectDataTableRow("signdataTable");
    if (ret) {
    	$.ajax({
    		url : "${ctx}/admin/customerXx/goVerifyPerfEstimate",
    		data : {custCode: ret.code},
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
	    	   		 Global.Dialog.showDialog("customerXxPerfEstimate",{
		    	   		  title:"签单排行签单业绩预估【"+ret.address+"】",
		    	   		  url:"${ctx}/admin/customerXx/goPerfEstimate?custCode="+ret.code,
		    	   		  height:710,
		    	   		  width:1260
	    	   		});		    		
    	    	}else {
    	    		art.dialog({
    					content: obj.msg
    				});
    	    	}
    		}
    	});
     
    } else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}

</script>
</head>
<body>
	<div class="body-box-form">
		<div class="query-form" hidden>
			<form action="" method="post" id="page_form" >
				<input type="hidden" name="jsonString" value="" />
				<input  id="expired" name="expired" value="${customer.expired }" />
				<input  id="empCode" name="empCode" value="${customer.empCode}" />
				<input  id="department1" name="department1" value="${customer.department1 }" />
				<input  id="department2" name="department2" value="${customer.department2 }" />
				<input  id="department3" name="department3" value="${customer.department3 }" />
				<input  id="team" name="team" value="${customer.team }" />
				<input  id="custType" name="custType" value="${customer.custType }" />
				<input  id="role" name="role" value="${customer.role }" />
				<input  id="dateFrom" name="dateFrom"  value="<fmt:formatDate value='${customer.dateFrom}' pattern='yyyy-MM-dd'/>" />
				<input  id="dateTo" name="dateTo"  value="<fmt:formatDate value='${customer.dateTo}' pattern='yyyy-MM-dd'/>" />
				<input  id="orderBy" name="orderBy" value="${customer.orderBy }" />
				<input  id="statistcsMethod" name="statistcsMethod" value="${customer.statistcsMethod }" />
				<input  id="builderCode" name="builderCode" value="${customer.builderCode }" />
			</form>
		</div>
		<div class="panel panel-system" >
			    <div class="panel-body" >
			    	<div class="btn-group-xs" >
			    		 <house:authorize authCode="EMPLOYEEDDPH_PERFESTIMATE">
		               	     <button type="button" class="btn btn-system" id="perfEstimateBtn" onclick="perfEstimate()">签单业绩预估</button>
		             	</house:authorize>
	               		<house:authorize authCode="QDPH_EXCEL">
	     					<button type="button" class="btn btn-system " onclick="doExcel()">导出excel</button>
	     				</house:authorize>
							<button type="button" class="btn btn-system" onclick="closeWin(false)">关闭</button>	
					</div>
				</div>
		  </div>
		<div  class="container-fluid" >  
		     <ul class="nav nav-tabs" >
		        <li id="tab1_li" class="" style="display: none;"><a href="#tab1" data-toggle="tab" >签单信息</a></li>  
		        <li id="tab6_li" class="" ><a href="#tab6" data-toggle="tab" >签单业绩信息</a></li> 
		        <li id="tab7_li" class="" ><a href="#tab7" data-toggle="tab" >增减业绩信息</a></li>  
		        <li id="tab2_li"  class=""style="display: none;"><a href="#tab2" data-toggle="tab" >统计期存单信息</a></li>
		        <li id="tab3_li" class="" style="display: none;"><a href="#tab3" data-toggle="tab" >当前有效存单信息</a></li>  
		        <li id="tab4_li" class="" style="display: none;"><a href="#tab4" data-toggle="tab" >接待信息</a></li> 
		        <li id="tab5_li" class="" style="display: none;"><a href="#tab5" data-toggle="tab" >下定信息</a></li>
		    </ul>  
		    <div class="tab-content">  
				<div id="tab1"  class="tab-pane fade in active"> 
					<div id="content-list">
						<table id= "signdataTable" ></table>
						<div id="signdataTablePager"></div>
					</div> 
				</div>  
	
				<div id="tab2"  class="tab-pane fade " > 
					<div id="content-list2">
					  <table id= "signSetdataTable" ></table>
					   <div id="signSetdataTablePager"></div>
				   </div> 
				</div>
				<div id="tab3"  class="tab-pane fade " > 
					<div id="content-list3">
						<table id= "nowSigndataTable"></table>
						<div id="nowSigndataTablePager"></div>
					</div> 
				</div>
				<div id="tab4"  class="tab-pane fade " > 
					<div id="content-list4">
						<table id= "crtdataTable"></table>
						<div id="crtdataTablePager"></div>
					</div> 
				</div>
				<div id="tab5"  class="tab-pane fade " > 
					<div id="content-list5">
						<table id= "setdataTable"></table>
						<div id="setdataTablePager"></div>
					</div> 
				</div>
				<div id="tab6"  class="tab-pane fade " > 
					<div id="content-list6">
						<table id= "qddataTable"></table>
						<div id="qddataTablePager"></div>
					</div> 
				</div>
				<div id="tab7"  class="tab-pane fade " > 
					<div id="content-list7">
						<table id= "zjdataTable"></table>
						<div id="zjdataTablePager"></div>
					</div> 
				</div>
			</div>	
		</div>
	</div>
</body>
</html>

