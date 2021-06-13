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
	<script src="${resourceRoot}/pub/component_customerOA.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_supplier.js?v=${v}" type="text/javascript"></script>
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
					<input type="hidden" id="pk" name="pk" value="${intProduce.pk}"/>
					<input type="hidden" id="lastUpdatedBy" name="lastUpdatedBy" value="${sessionScope.USER_CONTEXT_KEY.czybh}"/>
					<input type="hidden" id="expired" name="expired" value="${intProduce.expired}"/>
					<input type="hidden" id="supplCode" name="supplCode" value="${supplier.code}"/>
					<ul class="ul-form">
						<div class="row">
							<div class="col-xs-12">
								<li class="form-validate">
									<label><span class="required">*</span>客户编号</label>
									<input type="text" name="custCode" id="custCode">
								</li>
								<li>
                                    <label>楼盘</label>
                                    <input type="text" name="address" id="address" readonly="readonly">
                                </li>
								<li class="form-validate">
									<label><span class="required">*</span>供应商</label>
									<input type="text" name="supplier" id="supplier" 
										value="${supplier.code}|${supplier.descr}" readonly="true">
								</li>
								<li class="form-validate">
									<label><span class="required">*</span>是否橱柜</label>
									<house:xtdm id="isCupboard" dictCode="YESNO" style="width:160px;" 
										value="${intProduce.isCupboard}"/>
								</li>
								<li class="form-validate">
									<label>定板时间</label>
									<input type="text" id="setBoardDate" name="setBoardDate" class="i-date" style="width:160px;" 
										onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
										value="<fmt:formatDate value='${intProduce.setBoardDate}' pattern='yyyy-MM-dd'/>"/>
								</li>
								<li class="form-validate">
									<label>到板时间</label>
									<input type="text" id="arrBoardDate" name="arrBoardDate" class="i-date" style="width:160px;" 
										onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
										value="<fmt:formatDate value='${intProduce.arrBoardDate}' pattern='yyyy-MM-dd'/>"/>
								</li>
								<li class="form-validate">
									<label>开料时间</label>
									<input type="text" id="openMaterialDate" name="openMaterialDate" class="i-date" style="width:160px;" 
										onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
										value="<fmt:formatDate value='${intProduce.openMaterialDate}' pattern='yyyy-MM-dd'/>"/>
								</li>
								<li class="form-validate">
									<label>封边时间</label>
									<input type="text" id="sealingSideDate" name="sealingSideDate" class="i-date" style="width:160px;" 
										onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
										value="<fmt:formatDate value='${intProduce.sealingSideDate}' pattern='yyyy-MM-dd'/>"/>
								</li>
								<li class="form-validate">
									<label>排孔时间</label>
									<input type="text" id="exHoleDate" name="exHoleDate" class="i-date" style="width:160px;" 
										onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
										value="<fmt:formatDate value='${intProduce.exHoleDate}' pattern='yyyy-MM-dd'/>"/>
								</li>
								<li class="form-validate">
									<label>包装时间</label>
									<input type="text" id="packDate" name="packDate" class="i-date" style="width:160px;" 
										onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
										value="<fmt:formatDate value='${intProduce.packDate}' pattern='yyyy-MM-dd'/>"/>
								</li>
								<li class="form-validate">
									<label>入库时间</label>
									<input type="text" id="inWhDate" name="inWhDate" class="i-date" style="width:160px;" 
										onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
										value="<fmt:formatDate value='${intProduce.inWhDate}' pattern='yyyy-MM-dd'/>"/>
								</li>
							</div>
						</div>
					</ul>	
				</form>
			</div>
		</div>
	</div>
	<script type="text/javascript" defer>
		var url = "";
		$(function() {
			switch ("${intProduce.m_umState}") {
				case "M":
					url = "${ctx}/admin/supplierItemApp/doIntProduceUpdate";
					$("#custCode").openComponent_customerOA({
						showValue:"${intProduce.custCode}",
						showLabel:"${intProduce.custName}",
						condition:{
							status: "4", //只能选择合同施工状态
							showAll: "0"
						},
						callBack: function(data) {
                            validateRefresh_customer()
                            setAddressFromCustCode(data)
                        },
						disabled:true
					});
					$("#address").val("${intProduce.address}")
					$("#isCupboard").prop("disabled", true);
					if ("" != "${intProduce.inWhDate}") {
						disabledForm();
						$("#saveBtn").remove();
					}
					break;
				default:
					$("#custCode").openComponent_customerOA({
						condition:{
							status: "4",
							showAll: "0" 
						},
						callBack: function(data) {
						    validateRefresh_customer()
						    setAddressFromCustCode(data)
						}
					});
					url = "${ctx}/admin/supplierItemApp/doIntProduceSave";
					break;
			}
			$("#page_form").bootstrapValidator({
				message : "请输入完整的信息",
				feedbackIcons : {
					validating : "glyphicon glyphicon-refresh"
				},
				fields: { 
					openComponent_customerOA_custCode: {
						validators: {
							notEmpty: {
								message: "楼盘不允许为空"
							},
							remote: {
                                message: "请通过搜索图标查询楼盘地址",
                                url: '${ctx}/admin/customer/getCustomer',
                                data: getValidateVal,  
                                delay: 4 //这里特别要说明，必须要加此属性，否则用户输入一个字就会访问后台一次，会消耗大量的系统资源，
                            }
						}
					},
					openComponent_supplier_supplCode: {
						validators: {
							notEmpty: {
								message: "供应商不允许为空"
							}
						}
					},
					isCupboard: {
						validators: {
							notEmpty: {
								message: "是否橱柜不允许为空"
							}
						}
					}
				}
			});

			$("#saveBtn").on("click", function () {
				if ("V" == "${intProduce.m_umState}") return;
				$("#page_form").data("bootstrapValidator").validate();
				if(!$("#page_form").data("bootstrapValidator").isValid()) return;	
				var datas = $("#page_form").jsonForm();
				saveAjax(datas);
			});
			$("#closeBtn").on("click", function () {
				closeWin();
			});
		});

		function validateRefresh_customer() {
			$("#page_form").data("bootstrapValidator")  
				.updateStatus("openComponent_customerOA_custCode", "NOT_VALIDATED", null)
				.validateField("openComponent_customerOA_custCode");
		}
		
		function setAddressFromCustCode(data) {
		    $("#address").val(data.address)
		}

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
