<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://" + request.getServerName() + ":"+request.getServerPort()+path+"/";
String jasperPath = PathUtil.WEB_INF+"jsp/jasper/";
%>

<!DOCTYPE HTML>
<html>
<head>
    <title>人工费用管理</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>

	<script type="text/javascript">
	//新增
	function save(){
		Global.Dialog.showDialog("save",{
			title:"人工费用管理-新增",
			url:"${ctx}/admin/laborFee/goSave",
			height: 700,
		 	width:1300,
			returnFun: goto_query 
		});
	}
	
	function laborDetail(){
		Global.Dialog.showDialog("save",{
			title:"人工费用管理-明细查询",
			url:"${ctx}/admin/laborFee/goLaborDetail",
			height: 800,
			width:1300,
			returnFun: goto_query 
		});
	}
	
	//编辑
	function update(){
		var ret = selectDataTableRow();
	    if (ret) {
	    	if($.trim(ret.status)!="1"&&$.trim(ret.status)!="2"){
	    		art.dialog({
	    			content:"该人工费用单不在申请或退回状态，无法进行编辑操作！",
	    		});
	    		return;
	    	}
			Global.Dialog.showDialog("Update",{
				title:"人工费用管理-编辑",
				url:"${ctx}/admin/laborFee/goUpdate?no="+ret.no,
				height:700,
		  width:1300,
				returnFun: goto_query
			});
		} else {
	    	art.dialog({
				content: "请选择一条记录"
			});
	    }
	}
	
	//审核
	function check(){
		var ret = selectDataTableRow();
		if (ret) {
			if($.trim(ret.status)!="1"&&$.trim(ret.status)!="2"){
	    		art.dialog({
	    			content:"该人工费用单不在申请或退回状态，无法进行审核操作！",
	    		});
	    		return;
	    	}
	      Global.Dialog.showDialog("check",{
			  title:"人工费用管理-审核",
			  url:"${ctx}/admin/laborFee/goCheck?no="+ret.no,
			  height:700,
		  	  width:1300,
			  returnFun: goto_query
			});
	    } else {
	    	art.dialog({
				content: "请选择一条记录"
			});
	    }
	}
	
	//输入凭证
	function insertDocumentNo(){
		var ret = selectDataTableRow();
		if (ret) {
			if($.trim(ret.status)!="3"){
	    		art.dialog({
	    			content:"只能对审核状态的人工费用单输入凭证号操作！",
	    		});
	    		return;
	    	}
	      Global.Dialog.showDialog("insertDocumentNo",{
			  title:"人工费用管理-输入凭证号",
			  url:"${ctx}/admin/laborFee/goUpdateDocumentNo?no="+ret.no,
			  height:700,
		  	  width:1300,
			  returnFun: goto_query
			});
	    } else {
	    	art.dialog({
				content: "请选择一条记录"
			});
	    }
	}
	
	//反审核
	function reCheck(){
		var ret = selectDataTableRow();
		if (ret) {
			if($.trim(ret.status)!="3"){
	    		art.dialog({
	    			content:"只能对审核状态的人工费用单进行反审核操作！",
	    		});
	    		return;
	    	}
	    	if(ret.procstatusdescr == "审批中"){
				art.dialog({
					content:"存在未审批完成的单据，不允许进行反审核"
				});
				return;
			}
	      Global.Dialog.showDialog("reCheck",{
			  title:"人工费用管理-反审核",
			  url:"${ctx}/admin/laborFee/goReCheck?no="+ret.no,
			  height:700,
		 	  width:1300,
			  returnFun: goto_query
			});
	    } else {
	    	art.dialog({
				content: "请选择一条记录"
			});
	    }
	}
	
	//出纳签字
	function signature(){
		var ret = selectDataTableRow();
		if (ret) {
			if($.trim(ret.status)!="3"){
	    		art.dialog({
	    			content:"只能对审核状态的人工费用单进行出纳签字操作！",
	    		});
	    		return;
	    	}
	      Global.Dialog.showDialog("reCheck",{
			  title:"人工费用管理-出纳签字",
			  url:"${ctx}/admin/laborFee/goSignature?no="+ret.no,
			  height:700,
		 	  width:1300,
			  returnFun: goto_query
			});
	    } else {
	    	art.dialog({
				content: "请选择一条记录"
			});
	    }
	}
	
	//查看
	function view(){
		var ret = selectDataTableRow();
	    if (ret) {
	      Global.Dialog.showDialog("laborFeeView",{
			  title:"人工费用管理-查看",
			  url:"${ctx}/admin/laborFee/goView?no="+ret.no,
			  height:720,
		 	  width:1300,
			});
	    } else {
	    	art.dialog({
				content: "请选择一条记录"
			});
	    }
	}
	
	function doExcel(){
		var url = "${ctx}/admin/laborFee/doExcel";
		doExcelAll(url);
	}
	//打印
	function print(){
		var ret = selectDataTableRow();
		
		Global.Dialog.showDialog("goPrint",{ 
     		title:"人工费用打印",
			url:"${ctx}/admin/laborFee/goPrint",
			postData:{no:ret.no,wfProcInstNo:ret.wfprocinstno},
          	height: 215,
         	width:490,
          	returnFun:goto_query
		});
	
	}
	//清空
	function clearForm(){
		$("#page_form input[type='text']").val("");
		$("#status").val("");
		$("#documentNo").val("");
		$.fn.zTree.getZTreeObj("tree_status").checkAllNodes(false);
	} 
	/**初始化表格*/
	$(function(){
		Global.LinkSelect.initSelect("${ctx}/admin/item/itemTypeByAuthority","itemType1");
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/laborFee/goJqGrid",
			postData:{status:"1,2,3"},
			height:$(document).height()-$("#content-list").offset().top-70,
			styleUI: "Bootstrap", 
			colModel : [
				{name: "no", index: "no", width: 107, label: "单号", sortable: true, align: "left"},
				{name: "itemtype1descr", index: "itemtype1descr", width: 79, label: "材料类型1", sortable: true, align: "left"},
				{name: "status", index: "status", width: 75, label: "状态", sortable: true, align: "left",hidden:true},
				{name: "statusdescr", index: "statusdescr", width: 75, label: "状态", sortable: true, align: "left"},
				{name: "documentno", index: "documentno", width: 117, label: "凭证号", sortable: true, align: "left"},
				{name: "actname", index: "actname", width: 73, label: "户名", sortable: true, align: "left"},
				{name: "cardid", index: "cardid", width: 165, label: "卡号", sortable: true, align: "left"},
				{name: "bank", index: "bank", width: 141, label: "开户行", sortable: true, align: "left"},
				{name: "appczy", index: "appczy", width: 86, label: "申请人编号", sortable: true, align: "left"},
				{name: "appczydecr", index: "appczydecr", width: 86, label: "申请人", sortable: true, align: "left"},
				{name: "date", index: "date", width: 90, label: "申请日期", sortable: true, align: "left", formatter: formatTime},
				{name: "procstatus", index: "procstatus", width: 79, label: "流程审批状态", sortable: true, align: "left",formatter:clickOpt},
				{name: "wfprocinstno", index: "wfprocinstno", width: 79, label: "流程审批状态", sortable: true, align: "left",hidden:true},
				{name: "procstatusdescr", index: "procstatusdescr", width: 79, label: "流程审批状态", sortable: true, align: "left",hidden:true},
				{name: "confirmczydecr", index: "confirmczydecr", width: 79, label: "审核人", sortable: true, align: "left"},
				{name: "confirmdate", index: "confirmdate", width: 107, label: "审核日期", sortable: true, align: "left", formatter: formatTime},
				{name: "payczydecr", index: "payczydecr", width: 71, label: "签字人", sortable: true, align: "left"},
				{name: "paydate", index: "paydate", width: 87, label: "签字日期", sortable: true, align: "left", formatter: formatTime},
				{name: "amount", index: "amoubt", width: 87, label: "金额", sortable: true, align: "right"},
				{name: "remarks", index: "remarks", width: 242, label: "备注", sortable: true, align: "left"},
				{name: "lastupdate", index: "lastupdate", width: 108, label: "最后更新时间", sortable: true, align: "left", formatter: formatTime},
				{name: "lastupdatedby", index: "lastupdatedby", width: 99, label: "最后更新人员", sortable: true, align: "left"},
				{name: "expired", index: "expired", width: 74, label: "过期标志", sortable: true, align: "left"},
				{name: "actionlog", index: "actionlog", width: 72, label: "操作日志", sortable: true, align: "left"}
			],
			ondblClickRow: function(){
				view();
			}
		});
	});
	
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
			url:"${ctx}/admin/laborFee/goViewProcTrack",
			postData:{no:no},
          	height: 572,
         	width:1030,
          	returnFun:goto_query
		});
   	}
   	
	// 调整提交审批界面
	function goWfProcApply(){
		var ret = selectDataTableRow();
		var czybh = $.trim("${czybh}");
		if(!ret){
			art.dialog({
				content:"请选择一条记录进行提交"
			});
			return;
		}
		if($.trim(ret.status)!="3"){
    		art.dialog({
    			content:"只能对审核状态的人工费用单提交审批！",
    		});
    		return;
    	}
		if(czybh != $.trim(ret.appczy)){
			art.dialog({
				content:"只能提交自己申请的记录"
			});
			return;
		}
		if(ret.procstatusdescr == "审批中"){
			art.dialog({
				content:"存在未审批完成的单据，无法再次发起"
			});
			return;
		}
		
		Global.Dialog.showDialog("goApply",{ 
     		title:"工地人工费用报销单",
			url:"${ctx}/admin/laborFee/goWfProcApply",
			postData:{key:"constructionExpenseClaim",no:ret.no},
          	height: 780,
         	width:1280,
          	returnFun:goto_query
         });
	};
	</script>
</head>
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
				<input type="hidden" name="jsonString" value=""/>
				<ul class="ul-form">
					<li>
						<label>单号</label>
						<input type="text" id="no" name="no" style="width:160px;" value="${laborFee.no }"/>
					</li>
					<li>
						<label>材料类型1</label>
						<select id="itemType1" name="itemType1" style="width: 160px;"></select>
					</li>
					<li>
						<label>凭证号</label>
						<input id="documentNo" name="documentNo" style="width: 160px;" />
					</li>
					<li>
						<label>户名</label>
						<input type="text" id="actName" name="actName" style="width:160px;" />
					</li>
					<li>
						<label>申请时间从 </label>
						<input type="text" style="width:160px;" id="dateFrom" name="dateFrom" class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"/>
					</li>
					<li>
						<label>至</label>
						<input type="text" style="width:160px;" id="dateTo" name="dateTo" class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"/>
					</li>
					<li>
						<label>审核时间从 </label>
						<input type="text" style="width:160px;" id="confirmDateFrom" name="confirmDateFrom" class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"/>
					</li>
					<li>
						<label>至</label>
						<input type="text" style="width:160px;" id="confirmDateTo" name="confirmDateTo" class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"/>
					</li>
					<li>
						<label>状态</label>
						<house:xtdmMulit id="status" dictCode="LABORFEESTATUS" selectedValue="1,2,3"></house:xtdmMulit>    
					</li>
					<li>
						<button type="button" class="btn btn-sm btn-system" onclick="goto_query();">查询</button>
						<button type="button" class="btn btn-sm btn-system" onclick="clearForm();">清空</button>
					</li>
				</ul>	
			</form>
		</div>
		<div class="pageContent">
			<div class="btn-panel">
    			<div class="btn-group-sm">
					<house:authorize authCode="LABORFEE_SAVE">
						<button type="button" class="btn btn-system" onclick="save()">
							<span>新增</span>
						</button>
	                </house:authorize>
	                <house:authorize authCode="LABORFEE_UPDATE">
						<button type="button" class="btn btn-system" onclick="update()">
							<span>编辑</span>
						</button>
					</house:authorize>
				    <house:authorize authCode="LABORFEE_CHECK">
						<button type="button" class="btn btn-system" onclick="check()">
							<span>审核</span>
						</button>
					</house:authorize>
					<house:authorize authCode="LABORFEE_INSERTDOCUMENTNO">
						<button type="button" class="btn btn-system" onclick="insertDocumentNo()">
							<span>输入凭证</span>
						</button>
					</house:authorize>
					<house:authorize authCode="LABORFEE_RECHECK">
						<button type="button" class="btn btn-system" onclick="reCheck()">
							<span>反审核</span>
						</button>
					</house:authorize>
					<house:authorize authCode="LABORFEE_SIGNATURE">
						<button type="button" class="btn btn-system" onclick="signature()">
							<span>出纳签字</span>
						</button>
					</house:authorize>
					<house:authorize authCode="LABORFEE_VIEW">
						<button type="button" class="btn btn-system" onclick="view()">
							<span>查看</span>
						</button>
	                </house:authorize>
					<button type="button" class="btn btn-system" onclick="laborDetail()">
						<span>查看明细</span>
					</button>
					<house:authorize authCode="LABORFEE_WFPROCAPPLY">
						<button type="button" class="btn btn-system" onclick="goWfProcApply()">
							<span>提交审批</span>
						</button>
					</house:authorize>
					<button type="button" class="btn btn-system" onclick="doExcel()" title="导出检索条件数据">
						<span>输出到excel</span>
					</button>
                    <button type="button" class="btn btn-system" onclick="print()">
						<span>打印</span>
					</button>
                </div>
			</div> 
			<div id="content-list">
				<table id="dataTable"></table>
				<div id="dataTablePager"></div>
			</div>
		</div> 
	</div>
</body>
</html>
