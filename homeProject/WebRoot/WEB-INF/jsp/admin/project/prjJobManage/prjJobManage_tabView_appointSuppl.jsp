<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>

<% String fromFlag = request.getParameter("fromFlag"); %>

<script type="text/javascript">

$(function(){
	if("${prjJob.m_umState}"!="S"){
  		$("#add").attr("disabled","disabled");
  		$("#update").attr("disabled","disabled");
  		$("#del").attr("disabled","disabled");
	}
	$("#add").on("click",function(){
		Global.Dialog.showDialog("Save",{
			title:"任务管理——指派供应商",
			url:"${ctx}/admin/prjJobManage/goAddSuppl",
			postData:{no:"${prjJob.no}",jobType:$("#jobType").val(),
					},
			height:350,
			width:690,
			returnFun:query
		});
	}); 
	
	$("#update").on("click",function(){
		var ret=selectDataTableRow("dataTable_suppl");
		if(!ret){
			art.dialog({
				content:"请选择一条数据"
			});
			return;
		}
		if(ret.statusdescr!='未接收'&&ret.statusdescr!='接收退回'){
			art.dialog({
				content:"未接收、接收退回才可编辑",
			});
			return false;
		}	
		Global.Dialog.showDialog("Update",{
			title:"任务管理——指派供应商",
			url:"${ctx}/admin/prjJobManage/goUpdateSuppl",
			postData:{pk:ret.pk,jobType:$("#jobType").val(),
					},
			height:350,
			width:690,
			returnFun:query
		});
	}); 
	
	$("#view").on("click",function(){
		var ret=selectDataTableRow("dataTable_suppl");
		if(!ret){
			art.dialog({
				content:"请选择一条数据"
			});
			return;
		}
		Global.Dialog.showDialog("Update",{
			title:"任务管理——指派供应商",
			url:"${ctx}/admin/prjJobManage/goViewSuppl",
			postData:{pk:ret.pk,
					},
			height:350,
			width:690,
			returnFun:query
		});
	}); 
	
	$("#del").on("click",function(){
		var ret = selectDataTableRow("dataTable_suppl");
		if(ret.statusdescr!='未接收'&&ret.statusdescr!='接收退回'){
			art.dialog({
				content:"未接收、接收退回才可删除",
			});
			return false;
		}		
		art.dialog({
				 content:"是否删除",
				 lock: true,
				 width: 200,
				 height: 100,
				 ok: function () {
					$.ajax({
						url:"${ctx}/admin/prjJobManage/doDelSuppl",
						type:'post',
						data:{pk:ret.pk},
						dataType:'json',
						cache:false,
						error:function(obj){
							showAjaxHtml({"hidden": false, "msg": '获取数据出错~'});
						},
						success:function(obj){
							query();
							art.dialog({
								content:'删除成功',
								time: 1000,
							});
 						}
					});
				},
				cancel: function () {
						return true;
				}
		});	
	});
	
 	Global.JqGrid.initJqGrid("dataTable_suppl",{
		rowNum: 10000,
		url:"${ctx}/admin/prjJobManage/goSupplJqGrid",
		height:220,
		postData:{
			no:"${prjJob.no}",
		},
		styleUI:"Bootstrap",
		colModel:[
			{name:"pk", index:"pk", width:80, label:"pk", sortable:true, align:"left",hidden:true} ,
			{name:"prjjobno", index:"prjjobno", width:80, label:"任务单号", sortable:true, align:"left"} ,
			{name:"suppldescr", index:"suppldescr", width:80, label:"供应商", sortable:true, align:"left"} ,
			{name:"statusdescr", index:"statusdescr", width:80, label:"状态", sortable:true, align:"left"} ,
			{name:"appdescr", index:"appdescr", width:80, label:"指派人", sortable:true, align:"left"} ,
			{name:"date", index:"date", width:80, label:"指派时间", sortable:true, align:"left",formatter:formatDate} ,
			{name:"recvdate", index:"recvdate", width:80, label:"接收时间", sortable:true, align:"left",formatter:formatDate} ,
			{name:"plandate", index:"plandate", width:80, label:"计划处理时间", sortable:true, align:"left",formatter:formatDate} ,
			{name:"completedate", index:"completedate", width:80, label:"完成时间", sortable:true, align:"left",formatter:formatDate} ,
			{name:"remarks", index:"remarks", width:180, label:"备注", sortable:true, align:"left"} ,
			{name:"supplremarks", index:"supplremarks", width1:80, label:"供应商备注", sortable:true, align:"left"} ,
			{name:"lastupdate", index:"lastupdate", width:80, label:"最后修改时间", sortable:true, align:"left",formatter:formatDate} ,
			{name:"lastupdatedby", index:"lastupdatedby", width:80, label:"最后修改人", sortable:true, align:"left"} ,
		],
		
	});
});
function query(){
	$("#dataTable_suppl").jqGrid("setGridParam",{postData:{no:"${prjJob.no}"},page:1,sortname:''}).trigger("reloadGrid");
}
</script>
	<div class="body-box-list" style="margin-top: 0px;">
		<div class="panel panel-system" >
			<div class="panel-body" >
				<div class="btn-group-xs" >
					<button type="button" class="btn btn-system " id="add" >新增</button>
					<button type="button" class="btn btn-system " id="update" >编辑</button>
					<button type="button" class="btn btn-system " id="del" >删除</button>	
					<button type="button" class="btn btn-system " id="view">查看</button>	
				</div>
			</div>	
		</div>
 		<div id="content-list">
 			<table id="dataTable_suppl"></table>
  		</div>
	</div>
