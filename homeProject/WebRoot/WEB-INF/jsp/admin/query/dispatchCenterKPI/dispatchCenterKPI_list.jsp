<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<title>调度中心绩效分析</title>
	<%@ include file="/commons/jsp/common.jsp" %>

<script type="text/javascript">
var tableId='dataTablePicConfirm';
var tableName="图纸审核员";	
function view(){
	var recordDate=$("#"+tableId).jqGrid('getGridParam','records');
	if(recordDate==0){
		art.dialog({
			content:"请先检索出相应的信息"
		});
		return;
	}
	var ret = selectDataTableRow(tableId);
	if(($("#statistcsMethod").val()=='1'|| $("#statistcsMethod").val()=='2') &&(!ret) ){
		art.dialog({
			content: "请选择一条记录"
		});
		return;
	}
	var stitle="调度中心绩效分析";
	var postData=$("#page_form").jsonForm();
	if ($("#statistcsMethod").val() == '1') {		
		stitle=stitle+"--图纸审核明细";
		postData.empCode=ret.empcode;
	} else if ($("#statistcsMethod").val() == '2'){
		stitle=stitle+"--调度下单明细"
		postData.prjRegionCode=ret.prjregioncode;		 
	} else if ($("#statistcsMethod").val() == '3'){
		stitle=stitle+"--解单定责任明细";
	}else if ($("#statistcsMethod").val() == '4'){
		stitle=stitle+"--开工结算明细";
	}
	Global.Dialog.showDialog("dispatchCenterKPIView",{
		  title: stitle,
		  url:"${ctx}/admin/dispatchCenterKPI/goView",		  	
		  height:660,
		  width:900,
		  postData:postData,
		  returnFun: goto_query
		});
	
}
//导出EXCEL
function doExcel(){
	doExcelNow(tableName,tableId,"page_form");

}    
function getTableId(){
	if ($("#statistcsMethod").val() == '1') {		
		tableId='dataTablePicConfirm';
		tableName="图纸审核员";	
	} else if ($("#statistcsMethod").val() == '2'){
		tableId='dataTableDispatch';
		tableName="调度下单员";	
	} else if ($("#statistcsMethod").val() == '3'){
		tableId='dataTableSpecItemReq';
		tableName="解单定责员";	
	}else if ($("#statistcsMethod").val() == '4'){
		tableId='dataTableBeginAndCheck';
		tableName="开工结算员";
	}
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
    getTableId();
	$("#"+tableId).jqGrid("setGridParam",{url:"${ctx}/admin/dispatchCenterKPI/goJqGrid",datatype:'json',postData:$("#page_form").jsonForm(),page:1,fromServer: true}).trigger("reloadGrid");
}
		
/**初始化表格*/
$(function(){
	//图纸审核员   PicConfirm
	Global.JqGrid.initJqGrid("dataTablePicConfirm",{   					    
		height:380,
		styleUI: 'Bootstrap',
		colModel : [
				{name: "empcode", index: "empcode", width: 80, label: "审核人员", sortable: true, align: "left",hidden:true},
				{name: "confirmdescr", index: "confirmdescr", width: 80, label: "审核人员", sortable: true, align: "left",count :true},
				{name: "confirmcount", index: "confirmcount", width: 80, label: "审核单数", sortable: true, align: "right", sum: true},
				{name: "ontimecount", index: "ontimecount", width: 80, label: "及时套数", sortable: true,  align: "right", sum: true},
				{name: "jzcount", index: "jzcount", width: 80, label: "结转套数", sortable: true,  align: "right", sum: true},
				{name: "ontimeper", index: "ontimeper", width: 90, label: "及时率(%)", sortable: true,  align: "right", sum: true}	
	    ],
	    rowNum:100000,  
		pager :'1',
		loadonce: true, 
		gridComplete:function(){
		     var sumconfirmcount=myRound($("#dataTablePicConfirm").getCol('confirmcount',false,'sum'));  
             var sumontimecount=myRound($("#dataTablePicConfirm").getCol('ontimecount',false,'sum')+$("#dataTablePicConfirm").getCol('jzcount',false,'sum'));   
             var summontimeper=0;
             if(sumconfirmcount!=0){
               summontimeper=myRound(sumontimecount*100/sumconfirmcount,2);
             }
             $("#dataTablePicConfirm").footerData('set', {'ontimeper': summontimeper});		
	     },
	    	               		         
	});
	//调度下单员人 Dispatch 
    Global.JqGrid.initJqGrid("dataTableDispatch",{    		
		height:415,
		styleUI: 'Bootstrap',
		colModel : [
			{name: "prjregioncode", index: "prjregioncode", width: 100, label: "工程大区", sortable: true, align: "left",hidden : true},
			{name: "prjregiondescr", index: "prjregiondescr", width: 100, label: "工程大区", sortable: true, align: "left",count : true},
			{name: "custcount", index: "custcount", width: 80, label: "调度套数", sortable: true, align: "right", sum: true},
			{name: "checkamount", index: "checkamount", width: 100, label: "主材结算金额", sortable: true, align: "right", sum: true}
        ],              
        rowNum:100000,  
		pager :'1',  
	    loadonce: true,          

	}); 
	//解单定责员 SpecItemReq
	Global.JqGrid.initJqGrid("dataTableSpecItemReq",{   			
		height:415,
		styleUI: 'Bootstrap',
		colModel : [
			{name: "specitemreqcount", index: "specitemreqcount", width: 100, label: "解单套数", sortable: true, align: "left",count: true},
			{name: "dutycount", index: "dutycount", width: 80, label: "定责套数", sortable: true, align: "right", sum: true},
			{name: "ontimecount", index: "ontimecount", width: 90, label: "及时完成套数", sortable: true, align: "right", sum: true},
			{name: "ontimeper", index: "ontimeper", width: 100, label: "及时完成率(%)", sortable: true, align: "right", sum: true}
	     ],	       
         rowNum:100000,  
		 pager :'1', 
		 loadonce: true,      
	});
	//开工结算员 BeginAndCheck
	Global.JqGrid.initJqGrid("dataTableBeginAndCheck",{   			
		height:415,
		styleUI: 'Bootstrap',
		colModel : [
			{name: "begincount", index: "begincount", width: 100, label: "开工套数", sortable: true, align: "left",sum: true},
			{name: "checkcount", index: "checkcount", width: 80, label: "结算套数", sortable: true, align: "right", sum: true},
	    ],	     
        rowNum:100000,  
		pager :'1',
		loadonce: true,  
	});
	$("#content-list-dispatch").hide(); 
	$("#content-list-specItemReq").hide(); 
	$("#content-list-beginAndCheck").hide(); 

});

function clearForm(){
	$("#page_form input[type='text']").val('');
	$("#department2").val('');
} 

function changeTab(){ 
	getTableId();									
	if ($("#statistcsMethod").val() == '1') {							
		$("#content-list-picConfirm").show();					
		$("#content-list-dispatch").hide();
		$("#content-list-specItemReq").hide();
		$("#content-list-beginAndCheck").hide();
	} else if ($("#statistcsMethod").val() == '2'){
		$("#content-list-picConfirm").hide();					
		$("#content-list-dispatch").show();
		$("#content-list-specItemReq").hide();
		$("#content-list-beginAndCheck").hide();
	} else if ($("#statistcsMethod").val() == '3'){
		$("#content-list-picConfirm").hide();					
		$("#content-list-dispatch").hide();
		$("#content-list-specItemReq").show();
		$("#content-list-beginAndCheck").hide();
	} else if ($("#statistcsMethod").val() == '4'){
		$("#content-list-picConfirm").hide();					
		$("#content-list-dispatch").hide();
		$("#content-list-specItemReq").hide();
		$("#content-list-beginAndCheck").show();
	}	
}
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
							<label>统计开始日期</label>
							<input type="text" id="dateFrom" name="dateFrom" class="i-date"  onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${customer.dateFrom}' pattern='yyyy-MM-dd'/>" />
						</li>
						<li>
							<label>至</label>
							<input type="text" id="dateTo" name="dateTo" class="i-date"  onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${customer.dateTo}' pattern='yyyy-MM-dd'/>" />
						</li>
		
						<li>
						<label>工程部</label>
							<house:DictMulitSelect id="department2" dictCode="" 
								sql="select rtrim(Code) fd1, rtrim(Desc1) fd2 from tDepartment2 where expired='F' and DepType='3' order by DispSeq " 
								sqlLableKey="fd2" sqlValueKey="fd1">
							</house:DictMulitSelect>
						</li>
						<li> 
						    <label>统计方式</label>
							<select id="statistcsMethod" name="statistcsMethod" onchange="changeTab()" >
								<option value="1">图纸审核员</option>
								<option value="2">调度下单员</option>
								<option value="3">解单定责员</option>
								<option value="4">开工结算员</option>
							</select>
						</li>	
					</ul>		
					<ul class="ul-form">
					<li id="loadMore" >
						<button type="button"  class="btn btn-sm btn-system " onclick="goto_query()">查询</button>
					</li>					
				</ul>
			</form>
		</div>
		<div class="btn-panel" >
		       <div class="btn-group-sm"  >
		     	    <house:authorize authCode="DISPATCHCENTERKPI_VIEW">
						<button type="button" id = "btnview" class="btn btn-system "  onclick="view()">查看</button>
					</house:authorize>
					 <house:authorize authCode="DISPATCHCENTERKPI_EXCEL">
	                    <button type="button" class="btn btn-system " onclick="doExcel()">输出到Excel</button>
					</house:authorize>
				</div>
			<div id="content-list-picConfirm">               
				<table id= "dataTablePicConfirm"></table>  
				<div id="dataTablePicConfirmdPager"></div>
			</div>
			<div id="content-list-dispatch"> 
				<table id= "dataTableDispatch"></table> 
				<div id="dataTableDispatchPager"></div>
			</div>
			<div id="content-list-specItemReq">  
				<table id= "dataTableSpecItemReq"></table> 
				<div id="dataTableSpecItemReqPager"></div>
			</div>
			<div id="content-list-beginAndCheck"> 
				<table id= "dataTableBeginAndCheck"></table> 
				<div id="dataTableBeginAndCheckPager"></div>
			</div>
		</div> 
	</div>
</body>
</html>


