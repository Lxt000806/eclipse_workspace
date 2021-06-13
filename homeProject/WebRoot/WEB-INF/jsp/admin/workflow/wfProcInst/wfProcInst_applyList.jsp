<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<title>发起审批</title>
	<%@ include file="/commons/jsp/common.jsp" %>
	<link href="${resourceRoot}/plugins/qtip/jquery.qtip.min.css" type="text/css" rel="stylesheet" />
	<script src="${resourceRoot}/plugins/qtip/jquery.qtip.pack.js" type="text/javascript"></script>
	<script src="${resourceRoot}/plugins/html/jquery.outerhtml.js" type="text/javascript"></script>
	<script src="${resourceRoot}/js/workflow.js" type="text/javascript"></script>
	<style type="text/css">
		.SelectBG{
			background-color:white!important;
		}
	</style>
<script type="text/javascript">
$(function(){
	//初始化表格
	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/wfProcInst/getApplyListByJqgrid",//之后可能得改为 goJqGrid
		postData:{descr:$("#descr").val()},
		height:$(document).height()-$("#content-list").offset().top-80,
		colModel : [
		  {name : 'groupdescr',index : 'groupdescr',width : 60,label:'分组',sortable : true,align : "left",hidden:true},
		  {name : 'procid',index : 'procid',width : 60,label:'流程实例ID',sortable : true,align : "left",hidden:true},
		  {name : 'no',index : 'no',width : 160,label:'编号',sortable : true,align : "left",hidden:true},
		  {name : 'prockey',index : 'prockey',width : 150,label:'流程标识',sortable : true,align : "left",hidden:true},
		  {name : 'descr',index : 'descr',width : 350,label:'流程名称',sortable : true,align : "center"
		  		,formatter:function(cellvalue, options, rowObject){
	        			if(rowObject==null || !rowObject.descr){
	          				return '';
	          			}
	        			return "<a href=\"javascript:void(0)\" style=\"color:blue!important\" onclick=\"goApply('"
	        					+rowObject.prockey+"','"+rowObject.descr+"')\">"+rowObject.descr+"</a>";
		    	}
		  },
		  {name : 'dispseq',index : 'dispseq',width : 60,label:'分组',sortable : true,align : "left",hidden:true},
		],
		grouping: true, // 是否分组,默认为false
        groupingView: {
          groupField: ['groupdescr'], // 按照哪一列进行分组
          groupColumnShow: [false], // 是否显示分组列
          groupText: ['<b>{0}({1})</b>'], // 表头显示的数据以及相应的数据量
          groupCollapse: false, // 加载数据时是否只显示分组的组信息
          groupDataSorted: true, // 分组中的数据是否排序
          groupOrder: ['asc'], // 分组后的排序
          groupSummary: [false], // 是否显示汇总.如果为true需要在colModel中进行配置
          summaryType: 'max', // 运算类型，可以为max,sum,avg</div>
          summaryTpl: '<b>Max: {0}</b>',
          showSummaryOnHide: true //是否在分组底部显示汇总信息并且当收起表格时是否隐藏下面的分组
        },
        gridComplete:function(){
			$("#1").find("td").addClass("SelectBG");
		},
		onCellSelect: function(id,iCol,cellParam,e){
			var ids = $("#dataTable").jqGrid("getDataIDs");  
			for(var i=0;i<ids.length;i++){
				if(i!=id-1){
					$('#'+ids[i]).find("td").removeClass("SelectBG");
				}
			}
			$('#'+ids[id-1]).find("td").addClass("SelectBG");
		
		},
	});
});
	//发起页面
	function goApply(prockey,title){
		Global.Dialog.showDialog("goApply",{ 
     		title:title,
			url:"${ctx}/admin/wfProcInst/goApply",
			postData:{key:prockey},
          	height: 780,
         	width:1280,
          	returnFun:goto_query
         });
	};
</script>
</head>
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
				<input type="hidden" name="jsonString" value="" />
				<ul class="ul-form">
					<li>
						<label>流程名称</label>
						<input type="text" id="descr" name="descr" style="width:160px;"/>
					</li>
					<li>
						<button type="button" class="btn btn-sm btn-system" onclick="goto_query();">查询</button>
						<button type="button" class="btn btn-sm btn-system" onclick="clearForm();">清空</button>
					</li>
				</ul>
			</form>
		</div>
		<!--query-form-->
		<!-- <div class="btn-panel">
			 <div class="btn-group-sm">
				<button type="button" class="btn btn-system" onclick="goApply()">发起</button>
			</div>
		</div> -->
		<div id="content-list">
			<table id= "dataTable"></table>
			<div id="dataTablePager"></div>
		</div>
	</div>
</body>
</html>


