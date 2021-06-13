<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>合同模板管理</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
<script type="text/javascript">
function add(){
	Global.Dialog.showDialog("custContractTempAdd",{
		  title:"合同模板——新增",
		  url:"${ctx}/admin/custContractTemp/goSave",
		  height: 670,
		  width:1000,
		  returnFun: goto_query
		});
}

function update(){
	var ret = selectDataTableRow();
    if (ret) {
      Global.Dialog.showDialog("custContractTempUpdate",{
		  title:"合同模板——修改",
		  url:"${ctx}/admin/custContractTemp/goUpdate?id="+ret.pk,
		  height:670,
		  width:1000,
		  returnFun: goto_query
		});
    } else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}

function copy(){
	var ret = selectDataTableRow();
    if (ret) {
      Global.Dialog.showDialog("custContractTempCopy",{
		  title:"合同模板——复制",
		  url:"${ctx}/admin/custContractTemp/goCopy?id="+ret.pk,
		  height:670,
		  width:1000,
		  returnFun: goto_query
		});
    } else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}

function updateVersion(){
	var ret = selectDataTableRow();
    if (ret) {
      Global.Dialog.showDialog("custContractTempUpdateVersion",{
		  title:"合同模板——版本更新",
		  url:"${ctx}/admin/custContractTemp/goUpdateVersion?id="+ret.pk,
		  height:670,
		  width:1000,
		  returnFun: goto_query
		});
    } else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}

function view(){
	var ret = selectDataTableRow();
    if (ret) {
      Global.Dialog.showDialog("custContractTempView",{
		  title:"合同模板——查看",
		  url:"${ctx}/admin/custContractTemp/goView?id="+ret.pk,
		  height:670,
		  width:1000
		});
    } else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}

function viewFiled(){
	var ret = selectDataTableRow();
	if(!ret){
		art.dialog({
			content:"请选择一条记录进行查看",
		});
		
		return;
	}
	
	Global.Dialog.showDialog("viewFiledDoc",{
		title:"查看归档文档",
		postData:{custType: ret.custtype, type: ret.type},
		url:"${ctx}/admin/custContractTemp/goViewFiled",
		height:680,
		width:1300,
		returnFun:goto_query
	});	
}

/**初始化表格*/
$(function(){
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/custContractTemp/goJqGrid",
			postData:{status: "1"},
			height:$(document).height()-$("#content-list").offset().top-75,
			colModel : [
			  {name : 'pk',index : 'pk',width : 50,label:'编号',sortable : true,align : "left",hidden:true},
		      {name : 'custtypedescr',index : 'custtypedescr',width : 100,label:'客户类型',sortable : true,align : "left"},
		      {name : 'descr',index : 'descr',width : 170,label:'模板名称',sortable : true,align : "left"},
		      {name : 'version',index : 'version',width : 60,label:'版本号',sortable : true,align : "left"},
		      {name : 'typedescr',index : 'typedescr',width : 80,label:'合同类型',sortable : true,align : "left"},
		      {name : 'builderdescr',index : 'builderdescr',width : 80,label:'项目名称',sortable : true,align : "left"},
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
            ]
		});
});

function clearForm(){
	$("#page_form input[type='text']").val("");
	$("#type").val("");
	$("#custType").val("");
}

function doExcel(){
	var url = "${ctx}/admin/custContractTemp/doExcel";
	doExcelAll(url);
}

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

function downloadFile(fileName,fileName1,pk){
	console.log(fileName);
	console.log(pk);
	console.log(fileName1);
	window.open("${ctx}/admin/custContractTemp/download?fileName="+ fileName + "&fileName1=" + fileName1 + "&Pk="+pk);
}

</script>
</head>
    
<body>
	<div class="body-box-list">
	  	<div class="query-form">
	    	<form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
	    		<input type="hidden" name="exportData" id="exportData">
	    		<input type="hidden" name="jsonString" value=""/>
	    		<input type="hidden" name="status" id="status" value="1"/>
	      		<ul class="ul-form">
	        		<li>
	          			<label>模板名称</label>
	          			<input type="text" id="descr" name="descr" value="${custContractTemp.descr }"/>
	        		</li>
	        		<li>
		        		<label>客户类型</label>
		        		<house:xtdm id="custType" dictCode="CUSTTYPE"></house:xtdm>
					</li>
	        		<li>
		        		<label>合同类型</label>
		        		<house:xtdm id="type" dictCode="CUSTCONTEMPTYPE"></house:xtdm>
					</li>
	        		<li class="search-group"><input type="checkbox" id="expired"
						name="expired" value="${custContractTemp.expired}"
						onclick="hideExpired(this)" ${custType.expired!='T' ?'checked':'' }/>
						<p>隐藏过期</p>
						<button type="button" class="btn btn-sm btn-system "
							onclick="goto_query();">查询</button>
						<button type="button" class="btn btn-sm btn-system "
							onclick="clearForm();">清空</button>
					</li>
	      		</ul>
	    	</form>
	 	</div>
		<div class="btn-panel">
			<div class="btn-group-sm">
				<button type="button" id="add" class="btn btn-system" onclick="add()">新增</button>
				<button type="button" id="update" class="btn btn-system" onclick="update()">修改</button>
				<button type="button" id="copy" class="btn btn-system" onclick="copy()">复制</button>
				<button type="button" id="updateVersion" class="btn btn-system" onclick="updateVersion()">版本更新</button>
				<button type="button" id="view" class="btn btn-system" onclick="view()">查看</button>
				<button type="button" id="view" class="btn btn-system" onclick="viewFiled()">查看归档文档</button>
				<button type="button" class="btn btn-system" onclick="doExcel()"><span>导出excel</span></button>
			</div>
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


