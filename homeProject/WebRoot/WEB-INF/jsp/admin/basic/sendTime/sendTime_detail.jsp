<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript">
$(function(){
	Global.JqGrid.initJqGrid("detailDataTable",{
			url:"${ctx}/admin/sendTime/goDetailJqGrid",
			postData:{no:"${sendTime.no}"},
			height:235,
			styleUI: 'Bootstrap', 
			rowNum:10000000,
			searchBtn:true,
			colModel : [
				{name: "itemtype2", index: "itemtype2", width: 84, label: "itemtype2", sortable: true, align: "left",hidden:true},
				{name: "itemtype2descr", index: "itemtype2descr", width: 320, label: "材料类型2", sortable: true, align: "left"},
				{name: "itemdesc", index: "itemdesc", width: 320, label: "材料名称", sortable: true, align: "left"},
				{name: "lastupdate", index: "lastupdate", width: 151, label: "最后更新时间", sortable: true, align: "left", formatter: formatTime,hidden:true},
				{name: "lastupdatedby", index: "lastupdatedby", width: 110, label: "最后更新人员", sortable: true, align: "left",hidden:true},
				{name: "expired", index: "expired", width: 85, label: "是否过期", sortable: true, align: "left",hidden:true},
				{name: "actionlog", index: "actionlog", width: 85, label: "操作日志", sortable: true, align: "left",hidden:true}
			],
			loadonce:true,
			gridComplete:function(){
				var rowNum = $("#detailDataTable").jqGrid("getGridParam","records");
				if(rowNum>0){
					$("#itemType1").attr("disabled",true);
				}
			},
			ondblClickRow:function(){
				view();
	        },	
		});
	});
	//新增
	function add(){
		var itemType1=$("#itemType1").val();
		var isSetItem=$("#isSetItem").val();
		var ids=$("#detailDataTable").jqGrid("getDataIDs");
		var ret = $("#detailDataTable").jqGrid('getRowData',ids[ids.length-1]);
		var newId=1;
		if(ids.length>0){
			newId=parseInt(ids[ids.length-1],0)+1;
		} 
		if(itemType1==""){
			art.dialog({
				content: "请先选择材料类型1！"
			});
			return;
		}
		if(isSetItem!="1"){
			art.dialog({
				content: "请限制材料！"
			});
			return;
		}
		Global.Dialog.showDialog("save", {
			title : "发货时限明细--增加",
			url : "${ctx}/admin/sendTime/goDetailAdd?m_umState=A&itemType1="+itemType1,
		    height:300,
		    width:600,
			returnFun : function(v) {
						var json = {
							itemtype2:v.itemType2,itemtype2descr:v.itemType2Descr,itemdesc:v.itemDesc,
							expired:"F",actionlog:"ADD",lastupdate:new Date(),lastupdatedby:"${sendTime.lastUpdatedBy}"
						};
						$("#detailDataTable").jqGrid("addRowData", newId, json);
						var isDisabled=$("#itemType1").prop("disabled");
						if(!isDisabled){
							$("#itemType1").attr("disabled",true);
						}
						$("#dataChanged").val("1");
			}
		});
	}
	//编辑
	function update(){
		$("#itemType1").removeAttr("disabled");
		var itemType1=$("#itemType1").val();
		$("#itemType1").attr("disabled",true);
		var id = $("#detailDataTable").jqGrid("getGridParam","selrow");
	 	var ret = $("#detailDataTable").jqGrid('getRowData',id);
	 	if(!id){
			art.dialog({content: "请选择一条记录！",width: 200});
			return false;
		} 
		Global.Dialog.showDialog("save", {
			title : "发货时限明细--编辑",
			url : "${ctx}/admin/sendTime/goDetailAdd",
		    height:300,
		    width:600,
		    postData:{
		    		itemType2:ret.itemtype2.trim(),itemType2Descr:ret.itemtype2descr,itemDesc:ret.itemdesc,m_umState:"M",itemType1:itemType1
		    },
			returnFun : function(v) {
						var json = {
							itemtype2:v.itemType2,itemtype2descr:v.itemType2Descr,itemdesc:v.itemDesc,
							expired:"F",actionlog:"EDIT",lastupdate:new Date(),lastupdatedby:"${sendTime.lastUpdatedBy}"
						};
						$("#detailDataTable").setRowData(id, json);
						$("#dataChanged").val("1");
			}
		});
	}
	//查看
	function view(){
		$("#itemType1").removeAttr("disabled");
		var itemType1=$("#itemType1").val();
		$("#itemType1").attr("disabled",true);
		var id = $("#detailDataTable").jqGrid("getGridParam","selrow");
	 	var ret = $("#detailDataTable").jqGrid('getRowData',id);
	 	if(!id){
			art.dialog({content: "请选择一条记录！",width: 200});
			return false;
		} 
		Global.Dialog.showDialog("save", {
			title : "发货时限明细--查看",
			url : "${ctx}/admin/sendTime/goDetailAdd",
		    height:300,
		    width:600,
		    postData:{
		    		itemType2:ret.itemtype2.trim(),itemType2Descr:ret.itemtype2descr,itemDesc:ret.itemdesc,m_umState:"V",itemType1:itemType1
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
			 content:"是否确认要删除",
			 lock: true,
			 width: 200,
			 height: 100,
			 ok: function () {
				Global.JqGrid.delRowData("detailDataTable",id);
				var rowNum = $("#detailDataTable").jqGrid("getGridParam","records");
				if(rowNum==0){
					$("#itemType1").removeAttr("disabled");
				}
				$("#dataChanged").val("1");
			},
			cancel: function () {
					return true;
			}
		}); 
	}
</script>
<div class="body-box-list" style="margin-top: 0px;">
	<div class="panel panel-system">
		<div class="panel-body">
			<form role="form" class="form-search" id="form" method="post">
				<input type="hidden" name="jsonString" value="" />
				<input type="hidden" id="dataChanged" >
			
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
			</div>
		</div>
	</div>
	<div class="clear_float"></div>
	<!--query-form-->
	<div id="content-list">
		<table id="detailDataTable"></table>
	</div>
	</form>
</div>



