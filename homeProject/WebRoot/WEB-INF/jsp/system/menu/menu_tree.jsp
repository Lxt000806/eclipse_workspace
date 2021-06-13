<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <title>菜单树</title>
    <%@ include file="/commons/jsp/common.jsp" %>
    
    <script type="text/javascript">
	var setting = {
		view: {
			dblClickExpand: false,
			showLine: true,
			selectedMulti: false
		},
		data: {
			simpleData: {
				enable:true,
				idKey: "menuId",
				pIdKey: "parentId"
			},
			key:{
				name: "menuName"
			}
		},

		callback: {
			beforeClick: function(treeId, treeNode) {
				if (treeNode.isParent) {
					parent.zTree.expandNode(treeNode, true);
				}
				parent.listFrame.loadDate(treeNode);
				return true;
			}
		}
	};
	
	var zNodes =${nodes};
	
	$(document).ready(function(){
		for(var i=0; i<zNodes.length; i++){
			if(zNodes[i].menuType == '0'){
				zNodes[i].icon = '${resourceRoot}/images/folderClosed.gif';
			}
			if(zNodes[i].menuType == '1'){
				var flag = true;
				if(typeof(zNodes[i].openIcon) != 'undefined' && $.trim(zNodes[i].openIcon) != ""){
					zNodes[i].iconOpen = '${resourceRoot}/'+$.trim(zNodes[i].openIcon);
					flag = false;
				}
				if(typeof(zNodes[i].closeIcon) != 'undefined' && $.trim(zNodes[i].closeIcon) != ""){
					zNodes[i].iconClose  = '${resourceRoot}/'+$.trim(zNodes[i].closeIcon);
					flag = false;
				}
				if(flag){
					zNodes[i].isParent = true;
				}
			}
			if(zNodes[i].menuType == '2'){
				var url = "";
				if(typeof(zNodes[i].openIcon) != 'undefined' && $.trim(zNodes[i].openIcon) != ""){
					url = '${resourceRoot}/'+$.trim(zNodes[i].openIcon);
				}
				if(typeof(zNodes[i].closeIcon) != 'undefined' && $.trim(zNodes[i].closeIcon) != ""){
					url = '${resourceRoot}/'+$.trim(zNodes[i].closeIcon);
				}
				if(url != ""){
					zNodes[i].icon = url;
				}else{
					zNodes[i].isParent = false;
				}
			}
		}
	
		$.fn.zTree.init($("#tree"), setting, zNodes);
		parent.zTree = $.fn.zTree.getZTreeObj("tree");
		
		var rootNode = parent.zTree.getNodeByParam("menuId", '${rootMenuId}', null);
		parent.zTree.selectNode(rootNode);
		parent.zTree.expandNode(rootNode);
	});
   	</script>
</head>
<body style="background: #FFF;">
	<ul id="tree" class="ztree" style="position: absolute;left:0px;top: 5px;"></ul>
</body>
</html>
