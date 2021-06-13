<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<!DOCTYPE html>
<html>
<head>
<title>搜寻二级部门</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp" %>
<script type="text/javascript"> 
$(function(){
	Global.LinkSelect.initSelect("${ctx}/admin/department1/byDepType","department1","department2","department3");
	var postData = $("#page_form").jsonForm();
	//初始化表格
	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/department3/goJqGrid",
		postData:postData,
		height:$(document).height()-$("#content-list").offset().top-70,
		styleUI: 'Bootstrap',
		colModel : [
			  {name : "code",index : "code",width : 100,label:"部门编号",sortable : true,align : "left"},
		      {name : "desc1",index : "desc1",width : 100,label:"部门名称",sortable : true,align : "left"},
		      {name : "deptdescr2",index : "deptdescr2",width : 100,label:"二级部门",sortable : true,align : "left"},
		      {name : "bdeptype",index : "bdeptype ",width : 100,label:"部门类型",sortable : true,align : "left"},
		      {name : "department1",index : "department1",width : 100,label:"一级级部门",sortable : true,align : "left",hidden: true},
		],
	  ondblClickRow:function(rowid,iRow,iCol,e){
			var selectRow = $("#dataTable").jqGrid('getRowData', rowid);
           	Global.Dialog.returnData = selectRow;
           	Global.Dialog.closeDialog();
           }
	});

});

</script>
</head>
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form action="" method="post" id="page_form" class="form-search">
				<input type="hidden" id="expired" name="expired" value="${department3.expired }" /> 
					<ul class="ul-form">
					<li>
						<label>部门编号</label> 
						<input type="text" id="code" name="code" style="width:160px;" value="${department3.code }"/>
					</li>
					<li>
						<label>部门名称</label> 
						<input type="text" id="desc1" name="desc1" style="width:160px;" value="${department3.desc1 }"/>
					</li>
					<li>
						<label>部门类型</label> 
						<house:xtdm id="DepType" dictCode="DEPTYPE" style="width:160px;" />
					</li>
					<li>
						<label>一级部门</label> 
						<select id="department1" name="department1"></select>
					</li>
					<li>
						<label>二级部门</label> 
						<select id="department2" name="department2"></select>
					</li>
					<li>
						<input type="checkbox" id="expired_show" name="expired_show" value="${department3.expired }" onclick="hideExpired(this)" ${department2.expired!='T' ?'checked':'' }/>
						<label for="expired_show" style="margin-left: -3px;width: 50px;">隐藏过期</label>
						<button type="button" class="btn  btn-sm btn-system " onclick="goto_query();">查询</button>
						<button type="button" class="btn btn-sm btn-system " onclick="clearForm();">清空</button>
					</li>
				</ul>
				</ul>
			</form>
		</div>
		<!--query-form-->
		<div class="pageContent">
			<div id="content-list">
				<table id="dataTable"></table>
				<div id="dataTablePager"></div>
			</div>
		</div>
	</div>
</body>
</html>
