<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://" + request.getServerName() + ":"+request.getServerPort()+path+"/";
String jasperPath = PathUtil.WEB_INF+"jsp/jasper/";
%>
<!DOCTYPE html>
<html lang="zh">
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	
</head>
<body>
	<div class="body-box-form">
		<div class="panel panel-system">
		    <div class="panel-body">
		    	<div class="btn-group-xs">
					<button type="button" class="btn btn-system" id="closeBut" onclick="closeWin(false)">
						<span>关闭</span>
					</button>
				</div>
			</div>
		</div>
		<div class="panel panel-info" style="margin-bottom: 10px;">  
			<div class="panel-body">
				<form action="" method="post" id="page_form" class="form-search">
					<house:token></house:token>
					<input type="hidden" name="jsonString" value=""/>
					<ul class="ul-form">
						<div class="validate-group row">
							<li class="form-validate">
								<label><span class="required">*</span>批次号</label>
								<input type="text" id="code" name="code" style="width:160px;" value="${appItemTypeBatch.code}" readonly="readonly"/>
							</li>
							<li class="form-validate">
								<label><span class="required">*</span>名称</label>
								<input type="text" id="descr" name="descr" style="width:160px;" value="${appItemTypeBatch.descr}" readonly="readonly"/>
								<%-- <house:dict id="descr" dictCode="" sql=" select distinct Descr from tConfItemType where Expired = 'F' " 
									sqlValueKey="Descr" sqlLableKey="Descr" value="${appItemTypeBatch.descr}" disabled="true"/> --%>          
							</li>
						</div>
						<div class="validate-group row">
							<li class="form-validate">
								<label><span class="required">*</span>是否套餐材料</label>
								<house:xtdm id="isSetItem" dictCode="YESNO" style="width:160px;" value="${appItemTypeBatch.isSetItem}" disabled="true"/>
							</li>
							<li class="form-validate">
								<label><span class="required">*</span>提示一起下单</label>
								<house:xtdm id="infoAppAll" dictCode="YESNO" style="width:160px;" value="${appItemTypeBatch.infoAppAll}" disabled="true"/>
							</li>
						</div>
						<div class="validate-group row">
							<li class="form-validate">
								<label><span class="required">*</span>显示顺序</label>
								<input type="text" id="dispSeq" name="dispSeq" style="width:160px;" value="${appItemTypeBatch.dispSeq}" readonly="readonly"/>
							</li>
							<li>
								<label>过期</label>
								<input type="checkbox" id="expired" name="expired" value="${appItemTypeBatch.expired}" 
									onclick="checkExpired(this)" ${appItemTypeBatch.expired=="T"?"checked":""} disabled="disabled"/>
							</li>
						</div>
					</ul>
				</form>
			</div>
		</div>
		<div class="container-fluid" >  
			<ul class="nav nav-tabs" >
			    <li class="active"><a href="#custVisit_tabView_customer" data-toggle="tab">下单材料类型批次明细</a></li>  
			</ul>
		    <div class="tab-content">  
				<div id="custVisit_tabView_customer"  class="tab-pane fade in active"> 
		         	<div class="body-box-list">
						<div class="btn-panel">
							<div class="btn-group-sm" style="padding-top: 5px;">
								<button type="button" class="btn btn-system" id="detailView" onclick="view()">
									<span>查看</span>
								</button>
							</div>
						</div>
						<div id="content-list">
							<table id="dataTable"></table>
							<div id="dataTablePager"></div>
						</div>
					</div>
				</div> 
			</div>	
		</div>
	</div>
</body>	
<script type="text/javascript">
//表格中的最一开始的数据(全局) 
var originalDataTable;
$(function() {
	var gridOption = {
		url:"${ctx}/admin/appItemTypeBatch/goDetailJqGrid",
		postData:{code:"${appItemTypeBatch.code}"},
		sortable: true,
		height : $(document).height()-$("#content-list").offset().top - 80,
		rowNum : 10000000,
		pager:'1',
		colModel : [
			{name: "PK", index: "PK", width: 70, label: "pk", sortable: true, align: "left", hidden: true},
			{name: "AppItemTypeBatch", index: "AppItemTypeBatch", width: 140, label: "下单材料类型批次号", sortable: true, align: "left", hidden:true},
			{name: "ConfItemType", index: "ConfItemType", width: 90, label: "施工材料分类", sortable: true, align: "left", hidden:true},
			{name: "confitemtypedescr", index: "confitemtypedescr", width: 90, label: "施工材料分类", sortable: true, align: "left"},
			{name: "DispSeq", index: "DispSeq", width: 70, label: "显示顺序", sortable: true, align: "left"},
			{name: "LastUpdatedBy", index: "LastUpdatedBy", width: 100, label: "最后更新人员", sortable: true, align: "left"},
			{name: "LastUpdate", index: "LastUpdate", width: 130, label: "最后更新时间", sortable: true, align: "left", formatter: formatTime},
			{name: "ActionLog", index: "ActionLog", width: 70, label: "操作日志", sortable: true, align: "left"},
			{name: "Expired", index: "Expired", width: 70, label: "过期标志", sortable: true, align: "left"}
		],
		loadonce: true,
		ondblClickRow: function(){
			view();
		}
	};
	
	Global.JqGrid.initJqGrid("dataTable",gridOption);

});

/* 明细编辑 */
function view(){
	var ret=selectDataTableRow();
	/* 选择数据的id */
	var rowId = $("#dataTable").jqGrid("getGridParam","selrow");
	if(!ret){
		art.dialog({content: "请选择一条记录进行查看操作",width: 220});
		return false;
	}

	Global.Dialog.showDialog("detailView",{
		title:"下单材料类型批次明细——查看",
		url:"${ctx}/admin/appItemTypeBatch/goDetailSave",
		postData:{code:$("#code").val(),confItemType:ret.ConfItemType,dispSeq:ret.DispSeq,expired:ret.Expired,m_umState:"V"},
		height:310,
		width:450,
		returnFun:goto_query
	});
}

</script>
</html>
