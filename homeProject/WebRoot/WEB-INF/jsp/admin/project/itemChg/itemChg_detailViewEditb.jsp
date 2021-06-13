<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <title>增减明细--编辑</title>
  <META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8"/>
  <META HTTP-EQUIV="pragma" CONTENT="no-cache"/>
  <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"/>
  <META HTTP-EQUIV="expires" CONTENT="0"/>
  <META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge"/>
  <%@ include file="/commons/jsp/common.jsp" %>
  <script src="${resourceRoot}/pub/component_item.js?v=${v}"
          type="text/javascript"></script>
  <script src="${resourceRoot}/pub/component_fixArea.js?v=${v}"
          type="text/javascript"></script>
  <script src="${resourceRoot}/pub/component_custTypeItem.js?v=${v}"
          type="text/javascript"></script>
  <script type="text/javascript">
    var ret;
    var unitprice;
    function change(name) {
      switch (name) {
        case 'qty':
          ret.qty = $("#qty").val();
          var beflineamount = myRound(ret.qty * ret.unitprice, 4);
          var tmplineamount = myRound(myRound(ret.qty * ret.unitprice, 4) * ret.markup / 100);
          var lineamount = myRound(parseFloat(myRound(ret.qty * ret.unitprice, 4) * ret.markup / 100) + parseFloat(ret.processcost));
          $("#beflineamount").val(beflineamount);
          $("#tmplineamount").val(tmplineamount);
          $("#lineamount").val(lineamount);
          ret.beflineamount = beflineamount;
          ret.tmplineamount = tmplineamount;
          ret.lineamount = lineamount;
          break;
        case 'processcost':
          ret.processcost = parseFloat($("#processcost").val());
          var lineamount = myRound(parseFloat(ret.tmplineamount) + ret.processcost);
          $("#lineamount").val(lineamount);
          ret.lineamount = lineamount;
          break;
        case 'unitprice':
          var beflineamount = myRound(ret.qty * ret.unitprice, 4);
          var tmplineamount = myRound(myRound(ret.qty * ret.unitprice, 4) * ret.markup / 100);
          var lineamount = myRound(parseFloat(myRound(ret.qty * ret.unitprice, 4) * ret.markup / 100) + parseFloat(ret.processcost));
          $("#beflineamount").val(beflineamount);
          $("#tmplineamount").val(tmplineamount);
          $("#lineamount").val(lineamount);
          ret.beflineamount = beflineamount;
          ret.tmplineamount = tmplineamount;
          ret.lineamount = lineamount;
          break;
        default:
          ret.remarks = $("#remarks").val();
      }
    }
    function save() {
      if (!$("#dataForm").data('bootstrapValidator').isValid()) return;
      var datas = $("#dataForm").jsonForm();
      ret.fixareapk = datas.openComponent_fixArea_fixareapk.substring(0, datas.openComponent_fixArea_fixareapk.indexOf("|"));
      ret.fixareadescr = datas.openComponent_fixArea_fixareapk.substring(datas.openComponent_fixArea_fixareapk.indexOf("|") + 1);
      //ret.itemcode=datas.openComponent_item_itemcode.substring(0,datas.openComponent_item_itemcode.indexOf("|"));
      //ret.itemdescr=datas.openComponent_item_itemcode.substring(datas.openComponent_item_itemcode.indexOf("|")+1);
      Global.Dialog.returnData = ret;
      closeWin();

    }

    //校验函数
    $(function () {
      if (top.$("#iframe_itemChg_Add")[0]) {
        var topFrame = "#iframe_itemChg_Add";
      } else if (top.$("#iframe_itemChgConfirm")[0]) {
        var topFrame = "#iframe_itemChgConfirm";
      } else {
        var topFrame = "#iframe_itemChgUpdate";
      }
      ret = $(top.$(topFrame)[0].contentWindow.document.getElementById("dataTable")).jqGrid("getRowData", ${itemChg.rowId});
      if (ret.isoutset != "1") {
        $("#itemLi").hide();
      } else {
        $("#custTypeItemLi").hide();
      }
      unitprice =${itemChg.amount};
      $("#unitprice").val(ret.unitprice);
      if ('${itemChg.reqPk}') {
        if (ret.isoutset != "1") {
          $("#custtypeitempk").openComponent_custTypeItem({
            showValue: ret.custtypeitempk + "|" + ret.itemdescr,
            'readonly': true,
            condition: {'itemType1': '${itemChg.itemType1}', 'disabledEle': 'itemType1'}
          });
        } else {
          $("#itemcode").openComponent_item({
            showValue: ret.itemcode + "|" + ret.itemdescr,
            'readonly': true,
            condition: {'itemType1': '${itemChg.itemType1}', 'disabledEle': 'itemType1','custCode': '${itemChg.custCode}'},
            callBack: function () {
              validateRefresh('openComponent_item_itemcode');
            }
          });
        }
        $("#fixareapk").openComponent_fixArea({
          condition: {
            isService: '${itemChg.isService}',
            custCode: '${itemChg.custCode}',
            itemType1: '${itemChg.itemType1}'
          }, showValue: ret.fixareapk + "|" + ret.fixareadescr, 'readonly': true
        });
        $("#remarks").attr("readonly", "readonly");
      } else {
        if (ret.isoutset != "1") {
          $("#custtypeitempk").openComponent_custTypeItem({
            showValue: ret.custtypeitempk + "|" + ret.itemdescr,
            condition: {
              'itemType1': '${itemChg.itemType1}',
              'custType': '${itemChg.custType}',
              'disabledEle': 'itemType1'
            },
            callBack: function (data) {
              validateRefresh('openComponent_custTypeItem_custtypeitempk');
              selectItem(data);
            }
          });
        } else {
          $("#itemcode").openComponent_item({
            showValue: ret.itemcode + "|" + ret.itemdescr,
            condition: {'itemType1': '${itemChg.itemType1}', 'disabledEle': 'itemType1','custCode': '${itemChg.custCode}'},
            callBack: function (data) {
              validateRefresh('openComponent_item_itemcode');
              selectItem(data);
            }
          });
        }

        $("#fixareapk").openComponent_fixArea({
          condition: {
            isService: '${itemChg.isService}',
            custCode: '${itemChg.custCode}',
            itemType1: '${itemChg.itemType1}'
          }, showValue: ret.fixareapk + "|" + ret.fixareadescr, callBack: function () {
            validateRefresh('openComponent_fixArea_fixareapk');
          }
        });
      }
      $("#isoutset").val(ret.isoutset);
      $("#qty").val(ret.qty);
      $("#beflineamount").val(ret.beflineamount);
      $("#tmplineamount").val(ret.tmplineamount);
      $("#processcost").val(ret.processcost);
      $("#markup").val(ret.markup);
      $("#lineamount").val(ret.lineamount);
      $("#remarks").val(ret.remarks);
      //if(!ret.reqpk&&'${itemChg.isOutSet}'=='2')  $("#isoutset").removeAttr("disabled");
      //if(ret.isoutset=='0')  $("#unitprice").val(0);
      $("#dataForm").bootstrapValidator({
        message: 'This value is not valid',
        feedbackIcons: {
          /*input状态样式图片*/

          validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
          openComponent_fixArea_fixareapk: {
            validators: {
              notEmpty: {
                message: '区域不能为空'
              },
              remote: {
                message: '',
                url: '${ctx}/admin/fixArea/getFixArea',
                data: getValidateVal,
                delay: 4 //这里特别要说明，必须要加此属性，否则用户输入一个字就会访问后台一次，会消耗大量的系统资源，
              }
            }
          },

          openComponent_item_itemcode: {
            validators: {
              notEmpty: {
                message: '材料编号不能为空'
              },
              callback: {
                message: '请输入有效的材料编号'
              }
            }
          },
          openComponent_custTypeItem_custtypeitempk: {
            validators: {
              notEmpty: {
                message: '套餐材料信息编号不能为空'
              },
              callback: {
                message: '请输入有效的套餐材料信息编号'
              }
            }
          }
        },
        submitButtons: 'input[type="submit"]'/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
      }).on('success.form.bv', function (e) {
        e.preventDefault();
        save();

      });
    });
    function updateIsOutSet(event) {
      ret.isoutset = event.value;
      if (event.value == '1') {
        ret.isoutsetdescr = '是';
        ret.unitprice = unitprice;
        var beflineamount = myRound(ret.qty * ret.unitprice, 4);
        var tmplineamount = myRound(myRound(ret.qty * ret.unitprice, 4) * ret.markup / 100);
        var lineamount = myRound(parseFloat(myRound(ret.qty * ret.unitprice, 4) * ret.markup / 100) + parseFloat(ret.processcost));
        $("#beflineamount").val(beflineamount);
        $("#tmplineamount").val(tmplineamount);
        $("#lineamount").val(lineamount);
        $("#unitprice").val(unitprice);
        ret.beflineamount = beflineamount;
        ret.tmplineamount = tmplineamount;
        ret.lineamount = lineamount;

      }
      else {
        ret.isoutsetdescr = '否';
//         ret.unitprice=0;
//         $("#unitprice").val(0);
        var beflineamount = myRound(ret.qty * ret.unitprice, 4);
        var tmplineamount = myRound(myRound(ret.qty * ret.unitprice, 4) * ret.markup / 100);
        var lineamount = myRound(parseFloat(myRound(ret.qty * ret.unitprice, 4) * ret.markup / 100) + parseFloat(ret.processcost));
        $("#beflineamount").val(beflineamount);
        $("#tmplineamount").val(tmplineamount);
        $("#lineamount").val(lineamount);
        ret.beflineamount = beflineamount;
        ret.tmplineamount = tmplineamount;
        ret.lineamount = lineamount;
      }
    }
    function selectItem(data) {
      ret.itemcode = data.code;
      ret.itemdescr = data.descr;
      ret.itemtype2descr = data.itemtype2descr;
      ret.itemtype2 = data.itemtype2;
      ret.itemtype3descr = data.itemtype3descr;
      ret.unitprice = data.price;
      unitprice = data.price;
      ret.cost = data.cost;
      ret.allcost = myRound(data.cost * ret.qty, 4);
//       if($("#isoutset").val()==1)  $("#unitprice").val(unitprice);
//       else  $("#unitprice").val(0);
      $("#unitprice").val(unitprice);
      ret.marketprice = data.marketprice;
      change('unitprice');
      ret.sqlcodedescr = data.sqldescr;
      ret.uomdescr = data.uomdescr;
      ret.remarks = data.remark;
      ret.expired=data.expired;
      $("#remarks").val(data.remark);
      if (data.pk) ret.custtypeitempk = data.pk;
    }
  </script>

</head>

<body>
<div class="body-box-form">
  <div class="panel panel-system">
    <div class="panel-body">
      <div class="btn-group-xs">
        <button type="button" id="saveBtn" class="btn btn-system " onclick="validateDataForm()">保存</button>
        <button type="button" class="btn btn-system " onclick="closeWin()">关闭</button>
      </div>
    </div>
  </div>
  <div class="panel panel-info">
    <div class="panel-body">
      <form role="form" class="form-search">
        <ul class="ul-form">
          <li>
            <label>客户编号</label>
            <input type="text" id="custCode" name="custCode" value="${itemChg.custCode}" disabled="disabled"/>
          </li>
        </ul>
      </form>
    </div>
  </div>
  <div class="panel panel-info">
    <div class="panel-body">
      <form role="form" class="form-search" id="dataForm" action="" method="post" target="targetFrame">
        <house:token></house:token>
        <ul class="ul-form">
          <div class="validate-group">
            <li class="form-validate">
              <label>区域</label>
              <input type="text" id="fixareapk" name="fixareapk"/>
            </li>
            <li id="itemLi" class="form-validate">
              <label>材料编号</label>

              <input type="text" id="itemcode" name="itemcode"/>
            </li>
            <li id="custTypeItemLi" class="form-validate">
              <label>套餐材料编号</label>
              <input type="text" id="custtypeitempk" name="custtypeitempk"/>
            </li>
            <li class="form-validate">

              <label>套餐外材料</label>

              <select id="isoutset" name="isoutset" disabled="disabled" onchange="updateIsOutSet(this)">
                <option value="0">否</option>
                <option value="1">是</option>
              </select>
            </li>
          </div>
          <div class="validate-group">
            <li class="form-validate">
              <label>数量</label>
              <input type="number" id="qty" step="0.01"
                     onblur="change('qty')" name="qty"
                />
            </li>
            <li class="form-validate">
              <label>单价</label>
              <input type="text" id="unitprice"
                     name="unitprice" disabled="disabled"
                />
            </li>
            <li class="form-validate">
              <label>折扣前金额</label>
              <input type="text" id="beflineamount"
                     name="beflineamount" disabled="disabled"
                />
            </li>
          </div>
          <div class="validate-group">
            <li class="form-validate">
              <label>小计</label>
              <input type="text" id="tmplineamount"
                     name="tmplineamount" disabled="disabled"
                />
            </li>
            <li class="form-validate">
              <label>其他费用</label>
              <input type="number" step="0.01"
                     onblur="change('processcost')" id="processcost" name="processcost"
                />
            </li>
            <li class="form-validate">

              <label>折扣</label>
              <input type="text" id="markup"
                     name="markup" disabled="disabled"
                />
            </li>
          </div>
          <div class="validate-group">
            <li class="form-validate">
              <label>总价</label>
              <input type="text"
                     id="lineamount" name="lineamount" disabled="disabled"
                />
            </li>
            <li class="form-validate">
              <label class="control-textarea">备注</label>
              <textarea id="remarks" name="remarks" onblur="change('remarks')"></textarea>
            </li>
          </div>
        </ul>
      </form>
    </div>
  </div>
</div>
</body>
</html>
