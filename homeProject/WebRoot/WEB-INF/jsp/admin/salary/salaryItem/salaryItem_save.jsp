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
	<title>薪酬项目新增</title>
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
			status:{
				validators:{
					notEmpty:{
						message:"状态不能为空"
					}
				}
			},
			precision:{
				validators:{
					notEmpty:{
						message:"数值精度不能为空"
					}
				}
			},
			type:{
				validators:{
					notEmpty:{
						message:"统计属性不能为空"
					}
				}
			},
			plusMinus:{
				validators:{
					notEmpty:{
						message:"加减项不能为空"
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
		url:"${ctx}/admin/salaryItem/doSave",
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
						<button type="button" class="btn btn-system " id="saveBtn" onclick="doSave()">
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
						<house:token></house:token>
						<input type="hidden" name="jsonString" value=""/>
						<ul class="ul-form">
							<div class="validate-group row">
								<li class="form-validate">
									<label><span class="required">*</span>薪酬代码</label>
									<input type="text" id="code" name="code" style="width:160px;"/>
								</li>
								<li class="form-validate">
									<label><span class="required">*</span>薪酬名称</label>
									<input type="text" id="descr" name="descr" style="width:160px;"/>
								</li>
							</div>
							<div class="validate-group row">
								<li class="form-validate">
									<label><span class="required">*</span>系统保留项目</label>
									<house:xtdm  id="isSysRetention" dictCode="YESNO" style="width:160px;"></house:xtdm>
								</li>
								<li class="form-validate">
									<label><span class="required">*</span>支持手动修改</label>
									<house:xtdm  id="IsAdjustable" dictCode="YESNO" style="width:160px;"></house:xtdm>
								</li>
							</div>
							<div class="validate-group row">
								<li class="form-validate">
									<label><span class="required">*</span>计算优先级</label>
									<house:dict id="itemLevel" dictCode="" sql="select p.number code ,p.number descr from master..spt_values p 
												where p.type = 'p' and p.number between 1 and 30 " 
												sqlValueKey="code" sqlLableKey="descr"></house:dict>
								</li>
								<li class="form-validate">
									<label>薪酬项目分组</label>
									<house:xtdm  id="itemGroup" dictCode="SALITEMGROUP" style="width:160px;"></house:xtdm>
								</li>
							</div>
							<div class="validate-group row">
								<li class="form-validate">
									<label><span class="required">*</span>状态</label>
									<house:xtdm  id="status" dictCode="SALENABLESTAT" style="width:160px;" value="1"></house:xtdm>
								</li>
								<li class="form-validate">
									<label><span class="required">*</span>数值精度</label>
									<house:dict id="precision" dictCode="" sql="select p.number code ,p.number descr from master..spt_values p 
												where p.type = 'p' and p.number between 0 and 6 " 
												sqlValueKey="code" sqlLableKey="descr" value="2" ></house:dict>
								</li>
							</div>
							<div class="validate-group row">
								<li class="form-validate">
									<label><span class="required">*</span>统计属性</label>
									<house:xtdm  id="type" dictCode="SALITEMTYPE" style="width:160px;"></house:xtdm>
								</li>
								<li class="form-validate">
									<label><span class="required">*</span>加减项</label>
									<house:xtdm  id="plusMinus" dictCode="SALAPLUSMINUS" style="width:160px;" value="0"></house:xtdm>
								</li>
							</div>
							<div class="validate-group row">
								<li class="form-validate">
									<label class="control-textarea">薪酬说明</label>
									<textarea id="remarks" name="remarks" rows="3"></textarea>
								</li>
							</div>
						</ul>
					</form>
				</div>
			</div>
		</div>
	</body>	
</html>
