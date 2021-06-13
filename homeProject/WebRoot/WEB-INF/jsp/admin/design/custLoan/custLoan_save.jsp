 <%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>添加CustLoan</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp"%>
<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
 //校验函数
$(function() {	
	$("#custCode").openComponent_customer({showLabel:"${custLoan.custDescr}",showValue:"${custLoan.custCode}",condition:{status:"3,4,5"} ,callBack: getData});
	function getData(data){
		if (!data) return;
		$("#statusDescr").removeAttr("disabled");
		$("#address").val(data.address),
		$("#statusDescr").val(data.status),
		$("#statusDescr").attr("disabled",true);
		validateRefresh('openComponent_customer_custCode'); 
	}
	$("#dataForm").bootstrapValidator("addField", "openComponent_customer_custCode", {  
	     validators: {  
	         remote: {
	          message: '',
	          url: '${ctx}/admin/customer/getCustomer',
	          data: getValidateVal,  
	          delay: 4 //这里特别要说明，必须要加此属性，否则用户输入一个字就会访问后台一次，会消耗大量的系统资源，
	      }
	     }  
	 });
	$("#dataForm").bootstrapValidator({
		message : 'This value is not valid',
		feedbackIcons : {/*input状态样式图片*/
			validating : 'glyphicon glyphicon-refresh'
		},
		fields: {  
			custCode:{  
				validators: {  
					 notEmpty: {  
						message: '客户编号不能为空'  
					},
					stringLength: {
                           max: 20,
                           message: '长度不超过20个字符'
                    }
				}  
			},	
			followRemark: {
		        validators: { 
		        	stringLength:{
	               	 	min: 0,
	          			max: 400,
	               		message:'需跟踪长度必须在0-400之间' 
	               	}
		        }
      		},
      		ConfuseRemark: {
		        validators: { 
		        	stringLength:{
	               	 	min: 0,
	          			max: 400,
	               		message:'退件拒批长度必须在0-400之间' 
	               	}
		        }
      		},	
		},
		submitButtons : 'input[type="submit"]'/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
	});
});

function save(){
	if ($.trim($("#custCode").val())==""){
		art.dialog({
			content: "客户编号不能为空！",
			width: 200
		});
		return false;
	}
	$("#dataForm").bootstrapValidator('validate');
	if(!$("#dataForm").data('bootstrapValidator').isValid()) return;
 	var datas = $("#dataForm").serialize();	
	$.ajax({
		url:'${ctx}/admin/custLoan/doSave',
		type: 'post',
		data: datas,
		dataType: 'json',
		cache: false,
		error: function(obj){
			showAjaxHtml({"hidden": false, "msg": '保存数据出错~'});
	    },
	    success: function(obj){
	    	if(obj.rs){
	    		art.dialog({
					content: obj.msg,
					time: 3000,
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
</head>
<body>
	<div class="body-box-form">
		<div class="panel panel-system">
			<div class="panel-body">
				<div class="btn-group-xs">
					<button id="saveBut" type="button" class="btn btn-system " onclick="save()">保存</button>
					<button id="closeBut" type="button" class="btn btn-system " onclick="closeWin()">关闭</button>
				</div>
			</div>
		</div>
		<div class="panel panel-info">
			<div class="panel-body">
				<form action="" method="post" id="dataForm" class="form-search">
					<ul class="ul-form">
						<div class="validate-group">
							<li class="form-validate"><label><span class="required">*</span>客户编号</label> <input type="text"
								id="custCode" name="custCode" value="${custLoan.custCode}" />
							</li>
							<li class="form-validate"><label style="color:#777777">楼盘</label> <input type="text" id="address"
								name="address" value="${custLoan.address}" readonly="readonly" />
							</li>
						</div>
						<div class="validate-group">
							<li class="form-validate"><label style="color:#777777">客户状态</label> <house:xtdm id="statusDescr"
									dictCode="CUSTOMERSTATUS" value="${custLoan.statusDescr }"></house:xtdm> 
							</li>
							<li class="form-validate"><label>协议提交日期</label> <input type="text" id="agreeDate" name="agreeDate"
								class="i-date" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
								value="<fmt:formatDate value='${custLoan.agreeDate }' pattern='yyyy-MM-dd'/>" />
							</li>
						</div>
						<div class="validate-group">
							<li class="form-validate"><label>贷款银行</label> <input type="text" id="bank" name="bank"
								 value="${custLoan.bank}" />
							</li>
							<li class="form-validate"><label>贷款总额</label> <input type="text" id="amount" name="amount"
								 onkeyup="value=value.replace(/[^\-?\d.]/g,'')" value="${custLoan.amount}"/> 
								<span>万元</span>
							</li>
						</div>
						<div class="validate-group">
							<li class="validate-group"><label> 首次放款时间</label> <input type="text" id="firstDate"
								name="firstDate" class="i-date" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
								value="<fmt:formatDate value='${custLoan.firstDate}' pattern='yyyy-MM-dd'/>" />
							</li>
							<li class="form-validate"><label>首次放款金额</label> <input type="text" id="firstAmount"
								name="firstAmount"  onkeyup="value=value.replace(/[^\-?\d.]/g,'')"
								value="${custLoan.firstAmount}" />
								<span>万元</span>
							</li>
						</div>
						<div class="validate-group">
							<li class="form-validate"><label> 二次放款时间</label> <input type="text" id="secondDate"
								name="secondDate" class="i-date" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
								value="<fmt:formatDate value='${custLoan.secondDate}' pattern='yyyy-MM-dd'/>" />
							</li>
							<li class="form-validate"><label>二次放款金额</label> <input type="text" id="secondAmount"
								name="secondAmount"  onkeyup="value=value.replace(/[^\-?\d.]/g,'')"
								value="${custLoan.secondAmount}" />
								<span>万元</span>
								
							</li>
						</div>
						<li class="form-validate"><label class="control-textarea">已签约待放款</label> <textarea id="signRemark"
								name="signRemark">${custLoan.signRemark}</textarea>
						</li>
						<li class="form-validate"><label class="control-textarea">退件拒批</label> <textarea id="confuseRemark"
								name="confuseRemark">${custLoan.confuseRemark}</textarea>
						</li>
						<li class="form-validate"><label class="control-textarea">需跟踪</label> <textarea id="followRemark"
								name="followRemark">${custLoan.followRemark}</textarea>
						</li>
						<li class="form-validate"><label class="control-textarea">备注</label> <textarea id="Remark"
								name="Remark">${custLoan.remark}</textarea>
						</li>
					</ul>
				</form>
			</div>
		</div>
	</div>
</body>
</html>

