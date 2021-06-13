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
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<title>水电后期安装材料明细新增</title>
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_baseCheckItem.js?v=${v}" type="text/javascript"></script>
	<script type="text/javascript">
	$(function(){ 
	
		if("${waterAftInsItem.m_umState}"==="V"){
			$("#saveBtn").remove();
			disabledForm("dataForm");
		}
		$("#saveBtn").on("click",function(){
			$("#dataForm").bootstrapValidator("validate");/* 提交验证 */
			console.log($("#dataForm").data("bootstrapValidator").isValid());
			if(!$("#dataForm").data("bootstrapValidator").isValid()) return; 
			doSave();
		});	
		
		$("#dataForm").bootstrapValidator({
			message : "This value is not valid",
			feedbackIcons : {/*input状态样式图片*/
				validating : "glyphicon glyphicon-refresh"
			},
			fields: { 
				descr:{ 
					validators: {  
						notEmpty: {  
							message: "请输入完整的信息"  
						}, 
					}
				},
				uom:{ 
					validators: {  
						notEmpty: {  
							message: "请输入完整的信息"  
						}, 
					}
				}
			}
		});
	});	
	
	
	function getDescr(name){
		var text=$("#"+name).find("option:selected").text();
	 	var arr = text.split(" ");
	 	var nameDescr=arr[arr.length-1];
		return nameDescr;
	 }
	
	
	function doSave(){
	 		var selectRows = [];
			var datas=$("#dataForm").jsonForm();
			datas.uomDescr=getDescr("uom");
		 	selectRows.push(datas);
			Global.Dialog.returnData = selectRows;
			closeWin();
	}
		
	</script>
</head>
<body>
	<div class="body-box-form">
        <div class="panel panel-system">
	    <div class="panel-body">
	      <div class="btn-group-xs">
				<button type="button" class="btn btn-system" id="saveBtn" >
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
				<house:token></house:token>
				<ul class="ul-form">	
					<div class="validate-group">
						<li class="form-validate">
							<label><span class="required">*</span>材料名称</label>
							<input type="text" id="descr" name="descr" style="width:160px;" value="${waterAftInsItem.descr }"/>
						</li>
						<li class="form-validate">
							<label><span class="required">*</span>单位</label>
							<house:dict id="uom" dictCode="" sql="SELECT Code,Descr FROM tUom WHERE Expired='F' ORDER BY Code" sqlValueKey="Code" sqlLableKey="Descr" value="${waterAftInsItem.uom }"></house:dict>
						</li>
					</div>
				</ul>	
			</form>
		</div>
	</div>
</div>
</body>
</html>


