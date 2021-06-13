<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://" + request.getServerName() + ":"+request.getServerPort()+path+"/";
String jasperPath = PathUtil.WEB_INF+"jsp/jasper/";
%>
<!DOCTYPE html>
<html>
<head>
	<title>优惠额度调整新增</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	
<script type="text/javascript"> 
$(function() {
	$("#remainDisc_span").popover({trigger:"hover"});
	$("#remainDesignRiskFund_span").popover({trigger:"hover"});

	if("${map}" != ""){
		// 剩余优惠额度 = 设计师优惠额度(客户表) + 公司优惠额度 (客户表)+ 额外赠送 + 已使用额度(已使用额度数据库存的是负数)
		console.log(myRound("${map.DesignerMaxDiscAmount}"));
		console.log(myRound("${map.DirectorMaxDiscAmount}"));
		console.log(myRound("${map.ExtraDiscChgAmount}"));
		console.log(myRound("${map.UsedDiscAmount}"));
		var remainDisc = myRound("${map.DesignerMaxDiscAmount}") + myRound("${map.DirectorMaxDiscAmount}") 
						+ myRound("${map.ExtraDiscChgAmount}") + 
						(myRound("${map.UsedDiscAmount}"));
		// 前端剩余风险金 = 设计师风险金 + 已使用风险金(使用风险金在数据库填负数)
		console.log("------------------");
		console.log(myRound("${custType.designRiskFund}") )
		console.log(myRound("${map.UsedRiskFund}"))
		//var designRemainRiskFund = myRound("${custType.designRiskFund}") + (myRound("${map.UsedRiskFund}"));
		var designRemainRiskFund = myRound("${customer.designRiskFund}") + (myRound("${map.UsedRiskFund}"));
		
		//总优惠余额
		var totalRemainDisc = myRound(remainDisc) + myRound(designRemainRiskFund);
		
		$("#remainDisc").val(remainDisc);
		$("#remainDesignRiskFund").val(designRemainRiskFund);
		$("#totalRemainDisc").val(totalRemainDisc);
	}
	
	$("#dataForm").bootstrapValidator({
        message : 'This value is not valid',
        feedbackIcons : {/*input状态样式图片*/
            validating : 'glyphicon glyphicon-refresh'
        },
        fields: {
        	type:{
                validators:{
                    notEmpty:{
                        message:"类型不能为空"
                    }
                }
            }, 
            amount:{
                validators:{
                    numeric:{
                        message:"金额只能是数字"  
                    },
                    notEmpty : {
                        message : "请填写金额"
                    },
                }
            }, 
            isExtra:{
            	validators:{
                    notEmpty:{
                        message:"是否额外赠送不能为空"
                    }
                }
            },
            isRiskFund:{
            	validators:{
                    notEmpty:{
                        message:"是否风控基金不能为空"
                    }
                }
            }
        },
        submitButtons : 'input[type="submit"]'/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
    });
	
	
	$("#saveBtn").on("click",function(){
		$("#dataForm").bootstrapValidator('validate');
	    if (!$("#dataForm").data('bootstrapValidator').isValid()) return;
		var datas = $("#dataForm").serialize();
		$.ajax({
			url:"${ctx}/admin/itemChg/doSaveDiscAmtTran",
			type:"post",
			data:datas,
			dataType:"json",
			cache:false,
			error:function(obj){
				showAjaxHtml({"hidden": false, "msg": "获取数据出错~"});
			},
			success:function(obj){
				art.dialog({
					content: obj.msg,
					time: 1000,
					beforeunload:function(){
						closeWin(true);
					}
				});
			}
		});
	});
});

function exitPage(){
	closeWin(true);
}
</script>
</head>
	<body>
		<div class="body-box-list">
			<div class="content-form">
				<div class="panel panel-system" >
    				<div class="panel-body" >
      					<div class="btn-group-xs" >
							<button type="button" class="btn btn-system " id="saveBtn">
								<span>保存</span>
							</button>
							<button type="button" class="btn btn-system " id="closeBut" onclick="exitPage()">
								<span>关闭</span>
							</button>
						</div>
					</div>	
				</div>
				<div class="panel panel-info" >  
					<div class="panel-body">
			  			<form role="form" class="form-search" id="dataForm"  action="" method="post" target="targetFrame" >
							<input type="hidden" name="jsonString" value="" />
							<input type="hidden" name="custCode" id="custCode" value="${itemChg.custCode }" />
							<input type="hidden" name="totalDiscAmtTran" id="totalDiscAmtTran" value="0.0" />
			  				<house:token></house:token>
							<ul class="ul-form">
							<div class="validate-group row" >
								<li class="form-validate">
									<label style="margin-left:-10px">剩余优惠额度</label>
									<span class="glyphicon glyphicon-question-sign" id = "remainDisc_span" 
											data-container="body"  
											data-content="设计师优惠额度+总监优惠额度+额外赠送-累计已使用额度" 
											data-placement="bottom" trigger="hover"
											style="font-size: 13px;color:rgb(25,142,222);"></span>
									<input type="text" id="remainDisc" name="remainDisc" readonly= "true" style="width:160px;" />
								</li>
								<li class="form-validate">
									<label style="margin-left:-10px">剩余前端风险金</label>
									<span class="glyphicon glyphicon-question-sign" id = "remainDesignRiskFund_span" 
											data-container="body"  
											data-content="设计师风控基金-累计已使用" 
											data-placement="bottom" trigger="hover"
											style="font-size: 13px;color:rgb(25,142,222);"></span>
									<input type="text" id="remainDesignRiskFund" name="remainDesignRiskFund" style="width:160px;" readonly="true"/>
								</li>
							</div>
							<div class="validate-group row" >
								<li class="form-validate">
									<label><span class="required">*</span>类型</label>
									<house:xtdm  id="type" dictCode="DISCAMTTRANTYPE" style="width:160px;" 
										value="2" ></house:xtdm>
								</li>
								<li class="form-validate">
									<label>总优惠余额</label>
									<input type="text" id="totalRemainDisc" name="totalRemainDisc" readonly= "true" style="width:160px;" />
								</li>
							</div>
							<div class="validate-group row" >
								<li class="form-validate">
									<label><span class="required">*</span>调整金额</label>
									<input type="text" id="amount" name="amount" style="width:160px;" />
								</li>
								<li class="form-validate" >
									<label><span class="required">*</span>是否额外赠送</label>
									<house:xtdm  id="isExtra" dictCode="YESNO" style="width:160px;" 
										value="0" ></house:xtdm>
								</li>
							</div>
							<div class="validate-group row" hidden="true">
								<li class="form-validate">
									<label><span class="required">*</span>是否分控基金</label>
									<house:xtdm  id="isRiskFund" dictCode="YESNO" style="width:160px;" 
										value="0" ></house:xtdm>
								</li>
							</div>
							<div class="validate-group row" >
								<li>
									<label class="control-textarea">备注</label>
									<textarea id="remarks" name="remarks" rows="2"></textarea>
	  							</li>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</body>	
</html>
