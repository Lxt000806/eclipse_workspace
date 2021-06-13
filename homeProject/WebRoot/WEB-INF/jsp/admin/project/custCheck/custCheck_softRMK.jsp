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
					softRemark:{
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
			
			var status = $("#softSatus").val();
			if(status != '2' && status != '3'){
	    		art.dialog({
					content: "处理中或完成的软装申请才允许备注",
					time: 1000,
					beforeunload: function () {
	    				closeWin();
				    }
				});
	    		return;
			}
			
			var remark = $("#softRemark").val();
			var custCode = $("#custCode").val();
			var type = $("#type").val();
			
			var datas = {custCode:custCode,type:type,softRemark:remark};
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
		                   .updateStatus('softRemark', 'NOT_VALIDATED',null)  
		                   .validateField('softRemark');                    
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
					<input type="hidden" id="softSatus" name="softSatus" value="${custCheck.softSatus }"/>
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
							<label>软装结算状态</label>
							<input type="text" id="softStatusDescr" name="softStatusDescr" value="${custCheck.softStatusDescr}" />
						</li>
						<li>
							<label>软装接收日期</label>
							<input type="text" id="softRcvDate" name="softRcvDate" value="<fmt:formatDate value='${custCheck.softRcvDate}' pattern='yyyy-MM-dd HH:mm:ss'/>" />
						</li>

						<li>
							<label>软装接收人</label>
							<input type="text" id="softCZY" name="softCZY" value="${custCheck.softCZY}" />
						</li>
						<li>
							<label>软装完成日期</label>
							<input type="text" id="softCplDate" name="softCplDate" value="<fmt:formatDate value='${custCheck.softCplDate}' pattern='yyyy-MM-dd HH:mm:ss'/>" />
						</li>

						<li class="form-validate">
							<label class="control-textarea">软装备注</label>
							<textarea id="softRemark" name="softRemark" />${custCheck.softRemark }</textarea>
						</li>
					</ul>
				</form>
			</div>
		</div>
	</div>
</body>
</html>
