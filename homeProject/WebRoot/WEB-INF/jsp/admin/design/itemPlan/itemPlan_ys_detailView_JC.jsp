<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>集成预算--查看</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_fixArea.js?v=${v}"
	type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_item.js?v=${v}"
	type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_intProd.js?v=${v}"
	type="text/javascript"></script>
<script type="text/javascript">
//校验函数
$(function() {
      var topFrame="#iframe_itemPlan_ys";
      var tableId;
      if('${itemPlan.isService}'==1){
      tableId="serviceDataTable";
      }else{
       tableId="dataTable";
      }
      ret= $(top.$(topFrame)[0].contentWindow.document.getElementById(tableId)).jqGrid("getRowData",'${itemPlan.rowId}');
      $("#fixareapk").openComponent_fixArea({condition: {custCode:'${itemPlan.custCode}',itemType1:'${itemPlan.itemType1}'},showValue:ret.fixareapk+"|"+ret.fixareadescr,readonly:true});
	  $("#intprodpk").openComponent_intProd({condition: {isService:'${itemPlan.isService}',custCode:'${itemPlan.custCode}',itemType1:'${itemPlan.itemType1}'},showValue:ret.intprodpk+"|"+ret.intproddescr,'readonly':true});
	  $("#itemcode").openComponent_item({showValue:ret.itemcode+"|"+ret.itemdescr,readonly:true});
	  $("#custCode").val('${itemPlan.custCode}');
	  $("#isoutset").val(ret.isoutset);
      $("#qty").val(ret.qty);
      $("#projectqty").val(ret.projectqty);
	  $("#unitprice").val(ret.unitprice);
	  $("#beflineamount").val(ret.beflineamount);
	  $("#tmplineamount").val(ret.tmplineamount);
	  $("#processcost").val(ret.processcost);
	  $("#markup").val(ret.markup);
	  $("#lineamount").val(ret.lineamount);
	  $("#remark").val(ret.remark);
	
});

</script>

</head>

<body>
	<div class="body-box-form">
		<div class="panel panel-system">
			<div class="panel-body">
				<div class="btn-group-xs">
					<button type="button" class="btn btn-system" onclick="closeWin()">关闭</button>
				</div>
			</div>
		</div>
		<div class="panel panel-info">
			<div>
				<form role="form" class="form-search" action="" method="post" target="targetFrame">
					<house:token></house:token>
					<ul class="ul-form">
						<li><label>客户编号</label> <input type="text" id="custCode" name="custCode" style="width:160px;"
							value="${itemPlan.custCode}" disabled="disabled" />
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
						<li><label>区域</label> <input type="text" id="fixareapk" name="fixareapk" />
						</li>
						<li><label>集成成品</label> <input type="text" id="intprodpk" name="intprodpk" />
						</li>
						<li><label>材料编号</label> <input type="text" id="itemcode" name="itemcode" />
						</li>
						<li><label>套餐外材料</label> <select id="isoutset" name="isoutset" disabled="disabled"
							onchange="updateIsOutSet(this)">
								<option value="0">否</option>
								<option value="1">是</option>
						</select>
						</li>
						<li><label>预估施工量</label> <input type="text" id="projectqty" name="projectqty" />
						</li>
						<li><label>数量</label> <input type="number" id="qty" onblur="change('qty')" name="qty" /></li>
						<li><label>单价</label> <input type="text" id="unitprice" name="unitprice" disabled="disabled"
							value="" />
						</li>
						<li><label>折扣前金额</label> <input type="text" id="beflineamount" name="beflineamount"
							disabled="disabled" />
						</li>
						<li><label>折扣</label> <input type="text" id="markup" name="markup" disabled="disabled" /></li>
						<li><label>小计</label> <input type="text" id="tmplineamount" name="tmplineamount"
							disabled="disabled" />
						</li>
						<li><label>其他费用</label> <input type="number" onblur="change('processcost')" id="processcost"
							name="processcost" />
						</li>
						<li><label>总价</label> <input type="text" id="lineamount" name="lineamount" disabled="disabled" />
						</li>
						<li><label class="control-textarea">备注</label> <textarea id="remark" name="remark"
								onblur="change('remark')"></textarea>
						</li>
					</ul>
				</form>
			</div>
		</div>
	</div>
</body>
</html>
