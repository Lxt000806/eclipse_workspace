<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>快速预报价模板--增加</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_item.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_custTypeItem.js?v=${v}"type="text/javascript"></script>
<script type="text/javascript">
function save(){
	$("#isOutSet").removeAttr("disabled");
	var isOutSetDescr=$("#isOutSetDescr").val();
	var fixAreaType= $("#fixAreaType").val();
	var itemCode= $("#itemCode").val();
	var algorithm= $("#algorithm").val();
	var markup= $("#markup").val();
	var canModiQty= $("#canModiQty").val();
	if(fixAreaType==""){
		art.dialog({
			content : "区域类型不能为空！",
			width : 200
		});
		return;
	}
	if(itemCode==""){
		art.dialog({
			content : "材料编号不能为空！",
			width : 200
		});
		return;
	}
	if(algorithm==""){
		art.dialog({
			content : "算法不能为空！",
			width : 200
		});
		return;
	}
	if(canModiQty==""){
		art.dialog({
			content : "数量可修改不能为空！",
			width : 200
		});
		return;
	}
	
	var datas = $("#dataForm").jsonForm();
	if(datas.qty==""){
		datas['qty']=1;
	}
	if ($.trim($("#paveType").val())!=''){	
	 	var spavetypedescr=$("#paveType").find("option:selected").text();
		spavetypedescr = spavetypedescr.split(' ');//先按照空格分割成数组
		spavetypedescr=spavetypedescr[1];
		datas.paveTypeDescr=spavetypedescr;
	}
	Global.Dialog.returnData = datas;
	closeWin();
}
//校验函数
$(function() {
	findDescrByXtdm('isService','YESNO','isServiceDescr');
	findDescrByXtdm('isOutSet','YESNO','isOutSetDescr');
	findDescrByTable('fixAreaType','fixAreaTypeDescr');
	findDescrByTable('algorithm','algorithmDescr');
	findDescrByTable('isCut','isCutDescr');
	findDescrByXtdm('canModiQty','YESNO','canModiQtyDescr');
	getCutTypeDescr();
	if("${prePlanTempDetail.custType}"=="1"){
		$("#isOutSet").val("1");
		$("#isOutSet").attr("disabled",true);
	}
	changeIsOutSet();
	getAlgorithm("${prePlanTempDetail.itemCode}");
	findCutType("${prePlanTempDetail.itemCode}");
	changeAlgorithm();
	var m_umState='${prePlanTempDetail.m_umState}';
	if(m_umState=="V"){
		$("input").attr("disabled",true);
		$("select").attr("disabled",true);
		$("textarea").attr("disabled",true);
		$("#saveBtn").attr("disabled",true);
		$("#custTypeItemPk").openComponent_custTypeItem({disabled:true});
		$("#itemCode").openComponent_item({disabled:true});
	}
});
function changeIsOutSet(){
	if("${prePlanTempDetail.custType}"=="1"){
		$("#isOutSet").removeAttr("disabled");
	}
	var isOutSet=$("#isOutSet").val();
	if("${prePlanTempDetail.custType}"=="1"){
		$("#isOutSet").attr("disabled",true);
	}
	if(isOutSet=="0"){
        $("#itemCodeLi").hide();
        $("#tip").hide();
        $("#custTypeItemPkLi").show();
        $("#custTypeItemPk").openComponent_custTypeItem({
        showValue:'${prePlanTempDetail.custTypeItemPk}',
        showLabel:'${prePlanTempDetail.itemDescr}',
        condition:{custType:"${prePlanTempDetail.custType}"},
        callBack:function(data){
        	console.log(data);
        	$("#itemCode").val(data.code);
        	$("#itemDescr").val(data.descr);
        	findCutType(data.code.trim());
        	getAlgorithm(data.code);
	    }});
    }else if(isOutSet=="1") {
    	$("#itemCode").val("");
    	$("#tip").hide();
        $("#custTypeItemPkLi").hide();
        $("#itemCodeLi").show();
        $("#itemCode").openComponent_item({
        showValue:'${prePlanTempDetail.itemCode}',
        showLabel:'${prePlanTempDetail.itemDescr}',
        condition:{'custType':'${prePlanTempDetail.custType}'},
        callBack:function(data){
        	$("#itemCode").val(data.code);
        	$("#itemDescr").val(data.descr);
        	$("#itemExpired").val(data.expired);
        	findCutType(data.code.trim());
        	getAlgorithm(data.code);
	    }});
    }else{
   		$("#custTypeItemPk").val("");
   		$("#itemCode").val("");
   		$("#custTypeItemPkLi").hide();
		$("#itemCodeLi").hide();
		$("#tip").show();
    }
}
	//根据id查descr
	function findDescrByXtdm(cbm,id,taget) {
		$.ajax({
			url : '${ctx}/admin/intProgDetail/findDescr',
			type : 'post',
			data : {
				'cbm' : $("#"+cbm).val(),
				'id' : id
			},
			async:false,
			dataType : 'json',
			cache : false,
			error : function(obj) {
				console.log(obj);
				showAjaxHtml({
					"hidden" : false,
					"msg" : '保存数据出错~'
				});
			},
			success : function(obj) {
				$("#" + taget).val(obj.note);
			}
		});
	}
	
	//根据id查descr
	function findDescrByTable(id,taget) {
		var value=$("#"+id).val();
		if(value!="" && value!=null)
		$.ajax({
			url : '${ctx}/admin/workCostDetail/findDescr',
			type : 'post',
			data : {
				'value' :value,
				'id' : id,
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
				$("#" + taget).val(obj.Descr);
			}
		});
	}
		//根据id查descr
	function getAlgorithm(itemCode) {
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
	                $("#algorithm").val("${prePlanTempDetail.algorithm}");
                }
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
					"msg" : '保存数据出错~'
				});
				
			},
			success : function(obj) {
				$("#cutType").empty();
				$("#cutType").append($("<option/>").text("请选择...").attr("value",""));
				$("#cutType").removeAttr("disabled");
	            $.each(obj, function(i, o){      
	                $("#cutType").append($("<option/>").text(o.fd).attr("value",o.Code));
	            });
	            $("#cutType").val("${prePlanTempDetail.cutType}");
	            findDescrByXtdm('cutType','CUTTYPE','cutTypeDescr');
			}
		});
	}
	//修改算法
	function changeAlgorithm(){
		if($("#algorithm").val()==""){
			$("#cutType").val("");
			$("#cutType").attr("disabled",true);
			return;
		}
		$.ajax({
			url : '${ctx}/admin/algorithm/checkIsCalCutFee',
			type : 'post',
			data : {
				'code' :$("#algorithm").val(),
			},
			async:false,
			dataType : 'json',
			cache : false,
			async: false,
			error : function(obj) {
				showAjaxHtml({
					"hidden" : false,
					"msg" : '保存数据出错~'
				});
				
			},
			success : function(obj) {
				if(obj[0].isCalCutFee!="1"){
					$("#cutType").val("");
					$("#cutType").attr("disabled",true);
				}else{
					$("#cutType").removeAttr("disabled");
				}
			}
		});
	}
	
	function getCutTypeDescr(){
		var selectText=$("#cutType").find("option:selected").text();
		var arr = selectText.split(" ");
		var descr=arr[arr.length-1];
		if(descr=="请选择..."){
			descr="";
		}
		$("#cutTypeDescr").val(descr);
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
					<input type="hidden" style="width:160px;" id="fixAreaTypeDescr"
						name="fixAreaTypeDescr"
						value="${prePlanTempDetail.fixAreaTypeDescr}" /> <input
						type="hidden" style="width:160px;" id="isServiceDescr"
						name="isServiceDescr" value="${prePlanTempDetail.isServiceDescr}" />
					<input type="hidden" style="width:160px;" id="isOutSetDescr"
						name="isOutSetDescr" value="${prePlanTempDetail.isOutSetDescr}" />
					<input type="hidden" style="width:160px;" id="algorithmDescr"
						name="algorithmDescr" value="${prePlanTempDetail.algorithmDescr}" />
					<input type="hidden" style="width:160px;" id="itemDescr"
						name="itemDescr" value="${prePlanTempDetail.itemDescr}" /><input
						type="hidden" style="width:160px;" id="cutTypeDescr"
						name="cutTypeDescr" value="${prePlanTempDetail.cutTypeDescr}" />
					<input type="hidden" style="width:160px;" id="itemExpired"
						name="itemExpired" value="${prePlanTempDetail.itemExpired}" />
					<input type="hidden"
						style="width:160px;" id="canModiQtyDescr" name="canModiQtyDescr" />
					<house:token></house:token>
					<ul class="ul-form">
						<div class="validate-group row">
							<li class="form-validate"><label>区域类型</label> <house:dict
									id="fixAreaType" dictCode=""
									sql="select rtrim(Code)+' '+Descr fd,Code from tFixAreaType where Expired='F' order by DispSeq"
									sqlValueKey="Code" sqlLableKey="fd"
									value="${prePlanTempDetail.fixAreaType}"
									onchange="findDescrByTable('fixAreaType','fixAreaTypeDescr')">
								</house:dict></li>
							<li><label>是否服务性产品</label> <house:xtdm id="isService"
									dictCode="YESNO" value="${prePlanTempDetail.isService}"
									onchange="findDescrByXtdm('isService','YESNO','isServiceDescr')"></house:xtdm>
							</li>
						</div>
						<div class="validate-group row">
							<li><label>是否套餐外材料</label> <house:xtdm id="isOutSet"
									dictCode="YESNO" value="${prePlanTempDetail.isOutSet}"
									onchange="changeIsOutSet();findDescrByXtdm('isOutSet','YESNO','isOutSetDescr')"></house:xtdm>
							</li>
							<li id="tip">
								<p>
									<font color="red">&emsp;&emsp;请先选择是否套餐外材料后才能选择材料！</font>
								</p>
							</li>
							<li id="itemCodeLi"><label>材料编号</label> <input type="text"
								id="itemCode" name="itemCode" style="width:160px;"
								value="${prePlanTempDetail.itemCode}" />
							</li>
							<li id="custTypeItemPkLi"><label>套餐材料pk</label> <input
								type="text" id="custTypeItemPk" name="custTypeItemPk"
								style="width:160px;" value="${prePlanTempDetail.custTypeItemPk}" />
							</li>
						</div>
						<div class="validate-group row">
							<li><label>材料类型说明</label> <input type="text"
								id="itemTypeDescr" name="itemTypeDescr" style="width:160px;"
								value="${prePlanTempDetail.itemTypeDescr}" />
							</li>
							<li><label>折扣</label> <input type="number"
								style="width:160px;" id="markup" name="markup"
								value="${prePlanTempDetail.markup}" /><span
								style="position: absolute;left:290px;width: 30px;top:3px;">%</span>
							</li>
						</div>
						<div class="validate-group row">
							<li><label>其他费用</label> <input type="number"
								id="processCost" name="processCost" style="width:160px;"
								value="${prePlanTempDetail.processCost}" /></li>
							<li><label>数量</label> <input type="number" id="qty"
								name="qty" style="width:160px;" value="${prePlanTempDetail.qty}" />
							</li>
						</div>
						<div class="validate-group row">
							<li class="form-validate selector"><label>算法</label> <select
								id="algorithm" name="algorithm"
								onchange="findDescrByTable('algorithm','algorithmDescr');changeAlgorithm()"></select>
							</li>
							<li class="form-validate"><label>算法系数</label> 
								<input type="number" id="algorithmPer"
								name="algorithmPer" style="width:160px;" value="${prePlanTempDetail.algorithmPer}"
							</li>
							
						</div>
						<div class="validate-group row">
							<li class="form-validate"><label>算法扣除数</label> 
								<input type="number" id="algorithmDeduct"
								name="algorithmDeduct" style="width:160px;" value="${prePlanTempDetail.algorithmDeduct}"
							</li>
							<li class="form-validate selector"><label>切割类型</label> <select
								id="cutType" name="cutType" onchange="getCutTypeDescr()"></select>
							</li>	
						</div>
						<div class="validate-group row">
						    
							<li class="form-validate">
								<label>铺贴类型</label>
								<house:xtdm  id="paveType" dictCode="PAVETYPE" value="${prePlanTempDetail.paveType}" ></house:xtdm>
							</li>
							<li><label>数量可修改</label> <house:xtdm id="canModiQty" 
								dictCode="YESNO" value="${prePlanTempDetail.canModiQty}"
								onchange="findDescrByXtdm('canModiQty','YESNO','canModiQtyDescr')"></house:xtdm>
						</li>
						
						</div>
					
						<div class="validate-group row">
							<li class="form-validate"><label class="control-textarea">材料备注</label>
								<textarea id="remark" name="remark"
									style="overflow-y:scroll; overflow-x:hidden; height:75px; " />${prePlanTempDetail.remark
								}</textarea>
							</li>
						</div>
					</ul>
				</form>
			</div>
		</div>
	</div>
</body>
</html>
