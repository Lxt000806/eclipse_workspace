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
	<title>装修区域新增</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_prePlanArea.js?v=${v}" type="text/javascript"></script>

<script type="text/javascript"> 
$(function() {
	$("#notice").hide();

	if("${custType.mustImportTemp}" != "1"){
		$("#prePlanAreaLabel").hide();
	}
	
	function getPrePlanAreaData(data){
		if(!data) return;
		
		$("#descr").val(data.descr);
		$("#prePlanAreaDescr").val(data.descr);
	}
	
	$("#prePlanAreaPK").openComponent_prePlanArea({condition:{custCode:"${baseItemChg.custCode}"},callBack:getPrePlanAreaData});
	
	$("#dataForm").bootstrapValidator({
		message : "This value is not valid",
		feedbackIcons : {/*input状态样式图片*/
			validating : "glyphicon glyphicon-refresh"
		},
		fields: {
		    descr:{  
		        validators: {  
					notEmpty: {
		                message: "区域名称不能为空"
		            },
				}  
		    },
		},
        submitButtons : "input[type='submit']"/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
    });
	
	$("#saveBtn").on("click",function(){
		$("#dataForm").bootstrapValidator("validate");
	    if (!$("#dataForm").data("bootstrapValidator").isValid()) return;
		
		if($("#prePlanAreaPK").val() == ""){
			$("#prePlanAreaDescr").val("")
		}

		var datas = $("#dataForm").serialize();
		var descr = $("#descr").val();
		var prePlanAreaDescr = $("#prePlanAreaDescr").val();
		var prePlanAreaPK = $("#prePlanAreaPK").val();
		
		if (descr == "水电项目" || descr == "土建项目" || descr == "安装项目" || descr == "综合项目") {
      		art.dialog({
        		content: descr + "区域不能修改"
      		});
      		return;
    	}
		
		if(prePlanAreaPK == "" && "${custType.mustImportTemp}" && $.trim("${baseItemChg.baseTempNo}") != ""){
			art.dialog({
        		content:"对应空间不能为空",
      		});
      		return;
		}
		
		if(descr !="" && prePlanAreaDescr!="" && descr.indexOf(prePlanAreaDescr) != 0){
			$("#notice").show();
			return;
		} else {
			$("#notice").hide();
		}
		
		$.ajax({
			url:"${ctx}/admin/fixArea/addtFixArea",
			type: "post",
			data: datas,
			dataType: "json",
			cache: false,
			error: function(obj){
				showAjaxHtml({"hidden": false, "msg": "保存数据出错~"});
		    },
		    success: function(obj){
		    	if (obj) {
					art.dialog({
		            	content: "装修区域已存在！"
					});
		        } else {
		        	art.dialog({
						content: "新增成功！",
						time: 1000,
						beforeunload: function () {
		    				closeWin(true);
					    }
					});
		        }
		    }
		});
	});
});

function checkFixAreaDescr(){
	var descr = $("#descr").val();
	var prePlanAreaDescr = $("#prePlanAreaDescr").val();
	
	if(descr !="" && prePlanAreaDescr!="" && descr.indexOf(prePlanAreaDescr) != 0){
		$("#notice").show();
	} else {
		$("#notice").hide();
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
						<button type="button" class="btn btn-system" id="closeBut" onclick="closeWin(true)">
							<span>关闭</span>
						</button>
					</div>
				</div>
			</div>
			<div class="panel panel-info">  
				<div class="panel-body">
					<form action="" method="post" id="dataForm" class="form-search">
						<input type="hidden" name="jsonString" value=""/>
						<input type="hidden" name="custCode" id="custCode" value="${baseItemChg.custCode }"/>
						<input type="hidden" name="itemType1" id="itemType1" value="JZ"/>
						<input type="hidden" name="isService" id="isService" value="0"/>
						<input type="hidden" name="prePlanAreaDescr" id="prePlanAreaDescr" value=""/>
						<ul class="ul-form">
							<div class="validate-group row">
								<li class="form-validate">
									<label><span class="required" id="prePlanAreaLabel">*</span>对应空间</label>
									<input type="text" id="prePlanAreaPK" name="prePlanAreaPK" style="width:160px;"/>
								</li>
							</div>
							<div class="validate-group row">
								<li class="form-validate">
									<label><span class="required">*</span>区域名称</label>
									<input type="text" id="descr" name="descr" style="width:160px;" onkeyup="checkFixAreaDescr()"/>
									<span id="notice" style="color:red">区域名称以空间名称为前缀</span>
								</li>
							</div>
						</ul>
					</form>
				</div>
			</div>
		</div>
	</body>	
</html>
