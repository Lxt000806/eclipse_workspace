<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/commons/jsp/common.jsp" %>
<script type="text/javascript">
	/**初始化表格*/
$(document).ready(function(){
		Global.JqGrid.initJqGrid("dataTable_totalByCompany",{
			url:"${ctx}/admin/whCheckOut/goJqGrid_totalByCompany", 
			postData:{no:$.trim($("#no").val())},
		    rowNum:10000000,
			height:280,
        	styleUI: 'Bootstrap',
			colModel : [
			     {name: "cmpname", index: "cmpname", width: 116, label: "公司", sortable: true, align: "left", count: true},
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
				<button type="button" class="btn btn-system "  id="totalByCompanyExcel">输出到Excel</button>	
				<button type="button" class="btn btn-system "  id="totalByCompanyPrint">打印</button>
			</div>
		</div>
	</div>
	<table id="dataTable_totalByCompany" style="overflow: auto;"></table>
</div>




