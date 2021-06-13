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
	
<script type="text/javascript">
// 跳转到导入流程界面
function goImport(){
	Global.Dialog.showDialog("importProcdef",{
		title:"导入流程",
		url:"${ctx}/admin/actProcdef/goImport",
		height:320,
		width:800,
		returnFun: goto_query
	});
}

// 查看流程图
function viewImage(){
	var ret = selectDataTableRow();
	if (!ret) {
	   art.dialog({content: "请选择一条记录"});
	}

   	Global.Dialog.showDialog("查看流程图", {
		title: "查看流程图 -- "+ret.name,
		url: "${ctx }/admin/actProcdef/getResource?resourceType=image&processDefinitionId="+ret.id,
		height: 600,
		width: 1000
	});
}

// 激活/挂起流程
/* 激活挂起的功能先不要
function activateOrSuspendProcDef() {
    var ret = selectDataTableRow();
    if (!ret) {
       art.dialog({content: "请选择一条记录"});
    }
    
    var msg = "", url = "";
    if (ret.suspensionstate == '1') { // 当前状态为1.已激活
        msg = "您确定要挂起该流程吗？";
        url = "${ctx }/admin/workflowProcDef/doSuspendProcDef";
    } else {
        msg = "您确定要激活该流程吗？";
        url = "${ctx }/admin/workflowProcDef/doActivateProcDef";
    }
    
    art.dialog({
        content: msg,
		lock: true,
		width: 260,
		height: 100,
		ok: function () {
            $.ajax({
                url: url,
                contentType: "application/x-www-form-urlencoded; charset=UTF-8",
                data: {state: ret.suspensionstate, processDefinitionId: ret.id},
                dataType: "json",
                type: "post",
                cache: false,
                error: function(){
                    art.dialog({content: "操作失败！"});
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
                        art.dialog({content: obj.msg});
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
*/

$(function(){
    //初始化表格
	Global.JqGrid.initJqGrid("dataTable", {
		url: "${ctx}/admin/actProcdef/goJqGrid",
		postData: {onlyLatestVersion: $("#onlyLatestVersion").val()},
		height: $(document).height()-$("#content-list").offset().top-80,
		colModel: [  
			{name: "id", index: "id", width: 110, label:"流程ID", sortable: true, align: "left"}, 
			{name: "key", index: "key", width:100, label:"流程标识", sortable: true, align: "left"}, 
			{name: "name", index:"name",  width: 100, label:"流程名称", sortable: true, align: "left"}, 
			{name: "version", index: "version", width: 70, label:"流程版本", sortable: true, align: "left"}, 
			{name: "suspensionstate", index: "suspensionstate", width: 70, label:"流程状态", sortable: true, align: "left", hidden: true}, 
            {name: "suspensionstatedescr", index: "suspensionstatedescr", width: 70, label:"流程状态", sortable: true, align: "left"}, 
			{name: "deploytime", index: "deploytime", width: 126, label:"部署时间", sortable: true, align: "left", formatter: formatTime}, 
			{name: "deploymentid", index: "deployment_id", width: 60, label:"部署ID", sortable: true, align: "left"}, 
			{name: "category", index: "category", width: 200, label:"流程分类", sortable: true, align: "left"},
			{name: "resourcename", index: "resourcename", width: 200, label:"流程xml", sortable: true, align: "left"},
			{name: "dgrmresourcename", index: "dgrmresourcename", width: 200, label:"流程图片", sortable: true, align: "left" },
	    ],
	    ondblClickRow: function () {
            viewProcDefImage();
        }
	});
});

// 点击只显示最新版本
function onlyLatestVersionClick() {
	if($("#onlyLatestVersion").val() == "true"){
	    $("#onlyLatestVersion").val("false");
	}else{
	    $("#onlyLatestVersion").val("true");
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
						<input type="text" id="name" name="name" />
					</li>
					<li>
						<label>流程标识</label>
						<input type="text" id="key" name="key" />
					</li>
					<li style="width: 150px;">
                           <label>
                            <input type="checkbox" id="onlyLatestVersion" name="onlyLatestVersion" 
                                value="true" onclick="onlyLatestVersionClick()" checked="checked"/>只显示最新版本
                           </label>
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
				<button type="button" class="btn btn-system" onclick="goImport()">导入流程</button>
			    <%-- <house:authorize authCode="process_update">
			 		<button type="button" class="btn btn-system" onclick="activateOrSuspendProcDef()">激活/挂起</button>
				</house:authorize> --%>
			    <button type="button" class="btn btn-system" onclick="viewImage()">查看流程图</button>
			</div>
		</div>
		<div id="content-list">
			<table id= "dataTable"></table>
			<div id="dataTablePager"></div>
		</div>
	</div>
</body>
</html>


