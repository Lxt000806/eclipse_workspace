<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>

<script type="text/javascript">
$(function(){
	var gridOption = {
		url:"${ctx}/admin/giftCheckOut/goDetailJqGrid",
		postData:{checkOutNo:'${giftCheckOut.no}'},
		height:$(document).height()-$("#content-list").offset().top-70,
		rowNum:10000000,
		styleUI: 'Bootstrap', 
		colModel : [
			{name:'CheckSeq', index:'checkSeq', width:80, label:'结算顺序号', sortable:true ,align:"left",hidden:true },
			{name:'No', index:'No', width:80, label:'领用单号', sortable:true ,align:"left",count:true },
			{name:'Status', index:'Status', width:80, label:'状态', sortable:true ,align:"left" ,hidden:true},
			{name:'statusdescr', index:'statusdescr', width:80, label:'状态', sortable:true ,align:"left" ,},
			{name:'Type', index:'Type', width:80, label:'类型', sortable:true ,align:"left" ,hidden:true},
			{name:'typedescr', index:'typedescr', width:80, label:'类型', sortable:true ,align:"left" },
			{name:'CustCode', index:'CustCode', width:80, label:'客户编号', sortable:true ,align:"left",hidden:true }, 
			{name:'documentno', index:'documentno', width:85, label:'档案号', sortable:true ,align:"left" }, 
			{name:'address', index:'address', width:115, label:'楼盘', sortable:true ,align:"left" }, 
			{name:'setdate', index:'setdate', width:80, label:'下定时间', sortable:true ,align:"left" , formatter:formatDate},
			{name:'signdate', index:'signdate', width:80, label:'签订时间', sortable:true ,align:"left" , formatter:formatDate},
			{name:'customerendcode', index:'customerendcode', width:80, label:'结束代码', sortable:true ,align:"left" },
			{name:'SendDate', index:'SendDate', width:80, label:'发货时间', sortable:true ,align:"left" , formatter:formatDate},
			{name:'UseMan', index:'UseMan', width:80, label:'使用人', sortable:true ,align:"left",hidden:true },
			{name:'usedescr', index:'usedescr', width:80, label:'使用人', sortable:true ,align:"left" },
			{name:'AppCZY', index:'AppCZY', width:80, label:'申请人', sortable:true ,align:"left",hidden:true }, 
			{name:'appdescr', index:'appdescr', width:80, label:'申请人', sortable:true ,align:"left",hidden:true }, 
			{name:'Date', index:'Date', width:80, label:'申请时间', sortable:true ,align:"left" , formatter:formatDate,hidden:true},
			{name:'itemsumcost', index:'itemsumcost', width:80, label:'金额', sortable:true ,align:"right",sum:true },
			{name:'Phone', index:'Phone', width:80, label:'电话', sortable:true ,align:"left",hidden:true },
			{name:'bm', index:'bm', width:80, label:'业务员', sortable:true ,align:"left",},
			{name:'dm', index:'dm', width:80, label:'设计师', sortable:true ,align:"left",},
			{name:'custsource', index:'custsource', width:80, label:'客户来源', sortable:true ,align:"left" },
			{name:'netchaneldescr', index:'netchaneldescr', width:80, label:'渠道', sortable:true ,align:"left" },
			{name:'bd', index:'bd', width:80, label:'业务部', sortable:true ,align:"left",},
			{name:'dd', index:'dd', width:80, label:'设计部', sortable:true ,align:"left",},
			{name:'ActNo', index:'ActNo', width:80, label:'活动编号', sortable:true ,align:"left",hidden:true },
			{name:'descr', index:'descr', width:80, label:'活动名称', sortable:true ,align:"left" },
			{name:'Remarks', index:'Remarks', width:80, label:'备注', sortable:true ,align:"left" },
		],
		gridComplete:function(){
			var itemsumcost = parseFloat($("#dataTable").getCol('itemsumcost', false, 'sum'));
			var itemsumcost = myRound(itemsumcost,2);
			$("#dataTable").footerData('set', {"itemsumcost": itemsumcost});
		}
	};
		Global.JqGrid.initJqGrid("dataTable",gridOption);

});
 </script>
<div class="body-box-list" style="margin-top: 0px;">
		<!-- 	<div class="panel panel-system" >
				<div class="panel-body" >
					<div class="btn-group-xs" >
						<button type="button" class="btn btn-system "  id="view" onclick="view()">
							<span>查看</span></button>
						<button type="button" class="btn btn-system " onclick="doExcelNow('采购订单明细')" title="导出当前excel数据" >
							<span>导出excel</span></button>
					</div>
			</div>
		</div> -->
	<div id="content-list">
		<table id="dataTable" style="overflow: auto;"></table>
	</div>
</div>




