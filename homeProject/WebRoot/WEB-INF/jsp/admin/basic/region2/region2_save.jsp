<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<title>项目区域管理2--增加</title>
		<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
		<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
		<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
		<META HTTP-EQUIV="expires" CONTENT="0" />
		<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
		<%@ include file="/commons/jsp/common.jsp" %>
	    <script type="text/javascript">
	     	$(function() {
				$("#dataForm").bootstrapValidator({
					message : 'This value is not valid',
					feedbackIcons : {
						validating : 'glyphicon glyphicon-refresh'
					},
					fields: {  
						descr : {
							validators : {
								notEmpty : {
									message : '区域名称不能为空'
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
				var datas = $("#dataForm").serialize();
				$.ajax({
					url : "${ctx}/admin/region2/doSave",
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
						<ul class="ul-form">					  
							<li class="form-validate">
								<label>区域编号</label>
								<input type="text" id="code" name="code" placeholder="保存时自动生成" readonly="readonly"/>
							</li>
							<li class="form-validate">	
								<label>区域名称</label>
								<input type="text" id="descr" name="descr"/>
							</li>
							<li>
								<label>区域分类</label>
								<house:dict id="regionCode" dictCode="" sql="select code,code+' '+descr descr from tRegion a where a.Expired='F' order by Code ASC " 
				 				sqlValueKey="code" sqlLableKey="descr"></house:dict>
							</li>	
							<li>
								<label>工程大区</label>
								<house:dict id="prjRegionCode" dictCode="" 
									sql="select Code,Code+' '+Descr descr from tPrjRegion where Expired='F' order by Code " 
				 					sqlValueKey="code" sqlLableKey="descr"></house:dict>
							</li>
						</ul>		
					</form>
				</div>
	  		</div>
	  	</div>
	 </body>
</html>


