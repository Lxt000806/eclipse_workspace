<%@ page import="java.util.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="house" uri="http://www.housenet.com/Framework/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="v" value="20210526"></c:set>
<link href="${resourceRoot}/css/iss.core.css" rel="stylesheet" />
<link href="${resourceRoot}/css/tab.css" rel="stylesheet" />
<link href="${resourceRoot}/artDialog/skins/chrome.css" rel="stylesheet" />
<link rel="stylesheet" href="${resourceRoot}/zTree/3.5.18/css/zTreeStyle/zTreeStyle.css?v=20200711" type="text/css">
<%-- <link href="${resourceRoot}/zTree/css/zTreeStyle/zTreeStyle.css" rel="stylesheet"  /> --%>
<link href="${resourceRoot}/jquery-ui-1.11.4.custom/jquery-ui.min.css" type="text/css" rel="stylesheet"/>
<link href="${resourceRoot}/uploadify/uploadify.css" rel="stylesheet" />
<link href="${resourceRoot}/easyUpload/dist/jquery.magnify.css" rel="stylesheet" />
<link href="${resourceRoot}/easyUpload/upload/upload.css" rel="stylesheet" />
<link href="${resourceRoot}/easyUpload/easy-upload.css" type="text/css" rel="stylesheet" ></link>
<link href="${resourceRoot}/bootstrap/css/bootstrap.css" rel="stylesheet" />
<link href="${resourceRoot}/bootstrap/css/bootstrapValidator.min.css" rel="stylesheet" />
<link href="${resourceRoot}/bootstrap/css/ui.jqgrid-bootstrap.css" rel="stylesheet" />
<link href="${resourceRoot}/bootstrap/css/bsmain.css?v=${v}" rel="stylesheet" />
<link href="${resourceRoot}/bootstrap/css/search-suggest.css" rel="stylesheet" />
<link href="${resourceRoot}/css/steps.css" rel="stylesheet" />
<link href="${resourceRoot}/searchableSelect/jquery.searchableSelect.css" rel="stylesheet" />
<link href="${resourceRoot}/css/step_style2.css" rel="stylesheet" />
<style type="text/css">
#bgShadow{ display: none;  position: absolute;  top: 0%;  left: 0%;  width: 100%;  height: 100%;  background-color: gray;  z-index:1001;  -moz-opacity: 0.7;  opacity:.70;  filter: alpha(opacity=70);}
</style>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<script type="text/javascript">
	var ctx = "${ctx}";
</script>
<script src="${resourceRoot}/jquery/jquery-1.11.3.min.js" type="text/javascript"></script>
<%-- <script src="${resourceRoot}/js/jquery.js" type="text/javascript"></script> --%>
<script src="${resourceRoot}/bootstrap/js/bootstrap.min.js"></script>
<script src="${resourceRoot}/bootstrap/js/bootstrapValidator.js"></script>
<script src="${resourceRoot}/bootstrap/js/language/zh_CN.js"></script>
<script src="${resourceRoot}/bootstrap/js/search-suggest.js"></script>
<script src="${resourceRoot}/jquery-ui-1.11.4.custom/jquery-ui.min.js" type="text/javascript"></script>
<script src="${resourceRoot}/jqGrid/5.0.0/js/jquery.jqGrid.js?v=${v}" type="text/javascript"></script>
<script src="${resourceRoot}/jqGrid/5.0.0/js/i18n/grid.locale-cn.js" type="text/javascript"></script>
<script src="${resourceRoot}/jqGrid/5.0.0/js/jquery.form.js" type="text/javascript"></script>
<script src="${resourceRoot}/js/iss.cssTable.js" type="text/javascript"></script>
<script src="${resourceRoot}/js/iss.panelBar.js" type="text/javascript"></script>
<script src="${resourceRoot}/js/iss.pageBar.js" type="text/javascript"></script>
<script src="${resourceRoot}/artDialog/source/artDialog.js" type="text/javascript"></script>
<script src="${resourceRoot}/artDialog/source/artDialog.plugins.js" type="text/javascript"></script>
<script src="${resourceRoot}/js/jquery.validate.js" type="text/javascript"></script>
<script src="${resourceRoot}/js/additional-methods.js" type="text/javascript"></script>
<script src="${resourceRoot}/js/messages_cn.js" type="text/javascript"></script>
<%-- <script src="${resourceRoot}/zTree/js/jquery.ztree.all-3.1.js" type="text/javascript"></script> --%>
<script src="${resourceRoot}/zTree/3.5.18/js/jquery.ztree.all-3.5.min.js" type="text/javascript"></script>
<script src="${resourceRoot}/datePicker/WdatePicker.js?v=20200811"></script>	
<script src="${resourceRoot}/jqGrid/5.0.0/js/global.js?v=${v}" type="text/javascript"></script>
<script src="${resourceRoot}/js/iss.core.js?v=${v}" type="text/javascript"></script>
<script src="${resourceRoot}/js/ajaxfileupload.js"></script>
<script src="${resourceRoot}/easyUpload/easyUpload.js" type="text/javascript" ></script>
<script src="${resourceRoot}/easyUpload/upload/upload.js" type="text/javascript" ></script>
<script src="${resourceRoot}/easyUpload/blowup.js" type="text/javascript" ></script>
<script src="${resourceRoot}/uploadify/jquery.uploadify-3.1.min.js"></script>
<script src="${resourceRoot}/easyUpload/dist/jquery.magnify.js"></script>
<script src="${resourceRoot}/searchableSelect/jquery.searchableSelect.js?v=${v}"></script>
<%@ include file="/commons/jsp/setColumn.jsp" %>
<!-- 遮罩层 -->
<div id="bgShadow"></div>
<iframe id="targetFrame" name="targetFrame" style="display: none;"></iframe>
<form id="targetForm" action="" method="post" target="targetFrame" style="display:none;">
	<input type="hidden" name="exportData" id="exportData" />
</form>



