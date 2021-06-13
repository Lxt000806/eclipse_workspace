<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<title>业绩计算--报表</title>
		<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
		<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
		<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
		<META HTTP-EQUIV="expires" CONTENT="0" />
		<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
		<%@ include file="/commons/jsp/common.jsp" %>
		<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
		<script src="${resourceRoot}/pub/component_perfCycle.js?v=${v}" type="text/javascript"></script>
	<script type="text/javascript">
/* 		*初始化表格*/
		var excelName="业绩明细";
		var excelTable="detailDataTable";
		$(function(){
			$("#y,#season").append($("<option/>").text("请选择...").attr("value",""));
			for(var i=2014;i<=2100;i++){
				$("#y").append($("<option/>").text(i).attr("value",i));
			}
			for(var i=1;i<=6;i++){
				$("#season").append($("<option/>").text(i).attr("value",i));
			}
			$("#empCode").openComponent_employee();
			$("#no").openComponent_perfCycle({
				showValue:"${defautNo}",
			});
		});
		function doQuery(){
			var datas = {
				url:"${ctx}/admin/perfCycle/goReportDetailJqGrid",
				postData:$("#page_form").jsonForm()
			};
			$("#detailDataTable").jqGrid("setGridParam", datas).trigger("reloadGrid");//分页查询
			
			doReload('htyDataTable','${ctx}/admin/perfCycle/goReportHtyJqGrid');//不分页查询
			doReload('ywbDataTable','${ctx}/admin/perfCycle/goReportYwbJqGrid'); // 业务部
			doReload('sjbDataTable','${ctx}/admin/perfCycle/goReportSjbJqGrid'); // 设计部
			doReload('sybDataTable','${ctx}/admin/perfCycle/goReportSybJqGrid'); // 事业部
			doReload('gcbDataTable','${ctx}/admin/perfCycle/goReportGcbJqGrid'); // 工程部
			doReload('ywyDataTable','${ctx}/admin/perfCycle/goReportYwyJqGrid'); // 业务员
			doReload('sjsDataTable','${ctx}/admin/perfCycle/goReportSjsJqGrid'); // 设计师
			doReload('fdyDataTable','${ctx}/admin/perfCycle/goReportFdyJqGrid'); // 翻单员
			doReload('ywzrDataTable','${ctx}/admin/perfCycle/goReportYwzrJqGrid');// 业务主任 
			doReload('ywyDlxxDataTable','${ctx}/admin/perfCycle/goReportYwyDlxxJqGrid');// 业务员（独立销售）
			doReload('sjtdDataTable','${ctx}/admin/perfCycle/goReportSjtdJqGrid'); // 设计团队
			doReload('ywtdDataTable','${ctx}/admin/perfCycle/goReportYwtdJqGrid'); // 业务团队
		}
		function changeValue(){
			var value=$("#roleBox").prop("checked");
			$("#roleBox").val(value);
		}
		function setExcel(name,table){
			excelName=name;
			excelTable=table;
		}
		function doExcel(){
			if(excelTable=="detailDataTable"){//只有明细是分页的，其他不分页
				doExcelAll("${ctx}/admin/perfCycle/doExcelReport",excelTable,"page_form");
			}else{
				doExcelNow(excelName,excelTable,"page_form");
			}
		}
		//不分页查询，表格加了loadonce，要加datatype:"json"才能重新加载表格
		function doReload(tableId,url){
			$("#"+tableId).jqGrid("setGridParam",{url:url,datatype:"json",postData:$("#page_form").jsonForm(),page:1}).trigger("reloadGrid");
		}
		//清空
		function clearForm(){
			$("#page_form input[type='text']").val("");
			$("#roleBox").prop("checked",false);
			$("#roleBox").val("false");
			$("#department1").val("");
			$("#department2").val("");
			$("#department3").val("");
			$("#custType").val("");
			$("#region").val("");
			$("#perfType").val("");
			$("#checkStatus").val("");
			$.fn.zTree.getZTreeObj("tree_department1").checkAllNodes(false);
			$.fn.zTree.getZTreeObj("tree_department2").checkAllNodes(false);
			$.fn.zTree.getZTreeObj("tree_department3").checkAllNodes(false);
			$.fn.zTree.getZTreeObj("tree_custType").checkAllNodes(false);
			$.fn.zTree.getZTreeObj("tree_region").checkAllNodes(false);
			$.fn.zTree.getZTreeObj("tree_perfType").checkAllNodes(false);
			$.fn.zTree.getZTreeObj("tree_checkStatus").checkAllNodes(false);
		} 
	</script>
	</head>

<body>
	<div class="body-box-list">
		<div class="panel panel-system">
			<div class="panel-body">
				<div class="btn-group-xs" style="float:left">
					<button id="excel" type="button"
						class="btn btn-system" onclick="doExcel()">导出excel</button>
					<button id="closeBut" type="button" class="btn btn-system"
						onclick="closeWin(false)">关闭</button>
				</div>
			</div>
		</div>
		<div class="query-form">
			<form role="form" class="form-search" id="page_form" action=""
				method="post" target="targetFrame">
				<input type="hidden" name="jsonString" value="" />
				<ul class="ul-form">
			 		<li><label>生成时间从</label> <input type="text" id="crtDateFrom"
						name="crtDateFrom" class="i-date" style="width:160px;"
						onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
					</li>
					<li><label>至</label> <input type="text" id="crtDateTo"
						name="crtDateTo" class="i-date" style="width:160px;"
						onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
					</li>
					<li><label>达标时间从</label> <input type="text"
						id="achieveDateFrom" name="achieveDateFrom" class="i-date"
						style="width:160px;"
						onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
					</li>
					<li><label>至</label> <input type="text" id="achieveDateTo"
						name="achieveDateTo" class="i-date" style="width:160px;"
						onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
					</li>
					<li><label>部门类型</label> <house:xtdm id="deptType"
							dictCode="DEPTYPE"></house:xtdm>
					</li>
						<li>
							<label>一级部门</label>
							<house:DictMulitSelect id="department1" dictCode="" sql="select code,desc1 from tDepartment1 where Expired='F'" 
							sqlLableKey="desc1" sqlValueKey="code"  onCheck="checkDepartment1()"></house:DictMulitSelect>
						</li>
						<li>
							<label>二级部门</label>
							<house:DictMulitSelect id="department2" dictCode="" sql="select code,desc1 from tDepartment2 where 1=2" 
							sqlLableKey="desc1" sqlValueKey="code"  onCheck="checkDepartment2()"></house:DictMulitSelect>
						</li>
						<li>
							<label>三级部门</label>
							<house:DictMulitSelect id="department3" dictCode="" sql="select code,desc1 from tDepartment3 where 1=2" sqlLableKey="desc1" sqlValueKey="code" ></house:DictMulitSelect>
						</li>
						  <li>
							<label>客户类型</label>
							<house:custTypeMulit id="custType"  selectedValue="${allCustType}"></house:custTypeMulit>
						  </li>
					 	 <li>
						   <label>一级区域</label>
						  <house:xtdmMulit id="region" dictCode="" sql="select code SQL_VALUE_KEY,descr SQL_LABEL_KEY  from tRegion a where a.expired='F' " ></house:xtdmMulit>
						</li>	 
					<li><label>统计周期编号</label> <input type="text" id="no" name="no"
						style="width:160px;" />
					</li>
					<li><label>干系人编号</label> <input type="text" id="empCode"
						name="empCode" style="width:160px;" />
					</li>
					<li><label>楼盘</label> <input type="text" id="address"
						name="address" style="width:160px;" />
					</li>
					<li><label>业务类型</label> <house:xtdm id="busiType"
							dictCode="BUSITYPE"></house:xtdm>
					</li>
					 <li><label>业绩类型</label> <house:xtdmMulit id="perfType"
							dictCode="PERFTYPE"></house:xtdmMulit>
					</li>
					<li><label>归属年份</label> <select id="y" name="y"></select>
					</li>
					<li><label>归属季度</label> <select id="season" name="season"></select>
					</li>
					<li><label>复核</label> <house:xtdm id="isChecked"
							dictCode="YESNO"></house:xtdm>
					</li>
					<li><label>档案号</label> <input type="text" id="documentNo"
						name="documentNo"  style="width:160px;" />
					</li>
					<li>
						<label for="checkStatus">客户结算状态</label>
						<house:xtdmMulit id="checkStatus" dictCode="CheckStatus"/>
					</li>
					<li>
						<label>客户来源</label>
						<house:xtdm id="source" dictCode="CUSTOMERSOURCE" ></house:xtdm>
					</li>
					 <li>
					   <label>团队</label>
					  <house:xtdmMulit id="teamCode" dictCode="" sql="select code SQL_VALUE_KEY,desc1 SQL_LABEL_KEY  from tTeam a where a.expired='F' and a.IsCalcPerf='1'" ></house:xtdmMulit>
					</li>	
					<li class="search-group-shrink">
						<input type="checkbox" id="roleBox" name="roleBox" onclick="changeValue()"/><p>设计师关系单计事业部业绩</p>
						<button type="button" class="btn btn-sm btn-system"
							onclick="doQuery();">查询</button>
						<button type="button" class="btn btn-sm btn-system"
							onclick="clearForm();">清空</button>
					</li>
				</ul>
			</form>
		</div>
		</ul>
		</form>
	</div>
 	<div class="container-fluid">
		<ul class="nav nav-tabs">
			<li id="tabDetail" class="active" onclick="setExcel('业绩明细','detailDataTable')"><a
				href="#tab_Detail" data-toggle="tab">明细</a>
			</li>
			 <li id="tabYwb" class="" onclick="setExcel('业务部业绩汇总','ywbDataTable')"><a
				href="#tab_Ywb" data-toggle="tab">业务部</a>
			</li>
			<li id="tabSjb" class="" onclick="setExcel('设计部业绩汇总','sjbDataTable')"><a
				href="#tab_Sjb" data-toggle="tab">设计部</a>
			</li>
			<li id="tabSyb" class="" onclick="setExcel('事业部业绩汇总','sybDataTable')"><a
				href="#tab_Syb" data-toggle="tab">事业部</a>
			</li>
			<li id="tabGcb" class="" onclick="setExcel('工程部业绩汇总','gcbDataTable')"><a
				href="#tab_Gcb" data-toggle="tab">工程部</a>
			</li>
			<li id="tabYwy" class="" onclick="setExcel('业务员业绩','ywyDataTable')"><a
				href="#tab_Ywy" data-toggle="tab">业务员</a>
			</li>
			<li id="tabSjs" class="" onclick="setExcel('设计师业绩','sjsDataTable')"><a
				href="#tab_Sjs" data-toggle="tab">设计师</a>
			</li>
			<li id="tabFdy" class="" onclick="setExcel('翻单员业绩','fdyDataTable')"><a
				href="#tab_Fdy" data-toggle="tab">翻单员</a>
			</li>
			<li id="tabHty" class="" onclick="setExcel('绘图员排名','htyDataTable')"><a
				href="#tab_Hty" data-toggle="tab">绘图员</a>
			</li>
			<li id="tabYwzr" class="" onclick="setExcel('业务主任业绩','ywzrDataTable')"><a
				href="#tab_Ywzr" data-toggle="tab">业务主任</a>
			</li>
			<li id="tabYwyDlxx" class="" onclick="setExcel('业务员业绩(独立销售)','ywyDlxxDataTable')"><a
				href="#tab_Ywy_Dlxx" data-toggle="tab">业务员（独立销售）</a>
			</li>
			<li id="tabYwtd" class="" onclick="setExcel('业务团队','ywtdDataTable')"><a
				href="#tab_Ywtd" data-toggle="tab">业务团队</a>
			</li>
			<li id="tabSjtd" class="" onclick="setExcel('设计团队','sjtdDataTable')"><a
				href="#tab_Sjtd" data-toggle="tab">设计团队</a>
			</li>
		</ul>
		<div class="tab-content">
			<div id="tab_Detail" class="tab-pane fade in active">
				<jsp:include page="perfCycle_tab_detail.jsp"></jsp:include>
			</div>
			<div id="tab_Ywb" class="tab-pane fade ">
				<jsp:include page="perfCycle_tab_ywb.jsp"></jsp:include>
			</div> 
			<div id="tab_Sjb" class="tab-pane fade ">
				<jsp:include page="perfCycle_tab_sjb.jsp"></jsp:include>
			</div>
			<div id="tab_Syb" class="tab-pane fade ">
				<jsp:include page="perfCycle_tab_syb.jsp"></jsp:include>
			</div>
			<div id="tab_Gcb" class="tab-pane fade ">
				<jsp:include page="perfCycle_tab_gcb.jsp"></jsp:include>
			</div>
			<div id="tab_Ywy" class="tab-pane fade ">
				<jsp:include page="perfCycle_tab_ywy.jsp"></jsp:include>
			</div>
			<div id="tab_Sjs" class="tab-pane fade ">
				<jsp:include page="perfCycle_tab_sjs.jsp"></jsp:include>
			</div>
			<div id="tab_Fdy" class="tab-pane fade ">
				<jsp:include page="perfCycle_tab_fdy.jsp"></jsp:include>
			</div>
			<div id="tab_Hty" class="tab-pane fade ">
				<jsp:include page="perfCycle_tab_hty.jsp"></jsp:include>
			</div>
			<div id="tab_Ywzr" class="tab-pane fade ">
				<jsp:include page="perfCycle_tab_ywzr.jsp"></jsp:include>
			</div>
			<div id="tab_Ywy_Dlxx" class="tab-pane fade ">
				<jsp:include page="perfCycle_tab_ywy_dlxx.jsp"></jsp:include>
			</div>
			<div id="tab_Ywtd" class="tab-pane fade ">
				<jsp:include page="perfCycle_tab_ywtd.jsp"></jsp:include>
			</div> 
			<div id="tab_Sjtd" class="tab-pane fade ">
				<jsp:include page="perfCycle_tab_sjtd.jsp"></jsp:include>
			</div>
		</div>
	</div> 
	</div>
</body>
</html>
