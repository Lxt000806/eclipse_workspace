<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>
<%
UserContext uc = (UserContext)request.getSession().getAttribute(CommonConstant.USER_CONTEXT_KEY);
String czylb=uc.getCzylb();
String czybh="";
if("2".equals(czylb)) czybh=uc.getCzybh();
%>
<!DOCTYPE html>
<html>
<head>
	<title>添加</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_wareHouse.js?v=${v}" type="text/javascript"></script>
    <script src="${resourceRoot}/pub/component_supplier.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
$(function() {
	changeSendType();
	
	Global.LinkSelect.initSelect("${ctx}/admin/item/itemTypeByAuthority","itemType1","itemType2","itemType3");
	
    $("#supplCode").openComponent_supplier({
        showValue: '${sendFeeRule.supplCode}'
    });
    $("#openComponent_supplier_supplCode").blur();

    $("#wHCode").openComponent_wareHouse({
        showValue: '${sendFeeRule.wHCode}'
    });
    $("#openComponent_wareHouse_wHCode").blur();
	
	$("#dataForm").bootstrapValidator({
		message : 'This value is not valid',
		feedbackIcons : {/*input状态样式图片*/
		},
		fields: {  
			itemType1:{  
				validators: {  
					notEmpty: {  
						message: '材料类型1不能为空'  
					}
				}  
			},
			itemType2:{  
				validators: {  
					notEmpty: {  
						message: '材料类型2不能为空'  
					}
				}  
			},
			price:{  
				validators: {  
					notEmpty: {  
						message: '配送单价不能为空'  
					}
				}  
			},
			sendType:{  
				validators: {  
					notEmpty: {  
						message: '发货类型不能为空'  
					}
				}  
			},
			smallSendType:{  
				validators: {  
					notEmpty: {  
						message: '少量配送类型不能为空'  
					}
				}  
			},
			smallSendMaxValue:{  
				validators: {  
					notEmpty: {  
						message: '少量配送最大值不能为空'  
					}
				}  
			},
			smallSendFeeAdj:{  
				validators: {  
					notEmpty: {  
						message: '少量配送补贴不能为空'  
					}
				}  
			},
			calType:{  
				validators: {  
					notEmpty: {  
						message: '计算类型不能为空'  
					}  
				}  
			},
			beginTimes:{  
				validators: {  
					integer: {  
						message: '起始次数需为整数'  
					}  
				}  
			},
			endTimes:{  
				validators: {  
					integer: {  
						message: '截止次数需为整数'  
					}  
				}  
			},
			cardAmount:{  
				validators: {  
					numeric: {  
						message: '初始金额需为数字'  
					}  
				}  
			},
			incValue:{  
				validators: {  
					numeric: {  
						message: '递增值需为数字'  
					}  
				}  
			},
		},
		submitButtons : 'input[type="submit"]'/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
	});
	
	if("${sendFeeRule.m_umState}"=="V"){
		$("#saveBtn,input,textarea,select,#addItem,#updateItem,#delItem").attr("disabled",true);
	}
	
});
		
function save(){
	$("#dataForm").bootstrapValidator('validate'); //验证表单
	if(!$("#dataForm").data('bootstrapValidator').isValid()) return; //验证表单
	var rets = $("#dataTable").jqGrid("getRowData");
	var params = {"sendFeeRuleItemJson": JSON.stringify(rets)};
	Global.Form.submit("dataForm","${ctx}/admin/sendFeeRule/doSave",params,function(ret){
		if(ret.rs==true){
			art.dialog({
				content: ret.msg,
				time: 1000,
				beforeunload: function () {
			    	closeWin();
				}
			});
		}else{
			$("#_form_token_uniq_id").val(ret.token.token);
			 art.dialog({
				content: ret.msg,
			});
		}
					
	});
};
function validateRefresh(){
	$('#dataForm').data('bootstrapValidator')
                  .updateStatus('itemType1', 'NOT_VALIDATED',null)  
                  .validateField('itemType1')
                  .updateStatus('itemType2', 'NOT_VALIDATED',null)  
                  .validateField('itemType2')
                  .updateStatus('calType', 'NOT_VALIDATED',null)  
	              .validateField('calType')
	              .updateStatus('price', 'NOT_VALIDATED',null)  
	              .validateField('price')
	              .updateStatus('sendType', 'NOT_VALIDATED',null)  
	              .validateField('sendType')
	              .updateStatus('smallSendType', 'NOT_VALIDATED',null)  
	              .validateField('smallSendType')
	              .updateStatus('smallSendMaxValue', 'NOT_VALIDATED',null)  
	              .validateField('smallSendMaxValue')
	              .updateStatus('smallSendFeeAdj', 'NOT_VALIDATED',null)  
	              .validateField('smallSendFeeAdj')
	              .updateStatus('remarks', 'NOT_VALIDATED',null)  
	              .validateField('remarks');                     
};

function changeSendType() {
	var sendType=$("#sendType").val();
	if (sendType == "1") {
		$("#supplCode,#smallSendType,#smallSendMaxValue,#smallSendFeeAdj").parent().show();
		$("#wHCode,#beginTimes,#endTimes,#cardAmount,#incValue").parent().hide();
	} else if (sendType == "2") {
		$("#supplCode,#smallSendType,#smallSendMaxValue,#smallSendFeeAdj").parent().hide();
		$("#wHCode,#beginTimes,#endTimes,#cardAmount,#incValue").parent().show();
	} else {
	    $("#supplCode,#smallSendType,#smallSendMaxValue,#smallSendFeeAdj").parent().hide();
	    $("#wHCode,#beginTimes,#endTimes,#cardAmount,#incValue").parent().hide();
	}
}
</script>
</head>
<body>
	<div class="body-box-form">
		<div class="panel panel-system">
			<div class="panel-body">
				<div class="btn-group-xs">
					<button type="button" id="saveBtn" class="btn btn-system "
						onclick="save()">保存</button>
					<button type="button" class="btn btn-system "
						onclick="closeWin(false)">关闭</button>
				</div>
			</div>
		</div>
		<div class="panel panel-info">
			<div class="panel-body">
				<div class="body-box-form">
					<form role="form" class="form-search" id="dataForm" action=""
						method="post" target="targetFrame">
						<input type="hidden" id="m_umState" name="m_umState" value="${sendFeeRule.m_umState}" />
						<house:token></house:token>
						<ul class="ul-form">
							<div class="validate-group">
								<li><label style="width:120px"><span class="required">*</span>编号</label>
								    <input type="text" id="no" name="no" readonly="true" value="${sendFeeRule.no}"
									       placeholder="保存时生成" />
								</li>
								<li class="form-validate"><label style="width:120px"><span
										class="required">*</span>计算类型</label> <house:xtdm id="calType"
										dictCode="SendFeeCalType" value="${sendFeeRule.calType}"></house:xtdm>
								</li>
							</div>
							<div class="validate-group">
								<li class="form-validate"><label style="width:120px"><span
										class="required">*</span>材料类型1</label> <select id="itemType1"
									name="itemType1"></select>
								</li>
								<li class="form-validate"><label style="width:120px"><span
										class="required">*</span>材料类型2</label> <select id="itemType2"
									name="itemType2"></select>
								</li>
							</div>
							<div class="validate-group">
								<li class="form-validate"><label style="width:120px"><span
										class="required">*</span> 配送单价</label> <input type="text" id="price"
									name="price" value="${sendFeeRule.price }" 
									onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\.\d]/g,'');"/>
								</li>
							<li class="form-validate">
								<label style="width:120px"><span class="required">*</span> 发货类型</label>
								<house:xtdm id="sendType" dictCode="ITEMAPPSENDTYPE" value="${sendFeeRule.sendType }" onchange="changeSendType()"></house:xtdm>
							</li>
							</div>
							<div class="validate-group">
							    <li class="form-validate">
		                            <label style="width:120px">供应商</label>
		                            <input type="text" id="supplCode" name="supplCode" value="${sendFeeRule.supplCode}"/>
		                        </li>
								<li class="form-validate">
									<label style="width:120px"><span class="required">*</span>少量配送类型</label>
									<house:xtdm id="smallSendType" dictCode="SmallSendType" value="${sendFeeRule.smallSendType }"></house:xtdm>
								</li>
								<li class="form-validate"><label style="width:120px"><span
										class="required">*</span> 少量配送最大值</label> <input type="text" id="smallSendMaxValue"
									name="smallSendMaxValue" value="${sendFeeRule.smallSendMaxValue }" 
									onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\.\d]/g,'');"/>
								</li>
								<li class="form-validate"><label style="width:120px"><span
											class="required">*</span> 少量配送补贴</label> <input type="text" id="smallSendFeeAdj"
										name="smallSendFeeAdj" value="${sendFeeRule.smallSendFeeAdj }" 
										onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\.\d]/g,'');"/>
								</li>
							</div>
							<div class="validate-group">
								<li class="form-validate">
		                            <label style="width:120px">仓库</label>
		                            <input type="text" id="wHCode" name="wHCode" value="${sendFeeRule.wHCode}"/>
		                        </li>
								<li class="form-validate">
		                            <label style="width:120px">起始次数</label>
		                            <input type="text" id="beginTimes" name="beginTimes" value="${sendFeeRule.beginTimes}"/>
		                        </li>
								<li class="form-validate">
		                            <label style="width:120px">截止次数</label>
		                            <input type="text" id="endTimes" name="endTimes" value="${sendFeeRule.endTimes}"/>
		                        </li>
								<li class="form-validate">
		                            <label style="width:120px">初始金额</label>
		                            <input type="text" id="cardAmount" name="cardAmount" value="${sendFeeRule.cardAmount}"/>
		                        </li>
								<li class="form-validate">
		                            <label style="width:120px">递增值</label>
		                            <input type="text" id="incValue" name="incValue" value="${sendFeeRule.incValue}"/>
		                        </li>
							</div>
							<li class="form-validate"><label style="width:120px" class="control-textarea">备注</label>
								<textarea id="remarks" name="remarks" style="width:472px">${sendFeeRule.remarks}</textarea>
							</li>
						</ul>
					</form>
				</div>
			</div>
		</div>
		<div class="container-fluid">
			<ul class="nav nav-tabs">
				<li id="tabsalesInvoice" class="active"><a href="#tab_salesInvoice"
					data-toggle="tab">匹配材料</a></li>
			</ul>
			 <div class="tab-content">
				<div id="tab_salesInvoice" class="tab-pane fade in active">
					<jsp:include page="sendFeeRule_tab_item.jsp"></jsp:include>
				</div>
			</div> 
		</div>
	</div>
</body>
</html>
