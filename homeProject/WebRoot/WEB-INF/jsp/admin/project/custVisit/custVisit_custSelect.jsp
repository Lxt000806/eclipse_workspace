<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8"/>
	<META HTTP-EQUIV="pragma" CONTENT="no-cache"/>
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"/>
	<META HTTP-EQUIV="expires" CONTENT="0"/>
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge"/>
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
		.form-search .ul-form li label {
		    width: 60px;
		}
	</style>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
</head>

<body>
	<div class="body-box-form">
		<div class="panel panel-system" >
		    <div class="panel-body">
		      	<div class="btn-group-xs" >
					<button type="submit" class="btn btn-system " id="saveBtn">
						<span>保存</span>
					</button>
					<button type="button" class="btn btn-system " id="selectAllBtn">
						<span>全选</span>
					</button>
					<button type="button" class="btn btn-system " id="selectNoneBtn">
						<span>不选</span>
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
					<input type="hidden" name="jsonString" value=""/>
					<input type="hidden" id="expired" name="expired" value="${customer.expired}"/>
					<input type="hidden" id="visitType" name="visitType" value="${visitType}">
					<input type="hidden" id="haveCheck" name="haveCheck" value="${arr}"><!-- 已经选中的客户 -->
					<ul class="ul-form">
						<div class="row">
							<div class="col-sm-3">
								<li id="prjItemLi">
									<label>施工项目</label>
									<house:xtdm id="prjItem" dictCode="PRJITEM" style="width:160px;" value=""/>
								</li>
								<li>
									<label>客户类型</label>
									<house:DictMulitSelect id="custType" dictCode="" sql="select code ,desc1 descr from tcustType where expired='F'" 
									sqlValueKey="Code" sqlLableKey="Descr" selectedValue=""></house:DictMulitSelect>
								</li>
								<li>
									<label>楼盘</label>
									<input type="text" id="address" name="address" style="width: 160px;"/>
								</li>
							</div>
							<div class="col-sm-6">
								<li id="endDateLi">
									<label>结束日期从</label>
									<input type="text" id="endDateFrom" name="endDateFrom"
										class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
										value=""/>
								</li>
								<li id="toLi1">
									<label>至</label>
									<input type="text" id="endDateTo" name="endDateTo"
										class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
										value=""/>
								</li>
								
								<li id="beginDateLi">
									<label>开工日期从</label>
									<input type="text" id="beginDateFrom" name="beginDateFrom"
										class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
										value=""/>
								</li>
								<li id="toLi2">
									<label>至</label>
									<input type="text" id="beginDateTo" name="beginDateTo"
										class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
										value=""/>
								</li>
								<li id="checkOutDateLi">
									<label>结算日期从</label>
									<input type="text" id="checkOutDateFrom" name="checkOutDateFrom"
										class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
										value=""/>
								</li>
								<li id="toLi3">
									<label>至</label>
									<input type="text" id="checkOutDateTo" name="checkOutDateTo"
										class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
										value=""/>
								</li>
								<li>
									<label>回访日期从</label>
									<input type="text" id="dateFrom" name="dateFrom"
										class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
										value="<fmt:formatDate value='${customer.dateFrom}' pattern='yyyy-MM-dd'/>"/>
								</li>
								<li>
									<label>至</label>
									<input type="text" id="dateTo" name="dateTo"
										class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
										value="<fmt:formatDate value='${customer.dateTo}' pattern='yyyy-MM-dd'/>"/>
								</li>
							</div>
							<div class="col-sm-3">
								<li>
									<input type="checkbox" id="expired_show" name="expired_show" value="${customer.expired}" 
										onclick="hideExpired(this)" ${customer.expired!='T' ?'checked':''}/>
									<label id="expiredLabel" for="expired_show" style="margin-left: -3px;width: 100px;text-align: left;"></label>
								</li>
								<li>
									<button type="button" class="btn btn-system btn-sm" onclick="goto_query();">查询</button>
									<button id="clear" type="button" class="btn btn-system btn-sm" onclick="clearForm();">清空</button>
								</li>
							</div>
						</div>
					</ul>	
				</form>
			</div>
		</div>
	</div>
	<div class="pageContent">
		<div id="content-list">
			<table id="dataTable"></table>
			<div id="dataTablePager"></div>
		</div>
	</div>
</body>	
<script>
$(function() {

 	switch ("${visitType}") {
	case "1":
		$("#prjItemLi").prop("hidden",true);
		$("#checkOutDateLi").prop("hidden",true);
		$("#toLi3").prop("hidden",true);
		$("#endDateLi").prop("hidden",true);
		$("#toLi1").prop("hidden",true);
		$("#expiredLabel").text("隐藏已开工回访");
		$("#beginDateFrom").prop("value","<fmt:formatDate value='${beginDate}' pattern='yyyy-MM-dd'/>");/* 刚开始就设置从当前月的1号到月末 */
		$("#beginDateTo").prop("value","<fmt:formatDate value='${endDate}' pattern='yyyy-MM-dd'/>");
		break;
	case "2":
		$("#checkOutDateLi").prop("hidden",true);
		$("#toLi3").prop("hidden",true);
		$("#expiredLabel").text("隐藏已中期回访");
		$("#endDateFrom").prop("value","<fmt:formatDate value='${beginDate}' pattern='yyyy-MM-dd'/>");
		$("#endDateTo").prop("value","<fmt:formatDate value='${endDate}' pattern='yyyy-MM-dd'/>");
		break;
	case "3":
		$("#checkOutDateLi").prop("hidden",true);
		$("#toLi3").prop("hidden",true);
		$("#expiredLabel").text("隐藏已后期回访");
		$("#endDateFrom").prop("value","<fmt:formatDate value='${beginDate}' pattern='yyyy-MM-dd'/>");
		$("#endDateTo").prop("value","<fmt:formatDate value='${endDate}' pattern='yyyy-MM-dd'/>");
		break;
	case "4":
		$("#prjItemLi").prop("hidden",true);
		$("#beginDateLi").prop("hidden",true);
		$("#toLi2").prop("hidden",true);
		$("#endDateLi").prop("hidden",true);
		$("#toLi1").prop("hidden",true);
		$("#expiredLabel").text("隐藏已结算回访");
		$("#checkOutDateFrom").prop("value","<fmt:formatDate value='${beginDate}' pattern='yyyy-MM-dd'/>");
		$("#checkOutDateTo").prop("value","<fmt:formatDate value='${endDate}' pattern='yyyy-MM-dd'/>");
		break;
	default:
		art.dialog({
			content: "未选择访问类型",
			width: 200
		});
		break;
	}
	
	var postData = $("#page_form").jsonForm();
	
	Global.JqGrid.initJqGrid("dataTable", {
		sortable: true,
		multiselect : true,/* 多选 */
		url: "${ctx}/admin/custVisit/searchJqGrid",
		postData:postData ,
		height: $(document).height() - $("#content-list").offset().top - 80,
		styleUI: "Bootstrap", 
		colModel: [
			{name: "no", index: "no", width: 80, label: "回访编号", sortable: true, align: "left", hidden: true},
			{name: "code", index: "custcode", width: 70, label: "客户编号", sortable: true, align: "left", hidden: true},
			{name: "address", index: "address", width: 140, label: "楼盘", sortable: true, align: "left"},
			{name: "custtype", index: "custtype", width: 70, label: "客户类型num", sortable: true, align: "left", hidden: true},
			{name: "custtypedescr", index: "custtypedescr", width: 70, label: "客户类型", sortable: true, align: "left"},
			{name: "confirmbegin", index: "confirmbegin", width: 100, label: "开工时间", sortable: true, align: "left", formatter: formatDate},
			{name: "projectman", index: "projectman", width: 70, label: "项目经理num", sortable: true, align: "left", hidden: true},
			{name: "projectmandescr", index: "projectmandescr", width: 70, label: "项目经理", sortable: true, align: "left"},
			{name: "gcdeptdescr", index: "gcdeptdescr", width: 70, label: "工程部", sortable: true, align: "left"},
			{name: "prjitemdescr", index: "prjitemdescr", width: 150, label: "当前进度", sortable: true, align: "left"},
			{name: "visittypedescr", index: "visittypedescr", width: 70, label: "回访状态", sortable: true, align: "left"},
			{name: "visitdate", index: "visitdate", width: 130, label: "回访时间", sortable: true, align: "left", formatter: formatTime}
		],
		ondblClickRow: function(){
			view();
		}
	});

	//保存	
	$("#saveBtn").on("click",function(){
     	var ids = $("#dataTable").jqGrid("getGridParam", "selarrrow");
     	if(ids.length==0){
     		Global.Dialog.infoDialog("请选择要回访的客户");	
     		return;
     	}
     	var selectRows = [];
 		$.each(ids,function(k,id){
 			var row = $("#dataTable").jqGrid("getRowData", id);
 			selectRows.push(row);
 		});
 		
 		Global.Dialog.returnData = selectRows;
 		closeWin();
 	});
	
	//全选
	$("#selectAllBtn").on("click",function(){
		Global.JqGrid.jqGridSelectAll("dataTable",true);
		var ids = $("#dataTable").jqGrid("getGridParam", "selarrrow");
			$("#nowNo").val(ids.length); 
			$("#nowNo1").val(ids.length); 
	});
	
	//不选
	$("#selectNoneBtn").on("click",function(){
		Global.JqGrid.jqGridSelectAll("dataTable",false);
		var ids = $("#dataTable").jqGrid("getGridParam", "selarrrow");
			$("#nowNo").val(ids.length); 
			$("#nowNo1").val(ids.length); 
	});
	
	/* 清空下拉选择树checked状态 */
	$("#clear").on("click",function(){
		$("#custType").val("");

		$.fn.zTree.getZTreeObj("tree_custType").checkAllNodes(false);
	});
		
});

</script>
</html>
