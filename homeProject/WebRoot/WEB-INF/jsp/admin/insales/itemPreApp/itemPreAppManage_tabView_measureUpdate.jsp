<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
	    <title>测量单修改</title>
		<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
		<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
		<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
		<META HTTP-EQUIV="expires" CONTENT="0" />
		<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
		<%@ include file="/commons/jsp/common.jsp" %>
		<script src="${resourceRoot}/pub/component_supplier.js?v=${v}" type="text/javascript"></script>
		<script src="${resourceRoot}/pub/component_czybm.js?v=${v}" type="text/javascript"></script>
		<script type="text/javascript">
			function validateRefresh(){
				$("#dataForm").data("bootstrapValidator")
							  .updateStatus("openComponent_supplier_supplCode", "NOT_VALIDATED", null)  
		                   	  .validateField("openComponent_supplier_supplCode")
		                   	  .updateStatus("remarks", "NOT_VALIDATED", null)  
		                      .validateField("remarks");                    
			}
			function validateRefresh_choice(){
				 $("#dataForm").data("bootstrapValidator")
			                   .updateStatus("openComponent_supplier_supplCode", "NOT_VALIDATED", null)  
			                   .validateField("openComponent_supplier_supplCode");                    
			}
			$(function(){					
				$("input").attr("readonly",true);
				$("#measureRemark").attr("readonly",true);
				$("#supplCode").openComponent_supplier({
					showLabel:"${itemPreMeasure.supplerDescr}",
					showValue:"${itemPreMeasure.supplCode}",
					condition:{
						preAppNo:"${itemPreMeasure.preAppNo}",
						itemRight:"${itemPreMeasure.itemRightForSupplier}"
					},
					callBack:validateRefresh_choice
				});
				$("#openComponent_supplier_supplCode").attr("readonly", true);
				$("#appCzy").openComponent_czybm({
					showLabel:"${itemPreMeasure.appCzyDescr}",
					showValue:"${itemPreMeasure.appCzy}"
				});
		   		$("#openComponent_czybm_appCzy").attr("readonly", true);
				$("#openComponent_czybm_appCzy").next().attr("data-disabled", true);
				$("#confirmCzy").openComponent_czybm({
					showLabel:"${itemPreMeasure.confirmCzyDescr}",
					showValue:"${itemPreMeasure.confirmCzy}"
				});
		   		$("#openComponent_czybm_confirmCzy").attr("readonly", true);
				$("#openComponent_czybm_confirmCzy").next().attr("data-disabled", true);
				$("#completeCzy").openComponent_czybm({
					showLabel:"${itemPreMeasure.completeCzyDescr}",
					showValue:"${itemPreMeasure.completeCzy}"
				});
		   		$("#openComponent_czybm_completeCzy").attr("readonly", true);
				$("#openComponent_czybm_completeCzy").next().attr("data-disabled", true);
				$("#status").attr("disabled", true);
				$("#dataForm").bootstrapValidator({
					message : "This value is not valid",
					feedbackIcons : {/*input状态样式图片*/
						invalid : "glyphicon glyphicon-remove",
						validating : "glyphicon glyphicon-refresh"
					},
					fields: {  
						openComponent_supplier_supplCode:{  
							validators: {  
								notEmpty: {  
									message: "供应商不能为空"  
								},
								stringLength: {
		                            max: 20,
		                            message: "长度不超过20个字符"
		                        } 
							}  
						},
						remarks:{
							validators:{
								stringLength: {
		                            max: 200,
		                            message: "长度不超过200个字符"
		                        } 
							}
						}
					},
					submitButtons : "input[type='submit']"/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
				});	
			});
			function save(){
				$("#dataForm").bootstrapValidator("validate");
				if(!$("#dataForm").data("bootstrapValidator").isValid()){
					return;
				}
				var datas = {
					pk:$("#pk").val(),
					supplCode:$("#supplCode").val(),
					remarks:$("#remarks").val(),
					preAppNo:$("#preAppNo").val()
				};
				
			 	$.ajax({
					url:"${ctx}/admin/itemPreApp/doUpdate_preMeasure",
					type: "post",
					data: datas,
					dataType: "json",
					cache: false,
					error: function(obj){			    		
						art.dialog({
							content: "保存出错,请重试",
							time: 3000,
							beforeunload: function () {
							}
						});
					},
					success: function(obj){
						if(obj.rs){
							art.dialog({
								content: obj.msg,
								time: 3000,
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
	</head>
	<body>
		<div class="body-box-form">
			<div class="panel panel-system" >
			    <div class="panel-body" >
			    	<div class="btn-group-xs" >
						<button id="saveBut" type="button" class="btn btn-system " onclick="save()">保存</button>
						<button id="closeBut" type="button" class="btn btn-system " onclick="closeWin(false)">关闭</button>	
					</div>
				</div>
			</div>
			<div class="panel panel-info" >  
				<div class="panel-body">
					<form action="" method="post" id="dataForm" enctype="multipart/form-data" class="form-search">
						<ul class="ul-form">
							<li>
								<label>领料预申请单号</label>
								<input type="text" id="preAppNo" name="preAppNo" value="${itemPreMeasure.preAppNo}"/>
							</li>
							<li>
								<label>申请人</label>
								<input type="text" id="appCzy" name="appCzy" value="${itemPreMeasure.appCzy}"/>
							</li>
							<li>	
								<label>接收人</label>
								<input type="text" id="confirmCzy" name="confirmCzy" value="${itemPreMeasure.confirmCzy}"/>
							</li>
							<li>
								<label>状态</label>
								<house:xtdm id="status" dictCode="MEASURESTATUS" value="${itemPreMeasure.status }" ></house:xtdm>
							</li>
							<li class="form-validate">
								<label>供应商</label>
								<input type="text" id="supplCode" name="supplCode" value="${itemPreMeasure.supplCode}"/>
							</li>
							<li>
								<label>下单时间</label>
								<input type="text" id="completeDate" name="completeDate" value="<fmt:formatDate value='${itemPreMeasure.completeDate }' pattern='yyyy-MM-dd HH:mm:ss'/>"/>
							</li>
							<li>
								<label>申请日期</label>
								<input type="text" id="date" name="date" value="<fmt:formatDate value='${itemPreMeasure.date }' pattern='yyyy-MM-dd HH:mm:ss'/>"/>
							</li>
							<li>
								<label>接收时间</label>
								<input type="text" id="confirmDate" name="confirmDate" value="<fmt:formatDate value='${itemPreMeasure.confirmDate }' pattern='yyyy-MM-dd HH:mm:ss'/>"/>
							</li>
							<li>
								<label>下单人</label>
								<input type="text" id="completeCzy" name="completeCzy" value="${itemPreMeasure.completeCzy}"/>
							</li>
							<li class="form-validate">
								<label class="control-textarea">备注</label>
								<textarea id="remarks" name="remarks" >${itemPreMeasure.remarks }</textarea>
							</li>
							<li>
								<label class="control-textarea">测量数据</label>
								<textarea id="measureRemark" name="measureRemark">${itemPreMeasure.measureRemark }</textarea>
							</li>
							<ul hidden>	
								<label>pk</label>
								<input type="text" id="pk" name="pk" value="${itemPreMeasure.pk}"/>
							</ul>	
						</ul>
					</form>
				</div>
			</div>
		</div>
	</body>
</html>

