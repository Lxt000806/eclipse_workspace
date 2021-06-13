<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<title>选择查看角色页面</title>
		<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
		<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
		<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
		<META HTTP-EQUIV="expires" CONTENT="0" />
		<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
		<%@ include file="/commons/jsp/common.jsp" %>
	<script type="text/javascript">
	
	var last_contents=""; //最后搜索内容
	var last_flag=0;//最后定位光标
	$(document).ready(function(){
		var setting = {
			check: {
				enable: true,
				nocheckInherit: true
			},
			data: {
				simpleData: {
					enable: true,
					idKey: "RolePK",
					pIdKey: "pId",
				},
				key:{
					name: "ROLE_NAME"
				}
			}
		};
		var zUnSelectedNodes =${unSelectedJson};
		var zSelectedNodes =${selectedJson};
		$.fn.zTree.init($("#allList"), setting, zUnSelectedNodes);
		$.fn.zTree.init($("#selectedList"), setting, zSelectedNodes);
	});
	
	//移动到查看角色
	function addToSelectedList(){
		var allListZTree = $.fn.zTree.getZTreeObj("allList");
	    var selectedNodes = allListZTree.getCheckedNodes(true);
	    var selectedListZTree = $.fn.zTree.getZTreeObj("selectedList");
	    for (var i=0, l=selectedNodes.length; i < l; i++) 
		{
		    //删除选中的节点
			allListZTree.removeNode(selectedNodes[i]);	
		}
		selectedListZTree.addNodes(null, selectedNodes);
	    allListZTree.checkAllNodes(false);
	    selectedListZTree.checkAllNodes(false);
	    selectedListZTree.expandAll(true);
	}
	
	//移动到角色库
	function addToAllList(){
		var selectedListZTree = $.fn.zTree.getZTreeObj("selectedList");
	    var selectedNodes = selectedListZTree.getCheckedNodes(true);
	    var allListZTree = $.fn.zTree.getZTreeObj("allList");
	    for (var i=0, l=selectedNodes.length; i < l; i++) 
		{
		    //删除选中的节点
			selectedListZTree.removeNode(selectedNodes[i]);	
		}
	    allListZTree.addNodes(null, selectedNodes);
	    allListZTree.checkAllNodes(false);
	    selectedListZTree.checkAllNodes(false);
	    selectedListZTree.expandAll(true);
	}
	
	function  findContents(){
		var contents =$.trim($("#contents").val()); 
		if(contents==" ") return;
		var i_flag=0;
		var zTreeObj = $.fn.zTree.getZTreeObj("allList");
		var node=zTreeObj.getNodesByParamFuzzy('ROLE_NAME',contents);
		console.log(node);
		if (node.length>0) {
			if(contents==last_contents&&(last_flag+1<node.length)){
			    i_flag=last_flag+1; 
			} 
			zTreeObj.selectNode(node[i_flag]);
			last_contents=contents;
			last_flag=i_flag;
		}
	}
	
	$(function() {
		$("#contents").keydown(function(e) {  
           if (e.keyCode == 13) {  
             findContents(); 
             $("#contents").focus();  
           }  
     	 })
	});
	
	function save(){
		var treeObj = $.fn.zTree.getZTreeObj("selectedList");
		var nodes=treeObj.getNodes();
		var rows=[];
		if(nodes != null && nodes.length > 0){
			for(var i=0;i<nodes.length;i++){
				var json = {};
				json["rolepk"] = nodes[i].ROLE_ID;
				json["rolename"] = nodes[i].ROLE_NAME;
				rows.push(json);
			}
		}else{
			art.dialog({
				content: '选择角色不能为空'
			});
			return;
		}
		Global.Dialog.returnData = rows;
		closeWin();
	}
	</script>	
	</head>
	<body>
		<div class="body-box-list">
			<div class="panel panel-system" >
			    <div class="panel-body" >
			    	<div class="btn-group-xs" >
			    		<c:if test="${docFolderViewRole.m_umState!='V' }">
							<button id="saveBut" type="button" class="btn btn-system "   onclick="save()">保存</button>
						</c:if>
	    				<button id="closeBut" type="button" class="btn btn-system"   onclick="closeWin()">关闭</button>
					</div>
				</div>
			</div>
		<div class="panel panel-info">
			<div class="panel-body">
				<form  type="hidden" action="" method="post" id="page_form" class="form-search">
					<input type="hidden"name="jsonString" value=""  />
					<ul class="ul-form">
						<li>
						<li></li>
							<li></li>
						<li class="search-group">
						<li><label>查找内容</label> <input type="text" id="contents" name="Contents" value=" " />
						</li>
							<button type="button" class="btn  btn-sm btn-system " style="line-height: 1;" onclick="findContents();">查找</button>
						</li>
					</ul>
				</form>
			</div>
		</div>
			<div style="padding-top:10px">
				<div style="float:left;width:45%;height:400px;">
					<div style="height:30px;background:#F5F5F5;border:1px solid; border-color: #C0C0C0;border-bottom:0px;text-align: center;padding:6px;"><span>查看角色</span></div>
					<div style="height:370px;border:1px solid; border-color: #C0C0C0;overflow-x:hidden">
						<ul id="selectedList" class="ztree" >
						</ul>
					</div>
				</div>
				<div style="float:left;width:10%;height:400px;">
					<button style="width:90%;margin:5%;margin-top:100px" onclick="addToSelectedList()">&#60;&#60;</button>
					<button style="width:90%;margin:5%;" onclick="addToAllList()">&#62;&#62;</button>
				</div>
				<div style="float:right;width:45%;height:400px;">
					<div style="height:30px;background:#F5F5F5;border:1px solid;border-color: #C0C0C0;border-bottom:0px;text-align: center;padding:6px;""><span>角色库</span></div>
					<div style="height:370px;border:1px solid;border-color: #C0C0C0;overflow-x:hidden;">
						<ul id="allList" class="ztree" >
						</ul>
					</div>
				</div>
			</div> 
		</div>
	</body>
</html>
