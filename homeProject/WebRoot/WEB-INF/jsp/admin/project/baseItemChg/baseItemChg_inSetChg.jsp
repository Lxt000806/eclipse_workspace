<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  	<title>套内项目减项</title>
  	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8"/>
  	<META HTTP-EQUIV="pragma" CONTENT="no-cache"/>
  	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"/>
  	<META HTTP-EQUIV="expires" CONTENT="0"/>
  	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge"/>
  	<%@ include file="/commons/jsp/common.jsp" %>
  	<script type="text/javascript">
    var reqpk;
    var showOutSet = "1";
    /**初始化表格*/
    $(function () {
    	if (top.$("#iframe_baseItemChg_Add")[0]) {
        	var topFrame = "#iframe_baseItemChg_Add";
      	} else if (top.$("#iframe_baseItemChgConfirm")[0]) {
        	var topFrame = "#iframe_baseItemChgConfirm";
      	} else {
        	var topFrame = "#iframe_baseItemChgUpdate";
      	}
      	reqpk = top.$(topFrame)[0].contentWindow.getReqPk().join(",");
      	//初始化表格
		if("${baseItemChg.custTypeType}"=="1"){
			$("#showOutSet").attr("check","true");
			showOutSet = "1"
		}else{
			showOutSet = "0"
		}
      	Global.JqGrid.initJqGrid("dataTable", {
        	url: "${ctx}/admin/baseItemChg/goTransActionJqGrid?custCode=${baseItemChg.custCode}" + "&showOutSet=1" +"&isOutSet=0",
        	height: $(document).height() - $("#content-list").offset().top - 62,
        	multiselect: true,
        	rowNum: 10000,
        	footerrow: false,
			colModel : [
			    {name: "pk", index: "pk", width: 50, label: "PK", sortable: true, align: "left", hidden: true},
			    {name: "fixareaseq", index: "fixareaseq", width: 50, label: "fixareaseq", sortable: true, align: "left", hidden: true},
		  		{name: "custcode", index: "custcode", width: 85, label: "客户编号", sortable: true, align: "left", hidden: true},
		  		{name: "baseitemcode", index: "baseitemcode", width: 85, label: "材料编号", sortable: true, align: "left", hidden: true},
		  		{name: "customerdescr", index: "customerdescr", width: 85, label: "客户名称", sortable: true, align: "left", hidden: true},
		  		{name: "address", index: "address", width: 130, label: "楼盘", sortable: true, align: "left", hidden: true},
		  		{name: "fixareadescr", index: "fixareadescr", width: 150, label: "区域名称", sortable: true, align: "left", hidden: true},
		  		{name: "fixareadescr2", index: "fixareadescr2", width: 150, label: "区域名称2", sortable: true, align: "left", hidden: true},
		  		{name: "fixareapk", index: "fixareapk", width: 90, label: "区域编号", sortable: true, align: "left", hidden: true},
		  		{name: "baseitemdescr", index: "baseitemdescr", width: 200, label: "基装项目名称", sortable: true, align: "left"},
		  		{name: "qty", index: "qty", width: 50, label: "数量", sortable: true, align: "right"},
		  		{name: "uom", index: "uom", width: 50, label: "单位", sortable: true, align: "left", hidden: true},
		  		{name: "bicost", index: "bicost", width: 60, label: "成本", sortable: true, align: "right",hidden: true},
		  		{name: "offerpri", index: "offerpri", width: 80, label: "人工单价", sortable: true, align: "right"},
		  		{name: "bimaterial", index: "bimaterial", width: 80, label: "材料单价", sortable: true, align: "right"},
		  		{name: "lineamount", index: "lineamount", width: 90, label: "总价", sortable: true, align: "right",hidden:true},
		  		{name: "isoutsetdescr", index: "isoutsetdescr", width: 85, label: "套餐外项目", sortable: true, align: "left"},
		  		{name: "isoutset", index: "isoutset", width: 85, label: "套餐外项目", sortable: true, align: "left",hidden:true},
		  		{name: "remark", index: "remark", width: 200, label: "备注", sortable: true, align: "left"},
		  		{name: "lastupdate", index: "lastupdate", width: 120, label: "修改时间", sortable: true, align: "left", formatter: formatTime},
		  		{name: "lastupdatedby", index: "lastupdatedby", width: 70, label: "修改人", sortable: true, align: "left"},
		  		{name: "expired", index: "expired", width: 80, label: "是否过期", sortable: true, align: "left"},
		  		{name: "actionlog", index: "actionlog", width: 60, label: "操作", sortable: true, align: "left"},
		  		{name: "category", index: "category", width: 80, label: "项目类型", sortable: true, align: "left", hidden: true},
		  		{name: "prjctrltype", index: "prjctrltype", width: 80, label: "发包方式", sortable: true, align: "left", hidden: true},
		  		{name: "biofferctrl", index: "biofferctrl", width: 80, label: "人工发包", sortable: true, align: "left", hidden: true},
		  		{name: "bimaterialctrl", index: "bimaterialctrl", width: 80, label: "材料发包", sortable: true, align: "left", hidden: true},
		  		{name: "iscalmangefee", index: "iscalmangefee", width: 80, label: "是否计算管理费", sortable: true, align: "left", hidden: true},
		  		{name: "baseitemsetno", index: "baseitemsetno", width: 68, label: "套餐包", sortable: true, align: "left"},
	            {name: "ismainitem", index: "ismainitem", width: 68, label: "主项目", sortable: true, align: "left", hidden: true},
	            {name: "ismainitemdescr", index: "ismainitemdescr", width: 68, label: "主项目", sortable: true, align: "left"},
			],
			grouping : true,
	        groupingView: {
	            groupField: ["fixareadescr2"],
	            groupColumnShow: [false],
	            groupText: ["<b title=\"{0}({1})\">{0}({1})</b>"],
	            groupOrder: ["asc"],
	            groupSummary: [false],
	            groupCollapse: false
	        }
      	});
      	if ("${baseItemChg.costRight}".trim() == "1") {
        	$("#dataTable").setGridParam().showCol("cost").trigger("reloadGrid");
      	}
      	//保存
      	$("#saveBtn").on("click", function () {
        	var ids = $("#dataTable").jqGrid("getGridParam", "selarrrow");
        	if (ids.length == 0) {
          		Global.Dialog.infoDialog("请选择要增减的材料！");
          		return;
        	}
        	var returnData = {};
        	var selectRows = [];
        	$.each(ids, function (k, id) {
          		var row = $("#dataTable").jqGrid("getRowData", id);
          		row.preqty = 0;//row.qty 类似快速新增，需求数量为0
          		row.unitprice = row.offerpri; //快速新增时 人工单价取 tBaseItem.offerpri
          		row.material = row.bimaterial;//快速新增时 材料单价取 tBaseItem.material
          		row.offerctrl = row.biofferctrl;//快速新增时 人工发包取 tBaseItem.offerctrl
          		row.materialctrl = row.bimaterialctrl ;//快速新增时 材料发包取 tBaseItem.materialctrl
          		row.cost = row.bicost;//快速新增时 成本取 tBaseItem.cost
          		row.isoutset = "1"; //添加到变更明细时，套餐外项目=‘是’
          		row.isoutsetdescr = "是"; //添加到变更明细时，套餐外项目=‘是’
          		row.qty = -myRound(row.qty,4); //添加到变更明细时，套餐外项目=‘是’
          		row.remarks = row.remark;
          		selectRows.push(row);
        	});
        	returnData.selectRows = selectRows;
        	Global.Dialog.returnData = returnData;
        	closeWin();
      	});

      	//全选
      	$("#selectallBtn").on("click", function () {
        	Global.JqGrid.jqGridSelectAll("dataTable", true);
      	});

      	//不选
      	$("#unselectallBtn").on("click", function () {
        	Global.JqGrid.jqGridSelectAll("dataTable", false);
      	});
    });

    function changeShow(obj) {
      	var id = $(obj).attr("id");
      	if ($(obj).is(":checked")) {
        	$("#" + id).val("1");
      	} else {
        	$("#" + id).val("");
      	}
      	$("#dataTable").jqGrid("setGridParam", {
        	url: "${ctx}/admin/baseItemChg/goTransActionJqGrid?custCode=${baseItemChg.custCode}" + "&showOutSet=" + $("#" + id).val(),
        	page: 1,
      	}).trigger("reloadGrid");
   	}
  	</script>
</head>
<body>
<div class="body-box-list">
  	<div class="panel panel-system">
    	<div class="panel-body">
      		<div class="btn-group-xs">
        		<button type="button" class="btn btn-system" id="saveBtn">保存</button>
        		<button type="button" class="btn btn-system" id="selectallBtn">全选</button>
        		<button type="button" class="btn btn-system" id="unselectallBtn">不选</button>
        		<button type="button" class="btn btn-system" onclick="closeWin(false)">关闭</button>
      		</div>
    	</div>
  	</div>
	<c:if test="${baseItemChg.custTypeType!='1'}">
  	<div class="query-form">
    	<form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
      	<ul class="ul-form">
        	<li>
          		<label></label>
          		<input id="showOutSet" name="showOutSet" type="checkbox" onclick="changeShow(this)"/>显示套餐内项目&nbsp
        	</li>
      	</ul>
    	</form>
  	</div>
  	</c:if>

  	<div id="content-list">
    	<table id="dataTable"></table>

  	</div>
</div>
</body>
</html>


