<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<!DOCTYPE html>
<html lang="zh">
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
<script type="text/javascript">
$(function() {
	$("#dataForm").bootstrapValidator({
		excluded:[":disabled"],
	    message: 'This value is not valid',
        feedbackIcons : {/*input状态样式图片*/
             invalid: 'glyphicon glyphicon-remove',
             validating : 'glyphicon glyphicon-refresh'
        },
        fields: {
	      	 procedurefee: {
		        validators: { 
		            notEmpty: { 
		            	message: '手续费不能为空'  
		            },
		             numeric: {
		            	message: '手续费只能是数字' 
		            },
		        }
	      	},
		},
    	submitButtons : 'input[type="submit"]'/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
	});	
});
function save(){
	$("#dataForm").bootstrapValidator("validate");
	if(!$("#dataForm").data("bootstrapValidator").isValid()) return;
	//验证
	var datas = $("#dataForm").serialize();
	$.ajax({
		url:'${ctx}/admin/custPayTran/doUpdateProcedureFee',
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
					time: 1000,
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
//修改pos
function changePos(){
	var posCode=$("#posCode").val();
	if(posCode=="")
		$("#procedureFee").val(0.0);
	else	
	$.ajax({
		url : "${ctx}/admin/custPay/findBankPos",
		type : "post",
		data : {
			'code' : posCode
		},
		dataType : "json",
		cache : false,
		async : false,
		error : function(obj) {
			showAjaxHtml({
				"hidden" : false,
				"msg" : "保存数据出错~"
			});
		},
		success : function(obj) {
			if (obj.length > 0) {
				var MinFee=parseFloat(obj[0].MinFee);
				var MaxFee=parseFloat(obj[0].MaxFee);
				var FeePerc=parseFloat(obj[0].FeePerc);
				
				var AcquireFeePerc=parseFloat(obj[0].AcquireFeePerc);
				var amount=$("#payamount").val();
				if(amount<0){
					$("#procedureFee").val(0);
					return;
				}
				var ProcedureFee=FeePerc*1000*amount/1000;
				if(ProcedureFee<MinFee){
					ProcedureFee=MinFee;
				}
				if(ProcedureFee>MaxFee){
					ProcedureFee=MaxFee;
				}
				if(ProcedureFee<0){
					ProcedureFee=0;
				}
				ProcedureFee+=AcquireFeePerc*amount;
				$("#procedureFee").val(Math.round(accMul(ProcedureFee,100)) / 100);
			} 
		}
	});
}
//修复js小数乘法精度
function accMul(arg1,arg2){
	var m=0,s1=arg1.toString(),s2=arg2.toString();
	try{m+=s1.split(".")[1].length;}catch(e){}
	try{m+=s2.split(".")[1].length;}catch(e){}
	return (Number(s1.replace(".",""))*Number(s2.replace(".",""))/Math.pow(10,m)).toFixed(10);
}
</script>		
</head>
<body>
	<div class="body-box-form">
		<div class="panel panel-system">
			<div class="panel-body">
				<div class="btn-group-xs">
					<button id="saveBtn" type="submit" class="btn btn-system " onclick="save()">保存</button>
					<button id="closeBut" type="button" class="btn btn-system " onclick="closeWin(false)">关闭</button>
				</div>
			</div>
		</div>
		<div class="panel panel-info" style="margin-bottom: 10px;">
			<div class="panel-body">
				<form action="" method="post" id="dataForm" class="form-search">
					<house:token></house:token>
					<input type="hidden" name="jsonString" value="" />
					<input type="hidden" id="pk" name="pk" value="${map.pk}" />
					<ul class="ul-form">
						<li><label>客户编号</label> <input type="text" id="custcode"
							name="custcode" style="width:160px;" readonly="readonly"
							value="${map.custcode}">
						</li>
						<li><label>楼盘地址</label> <input type="text" id="address"
							name="address" style="width:160px;" readonly="readonly"
							value="${map.address}">
						</li>
						<li><label>操作员编号</label> <input type="text" id="appczy"
							name="appczy" style="width:160px;" readonly="readonly"
							value="${map.appczy}">
						</li>
						<li><label>状态</label> <input type="text" id="statusdescr"
							name="statusdescr" style="width:160px;" readonly="readonly"
							value="${map.statusdescr}">
						</li>
						<li><label>类型</label> <input type="text"
							id="typedescr" name="typedescr" style="width:160px;"
							readonly="readonly" value="${map.typedescr}">
						</li>
						<li><label>付款日期</label> <input type="text" id="date"
							name="date" style="width:160px;" readonly="readonly"
							value="${map.date}">
						</li>
						<li><label>交易总金额</label> <input type="text" id="tranamount"
							name="tranamount" style="width:160px;" readonly="readonly"
							value="${map.tranamount}">
						</li>

						<li><label>实际扣款金额</label> <input type="text" id="payamount"
							name="payamount" style="width:160px;" readonly="readonly"
							value="${map.payamount}" />
						</li>
						<li><label>收款账号</label> <input type="text" id="rcvact"
							name="rcvact" style="width:160px;" readonly="readonly"
							value="${map.rcvact}">
						</li>
						<li class="form-validate"><label><span class="required">*</span>手续费</label> <input type="text"
							id="procedureFee" name="procedureFee" style="width:160px;"
							value="${map.procedurefee}" onkeyup="value=value.replace(/[^\-?\d.]/g,'')" >
						</li>
						<li><label>登记日期</label> <input type="text" id="adddate"
							name="adddate" style="width:160px;" readonly="readonly"
							value="${map.adddate}">
						</li>
						<li><label>收款单号</label> <input type="text" id="payno"
							name="payno" style="width:160px;" readonly="readonly"
							value="${map.payno}">
						</li>
						<li><label>卡号</label> <input type="text" id="cardid"
							name="cardid" style="width:160px;" readonly="readonly"
							value="${map.cardid}">
						</li>
						<li><label>发卡行编码</label> <input type="text" id="bankcode"
							name="bankcode" style="width:160px;" readonly="readonly"
							value="${map.bankcode}">
						</li>
						<li><label>发卡行名称</label> <input type="text" id="bankname"
							name="bankname" style="width:160px;" readonly="readonly"
							value="${map.bankname}">
						</li>
						<li><label>凭证号</label> <input type="text" id="traceno"
							name="traceno" style="width:160px;" readonly="readonly"
							value="${map.traceno}">
						</li>
						<li><label>参考号</label> <input type="text" id="referno"
							name="referno" style="width:160px;" readonly="readonly"
							value="${map.referno}">
						</li>
						<li><label>客户签名</label> <input type="text" id="issigndescr"
							name="issigndescr" style="width:160px;" readonly="readonly"
							value="${map.issigndescr}">
						</li>
						<li><label>最后打印人员</label> <input type="text"
							id="printczy" name="printczy" style="width:160px;"
							readonly="readonly" value="${map.printczy}">
						</li>
						<li><label>最后打印时间</label> <input type="text"
							id="printdate" name="printdate"
							style="width:160px;" readonly="readonly"
							value="${map.printdate}">
						</li>
						<li><label>异常说明</label> <input type="text" id="exceptionremarks"
							name="exceptionremarks" style="width:451px;" readonly="readonly"
							value="${map.exceptionremarks}">
						</li>
						<li>
							<label>卡性质</label> 
							<house:xtdm id="cardAttr" dictCode="CARDATTR" style="width:160px;" value="${map.cardattr}"/>
						</li>
						<li class="form-validate">
							<label>POS机</label> 
							<house:dict id="posCode"
	                               dictCode=""
	                               sql="select Code,Code+' '+Descr Descr from tBankPos where Expired='F' and RcvAct=${map.rcvactcode}"
	                               sqlValueKey="Code" sqlLableKey="Descr"
	                               value="${map.poscode}" onchange="changePos()">
	                        </house:dict>
                        </li>
                        <li>
							<label>付款方式</label> 
							<house:xtdm id="payType" dictCode="POSPAYTYPE" style="width:160px;" value="${map.paytype}" disabled="true"/>
						</li>
					</ul>
				</form>
			</div>
		</div>
	</div>
</body>
</html>
