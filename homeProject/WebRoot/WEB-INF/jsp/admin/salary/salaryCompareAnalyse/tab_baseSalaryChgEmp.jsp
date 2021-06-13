<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>

<script type="text/javascript">
$(function(){
	var baseSalaryChgEmpGridOption ={
			//url:"${ctx}/admin/salaryCompareAnalyse/goBaseSalaryChgEmpJqGrid",
			//postData:{},
			height: $(document).height() - $("#content-list").offset().top-120,
			colModel : [
				{name: "empcode", index: "empcode", width: 70, label: "工号", sortable: true, align: "left",},
				{name: "empname", index: "empname", width: 70, label: "姓名", sortable: true, align: "left", },
				{name: "idnum", index: "idnum", width: 150, label: "身份证号", sortable: true, align: "left", },
				{name: "dep1descr", index: "dep1descr", width: 104, label: "一级部门", sortable: true, align: "left"},
				{name: "dep2descr", index: "dep2descr", width: 104, label: "二级部门", sortable: true, align: "left", },
				{name: "positiondescr", index: "positiondescr", width: 90, label: "职位", sortable: true, align: "left", },
				{name: "basesalary", index: "basesalary", width: 120, label: "应发", sortable: true, align: "right", formatter: formatRound},
				{name: "basesalarycompare", index: "basesalarycompare", width: 120, label: "上月应发", sortable: true, align: "right", formatter: formatRound},
				{name: "chgbasesalary", index: "chgbasesalary", width: 124, label: "变动金额", sortable: true, align: "right", formatter: formatRound},
				{name: "chgper", index: "chgper", width: 124, label: "变动百分比", sortable: true, align: "right", formatter: formatPer},
			], 
 		};
       //初始化送货申请明细
	   Global.JqGrid.initJqGrid("baseSalaryChgEmpDataTable",baseSalaryChgEmpGridOption);
	   	
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
		
		function formatPer(cellvalue, options, rowObject){
			if(rowObject==null){
	        	return '';
			}
			if(cellvalue == null){
				return cellvalue;
			}
			if(cellvalue == 0){
	      		return 0;
	      	}
	      	return myRound(cellvalue*100,1)+"%";
		}
});
 </script>
<div class="body-box-list" style="margin-top: 0px;border:1px solid #ddd;">
	<div class="pageContent">
		<table id="baseSalaryChgEmpDataTable"></table>
		<div id="baseSalaryChgEmpDataTablePager"></div>
	</div>
</div>
