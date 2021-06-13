<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<title>签到天数</title>
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_builderGroup.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
/**初始化表格*/
$(function(){
        //初始化表格
        var beginDate = $.trim($("#beginDate").val());
        var endDate = $.trim($("#endDate").val());
		Global.JqGrid.initJqGrid("dataTablecrtDate",{
			url:"${ctx}/admin/bzsgfx/goJqGridcrtDate",
			postData:{code:"${worker.code}",beginDate:beginDate,endDate:endDate,department2:$("#department2").val(),workType12:$("#workType12").val(),statisticalMethods:$("#statisticalMethods").val()},				
			height:300,		
			styleUI: 'Bootstrap',	
			colModel : [		
			    {name:'address',	index:'address',	width:120,	label:'楼盘地址',	sortable:true,align:"left" ,},
			    {name : 'crtdate',index : 'crtdate',width : 220,label:'签到时间',sortable : true,align : "left",formatter: formatTime},
				{name:'prjitem2descr',	index:'prjitem2descr',	width:90,	label:'施工阶段',	sortable:true,align:"left" ,},
				{name:'iscompletedescr',	index:'iscompletedescr',	width:90,	label:'是否完成',	sortable:true,align:"left" ,},
            ],
            beforeSelectRow:function(id){
			setTimeout(function(){
		    relocate(id,"dataTablecrtDate");
			},100);
			},
		    rowNum:100000 ,
				pager :'1', 
		});
});
function goto_query(){
	$("#dataTablecrtDate").jqGrid("setGridParam",{url:"${ctx}/admin/bzsgfx/goJqGridcrtDate",datatype:'json',postData:$("#page_form").jsonForm(),page:1,fromServer: true}).trigger("reloadGrid");
	change();
}
</script>
</head>
    
<body >               
<div class="body-box-form" style="padding-top: 0px;">
		<form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
			<input type="hidden" name="exportData" id="exportData">
			<ul class="ul-form">
			<li>
				<label>楼盘</label>
				<input type="text" id="address" name="address" />
			</li>
			<li id="loadMore" >
				<button type="button" class="btn  btn-system btn-sm" onclick="goto_query();"  >查询</button>
			</li>
			</ul>
		</form>
		<div class="pageContent" style="padding-top: 10px;">   
			<table id= "dataTablecrtDate"></table>
		</div>
</div>

<script src="${resourceRoot}/pub/component_wareHouse.js?v=${v}" type="text/javascript"></script>	
</body>
</html>


