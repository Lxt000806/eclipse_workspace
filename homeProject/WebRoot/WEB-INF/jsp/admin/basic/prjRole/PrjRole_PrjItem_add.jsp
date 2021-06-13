<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<title>工程角色施工</title>
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_builderGroup.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
$(function() {
	if( ($.trim($("#m_umState").val())=="C")||($.trim($("#m_umState").val())=="B")||($.trim($("#m_umState").val())=="V")){	
		 var Span1=$("#additem");
		 var Span2=$("#updateitem");
		 var Span3=$("#delitem");
		 Span1.css("display","none");
		 Span2.css("display","none");
		 Span3.css("display","none");		
	}  	
	$("#page_form").validate({
		rules: {				
			"itemType1": {
			validIllegalChar: true,
			required: true,
			maxlength: 10
			},
			"no": {
			validIllegalChar: true,
			required: true,
			maxlength: 10
			},
			"SendType": {
			validIllegalChar: true,
			required: true,
			maxlength: 10
			},
			"carryType": {
			validIllegalChar: true,
			required: true,
			maxlength: 10
			},
			"itemType2": {
			validIllegalChar: true,
			required: true,
			maxlength: 10
			},						
		}
	});
});


/**初始化表格*/
$(function(){
       //初始化表格
	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/prjRole/goJqGridPrjItem?prjRole="+"sas",
		ondblClickRow: function(){
               	viewitem();
               },				
		height:300,		
		styleUI: 'Bootstrap',	
		colModel : [		
		  {name : 'pk',index : 'pk',width : 80,label:'pk',sortable : true,align : "left",frozen: true,hidden:true},
	      {name : 'prjrole',index : 'prjrole',width : 80,label:'工程角色',sortable : true,align : "left",frozen: true,hidden:true},
	      {name : 'prjitem',index : 'prjitem',width : 100,label:'施工项目',sortable : true,align : "left",frozen: true,hidden:true},
	      {name : 'prjroledescr',index : 'prjroledescr',width : 80,label:'工程角色',sortable : true,align : "left",frozen: true,hidden:true},
	      {name : 'prjitemdescr',index : 'prjitemdescr',width : 180,label:'施工项目',sortable : true,align : "left",frozen: true},			 
	      {name : 'lastupdate',index : 'lastupdate',width : 220,label:'最后修改时间',sortable : true,align : "left",formatter: formatTime},
	      {name : 'lastupdatedby',index : 'lastupdatedby',width : 80,label:'修改人',sortable : true,align : "left"},
	      {name : 'expired',index : 'expired',width : 80,label:'是否过期',sortable : true,align : "left"},
	      {name : 'actionlog',index : 'actionlog',width : 80,label:'操作日志',sortable : true,align : "left"},
           ],
           beforeSelectRow:function(id){
		setTimeout(function(){
	    relocate(id,"dataTable");
		},100)
		},
	    rowNum:100000,  
	    pager :'1'
	});
});
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
			      	<button type="button" class="btn btn-system " id="ksadditem" >快速添加</button>
					<button type="button" class="btn btn-system " id="delitem" >删除</button>
			     </div>
			  	</div>
			</div>	
			<!--panelBar end-->
				<div id="content-list">
					<table id= "dataTable"></table>
					<div id="dataTablePager"></div>
				</div>
	</div>
</div>
<script src="${resourceRoot}/pub/component_wareHouse.js?v=${v}" type="text/javascript"></script>	
</body>
</html>


