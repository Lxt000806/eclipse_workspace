<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<title>工人质保金管理</title>
	<%@ include file="/commons/jsp/common.jsp" %>
    <script src="${resourceRoot}/pub/component_worker.js?v=${v}" type="text/javascript"></script>
<style type="text/css">

</style>
<script type="text/javascript">
	//导出EXCEL
	function doExcel(){
		var url = "${ctx}/admin/workQltFee/doExcel";
		doExcelAll(url);
	}
   function goUpdate() {
	     var ret = selectDataTableRow();
	     if(ret){
		      Global.Dialog.showDialog("workQltFeeUpdate", {
		        title: "工人质保金管理--存取",
		        url: "${ctx}/admin/workQltFee/goUpdate",
		        postData: {
		              'workerCode': ret.code,'nameChi': ret.namechi,'descr': ret.descr,
		              'qualityFee': ret.qualityfee,'accidentFee': ret.accidentfee
		            },
		         height: 300,
		         width: 780,
		         returnFun: goto_query
		        });
	      }else{
		        art.dialog({
		          content: "请先查询出记录"
		        });
	      }
    }
    function goDetailQuery() {
	     var ret = selectDataTableRow();
	     if(ret){
		      Global.Dialog.showDialog("workQltFeeDetailQuery", {
		        title: "工人质保金管理--变动明细",
		        url: "${ctx}/admin/workQltFee/goDetailQuery",
		        postData: {
		              'code': ret.code,'nameChi': ret.namechi,'descr': ret.descr,'qualityFee': ret.qualityfee
		            },
		         height: 600,
		         width: 1100,
		         returnFun: goto_query
		        });
	      }else{
		        art.dialog({
		          content: "请先查询出记录"
		        });
	      }
    }
/**初始化表格*/
$(function(){
	$("#code").openComponent_worker();
	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/workQltFee/goJqGrid",
		height:$(document).height()-$("#content-list").offset().top-82,
		styleUI: 'Bootstrap', 
		colModel : [
				{name: "code", index: "code", width: 70, label: "工人编号", sortable: true, align: "left"},
				{name: "namechi", index: "namechi", width: 60, label: "姓名", sortable: true, align: "left"},
				{name: "descr", index: "descr", width: 60, label: "工种", sortable: true, align: "left"},
				{name: "qualityfee", index: "qualityfee", width: 80, label: "质保金余额", sortable: true, align: "right"},
				{name: "accidentfee", index: "accidentfee", width: 80, label: "意外险余额", sortable: true, align: "right"},
				{name: "worktype12", index: "worktype12", width: 110, label: "工种类型12", sortable: true, align: "left",hidden:true}	
		],
	});

	$("#clear").on("click", function() {
		$("#workType12").val("");
		$.fn.zTree.getZTreeObj("tree_workType12").checkAllNodes(false);
	});
});
</script>
</head>
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form action="" method="post" id="page_form" class="form-search">
			<input
					type="hidden" name="jsonString" value="" />
				<ul class="ul-form">
					<li><label>工人</label> <input type="text" id="code"
						name="code" style="width:160px;" value="${worker.code}"/>
					</li>
					<li>
						<label>工人姓名</label> 
						<input type="text" id="nameChi" name="nameChi" style="width:160px;"/>
					</li>
					<li>
						<label>工种分类</label>
						<house:DictMulitSelect id="workType12" dictCode="" 
							sql="select rtrim(Code) Code,Descr cd from tWorkType12 where Expired = 'F' order by DispSeq" 
							sqlLableKey="cd" sqlValueKey="Code">
						</house:DictMulitSelect>
					</li>
					<li class="search-group-shrink">
						<input type="checkbox"
							id="expired" name="expired"
							value="${worker.expired}" onclick="hideExpired(this)"
							${worker.expired!='T' ?'checked':'' }/>
							<p>隐藏过期</p>
							<button type="button" class="btn  btn-sm btn-system "
								onclick="goto_query();">查询</button>
							<button type="button" id="clear" class="btn btn-sm btn-system "
								onclick="clearForm();">清空</button>
					</li>
				</ul>
			</form>
		</div>
		<div class="btn-panel">
		<div class="btn-group-sm">
			<house:authorize authCode="WORKQLTFEE_UPDATE">
				<button type="button" class="btn btn-system" onclick="goUpdate()">
					<span>存取</span>
				</button>
			</house:authorize>
			<house:authorize authCode="WORKQLTFEE_TRANDETAIL">
				<button type="button" class="btn btn-system" onclick="goDetailQuery()">
					<span>变动明细</span>
				</button>
			</house:authorize>
			<house:authorize authCode="WORKQLTFEE_EXCEL">
				<button type="button" class="btn btn-system" onclick="doExcel()">
					<span>导出excel</span>
				</button>
			</house:authorize>
		</div>
	</div>
			<div id="content-list">
				<table id="dataTable"></table>
				<div id="dataTablePager"></div>
			</div>
	</div>
</body>
</html>


