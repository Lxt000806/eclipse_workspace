<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<!DOCTYPE html>
<html>
<head>
	<title>搜寻——部门编号</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
<script type="text/javascript"> 
$(function(){
    $("#level").append($("<option/>").text("").attr("value",""));
	for(var i=1;i<=9;i++){
		$("#level").append($("<option/>").text(i).attr("value",i));
	}
	if("${department.higherLevel }"!=""){
		$("#level").val("${department.higherLevel }").attr("disabled",true);
	}
	var postData = $("#page_form").jsonForm();
	//初始化表格
	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/department/goJqGrid",
		postData:postData,
		height:$(document).height()-$("#content-list").offset().top-70,
		styleUI: 'Bootstrap',
		colModel : [
			  {name : 'code',index : 'code',width : 80,label:'部门编号',sortable : true,align : "left"},
			  {name : 'cmpcode',index : 'cmpcode',width : 80,label:'公司编号',sortable : true,align : "left"},
		      {name : 'desc2',index : 'desc2',width : 100,label:'部门名称',sortable : true,align : "left"},
		      {name : 'higherdepdescr',index : 'higherdepdescr ',width : 100,label:'上级部门',sortable : true,align : "left"},
		      {name : 'deptypedescr',index : 'deptypedescr ',width : 80,label:'部门类型',sortable : true,align : "left"},
		      {name : 'leadercode',index : 'leadercode',width : 80,label:'部门领导',sortable : true,align : "left", hidden: true},
		      {name : 'leadername',index : 'leadername ',width : 80,label:'部门领导',sortable : true,align : "left"},
		      {name : 'dispseq',index : 'dispseq',width : 70,label:'显示顺序',sortable : true,align : "right"},
		      {name : 'plannum',index : 'plannum',width : 70,label:'编制数',sortable : true,align : "right"},
		      {name : 'busitypedescr',index : 'busitypedescr',width : 100,label:'业务类型',sortable : true,align : "left"},
		      {name : 'path',index : 'path',width : 120,label:'路径',sortable : true,align : "left"},
		      {name : 'isactualdescr',index : 'isactualdescr',width : 90,label:'是否实际部门',sortable : true,align : "left"},
		      {name : 'lastupdate',index : 'lastupdate',width : 150,label:'最后修改时间',sortable : true,align : "left",formatter: formatTime},
		      {name : 'lastupdatedby',index : 'lastupdatedby',width : 100,label:'修改人',sortable : true,align : "left"},
		      {name : 'expired',index : 'expired',width : 100,label:'过期标志',sortable : true,align : "left"},
		      {name : 'actionlog',index : 'actionlog',width : 100,label:'操作日志',sortable : true,align : "left"}
		],
		  ondblClickRow:function(rowid,iRow,iCol,e){
				var selectRow = $("#dataTable").jqGrid('getRowData', rowid);
            	Global.Dialog.returnData = selectRow;
            	Global.Dialog.closeDialog();
            }
	});
	if("${department.isEmp}" =="1"){
		$("#isActual").attr("disabled",true);
	}
});

</script>
</head>
	<body>
		<div class="body-box-list">
			<div class="query-form">
				<form action="" method="post" id="page_form" class="form-search">
					<input type="hidden" id="expired"  name="expired" value="F"/>
					<input type="hidden" id="code"  name="code" value="${department.code}"/>
					<input type="hidden" id="isEmp"  name="isEmp" value="${department.isEmp}"/>
					<input type="hidden" id="isOutChannel" name="isOutChannel" value="${department.isOutChannel}"/>
					<input type="hidden" id="higherLevel"  name="higherLevel" value="${department.higherLevel}"/>
					<input type="hidden" id="salfDept"  name="salfDept" value="${department.salfDept}"/>
					
					<ul class="ul-form">
					<li>
						<label>部门名称</label> 
						<input type="text" id="desc2" name="desc2" style="width:160px;" value="${department.desc2}"/>
					</li>
					<li>
						<label>父部门名称</label> 
						<input type="text" id="parentDep" name="parentDep" style="width:160px;" value="${department.parentDep}"/>
					</li>
					<li>
						<label>部门级别</label> 
						<select id="level" name="level"></select>
					</li>
					<li>
						<label>是否实际部门</label>
						<house:xtdm id="isActual" dictCode="YESNO" style="width:160px;" value="1" />
					</li>
					<li>
						<input type="checkbox" id="expired_show" name="expired_show" value="${department.expired }" onclick="hideExpired(this)" ${department.expired!='T' ?'checked':'' }/>
						<label for="expired_show" style="margin-left: -3px;width: 50px;">隐藏过期</label>
						<button type="button" class="btn  btn-sm btn-system " onclick="goto_query();">查询</button>
						<button type="button" class="btn btn-sm btn-system " onclick="clearForm();">清空</button>
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
