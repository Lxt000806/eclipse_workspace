<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
    <META HTTP-EQUIV="pragma" CONTENT="no-cache" />
    <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
    <META HTTP-EQUIV="expires" CONTENT="0" />
    <META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
    <title>添加公告消息</title>
    <%@ include file="/commons/jsp/common.jsp" %>
    <script src="${resourceRoot}/zTree/3.5.18/js/jquery.ztree.exhide-3.5.min.js" type="text/javascript"></script>
    
    <script type="text/javascript">

	//所有部门ZTree配置
	var depEmpZTreeSetting = {
		check: {
			enable: true,
			chkStyle:"checkbox",
			chkboxType:{"Y":"ps", "N":"ps"},
			nocheckInherit: true
		},
		view: {
			dblClickExpand: false,
			showLine: true,
			selectedMulti: false
		},
		data: {
			simpleData: {
				enable:true,
				idKey: "id",
	 			pIdKey: "pid",
				rootPId: ""
			}
		},
		callback: {
			beforeClick: function(treeId, treeNode) {
				var zTree = $.fn.zTree.getZTreeObj("allDepEmpZTree");
				if (treeNode.isParent) {
					zTree.expandNode(treeNode);
					return false;
	 			} else {
					return true;
				}
			}
		}
	}
	var receiveZTreeSetting = {
		check: {
			enable: true,
			chkStyle:"checkbox",
			chkboxType:{"Y":"ps", "N":"ps"},
			nocheckInherit: true
		},
		view: {
			dblClickExpand: false,
			showLine: true,
			selectedMulti: false
		},
		data: {
			simpleData: {
				enable:true,
				idKey: "id",
	 			pIdKey: "pid",
				rootPId: ""
			}
		},
		callback: {
			beforeClick: function(treeId, treeNode) {
				var zTree = $.fn.zTree.getZTreeObj("receiveZTree");
				if (treeNode.isParent) {
					zTree.expandNode(treeNode);
					return false;
	 			} else {
					return true;
				}
			}
		}
	}
	
var alreadyAddNodes = [];
function addTeamDept(){
	var allDepEmpZTree = $.fn.zTree.getZTreeObj("allDepEmpZTree");
	var selectedNodes = allDepEmpZTree.getCheckedNodes(true);
	var parentNodes = [];
 	for(var selectedNode of selectedNodes){
		if(selectedNode.isParent){
			parentNodes.push(selectedNode.children);
			selectedNode.children=[];
		}
	} 
	var receiveZTree = $.fn.zTree.getZTreeObj("receiveZTree");
	alreadyAddNodes = alreadyAddNodes.concat(JSON.parse(JSON.stringify(selectedNodes)));
	for(var i=0;i<alreadyAddNodes.length;i++){
		for(var j=i+1;j<alreadyAddNodes.length;j++){
			if(alreadyAddNodes[i].id==alreadyAddNodes[j].id&&alreadyAddNodes[i].isParent){
				alreadyAddNodes.splice(i,1);
			}
		}
	}

	$.fn.zTree.init($("#receiveZTree"),receiveZTreeSetting, null);
	//将选中的节点添加到团队部门zTree
	receiveZTree.addNodes(null, alreadyAddNodes, false);
	//递归隐藏选中的节点
	var index=0;
	for(var selectedNode of selectedNodes){
		if(selectedNode.isParent){
			selectedNode.children=parentNodes[index];
			index++;
		}
	}
	for(var selectedNode of selectedNodes){
	   recursionHideNode(allDepEmpZTree, selectedNode);
	}
	allDepEmpZTree.checkAllNodes(false);
	receiveZTree.checkAllNodes(false);
	receiveZTree.expandAll(true);
}

function recursionHideNode(zTree, node){
    if(node === undefined || node === null) return;
    if(node.isParent){
    	/* var nodeTmp = zTree.getNodeByParam("id",node.id,null);
    	console.log(checkChildrenIsAllHidden(nodeTmp)); */
    	
    	if(checkChildrenIsAllHidden(node)){
	        zTree.hideNode(node);
	        //recursionHideNode(zTree, node);
	    }
    }else{
    	zTree.hideNode(node);
    }
}

function checkChildrenIsAllHidden(node){
    for(var subNode of node.children){
        if(subNode.checked === false){
            return false;
        }
    }
    return true;
}

function download(){
	var selectedRow = selectDataTableRow('dataTable');
	if(!selectedRow){
   		art.dialog({content: "请选择一条记录！",width: 200});
   		return false;
   	}
	if("${information.number}"==""){
		art.dialog({
			content: "服务器上找不到要下载的附件实体，可能原因：该附件未上传或上传失败！（备注：只有消息保存成功，附件才会上传至服务器。）",
			width: 300
		});
		return false;
	}else{
		window.open("${ctx}/admin/information/downloadNew?pk="+selectedRow.pk);
	}
}

function doCommit(){
	$.ajax({
		url:"${ctx}/admin/information/doCommit",
		data:{number:"${information.number}"},
		contentType:"application/x-www-form-urlencoded; charset=UTF-8",
		dataType:"json",
		type:"post",
		cache:false,
		error:function(){
			art.dialog({
				content:"提交异常"
			});
		},
		success:function(obj){
			if(obj.rs){
				art.dialog({
					content:obj.msg,
					time:1000,
					beforeunload: function () {
						closeWin(true);
					}
				});
			}else{
				art.dialog({
					content:obj.msg
				});
			}
		}
	});
}

function doPass(){
	$.ajax({
		url:"${ctx}/admin/information/doPass",
		data:{number:"${information.number}"},
		contentType:"application/x-www-form-urlencoded; charset=UTF-8",
		dataType:"json",
		type:"post",
		cache:false,
		error:function(){
			art.dialog({
				content:"审核通过异常"
			});
		},
		success:function(obj){
			if(obj.rs){
				art.dialog({
					content:obj.msg,
					time:1000,
					beforeunload: function () {
						closeWin(true);
					}
				});
			}else{
				art.dialog({
					content:obj.msg
				});
			}
		}
	});
}

function doReturn(){
	$.ajax({
		url:"${ctx}/admin/information/doReturn",
		data:{number:"${information.number}"},
		contentType:"application/x-www-form-urlencoded; charset=UTF-8",
		dataType:"json",
		type:"post",
		cache:false,
		error:function(){
			art.dialog({
				content:"审核不通过异常"
			});
		},
		success:function(obj){
			if(obj.rs){
				art.dialog({
					content:obj.msg,
					time:1000,
					beforeunload: function () {
						closeWin(true);
					}
				});
			}else{
				art.dialog({
					content:obj.msg
				});
			}
		}
	});
}

function doDelete(){
	$.ajax({
		url:"${ctx}/admin/information/doDelete",
		data:{number:"${information.number}"},
		contentType:"application/x-www-form-urlencoded; charset=UTF-8",
		dataType:"json",
		type:"post",
		cache:false,
		error:function(){
			art.dialog({
				content:"删除异常"
			});
		},
		success:function(obj){
			if(obj.rs){
				art.dialog({
					content:obj.msg,
					time:1000,
					beforeunload: function () {
						closeWin(true);
					}
				});
			}else{
				art.dialog({
					content:obj.msg
				});
			}
		}
	});
}

$(function() {
	var originalData = $("#page_form").serialize();
	if("${information.infoType}"=="2"){
		var depEmpZTreeNodes =${depEmpZTreeJson};
		$.fn.zTree.init($("#allDepEmpZTree"), depEmpZTreeSetting, depEmpZTreeNodes);
		$.fn.zTree.init($("#receiveZTree"), receiveZTreeSetting,null);
		var allDepEmpZTree = $.fn.zTree.getZTreeObj("allDepEmpZTree");
		var receiveZTreeNodes=${receiveZTreeJson};
		
		//console.log(receiveZTreeNodes);
		
		for(var i=0;i<receiveZTreeNodes.length;i++){
			if(receiveZTreeNodes[i].Type=="2"){
				var allCheckedTag=true;
				for(var j=i+1;j<receiveZTreeNodes.length;j++){
					if(receiveZTreeNodes[i].id==receiveZTreeNodes[j].pid){
						allCheckedTag=false;	
					}
				}
				if(allCheckedTag){
					var nodes = allDepEmpZTree.getNodesByParam("pid",receiveZTreeNodes[i].id);
					for(var node of nodes){
						allDepEmpZTree.checkNode(node,true,true);
					}
				}
			}else{
				var nodes = allDepEmpZTree.getNodesByParam("id",receiveZTreeNodes[i].id);
				for(var node of nodes){
					allDepEmpZTree.checkNode(node,true,true);
				}
			}
		}
		addTeamDept();
	}
   	Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/information/goInfoAttachJqGrid",
			postData:{number:"${information.number}"},
			height:$(document).height()-$("#content-list").offset().top-160,
			styleUI: 'Bootstrap', 
			colModel : [
				{name: "pk", index: "pk", width: 480, label: "附件pk", sortable: true, align: "left",hidden:true},
				{name: "infonum", index: "infonum", width: 480, label: "消息编号", sortable: true, align: "left",hidden:true},
				{name: "filename", index: "filename", width: 480, label: "附件名称", sortable: true, align: "left"},
				{name: "ftppath", index: "ftppath", width: 480, label: "下载路径", sortable: true, align: "left",hidden:true},
				{name: "dispseq", index: "dispseq", width: 480, label: "显示顺序", sortable: true, align: "left",hidden:true},	
				{name: "lastupdate", index: "lastupdate", width: 480, label: "最后更新日期", sortable: true, align: "left",hidden:true,formatter:formatTime},	
				{name: "lastupdatedby", index: "lastupdatedby", width: 480, label: "最后操作员", sortable: true, align: "left",hidden:true},	
				{name: "expired", index: "expired", width: 480, label: "是否过期", sortable: true, align: "left",hidden:true},	
				{name: "actionlog", index: "actionlog", width: 480, label: "操作方式", sortable: true, align: "left",hidden:true},	
				
			]
		});
		if("${information.infoType}"=="1"){
			$("#infoTable").css("width","100%");
			$("#infoTitle").css("width","900px");
			$("#infoText").css("width","900px");
		}else{
			
		}
		
		
});
	</script>
	<style type="text/css">
		.help-block{
		    margin-left:100px;
		}
	</style>
  </head>
  
  <body>
    <div class="body-box-form" style="height:35px">
	    <div class="panel panel-system">
	        <div class="panel-body">
	            <div class="btn-group-xs">
		            <c:if test="${information.m_umState=='P'}">
		            	<button type="button" class="btn btn-system" id="passBtn" onclick="doPass()">
		            		<span>审核通过</span>
		            	</button>
		            	<button type="button" class="btn btn-system" id="returnBtn" onclick="doReturn()">
		            		<span>审核不通过</span>
		            	</button>
	            	</c:if>
	            	<c:if test="${information.m_umState=='S'}">
		            	<button type="button" class="btn btn-system" id="commitBtn" onclick="doCommit()">
		            		<span>提交</span>
		            	</button>
	            	</c:if>
	            	<c:if test="${information.m_umState=='D'}">
		            	<button type="button" class="btn btn-system" id="commitBtn" onclick="doDelete()">
		            		<span>删除</span>
		            	</button>
	            	</c:if>
			        <button type="button" class="btn btn-system" id="closeBut" onclick="closeWin(true)">
		                <span>关闭</span>
		           	</button>
		        </div>
		    </div>
		</div>
	</div>
	<form action="" method="post" id="dataForm" class="form-search" enctype="multipart/form-data">
		<div class="panel panel-info">
		    <div class="panel-body">
		        <input type="hidden" name="jsonString" value="" />
		        <ul class="ul-form">
		            <div class="validate-group row">
		            	<li class="form-validate">
		            		<label style="width:200px;">消息编号</label>
		            		<input type="text" id="number"  name="number"  value="${information.number}" placeholder="保存时生成" readonly="readonly"/>
		            	</li>
		            	<li>
							<label>消息类型</label>
							<house:xtdm id="infoType" dictCode="INFOTYPE" value="${information.infoType}" disabled="true"></house:xtdm>
						</li>
		            </div>
		        </ul>
		    </div>
		</div>
		<div class="container-fluid">
		    <!-- 标签页 -->
		    <ul class="nav nav-tabs">
		        <li class="active"><a href="#tab_set_dept" data-toggle="tab">公告消息</a></li>
		    </ul>
		    <div class="tab-content">
		    	<div class="panel panel-info" >  
	        		<div class="panel-body" style="height:600px">
						<input type="hidden" name="exportData" id="exportData">
						<input type="hidden" name="sendCZY" id="sendCZY" value="${information.sendCZY }">
						<input type="hidden" name="confirmCZY" id="confirmCZY" value="${information.confirmCZY }">
						<ul class="ul-form">
							<div style="position:absolute;">
						    	<div class="validate-group" >	
						    		<li>
						    			<label>消息类别</label>
						    			<house:xtdmMulit id="infoCata" dictCode="" sql="select NOTE,CBM from tXTDM where ID='INFOCATA'" 
										sqlValueKey="CBM" sqlLableKey="NOTE" selectedValue="${information.infoCata}"></house:xtdmMulit>
						    		</li>
						    		<li>
						    			<label>发送日期</label>
						    			<input type="text" id="sendDate" name="sendDate" class="i-date"  
												onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
												value="<fmt:formatDate value='${information.sendDate}' pattern='yyyy-MM-dd'/>" disabled="true"/>
						    		</li>
						    		<li>
						    			<label>发送人</label>
						    			<input type="text" id="sendCzyDescr" name="sendCzyDescr" value="${information.sendCzyDescr }" readonly="readonly"/>
						    		</li>
						    		<li>
						    			<label>审批人</label>
						    			<input type="text" id="confirmCzyDescr" name="confirmCzyDescr" value="${information.confirmCzyDescr }" readonly="readonly"/>
						    		</li>
						    	</div>
						    	<div style="width:100%">
						    		<div style="width:50%;float:left" id="infoTable">
						    			<div class="validate-group" style="width:100%">
									    	<li>
									    		<label>消息主题</label>
									    		<input type="text" id="infoTitle" name="infoTitle" style="width:420px" value="${information.infoTitle }"/>
								    		</li>
								    	</div>
								    	<div class="validate-group" style="width:100%;height:200px">
								    		<li>
									    		<label >消息内容</label> 
												<textarea id="infoText" name="infoText" style="height:180px;width:450px ">${information.infoText}</textarea>
								    		</li>
								    	</div>
								    	<div id="content-list" style="width:86%;margin-left:70px">
								    		<label>附件</label>
											<table id= "dataTable"></table>
										</div> 
										<form action="" method="post" id="uploadForm" class="form-search" enctype="multipart/form-data">
											<div class="btn-panel" style="margin-left:70px;margin-top:20px" >
								    			<div class="btn-group-sm"  >
													<button type="button" class="btn btn-system " onclick="download()">
														<span>下载附件</span>
													</button>				
												</div>
											</div>
										</form>
						    		</div>
						    	</div>
					    	</div>
						</ul>
						<c:if test="${information.infoType=='2'}">
							<div style="position: absolute;width:100%;margin-left:50%;z-index:99;margin-top:40px">
								<div style="margin-bottom:20px;float:left;">
									<div style="">
										<input id="searchName" type="text" placeholder="请输入..." style="width:63%;height:24px;" >
										<button type="button" style="height:24px;width:24%;margin-left:26px" onclick="searchName()">搜索</button>
									</div>
									<div style="border:1px solid silver;overflow:auto;">
										<ul id="allDepEmpZTree" class="ztree" style="width:250px;height:450px;overflow:auto;"></ul>
									</div>
								</div>
								<div style="width:50px;float:left;padding-top:200px;">
									
								</div>
								<div style="float:left;margin-left:20px;margin-bottom:20px;">
									<div style="height:24px;padding-top:5px">
										<span style="height:24px">接收人员</span>
									</div>
									<div style="border:1px solid silver;overflow:auto;">
										<ul id="receiveZTree" class="ztree" style="width:250px;height:450px;overflow:auto;"></ul>
									</div>
								</div>
							</div>
						</c:if>
					</div>
		    	</div>
	    	</div>
		</div>
	</form>

</body>
</html>
