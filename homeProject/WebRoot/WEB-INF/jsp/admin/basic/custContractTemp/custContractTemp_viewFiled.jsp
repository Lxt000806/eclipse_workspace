<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://" + request.getServerName() + ":"+request.getServerPort()+path+"/";
String jasperPath = PathUtil.WEB_INF+"jsp/jasper/";
%>

<!DOCTYPE HTML>
<html>
<head>
    <title>查看归档合同</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_department.js?v=${v}" type="text/javascript"></script>

<script type="text/javascript">
$(function(){
	$("#department").openComponent_department();	
	
	var postData = $("#page_form").jsonForm();
	var url = "${ctx}/admin/custContractTemp/goJqGrid";
	
	/**初始化表格*/
	Global.JqGrid.initJqGrid("dataTable",{
		url : url,
		postData:postData,
		height:$(document).height()-$("#content-list").offset().top-70,
		rowNum:100000,
		colModel : [
			{name : 'pk',index : 'pk',width : 50,label:'编号',sortable : true,align : "left",hidden:true},
		      {name : 'custtypedescr',index : 'custtypedescr',width : 100,label:'客户类型',sortable : true,align : "left"},
		      {name : 'descr',index : 'descr',width : 170,label:'模板名称',sortable : true,align : "left"},
		      {name : 'version',index : 'version',width : 60,label:'版本号',sortable : true,align : "left"},
		      {name : 'typedescr',index : 'typedescr',width : 100,label:'合同类型',sortable : true,align : "left"},
		      {name : 'remarks',index : 'remarks',width : 250,label:'模板描述',sortable : true,align : "left"},
		      {name : 'filename',index : 'filename',width : 150,label:'模板文件',sortable : true,align : "left",hidden:true},
	    	  {name : 'filename1',index : 'filename1',width : 150,label:'模板文件',sortable : true,align : "left",
	    	  formatter:function(cellvalue, options, rowObject){
        			if(rowObject==null || !rowObject.filename){
          				return '';
          			}
        			return formatStr(rowObject);
	    	  },},
		      {name : 'lastupdate',index : 'lastupdate',width : 130,label:'最后更新时间',sortable : true,align : "left",formatter: formatTime},
		      {name : 'lastupdatedby',index : 'lastupdatedby',width : 80,label:'修改人',sortable : true,align : "left"},
		      {name : 'expired',index : 'expired',width : 80,label:'是否过期',sortable : true,align : "left"},
		      {name : 'actionlog',index : 'actionlog',width : 80,label:'操作日志',sortable : true,align : "left"},
		      {name : 'custtype',index : 'custtype',width : 80,label:'客户类型编号',sortable : true,align : "left"},
		      {name : 'type',index : 'type',width : 80,label:'合同类型编号',sortable : true,align : "left"},
		],
	});
	
	function formatStr(rowObject){
		var filename;
		var formatStr = "";
		if(rowObject && rowObject.filename && rowObject.filename != ""){
			filename = rowObject.filename;
			formatStr = "<a href=\"javascript:void(0)\" style=\"color:blue!important\" onclick=\"downloadFile('"
				+filename+"','"+rowObject.filename1+"','"+rowObject.pk+"')\">"+rowObject.filename1+"</a>";
		}
		return formatStr;
	}
});

function downloadFile(fileName,fileName1,pk){
	window.open("${ctx}/admin/custContractTemp/download?fileName="+ fileName + "&fileName1=" + fileName1 + "&Pk="+pk);
}
	

</script>
</head>
<body>
	<div class="body-box-list">
		<div class="panel panel-system">
		    <div class="panel-body">
		      	<div class="btn-group-xs">
					<button type="button" class="btn btn-system" id="closeBtn" onclick="closeWin(true)">
						<span>关闭</span>
					</button>
				</div>
			</div>
		</div>
		<div class="query-form" hidden="true">
			<form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
				<house:token></house:token>
				<input type="hidden" name="jsonString" value=""/>
				<input type="hidden" id="status" name="status" value="2"/>
				<input type="hidden" id="custType" name="custType" value="${custContractTemp.custType}"/>
				<input type="hidden" id="type" name="type" value="${custContractTemp.type }"/>
			</form>
		</div>
		<div class="pageContent">
			<div id="content-list">
				<table id="dataTable"></table>
				<div id="dataTablePager"></div>
			</div>
		</div> 
	</div>
</body>
</html>
