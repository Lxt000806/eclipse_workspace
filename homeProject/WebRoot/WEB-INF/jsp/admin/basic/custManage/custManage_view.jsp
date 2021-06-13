<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML>
<html>
  <head>
  
  	<title>查看客户</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
		<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
  	<script type="text/javascript">
		$(function() {
			Global.JqGrid.initJqGrid("custCodeDataTable", {
				height:300,
				url:"${ctx}/admin/custManage/goJqGridCustCode",
				postData: {
					pk: "${custAccount.pk}"
				},
				colModel : [			
					{name: "code", index: "code", width: 75, label: "code", sortable: true, align: "left", hidden:true},
					{name: "address", index: "address", width: 140, label: "楼盘地址", sortable: true, align: "left"},
					{name: "descr", index: "descr", width: 80, label: "客户名称", sortable: true, align: "left"},
					{name: "custtypedescr", index: "custtypedescr", width: 80, label: "客户类型", sortable: true, align: "left"},
					{name: "statusdescr", index: "statusdescr", width: 80, label: "客户状态", sortable: true, align: "left"},
	            ]
			});
			
		});
		
		
  	</script>

  </head>
  
  <body>
  	<div class="body-box-form">
		<div class="panel panel-system" >
		    <div class="panel-body" >
		    	<div class="btn-group-xs" >
     				<button id="closeBut" type="button" class="btn btn-system "   onclick="closeWin()">关闭</button>
				</div>
			</div>
		</div>
		<div class="panel panel-info" >  
			<div class="panel-body">
				<form action="" method="post" id="dataForm" class="form-search">
					<ul class="ul-form">
						<li>
							<label>手机号</label>
							<input type="text" id="mobile1" name="mobile1" value="${custAccount.mobile1}" readonly/>
						</li>
						<li>
							<label>用户昵称</label>
							<input type="text" id="nickName" name="nickName" value="${custAccount.nickName}"/>
						</li>
						<li class="search-group-shrink">
							<input type="checkbox" id="expired_show" name="expired_show"
									value="${custAccount.expired}" onclick="checkExpired(this)"
									${custAccount.expired=='T'?'checked':'' } disabled/><p>过期&nbsp;&nbsp;&nbsp;&nbsp;</p>
						</li>
					</ul>	
				</form>
			</div>
		</div>
		
		<div  class="container-fluid" >  
			<ul class="nav nav-tabs" >
			    <li class="active"><a href="#tabView_custCodeDataTable" data-toggle="tab">楼盘明细</a></li>  
			</ul>  
		    <div class="tab-content">  
				<div class="btn-panel" style="margin-top:2px;border: 1px solid #DDDDDD;border-top: 0px;border-bottom: 0px;">
				</div>	
				<div id="tabView_custCodeDataTable"  class="tab-pane fade in active"> 
					<table id="custCodeDataTable"></table>
				</div> 
			</div>	
		</div>
  	</div>
  </body>
</html>
