<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>
<%
UserContext uc = (UserContext)request.getSession().getAttribute(CommonConstant.USER_CONTEXT_KEY);
String czybh=uc.getCzybh();
%>
<!DOCTYPE html>
<html>
<head>
  <title>套餐包明细</title>
  <META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8"/>
  <META HTTP-EQUIV="pragma" CONTENT="no-cache"/>
  <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"/>
  <META HTTP-EQUIV="expires" CONTENT="0"/>
  <META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge"/>
  <%@ include file="/commons/jsp/common.jsp" %>
  <script type="text/javascript">
    function goto_query(table, form, event) {
      var datas = $("#" + form).jsonForm();
      datas.no = datas.itemSetNo;
      datas.custCode="${itemChg.custCode}";
      $("#" + table).jqGrid("setGridParam", {
        url: "${ctx}/admin/itemSetDetail/goJqGrid",
        postData: datas,
        page: 1
      }).trigger("reloadGrid");
    }
    
    function goto_query1(itemCode,itemDescr,event) {
      var datas = $("#tab1_page_form").jsonForm();
      datas.no = datas.itemSetNo;
      datas.itemcode=itemCode;
      datas.itemDescr=itemDescr;
      $("#itemSetDetaildataTable").jqGrid("setGridParam", {
        url: "${ctx}/admin/itemSetDetail/goJqGrid",
        postData: datas,
        page: 1
      }).trigger("reloadGrid");
    }
    
    
    //tab分页
    $(document).ready(function () {
      var itemType1 = '${itemChg.itemType1}'.trim();
      var id_detailW = window.parent.document.getElementById("id_detail").style.width.substring(0, 4);
//初始化套餐包
      Global.LinkSelect.initOption("itemSetNo", "${ctx}/admin/itemSet/itemSetNo", {
        itemType1: itemType1,
        custType: '${itemChg.custType}'
      });
      $("#itemSet").css('width', id_detailW);
      //初始化表格
      Global.JqGrid.initJqGrid("itemSetDetaildataTable", {
        height: 185,
        styleUI: 'Bootstrap',
		colModel : [
		  {name: "itemsetno", index: "itemsetno", width: 118, label: "套餐包 ", sortable: true, align: "left", hidden: true},
		  {name: "itemsetdescr", index: "itemsetdescr", width: 118, label: "套餐包 ", sortable: true, align: "left", hidden: true},
		  {name: "itemcode", index: "itemcode", width: 118, label: "材料编号 ", sortable: true, align: "left"},
		  {name: "itemdescr", index: "itemdescr", width: 241, label: "材料名称", sortable: true, align: "left"},
		  {name: "itemtype2descr", index: "itemtype2descr", width: 85, label: "材料类型2", sortable: true, align: "left", hidden: true},
		  {name: "itemtype2", index: "itemtype2", width: 85, label: "材料类型2", sortable: true, align: "left", hidden: true},
		  {name: "itemtype3descr", index: "itemtype3descr", width: 85, label: "材料类型3", sortable: true, align: "left", hidden: true},
		  {name: "uomdescr", index: "uomdescr", width: 60, label: "单位", sortable: true, align: "left"},
		  {name: "price", index: "price", width: 80, label: "单价", sortable: true, align: "right"},
		  {name: "setunitprice", index: "setunitprice", width: 80, label: "套餐单价", sortable: true, align: "right"},
		  {name: "remark", index: "remark", width: 180, label: "材料描述", sortable: true, align: "left"},
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

          var rowData = $("#itemSetDetaildataTable").jqGrid('getRowData', rowid);
          rowData.fixareapk = fixAreaPk;
          rowData.fixareadescr = fixAreaDescr;
          rowData.intprodpk = intProdPk;
          rowData.intproddescr = intProdDescr;
          rowData.unitprice = rowData.price;
          rowData.qty = 0;
          rowData.beflineamount = 0;
          rowData.markup = myRound(rowData.setunitprice / rowData.price * 100, 2);
          rowData.tmplineamount = 0;
          rowData.processcost = 0;
          rowData.lineamount = 0;
          //默认套餐外材料
          rowData.isoutsetdescr = "是";
          rowData.isoutset = "1";
          //预算管理
          rowData.projectqty = 0;
          rowData.remarks=rowData.remark;
          $(window.parent.document.getElementById("itemChgDetailDataTable")).addRowData(rowNo, rowData, "last");
          window.parent.document.getElementById("rowNo").value++;


        }

      });
      return false;
      
    });
    /* $(function(){
	    $("#itemcode").keydown(function(event){
			if(event.keyCode == 13){
				goto_query('itemSetDetaildataTable','tab1_page_form',this);
			}
		});
		$("#itemDescr").keydown(function(event){
			if(event.keyCode == 13){
				goto_query('itemSetDetaildataTable','tab1_page_form',this);
			}
		});
    }); */
  </script>


</head>

<body>
<div>
  <div id="itemSet" style="float: right;">
    <div>
      <div id="tab1" class="tab_content" style="display: block; ">
        <div class="edit-form">
          <form role="form" class="form-search" action="" method="post" id="tab1_page_form">
            <house:token></house:token>
            <ul class="ul-form">
              <li>

                <label>批次编号</label>
                <select type="text" id="itemSetNo" name="itemSetNo"
                        onchange="goto_query('itemSetDetaildataTable','tab1_page_form',this)"></select>
              </li>
              <!-- <li>
                <label>材料编号</label>
                <input type="text" id="itemcode" name="itemcode"
                        onchange="goto_query('itemSetDetaildataTable','tab1_page_form',this)"></input>
              </li>
              <li>
                <label>材料名称</label>
                <input type="text" id="itemDescr" name="itemDescr"
                        onchange="goto_query('itemSetDetaildataTable','tab1_page_form',this)"></input>
              </li> -->
            </ul>
          </form>
        </div>
        <div class="clear_float"></div>
        <!--query-form-->
        <div class="pageContent">
          <div id="content-list1">
            <table id="itemSetDetaildataTable"></table>
            <div id="itemSetDetaildataTablePager"></div>

          </div>
        </div>


      </div>

    </div>
  </div>
</div>
</body>
</html>
