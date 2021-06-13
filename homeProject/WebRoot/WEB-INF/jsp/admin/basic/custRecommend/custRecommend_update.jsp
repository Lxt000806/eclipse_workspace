<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<!DOCTYPE html>
<html lang="zh">
<html>
<head>
	<title>工人推荐客户编辑</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	
	<script type="text/javascript"> 
	$(function(){
		
		$("#dataForm").bootstrapValidator({
			message : "This value is not valid",
			feedbackIcons : {/*input状态样式图片*/
				validating : "glyphicon glyphicon-refresh"
			},
			fields: {    
				address:{  
					validators: {  
						notEmpty: {  
							message: "楼盘不能为空"  
						}
					}  
				},
				custName:{  
					validators: {  
						notEmpty: {  
							message: "客户姓名不能为空"  
						}
					}  
				},
				status:{  
					validators: {  
						notEmpty: {  
							message: "推荐状态不能为空"  
						}
					}  
				}
			},
			submitButtons : "input[type='submit']"/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
		});
	});
	function doUpdate(){
		$("#dataForm").bootstrapValidator("validate");
		if(!$("#dataForm").data("bootstrapValidator").isValid()) return;
		$.ajax({
			url:"${ctx}/admin/custRecommend/doUpdate",
			type: "post",
			data: {pk:$.trim($("#pk").val()),status:$.trim($("#status").val()),confirmRemarks:$.trim($("#confirmRemarks").val()),address:$("#address").val(),custName:$("#custName").val()},
			dataType: "json",
			cache: false,
			error: function(obj){
				showAjaxHtml({"hidden": false, "msg": "保存数据出错~"});
		    },
		    success: function(obj){
		    console.log(obj.rs);
		    	if(obj.rs){
		    		art.dialog({
						content : obj.msg,
						time : 1000,
						beforeunload : function() {
							closeWin();
						}
					});
		    	}else{
			    	var options = {
						content: obj.msg,
						width: 200
		    		};
		    		art.dialog(options);
		    	}
		   	} 
		});
	}
	</script>
</head>
<body>
	<div class="body-box-form">
		<div class="panel panel-system">
		    <div class="panel-body">
		    	<div class="btn-group-xs">
			    	<button type="button" class="btn btn-system" id="valid" onclick="doUpdate()">
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
					<input type="hidden" id="pk" name="pk" value="${custRecommend.pk }"/>
					<ul class="ul-form">
						<div class="validate-group row">
							<li class="form-validate">
								<label><span class="required">*</span>楼盘</label>
								<input type="text" id="address" name="address" style="width:160px;" value="${custRecommend.address }" />
							</li>
							<li class="form-validate">
								<label><span class="required">*</span>客户姓名</label>
								<input type="text" id="custName" name="custName" style="width:160px;" value="${custRecommend.custName}" />
							</li>
						</div>
						<div class="validate-group row">
							<li>
								<label>电话号码</label>
								<input type="text" id="custPhone" name="custPhone" style="width:160px;" value="${custRecommend.custPhone}" readonly="true"/>
							</li>
							<li>
								<label>推荐人</label>
								<input type="text" id="workerName" name="workerName" style="width:160px;" value="${custRecommend.recommenderDescr}" readonly="true"/>
							</li>
							<li class="form-validate">
								<label><span class="required">*</span>推荐状态</label>
								<house:xtdm id="status" dictCode="CUSTRESSTAT" unShowValue="3,4,5,6,7,8" value="${custRecommend.status }"></house:xtdm>
							</li>
						</div>
						<div class="validate-group row">
							<li>
								<label class="control-textarea">推荐备注</label>
								<textarea id="remarks" name="remarks" rows="2" readonly="ture">${custRecommend.remarks}</textarea>
							</li>
						</div>
						<div class="validate-group row">
							<li>
								<label class="control-textarea">确认备注</label>
								<textarea id="confirmRemarks" name="confirmRemarks" rows="2">${custRecommend.confirmRemarks}</textarea>
							</li>
						</div>
					</ul>
				</form>
			</div>
		</div>
	</div>
</body>	
</html>
