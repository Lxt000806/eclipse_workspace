<%@ page language="java" import="com.house.framework.commons.utils.PathUtil" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://" + request.getServerName() + ":"+request.getServerPort()+path+"/";
String jasperPath = PathUtil.WEB_INF+"jsp/jasper/";
%>
<!DOCTYPE html>
<html>
<head>
	<title>批量打印ItemApp</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_wareHouse.js?v=${v}" type="text/javascript"></script>

<script type="text/javascript">
var parentWin=window.opener;
$(function(){
        //初始化表格
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/supplierItemApp/goJqGrid",
			postData:{pPrint:"1"},
			multiselect: true,
			height:$(document).height()-$("#content-list").offset().top-110,
			styleUI: 'Bootstrap',
			colModel : [
			  {name: 'no', index: 'a.no', width: 100, label: '领料单号', sortable: true, align: "left", count: true},
		      {name: 'address', index: 'b.Address', width: 160, label: '楼盘', sortable: true, align: "left"},
		      {name: 'custdescr', index: 'c.Descr', width: 60, label: '客户名称', sortable: true, align: "left"},
		      {name: 'status', index: 'a.Status', width: 70, label: '领料单状态', sortable: true, align: "left", hidden: true},
		      {name: 'statusdescr', index: 'a.Status', width: 70, label: '领料单状态', sortable: true, align: "left"},
		      {name: 'appczydescr', index: 'a.AppCZYDescr', width: 70, label: '申请人', sortable: true, align: "left"},
		      {name: 'confirmdate', index: 'a.ConfirmDate', width: 72, label: '审核日期', sortable: true, align: "left", formatter: formatTime},
		      {name: 'senddate', index: 'a.SendDate', width: 74, label: '发货日期', sortable: true, align: "left", formatter: formatTime},
		      {name: 'remarks', index: 'a.Remarks', width: 180, label: '备注', sortable: true, align: "left"},
		      {name: 'whcodedescr', index: 'a.WHCodeDescr', width: 100, label: '仓库名称', sortable: true, align: "left"},
		      {name: 'projectman', index: 'a.ProjectMan', width: 70, label: '项目经理', sortable: true, align: "left"},
		      {name: 'phone', index: 'a.Phone', width: 100, label:'电话', sortable: true, align: "left"},
		      {name: 'splstatus', index: 'a.splstatus', width: 70, label: '供应商状态', sortable: true, align: "left", hidden: true},
		      {name: 'splstatusdescr', index: 'a.splstatus', width: 70, label: '供应商状态', sortable: true, align: "left"},
		      {name: 'arrivedate', index: 'a.arrivedate', width: 100, label: '预计到货日期', sortable: true, align: "left", formatter: formatTime},
		      {name: 'splremark', index: 'a.splremark', width: 180, label: '供应商备注', sortable: true, align: "left"},
		      {name: 'lastupdate', index: 'a.LastUpdate', width: 100, label: '最后更新日期', sortable: true, align: "left", formatter: formatTime},
		      {name: 'lastupdatedby', index: 'a.LastUpdatedBy', width: 100, label: '最后更新人员', sortable: true, align : "left"},
		      {name: 'expired', index: 'a.Expired', width: 100, label: '是否过期', sortable: true, align: "left"}
		      
            ]
		});
		
       //打印
        $("#printItemApp").on("click",function(){ 			
			var ids = $("#dataTable").jqGrid('getGridParam', 'selarrrow');
        	if(ids.length==0){
        		Global.Dialog.infoDialog("请选择需要打印的领料单！");
        		return;
        	}

        	var nos = "";
        	$.each(ids,function(k,id){
    			var row = $("#dataTable").jqGrid('getRowData', id);
    			nos = nos + "'" + $.trim(row.no) + "',";		
    		});
    		if (nos != "") {
    			nos = nos.substring(0,nos.length-1);
    		}
        	
        	var reportName = "itemAppSend_main.jasper";
        	Global.Print.showPrint(reportName, {
				No: nos,
				LogoPath : "<%=basePath%>jasperlogo/",
				SUBREPORT_DIR: "<%=jasperPath%>" 
			});
        }); 
        
        //合并打印
        $("#printUnion").on("click",function(){ 			
			var ids = $("#dataTable").jqGrid('getGridParam', 'selarrrow');
        	if(ids.length==0){
        		Global.Dialog.infoDialog("请选择需要打印的领料单！");
        		return;
        	}

        	var nos = "";
        	$.each(ids,function(k,id){
    			var row = $("#dataTable").jqGrid('getRowData', id);
    			nos = nos + "'" + $.trim(row.no) + "',";		
    		});
    		if (nos != "") {
    			nos = nos.substring(0,nos.length-1);
    		}
    		
        	var reportName = "itemAppSend_union_main.jasper";
        	Global.Print.showPrint(reportName, {
				No: nos,
				LogoPath : "<%=basePath%>jasperlogo/",
				SUBREPORT_DIR: "<%=jasperPath%>" 
			});
        }); 
        $("#whcode").openComponent_wareHouse({condition: {czybh:'${czybh}'}});
});

function clearForm(){
	$("#page_form input[type='text']").val('');
	$("#page_form select").val('');
	$("#status").val('');
	$.fn.zTree.getZTreeObj("tree_status").checkAllNodes(false);
	$("#splStatus").val('');
	$.fn.zTree.getZTreeObj("tree_splStatus").checkAllNodes(false);
}
</script>
</head>
    
<body onunload="closeWin()">
	<div class="body-box-list">
		<div class="query-form">
			<form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
				<input type="hidden" id="module" name="module" value="SupplierItemApp" />
				<input type="hidden" id="czybh" name="czybh" value="${itemApp.czybh}" />
				
				<ul class="ul-form">
					<li>
					<label>领料单状态</label>
					<house:xtdmMulit id="status" dictCode="" sql="select cbm SQL_VALUE_KEY,note SQL_LABEL_KEY from tXTDM where ID='ITEMAPPSTATUS' and (CBM='CONFIRMED' or CBM='SEND')" selectedValue="CONFIRMED"></house:xtdmMulit>
					</li>	
					<li>
					<label>供应商状态</label>
					<house:xtdmMulit id="splStatus" dictCode="APPSPLSTATUS" unShowValue="0,1"></house:xtdmMulit>
					</li>
					<li>
					<label>楼盘</label>
					<input type="text" id="custAddress" name="custAddress" />
					</li>
					<li>
					<label>发货类型</label>
					<house:xtdm id="sendType" dictCode="ITEMAPPSENDTYPE" value="${itemApp.sendType }"></house:xtdm>
					</li>
					<li>
					<label>审核时间</label>
					<input type="text" id="confirmDateFrom" name="confirmDateFrom" class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${itemApp.confirmDate}' pattern='yyyy-MM-dd'/>" />
					</li>
					<li>
					<label>至</label>
					<input type="text" id="confirmDateTo" name="confirmDateTo" class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${itemApp.confirmDate}' pattern='yyyy-MM-dd'/>" />
					</li>
					<li>
					<label>仓库编号</label>
					<input type="text" id="whcode" name="whcode" value="${itemApp.whcode}"/>
					</li> 
					<li id="loadMore" >
					<button type="button" class="btn btn-system btn-sm" onclick="goto_query();" >查询</button>
					<button type="button" class="btn btn-system btn-sm" onclick="clearForm();" >清空</button>
					</li>
				</ul>
			</form>
		</div>
		
		<div class="btn-panel">
			<div class="btn-group-sm">
                <button type="button" class="btn btn-system " id="printItemApp">打印</button>
                <button type="button" class="btn btn-system " id="printUnion">合并打印</button>
                <button type="button" class="btn btn-system " onclick="closeWin(false)">关闭</button>
			</div>
		</div>

		<div id="content-list">
			<table id= "dataTable"></table> 
			<div id="dataTablePager"></div>
		</div> 
	</div>
</body>
</html>
