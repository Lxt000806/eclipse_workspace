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
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8"/>
	<META HTTP-EQUIV="pragma" CONTENT="no-cache"/>
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"/>
	<META HTTP-EQUIV="expires" CONTENT="0"/>
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge"/>
	<%@ include file="/commons/jsp/common.jsp"%>
	<style type="text/css">
		.panel-info {
			margin: 0px;
		}
		.form-search .ul-form li label {
			width: 140px;
		}
	</style>
	<script type="text/javascript" defer>
		parent.$("#iframe_add").attr("height","98%");
		parent.$("#iframe_update").attr("height","98%");
		parent.$("#iframe_view").attr("height","98%");
		var m_umState = "${designFeeSd.m_umState}";
		var oldPosition = "${designFeeSd.position}", 
			oldCustType = "${designFeeSd.custType}"; //最初的级别
		$(function(){
			$("#page_form").bootstrapValidator({
				message: "请输入完整的信息",
				feedbackIcons: {
					validating: "glyphicon glyphicon-refresh"
				},
				fields: {
					position:{  
						validators: {  
							notEmpty: {  
								message: "设计师级别不能为空"  
							},
						}  
					},
					designFee:{  
						validators: {  
							notEmpty: {  
								message: "收费标准不能为空"  
							},
						}  
					},
					dispSeq:{  
						validators: {  
							notEmpty: {  
								message: "显示顺序不能为空"  
							},
						}  
					},
				},
				submitButtons: "input[type='submit']"
			});

			if ("V" == m_umState) {
				$("#saveBtn").remove();
				disabledForm();
			};
		});

		function doSave() {
			if ("V" == m_umState) return;
			$("#page_form").bootstrapValidator("validate");
			if(!$("#page_form").data("bootstrapValidator").isValid()) return;
			if ("M" == m_umState) {
				var url = "${ctx}/admin/designFeeSd/doUpdate";
			} else {
				var url = "${ctx}/admin/designFeeSd/doSave";
			}
			Global.Form.submit("page_form", url, 
				"M"==m_umState?{oldPosition:oldPosition, oldCustType:oldCustType}:undefined, function(ret){
				if(ret.rs==true){
					art.dialog({
						content: ret.msg,
						time: 500,
						beforeunload: function () {
							closeWin();
						}
					});
				}else{
					$("#_form_token_uniq_id").val(ret.token.token);
					art.dialog({
						content: ret.msg,
						width: 200
					});
				}
			});
		}
	</script>
</head>
<body>
	<div class="body-box-form">
		<div class="panel panel-system">
		    <div class="panel-body">
		      <div class="btn-group-xs">
					<button type="button" class="btn btn-system " id="saveBtn" onclick="doSave()">
						<span>保存</span>
					</button>
					<button type="button" class="btn btn-system " id="closeBut" onclick="closeWin(false);">
						<span>关闭</span>
					</button>
				</div>
			</div>
		</div>
		<div class="panel panel-info">  
			<div class="panel-body">
				<form class="form-search" id="page_form" action="" method="post" target="targetFrame">
					<house:token></house:token>
					<input type="hidden" name="pk" value="${designFeeSd.pk}">
					<ul class="ul-form">
						<li class="form-validate">
							<label><span class="required">*</span>设计师级别</label>
							<house:dict id="position" dictCode="" sql="select rtrim(Code)+' '+desc2 fd,Code from tPosition where type='4' order by Code" 
								sqlValueKey="Code" sqlLableKey="fd" value="${designFeeSd.position}"/>
						</li>
						<li class="form-validate">
							<label><span class="required">*</span>收费标准</label>
							<input type="text" id="designFee" name="designFee" style="width:160px;"
								onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\-\.\d]/g,'');"
								value="${designFeeSd.designFee}"/>
						</li>
						<li>
							<label>客户类型</label>
							<house:dict id="custType" dictCode="" sql="select rtrim(Code)+' '+Desc1 cd,Code from tCusttype order by dispSeq" 
								sqlValueKey="Code" sqlLableKey="cd" value="${designFeeSd.custType}"/>
						</li>
						<li class="form-validate">
							<label><span class="required">*</span>显示顺序</label>
							<input type="text" id="dispSeq" name="dispSeq" style="width:160px;"
								onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\-\.\d]/g,'');"
								value="${designFeeSd.dispSeq}"/>
						</li>
					</ul>	
				</form>
			</div>
		</div>
	</div>
</body>
</html>
