<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <title>WareHouse明细</title>
  <META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8"/>
  <META HTTP-EQUIV="pragma" CONTENT="no-cache"/>
  <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"/>
  <META HTTP-EQUIV="expires" CONTENT="0"/>
  <META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge"/>
  <link href="${resourceRoot}/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css"/>
  <link href="${resourceRoot}/css/tab.css" rel="stylesheet"/>
  <link href="${resourceRoot}/zTree/css/zTreeStyle/zTreeStyle.css" rel="stylesheet" type="text/css"/>
  <link href="${resourceRoot}/jgrowl/jquery.jgrowl.css" rel="stylesheet" type="text/css"/>
  <link href="${resourceRoot}/artDialog/skins/chrome.css" rel="stylesheet"/>
  <link href="${resourceRoot}/jquery-ui-1.11.4.custom/jquery-ui.min.css" type="text/css" rel="stylesheet"/>
  <script type="text/javascript" src="${resourceRoot}/js/jquery.js"></script>
  <%--
  <script src="${resourceRoot}/jquery/jquery-1.11.3.min.js" type="text/javascript"></script>
  --%>
  <script src="${resourceRoot}/jquery-ui-1.11.4.custom/jquery-ui.min.js" type="text/javascript"></script>
  <script src="${resourceRoot}/ligerUI/js/ligerui.all.js" type="text/javascript"></script>
  <script src="${resourceRoot}/zTree/js/jquery.ztree.all-3.1.js" type="text/javascript"></script>
  <script type="text/javascript" src="${resourceRoot}/js/iss.core.js"></script>
  <script type="text/javascript" src="${resourceRoot}/artDialog/source/artDialog.js"></script>
  <script type="text/javascript" src="${resourceRoot}/jgrowl/jquery.jgrowl.js"></script>
  <script src="${resourceRoot}/jqGrid/5.0.0/js/global.js?v=${v}" type="text/javascript"></script>
  <script type="text/javascript">
    //默认从材料表选择
    var src;
    function clickTree(treeNode) {
      var len = window.document.getElementById("listFrame_q").src.indexOf("?");
      src = window.document.getElementById("listFrame_q").src.substring(0, len);
      if (treeNode.parentIdStr) {
        src += "?sqlCode=" + treeNode.menuIdStr + "&itemType1=${itemChg.itemType1}&custCode=${itemChg.custCode}";
      } else {
        src += "?itemType2=" + treeNode.menuIdStr + "&itemType1=${itemChg.itemType1}&custCode=${itemChg.custCode}";
      }
      //套餐材料限制客户类型
      //if (src.indexOf("custTypeItem") != -1) 
      src += "&custType=${itemChg.custType}"
      window.document.getElementById("listFrame_q").src = src;
    }

  </script>
  <frameset cols="120,*" rows="*" border="0" framespacing="1" frameborder="yes" bordercolor="#B2D9FF"
            style="border-style: hidden;">
    <frame id="treeFrame_q" name="treeFrame_q"
           src="${ctx}/admin/itemType2/goTree?itemType1=${itemChg.itemType1}&itemType2=${itemChg.itemType2}"
           frameborder="0" border="1" noresize="noresize">
    <frame id="listFrame_q" name="listFrame_q" src="${ctx}/admin/item/goItemSelect?sqlCode=00" frameborder="0"
           border="1" noresize="noresize">
  </frameset>


</head>

<body>
</body>
</html>
