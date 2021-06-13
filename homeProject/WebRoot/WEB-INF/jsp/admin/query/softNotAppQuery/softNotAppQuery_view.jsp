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
	<title>软装未下单查询</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_supplier.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_wareHouse.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript"> 

$(function(){
	//初始化表格
	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/itemReq/goSoftNotAppQueryJqGrid",
		postData:{custCode:'${customer.code }',itemType2:'${customer.itemType2 }'},
		height:$(document).height()-$("#content-list").offset().top-70,
		styleUI: 'Bootstrap',
		colModel : [
			{name:'custcode',	index:'custcode',	width:90,	label:'custcode',	sortable:true,align:"left",hidden:true},
			{name:'fixareadescr',	index:'fixareadescr',	width:90,	label:'区域',	sortable:true,align:"left",},
			{name:'itemcode',	index:'itemcode',	width:190,	label:'材料名称',	sortable:true,align:"left",hidden:true},
			{name:'itemdescr',	index:'itemdescr',	width:190,	label:'材料名称',	sortable:true,align:"left",},
			{name:'itemtype1descr', 	index:'itemtype1descr',		width:70,	label:'材料类型1',sortable:true,align:"left",},
			{name:'itemtype2', 	index:'itemtype2',		width:70,	label:'材料类型2',sortable:true,align:"left",hidden:true},
			{name:'itemtype2descr', 	index:'itemtype2descr',		width:70,	label:'材料类型2',sortable:true,align:"left",},
			{name:'supdescr', 	index:'supdescr',		width:80,	label:'品牌',sortable:true,align:"left"},
			{name:'qty',		index:'qty',		width:70,	label:'数量',	sortable:true,align:"left",},
			{name:'sendqty',		index:'sendqty',		width:75,	label:'已发货数量',	sortable:true,align:"left",},
			{name:'appqty',		index:'appqty',		width:75,	label:'已申请数量',	sortable:true,align:"left",}, 
			{name:'notappqty',		index:'notappqty',		width:75,	label:'未下单数量',	sortable:true,align:"left",}, 
			{name:'uomdescr',		index:'uomdescr',		width:50,	label:'单位',	sortable:true,align:"left",}, 
			{name:'isfee',		index:'isfee',		width:80,	label:'工费（0为否）',	sortable:true,align:"left",hidden:true}, 
			{name:'remark',		index:'remark',		width:167,	label:'材料描述',	sortable:true,align:"left",}, 
		],
	});
});

</script>
</head>
<body>
<div class="body-box-form" >
	<div class="panel panel-system" >
    <div class="panel-body" >
      <div class="btn-group-xs" >
		<button type="button" class="btn btn-system "   onclick="closeWin(false)">关闭</button>
      </div>
   	</div>
	</div>
	<div class="panel panel-info" >  
         <div class="panel-body">
             <form role="form" class="form-search" id="dataForm"  action="" method="post" target="targetFrame">
					<house:token></house:token>
				<ul class="ul-form">
					<li>
					<label>客户编号</label>
						<input type="text" id="custCode" name="custCode"  value="${customer.code }" readonly="true"/>
					</label>
				</li>
				<li>
					<label>手机号码</label>
						<input type="text" id="mobile2" name="mobile2"  value="${customer.mobile2 }" readonly="true"/>
					</label>
				</li> 
				<li>
					<label>开工时间</label>
						<input type="text" id="confirmBegin" name="confirmBegin" class="i-date"  onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${customer.confirmBegin}' pattern='yyyy-MM-dd'/>" disabled="true"/>
					</label>
				</li>
				<li>		
					<label>楼盘</label>
						<input type="text" id="address" name="address"  value="${customer.address }" readonly="true" />
					</label>
				</li>
				<li>
					<label>软装设计师</label>
						<input type="text" id="designManDescr" name="designManDescr"  value="${customer.designManDescr }" readonly="true"/>
					</label>
				</li>				
				</form>
				</div>
			</div>
			</div>
			<div class="clear_float"></div>
			<!--query-form-->
			<div class="pageContent">
			<!-- panelBar -->
				 
					<!-- panelBar End -->
				<div id="content-list">
					<table id= "dataTable"></table>
					<div id="dataTablePager"></div>
				</div> 
			</div>
	</body>	
</html>
