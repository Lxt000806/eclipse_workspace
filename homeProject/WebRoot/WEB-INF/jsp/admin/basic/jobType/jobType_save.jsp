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
					<input type="hidden" id="expired" name="expired" value="${jobType.expired}"/>
					<ul class="ul-form">
						<div class="row">
							<div class="col-sm-6">
								<li class="form-validate">
									<label><span class="required">*</span>编号</label>
									<input type="text" id="code" name="code" style="width:160px;" value="${jobType.code}"/>
								</li>
								<li class="form-validate">
									<label><span class="required">*</span>材料类型1</label>
									<house:dict id="itemType1" dictCode="" 
										sql="select Code,Code+' '+Descr Descr from tItemType1 where Expired='F' " 
										sqlLableKey="Descr" sqlValueKey="Code" value="${jobType.itemType1}" />
								</li>
								<li>
									<label>名称</label>
									<input type="text" id="descr" name="descr" style="width:160px;" value="${jobType.descr}"/>
								</li>
								<li class="form-validate">
									<label><span class="required">*</span>是否选择处理人</label>
									<house:xtdm id="chooseMan" dictCode="YESNO" value="${jobType.chooseMan}"/>
								</li>
								<li>
									<label>一级部门</label>
									<house:dict id="department1" dictCode="" 
										sql="select Code,Code+' '+Desc1 Descr from tDepartment1 where Expired='F' " 
										sqlLableKey="Descr" sqlValueKey="Code" value="${jobType.department1}"/>
								</li>
								<li>
									<label>二级部门</label>
									<house:dict id="department2" dictCode="" 
										sql="select Code,Code+' '+Desc1 Descr from tDepartment2 where Expired='F' " 
										sqlLableKey="Descr" sqlValueKey="Code" value="${jobType.department2}"/>
								</li>
								<li>
									<label>职位</label>
									<house:DictMulitSelect id="position" dictCode="" 
										sql="select rtrim(Code) Code,Desc2 from tPosition where Expired='F' " 
										sqlLableKey="Desc2" sqlValueKey="Code" selectedValue="${jobType.position}">
									</house:DictMulitSelect>
								</li>
								<li>
									<label>角色</label>
									<house:dict id="role" dictCode="" 
										sql="select Code,Code+' '+Descr Descr from troll where Expired='F' " 
										sqlLableKey="Descr" sqlValueKey="Code" value="${jobType.role}"/>
								</li>
								<li>
									<label>是否橱柜</label>
									<house:xtdm id="isCupboard" dictCode="YESNO" value="${jobType.isCupboard}"/>
								</li>
							</div>
							<div class="col-sm-6">
								<li>
									<label>是否限定任务部门</label>
									<house:xtdm id="isJobDepart" dictCode="YESNO" value="${jobType.isJobDepart}"/>
								</li>
								<li>
									<label>是否指定供应商</label>
									<house:xtdm id="isNeedSuppl" dictCode="YESNO" value="${jobType.isNeedSuppl}"/>
								</li>
								<li>
									<label>显示客户电话</label>
									<house:xtdm id="isDispCustPhn" dictCode="YESNO" value="${jobType.isDispCustPhn}"/>
								</li>
								<li>
									<label>是否允许完工工地</label>
									<house:xtdm id="canEndCust" dictCode="YESNO" value="${jobType.canEndCust}"/>
								</li>
								<li>
									<label>是否需要拍照</label>
									<house:xtdm id="isNeedPic" dictCode="YESNO" value="${jobType.isNeedPic}"/>
								</li>
								<li>
									<label>有需求才允许申请</label>
									<select id="isNeedReq" name="isNeedReq">
										<option>请选择...</option>
										<option value="0">0 不控制</option>
										<option value="1">1 无需求不允许申请</option>
									</select>
								</li>
								<li class="form-validate">
									<label>项目未验收提醒</label>
									<house:xtdm id="prjItem" dictCode="PRJITEM" value="${jobType.prjItem}"/>
								</li>
								<li>
									<label>允许多次提交</label>
									<house:xtdm id="allowManySubmit" dictCode="YESNO" value="${jobType.allowManySubmit}"/>
								</li>
								<li>
									<label>是否材料发货任务</label>
									<house:xtdm id="isMaterialSendJob" dictCode="YESNO" value="${jobType.isMaterialSendJob}"/>
								</li>
								<li id="expired_li">
									<label>过期</label>
									<input type="checkbox" id="expired_show" name="expired_show" value="${jobType.expired}" 
										onclick="checkExpired(this)" ${jobType.expired=="T"?"checked":""}/>
								</li>
							</div>
							<div class="col-sm-12">
								<li style="max-height: 120px;">
									<label class="control-textarea" style="top: -60px;">备注</label>
									<textarea id="remarks" name="remarks" style="height: 80px;">${jobType.remarks}</textarea>
								</li>
							</div>
						</div>
					</ul>	
				</form>
			</div>
		</div>
	</div>
</body>
	<script type="text/javascript" defer>
		var m_umState="${jobType.m_umState}", url="";
		$(function() {
			$("#isNeedReq").val("${jobType.isNeedReq}");
			switch (m_umState) {
			case "A":
				$("#expired_li").remove();
				url = "${ctx}/admin/jobType/doSave";
				$("#isCupboard").prop("disabled",true);
				break;
			case "M":
				url = "${ctx}/admin/jobType/doUpdate";
				$("#code").prop("readonly",true);
				$("#isCupboard").prop("disabled",true);
				if("${jobType.itemType1}"=="JC"){
					$("#isCupboard").removeAttr("disabled");
				}
				break;
			default:
				$("#saveBtn").remove();
				disabledForm();
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
						}  
					},
					itemType1:{ 
						validators: {  
							notEmpty: {  
								message: "材料类型1不允许为空"  
							},
						}  
					},
					chooseMan:{ 
						validators: {  
							notEmpty: {  
								message: "是否选择处理人不允许为空"  
							},
						}  
					},
					/* prjItem:{ 
						validators: {  
							notEmpty: {  
								message: "项目未验收提醒不允许为空"  
							},
						}  
					}, */
				}
			});
			$("#itemType1").on("click", function () {
				if($("#itemType1").val()=="JC"){
					$("#isCupboard").removeAttr("disabled");
				}else{
					$("#isCupboard").prop("disabled",true);
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
