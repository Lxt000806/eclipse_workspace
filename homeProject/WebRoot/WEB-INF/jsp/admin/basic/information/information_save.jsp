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
function checkChildrenIsAllHidden(node){
	var result = true;
	if(node && node.children){
		$.each(node.children, function(eachIndex, subNode) {
	        if(subNode.checked == false){
	            result = false;
	        }
		
		});
	}
    return result;
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

function recursionShowNode(zTree, node){
    if(node === undefined || node === null) return;
    var sameCodeNodes = zTree.getNodesByParam("id", node.id, null);
    $.each(sameCodeNodes, function(eachIndex, sameCodeNode){
        zTree.showNode(sameCodeNode);
    });
}

function recursionRemoveNode(zTree, node){
    if(node === undefined || node === null) return;
    //移除非父节点，并递归检查该节点的父节点是否变为了非父节点
    if(node.isParent === false){
        zTree.removeNode(node);
        //递归移除没有子节点的父节点
        recursionRemoveNode(zTree, node.getParentNode());
    }
}

var alreadyAddNodes = [];
//添加团队部门按钮点击函数
function addTeamDept(){
	var allDepEmpZTree = $.fn.zTree.getZTreeObj("allDepEmpZTree");
	var selectedNodes = allDepEmpZTree.getCheckedNodes(true);
	var parentNodes = [];
	$.each(selectedNodes, function(eachIndex, selectedNode){
		if(selectedNode.isParent){
			parentNodes.push(selectedNode.children);
			selectedNode.children=[];
		}
	})
	alreadyAddNodes = alreadyAddNodes.concat(JSON.parse(JSON.stringify(selectedNodes)));
	for(var i=0;i<alreadyAddNodes.length;i++){
		for(var j=i+1;j<alreadyAddNodes.length;j++){
			if(alreadyAddNodes[i].id==alreadyAddNodes[j].id&&alreadyAddNodes[i].isParent){
				alreadyAddNodes.splice(i,1);
			}
		}
	}
	console.log(alreadyAddNodes);
	$.fn.zTree.init($("#receiveZTree"),receiveZTreeSetting, null);
	//将选中的节点添加到团队部门zTree
	var receiveZTree = $.fn.zTree.getZTreeObj("receiveZTree");
	receiveZTree.addNodes(null, alreadyAddNodes, false);
	//递归隐藏选中的节点
	var index=0;
	$.each(selectedNodes, function(eachIndex, selectedNode){
		if(selectedNode.isParent){
			selectedNode.children=parentNodes[index];
			index++;
		}
	})
	$.each(selectedNodes, function(eachIndex, selectedNode){
	   recursionHideNode(allDepEmpZTree, selectedNode);
	})
	allDepEmpZTree.checkAllNodes(false);
	receiveZTree.checkAllNodes(false);
	receiveZTree.expandAll(true);
}

//删除团队部门按钮点击函数
function deleteTeamDept(){
    var receiveZTree = $.fn.zTree.getZTreeObj("receiveZTree");
    var selectedNodes = receiveZTree.getCheckedNodes(true);
    var allDepEmpZTree = $.fn.zTree.getZTreeObj("allDepEmpZTree");
    $.each(selectedNodes, function(eachIndex, selectedNode){
    	for(var i=0;i<alreadyAddNodes.length;i++){
    		if(alreadyAddNodes[i].id==selectedNode.id){
    			if(alreadyAddNodes[i].isParent){
    				if(!hasNoCheckedChildNode(receiveZTree,alreadyAddNodes[i])){
    					alreadyAddNodes.splice(i,1);
    				}
    			}else{
    				alreadyAddNodes.splice(i,1);
    			}
    			
    		}
    	}
        recursionRemoveNode(receiveZTree, selectedNode);
        recursionShowNode(allDepEmpZTree, selectedNode);
    })
    allDepEmpZTree.checkAllNodes(false);
}

function hasNoCheckedChildNode(zTree,node){
	var tmpNodes = zTree.getNodesByParam("pid",node.id,null);
	for(var j=0;j<tmpNodes.length;j++){
		if(!tmpNodes[j].checked){
			return true;
		}
	}
	return false;
}
    
//用于将对象的属性名全部转为小写
function nameTo_(object) {
    var regObj = /([A-Z]+)/g;
    for (var i in object) {
        if (object.hasOwnProperty(i)) {
            var temp = object[i];
            if (regObj.test(i.toString())) {
                temp = object[i.replace(regObj, function (result) {
                    return result.toLowerCase();
                })] = object[i];
                delete object[i];
            }
            if (typeof temp === 'object' || Object.prototype.toString.call(temp) === '[object Array]') {
                nameTo_(temp);
            }
        }
    }
    return object;
};

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
		    
//待添加的团队部门ZTree配置
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


function changeType(){
	if($("#infoType").val()=="1"){
		$("#infoTable").css("width","100%");
		$("#infoTitle").css("width","900px");
		$("#infoText").css("width","900px");
		$("#zTreeTable").css("display","none");
	}else if($("#infoType").val()=="2"){
		$("#infoTable").css("width","50%");
		$("#infoTitle").css("width","450px");
		$("#infoText").css("width","450px");
		$("#zTreeTable").css("display","");
	}
}


var  nodeList = [], clickCount = 0;
function searchName(){
	var zTree = $.fn.zTree.getZTreeObj("allDepEmpZTree");
    //如果焦点已经移动到了最后一条数据上，则提示用户（或者返回第一条重新开始），否则继续移动到下一条
    if(nodeList.length==0){
    	art.dialog({
			content: "没有搜索结果！",
			width: 300
		});
        return ;
    }else if(nodeList.length==clickCount){
        clickCount=0;
        zTree.selectNode(nodeList[clickCount]);
        clickCount++;
        return;
    }else{
        //让结果集里边的第一个获取焦点（主要为了设置背景色），再把焦点返回给搜索框
        zTree.selectNode(nodeList[clickCount]);
        clickCount ++;
    }
}

function searchNode(e) {
    var zTree = $.fn.zTree.getZTreeObj("allDepEmpZTree");
   	var key = $("#searchNameInput").val();
    nodeList = zTree.getNodesByParamFuzzy("name", key); //调用ztree的模糊查询功能，得到符合条件的节点
    if (nodeList.length>0) {
		zTree.selectNode(nodeList[0]);
		clickCount=0;
	}
}

function save(){
	formData.append("infoCata",$("#infoCata").val());
    formData.append("infoType",$("#infoType").val());
	formData.append("infoTitle",$("#infoTitle").val());
	formData.append("infoText",$("#infoText").val());
	formData.append("m_umState","${information.m_umState}");
	if("${information.m_umState}"=="M"){
		formData.append("number","${information.number}");
		formData.append("expired",$("#expired").val());
	}
	formData.append("m_umState","${information.m_umState}");
	var param= JSON.stringify(Global.JqGrid.allToJson("dataTable"));
	formData.append("infoAttach",param);
	if($("#infoType").val()=="2"){
		var parentNodes=[];
		var childrenNodes=[];
		var allDepEmpZTree = $.fn.zTree.getZTreeObj("allDepEmpZTree");
		var receiveZTree = $.fn.zTree.getZTreeObj("receiveZTree");
		receiveZTree.checkAllNodes(true);
		var receiveNodes = receiveZTree.getCheckedNodes(true);
		for(var i=0;i<receiveNodes.length;i++){
			if(receiveNodes[i].isParent){
				parentNodes.push(receiveNodes[i]);
			}
		}
		if(parentNodes.length>0){
			for(var j=0;j<parentNodes.length;j++){
				var nodes = receiveZTree.getNodesByParam("pid",parentNodes[j].id);
				var hideNodes = allDepEmpZTree.getNodesByParam("pid",parentNodes[j].id);
				if(nodes.length==hideNodes.length){
					childrenNodes.push(parentNodes[j]);
				}else{
					for(var k=0;k<nodes.length;k++){
						if(!nodes[k].isParent){
							childrenNodes.push(nodes[k]);
						}
					}
				}
			}
		}
		var rcvCzys=JSON.stringify(nameTo_(childrenNodes));
		formData.append("rcvCzys",rcvCzys);
	}
	
	$("#saveBtn").attr("disabled", true)
	
	$.ajax({
			url:"${ctx}/admin/information/uploadInfoFileNew",
			type: "post",
			data: formData,
			contentType:false,
			processData:false,
			error: function(obj){
				showAjaxHtml({"hidden": false, "msg": "保存数据出错"});
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
		    		$("#saveBtn").attr("disabled", false)
		    		art.dialog({
						content: obj.msg,
						width: 200
					});
		    	}
	    	}
		});
};

var formData = new FormData();
$(function() {
	var originalData = $("#page_form").serialize();
	if("${information.m_umState}"==="A"){
		var depEmpZTreeNodes =${depEmpZTreeJson};
		$.fn.zTree.init($("#allDepEmpZTree"), depEmpZTreeSetting, depEmpZTreeNodes);
	}else{
		var depEmpZTreeNodes =${depEmpZTreeJson};
		$.fn.zTree.init($("#allDepEmpZTree"), depEmpZTreeSetting, depEmpZTreeNodes);
		$.fn.zTree.init($("#receiveZTree"), receiveZTreeSetting,null);
		var allDepEmpZTree = $.fn.zTree.getZTreeObj("allDepEmpZTree");
		var receiveZTreeNodes=${receiveZTreeJson};
		console.log(receiveZTreeNodes);
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
					$.each(nodes, function(eachIndex, node){
						allDepEmpZTree.checkNode(node,true,true);
					})
				}
			}else{
				var nodes = allDepEmpZTree.getNodesByParam("id",receiveZTreeNodes[i].id);
				$.each(nodes, function(eachIndex, node){
					allDepEmpZTree.checkNode(node,true,true);
				})
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
		
	$("#upload").on("click",function(){
		document.getElementById("files").click();
	});
	
	$("#download").on("click",function(){
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
		}
		
	})
	
	$("#del").on("click",function(){
		var id = $("#dataTable").jqGrid("getGridParam","selrow");
		if (!id) {
			art.dialog({
				content: "请选择一条记录"
			});
			return;
		}
		
		art.dialog({
			content:"是否删除该记录？",
			lock: true,
			width: 200,
			height: 80,
			ok: function () {
				Global.JqGrid.delRowData("dataTable",id);
			},
			cancel: function () {
				return true;
			}
		});
	})

	$("#files").change(function () {
		if($("#files").val() != ""){
			formData.append("file",document.getElementById("files").files[0]);
			var fileName=$("#files").val().substring($("#files").val().lastIndexOf("\\")+1);
			var json={
				despseq:"0",
				filename:fileName,
				lastupdate:new Date(),
				lastupdatedby:"${sessionScope.USER_CONTEXT_KEY.czybh}",
				expired:"F",
				actionlog:"ADD"
			};
			console.log(fileName);
			Global.JqGrid.addRowData("dataTable",json);
		}
	})
	
	changeType();
	$("#searchNameInput").bind("blur", searchNode);

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
	               <button type="button" class="btn btn-system" id="saveBtn" onclick="save()">
		               <span>保存</span>
		           </button>
		           <button type="button" class="btn btn-system" id="closeBut" onclick="closeWin(true)">
		                <span>关闭</span>
		           </button>
		        </div>
		    </div>
		</div>
	</div>
	<form action="" method="post" id="dataForm" class="form-search" enctype="multipart/form-data">
	    <house:token></house:token>
		<div class="panel panel-info">
		    <div class="panel-body">
		        <input type="hidden" name="jsonString" value="" />
		        <input type="hidden" id="expired" name="expired" value="" /> 
		        <ul class="ul-form">
		            <div class="validate-group row">
		            	<li class="form-validate">
		            		<label style="width:200px;">消息编号</label>
		            		<input type="text" id="number"  name="number"  value="${information.number}" placeholder="保存时生成" readonly="readonly"/>
		            	</li>
		            	<li>
							<label>消息类型</label>
							<house:xtdm id="infoType" dictCode="INFOTYPE" onchange="changeType()" value="${information.infoType}"></house:xtdm>
						</li>
						<li>
							<c:if test="${information.m_umState=='M'}">
								<label>过期</label>
								<input type="checkbox" id="expired_show" name="expired_show" value="${information.expired }" onclick="checkExpired(this)" ${information.expired=='T'?'checked':'' } />
							</c:if>
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
						    			<house:xtdmMulit id="infoCata" dictCode="INFOCATA" selectedValue="${information.infoCata}"></house:xtdmMulit>
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
													<button type="button" class="btn btn-system " id="upload" >
														<span>上传附件</span>
													</button>
													<button type="button" class="btn btn-system " id="del">
														<span>删除附件</span>
													</button>
												</div>
											</div>
											<input type="file" id="files" style="display:none">
										</form>
						    		</div>
						    	</div>
					    	</div>
						</ul>
						<div style="position: absolute;width:100%;margin-left:50%;z-index:99;margin-top:40px" id="zTreeTable">
							<div style="margin-bottom:20px;float:left;">
								<div style="">
									<input id="searchNameInput" type="text" placeholder="请输入..." style="width:63%;height:24px;">
									<button type="button" style="height:24px;width:24%;margin-left:26px" onclick="searchName()">搜索</button>
								</div>
								<div style="border:1px solid silver;overflow:auto;">
									<ul id="allDepEmpZTree" class="ztree" style="width:250px;height:450px;overflow:auto;"></ul>
								</div>
							</div>
							<div style="width:50px;float:left;padding-top:200px;">
								<button type="button" style="width:50px;margin-bottom:20px;" onclick="addTeamDept()">&gt;&gt;</button>
								<button type="button" style="width:50px;" onclick="deleteTeamDept()">&lt;&lt;</button>
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
					</div>
		    	</div>
	    	</div>
		</div>
	</form>

</body>
</html>
