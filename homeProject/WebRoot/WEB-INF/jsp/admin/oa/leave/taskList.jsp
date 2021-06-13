<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<title>待我审批的</title>
	<%@ include file="/commons/jsp/common.jsp" %>
	<link href="${resourceRoot}/plugins/qtip/jquery.qtip.min.css" type="text/css" rel="stylesheet" />
	<script src="${resourceRoot}/plugins/qtip/jquery.qtip.pack.js" type="text/javascript"></script>
	<script src="${resourceRoot}/plugins/html/jquery.outerhtml.js" type="text/javascript"></script>
	<script src="${resourceRoot}/js/workflow.js" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_czybm.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
function dealAll(){
	var ret = selectDataTableRow();
    if (ret) {
    	var id = ret.process_instance_id;
    	if (ret.task_def_key_ == '${modifyApply}'){
    		if (ret.key_=="leave"){
    			Global.Dialog.showDialog("leaveDealApply",{
      			  title:"调整申请-"+id,
      			  url:"${ctx }/admin/oa/leave/goDealApply?id="+id,
      			  height:500,
      			  width:800,
      			  returnFun: goto_query
      			});
    			return;
    		}
			if (ret.key_=="erpApp"){
				Global.Dialog.showDialog("erpDeal",{
		  			title:"调整申请-"+id,
    	  			url:"${ctx }/admin/oa/erpApp/goDealErpModefy?id="+id,
		  			height:500,
		  			width:800,
		  			returnFun: goto_query
		  		});
				return;
    		}
    	}else{
    		if (ret.key_=="leave"){
    			Global.Dialog.showDialog("leaveDeal",{
      			  title:"任务办理-"+id,
      			  url:"${ctx }/admin/oa/leave/goDeal?id="+id,
      			  height:500,
      			  width:800,
      			  returnFun: goto_query
      			});
    			return;
    		}
    		if (ret.key_=="erpApp"){
    			Global.Dialog.showDialog("erpDeal",{
    	  			title:"任务办理-"+id,
		  			url:"${ctx }/admin/oa/erpApp/goDealErp?id="+id,
    	  			height:500,
    	  			width:800,
    	  			returnFun: goto_query
    	  		});
    			return;
    		}
    	}
    } else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}

/* function dealErp(){
	var ret = selectDataTableRow();
	
    if (ret) {
	   	var id = ret.process_instance_id;
  		if (ret.task_def_key_ != '${modifyApply}'){
	  		Global.Dialog.showDialog("erpDeal",{
	  			title:"任务办理-"+id,
	  			url:"${ctx }/admin/oa/erpApp/goDealErp?id="+id,
	  			height:500,
	  			width:800,
	  			returnFun: goto_query
	  		});
	  	}else{
	  		Global.Dialog.showDialog("erpDeal",{
	  			title:"任务办理-"+id,
	  			url:"${ctx }/admin/oa/erpApp/goDealErpModefy?id="+id,
	  			height:500,
	  			width:800,
	  			returnFun: goto_query
	  		});
	  	}	
    } else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
} */


function viewPic(id){
	if (id){
		graphTrace({pid:id});
	}
}
function view(){
	var ret = selectDataTableRow();
    if (ret) {
    	graphTrace({pid:ret.process_instance_id});
    } else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}
/**初始化表格*/
$(function(){
        //初始化表格
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/oa/leave/goJqGrid",
			height:$(document).height()-$("#content-list").offset().top-80,
			colModel : [
			  {name : 'id_',index : 'id_',width : 60,label:'任务编号',sortable : true,align : "left",hidden:true},
			  /*{name : 'oper',index : 'oper',width : 60,label:'操作',sortable : false,align : "left",
					formatter:function(cellvalue, options, rowObject){
	        			if(rowObject==null || rowObject.id_ == null){
	          				return '';
	          			}
	        			if(rowObject.assignee_ == null){
	        				return "<a href=\"javascript:void(0)\" onclick=\"claim('"+rowObject.id_+"')\">签收</a>";
	        			}else{
	        				if (rowObject.task_def_key_ == '${modifyApply}'){
	        					return "<a href=\"javascript:void(0)\" onclick=\"dealApply('"+rowObject.leaveid+"')\">办理</a>";
	        				}else if (rowObject.task_def_key_ == '${reportBack}'){
	        					return "<a href=\"javascript:void(0)\" onclick=\"dealBack('"+rowObject.leaveid+"')\">办理</a>";
	        				}else{
	        					return "<a href=\"javascript:void(0)\" onclick=\"deal('"+rowObject.leaveid+"')\">办理</a>";
	        				}
	        			}
	          			
	    	  		} 
			  },*/
		      {name : 'zwxm',index : 'namechi',width : 70,label:'申请人',sortable : true,align : "left"},
		      {name : 'apply_time',index : 'apply_time',width : 120,label:'申请时间',sortable : true,align : "left",formatter: formatTime},
		      {name : 'procname',index : 'procname',width : 70,label:'流程名称',sortable : true,align : "left"},
		      {name : 'name_',index : 'name_',width : 100,label:'当前节点',sortable : true,align : "left",
		    	  formatter:function(cellvalue, options, rowObject){
	        			if(rowObject==null || !rowObject.name_){
	          				return '';
	          			}
	        			return "<a href=\"javascript:void(0)\" onclick=\"viewPic('"
	        					+rowObject.process_instance_id+"')\">"+rowObject.name_+"</a>";
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
		      {name : 'process_instance_id',index : 'process_instance_id',width : 90,label:'流程实例ID',sortable : true,align : "left"},
		      {name : 'statusdescr',index : 'statusdescr',width : 70,label:'审批状态',sortable : true,align : "left"},
		      {name : 'key_',index : 'key_',width : 60,label:'key_',sortable : true,align : "left",hidden:true}
            ]
		});
		$("#userId").openComponent_czybm();
});
</script>
</head>
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
				<input type="hidden" name="jsonString" value="" />
				<ul class="ul-form">
					<li>
						<label>申请人</label>
						<input type="text" id="userId" name="userId" style="width:160px;"/>
					</li>
					<li>
						<label>流程名称</label>
						<input type="text" id="procName" name="procName" style="width:160px;"/>
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
			 	<house:authorize authCode="task_deal">
			 	<button type="button" class="btn btn-system" onclick="dealAll()">办理</button>
			 	</house:authorize>
			 	<!-- 
			 	<button type="button" class="btn btn-system" onclick="dealErp()">办理erp</button>
			 	 -->
				<button type="button" class="btn btn-system" onclick="view()">查看节点</button>
			</div>
		</div>
		<div id="content-list">
			<table id= "dataTable"></table>
			<div id="dataTablePager"></div>
		</div>
	</div>
</body>
</html>


