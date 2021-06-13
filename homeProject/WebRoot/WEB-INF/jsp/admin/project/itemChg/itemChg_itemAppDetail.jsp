<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <title>未发货领料列表</title>
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
        height: $(document).height() - $("#content-list").offset().top - 55,
        styleUI: 'Bootstrap',
        cellsubmit: 'clientArray',
        rowNum: 10000,
        colModel: [
          {name: "no", index: "no", width: 85, label: "领料单号", sortable: true, align: "left", hidden: true},
          {name: "itemcode", index: "itemcode", width: 85, label: "材料编号", sortable: true, align: "left"},
          {name: "itemdescr", index: "itemdescr", width: 244, label: "材料名称", sortable: true, align: "left"}
        ],
        grouping: true, // 是否分组,默认为false
        groupingView: {
          groupField: ['no'], // 按照哪一列进行分组
          groupColumnShow: [false], // 是否显示分组列
          groupText: ['<b>领料单号：{0}({1})</b>'], // 表头显示的数据以及相应的数据量
          groupCollapse: false, // 加载数据时是否只显示分组的组信息
          groupDataSorted: true, // 分组中的数据是否排序
          groupOrder: ['asc'], // 分组后的排序
          groupSummary: [false], // 是否显示汇总.如果为true需要在colModel中进行配置
          summaryType: 'max', // 运算类型，可以为max,sum,avg</div>
          summaryTpl: '<b>Max: {0}</b>',
          showSummaryOnHide: true //是否在分组底部显示汇总信息并且当收起表格时是否隐藏下面的分组
        }
      });

    });
    function init() {
      setTimeout(function () {
        if (top.$("#iframe_itemChg_Add")[0]) {
          var topFrame = "#iframe_itemChg_Add";
        } else if (top.$("#iframe_itemChgConfirm")[0]) {
          var topFrame = "#iframe_itemChgConfirm";
        } else {
          var topFrame = "#iframe_itemChgUpdate";
        }
        var itemAppDetail = top.$(topFrame)[0].contentWindow.getItemAppDetail();
        var params = JSON.stringify(itemAppDetail);
        $("#dataTable").jqGrid("setGridParam", {
          url: "${ctx}/admin/itemChgDetail/goTmpJqGrid",
          postData: {'params': params, 'orderBy': 'no'},
          page: 1
        }).trigger("reloadGrid");
      }, 100);
    }
  </script>
</head>

<body onload="init()">

<div class="panel panel-info" style="border: none">
  <div class="panel-heading">
      <span style="color: red">该客户有未发货的领料单，请先处理掉以下领料单（取消领料单或者修改领料数量为0）！
            	当前该客户的材料增减不允许保存！</span>
  </div>
  <div class="panel-body" style="padding: 0px">
    <div id="content-list">
      <table id="dataTable"></table>

    </div>
  </div>
</div>


</body>
</html>


