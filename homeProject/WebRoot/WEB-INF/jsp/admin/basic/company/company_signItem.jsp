<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8"/>
	<META HTTP-EQUIV="pragma" CONTENT="no-cache"/>
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"/>
	<META HTTP-EQUIV="expires" CONTENT="0"/>
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge"/>
	<%@ include file="/commons/jsp/common.jsp"%>
	<script src="${resourceRoot}/pub/component_company.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_latitude.js?v=${v}" type="text/javascript"></script>
</head>
<body>
	<div class="body-box-form">
		<div class="panel panel-system">
		    <div class="panel-body">
		      <div class="btn-group-xs">
					<button type="button" class="btn btn-system " id="saveBtn">
						<span>保存</span>
					</button>
					<button type="button" class="btn btn-system " id="closeBtn">
						<span>关闭</span>
					</button>
				</div>
			</div>
		</div>
		<div class="panel panel-info" style="margin: 0px;">  
			<div class="panel-body">
				<form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
					<house:token></house:token><!-- 按钮只能点一次 -->
					<input type="hidden" id="lastUpdatedBy" name="lastUpdatedBy" value="${sessionScope.USER_CONTEXT_KEY.czybh}"/>
					<input type="hidden" name="pK" value="${signPlace.pK}">
					<input type="hidden" id="expired" name="expired" value="${signPlace.expired}"/>
					<ul class="ul-form">
						<div class="row">
							<li class="form-validate">
								<label><span class="required">*</span>打卡地点</label>
								<input type="text" id="descr" name="descr" style="width:189px;" value="${signPlace.descr}"/>
							</li>
						</div>
						<div class="row">
							<li class="form-validate">
								<label>公司编号</label>
								<input type="text" id="cmpCode" name="cmpCode" style="width:189px;"/>
							</li>
						</div>
						<div class="validate-group row">
							<li class="form-validate">
								<label><span class="required">*</span>经纬度</label> 
								<input type="text" id="tude" name="tude" style="width:189px;" />
							</li>
						</div>
						<div class="row">
							<li class="form-validate">
								<label>打开限制距离</label>
								<input type="text" id="limitDistance" name="limitDistance" style="width:189px;" value="${signPlace.limitDistance}"
									onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\-\.\d]/g,'');"
									onafterpaste="this.value =this.value.replace(/\s/g,'').replace(/[^\-\.\d]/g,'');"/>
							</li>
						</div>
						<div class="row" id="expired_div">
							<li>
								<label>过期</label>
								<input type="checkbox" id="expired_show" name="expired_show" value="${signPlace.expired}" 
										onclick="checkExpired(this)" ${signPlace.expired=="T"?"checked":""}/>
							</li>
						</div>
					</ul>	
				</form>
			</div>
		</div>
	</div>
</body>
	<script type="text/javascript" defer>
		var url = "";
		function refreshTude(data) {
			var tude=$("#tude").val().replace("|","%7C");
			$("#tude").openComponent_latitude({
				condition:{
					descr:$("#descr").val(),
					tude:tude,
				},
				callBack:refreshTude
			});
			$("#page_form").data("bootstrapValidator")  
				.updateStatus("openComponent_latitude_tude", "NOT_VALIDATED", null)
				.validateField("openComponent_latitude_tude");
		}
		$(function() {
			switch ("${signPlace.m_umState}") {
			case "M":
				$("#cmpCode").openComponent_company({
					showValue:"${signPlace.cmpCode}",
					showLabel:"${signPlace.cmpCodeDescr}",
					readonly:true
				});
				$("#tude").openComponent_latitude({
					showValue:"${signPlace.longitudetppc}|${signPlace.latitudetppc}",
					condition:{
						descr:$("#descr").val(),
						tude:"${signPlace.longitudetppc}%7C${signPlace.latitudetppc}",
					},
					callBack:refreshTude
				});
				url = "${ctx}/admin/company/doSignUpdate";
				break;
			default:
				$("#cmpCode").openComponent_company({
					showValue:"${signPlace.cmpCode}",
					showLabel:"${signPlace.cmpCodeDescr}",
					readonly:true
				});
				$("#tude").openComponent_latitude({
					callBack:refreshTude
				});
				url="${ctx}/admin/company/doSignAdd";
				$("#expired_div").remove();
				break;
			}
			$("#openComponent_company_cmpCode").css("width","164px");
			$("#openComponent_latitude_tude").css("width","164px");
			var dialog_id = "${signPlace.m_umState}"!=""?"updateItem":"addItem";
			parent.$("#iframe_"+dialog_id).attr("height","98%"); //消灭掉无用的滑动条
			$("#page_form").bootstrapValidator({
				message : "请输入完整的信息",
				feedbackIcons : {
					validating : "glyphicon glyphicon-refresh"
				},
				fields: { 
					descr:{ 
						validators: {  
							notEmpty: {  
								message: "打卡地点不允许为空"  
							},
						}  
					},
					openComponent_latitude_tude:{ 
						validators: {  
							notEmpty: {  
								message: "经纬度不允许为空"  
							},
						}  
					},
				}
			});
			$("#saveBtn").on("click", function () {
				$("#page_form").bootstrapValidator("validate");/* 提交验证 */
				if(!$("#page_form").data("bootstrapValidator").isValid()) return; /* 验证失败return */	
				var datas = $("#page_form").jsonForm();
				saveAjax(datas);
			});
			$("#closeBtn").on("click", function () {
				closeWin();
			});
		});
		function saveAjax(datas) {
			$.ajax({
				url:url,
				type: "post",
				data: datas,
				dataType: "json",
				cache: false,
				error: function(obj){
					showAjaxHtml({"hidden": false, "msg": "保存数据出错"});
				},
				success: function(obj){
					if(obj.rs){
						art.dialog({
							content: obj.msg,
							time: 700,
							beforeunload: function () {
								closeWin();
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
		}
	</script>
</html>
