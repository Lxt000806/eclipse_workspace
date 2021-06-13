<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>

<script type="text/javascript">
$(function(){
	var unPaidEmpGridOption ={
			//url:"${ctx}/admin/salaryCompareAnalyse/goUnPaidEmpJqGrid",
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
				{name: "statusdescr", index: "statusdescr", width: 120, label: "薪酬状态", sortable: true, align: "left"},
				{name: "unpaidsalary", index: "unpaidsalary", width: 124, label: "未付", sortable: true, align: "right", formatter: formatRound},
			], 
 		};
		//初始化送货申请明细
		Global.JqGrid.initJqGrid("unPaidEmpDataTable",unPaidEmpGridOption);
		
		function formatRound(cellvalue, options, rowObject){
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
		<table id="unPaidEmpDataTable"></table>
		<div id="unPaidEmpDataTablePager"></div>
	</div>
</div>
