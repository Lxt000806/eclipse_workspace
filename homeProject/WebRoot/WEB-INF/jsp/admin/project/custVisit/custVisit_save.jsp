<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8"/>
	<META HTTP-EQUIV="pragma" CONTENT="no-cache"/>
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"/>
	<META HTTP-EQUIV="expires" CONTENT="0"/>
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge"/>
	<%@ include file="/commons/jsp/common.jsp"%>
	
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
</head>

<body>
	<div class="body-box-form">
		<div class="panel panel-system" >
		    <div class="panel-body">
		      	<div class="btn-group-xs" >
					<button type="submit" class="btn btn-system " id="saveBtn">
						<span>保存</span>
					</button>
					<button type="button" class="btn btn-system " id="excelBtn" onclick="doExcelNow('回访客户_')">
						<span>导出Excel</span>
					</button>
					<button type="button" class="btn btn-system" id="closeBut">
						<span>关闭</span>
					</button>
				</div>
			</div>
		</div>
		<div class="panel panel-info" style="margin-bottom: 10px;">  
			<div class="panel-body">
				<form action="" method="post" id="page_form" class="form-search">
					<house:token></house:token>
					<input type="hidden" name="jsonString" value=""/>
					<ul class="ul-form">
						<li>
							<label>回访类型</label>
							<house:xtdm id="visitType" dictCode="VISITTYPE" style="width:160px;" value=""/>
						</li>
					</ul>	
				</form>
			</div>
		</div>
		<div class="container-fluid" >  
			<ul class="nav nav-tabs" >
			    <li class="active"><a href="#custVisit_tabView_customer" data-toggle="tab">回访客户</a></li>  
			</ul>
		    <div class="tab-content">  
				<div id="custVisit_tabView_customer"  class="tab-pane fade in active"> 
		         	<jsp:include page="custVisit_tabView_customer.jsp">
		         		<jsp:param value="" name=""/>
		         	</jsp:include>
				</div> 
			</div>	
		</div>
	</div>
</body>	
<script>
$(function() {
	/* 关闭按钮绑定数据是否更改校验 */
	$("#closeBut").on("click",function(){
		var ids = $("#dataTable").getDataIDs();
		if (ids.length != 0) {
			art.dialog({
				content: "数据已变动,是否保存？",
				width: 200,
				okValue: "确定",
				ok: function () {
					doSave();
				},
				cancelValue: "取消",
				cancel: function () {
					closeWin();
				}
			});
		} else {
			closeWin();
		}
	});
	
	/* 保存 */
	$("#saveBtn").on("click",function(){
		var ids = $("#dataTable").getDataIDs();
		if(!ids || ids.length == 0){
			art.dialog({content: "明细表中无数据",width: 200});
			return false;
		}
		doSave();
	});
	
	function doSave(){
		var param= Global.JqGrid.allToJson("dataTable");
		Global.Form.submit(
			"page_form",
			"${ctx}/admin/custVisit/doSave",
			param,
			function(ret){
				if(ret.rs==true){
					art.dialog({
						content: ret.msg,
						time: 1000,
						beforeunload: function () {
		    				closeWin();
					    }
					});
				}else{
					$("#_form_token_uniq_id").val(ret.token.token);
		    		art.dialog({
						content: ret.msg,
						width: 200
					});
				}
			});
	}
	
});

</script>
</html>
