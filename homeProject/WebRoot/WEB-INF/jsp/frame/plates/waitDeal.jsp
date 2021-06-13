<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	Integer index = Integer.parseInt(request.getParameter("index"));
	String margin = (index % 2 == 0 ? "margin: 10px 0px 0px 1%" : "margin: 10px 1% 0px 1%"); 
%>
<div class="panel panel-system" style="<%=margin %>;">
    <div class="panel-body" >
		<div class="btn-group-xs">
			<strong>待办事项</strong>
			<div>	
				<button type="button" class="btn btn-system"  onclick="query_()" >
					<span>刷新</span>
				</button>
			</div>
		</div>
    </div>
    
	<div class="pageContent">
		<div id="content-list">
			<table id= "dataTableDb"></table>
			<div class="tableNull"><img src="${resourceRoot}/images/dataTableNull.png"/></div>
		</div>
	</div>
</div>
<script type="text/javascript">
$(function(){
	Global.JqGrid.initJqGrid("dataTableDb", {
		url: "${ctx}/admin/wfProcInst/findWfProcInst",
		postData: {
			type: "1"
		},
		height: 299,
		isSelectOne: false,
		rowNum: 10000,
		colModel: [  
			{name: "no", index: "no", width: 200, label: "任务名称", sortable: true, align: "left"
				,formatter:function(cellvalue, options, rowObject){
	        		if(rowObject==null || !rowObject.no){
	          			return '';
	          		}
	        		return "<a href=\"javascript:void(0)\" onclick=\"view('"
	        			+rowObject.taskid+"','"+rowObject.actprocinstid+"','"+rowObject.title+"','"+rowObject.statusdescr+"')\">"+rowObject.title+"</a>";
		    	}
		    }, 
			{name: "title", index: "title", width: 160, label: "单据标题", sortable: true, align: "left",hidden:true}, 
			{name: "summary", index: "summary", width:310, label: "单据摘要", sortable: true, align: "left", formatter:function(cellvalue, options, rowObject){return cellvalue.replace(/\<br\/\>/g, ";").replace(/\<none\/\>/g, "");}}, 
			{name: "starttime", index: "starttime", width: 126, label: "发起时间", sortable: true, align: "left", formatter: formatTime},
			{name: "endtime", index: "endtime", width: 126, label: "完成时间", sortable: true, align: "left", formatter: formatTime,hidden:true}, 
			{name: "statusdescr", index: "statusdescr", width:120, label: "状态", sortable: true, align: "left",hidden:true},
			{name: "taskname", index: "taskname", width:120, label: "任务名称", sortable: true, align: "left",hidden:true},
			{name: "actprocinstid", index: "actprocinstid", width:120, label: "流程实例ID", sortable: true, align: "left",hidden:true},
			{name: "taskid", index: "taskid", width:120, label: "任务ID", sortable: true, align: "left",hidden:true }
	    ],
	    gridComplete:function(){
			tableNullTip("dataTableDb");
		},
		loadComplete: function(xhr) {
			if(!(window && window.parent && window.parent.document && window.parent.document.getElementById("letterTipSpan"))){
				return;
			}
 			if(xhr && xhr.records && xhr.records > 0) {
				if(xhr.records > 99) {
					xhr.records = "99";
				}
				window.parent.document.getElementById("letterTipSpan").innerHTML = xhr.records;
				window.parent.document.getElementById("letterTipSpan").style.display = "block";
			} else {
				window.parent.document.getElementById("letterTipSpan").style.display = "none";
			} 
		},
		onCellSelect: function(id,iCol,cellParam,e){
			var ids = $("#dataTableDb").jqGrid("getDataIDs");  
			for(var i=0;i<ids.length;i++){
				if(i!=id-1){
					$('#'+ids[i]).find("td").removeClass("SelectBG");
				}
			}
			$("#dataTableDb").find('#'+ids[id-1]).find("td").addClass("SelectBlack");
		},
	});
});

function view(taskid,actprocinstid,title,status) {
	var m_umState ="";
	if(status =="审批中"){
		m_umState = "C"; 
	}
    Global.Dialog.showDialog("view", {
		title: title,
		url: "${ctx }/admin/wfProcInst/goView?taskId=" + taskid + "&processInstanceId=" + actprocinstid + "&type=" + "1" +"&m_umState="+m_umState,
		height: 780,
		width: 1280,
		returnFun: query,
		close:query
    });
	function query(){
		$("#dataTableDb").jqGrid("setGridParam",{url: "${ctx}/admin/wfProcInst/findWfProcInst",postData:{type: "1"},page:1,sortname:''}).trigger("reloadGrid");
		goto_query();
	}
}
function query_(){
	tableNullTip("dataTableDb", true);
	$("#dataTableDb").jqGrid("setGridParam",{
		url: "${ctx}/admin/wfProcInst/findWfProcInst",
		postData:{
			type: "1"
		},
		page:1,
		sortname:''
	}).trigger("reloadGrid");
}
</script>
