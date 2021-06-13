<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://" + request.getServerName() + ":"+request.getServerPort()+path+"/";
String jasperPath = PathUtil.WEB_INF+"jsp/jasper/";
%>
<!DOCTYPE html>
<html>
<head>
	<title>薪酬方案新增</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_department.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_assetType.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_docFolder.js?v=${v}" type="text/javascript"></script>
	<style type="text/css">
		::-webkit-input-placeholder { /* Chrome */
		  color: #cccccc;
		}
	</style>
	<style type="text/css">
      .SelectBG{
          background-color:#198ede!important;
            color:rgb(255,255,255)
            }
      .SelectBG_yellow{
          background-color:yellow;
         }
	</style>
<script type="text/javascript"> 
$(function() {
	var gridOption = {	
		height:$(document).height()-$("#content-list").offset().top-100,
		rowNum:50,
		colModel : [
			{name:"pk", index:"pk", width:80, label:"pk", sortable:true ,align:"left",hidden:true},
			{name:"salaryitem", index:"salaryitem", width:80, label:"项目编号", sortable:true ,align:"left",},
			{name:"salaryitemdescr", index:"salaryitemdescr", width:150, label:"项目名称", sortable:true ,align:"left",},
			{name:"isshow", index:"isshow", width:80, label:"显示标识", sortable:true ,align:"left",hidden:true},
			{name:"isshowdescr", index:"isshowdescr", width:80, label:"显示标识", sortable:true ,align:"center",formatter:checkBtn},
			{name:"dispseq", index:"dispseq", width:80, label:"序号", sortable:true ,align:"right",},
			{name:"isrptshow", index:"isrptshow", width:80, label:"工资单显示", sortable:false,align:"left", hidden:true},
			{name:"isrptshowdescr", index:"isrptshowdescr", width:80, label:"工资单显示", sortable:false ,align:"center",formatter:checkRptBtn},
			{name:"rptdispseq", index:"rptdispseq", width:80, label:"工资单序号", sortable:false ,align:"right"},
		],
		onCellSelect: function(id,iCol,cellParam,e){
			var ids = $("#dataTable").jqGrid("getDataIDs");  
			for(var i=0;i<ids.length;i++){
				if(i!=id-1){
					$("#"+ids[i]).find("td").removeClass("SelectBG");
				}
			}
			$("#"+ids[id-1]).find("td").addClass("SelectBG");
		},
	}; 
	
	$.extend(gridOption,{
		url:"${ctx}/admin/salaryScheme/goSchemeItemJqgrid",
		postData:{pk:$.trim($("#pk").val())}
	});
	
	function checkBtn(v,x,r){
		if(r.isshow == "1"){
			return "<input type='checkbox' checked onclick='this.checked=!this.checked' />";
		} else {
			return "<input type='checkbox' onclick='this.checked=!this.checked' />";
		}
	}
	function checkRptBtn(v,x,r){
		if(r.rptdispseq == "1"){
			return "<input type='checkbox' checked onclick='this.checked=!this.checked' />";
		} else {
			return "<input type='checkbox' onclick='this.checked=!this.checked' />";
		}
	}
	
	Global.JqGrid.initJqGrid("dataTable",gridOption);
});

</script>
</head>
	<body>
		<div class="body-box-form">
			<div class="panel panel-system">
			    <div class="panel-body">
			      <div class="btn-group-xs">
						<button type="button" class="btn btn-system " id="closeBut" onclick="closeWin(true)">
							<span>关闭</span>
						</button>
					</div>
				</div>
			</div>
			<div class="panel panel-info">  
				<div class="panel-body">
					<form action="" method="post" id="dataForm" class="form-search">
						<house:token></house:token>
						<input type="hidden" name="jsonString" value=""/>
						<input type="hidden"  id="pk" name="pk" value="${salaryScheme.pk }"/>
						<ul class="ul-form">
							<div class="validate-group row">
								<li class="form-validate">
									<label>方案名称</label>
									<input type="text" id="descr" name="descr" style="width:160px;" readonly="true" value="${salaryScheme.descr }"/>
								</li>
								<li class="form-validate">
									<label>方案类型</label>
									<house:dict id="salarySchemeType" dictCode="" sql="select Code+' '+descr descr,code from tSalarySchemeType where expired = 'F'" 
												sqlValueKey="code" sqlLableKey="descr" disabled="true" value="${salaryScheme.salarySchemeType }"></house:dict>
								</li>
							</div>
							<div class="validate-group row">
								<li>
									<label>生效时间</label>
									<input type="text" id="beginMon" name="beginMon" class="i-date" 
										style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyyMM'})"
										value="${salaryScheme.beginMon }" disabled="true"/>
								</li>
								<li>
									<label>结束时间</label>
									<input type="text" id="endMon" name="endMon" class="i-date" 
										style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyyMM'})"
										value="${salaryScheme.endMon }" disabled="true"/>
								</li>
							</div>
							<div class="validate-group row">
								<li class="form-validate">
									<label>状态</label>
									<house:xtdm  id="status" dictCode="SALENABLESTAT" style="width:160px;"
										 disabled="true" value="${salaryScheme.status }"></house:xtdm>
								</li>
							</div>
							<div class="validate-group row">
								<li class="form-validate">
									<label class="control-textarea">方案说明</label>
									<textarea id="remarks" name="remarks" rows="3" readonly="true">${salaryScheme.remarks }</textarea>
								</li>
							</div>
						</ul>
					</form>
				</div>
			</div>
			<div class="container-fluid">  
				<ul class="nav nav-tabs"> 
			        <li class="active"><a href="#tab_detail" data-toggle="tab">薪酬项目</a></li>
			    </ul> 
				<div id="content-list">
					<div id="tab_detail" class="tab-pane fade in active">
						<div class="pageContent">
							<div id="content-list">
								<table id="dataTable"></table>
								<table id="dataTablePager"></table>
							</div>
						</div>
					</div>
				</div>	
			</div>	
		</div>
	</body>	
</html>
