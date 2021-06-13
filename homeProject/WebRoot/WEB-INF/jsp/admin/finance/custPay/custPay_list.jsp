<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<title>客户付款列表</title>
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
var rowId=1;//默认选中行id
var custRet;//修改客户资料中的选中行
function clearForm(){
	$("#page_form input[type='text']").val('');
	$("#page_form select").val('');
	$("#status").val('');
	$.fn.zTree.getZTreeObj("tree_status").checkAllNodes(false);
	$("#custType").val('');
	$.fn.zTree.getZTreeObj("tree_custType").checkAllNodes(false);
	$("#searchType").val("0");
}

function custPay(){
	var ret = selectDataTableRow();
	rowId = $("#dataTable").jqGrid('getGridParam', 'selrow');
	Global.Dialog.showDialog("custPay",{
		  title:"客户付款",
		  url:"${ctx}/admin/custPay/goCustPay?code="+ret.code+"&custType="+ret.custtype+"&payType="+ret.paytype,
		  height:750,
		  width:1300,
		  returnFun: goto_query
	});
}
function payPlan(){
	var ret = selectDataTableRow();
	rowId = $("#dataTable").jqGrid('getGridParam', 'selrow');
	Global.Dialog.showDialog("payPlan",{
		  title:"客户付款计划",
		  url:"${ctx}/admin/custPay/goPayPlan?code="+ret.code+"&custType="+ret.custtype+"&payType="+ret.paytype,
		  height : 500,
		  width : 650,
		  returnFun: goto_query
	});
}
function updateCust(){
	custRet = selectDataTableRow();
	rowId = $("#dataTable").jqGrid('getGridParam', 'selrow');
	var status=checkStatus(custRet.code);
	if(custRet.status!=status){
		art.dialog({
			content: "客户【"+custRet.address+"】状态发生改变，请刷新数据！"
		});
		return;
	}
	if(parseFloat(custRet.status)>4){
		art.dialog({
			content: "客户状态为【"+custRet.statusdescr+"】不允许修改！"
		});
		return;
	}
	Global.Dialog.showDialog("updateCust",{
		  title:"客户信息--编辑",
		  url:"${ctx}/admin/custPay/goUpdateCust?code="+custRet.code+"&custType="+custRet.custtype+"&payType="+custRet.paytype,
		  height : 430,
		  width : 650,
		  returnFun: goto_query
	});
}
function repairCard(){
	var ret = selectDataTableRow();
	rowId = $("#dataTable").jqGrid('getGridParam', 'selrow');
	var status=checkStatus(ret.code);
	if(ret.status!=status){
		art.dialog({
			content: "客户【"+ret.address+"】状态发生改变，请刷新数据！"
		});
		return;
	}
	Global.Dialog.showDialog("repairCard",{
		  title:"客户信息--保修卡",
		  url:"${ctx}/admin/custPay/goRepairCard?code="+ret.code+"&custType="+ret.custtype+"&payType="+ret.paytype,
		  height : 600,
		  width : 700,
		  returnFun: goto_query
	});
}
function detailQuery(){
	Global.Dialog.showDialog("detailQuery",{
		  title:"客户付款明细查询",
		  url:"${ctx}/admin/custPay/goDetailQuery",
		  height : 700,
		  width : 1300,
		  returnFun: goto_query
	});
}
function checkStatus(code){
		var status;
		$.ajax({
				url : "${ctx}/admin/custPay/findCustStatus",
				type : "post",
				data : {code:code},
				dataType : "json",
				cache : false,
				async:false,
				error : function(obj) {
					showAjaxHtml({
						"hidden" : false,
						"msg" : "保存数据出错~"
					});
				},
				success : function(obj) {
					status=obj[0].status;
				}
		}); 
		return status;
}
function checkSetDate(code){
		var setDate;
		$.ajax({
				url : "${ctx}/admin/custPay/findCustStatus",
				type : "post",
				data : {code:code},
				dataType : "json",
				cache : false,
				async:false,
				error : function(obj) {
					showAjaxHtml({
						"hidden" : false,
						"msg" : "保存数据出错~"
					});
				},
				success : function(obj) {
					setDate=obj[0].setDate;
				}
		}); 
		return setDate;
}
function viewPay(){
	var ret = selectDataTableRow();
	Global.Dialog.showDialog("viewPay",{
		  title:"查看付款",
		  url:"${ctx}/admin/custPay/goViewPay?code="+ret.code+"&custType="+ret.custtype+"&payType="+ret.paytype,
		  height:750,
		  width:1300,
		  returnFun: goto_query
	});
}
//导出EXCEL
function doExcel(){
	var url = "${ctx}/admin/custPay/doExcel";
	doExcelAll(url);
}

function importCustPay() {
    Global.Dialog.showDialog("importCustPay", {
        title: "收款信息——从Excel导入",
        url: "${ctx}/admin/custPay/goImportExcel",
        height: 600,
        width: 1200
    });
}

 //批量打印
function qPrint(){
    //查看窗口
	Global.Dialog.showDialog("goQPrint",{
 	  	title: "批量打印",
 	  	url: "${ctx}/admin/custPay/goQPrint",
 	    height: 800,
		width:1200,
	});       	
}
/**初始化表格*/
$(function(){
        //初始化表格
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/custPay/goJqGrid",
			height:$(document).height()-$("#content-list").offset().top-100,
			postData:{status:"3,4",searchType:"0"},
			colModel : [
				{name: "code", index: "code", width: 70, label: "客户编号", sortable: true, align: "left"},
				{name: "documentno", index: "documentno", width: 70, label: "档案编号", sortable: true, align: "left"},
				{name: "descr", index: "descr", width: 70, label: "客户名称", sortable: true, align: "left"},
				{name: "address", index: "address", width: 140, label: "楼盘", sortable: true, align: "left"},
				{name: "regiondescr", index: "regiondescr", width: 60, label: "片区", sortable: true, align: "left"},
				{name: "custtype", index: "custtype", width: 70, label: "客户类型", sortable: true, align: "left",hidden:true},
				{name: "custtypedescr", index: "custtypedescr", width: 70, label: "客户类型", sortable: true, align: "left"},
				{name: "status", index: "status", width: 70, label: "客户状态", sortable: true, align: "left",hidden:true},
				{name: "statusdescr", index: "statusdescr", width: 70, label: "客户状态", sortable: true, align: "left"},
				{name: "contractstatusdescr", index: "contractstatusdescr", width: 70, label: "设计协议", sortable: true, align: "left"},
				{name: "endcodedescr", index: "endcodedescr", width: 70, label: "结束代码", sortable: true, align: "left"},
				{name: "contractfee", index: "contractfee", width: 70, label: "工程造价", sortable: true, align: "right"},
				{name: "designfee", index: "designfee", width: 70, label: "设计费", sortable: true, align: "right"},
				{name: "zjxje", index: "zjxje", width: 80, label: "增减项金额", sortable: true, align: "right", sum: true},
				{name: "haspay", index: "haspay", width: 70, label: "已付款", sortable: true, align: "right"},
				{name: "firstpay", index: "firstpay", width: 70, label: "首批付款", sortable: true, align: "right"},
				{name: "secondpay", index: "secondpay", width: 70, label: "二批付款", sortable: true, align: "right"},
				{name: "thirdpay", index: "thirdpay", width: 70, label: "三批付款", sortable: true, align: "right"},
				{name: "fourpay", index: "fourpay", width: 70, label: "尾批付款", sortable: true, align: "right"},
				{name: "designmandescr", index: "designmandescr", width: 70, label: "设计师", sortable: true, align: "left"},
				{name: "businessmandescr", index: "businessmandescr", width: 70, label: "业务员", sortable: true, align: "left"},
				{name: "againmandescr", index: "againmandescr", width: 70, label: "翻单员", sortable: true, align: "left"},
				{name: "scenedesignman", index: "scenedesignman", width: 90, label: "现场设计师", sortable: true, align: "left"},
				{name: "confirmbegin", index: "confirmbegin", width: 110, label: "实际开工时间", sortable: true, align: "left", formatter: formatDate},
				{name: "repaircarddate", index: "repaircarddate", width: 110, label: "保修卡审核时间", sortable: true, align: "left", formatter: formatDate},
				{name: "repaircardmandescr", index: "repaircardman", width: 100, label: "保修卡领取人", sortable: true, align: "left",hidden:true},
				{name: "repaircardmandescr", index: "repaircardmandescr", width: 100, label: "保修卡领取人", sortable: true, align: "left"},
				{name: "cddate", index: "cddate", width: 120, label: "水电图片审核时间", sortable: true, align: "left", formatter: formatDate},
				{name: "cdman", index: "cdman", width: 107, label: "水电图片领取人", sortable: true, align: "left",hidden:true},
				{name: "cdmandescr", index: "cdmandescr", width: 107, label: "水电图片领取人", sortable: true, align: "left"},
				{name: "cancanceldescr", index: "cancanceldescr", width: 81, label: "是否可退订", sortable: true, align: "left"},
				{name: "lastupdate", index: "lastupdate", width: 120, label: "最后修改时间", sortable: true, align: "left", formatter: formatTime},
				{name: "lastupdatedby", index: "lastupdatedby", width: 100, label: "操作人员", sortable: true, align: "left"},
				{name: "paytype", index: "paytype", width: 100, label: "付款方式", sortable: true, align: "left",hidden:true},
            ],
            gridComplete:function(){
            	var isSaveSelect=true;//是否保留选中行
            	var searchStatus=$("#status").val();//搜索条件中的客户状态
            	var searchSetDateFrom=$("#setDateFrom").val();//搜索条件中的下定时间从
            	var searchSetDateTo=$("#setDateTo").val();//搜索条件中的下定时间至
            	if(custRet){
					var status=checkStatus(custRet.code);//修改后的状态
					var setDate=checkSetDate(custRet.code);//修改后的状态
					if(searchStatus.indexOf(status)==-1){//修改后的状态不在搜索条件范围以内
						isSaveSelect=false;
					}
					if((searchSetDateFrom!="" && formatDate(setDate)<searchSetDateFrom) 
					|| (searchSetDateTo!="" && formatDate(setDate)>searchSetDateTo)){//修改后的下定时间不在搜索范围内
						isSaveSelect=false;
					}
				}
				if(isSaveSelect){//满足保留条件的
	            	$('.ui-jqgrid .ui-jqgrid-bdiv').scrollTop(24*(parseFloat(rowId-1)));//滚动条滚动
	            	$("#dataTable").jqGrid("setSelection",rowId);//选中保存前的行
				}
            }
		});
		$("#dataTable").jqGrid('setFrozenColumns');
		$("#designMan").openComponent_employee();
		$("#businessMan").openComponent_employee();
		$("#code").openComponent_customer();
});
</script>
<style type="text/css">

</style>
</head>
<body>
	<div class="body-box-list">
		<div class="query-form" >
			<form role="form" class="form-search" id="page_form" action=""
				method="post" target="targetFrame">
				<input type="hidden" id="expired" name="expired"
					value="${customer.expired }" /> <input type="hidden"
					name="jsonString" value="" />
				<ul class="ul-form">
					<li><label>客户名称</label> <input type="text" id="descr"
						name="descr" style="width:160px;" value="${customer.descr }" />
					</li>
					<li><label>楼盘</label> <input type="text" id="address"
						name="address" style="width:160px;" value="${customer.address }" />
					</li>
					<li><label>客户编号</label> <input type="text" id="code"
						name="code" style="width:160px;" value="${customer.code }" />
					</li>
					<li><label>档案编号</label> <input type="text" id="documentNo"
						name="documentNo" style="width:160px;"
						value="${customer.documentNo }" />
					</li>
					<li><label>设计师编号</label> <input type="text" id="designMan"
						name="designMan" style="width:160px;"
						value="${customer.designMan}" />
					</li>
					<li><label>业务员编号</label> <input type="text" id="businessMan"
						name="businessMan" style="width:160px;"
						value="${customer.businessMan}" />
					</li>
					<li><label>客户来源</label> <house:xtdm id="source"
							dictCode="CUSTOMERSOURCE" value="${customer.source }"></house:xtdm>
					</li>
					<li><label>一级部门</label> <house:department1 id="department1"
							onchange="changeDepartment1(this,'department2','${ctx }')"
							value="${customer.department1 }"></house:department1>
					</li>
					<li><label>二级部门</label> <house:department2 id="department2"
							dictCode="${customer.department1 }"
							value="${customer.department2 }"
							onchange="changeDepartment2(this,'department3','${ctx }')"></house:department2>
					</li>
					<li><label>三级部门</label> <house:department3 id="department3"
							dictCode="${customer.department2 }"
							value="${customer.department3 }"></house:department3>
					</li>
							<li><label>下定时间从</label> <input type="text" id="setDateFrom"
								name="setDateFrom" class="i-date" style="width:160px;"
								onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
								value="" />
							</li>
							<li><label>至</label> <input type="text" id="setDateTo"
								name="setDateTo" class="i-date" style="width:160px;"
								onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
								value="" />
							</li>
							<li><label>签订时间从</label> <input type="text"
								id="signDateFrom" name="signDateFrom" class="i-date"
								style="width:160px;"
								onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
								value="" />
							</li>
							<li><label>至</label> <input type="text" id="signDateTo"
								name="signDateTo" class="i-date" style="width:160px;"
								onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
								value="" />
							</li>
							<li><label>客户状态</label> <house:xtdmMulit id="status"
									dictCode="CUSTOMERSTATUS" selectedValue="3,4"></house:xtdmMulit>
							</li>
							<li><label></label>&ensp;<select id="searchType"
								name="searchType">
									<option value="0">部门按设计师搜索</option>
									<option value="1">部门按业务员搜索</option>
							</select>
							</li>
							<li>
								<label>客户结算日期从</label> 
								<input type="text" id="custCheckDateFrom" name="custCheckDateFrom" class="i-date"
									onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
									/>
							</li>
							<li>
								<label>到</label>
								<input type="text" id="custCheckDateTo" name="custCheckDateTo" class="i-date"
									onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
									 />
							</li>
							<li>
								<label>客户类型</label>
								<house:custTypeMulit id="custType" ></house:custTypeMulit>
							</li>
							<li>
								<label>设计协议</label> 
								<house:xtdm id="contractStatus" dictCode="CONTRACTSTAT" ></house:xtdm>
							</li>
							<li class="search-group-shrink">
							<input type="checkbox"
								id="expired_show" name="expired_show"
								value="${customer.expired}" onclick="hideExpired(this)"
								${customer.expired!='T' ?'checked':'' }/>
								<p>隐藏过期</p>
								<button type="button" class="btn  btn-sm btn-system "
									onclick="goto_query();">查询</button>
								<button type="button" class="btn btn-sm btn-system "
									onclick="clearForm();">清空</button>
							</li>
				</ul>
			</form>
		</div>
		<!--query-form-->
		<div class="btn-panel">
			 <div class="btn-group-sm">
            	<house:authorize authCode="CUSTPAY_CUSTPAY">
            	<button type="button" class="btn btn-system" onclick="custPay()">客户付款</button>
                </house:authorize>
                <house:authorize authCode="CUSTPAY_VIEWPAY">
                <button type="button" class="btn btn-system" onclick="viewPay()">查看付款</button>
				</house:authorize>
				<house:authorize authCode="CUSTPAY_PAYPLAN">
				<button type="button" class="btn btn-system" onclick="payPlan()">付款计划</button>
				</house:authorize>
				<house:authorize authCode="CUSTPAY_UPDATECUST">
				<button type="button" class="btn btn-system" onclick="updateCust()">修改客户资料</button>
				</house:authorize>
				<house:authorize authCode="CUSTPAY_REPAIRCARD">
				<button type="button" class="btn btn-system" onclick="repairCard()">保修卡</button>
				</house:authorize>
                <button type="button" class="btn btn-system" onclick="detailQuery()">明细查询</button>
                <house:authorize authCode="CUSTPAY_QPRINT">
				<button type="button" class="btn btn-system" onclick="qPrint()">批量打印</button>
				</house:authorize>
				<button type="button" class="btn btn-system" onclick="doExcel()">导出excel</button>
				<house:authorize authCode="CUSTPAY_IMPORTEXCEL">
					<button type="button" class="btn btn-system" onclick="importCustPay()">导入客户付款</button>
				</house:authorize>
			</div>
		</div>
		<div id="content-list">
			<table id= "dataTable"></table>
			<div id="dataTablePager"></div>
		</div>
	</div>
</body>
</html>


