<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://" + request.getServerName() + ":"+request.getServerPort()+path+"/";
String jasperPath = PathUtil.WEB_INF+"jsp/jasper/";
%>
<!DOCTYPE html>
<html lang="zh">
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>

	<style type="text/css">
		.panel-info {
			margin: 0px;
		}
		.panel-info .panel-body {
			height: 210px;
		}
		.form-search {
			padding-top: 55px;
		}
	</style>
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
				<form action="" method="post" id="page_form" class="form-search">
					<input type="hidden" name="jsonString" value=""/>
					<input type="hidden" id="lastupdatedby" name="lastupdatedby" value="${sessionScope.USER_CONTEXT_KEY.czybh}"/>
					<ul class="ul-form">
						<li class="form-validate">
							<label><span class="required">*</span>施工类型</label>
							<house:xtdm id="prjtype" dictCode="BASEITEMPRJTYPE" style="width:160px;" 
								unShowValue="${keys}" value=""/>
						</li>
					</ul>
				</form>
			</div>
		</div>
	</div>
</body>	
<script type="text/javascript"> 
$(function() {
	switch ("${baseAlgorithm.m_umState}") {
	case "M":
		$("#prjtype").val("${baseAlgorithm.prjType}");
		break;
	case "V":
		//不显示保存按钮
		$("#saveBtn").hide();
		$("#prjtype").val("${baseAlgorithm.prjType}");
		
		$("#prjtype").prop("disabled",true);
		break;
	default:
		break;
	}

	$("#page_form").bootstrapValidator({
		message : "请输入完整的信息",
		feedbackIcons : {/*input状态样式图片*/
			validating : "glyphicon glyphicon-refresh"
		},
		fields: {  
			prjtype:{  
				validators: {  
					notEmpty: {  
						message: "施工类型不能为空"  
					}
				}  
			},
		},
		submitButtons : "input[type='submit']"/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
	});

	$("#saveBtn").on("click",function(){
		$("#page_form").bootstrapValidator("validate");
		if(!$("#page_form").data("bootstrapValidator").isValid()){ 
			art.dialog({content: "无法保存，请输入完整的信息",width: 220});
			return;
		}
		
		/* 获得选择值，并将descr提取出来 */
		var prjTypeText=$("#prjtype").find("option:selected").text();
	 	var arr = prjTypeText.split(" ");
	 	var prjTypeDescr=arr[arr.length-1];
	 	
		/* 将page_form json化后赋给selectRows */
 		var selectRows = [];
		var datas=$("#page_form").jsonForm();
		datas.prjtypedescr=(prjTypeDescr=="请选择..."?"":prjTypeDescr);
	 	selectRows.push(datas);
	 	//console.log(selectRows);
		Global.Dialog.returnData = selectRows;
		
		closeWin();
	});
	
});
</script>
</html>
