<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>调整申请</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script type="text/javascript">
	/**初始化表格*/
	$(function(){
        //初始化表格
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/actTaskinst/goJqGridByProcInstId?procInstId=${leave.processInstanceId}",
			height:$(document).height()-$("#content-list").offset().top-180,
			colModel : [
			  {name : 'id_',index : 'id_',width : 60,label:'任务编号',sortable : true,align : "left"},
			  {name : 'zwxm',index : 'namechi',width : 70,label:'申请人',sortable : true,align : "left"},
		      {name : 'apply_time',index : 'apply_time',width : 120,label:'申请时间',sortable : true,align : "left",formatter: formatTime},
		      {name : 'procname',index : 'procname',width : 70,label:'流程名称',sortable : true,align : "left"},
		      {name : 'name_',index : 'name_',width : 100,label:'历史节点',sortable : true,align : "left"},
	          {name : 'assigneename',index : 'assigneename',width : 90,label:'节点处理人',sortable : true,align : "left"},
		      {name : 'start_time_',index : 'start_time_',width : 120,label:'节点开始时间',sortable : true,align : "left",formatter: formatTime},
		      {name : 'end_time_',index : 'end_time_',width : 120,label:'节点结束时间',sortable : true,align : "left",formatter: formatTime},
		      {name : 'version_',index : 'version_',width : 70,label:'流程状态',sortable : true,align : "left",
		    	  formatter:function(cellvalue, options, rowObject){
	        			if(rowObject==null){
	          				return '';
	          			}
	        			if(rowObject.suspension_state_=='1'){
	        				return "正常V: <b title='流程版本号'>"+rowObject.version_+"</b>";
	        			}else{
	        				return "已挂起V: <b title='流程版本号'>"+rowObject.version_+"</b>";
	        			}
		    	  }
		      },
		      {name : 'task_def_key_',index : 'task_def_key_',width : 70,label:'节点KEY',sortable : true,align : "left",hidden:true},
		      {name : 'proc_inst_id_',index : 'proc_inst_id_',width : 90,label:'流程实例ID',sortable : true,align : "left"}
		    ]
		});
	});
	</script>
</head>
<body>
<div class="body-box-form">
	<div class="panel panel-system">
	    <div class="panel-body" >
	      <div class="btn-group-xs" >
	      <c:if test="${flag!='V' }">
	      <button type="button" class="btn btn-system" onclick="save(true)">调整申请</button>
	      <button type="button" class="btn btn-system" onclick="save(false)">取消申请</button>
	      </c:if>
	      <button type="button" class="btn btn-system" onclick="closeWin(false)">关闭</button>
	      </div>
	    </div>
	</div>
	<div class="container-fluid" id="id_detail">
	    <ul class="nav nav-tabs" >
	        <li class="active"><a href="#tab_leave_jbxx" data-toggle="tab">基本信息</a></li>
	        <c:if test="${flag=='V' }">
	        <li class=""><a href="#tab_leave_lct" data-toggle="tab">流程图</a></li>
	        <li class=""><a href="#tab_leave_history" data-toggle="tab">历史信息</a></li>
	        </c:if>
	    </ul>
	    <div class="tab-content">
    		<div id="tab_leave_jbxx" class="tab-pane fade in active"> 
	         	<div class="panel-body">
	        	<form role="form" class="form-search" id="dataForm"  action="" method="post" target="targetFrame">
	            	<house:token></house:token>
	            	<ul class="ul-form">
	            	<c:if test="${flag!='V' }">
	            	<div class="validate-group row">
	            		<li class="form-validate">
	            			<label class="control-textarea">驳回原因</label>
							<textarea id="leaderBackReason" name="leaderBackReason" maxlength="200" readonly="readonly">${leave.leaderBackReason }</textarea>
	            		</li>
	            	</div>
	            	</c:if>
	            	
	            	<c:if test="${leave.type=='3'}">
	            	<script>
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
	            	      	}
	            	      	},
	            	        submitButtons : 'input[type="submit"]'
	            	    });
	            	});
	            	function save(flag){
	            		$("#dataForm").bootstrapValidator('validate');
	            		if(!$("#dataForm").data('bootstrapValidator').isValid()) return;
	            		var datas = getJsonData([{
	            			key: 'reApply',
	            			value: flag,
	            			type: 'B'
	            		}, {
	            			key: 'leaveType',
	            			value: $('#leaveType').val(),
	            			type: 'S'
	            		}, {
	            			key: 'startTime',
	            			value: $('#startTime').val(),
	            			type: 'D'
	            		}, {
	            			key: 'endTime',
	            			value: $('#endTime').val(),
	            			type: 'D'
	            		}, {
	            			key: 'reason',
	            			value: $('#reason').val(),
	            			type: 'S'
	            		},{
	            			key: 'dayNum',
	            			value: $('#dayNum').val(),
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
	                				$("#_form_token_uniq_id").val(obj.datas.token);
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
	            	<div class="validate-group row">
	            		<li class="form-validate">
	            			<label><span class="required">*</span>请假类型</label>
	            			<house:xtdm id="leaveType" dictCode="OA_LEAVETYPE" value="${leave.leaveType }"></house:xtdm>
	            		</li>
	            	</div>
	            	<div class="validate-group row">
	            		<li class="form-validate">
	            			<label><span class="required">*</span>开始时间</label>
	            			<input type="text" style="width:160px;" id="startTime" name="startTime" class="i-date" 
	            			onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss'})" 
	            			value="<fmt:formatDate value='${leave.startTime }' pattern='yyyy-MM-dd HH:mm:ss'/>"/>
	            		</li>
	            	</div>
	            	<div class="validate-group row">
	            		<li class="form-validate">
	            			<label><span class="required">*</span>结束时间</label>
	            			<input type="text" style="width:160px;" id="endTime" name="endTime" class="i-date" 
	            			onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss'})" 
	            			value="<fmt:formatDate value='${leave.endTime }' pattern='yyyy-MM-dd HH:mm:ss'/>"/>
	            		</li>
	            	</div>
	            	<div class="validate-group row">
	            		<li class="form-validate">
	            			<label><span class="required">*</span>请假天数</label>
	            			<input type="text" style="width:160px;" id="dayNum" name="dayNum" value="${leave.dayNum }" maxlength="10"/>
	            		</li>
	            	</div>
	            	<div class="validate-group row">
	            		<li class="form-validate">
	            			<label class="control-textarea">请假原因</label>
							<textarea id="reason" name="reason" maxlength="200">${leave.reason }</textarea>
	            		</li>
	            	</div>
	            	</c:if>
	            	
	            	<c:if test="${leave.type=='1'}">
	            	<script>
	            	//校验函数
	            	$(function() {
	            		$("#dataForm").bootstrapValidator({
	            	        message : 'This value is not valid',
	            	        feedbackIcons : {/*input状态样式图片*/
	            	            validating : 'glyphicon glyphicon-refresh'
	            	        },
	            	        fields: {
	            	      	startTime: {
	            	      		validators: { 
	            		            notEmpty: { 
	            		            	message: '外出时间不能为空'  
	            		            }
	            		        }
	            	      	},
	            	      	endTime: {
	            	      		validators: { 
	            		            notEmpty: { 
	            		            	message: '回来时间不能为空'  
	            		            }
	            		        }
	            	      	},
	            	      	reason: {
	            	      		validators: { 
	            		            notEmpty: { 
	            		            	message: '外出原因不能为空'  
	            		            }
	            		        }
	            	      	}
	            	      	},
	            	        submitButtons : 'input[type="submit"]'
	            	    });
	            	});
	            	function save(flag){
	            		$("#dataForm").bootstrapValidator('validate');
	            		if(!$("#dataForm").data('bootstrapValidator').isValid()) return;
	            		var datas = getJsonData([{
	            			key: 'reApply',
	            			value: flag,
	            			type: 'B'
	            		}, {
	            			key: 'startTime',
	            			value: $('#startTime').val(),
	            			type: 'D'
	            		}, {
	            			key: 'endTime',
	            			value: $('#endTime').val(),
	            			type: 'D'
	            		}, {
	            			key: 'reason',
	            			value: $('#reason').val(),
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
	                				$("#_form_token_uniq_id").val(obj.datas.token);
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
	            	<div class="validate-group row">
	            		<li class="form-validate">
	            			<label><span class="required">*</span>外出时间</label>
	            			<input type="text" style="width:160px;" id="startTime" name="startTime" class="i-date" 
	            			onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss'})" 
	            			value="<fmt:formatDate value='${leave.startTime }' pattern='yyyy-MM-dd HH:mm:ss'/>"/>
	            		</li>
	            	</div>
	            	<div class="validate-group row">
	            		<li class="form-validate">
	            			<label><span class="required">*</span>回来时间</label>
	            			<input type="text" style="width:160px;" id="endTime" name="endTime" class="i-date" 
	            			onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss'})" 
	            			value="<fmt:formatDate value='${leave.endTime }' pattern='yyyy-MM-dd HH:mm:ss'/>"/>
	            		</li>
	            	</div>
	            	<div class="validate-group row">
	            		<li class="form-validate">
	            			<label class="control-textarea"><span class="required">*</span>外出原因</label>
							<textarea id="reason" name="reason" maxlength="200">${leave.reason }</textarea>
	            		</li>
	            	</div>
	            	</c:if>
	            	
	            	<c:if test="${leave.type=='2'}">
	            	<script>
	            	//校验函数
	            	$(function() {
	            		$("#dataForm").bootstrapValidator({
	            	        message : 'This value is not valid',
	            	        feedbackIcons : {/*input状态样式图片*/
	            	            validating : 'glyphicon glyphicon-refresh'
	            	        },
	            	        fields: {
	                    	dayNum: {
	            	      		validators: { 
	            	      			numeric: { 
	            		            	message: '只能输入数字'  
	            		            }
	            		        }
	            	      	},
	            	        realityStartTime: {
	            	      		validators: { 
	            		            notEmpty: { 
	            		            	message: '加班时间不能为空'  
	            		            }
	            		        }
	            	      	},
	            	      	realityEndTime: {
	            	      		validators: { 
	            		            notEmpty: { 
	            		            	message: '加班结束不能为空'  
	            		            }
	            		        }
	            	      	},
	            	      	startTime: {
	            	      		validators: { 
	            		            notEmpty: { 
	            		            	message: '调休开始不能为空'  
	            		            }
	            		        }
	            	      	},
	            	      	endTime: {
	            	      		validators: { 
	            		            notEmpty: { 
	            		            	message: '调休结束不能为空'  
	            		            }
	            		        }
	            	      	}
	            	      	},
	            	        submitButtons : 'input[type="submit"]'
	            	    });
	            	});
	            	function save(flag){
	            		$("#dataForm").bootstrapValidator('validate');
	            		if(!$("#dataForm").data('bootstrapValidator').isValid()) return;
	            		var datas = getJsonData([{
	            			key: 'reApply',
	            			value: flag,
	            			type: 'B'
	            		}, {
	            			key: 'startTime',
	            			value: $('#startTime').val(),
	            			type: 'D'
	            		}, {
	            			key: 'endTime',
	            			value: $('#endTime').val(),
	            			type: 'D'
	            		}, {
	            			key: 'realityStartTime',
	            			value: $('#realityStartTime').val(),
	            			type: 'D'
	            		}, {
	            			key: 'realityEndTime',
	            			value: $('#realityEndTime').val(),
	            			type: 'D'
	            		}, {
	            			key: 'reason',
	            			value: $('#reason').val(),
	            			type: 'S'
	            		},{
	            			key: 'dayNum',
	            			value: $('#dayNum').val(),
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
	                				$("#_form_token_uniq_id").val(obj.datas.token);
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
	            	<div class="validate-group row">
	            		<li class="form-validate">
	            			<label><span class="required">*</span>加班时间</label>
	            			<input type="text" style="width:160px;" id="realityStartTime" name="realityStartTime" class="i-date" 
	            			onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss'})" 
	            			value="<fmt:formatDate value='${leave.realityStartTime }' pattern='yyyy-MM-dd HH:mm:ss'/>"/>
	            		</li>
	            	</div>
	            	<div class="validate-group row">
	            		<li class="form-validate">
	            			<label><span class="required">*</span>加班结束</label>
	            			<input type="text" style="width:160px;" id="realityEndTime" name="realityEndTime" class="i-date" 
	            			onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss'})" 
	            			value="<fmt:formatDate value='${leave.realityEndTime }' pattern='yyyy-MM-dd HH:mm:ss'/>"/>
	            		</li>
	            	</div>
	            	<div class="validate-group row">
	            		<li class="form-validate">
	            			<label><span class="required">*</span>调休开始</label>
	            			<input type="text" style="width:160px;" id="startTime" name="startTime" class="i-date" 
	            			onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss'})" 
	            			value="<fmt:formatDate value='${leave.startTime }' pattern='yyyy-MM-dd HH:mm:ss'/>"/>
	            		</li>
	            	</div>
	            	<div class="validate-group row">
	            		<li class="form-validate">
	            			<label><span class="required">*</span>调休结束</label>
	            			<input type="text" style="width:160px;" id="endTime" name="endTime" class="i-date" 
	            			onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss'})" 
	            			value="<fmt:formatDate value='${leave.endTime }' pattern='yyyy-MM-dd HH:mm:ss'/>"/>
	            		</li>
	            	</div>
	            	<div class="validate-group row">
	            		<li class="form-validate">
	            			<label>调休天数</label>
	            			<input type="text" style="width:160px;" id="dayNum" name="dayNum" maxlength="10" value="${leave.dayNum }"/>
	            		</li>
	            	</div>
	            	<div class="validate-group row">
	            		<li class="form-validate">
	            			<label class="control-textarea">调休原因</label>
							<textarea id="reason" name="reason" maxlength="200">${leave.reason }</textarea>
	            		</li>
	            	</div>
	            	</c:if>
	            	
	            	</ul>
	            </form>
	         </div>
	        </div>  
	        <div id="tab_leave_lct" class="tab-pane fade"> 
	         	<img src="${ctx}/admin/workflow/process/trace/auto/${leave.processInstanceId}"/>
	        </div>
	        <div id="tab_leave_history" class="tab-pane fade"> 
	         	<div id="content-list">
					<table id= "dataTable"></table>
					<div id="dataTablePager"></div>
				 </div>
	        </div>
    	</div>
    </div>
</div>
</body>
</html>

