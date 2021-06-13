<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>主材预算--查看</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp"%>
<script src="${resourceRoot}/pub/component_fixArea.js?v=${v}" type="text/javascript"></script>
<script src="${resourceRoot}/pub/component_item.js?v=${v}" type="text/javascript"></script>
<script src="${resourceRoot}/pub/component_custTypeItem.js?v=${v}" type="text/javascript"></script>
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
      if(ret.custtypeitempk>0){
         $("#itemLi").hide();
           itemCode=ret.itemcode;
           $("#custtypeitempk").openComponent_custTypeItem({showValue:ret.custtypeitempk+"|"+ret.itemdescr,condition:{'itemType1':'${itemPlan.itemType1}','custType':'${itemPlan.custType}','disabledEle':'itemType1'}});
      }else{
          $("#custTypeItemLi").hide();
          itemCode=ret.itemcode;
          $("#itemcode").openComponent_item({showValue:ret.itemcode+"|"+ret.itemdescr,condition:{'itemType1':'${itemPlan.itemType1}','disabledEle':'itemType1'}});
      }
      $("#unitprice").val(ret.unitprice);
      $("#fixareapk").openComponent_fixArea({condition: {custCode:'${itemPlan.custCode}',itemType1:'${itemPlan.itemType1}'},showValue:ret.fixareapk+"|"+ret.fixareadescr});
	  $("#custCode").val('${itemPlan.custCode}');
      $("#qty").val(ret.qty);
      $("#isoutset").val(ret.isoutset);
      $("#projectqty").val(ret.projectqty);
	  $("#unitprice").val(ret.unitprice);
	  $("#beflineamount").val(ret.beflineamount);
	  $("#tmplineamount").val(ret.tmplineamount);
	  $("#processcost").val(ret.processcost);
	  $("#markup").val(ret.markup);
	  $("#lineamount").val(ret.lineamount);
	  $("#remark").val(ret.remark);
	  $("#isFixPrice").val(ret.isfixprice);
	  if('${itemPlan.itemType1}'!='ZC' ){
	    $('#prePlanAreaDescr_show').hide();
	    $('#algorithm_show').hide();
	    $('#cutType_show').hide();
	    $('#algorithmPer_show').hide();
	    $('#algorithmDeduct_show').hide();
	    
      }else{      	  
       	  $("#prePlanAreaPK").val(ret.preplanareapk);
       	  $("#prePlanAreaDescr").val(ret.preplanareadescr);
       	  $("#doorPk").val(ret.doorpk);
       	  $("#autoQty").val(ret.autoqty);
       	  $("#cutType").val(ret.cuttype);
       	  getAlgorithm();
       	  $("#algorithm").val(ret.algorithm);
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
			'code' :itemCode,
		},
		async:false,
		dataType : 'json',
		cache : false,
		error : function(obj) {
			showAjaxHtml({
				"hidden" : false,
				"msg" : '保存数据出错~'
			});
			
		},
		success : function(obj) {
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
					<button type="button" class="btn btn-system" onclick="closeWin()">关闭</button>
				</div>
			</div>
		</div>
		<div class="panel panel-info">
			<div>
				<form role="form" class="form-search" action="" method="post" target="targetFrame">
					<ul class="ul-form">
						<li><label>客户编号</label> <input type="text" id="custCode" name="custCode"
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
							<li class="form-validate"><label>区域</label> <input type="text" id="fixareapk" name="fixareapk" />
							</li>
							<li id="prePlanAreaDescr_show"><label>空间名称</label> <input type="text" id="prePlanAreaDescr" name="prePlanAreaDescr" readonly="true" />
							</li>
							<li id="itemLi" class="form-validate"><label>材料编号</label> <input type="text" id="itemcode"
								name="itemcode" />
							</li>
							<li id="algorithm_show" class="form-validate"><label>算法</label> 
								<select id="algorithm" name="algorithm"  ></select>
							</li>
							<li id="algorithmPer_show"><label>算法系数</label> 
								<input type="number" id="algorithmPer" name="algorithmPer" step="0.01" onchange="changeAlgorithm('1')"/>  
							</li>
							<li id="algorithmDeduct_show"><label>算法扣除数</label> 
								<input type="number" id="algorithmDeduct" name="algorithmDeduct" step="0.01" onchange="changeAlgorithm('1')"/>  
							</li>
							<li id="custTypeItemLi" class="form-validate"><label>套餐材料编号</label> <input type="text"
								id="custtypeitempk" name="custtypeitempk" />
							</li>
							<li class="form-validate"><label>套餐外材料</label> <select id="isoutset" name="isoutset"
								disabled="disabled" >
									<option value="0">否</option>
									<option value="1">是</option>
							</select>
							</li>
	
							<li class="form-validate"><label>预估施工量</label> <input type="text" id="projectqty"
								name="projectqty"  />
							</li>
							<li class="form-validate"><label>数量</label> <input type="number" id="qty" step="0.01"
								 name="qty" />
							</li>
							
							<li class="form-validate"><label>单价</label> <input type="text" id="unitprice" name="unitprice"
								 />
							</li>
	
							<li class="form-validate"><label>折扣前金额</label> <input type="text" id="beflineamount"
								name="beflineamount" disabled="disabled" />
							</li>
							<li class="form-validate"><label>折扣</label> <input type="text" id="markup" name="markup"
							 />
							</li>
							<li class="form-validate"><label>小计</label> <input type="text" id="tmplineamount"
								name="tmplineamount" disabled="disabled" />
							</li>
							
							<li class="form-validate"><label>其他费用</label> <input type="number" 
								id="processcost" name="processcost" />
							</li>
							<li class="form-validate"><label>总价</label> <input type="text" id="lineamount" name="lineamount"
								disabled="disabled" />
							</li>
							<li id="cutType_show" class="form-validate"><label>切割类型</label> 
							 <house:xtdm id="cutType" dictCode="CUTTYPE"></house:xtdm>
							</li>
						
							<li class="form-validate"><label class="control-textarea">备注</label> <textarea id="remark"
									name="remark" ></textarea>
							</li>
						<div class="validate-group" hidden="true" >
							<li class="form-validate"><label>是否固定价</label> <input type="text" id="isFixPrice" name="isFixPrice"/>
							</li>
							<li class="form-validate"><label>空间pk</label> <input type="text" id="prePlanAreaPK" name="prePlanAreaPK"/>
							</li>
							<li class="form-validate"><label>门pk</label> <input type="text" id="doorPk" name="doorPk"/>
							</li>
							<li class="form-validate"><label>系统计算量</label> <input type="text" id="autoQty" name="autoqty"/>
							</li>
						</div>
					</ul>
				</form>
			</div>
		</div>
	</div>
</body>
</html>
