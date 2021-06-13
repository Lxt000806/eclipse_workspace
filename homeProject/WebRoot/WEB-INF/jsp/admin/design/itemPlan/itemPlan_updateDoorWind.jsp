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
	<title>客户投诉管理添加</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_supplier.js?v=${v}" type="text/javascript"></script>
	
	<script type="text/javascript">
	$(function() {
		oldDatas=JSON.stringify($("#dataForm").jsonForm());
		$("#dataForm").bootstrapValidator({
			message : 'This value is not valid',
			feedbackIcons : {/*input状态样式图片*/
				validating : 'glyphicon glyphicon-refresh'
			},
			fields: {  
				length:{  
					validators: {  
						notEmpty: {  
							message: '长不能为空'  
						}
					}  
				},
				height:{  
					validators: {  
						notEmpty: {  
							message: '高不能为空'  
						}
					}  
				},
				type:{  
					validators: {  
						notEmpty: {  
							message: '类型不能为空'  
						}
					}  
				},
			},
			submitButtons : 'input[type="submit"]'/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
		});
	});
	 
	$(function(){
		$("#type").val("${map.type[0]}");
		$("#height").val("${map.height[0]}");
		$("#length").val("${map.length[0]}");
		$("#type").attr("disabled",true);
	
		$("#saveBtn").on("click",function(){
			$("#dataForm").bootstrapValidator('validate');
			if(!$("#dataForm").data('bootstrapValidator').isValid()) return;
			var selectRows = [];
			var datas=$("#dataForm").jsonForm();
		 	selectRows.push(datas);
			Global.Dialog.returnData = selectRows;
			closeWin();
		});
	});
	function changeDoorWind(){
		var type=$.trim($("#type").val());
		if(type=="1"){
			$("#length").val("");
			$("#height").val(2.1);
		}else{
			$("#length").val("");
			$("#height").val(1);
		}
			
	}
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
						<input type="hidden" name="jsonString" value=""/>
						<ul class="ul-form">
							<div class="validate-group row">
								<li class="form-validate">
									<label><span class="required">*</span>类型</label>
									<select type="text" id="type" name="type" onchange="changeDoorWind()" style="width:160px;">
										<option value="1" >门</option>
										<option value="2" >窗</option>
									</select>
								</li>
							</div>
							<div class="validate-group row">
								<li class="form-validate">
									<label>长</label>
									<input type="text" id="length" name="length" onkeyup="value=value.replace(/[^\-?\d.]/g,'')" style="width:160px;"/>
								</li>
								<li class="form-validate">
									<label>高</label>
									<input type="text" id="height" name="height" onkeyup="value=value.replace(/[^\-?\d.]/g,'')" style="width:160px;"/>
								</li>
							</div>
						</ul>
					</form>
				</div>
			</div>
		</div>
	</body>	
</html>
