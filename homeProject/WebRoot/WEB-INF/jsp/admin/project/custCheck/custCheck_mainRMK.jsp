<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script type="text/javascript">
		$(function(){
			$("input").attr("disabled",true);
			$("#dataForm").bootstrapValidator({
				message : 'This value is not valid',
				feedbackIcons : {/*input状态样式图片*/
					validating : 'glyphicon glyphicon-refresh'
				},
				fields: { 
					mainRemark:{
						validators:{
							stringLength: {
	                            max: 200,
	                            message: '长度不超过200个字符'
	                        } 
						}
					}
				},
				submitButtons : 'input[type="submit"]'/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
			});
		});
		function save(){
			$("#dataForm").bootstrapValidator('validate');
			if(!$("#dataForm").data('bootstrapValidator').isValid()) return;
			
			var status = $("#mainStatus").val();
			var remark = $("#mainRemark").val();
			var custCode = $("#custCode").val();
			var type = $("#type").val();
			
			var datas = {custCode:custCode,type:type,mainRemark:remark};
			$.ajax({
				url:'${ctx}/admin/CustCheck/doUpdate',
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
  		function validateRefresh(){
			 $('#dataForm').data('bootstrapValidator')
		                   .updateStatus('mainRemark', 'NOT_VALIDATED',null)  
		                   .validateField('mainRemark');                    
		}
	</script>
</head>
<body>
	<div class="body-box-form">
		<div class="panel panel-system" >
		    <div class="panel-body" >
		    	<div class="btn-group-xs" >
    				<button type="button" class="btn btn-system " id="saveBut" onclick="save()">保存</button>
    				<button type="button" class="btn btn-system " id="closeBut" onclick="closeWin(false)">关闭</button>
    			</div>
    		</div>
    	</div>	
		<div class="panel panel-info" >  
			<div class="panel-body">
				<form action="" method="post" id="dataForm" enctype="multipart/form-data" class="form-search">
					<house:token></house:token>
					<input type="hidden" id="custCode" name="custCode" value="${custCheck.custCode }"/>
					<input type="hidden" id="type" name="type" value="${custCheck.type }"/>
					<input type="hidden" id="mainStatus" name="mainStatus" value="${custCheck.mainStatus }"/>
					<ul class="ul-form">
						<li>
							<label>楼盘</label>
							<input type="text" id="address" name="address" value="${custCheck.address}" />
						</li>
						<li>
							<label>申请日期</label>
							<input type="text" id="appDate" name="appDate" value="<fmt:formatDate value='${custCheck.appDate}' pattern='yyyy-MM-dd HH:mm:ss'/>" />
						</li>

						<li>
							<label>主材结算状态</label>
							<input type="text" id="mainStatusDescr" name="mainStatusDescr" value="${custCheck.mainStatusDescr}" />
						</li>
						<li>
							<label>主材接收日期</label>
							<input type="text" id="mainRcvDate" name="mainRcvDate" value="<fmt:formatDate value='${custCheck.mainRcvDate}' pattern='yyyy-MM-dd HH:mm:ss'/>" />
						</li>

						<li>
							<label>主材接收人</label>
							<input type="text" id="mainCZY" name="mainCZY" value="${custCheck.mainCZY}" />
						</li>
						<li>
							<label>主材完成日期</label>
							<input type="text" id="mainCplDate" name="mainCplDate" value="<fmt:formatDate value='${custCheck.mainCplDate}' pattern='yyyy-MM-dd HH:mm:ss'/>" />
						</li>

						<li class="form-validate">	
							<label class="control-textarea">主材备注</label>
							<textarea id="mainRemark" name="mainRemark" />${custCheck.mainRemark }</textarea>
						</li>
					</ul>
				</form>
			</div>
		</div>
	</div>
</body>
</html>
