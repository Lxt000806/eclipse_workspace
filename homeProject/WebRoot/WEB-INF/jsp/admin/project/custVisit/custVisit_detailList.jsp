<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://" + request.getServerName() + ":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="zh">
<head>
	<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=utf-8"/>
	<META HTTP-EQUIV="pragma" CONTENT="no-cache"/>
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"/>
	<META HTTP-EQUIV="expires" CONTENT="0"/>
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge"/>
	<title>客户回访管理——明细列表</title>
	<%@ include file="/commons/jsp/common.jsp"%>
	
	<!-- 修改bootstrap中各个row的边距 -->
	<style>
		.row{
			margin: 0px;
		}
		.col-sm-3{
			padding: 0px;
		}
		.col-sm-6{
			padding: 0px;
		}
		.col-sm-9{
			padding: 0px;
		}
		.col-sm-12{
			padding: 0px;
		}
	</style>
	<script src="${resourceRoot}/pub/component_supplier.js?v=${v}" type="text/javascript"></script>
</head>
    
<body>
	<div class="body-box-form">
		<div class="panel panel-system" >
		    <div class="panel-body">
		      	<div class="btn-group-xs" >
					<button type="button" class="btn btn-system " id="excelBtn" onclick="doExcel()">
						<span>导出Excel</span>
					</button>
					<button type="button" class="btn btn-system" id="closeBut" onclick="closeWin(false)">
						<span>关闭</span>
					</button>
				</div>
			</div>
		</div>
		<div class="body-box-list">  
			<div class="query-form">
				<form action="" method="post" id="page_form" class="form-search">
					<house:token></house:token>
					<input type="hidden" id="expired" name="expired"/>
					<input type="hidden" name="jsonString" value=""/>
					<ul class="ul-form">
						<div class="row">
							<div class="col-sm-9">
								<li>
									<label>回访日期从</label>
									<input type="text" id="dateFrom" name="dateFrom"
										class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
										value="<fmt:formatDate value='${custVisit.dateFrom}' pattern='yyyy-MM-dd'/>"/>
								</li>
								<li>
									<label>至</label>
									<input type="text" id="dateTo" name="dateTo"
										class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
										value="<fmt:formatDate value='${custVisit.dateTo}' pattern='yyyy-MM-dd'/>"/>
								</li>
								<li>
									<label>回访状态</label>
									<house:xtdm id="status" dictCode="VISITSTATUS" style="width:160px;" value="${custVisit.status}"/>
								</li>
								<li>
									<label>楼盘</label>
									<input type="text" id="address" name="address" style="width: 160px;"/>
								</li>
								<li>
									<label>工程部</label>
									<house:DictMulitSelect id="department2" dictCode="" 
										sql="select rtrim(Code) fd1, rtrim(Desc1) fd2 from tDepartment2 where expired='F' and DepType='3' order by DispSeq " 
										sqlLableKey="fd2" sqlValueKey="fd1">
									</house:DictMulitSelect>
								</li>
								<li>
									<label>问题状态</label>
									<house:xtdm id="promStatus" dictCode="PROMSTATUS" style="width:160px;" value="${custProblem.promStatus}"/>
								</li>
								<li>
									<label>回访类型</label>
									<house:DictMulitSelect id="visitType" dictCode="" 
										sql=" select rtrim(cbm) fd1, rtrim(note) fd2 from txtdm where id='VISITTYPE' " 
										sqlLableKey="fd2" sqlValueKey="fd1">
									</house:DictMulitSelect>
								</li>
								<li>
									<label>问题分类</label>
									<house:dict id="promType1" dictCode="" sql="select rtrim(Code) Code, rtrim(Code)+' '+rtrim(Descr) PromType1 from tPromType1 where expired='F' " 
										sqlValueKey="Code" sqlLableKey="PromType1" value="${custProblem.promType1}"/>
								</li>
								<li>
									<label>材料分类</label>
									<house:dict id="promType2" dictCode="" sql="select rtrim(Code) Code, rtrim(Code)+' '+rtrim(Descr) PromType2 from tPromType2 where expired='F' " 
										sqlValueKey="Code" sqlLableKey="PromType2" value="${custProblem.promType2}"/>
								</li>
								<li>
									<label>问题原因</label>
									<house:dict id="promRsn" dictCode="" sql="select rtrim(Code) Code, rtrim(Code)+' '+rtrim(Descr) PromRsn from tPromRsn where expired='F' " 
										sqlValueKey="Code" sqlLableKey="PromRsn" value="${custProblem.promRsn}"/>
								</li>
								<li>
									<label>供应商</label>
									<input type="text" id="supplCode" name="supplCode" style="width:160px;" value=""/>
								</li>
							</div>
							<div class="col-sm-3">
								<li>
									<input type="checkbox" id="expired_show" name="expired_show" value="${custVisit.expired}" 
										onclick="hideExpired(this)" ${custVisit.expired!='T' ?'checked':''}/>
									<!-- <label for="expired_show" style="margin-left: -3px;text-align: left;">隐藏过期</label> -->
									<span>隐藏过期</span>
									<button type="button" class="btn btn-system btn-sm" onclick="goto_query();">查询</button>
									<button id="clear" type="button" class="btn btn-system btn-sm" onclick="clearForm();">清空</button>
								</li>
							</div>
						</div>
					</ul>	
				</form>
			</div>
		</div>
		<div class="container-fluid" > 
			<div class="pageContent">
				<div id="content-list">
					<table id="dataTable"></table>
					<div id="dataTablePager"></div>
				</div>
			</div> 
		</div>
	</div>
</body>

<script type="text/javascript">
var postData = $("#page_form").jsonForm();
$(function(){
	$("#supplCode").openComponent_supplier();

	Global.JqGrid.initJqGrid("dataTable", {
		sortable: true,/* 列重排 */
		url: "${ctx}/admin/custVisit/goDetailListJqGrid",
		postData:postData ,
		height: $(document).height() - $("#content-list").offset().top - 70,
		styleUI: "Bootstrap", 
		colModel: [
			{name: "no", index: "no", width: 80, label: "回访编号", sortable: true, align: "left"},
			{name: "custcode", index: "custcode", width: 70, label: "客户编号", sortable: true, align: "left"},
			{name: "address", index: "address", width: 140, label: "楼盘", sortable: true, align: "left"},
			{name: "custdescr", index: "custdescr", width: 70, label: "业主姓名", sortable: true, align: "left"},
			{name: "mobile1", index: "mobile1", width: 80, label: "电话", sortable: true, align: "left"},
			{name: "custtypedescr", index: "custtypedescr", width: 70, label: "客户类型", sortable: true, align: "left"},
			{name: "visittypedescr", index: "visittypedescr", width: 70, label: "回访类型", sortable: true, align: "left"},
			{name: "remarks", index: "remarks", width: 140, label: "回访问题详情", sortable: true, align: "left"},
			{name: "statusdescr", index: "statusdescr", width: 70, label: "回访状态", sortable: true, align: "left"},
			{name: "satisfactiondescr", index: "satisfactiondescr", width: 70, label: "满意度", sortable: true, align: "left"},
			{name: "visitczydescr", index: "visitczydescr", width: 70, label: "回访人", sortable: true, align: "left"},
			{name: "visitdate", index: "visitdate", width: 130, label: "回访时间", sortable: true, align: "left", formatter: formatTime},
			{name: "projectmandescr", index: "projectmandescr", width: 70, label: "项目经理", sortable: true, align: "left"},
			{name: "gcdeptdescr", index: "gcdeptdescr", width: 70, label: "工程部", sortable: true, align: "left"},
			{name: "designmandescr", index: "designmandescr", width: 70, label: "设计师", sortable: true, align: "left"},
			{name: "designdeptdescr", index: "designdeptdescr", width: 70, label: "设计部", sortable: true, align: "left"},
			{name: "promstatus", index: "promstatus", width: 70, label: "问题状态", sortable: true, align: "left"},
			{name: "promtype1descr", index: "promtype1descr", width: 70, label: "问题分类", sortable: true, align: "left"},
			{name: "promtype2descr", index: "promtype2descr", width: 70, label: "材料分类", sortable: true, align: "left"},
			{name: "promrsndescr", index: "promrsndescr", width: 70, label: "问题原因", sortable: true, align: "left"},
			{name: "supplcodedescr", index: "supplcodedescr", width: 70, label: "供应商", sortable: true, align: "left"},
			{name: "dealremarks", index: "dealremarks", width: 70, label: "处理结果", sortable: true, align: "left"},
		]
	});
	
	/* 清空下拉选择树checked状态 */
	$("#clear").on("click",function(){
		$("#department2").val("");
		$.fn.zTree.getZTreeObj("tree_department2").checkAllNodes(false);
		$("#visitType").val("");
		$.fn.zTree.getZTreeObj("tree_visitType").checkAllNodes(false);
		
	});
	
});
function doExcel(){
	var url = "${ctx}/admin/custVisit/doExcelDetail";
	doExcelAll(url);
}
</script>
</html>
