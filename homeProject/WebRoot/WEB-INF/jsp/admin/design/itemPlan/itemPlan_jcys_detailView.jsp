<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>基础预算--查看</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp"%>
<script src="${resourceRoot}/pub/component_fixArea.js?v=${v}" type="text/javascript"></script>
<script src="${resourceRoot}/pub/component_baseItem.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">



//校验函数
$(function() {
	var topFrame="";
	if( "${baseItemPlan.m_umState}"=="C"){
		topFrame="#iframe_itemPlan_jcys_confirm";
	}else{
		topFrame="#iframe_itemPlan_jcys";
	}
	ret= $(top.$(topFrame)[0].contentWindow.document.getElementById("dataTable")).jqGrid("getRowData",'${baseItemPlan.rowId}');
	$("#fixareapk").openComponent_fixArea({condition: {custCode:'${baseItemPlan.custCode}',itemType1:'${baseItemPlan.itemType1}'},showValue:ret.fixareapk,showLabel:ret.fixareadescr});
	var sPrjType="";
	if (ret.basealgorithm!=""){
		 $("#fixareapk").setComponent_fixArea({readonly: true});
		 sPrjType=ret.prjtype;
	}else{
		$("#baseAlgorithm").attr("disabled",true);
	}
	var sCategory='';
	if('${baseItemPlan.custTypeType}'=='1'){
		sCategory='1';
	}
	
	$("#baseitemcode").openComponent_baseItem({showValue:ret.baseitemcode,showLabel:ret.baseitemdescr,
		condition: {category: sCategory,prjType:sPrjType}	   
    });
	$("#custCode").val('${baseItemPlan.custCode}');
	$("#qty").val(ret.qty);
	$("#unitprice").val(ret.unitprice);
	$("#lineamount").val(ret.lineamount);
	$("#material").val(ret.material);
	$("#remark").val(ret.remark);
	$("#iscalmangefee").val(ret.iscalmangefee);
	$("#isrequired").val(ret.isrequired);
	$("#canreplace").val(ret.canreplace);
	$("#canmodiqty").val(ret.canmodiqty);
	$("#baseAlgorithm").val(ret.basealgorithm); 
	$("#baseAlgorithm").attr("disabled",true);
	$("#isrequired").attr("disabled",true);
	$("#canreplace").attr("disabled",true);
	$("#canmodiqty").attr("disabled",true);
	$("#prePlanAreaPk").val(ret.preplanareapk);
	if(ret.canmodiqty=="0"){
		$("#qty").attr("disabled",true);
	}
	if(ret.canreplace=="0"){
		 $("#baseitemcode").setComponent_baseItem({readonly: true});
	}
});
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
			<div>
				<form role="form" class="form-search" action="" method="post" target="targetFrame">
					<ul class="ul-form">
						<li><label>客户编号</label> <input type="text" id="custCode" name="custCode"
							value="${baseItemPlan.custCode}" disabled="disabled" /></li>
					</ul>
				</form>
			</div>

		</div>
		<div class="panel panel-info">
			<div class="panel-body">
				<form role="form" class="form-search" id="dataForm" action="" method="post" target="targetFrame">
					<input type="hidden" id="iscalmangefee" name="iscalmangefee" "/>
					<ul class="ul-form">
						<div class="validate-group">
							<li class="form-validate"><label>区域</label> <input type="text" id="fixareapk" name="fixareapk" />
							</li>
							<li class="form-validate"><label>基础项目</label> <input type="text" id="baseitemcode"
								name="baseitemcode" /></li>
							
							<li class="form-validate"><label>数量</label> <input type="number" step="0.01" id="qty" name="qty"
								/></li>
						</div>
						<div class="validate-group">
							<li class="form-validate"><label>人工单价</label> <input type="text" id="unitprice" name="unitprice"
								disabled="disabled" /></li>
							<li class="form-validate"><label>材料单价 </label> <input type="text" id="material" name="material"
								disabled="disabled" /></li>
							<li class="form-validate"><label>总价 </label> <input type="text" id="lineamount"
								name="lineamount" disabled="disabled" /></li>
						</div>
						<div class="validate-group">
							<li class="form-validate"><label>算法</label> 
								<house:dict id="baseAlgorithm" dictCode="" 
									sql=" select  Code,Code+' '+Descr fd from tBaseAlgorithm where Expired = 'F' " 
									sqlValueKey="Code" sqlLableKey="fd" readonly="readonly">
								</house:dict>
							</li>
							<li class="form-validate"><label>必选项</label> 
								<house:xtdm id="isrequired" dictCode="YESNO"></house:xtdm>
							</li>
							<li class="form-validate"><label>可替换</label> 
								<house:xtdm id="canreplace" dictCode="YESNO"></house:xtdm>
							</li>
						</div>
						<div class="validate-group">
						<li class="form-validate"><label>数量可修改</label> 
								<house:xtdm id="canmodiqty" dictCode="YESNO"></house:xtdm>
							</li>
							<li class="form-validate"><label class="control-textarea">备注 </label> <textarea id="remark"
									name="remark" onblur="change('remark')"></textarea></li>
						</div>
						<li hidden="true" ><label>空间pk</label> <input type="text" id="prePlanAreaPk" name="prePlanAreaPk"/>
						</li>
					</ul>
				</form>
			</div>

		</div>
	</div>
</body>
</html>
