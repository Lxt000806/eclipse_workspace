<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<title>适用产品线列表</title>
	<%@ include file="/commons/jsp/common.jsp" %>
<script type="text/javascript">
/**初始化表格*/
$(function(){
    //初始化表格
	Global.JqGrid.initJqGrid("dataTable_custType",{
		url:"${ctx}/admin/gift/goCustTypeJqGrid?pk="+"${gift.pk }",
		ondblClickRow: function(){
        	goCustType('V','查看');
        },				
		height:$(document).height()-$("#content-list").offset().top-67,		
		styleUI: 'Bootstrap',	
		colModel : [		
		  {name : 'pk',index : 'pk',width : 80,label:'pk',sortable : true,align : "left",hidden:true},
	      {name : 'giftpk',index : 'giftpk',width : 80,label:'giftpk',sortable : true,align : "left",hidden:true},
	      {name : 'custtype',index : 'custtype',width : 100,label:'custtype',sortable : true,align : "left",hidden:true},		 
	      {name : 'custtypedescr',index : 'custtypedescr',width : 80,label:'客户类型',sortable : true,align : "left"},	
	      {name : 'lastupdate',index : 'lastupdate',width : 130,label:'最后修改时间',sortable : true,align : "left",formatter: formatTime},
	      {name : 'lastupdatedby',index : 'lastupdatedby',width : 80,label:'修改人',sortable : true,align : "left"},
	      {name : 'expired',index : 'expired',width : 80,label:'是否过期',sortable : true,align : "left"},
	      {name : 'actionlog',index : 'actionlog',width : 80,label:'操作日志',sortable : true,align : "left"}
	     ]
	});
});
function goCustType(m_umState,name){
	var ret = selectDataTableRow("dataTable_custType");		
	var custTypes = $("#dataTable_custType").getCol("custtype", false).join(",");
	if(!ret && m_umState!="A"){
		art.dialog({    	
			content: "请选择一条记录"
		});
		return;
	}
    Global.Dialog.showDialog("goCustTypeAdd",{
		title:"适用产品线--"+name,
		url:"${ctx}/admin/gift/goCustTypeAdd",	
		postData:{
		  	custType:m_umState=="A"?"":ret.custtype,
		  	m_umState:m_umState,custTypeDescr:m_umState=="A"?"":ret.custtypedescr,
		  	custTypes:custTypes
		},	                 			 		 
		height:450,
		width:500,
		rowNum:100000,
		returnFun:function(data){
			var id = $("#dataTable_custType").jqGrid("getGridParam","selrow");
			if(data){
				$.each(data,function(k,v){
					var json = {
						custtype:v.custType,
		                custtypedescr:v.custTypeDescr,
		                lastupdate: new Date(),
		                lastupdatedby:"${USER_CONTEXT_KEY.czybh}",
		                expired: "F",
		                actionlog: m_umState=="M"?"EDIT":"ADD",
					};
					if(m_umState=="M"){
						$("#dataTable_custType").setRowData(id, json);
					}else if(m_umState=="A"){
						Global.JqGrid.addRowData("dataTable_custType",json);
					}
				});
			}
		}   
	}); 
}
function custTypeDel(){
	var id = $("#dataTable_custType").jqGrid("getGridParam","selrow");
	if(!id){
		art.dialog({content: "请选择一条记录进行删除操作！"});
		return false;
	}
	art.dialog({
		content:"是否确认要删除？",
		lock: true,
		ok: function () {
			Global.JqGrid.delRowData("dataTable_custType",id);
		},
		cancel: function () {
			return true;
		}
	}); 
}
</script>
</head>
    
<body >               
<div class="body-box-form" style="margin-top: 0px;">
	<div class="pageContent">         
		<!--query-form-->
			<!--panelBar-->
			<div class="panel panel-system" >
			   <div class="panel-body" >
			     <div class="btn-group-xs" >
			      	<button type="button" class="btn btn-system view" id="additem" onclick="goCustType('A','添加')">添加</button>
					<button type="button" class="btn btn-system view" id="updateitem" onclick="goCustType('M','编辑')">编辑</button>
					<button type="button" class="btn btn-system view" id="delitem" onclick="custTypeDel()">删除</button>
					<button type="button" class="btn btn-system " id="viewitem" onclick="goCustType('V','查看')">查看</button>	
			     </div>
			  	</div>
			</div>	
			<!--panelBar end-->
			<div id="content-list">
				<table id= "dataTable_custType"></table>
			</div>
	</div>
</div>
</body>
</html>


