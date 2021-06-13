<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<title>来客统计</title>
	<%@ include file="/commons/jsp/common.jsp" %>
<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
<script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>

<script type="text/javascript">

//导出EXCEL
function doExcel(){
	var url = "${ctx}/admin/lktj/doExcelchecklktj";
	var tableId = 'dataTable';
	if ($("#StatistcsMethod").val() == '1') {		
		tableId='dataTable';
	} else {
		tableId='dataTableGroupBycheckman';
	} 
	doExcelAll(url, tableId);
}    

function goto_query(){
	var tableId ;	
    if ($("#StatistcsMethod").val() == '1') {		
		tableId='dataTable';
	} else {
		tableId='dataTableGroupBycheckman';
	}
	$("#"+tableId).jqGrid("setGridParam",{url:"${ctx}/admin/lktj/lktjMx",datatype:'json',postData:$("#page_form").jsonForm(),page:1,fromServer: true}).trigger("reloadGrid");
	change();
}
			
/**初始化表格*/
$(function(){
$("#builderCode").openComponent_builder();
	Global.JqGrid.initJqGrid("dataTable",{    					     //明细
        height: $(document).height() - $("#content-list").offset().top - 100,
		styleUI: 'Bootstrap',
		colModel : [
				{name: "code", index: "code", width: 100, label: "客户编号", sortable: true, align: "left",count:true},
				{name: "dispatchdate", index: "dispatchdate", width: 72, label: "派单时间", sortable: true, align: "left", formatter: formatTime},
				{name: "crtdate", index: "crtdate", width: 72, label: "到店时间", sortable: true, align: "left", formatter: formatTime},
				{name: "descr", index: "descr", width: 70, label: "客户名称", sortable: true, align: "left"},
				{name: "mobile1", index: "mobile1", width: 100, label: "联系方式", sortable: true, align: "left", hidden: true},
				{name: "address", index: "address", width: 180, label: "楼盘地址", sortable: true, align: "left"},
				{name: "regiondescr", index: "regiondescr", width: 180, label: "一级区域", sortable: true, align: "left"},
				{name: "builderdescr", index: "builderdescr", width: 120, label: "项目名称", sortable: true, align: "left"},
				{name: "delivdate", index: "delivdate", width: 72, label: "交房时间", sortable: true, align: "left", formatter: formatTime},
				{name: "area", index: "area", width: 50, label: "面积", sortable: true, align: "right"},
				{name: "designmandescr", index: "designmandescr", width: 90, label: " 设计师", sortable: true, align: "left"},
				{name: "businessmandescr", index: "businessmandescr", width: 90, label: "业务员", sortable: true, align: "left"},
				{name: "singlemandescr", index: "singlemandescr", width: 91, label: "翻单员", sortable: true, align: "left"},
				{name: "depart1descr", index: "depart1descr", width: 100, label: "一级部门", sortable: true, align: "left"},
				{name: "depart2descr2", index: "depart2descr2", width: 100, label: "二级部门", sortable: true, align: "left"},
				{name: "depart3descr", index: "depart3descr", width: 100, label: "三级部门", sortable: true, align: "left"},
				{name: "sourcedescr", index: "sourcedescr", width: 100, label: "客户来源", sortable: true, align: "left"},
				{name: "custnetcnldescr", index: "custnetcnldescr", width: 100, label: "网络渠道", sortable: true, align: "left"}
	          ],	               	 			         
	});
    Global.JqGrid.initJqGrid("dataTableGroupBycheckman",{   		
        height: $(document).height() - $("#content-list").offset().top - 100,
		styleUI: 'Bootstrap',
		colModel : [
				{name: "depart1descr", index: "depart1descr", width: 130, label: "一级部门", sortable: true, align: "left",count:true},
				{name: "depart2descr2", index: "depart2descr2", width: 130, label: "二级部门", sortable: true, align: "left"},
				{name: "teamdescr", index: "teamdescr", width: 130, label: "团队", sortable: true, align: "left",count:true},
				{name: "builderdescr", index: "builderdescr", width: 170, label: "项目名称", sortable: true, align: "left",count:true},
				{name: "regiondescr", index: "regiondescr", width: 96, label: "区域", sortable: true, align: "left"},
				{name: "delivdate", index: "delivdate", width: 107, label: "交房时间", sortable: true, align: "left", formatter: formatTime},
				{name: "buildertypedescr", index: "buildertypedescr", width: 90, label: "项目类型", sortable: true, align: "left"},
				{name: "custtypedescr", index: "custtypedescr", width: 130, label: "客户类型", sortable: true, align: "left",count:true},
				{name: "custcount", index: "custcount", width: 100, label: "来客数", sortable: true, align: "right", sum: true}
           ],              
        	rowNum:100000,  
			pager :'1',  
			gridComplete:function(){
			    var tableId='dataTableGroupBycheckman';
		        if ($("#StatistcsMethod").val() == '2') {				//<option value="2">按一级部门</option>
		        	$("#"+tableId).jqGrid('showCol', "depart1descr");
		        	$("#"+tableId).jqGrid('hideCol', "depart2descr2");
					$("#"+tableId).jqGrid('hideCol', "teamdescr");		
					$("#"+tableId).jqGrid('hideCol', "builderdescr");
					$("#"+tableId).jqGrid('hideCol', "regiondescr");
					$("#"+tableId).jqGrid('hideCol', "delivdate");
					$("#"+tableId).jqGrid('hideCol', "buildertypedescr");	
					$("#"+tableId).jqGrid('hideCol', "custtypedescr");
					$("#"+tableId).jqGrid('showCol', "custcount");  
			   	} else if ($("#StatistcsMethod").val() == '3') {		//<option value="3">按二级部门</option>
					$("#"+tableId).jqGrid('showCol', "depart1descr");
		        	$("#"+tableId).jqGrid('showCol', "depart2descr2");
					$("#"+tableId).jqGrid('hideCol', "teamdescr");		
					$("#"+tableId).jqGrid('hideCol', "builderdescr");
					$("#"+tableId).jqGrid('hideCol', "regiondescr");
					$("#"+tableId).jqGrid('hideCol', "delivdate");
					$("#"+tableId).jqGrid('hideCol', "buildertypedescr");	
					$("#"+tableId).jqGrid('hideCol', "custtypedescr");
					$("#"+tableId).jqGrid('showCol', "custcount");  	
				} else if ($("#StatistcsMethod").val() == '4') {		//<option value="4">按团队</option>
					$("#"+tableId).jqGrid('hideCol', "depart1descr");
		        	$("#"+tableId).jqGrid('hideCol', "depart2descr2");
					$("#"+tableId).jqGrid('showCol', "teamdescr");		
					$("#"+tableId).jqGrid('hideCol', "builderdescr");
					$("#"+tableId).jqGrid('hideCol', "regiondescr");
					$("#"+tableId).jqGrid('hideCol', "delivdate");
					$("#"+tableId).jqGrid('hideCol', "buildertypedescr");	
					$("#"+tableId).jqGrid('hideCol', "custtypedescr");
					$("#"+tableId).jqGrid('showCol', "custcount");  	
				} else if ($("#StatistcsMethod").val() == '5') {		//<option value="5">按项目名称</option>
					$("#"+tableId).jqGrid('hideCol', "depart1descr");
		        	$("#"+tableId).jqGrid('hideCol', "depart2descr2");
					$("#"+tableId).jqGrid('hideCol', "teamdescr");		
					$("#"+tableId).jqGrid('showCol', "builderdescr");
					$("#"+tableId).jqGrid('showCol', "regiondescr");
					$("#"+tableId).jqGrid('showCol', "delivdate");
					$("#"+tableId).jqGrid('showCol', "buildertypedescr");	
					$("#"+tableId).jqGrid('hideCol', "custtypedescr");
					$("#"+tableId).jqGrid('showCol', "custcount");  
		        }else if ($("#StatistcsMethod").val() == '6') {			//<option value="6">按客户类型</option>
					$("#"+tableId).jqGrid('hideCol', "depart1descr");
		        	$("#"+tableId).jqGrid('hideCol', "depart2descr2");
					$("#"+tableId).jqGrid('hideCol', "teamdescr");		
					$("#"+tableId).jqGrid('hideCol', "builderdescr");
					$("#"+tableId).jqGrid('hideCol', "regiondescr");
					$("#"+tableId).jqGrid('hideCol', "delivdate");
					$("#"+tableId).jqGrid('hideCol', "buildertypedescr");	
					$("#"+tableId).jqGrid('showCol', "custtypedescr");
					$("#"+tableId).jqGrid('showCol', "custcount");  
		        }
		        
      		}          
	}); 
	
	$("#content-list-groupBycheckman").hide(); 
});

function clearForm(){
	$("#page_form input[type='text']").val('');
	$("#page_form select").val('');
	$("#openComponent_employee_projectMan").val('');
	$("#openComponent_employee_checkMan").val('');
	$("#Department2").val('');
	$("#Department2Check").val('');
	$("#address").val('');
	$("#isModify").val('');
	$("#isModifyComplete").val('');
} 

function change(){ 
	var tableId;		
	if ($("#StatistcsMethod").val() == '1') {		
		tableId = 'dataTable';						
		$("#content-list").show();					
		$("#content-list-groupBycheckman").hide();
	} else{
		tableId = 'groupBycheckman';
		$("#content-list").hide();
		$("#content-list-groupBycheckman").show();
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
							<label>一级部门</label>
							<house:department1 id="department1"  onchange="changeDepartment1(this,'department2','${ctx }')"></house:department1>
						</li>
						<li>
							<label>二级部门</label>
							<house:department2 id="department2" dictCode="${customer.department1 }"  onchange="changeDepartment2(this,'department3','${ctx }')" ></house:department2>
						</li>
						<li>
							<label>三级部门</label>
							<house:department3 id="department3" dictCode="${customer.department2 }"   ></house:department3>
						</li>
						<li>
							<label>客户类型</label>
							<house:DictMulitSelect id="custType" dictCode="" sql="select Code,  Desc1 Descr from tcustType order by  dispSeq " 
							sqlValueKey="Code" sqlLableKey="Descr"  ></house:DictMulitSelect>
						</li>
						<li>
							<label >统计开始日期</label>
							<input type="text" id="beginDate" name="beginDate" class="i-date"  
							onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
							value="<fmt:formatDate value='${customer.beginDate}' pattern='yyyy-MM-dd'/>" />
						</li>
						<li>
							<label >统计结束日期</label>
							<input type="text" id="endDate" name="endDate" class="i-date"  onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
							value="<fmt:formatDate value='${customer.endDate}' pattern='yyyy-MM-dd'/>" />
						</li>	
						<li> 
						    <label>统计方式</label>
							<select id="role" name="role" >								
								<option value="1">按设计师统计</option>
								<option value="2">按业务员统计</option>
							</select>
						</li>
						<li>
							<label >团队</label>
								<house:dict id="team" dictCode="" sql="select rtrim(Code) Code,rtrim(Code)+' '+Desc1 Descr
								from tTeam where Expired='F' order by Code" 
								sqlValueKey="Code" sqlLableKey="Descr"  value="${customer.team}" ></house:dict>
							</label>
						</li>						
						<li> 
						    <label>统计方式</label>
							<select id="StatistcsMethod" name="StatistcsMethod" >								
								<option value="1">明细</option>
								<option value="2">按一级部门</option>
								<option value="3">按二级部门</option>
								<option value="4">按团队</option>
								<option value="5">按项目名称</option>
								<option value="6">按客户类型</option>
							</select>
						</li>	
						<li >
							<label>项目名称</label>
							<input type="text" id="builderCode" name="builderCode"  value="${customer.builderCode}" readonly="true"/>
						</li>
						<li>
							<label>一级区域</label>
							<house:DictMulitSelect id="region" dictCode="" sql="select Code, Descr from tRegion where expired='F'  order by Code " 
							sqlValueKey="Code" sqlLableKey="Descr"  ></house:DictMulitSelect>
						</li>
						<li>
							<label>客户来源</label>
							<house:xtdm id="source" dictCode="CUSTOMERSOURCE" value="${customer.source }"></house:xtdm>
						</li>
						<li>
							<label >网络渠道</label>
								<house:dict id="custNetCnl" dictCode="" sql="select rtrim(Code) Code,rtrim(Code)+' '+Descr Descr
								from tCustNetCnl where Expired='F' order by DispSeq" 
								sqlValueKey="Code" sqlLableKey="Descr"  value="${customer.custNetCnl}" ></house:dict>
							</label>
						</li>	
						
					</ul>		
				
					<ul class="ul-form">
					<li id="loadMore" >
						<button type="button"  class="btn btn-sm btn-system " onclick="goto_query();"  >查询</button>
					</li>					
				</ul>
			</form>
		</div>
		<div class="btn-panel" >
		       <div class="btn-group-sm"  >
	                    <button type="button" class="btn btn-system " onclick="doExcel()">导出excel</button>
				</div>
			<div id="content-list">               <!-- 明细 -->
				<table id= "dataTable"></table>  
				<div id="dataTablePager"></div>
			</div>
			<div id="content-list-groupBycheckman"> <!-- 巡检人 -->
				<table id= "dataTableGroupBycheckman"></table> 
				<div id="dataTableGroupBycheckman"></div>
			</div>
		</div> 
	</div>
</body>
</html>


