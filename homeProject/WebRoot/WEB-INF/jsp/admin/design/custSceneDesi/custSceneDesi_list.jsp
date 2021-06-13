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
	<title>材料销售分析—按品类</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
		<script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>
		<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
		<script src="${resourceRoot}/pub/component_supplier.js?v=${v}" type="text/javascript"></script>
		<script src="${resourceRoot}/pub/component_brand.js?v=${v}" type="text/javascript"></script>
		<script src="${resourceRoot}/pub/component_department2.js?v=${v}" type="text/javascript"></script>
	
	<script type="text/javascript">
	function isSchemeDesign(obj){
		if ($(obj).is(":checked")){
			$("#isSchemeDesigner").val("1");
		}else{
			$("#isSchemeDesigner").val("0");
		}
	}
	
	$(function(){
		$("#custSceneDesi").openComponent_employee({condition:{SceneDesiCustCount:"1" ,department1:"${department1}",department2:"${department2}"}});		
		$("#designMan").openComponent_employee();	
		var postData = $("#page_form").jsonForm();
		var gridOption ={
			url:"${ctx}/admin/custSceneDesi/goJqGrid",
			postData:{
				status:"4",
				custType:$.trim("${isAddCustType}")
			},
			height:$(document).height()-$("#content-list").offset().top-72,
			styleUI:"Bootstrap", 
			colModel : [
				{name: "code", index: "code", width: 80, label: "客户编号", sortable: true, align: "left",},
				{name: "address", index: "address", width: 120, label: "楼盘", sortable: true, align: "left",},
				{name: "custtypedescr", index: "custtypedescr", width: 80, label: "客户类型", sortable: true, align: "left",},
				{name: "status", index: "status", width: 80, label: "客户状态", sortable: true, align: "left",hidden:true},
				{name: "statusdescr", index: "statusdescr", width: 80, label: "客户状态", sortable: true, align: "left",},
				{name: "signdate", index: "signdate", width: 85, label: "签单时间", sortable: true, align: "left",formatter:formatDate},
				{name: "custscenedesidescr", index: "custscenedesidescr", width: 85, label: "现场设计师", sortable: true, align: "left",},
				{name: "designerdescr", index: "designerdescr", width: 80, label: "设计师", sortable: true, align: "left",},
				{name: "designdepartment2", index: "designdepartment2", width: 100, label: "设计所", sortable: true, align: "left",},
				{name: "promandepartment2", index: "promandepartment2", width: 80, label: "工程部", sortable: true, align: "left"},
				{name: "regiondescr", index: "regiondescr", width: 80, label: "一级区域", sortable: true, align: "left"},
				{name: "confirmbegin", index: "confirmbegin", width: 100, label: "实际开工时间", sortable: true, align: "left",formatter:formatDate},
				{name: "contractfee", index: "contractfee", width: 80, label: "工程造价", sortable: true, align: "right", sum: true},
				{name: "lastupdatedby", index: "lastupdatedby", width: 100, label: "最后更新人员", sortable: true, align: "left",},
				{name: "lastupdate", index: "lastupdate", width: 100, label: "最后更新时间", sortable: true, align: "left",formatter:formatTime},
				{name: "expired", index: "expired", width: 80, label: "是否过期", sortable: true, align: "left",},
				{name: "actionlog", index: "actionlog", width: 76, label: "操作日志", sortable: true, align: "left",},
			],
		};
		Global.JqGrid.initJqGrid("dataTable",gridOption);
	
		//安排
		$("#arrange").on("click",function(){
			ret=selectDataTableRow();
			if(!ret){
				art.dialog({
	       			content: "请选择一条记录",
	       		});
	       		return;
			}
			if($.trim(ret.status)!="4"){
				art.dialog({
	       			content: "只有合同施工才允许安排",
	       		});
	       		return;
			}
			Global.Dialog.showDialog("arrange",{
				title:"现场设计师——安排",
				url:"${ctx}/admin/custSceneDesi/goArr",
				postData:{code:ret.code},
				height:500,
				width:750,
				returnFun:goto_query
			});
		});
		//查看
		$("#view").on("click",function(){
			ret=selectDataTableRow();
			if(!ret){
				art.dialog({
	       			content: "请选择一条记录",
	       		});
	       		return;
			}
			Global.Dialog.showDialog("view",{
				title:"现场设计师——查看",
				url:"${ctx}/admin/custSceneDesi/goView",
				postData:{code:ret.code},
				height:500,
				width:750,
				returnFun:goto_query
			});
		});
		
		/* 清空下拉选择树checked状态 */
		$("#clear").on("click",function(){
			$("#custType").val("");
			$("#status").val("");
			$("#department2").val("");

			$.fn.zTree.getZTreeObj("tree_custType").checkAllNodes(false);
			$.fn.zTree.getZTreeObj("tree_status").checkAllNodes(false);
			$.fn.zTree.getZTreeObj("tree_department2").checkAllNodes(false);
		});
	});
	function doExcel(){
		var url = "${ctx}/admin/custSceneDesi/doExcel";
		doExcelAll(url);
	}
	
	</script>
</head>
	<body>
		<div class="body-box-list">
			<div class="query-form">
			    <form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
					<input type="hidden" name="jsonString" value=""/>
					<ul class="ul-form">
						<div class="validate-group row" >
							<li>
								<label>楼盘</label>
								<input type="text" id="address" name="address"/>
							</li>
							<li>
								<label>现场设计师</label>
								<input type="text" id="custSceneDesi" name="custSceneDesi"/>
							</li>
							<li>	
								<label>签单时间</label>
								<input type="text" id="signDateFrom" name="signDateFrom" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"value=""/>
							</li>
							<li>
								<label>至</label>
								<input type="text" id="signDateTo" name="signDateTo" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"value=""/>
							</li>
						</div>
						<div class="validate-group row">
							<li>	
								<label>安排时间</label>
								<input type="text" id="dateFrom" name="dateFrom" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"value=""/>
							</li>
							<li>
								<label>至</label>
								<input type="text" id="dateTo" name="dateTo" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"  value=""/>
							</li>
							<li>
								<label>客户类型</label>
								<house:custTypeMulit id="custType"  selectedValue="${isAddCustType}"></house:custTypeMulit>
							</li>                    
							</li>
							<li>
								<label>客户状态</label>
								<house:xtdmMulit id="status" dictCode="CUSTOMERSTATUS" selectedValue="4"></house:xtdmMulit>                     
							</li>
						</div>
						<div class="validate-group row">
							<li>
								<label>设计师</label>
								<input type="text" id="designMan" name="designMan"/>
							</li>
							<li>
								<label>状态</label>
 								<select id="arrangeStatus" name="arrangeStatus" style="width: 160px;" >
 									<option value="">请选择...</option>
 									<option value="1">1 已安排</option>
 									<option value="2">2 待安排</option>
 								</select>
							</li>
							<li>
								<label>工程部</label>
								<house:DictMulitSelect id="department2" dictCode="" sql="select a.Code, a.desc1+' '+isnull(e.NameChi,'') desc1  from dbo.tDepartment2 a
									left join tEmployee e on e.Department2=a.Code and e.IsLead='1' and e.LeadLevel='1' and e.expired='F'
									where  a.deptype='3' and a.Expired='F' order By dispSeq " 
									sqlLableKey="desc1" sqlValueKey="code">
								</house:DictMulitSelect>
							</li>
							<li>
								<label>实际开工时间从</label>
								<input type="text" id="beginDateFrom" name="beginDateFrom" class="i-date"  style="width:160px;" 
									onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"/>
							</li>
							<li>
								<label>至</label>
								<input type="text" id="beginDateTo" name="beginDateTo" class="i-date"  style="width:160px;" 
									onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"/>
							</li>
							<li>
								<label>一级区域</label>
								<house:dict id="region" dictCode="" sql="SELECT Code,Descr FROM tRegion WHERE 1=1 and expired='F' " sqlValueKey="Code" sqlLableKey="Descr"></house:dict>
							</li>
							<li>
								<input type="checkbox" id="isSchemeDesigner" name="isSchemeDesigner" value="0" onclick="isSchemeDesign(this)"/>方案设计师工地
							</li>
							<li class="search-group" >
								<button type="button" class="btn btn-sm btn-system" onclick="goto_query();">查询</button>
								<button id="clear" type="button" class="btn btn-sm btn-system" onclick="clearForm();">清空</button>
							</li>
						</div>	
					</ul>
				</form>
			</div>
		</div>
		<div class="clear_float"></div>
		<div class="btn-panel">
  			  	<div class="btn-group-sm">
	  			<house:authorize authCode="CUSTSCENEDESI_ARR">
					<button type="button" class="btn btn-system" id="arrange">
						<span>安排</span>
					</button>
				</house:authorize>	
				<house:authorize authCode="CUSTSCENEDESI_VIEW">
					<button type="button" class="btn btn-system" id="view">
						<span>查看</span>
					</button>
 			  	</house:authorize>
 			  	<house:authorize authCode="CUSTSCENEDESI_EXCEL">
					<button type="button" class="btn btn-system" onclick="doExcel()">
						<span>导出excel</span>
					</button>
				</house:authorize>
			</div>
		</div>
		<div id="content-list">
			<table id="dataTable"></table>
			<div id="dataTablePager"></div>
		</div> 
	</body>	
</html>
