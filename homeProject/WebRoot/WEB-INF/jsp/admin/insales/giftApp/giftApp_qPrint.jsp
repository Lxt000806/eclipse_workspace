<%@ page language="java" import="com.house.framework.commons.utils.PathUtil" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://" + request.getServerName() + ":"+request.getServerPort()+path+"/";
String jasperPath = PathUtil.WEB_INF+"jsp/jasper/";
%>
<!DOCTYPE html>
<html>
<head>
	<title>批量打印GiftApp</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_supplier.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_item.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
var parentWin=window.opener;
$(function(){
        //初始化表格
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/giftApp/goQPrintJqGrid",
			multiselect: true,
			height:$(document).height()-$("#content-list").offset().top-110,
			styleUI: 'Bootstrap', 
			colModel : [
			  {name : 'no',index : 'no',width : 85,label:'领用单号',sortable : true,align : "left"},
			  {name : 'address',index : 'address',width : 150,label:'楼盘地址',sortable : true,align : "left"},
		      {name : 'custdescr',index : 'custdescr',width : 80,label:'客户名称',sortable : true,align : "left"},
		      {name : 'mobile1',index : 'mobile1',width : 100,label:'电话',sortable : true,align : "left",hidden:true},
		      {name : 'itemcode',index : 'itemcode',width : 50,label:'礼品编号',sortable : true,align : "left",hidden :true},
			  {name : 'itemdescr',index : 'itemdescr',width : 150,label:'礼品',sortable : true,align : "left"},
		      {name : 'qty',index : 'qty',width : 60,label:'数量',sortable : true,align : "right"},
		      {name : 'uomdescr',index : 'uomdescr',width : 50,label:'单位',sortable : true,align : "left"},
		      {name : 'remarks',index : 'remarks',width : 200,label:'备注',sortable : true,align : "left"},
            ]
       
		});
		$("#supplCode").openComponent_supplier(); 
		$("#itemCode").openComponent_item({condition:{itemType1:'LP'}});
	    $("#appCzy").openComponent_employee();
       //打印
        $("#printGiftApp").on("click",function(){		
			var ids = $("#dataTable").jqGrid('getGridParam', 'selarrrow');
        	if(ids.length==0){
        		Global.Dialog.infoDialog("请选择需要打印的礼品领用单！");
        		return;
        	}
        	

        	var nos = "";
        	var itemcodes = "";
        	$.each(ids,function(k,id){
    			var row = $("#dataTable").jqGrid('getRowData', id);
    			nos = nos + "'" + $.trim(row.no) + "'," ,
    			itemcodes = itemcodes + "'" + $.trim(row.itemcode) + "',";		
    				
    		});
    		if (nos != "") {
    			nos = nos.substring(0,nos.length-1);
    		}
        	if (itemcodes != "") {
    			itemcodes = itemcodes.substring(0,itemcodes.length-1);
    		}
        	var reportName = "giftApp.jasper";
        	Global.Print.showPrint(reportName, {
				No: nos,
				ItemCode:itemcodes,
				LogoPath : "<%=basePath%>jasperlogo/",
				SUBREPORT_DIR: "<%=jasperPath%>" 
			});
        }); 
        
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

<body>
	<div class="body-box-list">
		<div class="query-form">
			<form action="" method="post" id="page_form" class="form-search">
				<input type="hidden" id="expired" name="expired" value="${giftApp.expired }" />	
				<input type="hidden" name="m_umState" id="m_umState" value="${giftApp.m_umState}"/>				
			<ul class="ul-form">
						<li>
							<label >状态</label>
							<house:xtdmMulit id="status" dictCode="GIFTAPPSTATUS" selectedValue="${gifApp.status }"></house:xtdmMulit>    
						</li>
						<li>
							<label >开单时间从</label>
							<input type="text" style="width:160px;" id="dateFrom" name="dateFrom" class="i-date" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${giftApp.dateFrom}' pattern='yyyy-MM-dd'/>"/>
						</li>
						<li>
							<label >到</label>
							<input type="text" style="width:160px;" id="dateTo" name="dateTo" class="i-date" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${giftApp.dateTo }' pattern='yyyy-MM-dd'/>"/>
						</li>
					    <li>
							<label >供应商</label>
							<input type="text" id="supplCode" name="supplCode" style="width:160px;" value="${giftApp.supplCode}"/>    
						</li>
						 <li>
							<label >礼品</label>
							<input type="text" id="itemCode" name="itemCode" style="width:160px;" value="${giftApp.itemCode}"/>    
						</li>
						<li >
							<label> 领用类型</label>
							<house:xtdm id="type" dictCode="GIFTAPPTYPE"  value="${giftApp.type }" ></house:xtdm>
					    </li>
					    <li>
							<label >楼盘</label>
							<input type="text" id="address" name="address" style="width:160px;" value="${giftApp.address}"/>    
						</li>
						<li>
							<label>开单人</label>
							<input type="text" id="appCzy" name="appCzy" style="width:160px;" value="${gifApp.appczy }" />
						</li>
						<li id="loadMore" >
								<button type="button" class="btn btn-sm btn-system " onclick="goto_query();"  >查询</button>
						</li>	
					</ul>
			</form>
		</div>
	<div class="btn-panel" >

      <div class="btn-group-sm"  >
	      <button type="button" class="btn btn-system " id="printGiftApp" >打印</button>
	        <button type="button" class="btn btn-system "  id="closeBut" onclick="closeWin(false)">关闭</bu
     </div>
	</div>
		<div id="content-list">
				<table id="dataTable"></table>
				<div id="dataTablePager"></div>
			</div>
	</div>
</body>

</html>
