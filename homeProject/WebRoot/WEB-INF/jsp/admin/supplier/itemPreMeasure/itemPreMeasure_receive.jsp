<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>接收ItemPreMeasure</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<link href="${resourceRoot}/bootstrap/css/change.css" rel="stylesheet" />
		<style>
		#dataTablePager{
			width: auto !important;
		}
	</style>
<script type="text/javascript">
$(function(){
	//初始化表格
	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/supplierItemPreMeasure/goJqGridDetail?id=${preAppNo}",
		height:160,
		colModel : [
		  {name : 'ItemCode',index : 'ItemCode',width : 100,label:'材料编号',align : "left",count:true},
		  {name : 'itemcodedescr',index : 'itemcodedescr',width : 200,label:'材料名称',align : "left"},
		  {name : 'fixareadescr',index : 'fixareadescr',width : 200,label:'装修区域',align : "left"},
		  {name : 'Qty',index : 'Qty',width : 60,label:'数量',align : "right",sum:true},
		  {name : 'uom',index : 'uom',width : 60,label:'单位',align : "left"},
		  {name : 'Remarks',index : 'Remarks',width : 300,label:'备注',align : "left"}
          ]
	});  
	//$('div.ui-jqgrid-bdiv').css("height",250);
});

function receive(){
	$.ajax({
		url:'${ctx}/admin/supplierItemPreMeasure/doReceive',
		type: 'post',
		data: {'pk':${pk}},
		dataType: 'json',
		cache: false,
		error: function(obj){
			showAjaxHtml({"hidden": false, "msg": '接收出错~'});
	    },
	    success: function(obj){
	    	if(obj.rs){
	    		art.dialog({
					content: obj.msg,
					time: 1000,
					beforeunload: function () {
	    				closeWin();
				    }
				});
	    	}else{
	    		art.dialog({
					content: obj.msg,
					width: 200
				});
	    	}
	    }
	 });
	
}
function downLoad(urlString,fileName){
	$.ajax({
		url:'${ctx}/admin/supplierItemPreMeasure/doDownLoad',
		type: 'post',
		data: {'urlString':urlString,'fileName':fileName},
		dataType: 'json',
		cache: false,
		error: function(obj){
			showAjaxHtml({"hidden": false, "msg": '下载出错~'});
	    },
	    success: function(obj){
	    	if(obj.rs){
	    		
	    	}else{
	    		art.dialog({
					content: obj.msg,
					width: 200
				});
	    	}
	    }
	 });
	
}
function photoDownload(){
	var number = Global.JqGrid.allToJson("dataTable_material", "photoname").datas.length;
	if(number <= 0 ){
		art.dialog({
			content:"该记录没有图片",
			time:3000
		});
		return;
	}
	window.open("${ctx}/admin/itemPreApp/downLoad?no="+"${preAppNo}");
}
</script>

</head>
    
<body onunload="closeWin()">
<div class="body-box-form" >
	<form action="" method="post" id="page_form" ></form>
	<div class="panel panel-system">
	    <div class="panel-body" >
	      <div class="btn-group-xs" >
	      <button type="button" id="receiveBut" class="btn btn-system" onclick="receive()">接收</button>
	      <button id="photoBut" type="button" class="btn btn-system" onclick="photoDownload()">图片下载</button>
	      <button type="button" class="btn btn-system" onclick="closeWin(false)">关闭</button>
	      </div>
	    </div>
	</div>
	<div id="content-list">
		<table id= "dataTable"></table> 
		<div id="dataTablePager"></div>
	</div> 
	<%-- <div style="padding-bottom: 50px;height: 160px;overflow: auto;">
		<c:forEach items="${photoList }" var="item" varStatus="st">
			<img id="measure_${st.index}" src="${photoPath }${item.photoName }" onload="AutoResizeImage(150,150,'measure_${st.index}');"
			 height="200" width="200">
			<a href="${ctx}/admin/supplierItemPreMeasure/doDownLoad?urlString=${photoPath }${item.photoName }&fileName=${item.photoName }">下载</a>
		</c:forEach>
	</div> --%>
		<div class="container-fluid" style="height: 200px;">
			<ul class="nav nav-tabs">
				<li class="active"><a
					href="#itemPreAppManage_tabView_materialPhoto" data-toggle="tab">材料图片</a>
				</li>
			</ul>
			<div class="tab-content">
				<div id="itemPreAppManage_tabView_materialPhoto"
					class="tab-pane fade active in">
					<jsp:include page="itemPreMeasure_materialPhoto.jsp">
						<jsp:param value="${preAppNo}" name="no" />
					</jsp:include>
				</div>
			</div>
		</div>
	</div>


</body>
</html>
