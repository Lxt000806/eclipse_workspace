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
	<title>材料结算管理</title>
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_builderGroup.js?v=${v}" type="text/javascript"></script>
	<script type="text/javascript">
	function doExcel(){
		var url = "${ctx}/admin/cljsgl/doExcel";
		doExcelAll(url);
	}
	/**初始化表格*/
	$(function(){
		Global.LinkSelect.initSelect("${ctx}/admin/item/itemTypeByAuthority","itemType1","itemType2","itemType3");
		Global.LinkSelect.setSelect({firstSelect:'itemType1',
								firstValue:'${itemCheck.itemType1 }',
								});
        //初始化表格
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/cljsgl/goJqGrid",
			postData:{status:"*"},
            styleUI: 'Bootstrap',   
			height:$(document).height()-$("#content-list").offset().top-100,
			colModel : [
			  	{name: "no", index: "no", width: 70, label: "结算单号", sortable: true, align: "left"},
			  	{name: "checkstatus", index: "checkstatus", width: 70, label: "checkstatus", sortable: true, align: "left" ,hidden:true},
				{name: "custcode", index: "custcode", width: 70, label: "客户编号", sortable: true, align: "left"},
				{name: "documentno", index: "documentno", width: 70, label: "档案号", sortable: true, align: "left"},
				{name: "address", index: "address", width: 158, label: "楼盘", sortable: true, align: "left"},
				{name: "custstatus", index: "custstatus", width: 70, label: "客户状态", sortable: true, align: "left",hidden:true},
				{name: "custstatusdescr", index: "custstatusdescr", width: 70, label: "客户状态", sortable: true, align: "left"},
				{name: "itemtype1", index: "itemtype1", width: 70, label: "材料类型1", sortable: true, align: "left",hidden:true},
				{name: "itemtype1descr", index: "itemtype1descr", width: 75, label: "材料类型1", sortable: true, align: "left"},
				{name: "status", index: "status", width: 70, label: "结算状态", sortable: true, align: "left",hidden:true},
				{name: "statusdescr", index: "statusdescr", width: 70, label: "结算状态", sortable: true, align: "left"},
				{name: "appempdescr", index: "appempdescr", width: 70, label: "申请人", sortable: true, align: "left"},
				{name: "date", index: "date", width: 110, label: "申请日期", sortable: true, align: "left", formatter: formatTime},
				{name: "appremark", index: "appremark", width: 158, label: "申请说明", sortable: true, align: "left"},
				{name: "confirmempdescr", index: "confirmempdescr", width: 70, label: "审核人", sortable: true, align: "left"},
				{name: "confirmdate", index: "confirmdate", width: 110, label: "审核日期", sortable: true, align: "left", formatter: formatTime},
				{name: "confirmremark", index: "confirmremark", width: 158, label: "审核说明", sortable: true, align: "left"},
				{name: "isitemupdescr", index: "isitemupdescr", width: 93, label: "材料免费升级", sortable: true, align: "left"},
				{name: "custcheckdate", index: "custcheckdate", width: 100, label: "客户结算时间", sortable: true, align: "left", formatter: formatTime},
				{name: "enddate", index: "enddate", width: 100, label: "工程完工时间", sortable: true, align: "left", formatter: formatTime},
				{name: "lastupdate", index: "lastupdate", width: 100, label: "最后更新时间", sortable: true, align: "left", formatter: formatTime},
				{name: "lastupdatedby", index: "lastupdatedby", width: 93, label: "最后更新人员", sortable: true, align: "left"},
				{name: "expired", index: "expired", width: 65, label: "是否过期", sortable: true, align: "left", hidden: true},
				{name: "actionlog", index: "actionlog", width: 70, label: "操作日志", sortable: true, align: "left"}
            ]
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
		$("#page_form select").val('');
		$("#address").val('');
		$("#ProjectMan").val('');
		$("#Department2").val('');
		$("#isWorkApp").val('');
		$("#workType1").val('');
		$("#workType2").val('');
		$("#status").val('');
		$("#status_NAME").val('');
		$("#custType").val('');
		$("#custType_NAME").val('');
		$("#department1").val('');
		$("#custStatus").val('');
		$("#custStatus_NAME").val('');
		$.fn.zTree.getZTreeObj("tree_department1").checkAllNodes(false);
		$.fn.zTree.getZTreeObj("tree_status").checkAllNodes(false);
		$.fn.zTree.getZTreeObj("tree_custType").checkAllNodes(false);
		$.fn.zTree.getZTreeObj("tree_custStatus").checkAllNodes(false);
	} 
	function add(){	
		Global.Dialog.showDialog("cljsglAdd",{			
			title:"材料结算--添加",
			url:"${ctx}/admin/cljsgl/goSave",
			height: 400,
			width:800,
			returnFun: goto_query
		});

	}
	
	function update(){	
		var ret = selectDataTableRow();		
		if (ret) {
			if((ret.status!=1) && (ret.status!=4)){
				art.dialog({content: "只能对申请/审核退回状态的材料结算进行审核操作!",width: 200});
				return false;
			}			
			Global.Dialog.showDialog("cljsglupdate",{			
				title:"材料结算--编辑",
				url:"${ctx}/admin/cljsgl/goUpdate?id="+ret.no,
				height: 400,
				width:800,
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
			if((ret.status!=1) && (ret.status!=4)){
				art.dialog({content: "只能对申请/审核退回状态的材料结算进行审核操作!",width: 200});
				return false;
			}
			if (ret.custstatus!=4){
				art.dialog({content: "只能对合同施工状态的客户进行审核操作!",width: 200});
				return false;
			}
			Global.Dialog.showDialog("cljsglCheck",{			
				title:"材料结算--审核",
				url:"${ctx}/admin/cljsgl/goCheck?id="+ret.no,
				height: 400,
				width:800,
				returnFun: goto_query
			});
		}else{
			art.dialog({
				content: "请选择一条记录"
			});
		}
	}
	function back(){	
		var ret = selectDataTableRow();		
		if (ret) {
			if(ret.status!=2){
				art.dialog({content: "只能对审核状态的材料结算进行反审核操作!",width: 200});
				return false;
			}
			if (ret.custstatus!=4){
				art.dialog({content: "只能对合同施工状态的客户进行反审核操作!",width: 200});
				return false;
			}
			Global.Dialog.showDialog("cljsglBack",{			
				title:"材料结算--反审核",
				url:"${ctx}/admin/cljsgl/goBack?id="+ret.no,
				height: 400,
				width:800,
				returnFun: goto_query
			});
		}else{
			art.dialog({
				content: "请选择一条记录"
			});
		}
	}
	function itemUp(){	
		var ret = selectDataTableRow();		
		if (ret) {
			if($.trim(ret.checkstatus)!=1){
				art.dialog({content: "只有客户结算状态为【客户未结算】才能进行材料升级标记操作!",width: 200});
				return false;
			}
			Global.Dialog.showDialog("cljsglItemUp",{			
				title:"材料结算--材料升级",
				url:"${ctx}/admin/cljsgl/goItemUp?id="+ret.no,
				height: 400,
				width:800,
				returnFun: goto_query
			});
		}else{
			art.dialog({
				content: "请选择一条记录"
			});
		}
	}
	function view(){	
		var ret = selectDataTableRow();		
		if (ret) {			
			Global.Dialog.showDialog("cljsglView",{			
				title:"材料结算--查看",
				url:"${ctx}/admin/cljsgl/goView?id="+ret.no,
				height: 400,
				width:800,
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
		var ret = selectDataTableRow();
		if(ret){
			Global.Dialog.showDialog("cljsglprint",{
				title:"结算单打印",
			    url:"${ctx}/admin/cljsgl/goPrint?id="+ret.no,
			    height:320,
			    resizable:false, 
			    width:600
			});
		}else {
			art.dialog({
				content:"请选择一条数据",
			});
			return;
		}
	} 
	function discCost(){
		var ret = selectDataTableRow();
		Global.Dialog.showDialog("cljsgldiscCost",{
			title:"优惠分摊",
		    url:"${ctx}/admin/cljsgl/goDiscCost",
		    height:700,
		    //resizable:false, 
		    width:1300
		});
	}
	</script>
</head>
<body>
	<div class="body-box-list">
        <div class="query-form"  >  
	        <form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
				<input type="hidden" name="exportData" id="exportData">
				<input type="hidden" id="expired" name="expired" value="${itemCheck.expired}" />
				<input type="hidden" name="jsonString" value="" />
				<ul class="ul-form">
					<li>
						<label >申请日期</label>
						<input type="text" id="appDateFrom" name="appDateFrom" class="i-date"  
						onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
						value="<fmt:formatDate value='${itemCheck.appDateFrom}' pattern='yyyy-MM-dd'/>" />
					</li>
					<li>
						<label >至</label>
						<input type="text" id="appDateTo" name="appDateTo" class="i-date"  
						onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
						value="<fmt:formatDate value='${itemCheck.appDateTo}' pattern='yyyy-MM-dd'/>" />
					</li>	
					<li>
						<label>材料类型1</label>
						<select id="itemType1" name="itemType1"   ></select>
					</li>
					<li>
						<label>一级部门</label>
						<house:DictMulitSelect id="department1" dictCode="" sql="select code,desc1 from tDepartment1 where Expired='F'" 
						sqlLableKey="desc1" sqlValueKey="code" selectedValue="${itemCheck.department1 }" onCheck="checkDepartment1()"></house:DictMulitSelect>
					</li>
					<li>
						<label>结算状态</label>
						<house:xtdmMulit id="status" dictCode="ITEMCHECKSTATUS" selectedValue="1,2,4"></house:xtdmMulit>
					</li>
					<li >
						<label >客户编号</label>
						<input type="text" id="custCode" name="custCode" style="width:160px;" value="${prjProgConfirm.custCode }"  readonly="readonly"/>
					</li>
					<li>
						<label>客户类型</label>
						<house:custTypeMulit id="custType"  selectedValue="${itemCheck.custType}" ></house:custTypeMulit>
					</li>	
					<li>
						<label>客户结算日期从</label>
						<input type="text" style="width:160px;" id="custCheckDateFrom" name="custCheckDateFrom" class="i-date" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${customer.custCheckDateFrom }' pattern='yyyy-MM-dd'/>"/>
					</li>
					<li>
						<label>到</label>
						<input type="text" style="width:160px;" id="custCheckDateTo" name="custCheckDateTo" class="i-date" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${customer.custCheckDateTo }' pattern='yyyy-MM-dd'/>"/>
					</li>
					<li>
						<label>工程完工日期从</label>
						<input type="text" style="width:160px;" id="endDateFrom" name="endDateFrom" class="i-date" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${customer.endDateFrom }' pattern='yyyy-MM-dd'/>"/>
					</li>
					<li>
						<label>到</label>
						<input type="text" style="width:160px;" id="endDateTo" name="endDateTo" class="i-date" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${customer.endDateTo }' pattern='yyyy-MM-dd'/>"/>
					</li>
					<li>
						<label>客户状态</label>
						<house:xtdmMulit id="custStatus" dictCode="CUSTOMERSTATUS" selectedValue="4" unShowValue="1,2,3"></house:xtdmMulit>
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
					<house:authorize authCode="CLJSGL_SAVE">
						<button type="button" class="btn btn-system " onclick="add()">新增</button>
					</house:authorize>
					<house:authorize authCode="CLJSGL_UPDATE">
						<button type="button" class="btn btn-system "  onclick="update()">编辑</button>
					</house:authorize>
					<house:authorize authCode="CLJSGL_DISCCOST">
						<button type="button" class="btn btn-system " onclick="discCost()">优惠分摊</button>
					</house:authorize>
					<house:authorize authCode="CLJSGL_CHECK">
						<button type="button" class="btn btn-system "  onclick="check()">审核</button>
					</house:authorize>
					<house:authorize authCode="CLJSGL_BACK">
						<button type="button" class="btn btn-system "  onclick="back()">反审核</button>
					</house:authorize>
					<house:authorize authCode="CLJSGL_ITEMUP">
						<button type="button" class="btn btn-system "  onclick="itemUp()">材料升级标记</button>
					</house:authorize>
					<house:authorize authCode="CLJSGL_VIEW">
						<button type="button" class="btn btn-system " onclick="view()">查看</button>
					</house:authorize>
					<button type="button" class="btn btn-system " onclick="doExcel()">导出excel</button>
					<house:authorize authCode="CLJSGL_PRINT">
						<button type="button" class="btn btn-system " id="print" onclick="print()">结算单打印</button>
					</house:authorize>
		    	</div>
			</div>
			<div id="content-list">
				<table id= "dataTable"></table>
				<div id="dataTablePager"></div>
			</div>
		</div>
	</div>
</body>
</html>


