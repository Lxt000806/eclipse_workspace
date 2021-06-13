<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <title>材料增减--查看</title>
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
  <script type="text/javascript">
    var ret
    function change(name) {
      switch (name) {
        case 'qty':
          ret.qty = $("#qty").val();
          var beflineamount = ret.qty * ret.unitprice;
          var tmplineamount = Math.round(ret.qty * ret.unitprice * ret.markup / 100);
          var lineamount = Math.round(parseFloat(ret.qty * ret.unitprice * ret.markup / 100) + parseFloat(ret.processcost));
          $("#beflineamount").val(beflineamount);
          $("#tmplineamount").val(tmplineamount);
          $("#lineamount").val(lineamount);
          ret.beflineamount = beflineamount;
          ret.tmplineamount = tmplineamount;
          ret.lineamount = lineamount;
          break;
        case 'processcost':
          ret.processcost = parseFloat($("#processcost").val());
          var lineamount = Math.round(parseFloat(ret.tmplineamount) + ret.processcost);
          $("#lineamount").val(lineamount);
          ret.lineamount = lineamount;
          break;
        default:
          ret.remarks = $("#remarks").val();
      }
    }
    function save() {
      var datas = $("#dataForm").jsonForm();
      ret.fixareapk = datas.openComponent_fixArea_fixareapk.substring(0, datas.openComponent_fixArea_fixareapk.indexOf("|"));
      ret.fixareapkdescr = datas.openComponent_fixArea_fixareapk.substring(datas.openComponent_fixArea_fixareapk.indexOf("|") + 1);
      ret.itemcode = datas.openComponent_item_itemcode.substring(0, datas.openComponent_item_itemcode.indexOf("|"));
      ret.itemdescr = datas.openComponent_item_itemcode.substring(datas.openComponent_item_itemcode.indexOf("|") + 1);
      Global.Dialog.returnData = ret;
      closeWin();

    }

    //校验函数
    $(function () {
      if (top.$("#iframe_itemChg_Add")[0]) {
        var topFrame = "#iframe_itemChg_Add";
      } else if (top.$("#iframe_itemChgDetail")[0]) {
        var topFrame = "#iframe_itemChgDetail";
      } else if (top.$("#iframe_itemChgConfirm")[0]) {
        var topFrame = "#iframe_itemChgConfirm";
      } else {
        var topFrame = "#iframe_itemChgUpdate";
      }
      ret = $(top.$(topFrame)[0].contentWindow.document.getElementById("dataTable")).jqGrid("getRowData", ${itemChg.rowId});
      $("#itemcode").openComponent_item({
        showValue: ret.itemcode + "|" + ret.itemdescr, 'readonly': true, callBack: function (data) {
          ret.itemtype2descr = data.itemtype2descr;
        }
      });
      $("#fixareapk").openComponent_fixArea({
        condition: {
          isService: '${itemChg.isService}',
          custCode: '${itemChg.custCode}',
          itemType1: '${itemChg.itemType1}'
        }, showValue: ret.fixareapk + "|" + ret.fixareadescr, 'readonly': true
      });
      $("#isoutset").val(ret.isoutset);
      $("#qty").val(ret.qty);
      $("#unitprice").val(ret.unitprice);
      $("#beflineamount").val(ret.beflineamount);
      $("#tmplineamount").val(ret.tmplineamount);
      $("#processcost").val(ret.processcost);
      $("#markup").val(ret.markup);
      $("#lineamount").val(ret.lineamount);
      $("#remarks").val(ret.remarks);
      if($.trim('${itemChg.itemType1}')!='ZC'){ 
  	      $('#prePlanAreaDescr_show').hide();
  	      $('#algorithm_show').hide();
  	      $('#cutType_show').hide();
  	      $('#algorithmPer_show').hide();
  	      $('#algorithmDeduct_show').hide(); 
      }else{      	  
       	  $("#prePlanAreaPK").val(ret.preplanareapk);
       	  $("#prePlanAreaDescr").val(ret.preplanareadescr);
       	  $("#doorPk").val(ret.doorpk);
       	  getAlgorithm();
       	  $("#algorithm").val(ret.algorithm); 
       	  findCutType(ret.itemcode);
       	  $("#cutType").val(ret.cuttype); 
       	  $("#algorithmPer").val(ret.algorithmper);
       	  $("#algorithmDeduct").val(ret.algorithmdeduct); 
       }

    });
    
    //加载算法
    function getAlgorithm() {
    	$.ajax({
    		url : '${ctx}/admin/item/getAlgorithm',
    		type : 'post',
    		data : {
    			'code' :ret.itemcode,
    		},
    		async:false,
    		dataType : 'json',
    		cache : false,
    		error : function(obj) {
    			showAjaxHtml({
    				"hidden" : false,
    				"msg" : '加载算法数据出错~'
    			});
    			
    		},
    		success : function(obj) {
    			var  oldAlgorithm=$("#algorithm").val(); //原预算
    			$("#algorithm").empty();
    			$("#algorithm").append($("<option/>").text("请选择...").attr("value",""));
    			if(obj.length==0){
    				$("#algorithm").attr("disabled",true);
    			}else{
    				$("#algorithm").removeAttr("disabled");
                    $.each(obj, function(i, o){    
                        $("#algorithm").append($("<option/>").text(o.fd).attr("value",o.Code));
                     });
                }
                $("#algorithm").val(oldAlgorithm);
    		}
    	});
    }
    //根据切割类型匹配瓷砖尺寸
    function findCutType(itemCode){
    	$.ajax({
    		url : '${ctx}/admin/prePlanTempDetail/getQtyByCutType',
    		type : 'post',
    		data : {
    			'itemCode' :itemCode
    		},
    		async:false,
    		dataType : 'json',
    		cache : false,
    		error : function(obj) {
    			showAjaxHtml({
    				"hidden" : false,
    				"msg" : '匹配数据出错~'
    			});
    			
    		},
    		success : function(obj) {
    			$("#cutType").empty();
    			$("#cutType").append($("<option/>").text("请选择...").attr("value",""));
    			$("#cutType").removeAttr("disabled");
                $.each(obj, function(i, o){      
                    $("#cutType").append($("<option/>").text(o.fd).attr("value",o.Code));
                });
    		}
    	});
    }

  </script>

</head>

<body>
<div class="body-box-form">
  <div class="panel panel-system">
    <div class="panel-body">
      <div class="btn-group-xs">
        <button type="button" class="btn btn-system " onclick="closeWin()">关闭</button>
      </div>
    </div>
  </div>
  <div class="panel panel-info">
    <div class="panel-body">
      <form role="form" class="form-search">
        <house:token></house:token>
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
          <li>
            <label>区域</label>
            <input type="text" id="fixareapk" name="fixareapk"/>
          </li>
          <li>
            <label>材料编号</label>
            <input type="text" id="itemcode" name="itemcode"/>
          </li>
          <li>
            <label>套餐外材料</label>
            <select id="isoutset" name="isoutset" disabled="disabled">
              <option value="0">否</option>
              <option value="1">是</option>
            </select>
          </li>
          <li>
		  <li id="algorithm_show" ><label>算法</label> <select id="algorithm" name="algorithm"></select></li>
			<li id="algorithmPer_show" ><label>算法系数</label> 
			<input type="number" id="algorithmPer" name="algorithmPer" step="0.01" />  
			</li>
			<li id="algorithmDeduct_show" ><label>算法扣除数</label> 
			<input type="number" id="algorithmDeduct" name="algorithmDeduct" step="0.01" />  
			</li>
		  <li>
            <label>数量</label>
            <input type="number" id="qty"
                   onblur="change('qty')" name="qty"
              />
          </li>
          <li>
            <label>单价</label>
            <input type="text" id="unitprice"
                   name="unitprice" disabled="disabled"
              />
          </li>
          <li>
            <label>折扣前金额</label>
            <input type="text" id="beflineamount"
                   name="beflineamount" disabled="disabled"
              />
          </li>
          <li>
            <label>小计</label>
            <input type="text" id="tmplineamount"
                   name="tmplineamount" disabled="disabled"
              />
          </li>
          <li id="cutType_show" class="form-validate selector"><label>切割类型</label> 
				<select id="cutType"name="cutType" onchange="changeAlgorithm('2')"></select>
		 </li>
          <li>
            <label>其他费用</label>
            <input type="number"
                   onblur="change('processcost')" id="processcost" name="processcost"
              />
          </li>
          <li>
            <label>折扣</label>
            <input type="text" id="markup"
                   name="markup" disabled="disabled" />
          </li>
          <li>
            <label>总价</label>
            <input type="text"
                   id="lineamount" name="lineamount" disabled="disabled"/>
          </li>
          <li>
            <label class="control-textarea">备注</label>
            <textarea id="remarks" name="remarks" onblur="change('remarks')"></textarea>
          </li>
        </ul>
      </form>
    </div>
  </div>
</div>
</body>
</html>
