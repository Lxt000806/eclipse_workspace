<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<title>添加司机信息</title>
		<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
		<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
		<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
		<META HTTP-EQUIV="expires" CONTENT="0" />
		<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
		<%@ include file="/commons/jsp/common.jsp" %>

		<script type="text/javascript">
			// 保存按钮点击事件
			function save(){
				$("#dataForm").bootstrapValidator('validate');
					if(!$("#dataForm").data('bootstrapValidator').isValid()) return;
				// if($("#infoBoxDiv").css("display")!='none'){return false;}

				// 入职 & 离职时间逻辑关系
				var joinDate = $.trim($("#joinDate").val());
				var leaveDate = $.trim($("#leaveDate").val());
				// 判断离职时间是否为空
				if(leaveDate === ""){
					// 什么都不执行
				}else if(joinDate > leaveDate){ 
					art.dialog({content: "加入时间不能大于离开时间",width: 200});
					return false;
				}

				//验证
				var datas = $("#dataForm").serialize();
				$.ajax({
					url:'${ctx}/admin/driver/doSave',
					type: 'post',
					data: datas,
					dataType: 'json',
					cache: false,
					error: function(obj){
						showAjaxHtml({"hidden": false, "msg": '保存数据出错~'});
					},
					success: function(obj){
						if(obj.rs){
							art.dialog({
								content: obj.msg,
								time: 1000,
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
			//校验函数
			$(function() {
				$("#dataForm").bootstrapValidator({
					message : 'This value is not valid',
					feedbackIcons : {/*input状态样式图片*/
						validating : 'glyphicon glyphicon-refresh'
					},
					fields: {  
						driverType:{  
							validators: {  
								notEmpty: {  
									message: '必填字段'  
								}
							}  
						},
						nameChi:{  
							validators: {  
								notEmpty: {  
									message: '必填字段'  
								}
							}  
						},
						gender:{  
							validators: {  
								notEmpty: {  
									message: '必填字段'  
								}
							}  
						},
						carNo:{  
							validators: {  
								notEmpty: {  
									message: '必填字段'  
								},
							}  
						},
						phone:{  
							validators: {  
								notEmpty: {  
									message: '必填字段'  
								},
								remote: {
									message: "该手机号码重复",
									url: "${ctx}/admin/driver/checkPhone",
						            delay:1500, 
						            type:"post",
						        },
							}  
						},
						mm:{  
							validators: {  
								notEmpty: {  
									message: '必填字段'  
								},
								identical: {
									field: 'checkmm',
									message: '两次密码不一致'
								},
								regexp: {
									regexp: /^[a-zA-Z0-9_]+$/,
									message: '密码只能包含大写、小写、数字和下划线'
								},stringLength: {
									min: 0,
									max: 32,
									message: '用户名长度必须在0到32位之间'
								}
							}  
						},
						checkmm:{
							validators: {
								notEmpty: {
									message: '必填字段'
								},
								identical: {
									field: 'mm',
									message: '两次密码不一致'
								}
							}
						},
						idNum:{  
							validators: {  
								notEmpty: {  
									message: "请输入完整的信息"  
								}
							}  
						},
					},
					submitButtons : 'input[type="submit"]'/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
				});	
			});
		</script>
	</head>
    
	<body>
		<div class="body-box-form">
			<div class="content-form">
				<!-- panelBar -->
				<div class="panel panel-system" >
					<div class="panel-body" >
						<div class="btn-group-xs" >
							<button type="button" class="btn btn-system " id="saveBtn" onclick="save()">
								<span>保存</span>
							</button>
							<button type="button" class="btn btn-system " id="closeBut" onclick="closeWin(false)">
								<span>关闭</span>
							</button>
						</div>
					</div>
				</div>
				<div class="panel panel-info" style="margin: 0px;">  
					<div class="panel-body">
						<form role="form" class="form-search" id="dataForm"  action="" method="post" target="targetFrame" >
							<house:token></house:token>
							<ul class="ul-form">
								<div class="row">
									<div class="col-xs-12">
										<li class="form-validate">
											<label><span class="required">*</span>编码</label>
											<input type="text" style="width:160px;" value="${driver.code}" disabled="disabled" placeholder="保存后自动生成"> 
										</li>
									</div>
									<div class="col-xs-6">
										<li class="form-validate">
											<label><span class="required">*</span>司机类型</label>
											<house:xtdm id="driverType" dictCode="DRIVERTYPE" value="${driver.driverType }"></house:xtdm>
										</li>
										<li class="form-validate">
											<label><span class="required">*</span>姓名</label>
											<input type="text" style="width:160px;" id="nameChi" name="nameChi" value="${driver.nameChi }"/> 
										</li>	
										<li class="form-validate">
											<label><span class="required">*</span>性别</label>
											<house:xtdm id="gender" dictCode="GENDER" value="${driver.gender }"></house:xtdm> 
										</li>	
										<li class="form-validate">
											<label><span class="required">*</span>车牌号</label>
											<input type="text" style="width:160px;" id="carNo" name="carNo" value="${driver.carNo }"/>
										</li>
										<li class="form-validate">
											<label><span class="required">*</span>电话</label>
											<input type="text" style="width:160px;" id="phone" name="phone" value="${driver.phone }"/>
										</li>
										<li class="form-validate">
											<label><span class="required">*</span>密码</label>
											<input type="password" id="mm" name="mm" style="width:160px;"  value="${driver.mm}" />
										</li>	
										<li class="form-validate">
											<label><span class="required">*</span>确认密码</label>
											<input type="password" id="checkmm" name="checkmm" style="width:160px;"  value="${driver.mm}" />
										</li>
									</div>
									<div class="col-xs-6">
										<li class="form-validate">
											<label><span class="required">*</span>身份证号</label>
											<input type="text" id="idNum" name="idNum" style="width:160px;"
												onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^xX\d]/g,'');"
												maxlength="20"/>
											<!-- <input type="text" style="width:160px;" id="idNum" name="idNum" value="${driver.idNum }"/>  -->
										</li>		
										<li class="form-validate">
											<label>加入日期</label>
											<input type="text" style="width:160px;" id="joinDate" name="joinDate" class="i-date" value="<fmt:formatDate value='${driver.joinDate}'/>" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-%d'})"/> 
										</li>
										<li class="form-validate">
											<label>离开日期</label>
											<input type="text" style="width:160px;" id="leaveDate" name="leaveDate" class="i-date" value="<fmt:formatDate value='${driver.leaveDate}'/>" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-%d'})"/>
										</li>	
										<li class="form-validate">
		                                     <label class="control-textarea">备注</label>
		                                     <textarea id="remarks" name="remarks" style="width: 160px;height: 125px;">${driver.remarks }</textarea>
		                                </li>
									</div>
									<div class="col-xs-12">
										<li class="form-validate">
											<label>家庭住址</label>
											<input type="text" style="width:500px;" id="address" name="address" value="${driver.address }"/>
										</li>	
									</div>
								</div>
							</ul>
						</form>
					</div>
				</div>
			</div>
		</div>
	</body>
</html>
