<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <title></title>
    <script src="${resourceRoot}/jquery/jquery-1.11.3.min.js" type="text/javascript"></script>
    <script type="text/javascript">
	var zTree;
    </script>
	<frameset cols="160,*" rows="*"  border="1" framespacing="1" frameborder="yes" bordercolor="#B2D9FF"  >
	  	<frame id="treeFrame" name="treeFrame" src='${ctx}/admin/menu/goTree?rootMenuId=${initId }' frameborder="0" border="1"  noresize="noresize" >
	  	<frame id="listFrame" name="listFrame" src="${ctx}/admin/menu/goDetail?menuId=${initId }" frameborder="0" border="1"  noresize="noresize">
	</frameset>
  </head>
</html>
