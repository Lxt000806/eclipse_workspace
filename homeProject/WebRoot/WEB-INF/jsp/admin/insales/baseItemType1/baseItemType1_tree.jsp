<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>基装类型菜单树</title>
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
				idKey: "menuIdStr",
				pIdKey: "parentIdStr"
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
		parent.zTree.expandAll(true);
		if('${baseItemType1}'){
			//增加自动滚动到选中的材料 add by cjg 2018-09-26
			var allNodes=parent.zTree.getNodes();//获取所有父子节点
			var isFound=false;//判断是否找到选中的材料
			var newNodes=[];//新建一个数组，用于存放所选材料之前的所有材料
			for(var i=0;i<allNodes.length;i++){//把所选材料之前的所有材料存入新数组
				if(allNodes[i].menuIdStr!="${baseItemType1}"){
					newNodes.push(allNodes[i]);
				}else{
					isFound=true;
					break;
				};
			}
			var index=newNodes.length;//新数组长度，即为所选材料之前的父节点个数
			for(var i=0;i<newNodes.length;i++){//加上每个父节点的所有子节点个数
				index+=newNodes[i].children.length;
			}
			if(isFound)//有找到，就滚动
			$("html,body").animate({//用所有节点数计算并设置滚动位置
               scrollTop: index*18,
            }, 10);
            setTimeout(function(){
	            var node = parent.zTree.getNodeByParam("menuIdStr", '${baseItemType1}');
				parent.zTree.selectNode(node);
				parent.clickTree(node);
				parent.zTree.expandNode(node, true);
			},150);
			
		}
		
	});
    </script>
</head>
<body style="background: #FFF;">
<div style="wdith:200px">
<ul id="tree" class="ztree" style="position: absolute;left:0px;top: 5px;"></ul>
</div>

</body>
</html>


