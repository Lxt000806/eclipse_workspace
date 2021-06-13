<%@ page language="java" import="com.house.framework.commons.utils.PathUtil" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
	<head>
		<title>设计师计划结果分析</title>
		<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
		<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
		<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
		<META HTTP-EQUIV="expires" CONTENT="0" />
		<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
		<%@ include file="/commons/jsp/common.jsp"%>
		<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
		<script type="text/javascript">
			$(function(){
				var pageForm=$("#page_form").jsonForm();
				pageForm.department_emp="${customer.department }";
				Global.JqGrid.initJqGrid("dataTable",{
					url:"${ctx}/admin/designPlanResultAnaly/goJqGrid",
					height:$(document).height()-$("#content-list").offset().top-100,
					styleUI: 'Bootstrap',
					postData:pageForm,
					rowNum:100000000,
					colModel : [
						  {name : 'empdescr',index : 'empdescr',width : 80,label:'员工',sortable : true,align : "left"},
						  {name : 'dept21descr',index : 'dept21descr',width : 80,label:'一级部门',sortable : true,align : "left",hidden:true},
						  {name : 'dept2descr',index : 'dept2descr',width : 80,label:'二级部门',sortable : true,align : "left",hidden:true},
						  {name : 'dept1descr',index : 'dept1descr',width : 80,label:'事业部',sortable : true,align : "left",hidden:true},
						  {name : 'date',index : 'date',width : 70,label:'日期',sortable : true,align : "left",formatter: formatDate,hidden:true},
					      {name : 'weekdescr',index : 'weekdescr',width : 60,label:'星期',sortable : true,align : "left",hidden:true},
					      {name : 'addresrcust',index : 'addresrcust',width : 110,label:'新增资源客户数',sortable : true,align : "right",sum:true},	
					      {name : 'planmeasure',index : 'planmeasure',width : 70,label:'计划',sortable : true,align : "right",sum:true,excelLabel:"计划量房"},	
					      {name : 'realmeasure',index : 'realmeasure',width : 70,label:'实际',sortable : true,align : "right",sum:true,excelLabel:"实际量房"},	
					      {name : 'planarrive',index : 'planarrive',width : 70,label:'计划',sortable : true,align : "right",sum:true,excelLabel:"计划到店"},	
					      {name : 'realarrive',index : 'realarrive',width : 70,label:'实际',sortable : true,align : "right",sum:true,excelLabel:"实际到店"},	
					      {name : 'planset',index : 'planset',width : 70,label:'计划',sortable : true,align : "right",sum:true,excelLabel:"计划下定"},
					      {name : 'realset',index : 'realset',width : 70,label:'实际',sortable : true,align : "right",sum:true,excelLabel:"计划实际"},
					      {name : 'plansign',index : 'plansign',width : 70,label:'计划',sortable : true,align : "right",sum:true,excelLabel:"计划签单"},		 
					      {name : 'realsign',index : 'realsign',width : 70,label:'实际',sortable : true,align : "right",sum:true,excelLabel:"实际签单"},		 
					      {name : 'contractfee',index : 'contractfee',width : 70,label:'合同额',sortable : true,align : "right",sum:true},	
					      {name : 'connum',index : 'connum',width : 100,label:'联系客户次数',sortable : true,align : "right",sum:true},	
					      {name : 'planenum',index : 'planenum',width : 70,label:'出图数',sortable : true,align : "right",sum:true},	
					      {name : 'ontimeplanenum',index : 'ontimeplanenum',width : 90,label:'及时出图数',sortable : true,align : "right",sum:true},	
					],
					loadonce:true
				});
				$("#dataTable").jqGrid('setGroupHeaders', {
				  	useColSpanStyle: true, 
					groupHeaders:[
							{startColumnName: 'planmeasure', numberOfColumns: 2, titleText: '量房'},
							{startColumnName: 'planarrive', numberOfColumns: 2, titleText: '到店'},
							{startColumnName: 'planset', numberOfColumns: 2, titleText: '下定'},
							{startColumnName: 'plansign', numberOfColumns: 2, titleText: '签单'},
					],
				});
				$("#empCode").openComponent_employee();
			});
			function doExcel(){
				if(!$("#dateFrom").val()){
					art.dialog({
						content:"请选择一个开始时间"
					});
					return ;
				}
				if(!$("#dateTo").val()){
					art.dialog({
						content:"请选择一个结束时间"
					});
					return ;
				}
				doExcelAll("${ctx}/admin/designPlanResultAnaly/doExcel");
			}
			
			function clearForm(){
				 $("#page_form input[type='text']").val('');
			      $("#page_form select").val('');
			      $("#department1").val('');
			      $.fn.zTree.getZTreeObj("tree_department1").checkAllNodes(false);
			      $("#department2").val('');
			      $.fn.zTree.getZTreeObj("tree_department2").checkAllNodes(false);
			      $("#department_emp").val('');
			      $.fn.zTree.getZTreeObj("tree_department_emp").checkAllNodes(false);
			}
			
			function changeStatistcsMethod(){
				var statistcsMethod=$("#statistcsMethod").val();
				if(statistcsMethod=="1"){
					$("#empCode,#department_emp,#positionType").parent().removeClass("hidden");
					$("#department1,#department2,#depType").parent().addClass("hidden");
				}else if(statistcsMethod=="2"){
					$("#department2,#depType").parent().removeClass("hidden");
					$("#empCode,#department1,#department_emp,#positionType").parent().addClass("hidden");
				}else{
					$("#department1").parent().removeClass("hidden");
					$("#empCode,#department2,#department_emp,#positionType,#depType").parent().addClass("hidden");
				}
			}
			function goto_query(){
				var period=$("#period").val();
				var statistcsMethod=$("#statistcsMethod").val();
				if(!$("#dateFrom").val()){
					art.dialog({
						content:"请选择开始日期"
					});
					return ;
				}
				if(!$("#dateTo").val()){
					art.dialog({
						content:"请选择结束日期"
					});
					return ;
				}
				if(period=="1"){
					$("#dataTable").jqGrid('showCol', "weekdescr");
					$("#dataTable").jqGrid('showCol', "date");
				}else{
					$("#dataTable").jqGrid('hideCol', "weekdescr");
					$("#dataTable").jqGrid('hideCol', "date");
				}
				if(statistcsMethod=="1"){
					$("#dataTable").jqGrid('showCol', "empdescr");
					$("#dataTable").jqGrid('hideCol', "dept2descr");
					$("#dataTable").jqGrid('hideCol', "dept21descr");
					$("#dataTable").jqGrid('hideCol', "dept1descr");
				}else if(statistcsMethod=="2"){
					$("#dataTable").jqGrid('hideCol', "empdescr");
					$("#dataTable").jqGrid('showCol', "dept2descr");
					$("#dataTable").jqGrid('showCol', "dept21descr");
					$("#dataTable").jqGrid('hideCol', "dept1descr");
				}else{
					$("#dataTable").jqGrid('hideCol', "empdescr");
					$("#dataTable").jqGrid('hideCol', "dept2descr");
					$("#dataTable").jqGrid('hideCol', "dept21descr");
					$("#dataTable").jqGrid('showCol', "dept1descr");
				}
				 $("#dataTable").jqGrid(
				 "setGridParam",{
					datatype:"json",
					postData:$("#page_form").jsonForm(),
					page:1,
					url:"${ctx}/admin/designPlanResultAnaly/goJqGrid"
				}
				).trigger("reloadGrid"); 
			}
		</script>
	</head>
	<body>
		<div class="body-box-list">
			<div class="query-form" >
				<form action="" method="post" id="page_form" class="form-search" >
					<input type="hidden" name="jsonString" value="" />
					<ul class="ul-form">
						<li>
							<label>统计日期从</label>
							<input type="text" id="dateFrom" name="dateFrom" class="i-date" 
									onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
									value="<fmt:formatDate value='${customer.dateFrom }'  pattern='yyyy-MM-dd'/>"/>
						</li>
						<li>
							<label>至</label>
							<input type="text" id="dateTo" name="dateTo" class="i-date" 
									onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
									value="<fmt:formatDate value='${customer.dateFrom }'  pattern='yyyy-MM-dd'/>"/>
						</li>
						<li>		
							<label>统计周期</label>
							<select id="period" name="period" >
								<option value="1" >按日统计</option>
								<option value="2" selected>按汇总统计</option>
							</select>
						</li>
						<li>		
							<label>统计方式</label>
							<select id="statistcsMethod" name="statistcsMethod" onchange="changeStatistcsMethod()">
								<option value="1" selected>按员工</option>
								<option value="2">按部门</option>
								<option value="3">按事业部</option>
							</select>
						</li>
						<li>
							<label>员工部门</label>
							<house:MulitSelectDepatment id="department_emp" appendAllDom="true" selectedValue="${customer.department }"></house:MulitSelectDepatment>
						</li>
						<li>
							<label>职位类型</label>
							<house:xtdm id="positionType" dictCode="POSTIONTYPE" value="4"></house:xtdm>							
						</li>
						<li>
							<label>员工</label>
							<input type="text" id="empCode" name="empCode" />
						</li>
						<li class="hidden">
							<label>部门类型</label> 
							<house:xtdm id="depType" dictCode="DEPTYPE" style="width:160px;" value="2" onchange="checkDepartment1('','','',this.value)"/>
						</li>
						<li class="hidden">
							<label>部门</label>
							<house:DictMulitSelect id="department2" dictCode="" 
							sql="select Code,desc1 desc1  from dbo.tDepartment2 where Expired='F' and DepType='2' order By Department1 " 
							sqlLableKey="desc1" sqlValueKey="code"   ></house:DictMulitSelect>
						</li>
						<li class="hidden">
							<label>事业部</label>
							<house:DictMulitSelect id="department1" dictCode="" sql="select Code,desc1 desc1  from dbo.tDepartment1 where Expired='F' order By dispSeq  " 
							sqlLableKey="desc1" sqlValueKey="code"   ></house:DictMulitSelect>
						</li>
						<li>
							<button type="button" class="btn btn-sm btn-system " onclick="goto_query();"  >查询</button>
							<button type="button" class="btn btn-sm btn-system " onclick="clearForm();"  >清空</button>
						</li>
					</ul>	
				</form>
			</div>
			
			<div class="btn-panel" >
				<div class="btn-group-sm" >
					<%-- house:authorize authCode="PRJDEPTPERF_VIEW">
						<button type="button" class="btn btn-system " id="viewBtn" onclick="view()">查看</button>
					</house:authorize> --%>
					<house:authorize authCode="DESIGNPLANRESULTANALY_DOEXCEL">
						<button type="button" class="btn btn-system"  id="excelBtn" onclick="doExcel()">导出excel</button>
					</house:authorize>
				</div>
			</div>
					
			<div class="pageContent">
				<div id="content-list">
					<table id= "dataTable"></table>
				</div>
				<div id="content-list-custType">
					<table id= "custTypeDataTable"></table>
				</div>
			</div> 
		</div>
	</body>
</html>


