<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>

<script type="text/javascript">
$(function(){
	var ret = selectDataTableRow();
	var postData = $("#page_form").jsonForm();
	var gridOption ={
			url:"${ctx}/admin/netCustAnaly/goNetChanelMonthView",
			postData:postData,
		    rowNum:10000000,
			height:500,
			styleUI: 'Bootstrap', 
			colModel : [
				{name : 'custcode',index : 'custcode',width : 95,label:'资源客户编号',sortable : true,align : "left"},	
				{name : 'address',index : 'address',width : 150,label:'资源客户楼盘',sortable : true,align : "left"},
				{name : 'custresstatdescr',index : 'custresstatdescr',width : 95,label:'资源客户状态',sortable : true,align : "left"},
				{name : 'businessmandescr',index : 'businessmandescr',width : 60,label:'跟踪人',sortable : true,align : "left"},
  				{name : 'crtczydescr',index : 'crtczydescr',width : 60,label:'创建人',sortable : true,align : "left"},
  				{name : 'dispatchdate',index : 'dispatchdate',width : 80,label:'派单时间',sortable : true,align : "left",formatter: formatDate},
  				{name : 'custaddress',index : 'custaddress',width : 150,label:'意向客户楼盘',sortable : true,align : "left"},
  				{name : 'custstatusdescr',index : 'custstatusdescr',width : 70,label:'客户状态',sortable : true,align : "left"},
  				{name : 'visitdate',index : 'visitdate',width : 80,label:'到店时间',sortable : true,align : "left",formatter: formatDate},
  				{name : 'setdate',index : 'setdate',width : 80,label:'下定时间',sortable : true,align : "left",formatter: formatDate},
  				{name : 'signdate',index : 'signdate',width : 80,label:'签单时间',sortable : true,align : "left",formatter: formatDate},
  				{name : 'contractfee',index : 'contractfee',width : 60,label:'合同额',sortable : true,align : "left"}
			], 
 		};
	   Global.JqGrid.initJqGrid("dataTable_month",gridOption);


});
 </script>
<div class="body-box-list" style="margin-top: 0px;">		
		<div class="clear_float">
			<div id="content-list" >
				<table id="dataTable_month"></table>
			</div>
		</div>
</div>
