<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
	<title>报表打印</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
</head>
<body class="body-500">
    <div class="error-header"> </div>
    <div class="container">
        <section class="error-container text-center">
            <h1>下载地址</h1>
            <div class="error-divider">
                <h2><a href="${ctx}/static/AdbeRdr11000_zh_CN.exe">安装Adobe Reader XI</a></h2>
                <p class="description" style="font-size: 14px;">说明：打印报表必需安装Adobe Reader XI，请点击上面的下载地址进行安装！</p>
            </div>
        </section>
    </div>
</body>
</html>
