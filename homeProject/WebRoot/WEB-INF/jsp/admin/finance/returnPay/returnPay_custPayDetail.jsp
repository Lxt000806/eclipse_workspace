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
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<title>客户退款新增</title>
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_builderGroup.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_supplierPrepayDetail.js?v=${v}" type="text/javascript"></script>
	<script type="text/javascript">
	$(function(){
		if ("M" == "${returnPay.m_umState}") {
			$("#custCode").openComponent_customer({
				showValue:"${returnPay.custCode}",
				showLabel:"${returnPay.descr}",
				callBack:validateRefresh_address,
				disabled:true,
				condition:{status:"4,5"}
			});
		}else if("V" == "${returnPay.m_umState}"){
			$("#custCode").openComponent_customer({
				showValue:"${returnPay.custCode}",
				showLabel:"${returnPay.descr}",
				callBack:validateRefresh_address,
				condition:{status:"4,5"}
			});
			$("#saveBtn").hide();
			disabledForm("dataForm");
		}else {
			$("#custCode").openComponent_customer({
				showValue:"${returnPay.custCode}",
				showLabel:"${returnPay.descr}",
				callBack:validateRefresh_address,
				condition:{status:"4,5"}
			});
		}
		var originalData = $("#page_form").serialize();
		
		$("#amount").keydown(function(e) {  
			if (e.which == 13) {// 判断所按是否回车键  
		       $("#remarks").focus();
		    } else if(e.which>57||e.which<48){
		    	$("#amount")[0].value=$("#amount")[0].value.replace(/[^\-?\d.]/g,'')
		    }
	    });  
		
		$("#saveBtn").on("click",function(){
			$("#dataForm").bootstrapValidator("validate");/* 提交验证 */
			if(!$("#dataForm").data("bootstrapValidator").isValid()) return; 
			if (parseFloat($.trim($("#amount").val()))<0.0 && $.trim($("#custCheckStatus").val())!="1" ){
				art.dialog({content: "该楼盘客户已结算，金额不能小于0",width: 200});
				return false;
			}
			var isExistsCustCode=0;
			if("A"=="${returnPay.m_umState}"){
				var strs = $.trim("${returnPay.hasCustCode}").split(',');//先按照空格分割成数组
				for (var i=0;i<strs.length ;i++){
					if($.trim($("#custCode").val())==strs[i].replace(/(^\s*)|(\s*$)/g, "")){
						isExistsCustCode=1;
						break;	
					};
				}
			}
			if (isExistsCustCode==1){
				art.dialog({
					content:'该楼盘存在申请的退款单,是否确定保存',
					lock: true,
					width: 260,
					height: 100,
					ok: function () {
						doSave();
					},
					cancel: function () {
						return ;
					}
				});
			}else{
				doSave();
			}
		});	
		                                                                                                     
		$("#refSupplPrepayPK").openComponent_supplierPrepayDetail({showValue:"${returnPay.refSupplPrepayPK}",showLabel:"${returnPay.refSupplPrepayNo}",
			condition: {type:"1",status:"1",unSelected:"${returnPay.hasRefSupplPrepayPK}",readonly:"1"},
			callBack:function(data){
      			validateRefresh('openComponent_supplierPrepayDetail_refSupplPrepayPK');
      			$("#refSupplPrepayNo").val(data.no);
      			$("#splDescr").val(data.spldescr);
      			$("#thisAmount").val(data.amount);
      	    }
	    });	
	    $("#openComponent_supplierPrepayDetail_refSupplPrepayPK").attr("readonly",true);
		$("#dataForm").bootstrapValidator({
			message : "This value is not valid",
			feedbackIcons : {/*input状态样式图片*/
				validating : "glyphicon glyphicon-refresh"
			},
			fields: { 
				openComponent_customer_custCode:{  
					validators: {  
						notEmpty: {  
							message: "请输入完整的信息"  
						}              
					}  
				},
				amount:{ 
					validators: {  
						notEmpty: {  
							message: "请输入完整的信息"  
						}, 
					}
				},
				openComponent_supplierPrepayDetail_refSupplPrepayPK: {  
		        validators: {  
		             remote: {
			            message: '',
			            url: '${ctx}/admin/supplierPrepayDetail/getSupplierPrepayDetail',
			            data: getValidateVal,  
			            delay:4 //这里特别要说明，必须要加此属性，否则用户输入一个字就会访问后台一次，会消耗大量的系统资源，
			        }     
		        }  
	        }, 
			}
		});
	});	
	

	function validateRefresh_address(data){
		var code = data.custCode;
		$("#address").val(data.address);
		$("#documentNo").val(data.documentno);
		$("#descr").val(data.descr);
		$("#statusDescr").val(data.statusdescr);
		$("#endDescr").val(data.enddescr);
		$("#isPubReturnDescr").val(data.ispubreturndescr);
		$("#returnAmount").val(data.returnamount);
		$("#custCheckStatus").val(data.checkstatus);
		$("#custCheckStatusDescr").val(data.checkstatusdescr);
		$("#regionDescr").val(data.regiondescr);
		$("#prjDeptLeaderName").val(data.prjdeptleadername);
	}
	
	function jcszxx(){
		var code = $("#custCode").val().split("|")[0];			
		if(code){
			Global.Dialog.showDialog("jcszxx",{
				title:"工程退款管理新增--客户收支明细",
				url:"${ctx}/admin/itemSzQuery/goReturnPayJcszxx?code="+code+"&isWorkcost=1",
				height:730,
				width:1350
			});
		}else{
			art.dialog({
				content:"请先选择客户号"
			});
		}
	}
	
	
	function customerInfo(){
		var code = $("#custCode").val().split("|")[0];	
	    if (code) {
	      Global.Dialog.showDialog("customerXxView",{
			  title:"查看客户",
			  url:"${ctx}/admin/customerXx/goDetail?id="+code,
			  height:750,
			  width:1430
			});
	    } else {
	    	art.dialog({
				content: "请先选择客户号"
			});
	    }
	}
	function doSave(){
	 		var selectRows = [];
			var datas=$("#dataForm").jsonForm();
		 	selectRows.push(datas);
			Global.Dialog.returnData = selectRows;
			closeWin();
	}
		
	</script>
</head>
<body>
	<div class="body-box-form">
        <div class="panel panel-system">
	    <div class="panel-body">
	      <div class="btn-group-xs">
				<button type="button" class="btn btn-system" id="saveBtn" >
					<span>保存</span>
				</button>
				<button type="button" class="btn btn-system" id="budgetDetailBtn" onclick="jcszxx()">
					<span>收支明细</span>
				</button>
				<button type="button" class="btn btn-system" id="customerInfoBtn" onclick="customerInfo()">
					<span>查看客户信息</span>
				</button>
				<button type="button" class="btn btn-system" id="closeBut" onclick="closeWin(false)">
					<span>关闭</span>
				</button>
			</div>
		</div>
	</div>
	<div class="panel panel-info">  
		<div class="panel-body">
			<form action="" method="post" id="dataForm" class="form-search">
				<house:token></house:token>
				<input type="hidden" id="descr" name="descr" value="${returnPay.descr}" />
				<input type="hidden" id="statusDescr" name="statusDescr" value="${returnPay.statusDescr}" />
				<input type="hidden" id="endDescr" name="endDescr" value="${returnPay.endDescr}" />
				<input type="hidden" id="isPubReturnDescr" name="isPubReturnDescr" value="${returnPay.isPubReturnDescr}" />
				<input type="hidden" id="returnAmount" name="returnAmount" value="${returnPay.returnAmount}" />
				<input type="hidden" id="lastupdatedby" name="lastupdatedby" value="${sessionScope.USER_CONTEXT_KEY.czybh}"/>
				<input type="hidden" id="refSupplPrepayNo" name="refSupplPrepayNo" value="${returnPay.refSupplPrepayNo}"/>
				<input type="hidden" id="custCheckStatus" name="custCheckStatus" value="${returnPay.custCheckStatus}"/>
				<input type="hidden" id="custCheckStatusDescr" name="custCheckStatusDescr" value=""/>
				<input type="hidden" id="prjDeptLeaderName" name="prjDeptLeaderName" value=""/>
				<input type="hidden" id="regionDescr" name="regionDescr" value=""/>
				<input type="hidden" id="documentNo" name="documentNo" value="" />
				
				<ul class="ul-form">	
					<div class="validate-group row">
						<li class="form-validate">
							<label ><span class="required">*</span>客户编号</label>
							<input type="text" id="custCode" name="custCode" style="width:160px;"/>
						</li>
						<li>
							<label >楼盘</label>
							<input type="text" id="address" name="address" style="width:160px;" value="${returnPay.address }"  readonly="readonly"/>
						</li>
					</div>
					<div class="validate-group row" >
						<li class="form-validate">
							<label ><span class="required">*</span>金额</label>
							<input type="text" id="amount" name="amount" style="width:160px;" value="${returnPay.amount}"/>
						</li>
						<li>
							<input type="text" style="width:260px;border:0" disabled="true" value="退款给客户,填负数;客户付款,填正数." />
						</li>
					</div>
					<div class="validate-group row" >
						<li class="form-validate">
							<label >预付金单号</label>
							<input type="text" id="refSupplPrepayPK" name="refSupplPrepayPK" style="width:160px;" value="${returnPay.refSupplPrepayPK}" readonly="readonly"/>
						</li>
						<li>
							<label >供应商</label>
							<input type="text" id="splDescr" name="splDescr" style="width:160px;" value="${returnPay.splDescr}" readonly/>
						</li>
						<li >
							<label >本次付款金额</label>
							<input type="text" id="thisAmount" name="thisAmount" style="width:160px;" value="${returnPay.thisAmount}" readonly/>
						</li>
					</div>
					
					<div class="validate-group row">
						<li >
							<label class="control-textarea">备注</label>
							<textarea id="remarks" name="remarks" rows="4" >${returnPay.remarks }</textarea>
						</li>
					</div>
				</ul>	
			</form>
		</div>
	</div>
</div>
</body>
</html>


