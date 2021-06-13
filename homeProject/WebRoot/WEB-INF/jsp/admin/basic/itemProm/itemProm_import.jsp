<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  	<title>导入excel</title>
  	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8"/>
  	<META HTTP-EQUIV="pragma" CONTENT="no-cache"/>
  	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"/>
  	<META HTTP-EQUIV="expires" CONTENT="0"/>
  	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge"/>
  	<%@ include file="/commons/jsp/common.jsp" %>
  	<script type="text/javascript">
    var hasInvalid = true;
    $(document).ready(function () {
      	//初始化表格
      	Global.JqGrid.initJqGrid("dataTable", {
        	height: $(document).height() - $("#content-list").offset().top - 72,
        	rowNum: 10000,
			colModel : [
			  	{name: "isinvaliddescr", index: "isinvaliddescr", width: 120, label: "是否有效数据", sortable: true, align: "left"},
			  	{name: "isinvalid", index: "isinvalid", width: 100, label: "是否有效数据", sortable: true, align: "left",hidden:true},
				{name: "itemcode", index: "itemcode", width: 70, label: "材料编号", sortable: true, align: "left"},
				{name: "itemdescr", index: "itemdescr", width: 216, label: "材料名称", sortable: true, align: "left"},
				{name: "itemtype1descr", index: "itemtype1descr", width: 80, label: "材料类型1", sortable: true, align: "left"},
				{name: "itemtype2descr", index: "itemtype2descr", width: 80, label: "材料类型2", sortable: true, align: "left"},
				{name: "suppldescr", index: "suppldescr", width: 150, label: "供应商", sortable: true, align: "left"},
				{name: "promprice", index: "promprice", width: 80, label: "促销价", sortable: true, align: "right"},
				{name: "promcost", index: "promcost", width: 80, label: "促销进价", sortable: true, align: "right"},
				{name: "begindate", index: "begindate", width: 90, label: "促销开始日期", sortable: true, align: "left", formatter: formatDate},
				{name: "enddate", index: "enddate", width: 90, label: "促销结束日期", sortable: true, align: "left", formatter: formatDate}
			]
		});
      	//初始化excel模板表格
      	Global.JqGrid.initJqGrid("modelDataTable", {
        	height: $(document).height() - $("#content-list").offset().top - 70,
        	colModel: [
          		{name: "itemcode", index: "itemcode", width: 200, label: "材料编号", sortable: true, align: "left"},
          		{name: "promprice", index: "promprice", width: 103, label: "促销价", sortable: true, align: "right"},
				{name: "promcost", index: "promcost", width: 101, label: "促销进价", sortable: true, align: "right"},
				{name: "begindate", index: "begindate", width: 129, label: "促销开始日期", sortable: true, align: "left", formatter: formatDate},
				{name: "enddate", index: "enddate", width: 134, label: "促销结束日期", sortable: true, align: "left", formatter: formatDate}
        	]
		});
      	$("#modelDataTable").addRowData(1, {
        	"itemcode": "091066",
        	"promprice": "10",
        	"promcost": "8",
        	"begindate": "2016-9-20",
        	"enddate": "2016-9-28"
      	}, "last");
      	return false;
	});
    //加载文件验证
    function check() {
      	var fileName = $("#file").val();
      	var reg = /^.+\.(?:xls|xlsx)$/i;
      	if (fileName.length == 0) {
        	art.dialog({
          		content: "请选择需要导入的excel文件！"
        	});
        	return false;
      	} else if (fileName.match(reg) == null) {
        	art.dialog({
          		content: "文件格式不正确！请导入正确的excel文件！"
        	});
        	return false;
      	}
      	return true;
    }
    //加载excel
    function loadData() {
      	if (check()) {
        	var formData = new FormData();
        	formData.append("file", document.getElementById("file").files[0]);
        	formData.append("custCode", "${baseItemChg.custCode}");
        	$.ajax({
          		url: "${ctx}/admin/itemProm/loadImport",
          		type: "POST",
          		data: formData,
          		contentType: false,
          		processData: false,
          		success: function (data) {
            		if (data.hasInvalid) hasInvalid = true;
            		else hasInvalid = false;
            		if (data.success == false) {
              			art.dialog({
                			content: data.returnInfo
              			});
            		} else {
              			$("#dataTable").clearGridData();
              			$.each(data.datas, function (k, v) {
              				v.lineamount = myRound(v.qty*(v.unitprice+v.material));
                			$("#dataTable").addRowData(k + 1, v, "last");
              			});
            		}
          		},
          		error: function () {
            		art.dialog({
              			content: "上传文件失败!"
            		});
          		}
			});
      	}
    }
    //导入数据
    function doImport() {
    	var tableId = "dataTable";
    	var colModel = $("#"+tableId).jqGrid("getGridParam","colModel");
    	var rows = $("#"+tableId).jqGrid("getRowData");
    	if (!rows || rows.length==0){
    		art.dialog({
    			content: "无数据导入"
    		});
    		return;
    	}
    	if (hasInvalid) {
            art.dialog({
              	content: "存在无效的数据，无法导入！"
            });
            return;
		}
		var flag=true;
    	for(var i=0;i<rows.length;i++){
    		if(flag){
		    	$.ajax({
		    		url:"${ctx}/admin/itemProm/doImport",
		    		type: "post",
		    		data: {itemCode:rows[i].itemcode,promPrice:rows[i].promprice,promCost:rows[i].promcost,
		    				beginDate:rows[i].begindate,endDate:rows[i].enddate},
		    		dataType: "json",
		    		cache: false,
		    		async:false,
		    		error: function(obj){
		    			showAjaxHtml({"hidden": false, "msg": "导入数据出错~"});
		    	    },
		    	    success: function(obj){
		    	    	if(obj.ret!="1"){
		    	    		art.dialog({
		    					content: obj.errmsg,
		    				});
		    				flag=false;
		    	    	}else if(i==rows.length-1){
		    	    		art.dialog({
			    				content: obj.errmsg,
			    				time: 1000,
			    				beforeunload: function () {
				    	    		closeWin();
			    				}
			    			});
		    	    	}
		    	    }
		    	 });
    		}
    	}
	}
  	</script>
</head>
<body>
<div class="body-box-form">
  	<div class="btn-panel pull-left">
    	<div class="btn-group-sm">
      		<button type="button" class="btn btn-system" onclick="loadData()">加载数据</button>
      		<button type="button" class="btn btn-system" onclick="doImport()">导入数据</button>
      		<button type="button" class="btn btn-system" onclick="doExcelNow('材料促销导入模板','modelDataTable')"
              style="margin-right: 15px">下载模板</button>
    	</div>
  	</div>
  	<div class="query-form" style="padding: 0px;border: none">
    	<form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
	      	<house:token></house:token>
	      	<input type="hidden" name="jsonString" value=""/>
	
	      	<div class="form-group">
	        	<label for="inputfile"></label>
	        	<input type="file" style="position: relative;top: -12px;" id="file" name="file"
	               accept="application/vnd.ms-excel,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet">
	      	</div>
    	</form>
  	</div>
  	<div class="pageContent">
    	<!--panelBar-->
    	<div id="content-list">
      		<table id="dataTable"></table>

    	</div>
    	<div style="display: none">
      		<table id="modelDataTable"></table>
      		<div id="modelDataTable"></div>
    	</div>
  	</div>
</div>
</body>
</html>
