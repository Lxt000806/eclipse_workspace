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
		var m_umState = "${brand.m_umState}";
		$(function(){
			if ("M" == m_umState || "V" == m_umState) $("#expired_show").parent().attr("hidden", false);

			Global.LinkSelect.initSelect("${ctx}/admin/item/itemType","itemType1","itemType2");
			if ("A" != m_umState) {
				Global.LinkSelect.setSelect({
					firstSelect:"itemType1",
					firstValue:"${brand.itemType1}",
					secondSelect:"itemType2",
					secondValue:"${brand.itemType2}",
				});
			}
			
			$("#page_form").bootstrapValidator({
				message: "请输入完整的信息",
				feedbackIcons: {
					validating: "glyphicon glyphicon-refresh"
				},
				fields: {
					descr:{  
						validators: {  
							notEmpty: {  
								message: "名称不能为空"  
							},
						}  
					},
					itemType1:{  
						validators: {  
							notEmpty: {  
								message: "材料类型1不能为空"  
							},
						}  
					},
					itemType2:{  
						validators: {  
							notEmpty: {  
								message: "材料类型2不能为空"  
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
				var url = "${ctx}/admin/brand/doUpdate";
			} else {
				var url = "${ctx}/admin/brand/doSave";
			}
			Global.Form.submit("page_form", url, {m_umState:m_umState}, function(ret){
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
					<input type="hidden" name="expired" id="expired" value="${brand.expired}">
					<house:token></house:token>
					<ul class="ul-form">
						<li>
							<label><span class="required">*</span>编码</label>
							<input type="text" id="code" name="code" style="width:160px;" value="${brand.code}" 
								placeholder="保存自动生成" readonly="readonly"/>
						</li>
						<li class="form-validate">
							<label><span class="required">*</span>名称</label>
							<input type="text" id="descr" name="descr" style="width:160px;" value="${brand.descr}" 
								autofocus/>
						</li>
						<li class="form-validate">
							<label for="itemType1"><span class="required">*</span>材料类型1</label>
							<select id="itemType1" name="itemType1"></select>
						</li>
						<li class="form-validate">
							<label for="itemType2"><span class="required">*</span>材料类型2</label>
							<select id="itemType2" name="itemType2"></select>
						</li>
						<li hidden="true">
							<label>过期</label>
							<input type="checkbox" id="expired_show" name="expired_show" value="${brand.expired}" 
								onclick="checkExpired(this)" ${brand.expired=="T"?"checked":""}/>
						</li>
					</ul>	
				</form>
			</div>
		</div>
	</div>
</body>
</html>
