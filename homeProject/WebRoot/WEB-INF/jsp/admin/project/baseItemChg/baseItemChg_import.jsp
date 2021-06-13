<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  	<title>基装增减导入excel</title>
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
			  	{name: "fixareadescr", index: "fixareadescr", width: 200, label: "区域名称", sortable: true, align: "left"},
			  	{name: "fixareapk", index: "fixareapk", width: 90, label: "区域编号", sortable: true, align: "left", hidden: true},
			  	{name: "baseitemcode", index: "baseitemcode", width: 90, label: "基装项目编号", sortable: true, align: "left"},
			  	{name: "baseitemdescr", index: "baseitemdescr", width: 200, label: "基装项目名称", sortable: true, align: "left"},
			  	{name: "qty", index: "qty", width: 50, label: "变动数量", sortable: true, align: "left", sum: true},
			  	{name: "uom", index: "uom", width: 50, label: "单位", sortable: true, align: "left"},
			  	{name: "unitprice", index: "unitprice", width: 80, label: "人工单价", sortable: true, align: "left"},
			  	{name: "material", index: "material", width: 80, label: "材料单价", sortable: true, align: "left"},
			  	{name: "lineamount", index: "lineamount", width: 90, label: "总价", sortable: true, align: "left", sum: true},
			  	{name: "remarks", index: "remarks", width: 200, label: "备注", sortable: true, align: "left"},
			  	{name: "reqpk", index: "reqpk", width: 80, label: "需求PK", sortable: true, align: "left"},
			  	{name: "dispseq", index: "dispseq", width: 60, label: "顺序号", sortable: true, align: "left", hidden: true},
			  	{name: "preqty", index: "preqty", width: 80, label: "需求数量", sortable: true, align: "left", hidden: true},
			  	{name: "cost", index: "cost", width: 60, label: "成本", sortable: true, align: "left", hidden: true},
			  	{name: "baseitemsetno", index: "baseitemsetno", width: 68, label: "套餐包", sortable: true, align: "left"},
	            {name: "ismainitem", index: "ismainitem", width: 68, label: "主项目", sortable: true, align: "left", hidden: true},
	            {name: "ismainitemdescr", index: "ismainitemdescr", width: 68, label: "主项目", sortable: true, align: "left"},
				  	
			]
		});
      	//初始化excel模板表格
      	Global.JqGrid.initJqGrid("modelDataTable", {
        	height: $(document).height() - $("#content-list").offset().top - 70,
        	colModel: [
          		{name: "fixareadescr", index: "fixareadescr", width: 200, label: "区域名称", sortable: true, align: "left"},
          		{name: "baseitemcode", index: "baseitemcode", width: 90, label: "基装项目编号", sortable: true, align: "left"},
          		{name: "baseitemdescr", index: "baseitemdescr", width: 200, label: "基装项目名称", sortable: true, align: "left"},
          		{name: "qty", index: "qty", width: 50, label: "数量", sortable: true, align: "right"},
          		{name: "remarks", index: "remarks", width: 200, label: "备注", sortable: true, align: "left"},
          		{name: "baseitemsetno", index: "baseitemsetno", width: 68, label: "套餐包", sortable: true, align: "left"},
	            {name: "ismainitem", index: "ismainitem", width: 68, label: "主项目", sortable: true, align: "left", hidden: true},
	            {name: "ismainitemdescr", index: "ismainitemdescr", width: 68, label: "主项目", sortable: true, align: "left"},
        	]
		});
      	$("#modelDataTable").addRowData(1, {
        	"fixareadescr": "土建项目",
        	"baseitemcode": "0037",
        	"baseitemdescr": "砌12cm墙",
        	"qty": 2,
        	"remarks": "人工、水泥、沙浆、大红砖、不含粉刷，层高超过3米另计."
      	}, "last");
      	$("#modelDataTable").addRowData(1, {
        	"fixareadescr": "土建项目",
        	"baseitemcode": "0260",
        	"baseitemdescr": "马赛克饰面",
        	"qty": 2,
        	"remarks": "建福325水泥沙浆、人工辅料（不含瓷砖）"
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
        	formData.append("canUseComBaseItem","${baseItemChg.canUseComBaseItem}");
        	$.ajax({
          		url: "${ctx}/admin/baseItemChg/loadImport",
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
              				v.lineamount = myRound(myRound(v.qty * v.unitprice)+myRound(v.qty * v.material));
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
    	if (top.$("#iframe_baseItemChg_Add")[0]) {
        	var topFrame = "#iframe_baseItemChg_Add";
        } else if (top.$("#iframe_baseItemChgConfirm")[0]) {
            var topFrame = "#iframe_baseItemChgConfirm";
        } else {
            var topFrame = "#iframe_baseItemChgUpdate";
        }
        var arr = $(top.$(topFrame)[0].contentWindow.document.getElementById("dataTable")).jqGrid("getRowData");
        var returnData = $("#dataTable").jqGrid("getRowData");
        for (var i = 0; i < arr.length; i++) {
        	if (arr[i].reqpk) {
              	for (var j = 0; j < returnData.length; j++) {
                	if (arr[i].reqpk == returnData[j].reqpk) {
                  		art.dialog({
                    		content: "本次导入的需求pk已存在，无法生成增减明细！"
                  		});
                  		return;
                	}
              	}
            }
        }
    	var datas = {
        	custCode: "${baseItemChg.custCode}",
    		rows: JSON.stringify(rows)
    	};
    	$.ajax({
    		url:"${ctx}/admin/baseItemChg/doImport",
    		type: "post",
    		data: datas,
    		dataType: "json",
    		cache: false,
    		error: function(obj){
    			showAjaxHtml({"hidden": false, "msg": "导入数据出错~"});
    	    },
    	    success: function(obj){
    	    	if(obj.rs){
    	    		art.dialog({
    					content: obj.msg,
    					time: 1000,
    					beforeunload: function () {
    						Global.Dialog.returnData = obj.datas;
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
<div class="body-box-form">
  	<div class="btn-panel pull-left">
    	<div class="btn-group-sm">
      		<button type="button" class="btn btn-system" onclick="loadData()">加载数据</button>
      		<button type="button" class="btn btn-system" onclick="doImport()">导入数据</button>
      		<button type="button" class="btn btn-system" onclick="doExcelNow('基装增减导入模板','modelDataTable')"
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
