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
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<title>预付金管理</title>
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script type="text/javascript">
	/**初始化表格*/
	$(function(){
		Global.LinkSelect.initSelect("${ctx}/admin/item/itemTypeByAuthority","itemType1","itemType2","itemType3");
		$(function(){
			Global.JqGrid.initJqGrid("dataTable",{
				url:"${ctx}/admin/payManage/goJqGrid",
				styleUI: "Bootstrap",
				height:$(document).height()-$("#content-list").offset().top-70,
				colModel : [
			   	    {name: "no", index: "no", width: 80, label: "预付单号", sortable: true, align: "left"},
					{name: "bitemtype1", index: "bitemtype1", width: 80, label: "材料类型1", sortable: true, align: "left"},
					{name: "itemtype1", index: "itemtype1", width: 80, label: "材料类型1", sortable: true, align: "left",hidden:true},
					{name: "btype", index: "btype", width: 80, label: "预付类型", sortable: true, align: "left"},
					{name: "type", index: "type", width: 80, label: "预付类型", sortable: true, align: "left",hidden:true},
					{name: "bstatus", index: "bstatus", width: 60, label: "状态", sortable: true, align: "left"},
					{name: "status", index: "status", width: 60, label: "状态", sortable: true, align: "left",hidden:true},
					{name: "documentno", index: "documentno", width: 80, label: "凭证号", sortable: true, align: "left"},
					{name: "remarks", index: "remarks", width: 216, label: "备注", sortable: true, align: "left"},
					{name: "discapprovestatus", index: "discapprovestatus", width: 79, label: "预付单审批状态", sortable: true, align: "left",formatter: clickOpt },
					{name: "wfprocinstno", index: "wfprocinstno", width: 79, label: "流程审批状态", sortable: true, align: "left",hidden: true },
					{name: "procstatusdescr", index: "procstatusdescr", width: 79, label: "流程审批状态", sortable: true, align: "left",hidden: true },
					{name: "appemp", index: "appemp", width: 60, label: "申请人", sortable: true, align: "left", hidden: true},
					{name: "bappemp", index: "bappemp", width: 60, label: "申请人", sortable: true, align: "left", },
					{name: "appdate", index: "appdate", width: 135, label: "申请日期", sortable: true, align: "left", formatter: formatTime},
					{name: "bconfirmemp", index: "bconfirmemp", width: 80, label: "审核人", sortable: true, align: "left"},
					{name: "confirmdate", index: "confirmdate", width: 146, label: "审核日期", sortable: true, align: "left", formatter: formatTime},
					{name: "bpayemp", index: "bpayemp", width: 80, label: "付款人", sortable: true, align: "left"},
					{name: "payemp", index: "payemp", width: 80, label: "付款人", sortable: true, align: "left",hidden:true},
					{name: "paydate", index: "paydate", width: 80, label: "付款日期", sortable: true, align: "left", formatter: formatTime},
					{name: "lastupdate", index: "lastupdate", width: 90, label: "最后更新时间", sortable: true, align: "left", formatter: formatTime},
					{name: "lastupdatedby", index: "lastupdatedby", width: 90, label: "最后更新人员", sortable: true, align: "left"},
					{name: "expired", index: "expired", width: 70, label: "过期标志", sortable: true, align: "left"},
					{name: "actionlog", index: "actionlog", width: 70, label: "操作日志", sortable: true, align: "left"}
		           ],
		           gridComplete:function(){
					     var tableId="dataTable";
				   }
			});
		});  
	});
	function add(){	
		Global.Dialog.showDialog("payMangaeAdd",{			
			title:"预付金管理--添加",
			url:"${ctx}/admin/payManage/goSave",
			height: 700,
			width:1200,
			returnFun: goto_query
		});
	}
	
	function clickOpt(cellvalue, options, rowObject){
		if(rowObject==null){
	       	return "";
		}
		if(cellvalue == null){
			return "";
		}
		
		return "<a href=\"javascript:void(0)\" style=\"\" onclick=\"viewProc('"
	     			+rowObject.no+"')\">"+cellvalue+"</a>";
	}
	
	function viewProc(no){
		Global.Dialog.showDialog("goViewProcTrack",{ 
		title:"流程审批情况",
		url:"${ctx}/admin/payManage/goViewProcTrack",
		postData:{no: no},
			height: 702,
			width:1130,
			returnFun:goto_query
		});
	}
	
	function update(){
		var ret = selectDataTableRow();
		var status=ret.status;
		if(status!=1){
			art.dialog({content: "该预付单不在申请状态，无法进行审核操作!",width: 200});
			return false;
		}
		if(ret.discapprovestatus == "审批中" || ret.discapprovestatus == "已审批"){
			art.dialog({
				content:"存在未审批完成的单据，无法编辑"
			});
			return;
		}
	    if (ret) {    	
	    	Global.Dialog.showDialog("payMangaeupdate",{
				title:"预付金管理--编辑",
			  	url:"${ctx}/admin/payManage/goUpdate?id="+ret.no,
			  	height:700,
			  	width:1200,
			  	returnFun: goto_query
			});
	    } else {
	    	art.dialog({
				content: "请选择一条记录"
			});
	    }
	}
	function Check(){
		var ret = selectDataTableRow();
		var status=ret.status;
		if(status!=1){
		art.dialog({content: "该预付单不在申请状态，无法进行审核操作!",width: 200});
			return false;
		}
	    if (ret) {    	
	    	Global.Dialog.showDialog("payMangaeupdate",{
				title:"预付金管理--审核",
			  	url:"${ctx}/admin/payManage/goCheck?id="+ret.no,
			  	height:700,
			  	width:1200,
			  	returnFun: goto_query
			});
	    } else {
	    	art.dialog({
				content: "请选择一条记录"
			});
	    }
	}
	function back(){
		var ret = selectDataTableRow();
		var status=ret.status;
		var payemp=ret.payemp;
		if(status!=2){
			art.dialog({content: "只能对审核状态的预付单反审核操作!",width: 200});
			return false;
		}
		if(payemp!=''){
			art.dialog({content: "该预付单已经进行出纳签字操作，不能再进行反审核操作!",width: 200});
			return false;
		}
		if(ret.procstatusdescr == "审批中"){
			art.dialog({
				content:"存在未完成的审批流，需要审批流完成，才可以反审核"
			});
			return;
		}
	    if (ret) {    	
	    	Global.Dialog.showDialog("payMangaeupdate",{
				title:"预付金管理--反审核",
			  	url:"${ctx}/admin/payManage/goback?id="+ret.no,
			  	height:700,
			  	width:1200,
			  	returnFun: goto_query
			});
	    } else {
	    	art.dialog({
				content: "请选择一条记录"
			});
	    }
	}
	function Mark(){
		var ret = selectDataTableRow();
		var status=ret.status;
		var payemp=ret.payemp;
		if(status!=2){
			art.dialog({content: "只能对审核状态的预付金进行出纳签字操作!",width: 200});
			return false;
		}
		if(payemp!=''){
			art.dialog({content: "该预付单已经进行出纳签字操作!",width: 200});
			return false;
		}
	    if (ret) {    	
	    	Global.Dialog.showDialog("payMangaeMark",{
				title:"预付金管理--出纳签字",
			  	url:"${ctx}/admin/payManage/goMark?id="+ret.no,
			  	height:700,
			  	width:1200,
			  	returnFun: goto_query
			});
	    } else {
	    	art.dialog({
				content: "请选择一条记录"
			});
	    }
	}
	function view(){
		var ret = selectDataTableRow();
	    if (ret) {    	
	    	Global.Dialog.showDialog("goView",{
				title:"预付金管理--查看",
				url:"${ctx}/admin/payManage/goView?id="+ret.no,
				height:700,
				width:1200,
				returnFun: goto_query
			});
	    } else {
	    	art.dialog({
				content: "请选择一条记录"
			});
	    }
	}
	function print(){
		var row = selectDataTableRow();
		if(!row){
			art.dialog({content: "请选择一条记录进行打印操作！",width: 200});
			return false;
		} 
	   	Global.Dialog.showDialog("print",{
			title:"预付金管理--打印",
			url:"${ctx}/admin/payManage/goPrint",
			postData:{no: row.no, wfProcInstNo: row.wfprocinstno},
			height: 215,
         	width:490,
			returnFun: goto_query
		});
	}; 
	function MxSelect(){
		var ret = selectDataTableRow();
		var status=ret.status;
		var payemp=ret.payemp;
	    if (ret) {    	
	      Global.Dialog.showDialog("payMangaeMark",{
			  title:"预付金管理--明细查询",
			  url:"${ctx}/admin/payManage/goMxSelect",
			  height:700,
			  width:1200,
			  returnFun: goto_query
			});
	    } else {
	    	art.dialog({
				content: "请选择一条记录"
			});
	    }
	}
	function YeSelect(){
		var ret = selectDataTableRow();
		var status=ret.status;
		var payemp=ret.payemp;
	    if (ret) {    	
	      Global.Dialog.showDialog("payMangaeMark",{
			  title:"预付金管理--余额查询",
			  url:"${ctx}/admin/payManage/goYeSelect?",
			  height:700,
			  width:1200,
			  returnFun: goto_query
			});
	    } else {
	    	art.dialog({
				content: "请选择一条记录"
			});
	    }
	}
	function clearForm(){
		$("#page_form input[type='text']").val('');
		$("#page_form select").val('');
		$("#documentNo").val('');
		$("#appDate").val('');
		$("#appDate1").val('');
		$("#splpreaytype").val('');
		$("#no").val('');
		$("#itemType1").val('');
		$("#status").val('');
		$.fn.zTree.getZTreeObj("tree_status").checkAllNodes(false);
	} 
	function doExcel(){
		var url = "${ctx}/admin/payManage/doExcel"; 
		doExcelAll(url);
	}
	
	function procApply(){
		var ret = selectDataTableRow();
	    if (ret) {    	
	    	if($.trim("${czybh}") != $.trim(ret.appemp)){
	    		art.dialog({
	    			content: "只能提交本人申请的预付单",
	    		});
	    		return;
	    	}
	    	
	    	if(ret.procstatusdescr == "审批中"){
				art.dialog({
					content:"存在未审批完成的单据，无法再次发起"
				});
				return;
			}
		    
	    	Global.Dialog.showDialog("procApply",{
				title:"采购预付单",
				url:"${ctx}/admin/payManage/goWfProcApply",
				postData:{no: ret.no},
				height:700,
				width:1200,
				returnFun: goto_query
			});
	    } else {
	    	art.dialog({
				content: "请选择一条记录"
			});
	    }
	}
	
	function changeType(){
		var type = $("#type").val();
		if(type == "2"){
			$("#puNo").parent().removeClass("hidden");
		}else{
			$("#puNo").val("");
			$("#puNo").parent().addClass("hidden");
		}
	}
	</script>
</head>
<body>
	<div class="body-box-list">
		<div class="query-form">
			  <form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame" >								
				<input type="hidden" name="exportData" id="exportData">
				<input type="hidden" name="jsonString" value="" />
					<ul class="ul-form">
						<li>
						<label>预付单号</label>						
							<input type="text" id="no" name="no" value="${supplierPrepay.no }" />																									
						</li>
						<li>
						<label>预付类型</label>				
							<house:xtdm  id="type" dictCode="SPLPREPAYTYPE" value="${supplierPrepay.type}" onchange="changeType()"></house:xtdm>	
						</li>
						<li class="hidden">
							<label>采购单号</label>						
							<input type="text" id="puNo" name="puNo"  />																									
						</li>
						<li>
						<label>材料类型1</label>
							<select id="itemType1" name="itemType1" style="width: 166px;" ></select>
						</li>
						<li>
						<label>申请日期从</label>
							<input type="text" id="appDate" name="appDate" class="i-date"  
								 onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="${supplierPrepay.appDate}"/>
						</li>
						<li>
							<label>至</label>
							<input type="text" id="appdate1" name="appdate1" class="i-date"  
								 onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="${supplierPrepay.appdate1}"/>
						</li>
						<li>
							<label>凭证号</label>
							<input type="text" id="documentNo" name="documentNo" value="${supplierPrepay.documentNo }" />						
						</li>
						<li>
							<label>状态</label>
							<house:xtdmMulit id="status" dictCode="SPLPREPAYSTATUS" selectedValue="1,2,3"></house:xtdmMulit>
						</li>
						<li id="loadMore" >
							<button type="button" class="btn btn-system btn-sm" onclick="goto_query();" >查询</button>
							<button type="button" class="btn btn-system btn-sm" onclick="clearForm();" >清空</button>
						</li>
					</ul>
			</form>
		</div>
		 <div class="btn-panel" >
  			 <div class="btn-panel" >
		    	<div class="btn-group-sm"  >
					<house:authorize authCode="payMan_SAVE">
						<button type="button" class="btn btn-system " onclick="add()">添加</button>
					</house:authorize>
					<house:authorize authCode="payMan_UPDATE">
						<button type="button" class="btn btn-system "  onclick="update()">编辑</button>
					</house:authorize>
					<house:authorize authCode="payMan_CHECK">
						<button type="button" class="btn btn-system " onclick="Check()">审核</button>
					</house:authorize>
					<house:authorize authCode="payMan_BACK">
						<button type="button" class="btn btn-system "  onclick="back()">反审核</button>
					</house:authorize>
					<house:authorize authCode="payMan_PROCAPPLY">
						<button type="button" class="btn btn-system "  onclick="procApply()">提交审批</button>
					</house:authorize>
					<house:authorize authCode="payMan_MARK">
						<button type="button" class="btn btn-system " onclick="Mark()">出纳签字</button>
					</house:authorize>
					<house:authorize authCode="payMan_VIEW">
						<button type="button" class="btn btn-system "  onclick="view()">查看</button>
					</house:authorize>
						<button type="button" class="btn btn-system " onclick="MxSelect()">明细查询</button>
						<button type="button" class="btn btn-system "  onclick="YeSelect()">余额查询</button>
						<button type="button" class="btn btn-system " onclick="print()">打印</button>
						<button type="button" class="btn btn-system " onclick="doExcel()">导出excel</button>	
		    	</div>
			 </div>
			<!--panelBar-->
			<div class="pageContent">
			<div id="content-list">
				<table id= "dataTable"></table>
				<div id="dataTablePager"></div>
			</div>
		</div>
	</div>
</body>
</html>


