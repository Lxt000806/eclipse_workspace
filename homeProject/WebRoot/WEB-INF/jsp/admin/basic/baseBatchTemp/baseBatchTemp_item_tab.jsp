<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript">
$(function(){
	Global.JqGrid.initJqGrid("detailDataTable",{
			url:"${ctx}/admin/baseBatchTemp/goItemJqGrid",
			postData:{no:"${baseBatchTemp.m_umState}"=="A"?"":"${baseBatchTemp.areaNo}"},
			height:$(document).height()-$("#content-list").offset().top-82,
			styleUI: 'Bootstrap', 
			rowNum:10000000,
			searchBtn:true,
			onSortColEndFlag:true,
			height:380,
			colModel : [
				{name: "pk", index: "pk", width: 95, label: "pk", sortable: true, align: "left", hidden: true},
				{name: "areano", index: "areano", width: 162, label: "区域编号", sortable: true, align: "left", hidden: true},
				{name: "baseitemcode", index: "baseitemcode", width: 160, label: "项目编号", sortable: true, align: "left"},
				{name: "baseitemdescr", index: "baseitemdescr", width: 179, label: "项目名称", sortable: true, align: "left"},
				{name: "qty", index: "qty", width: 60, label: "数量", sortable: true, align: "right"},
				{name: "isoutset", index: "isoutset", width: 70, label: "是否套餐外", sortable: true, align: "left", hidden: true},
				{name: "isoutsetdescr", index: "isoutsetdescr", width: 80, label: "是否套餐外", sortable: true, align: "left"},
				{name: "dispseq", index: "dispseq", width: 70, label: "显示顺序", sortable: true, align: "left", hidden: true},
				{name: "lastupdate", index: "lastupdate", width: 120, label: "最后修改时间", sortable: true, align: "left", formatter: formatTime},
				{name: "lastupdatedby", index: "lastupdatedby", width: 70, label: "修改人", sortable: true, align: "left"},
				{name: "expired", index: "expired", width: 70, label: "过期标志", sortable: true, align: "left"},
				{name: "actionlog", index: "actionlog", width: 80, label: "修改操作", sortable: true, align: "left"}
			],
			onSortCol:function(index, iCol, sortorder){
					var rows = $("#detailDataTable").jqGrid("getRowData");
		   			rows.sort(function (a, b) {
		   				var reg = /^[0-9]+.?[0-9]*$/;
						if(reg.test(a[index])){
		   					return (a[index]-b[index])*(sortorder=="desc"?1:-1);
						}else{
		   					return a[index].toString().localeCompare(b[index].toString())*(sortorder=="desc"?1:-1);
						}  
		   			});    
		   			Global.JqGrid.clearJqGrid("detailDataTable"); 
		   			$.each(rows,function(k,v){
						Global.JqGrid.addRowData("detailDataTable", v);
					});
			},
		});
	});
	//新增
	function add(){
		var ids=$("#detailDataTable").jqGrid("getDataIDs");
		var ret = $("#detailDataTable").jqGrid('getRowData',ids[ids.length-1]);
		var dispseq=0;
		var newId=1;
		if(ids.length>0){
			dispseq=parseInt(ret.dispseq,0)+1;
			newId=parseInt(ids[ids.length-1],0)+1;
		} 
		Global.Dialog.showDialog("goAddItem", {
			title : "基础批量报价模板项目--增加",
			url : "${ctx}/admin/baseBatchTemp/goAddItem",
			postData:{m_umState:"A"},
		    height:300,
		    width:400,
			returnFun : function(v) {
					 	var json = {
							baseitemcode : v.baseItemCode,baseitemdescr : v.baseItemDescr,
							qty : parseFloat(v.qty,0),actionlog : "ADD",
							isoutset : v.isOutSet,isoutsetdescr: v.isOutSetDescr,
							lastupdate : new Date().getTime(),expired : "F",lastupdatedby : "${USER_CONTEXT_KEY.czybh}",
							pk:(ids.length)*-1
						};
						$("#detailDataTable").jqGrid("addRowData", newId, json);
						$("#dataChanged").val("1"); 
			}
		});
	}
	//编辑
	function update(){
		var id = $("#detailDataTable").jqGrid("getGridParam","selrow");
	 	var ret = $("#detailDataTable").jqGrid('getRowData',id);
	 	if(!id){
			art.dialog({content: "请选择一条记录！",width: 200});
			return false;
		} 
		Global.Dialog.showDialog("goUpdateItem", {
			title : "基础批量报价模板项目--编辑",
			url : "${ctx}/admin/baseBatchTemp/goAddItem",
		    height:300,
		    width:400,
		    postData:{
		    	baseItemCode:ret.baseitemcode,
				isOutSet:ret.isoutset, baseItemDescr : ret.baseitemdescr,
				qty : ret.qty, isOutSetDescr: ret.isoutsetdescr,m_umState:"M",
				pk:ret.pk
		    },
			returnFun : function(v) {
					 	var json = {
							baseitemcode : v.baseItemCode,baseitemdescr : v.baseItemDescr,
							qty : parseFloat(v.qty,0),actionlog : "ADD",
							isoutset : v.isOutSet,isoutsetdescr: v.isOutSetDescr,
							lastupdate : new Date().getTime(),expired : "F",lastupdatedby : "${USER_CONTEXT_KEY.czybh}",
							pk:v.pk
						};
						$("#detailDataTable").setRowData(id,json);
						$("#dataChanged").val("1"); 
			}
		});
	}
	//查看
	function view(){
		var id = $("#detailDataTable").jqGrid("getGridParam","selrow");
	 	var ret = $("#detailDataTable").jqGrid('getRowData',id);
	 	if(!id){
			art.dialog({content: "请选择一条记录！",width: 200});
			return false;
		} 
		Global.Dialog.showDialog("goViewItem", {
			title : "基础批量报价模板项目--查看",
			url : "${ctx}/admin/baseBatchTemp/goAddItem",
		    height:300,
		    width:400,
		    postData:{
		    	baseItemCode:ret.baseitemcode,
				isOutSet:ret.isoutset, baseItemDescr : ret.baseitemdescr,
				qty : ret.qty, isOutSetDescr: ret.isoutsetdescr,m_umState:"V",
				pk:ret.pk
		    },
		});
	}
	//删除
	function del(){
	 	var id = $("#detailDataTable").jqGrid("getGridParam","selrow");
		if(!id){
			art.dialog({content: "请选择一条记录进行删除操作！",width: 200});
			return false;
		}
		art.dialog({
			 content:"确定要删除该条记录吗？",
			 lock: true,
			 ok: function () {
				Global.JqGrid.delRowData("detailDataTable",id);
				var rowData = $("#detailDataTable").jqGrid('getRowData');
				$("#detailDataTable").jqGrid("clearGridData");
				$.each(rowData, function(i,r){
					r['dispseq']=i;//设置顺序
					$("#detailDataTable").jqGrid("addRowData", i+1, r);
				});
				$("#dataChanged").val("1");
			},
			cancel: function () {
					return true;
			}
		}); 
	}
	//向上
	function up(){
		var rowId = $("#detailDataTable").jqGrid("getGridParam", "selrow");
		var replaceId = parseInt(rowId)-1;
		if(rowId){
			if(rowId>1){
			    var ret1 = $("#detailDataTable").jqGrid("getRowData", rowId);
				var ret2 = $("#detailDataTable").jqGrid("getRowData", replaceId);
				replace(rowId,replaceId,"detailDataTable");
				scrollToPosi(replaceId,"detailDataTable");
				$("#detailDataTable").setCell(rowId,"dispseq",ret1.dispseq);
				$("#detailDataTable").setCell(replaceId,"dispseq",ret2.dispseq);
				$("#dataChanged").val("1");
			}
		}else{
			art.dialog({
				content: "请选择一条记录"
			});
		}
	}
	//向下
	function down(){
		var rowId = $("#detailDataTable").jqGrid("getGridParam", "selrow");
		var replaceId = parseInt(rowId)+1;
		var rowNum = $("#detailDataTable").jqGrid("getGridParam","records");
		if(rowId){
			if(rowId<rowNum){
				var ret1 = $("#detailDataTable").jqGrid("getRowData", rowId);
				var ret2 = $("#detailDataTable").jqGrid("getRowData", replaceId);
				scrollToPosi(replaceId,"detailDataTable");
				replace(rowId,replaceId,"detailDataTable");
				$("#detailDataTable").setCell(rowId,"dispseq",ret1.dispseq);
				$("#detailDataTable").setCell(replaceId,"dispseq",ret2.dispseq);
				$("#dataChanged").val("1");
			}
		}else{
			art.dialog({
				content: "请选择一条记录"
			});
		}
	}
	
</script>
<div class="body-box-list" style="margin-top: 0px;">
	<div class="panel panel-system">
		<div class="panel-body">
			<form role="form" class="form-search" id="form" method="post">
				<input type="hidden" name="jsonString" value="" />
			</form>
			<div class="btn-group-xs">
				<button style="align:left" type="button" class="btn btn-system view" 
					onclick="add()">
					<span>新增 </span>
				</button>
				<button style="align:left" type="button" class="btn btn-system view"
					onclick="update()">
					<span>编辑 </span>
				</button>
				<button style="align:left" type="button" class="btn btn-system view"
					onclick="del()">
					<span>删除 </span>
				</button>
				<button style="align:left" type="button" class="btn btn-system "
					onclick="view()">
					<span>查看 </span>
				</button>
				<button style="align:left" type="button" class="btn btn-system view"
					 onclick="up()">
					<span>向上 </span>
				</button>
				<button style="align:left" type="button" class="btn btn-system view"
					 onclick="down()">
					<span>向下 </span>
				</button>
				<button style="align:left" type="button" class="btn btn-system "
					 onclick="doExcelNow('基础批量报价模板项目','detailDataTable','form')">
					<span>导出excel </span>
				</button>
			</div>
		</div>
	</div>
	<div class="clear_float"></div>
	<!--query-form-->
	<div id="content-list">
		<table id="detailDataTable"></table>
	</div>
</div>



