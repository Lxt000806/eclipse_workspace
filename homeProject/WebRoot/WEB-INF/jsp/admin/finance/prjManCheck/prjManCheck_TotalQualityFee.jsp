<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<title>质保金汇总</title>
<%@ include file="/commons/jsp/common.jsp"%>
<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
$(function(){
	//初始化表格
	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/prjManCheck/goJqGrid_totalQualityFee",
		height:$(document).height()-$("#content-list").offset().top-100,
		styleUI: 'Bootstrap',
		colModel : [
		    {name: "projectmandescr", index: "projectmandescr", width: 136, label: "项目经理姓名", sortable: true, align: "left", count: true},
			{name: "projectman", index: "projectman", width: 136, label: "项目经理", sortable: true, align: "left", hidden:true },
			{name: "qualityfee", index: "qualityfee", width: 100, label: "质保金", sortable: true, align: "right", sum: true},
			{name: "accidentfee", index: "accidentfee", width: 100, label: "意外险", sortable: true, align: "right", sum: true}
        ]                        
	});	
    $("#projectMan").openComponent_employee();
			 
});  
function clearForm(){
	$("#page_form input[type='text']").val('');
}
//导出EXCEL
function doExcel(){
 	var url = "${ctx}/admin/prjManCheck/doExcel_totalQualityFee";
 	doExcelAll(url);

}
function qualityDetailView(){
	var ret = selectDataTableRow();
	    if (ret) {    	
	    	Global.Dialog.showDialog("goViewQualityDetail",{
				title:"质保金明细",
				url:"${ctx}/admin/prjManCheck/goViewQualityDetail",
				postData:{projectMan:ret.projectman},			
				height:600,
				width:800,
			});
	    } else {
	    	art.dialog({
				content: "请选择一条记录"
			});
	    }
}
</script>
</head>
<body>
	<div class="body-box-form">
		<div class="panel panel-system">
			<div class="panel-body">
				<div class="btn-group-xs">
					<button type="button" class="btn btn-system" onclick="qualityDetailView()">查看</button>
					<button type="button" class="btn btn-system" onclick="doExcel()">输出至excel</button>
					<button type="button" class="btn btn-system" onclick="closeWin(false)">关闭</button>
				</div>
			</div>
		</div>
		<div class="panel panel-info">
			<div class="panel-body">
				<form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
					<input type="hidden" name="jsonString" value="" />
					<input type="hidden" id="expired" name="expired" value="${prjCheck.expired}" />
					<ul class="ul-form">
						<li><label>项目经理</label> <input type="text" id="projectMan" name="projectMan" />
						</li>
						<li class="search-group">					
								<input type="checkbox" id="expired_show" name="expired_show"
									value="${prjCheck.expired}" onclick="hideExpired(this)"
									${whCheckOut.expired!='T' ?'checked':'' }/><p>隐藏过期</p> 
								<button type="button" class="btn  btn-sm btn-system " onclick="goto_query();"  >查询</button>
								<button type="button" class="btn btn-sm btn-system " onclick="clearForm();"  >清空</button>
						</li>	
					</ul>
			</form>
				</form>
			</div>
		</div>
		<div class="clear_float"></div>
		<!--query-form-->
		<div class="pageContent">
			<!--panelBar-->
			<div id="content-list">
				<table id="dataTable"></table>
				<div id="dataTablePager"></div>
			</div>
		</div>
	</div>
</body>
</html>


