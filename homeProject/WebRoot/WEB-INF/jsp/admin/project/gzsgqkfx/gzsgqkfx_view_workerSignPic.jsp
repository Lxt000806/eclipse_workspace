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
	<title>工人签到查看图片</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<style type="text/css">
      .SelectBG{
          background-color:#198ede!important;
            color:rgb(255,255,255)
            }
      .SelectBG_yellow{
          background-color:yellow;
         }
	</style>
<script type="text/javascript"> 
$(function() {
	var gridOption = {	
		height:$(document).height()-$("#content-list").offset().top-82,
		colModel : [
			{name:'pk',	index:'pk',	width:90,	label:'PK',	sortable:true,align:"left",hidden:true},
			{name:'photoname',	index:'photoname',	width:180,	label:'图片名称',	sortable:true,align:"left",},
			{name:'lastupdate',	index:'LastUpdated',	width:90,	label:'上传时间',	sortable:true,align:"left",formatter:formatTime},
			{name:'lastupdatedbydescr',	index:'lastupdatedbydescr',	width:90,	label:'上传人',	sortable:true,align:"left",},
		],
		onCellSelect: function(id,iCol,cellParam,e){
			var ids = $("#dataTable").jqGrid("getDataIDs");  
			var custCode =$.trim($("#code").val());
			var url =$.trim("${url}");
			var docName = Global.JqGrid.allToJson("dataTable","photoname");
			var arr = new Array();
				arr = docName.fieldJson.split(",");
			var arry = new Array();
				arry = arr[id-1].split(".");
			var sufName = arry[1].toLowerCase();
			if(sufName=="png" || sufName == "jpg" || sufName == "gif" || sufName == "jpeg" || sufName == "bmp"){//jpg/gif/jpeg/png/bmp 显示预览
				document.getElementById("docPic").src =url+arr[id-1];	
				document.getElementById("docPic_a").href =url + arr[id-1];	
			}else{
				document.getElementById("docPic").src ="";	
				document.getElementById("docPic_a").href ="";	
			}
			for(var i=0;i<ids.length;i++){
				if(i!=id-1){
					$('#'+ids[i]).find("td").removeClass("SelectBG");
				}
			}
			$('#'+ids[id-1]).find("td").addClass("SelectBG");
		},
	}
	$.extend(gridOption,{
		url:"${ctx}/admin/workTypeConstructDetail/goWorkSignPicJqGrid",
		postData:{no:"${no}"} ,
	});
	Global.JqGrid.initJqGrid("dataTable",gridOption);
});
</script>
</head>
	<body>
		<div class="panel panel-system" >
			<div class="panel-body" >
				<div class="btn-group-xs" >
					<button type="button" class="btn btn-system " id="closeBut" onclick="closeWin(false)">
						<span>关闭</span>
					</button>
				</div>
			</div>
		</div>
		<div style="width:53%; float:left; margin-left:0px; ">
			<div class="body-box-form" >
				<div class="container-fluid" style="whith:800px">  
					<div id="content-list" style="whith:800px">
						<table id= "dataTable"></table>
					</div>	
				</div>
			</div>
		</div>
		<div style="width:46.5%; float:right; margin-left:0px; ">
	    	<a data-magnify="gallery" data-caption="图片查看" id="docPic_a" href="">
				<img id="docPic" src=" " onload="AutoResizeImage(530,530,'docPic');" width="555" height="558" >  
			</a>
		</div>	
<script>
$(function(){
	
})
$("[data-magnify]").magnify({
	headToolbar: [
		"close"
	],
	footToolbar: [
		"zoomIn",
		"zoomOut",
		"fullscreen",
		"actualSize",
		"rotateRight"
	],
	title: false
});
</script>
	</body>	
</html>
