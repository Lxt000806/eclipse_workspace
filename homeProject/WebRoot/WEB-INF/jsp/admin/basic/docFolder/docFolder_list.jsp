<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  	<title>文档目录管理</title>
  	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8"/>
  	<META HTTP-EQUIV="pragma" CONTENT="no-cache"/>
  	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"/>
  	<META HTTP-EQUIV="expires" CONTENT="0"/>
  	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge"/>
  	<%@ include file="/commons/jsp/common.jsp" %>
  	<script type="text/javascript">
	    function hideTreeRightMenu() {
	    	var treeFrameWindow = $("#treeFrame")[0].contentWindow; 
	    	treeFrameWindow.hideRMenu(); 
		}
    </script>
    
</head>
	<body>
		<div class="body-box-list" style="width:21%;float:left;height:100%;">
			<iframe id="treeFrame" name="treeFrame" style="width: 100%;height: 685px;border-left: 0.8px solid #DDDDDD;border-top:none;"
                 src='${ctx}/admin/docFolder/goTree?initId=${initId }' onmouseout="hideTreeRightMenu()">
            </iframe>
		</div>
		<div class="body-box-list" style="width:79%;float:right; height:100%;" >
			 <iframe id="listFrame" name="listFrame" style="width: 100%;height: 685px;border-left: 0.8px solid #DDDDDD;border-top:none;"
                 src='${ctx}/admin/docFolder/goDetail?pk=${initId}' >
            </iframe>
		</div>
	</body>
</html>

























