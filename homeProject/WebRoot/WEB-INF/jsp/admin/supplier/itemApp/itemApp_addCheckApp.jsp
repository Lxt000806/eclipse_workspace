<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>采购费用明细--增加</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_supplFeeType.js?v=${v}"type="text/javascript"></script>
<script type="text/javascript">
function save(){
	var supplfeetype=$("#supplfeetype").val();
	var amount=$("#amount").val();
	var isRepeated=false;
	var pk=$("#pk").val();
	var name="";
	var carryfloor=$("#carryfloor").val();
	if(supplfeetype==""){
		art.dialog({
			content:"请选择费用类型！",
		});
		return;
	}
	if(amount==""){
		art.dialog({
			content:"金额不能为空！",
		});
		return;
	}
	if(supplfeetype=="03" && carryfloor==""){
		art.dialog({
			content:"搬运楼层不能为空！",
		});
		return;
	}
	if (supplfeetype=="03" && (parseInt(carryfloor) !== parseFloat(carryfloor))){
		art.dialog({
			content:"搬运楼层不能为小数！",
		});
		return;
	}
	var rows = $(top.$("#iframe_checkApp")[0].contentWindow.document.getElementById("dataTable")).jqGrid("getRowData");
	if(rows.length>0){
		for(var i=0;i<rows.length;i++){
			if(rows[i].supplfeetype.trim()==supplfeetype && rows[i].pk!=pk){
				name=rows[i].supplfeetypedescr;
				isRepeated=true;
				break;
			}
		}
	}
	if(isRepeated){
		art.dialog({
			content: "已经存在费用类型为["+name+"]的明细！",
		});
		return;
	}
	var datas = $("#dataForm").jsonForm();
	Global.Dialog.returnData = datas;
	closeWin();
}
//校验函数
$(function() {
	var m_umState="${m_umState}";
	$("#supplfeetype").openComponent_supplFeeType({
		showValue:"${map.supplfeetype}",
		showLabel:"${map.supplfeetypedescr}",
		callBack:function(data){
			$("#supplfeetypedescr").val(data.descr);
			changeType();
		}
	});
	$("#openComponent_supplFeeType_supplfeetype").attr("readonly",true);
	if(m_umState=="V"){
		disabledForm("dataForm");
		$("#saveBtn").attr("disabled",true);
	}
	changeType();
});
function changeType(){
	var supplfeetype=$("#supplfeetype").val();
	if(supplfeetype=="03"){
		$("#carryfloor").parent().removeClass("hidden");
	}else{
		$("#carryfloor").parent().addClass("hidden");
		$("#carryfloor").val("");
	}
}
</script>
<style type="text/css">

</style>
</head>

<body>
	<div class="body-box-form">
		<div class="panel panel-system">
			<div class="panel-body">
				<div class="btn-group-xs">
					<button type="button" id="saveBtn" class="btn btn-system"
						onclick="save()">保存</button>
					<button type="button" class="btn btn-system"
						onclick="closeWin(false)">关闭</button>
				</div>
			</div>
		</div>
		<div class="panel panel-info">
			<div class="panel-body">
				<form role="form" class="form-search" id="dataForm" action=""
					method="post" target="targetFrame">
					<house:token></house:token>
					 <input type="hidden" id="pk"
							name="pk" value="${map.pk }"/>
					<input type="hidden" id="supplfeetypedescr" name="supplfeetypedescr"
						value="${map.supplfeetypedescr }">
					<ul class="ul-form">
						<li><label>费用类型</label> <input type="text" id="supplfeetype"
							name="supplfeetype" style="width:160px;" value="${map.supplfeetype}" readonly/>
						</li>
						<li class="hidden"><label>搬楼层数</label> <input type="number" id="carryfloor"
							name="carryfloor" value="${m_umState=='A'?0:map.carryfloor }"/>
						</li>
						<li><label>金额</label> <input type="number" id="amount"
							name="amount" value="${m_umState=='A'?0:map.amount }"/>
						</li>
						<li>
							<label class="control-textarea">备注 </label>
							<textarea id="remarks" name="remarks" style="width:200px;" rows="3">${map.remarks}</textarea>
						</li>	
					</ul>
				</form>
			</div>
		</div>
	</div>
</body>
</html>
