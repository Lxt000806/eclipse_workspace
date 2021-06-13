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
	$("#page_form input[type='text']").val('');
	$("#page_form select").val('');
}

/**初始化表格*/
$(function(){
		console.log($("#fp__tWfCust_TravelExpenseClaim__0__Partner").val());
        //初始化表格
		Global.JqGrid.initJqGrid("dataTable",{
			//url:"${ctx}/admin/employee/goJqGrid",
			height:$(document).height()-$("#content-list").offset().top-85,
			postData: $("#page_form").jsonForm(),
			styleUI: 'Bootstrap',
			multiselect:true,
			colModel : [
			  {name : 'number',index : 'number',width : 100,label:'员工编号',sortable : true,align : "left"},
		      {name : 'namechi',index : 'namechi',width : 100,label:'中文名',sortable : true,align : "left"},
		      {name : 'nameeng',index : 'nameeng',width : 100,label:'英文名',sortable : true,align : "left"},
		      {name : 'posdesc',index : 'posdesc',width : 100,label:'职位',sortable : true,align : "left"},
		      {name : 'prjleveldescr',index : 'prjleveldescr',width : 60,label:'星级',sortable : true,align : "left",hidden: true},
		      {name : 'department1descr',index : 'department1descr',width : 100,label:'一级部门',sortable : true,align : "left"},
		      {name : 'department2descr',index : 'department2descr',width : 100,label:'二级部门',sortable : true,align : "left"},
		      {name : 'department3descr',index : 'department3descr',width : 100,label:'三级部门',sortable : true,align : "left"},
		      {name : 'phone',index : 'phone',width : 100,label:'手机号码',sortable : true,align : "left",hidden: true},
		      {name : 'department1',index : 'department1',width : 100,label:'一级部门',sortable : true,align : "left",hidden: true},
		      {name : 'department2',index : 'department2',width : 100,label:'二级部门',sortable : true,align : "left",hidden: true},
		      {name : 'department3',index : 'department3',width : 80,label:'三级部门',sortable : true,align : "left",hidden: true},
           	  {name : 'scenedesicustcount',index : 'scenedesicustcount',width : 80,label:'在建套数',sortable : true,align : "right",hidden: true},
              {name : 'nowcount',index : 'nowcount',width : 80,label:'当前持单量',sortable : true,align : "right",hidden: true},
              {name : 'thismonthcount',index : 'thismonthcoun',width : 80,label:'本月排单量',sortable : true,align : "right",hidden: true},
              {name : 'czybh',index : 'czybh',width : 80,label:'操作员编号',sortable : true,align : "right",hidden:true},
              {name : 'leadercode',index : 'leadercode',width : 80,label:'部门领导',sortable : true,align : "right",hidden:true},
              {name : 'leaderdescr',index : 'leaderdescr',width : 80,label:'部门领导',sortable : true,align : "right",hidden:true},
              {name : 'busidrc',index : 'busidrc',width : 80,label:'业务主任',sortable : true,align : "right",hidden:true},
              {name : 'busidrcdescr',index : 'busidrcdescr',width : 80,label:'业务主任',sortable : true,align : "right",hidden:true},
              {name : 'deptypedescr',index : 'deptypedescr',width : 80,label:'部门类型',sortable : true,align : "right",hidden:true},
              {name : 'expensedate',index : 'expensedate',width : 80,label:'预支最后修改时间',sortable : true,align : "right",formatter:formatDate,hidden:true},
            ],
		});
		/* 现场设计师在建套数计算  */
		if("${employee.sceneDesiCustCount}"!= "" ){
			$("#dataTable").jqGrid("showCol", "scenedesicustcount");
	    }
	    /* 项目经理显示  */
		if("${employee.prjManCustCount}"!= "" ){
		 	$("#dataTable").jqGrid("showCol", "prjleveldescr");
			$("#dataTable").jqGrid("showCol", "nowcount");
			$("#dataTable").jqGrid("showCol", "thismonthcount"); 
			$("#dataTable").jqGrid("hideCol", "department3descr");
			$('.emp_show').hide();
	   	    $('.prj_show').show(); 
	    }
		$("#nameChi").focus();
});
function goto_query(){
	var postData = $("#page_form").jsonForm();
	$("#dataTable").jqGrid("setGridParam", {
    	url:"${ctx}/admin/employee/goJqGrid",
    	postData: postData,
    	page:1
  	}).trigger("reloadGrid");
}

function getEmpAuthority(empCode){
	var hasAuthority = false;
	$.ajax({
		url:"${ctx}/admin/employee/getEmpAuthority",
		type:'post', 
		data:{empCode:empCode},
		dataType:'json',
		async:false,
		cache:false,
		error:function(obj){
			showAjaxHtml({"hidden": false, "msg": '获取数据出错~'});
		},
		success:function(obj){
			hasAuthority = obj ;
		}
	});
	return hasAuthority;
}
function save(){
	var ids = $("#dataTable").jqGrid('getGridParam', 'selarrrow');
	var selectRows = [];
	$.each(ids,function(k,id){
		var row = $("#dataTable").jqGrid('getRowData', id);
		selectRows.push(row);
	});
	Global.Dialog.returnData = selectRows;
	closeWin();
}
</script>
</head>
    
<body>
	<div class="body-box-list">
		<div class="panel panel-system">
			<div class="panel-body" >
				<div class="btn-group-xs" >
					<button type="button" class="btn btn-system" onclick="save()">完成</button>
					<button type="button" class="btn btn-system" onclick="closeWin()">关闭</button>
				</div>
			</div>
		</div>
			<div class="query-form">
		       <form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame">
				<input type="hidden" id="sceneDesiCustCount" name="sceneDesiCustCount" value="${employee.sceneDesiCustCount}" />
				<input type="hidden" id="prjManCustCount" name="prjManCustCount" value="${employee.prjManCustCount}" />
				<input type="hidden" id="expired" name="expired" value="${employee.dep1Type }" />
				<input type="hidden" id="taskKey" name="taskKey" value="${employee.taskKey }" />
				<input type="hidden" id="wfProcNo" name="wfProcNo" value="${employee.wfProcNo }" />
				<input type="hidden" id="custCode" name="custCode" value="${employee.custCode }" />
				<input type="hidden" id="role" name="role" value="${employee.role }" />
				<input type="hidden" id="startMan" name="startMan" value="${employee.startMan }" />
				<input type="hidden" id="empAuthority" name="empAuthority" value="${employee.empAuthority }" />
				<input type="hidden" id="status" name="status" value="${employee.status }" />
				<input type="hidden" id="positionType" name="positionType" value="${employee.positionType}" /><!-- 增加职位类型查询 add by zb on 20191031 -->
				<input type="hidden" id="isEmpForPerf" name="isEmpForPerf" value="${employee.isEmpForPerf}" />	
				<input type="hidden" id="isManager" name="isManager" value="${employee.isManager}" />	
					<ul class="ul-form">
					<li>	
						<label>员工编号</label>
						<input type="text" id="number" name="number" value="${employee.number }" />
					</li>
					<li>		
						<label>姓名</label>
						<input type="text" id="nameChi" name="nameChi"  value="${employee.nameChi }" />
					</li>
	    			<c:if test="${employee.onlyPrjMan !='1'}">
						<li>
							<label>职位</label>
							<house:position id="position" value="${employee.position }"></house:position>
						</li>
					</c:if>	
					<c:if test="${employee.onlyPrjMan =='1'}">
						<li>
							<label>职位</label>
							<house:dict id="position" dictCode=""
	                                  sql="select code ,desc2 descr  from tPosition where Type = '6' "
	                                  sqlLableKey="descr" sqlValueKey="code" />
						</li>	
					</c:if>	  
					
					<li class="emp_show">
						<label>一级部门</label>
						<house:department1 id="department1" onchange="changeDepartment1(this,'department2','${ctx }')" value="${employee.department1 }"></house:department1>
					</li>
					<li class="emp_show" >
						<label>二级部门</label>
						<house:department2 id="department2" dictCode="${employee.department1 }" value="${employee.department2 }" onchange="changeDepartment2(this,'department3','${ctx }')"></house:department2>
					</li>
					<li class="emp_show" >
						<label>三级部门</label>
						<house:department3 id="department3" dictCode="${employee.department2 }" value="${employee.department3 }"></house:department3>
					</li>
					<li class="prj_show" hidden="true">
						<label>工程部</label>
						<house:DictMulitSelect id="prjDepartment2" dictCode="" sql="select a.Code, a.desc1+' '+isnull(e.NameChi,'') desc1  from dbo.tDepartment2 a
								left join tEmployee e on e.Department2=a.Code and e.IsLead='1' and e.LeadLevel='1' and e.expired='F'
								 where  a.deptype='3' and a.Expired='F' order By dispSeq " 
						sqlLableKey="desc1" sqlValueKey="code" selectedValue="${employee.prjDepartment2 }"  ></house:DictMulitSelect>
					</li>
					<li class="prj_show" hidden="true">
						<label>项目经理星级</label>
						<house:dict id="prjLevel" dictCode=""
                                  sql="select code, descr from tPrjLevel where Expired='F' order by code  "
                                  sqlLableKey="descr" sqlValueKey="code" value="${employee.prjLevel}"  />
					</li>	
					<li class="search-group" >
						<input type="checkbox" id="expired_show" name="expired_show"
							value="${employee.expired}" onclick="hideExpired(this)"
							${employee.expired!='T' ?'checked':'' }/><p>隐藏过期</p>
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


