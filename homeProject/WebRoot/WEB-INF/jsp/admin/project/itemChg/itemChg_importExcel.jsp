<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <title>WareHouse明细</title>
  <META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8"/>
  <META HTTP-EQUIV="pragma" CONTENT="no-cache"/>
  <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"/>
  <META HTTP-EQUIV="expires" CONTENT="0"/>
  <META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge"/>
  <%@ include file="/commons/jsp/common.jsp" %>
  <script type="text/javascript">
    var hasInvalid = true;
    //tab分页
    $(document).ready(function () {
      var itemType1 = '${itemChg.itemType1}'.trim();

      //初始化表格
      Global.JqGrid.initJqGrid("dataTable", {
        height: $(document).height() - $("#content-list").offset().top - 72,
        styleUI: 'Bootstrap',
        rowNum: 10000,
		colModel : [
		  {name: "isinvaliddescr", index: "isinvaliddescr", width: 100, label: "是否有效数据", sortable: true, align: "left"},
		  {name: "isinvalid", index: "isinvalid", width: 100, label: "是否有效数据", sortable: true, align: "left",hidden:true},
		  {name: "fixareadescr", index: "fixareadescr", width: 149, label: "区域名称", sortable: true, align: "left"},
		  {name: "intproddescr", index: "intproddescr", width: 90, label: "集成成品", sortable: true, align: "left",hidden:true},
		  {name: "fixareapk", index: "fixareapk", width: 137, label: "区域名称", sortable: true, align: "left", hidden: true},
		  {name: "intprodpk", index: "intprodpk", width: 90, label: "集成成品", sortable: true, align: "left", hidden: true},
		  {name: "itemcode", index: "itemcode", width: 85, label: "材料编号", sortable: true, align: "left"},
		  {name: "itemdescr", index: "itemdescr", width: 220, label: "材料名称", sortable: true, align: "left"},
		  {name: "itemtype2descr", index: "itemtype2descr", width: 85, label: "材料类型2", sortable: true, align: "left", hidden: true},
		  {name: "itemtype2", index: "itemtype2", width: 85, label: "材料类型2", sortable: true, align: "left", hidden: true},
		  {name: "itemtype3descr", index: "itemtype3descr", width: 85, label: "材料类型3", sortable: true, align: "left", hidden: true},
		  {name: "algorithm", index: "algorithm", width: 70, label: "算法", sortable:false, align: "left", hidden: true},
		  {name: "algorithmdescr", index: "algorithmdescr", width: 85, label: "算法", sortable:false, align: "left", hidden: true},
		  {name: "qty", index: "qty", width: 76, label: "变动数量", sortable: true, align: "left", sum: true},
		  {name: "uom", index: "uom", width: 60, label: "单位", sortable: true, align: "left",hidden:true},
		  {name: "uomdescr", index: "uomdescr", width: 60, label: "单位", sortable: true, align: "left"},
		  {name: "unitprice", index: "unitprice", width: 80, label: "单价", sortable: true, align: "left"},
		  {name: "beflineamount", index: "beflineamount", width: 90, label: "折扣前金额", sortable: true, align: "left", sum: true},
		  {name: "markup", index: "markup", width: 68, label: "折扣", sortable: true, align: "left"},
		  {name: "tmplineamount", index: "tmplineamount", width: 90, label: "小计", sortable: true, align: "left", sum: true},
		  {name: "processcost", index: "processcost", width: 90, label: "其他费用", sortable: true, align: "left", sum: true},
		  {name: "lineamount", index: "lineamount", width: 90, label: "总价", sortable: true, align: "left", sum: true},
		  {name: "isoutsetdescr", index: "isoutsetdescr", width: 85, label: "套外", sortable: true, align: "left"},
		  {name: "isoutset", index: "isoutset", width: 85, label: "套餐外材料", sortable: true, align: "left", hidden: true},
		  {name: "remarks", index: "remarks", width: 187, label: "材料描述", sortable: true, align: "left"},
		  {name: "reqpk", index: "reqpk", width: 84, label: "需求PK", sortable: true, align: "left"},
		  {name: "isservice", index: "isservice", width: 20, label: "是否服务性产品", sortable: true, align: "left", hidden: true},
		  {name: "preqty", index: "preqty", width: 80, label: "需求数量", sortable: true, align: "left", hidden: true},
		  {name: "expired", index: "expired", width: 77, label: "是否过期", sortable: true, align: "left", hidden: true},
		  {name: "itemsetno", index: "itemsetno", width: 118, label: "套餐包 ", sortable: true, align: "left", hidden: true},
		  {name: "itemsetdescr", index: "itemsetdescr", width: 118, label: "套餐包 ", sortable: true, align: "left", hidden: true},
		  {name: "preplanareapk", index: "preplanareapk", width: 118, label: "空间", sortable: true, align: "left", hidden: true},
		  {name: "preplanareadescr", index: "preplanareadescr", width: 118, label: "空间 ", sortable: true, align: "left", hidden: true},
		  {name: "custtypeitempk", index: "custtypeitempk", width: 110, label: "套餐材料信息编号", sortable: true, align: "left"},
		  {name: "supplpromitempk", index: "supplpromitempk", width: 68, label: "供应商促销PK", sortable: true, align: "left"},
	   	  {name: "cuttypedescr", index: "cuttypedescr", width: 70, label: "切割类型", sortable:false, align: "left"},
		  {name: "cuttype", index: "cuttype", width: 65, label: "切割类型",sortable:false, align: "left",hidden: true},
		  {name: "algorithmper", index: "algorithmper", width: 80, label: "算法系数", sortable: true, align: "right",editable:true,edittype:'text',hidden: true},
		  {name: "algorithmdeduct", index: "algorithmdeduct", width: 80, label: "算法扣除数", sortable: true, align: "right",hidden: true},
		  {name: "doorpk", index: "doorpk", label: "门窗Pk",  width: 0.5,sortable:false, align: "left"},
		]
      });
      //初始化excel模板表格
      Global.JqGrid.initJqGrid("modelDataTable", {
        height: $(document).height() - $("#content-list").offset().top - 70,
        colModel: [
          {name: "fixareadescr", index: "fixareadescr", width: 149, label: "区域名称", sortable: true, align: "left"},
          {name: "intproddescr", index: "intproddescr", width: 90, label: "集成成品", sortable: true, align: "left"},
          {name: "itemcode", index: "itemcode", width: 85, label: "材料编号", sortable: true, align: "left"},
          {name: "qty", index: "qty", width: 76, label: "变动数量", sortable: true, align: "left", sum: true},
          {name: "unitprice", index: "unitprice", width: 80, label: "单价", sortable: true, align: "left"},
          {name: "markup", index: "markup", width: 68, label: "折扣", sortable: true, align: "left"},
          {name: "processcost", index: "processcost", width: 90, label: "其他费用", sortable: true, align: "left", sum: true},
          {name: "remarks", index: "remarks", width: 187, label: "材料描述", sortable: true, align: "left"},
          {name: "isoutsetdescr", index: "isoutsetdescr", width: 120, label: "套外", sortable: true, align: "left"},
          {name: "custtypeitempk", index: "custtypeitempk", width: 120, label: "套餐材料信息编号", sortable: true, align: "left"}
        ]
      });
      if (itemType1 == 'JC') $("#dataTable").setGridParam().showCol("intproddescr").trigger("reloadGrid");
      if (itemType1 == 'RZ') $("#dataTable").setGridParam().showCol("itemsetdescr").trigger("reloadGrid");
      if (itemType1 =='ZC'){
 		 $("#dataTable").setGridParam().showCol("algorithmdescr").trigger("reloadGrid");
 		 $("#dataTable").setGridParam().showCol("preplanareadescr").trigger("reloadGrid");
 		 $("#dataTable").setGridParam().showCol("algorithmper").trigger("reloadGrid");
 		 $("#dataTable").setGridParam().showCol("algorithmdeduct").trigger("reloadGrid");
 		 
 	 }	
      $("#modelDataTable").addRowData(1, {
        "fixareadescr": "主卧",
        "intproddescr": "主卧衣柜",
        "itemcode": "073455",
        "qty": 0,
        "unitprice": 0,
        "markup": 100,
        "processcost": 0
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
        formData.append("itemType1", '${itemChg.itemType1}');
        formData.append("custCode", '${itemChg.custCode}');
        formData.append("isService", '${itemChg.isService}');
        formData.append("isCupboard", '${itemChg.isCupboard}');
        $.ajax({
          url: "${ctx}/admin/itemChg/loadExcel",
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
                $("#dataTable").addRowData(k + 1, v, "last");
              })
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
    function importData() {
      if ($("#dataTable").jqGrid('getGridParam', 'records') == 0) {
        art.dialog({
          content: "请先载入要进行批量导入的预算数据！"
        });
        return;
      }
      if (hasInvalid) {
        art.dialog({
          content: "存在无效的数据，无法导入！"
        });
        return;
      }
      if (top.$("#iframe_itemChg_Add")[0]) {
        var topFrame = "#iframe_itemChg_Add";
      } else if (top.$("#iframe_itemChgConfirm")[0]) {
        var topFrame = "#iframe_itemChgConfirm";
      } else {
        var topFrame = "#iframe_itemChgUpdate";
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
      Global.Dialog.returnData = returnData;
      closeWin();
    }
  </script>
</head>
<body>
<div class="body-box-form">
  <div class="btn-panel pull-left">
    <div class="btn-group-sm">
      <button type="button" class="btn btn-system " onclick="loadData()">加载数据</button>
      <button type="button" class="btn btn-system " onclick="importData()">导入数据</button>
      <button type="button" class="btn btn-system " onclick="doExcelNow('材料增减导入模板','modelDataTable')"
              style="margin-right: 15px">下载模板
      </button>
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
