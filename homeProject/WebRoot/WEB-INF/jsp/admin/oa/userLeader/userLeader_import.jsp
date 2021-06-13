<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>userLeader导入excel</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
<script type="text/javascript">
var hasInvalid=true;
$(document).ready(function() {
    //初始化表格
	Global.JqGrid.initJqGrid("dataTable",{
		height:$(document).height()-$("#content-list").offset().top-72,
		rowNum:10000,
		colModel : [
		  {name : 'userid',index : 'userid',width : 100,label:'下属编号',sortable : true,align : "left"},
		  {name : 'leaderid',index : 'leaderid',width : 100,label:'上级领导编号',sortable : true,align : "left"}
        ]
	});
	//初始化excel模板表格
	Global.JqGrid.initJqGrid("modelDataTable",{
		height:$(document).height()-$("#content-list").offset().top-70,
		colModel : [
			{name : 'userid',index : 'userid',width : 100,label:'下属编号',sortable : true,align : "left"},
			{name : 'leaderid',index : 'leaderid',width : 100,label:'上级领导编号',sortable : true,align : "left"}
        ]
	});
	$("#modelDataTable").addRowData(1, {"userId":"00001","leaderId":"00002"}, "last");
});
//加载文件验证
function check(){
	var fileName=$("#file").val();
	var reg=/^.+\.(?:xls|xlsx)$/i;
    if(fileName.length==0){
    	art.dialog({
			content: "请选择需要导入的excel文件！"
		});
        return false;
    }else if(fileName.match(reg)==null){
   		 art.dialog({
			content: "文件格式不正确！请导入正确的excel文件！"
		});
		return false;
    }
    return true;
}
//加载excel
function loadData(){
	if(check()){
		var formData = new FormData();
        formData.append("file", document.getElementById("file").files[0]);
        $.ajax({
            url: "${ctx}/admin/userLeader/loadImport",
            type: 'post',
    		data: formData,
    		dataType: 'json',
    		cache: false,
            contentType: false,
            processData: false,
            success: function (data) {
            	if(data.hasInvalid) hasInvalid=true;
                else hasInvalid=false;
                if (data.success == false) {
                   art.dialog({
						content: data.returnInfo
					});
                }else{
                    $("#dataTable").clearGridData();
                    $.each(data.datas,function(k,v){
                     	$("#dataTable").addRowData(k+1,v,"last");
                    });
                }
             },
             error: function () {
                art.dialog({
					content: "加载文件失败!"
				});
             }
        });
     }
}
//导入数据
function doImport(){
	var tableId = "dataTable";
	var colModel = $("#"+tableId).jqGrid('getGridParam','colModel');
	var rows = $("#"+tableId).jqGrid("getRowData");
	if (!rows || rows.length==0){
		art.dialog({
			content: "无数据导入"
		});
		return;
	}
	var datas = {
		rows: JSON.stringify(rows)
	};
	$.ajax({
		url:'${ctx}/admin/userLeader/doImport',
		type: 'post',
		data: datas,
		dataType: 'json',
		cache: false,
		error: function(obj){
			showAjaxHtml({"hidden": false, "msg": '保存数据出错~'});
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
	    		$("#_form_token_uniq_id").val(obj.token.token);
	    		art.dialog({
					content: obj.msg,
					width: 200
				});
	    	}
	    }
	 });
}
</script>
</head>
<body>
<div class="body-box-form" >
	<div class="btn-panel pull-left" >
      <div class="btn-group-sm"  >
      <button type="button" class="btn btn-system" onclick="loadData()">加载数据</button>
      <button type="button" class="btn btn-system" onclick="doImport()">导入数据</button>
      <button type="button" class="btn btn-system" onclick="doExcelNow('分配下属模板','modelDataTable')" style="margin-right: 15px">下载模板</button>
      </div>
	</div>	
	<div class="query-form" style="padding: 0px;border: none">
        <form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
		 <house:token></house:token>
		 <input type="hidden" name="jsonString" value="" />
			<div class="form-group" >
		        <label for="inputfile"></label>
		        <input type="file" style="position: relative;top: -12px;" id="file" name="file" accept="application/vnd.ms-excel,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" >
			</div>
		</form>
   	</div>
	<div class="pageContent">
		<div id="content-list">
			<table id= "dataTable"></table>
		</div>
		<div style="display: none">
			<table id= "modelDataTable"></table>
		</div>
    </div>
</div>
</body>
</html>
