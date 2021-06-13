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
	<title>分账定义新增</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_department.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_assetType.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_docFolder.js?v=${v}" type="text/javascript"></script>
	<style type="text/css">
		::-webkit-input-placeholder { /* Chrome */
		  color: #cccccc;
		}
	</style>
<script type="text/javascript"> 
function setAlgorithm(e,val){
	
	var elem = document.getElementById("formulaShow");
	var index = getFocus(elem);
	
	var formulaShow = val;
	var formula = e.value;
	setFormulaShow(formulaShow, index);
}

function setFormula(val){
	var formula = $("#formula").val();
	
    $("#formula").val(formula+val+" ");
}

function setFormulaShow(val,index){
	
	var formulaShow = $("#formulaShow").val();
	if(index == 0){
		index = formulaShow.length;
	}
	
	var preStr = formulaShow.substring(0,index);
	var sufStr =formulaShow.substring(formulaShow.length -(formulaShow.length - index));

	if(index == formulaShow.length){
		
	    $("#formulaShow").val(formulaShow+val+" ");
	} else {
		
	    $("#formulaShow").val(preStr+" "+val+" "+sufStr+" ");
	}
	
	selectRange("formulaShow", myRound(index)+myRound(val.length)+2);
}

$(function() {
	
	$("#dataForm").bootstrapValidator({
		message : "This value is not valid",
		feedbackIcons : {/*input状态样式图片*/
			validating : "glyphicon glyphicon-refresh"
		},
		fields: {
			descr:{
				validators:{
					notEmpty:{
						message:"分账名称不能为空"
					}
				}
			},
			payMode:{
				validators:{
					notEmpty:{
						message:"发放方式不能为空"
					}
				}
			},
			realPaySalaryItem:{
				validators:{
					notEmpty:{
						message:"实发项目不能为空"
					}
				}
			},
			seqNo:{
				validators:{
					notEmpty:{
						message:"分账序号不能为空"
					}
				}
			},
		},
        submitButtons : "input[type='submit']"/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
    });
	
});

function doSave(){

	$("#dataForm").bootstrapValidator("validate");
    if (!$("#dataForm").data("bootstrapValidator").isValid()) return;

	var realPaySalaryItemDescr = $("#realPaySalaryItem").find("option:selected").text();
	var payModeDescr = $("#payMode").find("option:selected").text();

	var datas=$("#dataForm").jsonForm();
	
		datas["realPaySalaryItemDescr"] = realPaySalaryItemDescr;
		datas["payModeDescr"] = payModeDescr;
		Global.Dialog.returnData = datas;
		closeWin();
}

function goFilterFormula(){
	var filterFormulaShow = $.trim($("#filterFormulaShow").val());
	var filterFormula = $.trim($("#filterFormula").val());
	
	Global.Dialog.showDialog("FilterFormula",{
		title:"条件公式设置",
		postData:{},
		url:"${ctx}/admin/salaryItem/goFilterFormula?filterFormulaShow="+encodeURIComponent(filterFormulaShow)+"&filterFormula="+encodeURIComponent(filterFormula),
		height:480,
		width:920,
        resizable: true,
		returnFun:function(data){
			$("#filterFormulaShow").val(data.filterFormulaShow);
			$("#filterFormula").val(data.filterFormula);
		}
	});	
}

</script>
</head>
	<body>
		<div class="body-box-form">
			<div class="panel panel-system" >
			    <div class="panel-body" >
			    	<div class="btn-group-xs" >
						<button type="button" class="btn btn-system " id="saveBtn" onclick="doSave()">
							<span>保存</span>
						</button>
						<button type="button" class="btn btn-system " id="closeBut" onclick="closeWin(false)">
							<span>关闭</span>
						</button>
					</div>
				</div>
			</div>
			<div class="panel panel-info" >  
				<div class="panel-body">
					<form action="" method="post" id="dataForm" class="form-search">
						<house:token></house:token>
						<input type="hidden" name="jsonString" value=""/>
						<input type="hidden" id="filterFormula" name="filterFormula" value="${map.filterformula }"/>
						<ul class="ul-form">
							<div class="validate-group row">
								<li class="form-validate">
									<label>薪酬方案</label>
									<house:dict id="salaryScheme" dictCode="" sql="select pk,descr from tSalaryScheme" 
										 sqlValueKey="pk" sqlLableKey="descr" value="${map.salaryscheme }" disabled="true"></house:dict>
								</li>
								<li class="form-validate">
									<label><span class="required">*</span>分账名称</label>
									<input type="text" id="descr" name="descr" style="width:160px;" value="${map.descr }"/>
								</li>
							</div>
							<div class="validate-group row">
								<li class="form-validate">
									<label><span class="required">*</span>发放方式</label>
									<house:dict id="payMode" dictCode="" 
												sql="select cbm code, note descr from tXtdm where id= 'SALPAYMODE'" 
										 sqlValueKey="code" sqlLableKey="descr" value="${map.paymode }"></house:dict>
								</li>
								<li class="form-validate">
									<label><span class="required">*</span>分账序号</label>
									<input type="text" id="seqNo" name="seqNo" style="width:160px;" value="${map.seqno }"/>
								</li>
							</div>
							<div class="validate-group row">
								<li class="form-validate">
									<label class="control-textarea">分账说明</label>
									<textarea id="remarks" name="remarks" rows="3" >${map.remarks }</textarea>
								</li>
							</div>
							<div class="validate-group row">
								<li class="form-validate">
									<label class="control-textarea">条件公式</label>
									<textarea id="filterFormulaShow" name="filterFormulaShow" rows="3" >${map.filterformulashow }</textarea>
								</li>
								<div class="btn-group-xs" style="margin-top:15px">
									<button type="button" class="btn btn-system " onclick="goFilterFormula()" >
										<span>设置条件</span>
									</button>
								</div>
							</div>
							<div class="validate-group row">
								<li class="form-validate">
									<label><span class="required">*</span>实发项目</label>
									<house:dict id="realPaySalaryItem" dictCode="" 
												sql="select b.descr, a.SalaryItem code from tSalarySchemeItem a 
												left join tSalaryItem b on b.Code = a.SalaryItem
												where a.SalaryScheme = '${map.salaryscheme}' " 
										 sqlValueKey="code" sqlLableKey="descr" value="${map.realpaysalaryitem }"></house:dict>
								</li>
							</div>
							<div class="validate-group row">
								<li class="form-validate">
									<label class="control-textarea">报表数据</label>
									<textarea id="salaryItemList" name="salaryItemList" rows="3">${map.salaryitemlist }</textarea>
								</li>
							</div>
						</ul>
					</form>
				</div>
			</div>
		</div>
	</body>	
</html>
