<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://" + request.getServerName() + ":"+request.getServerPort()+path+"/";
String jasperPath = PathUtil.WEB_INF+"jsp/jasper/";
%>
<!DOCTYPE html>
<html>
<head>
<title>材料批次管理</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp"%>
<script src="${resourceRoot}/pub/component_employee.js?v=${v}"
	type="text/javascript"></script>
<script type="text/javascript">
	function clearForm(){
			$("#page_form input[type='text']").val("");
			$("#itemType1").val("");
			$("#batchType").val("");
		}	
	$(function(){
	        //初始化材料类型1，2，3三级联动
	    Global.LinkSelect.initSelect("${ctx}/admin/item/itemTypeByAuthority","itemType1");	
		Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/itemBatchHeader/goJqGrid",
		height:$(document).height()-$("#content-list").offset().top-82,
		styleUI: 'Bootstrap', 
		colModel : [
				{name: "no", index: "no", width: 122, label: "批次编号", sortable: true, align: "left"},
				{name: "remarks", index: "remarks", width: 135, label: "名称", sortable: true, align: "left"},
				{name: "date", index: "date", width: 134, label: "创建日期", sortable: true, align: "left", formatter: formatTime},
				{name: "itemtype1descr", index: "itemtype1descr", width: 75, label: "材料类型1", sortable: true, align: "left"},
				{name: "batchtypedescr", index: "batchtypedescr", width: 70, label: "批次类型", sortable: true, align: "left"},
				{name: "batchtype", index: "batchtype", width: 70, label: "批次类型", sortable: true, align: "left"},
				{name: "custtypedescr", index: "custtypedescr", width: 80, label: "客户类型", sortable: true, align: "left"},
				{name: "crtczydescr", index: "crtczydescr", width: 70, label: "创建人员", sortable: true, align: "left"},
				{name: "dispseq", index: "dispseq", width: 84, label: "显示顺序", sortable: true, align: "right"},
				{name: "worktype12", index: "worktype12", width: 84, label: "适用工种", sortable: true, align: "right",hidden:true},
				{name: "worktype12descr", index: "worktype12descr", width: 84, label: "适用工种", sortable: true, align: "left"},
				{name: "statusdescr", index: "statusdescr", width: 84, label: "状态", sortable: true, align: "left"},
				{name: "address", index: "address", width: 84, label: "楼盘", sortable: true, align: "left"},
				{name: "otherremarks", index: "otherremarks", width: 84, label: "备注", sortable: true, align: "left"},
				{name: "lastupdate", index: "lastupdate", width: 133, label: "最后修改时间", sortable: true, align: "left", formatter: formatTime},
				{name: "lastupdatedby", index: "lastupdatedby", width: 75, label: "修改人", sortable: true, align: "left"},
				{name: "expired", index: "expired", width: 75, label: "是否过期", sortable: true, align: "left"},
				{name: "actionlog", index: "actionlog", width: 60, label: "操作", sortable: true, align: "left"}
		],
	});
		//新增
		$("#add").on("click",function(){
				Global.Dialog.showDialog("goSave",{
					title:"材料批次--增加",
					url:"${ctx}/admin/itemBatchHeader/goSave",
				    height:700,
				    width:1250,
					returnFun:goto_query
				});
		});
		//编辑
		$("#update").on("click",function(){
				var ret = selectDataTableRow();
				if(ret.batchtype=="1"){
					art.dialog({content: "不能对下单批次进行修改！",width: 200});
					return false;
				}
				if(!ret){
					art.dialog({
								content:"请选择一条记录！",
								width: 200
							});
					return;
				}
				Global.Dialog.showDialog("goUpdate",{
					title:"材料批次--编辑",
					url:"${ctx}/admin/itemBatchHeader/goUpdate?id="+ret.no+"&crtCzyDescr="+ret.crtczydescr,
				    height:700,
				    width:1250,
					returnFun:goto_query
				});
		});
		//撤回
		$("#back").on("click",function(){
				var ret = selectDataTableRow();
				if(!ret){
					art.dialog({
						content:"请选择一条记录！",
						width: 200
					});
					return;
				}
				$.ajax({
					url : "${ctx}/admin/itemBatchHeader/goVerifyBack",
					data : {no: ret.no},
					contentType: "application/x-www-form-urlencoded; charset=UTF-8",
					dataType: "json",
					type: "post",
					cache: false,
					error: function(){
				       	art.dialog({
							content: '出现异常，无法撤回'
						});
				    },
				    success: function(obj){
				   		if(obj.rs){
				        	Global.Dialog.showDialog("goBack", {
				        	 	title: "材料批次--撤回",
				        	    url: "${ctx}/admin/itemBatchHeader/goBack",
				        	    height: 700,
				        	    width: 1250,               
				        	    postData:{no:ret.no}, 
								close:function(){
							  	 	goto_query();
							    },
				        	}); 			    		
				    	}else {
				    		art.dialog({
								content: obj.msg
							});
				    	}
					}
				});
		});
		//查看
		$("#view").on("click",function(){
				var ret = selectDataTableRow();
				if(!ret){
					art.dialog({
								content:"请选择一条记录！",
								width: 200
							});
					return;
				}
				Global.Dialog.showDialog("goDetail",{
					title:"材料批次--查看",
					url:"${ctx}/admin/itemBatchHeader/goDetail?id="+ret.no+"&crtCzyDescr="+ret.crtczydescr,
				    height:700,
				    width:1250,
					returnFun:goto_query
				});
		});
		
	});
	function doExcel(){
		var url = "${ctx}/admin/itemBatchHeader/doExcel";
		doExcelAll(url);
	}
	//打印
	function print(){
		var ret = selectDataTableRow();
		if(!ret){
			art.dialog({
						content:"请选择一条记录！",
						width: 200
					});
			return;
		}
		if(ret.batchtypedescr!="盘点批次"){
			art.dialog({
						content:"只有盘点批次类型的才需要打印！",
						width: 200
					});
			return;
		}
	   	var reportName = "itemBatchHeader_clpcpd.jasper";
	   	Global.Print.showPrint(reportName, {
	   		no:ret.no,
	   		date:(ret.date).substring(0,10),
	   		remarks:ret.remarks,
			LogoFile : "<%=basePath%>jasperlogo/logo.jpg",
			SUBREPORT_DIR: "<%=jasperPath%>" 
		}); 
	}
	</script>
</head>
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form role="form" class="form-search" id="page_form" action=""
				method="post" target="targetFrame">
				<input type="hidden" name="jsonString" value="" />
				<input type="hidden" id="expired" name="expired" value=""/>
				<ul class="ul-form">
					<li><label>批次编号</label> <input type="text" id="no" name="no"
						value="${itemBatchHeader.no}" />
					</li>
					<li><label>名称</label> <input type="text" id="remarks"
						name="remarks" style="width:160px;"
						value="${itemBatchHeader.remarks }" />
					</li>
					<li><label>材料类型1</label> <select id="itemType1"
						name="itemType1" value="${itemBatchHeader.itemType1}"></select>
					</li>
					<li><label>批次类型</label> <house:xtdm id="batchType"
							dictCode="BatchType" value="${itemBatchHeader.batchType}"></house:xtdm>
					</li>
					<li><label>楼盘</label> <input type="text" id="address"
						name="address" style="width:160px;"/>
					</li>
					<li><label>创建日期从</label> <input type="text" id="dateFrom"
						name="dateFrom" class="i-date" style="width:160px;"
						onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
					</li>
					<li><label>至</label> <input type="text" id="dateTo"
						name="dateTo" class="i-date" style="width:160px;"
						onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
					</li>
					<li class="search-group"><input type="checkbox" id="expired_show"
						name="expired_show" value="${itemBatchHeader.expired}"
						onclick="hideExpired(this)" ${itemBatchHeader.expired!='T' ?'checked':'' }/>
						<p>隐藏过期</p>
						<button type="button" class="btn  btn-sm btn-system "
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
			<button type="button" class="btn btn-system" id="add">
				<span>新增</span>
			</button>
			<button type="button" class="btn btn-system"
				id="update">
				<span>编辑</span>
			</button>
			<button type="button" class="btn btn-system" id="back">
				<span>撤回</span>
			</button>
			<house:authorize authCode="ITEMBATCHHEADER_VIEW">
				<button type="button" class="btn btn-system"
					id="view">
					<span>查看</span>
				</button>
			</house:authorize>
			<button type="button" class="btn btn-system" id="print"
				onclick="print()">
				<span>打印</span>
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
