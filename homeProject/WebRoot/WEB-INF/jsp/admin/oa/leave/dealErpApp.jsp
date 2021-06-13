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
	$(function(){
        //初始化表格
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/actTaskinst/goJqGridByProcInstId?procInstId=${appErp.processInstanceId}",
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
	function save(){
		var datas = getJsonData([{
			key: '${passKey}',
			value: true,
			type: 'B'
			},{
			key: 'type',
			value:$("#type").val(),
			type: 'S'
			}
			]);
		$.ajax({
			url:"${ctx}/admin/oa/erpApp/doComplete?taskId=${taskId}",
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
		$("#type").removeAttr("disabled","true");
		
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
		},{
			key: 'leaderBackReason',
			value: $("#leaderBackReason").val(),
			type: 'S'
		},{
			key: 'type',
			value:$("#type").val(),
			type: 'S'
		}
		]);
		$.ajax({
			url:"${ctx}/admin/oa/erpApp/doComplete?taskId=${taskId}",
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
	      <c:if test="${flag!='V' }">
	      <button type="button" class="btn btn-system" onclick="save()">同意</button>
	      <button type="button" class="btn btn-system" onclick="back()">驳回</button>
	      <button type="button" class="btn btn-system" onclick="closeWin(false)">取消</button>
	      </c:if>
	      <c:if test="${flag=='V' }">
	      <button type="button" class="btn btn-system" onclick="closeWin(false)">关闭</button>
	      </c:if>	
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
	        	<form role="form" class="form-search" id="dataForm"  action="" method="post" target="targetFrame">
	            	<house:token></house:token>
	            	<ul class="ul-form">
	            	<div class="validate-group row">
	            		<li class="form-validate">
							<label><span class="required">*</span>申请人</label>
							<input type="text" style="width:160px;" id="userName" name="userName" value="${appErp.userName }" readonly="readonly"/>
						</li>
	            	</div>
	            	<div class="validate-group row">
	            		<li class="form-validate">
	            			<label><span class="required">*</span>申请类型</label>
	            			<house:xtdm id="type" dictCode="OA_ERP_TYPE" value="${appErp.type }" disabled="true"></house:xtdm>
	            		</li>
	            	</div>
	            	<div class="validate-group row">
	            		<li class="form-validate">
	            			<label><span class="required">*</span>申请时间</label>
	            			<input type="text" style="width:160px;" id="applyTime" name="applyTime" class="i-date" disabled="disabled" 
	            			onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss'})" 
	            			value="<fmt:formatDate value='${appErp.applyTime }' pattern='yyyy-MM-dd HH:mm:ss'/>"/>
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
         	<div id="tab_leave_lct" class="tab-pane fade"> 
	         	<img src="${ctx}/admin/workflow/process/trace/auto/${appErp.processInstanceId}"/>
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

