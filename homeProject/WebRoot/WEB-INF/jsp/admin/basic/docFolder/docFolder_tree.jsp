<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>菜单树</title>
    <%@ include file="/commons/jsp/common.jsp" %>
    <style type="text/css">
	    .dropdown-menu {
		    min-width: 100px;
		    font-size: 13px;
		    cursor: pointer;
		}
		.dropdown-menu > li > a:hover{
			  text-decoration: none;
			  background-color: #ebf1f5;
		}	
	</style>
<script type="text/javascript">
    
	var zNodes =${nodes};
	var zTree,rMenu;
	var selectNode;  //选中节点
	var setting = {
		view: {
			dblClickExpand: false,
			showLine: true,
			selectedMulti: false
		},
		data: {
			simpleData: {
				enable:true,
				idKey: "pk",
				pIdKey: "parentPk"
			},
			key:{
				name: "folderName"
			}
		},
		callback: {
			beforeClick: zTreeBeforeClick,
			onRightClick: zTreeOnRightClick,
		}
	};
	
	$(document).ready(function(){
		$.fn.zTree.init($("#tree"), setting, zNodes);
		parent.zTree = $.fn.zTree.getZTreeObj("tree");
		var initNode = parent.zTree.getNodeByParam("pk", '${initId}', null);
		parent.zTree.selectNode(initNode);
		parent.zTree.expandNode(initNode);
		zTree = parent.zTree;
		rMenu = $("#rMenu");
	});
	
    function zTreeBeforeClick(treeId, treeNode){
		if (treeNode.isParent) {
			parent.zTree.expandNode(treeNode, true);
		}
		var pk = treeNode.pk;
   		if(typeof(pk) == "undefined"){
   			return ;
   		}
		parent.document.getElementById("listFrame").src="${ctx}/admin/docFolder/goDetail?pk="+pk;
		selectNode=treeNode;
		
	};
	
	function zTreeOnRightClick(event, treeId, treeNode){
		if(selectNode!=treeNode){
			return;
		}
		if (!treeNode && event.target.tagName.toLowerCase() != "button" && $(event.target).parents("a").length == 0) {
			zTree.cancelSelectedNode();
			showRMenu("root", event.clientX, event.clientY);
		} else if (treeNode && !treeNode.noR) {
			zTree.selectNode(treeNode);
			showRMenu("node", event.clientX, event.clientY);
		}
	}

	function showRMenu(type, x, y){
		$("#rMenu ul").show();
        y += document.body.scrollTop;
        x += document.body.scrollLeft;
        rMenu.css({"top":y+"px", "left":x+"px", "visibility":"visible"});
		$("body").bind("mousedown", onBodyMouseDown);
	}
	
	function hideRMenu(){
		if (rMenu) rMenu.css({"visibility": "hidden"});
		$("body").unbind("mousedown", onBodyMouseDown);
	}
	
	function onBodyMouseDown(event){
		if (!(event.target.id == "rMenu" || $(event.target).parents("#rMenu").length > 0)) {
			rMenu.css({"visibility" : "hidden"});
		}
	}
	
	function addTreeNode() {
		hideRMenu();
		parent.listFrame.add();
	
	}
	function updateTreeNode() {
		hideRMenu();
		parent.listFrame.edit();
	
	}
	function delTreeNode(){
		hideRMenu();
		parent.listFrame.del();
	
	}
	
	function resetTree() {
		hideRMenu();
		$.fn.zTree.init($("#tree"), setting, zNodes);
	}
		
 </script>
</head>
<body style="background: #FFF;" >
	<ul id="tree" class="ztree" style="position: absolute;left:0px;top: 5px;" ></ul>
	<div id="rMenu" style="position: absolute;left:100px; width:50px ;
				font-size: 12px; min-width: 120px; font-size: 13px;width: 100px;"   >
		<ul  class="dropdown-menu pull-right" role="menu"    > 
			<li role="presentation">	
				<house:authorize authCode="DOCFOLDER_SAVE">
					<a role="menuitem" tabindex="-1"  onclick="addTreeNode();">新增子目录</a>
				 </house:authorize>	
			</li>
			<li role="presentation">
				<house:authorize authCode="DOCFOLDER_UPDATE">
					<a role="menuitem"  tabindex="-1" onclick="updateTreeNode();">修改目录</a>
				</house:authorize>
			</li>
			<li role="presentation">
				<house:authorize authCode="DOCFOLDER_DELETE">
					<a role="menuitem"  tabindex="-1" onclick="delTreeNode();">删除目录</a>
				</house:authorize>
			</li>
		</ul>
    </div>

</body>
</html>
