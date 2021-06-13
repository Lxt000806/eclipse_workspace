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
$(function() {
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
	
});

function doSave(){
	$("#dataForm").bootstrapValidator("validate");
    if (!$("#dataForm").data("bootstrapValidator").isValid()) return;

    var datas = $("#dataForm").serialize();
	$.ajax({
		url:"${ctx}/admin/salaryItem/doSaveDefine",
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
							<div class="validate-group row">
								<li class="form-validate">
									<label class="control-textarea">筛选条件说明</label>
									<textarea id="filterRemarks" name="filterRemarks" rows="3" readonly="true">${salaryItemStatCfg.filterRemarks }</textarea>
								</li>
							</div>
							<div class="validate-group row">
								<li class="form-validate">
									<label class="control-textarea">筛选条件</br>公式显示</label>
									<textarea id="filterFormulaShow" name="filterFormulaShow" rows="3" readonly="true">${salaryItemStatCfg.filterFormulaShow}</textarea>
								</li>
							</div>
							<div class="validate-group row">
								<li class="form-validate">
									<label class="control-textarea">筛选条件公式</label>
									<textarea id="filterFormula" name="filterFormula" rows="3" readonly="true">${salaryItemStatCfg.filterFormula }</textarea>
								</li>
							</div>
							<div class="validate-group row">
								<li>
									<label>生效时间</label>
									<input type="text" id="beginMon" name="beginMon" class="i-date" disabled="true"
										style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM'})" value="${salaryItemStatCfg.beginMon }"/>
								</li>
								<li>
									<label>结束时间</label>
									<input type="text" id="endMon" name="endMon" class="i-date" disabled="true"
										style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM'})" value="${salaryItemStatCfg.endMon}"/>
								</li>
							</div>
							<div class="validate-group row">
								<li class="form-validate">
									<label class="control-textarea">条件公式</label>
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
									<house:dict id="filterLevel" dictCode="" sql="select p.number code ,p.number descr from master..spt_values p 
												where p.type = 'p' and p.number between 1 and 9 
												union select 99 code ,99 descr" 
												disabled="true" sqlValueKey="code" sqlLableKey="descr" value="${salaryItemStatCfg.filterLevel }"></house:dict>
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
