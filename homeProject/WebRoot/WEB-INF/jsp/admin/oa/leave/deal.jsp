<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>审批办理</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script type="text/javascript">
	function save(){
		var datas = getJsonData([{
			key: '${passKey}',
			value: true,
			type: 'B'
		}, {
			key: 'dayNum',
			value: '${dayNum}',
			type: 'N'
		}]);
		$.ajax({
			url:"${ctx}/admin/oa/leave/doComplete?taskId=${taskId}",
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
	function back(){
		if ($.trim($("#leaderBackReason").val())==''){
			art.dialog({
				content: "请输入驳回原因",
				width: 200
			});
			return;
		}
		var datas = getJsonData([{
			key: '${passKey}',
			value: false,
			type: 'B'
		}, {
			key: 'dayNum',
			value: '${dayNum}',
			type: 'N'
		},{
			key: 'leaderBackReason',
			value: $("#leaderBackReason").val(),
			type: 'S'
		}]);
		$.ajax({
			url:"${ctx}/admin/oa/leave/doComplete?taskId=${taskId}",
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
	</script>
</head>
<body>
<div class="body-box-form">
	<div class="panel panel-system">
	    <div class="panel-body" >
	      <div class="btn-group-xs" >
	      <button type="button" class="btn btn-system" onclick="save()">同意</button>
	      <button type="button" class="btn btn-system" onclick="back()">驳回</button>
	      <button type="button" class="btn btn-system" onclick="closeWin(false)">取消</button>
	      </div>
	    </div>
	</div>
	<div class="panel panel-info">  
        <div class="panel-body">
        	<form role="form" class="form-search" id="dataForm"  action="" method="post" target="targetFrame">
            	<house:token></house:token>
            	<ul class="ul-form">
            	<div class="validate-group row">
            		<li class="form-validate">
						<label><span class="required">*</span>申请人</label>
						<input type="text" style="width:160px;" id="userName" name="userName" value="${leave.userName }" readonly="readonly"/>
					</li>
            	</div>
            	<c:if test="${leave.type=='3'}">
            	<div class="validate-group row">
            		<li class="form-validate">
            			<label><span class="required">*</span>请假类型</label>
            			<house:xtdm id="leaveType" dictCode="OA_LEAVETYPE" value="${leave.leaveType }" disabled="true"></house:xtdm>
            		</li>
            	</div>
            	<div class="validate-group row">
            		<li class="form-validate">
            			<label><span class="required">*</span>请假开始时间</label>
            			<input type="text" style="width:160px;" id="startTime" name="startTime" class="i-date" disabled="disabled" 
            			onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss'})" 
            			value="<fmt:formatDate value='${leave.startTime }' pattern='yyyy-MM-dd HH:mm:ss'/>"/>
            		</li>
            	</div>
            	<div class="validate-group row">
            		<li class="form-validate">
            			<label><span class="required">*</span>请假结束时间</label>
            			<input type="text" style="width:160px;" id="endTime" name="endTime" class="i-date" disabled="disabled" 
            			onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss'})" 
            			value="<fmt:formatDate value='${leave.endTime }' pattern='yyyy-MM-dd HH:mm:ss'/>"/>
            		</li>
            	</div>
            	<div class="validate-group row">
            		<li class="form-validate">
            			<label>请假天数</label>
            			<input type="text" style="width:160px;" id="dayNum" name="dayNum" value="${leave.dayNum }" readonly="readonly"/>
            		</li>
            	</div>
            	<div class="validate-group row">
            		<li class="form-validate">
            			<label class="control-textarea">请假原因</label>
						<textarea id="reason" name="reason" maxlength="200" readonly="readonly">${leave.reason }</textarea>
            		</li>
            	</div>
            	</c:if>
            	<c:if test="${leave.type=='1'}">
            	<div class="validate-group row">
            		<li class="form-validate">
            			<label><span class="required">*</span>外出时间</label>
            			<input type="text" style="width:160px;" id="startTime" name="startTime" class="i-date" disabled="disabled"
            			onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss'})" 
            			value="<fmt:formatDate value='${leave.startTime }' pattern='yyyy-MM-dd HH:mm:ss'/>"/>
            		</li>
            	</div>
            	<div class="validate-group row">
            		<li class="form-validate">
            			<label><span class="required">*</span>回来时间</label>
            			<input type="text" style="width:160px;" id="endTime" name="endTime" class="i-date" disabled="disabled"
            			onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss'})" 
            			value="<fmt:formatDate value='${leave.endTime }' pattern='yyyy-MM-dd HH:mm:ss'/>"/>
            		</li>
            	</div>
            	<div class="validate-group row">
            		<li class="form-validate">
            			<label class="control-textarea">外出原因</label>
						<textarea id="reason" name="reason" maxlength="200" readonly="readonly">${leave.reason }</textarea>
            		</li>
            	</div>
            	</c:if>
            	<c:if test="${leave.type=='2'}">
            	<div class="validate-group row">
            		<li class="form-validate">
            			<label><span class="required">*</span>加班时间</label>
            			<input type="text" style="width:160px;" id="realityStartTime" name="realityStartTime" class="i-date" disabled="disabled"
            			onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss'})" 
            			value="<fmt:formatDate value='${leave.realityStartTime }' pattern='yyyy-MM-dd HH:mm:ss'/>"/>
            		</li>
            	</div>
            	<div class="validate-group row">
            		<li class="form-validate">
            			<label><span class="required">*</span>加班结束</label>
            			<input type="text" style="width:160px;" id="realityEndTime" name="realityEndTime" class="i-date" disabled="disabled"
            			onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss'})" 
            			value="<fmt:formatDate value='${leave.realityEndTime }' pattern='yyyy-MM-dd HH:mm:ss'/>"/>
            		</li>
            	</div>
            	<div class="validate-group row">
            		<li class="form-validate">
            			<label><span class="required">*</span>调休开始</label>
            			<input type="text" style="width:160px;" id="startTime" name="startTime" class="i-date" disabled="disabled"
            			onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss'})" 
            			value="<fmt:formatDate value='${leave.startTime }' pattern='yyyy-MM-dd HH:mm:ss'/>"/>
            		</li>
            	</div>
            	<div class="validate-group row">
            		<li class="form-validate">
            			<label><span class="required">*</span>调休结束</label>
            			<input type="text" style="width:160px;" id="endTime" name="endTime" class="i-date" disabled="disabled"
            			onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss'})" 
            			value="<fmt:formatDate value='${leave.endTime }' pattern='yyyy-MM-dd HH:mm:ss'/>"/>
            		</li>
            	</div>
            	<div class="validate-group row">
            		<li class="form-validate">
            			<label>调休天数</label>
            			<input type="text" style="width:160px;" id="dayNum" name="dayNum" value="${leave.dayNum }" readonly="readonly"/>
            		</li>
            	</div>
            	<div class="validate-group row">
            		<li class="form-validate">
            			<label class="control-textarea">调休原因</label>
						<textarea id="reason" name="reason" maxlength="200" readonly="readonly">${leave.reason }</textarea>
            		</li>
            	</div>
            	</c:if>
            	<div class="validate-group row">
            		<li class="form-validate">
            			<label><span class="required">*</span>申请时间</label>
            			<input type="text" style="width:160px;" id="applyTime" name="applyTime" class="i-date" disabled="disabled" 
            			onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss'})" 
            			value="<fmt:formatDate value='${leave.applyTime }' pattern='yyyy-MM-dd HH:mm:ss'/>"/>
            		</li>
            	</div>
            	<div class="validate-group row">
            		<li class="form-validate">
            			<label class="control-textarea">驳回原因</label>
						<textarea id="leaderBackReason" name="leaderBackReason" maxlength="200"></textarea>
            		</li>
            	</div>
            	</ul>
            </form>
         </div>
     </div>
</div>
</body>
</html>

