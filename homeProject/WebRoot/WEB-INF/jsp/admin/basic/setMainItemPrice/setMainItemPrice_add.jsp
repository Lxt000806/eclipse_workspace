<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh">
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8"/>
	<META HTTP-EQUIV="pragma" CONTENT="no-cache"/>
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"/>
	<META HTTP-EQUIV="expires" CONTENT="0"/>
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge"/>
	<%@ include file="/commons/jsp/common.jsp"%>
	<script src="${resourceRoot}/pub/component_workType2.js?v=${v}" type="text/javascript"></script>
</head>
<body>
	<div class="body-box-form">
		<div class="panel panel-system">
		    <div class="panel-body">
		      <div class="btn-group-xs">
					<button type="button" class="btn btn-system " id="saveBtn">
						<span>保存</span>
					</button>
					<button type="button" class="btn btn-system " id="closeBtn">
						<span>关闭</span>
					</button>
				</div>
			</div>
		</div>
		<div class="panel panel-info" style="margin: 0px;">  
			<div class="panel-body">
				<form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
					<house:token></house:token><!-- 按钮只能点一次 -->
					<input type="hidden" id="pk" name="pk" value="${setMainItemPrice.pk}"/>
					<input type="hidden" id="lastupdatedby" name="lastupdatedby" value="${sessionScope.USER_CONTEXT_KEY.czybh}"/>
					<input type="hidden" id="expired" name="expired" value="${setMainItemPrice.expired}"/>
					<ul class="ul-form">
						<div class="row">
							<div class="col-xs-12">
								<li class="form-validate">
									<label><span class="required">*</span>客户类型</label>
									<house:dict id="custType" dictCode="" 
										sql="select Code,Code+' '+Desc1 Descr from tCusttype where Expired='F' order by dispSeq" 
										sqlValueKey="Code" sqlLableKey="Descr" value="${setMainItemPrice.custType}"/>
								</li>
							</div>
							<div class="col-xs-12">
								<li class="form-validate">
									<label><span class="required">*</span>起始面积</label>
									<input type="text" id="fromArea" name="fromArea" style="width:160px;"
										onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\-\.\d]/g,'');" 
										value="${setMainItemPrice.fromArea}"/>
								</li>
								<li class="form-validate">
									<label><span class="required">*</span>截止面积</label>
									<input type="text" id="toArea" name="toArea" style="width:160px;"
										onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\-\.\d]/g,'');" 
										value="${setMainItemPrice.toArea}"/>
								</li>
							</div>
							<div class="col-xs-12">
								<li class="form-validate">
									<label><span class="required">*</span>基准单价</label>
									<input type="text" id="basePrice" name="basePrice" style="width:160px;"
										onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\-\.\d]/g,'');" 
										value="${setMainItemPrice.basePrice}"/>
								</li>
								<li class="form-validate">
									<label><span class="required">*</span>基准面积</label>
									<input type="text" id="baseArea" name="baseArea" style="width:160px;"
										onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\-\.\d]/g,'');" 
										value="${setMainItemPrice.baseArea}"/>
								</li>
							</div>
							<div class="col-xs-12">
								<li class="form-validate">
									<label><span class="required">*</span>每平米单价</label>
									<input type="text" id="unitPrice" name="unitPrice" style="width:160px;"
										onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\-\.\d]/g,'');" 
										value="${setMainItemPrice.unitPrice}"/>
								</li>
								<li class="form-validate">
									<label><span class="required">*</span>基准卫生间数</label>
									<input type="text" id="baseToiletCount" name="baseToiletCount" style="width:160px;"
										onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\-\.\d]/g,'');" 
										value="${setMainItemPrice.baseToiletCount}"/>
								</li>
							</div>
							<div class="col-xs-12">
								<li class="form-validate">
									<label><span class="required">*</span>卫生间单价</label>
									<input type="text" id="toiletPrice" name="toiletPrice" style="width:160px;"
										onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\-\.\d]/g,'');" 
										value="${setMainItemPrice.toiletPrice}"/>
								</li>
							</div>
						</div>
					</ul>	
				</form>
			</div>
		</div>
	</div>
	<script type="text/javascript" defer>
		var url = "", oldFromArea="", oldToArea="";
		$(function() {
			switch ("${setMainItemPrice.m_umState}") {
				case "M":
					oldFromArea="${setMainItemPrice.fromArea}", oldToArea="${setMainItemPrice.toArea}";
					url = "${ctx}/admin/setMainItemPrice/doUpdate";
					$("#custType").prop("disabled", true);
					break;
				case "V":
					oldFromArea="${setMainItemPrice.fromArea}", oldToArea="${setMainItemPrice.toArea}";
					$("#saveBtn").remove();
					disabledForm();
					break;
				default:
					url = "${ctx}/admin/setMainItemPrice/doSave";
					break;
			}
			$("#page_form").bootstrapValidator({
				message : "请输入完整的信息",
				feedbackIcons : {
					validating : "glyphicon glyphicon-refresh"
				},
				fields: { 
					custType: { 
						validators: {  
							notEmpty: {  
								message: "编号不允许为空"  
							},
						}  
					},
					fromArea:{ 
						validators: {  
							notEmpty: {  
								message: "起始面积不允许为空"  
							},
							remote: {
					            message: "和已存在的面积范围重复",
					            url: "${ctx}/admin/setMainItemPrice/checkArea",
					            data: function () {
					            	return {
					            		custType: $("#custType").val(), 
					            		oldFromArea: oldFromArea,
					            		oldToArea: oldToArea
					            	};
					            },
					            delay: 1000, 
					            type: "post", 
					        }
						}  
					},
					toArea:{ 
						validators: {  
							notEmpty: {  
								message: "截止面积不允许为空"  
							},
							remote: {
					            message: "和已存在的面积范围重复",
					            url: "${ctx}/admin/setMainItemPrice/checkArea",
					            data: function () {
					            	return {
					            		custType: $("#custType").val(), 
					            		oldFromArea: oldFromArea,
					            		oldToArea: oldToArea
					            	};
					            },
					            delay: 1000, 
					            type: "post", 
					        }
						}  
					},
					basePrice:{ 
						validators: {  
							notEmpty: {  
								message: "基准单价不允许为空"  
							},
						}  
					},
					baseArea:{
						validators: {  
							notEmpty: {  
								message: "基准面积不允许为空"  
							},
						}
					},
					unitPrice:{
						validators: {  
							notEmpty: {  
								message: "每平米单价不允许为空"  
							},
						}
					},
					baseToiletCount:{
						validators: {  
							notEmpty: {  
								message: "基准卫生间数不允许为空"  
							},
						}
					},
					toiletPrice:{
						validators: {  
							notEmpty: {  
								message: "卫生间单价不允许为空"  
							},
						}
					}
				}
			});

			$("#fromArea,#toArea,#basePrice,#baseArea,#unitPrice,#baseToiletCount,#toiletPrice").on("blur", function () {
				if ("" == $(this).val()) {
					$(this).val(0.0);
					$("#page_form").data("bootstrapValidator").resetForm();
					$("#page_form").data("bootstrapValidator").validate();
				}
			});
			/*$("#toArea").on("blur", function () {
				if ("" == $(this).val()) $(this).val(0.0);
			});
			$("#basePrice").on("blur", function () {
				if ("" == $(this).val()) $(this).val(0.0);
			});
			$("#baseArea").on("blur", function () {
				if ("" == $(this).val()) $(this).val(0.0);
			});
			$("#unitPrice").on("blur", function () {
				if ("" == $(this).val()) $(this).val(0.0);
			});
			$("#baseToiletCount").on("blur", function () {
				if ("" == $(this).val()) $(this).val(0.0);
			});
			$("#toiletPrice").on("blur", function () {
				if ("" == $(this).val()) $(this).val(0.0);
			});*/

			$("#custType").on("change", function () {
				$("#page_form").data("bootstrapValidator")  
					.updateStatus("fromArea", "NOT_VALIDATED", null)
					.updateStatus("toArea", "NOT_VALIDATED", null)
					.validateField("fromArea").validateField("toArea");
			});

			$("#saveBtn").on("click", function () {
				if ("V" == "${setMainItemPrice.m_umState}") return;
				$("#page_form").data("bootstrapValidator").validate();
				if(!$("#page_form").data("bootstrapValidator").isValid()) return;	
				var datas = $("#page_form").jsonForm();
				saveAjax(datas);
			});
			$("#closeBtn").on("click", function () {
				closeWin(false);
			});
		});

		function saveAjax(datas) {
			$.ajax({
				url:url,
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
							time: 700,
							beforeunload: function () {
								closeWin();
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
</body>
</html>
