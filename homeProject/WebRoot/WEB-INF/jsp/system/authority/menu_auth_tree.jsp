<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title></title>
    <%@ include file="/commons/jsp/common.jsp" %>
    
    <script type="text/javascript">
	var zTree;
	var operType=0; //1 新增 2 修改

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
					zTree.expandNode(treeNode, true);
				}
				clickTree(treeNode);
				return true;
			}
		}
	};
	
	var zNodes =${nodes};
	
	$(document).ready(function(){
		var t = $("#tree");
		t = $.fn.zTree.init(t, setting, zNodes);
		zTree = $.fn.zTree.getZTreeObj("tree");
		setSpace();
	});
	
	//设置页面宽高度
	function setSpace() {
		var width = parseInt(document.documentElement.scrollWidth);
		height=getBodyHeight()-20;
		$("#mainTable").css("width",width+"px").css("height",height+"px");
		$("#tree").css("height",height+"px");
		$("#testIframe").css("height",$("#mainTable").css("height"));
	}
	
	function clickTree(treeNode){
		$("#testIframe").attr("src", "${ctx}/admin/authority/goList?menuId="+treeNode.menuId);
	}
    </script>
</head>
<body style="width:100%;height:100%;border:0;overflow:hidden;">
<table id="mainTable" style="position: absolute;left: 0px;top: 0px;">
	<tr>
		<td width=200px align=left valign=top style="border: solid 1px #CCC;">
			<ul id="tree" class="ztree" style="width:200px; overflow:auto;"></ul>
		</td>
		<td align=left valign=top>
			<iframe id="testIframe" name="testIframe" frameborder=0 src="${ctx}/admin/authority/goList" scrolling=auto width=100% ></iframe>
		</td>
	</tr>
</table>
</body>
</html>


