<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://" + request.getServerName() + ":"+request.getServerPort()+path+"/";
String jasperPath = PathUtil.WEB_INF+"jsp/jasper/";
%>
<!DOCTYPE html>
<html lang="zh">
<head>
	<title>售中安排</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp"%>
	<style type="text/css">
		.body-box-form .panel.panel-info {
			margin-bottom: 10px;
		}
		.intReplenish-remarks {
			height: 70px;
			width: 310px !important;
		}
	</style>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
</head>
<body>
	<form action="" method="post" id="page_form" class="form-search">
		<div class="body-box-form">
			<div class="panel panel-system">
				<div class="panel-body">
					<div class="btn-group-xs">
						<button type="button" class="btn btn-system" id="closeBut" onclick="closeWin(false)">
							<span>关闭</span>
						</button>
					</div>
				</div>
			</div>
			<div class="panel panel-info">
				<div class="panel-body">
					<house:token></house:token><!-- 按钮只能点一次 -->
					<input type="hidden" name="jsonString" value="" />
					<input type="hidden" id="m_umState" name="m_umState" value="${intReplenish.m_umState}"/>
					<input type="hidden" id="lastUpdatedBy" name="lastUpdatedBy" value="${sessionScope.USER_CONTEXT_KEY.czybh}"/>
					<input type="hidden" id="status" name="status" value="${intReplenish.status}"/>
					<input type="hidden" id="custCode" name="custCode" value="${intReplenish.custCode}"/>
					<ul class="ul-form">
						<div class="validate-group row">
							<li>
								<label>单号</label>
								<input type="text" id="no" name="no" style="width:160px;"
									placeholder="保存自动生成" readonly value="${intReplenish.no}" />
							</li>
							<li id="status_li">
								<label>状态</label>
								<house:xtdm id="status" dictCode="IntRepStatus" style="width:160px;" 
									value="${intReplenish.status}"/>
							</li>
							<li>
								<label for="address">楼盘</label>
								<input type="text" id="address" name="address" 
									value="${intReplenish.address}" readonly="true">
							</li>
							<li class="form-validate">
								<label for="isCupboard">是否橱柜</label>
								<house:xtdm id="isCupboard" dictCode="YESNO" style="width:160px;" 
									value="${intReplenish.isCupboard}"/>
							</li>
							<li class="form-validate">
								<label for="source">补货来源</label>
								<house:xtdm id="source" dictCode="IntRepSource" style="width:160px;"
									value="${intReplenish.source}"/>
							</li>
							<li class="form-validate">
								<label>货好时间</label> 
								<input type="text" id="boardOKDate" name="boardOKDate" class="i-date"
									onclick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd',readOnly:true})" 
									value="<fmt:formatDate value='${intReplenish.boardOKDate}' pattern='yyyy-MM-dd'/>"
									readonly="true"/>
							</li>
							<li class="form-validate">
								<label>货齐时间</label> 
								<input type="text" id="readyDate" name="readyDate" class="i-date"
									onclick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd',readOnly:true})" 
									value="<fmt:formatDate value='${intReplenish.readyDate}' pattern='yyyy-MM-dd'/>"
									readonly="true"/>
							</li>
							<li class="form-validate">
								<label>售中时间</label> 
								<input type="text" id="serviceDate" name="serviceDate" class="i-date"
									onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd',readOnly:true})" 
									value="<fmt:formatDate value='${intReplenish.serviceDate}' pattern='yyyy-MM-dd'/>"/>
							</li>
							<li class="form-validate">
								<label for="serviceMan">售中人员</label>
								<input type="text" id="serviceMan" name="serviceMan">
							</li>
							<li class="form-validate">
								<label for="resPart">责任人</label>
								<house:xtdm id="resPart" dictCode="IntRepResPart" style="width:160px;"
									value="${intReplenish.resPart}"/>
							</li>
							<li class="form-validate">
								<label>完成时间</label> 
								<input type="text" id="finishDate" name="finishDate" class="i-date"
									onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd',readOnly:true})" 
									value="<fmt:formatDate value='${intReplenish.finishDate}' pattern='yyyy-MM-dd'/>"/>
							</li>
						</div>
						<div class="row">
							<li class="form-validate" style="max-height: 120px;">
								<label class="control-textarea">
									说明
								</label>
								<textarea class="intReplenish-remarks" id="remarks" 
									name="remarks">${intReplenish.remarks}</textarea>
							</li>
							<li class="form-validate" style="max-height: 120px;">
								<label class="control-textarea">
									货好备注
								</label>
								<textarea class="intReplenish-remarks" id="boardOKRemarks" 
									name="boardOKRemarks">${intReplenish.boardOKRemarks}</textarea>
							</li>
						</div>
					</ul>
				</div>
			</div>
			<div class="container-fluid" id="id_detail">
				<ul class="nav nav-tabs">
					<li class="active"><a href="#tab_info" data-toggle="tab">明细</a>
					</li>
				</ul>
				<div class="tab-content">
					<div id="tab_info" class="tab-pane fade in active">
						<div class="btn-panel">
							<div class="btn-group-sm">
								<button type="button" class="btn btn-system " id="downloaCAD">下载CAD文件</button>
							</div>
						</div>
						<div id="content-list">
								<table id="dataTable"></table>
						</div>
					</div>
				</div>
			</div>
		</div>
	</form>
	<script type="text/javascript">
		var originalData, originalDataTable, m_umState="${intReplenish.m_umState}";
		$(function(){
			$("#serviceMan").val("${intReplenish.serviceMan}"==""?"":"${intReplenish.serviceMan}|${intReplenish.serviceManDescr}");
			var postData = $("#page_form").jsonForm();
			Global.JqGrid.initJqGrid("dataTable", {
				url : "${ctx}/admin/intReplenishDT/goJqGridByNo",
				postData : {no: postData.no},
				height : $(document).height()-$("#content-list").offset().top-40,
				styleUI : 'Bootstrap',
				multiselect : true,
				rowNum : -1,
				loadonce: true,
				colModel : [
					{name:"pk", index:"pk", width:80, label:"PK", sortable:true, align:"left", hidden: true},
					{name:"no", index:"no", width:80, label:"补货单号", sortable:true, align:"left"},
					{name:"intspl", index:"intspl", width:80, label:"供应商", sortable:true, align:"left", hidden: true},
					{name:"intspldescr", index:"intspldescr", width:80, label:"供应商", sortable:true, align:"left"},
					{name:"type", index:"type", width:80, label:"补货类型", sortable:true, align:"left", hidden: true},
					{name:"typedescr", index:"typedescr", width:80, label:"补货类型", sortable:true, align:"left"},
					{name:"date", index:"date", width:100, label:"补货时间", sortable:true, align:"left", formatter:formatDate},
					{name:"remarks", index:"remarks", width:160, label:"补件详情", sortable:true, align:"left"},
					{name:"arrivedate", index:"arrivedate", width:100, label:"实际到货日期", sortable:true, align:"left", formatter:formatDate},
					{name:"prearrivdate", index:"prearrivdate", width:100, label:"预计到货日期", sortable:true, align:"left", formatter:formatDate},
					{name:"docdescr", index:"docdescr", width:100, label:"CAD文件名称", sortable:true, align:"left"},
					{name:"docname", index:"docname", width:80, label:"存放地址", sortable:true, align:"left"},
					{name:"arriveremarks", index:"arriveremarks", width:160, label:"到货备注", sortable:true, align:"left"},
					{name:"respartdescr", index:"respartdescr", width:80, label:"责任人", sortable:true, align:"left"},
					{name:"lastupdate", index:"lastupdate", width:120, label:"最后更新时间", sortable:true, align:"left", formatter:formatTime, hidden: true},
					{name:"lastupdatedby", index:"lastupdatedby", width:60, label:"修改人", sortable:true, align:"left", hidden: true},
					{name:"expired", index:"expired", width:70, label:"过期标志", sortable:true, align:"left", hidden: true},
					{name:"actionlog", index:"actionlog", width:60, label:"操作", sortable:true, align:"left", hidden: true}
				],
				loadComplete: function(){
					originalDataTable = Global.JqGrid.allToJson("dataTable").detailJson;
				}
			});
			$("#dataTable")
				.hideCol("no").hideCol("docname")
				.trigger("reloadGrid");
			originalData = $("#page_form").serialize();
			disabledForm();
			$("#closeBut").on("click",function(){
				doClose();
			});
			replaceCloseEvt("intReplenishView", doClose);
			
			$("#downloaCAD").on("click", function () {
				
				var custCode = $("#custCode").val()
				
				var grid = $("#dataTable")
				var ids = grid.jqGrid('getGridParam', 'selarrrow')
				var docNames = []
				var docDescrs = []
				
				for (var i = 0; i < ids.length; i++) {
				    var rowData = grid.getRowData(ids[i])
				    if (rowData.docname) {
				        docNames.push(rowData.docname)
				        docDescrs.push(encodeURIComponent(rowData.docdescr))
				    }
				}
				
				if (!docNames.length) {
				    art.dialog({content: "没有要下载的文档", width: 260})
				    return
				}
				
				window.open("${ctx}/admin/intReplenish/downloadNew" 
				    + "?custCode=" + custCode
				    + "&docNames=" + docNames.join(",")
				    + "&docDescrs=" + docDescrs.join(","))
			})
			
		});
		function doClose() {
			closeWin(false);
		}
	</script>
</body>
</html>
