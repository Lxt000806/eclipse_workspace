<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>请假申请</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script type="text/javascript">
	function save(){
		$("#dataForm").bootstrapValidator('validate');
		if(!$("#dataForm").data('bootstrapValidator').isValid()) return;
		var datas = $("#dataForm").serialize();
		$.ajax({
			url:'${ctx}/admin/oa/leave/doStart',
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
							//closeWin();
							$("#_form_token_uniq_id").val(obj.datas.token);
							clearDataForm();
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
	        leaveType: {
	      		validators: { 
		            notEmpty: { 
		            	message: '请假类型不能为空'  
		            }
		        }
	      	},
	      	dayNum: {
	      		validators: { 
	      			numeric: { 
		            	message: '只能输入数字'  
		            },
		            notEmpty: { 
		            	message: '请假天数不能为空'  
		            }
		        }
	      	},
	      	startTime: {
	      		validators: { 
		            notEmpty: { 
		            	message: '开始时间不能为空'  
		            }
		        }
	      	},
	      	endTime: {
	      		validators: { 
		            notEmpty: { 
		            	message: '结束时间不能为空'  
		            }
		        }
	      	},
	      	leaderId: {
	      		validators: { 
		            notEmpty: { 
		            	message: '直接主管不能为空'  
		            }
		        }
	      	}
	      	},
	        submitButtons : 'input[type="submit"]'
	    });
		$("#dataForm .i-date").blur(function(){validateRefresh(this.id)});
	});
	</script>
</head>
<body>
<div class="body-box-form">
	<div class="panel panel-system">
	    <div class="panel-body" >
	      <div class="btn-group-xs" >
	      <button type="button" id="saveBtn" class="btn btn-system" onclick="save()">保存</button>
	      </div>
	    </div>
	</div>
	<div class="panel panel-info">  
        <div class="panel-body">
        	<form role="form" class="form-search" id="dataForm"  action="" method="post" target="targetFrame">
        		<input type="hidden" id="type" name="type" value="3">
            	<house:token></house:token>
            	<ul class="ul-form">
            	<div class="validate-group row">
            		<li class="form-validate">
            			<label><span class="required">*</span>请假类型</label>
            			<house:xtdm id="leaveType" dictCode="OA_LEAVETYPE"></house:xtdm>
            		</li>
            	</div>
            	<div class="validate-group row">
            		<li class="form-validate">
            			<label><span class="required">*</span>开始时间</label>
            			<input type="text" style="width:160px;" id="startTime" name="startTime" class="i-date" 
            			onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
            		</li>
            	</div>
            	<div class="validate-group row">
            		<li class="form-validate">
            			<label><span class="required">*</span>结束时间</label>
            			<input type="text" style="width:160px;" id="endTime" name="endTime" class="i-date" 
            			onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
            		</li>
            	</div>
            	<div class="validate-group row">
            		<li class="form-validate">
            			<label><span class="required">*</span>请假天数</label>
            			<input type="text" style="width:160px;" id="dayNum" name="dayNum" maxlength="10"/>
            		</li>
            	</div>
            	<div class="validate-group row">
            		<li class="form-validate">
            			<label class="control-textarea">请假原因</label>
						<textarea id="reason" name="reason" maxlength="200"></textarea>
            		</li>
            	</div>
            	<div class="validate-group row">
            		<li class="form-validate">
            			<label><span class="required">*</span>直接主管</label>
            			<select id="leaderId" name="leaderId" style="width: 160px;">
            			<c:forEach items="${leaderList }" var="item" varStatus="st">
            			<c:if test="${st.index==0 }">
            			<option value="${item.leaderid }" selected="selected">${item.leaderid }-${item.leadername }</option>
            			</c:if>
            			<c:if test="${st.index!=0 }">
            			<option value="${item.leaderid }">${item.leaderid }-${item.leadername }</option>
            			</c:if>
            			</c:forEach>
            			</select>
            		</li>
            	</div>
            	</ul>
            </form>
         </div>
     </div>
</div>
</body>
</html>

