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
<script type="text/javascript"> 
$(function(){
	Global.LinkSelect.initSelect("${ctx}/admin/item/itemTypeByAuthority","itemType1","itemType2","itemType3");
	Global.LinkSelect.setSelect({firstSelect:'itemType1',
								firstValue:'RZ',
								secondSelect:'itemType2',
								secondValue:'${item.itemType2 }',
								thridSelect:'itemType3',
								thirdValue:'${item.itemType3 }',});

	//初始化表格
	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/customer/goSoftNotAppQueryJqGrid",
		styleUI: 'Bootstrap',
		height:$(document).height()-$("#content-list").offset().top-70,
		colModel : [
			{name:'code',		index:'code',			width:90,	label:'客户编号',	sortable:true,align:"left",cellattr: 'addCellAttr',},
			{name:'address', 	index:'address',		width:180,	label:'楼盘',		sortable:true,align:"left",},// hidden:true
			{name:'confirmbegin', index:'confirmbegin',	width:80,	label:'开工时间',	sortable:true,align:"left",formatter: formatDate},
			{name:'rzdesignmancode',index:'rzdesignmancode',	width:90,	label:'软装设计师',	sortable:true,align:"left",hidden:true},//,hidden:true
			{name:'rzdesignmandescr',index:'rzdesignmandescr',	width:90,	label:'软装设计师',	sortable:true,align:"left",},//,hidden:true
			{name:'empphone',	index:'empphone',		width:120,	label:'手机',		sortable:true,align:"left",},//,hidden:true
			{name:'chgconfirmdate', index:'chgconfirmdate',	width:120,	label:'增减审核日期',	sortable:true,align:"left",formatter: formatTime},
			{name:"appsoftremark", index:"appsoftremark",	width:220,	label:"未下单跟踪备注",	sortable:true,align:"left"},
		],
	});
	
	//查看明细
	$("#view").on("click",function(){
	var item2 = $.trim($("#itemType2").val());
		var ret = selectDataTableRow();
		if(ret){
			Global.Dialog.showDialog("view",{
				title:"软装未下单明细",
				url:"${ctx}/admin/softNotAppQuery/goView",
				postData:{code:ret.code,designMan:ret.rzdesignmandescr,mobile2:ret.empphone,itemType2:item2},
				height:700,
				width:1000,
				returnFun:goto_query
			});
		}else{
		  art.dialog({
		     content:"请选择一条数据",
		  });
		}
	}); 
	//导出Excel
	$("#doExcel").on("click",function(){
		var url = "${ctx}/admin/softNotAppQuery/doExcel";
		doExcelAll(url);
	});

	$("#appSoftRemark").on("click", function () {
		ret=selectDataTableRow();
		if(!ret){
			art.dialog({content: "请选择一条记录进行录入操作",width: 220});
			return false;
		}
		Global.Dialog.showDialog("appSoftRemark",{
			title:"未下单跟踪备注录入",
			url:"${ctx}/admin/softNotAppQuery/goAppSoftRemark",
			postData:{custCode:ret.code,appSoftRemark:ret.appsoftremark},
			width:500,
			height:260,
			returnFun:goto_query,
		});
	});
});

</script>
</head>
	<body>
	<div class="body-box-list">
        <div class="query-form"  >  
                    <form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
						<input type="hidden" name="exportData" id="exportData">
						<input type="hidden" name="jsonString" value="" />
						<ul class="ul-form">
							<li hidden="true">
							<label>材料类型1</label>
								<select id="itemType1" name="itemType1" style="width: 166px;" value="${customer.itemType1 }" ></select>
							</label>
							</li>
							<li>					
							<label>材料类型2</label>
								<select id="itemType2" name="itemType2" style="width: 166px;" value="${customer.itemType2 }"></select>
							</label>
							</li>
							<li id="loadMore" >
								<button type="button" class="btn  btn-system btn-sm" onclick="goto_query();" >查询</button>
							</li>
						</tr>
					</tbody>						
				  </table>
				</form>
			</div>
			</div>
			<div class="clear_float"></div>
			<!--query-form-->
			<div class="pageContent">
				<div class="btn-panel" >
				     <div class="btn-group-sm"  >
				      		<button type="button" class="btn btn-system " onclick="doExcelNow('软装未下单客户查询')">导出excel</button>
				      	<house:authorize authCode="softNotAppQuery_VIEW">
				      		<button type="button" class="btn btn-system " id="view">查看</button>
				      	</house:authorize>
				      	<button type="button" class="btn btn-system " id="appSoftRemark">未下单跟踪备注</button>
				     </div>
				</div>
				<div id="content-list">
					<table id= "dataTable"></table>
					<div id="dataTablePager"></div>
				</div> 
		</div>
	</body>	
</html>
