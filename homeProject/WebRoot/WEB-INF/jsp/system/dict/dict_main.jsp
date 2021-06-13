<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <title>字典管理</title>
    <script src="${resourceRoot}/jquery/jquery-1.11.3.min.js" type="text/javascript"></script>
    <script type="text/javascript">
	var zTree;
	
	function clickTree(treeNode, dictType){
		if(treeNode.nodeType == "root"){
			$("#listFrame").attr("src", "${ctx}/admin/dict/goList?dictType="+dictType);
		}
		if(treeNode.nodeType == "dict"){
			$("#listFrame").attr("src", "${ctx}/admin/dictItem/goList?dictId="+treeNode.id+"&dictType="+dictType);
		}
		if(treeNode.nodeType == "item"){
			var id = treeNode.id;
			itemId = id.substring(0,id.indexOf("_"));
			$("#listFrame").attr("src", "${ctx}/admin/dictItem/goDetail?itemId="+itemId+"&dictType="+dictType);
		}
	}
    </script>
    <frameset cols="160,*" rows="*"  border="0" framespacing="1" frameborder="yes" bordercolor="#B2D9FF" style="border-style: hidden;"  >
  		<frame id="treeFrame" name="treeFrame" src='${ctx}/admin/dict/goTree?defaultId=0&dictType=${dictType }' frameborder="0" border="1" noresize="noresize">
  		<frame id="listFrame" name="listFrame" src="${ctx}/admin/dict/goList?dictType=${dictType }" frameborder="0" border="1" noresize="noresize">
	</frameset>
</head>

</html>


