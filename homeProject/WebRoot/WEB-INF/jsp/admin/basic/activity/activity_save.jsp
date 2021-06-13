<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<title>展会活动管理--增加</title>
		<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
		<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
		<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
		<META HTTP-EQUIV="expires" CONTENT="0" />
		<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
		<%@ include file="/commons/jsp/common.jsp" %>
	    <script src="${resourceRoot}/pub/component_cmpactivity.js?v=${v}" type="text/javascript"></script>
	    <script type="text/javascript">
	    	$(function() {
		    $("#custCode").openComponent_cmpactivity({callBack:validateRefresh});
			$("#dataForm").bootstrapValidator({
				message : 'This value is not valid',
				feedbackIcons : {
					validating : 'glyphicon glyphicon-refresh'
				},
				fields: {  

					actName : {
						validators : {
							notEmpty : {
								message : '名称不能为空'
							}
						}
					},
					times : {
						validators : {
							notEmpty : {
								message : '界数不能为空'
							}
						}
					},
					length : {
						validators : {
							notEmpty : {
								message : '编号长度不能为空'
							}
						}
					},
					sites : {
						validators : {
							notEmpty : {
								message : '地点不能为空'
							}
						}
					},
					beginDate : {
						validators : {
							notEmpty : {
								message : '开始时间不能为空'
							}
						}
					 },
					 endDate : {
						validators : {
							notEmpty : {
								message : '结束时间不能为空'
							}
						}
					}, 
				},
				submitButtons : 'input[type="submit"]'
			}); 
			$("#cmpActNo").openComponent_cmpactivity({showLabel:"${activity.cmpActDescr}",showValue:"${activity.cmpActNo}",
				condition:{status:'4'}});
			$("openComponent_cmpactivity_cmpActNo").attr("readonly",true);
			});
	    	function save(){
				$("#dataForm").bootstrapValidator("validate");
				if(!$("#dataForm").data("bootstrapValidator").isValid()) return;
				var datas = $("#dataForm").serialize();
				$.ajax({
					url : "${ctx}/admin/activity/doSave",
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
							<li>
								<label>编码</label>
								<input type="text" placeholder="自动生成" readonly="readonly" />
							</li>
							<li class="form-validate">	
								<label>名称</label>
								<input type="text" id="actName" name="actName"/>
							</li>
							<li>
								<label>编号前缀</label>
								<input type="text" id="prefix" name="prefix"/>
							</li>
							<li class="form-validate">
								<label>界数</label>
								<input type="text" id="times" name="times" />
							</li>
							<li class="form-validate">
								<label>编号长度</label>
								<input type="text" id="length" name="length"/>
							</li>
							<li class="form-validate">
								<label>地点</label>
								<input type="text" id="sites" name="sites" />
							</li>
							<li>
								<label>公司活动编码</label>
								<input type="text" id="cmpActNo" name="cmpActNo" value="${activity.cmpActNo}" />
							</li>
							<li class="form-validate">
								<label>开始时间从</label>
								<input type="text" id="beginDate" name="beginDate" class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate 
								value="${activity.beginDate}" pattern='yyyy-MM-dd'/>" />
							</li>
							<li class="form-validate">						
								<label>至</label>
								<input type="text" id="endDate" name="endDate" class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate 
								value="${activity.endDate}" pattern='yyyy-MM-dd'/>" />
							</li>	
							<li><label class="control-textarea">备注</label> <textarea id="remarks"
								name="remarks"></textarea>
							</li>	
						</ul>		
					</form>
				</div>
	  		</div>
	  	</div>
	 </body>
</html>


