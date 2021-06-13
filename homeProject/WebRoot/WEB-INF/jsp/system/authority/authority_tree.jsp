<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>权限菜单树</title>
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
				parent.clickTree(treeNode);
				return true;
			}
		}
	};
	
	var zNodes =${nodes};
	
	$(document).ready(function(){
		$.fn.zTree.init($("#tree"), setting, zNodes);
		parent.zTree = $.fn.zTree.getZTreeObj("tree");
	});
    </script>
</head>
<body style="background: #FFF;">
	<ul id="tree" class="ztree" style="position: absolute;left:0px;top: 5px;"></ul>
</body>
</html>


