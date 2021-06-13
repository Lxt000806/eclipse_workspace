<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>权限查看</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>

</head>
    
<body>
<div class="body-box" >
	<div class="panel panel-system" >
    <div class="panel-body" >
      <div class="btn-group-xs" >
      <button id="closeBut" type="button" class="btn btn-system "   onclick="closeWin()">关闭</button>
      </div>
   </div>
	</div>

		<div class="edit-form">
			<form action="" method="post" id="dataForm" enctype="multipart/form-data" class="form-search">
				<ul class="ul-form">
							<div class="row">
								<div class="col-sm-6" >
								<li >
								<label><span class="required">*</span>权限名称</label>
								<input type="text"  id="authName" name="authName"  value="${authority.authName }"/>
								</li>
								</div>
								<div class="col-sm-6" >
								<li>
								<label><span class="required">*</span>权限编码</label>
						        <input type="text"  id="authCode" name="authCode" value="${authority.authCode }"/>
						        </li>
						        </div>
						        </div>
						      <div class="row">
								<div class="col-sm-6" >
									<li >
									<label>创建时间</label>
									    <input type="text"   value="${authority.genTime }"/>
							
								</li>
								</div>
								<div class="col-sm-6" >
									<li >
									<label>修改时间</label>
									    <input type="text"   value="${authority.updateTime }"/>
							
								</li>
								</div>
								</div>  
							<div class="row">
								<div class="col-sm-12" >
									<li >
									<label>所属菜单</label>
									    <input type="text"   value="${menuName }"/>
							
								</li>
								</div>
								</div>
								<div class="validate-group row">
								<div class="col-sm-12" >
								<li>
								<label  class="control-textarea">备注</label>
								<textarea style="width: 660px"   id="remark" name="remark">${authority.remark }</textarea>
								</li>
								</div>
							</div>
							</ul>
				<table class="table-layout-fixed" id="fix_layout_table">
					<thead>
						<tr isHead="1">
							<th style="width: 30px;">序号</th>
							<th style="width: 300px;">链接地址</th>
							<th>链接说明</th>
						</tr>
					</thead>
					<tbody>
					<c:forEach items="${authority.resourcesList}" varStatus="item_index" var="resources">
						<tr index="${item_index.index+1}">
							<td class="td-seq">${item_index.index+1}</td>
							<td>${resources.urlStr }</td>
							<td>${resources.remark }</td>
						</tr>
					</c:forEach>
					</tbody>
				</table>
			</form>
		</div>
	</div>
</div>
</body>
</html>
