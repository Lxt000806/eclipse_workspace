<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>基础结算项目预算--编辑</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp"%>
<script src="${resourceRoot}/pub/component_fixArea.js?v=${v}" type="text/javascript"></script>
<script src="${resourceRoot}/pub/component_baseCheckItem.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
var ret;
function save(){
	var qty=$("#qty").val();
	var code=$("#code").val();
	var fixareapk=$("#fixareapk").val();
	var offerpri=$("#offerpri").val();
	var material=$("#material").val();
	var prjofferpri=$("#prjofferpri").val();
	var prjmaterial=$("#prjmaterial").val();
	if(qty==""){art.dialog({content: "数量不能为空！"});return;}
	if(code==""){art.dialog({content: "基础结算项目不能为空！"});return;}
	if(fixareapk==""){art.dialog({content: "区域不能为空！"});return;}
	if(offerpri==""){art.dialog({content: "人工单价不能为空！"});return;}
	if(material==""){art.dialog({content: "材料单价不能为空！"});return;}
	if(prjofferpri==""){art.dialog({content: "项目经理人工单价不能为空！"});return;}
	if(prjmaterial==""){art.dialog({content: "项目经理材料单价不能为空！"});return;}
    var datas=$("#dataForm").jsonForm();
    datas['total']=parseFloat(datas.totalofferpri)+parseFloat(datas.totalmaterial);
    datas['prjtotal']=parseFloat(datas.totalprjofferpri)+parseFloat(datas.totalprjmaterial);
    datas['lastupdate']=new Date();
    datas['lastupdateby']="${USER_CONTEXT_KEY.czybh}";
    datas['actionlog']="EDIT";
	Global.Dialog.returnData =datas;
	closeWin();	
}
//校验函数
$(function() {
	var topFrame="#iframe_detail";
	ret= $(top.$(topFrame)[0].contentWindow.document.getElementById("dataTable")).jqGrid("getRowData",'${baseCheckItemPlan.rowId}');
	$("#fixareapk").openComponent_fixArea({
		showValue:ret.fixareapk,
		showLabel:ret.fixareadescr,
		condition: {
			custCode:'${baseCheckItemPlan.custCode}',
			itemType1:'${baseCheckItemPlan.itemType1}'
		},
		callBack:function(data){
			$("#fixareadescr").val(data.Descr);
		}
	});
	$("#openComponent_fixArea_fixareapk").attr("disabled",true);
	$("#code").openComponent_baseCheckItem({
		showValue:ret.code,
		showLabel:ret.descr,
		callBack:function(data){
			var qty=$("#qty").val();
			$("#descr").val(data.descr);
			$("#offerpri").val(data.offerpri);
			$("#material").val(data.material);
			$("#prjofferpri").val(data.prjofferpri);
			$("#prjmaterial").val(data.prjmaterial);
			$("#remark").val(data.remark);
			$("#uom").val(data.uom);
			$("#worktype1").val(data.worktype1);
			$("#worktype12").val(data.worktype12);
			$("#worktype1descr").val(data.worktype1descr);
			$("#worktype12descr").val(data.worktype12descr);
			$("#totalofferpri").val(myRound(qty*data.offerpri));
			$("#totalmaterial").val(myRound(qty*data.material));
			$("#totalprjofferpri").val(myRound(qty*data.prjofferpri));
			$("#totalprjmaterial").val(myRound(qty*data.prjmaterial));
			$("#tempofferpri").val(data.pofferpri);
			$("#tempmaterial").val(data.prjmaterial);
			$("#tempprjofferpri").val(data.prjofferpri);
			$("#tempprjmaterial").val(data.prjmaterial);
    	}
    }); 
    $("#openComponent_baseCheckItem_code").attr("disabled",true);
    $("#worktype1").val(ret.worktype1);
    $("#uom").val(ret.uom);
    $("#worktype12").val(ret.worktype12);
    $("#worktype1descr").val(ret.worktype1descr);
	$("#worktype12descr").val(ret.worktype12descr);
    $("#descr").val(ret.descr);
    $("#fixareadescr").val(ret.fixareadescr);
	$("#qty").val(ret.qty);
	$("#offerpri").val(ret.offerpri);
	$("#material").val(ret.material);
	$("#prjofferpri").val(ret.prjofferpri);
	$("#prjmaterial").val(ret.prjmaterial);
	$("#totalofferpri").val(ret.totalofferpri);
	$("#totalmaterial").val(ret.totalmaterial);
	$("#totalprjofferpri").val(ret.totalprjofferpri);
	$("#totalprjmaterial").val(ret.totalprjmaterial);
	$("#remark").val(ret.remark);
	
	$('#qty').bind('input propertychange', function() {
		var qty=parseFloat($("#qty").val());
		var offerpri=parseFloat($("#offerpri").val());
		var material=parseFloat($("#material").val());
		var prjofferpri=parseFloat($("#prjofferpri").val());
		var prjmaterial=parseFloat($("#prjmaterial").val());
		if(!isNaN(qty)){
			$("#totalofferpri").val(myRound(qty*offerpri));
			$("#totalmaterial").val(myRound(qty*material));
			$("#totalprjofferpri").val(myRound(qty*prjofferpri));
			$("#totalprjmaterial").val(myRound(qty*prjmaterial));
		}
	});
	$('#offerpri').bind('input propertychange', function() {  
		var qty=parseFloat($("#qty").val());
		var offerpri=parseFloat($("#offerpri").val());
		if(!isNaN(offerpri)){
			$("#totalofferpri").val(myRound(qty*offerpri));
		}
	});
	$('#material').bind('input propertychange', function() {  
		var qty=parseFloat($("#qty").val());
		var material=parseFloat($("#material").val());
		if(!isNaN(material)){
			$("#totalmaterial").val(myRound(qty*material));
		}
	});
	$('#prjofferpri').bind('input propertychange', function() {  
		var qty=parseFloat($("#qty").val());
		var prjofferpri=parseFloat($("#prjofferpri").val());
		if(!isNaN(prjofferpri)){
			$("#totalprjofferpri").val(myRound(qty*prjofferpri));
		}
	});
	$('#prjmaterial').bind('input propertychange', function() {  
		var qty=parseFloat($("#qty").val());
		var prjmaterial=parseFloat($("#prjmaterial").val());
		if(!isNaN(prjmaterial)){
			$("#totalprjmaterial").val(myRound(qty*prjmaterial));
		}
	});
	
	if('${baseCheckItemPlan.m_umState}'=='V'){
		$("input").attr("disabled",true);
		$("textarea").attr("disabled",true);
		$("#saveBtn").attr("disabled",true);
		$("#fixareapk").setComponent_fixArea({readonly:true});
		$("#code").setComponent_baseCheckItem({readonly:true});
	}
	
});
</script>

</head>

<body>
	<div class="body-box-form">
		<div class="panel panel-system">
			<div class="panel-body">
				<div class="btn-group-xs">
					<button type="button" id="saveBtn" class="btn btn-system " onclick="save()">保存</button>
					<button type="button" class="btn btn-system " onclick="closeWin()">关闭</button>
				</div>
			</div>
		</div>
		<div class="panel panel-info">
			<div>
				<form role="form" class="form-search" action="" method="post" target="targetFrame">
					<ul class="ul-form">
						<li><label>客户编号</label> <input type="text" id="custCode" name="custCode"
							value="${baseCheckItemPlan.custCode}" readonly="readonly"/></li>
					</ul>
				</form>
			</div>
		</div>
		<div class="panel panel-info">
			<div class="panel-body">
				<form role="form" class="form-search" id="dataForm" action=""
					method="post" target="targetFrame">
					<ul class="ul-form">
						<input type="hidden" id="uom" name="uom">
						<input type="hidden" id="worktype12" name="worktype12">
						<input type="hidden" id="worktype12descr" name="worktype12descr">
						<input type="hidden" id="worktype1" name="worktype1">
						<input type="hidden" id="worktype1descr" name="worktype1descr">
						<input type="hidden" id="fixareadescr" name="fixareadescr">
						<input type="hidden" id="descr" name="descr">
						<input type="hidden" id="total" name="total">
						<input type="hidden" id="prjtotal" name="prjtotal">
						<input type="hidden" id="tempofferpri" name="tempofferpri">
						<input type="hidden" id="tempmaterial" name="tempmaterial">
						<input type="hidden" id="tempprjofferpri" name="tempprjofferpri">
						<input type="hidden" id="tempprjmaterial" name="tempprjmaterial">
						<div class="validate-group">
							<li class="form-validate"><label>区域</label> <input
								type="text" id="fixareapk" name="fixareapk" /></li>
							<li class="form-validate"><label>基础结算项目</label> <input
								type="text" id="code" name="code" />
							</li>
							<li><label>数量</label> <input type="text" id="qty" name="qty"
								onkeyup="value=value.replace(/[^\?\d.]/g,'')" />
							</li>
						</div>
						<div class="validate-group">
							<li><label>人工单价</label> <input type="text" id="offerpri"
								name="offerpri" onkeyup="value=value.replace(/[^\?\d.]/g,'')" />
							</li>
							<li><label>材料单价 </label> <input type="text" id="material"
								name="material" onkeyup="value=value.replace(/[^\?\d.]/g,'')" />
							</li>
							<li class="form-validate"><label>人工总价 </label> <input
								type="text" id="totalofferpri" name="totalofferpri"
								readonly="readonly" />
							</li>
						</div>
						<div class="validate-group">
							<li class="form-validate"><label>材料总价 </label> <input
								type="text" id="totalmaterial" name="totalmaterial"
								readonly="readonly" />
							</li>
							<li><label>项目经理人工单价</label> <input type="text"
								id="prjofferpri" name="prjofferpri"
								onkeyup="value=value.replace(/[^\?\d.]/g,'')" />
							</li>
							<li><label>项目经理材料单价 </label> <input type="text"
								id="prjmaterial" name="prjmaterial"
								onkeyup="value=value.replace(/[^\?\d.]/g,'')" />
							</li>
						</div>
						<div class="validate-group">
							<li class="form-validate"><label>项目经理人工总价 </label> <input
								type="text" id="totalprjofferpri" name="totalprjofferpri"
								readonly="readonly" />
							</li>
							<li class="form-validate"><label>项目经理材料总价 </label> <input
								type="text" id="totalprjmaterial" name="totalprjmaterial"
								readonly="readonly" />
							</li>
						</div>
						<div class="validate-group">
							<li class="form-validate"><label class="control-textarea">备注
							</label> <textarea id="remark" name="remark"></textarea>
							</li>
						</div>
					</ul>
				</form>
			</div>
		</div>
	</div>
</body>
</html>
