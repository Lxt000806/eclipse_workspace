<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>

<script type="text/javascript">
$(function(){
	Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/itemBalAdjDetail/goJqGrid",
			postData:{ibhNo:'${itemBalAdjHeader.no }',noRepeat:'1'},
			styleUI: 'Bootstrap', 
			rowNum:10000000,
			height:271,
			colModel : [
				{name:'pk', index:'pk', width:105, label:'PK', sortable:true ,align:"left",hidden:true},
				{name:'itcode', index:'itcode', width:65, label:'材料编号', sortable:true ,align:"left"},
				{name:'ibhno', index:'ibhno', width:105, label:'仓库调整编号', sortable:true ,align:"left", hidden:true},
				{name:'itdescr', index:'itdescr', width:200, label:'材料名称', sortable:true ,align:"left"},
				{name:'uomdescr', index:'uomdescr', width:50, label:'单位', sortable:true, align:"left"},
				{name:'adjcost',index:'adjcost',width:70, label:'成本单价', sortable:true,align:"left",},
				{name:'aftcost',index:'aftcost',width:80, label:'变动后成本', sortable:true,align:"left", },
				{name:'adjqty',	index:'adjqty',width:70, label:'调整数量', sortable : true,align : "left",sum:true},
				{name:'qty', index:'qty', width:75, label:'变动后数量', sortable:true ,align:"left",sum:true},
				{name:"adjamount", index:"adjamount", width:70, label:"金额", sortable:true, align:"left", 
					formatter:formatterAdjAmount, sum:true},
				{name:'remarks',index:'remarks',width:180, label:'备注', sortable:true,align:"left"},	
				{name:'allqty',index:'allqty',width:250, label:'现存数量', sortable:true,align:"left", hidden:true},	
				{name:'cost', index:'cost', width:60, label:'调整成本', sortable : true,align : "left", sum:true, hidden:true},
				{name:'lastupdate',index : 'lastupdate',width : 135,label:'最后修改时间',sortable : true,align : "left",formatter:formatTime, hidden:true},
			    {name:'lastupdatedby',index : 'lastupdatedby',width : 42,label:'修改人',sortable : true,align : "left", hidden:true}
			],
		});

});
// 规范金额 add by zb
function formatterAdjAmount(cellvalue, options, rowObject) {
	return myRound(rowObject.adjcost * rowObject.adjqty, 2);
}
</script>
<div class="panel panel-system" >
    <div class="panel-body" >
      <div class="btn-group-xs" >
         	<button type="button" class="btn btn-system " onclick="view()">
  				<span>查看</span>
       	 <button type="button" class="btn btn-system " onclick="doExcelNow('仓库调整明细','dataTable');" >
			<span>导出Excel</span>
	</div>
	</div>
 </div>
<div class="body-box-list" style="margin-top: 0px;">
	<div id="content-list">
		<table id="dataTable" style="overflow: auto;"></table>
	</div>
</div>
