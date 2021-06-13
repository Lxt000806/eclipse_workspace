<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<!DOCTYPE html>
<html lang="zh">
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8"/>
	<META HTTP-EQUIV="pragma" CONTENT="no-cache"/>
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"/>
	<META HTTP-EQUIV="expires" CONTENT="0"/>
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge"/>
	<%@ include file="/commons/jsp/common.jsp"%>

	<style type="text/css">
		.form-search .ul-form li label {
			width: 130px;
		}
	</style>
	<script src="${resourceRoot}/pub/component_wareHouse.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_item.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_wareHousePosi.js?v=${v}" type="text/javascript"></script>
</head>
<body>
	<div class="body-box-form">
		<div class="panel panel-system">
		    <div class="panel-body">
		      <div class="btn-group-xs">
					<button type="button" class="btn btn-system " id="saveBtn" onclick="doSave()">
						<span>保存</span>
					</button>
					<button type="button" class="btn btn-system " id="closeBtn" >
						<span>关闭</span>
					</button>
				</div>
			</div>
		</div>
		<div class="panel panel-info" style="margin: 0px;">  
			<div class="panel-body">
				<form action="" method="post" id="page_form" class="form-search">
					<house:token></house:token>
					<input type="hidden" id="lastUpdatedBy" name="lastUpdatedBy" value="${sessionScope.USER_CONTEXT_KEY.czybh}"/>
					<ul class="ul-form">
						<div class="row">
							<li class="form-validate">
								<label>仓库</label>
								<input type="text" id="code" name="code" style="width:160px;"/>
							</li>
						</div>
						<div class="row">
							<li class="form-validate">
								<label><span class="required">*</span>库位</label>
								<input type="text" id="pk" name="pk" style="width:160px;"/>
							</li>
						</div>
						<div class="row">
							<li class="form-validate">
								<label>材料编号</label>
								<input type="text" id="itCode" name="itCode" style="width:160px;"/>
							</li>
						</div>
						<div class="row">
							<li>
								<label>已上架数量</label>
								<input type="text" id="qtyCal" name="qtyCal" style="width:160px;" value="0" readonly="readonly" />
							</li>
						</div>
						<div class="row">
							<li>
								<label>未上架数量</label>
								<input type="text" id="qtyNo" name="qtyNo" style="width:160px;" value="0" readonly="readonly" />
							</li>
						</div>
						<div class="row">
							<li class="form-validate">
								<label><span class="required">*</span>数量</label>
								<input type="text" id="qtyAdd" name="qtyAdd" style="width:160px;" 
									onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\.\d]/g,'');"
									value="0"/>
							</li>
						</div>
					</ul>	
				</form>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
var originalData;
// 原始表单json
var oriDataJson;
$(function() {
	$("#code").openComponent_wareHouse({
		callBack:validateRefresh_whcode,
		showValue:"${wareHouse.code}",
		showLabel:"${wareHouse.desc1}",
		condition:{
			czybh:"${sessionScope.USER_CONTEXT_KEY.czybh}"
		},
		readonly:true
	});
	var codeArr = $("#openComponent_wareHouse_code").val().split("|");
	$("#pk").openComponent_wareHousePosi({
		callBack:validateRefresh_pk,
		showValue:"${wareHouse.pk}",
		showLabel:"${wareHouse.whpDescr}",
		condition:{
			whcode:codeArr[0],
		}
	});
	$("#itCode").openComponent_item({
		callBack:validateRefresh_itCode,
		showValue:"${wareHouse.itCode}",
		showLabel:"${wareHouse.itemDescr}",
		readonly:true
	});
	// 选中、全选
	$("#openComponent_wareHouse_code").focus();
	//$("#openComponent_wareHouse_code").select();

	$("#page_form").bootstrapValidator({
		message : "请输入完整的信息",
		feedbackIcons : {/*input状态样式图片*/
			validating : "glyphicon glyphicon-refresh"
		},
		fields: { 
			openComponent_wareHouse_code:{ 
				validators: {  
					notEmpty: {  
						message: "请输入完整的信息"  
					}
				}  
			},
			openComponent_wareHousePosi_pk:{ 
				validators: {  
					notEmpty: {  
						message: "请输入完整的信息"  
					}
				}  
			},
			openComponent_item_itCode:{ 
				validators: {  
					notEmpty: {  
						message: "请输入完整的信息"  
					}
				}  
			},
			qtyAdd:{ 
				validators: {  
					notEmpty: {  
						message: "数量要大于0"  
					},
					greaterThan: {
						message: "数量要大于0",
						value: 0.0,
						inclusive: false,
					}
				}  
			},
		}
	});

	// 根据上下架动态添加校验
	if ("U" == "${wareHouse.m_umState}") {
		$("#page_form")
		.bootstrapValidator("addField", "qtyAdd", {  /* 动态添加校验 */
	       	validators: {  
	        	lessThan: {  
        			message: "上架数量不能大于未上架数量",
					value: $("#qtyNo").val()
	        	}
	        }  
    	});
	} else {
		$("#page_form")
		.bootstrapValidator("addField", "qtyAdd", {  /* 动态添加校验 */
	       	validators: {  
	        	lessThan: {  
	        		message: "下架数量不能大于已上架数量",
	        		value: $("#qtyCal").val()
	        	}
	        }  
    	});
	}

	oriDataJson = $("#page_form").jsonForm();

	// 获得原始数据
	originalData = $("#page_form").serialize();
	
	//用该方法可以刷新表格，在onclick中无法刷新
	$("#closeBtn").on("click",function(){
		closeWin();
	});
});

function validateRefresh_whcode(){
	$("#page_form").data("bootstrapValidator")  
		.updateStatus("openComponent_wareHouse_code", "NOT_VALIDATED", null)
		.validateField("openComponent_wareHouse_code");
	// 如果code有变化，就刷新pk
	if ($("#openComponent_wareHouse_code").val() != oriDataJson.openComponent_wareHouse_code) {
		var codeArr = $("#openComponent_wareHouse_code").val().split("|");
		$("#pk").openComponent_wareHousePosi({
			callBack:validateRefresh_pk,
			condition:{
				whcode:codeArr[0]
			}
		});
		oriDataJson = $("#page_form").jsonForm();
	}
}

function validateRefresh_pk(){
	$("#page_form").data("bootstrapValidator")  
		.updateStatus("openComponent_wareHousePosi_pk", "NOT_VALIDATED", null)
		.validateField("openComponent_wareHousePosi_pk");
	getCal();
}

function validateRefresh_itCode(){
	$("#page_form").data("bootstrapValidator")  
		.updateStatus("openComponent_item_itCode", "NOT_VALIDATED", null)
		.validateField("openComponent_item_itCode");
	getCal();
}

// 根据pk和code获取上下架数量
function getCal(){
	var codeArr = $("#openComponent_wareHouse_code").val().split("|");
	var pkArr = $("#openComponent_wareHousePosi_pk").val().split("|");
	var itCodeArr = $("#openComponent_item_itCode").val().split("|");
	if (pkArr[0] == ""||itCodeArr[0] == "") {
		return;
	}
	$.ajax({
		url:"${ctx}/admin/whPosiBal/getCal",
		type: "post",
		data: {code:codeArr[0],pk:pkArr[0],itCode:itCodeArr[0]},
		dataType: "json",
		cache: false,
		async:false,
		error: function(obj){
			showAjaxHtml({"hidden": false, "msg": "获取数据出错"});
	    },
	    success: function(obj){
	    	if(!obj.rs){
	    		$("#qtyCal").val(obj.datas.qtycal);
	    		$("#qtyNo").val(obj.datas.qtyno);
	    		$("#_form_token_uniq_id").val(obj.token.token);
	    	}
    	}
	});
	// 改变上架数量和未上架数量时，修改验证
	$("#page_form").bootstrapValidator("removeField","qtyAdd");
	if ("U" == "${wareHouse.m_umState}") {
		$("#page_form").bootstrapValidator("addField", "qtyAdd", {
	       	validators: {  
	        	lessThan: {  
        			message: "上架数量不能大于未上架数量",
					value: $("#qtyNo").val(),
	        	}
	        }  
    	});
	} else {
		$("#page_form").bootstrapValidator("addField", "qtyAdd", {
	       	validators: {  
	        	lessThan: {  
	        		message: "下架数量不能大于已上架数量",
	        		value: $("#qtyCal").val(),
	        	}
	        }  
    	});
	}
}

function doSave(){
	$("#page_form").bootstrapValidator("validate");/* 提交验证 */
	if(!$("#page_form").data("bootstrapValidator").isValid()){
		return;
	}  /* 验证失败return */	

	var datas = $("#page_form").jsonForm();
	//转换成json字符串以detailJson的name存入
	datas.detailJson = "["+JSON.stringify(datas)+"]";
	datas.m_umState = "D";

	$.ajax({
		url:"${ctx}/admin/whPosiBal/doSave",
		type: "post",
		data: datas,
		dataType: "json",
		cache: false,
		error: function(obj){
			showAjaxHtml({"hidden": false, "msg": "保存数据出错"});
	    },
	    success: function(obj){
	    	if(obj.rs){
					art.dialog({
						content: obj.msg,
						time: 1000,
						beforeunload: function () {
			    			closeWin(false);
						}
					});
	    	}else{
	    		$("#_form_token_uniq_id").val(obj.token.token);
	    		art.dialog({
					content: obj.msg,
					width: 200
				});
	    	}
    	}
	});
}

</script>
</html>
