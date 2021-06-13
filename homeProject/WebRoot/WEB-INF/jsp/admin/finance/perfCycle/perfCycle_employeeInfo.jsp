<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<title>业绩计算--员工信息同步</title>
		<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
		<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
		<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
		<META HTTP-EQUIV="expires" CONTENT="0" />
		<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
		<%@ include file="/commons/jsp/common.jsp" %>
		<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script type="text/javascript">
		var selectRows = [];
		var selTab="tab1_li";
		/**初始化表格*/
		$(function(){
			$("#expired").val('T'); 
			$("#expired_show").prop("checked",false);
			$("#expired").val('T'); 
			$("#empForPerfExpired_show").prop("checked",false);
			$("#expired_empForPerf").val('T'); 
			var postData = $("#page_form1").jsonForm();
			Global.JqGrid.initJqGrid("employeeDataTable",{
				url:"${ctx}/admin/perfCycle/goEmployeeJqGrid",
				postData:postData,
				height:500,
	        	styleUI: "Bootstrap",
	        	rowNum:10000000,
				multiselect: true,
				onSortColEndFlag:true,
				colModel : [
					{name: "number", index: "number", width: 100, label: "员工编号", sortable: true, align: "left"},
					{name: "personchgdate", index: "personchgdate", width: 130, label: "人事调岗时间", sortable: true, align: "left", formatter: formatDate},
					{name: "namechi", index: "namechi", width: 100, label: "员工姓名", sortable: true, align: "left"},
					{name: "department1descr", index: "department1descr", width: 120, label: "新一级部门", sortable: true, align: "left"},
					{name: "olddepartment1descr", index: "olddepartment1descr", width: 120, label: "旧一级部门", sortable: true, align: "left"},
					{name: "department2descr", index: "department2descr", width: 120, label: "新二级部门", sortable: true, align: "left"},
					{name: "olddepartment2descr", index: "olddepartment2descr", width: 120, label: "旧二级部门", sortable: true, align: "left"},
					{name: "dept3descr", index: "dept3descr", width: 120, label: "新三级部门", sortable: true, align: "left"},
					{name: "olddept3descr", index: "olddept3descr", width: 120, label: "旧三级部门", sortable: true, align: "left"},
					{name: "leadleveldescr", index: "leadleveldescr", width: 110, label: "领导级别", sortable: true, align: "left"},
					{name: "isleaddescr", index: "isleaddescr", width: 110, label: "是否领导", sortable: true, align: "left"},
					{name: "managerregulardate", index: "managerregulardate", width: 130, label: "代理转正时间", sortable: true, align: "left", formatter: formatDate},
					{name: "posidescr", index: "posidescr", width: 90, label: "新职位", sortable: true, align: "left"},
					{name: "oldposidescr", index: "oldposidescr", width: 90, label: "旧职位", sortable: true, align: "left"},
					{name: "posichgdate", index: "posichgdate", width: 130, label: "代理任命时间", sortable: true, align: "left", formatter: formatDate},
					{name: "olddeptdescr", index: "olddeptdescr", width: 90, label: "原部门", sortable: true, align: "left"},
					{name: "olddeptdate", index: "olddeptdate", width: 185, label: "业绩归属原部门截止时间", sortable: true, align: "left", formatter: formatDate},
					{name: "perfbelongmode", index: "perfbelongmode", width: 130, label: "业绩归属模式", sortable: true, align: "left"},
					{name: "empexpired", index: "empexpired", width: 120, label: "员工已过期", sortable: true, align: "left"},
					{name: "empforperfexpired", index: "empforperfexpired", width: 135, label: "业绩员工已过期", sortable: true, align: "left"}
	            ],
	         gridComplete:function (){
	         	dataTableCheckBox("employeeDataTable","number");
				dataTableCheckBox("employeeDataTable","personchgdate");
				dataTableCheckBox("employeeDataTable","namechi");
				dataTableCheckBox("employeeDataTable","department1descr");
				dataTableCheckBox("employeeDataTable","olddepartment1descr");
				dataTableCheckBox("employeeDataTable","department2descr");
				dataTableCheckBox("employeeDataTable","olddepartment2descr");
				dataTableCheckBox("employeeDataTable","olddept3descr");
				dataTableCheckBox("employeeDataTable","leadleveldescr");
				dataTableCheckBox("employeeDataTable","isleaddescr");
				dataTableCheckBox("employeeDataTable","managerregulardate");
				dataTableCheckBox("employeeDataTable","posichgdate");
				dataTableCheckBox("employeeDataTable","olddeptdescr");
				dataTableCheckBox("employeeDataTable","olddeptdate");
				dataTableCheckBox("employeeDataTable","perfbelongmode");
				dataTableCheckBox("employeeDataTable","posidescr");
				dataTableCheckBox("employeeDataTable","empexpired");
				dataTableCheckBox("employeeDataTable","empforperfexpired");
				dataTableCheckBox("employeeDataTable","oldposidescr");
				dataTableCheckBox("employeeDataTable","dept3descr");
				},
				onSortCol:function(index, iCol, sortorder){
					var rows = $("#employeeDataTable").jqGrid("getRowData");
		   			rows.sort(function (a, b) {
		   				var reg = /^[0-9]+.?[0-9]*$/;
						if(reg.test(a[index])){
		   					return (a[index]-b[index])*(sortorder=="desc"?1:-1);
						}else{
		   					return a[index].toString().localeCompare(b[index].toString())*(sortorder=="desc"?1:-1);
						}  
		   			});    
		   			Global.JqGrid.clearJqGrid("employeeDataTable"); 
		   			$.each(rows,function(k,v){
						Global.JqGrid.addRowData("employeeDataTable", v);
					});
				}, 
			});
			
			Global.JqGrid.initJqGrid("leaderDataTable",{
				url:"${ctx}/admin/perfCycle/goLeaderJqGrid",
				height:500,
	        	styleUI: "Bootstrap",
	        	rowNum:10000000,
				multiselect: true,
				onSortColEndFlag:true,
				colModel : [
					{name: "code", index: "code", width: 100, label: "部门编号", sortable: true, align: "left"},
					{name: "desc2", index: "desc2", width: 130, label: "部门名称", sortable: true, align: "left"},
					{name: "leadercode", index: "leadercode", width: 120, label: "新领导编号", sortable: true, align: "left"},
					{name: "leaderdescr", index: "leaderdescr", width: 120, label: "新领导姓名", sortable: true, align: "left"},
					{name: "oldleadercode", index: "oldleadercode", width: 120, label: "原领导编号", sortable: true, align: "left"},
					{name: "oldleaderdescr", index: "oldleaderdescr", width: 120, label: "原领导姓名", sortable: true, align: "left"},
	            ],
	         gridComplete:function (){
	         	dataTableCheckBox("leaderDataTable","code");
				dataTableCheckBox("leaderDataTable","desc2");
				dataTableCheckBox("leaderDataTable","leadercode");
				dataTableCheckBox("leaderDataTable","leaderdescr");
				dataTableCheckBox("leaderDataTable","oldleadercode");
				dataTableCheckBox("leaderDataTable","oldleaderdescr");
				},
				onSortCol:function(index, iCol, sortorder){
					var rows = $("#leaderDataTable").jqGrid("getRowData");
		   			rows.sort(function (a, b) {
		   				var reg = /^[0-9]+.?[0-9]*$/;
						if(reg.test(a[index])){
		   					return (a[index]-b[index])*(sortorder=="desc"?1:-1);
						}else{
		   					return a[index].toString().localeCompare(b[index].toString())*(sortorder=="desc"?1:-1);
						}  
		   			});    
		   			Global.JqGrid.clearJqGrid("leaderDataTable"); 
		   			$.each(rows,function(k,v){
						Global.JqGrid.addRowData("leaderDataTable", v);
					});
				}, 
			});
			$("#number").openComponent_employee();
			
			$("#level").append($("<option/>").text("").attr("value",""));
			for(var i=1;i<=9;i++){
				$("#level").append($("<option/>").text(i).attr("value",i));
			}
		});
		
		function save(){
			if(selTab=="tab1_li"){
				doSyncEmployee();
			}else{
				doSyncLeader();
			} 
		}
		
		function doSyncEmployee(){
			var ids=$("#employeeDataTable").jqGrid("getGridParam", "selarrrow");
			if(ids.length == 0){
				art.dialog({
					content:"请选择要同步的员工！"
				});				
				return ;
			}
			var numbers="";
			$.each(ids,function(i,id){
				var rowData = $("#employeeDataTable").getRowData(id);
				if(numbers!=""){
					numbers+=",";
				}
				numbers+=rowData.number;
			});
			$.ajax({
				url:"${ctx}/admin/perfCycle/doSyncEmployee",
				type:"post",
				data:{numbers:numbers},
				dataType:"json",
				cache:false,
				error:function(obj){
					showAjaxHtml({"hidden": false, "msg": "获取数据出错~"});
				},
				success:function(obj){
					art.dialog({
						content : obj.msg,
						time : 1000,
						beforeunload : function() {
							closeWin();
						}
					});
				}
			}); 
		}
		
		function doSyncLeader(){
			var ids=$("#leaderDataTable").jqGrid("getGridParam", "selarrrow");
			if(ids.length == 0){
				art.dialog({
					content:"请选择要同步的员工！"
				});				
				return ;
			}
			var codes="";
			$.each(ids,function(i,id){
				var rowData = $("#leaderDataTable").getRowData(id);
				if(codes!=""){
					codes+=",";
				}
				codes+=rowData.code;
			});
			$.ajax({
				url:"${ctx}/admin/perfCycle/doSyncLeader",
				type:"post",
				data:{codes:codes},
				dataType:"json",
				cache:false,
				error:function(obj){
					showAjaxHtml({"hidden": false, "msg": "获取数据出错~"});
				},
				success:function(obj){
					art.dialog({
						content : obj.msg,
						time : 1000,
						beforeunload : function() {
							closeWin();
						}
					});
				}
			}); 
		}
		
		function tableSelected(flag){
			if(selTab=="tab1_li"){
				selectAll("employeeDataTable", flag);
			}else{
				selectAll("leaderDataTable", flag);
			}
		}
		
		function hideExpiredDept(obj){
			if ($(obj).is(':checked')){
				$('#expired_dept').val('F');
			}else{
				$('#expired_dept').val('T');
			}
		}
		
		function hideExpiredEmpForPerf(obj){ 
			if ($(obj).is(':checked')){
				$('#expired_empForPerf').val('F');
			}else{
				$('#expired_empForPerf').val('T');
			}
		}
		
		function changeTab(tabId){
			selTab=tabId;
		}
		
		function doExcel(){
			if(selTab=="tab1_li"){
				doExcelNow('员工信息','employeeDataTable','page_form','1');
			}else{
				doExcelNow('部门领导','leaderDataTable','page_form','1');
			}
		}
	</script>
	</head>
	    
	<body>
		<div class="query-form" hidden>
			<form action="" method="post" id="page_form" >
				<input type="hidden" name="jsonString" value="" />
			</form>
		</div>
		<div class="body-box-form">
			<div class="panel panel-system">
			    <div class="panel-body">
			    	<div class="btn-group-xs" style="float:left">
	    				<button id="saveBut" type="button" class="btn btn-system" onclick="save()">保存</button>
	    				<button id="selAll" type="button" class="btn btn-system" onclick="tableSelected(true)">全选</button>
	    				<button id="selNone" type="button" class="btn btn-system" onclick="tableSelected(false)">不选</button>
	    				<button id="excel" type="button" class="btn btn-system" onclick="doExcel()">导出excel</button>
	    				<button id="closeBut" type="button" class="btn btn-system" onclick="closeWin(false)">关闭</button>
					</div>
				</div>
			</div>
		<div  class="container-fluid" >  
		     <ul class="nav nav-tabs" >
		        <li id="tab1_li" class="active" ><a href="#tab1" data-toggle="tab" onclick="changeTab('tab1_li')">员工信息</a></li>  
		        <li id="tab2_li" class="" ><a href="#tab2" data-toggle="tab" onclick="changeTab('tab2_li')">部门领导</a></li> 
		    </ul>  
		    <div class="tab-content">  
				<div id="tab1"  class="tab-pane fade in active" > 
				<div class="query-form">
					<form action="" method="post" id="page_form1" class="form-search">
						<input type="hidden" id="expired" name="expired" value=""/>
						<input type="hidden" id="expired_empForPerf" name="expired_empForPerf" value=""/>
						<ul class="ul-form">
							<li><label>员工编号</label> <input type="text" id="number"
								name="number" />
							</li>
							<li><label>人事调岗时间从</label> <input type="text" id="personChgDateFrom"
								name="personChgDateFrom" class="i-date" style="width:160px;"
								onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
							</li>
							<li><label>至</label> <input type="text" id="personChgDateTo"
								name="personChgDateTo" class="i-date" style="width:160px;"
								onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
							</li>
							<li class="search-group-shrink"><input type="checkbox" id="expired_show" name="expired_show" value="${perfCycle.expired}" 
									onclick="hideExpired(this)" ${perfCycle.expired!='T' ?'checked':'' }/>
								<p>隐藏员工过期</p>&nbsp&nbsp&nbsp&nbsp
								<input type="checkbox" id="empForPerfExpired_show" name="empForPerfExpired_show" value="${perfCycle.expired_empForPerf}"
									onclick="hideExpiredEmpForPerf(this)" ${perfCycle.expired_empForPerf!='T' ?'checked':'' }/>
								<p>隐藏业绩员工过期</p>
								<button type="button" class="btn  btn-system btn-sm"
									onclick="goto_query('employeeDataTable','page_form1');">查询</button>
								<button type="button" class="btn btn-system btn-sm"
									onclick="clearForm();">清空</button>
							</li>
						</ul>
					</form>
				</div>
					<table id="employeeDataTable"></table>
				</div>  
	
				<div id="tab2"  class="tab-pane fade " > 
					<div class="query-form">
						<form action="" method="post" id="page_form2" class="form-search">
						<ul class="ul-form">
							<li>
								<label>部门名称</label> 
								<input type="text" id="departmentDescr" name="departmentDescr" style="width:160px;" value="${perfCycle.departmentDescr }"/>
							</li>
							<li>
								<label>部门级别</label> 
								<select id="level" name="level"></select>
							</li>
							<input type="hidden" id="expired_dept" name="expired_dept" value=""/>
							<li class="search-group-shrink"><input type="checkbox"
								id="expired_dept_show" name="expired_dept_show" value="${perfCycle.expired_dept}"
								onclick="hideExpiredDept(this)" ${perfCycle.expired_dept!='T' ?'checked':'' }/>
								<p>隐藏过期</p>
								<button type="button" class="btn  btn-system btn-sm"
									onclick="goto_query('leaderDataTable','page_form2');">查询</button>
								<button type="button" class="btn btn-system btn-sm"
									onclick="clearForm();">清空</button>
							</li>
						</ul>
					</form>
					</div>
					<table id="leaderDataTable"></table>
				</div>
			</div>	
		</div>
	</div>
	</body>
</html>
