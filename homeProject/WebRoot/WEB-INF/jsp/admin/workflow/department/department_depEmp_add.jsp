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
var selectRows = [];
function clearForm(){
	$("#page_form input[type='text']").val('');
	$("#page_form select").val('');
}

/**初始化表格*/
$(function(){
        //初始化表格
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/employee/goJqGrid",
			height:400,
			postData: $("#page_form").jsonForm(),
			styleUI: 'Bootstrap',
			multiselect: true,
			rowNum:1000000,
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
              {name : 'thismonthcount',index : 'thismonthcoun',width : 80,label:'本月排单量',sortable : true,align : "right",hidden: true}
            ],
		});
		//全选
		$("#selAll").on("click",function(){
			Global.JqGrid.jqGridSelectAll("dataTable",true);
		});
		//全不选
		$("#selNone").on("click",function(){
			Global.JqGrid.jqGridSelectAll("dataTable",false);
		});
		 //改写窗口右上角关闭按钮事件
   		var titleCloseEle = parent.$("div[aria-describedby=dialog_sendFeeImport]").children(".ui-dialog-titlebar");
   		$(titleCloseEle[0]).children("button").remove();
   		$(titleCloseEle[0]).append("<button type=\"button\" class=\"ui-button ui-widget ui-state-default ui-corner-all ui-button-icon-only ui-dialog-titlebar-close\" role=\"button\" "
   									+"title=\"Close\"><span class=\"ui-button-icon-primary ui-icon ui-icon-closethick\" ></span><span class=\"ui-button-text\">Close</span></button>");
   		$(titleCloseEle[0]).children("button").on("click", closeAndReturn);
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
function save(){
	var ids=$("#dataTable").jqGrid("getGridParam", "selarrrow");
	var numbers="";
	if(ids.length == 0){
		art.dialog({
			content:"请选择数据后保存"
		});				
		return ;
	}
	$.each(ids,function(i,id){
		var rowData = $("#dataTable").jqGrid("getRowData", id);
		if(numbers != ""){
			numbers += ",";
		}
		numbers += rowData.number;
	});
	$.ajax({
		url:"${ctx}/admin/department/doSave",
		type: 'post',
		data: {m_umState:"EA",code:"${department.code}",empCode:numbers},
		dataType: 'json',
		cache: false,
		error: function(obj){
			showAjaxHtml({"hidden": false, "msg": '保存数据出错~'});
	    },
	    success: function(obj){
	    	if(obj.rs){
	    		art.dialog({
					content: obj.msg,
					time: 1000,
					beforeunload: function () {
	    				closeWin();
				    }
				});
	    	}else{
	    		$("#_form_token_uniq_id").val(obj.token.token);
	    		art.dialog({
					content: obj.msg,
				});
	    	}
	    }
	 });
}
function closeAndReturn(){
   	closeWin();
}
function tableSelected(flag){
	selectAll("dataTable", flag);
}
function doQuery(){
	$("#dataTable").jqGrid("setGridParam",{
		datatype:"json",
   		postData:$("#page_form").jsonForm(),
   		page:1,
   		sortname:''
  	}).trigger("reloadGrid");
}
</script>
</head>
    
<body>
	<div class="body-box-list">
 		<div class="panel panel-system">
		    <div class="panel-body">
		    	<div class="btn-group-xs" style="float:left">
    				<button id="saveBut" type="button" class="btn btn-system" onclick="save()">保存</button>
    				<button id="selAll" type="button" class="btn btn-system" >全选</button>
    				<button id="selNone" type="button" class="btn btn-system" >全不选</button>
    				<button id="closeBut" type="button" class="btn btn-system" onclick="closeAndReturn()">关闭</button>
				</div>
				<input hidden="true" id="dataTableExists_selectRowAll"
						name="dataTableExists_selectRowAll" value="">
			</div>
		</div> 
		<div class="query-form">
		       <form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame">
					<ul class="ul-form">
					<li>	
						<label>员工编号</label>
						<input type="text" id="number" name="number" value="${employee.number }" />
					</li>
					<li>		
						<label>姓名</label>
						<input type="text" id="nameChi" name="nameChi"  value="${employee.nameChi }" />
					</li>
					<li>
						<label>职位</label>
						<house:position id="position" value="${employee.position }"></house:position>
					</li>
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
			</div> 
		</div> 
	</div>
</body>
</html>
