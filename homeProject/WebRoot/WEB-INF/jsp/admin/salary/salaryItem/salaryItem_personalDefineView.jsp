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
	<title>薪酬项目编辑分类定义</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_salaryEmp.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_department.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_assetType.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_docFolder.js?v=${v}" type="text/javascript"></script>
	<style type="text/css">
		::-webkit-input-placeholder { /* Chrome */
		  color: #cccccc;
		}
	</style>
<script type="text/javascript"> 
$(function() {
	$("#filterFormula").openComponent_salaryEmp({showValue:"${salaryItemStatCfg.filterFormula }",showLabel:"${salaryItemStatCfg.empName }",readonly:true});
	
	$("#dataForm").bootstrapValidator({
		message : "This value is not valid",
		feedbackIcons : {/*input状态样式图片*/
			validating : "glyphicon glyphicon-refresh"
		},
		fields: {
			code:{
				validators:{
					notEmpty:{
						message:"编号不能为空"
					}
				}
			},
			descr:{
				validators:{
					notEmpty:{
						message:"名称不能为空"
					}
				}
			},
			itemLevel:{
				validators:{
					notEmpty:{
						message:"计算优先级不能为空"
					}
				}
			},
			isSysRetention:{
				validators:{
					notEmpty:{
						message:"系统保留项目不能为空"
					}
				}
			},
			IsAdjustable:{
				validators:{
					notEmpty:{
						message:"支持手动修改不能为空"
					}
				}
			},
			itemGroup:{
				validators:{
					notEmpty:{
						message:"薪酬项目分组不能为空"
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
		},
        submitButtons : "input[type='submit']"/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
    });
	
	
	var beginMon = $.trim("${salaryItemStatCfg.beginMon}");
	var emdMon = $.trim("${salaryItemStatCfg.endMon }");
	if(beginMon != ""){
		$("#beginMonReplace").val(beginMon.substring(0,4) +"-"+ beginMon.substring(4));
	}
	
	if(emdMon != ""){
		$("#endMonReplace").val(emdMon .substring(0,4) +"-"+ emdMon .substring(4));
	}
});

function doSave(){
	$("#dataForm").bootstrapValidator("validate");
    if (!$("#dataForm").data("bootstrapValidator").isValid()) return;

    var datas = $("#dataForm").serialize();
	$.ajax({
		url:"${ctx}/admin/salaryItem/doUpdateDefine",
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
}

function changeMon(id){
	var date = $("#"+id+"Replace").val();
	$("#"+id).val(date.replace("-",""));
}
</script>
</head>
	<body>
		<div class="body-box-form">
			<div class="panel panel-system" >
			    <div class="panel-body" >
			      <div class="btn-group-xs" >
						<button type="button" class="btn btn-system " id="closeBut" onclick="closeWin(true)">
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
						<input type="hidden" id="beginMon" name="beginMon" value="${salaryItemStatCfg.beginMon }"/>
						<input type="hidden" id="endMon" name="endMon" value="${salaryItemStatCfg.endMon }"/>
						<input type="hidden" id="pk" name="pk" value="${salaryItemStatCfg.pk }"/>
						<ul class="ul-form">
							<div class="validate-group row">
								<li class="form-validate">
									<label>薪酬代码</label>
									<input type="text" id="salaryItem" name="salaryItem" style="width:160px;" 
											readonly="true" value="${salaryItemStatCfg.salaryItem }"/>
								</li>
								<li class="form-validate">
									<label>薪酬名称</label>
									<input type="text" id="salaryItemDescr" name="salaryItemDescr" style="width:160px;" 
											readonly="true" value="${salaryItemStatCfg.salaryItemDescr }"/>
								</li>
							</div>
							<div class="validate-group row" hidden="true">
								<li class="form-validate">
									<label class="control-textarea">条件说明</label>
									<textarea id="filterRemarks" name="filterRemarks" rows="3" readonly="true">${salaryItemStatCfg.filterRemarks }</textarea>
								</li>
							</div>
							<div class="validate-group row">
								<li class="form-validate">
									<label>条件公式</label>
									<input id="filterFormula" name="filterFormula" type="text" readonly="true" value="${salaryItemStatCfg.filterFormula }"/>
								</li>
							</div>
							<div class="validate-group row">
								<li>
									<label>生效时间</label>
									<input type="text" id="beginMonReplace" name="beginMonReplace" class="i-date" 
										style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM'})" 
										disabled="true" onchange="changeMon('beginMon')"/>
								</li>
								<li>
									<label>结束时间</label>
									<input type="text" id="endMonReplace" name="endMonReplace" class="i-date" 
										style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM'})" 
										disabled="true" onchange="changeMon('endMon')"/>
								</li>
							</div>
							<div class="validate-group row">
								<li class="form-validate">
									<label class="control-textarea">公式说明</label>
									<textarea id="remarks" name="remarks" rows="3" readonly="true">${salaryItemStatCfg.remarks }</textarea>
								</li>
							</div>
							<div class="validate-group row">
								<li>
									<label>计算方式</label>
									<house:xtdm  id="calcMode" dictCode="SALCALCMODE" style="width:160px;" 
										disabled="true" value="${salaryItemStatCfg.calcMode }"></house:xtdm>
								</li>
								<li>
									<label>计算优先级</label>
									<house:dict id="filterLevel" dictCode="" sql="select 0 code ,0 descr" 
												sqlValueKey="code" sqlLableKey="descr" value="${salaryItemStatCfg.filterLevel }" disabled="true"></house:dict>
								</li>
							</div>
							<div class="validate-group row">
								<li class="form-validate">
									<label class="control-textarea">条件显示</label>
									<textarea id="formulaShow" name="formulaShow" rows="3" readonly="true">${salaryItemStatCfg.formulaShow }</textarea>
								</li>
							</div>
							<div class="validate-group row">
								<li class="form-validate">
									<label class="control-textarea">条件公式</label>
									<textarea id="formula" name="formula" rows="3" readonly="true">${salaryItemStatCfg.formula }</textarea>
								</li>
							</div>
						</ul>
					</form>
				</div>
			</div>
		</div>
	</body>	
</html>
