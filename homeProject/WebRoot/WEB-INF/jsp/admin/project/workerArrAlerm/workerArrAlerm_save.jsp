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
	<title>工人安排提醒</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	
	<script type="text/javascript"> 
	$(function() {
		$("#dataForm").bootstrapValidator({
			message : 'This value is not valid',
			feedbackIcons : {/*input状态样式图片*/
				validating : 'glyphicon glyphicon-refresh'
			},
			fields: {  
				workType12:{  
					validators: {  
						notEmpty: {  
							message: '工种不能为空'  
						}
					}  
				},
				itemType1:{  
					validators: {  
						notEmpty: {  
							message: '材料类型1不能为空'  
						}
					}  
				},
				isNeedReq:{  
					validators: {  
						notEmpty: {  
							message: '是否判断需求不能为空'  
						}
					}  
				},
				jobType:{  
					validators: {  
						notEmpty: {  
							message: '任务类型不能为空'  
						}
					}  
				},
				isNeedStakeholder:{  
					validators: {  
						notEmpty: {  
							message: '是否判断干系人不能为空'  
						}
					}  
				}
			},
			submitButtons : 'input[type="submit"]'/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
		});
	});
	
	$(function(){
		Global.LinkSelect.initSelect("${ctx}/admin/item/itemType","itemType1","itemType2");
    	Global.LinkSelect.initSelect("${ctx}/admin/prjJobManage/prjTypeByItemType1", "itemType1", "jobType");
		$("#saveBtn").on("click",function(){
			$("#dataForm").bootstrapValidator('validate');
			if(!$("#dataForm").data('bootstrapValidator').isValid()) return;
			var datas = $("#dataForm").serialize();

			$.ajax({
				url:"${ctx}/admin/workerArrAlarm/doSave",
				type: "post",
				data: datas,
				dataType: "json",
				cache: false,
				error: function(obj){
					showAjaxHtml({"hidden": false, "msg": "保存数据出错~"});
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
		});
		
		/* itemType1改变后就检查jobType */
		$("#itemType1").change(function(){
			$("#dataForm").data("bootstrapValidator")  
				.updateStatus("jobType", "NOT_VALIDATED", null)
				.validateField("jobType");
		});
		
	});
	</script>
</head>
	<body>
		<div class="body-box-form">
			<div class="panel panel-system">
			    <div class="panel-body">
			    	<div class="btn-group-xs">
						<button type="button" class="btn btn-system" id="saveBtn">
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
						<input type="hidden" name="jsonString" value=""/>
						<ul class="ul-form">
							<div class="validate-group row">
								<li class="form-validate">
									<label>工种</label>
									 <house:DataSelect id="workType12" className="WorkType12" keyColumn="code" valueColumn="descr" value="${workerArrAlarm.workType12 }"></house:DataSelect>
								</li>
								<li class="form-validate">
									<label>是否判断需求</label>
									<house:xtdm id="isNeedReq" dictCode="YESNO" value="${workerArrAlarm.isNeedReq }"></house:xtdm>                     
								</li>
							</div>
							<div class="validate-group row">
								<li class="form-validate">
									<label>材料类型1</label>
									<select id="itemType1" name="itemType1" style="width: 160px;"></select>
								</li>
								<li class="form-validate">
									<label>材料类型2</label>
									<select id="itemType2" name="itemType2" style="width: 160px;" ></select>
								</li>
							</div>
							<div class="validate-group row">
								<li class="form-validate">
									<label>任务类型</label>
									 <house:DataSelect id="jobType" className="JobType" keyColumn="code" valueColumn="descr" value="${workerArrAlarm.jobType }"></house:DataSelect>
								</li>
								<li class="form-validate">
									<label>是否判断干系人</label>
									<house:xtdm id="isNeedStakeholder" dictCode="YESNO"></house:xtdm>
								</li>
							</div>
						</ul>
					</form>
				</div>
			</div>
		</div>
	</body>	
</html>
