<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>
<%@ include file="/commons/jsp/common.jsp" %>
<style type="text/css">
      .SelectBG{
          background-color:#198ede!important;
            color:rgb(255,255,255)
            }
      .SelectBG_yellow{
          background-color:yellow;
         }
 </style>
<script type="text/javascript"> 

 $(function(){
     if( ($.trim($("#m_umState").val())=="V")){
		 $("addGiftStakeholder").css("color","gray");
		 $("updateGiftStakeholder").css("color","gray");
		 $("delGiftStakeholder").css("color","gray");
		 $("#addGiftStakeholder").attr("disabled",true);
		 $("#updateGiftStakeholder").attr("disabled",true);
		 $("#delGiftStakeholder").attr("disabled",true);
	  }  	 
	  console.log("${giftApp.custCode}");
	var lastCellRowId;
	/**初始化表格*/
	var gridOption ={			
			url:"${ctx}/admin/giftCheckOut/getIssueDetailJqgrid?",
			postData:{custCode:"${giftApp.custCode}"},	
			styleUI: 'Bootstrap',	
			height:270,
			colModel : [		
		      {name : 'no',index : 'no',width : 75,label:'领用单号',sortable : false,align : "left"}	 ,    
		      {name : 'actdescr',index : 'actdescr',width : 100,label:'活动名称',sortable : false,align : "left"}	,     
		      {name : 'apptypedescr',index : 'apptypedescr',width : 75,label:'领用类型',sortable : false,align : "left"},	     
		      {name : 'outtypedescr',index : 'outtypedescr',width : 75,label:'出库类型',sortable : false,align : "left"}	,     
		      {name : 'itemcode',index : 'itemcode',width : 75,label:'礼品编号',sortable : false,align : "left"}	   ,  
		      {name : 'itemdescr',index : 'itemdescr',width : 120,label:'礼品名称',sortable : false,align : "left"}	    , 
		      {name : 'qty',index : 'qty',width : 60,label:'数量',sortable : false,align : "right"}	     ,
		      {name : 'uomdescr',index : 'uomdescr',width : 60,label:'单位',sortable : false,align : "left"}	   ,  
		      {name : 'cost',index : 'cost',width : 60,label:'单价',sortable : false,align : "right"}	     ,
		      {name : 'amount',index : 'amount',width : 60,label:'金额',sortable : false,align : "right"}	     ,
		      {name : 'remarks',index : 'remarks',width : 180,label:'备注',sortable : false,align : "left"},
            ], 
 		}; 	     
       //初始化送货申请明细
	   Global.JqGrid.initJqGrid("dataTable_issue",gridOption);
	  
});
</script>	

<div class="body-box-list" style="margin-top: 0px;">
	<table id="dataTable_issue" style="overflow: auto;"></table>
</div>	
		


