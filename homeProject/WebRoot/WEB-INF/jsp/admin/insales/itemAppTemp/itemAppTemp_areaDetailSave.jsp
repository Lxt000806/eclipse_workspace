<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh">
<head>
	<title>添加</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>

<script src="${resourceRoot}/pub/component_item.js?v=${v}" type="text/javascript" defer></script>
<script type="text/javascript" defer>
var itExpired;
// itCode刷新验证
function validateRefresh_itCode(data){
	$("#page_form").data("bootstrapValidator")  
		.updateStatus("openComponent_item_itCode", "NOT_VALIDATED", null)
		.validateField("openComponent_item_itCode");
	// 根据itcode获取材料过期标志
	$.ajax({
		url:"${ctx}/admin/itemAppTemp/getItemByCode",
		type:"post",
		data:{code:data.code},
		dataType:"json",
		cache:false,
		error:function(obj){
			showAjaxHtml({"hidden": false, "msg": "获取数据出错"});
		},
		success:function(obj) {
			if (obj) {
				itExpired = obj.Expired;
			}
		}
	});
}

function save(){
	$("#page_form").bootstrapValidator("validate");/* 提交验证 */
	if(!$("#page_form").data("bootstrapValidator").isValid()) return;

 	var selectRows = [];
	var datas=$("#page_form").jsonForm();

	var itCodes = "${itCodes}".split(",");
	var pks = "${pks}".split(",");
	for (var i = 0; i < itCodes.length; i++) {
		if ("A" == "${m_umState}") {
			if ($.trim(itCodes[i]) == datas.itCode) {
				art.dialog({
					content: "此材料已存在",
					width: 200
				});
				return;
			} 
		} else {
			if (pks[i] != datas.pk && $.trim(itCodes[i]) == datas.itCode) {
				art.dialog({
					content: "此材料已存在",
					width: 200
				});
				return;
			} 
		}
	}

	datas.itExpired = itExpired;
	
	var itArr = datas.openComponent_item_itCode.split("|");
	datas.itDescr = itArr[itArr.length-1];

	datas.qty = parseFloat(datas.qty);

	var calTypeText=$("#calType").find("option:selected").text();
 	var arr = calTypeText.split(" ");
 	var calTypeDescr=arr[arr.length-1];
	datas.calTypeDescr=(calTypeDescr=="请选择..."?"":calTypeDescr);

	selectRows.push(datas);
 	//console.log(selectRows);
	Global.Dialog.returnData = selectRows;
	
	closeWin();
}

//校验函数
$(function() {
	$("#qty").val(parseFloat("${itemAppTempAreaDetail.qty}"));

	switch ("${m_umState}") {
	case "A":
		$("#qty").val(0);
		$("#itCode").openComponent_item({
			callBack:validateRefresh_itCode,
			condition:{
				itemType1:"${itemType1}",
				disabledEle:"itemType1",
			}
		});
		break;
	case "V":
		$("#itCode").openComponent_item({
			showValue:"${itemAppTempAreaDetail.itcode}",
			showLabel: "${itemAppTempAreaDetail.itDescr}",
		});
		$("#openComponent_item_itCode").prop("readonly",true).next().prop("disabled",true);
		$("#page_form input[type='text']").prop("readonly",true);
		$("#calType").prop("disabled",true);
		$("#saveBtn").hide();
		break;
	default:
		$("#itCode").openComponent_item({
			callBack:validateRefresh_itCode,
			showValue:"${itemAppTempAreaDetail.itcode}",
			showLabel: "${itemAppTempAreaDetail.itDescr}",
			condition:{
				itemType1:"${itemType1}",
				disabledEle:"itemType1",
			}
		});
		itExpired = "${itemAppTempAreaDetail.itExpired}";
		break;
	}

	$("#page_form").bootstrapValidator({
		message : "请输入完整的信息",
		feedbackIcons : {/*input状态样式图片*/
			validating : "glyphicon glyphicon-refresh"
		},
		fields: { 
			openComponent_item_itCode:{ 
				validators: {  
					notEmpty: {  
						message: "材料编号不允许为空"  
					}
				}  
			},
		}
	});

	// 获得原始数据
	var originalData = $("#page_form").serialize();

	$("#closeBut").on("click",function(){
		var changeData = $("#page_form").serialize();
		if (originalData != changeData) {
			art.dialog({
				content: "数据已变动,是否保存？",
				width: 200,
				okValue: "确定",
				ok: function () {
					save();
				},
				cancelValue: "取消",
				cancel: function () {
					closeWin();
				}
			});
		} else {
			closeWin();
		}
		
	});
});

</script>
</head>
<body>
	<div class="body-box-form">
		<div class="panel panel-system">
		    <div class="panel-body">
		      <div class="btn-group-xs">
					<button type="button" class="btn btn-system " id="saveBtn" onclick="save()">
						<span>保存</span>
					</button>
					<button type="button" class="btn btn-system " id="closeBut">
						<span>关闭</span>
					</button>
				</div>
			</div>
		</div>
		<div class="panel panel-info" style="margin: 0px;height: 207px;">  
			<div class="panel-body">
				<form action="" method="post" id="page_form" class="form-search">
					<house:token></house:token><!-- 按钮只能点一次 -->
					<input type="hidden" id="lastupdatedby" name="lastupdatedby" value="${sessionScope.USER_CONTEXT_KEY.czybh}"/>
					<input type="hidden" id="pk" name="pk" value="${itemAppTempAreaDetail.pk}"/>
					<ul class="ul-form">
						<div class="row">
							<li class="form-validate">
								<label><span class="required">*</span>材料编码</label>
								<input type="text" id="itCode" name="itCode" style="width:160px;"/>
							</li>
						</div>
						<div class="row">
							<li>
								<label>数量</label>
								<input type="text" id="qty" name="qty" style="width:160px;"
									onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\.\d]/g,'');"
									value="${itemAppTempAreaDetail.qty}" />
							</li>
						</div>
						<div class="row">
							<li>
								<label>计算类型</label>
								<house:xtdm id="calType" dictCode="ITEMTEMPTYPE" style="width:160px;" 
									value="${itemAppTempAreaDetail.calType}"></house:xtdm>
							</li>
						</div>
					</ul>	
				</form>
			</div>
		</div>
	</div>
</body>
</html>
