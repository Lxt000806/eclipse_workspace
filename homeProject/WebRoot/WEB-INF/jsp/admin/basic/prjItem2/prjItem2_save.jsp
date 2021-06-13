<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh">
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8"/>
	<META HTTP-EQUIV="pragma" CONTENT="no-cache"/>
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"/>
	<META HTTP-EQUIV="expires" CONTENT="0"/>
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge"/>
	<%@ include file="/commons/jsp/common.jsp"%>
	<script src="${resourceRoot}/pub/component_workType2.js?v=${v}" type="text/javascript"></script>
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
					<input type="hidden" id="lastupdatedby" name="lastupdatedby" value="${sessionScope.USER_CONTEXT_KEY.czybh}"/>
					<input type="hidden" id="expired" name="expired" value="${prjItem2.expired}"/>
					<ul class="ul-form">
						<div class="row">
							<li class="form-validate">
								<label><span class="required">*</span>编号</label>
								<input type="text" id="code" name="code" style="width:160px;" value="${prjItem2.code}"/>
							</li>
						</div>
						<div class="row">
							<li class="form-validate">
								<label><span class="required">*</span>名称</label>
								<input type="text" id="descr" name="descr" style="width:160px;" value="${prjItem2.descr}"/>
							</li>
						</div>
						<div class="row">
							<li class="form-validate">
								<label><span class="required">*</span>施工项目</label>
								<house:xtdm id="prjItem" dictCode="PRJITEM" style="width:160px;" value="${prjItem2.prjItem}"/>
							</li>
						</div>
						<div class="row">
							<li class="form-validate">
								<label><span class="required">*</span>最少施工天数</label>
								<input type="text" id="minDay" name="minDay" style="width:160px;"
									onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\-\.\d]/g,'');" 
									value="${prjItem2.minDay}"/>
							</li>
						</div>
						<div class="row">
							<li class="form-validate">
								<label><span class="required">*</span>节点顺序</label>
								<input type="text" id="seq" name="seq" style="width:160px;"
									onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\-\.\d]/g,'');" 
									value="${prjItem2.seq}"/>
							</li>
						</div>
						<div class="row">
							<li class="form-validate">
								<label><span class="required">*</span>完成可申请工资</label>
								<house:xtdm id="isAppWork" dictCode="YESNO" style="width:160px;" value="${prjItem2.isAppWork}"/>
							</li>
						</div>
						<div class="row">
							<li class="form-validate">
								<label>工种分类12</label>
								<house:dict id="workType12" dictCode="" 
									sql="select Code,Code+' '+Descr Descr from tWorkType12 where Expired='F' order by Code" 
									sqlValueKey="Code" sqlLableKey="Descr" value="${prjItem2.workType12}"/>
							</li>
						</div>
						<div class="row">
							<li class="form-validate">
								<label>是否更新结束日期</label>
								<house:xtdm id="isUpEndDate" dictCode="YESNO" style="width:160px;" value="${prjItem2.isUpEndDate}"/>
							</li>
						</div>
						<div class="row">
							<li class="form-validate">
								<label>人工工种分类</label>
								<input type="text" name="offerWorkType2" id="offerWorkType2" style="width: 160px;">
							</li>
						</div>
						<div class="row" id="expired_div">
							<li>
								<label>过期</label>
								<input type="checkbox" id="expired_show" name="expired_show" value="${prjItem2.expired}" 
									onclick="checkExpired(this)" ${prjItem2.expired=="T"?"checked":""}/>
							</li>
						</div>
					</ul>	
				</form>
			</div>
		</div>
	</div>
	<script type="text/javascript" defer>
		var url = "", oldCode="";
		$(function() {
			switch ("${prjItem2.m_umState}") {
				case "M":
					url = "${ctx}/admin/prjItem2/doUpdate";
					$("#offerWorkType2").openComponent_workType2({
						showValue:"${prjItem2.offerWorkType2}",
						showLabel:"${prjItem2.offerWorkType2Descr}",
					});
					oldCode = "${prjItem2.code}";
					$("#code").prop("readonly", true);
					break;
				case "V":
					$("#saveBtn").remove();
					$("#offerWorkType2").openComponent_workType2({
						showValue:"${prjItem2.offerWorkType2}",
						showLabel:"${prjItem2.offerWorkType2Descr}",
						readonly:true
					});
					oldCode = "${prjItem2.code}";
					disabledForm();
					break;
				default:
					$("#offerWorkType2").openComponent_workType2();
					$("#expired_div").remove();
					url = "${ctx}/admin/prjItem2/doSave";
					break;
			}
			$("#page_form").bootstrapValidator({
				message : "请输入完整的信息",
				feedbackIcons : {
					validating : "glyphicon glyphicon-refresh"
				},
				fields: { 
					code:{ 
						validators: {  
							notEmpty: {  
								message: "编号不允许为空"  
							},
							remote: {
					            message: "该编号已重复",
					            url: "${ctx}/admin/prjItem2/checkCode",
					            data: {oldCode: oldCode},
					            delay:1000, 
					            type:"post", 
					        }
						}  
					},
					descr:{ 
						validators: {  
							notEmpty: {  
								message: "名称不允许为空"  
							},
						}  
					},
					prjItem:{ 
						validators: {  
							notEmpty: {  
								message: "施工项目不允许为空"  
							},
						}  
					},
					minDay:{ 
						validators: {  
							notEmpty: {  
								message: "最少施工天数不允许为空"  
							},
						}  
					},
					seq:{
						validators: {  
							notEmpty: {  
								message: "节点顺序不允许为空"  
							},
						}
					},
					isAppWork:{
						validators: {  
							notEmpty: {  
								message: "完成可申请工资不允许为空"  
							},
						}
					}
				}
			});
			$("#saveBtn").on("click", function () {
				if ("V" == "${prjItem2.m_umState}") return;
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
</body>
</html>
