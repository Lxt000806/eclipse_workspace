<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <title>小计列表</title>
  <META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8"/>
  <META HTTP-EQUIV="pragma" CONTENT="no-cache"/>
  <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"/>
  <META HTTP-EQUIV="expires" CONTENT="0"/>
  <META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge"/>
  <%@ include file="/commons/jsp/common.jsp"%>
  <script type="text/javascript">
    if (top.$("#iframe_itemChg_Add")[0]) {
      var topFrame = "#iframe_itemChg_Add";
    } else if (top.$("#iframe_itemChgDetail")[0]) {
      var topFrame = "#iframe_itemChgDetail";
    } else if (top.$("#iframe_itemChgConfirm")[0]) {
      var topFrame = "#iframe_itemChgConfirm";
    } else {
      var topFrame = "#iframe_itemChgUpdate";
    }
    var itemtype2descr;
    var tmpsubtotal = 0;
    var amountsubtotal = 0;
    $(function () {
      Global.JqGrid.initJqGrid("dataTable", {
        height: $(document).height() - $("#content-list").offset().top - 100,
        styleUI: 'Bootstrap',
        colModel: [
          {name: "itemtype2descr", index: "itemtype2descr", width: 85, label: "材料类型2", sortable: true, align: "left"},
          {name: "tmpsubtotal", index: "tmpsubtotal", width: 85, label: "折扣前小计", sortable: true, align: "left", sum: true},
          {name: "amountsubtotal", index: "amountsubtotal", width: 85, label: "总价小计", sortable: true, align: "left", sum: true}
        ]

      });

    })

    function init() {
      var arr = $(top.$(topFrame)[0].contentWindow.document.getElementById("dataTable")).jqGrid("getRowData");
      $.each(arr, function (k, v) {
        if (itemtype2descr && itemtype2descr != v.itemtype2descr) {
          $("#dataTable").addRowData(Global.Random.nextInt(), {
            itemtype2descr: itemtype2descr,
            tmpsubtotal: tmpsubtotal,
            amountsubtotal: amountsubtotal
          }, "last");
          tmpsubtotal = 0;
          amountsubtotal = 0;
        }
        itemtype2descr = v.itemtype2descr;
        tmpsubtotal += parseFloat(v.beflineamount);
        amountsubtotal += myRound(v.lineamount);
        if (k + 1 == arr.length)  $("#dataTable").addRowData(Global.Random.nextInt(), {
          itemtype2descr: itemtype2descr,
          tmpsubtotal: tmpsubtotal,
          amountsubtotal: amountsubtotal
        }, "last");
      })
    }
  </script>
</head>
<body onload="init()">
<div class="body-box-list">
  <div class="clear_float"></div>
  <!--query-form-->
  <div class="pageContent">
    <!--panelBar-->
    <!--panelBar end-->
    <div id="content-list">
      <table id="dataTable"></table>
      <div id="dataTablePager"></div>
    </div>
  </div>
</div>
</body>
</html>


