<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8"/>
  <META HTTP-EQUIV="pragma" CONTENT="no-cache"/>
  <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"/>
  <META HTTP-EQUIV="expires" CONTENT="0"/>
  <META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge"/>
  <title>搜寻--客户编号</title>
  <%@ include file="/commons/jsp/common.jsp" %>

  <script type="text/javascript">
    $(function () {
      $("#status_NAME").attr("disabled", "disabled");
      //初始化表格
      Global.JqGrid.initJqGrid("dataTable", {
        url: "${ctx}/admin/customer/goJqGrid?status=4",
        height: $(document).height() - $("#content-list").offset().top - 70,
        styleUI: 'Bootstrap',
		colModel : [
		  {name : 'code',index : 'code',width : 70,label:'客户编号',sortable : true,align : "left"},
		  {name : 'isinitsign',index : 'isinitsign',width : 70,label:'草签',sortable : true,align : "left", hidden:true},
		  {name : 'descr',index : 'descr',width : 60,label:'客户名称',sortable : true,align : "left"},
		  {name : 'custtype',index : 'custtype',width : 100,label:'客户类型',sortable : true,align : "left",hidden:true},
		  {name : 'saletype',index : 'saletype',width : 200,label:'销售类型',sortable : true,align : "left",hidden:true},
		  {name : 'custtypedescr',index : 'custtypedescr',width : 70,label:'客户类型',sortable : true,align : "left"},
		  {name : 'address',index : 'address',width : 200,label:'楼盘',sortable : true,align : "left"},
		  {name : 'layoutdescr',index : 'layoutdescr',width : 40,label:'户型',sortable : true,align : "left"},
		  {name : 'area',index : 'area',width : 40,label:'面积',sortable : true,align : "right"},
		  {name : 'statusdescr',index : 'statusdescr',width : 70,label:'客户状态',sortable : true,align : "left"},
		  {name : 'checkstatusdescr',index : 'checkstatusdescr',width : 85,label:'客户结算状态',sortable : true,align : "left"},
		  {name : 'custcheckdate',index : 'custcheckdate',width : 85,label:'客户结算时间',sortable : true,align : "left",formatter: formatTime},
		  {name : 'designmandescr',index : 'designmandescr',width : 50,label:'设计师',sortable : true,align : "left"},
		  {name : 'businessmandescr',index : 'businessmandescr',width : 50,label:'业务员',sortable : true,align : "left"},
		  {name : 'projectman',index : 'projectman',width : 60,label:'项目经理',sortable : true,align : "left",hidden:true},
		  {name : 'projectmandescr',index : 'projectmandescr',width : 60,label:'项目经理',sortable : true,align : "left"},
		  {name : 'projectmanphone',index : 'projectmanphone',width : 200,label:'项目经理电话',sortable : true,align : "left",hidden:true}
		],

        ondblClickRow: function (rowid, iRow, iCol, e) {
          var selectRow = $("#dataTable").jqGrid('getRowData', rowid);
          
          if (selectRow.isinitsign === '1') {
          		art.dialog({
          			content:"草签楼盘无法进行操作！",
          		});
          		return
             /*  art.dialog({
		        content:"该楼盘当前是草签状态，是否继续？",
		        lock: true,
		        width: 300,
		        height: 60,
		        ok: function() {
                    addItemChg(selectRow)
		        
		            return true;
		        },
		        cancel: function() {
		            return true;
		        }
		    }) */
		    
          } else {
              addItemChg(selectRow)
          }
          
        }
        
      });

    });
    
    function addItemChg(selectRow) {
    
        $.ajax({
		    url: '${ctx}/admin/itemCheck/isCheckItem',
		    type: 'post',
		    data: {'custCode': selectRow.code, 'itemType1': $("#itemType1").val()},
		    dataType: 'json',
		    cache: false,
		    success: function (obj) {
		        if (obj) {
		            art.dialog({
		                content: "材料已进行结算，不能再做增减。"
		            });
		            return;
		        }
		        $.ajax({
		            url: '${ctx}/admin/itemChg/isAllowChg',
		            type: 'post',
		            data: {'code': selectRow.code, 'itemType1': $("#itemType1").val(), 'saleType': selectRow.saletype},
		            dataType: 'json',
		            cache: false,
		            success: function (obj) {
		                var itemType1Descr;
		                if ('${itemType1}' == 'ZC') itemType1Descr = '主材';
		                if ('${itemType1}' == 'RZ') itemType1Descr = '软装';
		                if ('${itemType1}' == 'JC') itemType1Descr = '集成';
		                var title = itemType1Descr + "增减";
		                if (obj) {
		                    Global.Dialog.showDialog("itemChg_Add", {
		                        title: title,
		                        url: "${ctx}/admin/itemChg/goItemChgAdd",
		                        postData: {
		                            'custCode': selectRow.code,
		                            'address': selectRow.address,
		                            'custType': selectRow.custtype,
		                            'itemType1': '${itemType1}'
		                        },
		                        height: window.screen.height - 100,
		                        width: window.screen.width - 40,
		                        returnFun: function () {
		                            setTimeout(function () {
		                                closeWin();
		                            }, 100);
		                        }
		                    });
		
		                } else {
		                    art.dialog({
		                        content: "一次销售客户，未含" + itemType1Descr + "，签单超过规定时间不允许做增减！"
		                    });
		                }
		            }
		        });
		    }
		});
		
    }

    function clearForm() {
      $("#page_form input[type='text'][id!='status_NAME']").val('');
      $("#page_form select").val('');
    }
  </script>
</head>

<body>
<div class="body-box-list">
  <div class="query-form">
    <input id="itemType1" name="itemType1" value="${itemType1}" style="display:none"/>
    <form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
      <ul class="ul-form">
        <li>
          <label>客户名称</label>
          <input type="text" id="descr" name="descr" value="${customer.descr }"/>
        </li>
        <li>
          <label>楼盘</label>
          <input type="text" id="address" name="address" value="${customer.address }"/>
        </li>
        <li>
          <label>项目经理</label>
          <input type="text" id="projectManDescr" name="projectManDescr" value="${customer.projectManDescr }"/>
        </li>
        <li>
          <label>客户状态</label>
          <house:xtdmMulit id="status" dictCode=""
                           sql="select cbm SQL_VALUE_KEY,note SQL_LABEL_KEY from tXTDM where ID='CUSTOMERSTATUS' and CBM='4' "
                           selectedValue="4"></house:xtdmMulit>
        </li>
        <li>
          <label id="mobile">电话</label>

          <input type="text" id="mobile1" name="mobile1" value="${customer.mobile1 }"/>
        </li>
        <li id="loadMore">
          <button type="button" class="btn  btn-system btn-sm" onclick="goto_query();">查询</button>
          <button type="button" class="btn btn-system btn-sm" onclick="clearForm();">清空</button>
        </li>


      </ul>
    </form>
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


