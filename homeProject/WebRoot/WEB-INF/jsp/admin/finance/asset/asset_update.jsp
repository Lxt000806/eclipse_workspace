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
	<title>固定资产管理增加</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_department.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_assetType.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript"> 
$(function() {
	function checkValidator(){
		$("#dataForm").data("bootstrapValidator")
	       .updateStatus("openComponent_department_department", "NOT_VALIDATED",null)  
	       .validateField("openComponent_department_department"); 
	}
	
	$("#useMan").openComponent_employee({showValue:"${asset.useMan}",showLabel:"${asset.useManDescr}"});
	$("#department").openComponent_department({showValue:"${asset.department}",showLabel:"${asset.deptDescr}",callBack:checkValidator,readonly:true});	
	if($.trim("${deprFlag }") == "true"){
		$("#useYear").attr("readonly","true");
		$("#originalValue").attr("readonly","true");
		$("#totalDeprAmount").attr("readonly","true");
		$("#deprType").attr("disabled","true");
	}
	
	function getAssetTypeDate(data){
		if(!data){
			return;
		}
		$("#remCode").val(data.remcode);
	}
	
	$("#assetType").openComponent_assetType({readonly:"true", callBack:getAssetTypeDate, 
			showLabel:"${asset.assetTypeDescr}",showValue:"${asset.assetType}"});
	
	$("#dataForm").bootstrapValidator({
		message : "This value is not valid",
		feedbackIcons : {/*input状态样式图片*/
			validating : "glyphicon glyphicon-refresh"
		},
		fields: {
			descr:{
				validators:{
					notEmpty:{
						message:"资产名称不能为空"
					}
				}
			}, 
			openComponent_assetType_assetType:{  
		        validators: {  
					notEmpty: {
		                message: "资产类别不能为空"
		            },
				}  
		    },
			uom:{
				validators:{
					notEmpty:{
						message:"单位不能为空"
					}
				}
			},
			addType:{
				validators:{
					notEmpty:{
						message:"增加方式不能为空"
					}
				}
			},
			openComponent_department_department:{  
		        validators: {  
		            remote: {
	    	            message: "",
	    	            url: "${ctx}/admin/department/getDepartment",
	    	            data: getValidateVal,  
	    	            delay: 4 // 这里特别要说明，必须要加此属性，否则用户输入一个字就会访问后台一次，会消耗大量的系统资源，
					},
					notEmpty:{
						message:"部门不能为空"
					}
				}  
		    },
			status:{
				validators:{
					notEmpty:{
						message:"状态不能为空"
					}
				}
			},
			qty:{
				validators:{
					notEmpty:{
						message:"数量不能为空"
					}
				}
			},
			originalValue:{
				validators:{
					notEmpty:{
						message:"原值不能为空"
					}
				}
			},
			beginDate:{
				validators:{
					notEmpty:{
						message:"开始使用时间不能为空"
					}
				}
			},
			totalDeprAmount:{
				validators:{
					notEmpty:{
						message:"累计折旧不能为空"
					}
				}
			},
			useYear:{
				validators:{
					notEmpty:{
						message:"使用年限不能为空"
					}
				}
			},
			deprMonth:{
				validators:{
					notEmpty:{
						message:"已计提月份不能为空"
					}
				}
			},
		},
        submitButtons : "input[type='submit']"/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
    });
	
	$("#saveBtn").on("click",function(){
		$("#dataForm").bootstrapValidator("validate");
	    if (!$("#dataForm").data("bootstrapValidator").isValid()) return;

		var datas = $("#dataForm").serialize();
		$.ajax({
			url:"${ctx}/admin/asset/doUpdate",
			type: "post",
			data: datas,
			dataType: "json",
			cache: false,
			error: function(obj){
				showAjaxHtml({"hidden": false, "msg": "保存数据出错~"});
		    },
		    success: function(obj){
		    	if(obj.rs){
		    		art.dialog({
						content: obj.msg,
						time: 1000,
						beforeunload: function () {
		    				closeWin(true);
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
	});
});


function getNetValue(){
	var originalValue=$("#originalValue").val().replace(/[^\-?\d.]/g,"");
	var totalDeprAmount=$("#totalDeprAmount").val().replace(/[^\-?\d.]/g,"");
	$("#originalValue").val(originalValue);
	$("#totalDeprAmount").val(totalDeprAmount);
	if(originalValue && totalDeprAmount){
		$("#netValue").val(originalValue - totalDeprAmount);
		getDeprAmountPerMonth();
	} else {
		$("#netValue").val(0);
		$("#deprAmountPerMonth").val(0);
	}
}

function getDeprAmountPerMonth(){
	var netValue = $.trim($("#netValue").val());
	var remainDeprMonth = $.trim($("#remainDeprMonth").val());
	var deprType = $.trim($("#deprType").val());	
	
	// 0:不提折旧 不计算月折旧额
	if(deprType == "0"){
		$("#deprAmountPerMonth").val(0);
		return;
	}
	
	if(myRound(netValue) == 0 || netValue == ""
			|| myRound(remainDeprMonth) == 0 || $.trim(remainDeprMonth) == ""){
		$("#deprAmountPerMonth").val(0);
	} else {
		$("#deprAmountPerMonth").val(myRound(myRound(netValue)/myRound(remainDeprMonth),2));
	}
}

function getRemainDeprMonth(){
	var useYear=$("#useYear").val().replace(/[^\-?\d]/g,"");
	var deprMonth=$("#deprMonth").val().replace(/[^\-?\d]/g,"");
	if(useYear && deprMonth){
		$("#remainDeprMonth").val(myRound(useYear)*12 - myRound(deprMonth));
		getDeprAmountPerMonth();
	} else {
		$("#remainDeprMonth").val(0);
		$("#deprAmountPerMonth").val(0);
	}
}

function chgDeprType(){
	var deprType = $.trim($("#deprType").val());		
	if(deprType == "0"){
		$("#deprAmountPerMonth").val(0);
	} else {
		getDeprAmountPerMonth();
	}
}
</script>
</head>
	<body>
		<div class="body-box-form">
			<div class="panel panel-system" >
			    <div class="panel-body" >
			      <div class="btn-group-xs" >
						<button type="button" class="btn btn-system " id="saveBtn">
							<span>保存</span>
						</button>
						<button type="button" class="btn btn-system " id="closeBut" onclick="closeWin(true)">
							<span>关闭</span>
						</button>
					</div>
				</div>
			</div>
			<div class="panel panel-info" >  
				<div class="panel-body">
					<form action="" method="post" id="dataForm" class="form-search">
						<input type="hidden" name="jsonString" value=""/>
						<ul class="ul-form">
							<div class="validate-group row">
								<li>
									<label>资产编号</label>
									<input type="text" id="code" name="code" style="width:160px;" value="${asset.code }" readonly="readonly"/>
								</li>
								<li class="form-validate">
									<label><span class="required">*</span>资产名称</label>
									<input type="text" id="descr" name="descr" style="width:160px;" value="${asset.descr }"/>
								</li>
							</div>
							<div class="validate-group row">
								<li class="form-validate">
									<label>类别编号</label>
									<input type="text" id="assetType" name="assetType" style="width:160px;"/>
								</li>
								<li class="form-validate">
									<label>规格型号</label>
									<input type="text" id="model" name="model" style="width:160px;" value="${asset.model}"/>
								</li>
							</div>
							<div class="validate-group row">
								<li class="form-validate">
									<label><span class="required">*</span>单位</label>
									<house:dict id="uom" dictCode="" sql="select Code,code+' '+descr Descr from tUom where expired = 'F' "  
										sqlValueKey="Code" sqlLableKey="Descr" value="${asset.uom }"></house:dict>                  
								</li>
								<li class="form-validate">
									<label><span class="required">*</span>增加方式</label>
									<house:xtdm id="addType" dictCode="ASSETADDTYPE" value="${asset.addType }"></house:xtdm>
								</li>
							</div>
							<div class="validate-group row">
								<li class="form-validate">
									<label><span class="required">*</span>部门</label>
									<input type="text" id="department" name="department" style="width:160px;" value="${asset.department }"/>
								</li>
								<li>
									<label>使用人</label>
									<input type="text" id="useMan" name="useMan" value="${asset.useMan }"/>
								</li>
							</div>
							<div class="validate-group row">
								<li class="form-validate">
									<label><span class="required">*</span>状态</label>
									<house:xtdm id="status" dictCode="ASSETSTATUS" value="${asset.status }"></house:xtdm>
								</li>
								<li class="form-validate">
									<label>存放地点</label>
									<input type="text" style="width:160px;" id="address" name="address" value="${asset.address }"/>
								</li>
							</div>
							<div class="validate-group row">
								<li class="form-validate">
									<label><span class="required">*</span>数量</label>
									<input type="text" style="width:160px;" id="qty" name="qty" value="${asset.qty }"/>
								</li>
								<li class="form-validate">
									<label><span class="required">*</span>原值</label>
									<input type="text" style="width:160px;" id="originalValue" name="originalValue" value="${asset.originalValue }"/>
								</li>
							</div>
							<div class="validate-group row">
								<li class="form-validate">
									<label><span class="required">*</span>开始使用时间</label>
									<input type="text" id="beginDate" name="beginDate" class="i-date" 
									style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd',minDate:'${asset.dateFrom}',maxDate:'${asset.dateTo}'})" 
									value="<fmt:formatDate value='${asset.beginDate }' pattern='yyyy-MM-dd'/>" />
									
								</li>
								<li class="form-validate">
									<label><span class="required">*</span>累计折旧</label>
									<input type="text" id="totalDeprAmount" name="totalDeprAmount" style="width:160px;" value="${asset.totalDeprAmount }" onkeyup="getNetValue()"/>
								</li>
							</div>
							<div class="validate-group row">
								<li class="form-validate">
									<label><span class="required">*</span>使用年限</label>
									<input type="text" id="useYear" name="useYear" style="width:160px;" value="${asset.useYear }" onkeyup="getRemainDeprMonth();getDeprAmountPerMonth()"/>
								</li>
								<li class="form-validate">
									<label>折旧方法</label>
									<house:xtdm id="deprType" dictCode="ASSETDEPRTYPE" onchange="chgDeprType()" value="${asset.deprType }"></house:xtdm>
								</li>
							</div>
							
							<div class="validate-group row">
								<li class="form-validate">
									<label><span class="required">*</span>已计提月份</label>
									<input type="text" id="deprMonth" name="deprMonth" style="width:160px;" value="${asset.deprMonth }" onkeyup="getRemainDeprMonth()"/>
								</li>
								<li class="form-validate">
									<label>剩余月份</label>
									<input type="text" id="remainDeprMonth" name="remainDeprMonth" style="width:160px;" value="${asset.remainDeprMonth }" placeholder="自动计算" readonly="true"/>
								</li>
							</div>
							<div class="validate-group row">
								<li class="form-validate">
									<label>净值</label>
									<input type="text" id="netValue" name="netValue" style="width:160px;" value="${asset.netValue }" placeholder="自动计算" readonly="true"/>
								</li>
								<li class="form-validate">
									<label>月折旧额</label>
									<input type="text" id="deprAmountPerMonth" name="deprAmountPerMonth" style="width:160px;" value="${asset.deprAmountPerMonth }" placeholder="自动计算" readonly="true"/>
								</li>
							</div>
							<div class="validate-group row">
								<li class="form-validate">
									<label class="control-textarea">备注</label>
									<textarea id="remarks" name="remarks">${asset.remarks }</textarea>
								</li>
							</div>
						</ul>
					</form>
				</div>
			</div>
		</div>
	</body>	
</html>
