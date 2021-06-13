<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://" + request.getServerName() + ":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="zh">
<head>
	<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=utf-8"/>
	<META HTTP-EQUIV="pragma" CONTENT="no-cache"/>
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"/>
	<META HTTP-EQUIV="expires" CONTENT="0"/>
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge"/>
	<title>集成补货信息管理——明细列表</title>
	<%@ include file="/commons/jsp/common.jsp"%>
	<!-- 修改bootstrap中各个row的边距 -->
	<style>
		.row{
			margin: 0px;
		}
		.col-sm-3{
			padding: 0px;
		}
		.col-sm-6{
			padding: 0px;
		}
		.col-sm-9{
			padding: 0px;
		}
		.col-sm-12{
			padding: 0px;
		}
	</style>
	<script src="${resourceRoot}/pub/component_supplier.js?v=${v}" type="text/javascript"></script>
</head>
<body>
	<div class="body-box-form">
		<div class="panel panel-system" >
		    <div class="panel-body">
		      	<div class="btn-group-xs" >
					<button type="button" class="btn btn-system " id="excelBtn" onclick="doExcelNow('集成补货信息明细_')">
						<span>导出Excel</span>
					</button>
					<button type="button" class="btn btn-system" id="closeBut" onclick="closeWin(false)">
						<span>关闭</span>
					</button>
				</div>
			</div>
		</div>
		<div class="body-box-list">  
			<div class="query-form">
				<form action="" method="post" id="page_form" class="form-search">
					<house:token></house:token>
					<input type="hidden" id="expired" name="expired"/>
					<input type="hidden" name="jsonString" value=""/>
					<ul class="ul-form">
						<div class="row">
							<div class="col-sm-9">
								<li>
									<label>楼盘</label>
									<input type="text" id="address" name="address" style="width: 160px;"/>
								</li>
								<li class="form-validate">
									<label for="isCupboard">是否橱柜</label>
									<house:xtdm id="isCupboard" dictCode="YESNO" style="width:160px;"/>
								</li>
								<li class="form-validate">
									<label for="source">补货来源</label>
									<house:xtdm id="source" dictCode="IntRepSource" style="width:160px;"
										/>
								</li>
								<li>
									<label>完成时间从</label>
									<input type="text" id="finishDateFrom" name="finishDateFrom"
										class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
										/>
								</li>
								<li>
									<label>至</label>
									<input type="text" id="finishDateTo" name="finishDateTo"
										class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
										/>
								</li>
								<li>
									<label>供应商</label>
									<input type="text" id="intSpl" name="intSpl" style="width:160px;" value=""/>
								</li>
								<li class="form-validate">
									<label for="type">补货类型</label>
									<house:xtdm id="type" dictCode="IntRepType" style="width:160px;"
										/>
								</li>
								<li>
									<label>补货时间从</label>
									<input type="text" id="dateFrom" name="dateFrom"
										class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
										/>
								</li>
								<li>
									<label>至</label>
									<input type="text" id="dateTo" name="dateTo"
										class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
										/>
								</li>
								<li>
									<label>到货时间从</label>
									<input type="text" id="arriveDateFrom" name="arriveDateFrom"
										class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
										/>
								</li>
								<li>
									<label>至</label>
									<input type="text" id="arriveDateTo" name="arriveDateTo"
										class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
										/>
								</li>
								<li id="status_li">
									<label>状态</label>
									<house:xtdmMulit id="status" dictCode="IntRepStatus"/>
								</li>
								<li>
									<label>货好时间从</label>
									<input type="text" id="okDateFrom" name="okDateFrom"
										class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
										/>
								</li>
								<li>
									<label>至</label>
									<input type="text" id="okDateTo" name="okDateTo"
										class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
										/>
								</li>
								<li>
									<label>责任人</label>
									<house:xtdm id="resPart" dictCode="IntRepResPart"></house:xtdm>
								</li>	
								<li>
									<label>承担方</label>
									<house:xtdm id="undertaker" dictCode="IntRepResPart"></house:xtdm>
								</li>	
							</div>
							<div class="col-sm-3">
								<li>
									<button type="button" class="btn btn-system btn-sm" onclick="goto_query();">查询</button>
									<button id="clear" type="button" class="btn btn-system btn-sm" onclick="clearForm();">清空</button>
								</li>
							</div>
						</div>
					</ul>	
				</form>
			</div>
		</div>
		<div class="container-fluid" > 
			<div class="pageContent">
				<div id="content-list">
					<table id="dataTable"></table>
					<div id="dataTablePager"></div>
				</div>
			</div> 
		</div>
	</div>
	<script type="text/javascript">
		var postData = $("#page_form").jsonForm();
		$(function(){
			$("#intSpl").openComponent_supplier({condition: {itemType1:"JC",isDisabled:"1"}});
			Global.JqGrid.initJqGrid("dataTable", {
				sortable: true,/* 列重排 */
				url: "${ctx}/admin/intReplenishDT/goJqGridByNo",
				postData:postData ,
				height: $(document).height() - $("#content-list").offset().top - 70,
				styleUI: "Bootstrap", 
				colModel: [
					{name:"no",index:"no",width:80,label:"单号",sortable:true,align:"left"},
					{name:"address",index:"address",width:140,label:"楼盘",sortable:true,align:"left"},
					{name:"intspl", index:"intspl", width:80, label:"供应商", sortable:true, align:"left", hidden: true},
					{name:"intspldescr", index:"intspldescr", width:80, label:"供应商", sortable:true, align:"left"},
					{name:"iano",index:"iano",width:80,label:"领料单号",sortable:true,align:"left"},
					{name:"itemappdate",index:"itemappdate",width:100,label:"领料申请日期",sortable:true,align:"left",formatter:formatDate},
					{name:"itemappconfirmdate",index:"itemappconfirmdate",width:100,label:"领料审核日期",sortable:true,align:"left",formatter:formatDate},
					{name:"okdate",index:"okdate",width:100,label:"货好时间",sortable:true,align:"left",formatter:formatDate},
					{name:"source",index:"source",width:80,label:"补货来源",sortable:true,align:"left", hidden:true},
					{name:"sourcedescr",index:"sourcedescr",width:80,label:"补货来源",sortable:true,align:"left"},
					{name:"date", index:"date", width:100, label:"补货时间", sortable:true, align:"left", formatter:formatDate},
					{name:"iscupboard",index:"iscupboard",width:80,label:"是否橱柜",sortable:true,align:"left", hidden:true},
					{name:"iscupboarddescr",index:"iscupboarddescr",width:80,label:"是否橱柜",sortable:true,align:"left"},
					{name:"status",index:"status",width:60,label:"状态",sortable:true,align:"left", hidden:true},
					{name:"statusdescr",index:"statusdescr",width:60,label:"状态",sortable:true,align:"left"},
					{name:"arrivedate", index:"arrivedate", width:100, label:"实际到货日期", sortable:true, align:"left", formatter:formatDate},
					{name:"readydate",index:"readydate",width:100,label:"货齐时间",sortable:true,align:"left",formatter:formatDate},
					{name:"servicedate",index:"servicedate",width:100,label:"售中时间",sortable:true,align:"left",formatter:formatDate},
					{name:"finishdate",index:"finishdate",width:100,label:"完成时间",sortable:true,align:"left",formatter:formatDate},
					{name:"type", index:"type", width:80, label:"补货类型", sortable:true, align:"left", hidden: true},
					{name:"typedescr", index:"typedescr", width:80, label:"补货类型", sortable:true, align:"left"},
					{name:"remarks", index:"remarks", width:160, label:"补件详情", sortable:true, align:"left"},
					{name:"arriveremarks", index:"arriveremarks", width:160, label:"到货备注", sortable:true, align:"left"},
					{name:"respartdescr", index:"respartdescr", width:80, label:"责任人", sortable:true, align:"left"},
					{name:"undertakerdescr", index:"undertakerdescr", width:80, label:"承担方", sortable:true, align:"left"},
				]
			});
			/* 清空下拉选择树checked状态 */
			$("#clear").on("click",function(){
				// zTreeCheckFalse("#department2");
				// zTreeCheckFalse("#visitType");
			});
		});
		// 清空下拉选择树选项
		function zTreeCheckFalse(id) {
			$("#"+id).val("");
			$.fn.zTree.getZTreeObj("tree_"+id).checkAllNodes(false);
		}
		
		function clearForm() {
		    $("#page_form input[type='text']").val('');
		    $("#page_form select").val('');
		    
            $("#status").val("");
            $.fn.zTree.getZTreeObj("tree_status").checkAllNodes(false);
		}
	</script>
</body>
</html>
