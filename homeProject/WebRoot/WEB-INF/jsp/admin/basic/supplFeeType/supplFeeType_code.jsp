<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<!DOCTYPE html>
<html>
<head>
	<title>搜寻——供应商费用类型</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
<script type="text/javascript"> 
$(function(){
	var postData = $("#page_form").jsonForm();
	//初始化表格
	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/supplFeeType/goJqGrid",
		postData:postData,
		height:$(document).height()-$("#content-list").offset().top-70,
		styleUI: 'Bootstrap',
		colModel : [
			  {name : 'code',index : 'code',width : 80,label:'编号',sortable : true,align : "left"},
		      {name : 'descr',index : 'descr',width : 100,label:'名称',sortable : true,align : "left"},
		      {name : 'isdefaultdescr',index : 'isdefaultdescr ',width : 80,label:'是否默认值',sortable : true,align : "left"},
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
});

</script>
</head>
	<body>
		<div class="body-box-list">
			<div class="query-form">
				<form action="" method="post" id="page_form" class="form-search">
					<input type="hidden" id="expired"  name="expired" value="F"/>
					<input type="hidden" id="isComponent" name="isComponent" style="width:160px;" value="1"/>
					<ul class="ul-form">
					<li>
						<label>名称</label> 
						<input type="text" id="descr" name="descr" style="width:160px;" value="${supplFeeType.descr }"/>
					</li>
					<li>
						<input type="checkbox" id="expired_show" name="expired_show" value="${supplFeeType.expired }" onclick="hideExpired(this)" ${department.expired!='T' ?'checked':'' }/>
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
