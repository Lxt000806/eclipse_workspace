
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <title>字典树</title>
    <%@ include file="/commons/jsp/common.jsp" %>
    
    <script type="text/javascript">
	var setting = {
		view: {
			dblClickExpand: false,
			showLine: true,
			selectedMulti: false,
			fontCss: getFont,
			nameIsHTML: true
		},
		data: {
			simpleData: {
				enable:true
			}
		},
		
		async:{
			url : '${ctx}/admin/dictItem/doLoadTree?dictId=${rootId}',
			enable : true,
			autoParam: ["id"]//异步加载时需要提交的参数，多个用逗号分隔  
		},

		callback: {
			beforeClick: function(treeId, treeNode) {
				if (treeNode.isParent) {
					parent.zTree.expandNode(treeNode, true);
				}
				parent.clickTree(treeNode, '${dictType }');
				return true;
			}
		}
	};
	
	var zNodes = [];
	
	$(document).ready(function(){
		var dictJson = ${dictJson };
		var node = {id: '0',name: '字典管理',pId: '-1',nodeType: 'root'};
		zNodes.push(node);
		
		for(var i=0; i<dictJson.length; i++){
			node = {id: dictJson[i]['dictId'],name: dictJson[i]['dictName'],pId: '0',isParent: true,nodeType: 'dict'};
			if('${unUsable}' == dictJson[i]['status']){
				node.name = "<span style='color:red;font-style:italic;'>"+node.name+"[禁用]</span>";
			}
			zNodes.push(node);
		}
	
		$.fn.zTree.init($("#tree"), setting, zNodes);
		parent.zTree = $.fn.zTree.getZTreeObj("tree");
		
		var defaultId = "${defaultId}";
		if(defaultId == "")
			defaultId = "0";
		
		var rootNode = parent.zTree.getNodeByParam("id", defaultId, null);
		parent.zTree.selectNode(rootNode);
		parent.zTree.expandNode(rootNode);
	});
	
	function getFont(treeId, node) {
		return node.font ? node.font : {};
	}
   	</script>
</head>
<body style="background: #FFF;">
	<ul id="tree" class="ztree" style="position: absolute;left:0px;top: 5px;"></ul>
</body>
</html>
