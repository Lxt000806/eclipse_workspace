<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
	    <title>领料预申请单录入备注</title>
		<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
		<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
		<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
		<META HTTP-EQUIV="expires" CONTENT="0" />
		<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
		<%@ include file="/commons/jsp/common.jsp" %>
<script type="text/javascript">
	function validateRefresh(){
		$("#dataForm").data("bootstrapValidator")
                   	  .updateStatus("remarks", "NOT_VALIDATED", null)  
                      .validateField("remarks");                    
	}
	$(function(){			
		$("#dataForm").bootstrapValidator({
			message : "This value is not valid",
			feedbackIcons : {/*input状态样式图片*/
				invalid : "glyphicon glyphicon-remove",
				validating : "glyphicon glyphicon-refresh"
			},
			fields: {  
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
		if("${data.from}" == "1"){
			$("#itemPreAppMemo").removeAttr("hidden");
		}else{
			$("#itemPreMeasureMemo").removeAttr("hidden");
		}
	});
	function save(){
		$("#dataForm").bootstrapValidator("validate");
		if(!$("#dataForm").data("bootstrapValidator").isValid()){
			return;
		}
		var datas = {
			remarks:$("#remarks").val(),
			from:$("#from").val()
		};
		if(datas.from == "1"){
			$.extend(datas, {
				no:$("#no").val()
			});
		}else{
			$.extend(datas, {
				pk:$("#pk").val(),
				custCode: $("#custCode").val(),
				informManager: $("#informManager").val(),
				informManagerCode: $("#informManagerCode").val(),
				address: $("#address").val(),
			});
		}
		
	 	$.ajax({
			url:"${ctx}/admin/itemPreApp/doEditRemark",
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
	
	function changeInformStatus(obj){
		if ($(obj).is(':checked')){
			$('#informManager').val('1');
		}else{
			$('#informManager').val('0');
		}
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
						<input type="hidden" id="from" name="from" value="${data.from }"/>
						<input type="hidden" id="informManagerCode" name="informManagerCode" value="${data.mainManagerCode }"/>
						<input type="hidden" id="custCode" name="custCode" value="${customer.code}"/>
						<input type="hidden" id="informManager" name="informManager" value="0"/>
						<ul class="ul-form">
							<div class="validate-group row" >
								<div id="itemPreAppMemo" hidden> 
									<li>
										<label>领料预申请单号</label>
										<input type="text" id="no" name="no" value="${data.no}" readonly/>
									</li>
								</div>
								<div hidden="true">
									<li>
										<label>pk</label>
										<input type="text" id="pk" name="pk" value="${data.pk}" readonly/>
									</li>
								</div>
							<c:if test="${data.from  == '2'}">
								<li>
									<label>楼盘</label>
									<input type="text" id="address" name="address" value="${customer.address}" readonly/>
								</li>
								<li>
									<label>主材管家</label>
									<input type="text" id="mainManager" name="mainManager" value="${data.mainManager}" readonly/>
								</li>
							</c:if>
								<li class="form-validate">
									<label class="control-textarea">备注</label>
									<textarea id="remarks" name="remarks" rows="4">${data.remarks }</textarea>
								</li>
	 						</div>
							<c:if test="${data.from  == '2'}">
								<li style="">	
									<label>通知管家变更</label>
									<input type="checkbox" id="informStatus" name="informStatus" value="" onclick="changeInformStatus(this)" />
	 							</li>	
	 						</c:if>
						</ul>
					</form>
				</div>
			</div>
		</div>
	</body>
</html>

