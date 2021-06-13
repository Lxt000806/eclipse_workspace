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
	<title>专盘信息管理</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
		<script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>
	
<script type="text/javascript"> 
$(function(){
	$("#code").openComponent_builder({showValue:"${code}",showLabel:"${descr}",readonly:true});
	
	var postData = $("#page_form").jsonForm();
	//初始化表格
	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/spcBuilder/goDelivJqGrid",
		postData:postData ,
		height:$(document).height()-$("#content-list").offset().top-82,
		styleUI: "Bootstrap", 
		multiselect:true,		
		colModel : [
			{name:"Code",	index:"Code",	width:90,	label:"批次号",	sortable:true,align:"left" ,},
			{name:"DelivNum",	index:"DelivNum",	width:90,	label:"交房数",	sortable:true,align:"left" ,},
			{name:"buildernum",	index:"buildernum",	width:300,	label:"楼栋号",	sortable:true,align:"left" ,},
		
		],
	});
	
	 $("#saveBtn").on("click",function(){
        	var ids = $("#dataTable").jqGrid('getGridParam', 'selarrrow');
        	if(ids.length==0){
        		Global.Dialog.infoDialog("请选择一条或多条记录进行新增操作！");	
        		return;
        	}
        	var selectRows = [];
    		$.each(ids,function(k,id){
    			var row = $("#dataTable").jqGrid('getRowData', id);
    			selectRows.push(row);
    		});
    		Global.Dialog.returnData = selectRows;
    		closeWin();
        });
	/* $("#saveBtn").on("click",function(){
        var ids = $("#dataTable").jqGrid('getGridParam', 'selarrrow');
        if(ids.length==0){
       		Global.Dialog.infoDialog("请选择一条或多条记录进行新增操作！");	
       		return;
       	}
       	var selectRows=[];
		var datas = {"Code":"","buildernum":""};
      	for(var i=1;i<ids.length+1;i++){
				datas.Code=datas.Code+','+$("#dataTable").jqGrid('getRowData', i).Code;
				datas.buildernum=datas.buildernum+","+$("#dataTable").jqGrid('getRowData', i).buildernum;
      	}
		selectRows.push(datas);
		Global.Dialog.returnData = selectRows;
		closeWin();
	}); */
	
});
</script>
</head>
	<body>
		<div class="panel panel-system" >
		    <div class="panel-body" >
		      <div class="btn-group-xs" >
					<button type="button" class="btn btn-system " id="saveBtn">
						<span>保存</span>
					</button>
					<button type="button" class="btn btn-system " id="closeBut" onclick="closeWin(false)">
						<span>关闭</span>
					</button>
				</div>
			</div>
		</div>
		<div class="body-box-list">
		 	 <form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
				<input type="hidden" name="jsonString" value="" />
				<input type="hidden" id="builderNum" name="builderNum" style="width:160px;" value="${delivNums}"/>
				<ul class="ul-form">
					<li>
						<label>专盘编号</label>
						<input type="text" id="code" name="code" style="width:160px;" value="${code}"/>
					</li>
				</ul>
			</form>
		</div>
		<div class="pageContent">
			<div id="content-list">
				<table id= "dataTable"></table>
				<div id="dataTablePager"></div>
			</div> 
		</div>
	</body>	
</html>
