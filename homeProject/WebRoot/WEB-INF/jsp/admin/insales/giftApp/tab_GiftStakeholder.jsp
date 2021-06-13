<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>
<%@ include file="/commons/jsp/common.jsp" %>
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
	var lastCellRowId;
	/**初始化表格*/
	var gridOption ={			
			url:"${ctx}/admin/giftApp/goJqGridGiftStakeholder?",
			postData:{no:"${giftApp.no}"},	
			ondblClickRow: function(){
                	$("#viewGiftAppDetail").on();
                },
			styleUI: 'Bootstrap',	
			height:210,
			rowNum:100000,  
    		pager :'1',
			colModel : [		
			  {name : 'pk',index : 'pk',width : 80,label:'pk',sortable : false,align : "left",frozen: true,hidden:true},
		      {name : 'no',index : 'no',width : 80,label:'编号',sortable : false,align : "left",frozen: true,hidden:true},
		      {name : 'role',index : 'role',width : 100,label:'角色',sortable : false,align : "left",hidden:true},	
		      {name : 'roledescr',index : 'roledescr',width : 100,label:'角色',sortable : false,align : "left"},		 
		      {name : 'empcode',index : 'empcode',width : 100,label:'员工编号',sortable : false,align : "left",hidden:true},
		      {name : 'empdescr',index : 'empdescr',width : 80,label:'员工姓名',sortable : false,align : "left"},		
		      {name : 'department1',index : 'department1',width : 100,label:'一级部门',sortable : false,align : "left",hidden:true},	
		      {name : 'department2',index : 'department2',width : 100,label:'二级部门',sortable : false,align : "left",hidden:true},
		      {name : 'department1descr',index : 'department1descr',width : 100,label:'一级部门',sortable : false,align : "left"},	
		      {name : 'department2descr',index : 'department2descr',width : 100,label:'二级部门',sortable : false,align : "left"},	
		      {name : 'shareper',index : 'shareper',width : 80,label:'分摊比例',editable:true,editrules: {number:true,required:true},sum:true,sortable : false,sortable : false,align : "right"},		
		      {name : 'lastupdate',index : 'lastupdate',width : 100,label:'最后修改时间',sortable : false,align : "left",formatter: formatTime},
		      {name : 'lastupdatedby',index : 'lastupdatedby',width : 80,label:'修改人',sortable : false,align : "left"},
		      {name : 'expired',index : 'expired',width : 80,label:'是否过期',sortable : false,align : "left"},
		      {name : 'actionlog',index : 'actionlog',width : 80,label:'操作日志',sortable : false,align : "left"}	     
			     
            ], 
            
	  		beforeSaveCell:function(rowId,name,val,iRow,iCol){
				lastCellRowId = rowId;
				
			},
			afterSaveCell:function(rowId,name,val,iRow,iCol){
			    var rowData = $("#dataTable_GiftStakeholder").jqGrid("getRowData",rowId);

	   		 },
	   		 beforeSelectRow:function(id){
         	   relocate(id,"dataTable_GiftStakeholder");
	       
            }
		   
 		}; 	     
       //初始化送货申请明细
	   Global.JqGrid.initEditJqGrid("dataTable_GiftStakeholder",gridOption);
	  
});
</script>	

<div class="body-box-list" style="margin-top: 0px;">
	<div class="panel panel-system" >
	    <div class="panel-body" >
	    	<div class="btn-group-xs" >
				<button type="button" class="btn btn-system "   id="addGiftStakeholder">新增</button>
				<button type="button" class="btn btn-system "   id="updateGiftStakeholder">编辑</button>
				<button type="button" class="btn btn-system "  id="delGiftStakeholder">删除</button>
				<button type="button" class="btn btn-system "  id="viewGiftStakeholder">查看</button>
				<button type="button" class="btn btn-system "  id="giftStakeholderExcel">输出到Excel</button>
			</div>
		</div>
	</div>
	<table id="dataTable_GiftStakeholder" style="overflow: auto;"></table>
</div>	
		




