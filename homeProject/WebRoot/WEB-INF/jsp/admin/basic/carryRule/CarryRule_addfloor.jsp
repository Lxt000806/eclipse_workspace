<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>
<%
UserContext uc = (UserContext)request.getSession().getAttribute(CommonConstant.USER_CONTEXT_KEY);
String czylb=uc.getCzylb();
String czybh="";
if("2".equals(czylb)) czybh=uc.getCzybh();
%>

<script type="text/javascript"> 
function viewfloor(){
	var ret = selectDataTableRow("dataTable_itemAppSend");
    if (ret) {
    	Global.Dialog.showDialog("itemSetView",{
			title:"查看楼层",
		    url:"${ctx}/admin/CarryRule/goaddView",	
		    postData:{
		    	beginFloor:ret.beginfloor,
                endFloor:ret.endfloor,
                cardAmount:ret.cardamount,   
                incValue:ret.incvalue,
            },		 			 
		  	height:600,
		 	width:1000,
		    returnFun:function(data){
				var id = $("#dataTable_itemAppSend").jqGrid("getGridParam","selrow");
					Global.JqGrid.delRowData("dataTable_itemAppSend",id);
					if(data){
						$.each(data,function(k,v){
						var json = {
							beginfloor:v.beginFloor,
		                    endfloor:v.endFloor,
		                    cardamount:v.cardAmount,   
		                    incvalue:v.incValue,		                    		                   		                   
						 };
						Global.JqGrid.addRowData("dataTable_itemAppSend",json);
					    });
				    }
			  }   
		});
    } else {
    	art.dialog({    	
			content: "请选择一条记录"
		});
    }
}
$(function(){
    if(($.trim($("#m_umState").val())=="B")||($.trim($("#m_umState").val())=="V")){
		 var Span1=$("#addfloor");
		 var Span2=$("#updatefloor");
		 var Span3=$("#delfloor");
		 Span1.css("display","none");
		 Span2.css("display","none");
		 Span3.css("display","none");		
	}  	
	var lastCellRowId;
	Global.LinkSelect.initSelect("${ctx}/admin/item/itemTypeByAuthority","itemType1","itemType2","itemType3");
	/**初始化表格*/
	var gridOption ={			
		url:"${ctx}/admin/CarryRule/goJqGridDetailadd?no="+"${carryRule.no}",	
		ondblClickRow: function(){
        	viewfloor();
        },
        styleUI: 'Bootstrap',
		height:$(document).height()-$("#content-list").offset().top-35,			
		colModel : [		
			  {name : 'pk',index : 'pk',width : 70,label:'pk',sortable : true,align : "left",frozen: true,hidden:true},
		      {name : 'no',index : 'no',width : 70,label:'编号',sortable : true,align : "left",frozen: true,hidden:true},
		      {name : 'beginfloor',index : 'beginfloor',width : 70,label:'起始楼层',sortable : true,align : "left",frozen: true},
		      {name : 'endfloor',index : 'endfloor',width : 60,label:'截至楼层',sortable : true,align : "left",frozen: true},	
		      {name : 'cardamount',index : 'cardamount',width : 60,label:'初始金额',sortable : true,align : "left",frozen: true},	
		      {name : 'incvalue',index : 'incvalue',width : 60,label:'递增值',sortable : true,align : "left",frozen: true},			     	 	
		      {name : 'lastupdate',index : 'lastupdate',width : 150,label:'最后修改时间',sortable : true,align : "left",formatter: formatTime},
		      {name : 'lastupdatedby',index : 'lastupdatedby',width : 60,label:'修改人',sortable : true,align : "left"},
		      {name : 'expired',index : 'expired',width : 60,label:'是否过期',sortable : true,align : "left"},
		      {name : 'actionlog',index : 'actionlog',width : 60,label:'操作日志',sortable : true,align : "left"}		     
        ], 
		beforeSelectRow:function(id){
			setTimeout(function(){
				relocate(id,"dataTable_itemAppSend");
			},100)
		},
	  	beforeSaveCell:function(rowId,name,val,iRow,iCol){
			lastCellRowId = rowId;
		},
		afterSaveCell:function(rowId,name,val,iRow,iCol){
			var rowData = $("#dataTable_itemAppSend").jqGrid("getRowData",rowId);
			if(("<%=czylb %>"=="2")&&(($.trim($("#m_umState").val())=="A")||($.trim($("#m_umState").val())=="M"))){
		    	rowData["projectothercost"] = (-1)*(parseFloat(rowData.whfee)+parseFloat(rowData.sendfee)).toFixed(2);
	    	}
	        rowData["sumcost"] = (parseFloat(rowData.itemsumcost)+parseFloat(rowData.whfee)+parseFloat(rowData.sendfee)).toFixed(2);
	        rowData["sumprojectamount"] = (parseFloat(rowData.projectamount)+parseFloat(rowData.whfee)+parseFloat(rowData.sendfee)+parseFloat(rowData.projectothercost)).toFixed(2);
	    		Global.JqGrid.updRowData("dataTable_itemAppSend",rowId,rowData);
	   		}
 		}; 	     
       //初始化送货申请明细
	   Global.JqGrid.initEditJqGrid("dataTable_itemAppSend",gridOption);
	   if("<%=czylb %>"!="1"){
            $("#dataTable_itemAppSend").jqGrid('hideCol', "itemsumcost");
            $("#dataTable_itemAppSend").jqGrid('hideCol', "projectothercost");
            $("#dataTable_itemAppSend").jqGrid('hideCol', "sumcost");
            $("#dataTable_itemAppSend").jqGrid('hideCol', "sumprojectamount");
        }              
});
</script>		
<div class="body-box-list" style="margin-top: 0px;">
	<div class="pageContent">
			 <div class="panel panel-system" >
			   <div class="panel-body" >
			     <div class="btn-group-xs" >
			      	<button type="button" class="btn btn-system " id="addfloor" >添加</button>
					<button type="button" class="btn btn-system " id="updatefloor" >编辑</button>
					<button type="button" class="btn btn-system " id="delfloor" >删除</button>
					<button type="button" class="btn btn-system " id="viewfloor" >查看</button>
			     </div>
			  	</div>
			</div>			
		<div id="content-list">	
			<table id="dataTable_itemAppSend" style="overflow: auto;"></table>
		</div>
	</div>
</div>




