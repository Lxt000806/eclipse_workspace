<%@ page language="java" import="com.house.framework.commons.utils.PathUtil" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://" + request.getServerName() + ":"+request.getServerPort()+path+"/";
String jasperPath = PathUtil.WEB_INF+"jsp/jasper/";
%>
<!DOCTYPE html>
<html>
<head>
  <title>材料增减--批量打印</title>
  <META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8"/>
  <META HTTP-EQUIV="pragma" CONTENT="no-cache"/>
  <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"/>
  <META HTTP-EQUIV="expires" CONTENT="0"/>
  <META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge"/>
  <%@ include file="/commons/jsp/common.jsp"%>
  <script src="${resourceRoot}/pub/component_employee.js?v=${v}"
          type="text/javascript"></script>
  <script src="${resourceRoot}/pub/component_customer.js?v=${v}"
          type="text/javascript"></script>
  <script type="text/javascript">
    function clearForm() {
      $("#page_form input[type='text']").val('');
      $("#page_form select").val('');
      $("#status").val('');
      $.fn.zTree.getZTreeObj("tree_status").checkAllNodes(false);
    }
    function goto_query() {
      $("#dataTable").jqGrid("setGridParam", {
        url: "${ctx}/admin/baseItemChg/goJqGrid",
        postData: $("#page_form").jsonForm(),
        page: 1
      }).trigger("reloadGrid");
    }
    function doPrint() {
      var ids = $("#dataTable").jqGrid('getGridParam', 'selarrrow');
      if (ids.length == 0) {
        Global.Dialog.infoDialog("请选择需要打印的领料单！");
        return;
      }
      var isPrjManSend = false; // 是否项目经理提交 add by zb on 20200327
      var nos = "";
      $.each(ids, function (k, id) {
        var row = $("#dataTable").jqGrid('getRowData', id);
        nos = nos + "'" + $.trim(row.no) + "',";
        if ("1" == row.prjstatus || "2" == row.prjstatus || "6" == row.prjstatus) {
          isPrjManSend = true;
          return false;
        }
      });
      if (isPrjManSend) {
        art.dialog({content: "存在项目经理提交的或者未接收处理的打印单，不允许打印。",width: 200});
        return false;
      }
      if (nos != "") {
        nos = nos.substring(0, nos.length - 1);
      }
		  // console.log(nos);
      var reportName = "baseItemChgDetail_main.jasper";
      Global.Print.showPrint(reportName, {
        no: nos,
        LogoPath: "<%=basePath%>jasperlogo/",
        SUBREPORT_DIR: "<%=jasperPath%>"
      });
    }
    /**初始化表格*/
    $(function () {
      Global.JqGrid.initJqGrid("dataTable", {
        height: $(document).height() - $("#content-list").offset().top - 110,
        multiselect: true,
        styleUI: 'Bootstrap',
		colModel : [
		  {name: "documentno", index: "documentno", width: 70, label: "档案编号", sortable: true, align: "left"},
			{name: "no", index: "no", width: 80, label: "增减单号", sortable: true, align: "left"},
			{name: "custcode", index: "custcode", width: 70, label: "客户编号", sortable: true, align: "left"},
			{name: "customerdescr", index: "customerdescr", width: 70, label: "客户名称", sortable: true, align: "left"},
			{name: "address", index: "address", width: 140, label: "楼盘", sortable: true, align: "left"},
			{name: "statusdescr", index: "statusdescr", width: 95, label: "基装增减状态", sortable: true, align: "left"},
			{name: "status", index: "status", width: 95, label: "基装增减状态", sortable: true, align: "left",hidden:true},
			{name: "date", index: "date", width: 120, label: "变更日期", sortable: true, align: "left", formatter: formatTime},
			{name: "appczydescr", index: "appczydescr", width: 60, label: "申请人", sortable: true, align: "left"},
			{name: "confirmdate", index: "confirmdate", width: 120, label: "审核日期", sortable: true, align: "left", formatter: formatTime},
			{name: "confirmczydescr", index: "confirmczydescr", width: 60, label: "审核人", sortable: true, align: "left"},
			{name: "befamount", index: "befamount", width: 85, label: "优惠前金额", sortable: true, align: "right", sum: false},
			{name: "discamount", index: "discamount", width: 85, label: "优惠金额", sortable: true, align: "right", sum: false},
			{name: "amount", index: "amount", width: 85, label: "实际总价", sortable: true, align: "right", sum: false},
			{name: "managefee", index: "managefee", width: 85, label: "管理费", sortable: true, align: "right"},
			{name: "iscalperf", index: "iscalperf", width: 102, label: "是否计算业绩", sortable: true, align: "left",hidden:true},
			{name: "iscalperfdescr", index: "iscalperfdescr", width: 102, label: "是否计算业绩", sortable: true, align: "left"},
			{name: "remarks", index: "remarks", width: 120, label: "备注", sortable: true, align: "left"},
			{name: "lastupdate", index: "lastupdate", width: 130, label: "最后修改时间", sortable: true, align: "left", formatter: formatTime},
			{name: "lastupdatedby", index: "lastupdatedby", width: 80, label: "修改人", sortable: true, align: "left"},
			{name: "expired", index: "expired", width: 80, label: "是否过期", sortable: true, align: "left"},
			{name: "actionlog", index: "actionlog", width: 80, label: "操作", sortable: true, align: "left"},
			{name: "perfpk", index: "perfpk", width: 80, label: "perfpk", sortable: true, align: "left", hidden: true},
      		{name: "prjstatus", index: "prjstatus", width: 95, label: "项目经理状态", sortable: true, align: "left",hidden:true},
		]
      });
      $("#appCzy").openComponent_employee();
      $("#custCode").openComponent_customer({condition: {'status': '4', 'disabledEle': 'status_NAME'}});
    });
  </script>
</head>

<body>
<div class="body-box-list">
  <div class="panel panel-system">
    <div class="panel-body">
      <div class="btn-group-xs">
        <button type="button" id="saveBtn" class="btn btn-system " onclick="doPrint()">打印</button>
        <button type="button" id="closeBut" class="btn btn-system " onclick="closeWin()">关闭</button>
      </div>
    </div>
  </div>
  <div class="panel panel-info">
    <div class="panel-body">
      <form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
        <input type="hidden" id="expired" name="expired"
               value="${baseItemChg.expired}"/>
        <input type="hidden" name="jsonString" value=""/>
        <ul class="ul-form">
          <li>
            <label>楼盘</label>
            <input type="text" id="address"
                   name="address"
                   value="${baseItemChg.address}"/>
          </li>
		  <li>
            <label>增减单状态</label>
            <house:xtdmMulit  id="status" dictCode=""
              sql="select cbm SQL_VALUE_KEY,note SQL_LABEL_KEY from tXTDM where ID='ITEMCHGSTATUS'" selectedValue="2">
            </house:xtdmMulit>
          </li>
          <li>
            <label>变更日期</label>
            <input type="text" id="dateFrom"
                   name="dateFrom" class="i-date"
                   onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
                   value="<fmt:formatDate value='${baseItemChg.dateFrom}' pattern='yyyy-MM-dd'/>"/>
          </li>
          <li>
            <label>至</label>
            <input type="text" id="dateTo"
                   name="dateTo" class="i-date"
                   onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
                   value="<fmt:formatDate value='${baseItemChg.dateTo}' pattern='yyyy-MM-dd'/>"/>
          </li>
          <li class="search-group">
            <button type="button" class="btn  btn-sm btn-system "
                    onclick="goto_query();">查询
            </button>
            <button type="button" class="btn btn-sm btn-system "
                    onclick="clearForm();">清空
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


