<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>Item列表</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
/**初始化表格*/
$(function(){
$("#custCode").openComponent_customer({
				showValue:"${prjWithHold.custCode}",
				showLabel:"${prjWithHold.custDescr}",
				readonly:true
			});	
	var postData = $("#page_form").jsonForm();
    //初始化表格
	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/prjWithHold/goCodeJqGrid",
		postData: postData,
		height:385,
		styleUI: 'Bootstrap', 
		colModel : [
			{name: "pk", index: "pk", width: 122, label: "项目经理预扣单号", sortable: true, align: "left"},
			{name: "custcode", index: "custcode", width: 93, label: "客户编号", sortable: true, align: "left"},
			{name: "address", index: "address", width: 177, label: "楼盘", sortable: true, align: "left"},
			{name: "custdescr", index: "custdescr", width: 103, label: "客户名称", sortable: true, align: "left"},
			{name: "worktype1descr", index: "worktype1descr", width: 105, label: "工种分类1", sortable: true, align: "left"},
			{name: "worktype2descr", index: "worktype2descr", width: 106, label: "工种分类2", sortable: true, align: "left"},
			{name: "typedescr", index: "typedescr", width: 100, label: "类型", sortable: true, align: "left"},
			{name: "amount", index: "amount", width: 100, label: "金额", sortable: true, align: "left"},
			{name: "remarks", index: "remarks", width: 220, label: "备注", sortable: true, align: "left"},
			{name: "lastupdate", index: "lastupdate", width: 140, label: "最后更新时间", sortable: true, align: "left", formatter: formatTime},
			{name: "lastupdatedby", index: "lastupdatedby", width: 140, label: "最后更新人员", sortable: true, align: "left"},
			{name: "expired", index: "expired", width: 100, label: "是否过期", sortable: true, align: "left"},
			{name: "actionlog", index: "actionlog", width: 100, label: "操作日志", sortable: true, align: "left"}
	        
        ],
        ondblClickRow:function(rowid,iRow,iCol,e){
			var selectRow = $("#dataTable").jqGrid('getRowData', rowid);
        	Global.Dialog.returnData = selectRow;
        	Global.Dialog.closeDialog();
        }
	});
	
	$("#view").on("click",function(){
		var ret = selectDataTableRow();
		if(ret){
			Global.Dialog.showDialog("View",{
				title:"在建工地——查看",
				url:"${ctx}/admin/prjWithHold/goViewOnDoDetail",
				postData:{code:ret.Code},
				height:600,
				width:850,
				returnFun:goto_query
			});
		}else{
			art.dialog({
				content:"请选择一条数据",
			});
		}
	});
	
	$("#nameChi").focus();
});
</script>
</head>
    
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form action="" method="post" id="page_form" class="form-search">
				<input type="hidden" id="workType12" name="workType12"
					style="width:160px;" />
				<ul class="ul-form">
					<li><label>客户编号</label> <input type="text" id="custCode"
						name="custCode" value="${prjWithHold.custCode}" />
					</li>
					<li><label>楼盘地址</label> <input type="text" id="address"
						name="address" style="width:160px;"
						value="${prjWithHold.address }" />
					</li>
					<li><label>类型</label> <house:xtdm id="type"
							dictCode="PrjWithHoldType"></house:xtdm>
					</li>
						<li><label>工种分类1</label> <house:dict id="workType1"
								dictCode=""
								sql="select rtrim(Code)+' '+ Descr fd,Code from tWorkType1 order by DispSeq"
								sqlValueKey="Code" sqlLableKey="fd" 
								value="${prjWithHold.workType1}" disabled="true">
							</house:dict>
						</li>
						<c:if test="${isEditWorkType2=='1'}">
						<li><label>工种分类2</label> <house:dict id="workType2"
								dictCode=""
								sql="select rtrim(Code)+' '+Descr fd,Code from tWorkType2 where  Expired='F' and WorkType1='${prjWithHold.workType1}' order by code"
								sqlValueKey="Code" sqlLableKey="fd" 
								value="${prjWithHold.workType2}" >
							</house:dict>
						</li>
						</c:if>
						<c:if test="${isEditWorkType2!='1'}">
						<li><label>工种分类2</label> <house:dict id="workType2"
								dictCode=""
								sql="select rtrim(Code)+' '+Descr fd,Code from tWorkType2 where  Expired='F' and CalType='1' and WorkType1='${prjWithHold.workType1}' order by code"
								sqlValueKey="Code" sqlLableKey="fd" 
								value="${prjWithHold.workType2}" disabled="true">
							</house:dict>
						</li>
						</c:if>
					<li class="search-group">
						<button type="button" class="btn btn-sm btn-system"
							onclick="goto_query();">查询</button>
						<button type="button" class="btn btn-sm btn-system"
							onclick="clearForm();">清空</button>
					</li>
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


