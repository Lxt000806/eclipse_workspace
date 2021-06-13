<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>
<%@ include file="/commons/jsp/common.jsp" %>
<script type="text/javascript"> 
	$(function(){
		if( ($.trim($("#m_umState").val())=="V")){
			$("addGiftAppDetail").css("color","gray");
			$("updateGiftAppDetail").css("color","gray");
			$("delGiftAppDetail").css("color","gray");
			$("#addGiftAppDetail").attr("disabled",true);
			$("#updateGiftAppDetail").attr("disabled",true);
			$("#delGiftAppDetail").attr("disabled",true);
		}  	
		var lastCellRowId;
		/**初始化表格*/
		var gridOption ={			
			url:"${ctx}/admin/giftApp/goJqGridGiftAppDetail",	
			postData:{no:"${giftApp.no}"},
			ondblClickRow: function(){
                	$("#viewGiftAppDetail").on();
                },
            height:210,
			styleUI: 'Bootstrap',
			cellEdit:true,	
			rowNum:100000,  
    		pager :'1',		
			colModel : [		
			  {name : 'pk',index : 'pk',width : 70,label:'pk',sortable : true,align : "left",frozen: true,hidden:true},
			  {name : 'no',index : 'pk',width : 70,label:'编号',sortable : true,align : "left",frozen: true,hidden:true},
		      {name : 'itemcode',index : 'itemcode',width : 70,label:'礼品编号',sortable : true,align : "left"},
		      {name : 'itemdescr',index : 'itemdescr',width : 120,label:'礼品名称',sortable : true,align : "left"},
		      {name : 'tokenpk',index : 'tokenpk',width : 60,label:'礼品pk',sortable : true,align : "left",hidden:true},
		      {name : 'tokenno',index : 'tokenno',width : 60,label:'券号',sortable : true,align : "left"},	
		      {name : 'qty',index : 'qty',width : 50,label:'数量',editable:true,editrules: {number:true,required:true},sortable : true,sortable : true,align : "right"},	
		      {name : 'uomdescr',index : 'uomdescr',width : 50,label:'单位',sortable : true,align : "right"},	
		      {name : 'price',index : 'price',width : 50,label:'售价',sortable : true,align : "right",hidden:true},	
		      {name : 'cost',index : 'cost',width : 50,label:'单价',sortable : true,align : "right"},	
		      {name : 'sendqty',index : 'sendqty',width : 70,label:'已领数量',sortable : true,align : "right"},
		      {name : 'sumcost',index : 'sumcost',width : 80,label:'成本总金额',sortable : true,align : "right",sum: true},
		      {name : 'usediscamount',index : 'usediscamount',width : 80,label:'使用优惠额度',sortable : true,align : "left",},
		      {name : 'remarks',index : 'remarks',width : 200,label:'备注',editrules: {required:true},editable:true,sortable : true,align : "left"},	     	 	
		      {name : 'lastupdate',index : 'lastupdate',width : 90,label:'最后修改时间',sortable : true,align : "left",formatter: formatTime},
		      {name : 'lastupdatedby',index : 'lastupdatedby',width : 60,label:'修改人',sortable : true,align : "left"},
		      {name : 'expired',index : 'expired',width : 60,label:'是否过期',sortable : true,align : "left"},
		      {name : 'actionlog',index : 'actionlog',width : 60,label:'操作日志',sortable : true,align : "left"}		     
			     
            ], 
            loadonce: true,
	  		beforeSaveCell:function(rowId,name,val,iRow,iCol){
				lastCellRowId = rowId;
				
			},
			afterSaveCell:function(rowId,name,val,iRow,iCol){
			    var rowData = $("#dataTable_GiftAppDetail").jqGrid("getRowData",rowId);
			    rowData["sumcost"] = (parseFloat(rowData.qty)*parseFloat(rowData.cost)).toFixed(2);
				Global.JqGrid.updRowData("dataTable_GiftAppDetail",rowId,rowData);	
	   		 },
	   		 beforeSelectRow:function(id){
         	   relocate(id,"dataTable_GiftAppDetail");
	       
          }
		   
 		}; 	     
       //初始化送货申请明细
	   Global.JqGrid.initEditJqGrid("dataTable_GiftAppDetail",gridOption);
	  
});
</script>	

<div class="body-box-list" style="margin-top: 0px;">
	<div class="panel panel-system" >
	    <div class="panel-body" >
	    	<div class="btn-group-xs" >
				<button type="button" class="btn btn-system "   id="addGiftAppDetail">新增</button>
				<button type="button" class="btn btn-system "   id="updateGiftAppDetail">编辑</button>
				<button type="button" class="btn btn-system "  id="delGiftAppDetail">删除</button>
				<button type="button" class="btn btn-system "  id="viewGiftAppDetail">查看</button>
				<button type="button" class="btn btn-system "  id="giftAppDetailExcel">输出到Excel</button>	
			</div>
		</div>
	</div>
	<table id="dataTable_GiftAppDetail" style="overflow: auto;"></table>
</div>	
		




