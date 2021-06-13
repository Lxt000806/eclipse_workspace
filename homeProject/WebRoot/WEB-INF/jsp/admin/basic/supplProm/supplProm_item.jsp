<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8"/>
	<META HTTP-EQUIV="pragma" CONTENT="no-cache"/>
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"/>
	<META HTTP-EQUIV="expires" CONTENT="0"/>
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge"/>
	<%@ include file="/commons/jsp/common.jsp"%>
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
					<button type="button" class="btn btn-system " id="getItemInfo">
						<span>从材料信息引入</span>
					</button>
				</div>
			</div>
		</div>
		<div class="panel panel-info" style="margin: 0px;">  
			<div class="panel-body">
				<form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
					<house:token></house:token><!-- 按钮只能点一次 -->
					<input type="hidden" id="lastupdatedby" name="lastupdatedby" value="${sessionScope.USER_CONTEXT_KEY.czybh}"/>
					<input type="hidden" id="expired" name="expired" value="${supplPromItem.expired}"/>
					<ul class="ul-form">
						<div class="row">
							<div class="col-xs-6">
								<li class="form-validate">
									<label><span class="required">*</span>品牌</label>
									<input type="text" id="itemtype3" name="itemtype3" style="width:160px;"
										value="${supplPromItem.itemType3}" />
								</li>
								<li class="form-validate">
									<label>品名</label>
									<input type="text" id="itemdescr" name="itemdescr" style="width:160px;"
										value="${supplPromItem.itemDescr}" />
								</li>
								<li class="form-validate">
									<label><span class="required">*</span>型号</label>
									<input type="text" id="model" name="model" style="width:160px;"
										value="${supplPromItem.model}" />
								</li>
								<li class="form-validate">
									<label>单位</label>
									<input type="text" id="uom" name="uom" style="width:160px;"
										value="${supplPromItem.uom}" />
								</li>
								<li class="form-validate">
									<label>规格</label>
									<input type="text" id="itemsize" name="itemsize" style="width:160px;"
										value="${supplPromItem.itemSize}" />
								</li>
							</div>
							<div class="col-xs-6">
								<li class="form-validate">
									<label><span class="required">*</span>有家直供价</label>
									<input type="text" id="unitprice" name="unitprice" style="width:160px;" 
										onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\.\d]/g,'');"
										onafterpaste="this.value =this.value.replace(/\s/g,'').replace(/[^\.\d]/g,'');"
										value="${supplPromItem.unitPrice}"/>
								</li>
								<li class="form-validate">
									<label><span class="required">*</span>活动价</label>
									<input type="text" id="promprice" name="promprice" style="width:160px;" 
										onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\.\d]/g,'');"
										onafterpaste="this.value =this.value.replace(/\s/g,'').replace(/[^\.\d]/g,'');"
										value="${supplPromItem.promPrice}"/>
								</li>
								<li class="form-validate">
									<label><span class="required">*</span>常规成本</label>
									<input type="text" id="cost" name="cost" style="width:160px;" 
										onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\.\d]/g,'');"
										onafterpaste="this.value =this.value.replace(/\s/g,'').replace(/[^\.\d]/g,'');"
										value="${supplPromItem.cost}"/>
								</li>
								<li class="form-validate">
									<label><span class="required">*</span>活动成本</label>
									<input type="text" id="promcost" name="promcost" style="width:160px;" 
										onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\.\d]/g,'');"
										onafterpaste="this.value =this.value.replace(/\s/g,'').replace(/[^\.\d]/g,'');"
										value="${supplPromItem.promCost}"/>
								</li>
								<!-- <li id="expired_li">
									<label>过期</label>
									<input type="checkbox" id="expired_show" name="expired_show" value="${supplPromItem.expired}" 
										onclick="checkExpired(this)" ${supplPromItem.expired=="T"?"checked":""}/>
								</li> -->
							</div>
							<div class="col-xs-12">
								<li style="max-height: 120px;">
									<label class="control-textarea" style="top: -60px;">备注</label>
									<textarea id="remarks" name="remarks" style="height: 80px;">${supplPromItem.remarks}</textarea>
								</li>
							</div>
						</div>
					</ul>	
				</form>
			</div>
		</div>
	</div>
</body>
	<script type="text/javascript">
		var m_umState = "${supplPromItem.m_umState}", selectRows = [];
		$(function() {
			switch (m_umState) {
			case "A":
				// $("#expired_li").remove();
				break;
			case "M":
				$("#getItemInfo").remove();
				break;
			default:
				$("#saveBtn").remove();
				$("#getItemInfo").remove();
				disabledForm();
				break;
			}
			$("#page_form").bootstrapValidator({
				message : "请输入完整的信息",
				feedbackIcons : {
					validating : "glyphicon glyphicon-refresh"
				},
				fields: { 
					itemtype3: { 
						validators: {  
							notEmpty: {  
								message: "材料类型3不允许为空"
							},
						}  
					},
					model: {
						validators: {
							notEmpty: {
								message: "型号不允许为空"
							}
						}
					},
					unitprice: { 
						validators: {  
							notEmpty: {
								message: "常规价不允许为空"
							},
						}  
					},
					promprice: { 
						validators: {  
							notEmpty: {
								message: "促销价不允许为空"
							},
							greaterThan: {
								message: "促销价要大于0",
								value: 0.0,
								inclusive: false,
							}
						}  
					},
					cost: { 
						validators: {  
							notEmpty: {
								message: "常规成本不允许为空"
							},
						}  
					},
					promcost: { 
						validators: {  
							notEmpty: {
								message: "促销成本不允许为空"
							},
							greaterThan: {
								message: "促销成本要大于0",
								value: 0.0,
								inclusive: false,
							}
						}  
					}
				}
			});
			$("#unitprice").on("blur",function () {
				if ($(this).val()=="") {
					$(this).val(0);
					$("#page_form").data("bootstrapValidator")
						.updateStatus("unitprice", "NOT_VALIDATED", null)
						.validateField("unitprice");
				}
			});
			/*$("#promprice").on("blur",function () {
				if ($(this).val()=="") {
					$(this).val(0);
					$("#page_form").data("bootstrapValidator")
						.updateStatus("promprice", "NOT_VALIDATED", null)
						.validateField("promprice");
				}
			});*/
			$("#cost").on("blur",function () {
				if ($(this).val()=="") {
					$(this).val(0);
					$("#page_form").data("bootstrapValidator")
						.updateStatus("cost", "NOT_VALIDATED", null)
						.validateField("cost");
				}
			});
			/*$("#promcost").on("blur",function () {
				if ($(this).val()=="") {
					$(this).val(0);
					$("#page_form").data("bootstrapValidator")
						.updateStatus("promcost", "NOT_VALIDATED", null)
						.validateField("promcost");
				}
			});*/
			$("#unitprice").trigger("blur");
			// $("#promprice").trigger("blur");
			$("#cost").trigger("blur");
			// $("#promcost").trigger("blur");
			$("#saveBtn").on("click", function () {
				if ("V" == m_umState) return;
				$("#page_form").bootstrapValidator("validate");/* 提交验证 */
				if(!$("#page_form").data("bootstrapValidator").isValid()) return; /* 验证失败return */
				var datas = $("#page_form").jsonForm();
				selectRows.push(datas);
				Global.Dialog.returnData = selectRows;
				closeWin();
			});
			$("#closeBtn").on("click", function () {
				closeWin();
			});
			$("#getItemInfo").on("click", function() {
				if ("A" != m_umState) return;
				$("#itemtype3").val("");
				$("#model").val("");
				$("#itemsize").val("");
				$("#uom").val("");
				$("#unitprice").val(0);
				$("#cost").val(0);
				Global.Dialog.showDialog("getItemInfo",{
					title: "材料信息导入",
					url: "${ctx}/admin/item/goCode",
					height: 691,
					width: 968,
					returnFun: function (data) {
						$("#itemtype3").val(data.sqldescr);
						$("#model").val(data.model);
						$("#itemsize").val(data.sizedescr);
						$("#uom").val(data.uomdescr);
						$("#unitprice").val(data.price);
						$("#cost").val(data.cost);
					}
				});
			});
		});
	</script>
</html>
