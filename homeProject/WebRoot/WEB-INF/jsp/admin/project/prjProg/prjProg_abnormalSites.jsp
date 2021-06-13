<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="house" uri="http://www.housenet.com/Framework/tags" %>
<!DOCTYPE html>
<html>
<head>
	<title>异常工地管理</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	
	<%@ include file="/commons/jsp/common.jsp" %>
	
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	
<script type="text/javascript"> 
var tab_index='1';
var excelUrl="${ctx}/admin/prjProg/doExcel_longTimeStop";
var excelTableId="dataTable_longTimeStop";
$(function(){
	$("#tab_4").css("display","none");
	$("#projectMan").openComponent_employee();	
	$("#department2").val('${department2}');
	$("#stopDays").val(7);
	$("#timeOutDays").val(1);
	//$("#openComponent_employee_projectMan").val('${projectMan}');
	$("#projectMan").openComponent_employee({showLabel:"${projectManDescr}",showValue:"${projectMan}"});
	//$("#projectMan").val('${projectMan}');
});
function goTab(index){
	tab_index=index;
	if(index=='1'){
		$("#tab_4").css("display","none");
		$("#tab_1").css("display","");
		$("#consStatus").css("display","");
	}else if(index=='4'){
		$("#tab_1").css("display","none");
		$("#tab_4").css("display","");
		$("#consStatus").css("display","none");
	}else if(index=='3'){
		$("#tab_1").css("display","none");
		$("#tab_4").css("display","none");
		$("#consStatus").css("display","");
	}else{
		$("#tab_1").css("display","none");
		$("#tab_4").css("display","none");
		$("#consStatus").css("display","none");
	}
	goJqGrid();
}

function goLongTimeJqGrid(){
	$("#dataTable_longTimeStop").jqGrid("setGridParam",{postData:{department2:$("#department2").val(),projectMan:$("#projectMan").val(),
		stopDays:$("#stopDays").val()==''?0:$("#stopDays").val(),prgRmkDateTo:$("#prgRmkDateTo").val(),constructStatus:$("#constructStatus").val(),prjItem:$("#prjProgTempNo").val()}}).trigger("reloadGrid");
}

function goWaitFirstCheck(){
	$("#dataTable_waitFirstCheck").jqGrid("setGridParam",{postData:{department2:$("#department2").val(),projectMan:$("#projectMan").val()}}).trigger("reloadGrid");
}

function goWaitCustWorkApp(){
	$("#dataTable_waitCustWorkApp").jqGrid("setGridParam",{postData:{department2:$("#department2").val(),projectMan:$("#projectMan").val(),constructStatus:$("#constructStatus").val()}}).trigger("reloadGrid");
}

function goTimeOutEnd(){
	$("#dataTable_timeOutEnd").jqGrid("setGridParam",{postData:{department2:$("#department2").val(),projectMan:$("#projectMan").val(),
	timeOutDays:$("#timeOutDays").val()==''?0:$("#timeOutDays").val(),workType12:$("#workType12").val(),custWorkerCustStatus:$("#CUSTWKSTATUS").val()}}).trigger("reloadGrid");
}

function goJqGrid(){
	if(tab_index=='1'){
		goLongTimeJqGrid()
	}else if(tab_index=='2'){
		goWaitFirstCheck();
	}else if(tab_index=='3'){
		goWaitCustWorkApp();
	}else{
		goTimeOutEnd();
	}
}

function clearForm(){
	$("#openComponent_employee_projectMan").val('');
	$("#projectMan").val('');
	$.fn.zTree.getZTreeObj("tree_department2").checkAllNodes(false);
	$("#department2").val('');
	$("#department2_NAME").val('');
	
	if(tab_index=='1'){
		$("#stopDays").val('');
		$.fn.zTree.getZTreeObj("tree_constructStatus").checkAllNodes(false);
		$("#constructStatus_NAME").val('');
		$("#constructStatus").val('');
		$("#prgRmkDateTo").val('');
		$.fn.zTree.getZTreeObj("tree_prjProgTempNo").checkAllNodes(false);
		$("#prjProgTempNo_NAME").val('');
		$("#prjProgTempNo").val('');
	}
	if(tab_index=='4'){
		$("#timeOutDays").val('');
		$.fn.zTree.getZTreeObj("tree_CUSTWKSTATUS").checkAllNodes(false);
		$("#CUSTWKSTATUS_NAME").val('');
		$("#CUSTWKSTATUS").val('');
		$.fn.zTree.getZTreeObj("tree_workType12").checkAllNodes(false);
		$("#workType12_NAME").val('');
		$("#workType12").val('');
	}
}

function getRetByDataTable(){
	var ret = null;
	if(tab_index=='1'){
		ret = selectDataTableRow("dataTable_longTimeStop");
	}else if(tab_index=='2'){
		ret = selectDataTableRow("dataTable_waitFirstCheck");
	}else if(tab_index=='3'){
		ret = selectDataTableRow("dataTable_waitCustWorkApp");
	}else{
		ret = selectDataTableRow("dataTable_timeOutEnd");
	}
	return ret;
}

function view(){
	var ret = getRetByDataTable();
	if (ret) {	
    	Global.Dialog.showDialog("Update",{
			title:"查看客户工程进度",
			url:"${ctx}/admin/prjDelayAnaly/goView",
			postData:{code:ret.code},
			width:1100,
			height:715,
			// returnFun:goto_query
		});
  	} else {
  		art.dialog({
			content: "请选择一条记录"
		});
  	}
} 

function goPrgRemark(){
	var ret = getRetByDataTable();
	if (ret) {	 
	    Global.Dialog.showDialog("prgRemark",{ 
	    	title:"修改工地备注",
	    	url:"${ctx}/admin/prjProg/goPrgRemark?id="+ret.code,
	    	height: 440,
	    	width:690,
	    	returnFun:goJqGrid
		});
	} else {
  		art.dialog({
			content: "请选择一条记录"
		});
  	}
}

function setExcelTable(tableId,url){
	excelTableId = tableId;
	excelUrl = "${ctx}/admin/prjProg/"+url;
}

function doExcel(){
	doExcelAll(excelUrl,excelTableId);
}
</script>
</head>
<body >
<div class="body-box-form" >
	<div class="content-form">
		<div class="panel panel-system" >
    		<div class="panel-body" >
      			<div class="btn-group-xs" >
	      			<house:authorize authCode="PRJPROG_VIEW">
						<button type="button" class="btn btn-system " id="view" onclick="view()"><span>查看</span></button>
					</house:authorize>
					<house:authorize authCode="PRJPROG_PRGREMARK">	
						<button type="button" class="btn btn-system " id="prgRemark" onclick="goPrgRemark()"><span>工地备注</span></button>
					</house:authorize>
					<button type="button" class="btn btn-system " id="excel" onclick="doExcel()"><span>导出Excel</span></button>
					<button type="button" class="btn btn-system " id="closeBut" onclick="closeWin(false)">
						<span>关闭</span>
					</button>
				</div>
			</div>
		</div>
        <div class="panel panel-info" >  
			<div class="panel-body">
			 	<form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
					<house:token></house:token>
					<input type="hidden" name="jsonString" value="" />
					<input type="hidden" name="m_umState" id="m_umState" value="M"/>
					<ul class="ul-form">
						<li>
							<label>工程部</label>
							<house:DictMulitSelect id="department2" dictCode="" 
								sql="select a.Code, a.desc1+' '+isnull(e.NameChi,'') desc1  from dbo.tDepartment2 a
										left join tEmployee e on e.Department2=a.Code and e.IsLead='1' and e.LeadLevel='1' and e.expired='F'
										 where  a.deptype='3' and a.Expired='F' order By dispSeq " 
								sqlValueKey="Code" sqlLableKey="Desc1">
							</house:DictMulitSelect>
						</li>
						<li>
							<label>项目经理</label>
							<input type="text" id="projectMan" name="projectMan" style="width:160px;" value="${projectMan}" />
						</li>
						<li id="consStatus">
							<label>施工状态</label>
							<house:xtdmMulit id="constructStatus" dictCode="CONSTRUCTSTATUS" selectedValue="1"></house:xtdmMulit>
						</li>
						<div id="tab_1">
							<li>
								<label>停工天数大于</label>
								<input type="number" id="stopDays" name="stopDays" style="width:160px;" value=${customer.stopDays}  />天
							</li>
							<li>
								<label>备注日期小于</label>
								<input type="text" id="prgRmkDateTo" name="prgRmkDateTo" class="i-date" style="width:160px;" 
									onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
									value=""/>
							</li>
							<li>
								<label>最后验收节点</label>
								<house:xtdmMulit id="prjProgTempNo" dictCode="" sql="select Code,Descr from tPrjItem1 where IsConfirm='1' " 
								sqlValueKey="Code" sqlLableKey="Descr" selectedValue="3,5,7,8,9,10,14,17,19,20"></house:xtdmMulit>
							</li>
						</div>
						
						<div id="tab_4">
							<li>
								<label>是否停工</label>
								<house:xtdmMulit id="CUSTWKSTATUS" dictCode="CUSTWKSTATUS" selectedValue="1"></house:xtdmMulit>
							</li>
							<li>
								<label>超时天数大于</label>
								<input type="number" id="timeOutDays" name="timeOutDays" style="width:160px;" value=${customer.timeOutDays}  />天
							</li>
							<li>
								<label>工种</label>
								<house:DictMulitSelect id="workType12" dictCode="" sql="select RTRIM(a.Code)Code,a.Descr from tWorkType12 a where  (
								(select PrjRole from tCZYBM where CZYBH='${USER_CONTEXT_KEY.czybh}') is null 
								or( select PrjRole from tCZYBM where CZYBH='${USER_CONTEXT_KEY.czybh }') ='' ) or  Code in(
									select WorkType12 From tprjroleworktype12 pr where pr.prjrole = 
									(select PrjRole from tCZYBM where CZYBH='${USER_CONTEXT_KEY.czybh }') or pr.prjrole = ''
									 )   " 
								sqlValueKey="Code" sqlLableKey="Descr" selectedValue="01,02,03,04,05,12,13"></house:DictMulitSelect>
							</li>
						</div>
						<li id="loadMore" style="width: 40%">							
							<button type="button" class="btn btn-system btn-sm" onclick="goJqGrid()">查询</button>
							<button type="button" class="btn btn-system btn-sm" onclick="clearForm()">清空</button>
						</li>
					</ul>	
				</form>
			</div>
		</div>
	</div>
		<div class="container-fluid" >  
			<ul class="nav nav-tabs" > 
		        <li class="active"><a href="#tab_longTimeStop" data-toggle="tab" onclick="goTab('1');setExcelTable('dataTable_longTimeStop','doExcel_longTimeStop')">长期停工</a></li>
		        <li class=""><a href="#tab_waitFirstCheck" data-toggle="tab" onclick="goTab('2');setExcelTable('dataTable_waitFirstCheck','doExcel_waitFirstCheck')">待初检工地</a></li>
		        <li class=""><a href="#tab_waitCustWorkApp" data-toggle="tab" onclick="goTab('3');setExcelTable('dataTable_waitCustWorkApp','doExcel_waitCustWorkApp')">待申请工人工地</a></li>
		        <li class=""><a href="#tab_timeOutEnd" data-toggle="tab" onclick="goTab('4');setExcelTable('dataTable_timeOutEnd','doExcel_timeOutEnd')">未按时完工</a></li>
		    </ul> 
		    <div class="tab-content">  
		        <div id="tab_longTimeStop" class="tab-pane fade in active"> 
		         	<jsp:include page="tab_longTimeStop.jsp"></jsp:include>
		        </div> 
		        <div id="tab_waitFirstCheck" class="tab-pane fade "> 
		         	<jsp:include page="tab_waitFirstCheck.jsp"></jsp:include>
		        </div> 
		        <div id="tab_waitCustWorkApp" class="tab-pane fade "> 
		         	<jsp:include page="tab_waitCustWorkApp.jsp"></jsp:include>
		        </div> 
		        <div id="tab_timeOutEnd" class="tab-pane fade "> 
		         	<jsp:include page="tab_timeOutEnd.jsp"></jsp:include>
		        </div> 
		    
		    </div>  
		</div>
</div>
</body>
</html>
