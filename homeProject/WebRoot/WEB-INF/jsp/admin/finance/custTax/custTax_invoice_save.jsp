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
	<style type="text/css">  
        /*把input放在select上*/  
        #taxPer,#taxService{  
            position: absolute;  
            width: 142px;  
            height: 20px;  
            left: 133px;  
            top: 2px;  
            border-bottom: 0px;  
            border-right: 0px;  
            border-left: 0px;  
            border-top: 0px;  
        }  
    </style>  
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
					<ul class="ul-form">
						<div class="row">
							<li class="form-validate">
								<label><span class="required">*</span>开票日期</label>
								<input type="text" id="date" name="date" class="i-date" style="width:160px;" 
									onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
									value=""/>
							</li>
							<li class="form-validate">
								<label><span class="required">*</span>发票号</label>
								<input type="text" id="invoiceNo" name="invoiceNo" style="width:160px;" value=""/>
							</li>
						</div>
						<div class="row">
							<li class="form-validate">
								<label>发票代码</label>
								<input type="text" id="invoiceCode" name="invoiceCode" style="width:160px;" value=""/>
							</li>
							<li><label>应税服务名称</label> <select style="width:160px;" id="selectTaxService"
								onchange="document.getElementById('taxService').value=this.value">
									<option value="工程款">工程款</option>
									<option value="设计费">设计费</option>
							</select> <input id="taxService" name="taxService" type="text" value="工程款">
							</li>
						</div>
						<div class="row">
							<li class="form-validate">
								<label>购买方</label>
								<input type="text" id="buyer" name="buyer" style="width:160px;" value="${custDescr }"/>
							</li>
							<li class="form-validate">
								<label><span class="required">*</span>开票金额</label>
								<input type="text" id="amount" name="amount" style="width:160px;"
									onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\-\.\d]/g,'');" value="0"/>
							</li>
						</div>
						<div class="row">
							<li><label>税率</label> <select style="width:160px;" id="selectTaxPer"
								onchange="document.getElementById('taxPer').value=this.value;count();">
									<option value="0.03">3%</option>
									<option value="0.05">5%</option>
									<option value="0.06">6%</option>
									<option value="0.09">9%</option>
									<option value="0.1">10%</option>
									<option value="0.11">11%</option>
									<option value="0.16">16%</option>
									<option value="0.17">17%</option>
							</select> <input id="taxPer" name="taxPer" type="text"
								onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\.\d]/g,'');"
								value="0">
							</li>
							<li>
								<label>不含税金额</label>
								<input type="text" id="noTaxAmount" name="noTaxAmount" style="width:160px;"
									onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\.\d]/g,'');" value="0" readonly="readonly"/>
							</li>
						</div>
						<div class="row">
							<li>
								<label>税额</label>
								<input type="text" id="taxAmount" name="taxAmount" style="width:160px;"
									onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\.\d]/g,'');" value="0" readonly="readonly"/>
							</li>
						</div>
						<div class="row">
							<li>
								<label class="control-textarea" for="remarks">备注</label>
								<textarea id="remarks" name="remarks" style="overflow-y:scroll; overflow-x:hidden; height:75px; "></textarea>
							</li>
						</div>
					</ul>
				</form>
			</div>
		</div>
	</div>
</body>	
<script type="text/javascript"> 
$(function() {
	$("#page_form").bootstrapValidator({
		message : "请输入完整的信息",
		feedbackIcons : {/*input状态样式图片*/
			validating : "glyphicon glyphicon-refresh"
		},
		fields: {  
			date:{  
				validators: {  
					notEmpty: {  
						message: "开票日期不能为空"  
					}
				}  
			},
			invoiceNo:{  
				validators: {  
					notEmpty: {  
						message: "发票号不能为空"  
					},
					/*callback: {
						message: "发票号重复",
						callback: function(iNo){
							if ("${keys}" == "") {
								return true;
							}
							// String 转 array
							var keys = "${keys}".split(",");
							for (var i in keys) {
								if (keys[i] == iNo) {
									return false;		
								}
							}
							return true;
						}
					}*/
				}  
			},
			amount:{  
				validators: {  
					notEmpty: {  
						message: "开票金额不能为空"  
					}
				}  
			}
		},
		submitButtons : "input[type='submit']"
	});

	// 根据开票金额和税率变动
	$("#amount").on("blur",function(){
		count();
	});

	$("#taxPer").on("blur",function(){
		count();
	});

	$("#saveBtn").on("click",function(){
 		var selectRows = [];
		var datas=$("#page_form").jsonForm();
		var dataTable_json = ${keys};
		$("#page_form").bootstrapValidator("validate");
		if(!$("#page_form").data("bootstrapValidator").isValid()){ 
			art.dialog({content: "无法保存，请输入完整的信息",width: 220});
			return;
		}
		if ([] != dataTable_json) {
			for (var i in dataTable_json) {
				var json_i = dataTable_json[i];
				if (datas.invoiceNo == json_i.InvoiceNo && 
					datas.taxService == $.trim(json_i.TaxService)) {
					art.dialog({content: "发票号+应税服务名称 重复",width: 220});
					return false;
				};
			}
		}
		// 将page_form json化后赋给selectRows
	 	selectRows.push(datas);
		Global.Dialog.returnData = selectRows;
		closeWin();
	});
	//input修改后立即赋值给select，防止选择相同数据无法触发onchange事件
	$('#taxPer').bind('input propertychange', function() {  
		$("#selectTaxPer").val($("#taxPer").val());
	});
	$('#taxService').bind('input propertychange', function() {  
		$("#selectTaxService").val($("#taxService").val());
	});
});
//计算不含税金和税额
function count(){
	var amount = parseFloat($("#amount").val());// 转变成float，否则会认为是字符串
	var taxPer = parseFloat($("#taxPer").val());
	var noTaxAmount = amount/(1.0 + taxPer);// 开票金额/（1+税率）
	noTaxAmount = Math.round(noTaxAmount * 100) / 100;// 省略小数后两位，round会省略小数，可以通过100省略之后再乘100的方式获得
	var taxAmount = amount - noTaxAmount;// 开票金额-不含税金额
	taxAmount = Math.round(taxAmount * 100) / 100;
	$("#noTaxAmount").val(noTaxAmount);
	$("#taxAmount").val(taxAmount);
}
</script>
</html>
