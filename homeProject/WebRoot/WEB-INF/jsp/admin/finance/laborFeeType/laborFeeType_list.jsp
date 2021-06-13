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
	<title>人工费用类型</title>
	<%@ include file="/commons/jsp/common.jsp" %>
	<script type="text/javascript">
	/**初始化表格*/
	$(function(){
		Global.LinkSelect.initSelect("${ctx}/admin/item/itemTypeByAuthority","itemType1","itemType2","itemType3");
        //初始化表格
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/laborFeeType/goJqGrid",
            styleUI: 'Bootstrap',   
			height:$(document).height()-$("#content-list").offset().top-70,
			colModel : [
			  	{name: "code", index: "code", width: 70, label: "编号", sortable: true, align: "left"},
			  	{name: "itemtype1", index: "itemtype1", width: 100, label: "材料类型1", sortable: true, align: "left",hidden:true},
			  	{name: "itemtype1descr", index: "itemtype1descr", width: 100, label: "材料类型1", sortable: true, align: "left"},
				{name: "descr", index: "descr", width: 130, label: "费用名称", sortable: true, align: "left"},
				{name: "iscalcost", index: "iscalcost", width: 70, label: "是否计入成本", sortable: true, align: "left",hidden:true},
				{name: "iscalcostdescr", index: "iscalcostdescr", width: 100, label: "是否计入成本", sortable: true, align: "left"},
				{name: "ishavesendno", index: "ishavesendno", width: 70, label: "是否填发单编号", sortable: true, align: "left",hidden:true},
				{name: "ishavesendnodescr", index: "ishavesendnodescr", width: 120, label: "是否填发单编号", sortable: true, align: "left"},
				{name: "itemtype12", index: "itemtype12", width: 70, label: "材料类型12", sortable: true, align: "left",hidden:true},
				{name: "itemtype12descr", index: "itemtype12descr", width: 100, label: "材料类型12", sortable: true, align: "left"},
				{name: "lastupdate", index: "lastupdate", width: 140, label: "最后更新时间", sortable: true, align: "left", formatter: formatTime},
				{name: "lastupdatedby", index: "lastupdatedby", width: 90, label: "最后更新人员", sortable: true, align: "left"},
				{name: "expired", index: "expired", width: 80, label: "是否过期", sortable: true, align: "left"},
				{name: "actionlog", index: "actionlog", width: 80, label: "操作日志", sortable: true, align: "left"}
            ]
		});	
		
	});  
	function clearForm(){
		$("#page_form input[type='text']").val('');
		$("#page_form select").val("");
		$.fn.zTree.getZTreeObj("tree_status").checkAllNodes(false);
		$("#status").val("");
	} 
	function add(){	
		Global.Dialog.showDialog("laborFeeTypeAdd",{			
			title:"人工费用类型--新增",
			url:"${ctx}/admin/laborFeeType/goSave",
			postData:{
			m_umState:"A",
			},
			height: 450,
			width:450,
			returnFun: goto_query
		});

	}
	
	function update(){	
		var ret = selectDataTableRow();		
		if (ret) {				
			Global.Dialog.showDialog("laborFeeTypeUpdate",{			
				title:"人工费用类型--编辑",
				url:"${ctx}/admin/laborFeeType/goSave",
				postData:{
					m_umState:"M",
					code:$.trim(ret.code)
				},
				height: 450,
				width:450,
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
			Global.Dialog.showDialog("laborFeeTypeView",{			
				title:"人工费用类型--查看",
				url:"${ctx}/admin/laborFeeType/goSave",
				postData:{
					m_umState:"V",
					code:$.trim(ret.code)
				},
				height: 450,
				width:450,
				returnFun: goto_query
			});
		}else{
			art.dialog({
				content: "请选择一条记录"
			});
			return;
		}
	}
	
	function doExcel(){
		var url = "${ctx}/admin/laborFeeType/doExcel";
		doExcelAll(url);
	}

	</script>
</head>
<body>
	<div class="body-box-list">
        <div class="query-form"  >  
	        <form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
	        	<input type="hidden" id="expired" name="expired" value="${laborFeeType.expired }"/>
				<input type="hidden" name="exportData" id="exportData">
				<input type="hidden" name="jsonString" value="" />
				<ul class="ul-form">
					<li>
						<label>编号</label>
						<input type="text" id="code" name="code" style="width:160px;" value="${laborFeeType.code}"/>
					</li>
					<li>
						<label>费用名称</label>
						<input type="text" id=descr name="descr" style="width:160px;" value="${laborFeeType.descr}"/>
					</li>
					<li>
						<label>材料类型1</label>
						<select id="itemType1" name="itemType1" ></select>
					</li>
					<li >
						<input type="checkbox" id="expired_show" name="expired_show" value="${laborFeeType.expired }" onclick="hideExpired(this)" ${laborFeeType.expired!='T' ?'checked':'' }/>
						<label for="expired_show" style="margin-left: -3px;width: 50px;">隐藏过期</label>																	
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
					<button type="button" class="btn btn-system " onclick="add()">新增</button>
					<button type="button" class="btn btn-system "  onclick="update()">编辑</button>
					<house:authorize authCode="LABORFEETYPE_VIEW">
						<button type="button" class="btn btn-system " onclick="view()">查看</button>
					</house:authorize>
					<button type="button" class="btn btn-system " onclick="doExcel()">导出excel</button>
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


