<%@ page language="java" import="com.house.framework.commons.utils.PathUtil" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://" + request.getServerName() + ":"+request.getServerPort()+path+"/";
String jasperPath = PathUtil.WEB_INF+"jsp/jasper/";
%>
<!DOCTYPE html>
<html>
<head>
	<title>工程完工列表</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp"%>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
	<script type="text/javascript">
	function clearForm(){
		$("#page_form input[type='text']").val("");
		$("#page_form select").val("");
		$("#status").val("");
		$.fn.zTree.getZTreeObj("tree_status").checkAllNodes(false);
		$("#checkStatus").val("");
		$.fn.zTree.getZTreeObj("tree_checkStatus").checkAllNodes(false);
		$("#endCode").val("");
		$.fn.zTree.getZTreeObj("tree_endCode").checkAllNodes(false);
		$("#custType").val("");
		$.fn.zTree.getZTreeObj("tree_custType").checkAllNodes(false);
		$("#constructStatus").val("");
		$.fn.zTree.getZTreeObj("tree_constructStatus").checkAllNodes(false);
	}
	
	function update(){
		var ret = selectDataTableRow();
	    if (ret) {
	    	 Global.Dialog.showDialog("gcwgUpdate",{
			  	title:"工程完工信息录入",
			  	url:"${ctx}/admin/gcwg/goUpdate",
			  	postData:{"id":ret.code},
			  	height:800,
			  	width:1100,
			  	returnFun: goto_query
			});
	    } else {
	    	art.dialog({
				content: "请选择一条记录"
			});
	    }
	}
	
	function finish(){
		var ret = selectDataTableRow();
	    if (ret) {
	    	if ($.trim(ret.status)!="4"){
	    		art.dialog({
					content: "此状态下客户不能进行完工操作!"
				});
	    		return;
	    	}
	    	Global.Dialog.showDialog("gcwgFinish",{
				title:"工程完工结转",
			  	url:"${ctx}/admin/gcwg/goFinish",
			  	postData:{"id":ret.code},
			  	height:350,
			  	width:800,
			  	returnFun: goto_query
			});
	    } else {
	    	art.dialog({
				content: "请选择一条记录"
			});
	    }
	}
	
	function finishReturn(){
		var ret = selectDataTableRow();
	    if (ret) {
	    	if ($.trim(ret.status)!="5" || $.trim(ret.checkstatus)!="1"){
	    		art.dialog({
					content: "处于归档状态且未结算客户,才能进行完工回退操作!"
				});
	    		return;
	    	}
	    	if ($.trim(ret.endcode)=="4" && $.trim(ret.hadcalcbackperf)=="1"){
	    		art.dialog({
					content: "已计算回退业绩的施工取消客户,不能进行完工回退操作!"
				});
	    		return;
	    	}
	    	art.dialog({
	    		content:"确认要对["+ret.code+"]["+ret.descr+"]客户进行完工退回操作吗?",
	    		lock: true,
	    		width: 200,
	    		height: 100,
	    		ok: function () {
	    			doFinishReturn();
	    	    	return true;
	    		},
	    		cancel: function () {
	    			return true;
	    		}
	    	});
	    } else {
	    	art.dialog({
				content: "请选择一条记录"
			});
	    }
	}
	
	function doFinishReturn(){
		var ret = selectDataTableRow();
		$.ajax({
			url:"${ctx}/admin/gcwg/doFinishReturn",
			type: "post",
			data: {code: ret.code},
			dataType: "json",
			cache: false,
			error: function(obj){
				showAjaxHtml({"hidden": false, "msg": "保存数据出错~"});
		    },
		    success: function(obj){
		    	if(obj.rs){
	    			art.dialog({
						content: obj.msg,
						time: 1000,
						beforeunload: function () {
		    				goto_query();
					    }
					});
		    	}else{
					art.dialog({
						content: obj.msg,
						width: 200
					});
		    	}
		    }
		});
	}
	
	function khjs(){
		var ret = selectDataTableRow();
		if (ret) {
			if ($.trim(ret.status)!="5" || $.trim(ret.checkstatus)!="1"){
	    		art.dialog({
					content: "处于归档状态且未结算客户，才能进行结算操作!"
				});
	    		return;
	    	}
			$.ajax({
				url:"${ctx}/admin/gcwg/beforeKhjs",
				type: "post",
				data: {code: ret.code},
				dataType: "json",
				cache: false,
				error: function(obj){
					showAjaxHtml({"hidden": false, "msg": "保存数据出错~"});
			    },
			    success: function(obj){
			    	if(obj.rs){
			    		Global.Dialog.showDialog("gcwgKhjs",{
							title:"客户结算",
						  	url:"${ctx}/admin/gcwg/goKhjs",
						  	postData:{"id":ret.code},
						  	height:350,
						  	width:500,
						  	returnFun: goto_query
						});
			    	}else{
						art.dialog({
							content: obj.msg,
							width: 200
						});
			    	}
			    }
			});
		}else{
			art.dialog({
				content: "请选择一条记录"
			});
		}
		
	}
	
	function khjsReturn(){
		var ret = selectDataTableRow();
	    if (ret) {
	    	if ($.trim(ret.checkstatus)!="2"){
	    		art.dialog({
					content: "已结算客户，才能进行结算退回操作!"
				});
	    		return;
	    	}
	    	art.dialog({
	    		content:"确认要对["+ret.code+"]["+ret.descr+"]客户进行结算退回操作吗?",
	    		lock: true,
	    		width: 200,
	    		height: 100,
	    		ok: function () {
	    			doKhjsReturn();
	    	    	return true;
	    		},
	    		cancel: function () {
	    			return true;
	    		}
	    	});
	    } else {
	    	art.dialog({
				content: "请选择一条记录"
			});
	    }
	}
	
	function doKhjsReturn(){
		var ret = selectDataTableRow();
		$.ajax({
			url:"${ctx}/admin/gcwg/doKhjsReturn",
			type: "post",
			data: {code: ret.code},
			dataType: "json",
			cache: false,
			error: function(obj){
				showAjaxHtml({"hidden": false, "msg": "保存数据出错~"});
		    },
		    success: function(obj){
		    	if(obj.rs){
	    			art.dialog({
						content: obj.msg,
						time: 1000,
						beforeunload: function () {
		    				goto_query();
					    }
					});
		    	}else{
					art.dialog({
						content: obj.msg,
						width: 200
					});
		    	}
		    }
		});
	}
	
	function view(){
		var ret = selectDataTableRow();
	    if (ret) {
	    	Global.Dialog.showDialog("gcwgDetail",{
			  	title:"工程完工—查看",
			  	url:"${ctx}/admin/gcwg/goDetail",
			  	postData:{"id":ret.code},
			  	height:window.screen.height-100,
			  	width:window.screen.width-40
			});
	    } else {
	    	art.dialog({
				content: "请选择一条记录"
			});
	    }
	}
	
	//导出EXCEL
	function doExcel(){
		var url = "${ctx}/admin/gcwg/doExcel";
		doExcelAll(url);
	}
	
	 //结算单打印
	function jsdPrint(){
		var row = selectDataTableRow();
		if(!row){
			art.dialog({content: "请选择一条记录进行打印操作！",width: 200});
			return false;
		}
		Global.Dialog.showDialog("gcwgJsdPrint",{
		  	title:"结算单打印",
		  	url:"${ctx}/admin/gcwg/goJsdPrint?custCode="+row.code,
		  	height:350,
		  	width:500,
		  	returnFun: goto_query
		});
	}
	 
	//记账打印
	function jzPrint(){
		var row = selectDataTableRow();
		if(!row){
			art.dialog({content: "请选择一条记录进行打印操作！",width: 200});
			return false;
		}
		$.ajax({
			url:"${ctx}/admin/gcwg/beforeJzdPrint",
			type: "post",
			data: {custCode: row.code},
			dataType: "json",
			cache: false,
			error: function(obj){
				showAjaxHtml({"hidden": false, "msg": "保存数据出错~"});
		    },
		    success: function(obj){
		    	if(obj.rs){
		    		art.dialog({
			   			 content:"有申请状态的增减项单，是否继续打印?",
			   			 lock: true,
			   			 width: 200,
			   			 height: 100,
			   			 ok: function () {
			   				doJzPrint();
			   			 },
			 			 cancel: function () {
							return true;
						 }
		    		});
		    	}else{
		    		doJzPrint();
		    	}
		    }
		});
	}
	function doJzPrint(){
		var row = selectDataTableRow();
		var reportName = "gcwg_jzd.jasper";
	    Global.Print.showPrint(reportName, {
			CustCode: row.code,
			CompanyName: "${companyName}",
			LogoFile : "<%=basePath%>jasperlogo/logo.jpg",
			SUBREPORT_DIR: "<%=jasperPath%>" 
		});
	}
	
	function qzjs(){
		var row = selectDataTableRow();
		if(!row){
			art.dialog({
				content: "请选择一条记录"
			});
			return;
		}
		if(row.constructstatus=="5"){
			art.dialog({
				content: "该记录已经进行过强制结算操作！",
				width:350
			});
			return;
		}
		art.dialog({
			content:"是否确定对“"+row.address+"”进行强制结算操作",
			lock: true,
			width: 200,
			height: 90,
			okValue:"是",
			cancelValue:"否",
			ok: function () {
				doQzjs();
			},
			cancel: function () {
				return;
			}
		});	
	}
	
	function doQzjs(){
		var row = selectDataTableRow();
		if(!row){
			art.dialog({
				content: "请选择一条记录"
			});
			return;
		}
		if(row.constructstatusdescr=="强制结算"){
			art.dialog({
				content: "该记录已经进行过强制结算操作！",
				width:350
			});
			return;
		}
		$.ajax({
				url:"${ctx}/admin/gcwg/doQzjs",
				type: "post",
				data: {code: row.code},
				dataType: "json",
				cache: false,
				error: function(obj){
					showAjaxHtml({"hidden": false, "msg": "保存数据出错~"});
			    },
			    success: function(obj){
			    		goto_query();
			    }
			});
	}
	
	function qzjsth(){
		var row = selectDataTableRow();
		if(!row){
			art.dialog({
				content: "请选择一条记录"
			});
			return;
		}
		if(row.constructstatus!="5"){
			art.dialog({
				content: "只有施工状态为强制结算才可进行该操作！",
				width:350
			});
			return;
		}
		art.dialog({
			content:"是否确定对“"+row.address+"”进行强制结算退回操作",
			lock: true,
			width: 200,
			height: 90,
			okValue:"是",
			cancelValue:"否",
			ok: function () {
				doQzjsth();
			},
			cancel: function () {
				return;
			}
		});	
	}
	function doQzjsth(){
		var row = selectDataTableRow();
		if(!row){
			art.dialog({
				content: "请选择一条记录"
			});
			return;
		}
		if(row.constructstatusdescr!="强制结算"){
			art.dialog({
				content: "只有施工状态为强制结算才可进行该操作！",
				width:350
			});
			return;
		}
		$.ajax({
				url:"${ctx}/admin/gcwg/doQzjsth",
				type: "post",
				data: {code: row.code},
				dataType: "json",
				cache: false,
				error: function(obj){
					showAjaxHtml({"hidden": false, "msg": "保存数据出错~"});
			    },
			    success: function(obj){
			    		goto_query();
			    }
			});
	}
	</script>
</head>
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form role="form" class="form-search" id="page_form" action=""
				method="post" target="targetFrame">
				<input type="hidden" name="jsonString" value="" />
				<ul class="ul-form">
					<li><label>客户编号</label> <input type="text" id="code"
						name="code" value="${customer.code}" />
					</li>
					<li><label>档案号</label> <input type="text" id="documentNo"
						name="documentNo" value="${customer.documentNo}" />
					</li>
					<li><label>楼盘</label> <input type="text" id="address"
						name="address" value="${customer.address}" />
					</li>
					<li><label>施工状态</label> <house:xtdmMulit id="constructStatus"
							dictCode="CONSTRUCTSTATUS"></house:xtdmMulit>
					</li>
					<li><label>材料已结算</label> <house:xtdm id="isItemCheck"
							dictCode="YESNO" value="${customer.isItemCheck}"></house:xtdm>
					</li>
					<li><label>凭证号</label> <input type="text" id="checkDocumentNo"
						name="checkDocumentNo" value="${customer.checkDocumentNo}" />
					</li>
					<li><label>设计师编号</label> <input type="text" id="designMan"
						name="designMan" value="${customer.designMan}" />
					</li>
					<li><label>业务员编号</label> <input type="text" id="businessMan"
						name="businessMan" value="${customer.businessMan}" />
					</li>
					<li><label>客户状态</label> <house:xtdmMulit id="status"
							dictCode="CUSTOMERSTATUS" selectedValue="${customer.status}"
							unShowValue="1,2,3"></house:xtdmMulit>
					</li>
					<li><label>客户结算状态</label> <house:xtdmMulit id="checkStatus"
							dictCode="CheckStatus" selectedValue="${customer.checkStatus}"></house:xtdmMulit>
					</li>
					<li><label>结束时间从</label> <input type="text" id="endDateFrom"
						name="endDateFrom" class="i-date"
						onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
						value="<fmt:formatDate value='${customer.endDateFrom}' pattern='yyyy-MM-dd'/>" />
					</li>
					<li><label>至</label> <input type="text" id="endDateTo"
						name="endDateTo" class="i-date"
						onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
						value="<fmt:formatDate value='${customer.endDateTo}' pattern='yyyy-MM-dd'/>" />
					</li>
					<li><label>客户结算日期从</label> <input type="text"
						id="custCheckDateFrom" name="custCheckDateFrom" class="i-date"
						onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
						value="<fmt:formatDate value='${customer.custCheckDateFrom}' pattern='yyyy-MM-dd'/>" />
					</li>
					<li><label>至</label> <input type="text" id="custCheckDateTo"
						name="custCheckDateTo" class="i-date"
						onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
						value="<fmt:formatDate value='${customer.custCheckDateTo}' pattern='yyyy-MM-dd'/>" />
					</li>
					<li><label>结束原因</label> <house:xtdmMulit id="endCode"
							dictCode="CUSTOMERENDCODE" selectedValue="${customer.endCode}"></house:xtdmMulit>
					</li>
					<li>
						<label>客户类型</label>
						<house:custTypeMulit id="custType" selectedValue="${customer.custType }" ></house:custTypeMulit>
					</li>
					<li class="search-group-shrink">
						<button type="button" class="btn btn-sm btn-system"
							onclick="goto_query();">查询</button>
						<button type="button" class="btn btn-sm btn-system"
							onclick="clearForm();">清空</button>
					</li>
				</ul>
			</form>
		</div>
		<div class="btn-panel">
			<div class="btn-group-sm">
				<house:authorize authCode="GCWG_UPDATE">
					<button type="button" class="btn btn-system" onclick="update()">完工信息录入</button>
				</house:authorize>
				<house:authorize authCode="GCWG_JSDPRINT">
					<button type="button" class="btn btn-system" onclick="jsdPrint()">结算单打印</button>
				</house:authorize>

				<button type="button" class="btn btn-system" onclick="jzPrint()">记账打印</button>

				<house:authorize authCode="GCWG_FINISH">
					<button type="button" class="btn btn-system" onclick="finish()">完工</button>
				</house:authorize>
				<house:authorize authCode="GCWG_FINISHRETURN">
					<button type="button" class="btn btn-system"
						onclick="finishReturn()">完工回退</button>
				</house:authorize>
				<house:authorize authCode="GCWG_KHJS">
					<button type="button" class="btn btn-system" onclick="khjs()">客户结算</button>
				</house:authorize>
				<house:authorize authCode="GCWG_KHJSRETURN">
					<button type="button" class="btn btn-system" onclick="khjsReturn()">结算退回</button>
				</house:authorize>
				<house:authorize authCode="GCWG_VIEW">
					<button type="button" class="btn btn-system" onclick="view()">查看</button>
				</house:authorize>
				<house:authorize authCode="GCWG_QZJS">
					<button type="button" class="btn btn-system" onclick="qzjs()">强制结算</button>
				</house:authorize>
				<house:authorize authCode="GCWG_QZJSTH">
					<button type="button" class="btn btn-system" onclick="qzjsth()">强制结算退回</button>
				</house:authorize>
				<button type="button" class="btn btn-system" onclick="doExcel()">导出excel</button>
			</div>
		</div>
		<div id="content-list">
			<table id="dataTable"></table>
			<div id="dataTablePager"></div>
		</div>
	</div>
	<script type="text/javascript">
		/**初始化表格*/
		$(function() {
			Global.JqGrid.initJqGrid("dataTable", {
				url : "${ctx}/admin/gcwg/goJqGrid",
				height : $(document).height() - $("#content-list").offset().top
						- 100,
				colModel : [ 
							{name: "code", index: "code", width: 70, label: "客户编号", sortable: true, align: "left"},
							{name: "documentno", index: "documentno", width: 70, label: "档案编号", sortable: true, align: "left"},
							{name: "descr", index: "descr", width: 70, label: "客户名称", sortable: true, align: "left"},
							{name: "address", index: "address", width: 140, label: "楼盘", sortable: true, align: "left"},
							{name: "custtypedescr", index: "custtypedescr", width: 70, label: "客户类型", sortable: true, align: "left"},
							{name: "status", index: "status", width: 70, label: "客户状态", sortable: true, align: "left", hidden: true},
							{name: "statusdescr", index: "statusdescr", width: 70, label: "客户状态", sortable: true, align: "left"},
							{name: "endcodedescr", index: "endcodedescr", width: 70, label: "结束原因", sortable: true, align: "left"},
							{name: "enddate", index: "enddate", width: 80, label: "结束时间", sortable: true, align: "left", formatter: formatDate},
							{name: "checkstatus", index: "checkstatus", width: 85, label: "客户结算状态", sortable: true, align: "left", hidden: true},
							{name: "checkstatusdescr", index: "checkstatusdescr", width: 100, label: "客户结算状态", sortable: true, align: "left"},
							{name: "custcheckdate", index: "custcheckdate", width: 100, label: "客户结算日期", sortable: true, align: "left", formatter: formatDate},
							{name: "isitemcheckdescr", index: "isitemcheckdescr", width: 85, label: "材料已结算", sortable: true, align: "left"},
							{name: "itemcheckdate", index: "itemcheckdate", width: 100, label: "材料结算日期", sortable: true, align: "left", formatter: formatDate},
							{name: "constructstatus", index: "constructstatus", width: 85, label: "施工状态", sortable: true, align: "left",hidden:true},
							{name: "constructstatusdescr", index: "constructstatusdescr", width: 85, label: "施工状态", sortable: true, align: "left"},
							{name: "constadate", index: "constadate", width: 120, label: "施工状态变动时间", sortable: true, align: "left", formatter: formatTime},
							{name: "checkdocumentno", index: "checkdocumentno", width: 100, label: "凭证号", sortable: true, align: "left"},
							{name: "contractfee", index: "contractfee", width: 70, label: "工程造价", sortable: true, align: "right", sum: true},
							{name: "designfee", index: "designfee", width: 60, label: "设计费", sortable: true, align: "right", sum: true},
							{name: "measurefee", index: "measurefee", width: 60, label: "量房费", sortable: true, align: "right", sum: true},
							{name: "drawfee", index: "drawfee", width: 60, label: "制图费", sortable: true, align: "right", sum: true},
							{name: "colordrawfee", index: "colordrawfee", width: 70, label: "效果图费", sortable: true, align: "right", sum: true},
							{name: "managefee", index: "managefee", width: 60, label: "管理费", sortable: true, align: "right", sum: true},
							{name: "basefee", index: "basefee", width: 60, label: "基础费", sortable: true, align: "right", sum: true},
							{name: "basedisc", index: "basedisc", width: 93, label: "基础协议优惠", sortable: true, align: "right", sum: true},
							{name: "basefee_dirct", index: "basefee_dirct", width: 90, label: "基础直接费", sortable: true, align: "right", sum: true},
							{name: "basefee_comp", index: "basefee_comp", width: 90, label: "基础综合费", sortable: true, align: "right", sum: true},
							{name: "mainfee", index: "mainfee", width: 60, label: "主材费", sortable: true, align: "right", sum: true},
							{name: "mainfee_per", index: "mainfee_per", width: 130, label: "主材费(按比例提成)", sortable: true, align: "right", sum: true},
							{name: "maindisc", index: "maindisc", width: 70, label: "主材优惠", sortable: true, align: "right", sum: true},
							{name: "softfee", index: "softfee", width: 60, label: "软装费", sortable: true, align: "right", sum: true},
							{name: "softdisc", index: "softdisc", width: 70, label: "软装优惠", sortable: true, align: "right", sum: true},
							{name: "integratefee", index: "integratefee", width: 60, label: "集成费", sortable: true, align: "right", sum: true},
							{name: "integratedisc", index: "integratedisc", width: 70, label: "集成优惠", sortable: true, align: "right", sum: true},
							{name: "cupboardfee", index: "cupboardfee", width: 60, label: "橱柜费", sortable: true, align: "right", sum: true},
							{name: "cupboarddisc", index: "cupboarddisc", width: 70, label: "橱柜优惠", sortable: true, align: "right", sum: true},
							{name: "chgbasefee", index: "chgbasefee", width: 80, label: "基础费变更", sortable: true, align: "right", sum: true},
							{name: "chgmainfee_per", index: "chgmainfee_per", width: 140, label: "主材变更(按比例提成)", sortable: true, align: "right", sum: true},
							{name: "chgsoftfee", index: "chgsoftfee", width: 80, label: "软装费变更", sortable: true, align: "right", sum: true},
							{name: "chgintfee", index: "chgintfee", width: 80, label: "集成费变更", sortable: true, align: "right", sum: true},
							{name: "chgcupfee", index: "chgcupfee", width: 80, label: "橱柜费变更", sortable: true, align: "right", sum: true},
							{name: "materialfee", index: "materialfee", width: 100, label: "物料人工耗用", sortable: true, align: "right", sum: true},
							{name: "designmandescr", index: "designmandescr", width: 60, label: "设计师", sortable: true, align: "left"},
							{name: "businessmandescr", index: "businessmandescr", width: 60, label: "业务员", sortable: true, align: "left"},
							{name: "projectmandescr1", index: "projectmandescr1", width: 70, label: "项目经理", sortable: true, align: "left"},
							{name: "projectmandescr", index: "projectmandescr", width: 80, label: "工程部经理", sortable: true, align: "left"},
							{name: "softtokenamount", index: "softtokenamount", width: 80, label: "软装券金额", sortable: true, align: "right"},
							{name: "lastupdate", index: "lastupdate", width: 120, label: "最后修改时间", sortable: true, align: "left", formatter: formatTime},
							{name: "lastupdatedby", index: "lastupdatedby", width: 60, label: "修改人", sortable: true, align: "left"},
							{name: "expired", index: "expired", width: 70, label: "是否过期", sortable: true, align: "left"},
							{name: "actionlog", index: "actionlog", width: 60, label: "操作", sortable: true, align: "left"},
							{name: "hadcalcbackperf", index: "hadcalcbackperf", width: 60, label: "是否已计算回退业绩", sortable: true, align: "left",hidden: true}
			],
				ondblClickRow : function() {
					view();
				}
			});
			$("#designMan").openComponent_employee();
			$("#businessMan").openComponent_employee();
		});
	</script>
</body>
</html>


