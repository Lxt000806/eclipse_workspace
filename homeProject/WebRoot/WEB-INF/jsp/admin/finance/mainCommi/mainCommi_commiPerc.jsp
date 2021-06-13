<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>主材提成计算设置提成比例</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp"%>
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
					<li>
						<label>角色编号</label>
						<input type="text" id="code" name="code" style="width:160px;"/>
					</li>
					<li>
						<label>角色名称</label>
						<input type="text" id="descr" name="descr" style="width:160px;"/>
					</li>
					<li>
						<button type="button" class="btn btn-sm btn-system" onclick="goto_query();">查询</button>
						<button type="button" class="btn btn-sm btn-system" onclick="clearForm();">清空</button>
					</li>
				</ul>	

		</form>
	</div>
	<div class="container-fluid">
		<ul class="nav nav-tabs">
			<li id="tabMainCommiPercIndeSale" class="active"><a href="#tab_mainCommiPerc"
				data-toggle="tab">非独立销售提成比例</a></li>
			<li id="tabMainCommiPercIndeSale" class=""><a href="#tab_mainCommiPerc_indeSale"
				data-toggle="tab">独立销售提成比例</a></li>
		</ul>
		<div class="tab-content">
			<div id="tab_mainCommiPerc" class="tab-pane fade in active">
				<jsp:include page="tab_mainCommiPerc.jsp"></jsp:include> 
			</div>
			<div id="tab_mainCommiPerc_indeSale" class="tab-pane fade ">
				<jsp:include page="tab_mainCommiPerc_indeSale.jsp"></jsp:include>
			</div>
		</div>
	</div>
	</div>
</body>
<script>
	//重新加载表格 
	function goto_query(){
		$("#dataTable").jqGrid("setGridParam",{postData:$("#page_form").jsonForm(),page:1}).trigger("reloadGrid");
		$("#mainCommiPercIndeSale_dataTable").jqGrid("setGridParam",{postData:$("#page_form").jsonForm(),page:1}).trigger("reloadGrid");
	}

</script>
</html>
