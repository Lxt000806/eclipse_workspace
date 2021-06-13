<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<title>流程定义列表</title>
	<%@ include file="/commons/jsp/common.jsp" %>
		<script src="${resourceRoot}/js/workflow.js" type="text/javascript"></script>
	<link href="${resourceRoot}/plugins/qtip/jquery.qtip.min.css" type="text/css" rel="stylesheet" />
	<script src="${resourceRoot}/plugins/qtip/jquery.qtip.pack.js" type="text/javascript"></script>
	<script src="${resourceRoot}/plugins/html/jquery.outerhtml.js" type="text/javascript"></script>
<script type="text/javascript">
function goImport(){
      Global.Dialog.showDialog("modelImport",{		  
		  title:"导入流程",
          url:"${ctx}/admin/workflow/process/goImport",
		  height:300,
		  width:500,
		  returnFun: goto_query
		});
}
function viewPic(){
	var ret = selectDataTableRow();
	var resourceType='image';
    if (ret) {
    	Global.Dialog.showDialog("leaveAll",{
			  title:"查看流程图片--"+ret.name_,
			  url:'${ctx }/admin/workflow/process/resource/read?processDefinitionId='+ret.id_+'&resourceType='+resourceType ,
			  height:500,
			  width:1000
			});
    } else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}
function convertToModel(){
	var ret = selectDataTableRow();
	var processDefinitionId=ret.id_ ;
	if(ret.length <1){
		 art.dialog({
			content: '请选择需要转换为模型的记录',
			lock: true
		 });
		return ;
	}
	art.dialog({
		 content:'您确定要转换为模型吗？',
		 lock: true,
		 width: 260,
		 height: 100,
		 ok: function () {
	        $.ajax({
				url:"${ctx }/admin/workflow/process/doConvertToModel/"+processDefinitionId,
				contentType: "application/x-www-form-urlencoded; charset=UTF-8",
				dataType: 'json',
				type: 'post',
				cache: false,
				error: function(){
			        art.dialog({
						content: '转化模型出错'
					});
			    },
			    success: function(obj){
			    	if(obj.rs){
			    		art.dialog({
							content: obj.msg,
							time: 1000,
							beforeunload: function () {
								goto_query();
						    }
						});
			    	}else{
			    		art.dialog({
							content: obj.msg
						});
			    	}
				}
			});
	    	return true;
		},
		cancel: function () {
			return true;
		}
	});
}

function del(){
	var ret = selectDataTableRow();
	var deploymentId=ret.deployment_id_ ;
	var url = "${ctx }/admin/workflow/process/doDelete/"+deploymentId;
	beforeDel(url);
}
function upActive(){
	var ret = selectDataTableRow();
	if(ret.length <1){
		 art.dialog({
			content: '请选择一条记录',
			lock: true
		 });
		return ;
	}
	var sMessage='';
	if (ret.suspension_state_=='已激活'){
		sMessage='您确定要挂起该流程吗？'
	}else{
		sMessage='您确定要激活该流程吗？'
	}
	art.dialog({
		 content:sMessage,
		 lock: true,
		 width: 260,
		 height: 100,
		 ok: function () {
	        $.ajax({
				url:'${ctx }/admin/workflow/process/processdefinition/update',
				contentType: "application/x-www-form-urlencoded; charset=UTF-8",
				data:{state:ret.suspension_state_,processDefinitionId:ret.id_},
				dataType: 'json',
				type: 'post',
				cache: false,
				error: function(){
			        art.dialog({
						content: '修改状态出错'
					});
			    },
			    success: function(obj){
			    	if(obj.rs){
			    		art.dialog({
							content: obj.msg,
							time: 1000,
							beforeunload: function () {
								goto_query();
						    }
						});
			    	}else{
			    		art.dialog({
							content: obj.msg
						});
			    	}
				}
			});
	    	return true;
		},
		cancel: function () {
			return true;
		}
	});
}

/**初始化表格*/
$(function(){
        //初始化表格
        var postData = $("#page_form").jsonForm();
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/actProcdef/goJqGrid",
			postData: postData,
			height:$(document).height()-$("#content-list").offset().top-80,
			colModel : [  
			  {name : 'category_',index : 'category_',width : 100,label:'流程分类',sortable : true,align : "left"},
			  {name : 'id_',index : 'id_',width : 90,label:'流程ID',sortable : true,align : "left"},
			  {name : 'deployment_id_',index : 'deployment_id_',width : 100,label:'流程部署ID',sortable : true,align : "left"},
			  {name : 'key_',index : 'key_',width :100,label:'流程标识',sortable : true,align : "left"},
		      {name : 'name_',index :'name_', width : 100,label:'流程名称',sortable : true,align : "left"},
		      {name : 'version_',index : 'version_',width : 100,label:'流程版本',sortable : true,align : "left"},
		      {name : 'deploy_time_',index : 'deploy_time_',width : 110,label:'部署时间',sortable : true,align : "left",formatter: formatTime},
		      {name : 'suspension_state_',index : 'suspension_state_',width : 70,label:'流程状态',sortable : true,align : "left",
		    	  formatter:function(cellvalue, options, rowObject){
	        			if(rowObject==null){
	          				return '';
	          			}
	        			if(rowObject.suspension_state_=='1'){
	        				return "已激活";
	        			}else{
	        				return "已挂起";
	        			}
		    	  }
		      },
		      {name : 'resource_name_',index : 'resource_name_',width : 200,label:'流程xml',sortable : true,align : "left"}, 
		      {name : 'dgrm_resource_name_',index : 'dgrm_resource_name_',width : 200,label:'流程图片',sortable : true,align : "left"},
            ]
		});
});  
		
</script>
</head>
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form role="form" class="form-search" id="page_form"   >
				<input type="hidden" name="jsonString" value="" />
				<input type="hidden" type="text" id="key" name="key" value="${actProcdef.key}" />
			</form>
		</div>
		<!--query-form-->
		<div class="btn-panel">
			 <div class="btn-group-sm">
			 	<house:authorize authCode="process_import">
					<button type="button" class="btn btn-system" onclick="goImport()">导入流程</button>
				</house:authorize>
				<house:authorize authCode="process_convertToModel">
					<button type="button" class="btn btn-system" onclick="convertToModel()">转化为模型</button>
			    </house:authorize>
			    <house:authorize authCode="process_update">
			 		<button type="button" class="btn btn-system" onclick="upActive()">激活/挂起</button>
				</house:authorize>
			 	<house:authorize authCode="process_del">
			    	<button type="button" class="btn btn-system" onclick="del()">删除</button>
			 	</house:authorize>
			 	<house:authorize authCode="process_del">
			    	<button type="button" class="btn btn-system" onclick="viewPic()">查看图片</button>
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


