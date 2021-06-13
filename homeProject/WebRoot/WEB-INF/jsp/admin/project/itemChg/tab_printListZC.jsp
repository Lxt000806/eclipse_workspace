<%@ page language="java" import="com.house.framework.commons.utils.PathUtil" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://" + request.getServerName() + ":"+request.getServerPort()+path+"/";
String jasperPath = PathUtil.WEB_INF+"jsp/jasper/";
%>
<!DOCTYPE html>
<html>
<head>
  <title>材料增减--打印</title>
  <META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8"/>
  <META HTTP-EQUIV="pragma" CONTENT="no-cache"/>
  <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"/>
  <META HTTP-EQUIV="expires" CONTENT="0"/>
  <META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge"/>
  <%@ include file="/commons/jsp/common.jsp"%>
  <script type="text/javascript">
    $(function () {
      $('input:radio:first').attr('checked', 'checked');
    })
    function doPrint() {
      var reportName = "itemChgDetail_ZC.jasper";
      if ($("input[name='printId']:checked").val() == "1")
        reportName = "itemChgDetail_ZC_itemType2.jasper";
      Global.Print.showPrint(reportName, {
        No: '${no}',
        LogoFile: "<%=basePath%>jasperlogo/logo.jpg",
        SUBREPORT_DIR: "<%=jasperPath%>"
      });
    }

  </script>
</head>

<body>
<div class="body-box-form">
  <div class="panel panel-system">
    <div class="panel-body">
      <div class="btn-group-xs">
        <button type="button" class="btn btn-system " onclick="doPrint()">打印</button>
        <button type="button" class="btn btn-system " onclick="closeWin()">关闭</button>
      </div>
    </div>
  </div>

  <div class="query-form">
    <form action="" method="post" id="page_form">
      <input type="hidden" id="expired" name="expired"
             value="${itemChg.expired}"/>
      <input type="hidden" name="jsonString" value=""/>
      <table cellpadding="0" cellspacing="0" width="100%">
        <tbody>
        <tr class="td-btn">
          <td class="td-label">材料增减报表</td>
          <td class="td-value" colspan="1">
          	<input type="radio" name="printId" value="0"/>
          </td>
          <td class="td-label">材料增减报表(按材料类型2汇总)</td>
          <td class="td-value" colspan="1">
          	<input type="radio" name="printId" value="1"/>
          </td>
        </tr>
        </tbody>
      </table>
    </form>
  </div>
</div>
</body>
</html>


