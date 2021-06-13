<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <title>预算添加列表</title>
  <META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8"/>
  <META HTTP-EQUIV="pragma" CONTENT="no-cache"/>
  <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"/>
  <META HTTP-EQUIV="expires" CONTENT="0"/>
  <META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge"/>
  <%@ include file="/commons/jsp/common.jsp" %>
  <script type="text/javascript">
    /**初始化表格*/
    $(function () {
      //初始化表格
      Global.JqGrid.initJqGrid("dataTable", {
        url: "${ctx}/admin/itemChg/goItemPlanAddJqGrid?custCode=${itemChg.custCode}&itemType1=${itemChg.itemType1}&isService=${itemChg.isService}",
        height: $(document).height() - $("#content-list").offset().top - 80,
        multiselect: true,
        styleUI: 'Bootstrap',
        rowNum:100000,
		colModel : [
		  {name: "custcode", index: "custcode", width: 85, label: "客户编号", sortable: true, align: "left", hidden: true},
		  {name: "itemcode", index: "itemcode", width: 85, label: "材料编号", sortable: true, align: "left", hidden: true},
		  {name: "customerdescr", index: "customerdescr", width: 85, label: "客户名称", sortable: true, align: "left", hidden: true},
		  {name: "address", index: "address", width: 130, label: "楼盘", sortable: true, align: "left", hidden: true},
		  {name: "fixareapk", index: "fixareapk", width: 127, label: "区域编号", sortable: true, align: "left", hidden: true},
		  {name: "intprodpk", index: "intprodpk", width: 127, label: "集成成品编号", sortable: true, align: "left", hidden: true},
		  {name: "fixareadescr", index: "fixareadescr", width: 127, label: "区域名称", sortable: true, align: "left"},
		  {name: "intproddescr", index: "intproddescr", width: 90, label: "集成成品", sortable: true, align: "left", hidden: true},
		  {name: "itemdescr", index: "itemdescr", width: 244, label: "材料名称", sortable: true, align: "left"},
		  {name: "itemtype1descr", index: "itemtype1descr", width: 90, label: "材料类型1", sortable: true, align: "left"},
		  {name: "itemtype2descr", index: "itemtype2descr", width: 65, label: "材料类型2", sortable: true, align: "left", hidden: true},
		  {name: "itemtype2", index: "itemtype2", width: 85, label: "材料类型2", sortable: true, align: "left", hidden: true},
		  {name: "itemtype3descr", index: "itemtype3descr", width: 65, label: "材料类型3", sortable: true, align: "left", hidden: true},
		  {name: "qty", index: "qty", width: 70, label: "数量", sortable: true, align: "right"},
		  {name: "uom", index: "uom", width: 61, label: "单位", sortable: true, align: "left", hidden: true},
		  {name: "uomdescr", index: "uomdescr", width: 61, label: "单位", sortable: true, align: "left"},
		  {name: "beflineamount", index: "beflineamount", width: 80, label: "折扣前金额", sortable: true, align: "left", sum: true, hidden: true},
		  {name: "cost", index: "cost", width: 90, label: "成本", sortable: true, align: "right", hidden: true},
		  {name: "unitprice", index: "unitprice", width: 80, label: "单价", sortable: true, align: "right"},
		  {name: "markup", index: "markup", width: 60, label: "折扣", sortable: true, align: "right"},
		  {name: "tmplineamount", index: "tmplineamount", width: 90, label: "小计", sortable: true, align: "right"},
		  {name: "processcost", index: "processcost", width: 90, label: "其他费用", sortable: true, align: "right"},
		  {name: "lineamount", index: "lineamount", width: 90, label: "总价", sortable: true, align: "right"},
		  {name: "remark", index: "remark", width: 140, label: "备注", sortable: true, align: "left"},
		  {name: "itemsetdescr", index: "itemsetdescr", width: 100, label: "套餐包", sortable: true, align: "left"},
		  {name: "lastupdate", index: "lastupdate", width: 110, label: "修改时间", sortable: true, align: "left", formatter: formatTime},
		  {name: "lastupdatedby", index: "lastupdatedby", width: 70, label: "修改人", sortable: true, align: "left"},
		  {name: "expired", index: "expired", width: 85, label: "是否过期", sortable: true, align: "left"},
		  {name: "actionlog", index: "actionlog", width: 70, label: "操作", sortable: true, align: "left"}
		]

      });
      if ('${itemChg.itemType1}'.trim() == 'JC') {
        $("#dataTable").setGridParam().showCol("intproddescr").trigger("reloadGrid");
      }
      //保存
      $("#saveBtn").on("click", function () {
        var ids = $("#dataTable").jqGrid('getGridParam', 'selarrrow');
        if (ids.length == 0) {
          Global.Dialog.infoDialog("请选择要增减的材料！");
          return;
        }
        var returnData = {};
        var selectRows = [];
        $.each(ids, function (k, id) {
          var row = $("#dataTable").jqGrid('getRowData', id);
          row.preqty = 0;
          row.tmplineamount = myRound(myRound(row.qty * row.unitprice, 4) * row.markup / 100);
          if (row.qty < 0) row.processcost = -row.processcost;
          row.lineamount = myRound(parseFloat(row.processcost) + parseFloat(row.tmplineamount));
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
  </script>
</head>

<body>
<div class="body-box-list">
  <div class="panel panel-system">
    <div class="panel-body">
      <div class="btn-group-xs">
        <button type="button" class="btn btn-system " id="saveBtn">保存</button>
        <button type="button" class="btn btn-system " id="selectallBtn">全选</button>
        <button type="button" class="btn btn-system " id="unselectallBtn">不选</button>
        <button type="button" class="btn btn-system " onclick="closeWin(false)">关闭</button>
      </div>
    </div>
  </div>
  <div id="content-list">
    <table id="dataTable"></table>

  </div>
</div>

</body>
</html>


