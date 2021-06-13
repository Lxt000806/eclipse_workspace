<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>基础算量模板--增加</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_baseItem.js?v=${v}"type="text/javascript"></script>
<script type="text/javascript">
	function save(){
		var fixAreaType= $("#fixAreaType").val();
		var baseItemCode= $("#baseItemCode").val();
		var baseAlgorithm= $("#baseAlgorithm").val();
		var qty= $("#qty").val();
		var isOutSet= $("#isOutSet").val();
		var isRequired= $("#isRequired").val();
		var canReplace= $("#canReplace").val();
		var canModiQty= $("#canModiQty").val();
		var dispSeq= $("#dispSeq").val();
		var paveTile= $("#paveTile").val();
		var paveFloor= $("#paveFloor").val();
		var paveDiamondFloor= $("#paveDiamondFloor").val();
		if(fixAreaType==""){
			art.dialog({
				content : "区域类型不能为空！",
				width : 200
			});
			return;
		}
		if(baseItemCode==""){
			art.dialog({
				content : "基础项目编号不能为空！",
				width : 200
			});
			return;
		}
		if(baseAlgorithm==""){
			art.dialog({
				content : "基础项目算法不能为空！",
				width : 200
			});
			return;
		}
		
		if(qty==""){
			art.dialog({
				content : "数量不能为空！",
				width : 200
			});
			return;
		}
		if(isOutSet==""){
			art.dialog({
				content : "是否套餐外材料不能为空！",
				width : 200
			});
			return;
		}
		if(isRequired==""){
			art.dialog({
				content : "必选项不能为空！",
				width : 200
			});
			return;
		}
		if(canReplace==""){
			art.dialog({
				content : "可替换不能为空！",
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
			
		if(dispSeq==""){
			art.dialog({
				content : "顺序不能为空！",
				width : 200
			});
			return;
		}
		if(paveTile==""){
			art.dialog({
				content : "铺瓷砖不能为空！",
				width : 200
			});
			return;
		}
		if(paveFloor==""){
			art.dialog({
				content : "铺实木地板不能为空！",
				width : 200
			});
			return;
		}
		if(paveDiamondFloor==""){
			art.dialog({
				content : "铺金刚板不能为空！",
				width : 200
			});
			return;
		}
		$("#isOutSet").removeAttr("disabled");
		var datas = $("#dataForm").jsonForm();
		Global.Dialog.returnData = datas;
		closeWin();
	}
	
	$(function() {
		if("${basePlanTempDetail.custType}"=="1"){
			// $("#isOutSet").val("1");
			findDescrByXtdm('isOutSet','YESNO','isOutSetDescr');
			// $("#isOutSet").attr("disabled",true); //家装客户的是否套餐外材料要允许修改 modify by zb
		}
		//初始化下拉框名称值
		findDescrByTable('fixAreaType','fixAreaTypeDescr');
		findDescrByXtdm('isOutSet','YESNO','isOutSetDescr');
		findDescrByXtdm('isRequired','YESNO','isRequiredDescr');
		findDescrByXtdm('canReplace','YESNO','canReplaceDescr');
		findDescrByXtdm('canModiQty','YESNO','canModiQtyDescr');
		findDescrByXtdm('paveTile','YESNO','paveTileDescr');
		findDescrByXtdm('paveFloor','YESNO','paveFloorDescr');
		findDescrByXtdm('paveDiamondFloor','YESNO','paveDiamondFloorDescr');
		//初始化算法下拉框
		getBaseAlgorithm("${basePlanTempDetail.baseItemCode}");
		$("#baseItemCode").openComponent_baseItem({
	        showValue:'${basePlanTempDetail.baseItemCode}',
	        showLabel:'${basePlanTempDetail.baseItemDescr}',
	        callBack:function(data){
	        	$("#baseItemCode").val(data.code);
	        	$("#baseItemDescr").val(data.descr);
	        	getBaseAlgorithm(data.code);
		  	},
		    condition:{customerType:"${basePlanTempDetail.custType}"}
		});
		$("#openComponent_baseItem_baseItemCode").attr("readonly",true);
		if("${basePlanTempDetail.m_umState}"=="V"){
			$("input").attr("disabled",true);
			$("select").attr("disabled",true);
			$("textarea").attr("disabled",true);
			$("#saveBtn").attr("disabled",true);
			$("#baseItemCode").setComponent_baseItem({readonly:true});
		}
	});

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
	//查算法
	function getBaseAlgorithm(baseItemCode) {
		$.ajax({
			url : '${ctx}/admin/baseItem/getBaseAlgorithm',
			type : 'post',
			data : {
				'code' :baseItemCode,
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
				$("#baseAlgorithm").empty();
				$("#baseAlgorithm").append($("<option/>").text("请选择...").attr("value",""));
				if(obj.length==0){
					$("#baseAlgorithm").attr("disabled",true);
				}else{
					$("#baseAlgorithm").removeAttr("disabled");
	                $.each(obj, function(i, o){      
	                    $("#baseAlgorithm").append($("<option/>").text(o.fd).attr("value",o.Code));
	                 });
	                $("#baseAlgorithm").val("${basePlanTempDetail.baseAlgorithm}");
	                findDescrByTable('baseAlgorithm','baseAlgorithmDescr');
				}
			}
		});
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
						name="fixAreaTypeDescr" /> <input type="hidden"
						style="width:160px;" id="baseItemDescr" name="baseItemDescr" value="${basePlanTempDetail.baseItemDescr}"/> <input
						type="hidden" style="width:160px;" id="isOutSetDescr"
						name="isOutSetDescr" /> <input type="hidden" style="width:160px;"
						id="isRequiredDescr" name="isRequiredDescr" /> <input
						type="hidden" style="width:160px;" id="canReplaceDescr"
						name="canReplaceDescr" /> <input type="hidden"
						style="width:160px;" id="canModiQtyDescr" name="canModiQtyDescr" />
					<input type="hidden" style="width:160px;" id="paveTileDescr"
						name="paveTileDescr" /> <input type="hidden" style="width:160px;"
						id="paveFloorDescr" name="paveFloorDescr" /> <input type="hidden"
						style="width:160px;" id="paveDiamondFloorDescr"
						name="paveDiamondFloorDescr" /> <input type="hidden"
						style="width:160px;" id="baseAlgorithmDescr"
						name="baseAlgorithmDescr" />
					<house:token></house:token>
					<ul class="ul-form">
						<div class="validate-group row">
							<li class="form-validate"><label>区域类型</label> <house:dict
									id="fixAreaType" dictCode=""
									sql="select rtrim(Code)+' '+Descr fd,Code from tFixAreaType where Expired='F' order by DispSeq"
									sqlValueKey="Code" sqlLableKey="fd"
									value="${basePlanTempDetail.fixAreaType}"
									onchange="findDescrByTable('fixAreaType','fixAreaTypeDescr')">
								</house:dict></li>
							<li><label>基础项目编号</label> <input type="text"
								id="baseItemCode" name="baseItemCode" style="width:160px;"
								value="${basePlanTempDetail.baseItemCode}" />
							</li>
						</div>
						<div class="validate-group row">
							<li class="form-validate selector"><label>基础项目算法</label> <select
								id="baseAlgorithm" name="baseAlgorithm"
								onchange="findDescrByTable('baseAlgorithm','baseAlgorithmDescr')"></select>
							</li>
							<li><label>数量</label> <input type="number" id="qty"
								name="qty" style="width:160px;" onkeyup="value=value.replace(/[^\-?\d.]/g,'')"
							onafterpaste="this.value=this.value.replace(/\D/g,'')"
								value="${basePlanTempDetail.qty}" /></li>
						</div>
						<div class="validate-group row">
							<li><label>是否套餐外项目</label> <house:xtdm id="isOutSet"
									dictCode="YESNO" value="${basePlanTempDetail.isOutSet}"
									onchange="findDescrByXtdm('isOutSet','YESNO','isOutSetDescr')"></house:xtdm>
							</li>
							<li><label>必选项</label> <house:xtdm id="isRequired"
									dictCode="YESNO" value="${basePlanTempDetail.isRequired}"
									onchange="findDescrByXtdm('isRequired','YESNO','isRequiredDescr')"></house:xtdm>
							</li>
						</div>
						<div class="validate-group row">
							<li><label>可替换</label> <house:xtdm id="canReplace"
									dictCode="YESNO" value="${basePlanTempDetail.canReplace}"
									onchange="findDescrByXtdm('canReplace','YESNO','canReplaceDescr')"></house:xtdm>
							</li>
							<li><label>数量可修改</label> <house:xtdm id="canModiQty"
									dictCode="YESNO" value="${basePlanTempDetail.canModiQty}"
									onchange="findDescrByXtdm('canModiQty','YESNO','canModiQtyDescr')"></house:xtdm>
							</li>
						</div>
						<div class="validate-group row">
							<li><label>铺瓷砖</label> <house:xtdm id="paveTile"
									dictCode="YESNO" value="${basePlanTempDetail.paveTile}"
									onchange="findDescrByXtdm('paveTile','YESNO','paveTileDescr')"></house:xtdm>
							</li>
							<li><label>铺实木地板</label> <house:xtdm id="paveFloor"
									dictCode="YESNO" value="${basePlanTempDetail.paveFloor}"
									onchange="findDescrByXtdm('paveFloor','YESNO','paveFloorDescr')"></house:xtdm>
							</li>
						</div>
						<div class="validate-group row">
							<li><label>铺金刚板</label> <house:xtdm id="paveDiamondFloor"
									dictCode="YESNO" value="${basePlanTempDetail.paveDiamondFloor}"
									onchange="findDescrByXtdm('paveDiamondFloor','YESNO','paveDiamondFloorDescr')"></house:xtdm>
							</li>
						</div>
						<div class="validate-group row">
							<li class="form-validate"><label class="control-textarea">备注</label>
								<textarea id="remark" name="remark"
									style="overflow-y:scroll; overflow-x:hidden; height:75px; " />${basePlanTempDetail.remark
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
