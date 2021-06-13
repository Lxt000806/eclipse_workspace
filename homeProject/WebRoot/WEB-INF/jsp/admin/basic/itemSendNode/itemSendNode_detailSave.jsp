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
	<title>材料配送节点单明细新增</title>
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_baseCheckItem.js?v=${v}" type="text/javascript"></script>
	<script type="text/javascript">
	$(function(){ 
	
		if("${itemSendNode.m_umState}"==="V"){
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
				confItemType:{ 
					validators: {  
						notEmpty: {  
							message: "请输入完整的信息"  
						}, 
					}
				},
				beginNode:{ 
					validators: {  
						notEmpty: {  
							message: "请输入完整的信息"  
						}, 
					}
				},
				beginDateType:{ 
					validators: {  
						notEmpty: {  
							message: "请输入完整的信息"  
						}, 
					}
				},
				beginAddDays:{ 
					validators: {  
						notEmpty: {  
							message: "请输入完整的信息"  
						}, 
					}
				},
				endNode:{ 
					validators: {  
						notEmpty: {  
							message: "请输入完整的信息"  
						}, 
					}
				},
				endDateType:{ 
					validators: {  
						notEmpty: {  
							message: "请输入完整的信息"  
						}, 
					}
				},
				endAddDays:{ 
					validators: {  
						notEmpty: {  
							message: "请输入完整的信息"  
						}, 
					}
				},
				payNum:{ 
					validators: {  
						notEmpty: {  
							message: "请输入完整的信息"  
						}, 
					}
				},
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
			datas.confItemTypeDescr=getDescr("confItemType");
			datas.beginNodeDescr=getDescr("beginNode");
			datas.beginDateTypeDescr=getDescr("beginDateType");
			datas.endNodeDescr=getDescr("endNode");
			datas.endDateTypeDescr=getDescr("endDateType");
			console.log(datas);
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
							<label><span class="required">*</span>施工材料分类</label>
							<house:dict id="confItemType" dictCode="" sql="SELECT Code,Descr FROM tConfItemType WHERE Expired='F' ORDER BY DispSeq" sqlValueKey="Code" sqlLableKey="Descr" value="${itemSendNode.confItemType }"></house:dict>
						</li>
						<li class="form-validate">
							<label><span class="required">*</span>起始节点</label>
							<house:dict id="beginNode" dictCode="" sql="SELECT Code,Descr FROM tPrjItem1 WHERE Expired='F' ORDER BY Seq" sqlValueKey="Code" sqlLableKey="Descr" value="${itemSendNode.beginNode }"></house:dict>
						</li>
						<li class="form-validate">
							<label><span class="required">*</span>起始时间类型</label>
							<house:xtdm id="beginDateType" dictCode="ALARMDAYTYPE" unShowValue="4" value="${itemSendNode.beginDateType }"></house:xtdm>
						</li>
						<li class="form-validate">
							<label><span class="required">*</span>起始增加天数</label>
							<input type="text" id="beginAddDays" name="beginAddDays" style="width:160px;" value="${itemSendNode.beginAddDays }"/>
						</li>
						<li class="form-validate">
							<label><span class="required">*</span>结束节点</label>
							<house:dict id="endNode" dictCode="" sql="SELECT Code,Descr FROM tPrjItem1 WHERE Expired='F' ORDER BY Seq" sqlValueKey="Code" sqlLableKey="Descr" value="${itemSendNode.endNode }"></house:dict>
						</li>
						<li class="form-validate">
							<label><span class="required">*</span>结束时间类型</label>
							<house:xtdm id="endDateType" dictCode="ALARMDAYTYPE" unShowValue="4" value="${itemSendNode.endDateType }"></house:xtdm>
						</li>
						<li class="form-validate">
							<label><span class="required">*</span>结束增加天数</label>
							<input type="text" id="endAddDays" name="endAddDays" style="width:160px;" value="${itemSendNode.endAddDays}"/>
						</li>
						<li class="form-validate">
							<label><span class="required">*</span>付款期数</label>
							<input type="number" id="payNum" name="payNum" style="width:160px;" value="${itemSendNode.payNum}"/>
						</li>
					</div>
				</ul>	
			</form>
		</div>
	</div>
</div>
</body>
</html>


