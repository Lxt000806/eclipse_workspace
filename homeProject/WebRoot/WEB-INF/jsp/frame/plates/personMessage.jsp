<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>
<%
	Integer index = Integer.parseInt(request.getParameter("index"));
	String margin = (index % 2 == 0 ? "margin: 10px 0px 0px 1%" : "margin: 10px 1% 0px 1%"); 
	UserContext uc = (UserContext)request.getSession().getAttribute(CommonConstant.USER_CONTEXT_KEY);
	String czybh = uc.getCzybh();
%>
<div class="panel panel-system" style="<%=margin %>;">
	<div class="panel-body" >
		<div class="btn-group-xs">
			<strong>消息中心</strong>
			<div>
				<button type="button" class="btn btn-system "  onclick="goto_query()" >
					<span>刷新</span>
				</button>
				<button class="btn btn-system" id="id_more" onclick="moreClickPersonMessage()">更多</button>
			</div>
		</div>
	</div>
	<div class="pageContent">
		<div id="content-list">
			<table id= "dataTableInfo"></table>
			<div class="tableNull"><img src="${resourceRoot}/images/dataTableNull.png"/></div>
		</div>
	</div> 
</div>
<script type="text/javascript">
$(function(){
	//初始化表格
	Global.JqGrid.initJqGrid("dataTableInfo",{
		url:"${ctx}/admin/personMessage/goJqGrid",
		height: 299,
		styleUI: 'Bootstrap',
		postData: {
			rcvStatus: "0",
			rcvCzy: "<%=czybh%>",
			rcvType: "3"
		},
		rowNum: 10000,
		isSelectOne: false,
		colModel : [
			{name : 'pk',index : 'pk',width : 100,label:'pk',sortable : true,align : "left", hidden: true},
			{name : 'msgrelno',index : 'msgrelno',width : 100,label:'msgrelno',sortable : true,align : "left", hidden: true},
			{name : 'msgtype',index : 'msgtype',width : 100,label:'msgtype',sortable : true,align : "left", hidden: true},
			{name : 'bmsgtype',index : 'bmsgtype',width : 100,label:'消息类型',sortable : true,align : "left"},
			{name : 'msgtext',index : 'msgtext',width : 310,label:'内容',sortable : true,align : "left", formatter: formatTitleViewBtnPersonMessage},
			{name : 'senddate',index : 'senddate',width : 150,label:'日期',sortable : true,align : "left",formatter:formatTime}
		],
		ondblClickRow:function(rowid,iRow,iCol,e){
			goTitleViewPersonMessage(rowid);
		},
		gridComplete: function() {
			tableNullTip("dataTableInfo");
		}
	});
});

function formatTitleViewBtnPersonMessage(v,x,r){
	return "<a href='#' onclick='goTitleViewPersonMessage("+x.rowId+");'>"+v+"</a>";
}

function goTitleViewPersonMessage(rowid){
	var ret = $("#dataTableInfo").jqGrid("getRowData", rowid);
	if (ret) {
		if(ret.msgtype == "18") {
			getWfProcInstPersonMessage(ret.msgrelno, ret.pk);
		}else{
			Global.Dialog.showDialog("personMessage_detail",{
				title:"查看消息",
				url:"${ctx}/admin/personMessage/goViewFromHome?pk="+ret.pk,
				height:330,
				width:800,
				returnFun: goto_query
			});
		}
	} else {
		art.dialog({
			content: "请选择一条记录"
		});
	}
}   

function getWfProcInstPersonMessage(no, msgPK){
	$.ajax({
		url: "${ctx}/admin/wfProcInst/findWfProcInst",
		type: "get",
		data: {
			no: no,
			type: 5
		},
		dataType: "json",
		error: function(obj){			    		
			art.dialog({
				content:"访问出错,请重试!",
				time:3000,
				beforeunload: function () {}
			});
		},
		success: function (res){
			if(res && res.rows && res.rows.length > 0){
				goWfProcInstViewPersonMessage(res.rows[0].taskid, res.rows[0].actprocinstid, res.rows[0].title, res.rows[0].statusdescr, msgPK);
			}else{
				art.dialog({
					content:"打开页面出错,请重试!",
					time:3000,
					beforeunload: function () {}
				});
			}
		} 
	});	
}

function goWfProcInstViewPersonMessage(taskid, actprocinstid, title, status, msgPK){
	var m_umState ="";
	if(status =="审批中"){
		m_umState = "C"; 
	}
    Global.Dialog.showDialog("view", {
		title: title,
		url: "${ctx }/admin/wfProcInst/goView?taskId=" + taskid + "&processInstanceId=" + actprocinstid + "&type=" + "1" +"&m_umState="+m_umState,
		height: 780,
		width: 1280,
    	close:function(){
    		doRcv(msgPK);
    	}
    });
}

function doRcv(msgPK){
	$.ajax({
		url: "${ctx}/admin/personMessage/doRcv",
		type: "get",
		data: {
			pk: msgPK
		},
		dataType: "json",
		error: function(obj){			    		
			art.dialog({
				content:"访问出错,请重试!",
				time:3000,
				beforeunload: function () {}
			});
		},
		success: function (res){
			goto_query();
		} 
	});		
}

function moreClickPersonMessage(){
	Global.Dialog.showDialog("moreViewPersonMessage",{
		title:"消息中心——更多",
		url:"${ctx}/admin/personMessage/goListFromHome",
		height:650,
		width:1000,
		returnFun: goto_query
	});
}	

function goto_query(retData){
	var tableId = "dataTableInfo";
	tableNullTip(tableId, true);
	$("#"+tableId).jqGrid("setGridParam",{
		postData:{
			rcvStatus: "0",
			rcvCzy: "<%=czybh%>",
			rcvType: "3"
		},
		page:1
	}).trigger("reloadGrid");
}
</script>
