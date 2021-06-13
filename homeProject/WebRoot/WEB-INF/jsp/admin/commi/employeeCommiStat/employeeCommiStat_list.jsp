<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<title>员工提成统计</title>
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
function view() {
	var ret = selectDataTableRow();
  		if(!ret){
  			art.dialog({content: "请选择一条记录进行查看！",width: 200});
  			return false;
  		}
  	 	var showTabs = getShowTabs(ret);
  	    var params = $("#page_form").jsonForm();
  	 	    params.number = ret.number;
  	 	    params.showTabs = showTabs;
  	    Global.Dialog.showDialog("employeeCommiStatView",{
		 title:"员工提成统计--明细查看",
		 url:"${ctx}/admin/employeeCommiStat/goView", 
		 height:620,
         width:1100,
		  postData:params,
		  returnFun: goto_query
		});
}

function getShowTabs(ret){
	var showTabs = "";
	if(ret.businesscommi != 0){
		showTabs += "业务提成,";
	}
	if(ret.designcommi != 0){
		showTabs += "设计提成,";
	}
	if(ret.againcommi != 0){
		showTabs += "翻单提成,";
	}
	if(ret.scenecommi != 0){
		showTabs += "现场提成,";
	}
	if(ret.designfeecommi != 0){
		showTabs += "设计费提成,";
	}
	if(ret.mainbusicommi != 0){
		showTabs += "主材提成,";
	}
	if(ret.intbusicommi != 0){
		showTabs += "集成提成,";
	}
	if(ret.softcommi != 0){
		showTabs += "软装提成,";
	}
	if(ret.basepersonalcommi != 0){
		showTabs += "基础个性化提成,";
	}
	if(ret.supplcommi != 0){
		showTabs += "返利提成,";
	}
	return showTabs;
}
         
function goto_query(){
	if($.trim($("#beginMon").val())=='' || $.trim($("#endMon").val())==''){
			art.dialog({content: "月份不能为空",width: 200});
			return false;
	} 
	$("#dataTable").jqGrid("setGridParam",{url:"${ctx}/admin/employeeCommiStat/goJqGrid",datatype:'json',postData:$("#page_form").jsonForm(),page:1,fromServer: true}).trigger("reloadGrid");
}


function doExcel(){
	doExcelNow("员工提成统计","dataTable","page_form");
}			
/**初始化表格*/
$(function(){
	$("#number").openComponent_employee();
	var postData=$("#page_form").jsonForm();
       //初始化表格
	Global.JqGrid.initJqGrid("dataTable",{
		postData: postData,
		height:$(document).height()-$("#content-list").offset().top-90,
		colModel : [
			{name: "number", index: "number", width: 70, label: "员工编号", sortable: true, align: "left",hideen:true},
			{name: "namechi", index: "namechi", width: 75, label: "员工", sortable: true, align: "left", count: true},
			{name: "department1descr", index: "department1descr", width: 80, label: "一级部门", sortable: true, align: "left"},
			{name: "department2descr", index: "department2descr", width: 80, label: "二级部门", sortable: true, align: "left"},
			{name: "leavestatusdescr", index: "leavestatusdescr", width: 80, label: "离职状态", sortable: true, align: "left"},
			{name: "allcommi", index: "allcommi", width: 80, label: "合计提成", sortable: true,sorttype:'float', align: "right", sum: true },
			{name: "businesscommi", index: "businesscommi", width: 80, label: "业务提成", sortable: true,sorttype:'float', align: "right", sum: true },
			{name: "designcommi", index: "designcommi", width: 80, label: "设计提成", sortable: true,sorttype:'float', align: "right", sum: true },
			{name: "againcommi", index: "againcommi", width: 80, label: "翻单提成", sortable: true,sorttype:'float', align: "right", sum: true },
			{name: "scenecommi", index: "scenecommi", width: 80, label: "现场提成", sortable: true,sorttype:'float', align: "right", sum: true },
			{name: "designfeecommi", index: "designfeecommi", width: 80, label: "设计费提成（含绘图/效果图）", sortable: true,sorttype:'float', align: "right", sum: true },
			{name: "mainbusicommi", index: "mainbusicommi", width: 80, label: "主材提成", sortable: true,sorttype:'float', align: "right", sum: true },
			{name: "intbusicommi", index: "intbusicommi", width: 80, label: "集成提成", sortable: true,sorttype:'float', align: "right", sum: true },
			{name: "softcommi", index: "softcommi", width: 80, label: "软装提成", sortable: true,sorttype:'float', align: "right", sum: true },
			{name: "basepersonalcommi", index: "basepersonalcommi", width: 80, label: "基础个性化提成", sortable: true,sorttype:'float', align: "right", sum: true },
			{name: "supplcommi", index: "supplcommi", width: 80, label: "返利提成", sortable: true,sorttype:'float', align: "right", sum: true },
			{name: "leavedate", index: "leavedate", width: 80, label: "离职日期", sortable: true, align: "left", formatter: formatDate},
	    ],    
	    loadonce: true,
	    rowNum:100000,  
	    pager :'1'
	});
});

function showEmp(obj){
	if($(obj).is(":checked")){
		$(obj).val("1");
	}else{
		$(obj).val("0");
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
							<label>月份从</label>
							<input type="text" id="beginMon" name="beginMon" value="${employee.beginMon}" class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyyMM'})"  />
						</li>
						<li>
							<label>至</label>
							<input type="text" id="endMon" name="endMon" value="${employee.endMon}" class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyyMM'})"  />
						</li>
						<li>
							<label>一级部门</label>
							<house:DictMulitSelect id="department1" dictCode="" sql="select code,desc1 from tDepartment1 where Expired='F'" 
							sqlLableKey="desc1" sqlValueKey="code" selectedValue="${employee.department1 }" onCheck="checkDepartment1()"></house:DictMulitSelect>
						</li>
						<li>
							<label>二级部门</label>
							<house:DictMulitSelect id="department2" dictCode="" sql="select code,desc1 from tDepartment2 where 1=2" 
							sqlLableKey="desc1" sqlValueKey="code" selectedValue="${employee.department2 }" onCheck="checkDepartment2()"></house:DictMulitSelect>
						</li>
						<li>
							<label>员工</label>
							<input type="text" id="number" name="number" style="width:160px;" />
						</li>
						<li>
							<label>职位</label>
	                        <house:DictMulitSelect id="positionType" dictCode=""
	                                               sql="select note SQL_LABEL_KEY, cbm SQL_VALUE_KEY from tXTDM where id = 'POSTIONTYPE'"
	                                               selectedValue="${employee.position }"></house:DictMulitSelect> 
						</li>
						<li>
							<label style="width:140px;vertical-align:middle;margin-left:-130px;"></label>
							<input type="checkbox" id="showNoCommiEmp" name="showNoCommiEmp" value="0" onclick="showEmp(this)"/>显示提成为0员工&nbsp;
						</li>
					<li id="loadMore" >
						<button type="button" class="btn btn-sm btn-system " onclick="goto_query();" >查询</button>
					</li>		
				</ul>
			</form>
		</div>
		<div class="btn-panel" >
		       <div class="btn-group-sm"  >
               	   <house:authorize authCode="EMPLOYEECOMMISTAT_VIEW">
               	        <button id="btnView" type="button" class="btn btn-system " onclick="view()">查看</button>	
					</house:authorize>	
	                <house:authorize authCode="EMPLOYEECOMMISTAT_EXCEL">
	                     <button type="button" class="btn btn-system " onclick="doExcel()">导出excel</button>
					</house:authorize>
				
				</div>
			<div id="content-list">
				<table id= "dataTable"></table>
				<div id="dataTablePager"></div>
			</div>
		</div> 
	</div>
</body>
</html>


