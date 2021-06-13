<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<title>材料销售分析--按单品</title>
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_item.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_supplier.js?v=${v}" type="text/javascript"></script>
<style type="text/css">

</style>
<script type="text/javascript">
	function doExcel() {
		var url ="${ctx}/admin/itemSaleAnalyse/doExcel";
		doExcelAll(url);
	}
$(function(){
	Global.LinkSelect.initSelect("${ctx}/admin/department1/byDepType","department1","department2","department3");

	//初始化表格
	Global.JqGrid.initJqGrid("dataTable",{
		//url:"${ctx}/admin/salaryCompareAnalyse/goJqGrid",
		height: $(document).height() - $("#content-list").offset().top-70,
		styleUI: "Bootstrap",
		postData:$("#page_form").jsonForm(),
		rowNum: 500,
		colModel : [
		  {name : "salarymon",index : "salarymon",width : 75,label:"薪酬月份",sortable : true,align : "left"},
		  {name : "empname",index : "empname",width : 75,label:"员工姓名",sortable : true,align : "left"},
		  {name : "cmpdescr",index : "cmpdescr",width : 75,label:"所属公司",sortable : true,align : "left"},
		  {name : "dept1descr",index : "dept1descr",width : 75,label:"一级部门",sortable : true,align : "left"},
		  {name : "dept2descr",index : "dept2descr",width : 75,label:"二级部门",sortable : true,align : "left"},
		  {name : "empnum",index : "empnum",width : 75,label:"人数",sortable : true,align : "right"},
		  {name : "positiondescr",index : "positiondescr",width : 75,label:"职位",sortable : true,align : "left"},
		  {name : "basesalary",index : "basesalary",width : 75,label:"应发",sortable : true,align : "right", sum: true, formatter: formatRound},
		  {name : "withhold",index : "withhold",width : 75,label:"扣款",sortable : true,align : "right", sum: true, formatter: formatRound},
		  {name : "taxsalary",index : "taxsalary",width : 75,label:"计税薪酬",sortable : true,align : "right", sum: true, formatter: formatRound},
		  {name : "incometax",index : "incometax",width : 75,label:"个税",sortable : true,align : "right", sum: true, formatter: formatRound},
		  {name : "realpaysalary",index : "realpaysalary",width : 75,label:"实发",sortable : true,align : "right", sum: true, formatter: formatRound},
		  {name : "unpaidsalary",index : "unpaidsalary",width : 75,label:"未付",sortable : true,align : "right", sum: true, formatter: formatRound},
	    ]
	});
	
	function formatRound(cellvalue, options, rowObject){
      	if(rowObject==null){
        	return '';
		}
		if(cellvalue == null){
			return cellvalue;
		}
      	if(cellvalue == 0){
      		return 0;
      	}
      	return myRound(cellvalue, 2);
   	}
	
	window.goto_query = function(){
		var salaryScheme = $("#salaryScheme").val();
		var salaryMon = $("#salaryMon").val();
		var calcRptType = $("#calcRptType").val();
		var salaryMon= $("#salaryMon").val();
		var salaryMonTo = $("#salaryMonTo").val();
		if(salaryMon > salaryMonTo && salaryMonTo != ""){
			art.dialog({
				content:"开始月份不能大于结束月份",
			});
			return;
		}
		console.log(calcRptType);
		if(calcRptType == "" || !calcRptType ){
			art.dialog({
				content:"统计方式不能为空",
			});
			return;
		}
		if(calcRptType == "1"){
			$("#dataTable").setGridParam().hideCol("salarymon");
			$("#dataTable").setGridParam().showCol("empname");
			$("#dataTable").setGridParam().showCol("cmpdescr");
			$("#dataTable").setGridParam().showCol("positiondescr");
			$("#dataTable").setGridParam().showCol("dept2descr");
			$("#dataTable").setGridParam().hideCol("empnum");
		}
		if(calcRptType == "2"){
			$("#dataTable").setGridParam().hideCol("salarymon");
			$("#dataTable").setGridParam().hideCol("empname");
			$("#dataTable").setGridParam().hideCol("cmpdescr");
			$("#dataTable").setGridParam().hideCol("positiondescr");
			$("#dataTable").setGridParam().hideCol("dept2descr");
			$("#dataTable").setGridParam().showCol("empnum");
		}
		if(calcRptType == "3"){
			$("#dataTable").setGridParam().showCol("salarymon");
			$("#dataTable").setGridParam().showCol("empname");
			$("#dataTable").setGridParam().showCol("cmpdescr");
			$("#dataTable").setGridParam().showCol("positiondescr");
			$("#dataTable").setGridParam().showCol("dept2descr");
			$("#dataTable").setGridParam().hideCol("empnum");
		}
		if(calcRptType == "4"){
			$("#dataTable").setGridParam().showCol("salarymon");
			$("#dataTable").setGridParam().hideCol("empname");
			$("#dataTable").setGridParam().hideCol("cmpdescr");
			$("#dataTable").setGridParam().hideCol("positiondescr");
			$("#dataTable").setGridParam().hideCol("dept2descr");
			$("#dataTable").setGridParam().showCol("empnum");
		}
		
		$("#dataTable").jqGrid("setGridParam",{
			postData:$("#page_form").jsonForm(),page:1,url: "${ctx}/admin/salaryCalcReport/goJqGrid"}).trigger("reloadGrid");
	}
	chgScheme();
});

function chgRptType(){
	var calcRptType = $("#calcRptType").val();
	if(calcRptType == "4" || calcRptType=="2"){
		$("#empName_li").hide();
		$("#empName").val("");
	} else {
		$("#empName_li").show();
	}
}

function chgScheme(){
	var salaryMonTo = $("#salaryMonTo").val();
	$.ajax({
		url:"${ctx}/admin/salaryPaymentQuery/getSchemeList",
		type: "post",
		data: {salaryMon: salaryMonTo},
		dataType: "json",
		cache: false,
		error: function(obj){
			showAjaxHtml({"hidden": false, "msg": "保存数据出错~"});
	    },
	    success: function(obj){
	   		setSchemeOption(obj);
	    }
	});
}

function setSchemeOption(opt){
	$("#salaryScheme").val("");
	$("#salaryScheme").find("option").remove();
	$("#salaryScheme").append("<option value=\"\">请选择...</option>")
	var salaeyScheme = "";
	if(opt.length > 0 ){
		for(var i = 0; i < opt.length; i++){
			$("#salaryScheme").append("<option value=\""+opt[i].SalaryScheme+"\">"+opt[i].Descr+"</option>")
			if(i=="0"){
				salaryScheme = opt[i].SalaryScheme;
			}
		}
		$("#salaryScheme").val(salaryScheme);
	}
	$("#salaryScheme").searchableSelect();
}
</script>
</head>
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form action="" method="post" id="page_form" class="form-search" >
				<input type="hidden" name="jsonString" value="" />
				<ul class="ul-form">
					<li class="form-validate">
						<label style="">开始月份</label>
						<house:dict id="salaryMon" dictCode="" sql=" select salaryMon, descr from tSalaryMon order by salaryMon desc"  
							 sqlValueKey="salaryMon" sqlLableKey="descr" value="${salaryData.salaryMon }"></house:dict>
					</li>
					<li class="form-validate">
						<label style="">结束月份</label>
						<house:dict id="salaryMonTo" dictCode="" sql=" select salaryMon, descr from tSalaryMon order by salaryMon desc"  
							 sqlValueKey="salaryMon" sqlLableKey="descr" value="${salaryData.salaryMonTo }" onchange="chgScheme()"></house:dict>
					</li>
					<li class="form-validate">
						<label>薪酬方案</label>
						<select id = "salaryScheme" name = "salaryScheme" value="">
							<option value="">请选择...</option>
						</select>
					</li>
					<li class="form-validate">
						<label style="">统计方式</label>
						<select id="calcRptType" name="calcRptType" onchange="chgRptType()">
							<option value="1">个人</option>
							<option value="2">部门</option>
							<option value="3">个人&月</option>
							<option value="4">部门&月</option>
						</select>
					</li>
					<li class="form-validate">
						<label>一级部门</label>
						<select id="department1" name="department1" value=""></select>
					</li>
					<li>
						<label>二级部门</label>
						<select id="department2" name="department2" value=""></select>
					</li>
					<li id="empName_li">
						<label style="">人员</label>
						<input type="text" id="empName" name="empName" placeholder="姓名/工号/身份证"/>
					</li>
					<li id="loadMore" >
						<button type="button" class="btn btn-sm btn-system " onclick="goto_query();"  >查询</button>
						<button type="button" class="btn btn-sm btn-system " onclick="clearForm();"  >清空</button>
					</li>
				</ul>
			</form>
		</div>
		<div class="btn-panel">
			<div id="content-list">
				<table id= "dataTable"></table>
			</div>
		</div> 
	</div>
</body>
</html>


