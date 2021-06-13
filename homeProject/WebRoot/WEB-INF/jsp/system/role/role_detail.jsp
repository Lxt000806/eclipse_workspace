<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>查看角色</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>

</head>
    
<body>
<div class="body-box-form" >
	<div class="content-form">

		<!--panelBar-->
		<div class="panel panel-system" >
    <div class="panel-body" >
      <div class="btn-group-xs" >
						<button type="button" class="btn btn-system " id="closeBut" onclick="closeWin(false)">
							<span>关闭</span>
						</button>
					</div>
				</div>
			</div>
			<div class="panel panel-info" >  
			<div class="panel-body">
			  <form role="form" class="form-search" id="dataForm"  action="" method="post" target="targetFrame" >
						<ul class="ul-form">
							<li>
								<label>角色名称</label>
								<input value="${role.roleName }"/>
							</li>
							<li>
								<label>角色编码</label>
							<input value="${role.roleCode }"/>
							</li>
							<li>
								<label>创建时间</label>
								<input value="<fmt:formatDate value="${role.genTime }"  pattern="yyyy-MM-dd HH:mm:ss"/>"/>
							</li>
							<li>
								<label>修改时间</label>
								<input value="<fmt:formatDate value="${role.updateTime }"  pattern="yyyy-MM-dd HH:mm:ss"/>"/>
							</li>
							<li>
								<label>平台类型</label>
							<input value="${role.ptDescr }"/>
							</li>
							
							<li>
								<label class="control-textarea">备注</label>
								<textarea id="remark" name="remark" rows="3" readonly="readonly">${role.remark  }</textarea>
							</li>
							
							<li>
								<label class="control-textarea">分配用户</label>
								<textarea rows="3" readonly="readonly">${userNames }</textarea>
							</li>
							
							<li>
								<label class="control-textarea">分配权限</label>
								<textarea  rows="3" readonly="readonly">${authNames }</textarea>
							</li>
					</ul>	
			</form>
			</div>
			</div>
		</div>
</div>
</body>
</html>
