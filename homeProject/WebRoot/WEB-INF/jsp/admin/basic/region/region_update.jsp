<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<title>项目区域管理--修改</title>
		<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
		<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
		<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
		<META HTTP-EQUIV="expires" CONTENT="0" />
		<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
		<%@ include file="/commons/jsp/common.jsp" %>
		<script src="${resourceRoot}/pub/component_company.js?v=${v}" type="text/javascript"></script>
	    <script type="text/javascript">
	     	$(function() {
	     		$("#cmpCode").openComponent_company({
	     			showValue:"${region.cmpCode}",
	     			showLabel:"${region.cmpDescr}",
	     		});
	     		$("#openComponent_company_cmpCode").prop("readonly", true);
				$("#dataForm").bootstrapValidator({
					message : 'This value is not valid',
					feedbackIcons : {
						validating : 'glyphicon glyphicon-refresh'
					},
					fields: {  
	
						intSendType : {
							validators : {
								notEmpty : {
									message : '集成出货模式不能为空'
								}
							}
						},
						code : {
							validators : {
								notEmpty : {
									message : '区域编号不能为空'
								}
							}
						},
						descr : {
							validators : {
								notEmpty : {
									message : '区域名称不能为空'
								}
							}
						},
						waterLongPension : {
							validators : {
								notEmpty : {
									message : '防水远程补贴不能为空'
								}
							}
						},
					},
					submitButtons : 'input[type="submit"]'
				});  
			});
	     	function save(){
				$("#dataForm").bootstrapValidator("validate");
				if(!$("#dataForm").data("bootstrapValidator").isValid()) return;
				if ($("#descr").val()==$("#validDescr").val()) {
					$("#validDescr").val("1");
				}else{
					$("#validDescr").val("2");
				}
				var datas = $("#dataForm").serialize();
				$.ajax({
					url : "${ctx}/admin/region/doUpdate",
					type : "post",
					data : datas,
					dataType : "json",
					cache : false,
					error : function(obj) {
						showAjaxHtml({
							"hidden" : false,
							"msg" : "保存数据出错~"
						});
					},
					success : function(obj) {
						if (obj.rs) {
							art.dialog({
								content : obj.msg,
								time : 1000,
								beforeunload : function() {
									closeWin();
								}
							});
						} else {
							$("#_form_token_uniq_id").val(obj.token.token);
							art.dialog({
								content : obj.msg,
								width : 200
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
			    		<button type="submit" class="btn btn-system" id="saveBut" onclick="save()">保存</button>
	     				<button id="closeBut" type="button" class="btn btn-system "   onclick="closeWin()">关闭</button>
					</div>
				</div>
			</div>
			<div class="panel panel-info" >  
				<div class="panel-body">
					<form action="" method="post" id="dataForm" class="form-search">
						<input type="text" id="expired" name="expired" value="${region.expired}" hidden="true" />	
						<input type="text" id="validDescr" name="validDescr" value="${region.validDescr}" hidden="true" />	
						<ul class="ul-form">					  
							<li class="form-validate">
								<label><span class="required">*</span>区域编号</label>
								<input type="text" id="code" name="code" value="${region.code}" readonly="readonly"/>
							</li>
							<li class="form-validate">	
								<label><span class="required">*</span>区域名称</label>
								<input type="text" id="descr" name="descr" value="${region.descr}"/>
							</li>
							<li>
								<label>是否指定工人</label>
								<house:dict id="isSpcWorker" dictCode="" sql="select cbm,cbm+' '+note note from tXTDM a where a.ID='YESNO' " 
						 				sqlValueKey="cbm" sqlLableKey="note" value="${region.isSpcWorker}"></house:dict>
							</li>
							<li>
								<label for="cmpCode">公司编号</label>
								<input type="text" id="cmpCode" name="cmpCode">
							</li>
							<li class="form-validate">	
								<label><span class="required">*</span>集成出货模式</label>
								<house:dict id="intSendType" dictCode="" sql="select cbm,cbm+' '+note note from tXTDM a where a.ID='INTSENDTYPE' " 
						 				sqlValueKey="cbm" sqlLableKey="note" value="${region.intSendType}"></house:dict>
							</li>
							<li class="form-validate">	
								<label><span class="required">*</span>防水远程补贴</label>
								<input type="text" id="waterLongPension" name="waterLongPension" value="${region.waterLongPension}"/>
							</li>
							<li>
								<label>过期</label>
								<input type="checkbox" id="expired_show" name="expired_show"
									onclick="checkExpired(this)" ${region.expired=="T"?"checked":""} />
							</li>
						</ul>	
					</form>
				</div>
	  		</div>
	  	</div>
	 </body>
</html>


