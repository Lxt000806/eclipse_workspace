<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<title>搜寻--职位信息</title>
	<%@ include file="/commons/jsp/common.jsp" %>
<script type="text/javascript">
$(function(){
if('${applicant.disabledEle}'){
	var arr='${applicant.disabledEle}'.split(",");
	$.each(arr,function(k,v){
		$("#"+v).attr("disabled","disabled");
	})
}

//mobileHidden 存在时  能根据电话查询
if('${customer.mobileHidden}'==''||'${customer.mobileHidden}'==null){
	$("#mobile1").css("display","none");
	$("#mobile").css("display","none");
}
if('${applicant.mobileHidden}'=='4'){
	document.getElementById("status").disabled=true
}
	var postData=$("#page_form").jsonForm();
	postData.status="${applicant.status}";
    //初始化表格
	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/position/goJqGrid",
		postData:postData,
		height:$(document).height()-$("#content-list").offset().top-70,
		styleUI: 'Bootstrap',   
		colModel : 
		   [
			  {name : 'code',index : 'Code',width : 100,label:'职位编号',sortable : true,align : "left"},
		      {name : 'desc2',index : 'Desc2',width : 100,label:'职位名称',sortable : true,align : "left"},
		      {name : 'type',index : 'type',width : 100,label:'职位类型',sortable : true,align : "left",hidden:"true"},
		   ], 
        ondblClickRow:function(rowid,iRow,iCol,e){
			var selectRow = $("#dataTable").jqGrid('getRowData', rowid);
        	Global.Dialog.returnData = selectRow;
        	Global.Dialog.closeDialog();
        }
	});
	$("#address").focus();
});

function clearForm(){
	if('${applicant.disabledEle}'.indexOf('status_NAME')==-1){
		$("#page_form input[type='text']").val('');
		$.fn.zTree.getZTreeObj("tree_status").checkAllNodes(false);
	} 
	else $("#page_form input[type='text'][id!='status_NAME']").val('');
	$("#page_form select").val('');
}
function goto_query(){
	var postData = $("#page_form").jsonForm();
	$("#dataTable").jqGrid("setGridParam", {
    	url: "${ctx}/admin/position/goJqGrid",
    	postData: postData
  	}).trigger("reloadGrid");
}
</script>
</head>  
<body>
	<div class="body-box-list">
		<div class="query-form">
			  <form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame">
			    <input type="text" id="expired" name="expired" hidden="true" value="F">
				<ul class="ul-form">
				    <li><label>职位名称</label> <input type="text" id="desc2" name="desc2" /></li>					
				    <li class="search-group-shrink">
				    	<input type="checkbox"  id="expired_show" name="expired_show" onclick="hideExpired(this)" ${position.expired!='T'?'checked':''} />   
						<P>隐藏过期</p>  
						<button type="summit" class="btn  btn-sm btn-system " onclick="goto_query();">查询</button>
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


