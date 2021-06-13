<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<title>交房批次管理</title>
		<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
		<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
		<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
		<META HTTP-EQUIV="expires" CONTENT="0" />
		<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
		<%@ include file="/commons/jsp/common.jsp" %>
		<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	
	<script type="text/javascript">
	</script>
	</head>

<body>
	<div class="body-box-list">
		<div class="panel panel-system">
			<div class="panel-body">
				<div class="btn-group-xs" style="float:left">
							<button id="closeBut" type="button" class="btn btn-system"
								onclick="closeWin(false)">关闭</button>
				</div>
			</div>
		</div>
		<div class="panel panel-info">
			<form action="" method="post" id="page_form" class="form-search">
				<ul class="ul-form">
					<div class="validate-group row">
						<li><label>项目编号</label> <input type="text" id="code" name="code"
							value="${builder.code}" readonly="readonly" />
						</li>
						<li><label>项目名称</label> <input type="text" id="descr"
							name="descr" style="width:160px;" value="${builder.Descr }" readonly="readonly"/>
						</li>
					</div>
					<div class="validate-group row">
							<li><label class="control-textarea">地址</label> <textarea
									id="adress" name="adress" disabled="disabled">${builder.Adress }</textarea>
							</li>
					</div>
		</div>
		</ul>
		</form>
	</div>
	<div class="container-fluid">
		<ul class="nav nav-tabs">
			<li id="tabHouseInfo" class="active"><a
				href="#tab_HouseInfo" data-toggle="tab">交房批次信息</a>
			</li>
			<li id="tabHouseNo" class=""><a
				href="#tab_HouseNo" data-toggle="tab">交房楼号</a>
			</li>
		</ul>
		<div class="tab-content">
			<div id="tab_HouseInfo" class="tab-pane fade in active">
				<jsp:include page="builder_houseManage_houseInfo.jsp"></jsp:include>
			</div>
			<div id="tab_HouseNo" class="tab-pane fade ">
				<jsp:include page="builder_houseManage_houseNo.jsp"></jsp:include>
			</div>
		</div> 
	</div>
	</div>
</body>
</html>
