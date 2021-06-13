<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<!DOCTYPE HTML>
<html>
<head>
    <title>防水施工面积确认</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script type="text/javascript">
	function doExcel(){
		var url = "${ctx}/admin/waterAreaCfm/doExcel";
		doExcelAll(url);
	}
	function go(m_umState,content){
		var ret =selectDataTableRow();
		if(!ret){
			art.dialog({
				content:"请选择一条数据",
			});
			return;
		}
		if(m_umState=="goConfirm" && ret.status!="1"){
			art.dialog({
				content:"只有待确认状态可进行确认操作！",
			});
			return;
		}
		Global.Dialog.showDialog("view",{
			title:"防水施工面积确认--"+content,
			postData:{map:JSON.stringify(ret),m_umState:m_umState},
			url:"${ctx}/admin/waterAreaCfm/"+m_umState,
			height: 350,
		 	width:800,
			returnFun: goto_query 
		});
	}
	//清空
	function clearForm(){
		$("#page_form input[type='text']").val("");
		$("select").val("");
	} 
	/**初始化表格*/
	$(function(){

		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/waterAreaCfm/goJqGrid",
			height:$(document).height()-$("#content-list").offset().top-70,
			styleUI: "Bootstrap", 
			postData:{status:"1"},
			colModel : [
				{name: "custcode", index: "custcode", width: 80, label: "客户编号", sortable: true, align: "left"},
				{name: "custdescr", index: "custdescr", width: 80, label: "客户名称", sortable: true, align: "left"},
				{name: "address", index: "address", width: 150, label: "楼盘", sortable: true, align: "left"},
				{name: "custtypedescr", index: "custtypedescr", width: 80, label: "客户类型", sortable: true, align: "left"},
				{name: "custtype", index: "custtype", width: 120, label: "客户类型", sortable: true, align: "left",hidden:true},
				{name: "waterctrlarea",	index:"waterctrlarea",	width:90,	label:"防水施工面积",	sortable:true,align:"right" ,},
				{name: "waterarea", index: "waterarea", width: 90, label: "工人申报面积", sortable: true, align: "right"},
				{name: "workername", index: "workername", width: 80, label: "工人", sortable: true, align: "left"},
				{name: "workercode", index: "workercode", width: 80, label: "工人", sortable: true, align: "left",hidden:true},
				{name: "appdate", index: "appdate", width: 120, label: "提报时间", sortable: true, align: "left", formatter: formatTime},
				{name: "statusdescr", index: "statusdescr", width: 70, label: "状态", sortable: true, align: "left"},
				{name: "status", index: "status", width: 80, label: "状态", sortable: true, align: "left",hidden:true},
				{name: "confirmczydescr", index: "confirmczydescr", width: 70, label: "确认人", sortable: true, align: "left"},
				{name: "confirmdate", index: "confirmdate", width: 120, label: "确认时间", sortable: true, align: "left", formatter: formatTime},
				{name: "confirmremarks", index: "confirmremarks", width: 120, label: "确认备注", sortable: true, align: "left"},
				{name: "lastupdate", index: "lastupdate", width: 120, label: "最后更新时间", sortable: true, align: "left", formatter: formatTime},
				{name: "lastupdatedby", index: "lastupdatedby", width: 101, label: "最后更新人员", sortable: true, align: "left"},
				{name: "expired", index: "expired", width: 74, label: "是否过期", sortable: true, align: "left"},
				{name: "actionlog", index: "actionlog", width: 76, label: "操作日志", sortable: true, align: "left"}
			],
			ondblClickRow: function(){
				go('goView','查看');
			}
		});
	});
	</script>
</head>
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form role="form" class="form-search" id="page_form" action=""
				method="post" target="targetFrame">
				<input type="hidden" name="jsonString" value="" />
				<input type="hidden" id="expired" name="expired" value="" /> 
				<ul class="ul-form">
					<li><label>楼盘地址</label> <input type="text" id="address"
						name="address" />
					</li>
					<li><label>状态</label> <house:xtdm id="status"
							dictCode="WTAREACFMSTAT" value="1"></house:xtdm>
					</li>
					<li class="search-group"><input type="checkbox" id="expired_show"
						name="expired_show" value="${waterAreaCfm.expired}"
						onclick="hideExpired(this)" ${waterAreaCfm.expired!='T' ?'checked':'' }/>
						<p>隐藏过期</p>
						<button type="button" class="btn  btn-sm btn-system "
							onclick="goto_query();">查询</button>
						<button type="button" class="btn btn-sm btn-system "
							onclick="clearForm();">清空</button>
					</li>
				</ul>
			</form>
		</div>
		<div class="pageContent">
			<div class="btn-panel">
				<div class="btn-group-sm">
					<house:authorize authCode="WATERAREACFM_VIEW">
						<button type="button" class="btn btn-system" onclick="go('goView','查看')">
							<span>查看</span>
						</button>
					</house:authorize>
					<house:authorize authCode="WATERAREACFM_CONFIRM">
						<button type="button" class="btn btn-system" onclick="go('goConfirm','确认')">
							<span>确认</span>
						</button>
					</house:authorize>
					<button type="button" class="btn btn-system "  onclick="doExcel()" title="导出检索条件数据">
						<span>导出excel</span>
					</button>
				</div>
			</div>
			<div id="content-list">
				<table id="dataTable"></table>
				<div id="dataTablePager"></div>
			</div>
		</div>
	</div>
</body>
</html>
