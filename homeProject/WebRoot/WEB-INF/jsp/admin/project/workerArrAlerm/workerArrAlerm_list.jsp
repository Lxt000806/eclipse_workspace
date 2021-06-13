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
	<title>工人安排提醒</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
		<script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>
		<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
		<script src="${resourceRoot}/pub/component_supplier.js?v=${v}" type="text/javascript"></script>
		<script src="${resourceRoot}/pub/component_brand.js?v=${v}" type="text/javascript"></script>
		<script src="${resourceRoot}/pub/component_department2.js?v=${v}" type="text/javascript"></script>
	
	<script type="text/javascript">
	$(function(){
		Global.LinkSelect.initSelect("${ctx}/admin/item/itemType","itemType1","itemType2","itemType3");
		var postData = $("#page_form").jsonForm();
		var gridOption ={
			url:"${ctx}/admin/workerArrAlarm/goJqGrid",
			postData:{},
			height:$(document).height()-$("#content-list").offset().top-72,
			styleUI:"Bootstrap", 
			colModel : [
				{name: "PK", index: "PK", width: 80, label: "PK", sortable: true, align: "left",hidden:true},
				{name: "WorkType12", index: "WorkType12", width: 120, label: "工种12", sortable: true, align: "left",hidden:true},
				{name: "worktype12descr", index: "worktype12descr", width: 120, label: "工种12", sortable: true, align: "left",},
				{name: "item1descr", index: "item1descr", width: 120, label: "材料类型1", sortable: true, align: "left",},
				{name: "item2descr", index: "item2descr", width: 120, label: "材料类型2", sortable: true, align: "left",},
				{name: "jobtypedescr", index: "jobtypedescr", width: 120, label: "任务类型", sortable: true, align: "left",},
				{name: "isneedreqdescr", index: "isneedreqdescr", width: 120, label: "是否需要需求", sortable: true, align: "left",},
				{name: "isneedstakeholderdescr", index: "isneedstakeholderdescr", width: 120, label: "是否判断干系人", sortable: true, align: "left",},
				{name: "LastUpdate", index: "LastUpdate", width: 142, label: "最后修改时间", sortable: true, align: "left", formatter: formatTime},
				{name: "LastUpdatedBy", index: "LastUpdatedBy", width: 60, label: "修改人", sortable: true, align: "left"},
				{name: "Expired", index: "Expired", width: 70, label: "是否过期", sortable: true, align: "left"},
				{name: "ActionLog", index: "ActionLog", width: 50, label: "操作", sortable: true, align: "left"}
			],
		};
		Global.JqGrid.initJqGrid("dataTable",gridOption);
	
		$("#save").on("click",function(){
			Global.Dialog.showDialog("save",{
				title:"工人安排提醒设置——新增",
				url:"${ctx}/admin/workerArrAlarm/goSave",
				height:500,
				width:750,
				returnFun:goto_query
			});
		});
		//编辑
		$("#update").on("click",function(){
			ret=selectDataTableRow();
			if(!ret){
				art.dialog({
	       			content: "请选择一条记录",
	       		});
	       		return;
			}
			Global.Dialog.showDialog("update",{
				title:"工人安排提醒设置——编辑",
				url:"${ctx}/admin/workerArrAlarm/goUpdate",
				postData:{pk:ret.PK},
				height:500,
				width:750,
				returnFun:goto_query
			});
		});
		
		$("#view").on("click",function(){
			ret=selectDataTableRow();
			if(!ret){
				art.dialog({
	       			content: "请选择一条记录",
	       		});
	       		return;
			}
			Global.Dialog.showDialog("view",{
				title:"工人安排提醒设置——查看",
				url:"${ctx}/admin/workerArrAlarm/goView",
				postData:{pk:ret.PK},
				height:500,
				width:750,
				returnFun:goto_query
			});
		});
		
		/* 增加删除按钮——zb */
		$("#delete").on("click",function(){
			ret=selectDataTableRow();
			if(!ret){
				art.dialog({
	       			content: "请选择一条记录",
	       		});
	       		return;
			}
			art.dialog({
	    		content:"确认删除该记录？",
	    		ok:function(){
	    			$.ajax({
	    				url:"${ctx}/admin/workerArrAlarm/doDelete",
	    				type:"post",
	    				data:"pk="+ret.PK,
	    				dataType:"json",
	    				cache:false,
	    				error:function(obj){
	    					art.dialog({
	    						content:"访问出错,请重试",							
	    						time: 1000,
								beforeunload: function () {
									goto_query();
								}
	    					});
	    				},
	    				success:function(obj){
	    					if(obj.rs){
								goto_query();
							}else{
								$("#_form_token_uniq_id").val(obj.token.token);
								art.dialog({
									content: obj.msg,
									width: 200
								});
							}
	    				}
	    			});
	   			},
	    		cancel:function(){
	    			goto_query();
	    		}
	    	});
		});
		
	});
	
	function doExcel(){
		var url = "${ctx}/admin/workerArrAlarm/doExcel";
		doExcelAll(url);
	}
	</script>
</head>
	<body>
		<div class="body-box-list">
			<div class="query-form">
			    <form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
			    	<house:token></house:token>
					<input type="hidden" name="jsonString" value=""/>
					<ul class="ul-form">
						<li class="form-validate">
							<label>工种</label>
							 <house:DataSelect id="workType12" className="WorkType12" keyColumn="code" valueColumn="descr" value="${workerArrAlarm.workType12 }"></house:DataSelect>
						</li>
						<li>
							<label>材料类型1</label>
							<select id="itemType1" name="itemType1" style="width: 160px;"  ></select>
						</li>
						<li>
							<label>材料类型2</label>
							<select id="itemType2" name="itemType2" style="width: 160px;" ></select>
						</li>
						<li class="search-group" >
							<button type="button" class="btn btn-sm btn-system" onclick="goto_query();">查询</button>
							<button type="button" class="btn btn-sm btn-system" onclick="clearForm();">清空</button>
						</li>
					</ul>
				</form>
			</div>
		</div>
		<div class="clear_float"></div>
		<div class="btn-panel">
  			<div class="btn-group-sm">
				<button type="button" class="btn btn-system" id="save">
					<span>新增</span>
				</button>
				<button type="button" class="btn btn-system" id="update">
					<span>编辑</span>
				</button>
				<house:authorize authCode="WORKERARRALARM_VIEW">
					<button type="button" class="btn btn-system" id="view">
						<span>查看</span>
					</button>
 			  	</house:authorize>
 			  	<button type="button" class="btn btn-system" id="delete">
					<span>删除</span>
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
