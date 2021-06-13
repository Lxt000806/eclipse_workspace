<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<!DOCTYPE html>
<html>
<head>
<title>材料促销管理--新增</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp"%>
<script src="${resourceRoot}/pub/component_item.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
	$(function() {
		$("#itemCode").openComponent_item({
			showLabel:"${itemProm.itemDescr}",
			showValue:"${itemProm.itemCode}",
			callBack:function(data){
				$("#itemDescr").val(data.descr);
				$("#itemPrice").val(data.oldprice);
				$("#itemCost").val(data.oldcost);
			}
		});						
		$("#saveBtn").on("click", function() {
			var itemCode=$("#itemCode").val();
			var beginDate=$("#beginDate").val();
			var endDate=$("#endDate").val();
			var promCost=$("#promCost").val();
			var promPrice=$("#promPrice").val();
			var isExists=checkIsExists(itemCode);
			var isItemCode=checkIsItemCode(itemCode);
			if(beginDate==""){
				art.dialog({
					content : "请选择促销开始日期！",
				});
				return;
			}
			if(endDate==""){
				art.dialog({
					content : "请选择促销结束日期！",
				});
				return;
			}
			if(beginDate>endDate){
				art.dialog({
					content : "促销开始日期不能大于促销结束日期！",
				});
				return;
			}
			if(isExists){
				art.dialog({
					content : "该材料已存在未过期的促销记录！",
				});
				return;
			}
			if(!isItemCode){
				art.dialog({
					content : "该材料编码不存在,请重新输入！",
				});
				return;
			}
			if(isNaN(promCost) || isNaN(promPrice)){
				art.dialog({
					content : "促销价有错，不是有效的金额数据！请重新输入！",
				});
				return;
			}
			$("#dataForm").bootstrapValidator('validate');
			if (!$("#dataForm").data('bootstrapValidator').isValid())
				return;
			var datas = $("#dataForm").serialize();
			$.ajax({
				url : "${ctx}/admin/itemProm/doSave",
				type : "post",
				data : datas,
				dataType : "json",
				cache : false,
				error : function(obj) {
					showAjaxHtml({
						"hidden" : false,
						"msg" : "保存数据出错~"
					});
				},
				success : function(obj) {
					if (obj.rs) {
						art.dialog({
							content : obj.msg,
							time : 1000,
							beforeunload : function() {
								closeWin();
							}
						});
					} else {
						$("#_form_token_uniq_id").val(obj.token.token);
						art.dialog({
							content : obj.msg,
							width : 200
						});
					}
				}
			});
		});
	});
	//是否已存在促销记录
	function checkIsExists(itemCode){
		var isExists=false;
		$.ajax({
			url:"${ctx}/admin/itemProm/checkIsExists",
			type:"post",
			data:{itemCode:itemCode,pk:"${itemProm.pk }"},
			dataType:"json",
			cache:false,
			async:false,
			error:function(obj){
				showAjaxHtml({"hidden": false, "msg": "获取数据出错~"});
			},
			success:function(obj){
				if(obj=="1"){
					isExists=true;
				}
			}
		});
		return isExists;
	}
	//材料是否存在
	function checkIsItemCode(itemCode){
		var isItemCode=true;
		$.ajax({
			url:"${ctx}/admin/itemProm/checkIsItemCode",
			type:"post",
			data:{itemCode:itemCode},
			dataType:"json",
			cache:false,
			async:false,
			error:function(obj){
				showAjaxHtml({"hidden": false, "msg": "获取数据出错~"});
			},
			success:function(obj){
				if(obj=="0"){
					isItemCode=false;
				}
			}
		});
		return isItemCode;
	}
</script>
</head>
<body>
	<form action="" method="post" id="dataForm" class="form-search">
		<div class="body-box-form">
			<div class="panel panel-system">
				<div class="panel-body">
					<div class="btn-group-xs">
						<button type="button" class="btn btn-system" id="saveBtn">
							<span>保存</span>
						</button>
						<button type="button" class="btn btn-system" id="closeBut"
							onclick="closeWin(false)">
							<span>关闭</span>
						</button>
					</div>
				</div>
			</div>
			<div class="panel panel-info">
				<div class="panel-body">
					<input type="hidden" name="jsonString" value="" />
					<ul class="ul-form">
						<div class="validate-group row">
							<li><label>材料编码</label> <input type="text" id="itemCode"
								name="itemCode" />
							</li>
							<li><label>材料名称</label> <input type="text" id="itemDescr"
								name="itemDescr" style="width:160px;" readonly/>
							</li>
						</div>
						<div class="validate-group row">
							<li><label>现价</label> <input type="text" id="itemPrice"
								name="itemPrice" style="width:160px;" readonly
								/>
							</li>
							<li><label>成本价</label> <input type="text" id="itemCost"
								name="itemCost" style="width:160px;" readonly
								/>
							</li>
						</div>
					</ul>
				</div>
			</div>
			<div class="container-fluid" id="id_detail">
				<ul class="nav nav-tabs">
					<li class="active"><a href="#tab_main" data-toggle="tab">主项目</a>
					</li>
				</ul>
				<ul class="ul-form">
					<div class="tab-content">
						<div id="tab_main" class="tab-pane fade in active"><br>
							<div class="pageContent" style="height: 120px;">
								<form action="" method="post" id="page_form_1"
									class="form-search"
									style="padding-top: 25px;padding-left: 15px;">
									<ul class="ul-form">
										<div class="validate-group row">
											<li><label>促销价</label> <input type="text" id="promPrice"
												name="promPrice" style="width:160px;"
												onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\.\d]/g,'');"
												value="0" />
											</li>
											<li><label>促销进价</label> <input type="text" id="promCost"
												name="promCost" style="width:160px;"
												onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\.\d]/g,'');"
												value="0" />
											</li>
										</div>
										<div class="validate-group row">
											<li><label>促销开始日期</label> <input type="text"
												id="beginDate" name="beginDate" class="i-date"
												style="width:160px;"
												onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
												value="<fmt:formatDate value='${itemProm.beginDate}' pattern='yyyy-MM-dd'/>" />
											</li>
											<li><label>促销结束日期</label> <input type="text"
												id="endDate" name="endDate" class="i-date"
												style="width:160px;"
												onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
												value="<fmt:formatDate value='${itemProm.endDate}' pattern='yyyy-MM-dd'/>" />
											</li>
										</div>
									</ul>
								</form>
							</div>
						</div>
					</div>
				</ul>
			</div>
		</div>
	</form>
</body>
</html>
