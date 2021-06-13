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
					finRemark:{
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
			
			var status = $("#finStatus").val();
			if(status != '2'&&status != '3'){
	    		art.dialog({
					content: "处理中或完成的财务申请才允许备注",
					time: 1000,
					beforeunload: function () {
	    				closeWin();
				    }
				});
	    		return;
			}
			
			var remark = $("#finRemark").val();
			var custCode = $("#custCode").val();
			var type = $("#type").val();
			
			var datas = {custCode:custCode,type:type,finRemark:remark};
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
		                   .updateStatus('finRemark', 'NOT_VALIDATED',null)  
		                   .validateField('finRemark');                    
		}
	</script>
</head>
<body>
	<div class="body-box-form">
		<div class="panel panel-system" >
		    <div class="panel-body" >
		    	<div class="btn-group-xs" >
	    				<button  id="saveBut" type="button" class="btn btn-system "   onclick="save()">保存</button>
	    				<button  id="closeBut" type="button" class="btn btn-system "   onclick="closeWin(false)">关闭</button>
				</div>
			</div>
		</div>
		<div class="panel panel-info" >  
			<div class="panel-body">
				<form action="" method="post" id="dataForm" class="form-search">
					<house:token></house:token>
					<input type="hidden" id="custCode" name="custCode",style="width:160px" value="${custCheck.custCode }"/>
					<input type="hidden" id="type" name="type",style="width:160px" value="${custCheck.type }"/>
					<input type="hidden" id="finStatus" name="finStatus",style="width:160px" value="${custCheck.finStatus }"/>
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
							<label>财务结算状态</label>
							<input type="text" id="finStatusDescr" name="finStatusDescr" value="${custCheck.finStatusDescr}" />
						</li>
						<li>
							<label>财务接收人</label>
							<input type="text" id="finCZY" name="finCZY" value="${custCheck.finCZY}" />
						</li>

						<li>
							<label>财务接收日期</label>
							<input type="text" id="finRcvDate" name="finRcvDate" value="<fmt:formatDate value='${custCheck.finRcvDate}' pattern='yyyy-MM-dd HH:mm:ss'/>" />
						</li>
						<li>
							<label>财务完成日期</label>
							<input type="text" id="finCplDate" name="finCplDate" value="<fmt:formatDate value='${custCheck.finCplDate}' pattern='yyyy-MM-dd HH:mm:ss'/>" />
						</li>

						<li class="form-validate">
							<label class="control-textarea">财务备注</label>
							<textarea id="finRemark" name="finRemark"/>${custCheck.finRemark }</textarea>
						</li>
					</ul>	
				</form>
			</div>
		</div>
	</div>
</body>
</html>
