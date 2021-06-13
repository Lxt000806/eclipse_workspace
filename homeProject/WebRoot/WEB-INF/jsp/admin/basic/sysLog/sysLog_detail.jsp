<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>SysLog明细</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp"%>
<style type="text/css">
.td-value{word-break:break-all;}
</style>
</head>
<body>
	<div class="body-box-form">
		<div class="panel panel-system" >
		    <div class="panel-body" >
		    	<div class="btn-group-xs" >
     				<button id="closeBut" type="button" class="btn btn-system "   onclick="closeWin(false)" id="closeBut">关闭</button>
				</div>
			</div>
		</div>
		<div class="panel panel-info" >  
			<div class="panel-body">	
				<form action="" method="post" id="dataForm" enctype="multipart/form-data" class="form-search">
					<ul class="ul-form">
						<div class="validate-group">
							<li>
								<label>操作日志编号</label>
								<input type="text" value="${sysLog.id}"/>
							</li>
							<li>
								<label>日志标题</label>
								<input type="text" value="${sysLog.title}"/>
							</li>
						</div>
						<div class="validate-group">
							<li>
								<label>日志类型</label>
								<input type="text" value="${sysLog.type}"/>
							</li>
							<li>
								<label>app日志类型</label>
								<input type="text" value="${sysLog.appType}"/>
							</li>
						</div>
						<div class="validate-group">
							<li>
								<label>请求URL</label>
								<input type="text" value="${sysLog.requestUrl}"/>
							</li>
							<li>
								<label>请求方法</label>
								<input type="text" value="${sysLog.method}"/>
							</li>
						</div>
						<div class="validate-group">
							<li>
								<label>远程访问IP</label>
								<input type="text" value="${sysLog.remoteAddr}"/>
							</li>
							<li>
								<label>操作员编号</label>
								<input type="text" value="${sysLog.operId}"/>
							</li>
						</div>
						<div class="validate-group">
							<li>
								<label>操作日期</label>
								<input type="text" value="<fmt:formatDate value='${sysLog.operDate }' pattern='yyyy-MM-dd HH:mm:ss'/>"/>
							</li>
							<li>
								<label>请求类</label>
								<input type="text" value="${sysLog.clazz}"/>
							</li>
						</div>
						<div class="validate-group">
							<li>
								<label class="control-textarea">用户请求代理</label>
								<textarea>${sysLog.userAgent}</textarea>
							</li>
						</div>
						<div class="validate-group">
							<li style="max-height:200px">
								<label class="control-textarea" style="top:-50px">请求参数</label>
								<textarea rows="8">${sysLog.params}</textarea>
							</li>
						</div>
						<div class="validate-group">
							<li>
								<label class="control-textarea">日志内容</label>
								<textarea>${sysLog.description}</textarea>
							</li>
						</div>
						<div class="validate-group">
							<li>
								<label class="control-textarea">返回内容</label>
								<textarea>${sysLog.responseContent}</textarea>
							</li>
						</div>
					</ul>
				</form>
			</div>
		</div>
	</div>
</body>
</html>

