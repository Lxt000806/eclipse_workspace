<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/commons/jsp/common.jsp" %>
<script type="text/javascript">
	/**初始化表格*/
$(document).ready(function(){
		Global.JqGrid.initJqGrid("dataTable_totalByItemType2",{
			url:"${ctx}/admin/whCheckOut/goJqGrid_totalByItemType2", 
			postData:{no:$.trim($("#no").val())},
		    rowNum:10000000,
			height:280,
        	styleUI: 'Bootstrap',
			colModel : [
			     {name: "itemtype2descr", index: "itemtype2descr", width: 116, label: "材料类型2", sortable: true, align: "left", count: true},
                 {name: "sumcost2", index: "sumcost2", width: 109, label: "总金额", sortable: true, align: "right", sum: true},
                 {name: "sumprojectcost", index: "sumprojectcost", width: 140, label: "项目经理结算价", sortable: true, align: "right", sum: true}
            ]
		});
});

 </script>
<div class="body-box-list" style="margin-top: 0px;">
	<div class="panel panel-system" >
	    <div class="panel-body" >
	    	<div class="btn-group-xs" >
				<button type="button" class="btn btn-system "  id="totalByItemTypeExcel">输出到Excel</button>
				<button type="button" class="btn btn-system "  id="totalByItemType2Print">打印</button>
			</div>
		</div>
	</div>
	<table id="dataTable_totalByItemType2" style="overflow: auto;"></table>
</div>




