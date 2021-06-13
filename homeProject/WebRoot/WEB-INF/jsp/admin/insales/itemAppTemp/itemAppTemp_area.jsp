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
	
<script type="text/javascript" defer> 
$(function() {
	$("#itemType1").prop("disabled",true);
	$("#workType12").prop("disabled",true);

	postData = $("#page_form").jsonForm();

	var gridOption = {
		url:"${ctx}/admin/itemAppTemp/goAreaJqGrid",
		postData:postData,
		sortable: true,
		height : $(document).height()-$("#content-list").offset().top - 33,
		rowNum : 10000000,
		colModel : [
			{name: "no", index: "no", width: 70, label: "区间编号", sortable: true, align: "left"},
			{name: "fromarea", index: "fromarea", width: 70, label: "从面积", sortable: true, align: "right"},
			{name: "toarea", index: "toarea", width: 70, label: "到面积", sortable: true, align: "right"},
			{name: "lastupdate", index: "lastupdate", width: 130, label: "最后修改时间", sortable: true, align: "left", formatter: formatTime},
			{name: "lastupdatedby", index: "lastupdatedby", width: 90, label: "修改人", sortable: true, align: "left"},
			{name: "expired", index: "expired", width: 70, label: "过期标志", sortable: true, align: "left"},
			{name: "actionlog", index: "actionlog", width: 70, label: "操作日志", sortable: true, align: "left"},
		],
		ondblClickRow: function(){
			view();
		}
	};
	
	Global.JqGrid.initJqGrid("dataTable",gridOption);

	$("#save").on("click",function(){
		Global.Dialog.showDialog("AreaSave",{
			title:"领料申请模板面积区间——新增",
			url:"${ctx}/admin/itemAppTemp/goAreaSave",
			postData:{m_umState:"A",itemType1:"${itemAppTemp.itemType1}",iatno:"${itemAppTemp.no}"},
			height:650,
			width:930,
			returnFun: goto_query,
		});
	});
	
	$("#delete").on("click",function(){
		var id = $("#dataTable").jqGrid("getGridParam","selrow");
		if(!id){
			art.dialog({content: "请选择一条记录进行删除操作",width: 220});
			return false;
		}
		var ret=selectDataTableRow();
		ret.m_umState = "D";
		art.dialog({
			content:"是否删除该记录？",
			lock: true,
			width: 200,
			height: 80,
			ok: function () {
				$.ajax({
					url:"${ctx}/admin/itemAppTemp/doAreaSave",
					type: "post",
					data: ret,
					dataType: "json",
					cache: false,
					error: function(obj){
						showAjaxHtml({"hidden": false, "msg": "保存数据出错"});
				    },
				    success: function(obj){
				    	if(!obj.rs){
				    		$("#_form_token_uniq_id").val(obj.token.token);
				    		art.dialog({
								content: obj.msg,
								width: 200
							});
				    	} else {
				    		goto_query();
				    	}
			    	}
				});
			},
			cancel: function () {
				return true;
			}
		});
		
	});
	
});

function update(){
	var ret=selectDataTableRow();
	if(!ret){
		art.dialog({content: "请选择一条记录进行编辑操作",width: 220});
		return false;
	}
	
	Global.Dialog.showDialog("AreaUpdate",{
		title:"领料申请模板面积区间——编辑",
		url:"${ctx}/admin/itemAppTemp/goAreaUpdate",
		postData:{no:ret.no,m_umState:"M",itemType1:"${itemAppTemp.itemType1}"},
		height:650,
		width:930,
		returnFun : goto_query
	});
}

function view(){
	var ret=selectDataTableRow();
	if(!ret){
		art.dialog({content: "请选择一条记录进行查看操作",width: 220});
		return false;
	}
	
	Global.Dialog.showDialog("AreaUpdate",{
		title:"领料申请模板面积区间——查看",
		url:"${ctx}/admin/itemAppTemp/goAreaUpdate",
		postData:{no:ret.no,m_umState:"V",itemType1:"${itemAppTemp.itemType1}"},
		height:650,
		width:930,
		returnFun : goto_query
	});
}
</script>
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
						<li>
							<label>模板编号</label>
							<input type="text" id="no" name="no" style="width:160px;" placeholder="保存自动生成" 
								value="${itemAppTemp.no}" readonly="readonly"/>
						</li>
						<li class="form-validate">
							<label><span class="required">*</span>材料类型1</label>
							<house:dict id="itemType1" dictCode="" sql="select Code,Code+' '+Descr cd from tItemType1 where Expired != 'T'" 
								sqlValueKey="Code" sqlLableKey="cd" value="${itemAppTemp.itemType1}"/>
						</li>
						<li>
							<label>工种分类1-2</label>
							<house:dict id="workType12" dictCode="" sql="select Code,Code+' '+Descr cd from tWorkType12 where Expired != 'T'" 
								sqlValueKey="Code" sqlLableKey="cd" value="${itemAppTemp.workType12}"/>
						</li>
						<li style="max-height: 120px;">
							<label class="control-textarea" style="top: -60px;">备注</label>
							<textarea id="remarks" name="remarks" style="height: 80px;" 
								readonly="readonly">${itemAppTemp.remarks}</textarea>
						</li>
					</ul>
				</form>
			</div>
		</div>
		<div class="container-fluid" >  
			<ul class="nav nav-tabs" >
			    <li class="active"><a href="#itemAppTemp_area_tabView" data-toggle="tab">领料申请模板面积区间</a></li>  
			</ul>
		    <div class="tab-content">  
				<div id="itemAppTemp_area_tabView"  class="tab-pane fade in active"> 
		         	<div class="body-box-list">
						<div class="btn-panel">
							<div class="btn-group-sm" style="padding-top: 5px;">
								<button type="button" class="btn btn-system" id="save">
									<span>新增</span>
								</button>
								<button type="button" class="btn btn-system" id="update" onclick="update()">
									<span>编辑</span>
								</button>
								<button type="button" class="btn btn-system" id="delete">
									<span>删除</span>
								</button>
								<button type="button" class="btn btn-system" id="view" onclick="view()">
									<span>查看</span>
								</button>
								<button type="button" class="btn btn-system" id="excel" onclick="doExcelNow('领料申请模板面积区间')">
									<span>导出Excel</span>
								</button>
							</div>
						</div>
						<div id="content-list">
							<table id="dataTable"></table>
							<!-- <div id="dataTablePager"></div> -->
						</div>
					</div>
				</div> 
			</div>	
		</div>
	</div>
</body>	

</html>
