<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>

<script type="text/javascript">
$(function(){
	var leaveEmpOption ={
			//url:"${ctx}/admin/salaryCompareAnalyse/goLeaveEmpDataTableJqGrid",
			//postData:{},
			height: $(document).height() - $("#content-list").offset().top-120,
			colModel : [
				{name: "empcode", index: "empcode", width: 70, label: "工号", sortable: true, align: "left",},
				{name: "empname", index: "empname", width: 70, label: "姓名", sortable: true, align: "left", },
				{name: "idnum", index: "idnum", width: 150, label: "身份证号", sortable: true, align: "left", },
				{name: "dep1descr", index: "dep1descr", width: 104, label: "一级部门", sortable: true, align: "left"},
				{name: "dep2descr", index: "dep2descr", width: 104, label: "二级部门", sortable: true, align: "left", },
				{name: "positiondescr", index: "positiondescr", width: 90, label: "职位", sortable: true, align: "left", },
				{name: "joindate", index: "joindate", width: 90, label: "入职时间", sortable: true, align: "left", formatter:formatDate},
				{name: "leavedate", index: "leavedate", width: 90, label: "离职时间", sortable: true, align: "left", formatter:formatDate},
				{name: "basesalary", index: "basesalary", width: 120, label: "应发", sortable: true, align: "right", formatter: formatRound},
				{name: "realpaysalary", index: "realpaysalary", width: 124, label: "实发", sortable: true, align: "right", formatter: formatRound},
				{name: "unpaidsalary", index: "unpaidsalary", width: 124, label: "未付", sortable: true, align: "right", formatter: formatRound},
			], 
 		};
		//初始化送货申请明细
		Global.JqGrid.initJqGrid("leaveEmpDataTable",leaveEmpOption);
	   
		function formatRound(cellvalue, options, rowObject){
			console.log(cellvalue);
			console.log(rowObject);
			if(rowObject==null){
	        	return '';
			}
			if(cellvalue == null){
				return cellvalue;
			}
	      	if(cellvalue == 0){
	      		return 0;
	      	}
	      	return myRound(cellvalue, 2);
		}
});
 </script>
<div class="body-box-list" style="margin-top: 0px;border:1px solid #ddd;">
	<div class="pageContent">
		<table id="leaveEmpDataTable"></table>
		<div id="leaveEmpDataTablePager"></div>
	</div>
</div>
