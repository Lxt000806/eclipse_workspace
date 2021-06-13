<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<title>员工信息--列表</title>
<%@ include file="/commons/jsp/common.jsp" %>
<script type="text/javascript">
	function clearForm(){
		$("#type").val('');
		$("#page_form input[type='text']").val('');
		$("#page_form select").val('');
	} 
	$(function(){
		Global.LinkSelect.initSelect("${ctx}/admin/department1/byDepType","department1","department2","department3");
	});
	function goSalaryUpdate(){
		var ret = selectDataTableRow();
	    if (ret) {
	      Global.Dialog.showDialog("salaryUpdate",{
			  title:"员工信息薪资--录入",
			  url:"${ctx}/admin/employee/goSalaryUpdate",
			  postData:{
			  	id:ret.number,
			  },
			  height: 400,
		  	  width:500,
			  returnFun: goto_query
			});
	    } else {
	    	art.dialog({
				content: "请选择一条记录"
			});
	    }
	}
	function doExcel() {
		var url = "${ctx}/admin/employee/doExcel";
		doExcelAll(url);
	} 
	/**初始化表格*/
	$(function(){
		if ("${employee.m_umState}"=="V"){
			$("#btnSalaryUpdate").hide();
		}
		Global.JqGrid.initJqGrid("dataTable",{
			styleUI : "Bootstrap",
			url:"${ctx}/admin/employee/goJqGrid",
			postData: $("#page_form").jsonForm(),
			height:$(document).height()-$("#content-list").offset().top-100,
			colModel : [
						{name: "number", index: "number", width: 70, label: "员工编号", sortable: true, align: "left"},
						{name: "namechi", index: "namechi", width: 80, label: "姓名", sortable: true, align: "left"},
						{name: "nameeng", index: "nameeng", width: 80, label: "艺名", sortable: true, align: "left", hidden: true},
						{name: "statusdesc", index: "statusdesc", width: 70, label: "状态", sortable: true, align: "left"},
						{name: "basicwage", index: "basicwage", width: 70, label: "工资", sortable: true, align: "right",sum:true},
						{name: "persocialinsur", index: "persocialinsur", width: 100, label: "五险一金(个人)", sortable: true, align: "right",sum:true},
						{name: "comsocialinsur", index: "comsocialinsur", width: 100, label: "五险一金(企业)", sortable: true, align: "right",sum:true},
						{name: "insursigncmp", index: "insursigncmp", width: 90, label: "社保所属公司", sortable: true},
						{name: "consigncmpdescr", index: "consigncmpdescr", width: 90, label: "合同签约公司", sortable: true},
						{name: "department1descr", index: "department1descr", width: 90, label: "一级部门", sortable: true, align: "left"},
						{name: "department2descr", index: "department2descr", width: 90, label: "二级部门", sortable: true, align: "left"},
						{name: "posdesc", index: "posdesc", width: 80, label: "职位", sortable: true, align: "left"},
						{name: "isleaddesc", index: "isleaddesc", width: 70, label: "是否领导", sortable: true, align: "left"},
						{name: "joindate", index: "joindate", width: 80, label: "加入日期", sortable: true, align: "left", formatter: formatDate},
						{name: "posichgdate", index: "posichgdate", width: 80, label: "调岗日期", sortable: true, align: "left", formatter: formatDate},
						{name: "seniority", index: "seniority", width: 80, label: "工龄", sortable: true, align: "left"},
						{name: "leavedate", index: "leavedate", width: 80, label: "离开日期", sortable: true, align: "left", formatter: formatDate},						
				], 
      			
			});
		
		});
			
			
</script>
</head> 
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
			<input type="hidden" name="jsonString" value="" />
			<input type="hidden" id="expired"  name="expired" />
			<input type="hidden" id="empAuthority" name="empAuthority" value="1" />
			<input type="hidden" name="viewEmpType" value="0"/>
				<ul class="ul-form">
					<li><label style="width:120px">员工编号</label> <input type="text" id="number" name="number" /></li>
				    <li><label style="width:120px">姓名</label> <input type="text" id="nameChi" name="nameChi" /></li>
 					<li>
						<label style="width:120px">一级部门</label>
						<select id="department1" name="department1"></select>
					</li>
					<li>
						<label style="width:120px">二级部门</label>
						<select id="department2" name="department2"></select>
					</li>
					<li>
						<label style="width:120px">员工状态</label>
						<house:xtdm id="status" dictCode="EMPSTS"></house:xtdm>		
					</li>
			        <li class="search-group"><input type="checkbox" id="expired" name="expired" value="F"
						 onclick="changeExpired(this)" checked />
						<p>隐藏过期</p>
						<button type="button" class="btn  btn-sm btn-system " onclick="goto_query();">查询</button>
						<button type="button" class="btn btn-sm btn-system " onclick="clearForm();">清空</button>
					</li>
				</ul>	
			</form>
		</div>
		<div class="btn-panel">
			<div class="btn-group-sm">
					<house:authorize authCode="employee_SALARYINPUT">
						<button type="button" id="btnSalaryUpdate"  class="btn btn-system " onclick="goSalaryUpdate()">
							<span>薪资录入</span>
					</button>
					</house:authorize>
					<button type="button" class="btn btn-system " onclick="doExcel()">导出到Excel</button>
				</div>
		</div>
		<div id="content-list">
				<table id="dataTable"></table>
				<div id="dataTablePager"></div>
		</div>
	</div>	
</body>
</html>
