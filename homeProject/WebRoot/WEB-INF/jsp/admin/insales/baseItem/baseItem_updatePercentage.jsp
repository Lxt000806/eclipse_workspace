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
	<title>基础项目管理修改业绩比例</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	
	<script type="text/javascript"> 
	$(function() {
		$("#dataForm").bootstrapValidator({
			message : 'This value is not valid',
			feedbackIcons : {/*input状态样式图片*/
				validating : 'glyphicon glyphicon-refresh'
			},
			fields: {  
				perfPer:{  
					validators: {  
						notEmpty: {  
							message: '业绩比例不能为空'  
						}
					}  
				},
			},
			submitButtons : 'input[type="submit"]'/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
		});
	});
	$(function(){
		$("#saveBtn").on("click",function(){
			$("#dataForm").bootstrapValidator('validate');
			if(!$("#dataForm").data('bootstrapValidator').isValid()) return;
			var datas = $("#dataForm").serialize();
			var perfPer = $.trim($("#perfPer").val());
			if(perfPer > 1){
				art.dialog({
					content:"业绩比例只能在0-1之间",
				});
				return;
			}
			$.ajax({
				url:"${ctx}/admin/baseItem/doUpdatePercentage",
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
		});
	});
	</script>
</head>
	<body>
		<div class="body-box-form">
			<div class="panel panel-system">
			    <div class="panel-body">
			    	<div class="btn-group-xs">
						<button type="button" class="btn btn-system" id="saveBtn">
							<span>保存</span>
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
						<input type="hidden" name="jsonString" value="" />
						<ul class="ul-form">
							<div class="validate-group row">
								<li>
									<label>基础项目编号</label> 
									<input type="text" id="code" name="code" style="width:160px;" value="${baseItem.code }" readonly="readonly" />
								</li>
							</div>
							<div class="validate-group row">
								<li class="form-validate">
									<label>基装项目名称</label> 
									<input type="text" id="descr" name="descr" style="width:160px;" value="${baseItem.descr }" readonly="true"/>
								</li>
								<li class="form-validate">
									<label>项目分类</label> 
									<house:xtdm id="category" dictCode="BASEITEMCAT" value="${baseItem.category}" disabled="true"></house:xtdm>
							
								</li>
							</div>
							<div class="validate-group row">
								<li class="form-validate">
									<label>基装类型1</label> 
									 <house:DataSelect id="baseItemType1" className="BaseItemType1" keyColumn="code" valueColumn="descr" value="${baseItem.baseItemType1 }" disabled="true"></house:DataSelect>
								</li>
								<li class="form-validate">
									<label>基装类型2</label> 
									 <house:DataSelect id="baseItemType2" className="BaseItemType2" keyColumn="code" valueColumn="descr" value="${baseItem.baseItemType2 }" disabled="true"></house:DataSelect>
								</li>
							</div>
							<div class="validate-group row">
								<li class="form-validate">
									<label>单位</label> 
									 <house:DataSelect id="uom" className="Uom" keyColumn="code" valueColumn="descr" value="${baseItem.uom }" disabled="true"></house:DataSelect>
								</li>
								<li class="form-validate">
									<label><span class="required">*</span>业绩比例</label> 
									<input type="text" id="perfPer" name="perfPer" onkeyup="value=value.replace(/[^\?\d.]/g,'')" style="width:160px;" value="${baseItem.perfPer }"/>
								</li>
							</div>
							<div class="validate-group row">
								<li class="form-validate">
									<label class="control-textarea">备注</label>
									<textarea id="remark" name="remark" rows="2" readonly="true">${baseItem.remark }</textarea>
								</li>
							</div>
						</ul>
					</form>
				</div>
			</div>
		</div>
	</body>	
</html>
