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
	<title>工程退款管理</title>
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_builderGroup.js?v=${v}" type="text/javascript"></script>
	<script type="text/javascript">
	function doExcel(){
		var url = "${ctx}/admin/returnPay/doExcel";
		doExcelAll(url);
	}
	/**初始化表格*/
	$(function(){
        //初始化表格
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/returnPay/goJqGrid",
			postData:{status:"*"},
            styleUI: 'Bootstrap',   
			height:$(document).height()-$("#content-list").offset().top-100,
			colModel : [
			  	{name: "no", index: "no", width: 70, label: "退款单号", sortable: true, align: "left"},
			  	{name: "status", index: "status", width: 70, label: "账单状态编号", sortable: true, align: "left",hidden:true},
			  	{name: "type", index: "type", width: 80, label: "类型", sortable: true, align: "left", hidden: true},
			  	{name: "typedescr", index: "typedescr", width: 80, label: "类型", sortable: true, align: "left"},
			  	{name: "statusdescr", index: "statusdescr", width: 70, label: "账单状态", sortable: true, align: "left"},
				{name: "documentno", index: "documentno", width: 109, label: "凭证号", sortable: true, align: "left"},
				{name: "checkdate", index: "checkdate", width: 140, label: "退款日期", sortable: true, align: "left",formatter: formatTime},
				{name: "appczy", index: "appczy", width: 60, label: "开单人", sortable: true, align: "left",hidden:true},
				{name: "appczydescr", index: "appczydescr", width: 60, label: "开单人", sortable: true, align: "left"},
				{name: "date", index: "date", width: 140, label: "申请日期", sortable: true, align: "left",formatter: formatTime},
				{name: "confirmczy", index: "confirmczy", width: 70, label: "审核人员", sortable: true, align: "left",hidden:true},
				{name: "confirmczydescr", index: "confirmczydescr", width: 70, label: "审核人员", sortable: true, align: "left"},
				{name: "confirmdate", index: "confirmdate", width: 140, label: "审核日期", sortable: true, align: "left", formatter: formatTime},
				{name: "amount", index: "amount", width: 70, label: "金额", sortable: true, align: "left",sum:true},
				{name: "remarks", index: "remarks", width: 158, label: "备注", sortable: true, align: "left"},
				{name: "lastupdate", index: "lastupdate", width: 140, label: "最后更新时间", sortable: true, align: "left", formatter: formatTime},
				{name: "lastupdatedby", index: "lastupdatedby", width: 93, label: "最后更新人员", sortable: true, align: "left"},
				{name: "expired", index: "expired", width: 65, label: "是否过期", sortable: true, align: "left", hidden: true},
				{name: "actionlog", index: "actionlog", width: 70, label: "操作日志", sortable: true, align: "left"}
            ],
           
            ondblClickRow:function(rowid,iRow,iCol,e){
            	
				var ret = $("#dataTable").jqGrid("getRowData",rowid);
				
				if(ret){
					Global.Dialog.showDialog("returnPayView",{			
						title:"工程退款管理--查看",
						url:"${ctx}/admin/returnPay/goSave",
						postData:{
							m_umState:"V",
							no:$.trim(ret.no),
							appCzyDescr:ret.appczydescr,
							confirmCzyDescr:ret.confirmczydescr
						},
						height: 750,
						width:1100,
						returnFun: goto_query
					});
				}
            },
            gridComplete:function(){
				var amount_sum = $("#dataTable").getCol("amount",false,"sum");
				$("#dataTable").footerData("set",{"amount":myRound(amount_sum,2)});
			}
		});
		$("#custCode").openComponent_customer({condition:{status:"4,5"}});
		
		<%-- $("#print").on("click",function(){
			var ret = selectDataTableRow();
			var reportName = "cljsgl_jsd.jasper";
				Global.Print.showPrint(reportName, {
				CustCode:ret.custcode,
				ItemType1:$.trim(ret.itemtype1),
				LogoFile : "<%=basePath%>jasperlogo/logo.jpg",
				SUBREPORT_DIR: "<%=jasperPath%>"
			});
		}); --%>	
		
	});  
	function clearForm(){
		$("#page_form input[type='text']").val('');
		$("#page_form select").val("");
		$.fn.zTree.getZTreeObj("tree_status").checkAllNodes(false);
		$("#status").val("");
	} 
	function add(){	
		Global.Dialog.showDialog("returnPayAdd",{			
			title:"工程退款管理--新增",
			url:"${ctx}/admin/returnPay/goSave",
			height: 750,
			width:1100,
			returnFun: goto_query
		});

	}
	
	function update(){	
		var ret = selectDataTableRow();		
		if (ret) {
			if(ret.status==2){
				art.dialog({content: "工程退款单状态为审核，不允许进行编辑!",width: 200});
				return false;
			}
			if(ret.status==3){
				art.dialog({content: "工程退款单状态为取消，不允许进行编辑!",width: 200});
				return false;
			}				
			Global.Dialog.showDialog("returnPayUpdate",{			
				title:"工程退款管理--编辑",
				url:"${ctx}/admin/returnPay/goSave",
				postData:{
					m_umState:"M",
					no:$.trim(ret.no),
					appCzyDescr:ret.appczydescr,
					confirmCzyDescr:ret.confirmczydescr
				},
				height: 750,
				width:1100,
				returnFun: goto_query
			});
		}else{
			art.dialog({
				content: "请选择一条记录"
			});
		}
	}
	function check(){	
		var ret = selectDataTableRow();		
		if (ret) {
			if(ret.status==2){
				art.dialog({content: "工程退款单状态为审核，不允许进行审核!",width: 200});
				return false;
			}
			if(ret.status==3){
				art.dialog({content: "工程退款单状态为取消，不允许进行审核!",width: 200});
				return false;
			}	
			Global.Dialog.showDialog("returnPayCheck",{			
				title:"工程退款管理--审核",
				url:"${ctx}/admin/returnPay/goSave",
				postData:{
					m_umState:"C",
					no:$.trim(ret.no),
					appCzyDescr:ret.appczydescr,
					confirmCzyDescr:ret.confirmczydescr
				},
				height: 750,
				width:1100,
				returnFun: goto_query
			});
		}else{
			art.dialog({
				content: "请选择一条记录"
			});
		}
	}
	
	function batchCheck() {
	    var ret = selectDataTableRow();
	    if (ret.type !== "1") {
	        art.dialog({content: "请选择结算退款类型的记录！"});
	        return;
	    }
	    
	    if (ret.status.trim() !== "1") {
	        art.dialog({content: "请选择申请状态的记录！"});
            return;
	    }
	    
	    Global.Dialog.showDialog("returnPayBatchCheck", {
		    title: "工程退款管理--批量客户结算",
		    url: "${ctx}/admin/returnPay/goSave",
		    postData: {
		        m_umState: "BC",
		        no: $.trim(ret.no),
		        appCzyDescr: ret.appczydescr,
		        confirmCzyDescr: ret.confirmczydescr
		    },
		    height: 750,
		    width: 1100,
		    returnFun: goto_query
		});
	}
	
	function view(){	
		var ret = selectDataTableRow();		
		if (ret) {			
			Global.Dialog.showDialog("returnPayView",{			
				title:"工程退款管理--查看",
				url:"${ctx}/admin/returnPay/goSave",
				postData:{
					m_umState:"V",
					no:$.trim(ret.no),
					appCzyDescr:ret.appczydescr,
					confirmCzyDescr:ret.confirmczydescr
				},
				height: 750,
				width:1100,
				returnFun: goto_query
			});
		}else{
			art.dialog({
				content: "请选择一条记录"
			});
			return;
		}
	}
	
	function print(){
		var row = selectDataTableRow();
   		if(!row){
   			art.dialog({content: "请选择一条记录进行打印操作！"});
   			return false;
   		} 
       	Global.Print.showPrint("returnPay.jasper", {
			No: $.trim(row.no) ,
			Date: $.trim(row.checkdate) ,
			LogoPath : "<%=basePath%>jasperlogo/",
			SUBREPORT_DIR: "<%=jasperPath%>" 
		});
	}

	</script>
</head>
<body>
	<div class="body-box-list">
        <div class="query-form"  >  
	        <form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
				<input type="hidden" name="exportData" id="exportData">
				<input type="hidden" name="jsonString" value="" />
				<ul class="ul-form">
					
					<li>
						<label >申请日期</label>
						<input type="text" id="appDateFrom" name="appDateFrom" class="i-date"  
						onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
						value="<fmt:formatDate value='${returnPay.appDateFrom}' pattern='yyyy-MM-dd'/>" />
					</li>
					<li>
						<label >至</label>
						<input type="text" id="appDateTo" name="appDateTo" class="i-date"  
						onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
						value="<fmt:formatDate value='${returnPay.appDateTo}' pattern='yyyy-MM-dd'/>" />
					</li>	
					<li>
						<label>凭证号</label>
						<input type="text" id="documentNo" name="documentNo" style="width:160px;" value="${returnPay.documentNo}"/>
					</li>
					<li>
						<label>账单状态</label>
						<house:xtdmMulit id="status" dictCode="RETURNPAYSTATUS" selectedValue="1,2,3"></house:xtdmMulit>
					</li>
					<li >
						<label >客户编号</label>
						<input type="text" id="custCode" name="custCode" style="width:160px;" value="${returnPay.custCode }"  readonly="readonly"/>
					</li>
					<li>
					   <label>类型</label>
					   <house:xtdm id="type" dictCode="RETURNPAYTYPE"></house:xtdm>
					</li>
					<li >																	
						<button type="button" class="btn  btn-system btn-sm" onclick="goto_query();"  >查询</button>
						<button type="button" class="btn btn-system btn-sm" onclick="clearForm();"  >清空</button>
					</li>
				</ul>
			</form>
		</div>
		<div class="clear_float"> </div>
		<!--query-form-->
		<div class="pageContent">
			<div class="btn-panel" >
		    	<div class="btn-group-sm"  >
					<house:authorize authCode="RETURNPAY_ADD">
						<button type="button" class="btn btn-system " onclick="add()">新增</button>
					</house:authorize>
					<house:authorize authCode="RETURNPAY_UPDATE">
						<button type="button" class="btn btn-system "  onclick="update()">编辑</button>
					</house:authorize>
					<house:authorize authCode="RETURNPAY_BATCHCHECK">
						<button type="button" class="btn btn-system "  onclick="batchCheck()">批量客户结算</button>
					</house:authorize>
					<house:authorize authCode="RETURNPAY_CONFIRM">
						<button type="button" class="btn btn-system "  onclick="check()">审核</button>
					</house:authorize>
					<house:authorize authCode="RETURNPAY_VIEW">
						<button type="button" class="btn btn-system " onclick="view()">查看</button>
					</house:authorize>
					<button type="button" class="btn btn-system " onclick="doExcel()">导出excel</button>
					<house:authorize authCode="RETURNPAY_VIEW">
						<button type="button" class="btn btn-system " onclick="print()">打印</button>
					</house:authorize>
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


