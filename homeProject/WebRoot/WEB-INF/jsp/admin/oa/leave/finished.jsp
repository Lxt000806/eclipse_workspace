<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<title>我已审批的</title>
	<%@ include file="/commons/jsp/common.jsp" %>
	<link href="${resourceRoot}/plugins/qtip/jquery.qtip.min.css" type="text/css" rel="stylesheet" />
	<script src="${resourceRoot}/plugins/qtip/jquery.qtip.pack.js" type="text/javascript"></script>
	<script src="${resourceRoot}/plugins/html/jquery.outerhtml.js" type="text/javascript"></script>
	<script src="${resourceRoot}/js/workflow.js" type="text/javascript"></script>
<script type="text/javascript">
/**初始化表格*/
$(function(){
        //初始化表格
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/oa/leave/goJqGridFinished",
			height:$(document).height()-$("#content-list").offset().top-80,
			colModel : [
		      {name : 'id_',index : 'id_',width : 60,label:'任务编号',sortable : true,align : "left",hidden:true},
			  {name : 'zwxm',index : 'namechi',width : 70,label:'申请人',sortable : true,align : "left"},
		      {name : 'apply_time',index : 'apply_time',width : 120,label:'申请时间',sortable : true,align : "left",formatter: formatTime},
		      {name : 'procname',index : 'procname',width : 70,label:'流程名称',sortable : true,align : "left"},
		      {name : 'name_',index : 'name_',width : 100,label:'当前节点',sortable : true,align : "left",
		    	  formatter:function(cellvalue, options, rowObject){
	        			if(rowObject==null || !rowObject.name_){
	          				return '';
	          			}
	        			return "<a href=\"javascript:void(0)\" onclick=\"viewPic('"
	        					+rowObject.proc_inst_id_+"')\">"+rowObject.name_+"</a>";
		    	  }
	          },
	          {name : 'assigneename',index : 'assigneename',width : 90,label:'节点处理人',sortable : true,align : "left"},
		      {name : 'create_time_',index : 'create_time_',width : 120,label:'节点开始时间',sortable : true,align : "left",formatter: formatTime},
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
		      {name : 'proc_inst_id_',index : 'proc_inst_id_',width : 90,label:'流程实例ID',sortable : true,align : "left"},
		      {name : 'statusdescr',index : 'statusdescr',width : 70,label:'审批状态',sortable : true,align : "left"},
		      {name : 'key_',index : 'key_',width : 60,label:'key_',sortable : true,align : "left",hidden:true}
		      
		      
		    ]
		});
});
function view(){
	var ret = selectDataTableRow();
    if (ret) {
    	if(ret.key_=='leave'){
    		var id = ret.proc_inst_id_;
	    	Global.Dialog.showDialog("leaveFinished",{
				  title:"查看申请-"+id,
				  url:"${ctx }/admin/oa/leave/goDealApply?flag=V&id="+id,
				  height:600,
				  width:1200
			});
    	}else if(ret.key_=='erpApp'){
    		var id = ret.proc_inst_id_;
	    	Global.Dialog.showDialog("erpAppFinished",{
				  title:"查看申请-"+id,
				  url:"${ctx }/admin/oa/erpApp/goDealErp?flag=V&id="+id,
				  height:600,
				  width:1200
			});
    	}
    } else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}
function viewPic(id){
	if (id){
		graphTrace({pid:id});
	}
}
</script>
</head>
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
				<input type="hidden" name="jsonString" value="" />
				<ul class="ul-form">
					<li>
						<label>流程名称</label>
						<input type="text" id="procName" name="procName" style="width:160px;"/>
					</li>
            		<li>
            			<label>申请时间从</label>
            			<input type="text" style="width:160px;" id="dateFrom" name="dateFrom" class="i-date" 
            			onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
            			value="<fmt:formatDate value='${actTask.dateFrom }' pattern='yyyy-MM-dd'/>"/>
            		</li>
	           		<li>
	           			<label>申请时间至</label>
	           			<input type="text" style="width:160px;" id="dateTo" name="dateTo" class="i-date" 
	           			onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
	           			value="<fmt:formatDate value='${actTask.dateTo }' pattern='yyyy-MM-dd'/>"/>
	           		</li>
					<li>
						<button type="button" class="btn btn-sm btn-system" onclick="goto_query();">查询</button>
						<button type="button" class="btn btn-sm btn-system" onclick="clearForm();">清空</button>
					</li>
				</ul>
			</form>
		</div>
		<!--query-form-->
		<div class="btn-panel">
			 <div class="btn-group-sm">
			 	<house:authorize authCode="finished_view">
				<button type="button" class="btn btn-system" onclick="view()">查看</button>
				</house:authorize>
			</div>
		</div>
		<div id="content-list">
			<table id= "dataTable"></table>
			<div id="dataTablePager"></div>
		</div>
	</div>
</body>
</html>
