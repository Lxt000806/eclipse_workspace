<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>
<script type="text/javascript">
	$(function(){
		/*材料选定时间*/
		var gridOption ={
			url:"${ctx}/admin/prjDelayAnaly/goCustConfirmResultJqGrid",
			postData:{custCode:"${customer.code}"},
		    rowNum:1000000,
			height: $(document).height()-$("#content-list-7").offset().top-245,
			styleUI: 'Bootstrap', 
			colModel: [
				{name: 'confitemtypedescr',index: 'confitemtypedescr',width: 100,label:'施工材料分类',align:"left",sortable: false},
		    	{name: 'itemconfstatusdescr',index: 'itemconfstatusdescr',width: 100,label:'确认状态',align: "left",sortable: false},
		    	{name: 'itemtimecode',index: 'itemtimecode',width: 120,label:'确认节点',align: "left",sortable: false,hidden:true},
		    	{name: 'itemtimedescr',index: 'itemtimedescr',width: 120,label:'确认节点',align: "left",sortable: false},
		    	{name: 'confirmdate',index: 'confirmdate',width: 120,label:'节点完成时间',align: "left",sortable: false, formatter: formatTime, hidden: true}	,		
		    	{name: 'custconfirmdate',index: 'custconfirmdate',width: 140,label:'确认时间',align: "left",sortable: false, formatter: formatTime}	,		
			], 
 		};
		//初始化送货申请明细
		Global.JqGrid.initJqGrid("dataTable_itemSetDate",gridOption);
	});
</script>
<div class="body-box-list" style="margin-top: 0px;">
	<div id="content-list-7">
		<table id="dataTable_itemSetDate"></table>
		<div id="dataTable_itemSetDatePager"></div>
	</div>
</div>
