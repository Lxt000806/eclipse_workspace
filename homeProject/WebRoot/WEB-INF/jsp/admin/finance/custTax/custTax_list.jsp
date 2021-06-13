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
	<title>税务信息登记</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8"/>
	<META HTTP-EQUIV="pragma" CONTENT="no-cache"/>
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"/>
	<META HTTP-EQUIV="expires" CONTENT="0"/>
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge"/>
	<%@ include file="/commons/jsp/common.jsp"%>
	
</head>
<body>
	<div class="body-box-list">
		<div class="query-form">
		    <form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
		    	<house:token></house:token>
				<input type="hidden" name="jsonString" value=""/>
				<input type="hidden" id="expired" name="expired" value=""/>
				<ul class="ul-form">
					<li>
						<label>楼盘</label>
						<input type="text" id="address" name="address" style="width:160px;"/>
					</li>
					<li>
						<label>档案号</label>
						<input type="text" id="documentNo" name="documentNo" style="width:160px;"/>
					</li>
					<li>
						<label>客户名称</label>
						<input type="text" id="descr" name="descr" style="width:160px;"/>
					</li>
					<li>
						<label>客户状态</label>
						<house:xtdmMulit id="status" dictCode="CUSTOMERSTATUS" selectedValue="3,4,5"/>
					</li>
					<li>
						<label>下定时间从</label>
						<input type="text" id="setDateFrom" name="setDateFrom" class="i-date" style="width:160px;" 
							onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
							value=""/>
					</li>
					<li>
						<label>至</label>
						<input type="text" id="setDateTo" name="setDateTo" class="i-date" style="width:160px;" 
							onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
							value=""/>
					</li>
					<li>
						<label>签订时间从</label>
						<input type="text" id="signDateFrom" name="signDateFrom" class="i-date" style="width:160px;" 
							onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
							value=""/>
					</li>
					<li>
						<label>至</label>
						<input type="text" id="signDateTo" name="signDateTo" class="i-date" style="width:160px;" 
							onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
							value=""/>
					</li>
					<li>
						<label>客户结算日期从</label>
						<input type="text" id="custCheckDateFrom" name="custCheckDateFrom" class="i-date" style="width:160px;" 
							onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
							value=""/>
					</li>
					<li>
						<label>至</label>
						<input type="text" id="custCheckDateTo" name="custCheckDateTo" class="i-date" style="width:160px;" 
							onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
							value=""/>
					</li>
					<li>
						<label for="payeeCode">收款单位</label>
						<house:dict id="payeeCode" dictCode="" 
							sql=" select rtrim(Code) code,rtrim(Code)+' '+rtrim(Descr) descr from tTaxPayee where Expired = 'F' "
							sqlLableKey="descr" sqlValueKey="code">
						</house:dict>
					</li>
					<li>
						<label for="checkStatus">客户结算状态</label>
						<house:xtdmMulit id="checkStatus" dictCode="CheckStatus"/>
					</li>
					<li>
						<label>劳务分包公司</label>
						<house:DictMulitSelect id="laborCompny" dictCode="" 
							sql="select rtrim(Code) Code,rtrim(Descr) cd from tLaborCompny where expired='F' " 
							sqlLableKey="cd" sqlValueKey="Code">
						</house:DictMulitSelect>
					</li>
					<li>
						<label>劳务开票日期从</label>
						<input type="text" id="laborDateFrom" name="laborDateFrom" class="i-date" style="width:160px;" 
							onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
							value=""/>
					</li>
					<li>
						<label>至</label>
						<input type="text" id="laborDateTo" name="laborDateTo" class="i-date" style="width:160px;" 
							onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
							value=""/>
					</li>
					<li>
						<label for="isTaxComplete">是否完成开票</label>
						<house:xtdm id="isTaxComplete" dictCode="YESNO"/>
					</li>
					<li>
						<label>客户类型</label>
						<house:DictMulitSelect id="custType" dictCode="" 
							sql="select Code,Desc1 Descr from tCusttype where Expired='F' order by dispSeq " 
							sqlLableKey="Descr" sqlValueKey="Code">
						</house:DictMulitSelect>
					</li>
					<li class="search-group">
						<input type="checkbox" id="expired_show" name="expired_show" value="${custTax.expired}" 
							onclick="hideExpired(this)" ${custTax.expired!='T' ?'checked':''}/>
						<p>隐藏过期</p>
						<input type="checkbox" id="unShow" name="unShow" value="0" 
							onclick="changeUnShow(this)" />
						<p>不显示本次开票为0</p>
						<button type="button" class="btn btn-sm btn-system" onclick="goto_query();">查询</button>
						<button id="clear" type="button" class="btn btn-system btn-sm" onclick="clearForm();">清空</button>
					</li>
				</ul>
			</form>
		</div>
	</div>
	<div class="clear_float"></div>
	<div class="btn-panel">
 			<div class="btn-group-sm">
			<house:authorize authCode="CUSTTAX_ADD">
 				<button type="button" class="btn btn-system" id="save">
 					<span>新增</span>
 				</button>
 			</house:authorize>
 			<house:authorize authCode="CUSTTAX_UPDATE">
	 			<button type="button" class="btn btn-system" id="update">
	 				<span>编辑</span>
	 			</button>
	 		</house:authorize>
	 		<house:authorize authCode="CUSTTAX_DELETE">
	 			<button type="button" class="btn btn-system" id="delete">
					<span>删除</span>
				</button>
	 		</house:authorize>
			<house:authorize authCode="CUSTTAX_VIEW">
				<button type="button" class="btn btn-system" id="view" onclick="view()">
					<span>查看</span>
				</button>
		  	</house:authorize>
		  	<button type="button" class="btn btn-system" id="findInvoice">
				<span>开票查询</span>
			</button>
			<button type="button" class="btn btn-system" id="custInvoice">
				<span>导入开票明细</span>
			</button>
			<button type="button" class="btn btn-system" id="importlaborInvoice"> 
				<span>导入劳务分包开票</span>
			</button>
			<house:authorize authCode="CUSTTAX_LABORCTRLLIST">
				<button type="button" class="btn btn-system" id="laborCtrlList"> 
				<span>劳务分包查询</span>
			</button>
		  	</house:authorize>
		  	<house:authorize authCode="CUSTTAX_EXCEL">
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
<script>
$(function(){
	var postData = $("#page_form").jsonForm();
	var gridOption ={
		url:"${ctx}/admin/custTax/goJqGrid",
		postData:postData,
		sortable: true,
		height:$(document).height()-$("#content-list").offset().top-70,
		styleUI:"Bootstrap", 
		colModel : [
			{name: "custcode", index: "custcode", width: 70, label: "客户编号", sortable: true, align: "left"},
			{name: "documentno", index: "documentno", width: 90, label: "税务档案编号", sortable: true, align: "left"},
			{name: "descr", index: "descr", width: 70, label: "客户名称", sortable: true, align: "left"},
			{name: "address", index: "address", width: 100, label: "楼盘", sortable: true, align: "left"},
			{name: "area", index: "area", width: 50, label: "面积", sortable: true, align: "right"},
			{name: "contractday", index: "contractday", width: 70, label: "合同工期", sortable: true, align: "right"},
			{name: "regiondescr", index: "regiondescr", width: 70, label: "片区", sortable: true, align: "left"},
			{name: "custtypedescr", index: "custtypedescr", width: 70, label: "客户类型", sortable: true, align: "left"},
			{name: "cmpnyname", index: "cmpnyname", width: 90, label: "产品线归属", sortable: true, align: "left"},
			{name: "statusdescr", index: "statusdescr", width: 70, label: "客户状态", sortable: true, align: "left"},
			{name: "payeecodedescr", index: "payeecodedescr", width: 70, label: "收款单位", sortable: true, align: "left"},
			{name: "contractfeeaddedtax", index: "contractfeeaddedtax", width: 90, label: "合同工程款", sortable: true, align: "right"},
			{name: "custdesignfee", index: "custdesignfee", width: 90, label: "合同设计费", sortable: true, align: "right"},
			{name: "totalcontractfee", index: "totalcontractfee", width: 90, label: "合同总造价", sortable: true, align: "right"},
			{name: "sumchgamount", index: "sumchgamount", width: 80, label: "增减金额", sortable: true, align: "right"},
			{name: "custcountcost", index: "custcountcost", width: 70, label: "结算金额", sortable: true, align: "right"},
			{name: "sumdesignamount", index: "sumdesignamount", width: 95, label: "已开票设计费", sortable: true, align: "right"},
			{name: "sumprjamount", index: "sumprjamount", width: 95, label: "已开票工程款", sortable: true, align: "right"},
			{name: "istaxcomplete", index: "istaxcomplete", width: 90, label: "是否完成开票", sortable: true, align: "left"},
			{name: "paidamount", index: "paidamount", width: 80, label: "已付款", sortable: true, align: "right"},
			{name: "firstpay", index: "firstpay", width: 80, label: "首批付款", sortable: true, align: "right"},
			{name: "secondpay", index: "secondpay", width: 80, label: "二批付款", sortable: true, align: "right"},
			{name: "thirdpay", index: "thirdpay", width: 80, label: "三批付款", sortable: true, align: "right"},
			{name: "fourpay", index: "fourpay", width: 80, label: "尾批付款", sortable: true, align: "right"},
			{name: "paidnum", index: "paidnum", width: 90, label: "已缴款期数", sortable: true, align: "right"},
			{name: "thisdesignamount_overzero", index: "thisdesignamount_overzero", width: 110, label: "本次开票设计费", sortable: true, align: "right"},
			{name: "thisprjamount_overzero", index: "thisprjamount_overzero", width: 110, label: "本次开票工程款", sortable: true, align: "right"},
			{name: "laborcompnydescr", index: "laborcompnydescr", width: 100, label: "劳务分包公司", sortable: true, align: "left"},
			{name: "laboramount", index: "laboramount", width: 100, label: "劳务分包金额", sortable: true, align: "right"},
			{name: "ispubreturndescr", index: "ispubreturndescr", width: 70, label: "对公退款", sortable: true, align: "left"},
			{name: "setdate", index: "setdate", width: 100, label: "下定日期", sortable: true, align: "left", formatter: formatDate},
			{name: "signdate", index: "signdate", width: 100, label: "签订日期", sortable: true, align: "left", formatter: formatDate},
			{name: "confirmbegin", index: "confirmbegin", width: 100, label: "实际开工日期", sortable: true, align: "left", formatter: formatDate},
			{name: "checkstatusdescr", index: "checkstatusdescr", width: 90, label: "客户结算状态", sortable: true, align: "left"},
			{name: "custcheckdate", index: "custcheckdate", width: 100, label: "客户结算日期", sortable: true, align: "left", formatter: formatDate},
			/*{name: "basefee", index: "basefee", width: 70, label: "基础费", sortable: true, align: "right"},
			{name: "basedisc", index: "basedisc", width: 90, label: "基础协议优惠", sortable: true, align: "right"},
			{name: "mainfee", index: "mainfee", width: 70, label: "主材费", sortable: true, align: "right"},
			{name: "integratefee", index: "integratefee", width: 70, label: "集成费", sortable: true, align: "right"},
			{name: "cupboardfee", index: "cupboardfee", width: 70, label: "橱柜费", sortable: true, align: "right"},
			{name: "softfee", index: "softfee", width: 70, label: "软装费", sortable: true, align: "right"},
			{name: "mainservfee", index: "mainservfee", width: 90, label: "服务性产品费", sortable: true, align: "right"},*/
			{name: "designdescr", index: "designdescr", width: 60, label: "设计师", sortable: true, align: "left"},
			{name: "businessdescr", index: "businessdescr", width: 60, label: "业务员", sortable: true, align: "left"},
			{name: "againdescr", index: "againdescr", width: 60, label: "翻单员", sortable: true, align: "left"},
			{name: "cancanceldescr", index: "cancanceldescr", width: 80, label: "是否可退订", sortable: true, align: "left"},
			{name: "lastupdate", index: "lastupdate", width: 142, label: "最后修改时间", sortable: true, align: "left", formatter: formatTime},
			{name: "lastupdatedby", index: "lastupdatedby", width: 60, label: "修改人", sortable: true, align: "left"},
			
			/*{name: "returnamount", index: "returnamount", width: 70, label: "退款金额", sortable: true, align: "right"},
			{name: "constructtypedescr", index: "constructtypedescr", width: 70, label: "施工方式", sortable: true, align: "left"},
			{name: "adress", index: "adress", width: 100, label: "项目地址", sortable: true, align: "left"},
			{name: "layoutdescr", index: "layoutdescr", width: 50, label: "户型", sortable: true, align: "left"},
			{name: "remarks", index: "remarks", width: 140, label: "备注", sortable: true, align: "left"},
			{name: "expired", index: "expired", width: 70, label: "是否过期", sortable: true, align: "left"},
			{name: "actionlog", index: "actionlog", width: 50, label: "操作", sortable: true, align: "left"},*/
		],
		ondblClickRow: function(){
			view();
		}
	};
	Global.JqGrid.initJqGrid("dataTable",gridOption);

	$("#save").on("click",function(){
		Global.Dialog.showDialog("save",{
			title:"税务信息登记——新增",
			url:"${ctx}/admin/custTax/goSave",
			height:600,
			width:882,
			returnFun:goto_query
		});
	});
	
	$("#update").on("click",function(){
		ret=selectDataTableRow();
		if(!ret){
			art.dialog({
       			content: "请选择一条记录",
       		});
       		return;
		}
		Global.Dialog.showDialog("update",{
			title:"税务信息登记——编辑",
			url:"${ctx}/admin/custTax/goUpdate",
			postData:{custCode:ret.custcode},
			height:600,
			width:882,
			returnFun:goto_query
		});
	});
	
	$("#delete").on("click",function(){
		var url = "${ctx}/admin/custTax/doDelete";
		beforeDel(url,"custcode","删除该记录");
		returnFun: goto_query;
		return true;
	});

	$("#findInvoice").on("click",function(){
		Global.Dialog.showDialog("findInvoice",{
			title:"税务信息登记——开票查询",
			url:"${ctx}/admin/custTax/goFindInvoice",
			height:700,
			width:1050,
			returnFun:goto_query
		});
	});

	$("#custInvoice").on("click",function(){
		Global.Dialog.showDialog("custInvoice",{
			title:"税务信息登记——导入开票明细",
			url:"${ctx}/admin/custTax/goCustInvoice",
			height:700,
			width:1254,
			returnFun:goto_query
		});
	});
	
	$("#laborCtrlList").on("click",function(){
		Global.Dialog.showDialog("laborCtrlList",{
			title:"税务信息登记——劳务分包查询",
			url:"${ctx}/admin/custTax/goLaborCtrlList",
			height:700,
			width:1254,
			returnFun:goto_query
		});
	});
	
	$("#importlaborInvoice").on("click",function(){
		Global.Dialog.showDialog("importlaborInvoice",{
			title:"税务信息登记——导入劳务分包开票",
			url:"${ctx}/admin/custTax/goImportlaborInvoice",
			height:700,
			width:1254,
			returnFun:goto_query
		});
	});
	// 清空下拉选择树checked状态
	$("#clear").on("click",function(){
		$("#status").val("");
		$.fn.zTree.getZTreeObj("tree_status").checkAllNodes(false);
		$("#checkStatus").val("");
		$.fn.zTree.getZTreeObj("tree_checkStatus").checkAllNodes(false);
		$("#laborCompny").val("");
		$.fn.zTree.getZTreeObj("tree_laborCompny").checkAllNodes(false);
		$("#custType").val("");
		$.fn.zTree.getZTreeObj("tree_custType").checkAllNodes(false);
	});
	
});

function view(){
	ret=selectDataTableRow();
	if(!ret){
		art.dialog({
      			content: "请选择一条记录",
      		});
      		return;
	}
	Global.Dialog.showDialog("view",{
		title:"税务信息登记——查看",
		url:"${ctx}/admin/custTax/goView",
		postData:{custCode:ret.custcode,m_umState:"V"},
		height:600,
		width:882,
		returnFun:goto_query
	});
}

function doExcel(){
	var url = "${ctx}/admin/custTax/doExcel";
	doExcelAll(url);
}
function changeUnShow(obj){
	if ($(obj).is(":checked")){
		$("#unShow").val("1");
	}else{
		$("#unShow").val("0");
	}
}
</script>
</html>
