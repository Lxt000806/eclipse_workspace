<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<!DOCTYPE html>
<html lang="zh">
<html>
<head>
	<title>工人推荐客户确认信息</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
	<script type="text/javascript"> 
		$(function() {
			$("#custCode").openComponent_customer({showValue:"${custRecommend.custCode}",callBack:fillData,condition:{status:"2,3,4",disabledEle:"status_NAME"}});
			
			$("#dataForm").bootstrapValidator({
			message : "This value is not valid",
			feedbackIcons : {/*input状态样式图片*/
				validating : "glyphicon glyphicon-refresh"
			},
			fields: {    
				openComponent_customer_custCode:{  
			        validators: {  
			            notEmpty: {  
			           		 message: "关联楼盘不能为空"  
			            }
			        }  
			    },
			},
			submitButtons : "input[type='submit']"/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
		});
		});
		
		function fillData(ret){
			validateRefresh('openComponent_customer_custCode');
		}
		
		function doArrive(){
			$("#dataForm").bootstrapValidator("validate");
			if(!$("#dataForm").data("bootstrapValidator").isValid()) return;
			$.ajax({
				url:"${ctx}/admin/custRecommend/doArrive",
				type: "post",
				data: {pk:$.trim($("#pk").val()),custCode:$("#custCode").val()},
				dataType: "json",
				cache: false,
				error: function(obj){
					showAjaxHtml({"hidden": false, "msg": "保存数据出错~"});
			    },
			    success: function(obj){
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
			    	<button type="button" class="btn btn-system" id="arrive" onclick="doArrive('1')">
						<span>确认到店</span>
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
							<li>
								<label>楼盘</label>
								<input type="text" id="address" name="address" style="width:160px;" value="${custRecommend.address }" readonly="true"/>
							</li>
							<li>
								<label>客户姓名</label>
								<input type="text" id="custName" name="custName" style="width:160px;" value="${custRecommend.custName}" readonly="true"/>
							</li>
						</div>
						<div class="validate-group row">
							<li>
								<label>电话号码</label>
								<input type="text" id="custPhone" name="custPhone" style="width:160px;" value="${custRecommend.custPhone}" readonly="true"/>
							</li>
							<li>
								<label>推荐人</label>
								<input type="text" id="recommenderDescr" name="recommenderDescr" style="width:160px;" value="${custRecommend.recommenderDescr}" readonly="true"/>
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
								<textarea id="confirmRemarks" name="confirmRemarks" rows="2" readonly="ture">${custRecommend.confirmRemarks}</textarea>
							</li>
						</div>
						<div class="validate-group row">
							<li class="form-validate">
								<label><span class="required">*</span>关联楼盘</label>
								<input readonly="readonly"  type="text" id="custCode" name="custCode"   value="${custRecommend.custCode}" />
							</li>
						</div>
					</ul>
				</form>
			</div>
		</div>
	</div>
</body>	
</html>
