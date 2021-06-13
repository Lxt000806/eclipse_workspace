<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<title>搜寻--员工编号</title>
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
	//增加预入条件
	if("${status}"==2){
		postData.status = "${status}";
	}
    //初始化表格
	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/applicant/goJqGrid",
		postData:postData,
		height:$(document).height()-$("#content-list").offset().top-70,
		styleUI: 'Bootstrap',   
		colModel : [
		  {name: "pk", index: "pk", width: 77, label: "序号", sortable: true, align: "left"},
		  {name: "namechi", index: "namechi", width: 77, label: "姓名", sortable: true, align: "left"},
		  {name: "genderdescr", index: "genderdescr", width: 57, label: "性别", sortable: true, align: "left"},
		  {name: "gender", index: "gender", width: 57, label: "性别", sortable: true, align: "left",hidden:true},
		  {name: "phone", index: "phone", width: 103, label: "联系电话", sortable: true, align: "left"},
	      {name: "deptdescr", index: "deptdescr", width: 110, label: "应聘部门", sortable: true, align: "left"},
		  {name: "dept1descr", index: "dept1descr", width: 110, label: "一级部门", sortable: true, align: "left"},
		  {name: "positiondescr", index: "positiondescr", width: 105, label: "应聘岗位", sortable: true, align: "left"},
		  {name: "appdate", index: "appdate", width: 75, label: "面试时间", sortable: true, align: "left", formatter: formatTime},
		  {name: "statusdescr", index: "statusdescr", width: 79, label: "状态", sortable: true, align: "left"},
		  {name: "comeindate", index: "comeindate", width: 83, label: "预入时间", sortable: true, align: "left", formatter: formatTime},
		  {name: "birtplace", index: "birtplace", width: 115, label: "籍贯", sortable: true, align: "left"},
		  {name: "idnum", index: "idnum", width: 132, label: "身份证号", sortable: true, align: "left"},
		  {name: "birth", index: "birth", width: 78, label: "出生日期", sortable: true, align: "left", formatter: formatTime},
		  {name: "age", index: "age", width: 58, label: "年龄", sortable: true, align: "left"},
		  {name: "address", index: "address", width: 191, label: "地址", sortable: true, align: "left"},
		  {name: "edudescr", index: "edudescr", width: 85, label: "文化程度", sortable: true, align: "left"},
		  {name: "edu", index: "edu", width: 85, label: "文化程度", sortable: true, align: "left",hidden:true},
	      {name: "school", index: "school", width: 123, label: "毕业院校", sortable: true, align: "left"},
		  {name: "schdept", index: "schdept", width: 98, label: "专业", sortable: true, align: "left"},
		  {name: "source", index: "source", width: 98, label: "途径", sortable: true, align: "left"},
		  {name: "remarks", index: "remarks", width: 198, label: "备注", sortable: true, align: "left"},
		  {name: "lastupdate", index: "lastupdate", width: 77, label: "录入时间", sortable: true, align: "left", formatter: formatTime},
		  {name: "lastupdatedby", index: "lastupdatedby", width: 67, label: "录入人", sortable: true, align: "left"},
		  {name: "expired", index: "expired", width: 74, label: "是否过期", sortable: true, align: "left"},
		  {name: "actionlog", index: "actionlog", width: 74, label: "操作标志", sortable: true, align: "left"}						
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
    	url: "${ctx}/admin/applicant/goJqGrid",
    	postData: postData
  	}).trigger("reloadGrid");
}

</script>
</head>
    
<body>
	<div class="body-box-list">
		<div class="query-form">
			  <form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame">
				<ul class="ul-form">
					<li>
						<label>序号</label> 
						<input type="text" id="pk" name="pk" />
					</li>
				    <li>
				    	<label>姓名</label> 
				    	<input type="text" id="nameChi" name="nameChi" />
				    </li>
					<li>
						<label>电话</label> 
						<input type="text" id="phone" name="phone" />
					</li>
					<li>
						<label>面试岗位</label> 
						<input type="text" id="positionDescr" name="positionDescr" />
					</li>
					<li>
						<label>应聘部门</label>
						<input type="text" id="deptDescr" name="deptDescr"/>
					</li>
					<li>
						<label>途径</label> 
						<input type="text" id="source" name="source" />
					</li>
					<li>
						<label>应聘日期</label>
						<input type="text" id="dateFrom" name="dateFrom" class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${applicant.appDate}' pattern='yyyy-MM-dd'/>" />
					</li>
     				<li>						
						<label>至</label>
						<input type="text" id="dateTo" name="dateTo" class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${applicant.appDate}' pattern='yyyy-MM-dd'/>" />
					</li> 
					<li>
						<label>预入日期</label>
						<input type="text" id="earliestComeInDate" name="earliestComeInDate" class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${applicant.comeInDate}' pattern='yyyy-MM-dd'/>" />
					</li>
					<li>						
						<label>至</label>
						<input type="text" id="lastestComeInDate" name="lastestComeInDate" class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${applicant.endComeinDate}' pattern='yyyy-MM-dd'/>" />
					</li> 
				    <li class="search-group-shrink">
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


