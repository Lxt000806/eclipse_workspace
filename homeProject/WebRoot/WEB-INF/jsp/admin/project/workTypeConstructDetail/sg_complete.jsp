<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<title>完成套数</title>
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
		Global.JqGrid.initJqGrid("dataTablecomplete",{
			url:"${ctx}/admin/bzsgfx/goJqGridcomplete",
			postData:{code:"${worker.code}",beginDate:beginDate,endDate:endDate,department2:$("#department2").val(),workType12:$("#workType12").val(),statisticalMethods:$("#statisticalMethods").val()},		
			height:310,		
			styleUI: 'Bootstrap',	
			colModel : [		
				{name:'address',	index:'address',	width:120,	label:'楼盘地址',	sortable:true,align:"left" ,},
				{name:'custtypedescr',	index:'custtypedescr',	width:90,	label:'客户类型',	sortable:true,align:"left" ,},
				{name:'layoutdescr',	index:'layoutdescr',	width:90,	label:'户型',	sortable:true,align:"left" ,},
				{name:'area',	index:'area',	width:90,	label:'面积',	sortable:true,align:"right" ,},
				{name:'comedate',	index:'comedate',	width:90,	label:'工人进场时间',	sortable:true,align:"left" ,formatter:formatDate},
		        {name : 'mincrtdate',index : 'mincrtdate',width : 110,label:'首次签到时间',sortable : true,align : "left",formatter: formatDate},
		        {name : 'maxcrtdate',index : 'maxcrtdate',width : 110,label:'最后签到时间',sortable : true,align : "left",formatter: formatDate},
				{name:'planend',	index:'planend',	width:90,	label:'计划完工时间',	sortable:true,align:"left" ,formatter:formatDate},
				{name:'enddate',	index:'enddate',	width:90,	label:'实际完工时间',	sortable:true,align:"left" ,formatter:formatDate},
				{name:'constructday',	index:'constructday',	width:90,	label:'标准工期',	sortable:true,align:"right" ,},
				{name:'consdays',	index:'consdays',	width:90,	label:'施工天数',	sortable:true,align:"right" ,},
				{name:'signnum',	index:'signnum',	width:90,	label:'签到天数',	sortable:true,align:"right" ,},
				{name:'workload',	index:'workload',	width:90,	label:'完工工作量',	sortable:true,align:"right" ,sum:true},
				{name:'confdescr',	index:'confdescr',	width:90,	label:'验收人',	sortable:true,align:"left" ,},
				{name:'prjleveldescr',	index:'prjleveldescr',	width:90,	label:'验收评级',	sortable:true,align:"left" ,},
				{name:'projectmandescr',	index:'projectmandescr',	width:90,	label:'监理',	sortable:true,align:"left" ,},
				{name:'depa1descr',	index:'depa1descr',	width:90,	label:'工程事业部',	sortable:true,align:"left" ,},
				{name:'depa2descr',	index:'depa2descr',	width:90,	label:'工程部',	sortable:true,align:"left" ,},
				{name:'confirmamount',	index:'confirmamount',	width:90,	label:'工资发放额',	sortable:true,align:"right" ,},
				{name:'pk',	index:'pk',	width:90,	label:'pk',	sortable:true,align:"right" ,hidden:true}
				
            ],
            beforeSelectRow:function(id){
			setTimeout(function(){
		    relocate(id,"dataTablecomplete");
			},100)
			},
		    rowNum:100000,  
		    pager :'1'
		});
});

function signView() {
	var ret = selectDataTableRow("dataTablecomplete");
	if(ret){
		Global.Dialog.showDialog("SignView",{
			title:"完工工地——查看签到",
			url:"${ctx}/admin/bzsgfx/goSignView",		
			postData:{
				pk: ret.pk,
				beginDate:$("#beginDate").val(),
				endDate:$("#endDate").val()
			},
			height:600,
			width:1200
		});
	}else{
		art.dialog({content:"请选择一条数据",width:200});
	}
}
</script>
</head>
    
<body >        
      

<div class="body-box-list" >
	<div class="pageContent"  style="padding-top: 10px;">   
	
		<div class="btn-panel" style="margin-top:2px">
			<div class="btn-group-sm"  >
				<button id="signView" type="button" class="btn btn-system" onclick="signView()">查看签到</button>
			</div>
		</div>	 
		<table id= "dataTablecomplete"></table>
	</div>
</div>
<script src="${resourceRoot}/pub/component_wareHouse.js?v=${v}" type="text/javascript"></script>	
</body>
</html>


