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
	<title>验收填写值新增</title>
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
		message : "This value is not valid",
		feedbackIcons : {/*input状态样式图片*/
			validating : "glyphicon glyphicon-refresh"
		},
		fields: {  
			descr:{  
				validators: {  
					notEmpty: {  
						message: "填写项目不能为空"  
					}
				}  
			},
			type:{  
				validators: {  
					notEmpty: {  
						message: "填写类型不能为空"  
					}
				}  
			},
			prjItem:{  
				validators: {  
					notEmpty: {  
						message: "施工项目不能为空"  
					}
				}  
			},
			dispSeq:{  
				validators: {  
					notEmpty: {  
						message: "顺序不能为空"  
					}
				}  
			},
		},
		submitButtons : "input[type='submit']"/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
	});
});	
$(function() {
	$("#saveBtn").on("click",function(){
		$("#dataForm").bootstrapValidator("validate");
		if (!$("#dataForm").data("bootstrapValidator").isValid()) return;
        
        var datas = $("#dataForm").serialize();
    
		$.ajax({
			url:"${ctx}/admin/confirmCustomFiled/doSave",
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

function chgType(){
	var type = $("#type").val();
	if(type == "1"){
		$("#options_li").show();
	} else {
		$("#options_li").hide();
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
						<button type="button" class="btn btn-system " id="closeBut" onclick="closeWin(false)">
							<span>关闭</span>
						</button>
					</div>
				</div>
			</div>
			<div class="panel panel-info" >  
				<div class="panel-body">
					<form action="" method="post" id="dataForm" class="form-search">
						<input type="hidden" name="jsonString" value="" />
						<ul class="ul-form">
							<li class="form-validate">
								<label>编号</label>
								<input type="text" id="code" name="code" style="width:160px;" value="" placeholder="保存后自动生成" readonly="true"/>
							</li>
							<li class="form-validate">
								<label><span class="required">*</span>填写项目名称</label>
								<input type="text" id="descr" name="descr" style="width:160px;" value=""/>
							</li>
							<li class="form-validate">
	                            <label><span class="required">*</span>施工项目</label>
	                            <house:dict id="prjItem" dictCode="" sqlValueKey="Code" sqlLableKey="Descr"
								    sql="select Code, Descr  from tPrjItem1 where Expired = 'F' order by Seq"></house:dict>
							<li class="form-validate">
								<label><span class="required">*</span>填写类型</label>
								<house:xtdm id="type" dictCode="CONFFILEDTYPE"  style="width:160px;" onchange="chgType()"></house:xtdm>
							</li>
							<li class="form-validate" id="options_li">
	                            <label>列表项目</label>
								<input type="text" id="options" name="options" style="width:160px;" value="${confirmCustomFiled.options }"/>
	                        </li>
							<li class="form-validate">
	                            <label><span class="required">*</span>顺序</label>
								<input type="text" id="dispSeq" name="dispSeq" style="width:160px;" value="${confirmCustomFiled.dispSeq }"/>
	                        </li>
						</ul>	
					</form>
				</div>
			</div>
		</div>
	</body>	
</html>
