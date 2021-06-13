<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<title>搜寻--员工</title>
	<%@ include file="/commons/jsp/common.jsp" %>

<script type="text/javascript">
function clearForm(){
	$("#page_form input[type='text'] ").val('');
	$("#page_form select[id!='scopeType'][id!='scopeOperate']").val('');
	$.fn.zTree.getZTreeObj("tree_department1").checkAllNodes(false);
	$("#department1").val('');
} 
/**初始化表格*/
$(function(){
        //初始化表格
		Global.JqGrid.initJqGrid("dataTable",{
			height:$(document).height()-$("#content-list").offset().top-70,
			postData: $("#page_form").jsonForm(),
			styleUI: 'Bootstrap',
			colModel : [
				{name: "empcode", index: "empcode", width: 70, label: "工号", sortable: true, align: "left"},
				{name: "empname", index: "empname", width: 70, label: "姓名", sortable: true, align: "left"},
				{name: "department1descr", index: "department1descr", width: 90, label: "一级部门", sortable: true, align: "left"},
				{name: "department2descr", index: "department2descr", width: 90, label: "二级部门", sortable: true, align: "left"},
				{name: "categorydescr", index: "categorydescr", width: 70, label: "员工类别", sortable: true, align: "left"},
				{name: "consigncmpdescr", index: "consigncmpdescr", width: 80, label: "签约公司", sortable: true, align: "left",},
				{name: "idnum", index: "idnum", width: 140, label: "身份证号", sortable: true, align: "left"},
				{name: "financialcode", index: "financialcode", width: 84, label: "财务编码", sortable: true, align: "left",},
				{name: "posiclassdescr", index: "posiclassdescr", width: 90, label: "岗位类别", sortable: true, align: "left"},
				{name: "posileveldescr", index: "posileveldescr", width: 90, label: "岗位级别", sortable: true, align: "left"},
				{name: "statusdescr", index: "statusdescr", width: 70, label: "状态", sortable: true, align: "left"},
				{name: "joindate", index: "joindate", width: 90, label: "入职日期", sortable: true, align: "left", formatter: formatDate},
				{name: "regulardate", index: "regulardate", width: 90, label: "转正日期", sortable: true, align: "left", formatter: formatDate},
				{name: "leavedate", index: "leavedate", width: 90, label: "离开日期", sortable: true, align: "left", formatter: formatDate},
				{name: "salary", index: "salary", width: 70, label: "工资", sortable: true, align: "right"},
				{name: "basicsalary", index: "basicsalary", width: 70, label: "基本工资", sortable: true, align: "right"},
				{name: "socialinsurparamdescr", index: "socialinsurparamdescr", width: 200, label: "社保公积金参数", sortable: true, align: "left",},
				{name: "socialinsurparam", index: "socialinsurparam", width: 110, label: "社保公积金参数", sortable: true, align: "left",hidden:true},
				{name: "edminsurmon", index: "edminsurmon", width: 120, label: "养老保险开始月份", sortable: true, align: "left",},
				{name: "medinsurmon", index: "medinsurmon", width: 120, label: "医保开始月份", sortable: true, align: "left",},
				{name: "houfundmon", index: "houfundmon", width: 120, label: "公积金开始月份", sortable: true, align: "left",},
				{name: "paymodedescr", index: "paymodedescr", width: 80, label: "发放方式", sortable: true, align: "left"},
				{name: "banktypedescr", index: "banktypedescr", width: 100, label: "银行卡", sortable: true, align: "left",},
				{name: "istaxabledescr", index: "istaxabledescr", width: 70, label: "是否计税", sortable: true, align: "left"},
				{name: "remarks", index: "remarks", width: 120, label: "备注", sortable: true, align: "left"},
				{name: "lastupdate", index: "lastupdate", width: 140, label: "最后更新时间", sortable: true, align: "left", formatter: formatTime},
				{name: "lastupdatedby", index: "lastupdatedby", width: 120, label: "最后更新人员", sortable: true, align: "left"},
				{name: "expired", index: "expired", width: 100, label: "是否过期", sortable: true, align: "left"},
				{name: "actionlog", index: "actionlog", width: 100, label: "操作日志", sortable: true, align: "left"},
				{name: "department1", index: "department1", width: 90, label: "一级部门", sortable: true, align: "left",hidden:true},
				{name: "department2", index: "department2", width: 90, label: "二级部门", sortable: true, align: "left",hidden:true},
				{name: "posiclass", index: "posiclass", width: 90, label: "岗位类别", sortable: true, align: "left",hidden:true},
            ],
            ondblClickRow:function(rowid,iRow,iCol,e){
				var selectRow = $("#dataTable").jqGrid('getRowData', rowid);
            	Global.Dialog.returnData = selectRow;
            	Global.Dialog.closeDialog();
            }  
		});
});
function goto_query(){
	var postData = $("#page_form").jsonForm();
	$("#dataTable").jqGrid("setGridParam", {
    	url:"${ctx}/admin/salaryEmp/goJqGrid",
    	postData: postData,
    	page:1
  	}).trigger("reloadGrid");
}

</script>
</head>
    
<body>
	<div class="body-box-list">
		<div class="query-form">
		       <form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame">
				<input type="hidden" id="isStakeholder" name="isStakeholder" value="${employee.isStakeholder}" />	
					<ul class="ul-form">
					<li>
						<label style="width:120px">人员类别</label>
						<house:xtdm id="category" dictCode="SALEMPCATEGORY" ></house:xtdm>
					</li>
					<li>
                        <label style="width:120px">一级部门</label>
                        <house:DictMulitSelect id="department1" dictCode="" sql="select code, desc1 from tDepartment1 where Expired='F'" 
                            sqlLableKey="desc1" sqlValueKey="code" onCheck="checkDepartment1()"></house:DictMulitSelect>
                    </li>
					<li>
						<label style="width:120px">人员状态</label>
						<house:xtdm id="status" dictCode="EMPSTS"></house:xtdm>
					</li>
					<li>
						<label style="width:120px">查询条件</label>
						<input type="text" id="queryCondition" name="queryCondition" placeholder="姓名/工号/身份证/财务编码"/>
					</li>
					<li>
						<label style="width:120px">入职时间从</label>
						<input type="text" id="joinDateFrom" name="joinDateFrom" class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${salaryEmp.joinDate}' pattern='yyyy-MM-dd'/>" />
					</li>
					<li>						
						<label style="width:120px">至</label>
						<input type="text" id="joinDateTo" name="joinDateTo" class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${salaryEmp.joinDate}' pattern='yyyy-MM-dd'/>" />
					</li>
					<li>
						<label style="width:120px">发放方式</label>
						<house:xtdm id="payMode" dictCode="SALPAYMODE"></house:xtdm>
					</li>
					<li class="search-group" >
						<button type="button" class="btn  btn-sm btn-system " onclick="goto_query();"  >查询</button>
						<button type="button" class="btn btn-sm btn-system " onclick="clearForm();"  >清空</button>
					</li>
				</ul>
			</form>
		</div>
		<!--query-form-->
		<div class="pageContent">
			<div id="content-list">
				<table id= "dataTable"></table>
				<div id="dataTablePager"></div>
			</div> 
		</div> 
	</div>
</body>
</html>


