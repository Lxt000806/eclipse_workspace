<%@ page language="java" import="com.house.framework.commons.utils.PathUtil" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://" + request.getServerName() + ":"+request.getServerPort()+path+"/";
String jasperPath = PathUtil.WEB_INF+"jsp/jasper/";
%>
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
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}"
          type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_customer.js?v=${v}"
          type="text/javascript"></script>
<script type="text/javascript">
	function clearForm() {
		$("#page_form input[type='text']").val('');
		$("#page_form select").val('');
		$("#remarks").val('');
		$("#status").val('');
		$("#confirmStatus").val('');
		$("#confirmStatus_NAME").val('');
		$.fn.zTree.getZTreeObj("tree_status").checkAllNodes(false);
		$.fn.zTree.getZTreeObj("tree_confirmStatus").checkAllNodes(false);
		$("#custType").val('');
		$.fn.zTree.getZTreeObj("tree_custType").checkAllNodes(false);
	}
	function add(itemType1) {
		Global.Dialog.showDialog("itemChgAdd", {
			title: "搜寻--客户编号",
			url: "${ctx}/admin/itemChg/goSave?itemType1=" + itemType1,
			height: 600,
			width: 1000,
			returnFun: goto_query
		});
	}

	function update() {
		var ret = selectDataTableRow();
		if (ret) {
			if (ret.statusdescr == '已申请') {
				Global.Dialog.showDialog("itemChgUpdate", {
					title: "材料增减—编辑",
					url: "${ctx}/admin/itemChg/goUpdate",
					postData: {
						'no': ret.no,
						'appCzyDescr': ret.appczydescr,
						'documentNo': ret.documentno,
						'address': ret.address,
						'confirmCzyDescr': ret.confirmczydescr,
						'statusDescr': ret.statusdescr,
						'custType': ret.custtype,
						'faultManDescr':ret.faultmandescr,
						'prjQualityFee':ret.prjqualityfee,
						'projectMan':ret.projectman,
						'projectManDescr':ret.projectmandescr
					},
					height: window.screen.height - 100,
					width: window.screen.width - 40,
					returnFun: goto_query
				});
			} else {
				art.dialog({
					content: "该增减项已审核或取消,不能编辑！"
				});
			}
		} else {
	        art.dialog({
	          content: "请选择一条记录"
	        });
		}
    }

    function copy() {
      var ret = selectDataTableRow();
      if (ret) {
        Global.Dialog.showDialog("itemChgCopy", {
          title: "复制ItemChg",
          url: "${ctx}/admin/itemChg/goSave?id=" + ret.No,
          height: 600,
          width: 1000,
          returnFun: goto_query
        });
      } else {
        art.dialog({
          content: "请选择一条记录"
        });
      }
    }

    function view() {
      var ret = selectDataTableRow();
      if (ret) {
        Global.Dialog.showDialog("itemChgDetail", {
          title: "材料增减—查看",
          url: "${ctx}/admin/itemChg/goView",
          postData: {
            'no': ret.no,
            'appCzyDescr': ret.appczydescr,
            'documentNo': ret.documentno,
            'address': ret.address,
            'confirmCzyDescr': ret.confirmczydescr,
            'statusDescr': ret.statusdescr,
            'custType': ret.custtype,
            'faultManDescr':ret.faultmandescr,
            'prjQualityFee':ret.prjqualityfee,
            'projectMan':ret.projectman,
            'projectManDescr':ret.projectmandescr
          },
          height: window.screen.height - 100,
          width: window.screen.width - 40,
        });

      } else {
        art.dialog({
          content: "请选择一条记录"
        });
      }
    }

    function del() {
      var url = "${ctx}/admin/itemChg/doDelete";
      beforeDel(url);
    }
    function goDetailQuery() {
      Global.Dialog.showDialog("itemChgDetailView", {
        title: "材料增减--明细查询",
        url: "${ctx}/admin/itemChg/goDetailQuery",
        height: window.screen.height - 100,
        width: window.screen.width - 40,
      });
    }
    //导出EXCEL
    function doExcel() {
      var url = "${ctx}/admin/itemChg/doExcel";
      doExcelAll(url);
    }
    //增减业绩
    function zjyj() {
      var ret = selectDataTableRow();
      if (ret) {
        if (ret.status.trim() != '2') {
          art.dialog({
            content: "只有审核状态的增减单才能设置是否计算业绩！"
          });
          return;
        }
        if (ret.perfpk != '') {
          art.dialog({
            content: "此增减单已计算业绩，不能进行此操作！"
          });
          return;
        }
        Global.Dialog.showDialog("itemChgZjyj", {
          title: "是否计算增减业绩",
          url: "${ctx}/admin/itemChg/goZjyj?no=" + ret.no + "&iscalPerf=" + ret.iscalperf,
          height: 600,
          width: 1000,
          returnFun: goto_query
        });
      } else {
        art.dialog({
          content: "请选择一条记录"
        });
      }
    }
    
	function plzjyj() {
        Global.Dialog.showDialog("itemChgPlzjyj", {
			title: "批量增减业绩",
			url: "${ctx}/admin/itemChg/goPlzjyj",
			height: 750,
			width: 1000,
			returnFun: goto_query
		});
	}
    
    //审核
    function confirm() {
      var ret = selectDataTableRow();
      if (ret) {
        if (ret.status.trim() != '1') {
          art.dialog({
            content: "该增减项已审核或取消,不能审核！"
          });
          return;
        }

        Global.Dialog.showDialog("itemChgConfirm", {
          title: "材料增减--审核",
          url: "${ctx}/admin/itemChg/goConfirm",
          postData: {
            'no': ret.no,
            'appCzyDescr': ret.appczydescr,
            'documentNo': ret.documentno,
            'address': ret.address,
            'confirmCzyDescr': ret.confirmczydescr,
            'statusDescr': ret.statusdescr,
            'custType': ret.custtype,
            'faultManDescr':ret.faultmandescr,
            'prjQualityFee':ret.prjqualityfee,
            'projectMan':ret.projectman,
            'projectManDescr':ret.projectmandescr
          },
          height: window.screen.height - 100,
          width: window.screen.width - 40,
          returnFun: goto_query
        });
      } else {
        art.dialog({
          content: "请选择一条记录"
        });
      }
    }
    //打印
    function doPrint() {
      var row = selectDataTableRow();
      if (!row) {
        art.dialog({content: "请选择一条记录进行打印操作！", width: 200});
        return false;
      }
      if (row.itemtype1.trim() == 'RZ' || row.itemtype1.trim() == 'ZC') {
        Global.Dialog.showDialog("itemChgConfirm", {
          title: "材料增减--打印",
          url: "${ctx}/admin/itemChg/goPrint" + row.itemtype1.trim(),
          postData: {'no': row.no},
          height: 320,
          resizable: false,
          width: 500
        });
        return false;
      }

      var reportName = "itemChgDetail_" + row.itemtype1.trim() + ".jasper";
      Global.Print.showPrint(reportName, {
        No: row.no,
        LogoFile: "<%=basePath%>jasperlogo/logo.jpg",
        SUBREPORT_DIR: "<%=jasperPath%>"
      });
    }
    //批量打印
    function doAllPrint() {
      //查看窗口
      Global.Dialog.showDialog("itemChgView", {
        title: "增减单--批量打印",
        url: "${ctx}/admin/itemChg/goQPrint",
        height: 700,
        width: 1200
      });
    }
    /**初始化表格*/
    $(function () {
		//初始化材料类型1，2，3三级联动
      Global.LinkSelect.initSelect("${ctx}/admin/item/itemTypeByAuthority", "itemType1", "itemType2");
      Global.JqGrid.initJqGrid("dataTable", {
        url: "${ctx}/admin/itemChg/goJqGrid",
        height: $(document).height() - $("#content-list").offset().top - 100,
        styleUI: 'Bootstrap',
		colModel : [
		  {name: "documentno", index: "documentno", width: 65, label: "档案编号", sortable: true, align: "left",},
		  {name: "no", index: "no", width: 100, label: "增减单号", sortable: true, align: "left"},
		  {name: "itemtype1descr", index: "itemtype1descr", width: 75, label: "材料类型1", sortable: true, align: "left"},
		  {name: "itemtype1", index: "itemtype1", width: 62, label: "材料类型1", sortable: true, align: "left",hidden:true},
		  {name: "custcode", index: "custcode", width: 70, label: "客户编号", sortable: true, align: "left"},
		  {name: "customerdescr", index: "customerdescr", width: 80, label: "客户名称", sortable: true, align: "left"},
		  {name: "custtypedescr", index: "custtypedescr", width: 80, label: "客户类型", sortable: true, align: "left"},
		  {name: "custtype", index: "custtype", width: 80, label: "客户类型", sortable: true, align: "left",hidden:true},
		  {name: "address", index: "address", width: 210, label: "楼盘", sortable: true, align: "left"},
		  {name: "statusdescr", index: "statusdescr", width: 90, label: "材料增减状态", sortable: true, align: "left"},
		  {name: "confirmstatusdescr", index: "confirmstatusdescr", width: 75, label: "审核状态", sortable: true, align: "left"},
		  {name: "status", index: "status", width: 95, label: "材料增减状态", sortable: true, align: "left",hidden:true},
		  {name: "isservicedescr", index: "isservicedescr", width: 98, label: "服务性产品", sortable: true, align: "left"},
		  {name: "iscupboarddescr", index: "iscupboarddescr", width: 75, label: "是否橱柜", sortable: true, align: "left"},
		  {name: "date", index: "date", width: 135, label: "变更日期", sortable: true, align: "left", formatter: formatTime},
		  {name: "befamount", index: "befamount", width: 85, label: "优惠前金额", sortable: true, align: "right", sum: true},
		  {name: "discamount", index: "discamount", width: 85, label: "优惠金额", sortable: true, align: "right", sum: true},
		  {name: "amount", index: "amount", width: 85, label: "实际总价", sortable: true, align: "right", sum: true},
		  {name: "managefee", index: "managefee", width: 85, label: "管理费", sortable: true, align: "right"},
		  {name: "tax", index: "tax", width: 60, label: "税金", sortable: true, align: "right"},
		  {name: "designmandescr", index: "designmandescr", width: 85, label: "设计师", sortable: true, align: "right"},
		  {name: "designdept2", index: "designdept2", width: 85, label: "设计所", sortable: true, align: "right"},
		  {name: "remarks", index: "remarks", width: 120, label: "备注", sortable: true, align: "left"},
		  {name: "exceptionremarks", index: "exceptionremarks", width: 120, label: "审核说明", sortable: true, align: "left"},
		  {name: "appczydescr", index: "appczydescr", width: 103, label: "申请人", sortable: true, align: "left"},
		  {name: "confirmczydescr", index: "confirmczydescr", width: 106, label: "审核人", sortable: true, align: "left"},
		  {name: "confirmdate", index: "confirmdate", width: 107, label: "审核日期", sortable: true, align: "left", formatter: formatTime},
		  {name: "iscalperf", index: "iscalperf", width: 102, label: "是否计算业绩", sortable: true, align: "left",hidden:true},
		  {name: "iscalperfdescr", index: "iscalperfdescr", width: 102, label: "是否计算业绩", sortable: true, align: "left"},
		  {name: "designemp", index: "designemp", width: 102, label: "软装设计师", sortable: true, align: "left"},
		  {name: "lastupdate", index: "lastupdate", width: 130, label: "最后修改时间", sortable: true, align: "left", formatter: formatTime},
		  {name: "lastupdatedby", index: "lastupdatedby", width: 80, label: "修改人", sortable: true, align: "left"},
		  {name: "expired", index: "expired", width: 80, label: "是否过期", sortable: true, align: "left"},
		  {name: "actionlog", index: "actionlog", width: 80, label: "操作", sortable: true, align: "left"},
		  {name: "perfpk", index: "perfpk", width: 80, label: "", sortable: true, align: "left",hidden:true},
		  {name: "faultmandescr", index: "faultmandescr", width: 80, label: "责任人", sortable: true, align: "left",hidden:true},
		  {name: "prjqualityfee", index: "prjqualityfee", width: 80, label: "项目经理质保金", sortable: true, align: "left",hidden:true},
		  {name: "projectman", index: "projectman", width: 80, label: "项目经理", sortable: true, align: "left",hidden:true},
		  {name: "projectmandescr", index: "projectmandescr", width: 80, label: "项目经理", sortable: true, align: "left",hidden:true},
		],

        ondblClickRow: function () {
          view();
        }
      });
      $("#appCzy").openComponent_employee();
      $("#custCode").openComponent_customer({
        showLabel: "${itemChg.descr}",
        showValue: "${itemChg.custCode}",
        condition: {'status': '4', 'disabledEle': 'status_NAME', mobileHidden: "1"}
      });
      onCollapse(44);
    });
    function goto_query() {
      var datas = $("#page_form").jsonForm();
      if (datas.itemDescr && !datas.custCode) {
        art.dialog({
          content: "请先选择客户编号再进行材料名称查询！"
        });
        return;
      }
      $(".s-ico").css("display", "none");
      $("#dataTable").jqGrid("setGridParam", {postData: datas, page: 1, sortname: ''}).trigger("reloadGrid");
    }
    
  </script>
</head>

<body>
<div class="body-box-list">
  <div class="query-form">
    <form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
      <input type="hidden" id="expired" name="expired" value="${itemChg.expired}"/>
      <input type="hidden" name="jsonString" value=""/>
      <ul class="ul-form">
        <li>
          <label>客户编号</label>
          <input type="text"
                 id="custCode" name="custCode"
                 value="${itemChg.custCode}"/>
        </li>
        <li>
          <label>楼盘</label>
          <input type="text" id="address"
                 name="address"
                 value="${itemChg.address}"/>
        </li>
       	<li>
			<label>客户类型</label>
			<house:custTypeMulit id="custType" selectedValue="${itemChg.custType }"  ></house:custTypeMulit>
		</li>
        <li>
          <label>增减单状态</label>
          <house:xtdmMulit
            id="status" dictCode=""
            sql="select cbm SQL_VALUE_KEY,note SQL_LABEL_KEY from tXTDM where ID='ITEMCHGSTATUS'"
            selectedValue="1,4"></house:xtdmMulit>
        </li>
         <li>
          <label>审核状态</label>
          <house:xtdmMulit
            id="confirmStatus" dictCode=""
            sql="select cbm SQL_VALUE_KEY,note SQL_LABEL_KEY from tXTDM where ID='ITEMCHGCONFSTAT'" ></house:xtdmMulit>
        </li>
        <li>
          <label>材料类型1</label>

          <select id="itemType1" name="itemType1"></select>
        </li>
        <li>
          <label>增减单号</label>
          <input type="text" id="no"
                 name="no"
                 value="${itemChg.no}"/>
        </li>
        <li>
          <label>变更日期</label>
          <input type="text" id="dateFrom"
                 name="dateFrom" class="i-date"
                 onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
                 value="<fmt:formatDate value='${itemChg.dateFrom}' pattern='yyyy-MM-dd'/>"/>
        </li>
        <li>
          <label>至</label>
          <input type="text" id="dateTo"
                 name="dateTo" class="i-date"
                 onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
                 value="<fmt:formatDate value='${itemChg.dateTo}' pattern='yyyy-MM-dd'/>"/>
        </li>

        <li id="loadMore">
          <button data-toggle="collapse" class="btn btn-sm btn-link " data-target="#collapse">更多</button>
          <button type="button" class="btn btn-sm btn-system " onclick="goto_query();">查询</button>
          <button type="button" class="btn btn-sm btn-system " onclick="clearForm();">清空</button>
        </li>
        <div class="collapse " id="collapse">
          <ul>
            <li>
              <label>审核日期</label>
              <input type="text" id="confirmDateFrom"
                     name="confirmDateFrom" class="i-date"
                     onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
                     value="<fmt:formatDate value='${itemChg.confirmDateFrom}' pattern='yyyy-MM-dd'/>"/>
            </li>
            <li>
              <label>至</label>
              <input type="text" id="confirmDateTo"
                     name="confirmDateTo" class="i-date"
                     onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
                     value="<fmt:formatDate value='${itemChg.confirmDateTo}' pattern='yyyy-MM-dd'/>"/>
            </li>
            <li>
              <label>申请人员</label>
              <input type="text"
                     id="appCzy" name="appCzy"
                     value="${itemChg.appCzy}"/>
            </li>
            <li>
              <label>材料名称</label>
              <input type="text"
                     id="itemDescr" name="itemDescr"
                />
            </li>
            <li>
              <label>一级部门</label>

              <house:department1 id="department1" onchange="changeDepartment1(this,'department2','${ctx }')"
                                 value="${czybm.department1 }"></house:department1>
            </li>
            <li>
				<label>是否服务性产品</label>
				<house:xtdm id="isService" dictCode="YESNO" ></house:xtdm>                     
			</li>
			<li><label>客户结算状态</label> <house:xtdmMulit id="checkStatus" dictCode="CheckStatus" ></house:xtdmMulit>
			<li>
				<label>结束代码</label>
				<house:xtdmMulit id="endCode" dictCode="CUSTOMERENDCODE" ></house:xtdmMulit>
			</li>
            <li>
              <label class="control-textarea">备注</label>
              <textarea id="remarks" name="remarks">${itemChg.remarks }</textarea>
            </li>

            <li class="search-group-shrink">
              <button data-toggle="collapse" class="btn btn-sm btn-link " data-target="#collapse">收起</button>
              <input type="checkbox" id="expired_show" name="expired_show"
                     value="${itemChg.expired}" onclick="hideExpired(this)"
                     ${itemChg.expired!='T' ?'checked':'' }/><p>隐藏过期</p>
              <button type="button" class="btn  btn-sm btn-system " onclick="goto_query();">查询</button>
              <button type="button" class="btn btn-sm btn-system " onclick="clearForm();">清空</button>
            </li>
          </ul>
        </div>
      </ul>
    </form>
  </div>
  <div class="btn-panel">

    <div class="btn-group-sm">
      <house:authorize authCode="ITEMCHG_ZCZJ">
        <button type="button" class="btn btn-system " onclick="add('ZC')">主材增减</button>
      </house:authorize>
      <house:authorize authCode="ITEMCHG_RZZJ">
        <button type="button" class="btn btn-system " onclick="add('RZ')">软装增减</button>
      </house:authorize>
      <house:authorize authCode="ITEMCHG_JCZJ">
        <button type="button" class="btn btn-system " onclick="add('JC')">集成增减</button>
      </house:authorize>
      <house:authorize authCode="ITEMCHG_UPDATE">
        <button type="button" class="btn btn-system " onclick="update()">编辑</button>
      </house:authorize>
      <house:authorize authCode="ITEMCHG_AUDIT">
        <button type="button" class="btn btn-system " onclick="confirm()">审核</button>
      </house:authorize>
      <house:authorize authCode="ITEMCHG_ZJYJ">
        <button type="button" class="btn btn-system " onclick="zjyj()">增减业绩</button>
      </house:authorize>
      <house:authorize authCode="ITEMCHG_PLZJYJ">
        <button type="button" class="btn btn-system " onclick="plzjyj()">批量增减业绩</button>
      </house:authorize>
      <house:authorize authCode="ITEMCHG_VIEW">
        <button type="button" class="btn btn-system " onclick="view()">查看</button>
      </house:authorize>
      <house:authorize authCode="ITEMCHG_DETAILVIEW">
        <button type="button" class="btn btn-system " onclick="goDetailQuery()">明细查询</button>
      </house:authorize>
        <button type="button" class="btn btn-system " onclick="doPrint()">打印</button>
        <button type="button" class="btn btn-system " onclick="doAllPrint()">批量打印</button>
      <house:authorize authCode="ITEMCHG_EXCEL">
        <button type="button" class="btn btn-system " onclick="doExcel()">导出excel</button>
      </house:authorize>
    </div>
  </div>
  <div id="content-list">
    <table id="dataTable"></table>
    <div id="dataTablePager"></div>
  </div>
</div>
</body>
</html>


