<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>

<% String fromFlag = request.getParameter("fromFlag"); %>

<script type="text/javascript">
function op(showContent, tipContent, url){
   	art.dialog({
		content: showContent,
		ok:function(){
			$.ajax({
				url:url,
				type: "post",
				dataType: "json",
				cache: false,
				error: function(obj){			    		
					art.dialog({
						content: tipContent,
						time: 3000,
						beforeunload: function () {
							goto_query();
						}
					});
				},
				success: function(obj){
					if(obj.rs){
						art.dialog({
							content: obj.msg,
							time: 3000,
							beforeunload: function () {
								goto_query();
							}
						});
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
		cancel:function(){}
	});
}
function showDialog(sign, title, url){
	Global.Dialog.showDialog(sign,{
		title:title,
		url:url, 
		height:400,
		width:1000,
		returnFun:goto_query 
	});
}
function view(){
	var ret = selectDataTableRow("dataTable_measure");
	if (ret) {
		showDialog("tabView_measureDetail", "查看测量单", "${ctx}/admin/itemPreApp/goDetail_tabView_measure?pk="+ret.pk);	
    } else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}
function add(){
	var no ="${itemPreApp.no }";
	showDialog("tabView_measureAdd", "新增测量单", "${ctx}/admin/itemPreApp/goAdd_tabView_measure?no="+no);	
}
function update(){
	var ret = selectDataTableRow("dataTable_measure");
	if (ret) {
		if(ret.status > 2){	
	    	art.dialog({
				content: "只能对未接收状态进行编辑操作"
			});
			return;
		}
		showDialog("tabView_measureUpdate", "编辑测量单","${ctx}/admin/itemPreApp/goUpdate_tabView_measure?pk="+ret.pk);	
    } else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}
function goto_query(){
	$("#dataTable_measure").jqGrid("setGridParam",{
		url:"${ctx}/admin/itemPreApp/goMeasureJqGrid",
		postData:{},
		page:1
	}).trigger("reloadGrid");
}
function del(){
	var ret = selectDataTableRow("dataTable_measure");
	if (ret) {
		if(ret.status.trim() == "1"){
			op("是否删除该记录", "删除出错,请重试", "${ctx}/admin/itemPreApp/doDel_preMeasure?pk="+ret.pk);
		}else{
	    	art.dialog({
				content: "只能对申请状态进行删除操作"
			});
		}
    } else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}
function cancel(){
	var ret = selectDataTableRow("dataTable_measure");
	if (ret) {
		if(ret.status.trim() == "1"){
			op("是否取消该记录申请", "取消申请出错,请重试", "${ctx}/admin/itemPreApp/doCancel_preMeasure?pk="+ret.pk);
		}else{
	    	art.dialog({
				content: "只能对申请状态的记录进行取消操作"
			});
		}
    } else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}
function recovery(){
	var ret = selectDataTableRow("dataTable_measure");
	if (ret) {
		if(ret.status.trim() == "5"){
			op("是否恢复该记录申请", "恢复申请出错,请重试", "${ctx}/admin/itemPreApp/doRecovery_preMeasure?pk="+ret.pk);
		}else{
	    	art.dialog({
				content: "只能对取消状态的记录进行恢复操作"
			});
		}
    } else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}
$(function(){
 	Global.JqGrid.initJqGrid("dataTable_measure",{
		height:300,
		rowNum: 10000,
		url:"${ctx}/admin/itemPreApp/goMeasureJqGrid",
		postData:{
			no:"${itemPreApp.no }"
		},
		styleUI:"Bootstrap",
		colModel:[
			{name:"supplcode", index:"supplcode", width:80, label:"供应商编号", sortable:true, align:"left"} ,
			{name:"supplerdescr", index:"supplerdescr", width:120, label:"供应商名称", sortable:true , align:"left"},
			{name:"statusdescr", index:"statusdescr", width:50, label:"状态", sortable:true, align:"left"},
			{name:"appczydescr", index:"appczydescr", width:70, label:"申请人", sortable:true, align:"right"},
			{name:"date", index:"date", width:70, label:"申请时间", sortable:true, align:"left", formatter:formatTime},
			{name:"recvdate", index:"recvdate", width:70, label:"接收时间", sortable:true, align:"left", formatter:formatTime},
			{name:"confirmczydescr", index:"confirmczydescr", width:70, label:"测量人", sortable:true, align:"left"},
			{name:"confirmdate", index:"confirmdate", width:70, label:"测量时间", sortable:true, align:"right", formatter:formatTime},
			{name:"remarks", index:"remarks", width:150, label:"备注", sortable:true, align:"left"},
			{name:"measureremark", index:"measureremark", width:150, label:"测量数据", sortable:true, align:"left"},
			{name:"completedate", index:"completedate", width:70, label:"下单时间", sortable:true, align:"left", formatter:formatTime},
			{name:"completeczydescr", index:"completeczydescr", width:70, label:"下单人", sortable:true, align:"left"},
			{name:"lastupdate", index:"lastupdate", width:90, label:"最后更新时间", sortable:true, align:"left", formatter:formatTime},
			{name:"lastupdatedby", index:"lastupdatedby", width:80, label:"最后修改人", sortable:true, align:"left"},
			{name:"actionlog", index:"actionlog", width:70, label:"操作标志", sortable:true, align:"left"},
	        {name:"pk", index:"pk", width:80, label:"pk", sortable: true, align:"left", hidden:true},
	        {name:"status", index:"status", width:80, label:"status", sortable:true, align:"left", hidden:true}  	 	
		]
	});
	if($("#fromFlag_measure").val().trim()=="M"){
		$(".measureBtns button").removeAttr("disabled");
	}
});
</script>
<div class="panel panel-system" >
    <div class="btn-panel" >
    	<div class="btn-group-sm measureBtns" >
			<button type="button" class="funBtn funBtn-system" onclick="add()" disabled>新增</button>
			<button type="button" class="funBtn funBtn-system" onclick="update()" disabled>编辑</button>
			<button type="button" class="funBtn funBtn-system" onclick="del()" disabled>删除</button>	
			<button type="button" class="funBtn funBtn-system" onclick="cancel()" disabled>取消</button>
			<button type="button" class="funBtn funBtn-system" onclick="recovery()" disabled>恢复</button>
			<button type="button" class="funBtn funBtn-system" onclick="view()">查看</button>	
		</div>
	</div>
</div>
<input type="hidden" id="fromFlag_measure" value="<%=fromFlag %>" />
<table id="dataTable_measure"></table>
