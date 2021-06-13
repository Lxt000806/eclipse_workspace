<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<!DOCTYPE html>
<html>
<head>
<title>配送管理</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp"%>
<script src="${resourceRoot}/pub/component_driver.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
	function clearForm(){
			$("#page_form input[type='text']").val("");
			$("#status").val("");
		}	
	$(function(){
		Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/itemSendBatch/goJqGrid",
		height:$(document).height()-$("#content-list").offset().top-82,
		styleUI: 'Bootstrap', 
		colModel : [
			{name: "no", index: "no", width: 100, label: "批次号", sortable: true, align: "left"},
			{name: "appczy", index: "appczy", width: 75, label: "申请人员", sortable: true, align: "left",hidden:true},
			{name: "appczydesc", index: "appczydesc", width: 75, label: "申请人员", sortable: true, align: "left"},
			{name: "date", index: "date", width: 120, label: "生成日期", sortable: true, align: "left", formatter: formatTime},
			{name: "drivercode", index: "drivercode", width: 156, label: "司机", sortable: true, align: "left",hidden:true},
			{name: "drivername", index: "drivername", width: 50, label: "司机", sortable: true, align: "left"},
			{name: "followman", index: "followman", width: 75, label: "跟车员", sortable: true, align: "left",hidden:true},
			{name: "followmandescr", index: "followmandescr", width: 75, label: "跟车员", sortable: true, align: "left"},
			{name: "status", index: "status", width: 129, label: "状态", sortable: true, align: "left",hidden:true},
			{name: "statusdesc", index: "statusdesc", width: 70, label: "状态", sortable: true, align: "left"},
			{name: "remarks", index: "remarks", width: 156, label: "备注", sortable: true, align: "left"},
			{name: "lastupdate", index: "lastupdate", width: 120, label: "最后修改时间", sortable: true, align: "left", formatter: formatTime},
			{name: "lastupdatedby", index: "lastupdatedby", width: 70, label: "修改人", sortable: true, align: "left"},
			{name: "expired", index: "expired", width: 75, label: "是否过期", sortable: true, align: "left", hidden: true},
			{name: "actionlog", index: "actionlog", width: 50, label: "操作", sortable: true, align: "left"},
			
		],
	});
		$("#driverCode").openComponent_driver();
	});
	function doExcel(){
		var url = "${ctx}/admin/itemSendBatch/doExcel";
		doExcelAll(url);
	}
	//各按钮点击操作
	function go(btn,name){
		var postdata="";
		if(btn!='A'){
			var ret=selectDataTableRow();
			postdata={
				no:ret.no,date:ret.date,status:ret.status,appCZY:ret.appczy,
				driverCode:ret.drivercode,remarks:ret.remarks,m_umState:btn,
				driverCodeDescr:ret.drivername,appCZYDescr:ret.appczydesc,expired:ret.expired,
				followMan:ret.followman,followManDescr:ret.followmandescr
			};
			if(!ret){
				art.dialog({
	       			content: "请选择一条记录",
	       		});
	       		return;
			}
			if(btn=='M'){
				if(ret.status!='1'){
					art.dialog({
		       			content: "配送状态为"+ret.statusdesc+",不允许进行编辑操作！",
	       			});
	       			return;
				}
			}else if(btn=='R'){
				if(ret.status!='2'){
					art.dialog({
		       			content: "配送状态为"+ret.statusdesc+",不允许进行退回操作！",
	       			});
	       			return;
				}
			}
		}else{
			postdata={m_umState:btn};
		}
		Global.Dialog.showDialog("",{
					title:"配送管理——"+name,
					url:"${ctx}/admin/itemSendBatch/goAdd",
					postData:postdata,
				    height:700,
				    width:1300,
					returnFun:goto_query
				});
	}
	function detailManage(){
		Global.Dialog.showDialog("detailManage",{
						title:"明细管理",
						url:"${ctx}/admin/itemSendBatch/goDetailManage",
					    height:675,
					    width:1300,
						returnFun:goto_query
					});
	}
	function detailQuery(){
		Global.Dialog.showDialog("detailQuery",{
						title:"明细查询",
						url:"${ctx}/admin/itemSendBatch/goDetailQuery",
					    height:675,
					    width:1300,
						returnFun:goto_query
					});
	}
	//回车键搜索
	function keyQuery(){
		if (event.keyCode==13)  //回车键的键值为13
		$("#qr").click(); //调用登录按钮的登录事件
	}
	</script>
</head>
<body onkeydown="keyQuery()">
	<div class="body-box-list">
		<div class="query-form">
			<form role="form" class="form-search" id="page_form" action=""
				method="post" target="targetFrame">
				<input type="hidden" name="jsonString" value="" />
				<input type="hidden" id="expired" name="expired" value=""/>
				<ul class="ul-form">
					<li><label>批次号</label> <input type="text" id="no" name="no"
						value="${itemSendBatch.no}" />
					</li>
					<li><label>送货状态</label> <house:xtdm id="status"
							dictCode="SENDBATCHSTATUS" value="${itemSendBatch.status}"></house:xtdm>
					</li>
					<li><label>司机编号</label> <input type="text" id="driverCode"
						name="driverCode" style="width:160px;" value="${itemSendBatch.driverCode }" />
					</li>
					<li><label>生成日期从</label> <input type="text" id="dateFrom"
						name="dateFrom" class="i-date" style="width:160px;"
						onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
					</li>

					<li><label>至</label> <input type="text" id="dateTo"
						name="dateTo" class="i-date" style="width:160px;"
						onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
					</li>
						<li class="search-group"><input type="checkbox" id="expired_show"
						name="expired_show" value="${itemSendBatch.expired}"
						onclick="hideExpired(this)" ${itemSendBatch.expired!='T' ?'checked':'' }/>
						<p>隐藏过期</p>
						<button type="button" id="qr" class="btn  btn-sm btn-system "
							onclick="goto_query();">查询</button>
						<button type="button" class="btn btn-sm btn-system "
							onclick="clearForm();">清空</button></li>
				</ul>
			</form>
		</div>
	</div>
	<div class="clear_float"></div>
	<div class="btn-panel">
		<div class="btn-group-sm">
			<house:authorize authCode="ITEMSENDBATCH_ADD">
				<button type="button" class="btn btn-system" onclick="go('A','新增')">
					<span>新增</span>
				</button>
			</house:authorize>
			<house:authorize authCode="ITEMSENDBATCH_UPDATE">
				<button type="button" class="btn btn-system" onclick="go('M','编辑')">
					<span>编辑</span>
				</button>
			</house:authorize>
			<house:authorize authCode="ITEMSENDBATCH_SENDFEE">
				<button type="button" class="btn btn-system" onclick="go('F','配送费录入')">
					<span>配送费录入</span>
				</button>
			</house:authorize>
			<house:authorize authCode="ITEMSENDBATCH_RETURN">
				<button type="button" class="btn btn-system" onclick="go('R','退回')">
					<span>退回</span>
				</button>
			</house:authorize>
			<house:authorize authCode="ITEMSENDBATCH_VIEW">
				<button type="button" class="btn btn-system" onclick="go('V','查看')" >
					<span>查看</span>
				</button>
			</house:authorize>
			<house:authorize authCode="ITEMSENDBATCH_DETAILMANAGE">
				<button type="button" class="btn btn-system" onclick="detailManage()">
					<span>明细管理</span>
				</button>
			</house:authorize>
				<button type="button" class="btn btn-system" onclick="detailQuery()">
					<span>明细查询</span>
				</button>
				<button type="button" class="btn btn-system" onclick="doExcel()">
					<span>导出excel</span>
				</button>
		</div>
	</div>
	<div id="content-list">
		<table id="dataTable"></table>
		<div id="dataTablePager"></div>
	</div>
</body>
</html>
