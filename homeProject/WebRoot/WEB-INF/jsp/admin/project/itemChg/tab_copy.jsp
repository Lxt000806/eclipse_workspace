<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <title></title>
  <META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8"/>
  <META HTTP-EQUIV="pragma" CONTENT="no-cache"/>
  <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"/>
  <META HTTP-EQUIV="expires" CONTENT="0"/>
  <META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge"/>
  <%@ include file="/commons/jsp/common.jsp" %>
  <script type="text/javascript">
    //tab分页
    $(document).ready(function () {
      var itemType1 = '${itemType1}'.trim();
      //初始化表格
      Global.JqGrid.initJqGrid("itemCopyDataTable", {
        height: 190,
        styleUI: 'Bootstrap',
		colModel : [
		  {name: "iscommi", index: "iscommi", width: 84, label: "是否提成", sortable: true, align: "left", hidden: true},
		  {name: "fixareadescr", index: "fixareadescr", width: 137, label: "区域111名称", sortable: true, align: "left"},
		  {name: "fixareapk", index: "fixareapk", width: 137, label: "区域名称", sortable: true, align: "left", hidden: true},
		  {name: "intprodpk", index: "intprodpk", width: 80, label: "集成成品", sortable: true, align: "left", hidden: true},
		  {name: "intproddescr", index: "intproddescr", width: 80, label: "集成成品", sortable: true, align: "left", hidden: true},
		  {name: "itemcode", index: "itemcode", width:60, label: "材料编号", sortable: true, align: "left"},
		  {name: "itemdescr", index: "itemdescr", width: 150, label: "材料名称", sortable: true, align: "left"},
		  {name: "itemtype2descr", index: "itemtype2descr", width: 85, label: "材料类型2", sortable: true, align: "left", hidden: true},
		  {name: "itemtype2", index: "itemtype2", width: 85, label: "材料类型2", sortable: true, align: "left", hidden: true},
		  {name: "sqlcodedescr", index: "sqlcodedescr", width: 89, label: "品牌", sortable: true, align: "left", hidden: true},
		  {name: "preqty", index: "preqty", width: 50, label: "需求数量", sortable: true, align: "right"},
		  {name: "qty", index: "qty", width: 50, label: "数量", sortable: true, align: "right",editable:true,edittype:'text',editrules:{number:true,required:true}},
		  {name: "uom", index: "uom", width: 30, label: "单位", sortable: true, align: "left", hidden: true},
		  {name: "uomdescr", index: "uomdescr", width: 30, label: "单位", sortable: true, align: "left"},
		  {name: "marketprice", index: "marketprice", width: 68, label: "市场价", sortable: true, align: "right", hidden: true},
		  {name: "unitprice", index: "unitprice", width: 50, label: "单价", sortable: true, align: "right",editable:true,edittype:'text',editrules:{number:true,required:true}},
		  {name: "beflineamount", index: "beflineamount", width: 80, label: "折扣前金额", sortable: true, align: "right", sum: true},
		  {name: "markup", index: "markup", width: 40, label: "折扣", sortable: true, align: "right",editable:true,edittype:'text',editrules:{number:true,required:true,integer:true,minValue:1}},
		  {name: "tmplineamount", index: "tmplineamount", width: 77, label: "小计", sortable: true, align: "right", sum: true},
		  {name: "processcost", index: "processcost", width: 60, label: "其他费用", sortable: true, align: "right", sum: true,editable:true,edittype:'text',editrules:{number:true,required:true}},
		  {name: "lineamount", index: "lineamount", width: 80, label: "总价", sortable: true, align: "right", sum: true},
		  {name: "isoutsetdescr", index: "isoutsetdescr", width: 80, label: "套餐外材料", sortable: true, align: "left", hidden: true},
		  {name: "disccost", index: "disccost", width: 104, label: "优惠分摊成本", sortable: true, align: "left", sum: true, hidden: true},
		  {name: "remarks", index: "remarks", width: 177, label: "材料描述", sortable: true, align: "left",editable:true,edittype:'textarea'},
		  {name: "lastupdate", index: "lastupdate", width: 126, label: "最后修改日期", sortable: true, align: "left", formatter: formatTime},
		  {name: "lastupdatedby", index: "lastupdatedby", width: 78, label: "修改人", sortable: true, align: "left"},
		  {name: "expired", index: "expired", width: 77, label: "是否过期", sortable: true, align: "left", hidden: true},
		  {name: "actionlog", index: "actionlog", width: 68, label: "操作", sortable: true, align: "left"},
		  {name: "reqpk", index: "reqpk", width: 20, label: "需求PK", sortable: true, align: "left", hidden: true},
		  {name: "isfixprice", index: "isfixprice", width: 20, label: "是否固定价格", sortable: true, align: "left", hidden: true},
		  {name: "isoutset", index: "isoutset", width: 20, label: "套餐外材料", sortable: true, align: "left", hidden: true},
		  {name: "isservice", index: "isservice", width: 20, label: "是否服务性产品", sortable: true, align: "left", hidden: true},
		  {name: "pk", index: "pk", width: 20, label: "编号", sortable: true, align: "left", hidden: true},
		  {name: "no", index: "no", width: 20, label: "增减单号", sortable: true, align: "left", hidden: true},
		  {name: "cost", index: "cost", width: 20, label: "成本", sortable: true, align: "left", hidden: true}
		],

        ondblClickRow: function (rowid, status) {
          var fixAreaPk = window.parent.document.getElementById("fixAreaPk").value;
          var fixAreaDescr = window.parent.document.getElementById("fixAreaDescr").value;
          var intProdPk = window.parent.document.getElementById("intProdPk").value;
          var intProdDescr = window.parent.document.getElementById("intProdDescr").value;
          var rowNo = window.parent.document.getElementById("rowNo").value;
          if (!fixAreaDescr) {
            art.dialog({
              content: "请先选择区域!"
            });
            return;
          }
          if (!intProdDescr && itemType1 == 'JC') {
            art.dialog({
              content: "请先选择集成成品!"
            });
            return;
          }
          var rowData = $("#itemCopyDataTable").jqGrid('getRowData', rowid);
          var opt = {};
          rowData.fixareapk = fixAreaPk;
          rowData.fixareadescr = fixAreaDescr;
          rowData.intprodpk = intProdPk;
          rowData.intproddescr = intProdDescr;
          rowData.qty = 0;
          rowData.beflineamount = 0;
          rowData.markup = 100;
          rowData.tmplineamount = 0;
          rowData.processcost = 0;
          rowData.lineamount = 0;
          //默认套餐外材料
          rowData.isoutsetdescr = "是";
          rowData.isoutset = "1";
          $(window.parent.document.getElementById("itemChgDetailDataTable")).addRowData(rowNo, rowData, "last");
          window.parent.document.getElementById("rowNo").value++;


        }

      });
      return false;
    });
  </script>


</head>

<body>

<div style="width: 1150px;height: 190px;float: right;">
  <table id="itemCopyDataTable"></table>
  <div id="itemCopyDataTablePager"></div>
</div>

</body>
</html>
