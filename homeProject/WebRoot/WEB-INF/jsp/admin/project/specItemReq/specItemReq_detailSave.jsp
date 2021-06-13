<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://" + request.getServerName() + ":"+request.getServerPort()+path+"/";
String jasperPath = PathUtil.WEB_INF+"jsp/jasper/";
%>
<!DOCTYPE html>
<html lang="zh">
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	
	<script src="${resourceRoot}/pub/component_item.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_intProd.js?v=${v}" type="text/javascript"></script>
</head>
<body>
	<div class="body-box-form">
		<div class="panel panel-system">
		    <div class="panel-body">
		    	<div class="btn-group-xs">
					<button type="button" class="btn btn-system" id="saveBtn">
						<span>保存</span>
					</button>
					<button type="button" class="btn btn-system" id="closeBut" onclick="closeWin(false)">
						<span>关闭</span>
					</button>
				</div>
			</div>
		</div>
		<div class="panel panel-info" style="margin: 0px;">  
			<div class="panel-body">
				<form action="" method="post" id="page_form" class="form-search">
					<input type="hidden" name="jsonString" value=""/>
					<input type="hidden" id="lastUpdatedBy" name="lastUpdatedBy" value="${sessionScope.USER_CONTEXT_KEY.czybh}"/>
					<input type="hidden" id="itemType1" name="itemType1" value="JC"/>
					<input type="hidden" id="cost" name="cost" value="${specItemReqDt.itCost}"/>
					<input type="hidden" id="price" name="price" value="${specItemReqDt.price}"/>
					<input type="hidden" id="moveCost" name="moveCost" value="${specItemReqDt.cost}"/>
					<!-- <input type="hidden" id="appQty" name="appQty" value="${specItemReqDt.appQty}"/> -->
					<input type="hidden" id="isExist" name="isExist" value="${specItemReqDt.isExist}"/>
					<ul class="ul-form">
						<div class="row">
							<li>
								<label for="isCupboard"><span class="required">*</span>是否橱柜</label>
								<house:xtdm id="isCupboard" dictCode="YESNO" style="width:160px;" value="${specItemReqDt.isCupboard}"/>
							</li>
						</div>
						<div class="row">
							<li class="form-validate">
								<label for="itemCode"><span class="required">*</span>材料</label>
								<input type="text" id="itemCode" name="itemCode" style="width: 160px;">
							</li>
						</div>
						<div class="row">
							<li>
								<label for="intprodpk">集成成品</label>
								<input type="text" id="intprodpk" name="intprodpk" style="width: 160px;">
							</li>
						</div>
						<div class="row">
							<li class="form-validate">
								<label><span class="required">*</span>数量</label>
								<input type="text" id="qty" name="qty" style="width:160px;" 
									onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\.\d]/g,'');"
									onafterpaste="this.value =this.value.replace(/\s/g,'').replace(/[^\.\d]/g,'');"
									value="0"/>
							</li>
						</div>
						<div class="row">
							<li>
								<label>已申请数量</label>
								<input type="text" id="appQty" name="appQty" style="width:160px;" 
									value="${specItemReqDt.appQty}" readonly />
							</li>
						</div>
						<div class="row">
							<li style="max-height: 120px;">
								<label class="control-textarea" style="top: -60px;">描述</label>
								<textarea id="remark" name="remark" style="height: 80px;width: 160px;">${specItemReqDt.remark}</textarea>
							</li>
						</div>
					</ul>
				</form>
			</div>
		</div>
	</div>
</body>	
<script type="text/javascript"> 
	m_umState = "${specItemReqDt.m_umState}";
$(function() {
	$("#isCupboard").prop("disabled",true);
	// 去掉小数0
	$("#cost").val(Math.round(parseFloat("${specItemReqDt.itCost}") * 100) / 100);
	$("#price").val(Math.round(parseFloat("${specItemReqDt.price}") * 100) / 100);
	$("#moveCost").val(Math.round(parseFloat("${specItemReqDt.cost}") * 100) / 100);
	$("#appQty").val(Math.round(parseFloat("${specItemReqDt.appQty}") * 100) / 100);

	if ("A" == m_umState) {
		$("#appQty").val(0);
		$("#itemCode").openComponent_item({
			callBack:setItemInfo,
			condition:{
				itemType1:"JC",
				itemType2:"${specItemReqDt.itemType2}",
				disabledEle:"itemType1",
			}
		});
		$("#intprodpk").openComponent_intProd({
			condition:{
				custCode:"${specItemReqDt.custCode}",
				itemType1:"JC",
				isCupboard:"${specItemReqDt.isCupboard}",
				fixAreaPk:-1,
			},
		});
	} else if ("V" == m_umState) {
		$("#saveBtn").hide();
		$("#itemCode").openComponent_item({
			readonly:true,
			callBack:setItemInfo,
			showValue:"${specItemReqDt.itemCode}",
			showLabel: "${specItemReqDt.itemDescr}",
			condition:{
				itemType1:"JC",
				itemType2:"${specItemReqDt.itemType2}",
				disabledEle:"itemType1",
			}
		});
		$("#intprodpk").openComponent_intProd({
			readonly:true,
			showValue:"${specItemReqDt.intProdPK}",
			showLabel: "${specItemReqDt.intProdPKDescr}",
			condition:{
				custCode:"${specItemReqDt.custCode}",
				itemType1:"JC",
				isCupboard:"${specItemReqDt.isCupboard}",
				fixAreaPk:-1,
			},
		});
		$("#qty").val(Math.round(parseFloat("${specItemReqDt.qty}") * 100) / 100);
		$("#page_form").disableForm();
	} else {
		$("#itemCode").openComponent_item({
			callBack:setItemInfo,
			showValue:"${specItemReqDt.itemCode}",
			showLabel: "${specItemReqDt.itemDescr}",
			condition:{
				itemType1:"JC",
				itemType2:"${specItemReqDt.itemType2}",
				disabledEle:"itemType1",
			}
		});
		$("#intprodpk").openComponent_intProd({
			showValue:"${specItemReqDt.intProdPK}",
			showLabel: "${specItemReqDt.intProdPKDescr}",
			condition:{
				custCode:"${specItemReqDt.custCode}",
				itemType1:"JC",
				isCupboard:"${specItemReqDt.isCupboard}",
				fixAreaPk:-1,
			},
		});
		$("#qty").val(Math.round(parseFloat("${specItemReqDt.qty}") * 100) / 100);
	}

	$("#openComponent_intProd_intprodpk").prop("readonly", true);

	$("#page_form").bootstrapValidator({
		message : "请输入完整的信息",
		feedbackIcons : {/*input状态样式图片*/
			validating : "glyphicon glyphicon-refresh"
		},
		fields: {  
			openComponent_item_itemCode:{  
				validators: {  
					notEmpty: {  
						message: "材料不能为空"  
					},
					/*callback: {
						message: "材料重复",
						callback: function(itemCode){
							if ("${itemCodes}" == "") {
								return true;
							}
							var item = itemCode.split("|");
							var keys = "${itemCodes}".split(",");
							for ( var i in keys) {
								if (keys[i] == item[0]) {
									return false;		
								}
							}
							return true;
						}
					}*/
				}  
			},
			qty:{  
				validators: {  
					notEmpty: {  
						message: "数量不能为空"  
					},
					// 可以为true（默认值）或false。如果为true，则输入值必须大于或等于比较值。如果为false，则输入值必须大于比较值。
		        	greaterThan: {
						message: "数量必须大于或等于已下单数量"+$("#appQty").val(),
						value: $("#appQty").val(),
						inclusive: true,
					},
				}  
			}
		},
		submitButtons : "input[type='submit']"
	});

	$("#saveBtn").on("click",function(){
		$("#page_form").bootstrapValidator("validate");
		if(!$("#page_form").data("bootstrapValidator").isValid()) return;
		
		if ("V" == m_umState) return;

		// 将page_form json化后赋给selectRows
 		var selectRows = [];
		var datas=$("#page_form").jsonForm();

		var itemArr = datas.openComponent_item_itemCode.split("|");
		datas.itemDescr = itemArr[itemArr.length-1];
		var intProdArr = datas.openComponent_intProd_intprodpk.split("|");
		datas.intprodpkdescr = intProdArr[intProdArr.length-1];
		datas.totalCost = Math.round(parseFloat(datas.cost) * parseFloat(datas.qty) * 100) / 100;

	 	selectRows.push(datas);
		Global.Dialog.returnData = selectRows;
		
		closeWin();
	});
	
});
// 设置材料信息
function setItemInfo(data) {
	/*$.ajax({
		url:"${ctx}/admin/specItemReq/getAppQty",
		type:"post",
		data:{custCode:"${specItemReqDt.custCode}",itemCode:data.code},
		dataType:"json",
		cache:false,
		async:false,// (默认: true) 默认设置下，所有请求均为异步请求。
		error: function(obj){
			showAjaxHtml({"hidden": false, "msg": "获取已下单数据出错"});
	    },
		success:function(obj){
			$("#appQty").val(obj.AppQty);
			$("#qty").val(obj.AppQty);
			$("#isExist").val(obj.isExist);
			// 刷新数量验证器
			$("#page_form").data("bootstrapValidator")
				.updateOption("qty", "greaterThan", "value", $("#appQty").val())
				.updateOption("qty", "greaterThan", "message", "数量必须大于或等于已下单数量"+$("#appQty").val());
		}
	});*/
	$("#page_form").data("bootstrapValidator")  
		.updateStatus("openComponent_item_itemCode", "NOT_VALIDATED", null)
		.validateField("openComponent_item_itemCode")
		.updateStatus("qty", "NOT_VALIDATED", null)
		.validateField("qty");
	$("#cost").val(data.cost);
	$("#price").val(data.price);
	$("#moveCost").val(data.avgcost);
}

</script>
</html>
