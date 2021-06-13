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
	
	<script src="${resourceRoot}/pub/component_supplier.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
</head>

<body>
	<div class="body-box-form">
		<div class="panel panel-system" >
		    <div class="panel-body">
		      	<div class="btn-group-xs" >
					<button type="submit" class="btn btn-system " id="saveBtn">
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
					<house:token></house:token>
					<input type="hidden" name="jsonString" value=""/>
					<input type="hidden" id="lastUpdatedBy" name="lastUpdatedBy" value="${sessionScope.USER_CONTEXT_KEY.czybh}"/>
					<ul class="ul-form">
						<div class="row">
							<div class="col-xs-6">
								<li>
									<label><span class="required">*</span>问题编号</label>
									<input type="text" id="pk" name="pk" style="width:160px;" value="${custProblem.pk}"
										placeholder="保存自动生成" readonly="readonly"/>
								</li>
								<li class="form-validate">
									<label><span class="required">*</span>问题类型</label>
									<house:xtdm id="promSource" dictCode="PROMSOURCE" style="width:160px;" value="${custProblem.promSource}"/>
								</li>
								<li class="form-validate">
									<label><span class="required">*</span>回访编号</label>
									<input type="text" id="no" name="no" style="width:160px;" value="${custProblem.no}" readonly="readonly"/>
								</li>
								<li class="form-validate">
									<label><span class="required">*</span>问题分类</label>
									<house:dict id="promType1" dictCode="" sql="select rtrim(Code) Code, rtrim(Code)+' '+rtrim(Descr) PromType1 from tPromType1 where expired='F' " 
										sqlValueKey="Code" sqlLableKey="PromType1" value="${custProblem.promType1}"/>
								</li>
								<li>
									<label>材料分类</label>
									<house:dict id="promType2" dictCode="" sql="select rtrim(Code) Code, rtrim(Code)+' '+rtrim(Descr) PromType2 from tPromType2 where expired='F' " 
										sqlValueKey="Code" sqlLableKey="PromType2" value="${custProblem.promType2}"/>
								</li>
								<li>
									<label>问题原因</label>
									<house:dict id="promRsn" dictCode="" sql="select rtrim(Code) Code, rtrim(Code)+' '+rtrim(Descr) PromRsn from tPromRsn where expired='F' " 
										sqlValueKey="Code" sqlLableKey="PromRsn" value="${custProblem.promRsn}"/>
								</li>
							</div>
							<div class="col-xs-6">
								<li>
									<label>供应商</label>
									<input type="text" id="supplCode" name="supplCode" style="width:160px;" value=""/>
								</li>
								<li>
									<label>通知时间</label>
									<input type="text" id="infodate" name="infodate" class="i-date" style="width:160px;" 
										onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
										value="<fmt:formatDate value='${custProblem.infoDate}' pattern='yyyy-MM-dd'/>"/>
								</li>
								<li>
									<label>处理人</label>
									<input type="text" id="dealCZY" name="dealCZY" style="width:160px;" value=""/>
								</li>
								<li>
									<label>计划处理时间</label>
									<input type="text" id="plandealdate" name="plandealdate" class="i-date" style="width:160px;" 
										onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
										value="<fmt:formatDate value='${custProblem.planDealDate}' pattern='yyyy-MM-dd'/>"/>
								</li>
								<li>
									<label>实际处理时间</label>
									<input type="text" id="dealdate" name="dealdate" class="i-date" style="width:160px;" 
										onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
										value="<fmt:formatDate value='${custProblem.dealDate}' pattern='yyyy-MM-dd'/>"/>
								</li>
								<li class="form-validate">
									<label><span class="required">*</span>问题状态</label>
									<house:xtdm id="status" dictCode="PROMSTATUS" style="width:160px;" value="${custProblem.status}"/>
								</li>
							</div>
							<div class="col-xs-12">
								<li>
									<label class="control-textarea">处理结果</label>
									<textarea id="dealremarks" name="dealremarks" rows="2" style="height: 50px;">${custProblem.dealRemarks}</textarea>
								</li>
							</div>
						</div>
					</ul>	
				</form>
			</div>
		</div>
	</div>
</body>	
<script>
$(function() {
	$("#promSource").attr("disabled",true);
	
 	switch ("${custProblem.m_umState}") {
	case "M":
		$("#supplCode").openComponent_supplier({showValue:"${custProblem.supplCode}",showLabel: "${custProblem.supplDescr}"});	
		$("#dealCZY").openComponent_employee({showValue:"${custProblem.dealCZY}",showLabel: "${custProblem.dealCZYDescr}"});
		break;
	case "V":
		$("#saveBtn").hide();		
		$("#supplCode").openComponent_supplier({showValue:"${custProblem.supplCode}",showLabel: "${custProblem.supplDescr}"});	
		$("#dealCZY").openComponent_employee({showValue:"${custProblem.dealCZY}",showLabel: "${custProblem.dealCZYDescr}"});
		break;
	default:
		//供应商选择 
		$("#supplCode").openComponent_supplier();	
		$("#dealCZY").openComponent_employee();

		$("#promSource").val("1");
		$("#status").val("2");
		break;
	}
	
	$("#page_form").bootstrapValidator({
		message : "请输入完整的信息",
		feedbackIcons : {
			validating : "glyphicon glyphicon-refresh"
		},
		fields: { 
			promSource:{
				validators: {  
					notEmpty: {  
						message: "请选择问题来源"  
					}
				}  
			},
			promType1:{
				validators: {
					notEmpty: {
						message: "请选择问题分类"
					}
				}
			},
			status: {
				validators: {
					notEmpty: {
						message: "请选择处理状态"
					}
				}
			}	
		}
	});
	
	var originalData = $("#page_form").serialize();
	
	$("#saveBtn").on("click",function(){
		/* 先关掉disabled才能获取值 */
		$("#promSource").prop("disabled",false);
	
		$("#page_form").bootstrapValidator("validate");
		if(!$("#page_form").data("bootstrapValidator").isValid()) return;
		
		/* 将page_form json化后赋给selectRows */
 		var selectRows = [];
		var datas=$("#page_form").jsonForm();
	 	selectRows.push(addDescr(datas));
	 	// console.log(selectRows);
		Global.Dialog.returnData = selectRows;
		
		$("#promSource").prop("disabled",true);
		
		closeWin();
	});
	
	/*添加Descr返回方法*/
	function addDescr(datas){
		var arr;
		/* 获得选择值，并将descr提取出来 */
		var promSourceText=$("#promSource").find("option:selected").text();
	 	arr = promSourceText.split(" ");
	 	var promSourceDescr=arr[arr.length-1];
	 	var promType1Text=$("#promType1").find("option:selected").text();
	 	arr = promType1Text.split(" ");
	 	var promType1Descr=arr[arr.length-1];
	 	var promType2Text=$("#promType2").find("option:selected").text();
	 	arr = promType2Text.split(" ");
	 	var promType2Descr=arr[arr.length-1];
	 	var promRsnText=$("#promRsn").find("option:selected").text();
	 	arr = promRsnText.split(" ");
	 	var promRsnDescr=arr[arr.length-1];
	 	var statusText=$("#status").find("option:selected").text();
	 	arr = statusText.split(" ");
	 	var statusDescr=arr[arr.length-1];
	 	var supplVal=$("#openComponent_supplier_supplCode").val();
	 	arr = supplVal.split("|");
	 	var supplDescr=arr[arr.length-1];
	 	var dealCZYVal=$("#openComponent_employee_dealCZY").val();
	 	arr = dealCZYVal.split("|");
	 	var dealCZYDescr=arr[arr.length-1];
	 	/*var infoDateVal = $("#infodate").val();
	 	var planDealDateVal = $("#plandealdate").val();
	 	var dealDateVal = $("#dealdate").val();*/
	 	
	 	datas.promSourceDescr=(promSourceDescr=="请选择..."?"":promSourceDescr);
	 	datas.promType1Descr=(promType1Descr=="请选择..."?"":promType1Descr);
	 	datas.promType2Descr=(promType2Descr=="请选择..."?"":promType2Descr);
	 	datas.promRsnDescr=(promRsnDescr=="请选择..."?"":promRsnDescr);
	 	datas.statusDescr=(statusDescr=="请选择..."?"":statusDescr);
	 	datas.supplDescr=supplDescr;
	 	datas.dealCZYDescr=dealCZYDescr;
	 	/*datas.infodate = (infoDateVal == ""? null:infoDateVal);
	 	datas.plandealdate = (planDealDateVal == ""? null:planDealDateVal);
	 	datas.dealdate = (dealDateVal == ""? null:dealDateVal);*/

	 	
	 	return datas;
	}
	
});

</script>
</html>
