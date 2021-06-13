<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <title>ItemChg列表</title>
  <META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8"/>
  <META HTTP-EQUIV="pragma" CONTENT="no-cache"/>
  <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"/>
  <META HTTP-EQUIV="expires" CONTENT="0"/>
  <META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge"/>
  <%@ include file="/commons/jsp/common.jsp"%>
  <script src="${resourceRoot}/pub/component_item.js?v=${v}"
          type="text/javascript"></script>
  <script src="${resourceRoot}/pub/component_customer.js?v=${v}"
          type="text/javascript"></script>
  <script type="text/javascript">
    function goto_query() {
      $("#dataTable").jqGrid("setGridParam", {
        url: "${ctx}/admin/itemChg/goDetailJqGrid",
        postData: $("#page_form").jsonForm(),
        page: 1
      }).trigger("reloadGrid");
    }
    //导出EXCEL
    function doExcel() {
      var url = "${ctx}/admin/itemChg/doDetailQueryExcel";
      doExcelAll(url);
    }
    /**初始化表格*/
    $(function () {
//初始化材料类型1，2，3三级联动
      Global.LinkSelect.initSelect("${ctx}/admin/item/itemTypeByAuthority", "itemType1", "itemType2");
      Global.JqGrid.initJqGrid("dataTable", {
        height: $(document).height() - $("#content-list").offset().top - 100,
        styleUI: 'Bootstrap',
		colModel : [
		  {name: "no", index: "no", width: 100, label: "增减单号", sortable: true, align: "left"},
		  {name: "itemtype1descr", index: "itemtype1descr", width: 80, label: "材料类型1", sortable: true, align: "left"},
		  {name: "custcode", index: "custcode", width: 90, label: "客户编号", sortable: true, align: "left"},
		  {name: "customerdescr", index: "customerdescr", width: 80, label: "客户名称", sortable: true, align: "left"},
		  {name: "address", index: "address", width: 140, label: "楼盘", sortable: true, align: "left"},
		  {name: "statusdescr", index: "statusdescr", width: 95, label: "材料增减状态", sortable: true, align: "left"},
		  {name: "isservicedescr", index: "isservicedescr", width: 112, label: "是否服务性产品", sortable: true, align: "left"},
		  {name: "confirmdate", index: "confirmdate", width: 130, label: "审核时间", sortable: true, align: "left", formatter: formatTime},
		  {name: "iscommidescr", index: "iscommidescr", width: 80, label: "是否提成", sortable: true, align: "left"},
		  {name: "fixareadescr", index: "fixareadescr", width: 120, label: "区域名称", sortable: true, align: "left"},
		  {name: "itemcodedescr", index: "itemcodedescr", width: 140, label: "材料名称", sortable: true, align: "left"},
		  {name: "sqlcodedescr", index: "sqlcodedescr", width: 95, label: "品牌", sortable: true, align: "left"},
		  {name: "reqqty", index: "reqqty", width: 90, label: "需求数量", sortable: true, align: "left"},
		  {name: "qty", index: "qty", width: 90, label: "数量", sortable: true, align: "left"},
		  {name: "unitdescr", index: "unitdescr", width: 90, label: "单位", sortable: true, align: "left"},
		  {name: "unitprice", index: "unitprice", width: 90, label: "单价", sortable: true, align: "left"},
		  {name: "cost", index: "cost", width: 83, label: "成本", sortable: true, align: "left", hidden: true},
		  {name: "beflineamount", index: "beflineamount", width: 90, label: "折扣前金额", sortable: true, align: "left", sum: true},
		  {name: "markup", index: "markup", width: 90, label: "折扣", sortable: true, align: "left"},
		  {name: "tmplineamount", index: "tmplineamount", width: 100, label: "小计", sortable: true, align: "left", sum: true},
		  {name: "processcost", index: "processcost", width: 90, label: "其它费用", sortable: true, align: "left", sum: true},
		  {name: "lineamount", index: "lineamount", width: 100, label: "总价", sortable: true, align: "left", sum: true},
		  {name: "remarks", index: "remarks", width: 160, label: "材料描述", sortable: true, align: "left"}
		]

      });
      $("#itemCode").openComponent_item();
      $("#custCode").openComponent_customer({condition: {'status': '4', 'disabledEle': 'status_NAME'}});
    });
  </script>
</head>

<body>
<div class="body-box-list">
  <div class="panel panel-system">
    <div class="panel-body">
      <div class="btn-group-xs">
        <button type="button" class="btn btn-system " onclick="closeWin()">关闭</button>
      </div>
    </div>
  </div>
  <div class="panel panel-info">
    <div class="panel-body">
      <form role="form" class="form-search" id="page_form" action=""
            method="post" target="targetFrame">
        <input type="hidden" id="expired" name="expired"
               value="${itemChg.expired}"/> <input type="hidden"
                                                   name="jsonString" value=""/>
        <ul class="ul-form">
          <li><label>客户编号</label>
            <input type="text" id="custCode"
                   name="custCode" value="${itemChg.custCode}"/>
          </li>
          <li><label>楼盘</label>
            <input type="text" id="address"
                   name="address" value="${itemChg.address}"/>
          </li>
          <li><label>材料类型1</label>
            <select id="itemType1"
                    name="itemType1"></select>
          </li>
          <li><label>材料类型2</label>
            <select id="itemType2"
                    name="itemType2"></select>
          </li>

          <li><label>增减单号</label> <input type="text" id="no" name="no"
                                         value="${itemChg.no}"/></li>
          <li><label>材料编号</label>
            <input type="text" id="itemCode"
                   name="itemCode" value="${itemChg.itemCode}"/>
          </li>
          <li><label>审核时间</label>
            <input type="text" id="dateFrom"
                   name="dateFrom" class="i-date"
                   onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
                   value="<fmt:formatDate value='${itemChg.dateFrom}' pattern='yyyy-MM-dd'/>"/>
          </li>
          <li><label>至</label>
            <input type="text" id="dateTo"
                   name="dateTo" class="i-date"
                   onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
                   value="<fmt:formatDate value='${itemChg.dateTo}' pattern='yyyy-MM-dd'/>"/>
          </li>
          <li><label>增减单状态</label>
            <house:xtdmMulit id="status"
                             dictCode="ITEMCHGSTATUS" selectedValue="${itemChg.status }"></house:xtdmMulit>
          </li>


          <li class="search-group">
            <input type="checkbox" id="expired_show"
                   name="expired_show" value="${itemChg.expired}"
                   onclick="hideExpired(this)" ${itemChg.expired!='T' ?'checked':'' }/><p>隐藏过期</p>
            <button type="button" class="btn  btn-sm btn-system "
                    onclick="goto_query();">查询
            </button>
            <button type="button" class="btn btn-sm btn-system "
                    onclick="doExcel()">输出excel
            </button>
          </li>
        </ul>

      </form>
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


